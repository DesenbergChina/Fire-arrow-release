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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BedArrowEntity extends ArrowEntity {

    public BedArrowEntity(EntityType<? extends BedArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public BedArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FireArrow.BED_ARROW_ENTITY, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(FireArrow.BED_ARROW);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if (this.getEntityWorld().isClient()) {
            return;
        }

        RegistryKey<World> worldKey = this.getEntityWorld().getRegistryKey();
        if (worldKey == World.NETHER || worldKey == World.END) {
            // Match bed-like behavior: strong explosion when used in Nether/End.
            this.getEntityWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 5.0F,
                    World.ExplosionSourceType.BLOCK);
            this.discard();
        }
    }
}
