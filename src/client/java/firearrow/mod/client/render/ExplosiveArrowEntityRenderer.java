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
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.util.Identifier;

// 继承 ArrowEntityRenderer 以复用箭矢的模型和渲染逻辑
public class ExplosiveArrowEntityRenderer extends ArrowEntityRenderer {

    // 自定义贴图路径 (需在 resources/assets/firearrow/textures/entity/ 下创建 png)
    public static final Identifier TEXTURE = Identifier.of("fire-arrow", "textures/entity/explosive_arrow.png");

    public ExplosiveArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    // [!code focus] 方法签名修改：参数由 Entity 变为 State
    @Override
    public Identifier getTexture(ArrowEntityRenderState state) {
        return TEXTURE;
    }
}
