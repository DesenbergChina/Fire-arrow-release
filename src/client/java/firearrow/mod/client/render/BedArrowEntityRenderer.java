/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-03-06
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

public class BedArrowEntityRenderer extends ArrowEntityRenderer {

    // Use custom bed arrow texture from the Fire Arrow mod resources.
    public static final Identifier TEXTURE = Identifier.of("fire-arrow", "textures/entity/bed_arrow.png");

    public BedArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ArrowEntityRenderState state) {
        return TEXTURE;
    }
}
