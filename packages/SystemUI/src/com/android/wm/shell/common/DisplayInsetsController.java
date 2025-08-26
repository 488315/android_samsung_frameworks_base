package com.android.wm.shell.common;

import android.content.ComponentName;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.IDisplayWindowInsetsController;
import android.view.IWindowManager;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.inputmethod.ImeTracker;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.sysui.ShellInit;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class DisplayInsetsController implements DisplayController.OnDisplaysChangedListener {
    public final DisplayController mDisplayController;
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;
    public final SparseArray mInsetsPerDisplay = new SparseArray();
    public final SparseArray mListeners = new SparseArray();
    public final CopyOnWriteArrayList mGlobalListeners = new CopyOnWriteArrayList();

    public interface OnInsetsChangedListener {
        default void insetsChanged(InsetsState insetsState) {
        }

        default void insetsChanged(int i, InsetsState insetsState) {
            insetsChanged(insetsState);
        }

        default void hideInsets(int i, ImeTracker.Token token) {
        }

        default void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        }

        default void setImeInputTargetRequestedVisibility(boolean z, ImeTracker.Token token) {
        }

        default void showInsets(int i, ImeTracker.Token token) {
        }
    }

    public class PerDisplay {
        public final int mDisplayId;
        public final DisplayWindowInsetsControllerImpl mInsetsControllerImpl = new DisplayWindowInsetsControllerImpl(this, 0);

        public class DisplayWindowInsetsControllerImpl extends IDisplayWindowInsetsController.Stub {
            public /* synthetic */ DisplayWindowInsetsControllerImpl(PerDisplay perDisplay, int i) {
                this();
            }

            public final void hideInsets(int i, boolean z, ImeTracker.Token token) {
                DisplayInsetsController.this.mMainExecutor.execute(new DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda4(this, i, z, token, 1));
            }

            public final void insetsChanged(InsetsState insetsState) {
                DisplayInsetsController.this.mMainExecutor.execute(new DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(this, insetsState));
            }

            public final void insetsControlChanged(final InsetsState insetsState, final InsetsSourceControl[] insetsSourceControlArr) {
                DisplayInsetsController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = this.f$0;
                        InsetsState insetsState2 = insetsState;
                        InsetsSourceControl[] insetsSourceControlArr2 = insetsSourceControlArr;
                        DisplayInsetsController.PerDisplay perDisplay = DisplayInsetsController.PerDisplay.this;
                        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) DisplayInsetsController.this.mListeners.get(perDisplay.mDisplayId);
                        if (copyOnWriteArrayList == null) {
                            return;
                        }
                        Iterator it = copyOnWriteArrayList.iterator();
                        while (it.hasNext()) {
                            ((DisplayInsetsController.OnInsetsChangedListener) it.next()).insetsControlChanged(insetsState2, insetsSourceControlArr2);
                        }
                    }
                });
            }

            public final void setImeInputTargetRequestedVisibility(final boolean z, final ImeTracker.Token token) {
                DisplayInsetsController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = this.f$0;
                        boolean z2 = z;
                        ImeTracker.Token token2 = token;
                        DisplayInsetsController.PerDisplay perDisplay = DisplayInsetsController.PerDisplay.this;
                        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) DisplayInsetsController.this.mListeners.get(perDisplay.mDisplayId);
                        if (copyOnWriteArrayList == null) {
                            return;
                        }
                        Iterator it = copyOnWriteArrayList.iterator();
                        while (it.hasNext()) {
                            ((DisplayInsetsController.OnInsetsChangedListener) it.next()).setImeInputTargetRequestedVisibility(z2, token2);
                        }
                    }
                });
            }

            public final void showInsets(int i, boolean z, ImeTracker.Token token) {
                DisplayInsetsController.this.mMainExecutor.execute(new DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda4(this, i, z, token, 0));
            }

            public final void topFocusedWindowChanged(ComponentName componentName, int i) {
                DisplayInsetsController.this.mMainExecutor.execute(new DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(this, componentName, i));
            }

            private DisplayWindowInsetsControllerImpl() {
            }
        }

        public PerDisplay(int i) {
            this.mDisplayId = i;
        }
    }

    public DisplayInsetsController(IWindowManager iWindowManager, ShellInit shellInit, DisplayController displayController, ShellExecutor shellExecutor) {
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayInsetsController displayInsetsController = this.f$0;
                displayInsetsController.mDisplayController.addDisplayWindowListener(displayInsetsController, -1);
            }
        }, this);
    }

    public final void addInsetsChangedListener(int i, OnInsetsChangedListener onInsetsChangedListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.mListeners.get(i);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList();
            this.mListeners.put(i, copyOnWriteArrayList);
        }
        if (copyOnWriteArrayList.contains(onInsetsChangedListener)) {
            return;
        }
        copyOnWriteArrayList.add(onInsetsChangedListener);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        PerDisplay perDisplay = new PerDisplay(i);
        int i2 = perDisplay.mDisplayId;
        try {
            DisplayInsetsController.this.mWmService.setDisplayWindowInsetsController(i2, perDisplay.mInsetsControllerImpl);
        } catch (RemoteException unused) {
            Slog.w("DisplayInsetsController", "Unable to set insets controller on display " + i2);
        }
        this.mInsetsPerDisplay.put(i, perDisplay);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        PerDisplay perDisplay = (PerDisplay) this.mInsetsPerDisplay.get(i);
        if (perDisplay == null) {
            return;
        }
        int i2 = perDisplay.mDisplayId;
        try {
            DisplayInsetsController.this.mWmService.setDisplayWindowInsetsController(i2, (IDisplayWindowInsetsController) null);
        } catch (RemoteException unused) {
            Slog.w("DisplayInsetsController", "Unable to remove insets controller on display " + i2);
        }
        this.mInsetsPerDisplay.remove(i);
    }

    public final void removeInsetsChangedListener(int i, OnInsetsChangedListener onInsetsChangedListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.mListeners.get(i);
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onInsetsChangedListener);
    }
}
