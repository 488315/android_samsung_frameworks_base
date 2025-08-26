package com.android.systemui.qp.util;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Arrays;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class SubscreenUtil {
    public final Executor mMainExecutor;
    public SubScreenQuickPanelWindowController mSubScreenQuickPanelWindowController;

    public SubscreenUtil(Executor executor) {
        this.mMainExecutor = executor;
    }

    public static void applyRotation(Context context, View view) {
        if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1) {
            view.setRotation(180.0f);
        } else {
            view.setRotation(0.0f);
        }
    }

    public static Display getSubDisplay(Context context) {
        return (Display) Arrays.stream(((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")).filter(new SubscreenUtil$$ExternalSyntheticLambda0()).findFirst().orElse(null);
    }

    public static void sendAnnouncementEvent(Context context, String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        accessibilityEventObtain.getText().clear();
        accessibilityEventObtain.getText().add(str);
        accessibilityEventObtain.setPackageName(context.getPackageName());
        accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
    }

    public final void closeSubscreenPanel() {
        Log.d("SubscreenUtil", "closeSubscreenPanel " + this.mSubScreenQuickPanelWindowController);
        if (this.mSubScreenQuickPanelWindowController != null) {
            this.mMainExecutor.execute(new SubscreenUtil$$ExternalSyntheticLambda1(this, 0));
        }
    }

    public final void showLockscreenOnCoverScreen(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction(str);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 201326592);
        Intent intent2 = new Intent();
        intent2.putExtra("showCoverToast", true);
        intent2.putExtra("runOnCover", true);
        intent2.putExtra("afterKeyguardGone", true);
        intent2.putExtra("ignoreKeyguardState", true);
        keyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent2);
        closeSubscreenPanel();
    }

    public final void startActivity(Context context, String str) {
        Log.d("SubscreenUtil", "Activity starting".concat(str));
        Intent intent = new Intent();
        intent.addFlags(268959744);
        intent.setClassName("com.android.systemui", str);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("Display is="), LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN, "LsRune");
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            if (getSubDisplay(context) == null) {
                Log.d("SubscreenUtil", "No sub display to start activity");
                return;
            }
            activityOptionsMakeBasic.setLaunchDisplayId(getSubDisplay(context).getDisplayId());
        }
        activityOptionsMakeBasic.setForceLaunchWindowingMode(1);
        try {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                SubscreenFlashLightController.getInstance(context).finishFlashLightActivity();
                this.mMainExecutor.execute(new SubscreenUtil$$ExternalSyntheticLambda2());
                new Handler().postDelayed(new SubscreenUtil$$ExternalSyntheticLambda1(this, 1), 100L);
            }
            context.startActivity(intent, activityOptionsMakeBasic.toBundle());
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
