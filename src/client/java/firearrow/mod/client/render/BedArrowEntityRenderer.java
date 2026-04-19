/*
 * Author: DesenbergChina(Yes_Muscle)
 * Date: 2026-03-06
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

public class BedArrowEntityRenderer extends ArrowRenderer<AbstractArrow, ArrowRenderState> {

    // Use custom bed arrow texture from the Fire Arrow mod resources.
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("fire-arrow", "textures/entity/bed_arrow.png");

    public BedArrowEntityRenderer(EntityRendererProvider.Context context) {
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
