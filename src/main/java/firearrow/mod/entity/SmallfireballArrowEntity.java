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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SmallfireballArrowEntity extends Arrow {

    private boolean ifFireballSpawned = false;

    public SmallfireballArrowEntity(EntityType<? extends SmallfireballArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public SmallfireballArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {

        // 1. 调用允许自定义 EntityType 的构造函数
        super(FireArrow.SMALL_FIREBALL_ARROW_ENTITY, world);

        // 2. 手动设置所有者
        this.setOwner(owner);

        // 3. 手动设置位置
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FireArrow.SMALL_FIREBALL_ARROW);
    }

    // 因为可能生物靠的太近了，此时需要这个逻辑生成小火球
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult); // 先调用父类逻辑（造成伤害）

        if (!this.level().isClientSide()) {
            // 1. 让被击中的实体着火
            entityHitResult.getEntity().igniteForSeconds(5);

            // 2. 在击中位置生成小火球
            Vec3 pos = entityHitResult.getLocation();
            SmallFireball fireball = new SmallFireball(
                    this.level(),
                    pos.x,
                    pos.y,
                    pos.z,
                    new Vec3(0, -0.1, 0) // 向下微速度模拟爆炸
            );
            fireball.setOwner(this.getOwner());
            this.level().addFreshEntity(fireball);
        }
    }

    // 击中方块时的行为（不生成火球，保持原版箭的行为）
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult); // 调用父类逻辑，让箭插在方块上

        // 不做额外处理，箭会正常插在方块上
        // 如果你想要箭插在方块上时冒火焰粒子，可以在这里添加粒子效果
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

        if (!this.level().isClientSide() && !ifFireballSpawned) {
            Vec3 velocity = this.getDeltaMovement();

            // 确保有速度才生成，否则火球会悬空
            if (velocity.lengthSqr() > 0.1) {
                // 生成火球
                SmallFireball fireball = new SmallFireball(
                        this.level(),
                        this.getX(), this.getY(), this.getZ(),
                        velocity.normalize().scale(1.5) // 火球飞行速度
                );
                // 2. 继承发射者（这样火球算玩家的伤害，且不会一开始炸到玩家）
                fireball.setOwner(this.getOwner());
                // 3. 生成火球
                this.level().addFreshEntity(fireball);

                // 4. 标记已生成并在本 tick 结束时销毁箭矢
                ifFireballSpawned = true;
                // 生成火球后销毁箭矢，让视觉效果变成“射出火球”
                this.discard();
            }

        }
    }

    // 撞击任何东西（方块或实体）时生成火球效果
    /*
     * @Override
     * protected void onCollision(HitResult hitResult) {
     * super.onCollision(hitResult);
     * if (!this.getEntityWorld().isClient()) {
     * // 在撞击位置生成一个小火球
     * Vec3d pos = hitResult.getPos();
     * SmallFireballEntity fireball = new SmallFireballEntity(
     * this.getEntityWorld(),
     * pos.x,
     * pos.y,
     * pos.z,
     * new Vec3d(0, -0.1, 0) // 向下的微小速度，模拟爆炸效果
     * );
     * fireball.setOwner(this.getOwner());
     * this.getEntityWorld().spawnEntity(fireball);
     * 
     * this.discard(); // 销毁箭矢
     * }
     * }
     */
}
