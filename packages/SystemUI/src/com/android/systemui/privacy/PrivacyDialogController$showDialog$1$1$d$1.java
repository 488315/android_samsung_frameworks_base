package com.android.systemui.privacy;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class PrivacyDialogController$showDialog$1$1$d$1 extends FunctionReferenceImpl implements Function4 {
    public PrivacyDialogController$showDialog$1$1$d$1(Object obj) {
        super(4, obj, PrivacyDialogController.class, "startActivity", "startActivity(Ljava/lang/String;ILjava/lang/CharSequence;Landroid/content/Intent;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        Dialog dialog;
        String str = (String) obj;
        int intValue = ((Number) obj2).intValue();
        Intent intent = (Intent) obj4;
        final PrivacyDialogController privacyDialogController = (PrivacyDialogController) this.receiver;
        if (intent == null) {
            int i = PrivacyDialogController.$r8$clinit;
            privacyDialogController.getClass();
            intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
            intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            intent.putExtra("android.intent.extra.USER", UserHandle.of(intValue));
        }
        privacyDialogController.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS, intValue, str);
        privacyDialogController.privacyLogger.logStartSettingsActivityFromDialog(intValue, str);
        if (!privacyDialogController.keyguardStateController.isUnlocked() && (dialog = privacyDialogController.dialog) != null) {
            dialog.hide();
        }
        privacyDialogController.activityStarter.startActivity(intent, true, new ActivityStarter.Callback() { // from class: com.android.systemui.privacy.PrivacyDialogController$startActivity$1
            @Override // com.android.systemui.plugins.ActivityStarter.Callback
            public final void onActivityStarted(int i2) {
                boolean isStartResultSuccessful = ActivityManager.isStartResultSuccessful(i2);
                PrivacyDialogController privacyDialogController2 = PrivacyDialogController.this;
                if (isStartResultSuccessful) {
                    Dialog dialog2 = privacyDialogController2.dialog;
                    if (dialog2 != null) {
                        dialog2.dismiss();
                        return;
                    }
                    return;
                }
                Dialog dialog3 = privacyDialogController2.dialog;
                if (dialog3 != null) {
                    dialog3.show();
                }
            }
        });
        return Unit.INSTANCE;
    }
}
