package com.android.systemui.statusbar.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.WindowManager;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenNotificationIntelligenceStartActivity extends Activity implements CommandQueue.Callbacks {
    public static final String TAG;
    public final SubscreenNotificationController controller;
    public SubscreenNotificationSmartReplyDisclaimerDialog mDialog;
    private final SettingsHelper settingsHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TAG = "SubscreenNotificationIntelligenceStartActivity";
    }

    public SubscreenNotificationIntelligenceStartActivity(SubscreenNotificationController subscreenNotificationController, SettingsHelper settingsHelper) {
        this.controller = subscreenNotificationController;
        this.settingsHelper = settingsHelper;
        Log.d(TAG, "SubscreenNotificationIntelligenceStartActivity()");
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        super.onActivityResult(i, i2, intent);
        String m = HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, i2, "onActivityResult() request: ", ", result: ");
        String str = TAG;
        Log.d(str, m);
        if (i != 10) {
            if (i == 20 && i2 == -1) {
                setAiInfoConfirmed();
                if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && !this.settingsHelper.isSuggestResponsesEnabled() && !this.settingsHelper.isSuggestResponsesUsed()) {
                    showDisclaimerDialog();
                    return;
                }
            }
        } else if (i2 == -1 && (subscreenDeviceModelParent = this.controller.mDeviceModel) != null) {
            getBaseContext();
            if (!subscreenDeviceModelParent.isSamsungAccountLoggedIn()) {
                Log.d(str, "startSamsungAccountSignInPopup()");
                Intent intent2 = new Intent("com.msc.action.samsungaccount.SIGNIN_POPUP");
                intent2.setFlags(603979776);
                intent2.putExtra("client_id", "i5to7wq0er");
                startActivityForResult(intent2, 20);
                return;
            }
            setAiInfoConfirmed();
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && !this.settingsHelper.isSuggestResponsesEnabled() && !this.settingsHelper.isSuggestResponsesUsed()) {
                showDisclaimerDialog();
                return;
            }
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.subscreen_notification_intelligence_start_activity);
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            startActivityForResult(new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"), 10, null);
            return;
        }
        boolean z = false;
        if (bundle != null && bundle.getInt(NetworkAnalyticsConstants.DataPoints.PID) == Process.myPid()) {
            z = true;
        }
        Boolean valueOf = bundle != null ? Boolean.valueOf(bundle.getBoolean("is_dialog_showing")) : null;
        if (!z) {
            startActivityForResult(new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"), 10, null);
        } else if (Intrinsics.areEqual(valueOf, Boolean.TRUE)) {
            showDisclaimerDialog();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog;
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA || (subscreenNotificationSmartReplyDisclaimerDialog = this.mDialog) == null) {
            return;
        }
        AlertDialog alertDialog = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
        if (alertDialog != null ? alertDialog.isShowing() : false) {
            AlertDialog alertDialog2 = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
            if (alertDialog2 != null) {
                alertDialog2.setOnDismissListener(null);
            }
            AlertDialog alertDialog3 = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
            if (alertDialog3 != null) {
                alertDialog3.dismiss();
            }
        }
        subscreenNotificationSmartReplyDisclaimerDialog.alertDialog = null;
        this.mDialog = null;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            bundle.putInt(NetworkAnalyticsConstants.DataPoints.PID, Process.myPid());
            SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog = this.mDialog;
            if (subscreenNotificationSmartReplyDisclaimerDialog != null) {
                AlertDialog alertDialog = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
                bundle.putBoolean("is_dialog_showing", alertDialog != null ? alertDialog.isShowing() : false);
            }
        }
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
    }

    public final void setAiInfoConfirmed() {
        Log.d(TAG, "set ai_info_confirmed to 1");
        this.settingsHelper.setAiInfoConfirmed(true);
    }

    public final void showDisclaimerDialog() {
        SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog = this.mDialog;
        if (subscreenNotificationSmartReplyDisclaimerDialog != null) {
            AlertDialog alertDialog = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
            if (alertDialog != null ? alertDialog.isShowing() : false) {
                AlertDialog alertDialog2 = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
                if (alertDialog2 != null) {
                    alertDialog2.setOnDismissListener(null);
                }
                AlertDialog alertDialog3 = subscreenNotificationSmartReplyDisclaimerDialog.alertDialog;
                if (alertDialog3 != null) {
                    alertDialog3.dismiss();
                }
            }
            subscreenNotificationSmartReplyDisclaimerDialog.alertDialog = null;
            this.mDialog = null;
        }
        SubscreenNotificationSmartReplyDisclaimerDialog subscreenNotificationSmartReplyDisclaimerDialog2 = new SubscreenNotificationSmartReplyDisclaimerDialog(this, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationIntelligenceStartActivity$showDisclaimerDialog$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SubscreenNotificationIntelligenceStartActivity.this.settingsHelper.setSuggestResponsesEnabled(true);
                SubscreenNotificationIntelligenceStartActivity.this.settingsHelper.setSuggestResponsesUsed(true);
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
                SubscreenNotificationIntelligenceStartActivity.this.finish();
            }
        }, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationIntelligenceStartActivity$showDisclaimerDialog$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
                SubscreenNotificationIntelligenceStartActivity.this.finish();
            }
        }, new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationIntelligenceStartActivity$showDisclaimerDialog$3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SubscreenNotificationIntelligenceStartActivity.this.finish();
            }
        });
        this.mDialog = subscreenNotificationSmartReplyDisclaimerDialog2;
        try {
            AlertDialog alertDialog4 = subscreenNotificationSmartReplyDisclaimerDialog2.alertDialog;
            if (alertDialog4 != null) {
                alertDialog4.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            Log.e("SubscreenNotificationSmartReplyDisclaimerDialog", "BadTokenException occurs. The dialog show will be ignored.");
        }
    }
}
