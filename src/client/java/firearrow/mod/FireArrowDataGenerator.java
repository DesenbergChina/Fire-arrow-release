/*
 * @Author: Desenberg
 * @Date: 2026-02-03 15:49:56
 * LastEditors: DesenbergChina(Yes_Muscle)
 * @Description: 
 * Email: desenberg@163.com
 * Copyright (c) 2026 by Desenberg, All Rights Reserved.
 */

package firearrow.mod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

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
        public FireArrowRecipeProvider(FabricPackOutput output,
                CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries,
                RecipeOutput output) {
            return new RecipeProvider(registries, output) {
                @Override
                public void buildRecipes() {
                    // Explosive Arrow: Gunpowder + Arrow
                    shapeless(RecipeCategory.COMBAT, FireArrow.EXPLOSIVE_ARROW)
                            .requires(Items.GUNPOWDER)
                            .requires(Items.ARROW)
                            .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                            .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                            .save(output);

                    // Smallfireball Arrow: Fire Charge + Arrow
                    shapeless(RecipeCategory.COMBAT, FireArrow.SMALL_FIREBALL_ARROW)
                            .requires(Items.FIRE_CHARGE)
                            .requires(Items.ARROW)
                            .unlockedBy(getHasName(Items.FIRE_CHARGE), has(Items.FIRE_CHARGE))
                            .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                            .save(output);

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

                    shaped(RecipeCategory.COMBAT, FireArrow.FIREBALL_ARROW)
                            .pattern("AF ") // 第一行：火焰弹
                            .pattern(" P ") // 第二行：火药
                            .pattern("   ") // 第三行：箭
                            .define('F', Items.FIRE_CHARGE) // 定义 F 代表火焰弹
                            .define('P', Items.GUNPOWDER) // 定义 P 代表火药
                            .define('A', Items.ARROW) // 定义 A 代表箭
                            .unlockedBy(getHasName(Items.FIRE_CHARGE), has(Items.FIRE_CHARGE))
                            .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                            .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                            .save(output);

                    // Bed Arrow: Bed + Arrow
                    shapeless(RecipeCategory.COMBAT, FireArrow.BED_ARROW)
                            .requires(ItemTags.BEDS)
                            .requires(Items.ARROW)
                            .unlockedBy("has_bed", has(ItemTags.BEDS))
                            .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                            .save(output);
                }
            };

        }

        @Override
        public String getName() {
            return "Fire Arrow Recipes";
        }
    }

    // ========== [!code ++] 标签生成器 (新增的代码) ==========
    static class FireArrowItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
        public FireArrowItemTagProvider(FabricPackOutput output,
                CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            // 核心代码：将你的自定义箭添加到 minecraft:arrows 标签
            valueLookupBuilder(ItemTags.ARROWS)
                    .add(FireArrow.EXPLOSIVE_ARROW)
                    .add(FireArrow.SMALL_FIREBALL_ARROW)
                    .add(FireArrow.FIREBALL_ARROW)
                    .add(FireArrow.BED_ARROW);
        }

        @Override
        public String getName() {
            return "Fire Arrow Item Tags";
        }
    }
}
