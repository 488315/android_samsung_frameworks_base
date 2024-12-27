package com.android.systemui.user.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PseudoGridView;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class UserSwitchDialog extends SystemUIDialog {
    public static final Intent USER_SETTINGS_INTENT;
    public static final Intent USER_SETTINGS_KT_TWO_PHONE_INTENT;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");
        USER_SETTINGS_KT_TWO_PHONE_INTENT = new Intent("com.kt.menu.action.KT_TWOPHONE_SETTINGS");
    }

    public UserSwitchDialog(Context context, UserDetailView.Adapter adapter, final UiEventLogger uiEventLogger, final FalsingManager falsingManager, final ActivityStarter activityStarter, final DialogTransitionAnimator dialogTransitionAnimator) {
        super(context, R.style.Theme_SystemUI_Dialog_Alert);
        SystemUIDialog.setShowForAllUsers(this);
        setCanceledOnTouchOutside(true);
        setTitle(R.string.qs_user_switch_dialog_title);
        setPositiveButton(R.string.quick_settings_done, new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.UserSwitchDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                uiEventLogger.log(QSUserSwitcherEvent.QS_USER_DETAIL_CLOSE);
            }
        });
        setNeutralButton(R.string.quick_settings_more_user_settings, new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.UserSwitchDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (FalsingManager.this.isFalseTap(1)) {
                    return;
                }
                uiEventLogger.log(QSUserSwitcherEvent.QS_USER_MORE_SETTINGS);
                DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, this.getButton(-3));
                if (createActivityTransitionController$default == null) {
                    this.dismiss();
                }
                activityStarter.postStartActivityDismissingKeyguard((QpRune.QUICK_MUM_TWO_PHONE && UserManager.supportsMultipleUsers()) ? UserSwitchDialog.USER_SETTINGS_KT_TWO_PHONE_INTENT : UserSwitchDialog.USER_SETTINGS_INTENT, 0, createActivityTransitionController$default);
            }
        }, false);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(81);
        }
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.sec_qs_user_dialog_content, (ViewGroup) null);
        setView(inflate);
        PseudoGridView.ViewGroupAdapterBridge.link((ViewGroup) inflate.findViewById(R.id.grid), adapter);
        adapter.mDialogShower = new DialogShowerImpl(this, dialogTransitionAnimator);
    }
}
