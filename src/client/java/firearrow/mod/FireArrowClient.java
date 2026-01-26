/*
 *  Author: DesenbergChina(Yes_Muscle)
 *  Date: 2026-01-23 19:55:03
 *  LastEditors: DesenbergChina(Yes_Muscle)
 *  Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod;

import firearrow.mod.client.render.ExplosiveArrowEntityRenderer;
import firearrow.mod.client.render.SmallfireballArrowEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
//import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//import net.minecraft.client.render.entity.ArrowEntityRenderer; 
import net.minecraft.client.render.entity.EntityRendererFactories;

public class FireArrowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 注册实体渲染器
        // 使用 ArrowEntityRenderer 代替 TippableArrowEntityRenderer
        EntityRendererFactories.register(FireArrow.EXPLOSIVE_ARROW_ENTITY, ExplosiveArrowEntityRenderer::new);
        EntityRendererFactories.register(FireArrow.SMALL_FIREBALL_ARROW_ENTITY, SmallfireballArrowEntityRenderer::new);

        // 注意：
        // 1. ArrowEntityRenderer 适用于继承自 ArrowEntity 的实体。
        // 2. 如果你的自定义箭实体 (ExplosiveArrowEntity) 没有继承 ArrowEntity，
        // 而是继承的 PersistentProjectileEntity，你可能需要自定义 Renderer 或暂时使用
        // ProjectileEntityRenderer。
    }
}
