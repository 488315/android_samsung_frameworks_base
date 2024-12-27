package com.android.server.enterprise.auditlog.eventsdata.types;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EventMessage {
    public final String defaultMessage;
    public final int formatType;
    public String message;

    public EventMessage(String str, int i) {
        this.defaultMessage = str;
        this.formatType = i;
    }

    public static EventMessage buildMessage(String str) {
        return new EventMessage(str, 2);
    }

    public final void buildLogMessage(List list) {
        Object[] objArr;
        if (this.message == null || list == null) {
            this.message = "";
            return;
        }
        if (list.isEmpty()) {
            return;
        }
        String str = this.message;
        if (list.isEmpty()) {
            objArr = null;
        } else {
            Object[] objArr2 = new Object[list.size()];
            for (int i = 0; i < list.size(); i++) {
                try {
                    try {
                        try {
                            objArr2[i] = Integer.valueOf((String) list.get(i));
                        } catch (NumberFormatException unused) {
                            objArr2[i] = list.get(i);
                        }
                    } catch (NumberFormatException unused2) {
                        objArr2[i] = Float.valueOf((String) list.get(i));
                    }
                } catch (NumberFormatException unused3) {
                    objArr2[i] = Long.valueOf((String) list.get(i));
                }
            }
            objArr = objArr2;
        }
        this.message = String.format(str, objArr);
    }
}
