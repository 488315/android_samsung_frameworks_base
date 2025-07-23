package com.android.wm.shell.transition;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Slog;
import android.view.InsetsState;
import android.window.DesktopModeFlags;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.shared.IHomeTransitionListener$Stub$Proxy;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                final HomeTransitionObserver homeTransitionObserver = HomeTransitionObserver.this;
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
                            Parcel obtain = Parcel.obtain(iHomeTransitionListener$Stub$Proxy.mRemote);
                            try {
                                obtain.writeInterfaceToken("com.android.wm.shell.shared.IHomeTransitionListener");
                                obtain.writeTypedObject(insetsState, 0);
                                iHomeTransitionListener$Stub$Proxy.mRemote.transact(2, obtain, null, 1);
                                obtain.recycle();
                            } catch (Throwable th) {
                                obtain.recycle();
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
                Parcel obtain = Parcel.obtain(iHomeTransitionListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.shared.IHomeTransitionListener");
                    obtain.writeBoolean(z);
                    iHomeTransitionListener$Stub$Proxy.mRemote.transact(1, obtain, null, 1);
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain.recycle();
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x006d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x000a A[SYNTHETIC] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTransitionReady(android.os.IBinder r8, android.window.TransitionInfo r9, android.view.SurfaceControl.Transaction r10, android.view.SurfaceControl.Transaction r11) {
        /*
            r7 = this;
            java.util.List r10 = r9.getChanges()
            java.util.Iterator r10 = r10.iterator()
            r11 = 0
            r0 = r11
        La:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L6f
            java.lang.Object r1 = r10.next()
            android.window.TransitionInfo$Change r1 = (android.window.TransitionInfo.Change) r1
            android.app.ActivityManager$RunningTaskInfo r2 = r1.getTaskInfo()
            if (r2 == 0) goto La
            int r3 = r2.displayId
            if (r3 != 0) goto La
            int r3 = r2.taskId
            r4 = -1
            if (r3 == r4) goto La
            boolean r3 = r2.isRunning
            if (r3 != 0) goto L2a
            goto La
        L2a:
            int r3 = r1.getMode()
            r4 = 131072(0x20000, float:1.83671E-40)
            boolean r1 = r1.hasFlags(r4)
            int r2 = r2.getActivityType()
            r4 = 2
            if (r2 != r4) goto L6a
            r2 = 0
            r4 = 1
            if (r1 == 0) goto L4b
            int r5 = r9.getType()
            boolean r5 = com.android.wm.shell.shared.TransitionUtil.isClosingType(r5)
            if (r5 == 0) goto L4b
            r5 = r4
            goto L4c
        L4b:
            r5 = r2
        L4c:
            if (r5 != 0) goto L5c
            boolean r6 = com.android.wm.shell.shared.TransitionUtil.isClosingMode(r3)
            if (r6 != 0) goto L5c
            if (r1 != 0) goto L6a
            boolean r1 = com.android.wm.shell.shared.TransitionUtil.isOpeningMode(r3)
            if (r1 == 0) goto L6a
        L5c:
            if (r5 != 0) goto L64
            boolean r1 = com.android.wm.shell.shared.TransitionUtil.isOpeningType(r3)
            if (r1 == 0) goto L65
        L64:
            r2 = r4
        L65:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            goto L6b
        L6a:
            r1 = r11
        L6b:
            if (r1 == 0) goto La
            r0 = r1
            goto La
        L6f:
            int r9 = r9.getType()
            r10 = 1109(0x455, float:1.554E-42)
            if (r9 != r10) goto L85
            android.window.DesktopModeFlags r9 = android.window.DesktopModeFlags.ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX
            boolean r9 = r9.isTrue()
            if (r9 != 0) goto L80
            goto L92
        L80:
            r7.mPendingHomeVisibilityUpdate = r0
            r7.mPendingStartDragTransition = r8
            return
        L85:
            if (r0 == 0) goto L92
            r7.mPendingHomeVisibilityUpdate = r11
            r7.mPendingStartDragTransition = r11
            boolean r8 = r0.booleanValue()
            r7.notifyHomeVisibilityChanged(r8)
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.HomeTransitionObserver.onTransitionReady(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction):void");
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
