package com.android.server.wm;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.WindowManager;

import com.android.server.am.ActivityManagerService;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Task$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ Task$$ExternalSyntheticLambda10(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Rect rect = (Rect) this.f$0;
                boolean[] zArr = (boolean[]) this.f$1;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (!activityRecord.mIsExiting
                        && activityRecord.mClientVisible
                        && activityRecord.isVisibleRequested()) {
                    int size = activityRecord.mChildren.size();
                    WindowState windowState = null;
                    WindowState windowState2 = null;
                    for (int i = 0; i < size; i++) {
                        WindowState windowState3 = (WindowState) activityRecord.mChildren.get(i);
                        int i2 = windowState3.mAttrs.type;
                        if (windowState3.mHasSurface
                                && windowState3.mViewVisibility == 0
                                && (i2 == 1 || i2 == 2)) {
                            int width = windowState3.mWindowFrames.mFrame.width();
                            int height = windowState3.mWindowFrames.mFrame.height();
                            if (windowState3.mAnimatingExit) {
                                if (windowState2 == null
                                        || (windowState2.mWindowFrames.mFrame.height() <= height
                                                && windowState2.mWindowFrames.mFrame.width()
                                                        <= width)) {
                                    windowState2 = windowState3;
                                }
                            } else if (windowState == null
                                    || (windowState.mWindowFrames.mFrame.height() <= height
                                            && windowState.mWindowFrames.mFrame.width() <= width)) {
                                windowState = windowState3;
                            }
                        }
                    }
                    if (windowState == null) {
                        windowState = windowState2;
                    }
                    if (windowState != null) {
                        if (!zArr[0]) {
                            zArr[0] = true;
                            rect.setEmpty();
                        }
                        Rect rect2 = Task.sTmpBounds;
                        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                        rect2.set(windowState.mWindowFrames.mFrame);
                        rect2.inset(
                                windowState
                                        .getInsetsStateWithVisibilityOverride()
                                        .calculateVisibleInsets(
                                                rect2,
                                                layoutParams.type,
                                                windowState.getActivityType(),
                                                layoutParams.softInputMode,
                                                layoutParams.flags));
                        rect.union(rect2);
                        break;
                    }
                }
                break;
            case 1:
                Task task = (Task) this.f$0;
                int[] iArr = (int[]) this.f$1;
                Task task2 = (Task) obj;
                task.getClass();
                if (task2 != task
                        && task2.inFreeformWindowingMode()
                        && !task2.isMinimized()
                        && task2.isVisible()) {
                    iArr[0] = iArr[0] + 1;
                    break;
                }
                break;
            case 2:
                ActivityManagerService.ItemMatcher itemMatcher =
                        (ActivityManagerService.ItemMatcher) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (itemMatcher.match(activityRecord2.intent.getComponent(), activityRecord2)) {
                    arrayList.add(activityRecord2);
                    break;
                }
                break;
            default:
                WindowState windowState4 = (WindowState) this.f$0;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$1;
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) obj;
                windowState4.mIsSurfacePositionPaused = false;
                windowState4.updateSurfacePosition(transaction2);
                transaction2.merge(transaction);
                break;
        }
    }
}
