package com.android.systemui.plugins;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = NavigationEdgeBackPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface NavigationEdgeBackPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NAVIGATION_EDGE_BACK_ACTION";
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface BackCallback {
        void cancelBack();

        void setTriggerBack(boolean z);

        void triggerBack();
    }

    void dump(PrintWriter printWriter);

    void onMotionEvent(MotionEvent motionEvent);

    void setBackCallback(BackCallback backCallback);

    void setDisplaySize(Point point);

    void setInsets(int i, int i2);

    void setIsLeftPanel(boolean z);

    void setLayoutParams(WindowManager.LayoutParams layoutParams);

    default void updateActiveIndicatorSpringParams(float f, float f2) {
    }

    default void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
    }

    default void updateBackPanelColor(int i, int i2, int i3, int i4) {
    }
}
