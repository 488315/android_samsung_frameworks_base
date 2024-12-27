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
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.view.SemWindowManager;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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
    private final SettingsHelper settingsHelper;
    public final Context windowContext;

    public final class CoverTask extends TaskStackListener {
        public final Consumer callback;

        public CoverTask(Consumer<Object> consumer) {
            this.callback = consumer;
        }

        public final void onTaskFocusChanged(final int i, boolean z) {
            if (DeviceStateInteractor.this.getSettingsHelper().isLargeCoverScreenNavigation() || !z) {
                return;
            }
            final DeviceStateInteractor deviceStateInteractor = DeviceStateInteractor.this;
            deviceStateInteractor.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$CoverTask$onTaskFocusChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    Object obj;
                    List tasks = ActivityTaskManager.getInstance().getTasks(3);
                    if (tasks != null) {
                        int i2 = i;
                        Iterator it = tasks.iterator();
                        while (true) {
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
                            boolean z2 = runningTaskInfo.displayId == 1 && runningTaskInfo.isCoverLauncherWidgetTask;
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

    public final class MultimodalTask extends TaskStackListener {
        public final Consumer callback;

        public MultimodalTask(Consumer<Object> consumer) {
            this.callback = consumer;
        }

        public final void onTaskStackChanged() {
            DeviceStateInteractor.this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$MultimodalTask$onTaskStackChanged$1
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.navigationbar.interactor.DeviceStateInteractor$componentCallbacks$1] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.navigationbar.interactor.DeviceStateInteractor$displayListener$1] */
    public DeviceStateInteractor(Context context, SettingsHelper settingsHelper, Handler handler) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.handler = handler;
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
                        NavigationBarController navigationBarController = (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                        if (navigationBarController != null) {
                            ((NavigationBarControllerImpl) navigationBarController).forceRepositionCoverNavigationBar(i2);
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

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
