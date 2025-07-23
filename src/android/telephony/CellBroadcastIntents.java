package android.telephony;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Telephony;
import com.android.internal.telephony.PhoneConstants;

@SystemApi
/* loaded from: classes4.dex */
public class CellBroadcastIntents {
    public static final String ACTION_AREA_INFO_UPDATED = "android.telephony.action.AREA_INFO_UPDATED";
    private static final String EXTRA_MESSAGE = "message";
    private static final String LOG_TAG = "CellBroadcastIntents";

    private CellBroadcastIntents() {
    }

    public static void sendSmsCbReceivedBroadcast(Context context, UserHandle userHandle, SmsCbMessage smsCbMessage, BroadcastReceiver broadcastReceiver, Handler handler, int i, int i2) {
        Intent intent = new Intent(Telephony.Sms.Intents.SMS_CB_RECEIVED_ACTION);
        intent.putExtra("message", smsCbMessage);
        putPhoneIdAndSubIdExtra(context, intent, i2);
        if (userHandle != null) {
            context.createContextAsUser(userHandle, 0).sendOrderedBroadcast(intent, Manifest.permission.RECEIVE_SMS, AppOpsManager.OPSTR_RECEIVE_SMS, broadcastReceiver, handler, i, (String) null, (Bundle) null);
        } else {
            context.sendOrderedBroadcast(intent, Manifest.permission.RECEIVE_SMS, AppOpsManager.OPSTR_RECEIVE_SMS, broadcastReceiver, handler, i, (String) null, (Bundle) null);
        }
    }

    private static void putPhoneIdAndSubIdExtra(Context context, Intent intent, int i) {
        int subscriptionId = SubscriptionManager.getSubscriptionId(i);
        if (subscriptionId != -1) {
            intent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, subscriptionId);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subscriptionId);
        }
        intent.putExtra("phone", i);
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
    }
}
