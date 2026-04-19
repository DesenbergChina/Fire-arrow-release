/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-02-01 13:15:24
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.jetbrains.annotations.NotNull;

// 继承 ArrowEntityRenderer 以复用标准箭矢模型
public class FireballArrowEntityRenderer extends ArrowRenderer<AbstractArrow, ArrowRenderState> {

    // 定义贴图路径：
    // 确保你的贴图文件放在:
    // src/main/resources/assets/firearrow/textures/entity/fireball_arrow.png
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("fire-arrow",
            "textures/entity/fireball_arrow.png");

    public FireballArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * 重写获取贴图的方法。
     * 注意：在 Minecraft 1.21.4+ 中，参数必须是 ArrowEntityRenderState，而不是 Entity。
     */
    @Override
    protected @NotNull Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
