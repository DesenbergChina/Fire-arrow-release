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
import firearrow.mod.item.ExplosiveArrowItem;
import firearrow.mod.item.SmallfireballArrowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

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
	public static final RegistryKey<Item> EXPLOSIVE_ARROW_KEY = RegistryKey.of(RegistryKeys.ITEM,
			Identifier.of(MOD_ID, "explosive_arrow"));
	public static final RegistryKey<Item> SMALL_FIREBALL_ARROW_KEY = RegistryKey.of(RegistryKeys.ITEM,
			Identifier.of(MOD_ID, "small_fireball_arrow"));

	// Entities Keys
	public static final RegistryKey<EntityType<?>> EXPLOSIVE_ARROW_ENTITY_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE,
			Identifier.of(MOD_ID, "explosive_arrow"));
	public static final RegistryKey<EntityType<?>> SMALL_FIREBALL_ARROW_ENTITY_KEY = RegistryKey.of(
			RegistryKeys.ENTITY_TYPE,
			Identifier.of(MOD_ID, "small_fireball_arrow"));

	// Items: 使用 .registryKey(KEY) 将 ID 绑定到 Item.Settings
	public static final Item EXPLOSIVE_ARROW = registerItem(EXPLOSIVE_ARROW_KEY,
			new ExplosiveArrowItem(new Item.Settings().registryKey(EXPLOSIVE_ARROW_KEY)));
	public static final Item SMALL_FIREBALL_ARROW = registerItem(SMALL_FIREBALL_ARROW_KEY,
			new SmallfireballArrowItem(new Item.Settings().registryKey(SMALL_FIREBALL_ARROW_KEY)));

	// Entities: EntityType.Builder.build(KEY) 必须传入 Key
	public static final EntityType<ExplosiveArrowEntity> EXPLOSIVE_ARROW_ENTITY = registerEntity(
			EXPLOSIVE_ARROW_ENTITY_KEY, EntityType.Builder
					.<ExplosiveArrowEntity>create(ExplosiveArrowEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
					.build(EXPLOSIVE_ARROW_ENTITY_KEY));

	public static final EntityType<SmallfireballArrowEntity> SMALL_FIREBALL_ARROW_ENTITY = registerEntity(
			SMALL_FIREBALL_ARROW_ENTITY_KEY, EntityType.Builder
					.<SmallfireballArrowEntity>create(SmallfireballArrowEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
					.build(SMALL_FIREBALL_ARROW_ENTITY_KEY));

	// 辅助方法：注册物品
	private static Item registerItem(RegistryKey<Item> key, Item item) {
		return Registry.register(Registries.ITEM, key, item);
	}

	// 辅助方法：注册实体
	// 注意这里泛型的处理，EntityType<?> key 和 EntityType<T> type
	@SuppressWarnings("unchecked")
	private static <T extends EntityType<?>> T registerEntity(RegistryKey<EntityType<?>> key, T type) {
		return (T) Registry.register(Registries.ENTITY_TYPE, key, type);
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
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(EXPLOSIVE_ARROW);
			entries.add(SMALL_FIREBALL_ARROW);
		});

		LOGGER.info("Fire Arrow mod initialized!(“火箭”模组初始化完成！)");
	}
}