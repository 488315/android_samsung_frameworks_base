package com.android.wm.shell.common;

import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.IDisplayChangeWindowCallback;
import android.view.IDisplayChangeWindowController;
import android.view.IWindowManager;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.sysui.ShellInit;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DisplayChangeController {
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;
    public final CopyOnWriteArrayList mDisplayChangeListener = new CopyOnWriteArrayList();
    public final DisplayChangeWindowControllerImpl mControllerImpl = new DisplayChangeWindowControllerImpl(this, 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisplayChangeWindowControllerImpl extends IDisplayChangeWindowController.Stub {
        public /* synthetic */ DisplayChangeWindowControllerImpl(DisplayChangeController displayChangeController, int i) {
            this();
        }

        public final void onDisplayChange(final int i, final int i2, final int i3, final DisplayAreaInfo displayAreaInfo, final IDisplayChangeWindowCallback iDisplayChangeWindowCallback) {
            if (Trace.isTagEnabled(32L)) {
                Trace.beginAsyncSection("HandleRemoteDisplayChange", iDisplayChangeWindowCallback.hashCode());
            }
            DisplayChangeController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayChangeController$DisplayChangeWindowControllerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayChangeController.DisplayChangeWindowControllerImpl displayChangeWindowControllerImpl = DisplayChangeController.DisplayChangeWindowControllerImpl.this;
                    int i4 = i;
                    int i5 = i2;
                    int i6 = i3;
                    DisplayAreaInfo displayAreaInfo2 = displayAreaInfo;
                    IDisplayChangeWindowCallback iDisplayChangeWindowCallback2 = iDisplayChangeWindowCallback;
                    DisplayChangeController displayChangeController = DisplayChangeController.this;
                    displayChangeController.getClass();
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    displayChangeController.dispatchOnDisplayChange(i4, i5, i6, displayAreaInfo2, windowContainerTransaction);
                    try {
                        try {
                            iDisplayChangeWindowCallback2.continueDisplayChange(windowContainerTransaction);
                            if (Trace.isTagEnabled(32L)) {
                                Trace.endAsyncSection("HandleRemoteDisplayChange", iDisplayChangeWindowCallback2.hashCode());
                            }
                        } catch (RemoteException e) {
                            Slog.e("DisplayChangeController", "Failed to continue handling display change", e);
                            if (Trace.isTagEnabled(32L)) {
                                Trace.endAsyncSection("HandleRemoteDisplayChange", iDisplayChangeWindowCallback2.hashCode());
                            }
                        }
                    } catch (Throwable th) {
                        if (Trace.isTagEnabled(32L)) {
                            Trace.endAsyncSection("HandleRemoteDisplayChange", iDisplayChangeWindowCallback2.hashCode());
                        }
                        throw th;
                    }
                }
            });
        }

        private DisplayChangeWindowControllerImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnDisplayChangingListener {
        void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction);
    }

    public DisplayChangeController(IWindowManager iWindowManager, ShellInit shellInit, ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
        this.mWmService = iWindowManager;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.DisplayChangeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayChangeController displayChangeController = DisplayChangeController.this;
                try {
                    displayChangeController.mWmService.setDisplayChangeWindowController(displayChangeController.mControllerImpl);
                } catch (RemoteException unused) {
                    throw new RuntimeException("Unable to register rotation controller");
                }
            }
        }, this);
    }

    public final void dispatchOnDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        if (Trace.isTagEnabled(32L)) {
            Trace.beginSection("dispatchOnDisplayChange");
        }
        Iterator it = this.mDisplayChangeListener.iterator();
        while (it.hasNext()) {
            ((OnDisplayChangingListener) it.next()).onDisplayChange(i, i2, i3, displayAreaInfo, windowContainerTransaction);
        }
        if (Trace.isTagEnabled(32L)) {
            Trace.endSection();
        }
    }
}
