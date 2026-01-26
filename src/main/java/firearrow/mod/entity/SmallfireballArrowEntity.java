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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
//import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SmallfireballArrowEntity extends ArrowEntity {

    private boolean ifFireballSpawned = false;

    public SmallfireballArrowEntity(EntityType<? extends SmallfireballArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public SmallfireballArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {

        // 1. 调用允许自定义 EntityType 的构造函数
        super(FireArrow.SMALL_FIREBALL_ARROW_ENTITY, world);

        // 2. 手动设置所有者
        this.setOwner(owner);

        // 3. 手动设置位置
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(FireArrow.SMALL_FIREBALL_ARROW);
    }

    // 击中实体时让其着火并生成小火球
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult); // 先调用父类逻辑（造成伤害）

        if (!this.getEntityWorld().isClient()) {
            // 1. 让被击中的实体着火
            entityHitResult.getEntity().setOnFireFor(5);

            // 2. 在击中位置生成小火球
            Vec3d pos = entityHitResult.getPos();
            SmallFireballEntity fireball = new SmallFireballEntity(
                    this.getEntityWorld(),
                    pos.x,
                    pos.y,
                    pos.z,
                    new Vec3d(0, -0.1, 0) // 向下微速度模拟爆炸
            );
            fireball.setOwner(this.getOwner());
            this.getEntityWorld().spawnEntity(fireball);

            // 3. 销毁箭矢（避免箭还插在实体身上）
            this.discard();
        }
    }

    // 击中方块时的行为（不生成火球，保持原版箭的行为）
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult); // 调用父类逻辑，让箭插在方块上

        // 不做额外处理，箭会正常插在方块上
        // 如果你想要箭插在方块上时冒火焰粒子，可以在这里添加粒子效果
    }

    // 可选：飞行过程中显示火焰粒子效果
    @Override
    public void tick() {
        super.tick();

        // 在客户端显示火焰粒子轨迹
        if (this.getEntityWorld().isClient() && (!this.isOnGround() || !this.isInGround())) {
            this.getEntityWorld().addParticleClient(
                    net.minecraft.particle.ParticleTypes.FLAME,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0, 0, 0);
        }

        if (!this.getEntityWorld().isClient() && !ifFireballSpawned) {
            Vec3d velocity = this.getVelocity();

            // 确保有速度才生成，否则火球会悬空
            if (velocity.lengthSquared() > 0.1) {
                // 生成火球
                // [!code focus] 同样这里也用 getEntityWorld()
                SmallFireballEntity fireball = new SmallFireballEntity(
                        this.getEntityWorld(),
                        this.getX(), this.getY(), this.getZ(),
                        velocity.normalize().multiply(1.5) // 火球飞行速度
                );

                fireball.setOwner(this.getOwner());
                this.getEntityWorld().spawnEntity(fireball); // [!code focus] 还有这里

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
