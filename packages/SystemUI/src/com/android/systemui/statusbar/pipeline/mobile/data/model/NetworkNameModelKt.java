package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.content.Intent;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NetworkNameModelKt {
    public static final NetworkNameModel.IntentDerived toNetworkNameModel(Intent intent, String str) {
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.DATA_SPN");
        if (stringExtra == null || stringExtra.length() == 0) {
            stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
        }
        boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
        String stringExtra2 = intent.getStringExtra("android.telephony.extra.PLMN");
        StringBuilder sb = new StringBuilder();
        if (booleanExtra2 && stringExtra2 != null) {
            sb.append(stringExtra2);
        }
        if (booleanExtra && stringExtra != null) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            sb.append(stringExtra);
        }
        if (sb.length() > 0) {
            return new NetworkNameModel.IntentDerived(sb.toString());
        }
        return null;
    }
}
