/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-24 15:32:46
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */

package firearrow.mod;

import firearrow.mod.entity.ExplosiveArrowEntity;
import firearrow.mod.entity.SmallfireballArrowEntity;
import firearrow.mod.entity.FireballArrowEntity;
import firearrow.mod.entity.BedArrowEntity;
import firearrow.mod.item.ExplosiveArrowItem;
import firearrow.mod.item.SmallfireballArrowItem;
import firearrow.mod.item.FireballArrowItem;
import firearrow.mod.item.BedArrowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireArrow implements ModInitializer {
	public static final String MOD_ID = "fire-arrow";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// =============================================================
	// 1. 定义 Registry Keys (必须先定义 Key)
	// =============================================================

	// Items Keys
	public static final ResourceKey<Item> EXPLOSIVE_ARROW_KEY = ResourceKey.create(Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "explosive_arrow"));
	public static final ResourceKey<Item> SMALL_FIREBALL_ARROW_KEY = ResourceKey.create(Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "small_fireball_arrow"));
	public static final ResourceKey<Item> FIREBALL_ARROW_KEY = ResourceKey.create(Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "fireball_arrow"));
	public static final ResourceKey<Item> BED_ARROW_KEY = ResourceKey.create(Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "bed_arrow"));

	// Entities Keys
	public static final ResourceKey<EntityType<?>> EXPLOSIVE_ARROW_ENTITY_KEY = ResourceKey.create(
			Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "explosive_arrow"));
	public static final ResourceKey<EntityType<?>> SMALL_FIREBALL_ARROW_ENTITY_KEY = ResourceKey.create(
			Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "small_fireball_arrow"));
	public static final ResourceKey<EntityType<?>> FIREBALL_ARROW_ENTITY_KEY = ResourceKey.create(
			Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "fireball_arrow"));
	public static final ResourceKey<EntityType<?>> BED_ARROW_ENTITY_KEY = ResourceKey.create(
			Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "bed_arrow"));

	// Items: 使用 .registryKey(KEY) 将 ID 绑定到 Item.Settings
	public static final Item EXPLOSIVE_ARROW = registerItem(EXPLOSIVE_ARROW_KEY,
			new ExplosiveArrowItem(new Item.Properties().setId(EXPLOSIVE_ARROW_KEY)));
	public static final Item SMALL_FIREBALL_ARROW = registerItem(SMALL_FIREBALL_ARROW_KEY,
			new SmallfireballArrowItem(new Item.Properties().setId(SMALL_FIREBALL_ARROW_KEY)));
	public static final Item FIREBALL_ARROW = registerItem(FIREBALL_ARROW_KEY,
			new FireballArrowItem(new Item.Properties().setId(FIREBALL_ARROW_KEY)));
	public static final Item BED_ARROW = registerItem(BED_ARROW_KEY,
			new BedArrowItem(new Item.Properties().setId(BED_ARROW_KEY)));

	// Entities: EntityType.Builder.build(KEY) 必须传入 Key
	public static final EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW_ENTITY = registerEntity(
			EXPLOSIVE_ARROW_ENTITY_KEY, EntityType.Builder
					.<ExplosiveArrowEntity>of(ExplosiveArrowEntity::new, MobCategory.MISC)
					.sized(0.5f, 0.5f)
					.build(EXPLOSIVE_ARROW_ENTITY_KEY));

	public static final EntityType<SmallfireballArrowEntity> SMALL_FIREBALL_ARROW_ENTITY = registerEntity(
			SMALL_FIREBALL_ARROW_ENTITY_KEY, EntityType.Builder
					.<SmallfireballArrowEntity>of(SmallfireballArrowEntity::new, MobCategory.MISC)
					.sized(0.5f, 0.5f)
					.build(SMALL_FIREBALL_ARROW_ENTITY_KEY));

	public static final EntityType<FireballArrowEntity> FIREBALL_ARROW_ENTITY = registerEntity(
			FIREBALL_ARROW_ENTITY_KEY, EntityType.Builder
					.<FireballArrowEntity>of(FireballArrowEntity::new, MobCategory.MISC)
					.sized(0.5f, 0.5f)
					.build(FIREBALL_ARROW_ENTITY_KEY));

	public static final EntityType<BedArrowEntity> BED_ARROW_ENTITY = registerEntity(
			BED_ARROW_ENTITY_KEY, EntityType.Builder
					.<BedArrowEntity>of(BedArrowEntity::new, MobCategory.MISC)
					.sized(0.5f, 0.5f)
					.build(BED_ARROW_ENTITY_KEY));

	// 辅助方法：注册物品
	private static Item registerItem(ResourceKey<Item> key, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	// 辅助方法：注册实体
	// 注意这里泛型的处理，EntityType<?> key 和 EntityType<T> type
	@SuppressWarnings("unchecked")
	private static <T extends EntityType<?>> T registerEntity(ResourceKey<EntityType<?>> key, T type) {
		return (T) Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		// Register items
		/*
		 * Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "explosive_arrow"),
		 * EXPLOSIVE_ARROW);
		 * Registry.register(Registries.ITEM, Identifier.of(MOD_ID,
		 * "small_fireball_arrow"), SMALL_FIREBALL_ARROW);
		 * 
		 * // Register entities
		 * Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID,
		 * "explosive_arrow"), EXPLOSIVE_ARROW_ENTITY);
		 * Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID,
		 * "small_fireball_arrow"), SMALL_FIREBALL_ARROW_ENTITY);
		 */
		// Add to item group
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
			entries.accept(EXPLOSIVE_ARROW);
			entries.accept(SMALL_FIREBALL_ARROW);
			entries.accept(FIREBALL_ARROW);
			entries.accept(BED_ARROW);
		});

		LOGGER.info("Fire Arrow mod initialized!(“火箭”模组初始化完成！)");
	}
}