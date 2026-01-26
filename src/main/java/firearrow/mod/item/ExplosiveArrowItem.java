/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-24 15:32:46
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.item;

import firearrow.mod.entity.ExplosiveArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExplosiveArrowItem extends ArrowItem {
    public ExplosiveArrowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter,
            @Nullable ItemStack weaponStack) {

        return new ExplosiveArrowEntity(world, shooter, stack.copyWithCount(1), weaponStack);
    }
}
