package com.android.systemui.plugins;

import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.statusbar.DozeParameters;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = OverlayPlugin.ACTION, version = 4)
/* loaded from: classes2.dex */
public interface OverlayPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_OVERLAY";
    public static final int VERSION = 4;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
