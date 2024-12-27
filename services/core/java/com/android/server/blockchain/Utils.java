package com.android.server.blockchain;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

public abstract class Utils {
    public static void sendSecureUIAbortIntent(Context context) {
        Log.d("com.android.server.blockchain.Utils", "sendSecureUIAbortIntent");
        Intent intent = new Intent();
        intent.setAction("com.qualcomm.qti.services.secureui.action.ACTION_SUI_ABORT_MSG");
        intent.setPackage("com.qualcomm.qti.services.secureui");
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }
}
