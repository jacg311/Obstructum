package io.github.jacg311.obstructum.mixin.client;

import io.github.jacg311.obstructum.Util;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
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

@Mixin(AbstractSignBlock.class)
public class AbstractSignBlockMixin {
    @Inject(method = "onUse", at = @At("HEAD"))
    private void obstructum$sendMessageWhenCantEdit(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient() || !player.canModifyBlocks()) {
            return;
        }

        if (!(world.getBlockEntity(pos) instanceof SignBlockEntity signBlockEntity)) {
            return;
        }

        if (signBlockEntity.isWaxed()) {
            Util.sendOverLayMessage("obstructum.waxed_sign");
        }
    }
}
