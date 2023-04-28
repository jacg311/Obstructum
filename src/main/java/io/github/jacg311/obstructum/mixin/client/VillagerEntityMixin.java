package io.github.jacg311.obstructum.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void obstructum$sendMessageWhenCantTrade(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.getWorld().isClient() && (this.isBaby() || this.getOffers().isEmpty())) {
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.translatable("obstructum.villager_trade"), false);
        }
    }
}
