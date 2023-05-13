package io.github.jacg311.obstructum;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Util {
    public static void sendOverLayMessage(String translationKey) {
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.translatable(translationKey), false);
    }
}
