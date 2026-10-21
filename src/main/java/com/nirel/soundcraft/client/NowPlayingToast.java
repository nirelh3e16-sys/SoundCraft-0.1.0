package com.nirel.soundcraft.client;

import com.nirel.soundcraft.client.data.Track;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * Lightweight in-game now-playing card. It uses the same extraction renderer as
 * SoundCraft's screen, which is the public GUI API available to Minecraft 26.2.
 */
public final class NowPlayingToast {
    private static long visibleUntil;
    private static String text = "";

    private NowPlayingToast() {}

    public static void show(Minecraft ignored, Track track) {
        String title = safe(track == null ? null : track.title(), "Unknown track");
        String artist = safe(track == null ? null : track.author(), "Unknown artist");
        text = title + " — " + artist;
        visibleUntil = System.nanoTime() / 1_000_000L + 5_000L;
    }

    public static void render(GuiGraphicsExtractor graphics, Font font, int screenWidth) {
        if (System.nanoTime() / 1_000_000L >= visibleUntil) return;
        int width = Math.min(220, screenWidth - 16);
        int x = screenWidth - width - 8;
        graphics.fill(x, 8, x + width, 40, 0xEE202020);
        graphics.fill(x, 8, x + width, 9, 0xFF7A7A7A);
        graphics.text(font, "Сейчас играет", x + 8, 14, 0xFFFFFF55, true);
        graphics.text(font, text, x + 8, 26, 0xFFFFFFFF, false);
    }

    private static String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
