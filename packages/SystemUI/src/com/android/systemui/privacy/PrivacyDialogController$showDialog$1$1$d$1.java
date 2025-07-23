package com.android.systemui.privacy;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class PrivacyDialogController$showDialog$1$1$d$1 extends FunctionReferenceImpl implements Function4 {
    public PrivacyDialogController$showDialog$1$1$d$1(Object obj) {
        super(4, obj, PrivacyDialogController.class, "startActivity", "startActivity(Ljava/lang/String;ILjava/lang/CharSequence;Landroid/content/Intent;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        PrivacyDialog privacyDialog;
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
        PrivacyLogger privacyLogger = privacyDialogController.privacyLogger;
        privacyLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = privacyLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = intValue;
        logBuffer.commit(obtain);
        if (!privacyDialogController.keyguardStateController.isUnlocked() && (privacyDialog = privacyDialogController.dialog) != null) {
            privacyDialog.hide();
        }
        privacyDialogController.activityStarter.startActivity(intent, true, new ActivityStarter.Callback() { // from class: com.android.systemui.privacy.PrivacyDialogController$startActivity$1
            @Override // com.android.systemui.plugins.ActivityStarter.Callback
            public final void onActivityStarted(int i2) {
                boolean isStartResultSuccessful = ActivityManager.isStartResultSuccessful(i2);
                PrivacyDialogController privacyDialogController2 = PrivacyDialogController.this;
                if (isStartResultSuccessful) {
                    PrivacyDialog privacyDialog2 = privacyDialogController2.dialog;
                    if (privacyDialog2 != null) {
                        privacyDialog2.dismiss();
                        return;
                    }
                    return;
                }
                PrivacyDialog privacyDialog3 = privacyDialogController2.dialog;
                if (privacyDialog3 != null) {
                    privacyDialog3.show();
                }
            }
        });
        return Unit.INSTANCE;
    }
}
