package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Slog;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OverviewToDesktopTransitionObserver implements Transitions.TransitionObserver {
    public final Map transitionToCallback = new LinkedHashMap();
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public OverviewToDesktopTransitionObserver(Transitions transitions, ShellInit shellInit) {
        this.transitions = transitions;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.OverviewToDesktopTransitionObserver.1
            @Override // java.lang.Runnable
            public final void run() {
                OverviewToDesktopTransitionObserver overviewToDesktopTransitionObserver = OverviewToDesktopTransitionObserver.this;
                overviewToDesktopTransitionObserver.transitions.registerObserver(overviewToDesktopTransitionObserver);
            }
        }, this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
        try {
            IMoveToDesktopCallback iMoveToDesktopCallback = (IMoveToDesktopCallback) ((LinkedHashMap) this.transitionToCallback).get(iBinder);
            if (iMoveToDesktopCallback != null) {
                IMoveToDesktopCallback$Stub$Proxy iMoveToDesktopCallback$Stub$Proxy = (IMoveToDesktopCallback$Stub$Proxy) iMoveToDesktopCallback;
                Parcel obtain = Parcel.obtain(iMoveToDesktopCallback$Stub$Proxy.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IMoveToDesktopCallback");
                    iMoveToDesktopCallback$Stub$Proxy.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.recycle();
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }
            ((LinkedHashMap) this.transitionToCallback).clear();
        } catch (RemoteException e) {
            Slog.e("OverviewToDesktopTransitionObserver", "onTransitionFinished: Error calling onTaskMovedToDesktop", e);
        }
    }
}
