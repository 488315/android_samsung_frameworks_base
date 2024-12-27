package com.android.server.updates;

import android.content.Context;
import android.net.Uri;
import android.provider.Telephony;

public class ApnDbInstallReceiver extends ConfigUpdateInstallReceiver {
    public static final Uri UPDATE_APN_DB =
            Uri.withAppendedPath(Telephony.Carriers.CONTENT_URI, "update_db");

    public ApnDbInstallReceiver() {
        super("/data/misc/apns/", "apns-conf.xml", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final void postInstall(Context context) {
        context.getContentResolver().delete(UPDATE_APN_DB, null, null);
    }
}
