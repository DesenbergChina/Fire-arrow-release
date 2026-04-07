/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-24 15:32:46
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.item;

import firearrow.mod.entity.FireballArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FireballArrowItem extends ArrowItem {
    public FireballArrowItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter,
            @Nullable ItemStack weaponStack) {

        return new FireballArrowEntity(world, shooter, stack.copyWithCount(1), weaponStack);
    }
}
