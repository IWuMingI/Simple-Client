package cn.wuming.simple.event;

import java.lang.reflect.Method;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public class EventMethod {

    private EventListener eventListener;
    private Method method;

    public EventMethod(EventListener eventListener, Method method) {
        this.eventListener = eventListener;
        this.method = method;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public Method getMethod() {
        return method;
    }
}