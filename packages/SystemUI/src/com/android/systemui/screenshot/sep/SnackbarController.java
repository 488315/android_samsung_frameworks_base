package com.android.systemui.screenshot.sep;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda2;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SnackbarController {
    public final DismissedCallback cb;
    public final Context context;
    public final int displayId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DismissedCallback {
    }

    public SnackbarController(Context context, int i, DismissedCallback dismissedCallback) {
        this.context = context;
        this.displayId = i;
        this.cb = dismissedCallback;
    }

    public final void showScreenshotError(final View view, SemScreenshotResult semScreenshotResult) {
        int i = semScreenshotResult.failedReason;
        if ((i & 16) != 0) {
            String string = this.context.getResources().getString(R.string.disallow_screenshots_security_flag_single_window);
            int multiWindowModeStates = new MultiWindowManager().getMultiWindowModeStates(this.displayId);
            if (multiWindowModeStates == 1 || multiWindowModeStates == 2) {
                string = this.context.getResources().getString(R.string.disallow_screenshots_security_flag_multi_window);
            }
            Snackbar makeInternal = Snackbar.makeInternal(null, view, string, -1, -1);
            makeInternal.addCallback(new BaseTransientBottomBar.BaseCallback() { // from class: com.android.systemui.screenshot.sep.SnackbarController$showSnackBarSecurityFlag$1
                @Override // com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
                public final void onDismissed(Object obj) {
                    ((ScreenshotController$$ExternalSyntheticLambda2) SnackbarController.this.cb).f$0.detachSemScreenshotLayoutToWindow();
                    ScreenshotController.mIsSnackBarShowing = false;
                }
            });
            makeInternal.show();
            return;
        }
        if ((i & 32) == 0) {
            ((ScreenshotController$$ExternalSyntheticLambda2) this.cb).f$0.detachSemScreenshotLayoutToWindow();
            ScreenshotController.mIsSnackBarShowing = false;
        } else {
            Snackbar makeInternal2 = Snackbar.makeInternal(null, view, this.context.getResources().getString(R.string.disallow_screenshots_mdm), -1, -1);
            makeInternal2.setAction(this.context.getResources().getString(R.string.view_admin_apps), new View.OnClickListener() { // from class: com.android.systemui.screenshot.sep.SnackbarController$showSnackBarMdm$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SnackbarController snackbarController = SnackbarController.this;
                    Context context = view.getContext();
                    snackbarController.getClass();
                    Intent intent = new Intent();
                    intent.setFlags(872415232);
                    intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.DeviceAdminSettings"));
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("goDeviceAdminSettings: message=", e.getMessage(), "SnackbarController");
                    } catch (IllegalArgumentException e2) {
                        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("goDeviceAdminSettings: message=", e2.getMessage(), "SnackbarController");
                    }
                }
            });
            makeInternal2.addCallback(new BaseTransientBottomBar.BaseCallback() { // from class: com.android.systemui.screenshot.sep.SnackbarController$showSnackBarMdm$2
                @Override // com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
                public final void onDismissed(Object obj) {
                    ((ScreenshotController$$ExternalSyntheticLambda2) SnackbarController.this.cb).f$0.detachSemScreenshotLayoutToWindow();
                    ScreenshotController.mIsSnackBarShowing = false;
                }
            });
            makeInternal2.show();
        }
    }
}
