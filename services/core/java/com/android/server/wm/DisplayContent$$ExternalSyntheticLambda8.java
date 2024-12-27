package com.android.server.wm;

import android.graphics.Point;
import android.view.SurfaceControl;
import android.view.WindowInsets;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda8(
            SurfaceControl.Transaction transaction, int i, int i2, boolean z) {
        this.$r8$classId = 1;
        this.f$2 = transaction;
        this.f$1 = i;
        this.f$3 = i2;
        this.f$0 = z;
    }

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda8(List list, boolean z) {
        this.$r8$classId = 0;
        this.f$0 = z;
        this.f$1 = 1;
        this.f$2 = list;
        this.f$3 = 5;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task;
        switch (this.$r8$classId) {
            case 0:
                boolean z = this.f$0;
                int i = this.f$1;
                List list = (List) this.f$2;
                int i2 = this.f$3;
                Task task2 = (Task) obj;
                if (!z) {
                    if (task2.isCompatible(i2, i)) {
                        list.add(task2);
                        break;
                    }
                } else if (task2.getActivityType() == i) {
                    list.add(task2);
                    break;
                }
                break;
            default:
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$2;
                int i3 = this.f$1;
                int i4 = this.f$3;
                boolean z2 = this.f$0;
                WindowState windowState = (WindowState) obj;
                if (windowState.isVisibleNow()
                        && !windowState.mIsWallpaper
                        && !windowState.mToken.hasFixedRotationTransform()
                        && ((task = windowState.getTask()) == null
                                || !task.inPinnedWindowingMode())) {
                    SeamlessRotator seamlessRotator = windowState.mPendingSeamlessRotate;
                    if (seamlessRotator != null) {
                        i3 = seamlessRotator.mOldRotation;
                    }
                    InsetsSourceProvider insetsSourceProvider =
                            windowState.mControllableInsetProvider;
                    if ((insetsSourceProvider == null
                                    || insetsSourceProvider.mSource.getType()
                                            != WindowInsets.Type.ime())
                            && (windowState.mForceSeamlesslyRotate || z2)) {
                        InsetsSourceProvider insetsSourceProvider2 =
                                windowState.mControllableInsetProvider;
                        if (insetsSourceProvider2 != null
                                && !insetsSourceProvider2.mSeamlessRotating) {
                            insetsSourceProvider2.mSeamlessRotating = true;
                            insetsSourceProvider2.mWindowContainer.cancelAnimation();
                        }
                        windowState.mPendingSeamlessRotate =
                                new SeamlessRotator(i3, i4, windowState.getDisplayInfo(), false);
                        Point point = windowState.mLastSurfacePosition;
                        Point point2 = windowState.mSurfacePosition;
                        point.set(point2.x, point2.y);
                        windowState.mPendingSeamlessRotate.unrotate(transaction, windowState);
                        windowState
                                .getDisplayContent()
                                .mDisplayRotation
                                .markForSeamlessRotation(windowState, true);
                        windowState.applyWithNextDraw(
                                0, windowState.mSeamlessRotationFinishedConsumer);
                    }
                }
                if (!z2 && windowState.mHasSurface) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_ORIENTATION,
                                2632363530212357762L,
                                0,
                                null,
                                String.valueOf(windowState));
                    }
                    windowState.setOrientationChanging(true);
                    break;
                }
                break;
        }
    }
}
