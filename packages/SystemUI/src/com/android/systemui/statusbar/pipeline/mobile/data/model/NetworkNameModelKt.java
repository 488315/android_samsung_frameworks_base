package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.content.Intent;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;

public abstract class NetworkNameModelKt {
    public static final NetworkNameModel.IntentDerived toNetworkNameModel(Intent intent, String str) {
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
        String stringExtra2 = intent.getStringExtra("android.telephony.extra.DATA_SPN");
        boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
        String stringExtra3 = intent.getStringExtra("android.telephony.extra.PLMN");
        StringBuilder sb = new StringBuilder();
        if (booleanExtra2 && stringExtra3 != null) {
            sb.append(stringExtra3);
        }
        if (booleanExtra && stringExtra != null) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            sb.append(stringExtra);
        }
        if (booleanExtra && stringExtra2 != null) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            sb.append(stringExtra2);
        }
        if (sb.length() > 0) {
            return new NetworkNameModel.IntentDerived(sb.toString());
        }
        return null;
    }
}
