package com.android.systemui.accessibility;

import android.graphics.Rect;
import com.android.systemui.accessibility.WindowMagnificationSettings;
import java.util.Collections;

public final /* synthetic */ class WindowMagnificationSettings$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowMagnificationSettings$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WindowMagnificationSettings windowMagnificationSettings = (WindowMagnificationSettings) obj;
                Rect draggableWindowBounds$1 = windowMagnificationSettings.getDraggableWindowBounds$1();
                if (!windowMagnificationSettings.mDraggableWindowBounds.equals(draggableWindowBounds$1)) {
                    windowMagnificationSettings.mDraggableWindowBounds.set(draggableWindowBounds$1);
                    break;
                }
                break;
            case 1:
                ((WindowMagnificationSettings) obj).mMagnifierSizeTv.performAccessibilityAction(64, null);
                break;
            case 2:
                WindowMagnificationSettings windowMagnificationSettings2 = (WindowMagnificationSettings) obj;
                windowMagnificationSettings2.mSettingView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, windowMagnificationSettings2.mSettingView.getWidth(), windowMagnificationSettings2.mSettingView.getHeight())));
                break;
            default:
                ((WindowMagnificationSettings.AnonymousClass1) obj).this$0.updateUIControlsIfNeeded();
                break;
        }
    }
}
