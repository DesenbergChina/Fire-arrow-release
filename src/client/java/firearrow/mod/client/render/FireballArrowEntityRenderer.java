/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-02-01 13:15:24
 * LastEditors: DesenbergChina(Yes_Muscle)
 * Description: 
 * Email:desenberg@163.com
 * This file is part of Fire Arrow,Licensed under the MIT License (see root/LICENSE file) 
 */
package firearrow.mod.client.render;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.util.Identifier;

// 继承 ArrowEntityRenderer 以复用标准箭矢模型
public class FireballArrowEntityRenderer extends ArrowEntityRenderer {

    // 定义贴图路径：
    // 确保你的贴图文件放在:
    // src/main/resources/assets/firearrow/textures/entity/fireball_arrow.png
    public static final Identifier TEXTURE = Identifier.of("fire-arrow", "textures/entity/fireball_arrow.png");

    public FireballArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    /**
     * 重写获取贴图的方法。
     * 注意：在 Minecraft 1.21.4+ 中，参数必须是 ArrowEntityRenderState，而不是 Entity。
     */
    @Override
    public Identifier getTexture(ArrowEntityRenderState state) {
        return TEXTURE;
    }
}
