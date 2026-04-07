/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-03-06
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description:
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file)
 */
package firearrow.mod.entity;

import firearrow.mod.FireArrow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BedArrowEntity extends Arrow {

    public BedArrowEntity(EntityType<? extends BedArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public BedArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FireArrow.BED_ARROW_ENTITY, world);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FireArrow.BED_ARROW);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        if (this.level().isClientSide()) {
            return;
        }

        ResourceKey<Level> worldKey = this.level().dimension();
        if (worldKey == Level.NETHER || worldKey == Level.END) {
            // Match bed-like behavior: strong explosion when used in Nether/End.
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5.0F,
                    Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }
}
