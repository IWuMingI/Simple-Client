package cn.wuming.simple.event.events;

import cn.wuming.simple.event.Event;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
public class MotionUpdateEvent extends Event {

    private State state;

    public MotionUpdateEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public enum State {
        PRE, POST;
    }
}