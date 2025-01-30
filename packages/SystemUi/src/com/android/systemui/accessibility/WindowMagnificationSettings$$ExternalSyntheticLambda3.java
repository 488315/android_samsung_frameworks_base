package com.android.systemui.accessibility;

import android.graphics.Rect;
import android.view.WindowManager;
import com.android.systemui.accessibility.WindowMagnificationSettings;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnificationSettings$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowMagnificationSettings$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WindowMagnificationSettings windowMagnificationSettings = (WindowMagnificationSettings) this.f$0;
                Rect draggableWindowBounds = windowMagnificationSettings.getDraggableWindowBounds();
                if (!windowMagnificationSettings.mDraggableWindowBounds.equals(draggableWindowBounds)) {
                    windowMagnificationSettings.mDraggableWindowBounds.set(draggableWindowBounds);
                    break;
                }
                break;
            case 1:
                WindowMagnificationSettings windowMagnificationSettings2 = (WindowMagnificationSettings) this.f$0;
                WindowManager.LayoutParams layoutParams = windowMagnificationSettings2.mParams;
                layoutParams.x = (windowMagnificationSettings2.mDraggableWindowBounds.width() - windowMagnificationSettings2.mSettingView.getWidth()) / 2;
                if (windowMagnificationSettings2.mSettingView.getWindowToken() != null) {
                    windowMagnificationSettings2.mWindowManager.updateViewLayout(windowMagnificationSettings2.mSettingView, layoutParams);
                    break;
                }
                break;
            case 2:
                ((WindowMagnificationSettings) this.f$0).mMagnifierSizeTv.performAccessibilityAction(64, null);
                break;
            case 3:
                WindowMagnificationSettings windowMagnificationSettings3 = (WindowMagnificationSettings) this.f$0;
                windowMagnificationSettings3.mSettingView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, windowMagnificationSettings3.mSettingView.getWidth(), windowMagnificationSettings3.mSettingView.getHeight())));
                break;
            default:
                ((WindowMagnificationSettings.C10011) this.f$0).this$0.updateUIControlsIfNeeded();
                break;
        }
    }
}
