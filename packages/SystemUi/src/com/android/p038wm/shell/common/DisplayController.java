package com.android.p038wm.shell.common;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.IWindowManager;
import android.view.InsetsState;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.sysui.ConfigurationChangeListener;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayController implements ConfigurationChangeListener {
    public final DisplayChangeController mChangeController;
    public final Context mContext;
    public String[] mLastOverlayPath;
    public final ShellExecutor mMainExecutor;
    public final ShellController mShellController;
    public final IWindowManager mWmService;
    public final SparseArray mDisplays = new SparseArray();
    public final ArrayList mDisplayChangedListeners = new ArrayList();
    public final DisplayWindowListenerImpl mDisplayContainerListener = new DisplayWindowListenerImpl(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisplayRecord {
        public Context mContext;
        public DisplayLayout mDisplayLayout;
        public InsetsState mInsetsState;

        /* renamed from: -$$Nest$msetDisplayLayout, reason: not valid java name */
        public static void m2739$$Nest$msetDisplayLayout(DisplayRecord displayRecord, Context context, DisplayLayout displayLayout) {
            displayRecord.mContext = context;
            displayRecord.mDisplayLayout = displayLayout;
            Resources resources = context.getResources();
            displayLayout.mInsetsState = displayRecord.mInsetsState;
            displayLayout.recalcInsets(resources);
            if (CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID) {
                Slog.d("DisplayController", "setDisplayLayout. displayId=" + context.getDisplayId() + " displayLayout=" + displayLayout + " overlay=" + Arrays.toString(context.getApplicationInfo().overlayPaths));
            }
        }

        public /* synthetic */ DisplayRecord(int i, int i2) {
            this(i);
        }

        private DisplayRecord(int i) {
            this.mInsetsState = new InsetsState();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisplayWindowListenerImpl extends IDisplayWindowListener.Stub {
        public /* synthetic */ DisplayWindowListenerImpl(DisplayController displayController, int i) {
            this();
        }

        public final void onDisplayAdded(int i) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new RunnableC3852xd1ef1ed1(this, i, 0));
        }

        public final void onDisplayConfigurationChanged(final int i, final Configuration configuration) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = DisplayController.DisplayWindowListenerImpl.this;
                    DisplayController.this.onDisplayConfigurationChanged(i, configuration);
                }
            });
        }

        public final void onDisplayRemoved(int i) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new RunnableC3852xd1ef1ed1(this, i, 1));
        }

        public final void onFixedRotationFinished(int i) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new RunnableC3852xd1ef1ed1(this, i, 2));
        }

        public final void onFixedRotationStarted(final int i, final int i2) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = DisplayController.DisplayWindowListenerImpl.this;
                    int i3 = i;
                    int i4 = i2;
                    DisplayController displayController = DisplayController.this;
                    synchronized (displayController.mDisplays) {
                        if (displayController.mDisplays.get(i3) != null && displayController.getDisplay(i3) != null) {
                            int size = displayController.mDisplayChangedListeners.size();
                            while (true) {
                                size--;
                                if (size < 0) {
                                    return;
                                } else {
                                    ((DisplayController.OnDisplaysChangedListener) displayController.mDisplayChangedListeners.get(size)).onFixedRotationStarted(i3, i4);
                                }
                            }
                        }
                        Slog.w("DisplayController", "Skipping onFixedRotationStarted on unknown display, displayId=" + i3);
                    }
                }
            });
        }

        public final void onKeepClearAreasChanged(final int i, final List list, final List list2) {
            ((HandlerExecutor) DisplayController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = DisplayController.DisplayWindowListenerImpl.this;
                    int i2 = i;
                    List list3 = list;
                    List list4 = list2;
                    DisplayController displayController = DisplayController.this;
                    ArraySet arraySet = new ArraySet(list3);
                    ArraySet arraySet2 = new ArraySet(list4);
                    synchronized (displayController.mDisplays) {
                        if (displayController.mDisplays.get(i2) != null && displayController.getDisplay(i2) != null) {
                            int size = displayController.mDisplayChangedListeners.size();
                            while (true) {
                                size--;
                                if (size < 0) {
                                    return;
                                } else {
                                    ((DisplayController.OnDisplaysChangedListener) displayController.mDisplayChangedListeners.get(size)).onKeepClearAreasChanged(i2, arraySet, arraySet2);
                                }
                            }
                        }
                        Slog.w("DisplayController", "Skipping onKeepClearAreasChanged on unknown display, displayId=" + i2);
                    }
                }
            });
        }

        private DisplayWindowListenerImpl() {
        }
    }

    public DisplayController(Context context, IWindowManager iWindowManager, ShellInit shellInit, ShellExecutor shellExecutor, ShellController shellController) {
        this.mMainExecutor = shellExecutor;
        this.mContext = context;
        this.mWmService = iWindowManager;
        this.mChangeController = new DisplayChangeController(iWindowManager, shellInit, shellExecutor);
        this.mShellController = shellController;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayController displayController = DisplayController.this;
                displayController.getClass();
                try {
                    for (int i : displayController.mWmService.registerDisplayWindowListener(displayController.mDisplayContainerListener)) {
                        displayController.onDisplayAdded(i);
                    }
                    displayController.mShellController.addConfigurationChangeListener(displayController);
                } catch (RemoteException unused) {
                    throw new RuntimeException("Unable to register display controller");
                }
            }
        }, this);
    }

    public final void addDisplayWindowListener(OnDisplaysChangedListener onDisplaysChangedListener) {
        synchronized (this.mDisplays) {
            if (this.mDisplayChangedListeners.contains(onDisplaysChangedListener)) {
                return;
            }
            this.mDisplayChangedListeners.add(onDisplaysChangedListener);
            for (int i = 0; i < this.mDisplays.size(); i++) {
                onDisplaysChangedListener.onDisplayAdded(this.mDisplays.keyAt(i));
            }
        }
    }

    public final Display getDisplay(int i) {
        return ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
    }

    public final Context getDisplayContext(int i) {
        DisplayRecord displayRecord = (DisplayRecord) this.mDisplays.get(i);
        if (displayRecord != null) {
            return displayRecord.mContext;
        }
        return null;
    }

    public final DisplayLayout getDisplayLayout(int i) {
        DisplayRecord displayRecord = (DisplayRecord) this.mDisplays.get(i);
        if (displayRecord != null) {
            return displayRecord.mDisplayLayout;
        }
        return null;
    }

    public final InsetsState getInsetsState(int i) {
        DisplayRecord displayRecord = (DisplayRecord) this.mDisplays.get(i);
        if (displayRecord != null) {
            return displayRecord.mInsetsState;
        }
        return null;
    }

    @Override // com.android.p038wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        synchronized (this.mDisplays) {
            String[] strArr = this.mContext.getApplicationInfo().overlayPaths;
            if (!Arrays.equals(strArr, this.mLastOverlayPath)) {
                Slog.w("DisplayController", "START onConfigurationChanged. mismatch lastOverlayPaths=" + Arrays.toString(this.mLastOverlayPath) + " newOverlayPaths=" + Arrays.toString(strArr));
                onDisplayConfigurationChanged(0, configuration);
            }
        }
    }

    public final void onDisplayAdded(int i) {
        synchronized (this.mDisplays) {
            if (this.mDisplays.get(i) != null) {
                return;
            }
            Display display = getDisplay(i);
            if (display == null) {
                return;
            }
            Context createDisplayContext = i == 0 ? this.mContext : this.mContext.createDisplayContext(display);
            int i2 = 0;
            DisplayRecord displayRecord = new DisplayRecord(i, i2);
            DisplayRecord.m2739$$Nest$msetDisplayLayout(displayRecord, createDisplayContext, new DisplayLayout(createDisplayContext, display));
            this.mDisplays.put(i, displayRecord);
            while (i2 < this.mDisplayChangedListeners.size()) {
                ((OnDisplaysChangedListener) this.mDisplayChangedListeners.get(i2)).onDisplayAdded(i);
                i2++;
            }
            if (i == 0) {
                ActivityThread.currentActivityThread().getSystemUiContext(i);
            }
        }
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        synchronized (this.mDisplays) {
            DisplayRecord displayRecord = (DisplayRecord) this.mDisplays.get(i);
            if (displayRecord == null) {
                Slog.w("DisplayController", "Skipping Display Configuration change on non-added display.");
                return;
            }
            Display display = getDisplay(i);
            if (display == null) {
                Slog.w("DisplayController", "Skipping Display Configuration change on invalid display. It may have been removed.");
                return;
            }
            Context createConfigurationContext = (i == 0 ? this.mContext : this.mContext.createDisplayContext(display)).createConfigurationContext(configuration);
            DisplayRecord.m2739$$Nest$msetDisplayLayout(displayRecord, createConfigurationContext, new DisplayLayout(createConfigurationContext, display));
            for (int i2 = 0; i2 < this.mDisplayChangedListeners.size(); i2++) {
                ((OnDisplaysChangedListener) this.mDisplayChangedListeners.get(i2)).onDisplayConfigurationChanged(i, configuration);
            }
            String[] strArr = this.mContext.getApplicationInfo().overlayPaths;
            if (strArr == null) {
                this.mLastOverlayPath = null;
            } else {
                this.mLastOverlayPath = (String[]) Arrays.copyOf(strArr, strArr.length);
            }
        }
    }

    public final void removeDisplayWindowListener(OnDisplaysChangedListener onDisplaysChangedListener) {
        synchronized (this.mDisplays) {
            this.mDisplayChangedListeners.remove(onDisplaysChangedListener);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnDisplaysChangedListener {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayRemoved(int i) {
        }

        default void onFixedRotationFinished(int i) {
        }

        default void onDisplayConfigurationChanged(int i, Configuration configuration) {
        }

        default void onFixedRotationStarted(int i, int i2) {
        }

        default void onKeepClearAreasChanged(int i, Set set, Set set2) {
        }
    }
}
