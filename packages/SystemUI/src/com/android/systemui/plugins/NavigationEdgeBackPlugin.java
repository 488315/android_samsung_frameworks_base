package com.android.systemui.plugins;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

@ProvidesInterface(action = NavigationEdgeBackPlugin.ACTION, version = 1)
public interface NavigationEdgeBackPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NAVIGATION_EDGE_BACK_ACTION";
    public static final int VERSION = 1;

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
