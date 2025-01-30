package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.content.Intent;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NetworkNameModelKt {
    public static final NetworkNameModel.IntentDerived toNetworkNameModel(Intent intent, String str) {
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.DATA_SPN");
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
