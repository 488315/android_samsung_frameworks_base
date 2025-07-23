package com.google.protobuf;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UninitializedMessageException extends RuntimeException {
    private static final long serialVersionUID = -7466929953374883507L;
    private final List<String> missingFields;

    public UninitializedMessageException(MessageLite messageLite) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.missingFields = null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UninitializedMessageException(java.util.List<java.lang.String> r6) {
        /*
            r5 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Message missing required fields: "
            r0.<init>(r1)
            java.util.Iterator r1 = r6.iterator()
            r2 = 1
        Lc:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L25
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            if (r2 == 0) goto L1c
            r2 = 0
            goto L21
        L1c:
            java.lang.String r4 = ", "
            r0.append(r4)
        L21:
            r0.append(r3)
            goto Lc
        L25:
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            r5.missingFields = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.UninitializedMessageException.<init>(java.util.List):void");
    }
}
