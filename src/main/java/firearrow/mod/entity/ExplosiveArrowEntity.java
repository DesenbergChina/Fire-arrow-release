/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-24 15:32:46
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.entity;

import firearrow.mod.FireArrow; // 引用主类获取 Item 实例
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExplosiveArrowEntity extends ArrowEntity {

    // 默认构造函数（用于 EntityType 创建）
    public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    // 用于发射的构造函数
    public ExplosiveArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FireArrow.EXPLOSIVE_ARROW_ENTITY, world); // 1. 先用正确的 EntityType 初始化
        this.setOwner(owner); // 2. 设置所有者

        // 3. 设置位置和初始状态 (参考原版 ArrowEntity 构造函数逻辑)
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());

        // 4. 保存物品堆
        // 在 1.21+ 中，设置 stack 可能需要通过特定方法或直接在初始化后不做（依赖 ArrowItem 的后续处理），
        // 但通常最好在这里确保存储了正确的 Stack
        // 如果需要，可以在这里设置默认伤害或其他属性
        // this.setDamage(2.0); // 示例
    }

    // 关键：重写此方法以确保捡起时是爆炸箭
    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(FireArrow.EXPLOSIVE_ARROW);
    }

    @Override
    public void tick() {
        super.tick();
        // 如果需要飞行粒子效果可以在这里加
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        // 爆炸逻辑
        if (!this.getEntityWorld().isClient()) {

            this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.3F,
                    World.ExplosionSourceType.TNT);
        }
    }

    // 如果击中方块也想爆炸，需要重写 onBlockHit 或 checkBlockCollision，或者简单地在 tick 里检查
    // isStackInGround
    /*
     * @Override
     * protected void onBlockHit(net.minecraft.util.hit.BlockHitResult
     * blockHitResult) {
     * super.onBlockHit(blockHitResult);
     * if (!this.getEntityWorld().isClient()) {
     * this.getEntityWorld().createExplosion(this, this.getX(), this.getY(),
     * this.getZ(), 3.0F, World.ExplosionSourceType.TNT);
     * this.discard();
     * }
     * }
     */
}
