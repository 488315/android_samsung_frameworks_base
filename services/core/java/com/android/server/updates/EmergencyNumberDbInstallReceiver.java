package com.android.server.updates;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class EmergencyNumberDbInstallReceiver extends ConfigUpdateInstallReceiver {
    public EmergencyNumberDbInstallReceiver() {
        super("/data/misc/emergencynumberdb", "emergency_number_db", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final void postInstall(Context context) {
        Slog.i("EmergencyNumberDbInstallReceiver", "Emergency number database is updated in file partition");
        ((TelephonyManager) context.getSystemService("phone")).notifyOtaEmergencyNumberDbInstalled();
    }
}
