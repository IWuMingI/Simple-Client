package cn.wuming.simple.module;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public enum ModuleCategory {

    PLAYER("Player"),
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    WORLD("World"),
    MISC("Misc"),
    EXPLOIT("Exploit"),
    RENDER("Render");

    private String name;

    ModuleCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}