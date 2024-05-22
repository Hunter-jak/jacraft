package net.jakymc.jakthing.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jakymc.jakthing.client.model.LivingPotatoModel;
import net.jakymc.jakthing.entity.LivingPotato;
import net.jakymc.jakthing.JakThing;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderLivingPotato extends MobRenderer<LivingPotato, LivingPotatoModel<LivingPotato>> {
    public RenderLivingPotato(EntityRendererProvider.Context context) {
        super(context, new LivingPotatoModel(context.bakeLayer(LivingPotatoModel.LIVINGPOTATO_LAYER)),0.25F);
    }
    @Override
    public void render(LivingPotato potato, float p_115977_, float p_115978_, PoseStack p_115979_, MultiBufferSource p_115980_, int p_115981_) {
        this.shadowRadius = 0.25F + (0.0175F * (float)potato.getPotatoSize());
        super.render(potato, p_115977_, p_115978_, p_115979_, p_115980_, p_115981_);
    }

    @Override
    protected void scale(LivingPotato potato, PoseStack pose, float size) {
        int i = potato.getPotatoSize();
        float f = 1.0F + 0.1F * (float)i;
        pose.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingPotato p_114482_) {
        return new ResourceLocation(JakThing.MODID, "textures/entity/living_potato.png");
    }
}
