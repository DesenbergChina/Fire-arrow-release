/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-01-22 21:45:18
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.client.render;

//import firearrow.mod.entity.ExplosiveArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.jetbrains.annotations.NotNull;

// 继承 ArrowEntityRenderer 以复用箭矢的模型和渲染逻辑
public class ExplosiveArrowEntityRenderer extends ArrowRenderer<AbstractArrow, ArrowRenderState> {

    // 自定义贴图路径 (需在 resources/assets/firearrow/textures/entity/ 下创建 png)
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("fire-arrow",
            "textures/entity/explosive_arrow.png");

    public ExplosiveArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected @NotNull Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
