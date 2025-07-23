package android.app.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes.dex */
public class DelegatedAdminReceiver extends BroadcastReceiver {
    private static final String TAG = "DelegatedAdminReceiver";

    public String onChoosePrivateKeyAlias(Context context, Intent intent, int i, Uri uri, String str) {
        throw new UnsupportedOperationException("onChoosePrivateKeyAlias should be implemented");
    }

    public void onNetworkLogsAvailable(Context context, Intent intent, long j, int i) {
        throw new UnsupportedOperationException("onNetworkLogsAvailable should be implemented");
    }

    public void onSecurityLogsAvailable(Context context, Intent intent) {
        throw new UnsupportedOperationException("onSecurityLogsAvailable should be implemented");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DeviceAdminReceiver.ACTION_CHOOSE_PRIVATE_KEY_ALIAS.equals(action)) {
            setResultData(onChoosePrivateKeyAlias(context, intent, intent.getIntExtra(DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID, -1), (Uri) intent.getParcelableExtra(DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_URI, Uri.class), intent.getStringExtra(DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_ALIAS)));
            return;
        }
        if (DeviceAdminReceiver.ACTION_NETWORK_LOGS_AVAILABLE.equals(action)) {
            onNetworkLogsAvailable(context, intent, intent.getLongExtra(DeviceAdminReceiver.EXTRA_NETWORK_LOGS_TOKEN, -1L), intent.getIntExtra(DeviceAdminReceiver.EXTRA_NETWORK_LOGS_COUNT, 0));
        } else {
            if (DeviceAdminReceiver.ACTION_SECURITY_LOGS_AVAILABLE.equals(action)) {
                onSecurityLogsAvailable(context, intent);
                return;
            }
            Log.w(TAG, "Unhandled broadcast: " + action);
        }
    }
}
