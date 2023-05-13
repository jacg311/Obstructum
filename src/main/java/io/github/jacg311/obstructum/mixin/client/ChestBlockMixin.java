package io.github.jacg311.obstructum.mixin.client;

import io.github.jacg311.obstructum.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin {
    @Shadow @Final public static EnumProperty<ChestType> CHEST_TYPE;
    @Shadow @Final public static DirectionProperty FACING;

    @Shadow
    public static boolean isChestBlocked(WorldAccess world, BlockPos pos) {
        return false;
    }

    @Inject(method = "onUse", at = @At("HEAD"))
    private void obstructum$sendMessageIfCantOpen(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient() || !(state.getBlock() instanceof ChestBlock)) return;

        ChestType type = state.get(CHEST_TYPE);
        Direction direction = state.get(FACING);
        boolean locked = isChestBlocked(world, pos);

        if (type == ChestType.LEFT) {
            BlockPos otherPos = pos.offset(direction.rotateClockwise(Direction.Axis.Y));
            locked |= isChestBlocked(world, otherPos);
        }

        if (type == ChestType.RIGHT) {
            BlockPos otherPos = pos.offset(direction.rotateCounterclockwise(Direction.Axis.Y));
            locked |= isChestBlocked(world, otherPos);
        }

        if (locked) {
            Util.sendOverLayMessage("obstructum.chest");
        }
    }
}
