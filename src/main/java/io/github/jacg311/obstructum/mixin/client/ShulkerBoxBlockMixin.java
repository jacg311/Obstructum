package io.github.jacg311.obstructum.mixin.client;

import io.github.jacg311.obstructum.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
    @Shadow
    private static boolean canOpen(BlockState state, World world, BlockPos pos, ShulkerBoxBlockEntity entity) {
        return false;
    }

    @Inject(method = "onUse", at = @At("HEAD"))
    private void obstructum$sendMessageIfCantOpen(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient() || !(world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBE)) return;

        if (!canOpen(state, world, pos, shulkerBoxBE)) {
            Util.sendOverLayMessage("obstructum.shulker_box");
        }
    }
}
