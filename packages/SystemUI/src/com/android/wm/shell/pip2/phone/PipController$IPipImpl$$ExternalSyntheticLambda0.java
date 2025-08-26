package com.android.wm.shell.pip2.phone;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.pip.IPipAnimationListener$Stub$Proxy;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip2.phone.PipController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$3;
    public final /* synthetic */ Rect f$4;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda0(int i, ComponentName componentName, Rect rect, SurfaceControl surfaceControl, Rect rect2, Rect rect3) {
        this.f$0 = i;
        this.f$1 = componentName;
        this.f$3 = surfaceControl;
        this.f$4 = rect2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) throws Resources.NotFoundException {
        DisplayLayout displayLayout;
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                SurfaceControl surfaceControl = (SurfaceControl) this.f$3;
                Rect rect = this.f$4;
                PipController pipController = (PipController) obj;
                int i2 = PipController.IPipImpl.$r8$clinit;
                pipController.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -394357184756239568L, 0, String.valueOf(componentName));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("swipe_to_pip_overlay", surfaceControl);
                bundle.putParcelable("pip_app_bounds", rect);
                pipController.mPipTransitionState.setState(1, bundle);
                if (surfaceControl != null) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    pipController.mShellTaskOrganizer.reparentChildSurfaceToTask(i, transaction, surfaceControl);
                    transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                    transaction.apply();
                }
                PipController.IPipImpl.AnonymousClass1 anonymousClass1 = pipController.mPipRecentsAnimationListener;
                if (anonymousClass1 != null) {
                    IInterface iInterface = PipController.IPipImpl.this.mListener.mListener;
                    if (iInterface == null) {
                        Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                        break;
                    } else {
                        try {
                            ((IPipAnimationListener$Stub$Proxy) iInterface).onPipAnimationStarted();
                            break;
                        } catch (RemoteException e) {
                            Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                            return;
                        }
                    }
                }
                break;
            default:
                Rect[] rectArr = (Rect[]) this.f$1;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$3;
                int i3 = this.f$0;
                Rect rect2 = this.f$4;
                PipController pipController2 = (PipController) obj;
                int i4 = PipController.IPipImpl.$r8$clinit;
                ComponentName componentName2 = runningTaskInfo.topActivity;
                ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
                int i5 = runningTaskInfo.displayId;
                PictureInPictureParams pictureInPictureParams = runningTaskInfo.pictureInPictureParams;
                pipController2.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8767559398966243625L, 0, String.valueOf(componentName2));
                }
                boolean zIsTrue = DesktopExperienceFlags.ENABLE_CONNECTED_DISPLAYS_PIP.isTrue();
                PipDisplayLayoutState pipDisplayLayoutState = pipController2.mPipDisplayLayoutState;
                if (zIsTrue && (displayLayout = pipController2.mDisplayController.getDisplayLayout(i5)) != null) {
                    pipDisplayLayoutState.mDisplayId = i5;
                    pipDisplayLayoutState.mDisplayLayout.set(displayLayout);
                }
                PipBoundsState pipBoundsState = pipController2.mPipBoundsState;
                pipBoundsState.setNamedUnrestrictedKeepClearArea(0, rect2);
                pipDisplayLayoutState.rotateTo(i3);
                PipBoundsAlgorithm pipBoundsAlgorithm = pipController2.mPipBoundsAlgorithm;
                pipBoundsState.setBoundsStateForEntry(componentName2, activityInfo, pictureInPictureParams, pipBoundsAlgorithm);
                pipBoundsState.updateMinMaxSize(pipBoundsState.mAspectRatio);
                rectArr[0] = pipBoundsAlgorithm.getEntryDestinationBounds();
                break;
        }
    }

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda0(Rect[] rectArr, ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect) {
        this.f$1 = rectArr;
        this.f$3 = runningTaskInfo;
        this.f$0 = i;
        this.f$4 = rect;
    }
}
