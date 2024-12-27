package com.android.server.enterprise.auditlog.eventsdata.types;

import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EventPrivacy {
    public final int privacy;
    public String redactedMessage;
    public int userId;

    public EventPrivacy(int i) {
        this.privacy = i;
    }

    public static int parseInteger(String str) {
        if (TextUtils.isEmpty(str)) {
            return -10000;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e("EventPrivacy", "Failed to parse integer", e);
            return -10000;
        }
    }
}
