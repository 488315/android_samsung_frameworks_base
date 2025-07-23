package com.android.wm.shell.common.pip;

import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.WindowManager;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface PipMenuController {
    static WindowManager.LayoutParams getPipMenuLayoutParams(Context context, int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2038, 537133072, -3);
        layoutParams.privateFlags |= VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        layoutParams.setTitle("PipMenuView");
        layoutParams.accessibilityTitle = context.getResources().getString(R.string.pip_menu_accessibility_title);
        return layoutParams;
    }

    void attach(SurfaceControl surfaceControl);

    void detach();

    boolean isMenuVisible();

    void movePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    void resizePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    void updateMenuBounds(Rect rect);

    default void setSplitMenuEnabled(boolean z) {
    }
}
