/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-24 15:32:46
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.entity;

import firearrow.mod.FireArrow;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FireballArrowEntity extends Arrow {

    private boolean ifFireballSpawned = false;

    public FireballArrowEntity(EntityType<? extends FireballArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FireballArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {

        // 1. 调用允许自定义 EntityType 的构造函数
        super(FireArrow.FIREBALL_ARROW_ENTITY, world);

        // 2. 手动设置所有者
        this.setOwner(owner);

        // 3. 手动设置位置
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FireArrow.FIREBALL_ARROW);
    }

    // 射中生物时生成火焰弹
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult); // 先调用父类逻辑（造成伤害）

        if (!this.level().isClientSide()) {
            // 1. 让被击中的实体着火
            entityHitResult.getEntity().igniteForSeconds(8);

            // 2. 在击中位置生成火焰弹（大火球）
            LivingEntity owner = (LivingEntity) this.getOwner();
            Vec3 velocity = this.getDeltaMovement().normalize().scale(1.5);
            LargeFireball fireball = new LargeFireball(
                    this.level(),
                    owner,
                    velocity,
                    2 // 爆炸威力
            );
            // 设置位置到击中点
            Vec3 pos = entityHitResult.getLocation();
            fireball.setPos(pos.x, pos.y, pos.z);
            this.level().addFreshEntity(fireball);
        }
    }

    // 击中方块时保持原版箭的行为（插在方块上）
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult); // 调用父类逻辑，让箭插在方块上
        // 不做额外处理，箭会正常插在方块上
    }

    @Override
    public void tick() {
        super.tick();
        // 在客户端显示箭的火焰粒子效果
        if (this.level().isClientSide() && (!this.onGround() ||
                !this.isInGround())) {
            this.level().addParticle(
                    net.minecraft.core.particles.ParticleTypes.FLAME,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0, 0, 0);
        }

        // 射出时立即生成火焰弹，然后销毁箭矢
        if (!this.level().isClientSide() && !ifFireballSpawned) {
            Vec3 velocity = this.getDeltaMovement();

            // 确保有速度才生成，否则火球会悬空
            if (velocity.lengthSqr() > 0.1) {
                // 获取发射者并进行类型检查
                Entity owner = this.getOwner();
                if (owner instanceof LivingEntity livingOwner) {
                    // 生成火焰弹（大火球）
                    LargeFireball fireball = new LargeFireball(
                            this.level(),
                            livingOwner,
                            velocity.normalize().scale(1.5), // 火球飞行速度
                            1 // 爆炸威力
                    );
                    // 设置位置
                    fireball.setPos(this.getX(), this.getY(), this.getZ());
                    // 生成火球
                    this.level().addFreshEntity(fireball);
                }

                // 标记已生成并在本 tick 结束时销毁箭矢
                ifFireballSpawned = true;
                // 生成火球后销毁箭矢，让视觉效果变成"射出火球"
                this.discard();
            }
        }
    }
}
