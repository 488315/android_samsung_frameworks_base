package com.android.p038wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.ArraySet;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.DisplayAreaInfo;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.ExecutorUtils;
import com.android.p038wm.shell.common.ExternalInterfaceBinder;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.RemoteCallable;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.desktopmode.DesktopModeController;
import com.android.p038wm.shell.desktopmode.IDesktopMode;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeController implements RemoteCallable, Transitions.TransitionHandler {
    public final Context mContext;
    public final DesktopModeImpl mDesktopModeImpl = new DesktopModeImpl(this, 0);
    public final DesktopModeTaskRepository mDesktopModeTaskRepository;
    public boolean mIsDesktopModeActive;
    public final ShellExecutor mMainExecutor;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public final SettingsObserver mSettingsObserver;
    public final ShellController mShellController;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Transitions mTransitions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DesktopModeImpl implements DesktopMode {
        public /* synthetic */ DesktopModeImpl(DesktopModeController desktopModeController, int i) {
            this();
        }

        @Override // com.android.p038wm.shell.desktopmode.DesktopMode
        public final void addDesktopGestureExclusionRegionListener(Executor executor, EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
            ((HandlerExecutor) DesktopModeController.this.mMainExecutor).execute(new DesktopModeController$DesktopModeImpl$$ExternalSyntheticLambda0(this, edgeBackGestureHandler$$ExternalSyntheticLambda0, executor, 0));
        }

        @Override // com.android.p038wm.shell.desktopmode.DesktopMode
        public final void addVisibleTasksListener(WMShell.C374814 c374814, Executor executor) {
            ((HandlerExecutor) DesktopModeController.this.mMainExecutor).execute(new DesktopModeController$DesktopModeImpl$$ExternalSyntheticLambda0(this, c374814, executor, 1));
        }

        private DesktopModeImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IDesktopModeImpl extends IDesktopMode.Stub implements ExternalInterfaceBinder {
        public DesktopModeController mController;

        public IDesktopModeImpl(DesktopModeController desktopModeController) {
            this.mController = desktopModeController;
        }

        @Override // com.android.p038wm.shell.desktopmode.IDesktopMode
        public final int getVisibleTaskCount(final int i) {
            final int[] iArr = new int[1];
            ExecutorUtils.executeRemoteCallWithTaskPermission(this.mController, "getVisibleTaskCount", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopModeController$IDesktopModeImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    iArr[0] = ((DesktopModeController) obj).mDesktopModeTaskRepository.getVisibleTaskCount(i);
                }
            }, true);
            return iArr[0];
        }

        @Override // com.android.p038wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }

        @Override // com.android.p038wm.shell.desktopmode.IDesktopMode
        public final void showDesktopApps(final int i) {
            ExecutorUtils.executeRemoteCallWithTaskPermission(this.mController, "showDesktopApps", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopModeController$IDesktopModeImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    DesktopModeController desktopModeController = (DesktopModeController) obj;
                    desktopModeController.getClass();
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    desktopModeController.bringDesktopAppsToFront(windowContainerTransaction, i2);
                    if (windowContainerTransaction.isEmpty()) {
                        return;
                    }
                    if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                        desktopModeController.mTransitions.startTransition(0, windowContainerTransaction, null);
                    } else {
                        desktopModeController.mShellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    }
                }
            }, false);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsObserver extends ContentObserver {
        public final Context mContext;
        public final Uri mDesktopModeSetting;
        public final Uri mNewDexSetting;

        public SettingsObserver(Context context, Handler handler) {
            super(handler);
            this.mDesktopModeSetting = Settings.System.getUriFor("desktop_mode");
            this.mNewDexSetting = Settings.System.getUriFor("new_dex");
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mDesktopModeSetting.equals(uri)) {
                if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -933413408, 0, null, null);
                }
                DesktopModeController.this.updateDesktopModeActive(DesktopModeStatus.isActive(this.mContext));
            }
            if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY && this.mNewDexSetting.equals(uri)) {
                DesktopModeController.this.updateDesktopModeActive(DesktopModeStatus.isActive(this.mContext));
            }
        }
    }

    public DesktopModeController(Context context, ShellInit shellInit, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Transitions transitions, DesktopModeTaskRepository desktopModeTaskRepository, Handler handler, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.mTransitions = transitions;
        this.mDesktopModeTaskRepository = desktopModeTaskRepository;
        this.mMainExecutor = shellExecutor;
        this.mSettingsObserver = new SettingsObserver(context, handler);
        if (DesktopModeStatus.IS_SUPPORTED) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final DesktopModeController desktopModeController = DesktopModeController.this;
                    desktopModeController.getClass();
                    if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                        ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1220928955, 0, null, null);
                    }
                    desktopModeController.mShellController.addExternalInterface("extra_shell_desktop_mode", new Supplier() { // from class: com.android.wm.shell.desktopmode.DesktopModeController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            DesktopModeController desktopModeController2 = DesktopModeController.this;
                            desktopModeController2.getClass();
                            return new DesktopModeController.IDesktopModeImpl(desktopModeController2);
                        }
                    }, desktopModeController);
                    DesktopModeController.SettingsObserver settingsObserver = desktopModeController.mSettingsObserver;
                    settingsObserver.mContext.getContentResolver().registerContentObserver(settingsObserver.mDesktopModeSetting, false, settingsObserver, -2);
                    if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY) {
                        settingsObserver.mContext.getContentResolver().registerContentObserver(settingsObserver.mNewDexSetting, false, settingsObserver, -2);
                    }
                    if (DesktopModeStatus.isActive(desktopModeController.mContext)) {
                        desktopModeController.updateDesktopModeActive(true);
                    }
                    desktopModeController.mTransitions.addHandler(desktopModeController);
                }
            }, this);
        }
    }

    public final void bringDesktopAppsToFront(WindowContainerTransaction windowContainerTransaction, int i) {
        ShellTaskOrganizer shellTaskOrganizer;
        DesktopModeTaskRepository desktopModeTaskRepository = this.mDesktopModeTaskRepository;
        ArraySet activeTasks = desktopModeTaskRepository.getActiveTasks(i);
        if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -635035055, 0, null, String.valueOf(activeTasks.size()));
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = activeTasks.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            shellTaskOrganizer = this.mShellTaskOrganizer;
            if (!hasNext) {
                break;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(((Integer) it.next()).intValue());
            if (runningTaskInfo != null) {
                arrayList.add(runningTaskInfo);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        Iterator it2 = shellTaskOrganizer.getRunningTasks(this.mContext.getDisplayId()).iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it2.next();
            if (runningTaskInfo2.getActivityType() == 2) {
                windowContainerTransaction.reorder(runningTaskInfo2.token, true);
                break;
            }
        }
        if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 444275101, 0, null, null);
        }
        final List list = desktopModeTaskRepository.freeformTasksInZOrder;
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.wm.shell.desktopmode.DesktopModeController$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return -list.indexOf(Integer.valueOf(((ActivityManager.RunningTaskInfo) obj).taskId));
            }
        }));
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            windowContainerTransaction.reorder(((ActivityManager.RunningTaskInfo) it3.next()).token, true);
        }
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        if (!DesktopModeStatus.isActive(this.mContext)) {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1510332853, 0, null, null);
            }
            return null;
        }
        if (transitionRequestInfo.getType() != 1 && transitionRequestInfo.getType() != 3) {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 1189106472, 0, null, String.valueOf(WindowManager.transitTypeToString(transitionRequestInfo.getType())));
            }
            return null;
        }
        if (triggerTask == null || triggerTask.getWindowingMode() != 5) {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 255316647, 0, null, null);
            }
            return null;
        }
        if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -2002808516, 0, null, String.valueOf(transitionRequestInfo));
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (!CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY) {
            bringDesktopAppsToFront(windowContainerTransaction, triggerTask.displayId);
        } else if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1471158310, 0, null, null);
        }
        windowContainerTransaction.reorder(triggerTask.token, true);
        return windowContainerTransaction;
    }

    public final void setDisplayAreaWindowingMode(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        DisplayAreaInfo displayAreaInfo = (DisplayAreaInfo) this.mRootTaskDisplayAreaOrganizer.mDisplayAreasInfo.get(i);
        if (displayAreaInfo == null) {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 233541056, 1, null, Long.valueOf(i));
            }
        } else {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -549380958, 21, null, Long.valueOf(i), Long.valueOf(displayAreaInfo.configuration.windowConfiguration.getWindowingMode()), Long.valueOf(i2));
            }
            windowContainerTransaction.setWindowingMode(displayAreaInfo.token, i2);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        return false;
    }

    public void updateDesktopModeActive(boolean z) {
        if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY && z == this.mIsDesktopModeActive) {
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1251802755, 0, null, String.valueOf(z));
        }
        int displayId = this.mContext.getDisplayId();
        ArrayList runningTasks = this.mShellTaskOrganizer.getRunningTasks(displayId);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY) {
            if (z) {
                setDisplayAreaWindowingMode(displayId, 5, windowContainerTransaction);
            } else {
                setDisplayAreaWindowingMode(displayId, 1, windowContainerTransaction);
            }
            for (ActivityManager.RunningTaskInfo runningTaskInfo : ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE, false, false, 0)) {
                int activityType = runningTaskInfo.getActivityType();
                if (activityType != 2 && activityType != 3) {
                    int windowingMode = runningTaskInfo.getWindowingMode();
                    if (windowingMode != 1) {
                        if (windowingMode == 5) {
                            if (z) {
                                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
                                windowContainerTransaction.setAlwaysOnTop(runningTaskInfo.token, false);
                            } else if (runningTaskInfo.isVisible) {
                                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 5);
                                windowContainerTransaction.setAlwaysOnTop(runningTaskInfo.token, true);
                            } else {
                                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
                                windowContainerTransaction.setAlwaysOnTop(runningTaskInfo.token, false);
                                windowContainerTransaction.reorder(runningTaskInfo.token, false);
                                windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
                            }
                        }
                    } else if (z) {
                        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, runningTaskInfo.isVisible ? 1 : 0);
                    } else {
                        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
                    }
                }
            }
            windowContainerTransaction.setTransactionType(4);
            StringBuilder sb = new StringBuilder("new_dex(");
            sb.append(z ? "on" : "off");
            sb.append(")");
            windowContainerTransaction.setDisplayIdForChangeTransition(displayId, sb.toString());
            this.mIsDesktopModeActive = z;
        } else {
            if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1281458586, 0, null, null);
            }
            Iterator it = runningTasks.iterator();
            while (it.hasNext()) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it.next();
                if (runningTaskInfo2.getWindowingMode() == 5 && runningTaskInfo2.getActivityType() == 1) {
                    if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 1730840720, 0, null, String.valueOf(runningTaskInfo2.token), String.valueOf(runningTaskInfo2));
                    }
                    windowContainerTransaction.setWindowingMode(runningTaskInfo2.token, 0);
                }
            }
            if (z) {
                if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -130262671, 0, null, null);
                }
                ArrayList arrayList = new ArrayList();
                Iterator it2 = runningTasks.iterator();
                ActivityManager.RunningTaskInfo runningTaskInfo3 = null;
                while (it2.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) it2.next();
                    if (runningTaskInfo4.getActivityType() == 2) {
                        runningTaskInfo3 = runningTaskInfo4;
                    } else if (runningTaskInfo4.getActivityType() == 1 && runningTaskInfo4.isVisible()) {
                        arrayList.add(runningTaskInfo4);
                    }
                }
                if (runningTaskInfo3 != null) {
                    if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -480115082, 1, null, Long.valueOf(arrayList.size()));
                    }
                    windowContainerTransaction.reorder(runningTaskInfo3.getToken(), true);
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        windowContainerTransaction.reorder(((ActivityManager.RunningTaskInfo) it3.next()).getToken(), true);
                    }
                } else if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -310990440, 0, null, null);
                }
                setDisplayAreaWindowingMode(displayId, 5, windowContainerTransaction);
            } else {
                if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 167766699, 0, null, null);
                }
                Iterator it4 = runningTasks.iterator();
                while (it4.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) it4.next();
                    if (runningTaskInfo5.getActivityType() == 1) {
                        if (ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 736112492, 0, null, String.valueOf(runningTaskInfo5.token), String.valueOf(runningTaskInfo5));
                        }
                        windowContainerTransaction.setBounds(runningTaskInfo5.token, (Rect) null);
                    }
                }
                setDisplayAreaWindowingMode(displayId, 1, windowContainerTransaction);
            }
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTransitions.startTransition(6, windowContainerTransaction, null);
        } else {
            this.mRootTaskDisplayAreaOrganizer.applyTransaction(windowContainerTransaction);
        }
    }
}
