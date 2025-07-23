package com.android.wm.shell.common;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayTopology;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.IWindowManager;
import android.view.InsetsState;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DisplayController {
    public final DisplayChangeController mChangeController;
    public final Context mContext;
    public final DesktopState mDesktopState;
    public final DisplayManager mDisplayManager;
    public DisplayTopology mDisplayTopology;
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;
    public final SparseArray mDisplays = new SparseArray();
    public final ArrayList mDisplayChangedListeners = new ArrayList();
    public final Map mUnpopulatedDisplayBounds = new HashMap();
    public final DisplayWindowListenerImpl mDisplayContainerListener = new DisplayWindowListenerImpl(this, 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisplayRecord {
        public Context mContext;
        public DisplayLayout mDisplayLayout;
        public InsetsState mInsetsState;

        public /* synthetic */ DisplayRecord(int i, int i2) {
            this(i);
        }

        private DisplayRecord(int i) {
            this.mInsetsState = new InsetsState();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisplayWindowListenerImpl extends IDisplayWindowListener.Stub {
        public /* synthetic */ DisplayWindowListenerImpl(DisplayController displayController, int i) {
            this();
        }

        public final void onDesktopModeEligibleChanged(int i) {
            DisplayController.this.mMainExecutor.execute(new DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 3));
        }

        public final void onDisplayAdded(int i) {
            DisplayController.this.mMainExecutor.execute(new DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 0));
        }

        public final void onDisplayConfigurationChanged(final int i, final Configuration configuration) {
            DisplayController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = DisplayController.DisplayWindowListenerImpl.this;
                    int i2 = i;
                    Configuration configuration2 = configuration;
                    DisplayController displayController = DisplayController.this;
                    synchronized (displayController.mDisplays) {
                        try {
                            DisplayController.DisplayRecord displayRecord = (DisplayController.DisplayRecord) displayController.mDisplays.get(i2);
                            if (displayRecord == null) {
                                Slog.w("DisplayController", "Skipping Display Configuration change on non-added display.");
                                return;
                            }
                            Display display = displayController.mDisplayManager.getDisplay(i2);
                            if (display == null) {
                                Slog.w("DisplayController", "Skipping Display Configuration change on invalid display. It may have been removed.");
                                return;
                            }
                            Context createConfigurationContext = (i2 == 0 ? displayController.mContext : displayController.mContext.createDisplayContext(display)).createConfigurationContext(configuration2);
                            DisplayLayout displayLayout = new DisplayLayout(createConfigurationContext, display);
                            DisplayTopology displayTopology = displayController.mDisplayTopology;
                            if (displayTopology != null) {
                                displayLayout.mGlobalBoundsDp = (RectF) displayTopology.getAbsoluteBounds().get(i2, displayLayout.mGlobalBoundsDp);
                            }
                            displayRecord.mContext = createConfigurationContext;
                            displayRecord.mDisplayLayout = displayLayout;
                            Resources resources = createConfigurationContext.getResources();
                            displayLayout.mInsetsState = displayRecord.mInsetsState;
                            displayLayout.recalcInsets(resources);
                            for (int i3 = 0; i3 < displayController.mDisplayChangedListeners.size(); i3++) {
                                ((DisplayController.OnDisplaysChangedListener) displayController.mDisplayChangedListeners.get(i3)).onDisplayConfigurationChanged(i2, configuration2);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        public final void onDisplayRemoved(int i) {
            DisplayController.this.mMainExecutor.execute(new DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 1));
        }

        public final void onFixedRotationFinished(int i) {
            DisplayController.this.mMainExecutor.execute(new DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(this, i, 2));
        }

        public final void onFixedRotationStarted(final int i, final int i2) {
            DisplayController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = DisplayController.DisplayWindowListenerImpl.this;
                    int i3 = i;
                    int i4 = i2;
                    DisplayController displayController = DisplayController.this;
                    synchronized (displayController.mDisplays) {
                        try {
                            if (displayController.mDisplays.get(i3) != null && displayController.mDisplayManager.getDisplay(i3) != null) {
                                for (int size = displayController.mDisplayChangedListeners.size() - 1; size >= 0; size--) {
                                    ((DisplayController.OnDisplaysChangedListener) displayController.mDisplayChangedListeners.get(size)).onFixedRotationStarted(i3, i4);
                                }
                                return;
                            }
                            Slog.w("DisplayController", "Skipping onFixedRotationStarted on unknown display, displayId=" + i3);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        public final void onKeepClearAreasChanged(final int i, final List list, final List list2) {
            DisplayController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3
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
                        try {
                            if (displayController.mDisplays.get(i2) != null && displayController.mDisplayManager.getDisplay(i2) != null) {
                                for (int size = displayController.mDisplayChangedListeners.size() - 1; size >= 0; size--) {
                                    ((DisplayController.OnDisplaysChangedListener) displayController.mDisplayChangedListeners.get(size)).onKeepClearAreasChanged(i2, arraySet, arraySet2);
                                }
                                return;
                            }
                            Slog.w("DisplayController", "Skipping onKeepClearAreasChanged on unknown display, displayId=" + i2);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        private DisplayWindowListenerImpl() {
        }
    }

    public DisplayController(Context context, IWindowManager iWindowManager, ShellInit shellInit, ShellExecutor shellExecutor, DisplayManager displayManager, DesktopState desktopState) {
        this.mMainExecutor = shellExecutor;
        this.mContext = context;
        this.mWmService = iWindowManager;
        this.mDisplayManager = displayManager;
        this.mDesktopState = desktopState;
        this.mChangeController = new DisplayChangeController(iWindowManager, shellInit, shellExecutor);
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.DisplayController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final DisplayController displayController = DisplayController.this;
                try {
                    for (int i : displayController.mWmService.registerDisplayWindowListener(displayController.mDisplayContainerListener)) {
                        displayController.onDisplayAdded(i);
                    }
                    if (((DesktopStateImpl) displayController.mDesktopState).canEnterDesktopMode) {
                        displayController.mDisplayManager.registerTopologyListener(displayController.mMainExecutor, new Consumer() { // from class: com.android.wm.shell.common.DisplayController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DisplayController.this.onDisplayTopologyChanged((DisplayTopology) obj);
                            }
                        });
                        displayController.onDisplayTopologyChanged(displayController.mDisplayManager.getDisplayTopology());
                    }
                } catch (RemoteException unused) {
                    throw new RuntimeException("Unable to register display controller");
                }
            }
        }, this);
    }

    public final void addDisplayChangingController(DisplayChangeController.OnDisplayChangingListener onDisplayChangingListener) {
        this.mChangeController.mDisplayChangeListener.add(onDisplayChangingListener);
    }

    public final void addDisplayWindowListener(OnDisplaysChangedListener onDisplaysChangedListener, int i) {
        synchronized (this.mDisplays) {
            try {
                if (this.mDisplayChangedListeners.contains(onDisplaysChangedListener)) {
                    return;
                }
                this.mDisplayChangedListeners.add(onDisplaysChangedListener);
                for (int i2 = 0; i2 < this.mDisplays.size(); i2++) {
                    if (!CoreRune.MW_CAPTION_BUG_FIX || i == -1) {
                        onDisplaysChangedListener.onDisplayAdded(this.mDisplays.keyAt(i2));
                    } else {
                        int keyAt = this.mDisplays.keyAt(i2);
                        if (keyAt == i) {
                            Slog.w("DisplayController", "addDisplayWindowListener: The display " + i + " is not registered in DisplayManager.");
                        } else {
                            onDisplaysChangedListener.onDisplayAdded(keyAt);
                        }
                    }
                }
                onDisplaysChangedListener.onTopologyChanged(this.mDisplayTopology);
            } catch (Throwable th) {
                throw th;
            }
        }
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

    public final void onDisplayAdded(int i) {
        synchronized (this.mDisplays) {
            try {
                if (this.mDisplays.get(i) != null) {
                    return;
                }
                Display display = this.mDisplayManager.getDisplay(i);
                if (display == null) {
                    return;
                }
                Context createDisplayContext = i == 0 ? this.mContext : this.mContext.createDisplayContext(display);
                int i2 = 0;
                DisplayRecord displayRecord = new DisplayRecord(i, i2);
                DisplayLayout displayLayout = new DisplayLayout(createDisplayContext, display);
                if (((HashMap) this.mUnpopulatedDisplayBounds).containsKey(Integer.valueOf(i))) {
                    displayLayout.mGlobalBoundsDp = (RectF) ((HashMap) this.mUnpopulatedDisplayBounds).get(Integer.valueOf(i));
                }
                displayRecord.mContext = createDisplayContext;
                displayRecord.mDisplayLayout = displayLayout;
                Resources resources = createDisplayContext.getResources();
                displayLayout.mInsetsState = displayRecord.mInsetsState;
                displayLayout.recalcInsets(resources);
                this.mDisplays.put(i, displayRecord);
                while (i2 < this.mDisplayChangedListeners.size()) {
                    ((OnDisplaysChangedListener) this.mDisplayChangedListeners.get(i2)).onDisplayAdded(i);
                    i2++;
                }
                if (i == 0) {
                    ActivityThread.currentActivityThread().getSystemUiContext(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDisplayTopologyChanged(DisplayTopology displayTopology) {
        if (displayTopology == null) {
            return;
        }
        this.mDisplayTopology = displayTopology;
        SparseArray absoluteBounds = displayTopology.getAbsoluteBounds();
        ((HashMap) this.mUnpopulatedDisplayBounds).clear();
        for (int i = 0; i < absoluteBounds.size(); i++) {
            int keyAt = absoluteBounds.keyAt(i);
            DisplayLayout displayLayout = getDisplayLayout(keyAt);
            if (displayLayout == null) {
                Slog.d("DisplayController", "Storing bounds for onDisplayTopologyChanged on unknown display, displayId=" + keyAt);
                ((HashMap) this.mUnpopulatedDisplayBounds).put(Integer.valueOf(keyAt), (RectF) absoluteBounds.valueAt(i));
            } else {
                displayLayout.mGlobalBoundsDp = (RectF) absoluteBounds.valueAt(i);
            }
        }
        for (int i2 = 0; i2 < this.mDisplayChangedListeners.size(); i2++) {
            ((OnDisplaysChangedListener) this.mDisplayChangedListeners.get(i2)).onTopologyChanged(displayTopology);
        }
    }

    public final void removeDisplayWindowListener(OnDisplaysChangedListener onDisplaysChangedListener) {
        synchronized (this.mDisplays) {
            this.mDisplayChangedListeners.remove(onDisplaysChangedListener);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnDisplaysChangedListener {
        default void onDesktopModeEligibleChanged(int i) {
        }

        default void onDisplayAdded(int i) {
        }

        default void onDisplayRemoved(int i) {
        }

        default void onFixedRotationFinished(int i) {
        }

        default void onTopologyChanged(DisplayTopology displayTopology) {
        }

        default void onDisplayConfigurationChanged(int i, Configuration configuration) {
        }

        default void onFixedRotationStarted(int i, int i2) {
        }

        default void onKeepClearAreasChanged(int i, Set set, Set set2) {
        }
    }
}
