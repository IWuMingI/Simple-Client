package cn.wuming.simple.module;

import cn.wuming.simple.Simple;
import cn.wuming.simple.event.EventListener;
import cn.wuming.simple.notifications.Notification;
import cn.wuming.simple.notifications.NotificationType;
import cn.wuming.simple.valuesystem.Value;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public class Module implements EventListener {
    private final String moduleName = getClass().getAnnotation(ModuleInfo.class).moduleName();
    private final String moduleDescription = getClass().getAnnotation(ModuleInfo.class).moduleDescription();
    private final ModuleCategory moduleCategory = getClass().getAnnotation(ModuleInfo.class).moduleCateogry();
    private final boolean canEnable = getClass().getAnnotation(ModuleInfo.class).canEnable();
    private final boolean isHidden = getClass().getAnnotation(ModuleInfo.class).isHidden();

    private int keyBind = getClass().getAnnotation(ModuleInfo.class).defaultKey();

    private boolean state;

    protected final Minecraft mc = Minecraft.getMinecraft();

    public Module() {
        Simple.INSTANCE.eventManager.registerListener(this);
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public ModuleCategory getModuleCategory() {
        return moduleCategory;
    }

    public boolean canEnable() {
        return canEnable;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setState(boolean state) {
        if(state && canEnable)
            this.state = state;
        else
            this.state = false;

        if(state) {
            onEnable();
            Simple.INSTANCE.notificationManager.show(new Notification(NotificationType.INFO, getModuleName(), getModuleName() + " was onEnable.", 1));
        } else {
            onDisable();
            Simple.INSTANCE.notificationManager.show(new Notification(NotificationType.INFO, getModuleName(), getModuleName() + " was onDisable.", 1));
        }
    }

    public boolean getState() {
        return state;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public Value getValue(final String valueName) {
        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                System.out.println(field.getName());

                if(o instanceof Value) {
                    final Value value = (Value) o;

                    if(value.getName().equalsIgnoreCase(valueName))
                        return value;
                }
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<Value> getValues() {
        final List<Value> values = new ArrayList<>();

        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                if(o instanceof Value)
                    values.add((Value) o);
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return values;
    }
}
