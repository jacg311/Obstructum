package io.github.jacg311.obstructum.mixin.client;

import io.github.jacg311.obstructum.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CakeBlock;tryEat(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/util/ActionResult;", ordinal = 0))
    private void obstructum$sendMessageWhenCantEat(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient()) return;

        if (!player.canConsume(false) && !player.isCreative()) {
            Util.sendOverLayMessage("obstructum.cake");
        }
    }
}
