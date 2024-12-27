package com.android.systemui.plugins;

import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.statusbar.DozeParameters;

@ProvidesInterface(action = OverlayPlugin.ACTION, version = 4)
public interface OverlayPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_OVERLAY";
    public static final int VERSION = 4;

    public interface Callback {
        void onHoldStatusBarOpenChange();
    }

    default boolean holdStatusBarOpen() {
        return false;
    }

    void setup(View view, View view2);

    default void setup(View view, View view2, Callback callback, DozeParameters dozeParameters) {
        setup(view, view2);
    }

    default void setCollapseDesired(boolean z) {
    }
}
