package android.telephony;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;

/* loaded from: classes4.dex */
public abstract class VisualVoicemailService extends Service {
    public static final String DATA_PHONE_ACCOUNT_HANDLE = "data_phone_account_handle";
    public static final String DATA_SMS = "data_sms";
    public static final int MSG_ON_CELL_SERVICE_CONNECTED = 1;
    public static final int MSG_ON_SIM_REMOVED = 3;
    public static final int MSG_ON_SMS_RECEIVED = 2;
    public static final int MSG_TASK_ENDED = 4;
    public static final int MSG_TASK_STOPPED = 5;
    public static final String SERVICE_INTERFACE = "android.telephony.VisualVoicemailService";
    private static final String TAG = "VvmService";
    private final Messenger mMessenger = new Messenger(new Handler() { // from class: android.telephony.VisualVoicemailService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) message.getData().getParcelable(VisualVoicemailService.DATA_PHONE_ACCOUNT_HANDLE, PhoneAccountHandle.class);
            VisualVoicemailTask visualVoicemailTask = new VisualVoicemailTask(message.replyTo, message.arg1);
            int i = message.what;
            if (i == 1) {
                VisualVoicemailService.this.onCellServiceConnected(visualVoicemailTask, phoneAccountHandle);
                return;
            }
            if (i == 2) {
                VisualVoicemailService.this.onSmsReceived(visualVoicemailTask, (VisualVoicemailSms) message.getData().getParcelable(VisualVoicemailService.DATA_SMS, VisualVoicemailSms.class));
            } else if (i == 3) {
                VisualVoicemailService.this.onSimRemoved(visualVoicemailTask, phoneAccountHandle);
            } else if (i == 5) {
                VisualVoicemailService.this.onStopped(visualVoicemailTask);
            } else {
                super.handleMessage(message);
            }
        }
    });

    public abstract void onCellServiceConnected(VisualVoicemailTask visualVoicemailTask, PhoneAccountHandle phoneAccountHandle);

    public abstract void onSimRemoved(VisualVoicemailTask visualVoicemailTask, PhoneAccountHandle phoneAccountHandle);

    public abstract void onSmsReceived(VisualVoicemailTask visualVoicemailTask, VisualVoicemailSms visualVoicemailSms);

    public abstract void onStopped(VisualVoicemailTask visualVoicemailTask);

    public static class VisualVoicemailTask {
        private final Messenger mReplyTo;
        private final int mTaskId;

        private VisualVoicemailTask(Messenger messenger, int i) {
            this.mTaskId = i;
            this.mReplyTo = messenger;
        }

        public final void finish() {
            Message messageObtain = Message.obtain();
            try {
                messageObtain.what = 4;
                messageObtain.arg1 = this.mTaskId;
                this.mReplyTo.send(messageObtain);
            } catch (RemoteException unused) {
                Log.e(VisualVoicemailService.TAG, "Cannot send MSG_TASK_ENDED, remote handler no longer exist");
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof VisualVoicemailTask) && this.mTaskId == ((VisualVoicemailTask) obj).mTaskId;
        }

        public int hashCode() {
            return this.mTaskId;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    @SystemApi
    public static final void setSmsFilterSettings(Context context, PhoneAccountHandle phoneAccountHandle, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        int subId = getSubId(context, phoneAccountHandle);
        if (visualVoicemailSmsFilterSettings == null) {
            telephonyManager.disableVisualVoicemailSmsFilter(subId);
        } else {
            telephonyManager.enableVisualVoicemailSmsFilter(subId, visualVoicemailSmsFilterSettings);
        }
    }

    @SystemApi
    public static final void sendVisualVoicemailSms(Context context, PhoneAccountHandle phoneAccountHandle, String str, short s, String str2, PendingIntent pendingIntent) {
        ((TelephonyManager) context.getSystemService(TelephonyManager.class)).sendVisualVoicemailSmsForSubscriber(getSubId(context, phoneAccountHandle), str, s, str2, pendingIntent);
    }

    private static int getSubId(Context context, PhoneAccountHandle phoneAccountHandle) {
        return ((TelephonyManager) context.getSystemService(TelephonyManager.class)).getSubIdForPhoneAccount(((TelecomManager) context.getSystemService(TelecomManager.class)).getPhoneAccount(phoneAccountHandle));
    }
}
