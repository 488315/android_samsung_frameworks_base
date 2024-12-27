package com.android.server.updates;

import android.content.Context;
import android.net.Uri;
import android.provider.Telephony;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class ApnDbInstallReceiver extends ConfigUpdateInstallReceiver {
    public static final Uri UPDATE_APN_DB = Uri.withAppendedPath(Telephony.Carriers.CONTENT_URI, "update_db");

    public ApnDbInstallReceiver() {
        super("/data/misc/apns/", "apns-conf.xml", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final void postInstall(Context context) {
        context.getContentResolver().delete(UPDATE_APN_DB, null, null);
    }
}
