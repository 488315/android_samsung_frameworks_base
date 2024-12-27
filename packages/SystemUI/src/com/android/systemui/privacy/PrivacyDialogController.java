package com.android.systemui.privacy;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.permission.PermissionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PrivacyDialogController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public Dialog dialog;
    public final DialogProvider dialogProvider;
    public final KeyguardStateController keyguardStateController;
    public final LocationManager locationManager;
    public final PrivacyDialogController$onDialogDismissed$1 onDialogDismissed;
    public final PackageManager packageManager;
    public final Lazy panelSplitHepler$delegate;
    public final PermissionManager permissionManager;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public final ShadeInteractor shadeInteractor;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DialogProvider {
    }

    static {
        new Companion(null);
    }

    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, LocationManager locationManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, ShadeInteractor shadeInteractor) {
        this(permissionManager, packageManager, locationManager, privacyItemController, userTracker, activityStarter, executor, executor2, privacyLogger, keyguardStateController, appOpsController, uiEventLogger, shadeInteractor, PrivacyDialogControllerKt.defaultDialogProvider);
    }

    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, LocationManager locationManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, ShadeInteractor shadeInteractor, DialogProvider dialogProvider) {
        this.permissionManager = permissionManager;
        this.packageManager = packageManager;
        this.locationManager = locationManager;
        this.privacyItemController = privacyItemController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.privacyLogger = privacyLogger;
        this.keyguardStateController = keyguardStateController;
        this.appOpsController = appOpsController;
        this.uiEventLogger = uiEventLogger;
        this.shadeInteractor = shadeInteractor;
        this.dialogProvider = dialogProvider;
        this.onDialogDismissed = new PrivacyDialogController$onDialogDismissed$1(this);
        this.panelSplitHepler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.privacy.PrivacyDialogController$panelSplitHepler$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
            }
        });
    }
}
