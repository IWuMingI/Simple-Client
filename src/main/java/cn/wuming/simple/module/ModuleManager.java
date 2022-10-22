package cn.wuming.simple.module;

import cn.wuming.simple.event.EventListener;
import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.module.modules.combat.KillAura;
import cn.wuming.simple.module.modules.movement.Speed;
import cn.wuming.simple.module.modules.movement.Sprint;
import cn.wuming.simple.event.events.KeyEvent;
import cn.wuming.simple.module.modules.render.Chams;
import cn.wuming.simple.module.modules.render.ClickGUI;
import cn.wuming.simple.module.modules.render.FullBright;
import cn.wuming.simple.module.modules.render.HUD;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public class ModuleManager implements EventListener {
    private static final List<Module> modules = new ArrayList<>();

    public void registerModules() {
        registerModule(new KillAura());
        registerModule(new Speed());
        registerModule(new ClickGUI());
        registerModule(new Chams());
        registerModule(new Sprint());
        registerModule(new FullBright());

        registerModule(new HUD());
    }

    public void registerModule(final Module module) {
        modules.add(module);
    }

    public static Module getModule(final Class<? extends Module> targetModule) {
        synchronized(modules) {
            for(final Module currentModule : modules)
                if(currentModule.getClass() == targetModule)
                    return currentModule;
        }

        return null;
    }

    public static Module getModule(final String targetModule) {
        synchronized(modules) {
            for(final Module currentModule : modules)
                if(currentModule.getModuleName().equalsIgnoreCase(targetModule))
                    return currentModule;
        }

        return null;
    }

    public static List<Module> getModules() {
        return modules;
    }

    @EventTarget
    public void onKey(KeyEvent event) {
        synchronized(modules) {
            modules.stream().filter(module -> module.getKeyBind() == event.getKey()).forEach(module -> module.setState(!module.getState()));
        }
    }
}
