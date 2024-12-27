package com.android.server.enterprise.auditlog.eventsdata.types;

import android.text.TextUtils;
import android.util.Log;

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
