package com.android.systemui.privacy;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.permission.PermissionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyDialogController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public Dialog dialog;
    public final DialogProvider dialogProvider;
    public final KeyguardStateController keyguardStateController;
    public int mDialogTranslateX;
    public final PrivacyDialogController$onDialogDismissed$1 onDialogDismissed;
    public final PackageManager packageManager;
    public final PermissionManager permissionManager;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public boolean qsExpanded;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final PrivacyDialogController$shadeQsExpansionListener$1 shadeQsExpansionListener;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DialogProvider {
    }

    static {
        new Companion(null);
    }

    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, ShadeExpansionStateManager shadeExpansionStateManager) {
        this(permissionManager, packageManager, privacyItemController, userTracker, activityStarter, executor, executor2, privacyLogger, keyguardStateController, appOpsController, uiEventLogger, shadeExpansionStateManager, PrivacyDialogControllerKt.defaultDialogProvider);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.privacy.PrivacyDialogController$shadeQsExpansionListener$1] */
    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, ShadeExpansionStateManager shadeExpansionStateManager, DialogProvider dialogProvider) {
        this.permissionManager = permissionManager;
        this.packageManager = packageManager;
        this.privacyItemController = privacyItemController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.privacyLogger = privacyLogger;
        this.keyguardStateController = keyguardStateController;
        this.appOpsController = appOpsController;
        this.uiEventLogger = uiEventLogger;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.dialogProvider = dialogProvider;
        this.onDialogDismissed = new PrivacyDialogController$onDialogDismissed$1(this);
        this.shadeQsExpansionListener = new ShadeQsExpansionListener() { // from class: com.android.systemui.privacy.PrivacyDialogController$shadeQsExpansionListener$1
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                PrivacyDialogController.this.qsExpanded = z;
            }
        };
    }
}
