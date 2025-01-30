package com.android.p038wm.shell.common;

import android.content.ComponentName;
import android.content.res.Resources;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.IDisplayWindowInsetsController;
import android.view.IWindowManager;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.inputmethod.ImeTracker;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayInsetsController;
import com.android.p038wm.shell.sysui.ShellInit;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayInsetsController implements DisplayController.OnDisplaysChangedListener {
    public final DisplayController mDisplayController;
    public final SparseArray mInsetsPerDisplay = new SparseArray();
    public final SparseArray mListeners = new SparseArray();
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PerDisplay {
        public final int mDisplayId;
        public final DisplayWindowInsetsControllerImpl mInsetsControllerImpl = new DisplayWindowInsetsControllerImpl(this, 0);

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DisplayWindowInsetsControllerImpl extends IDisplayWindowInsetsController.Stub {
            public /* synthetic */ DisplayWindowInsetsControllerImpl(PerDisplay perDisplay, int i) {
                this();
            }

            public final void hideInsets(int i, boolean z, ImeTracker.Token token) {
                ((HandlerExecutor) DisplayInsetsController.this.mMainExecutor).execute(new RunnableC3855x9b4a9e24(this, i, z, token, 0));
            }

            public final void insetsChanged(final InsetsState insetsState) {
                ((HandlerExecutor) DisplayInsetsController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl.this;
                        InsetsState insetsState2 = insetsState;
                        DisplayInsetsController.PerDisplay perDisplay = DisplayInsetsController.PerDisplay.this;
                        DisplayInsetsController displayInsetsController = DisplayInsetsController.this;
                        SparseArray sparseArray = displayInsetsController.mListeners;
                        int i = perDisplay.mDisplayId;
                        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) sparseArray.get(i);
                        if (copyOnWriteArrayList == null) {
                            return;
                        }
                        DisplayController.DisplayRecord displayRecord = (DisplayController.DisplayRecord) displayInsetsController.mDisplayController.mDisplays.get(i);
                        if (displayRecord != null) {
                            displayRecord.mInsetsState = insetsState2;
                            DisplayLayout displayLayout = displayRecord.mDisplayLayout;
                            Resources resources = displayRecord.mContext.getResources();
                            displayLayout.mInsetsState = insetsState2;
                            displayLayout.recalcInsets(resources);
                        }
                        Iterator it = copyOnWriteArrayList.iterator();
                        while (it.hasNext()) {
                            ((DisplayInsetsController.OnInsetsChangedListener) it.next()).insetsChanged(insetsState2);
                        }
                    }
                });
            }

            public final void insetsControlChanged(final InsetsState insetsState, final InsetsSourceControl[] insetsSourceControlArr) {
                ((HandlerExecutor) DisplayInsetsController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl.this;
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

            public final void showInsets(int i, boolean z, ImeTracker.Token token) {
                ((HandlerExecutor) DisplayInsetsController.this.mMainExecutor).execute(new RunnableC3855x9b4a9e24(this, i, z, token, 1));
            }

            public final void topFocusedWindowChanged(final ComponentName componentName, final int i) {
                ((HandlerExecutor) DisplayInsetsController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInsetsController.PerDisplay perDisplay = DisplayInsetsController.PerDisplay.this;
                        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) DisplayInsetsController.this.mListeners.get(perDisplay.mDisplayId);
                        if (copyOnWriteArrayList == null) {
                            return;
                        }
                        Iterator it = copyOnWriteArrayList.iterator();
                        while (it.hasNext()) {
                            ((DisplayInsetsController.OnInsetsChangedListener) it.next()).topFocusedWindowChanged();
                        }
                    }
                });
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
                DisplayInsetsController displayInsetsController = DisplayInsetsController.this;
                displayInsetsController.mDisplayController.addDisplayWindowListener(displayInsetsController);
            }
        }, this);
    }

    public final void addInsetsChangedListener(int i, OnInsetsChangedListener onInsetsChangedListener) {
        SparseArray sparseArray = this.mListeners;
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) sparseArray.get(i);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList();
            sparseArray.put(i, copyOnWriteArrayList);
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
        SparseArray sparseArray = this.mInsetsPerDisplay;
        PerDisplay perDisplay = (PerDisplay) sparseArray.get(i);
        if (perDisplay == null) {
            return;
        }
        int i2 = perDisplay.mDisplayId;
        try {
            DisplayInsetsController.this.mWmService.setDisplayWindowInsetsController(i2, (IDisplayWindowInsetsController) null);
        } catch (RemoteException unused) {
            Slog.w("DisplayInsetsController", "Unable to remove insets controller on display " + i2);
        }
        sparseArray.remove(i);
    }

    public final void removeInsetsChangedListener(int i, OnInsetsChangedListener onInsetsChangedListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.mListeners.get(i);
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onInsetsChangedListener);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnInsetsChangedListener {
        void insetsChanged(InsetsState insetsState);

        default void topFocusedWindowChanged() {
        }

        default void hideInsets(int i, ImeTracker.Token token) {
        }

        default void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        }

        default void showInsets(int i, ImeTracker.Token token) {
        }
    }
}
