package cn.wuming.simple.module.modules.render;

import cn.wuming.simple.Simple;
import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.KeyEvent;
import cn.wuming.simple.event.events.Render2DEvent;
import cn.wuming.simple.gui.tabgui.SubTab;
import cn.wuming.simple.gui.tabgui.Tab;
import cn.wuming.simple.gui.tabgui.TabGui;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;
import cn.wuming.simple.module.ModuleManager;
import cn.wuming.simple.notifications.NotificationManager;
import cn.wuming.simple.utils.GLUtil;
import cn.wuming.simple.valuesystem.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@ModuleInfo(moduleName = "HUD", moduleDescription = "meh", moduleCateogry = ModuleCategory.RENDER, isHidden = true)
public class HUD extends Module {

    private TabGui<Module> tabGui = new TabGui<>();
    private List<Integer> fps = new ArrayList<>();

    private NumberValue<Integer> fpsStatisticLength = new NumberValue<>("FPSStatisticLength", 250, 10, 500);

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public HUD() {
        setState(true);
        //HashMap<ModuleCategory, java.util.List<Module>> moduleCategoryMap = new HashMap<>();
        HashMap<String, java.util.List<Module>> moduleCategoryMap = new HashMap<>();

        for (Module module : Simple.INSTANCE.moduleManager.getModules()) {
            if (!moduleCategoryMap.containsKey(module.getModuleCategory())) {
                moduleCategoryMap.put(module.getModuleCategory().getName(), new ArrayList<>());
            }

            moduleCategoryMap.get(module.getModuleCategory().getName()).add(module);
        }

        moduleCategoryMap.entrySet().stream().sorted(Comparator.comparingInt(cat -> cat.getKey().toString().hashCode())).forEach(cat -> {
            Tab<Module> tab = new Tab<>(cat.getKey());

            for (Module module : cat.getValue()) {
                tab.addSubTab(new SubTab<>(module.getModuleName(), subTab -> subTab.getObject().setState(!subTab.getObject().getState()), module));
            }

            tabGui.addTab(tab);
        });

    }

    @EventTarget
    public void onRender2D(final Render2DEvent event) {
        if (!getState())
            return;

        fps.add(Minecraft.getDebugFPS());
        while (fps.size() > fpsStatisticLength.getObject()) {
            fps.remove(0);
        }

        FontRenderer fontRenderer = mc.fontRendererObj;

        ScaledResolution res = new ScaledResolution(mc);

        int blackBarHeight = fontRenderer.FONT_HEIGHT * 2 + 4;

        Gui.drawRect(0, res.getScaledHeight() - blackBarHeight, res.getScaledWidth(), res.getScaledHeight(), GLUtil.toRGBA(new Color(0, 0, 0, 150)));

        GL11.glScaled(2.0, 2.0, 2.0);
        int i = fontRenderer.drawString(Simple.CLIENT_NAME, 2, 2, rainbow(0), true);
        int initialSize = fontRenderer.drawString(Simple.CLIENT_INITIALS, 1, res.getScaledHeight() / 2.0f - fontRenderer.FONT_HEIGHT, rainbow(0), true);
        GL11.glScaled(0.5, 0.5, 0.5);

        fontRenderer.drawString(Simple.CLIENT_VERSION_DEV, i * 2, fontRenderer.FONT_HEIGHT * 2 - 7, rainbow(100), true);
        fontRenderer.drawString("By " + Simple.CLIENT_AUTHOR, 4, fontRenderer.FONT_HEIGHT * 2 + 2, rainbow(200), true);

        double currSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

        int fpsWidth = fontRenderer.drawString("FPS: " + Minecraft.getDebugFPS(), initialSize * 2 + 2, res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, true);
        fpsWidth = Math.max(fpsWidth, fontRenderer.drawString(String.format("BPS: %.2f", currSpeed), initialSize * 2 + 2, res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, true));


        LocalDateTime now = LocalDateTime.now();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);


        fontRenderer.drawString(date, res.getScaledWidth() - fontRenderer.getStringWidth(date), res.getScaledHeight() - fontRenderer.FONT_HEIGHT - 2, -1, true);
        fontRenderer.drawString(time, res.getScaledWidth() - fontRenderer.getStringWidth(time), res.getScaledHeight() - fontRenderer.FONT_HEIGHT * 2 - 2, -1, true);


        AtomicInteger offset = new AtomicInteger(3);
        AtomicInteger index = new AtomicInteger();

        Simple.INSTANCE.moduleManager.getModules().stream().filter(mod -> mod.getState() && !mod.isHidden()).sorted(Comparator.comparingInt(mod -> -fontRenderer.getStringWidth(mod.getModuleName()))).forEach(mod -> {
            fontRenderer.drawString(mod.getModuleName(), res.getScaledWidth() - fontRenderer.getStringWidth(mod.getModuleName()) - 3, offset.get(), rainbow(index.get() * 100), true);

            offset.addAndGet(fontRenderer.FONT_HEIGHT + 2);
            index.getAndIncrement();
        });

        NotificationManager.render();
        tabGui.render(5, (2 + fontRenderer.FONT_HEIGHT) * 3);


        int max = fps.stream().max(Integer::compareTo).orElse(1);
        double transform = blackBarHeight / 2.0 / (double) max;

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glLineWidth(1.0f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glBegin(GL11.GL_LINE_STRIP);

        fpsWidth += 3;

        double v = ((res.getScaledWidth() / 2.0 - 100) - fpsWidth) / (double) fps.size();

        for (int j = 0; j < fps.size(); j++) {
            int currFPS = fps.get(j);

            GL11.glVertex2d(fpsWidth + j * v, res.getScaledHeight() - transform * currFPS);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360f), 0.8f, 0.7f).getRGB();
    }

    @EventTarget
    public void onKey(KeyEvent event) {
        tabGui.handleKey(event.getKey());
    }
}