package com.android.server.updates;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.Telephony;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class CarrierIdInstallReceiver extends ConfigUpdateInstallReceiver {
    public CarrierIdInstallReceiver() {
        super("/data/misc/carrierid", "carrier_list.pb", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final void postInstall(Context context) {
        context.getContentResolver()
                .update(
                        Uri.withAppendedPath(Telephony.CarrierId.All.CONTENT_URI, "update_db"),
                        new ContentValues(),
                        null,
                        null);
    }
}
