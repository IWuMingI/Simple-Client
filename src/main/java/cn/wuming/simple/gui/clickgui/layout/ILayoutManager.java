package cn.wuming.simple.gui.clickgui.layout;

import cn.wuming.simple.gui.clickgui.AbstractComponent;

import java.util.List;

public interface ILayoutManager {
    int[] getOptimalDiemension(List<AbstractComponent> components, int maxWidth);

    Layout buildLayout(List<AbstractComponent> components, int width, int height);
}
