package net.jakymc.jakthing.mixin;

import net.jakymc.jakthing.effect.JakEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public class SMGSnowball {
    @Inject(method = "onHitEntity", at = @At("RETURN"))
    private void onHitEntity(EntityHitResult hit, CallbackInfo cbi) {
        final LivingEntity hitEntity = (LivingEntity) hit.getEntity();
        final Snowball self = (Snowball)(Object)this;
        if(self.getPersistentData().getBoolean("smg")){
            if (hitEntity instanceof Player player) {
                player.hurt(player.level().damageSources().thrown(self, self.getOwner()), -1.0f);
            }
            if(hitEntity.hasEffect(JakEffect.FLAME.get())){
                hitEntity.hurt(self.damageSources().thrown(self, self.getOwner()), 1.0f);
            }
            hitEntity.invulnerableTime = 0;
        }
    }
}