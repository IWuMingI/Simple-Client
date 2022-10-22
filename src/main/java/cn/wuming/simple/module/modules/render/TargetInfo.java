package cn.wuming.simple.module.modules.render;

import cn.wuming.simple.event.EventTarget;
import cn.wuming.simple.event.events.Render2DEvent;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleCategory;
import cn.wuming.simple.module.ModuleInfo;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

/**
 * @author WuMing
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMing | All rights reserved.
 * @project SimpleClient
 */
@ModuleInfo(moduleName = "TargetInfo", moduleDescription = "Show Target Info", moduleCateogry = ModuleCategory.RENDER)
public class TargetInfo extends Module {
    @EventTarget
    public void Render2D(Render2DEvent event) {
        if (!getState())
            return;
        Entity hit = mc.objectMouseOver.entityHit;
        if (hit != null && hit instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) hit;
            GuiInventory.drawEntityOnScreen(100, 100, 30, 0 ,0, entityLivingBase);
            mc.fontRendererObj.drawString(entityLivingBase.getName(), 100, 100, new Color(0, 255, 255).getRGB());
            mc.fontRendererObj.drawString(String.format("%s/%s", entityLivingBase.getMaxHealth(), entityLivingBase.getHealth()), 100, 110, new Color(97, 255, 192, 128).getRGB());
        }
    }
}
