package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.view.SemWindowManager;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceStateInteractor {
    public final DeviceStateInteractor$componentCallbacks$1 componentCallbacks;
    public final Context context;
    public CoverTask coverTask;
    public boolean coverTaskCache;
    public final DeviceStateManager deviceStateManager;
    public final DeviceStateInteractor$displayListener$1 displayListener;
    public final DisplayManager displayManager;
    public boolean foldCache;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public final Handler handler;
    public Consumer largeCoverRotationCallback;
    public int lastCoverRotation;
    public int lastRotation;
    public MultimodalTask multimodalTask;
    public final SettingsHelper settingsHelper;
    public final Context windowContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CoverTask extends TaskStackListener {
        public final Consumer callback;

        public CoverTask(Consumer<Object> consumer) {
            this.callback = consumer;
        }

        public final void onTaskFocusChanged(final int i, boolean z) {
            SettingsHelper settingsHelper = DeviceStateInteractor.this.settingsHelper;
            settingsHelper.getClass();
            if ((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && settingsHelper.mItemLists.get("large_cover_screen_navigation").getIntValue() != 0) || !z) {
                return;
            }
            Handler handler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
            final DeviceStateInteractor deviceStateInteractor = DeviceStateInteractor.this;
            handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$CoverTask$onTaskFocusChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z2;
                    Object obj;
                    List tasks = ActivityTaskManager.getInstance().getTasks(3);
                    if (tasks != null) {
                        int i2 = i;
                        Iterator it = tasks.iterator();
                        while (true) {
                            z2 = false;
                            if (!it.hasNext()) {
                                obj = null;
                                break;
                            } else {
                                obj = it.next();
                                if (((ActivityManager.RunningTaskInfo) obj).taskId == i2) {
                                    break;
                                }
                            }
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                        if (runningTaskInfo != null) {
                            DeviceStateInteractor deviceStateInteractor2 = deviceStateInteractor;
                            DeviceStateInteractor.CoverTask coverTask = this;
                            if (runningTaskInfo.displayId == 1 && runningTaskInfo.isCoverLauncherWidgetTask) {
                                z2 = true;
                            }
                            deviceStateInteractor2.coverTaskCache = z2;
                            Consumer consumer = coverTask.callback;
                            if (consumer != null) {
                                consumer.accept(Boolean.valueOf(z2));
                            }
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MultimodalTask extends TaskStackListener {
        public final Consumer callback;

        public MultimodalTask(DeviceStateInteractor deviceStateInteractor, Consumer<Object> consumer) {
            this.callback = consumer;
        }

        public final void onTaskStackChanged() {
            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$MultimodalTask$onTaskStackChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    List tasks = ActivityTaskManager.getInstance().getTasks(2);
                    if (tasks != null) {
                        DeviceStateInteractor.MultimodalTask multimodalTask = DeviceStateInteractor.MultimodalTask.this;
                        if (tasks.size() > 0) {
                            int i = 0;
                            if (!SemPersonaManager.isSecureFolderId(((ActivityManager.RunningTaskInfo) tasks.get(0)).userId) && !SemDualAppManager.isDualAppId(((ActivityManager.RunningTaskInfo) tasks.get(0)).userId)) {
                                i = ((ActivityManager.RunningTaskInfo) tasks.get(0)).userId;
                            }
                            Consumer consumer = multimodalTask.callback;
                            if (consumer != null) {
                                consumer.accept(Integer.valueOf(i));
                            }
                        }
                    }
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.navigationbar.interactor.DeviceStateInteractor$displayListener$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.navigationbar.interactor.DeviceStateInteractor$componentCallbacks$1] */
    public DeviceStateInteractor(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.deviceStateManager = (DeviceStateManager) context.getSystemService(DeviceStateManager.class);
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.displayManager = displayManager;
        this.displayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$displayListener$1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                if (i != 1) {
                    return;
                }
                DisplayInfo displayInfo = new DisplayInfo();
                Display display = DeviceStateInteractor.this.displayManager.getDisplay(i);
                if (display != null) {
                    DeviceStateInteractor deviceStateInteractor = DeviceStateInteractor.this;
                    display.getDisplayInfo(displayInfo);
                    int i2 = displayInfo.rotation;
                    if (deviceStateInteractor.lastRotation != i2) {
                        deviceStateInteractor.lastRotation = i2;
                        NavigationBarController navigationBarController = (NavigationBarController) Dependency.get(NavigationBarController.class);
                        if (navigationBarController != null) {
                            navigationBarController.forceRepositionCoverNavigationBar(i2);
                        }
                    }
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.handler = new Handler(Looper.getMainLooper());
        this.foldCache = SemWindowManager.getInstance().isFolded();
        this.lastRotation = -1;
        Display display = displayManager.getDisplay(1);
        this.windowContext = display != null ? context.createWindowContext(display, 2, null) : null;
        this.componentCallbacks = new ComponentCallbacks() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$componentCallbacks$1
            @Override // android.content.ComponentCallbacks
            public final void onConfigurationChanged(Configuration configuration) {
                int rotation = configuration.windowConfiguration.getRotation();
                DeviceStateInteractor deviceStateInteractor = DeviceStateInteractor.this;
                if (deviceStateInteractor.lastCoverRotation != rotation) {
                    deviceStateInteractor.lastCoverRotation = rotation;
                    Consumer consumer = deviceStateInteractor.largeCoverRotationCallback;
                    if (consumer != null) {
                        consumer.accept(Integer.valueOf(rotation));
                    }
                }
            }

            @Override // android.content.ComponentCallbacks
            public final void onLowMemory() {
            }
        };
    }
}
