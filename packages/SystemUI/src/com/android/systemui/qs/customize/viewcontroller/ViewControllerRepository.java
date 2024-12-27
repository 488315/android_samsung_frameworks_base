package com.android.systemui.qs.customize.viewcontroller;

public final class ViewControllerRepository {
    public final ViewControllerType MAIN_TYPE = ViewControllerType.LayoutEdit;
    public ViewControllerType currentType;
    public ViewControllerType prevType;
    public final ViewControllerBase[] viewControllers;

    public ViewControllerRepository() {
        ViewControllerType viewControllerType = ViewControllerType.None;
        this.currentType = viewControllerType;
        this.prevType = viewControllerType;
        ViewControllerBase[] viewControllerBaseArr = new ViewControllerBase[4];
        for (int i = 0; i < 4; i++) {
            viewControllerBaseArr[i] = null;
        }
        this.viewControllers = viewControllerBaseArr;
    }
}
