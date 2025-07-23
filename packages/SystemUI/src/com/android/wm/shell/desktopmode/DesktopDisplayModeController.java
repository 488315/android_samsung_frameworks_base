package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.view.IWindowManager;
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
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                DesktopDisplayModeController.this.updateDefaultDisplayWindowingMode();
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceChanged(int i) {
                DesktopDisplayModeController.this.updateDefaultDisplayWindowingMode();
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceRemoved(int i) {
                DesktopDisplayModeController.this.updateDefaultDisplayWindowingMode();
            }
        };
        if (DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH.isTrue()) {
            inputManager.registerInputDeviceListener(inputDeviceListener, handler);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x005d, code lost:
    
        if (android.provider.Settings.Global.getInt(r8.context.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getTargetWindowingModeForDefaultDisplay() {
        /*
            r8 = this;
            com.android.wm.shell.shared.desktopmode.DesktopState r0 = r8.desktopState
            com.android.wm.shell.shared.desktopmode.DesktopStateImpl r0 = (com.android.wm.shell.shared.desktopmode.DesktopStateImpl) r0
            r1 = 0
            boolean r2 = r0.isDesktopModeSupportedOnDisplay(r1)
            if (r2 == 0) goto Lc8
            android.window.DesktopExperienceFlags r2 = android.window.DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT
            boolean r2 = r2.isTrue()
            com.android.wm.shell.RootTaskDisplayAreaOrganizer r3 = r8.rootTaskDisplayAreaOrganizer
            if (r2 == 0) goto L51
            int[] r2 = r3.getDisplayIds()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            int r5 = r2.length
            r6 = r1
        L20:
            if (r6 >= r5) goto L30
            r7 = r2[r6]
            if (r7 == 0) goto L2d
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r4.add(r7)
        L2d:
            int r6 = r6 + 1
            goto L20
        L30:
            boolean r2 = r4.isEmpty()
            if (r2 == 0) goto L37
            goto L6f
        L37:
            int r2 = r4.size()
            r5 = r1
        L3c:
            if (r5 >= r2) goto L6f
            java.lang.Object r6 = r4.get(r5)
            int r5 = r5 + 1
            java.lang.Number r6 = (java.lang.Number) r6
            int r6 = r6.intValue()
            boolean r6 = r0.isDesktopModeSupportedOnDisplay(r6)
            if (r6 == 0) goto L3c
            goto L5f
        L51:
            android.content.Context r0 = r8.context
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "force_desktop_mode_on_external_displays"
            int r0 = android.provider.Settings.Global.getInt(r0, r2, r1)
            if (r0 == 0) goto L6f
        L5f:
            int[] r0 = r3.getDisplayIds()
            int r2 = r0.length
            r3 = r1
        L65:
            if (r3 >= r2) goto L6f
            r4 = r0[r3]
            if (r4 == 0) goto L6c
            goto Lc0
        L6c:
            int r3 = r3 + 1
            goto L65
        L6f:
            android.window.DesktopExperienceFlags r0 = android.window.DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH
            boolean r0 = r0.isTrue()
            if (r0 == 0) goto Lc8
            android.hardware.input.InputManager r0 = r8.inputManager
            int[] r0 = r0.getInputDeviceIds()
            int r2 = r0.length
            r3 = r1
        L7f:
            if (r3 >= r2) goto Lc8
            r4 = r0[r3]
            android.hardware.input.InputManager r5 = r8.inputManager
            android.view.InputDevice r4 = r5.getInputDevice(r4)
            if (r4 == 0) goto Lc5
            r5 = 1048584(0x100008, float:1.469379E-39)
            boolean r5 = r4.supportsSource(r5)
            if (r5 == 0) goto Lc5
            boolean r4 = r4.isEnabled()
            if (r4 == 0) goto Lc5
            android.hardware.input.InputManager r0 = r8.inputManager
            int[] r0 = r0.getInputDeviceIds()
            int r2 = r0.length
            r3 = r1
        La2:
            if (r3 >= r2) goto Lc8
            r4 = r0[r3]
            android.hardware.input.InputManager r5 = r8.inputManager
            android.view.InputDevice r4 = r5.getInputDevice(r4)
            if (r4 == 0) goto Lc2
            boolean r5 = r4.isVirtual()
            if (r5 != 0) goto Lc2
            boolean r5 = r4.isFullKeyboard()
            if (r5 == 0) goto Lc2
            boolean r4 = r4.isEnabled()
            if (r4 == 0) goto Lc2
        Lc0:
            r8 = 5
            return r8
        Lc2:
            int r3 = r3 + 1
            goto La2
        Lc5:
            int r3 = r3 + 1
            goto L7f
        Lc8:
            android.window.DesktopExperienceFlags r0 = android.window.DesktopExperienceFlags.FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH
            boolean r0 = r0.isTrue()
            if (r0 == 0) goto Ld2
            r8 = 1
            return r8
        Ld2:
            android.view.IWindowManager r8 = r8.windowManager
            int r8 = r8.getWindowingMode(r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopDisplayModeController.getTargetWindowingModeForDefaultDisplay():int");
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
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopDisplayModeController", objArr);
        ProtoLog.v(shellProtoLogGroup, "%s: Changing display#%d's windowing mode from %s to %s", m.list.toArray(new Object[m.list.size()]));
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
