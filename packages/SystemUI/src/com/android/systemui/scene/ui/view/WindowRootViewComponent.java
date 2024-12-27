package com.android.systemui.scene.ui.view;

public interface WindowRootViewComponent {

    public interface Factory {
        WindowRootViewComponent create();
    }

    WindowRootView getWindowRootView();
}
