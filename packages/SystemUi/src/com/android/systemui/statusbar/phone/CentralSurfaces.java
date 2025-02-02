package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import android.window.RemoteTransition;
import androidx.lifecycle.LifecycleOwner;
import com.android.wm.shell.transition.Transitions;
import com.android.systemui.Dumpable;
import com.android.systemui.shared.system.RemoteAnimationRunnerCompat;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CentralSurfaces extends Dumpable, LifecycleOwner {
    public static final long[] CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS = {20, 20, 20, 20, 100, 20};
    public static final int[] CAMERA_LAUNCH_GESTURE_VIBRATION_AMPLITUDES = {39, 82, 139, IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber, 0, 127};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyboardShortcutsMessage {
        public final int mDeviceId;

        public KeyboardShortcutsMessage(int i) {
            this.mDeviceId = i;
        }
    }

    static void dumpBarTransitions(PrintWriter printWriter, String str, BarTransitions barTransitions) {
        printWriter.print("  ");
        printWriter.print(str);
        printWriter.print(".BarTransitions.mMode=");
        if (barTransitions != null) {
            printWriter.println(BarTransitions.modeToString(barTransitions.mMode));
        } else {
            printWriter.println("Unknown");
        }
    }

    static Bundle getActivityOptions(int i, RemoteAnimationAdapter remoteAnimationAdapter) {
        ActivityOptions defaultActivityOptions = getDefaultActivityOptions(remoteAnimationAdapter);
        defaultActivityOptions.setLaunchDisplayId(i);
        defaultActivityOptions.setCallerDisplayId(i);
        defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
        return defaultActivityOptions.toBundle();
    }

    static ActivityOptions getDefaultActivityOptions(RemoteAnimationAdapter remoteAnimationAdapter) {
        ActivityOptions makeBasic;
        if (remoteAnimationAdapter == null) {
            makeBasic = ActivityOptions.makeBasic();
        } else if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            IRemoteAnimationRunner runner = remoteAnimationAdapter.getRunner();
            boolean z = RemoteAnimationRunnerCompat.ONE_UI_6_1;
            makeBasic = ActivityOptions.makeRemoteTransition(new RemoteTransition(new RemoteAnimationRunnerCompat.C24951(runner), remoteAnimationAdapter.getCallingApplication(), "SysUILaunch"));
        } else {
            makeBasic = ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter);
        }
        makeBasic.setSplashScreenStyle(0);
        return makeBasic;
    }

    static PackageManager getPackageManagerForUser(int i, Context context) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getPackageManager();
    }

    static String viewInfo(View view) {
        return "[(" + view.getLeft() + "," + view.getTop() + ")(" + view.getRight() + "," + view.getBottom() + ") " + view.getWidth() + "x" + view.getHeight() + "]";
    }

    void setBarStateForTest(int i);

    void updateScrimController();
}
