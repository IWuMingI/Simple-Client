package cn.wuming.simple.module;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String moduleName();
    String moduleDescription();
    ModuleCategory moduleCateogry();
    int defaultKey() default Keyboard.KEY_NONE;
    boolean canEnable() default true;
    boolean isHidden() default false;

}
