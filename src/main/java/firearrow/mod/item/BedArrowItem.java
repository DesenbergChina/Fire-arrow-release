/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-03-06
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description:
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file)
 */
package firearrow.mod.item;

import firearrow.mod.entity.BedArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BedArrowItem extends ArrowItem {
    public BedArrowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter,
            @Nullable ItemStack weaponStack) {
        return new BedArrowEntity(world, shooter, stack.copyWithCount(1), weaponStack);
    }
}
