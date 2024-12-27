package com.android.systemui.privacy;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.permission.PermissionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PrivacyDialogControllerV2 {
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public final DialogProvider dialogProvider;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final KeyguardStateController keyguardStateController;
    public final LocationManager locationManager;
    public final PrivacyDialogControllerV2$onDialogDismissed$1 onDialogDismissed;
    public final PackageManager packageManager;
    public final PermissionManager permissionManager;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DialogProvider {
    }

    public PrivacyDialogControllerV2(PermissionManager permissionManager, PackageManager packageManager, LocationManager locationManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator) {
        this(permissionManager, packageManager, locationManager, privacyItemController, userTracker, activityStarter, executor, executor2, privacyLogger, keyguardStateController, appOpsController, uiEventLogger, dialogTransitionAnimator, PrivacyDialogControllerV2Kt.defaultDialogProvider);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.privacy.PrivacyDialogControllerV2$onDialogDismissed$1] */
    public PrivacyDialogControllerV2(PermissionManager permissionManager, PackageManager packageManager, LocationManager locationManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator, DialogProvider dialogProvider) {
        this.activityStarter = activityStarter;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.onDialogDismissed = new Object(this) { // from class: com.android.systemui.privacy.PrivacyDialogControllerV2$onDialogDismissed$1
            public final /* synthetic */ PrivacyDialogControllerV2 this$0;
        };
    }
}
