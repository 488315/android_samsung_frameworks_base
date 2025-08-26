package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.provider.Settings;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.window.DesktopExperienceFlags;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopDisplayModeController {
    public final Context context;
    public final DesktopState desktopState;
    public final DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider;
    public final InputManager inputManager;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final Transitions transitions;
    public final IWindowManager windowManager;

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

    public DesktopDisplayModeController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider, InputManager inputManager, DisplayController displayController, Handler handler, DesktopState desktopState) {
        this.context = context;
        this.transitions = transitions;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.windowManager = iWindowManager;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.desktopWallpaperActivityTokenProvider = desktopWallpaperActivityTokenProvider;
        this.inputManager = inputManager;
        this.desktopState = desktopState;
        InputManager.InputDeviceListener inputDeviceListener = new InputManager.InputDeviceListener() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayModeController$inputDeviceListener$1
            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceAdded(int i) {
                this.this$0.updateDefaultDisplayWindowingMode();
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceChanged(int i) {
                this.this$0.updateDefaultDisplayWindowingMode();
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceRemoved(int i) {
                this.this$0.updateDefaultDisplayWindowingMode();
            }
        };
        if (DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH.isTrue()) {
            inputManager.registerInputDeviceListener(inputDeviceListener, handler);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getTargetWindowingModeForDefaultDisplay() {
        DesktopStateImpl desktopStateImpl = (DesktopStateImpl) this.desktopState;
        if (desktopStateImpl.isDesktopModeSupportedOnDisplay(0)) {
            boolean zIsTrue = DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT.isTrue();
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.rootTaskDisplayAreaOrganizer;
            if (zIsTrue) {
                int[] displayIds = rootTaskDisplayAreaOrganizer.getDisplayIds();
                ArrayList arrayList = new ArrayList();
                for (int i : displayIds) {
                    if (i != 0) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        if (desktopStateImpl.isDesktopModeSupportedOnDisplay(((Number) obj).intValue())) {
                            int[] displayIds2 = rootTaskDisplayAreaOrganizer.getDisplayIds();
                            for (int i3 : displayIds2) {
                                if (i3 != 0) {
                                    return 5;
                                }
                            }
                        }
                    }
                }
                if (DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH.isTrue()) {
                    int[] inputDeviceIds = this.inputManager.getInputDeviceIds();
                    int length = inputDeviceIds.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length) {
                            break;
                        }
                        InputDevice inputDevice = this.inputManager.getInputDevice(inputDeviceIds[i4]);
                        if (inputDevice != null && inputDevice.supportsSource(1048584) && inputDevice.isEnabled()) {
                            for (int i5 : this.inputManager.getInputDeviceIds()) {
                                InputDevice inputDevice2 = this.inputManager.getInputDevice(i5);
                                if (inputDevice2 != null && !inputDevice2.isVirtual() && inputDevice2.isFullKeyboard() && inputDevice2.isEnabled()) {
                                    return 5;
                                }
                            }
                        } else {
                            i4++;
                        }
                    }
                }
            } else {
                if (Settings.Global.getInt(this.context.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0) {
                    int[] displayIds22 = rootTaskDisplayAreaOrganizer.getDisplayIds();
                    while (i < r2) {
                    }
                }
                if (DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH.isTrue()) {
                }
            }
        }
        if (DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH.isTrue()) {
            return 1;
        }
        return this.windowManager.getWindowingMode(0);
    }

    public final void updateDefaultDisplayWindowingMode() {
        if (DesktopExperienceFlags.ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING.isTrue()) {
            updateDisplayWindowingMode(0, getTargetWindowingModeForDefaultDisplay());
        }
    }

    public final void updateDisplayWindowingMode(int i, int i2) {
        DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i);
        if (displayAreaInfo == null) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "DisplayAreaInfo of display#", " must be non-null.").toString());
        }
        int windowingMode = displayAreaInfo.configuration.windowConfiguration.getWindowingMode();
        if (windowingMode == i2) {
            return;
        }
        Object[] objArr = {Integer.valueOf(i), WindowConfiguration.windowingModeToString(windowingMode), WindowConfiguration.windowingModeToString(i2)};
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopDisplayModeController", objArr);
        ProtoLog.v(shellProtoLogGroup, "%s: Changing display#%d's windowing mode from %s to %s", spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setWindowingMode(displayAreaInfo.token, i2);
        ArrayList runningTasks = this.shellTaskOrganizer.getRunningTasks(i);
        ArrayList arrayList = new ArrayList();
        int size = runningTasks.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = runningTasks.get(i3);
            i3++;
            if (((ActivityManager.RunningTaskInfo) obj).getActivityType() == 1) {
                arrayList.add(obj);
            }
        }
        int size2 = arrayList.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList.get(i4);
            i4++;
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj2;
            int windowingMode2 = runningTaskInfo.getWindowingMode();
            if (windowingMode2 == windowingMode) {
                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, windowingMode);
            } else if (windowingMode2 == i2) {
                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
            }
        }
        WindowContainerToken token = this.desktopWallpaperActivityTokenProvider.getToken(i);
        if (token != null) {
            windowContainerTransaction.setWindowingMode(token, 1);
        }
        this.transitions.startTransition(6, windowContainerTransaction, null);
    }
}
