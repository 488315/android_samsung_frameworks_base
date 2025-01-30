package com.android.p038wm.shell.common;

import android.os.RemoteException;
import android.util.Slog;
import android.view.IDisplayChangeWindowCallback;
import android.view.IDisplayChangeWindowController;
import android.view.IWindowManager;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.DisplayChangeController;
import com.android.p038wm.shell.sysui.ShellInit;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayChangeController {
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;
    public final CopyOnWriteArrayList mDisplayChangeListener = new CopyOnWriteArrayList();
    public final DisplayChangeWindowControllerImpl mControllerImpl = new DisplayChangeWindowControllerImpl(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisplayChangeWindowControllerImpl extends IDisplayChangeWindowController.Stub {
        public /* synthetic */ DisplayChangeWindowControllerImpl(DisplayChangeController displayChangeController, int i) {
            this();
        }

        public final void onDisplayChange(final int i, final int i2, final int i3, final DisplayAreaInfo displayAreaInfo, final IDisplayChangeWindowCallback iDisplayChangeWindowCallback) {
            ((HandlerExecutor) DisplayChangeController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayChangeController$DisplayChangeWindowControllerImpl$$ExternalSyntheticLambda0
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
                        iDisplayChangeWindowCallback2.continueDisplayChange(windowContainerTransaction);
                    } catch (RemoteException e) {
                        Slog.e("DisplayChangeController", "Failed to continue handling display change", e);
                    }
                }
            });
        }

        private DisplayChangeWindowControllerImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                displayChangeController.getClass();
                try {
                    displayChangeController.mWmService.setDisplayChangeWindowController(displayChangeController.mControllerImpl);
                } catch (RemoteException unused) {
                    throw new RuntimeException("Unable to register rotation controller");
                }
            }
        }, this);
    }

    public final void dispatchOnDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        Iterator it = this.mDisplayChangeListener.iterator();
        while (it.hasNext()) {
            ((OnDisplayChangingListener) it.next()).onDisplayChange(i, i2, i3, displayAreaInfo, windowContainerTransaction);
        }
    }
}
