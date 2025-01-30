package com.android.systemui.plugins;

import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.statusbar.DozeParameters;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = OverlayPlugin.ACTION, version = 4)
/* loaded from: classes2.dex */
public interface OverlayPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_OVERLAY";
    public static final int VERSION = 4;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
