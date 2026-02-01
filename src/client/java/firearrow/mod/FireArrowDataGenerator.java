/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-23 19:55:03
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */

package firearrow.mod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class FireArrowDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        // 添加配方生成器
        pack.addProvider(FireArrowRecipeProvider::new);

        // 添加标签生成器 (这是新增的核心代码！)
        pack.addProvider(FireArrowItemTagProvider::new);
    }

    // ========== 配方生成器 (你已有的代码) ==========
    static class FireArrowRecipeProvider extends FabricRecipeProvider {
        public FireArrowRecipeProvider(FabricDataOutput output,
                CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries,
                RecipeExporter exporter) {

            return new RecipeGenerator(registries, exporter) {
                @Override
                public void generate() {
                    RegistryEntryLookup<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                    // Explosive Arrow: Gunpowder + Arrow
                    ShapelessRecipeJsonBuilder
                            .create(itemLookup, RecipeCategory.COMBAT, FireArrow.EXPLOSIVE_ARROW)
                            .input(Items.GUNPOWDER)
                            .input(Items.ARROW)
                            .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER))
                            .criterion(hasItem(Items.ARROW), conditionsFromItem(Items.ARROW))
                            .offerTo(exporter);

                    // Smallfireball Arrow: Fire Charge + Arrow
                    ShapelessRecipeJsonBuilder
                            .create(itemLookup, RecipeCategory.COMBAT, FireArrow.SMALL_FIREBALL_ARROW)
                            .input(Items.FIRE_CHARGE)
                            .input(Items.ARROW)
                            .criterion(hasItem(Items.FIRE_CHARGE), conditionsFromItem(Items.FIRE_CHARGE))
                            .criterion(hasItem(Items.ARROW), conditionsFromItem(Items.ARROW))
                            .offerTo(exporter);

                    // Fireball Arrow: Fire Charge + Gunpowder + Arrow
                    // ShapelessRecipeJsonBuilder
                    // .create(itemLookup, RecipeCategory.COMBAT, FireArrow.FIREBALL_ARROW)
                    // .input(Items.FIRE_CHARGE)
                    // .input(Items.GUNPOWDER)
                    // .input(Items.ARROW)
                    // .criterion(hasItem(Items.FIRE_CHARGE), conditionsFromItem(Items.FIRE_CHARGE))
                    // .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER))
                    // .criterion(hasItem(Items.ARROW), conditionsFromItem(Items.ARROW))
                    // .offerTo(exporter);

                    ShapedRecipeJsonBuilder
                            .create(itemLookup, RecipeCategory.COMBAT, FireArrow.FIREBALL_ARROW)
                            .pattern("AF ") // 第一行：火焰弹
                            .pattern(" P ") // 第二行：火药
                            .pattern("   ") // 第三行：箭
                            .input('F', Items.FIRE_CHARGE) // 定义 F 代表火焰弹
                            .input('P', Items.GUNPOWDER) // 定义 P 代表火药
                            .input('A', Items.ARROW) // 定义 A 代表箭
                            .criterion(hasItem(Items.FIRE_CHARGE), conditionsFromItem(Items.FIRE_CHARGE))
                            .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER))
                            .criterion(hasItem(Items.ARROW), conditionsFromItem(Items.ARROW))
                            .offerTo(exporter);
                }
            };

        }

        @Override
        public String getName() {
            return "Fire Arrow Recipes";
        }
    }

    // ========== [!code ++] 标签生成器 (新增的代码) ==========
    static class FireArrowItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public FireArrowItemTagProvider(FabricDataOutput output,
                CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            // 核心代码：将你的自定义箭添加到 minecraft:arrows 标签
            valueLookupBuilder(ItemTags.ARROWS)
                    .add(FireArrow.EXPLOSIVE_ARROW)
                    .add(FireArrow.SMALL_FIREBALL_ARROW)
                    .add(FireArrow.FIREBALL_ARROW);
        }

        @Override
        public String getName() {
            return "Fire Arrow Item Tags";
        }
    }
}
