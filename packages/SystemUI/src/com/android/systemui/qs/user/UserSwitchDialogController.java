package com.android.systemui.qs.user;

import android.content.DialogInterface;
import android.content.Intent;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserSwitchDialogController {
    public static final Intent USER_SETTINGS_INTENT;
    public final ActivityStarter activityStarter;
    public final SystemUIDialog.Factory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final FalsingManager falsingManager;
    public final UiEventLogger uiEventLogger;
    public final Provider userDetailViewAdapterProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DialogShower extends DialogInterface {
    }

    static {
        new Companion(null);
        USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");
    }

    public UserSwitchDialogController(Provider provider, ActivityStarter activityStarter, FalsingManager falsingManager, DialogTransitionAnimator dialogTransitionAnimator, UiEventLogger uiEventLogger, SystemUIDialog.Factory factory) {
        this.userDetailViewAdapterProvider = provider;
        this.activityStarter = activityStarter;
        this.falsingManager = falsingManager;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.uiEventLogger = uiEventLogger;
        this.dialogFactory = factory;
    }
}
