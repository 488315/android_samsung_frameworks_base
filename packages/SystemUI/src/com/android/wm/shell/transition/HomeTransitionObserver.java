package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Slog;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.shared.IHomeTransitionListener$Stub$Proxy;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;

/* loaded from: classes3.dex */
public class HomeTransitionObserver implements Transitions.TransitionObserver, RemoteCallable {
    public final Context mContext;
    public final DisplayInsetsController mDisplayInsetsController;
    public SingleInstanceRemoteListener mListener;
    public final ShellExecutor mMainExecutor;
    public Boolean mPendingHomeVisibilityUpdate;
    public IBinder mPendingStartDragTransition;

    public HomeTransitionObserver(Context context, ShellExecutor shellExecutor, DisplayInsetsController displayInsetsController, ShellInit shellInit) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mDisplayInsetsController = displayInsetsController;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.transition.HomeTransitionObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final HomeTransitionObserver homeTransitionObserver = this.f$0;
                homeTransitionObserver.getClass();
                homeTransitionObserver.mDisplayInsetsController.addInsetsChangedListener(0, new DisplayInsetsController.OnInsetsChangedListener() { // from class: com.android.wm.shell.transition.HomeTransitionObserver.1
                    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
                    public final void insetsChanged(InsetsState insetsState) {
                        SingleInstanceRemoteListener singleInstanceRemoteListener = HomeTransitionObserver.this.mListener;
                        if (singleInstanceRemoteListener == null) {
                            return;
                        }
                        IInterface iInterface = singleInstanceRemoteListener.mListener;
                        if (iInterface == null) {
                            Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                            return;
                        }
                        try {
                            IHomeTransitionListener$Stub$Proxy iHomeTransitionListener$Stub$Proxy = (IHomeTransitionListener$Stub$Proxy) iInterface;
                            Parcel parcelObtain = Parcel.obtain(iHomeTransitionListener$Stub$Proxy.mRemote);
                            try {
                                parcelObtain.writeInterfaceToken("com.android.wm.shell.shared.IHomeTransitionListener");
                                parcelObtain.writeTypedObject(insetsState, 0);
                                iHomeTransitionListener$Stub$Proxy.mRemote.transact(2, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        } catch (RemoteException e) {
                            Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                        }
                    }
                });
            }
        }, this);
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final void notifyHomeVisibilityChanged(boolean z) {
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.mListener;
        if (singleInstanceRemoteListener != null) {
            IInterface iInterface = singleInstanceRemoteListener.mListener;
            if (iInterface == null) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                return;
            }
            try {
                IHomeTransitionListener$Stub$Proxy iHomeTransitionListener$Stub$Proxy = (IHomeTransitionListener$Stub$Proxy) iInterface;
                Parcel parcelObtain = Parcel.obtain(iHomeTransitionListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.shared.IHomeTransitionListener");
                    parcelObtain.writeBoolean(z);
                    iHomeTransitionListener$Stub$Proxy.mRemote.transact(1, parcelObtain, null, 1);
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (RemoteException e) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
        IBinder iBinder2;
        Boolean bool;
        if (DesktopModeFlags.ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX.isTrue() && (iBinder2 = this.mPendingStartDragTransition) != null && iBinder2 == iBinder) {
            this.mPendingStartDragTransition = null;
            if (z || (bool = this.mPendingHomeVisibilityUpdate) == null) {
                return;
            }
            notifyHomeVisibilityChanged(bool.booleanValue());
            this.mPendingHomeVisibilityUpdate = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006a  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        Boolean bool = null;
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null && taskInfo.displayId == 0 && taskInfo.taskId != -1 && taskInfo.isRunning) {
                int mode = change.getMode();
                boolean zHasFlags = change.hasFlags(131072);
                if (taskInfo.getActivityType() == 2) {
                    boolean z = zHasFlags && TransitionUtil.isClosingType(transitionInfo.getType());
                    Boolean boolValueOf = (z || TransitionUtil.isClosingMode(mode) || (!zHasFlags && TransitionUtil.isOpeningMode(mode))) ? Boolean.valueOf(z || TransitionUtil.isOpeningType(mode)) : null;
                    if (boolValueOf != null) {
                        bool = boolValueOf;
                    }
                }
            }
        }
        if (transitionInfo.getType() == 1109) {
            if (DesktopModeFlags.ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX.isTrue()) {
                this.mPendingHomeVisibilityUpdate = bool;
                this.mPendingStartDragTransition = iBinder;
                return;
            }
            return;
        }
        if (bool != null) {
            this.mPendingHomeVisibilityUpdate = null;
            this.mPendingStartDragTransition = null;
            notifyHomeVisibilityChanged(bool.booleanValue());
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
