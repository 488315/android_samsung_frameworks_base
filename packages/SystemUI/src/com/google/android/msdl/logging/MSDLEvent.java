package com.google.android.msdl.logging;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MSDLEvent {
    public final String properties;
    public final String timeStamp;
    public final String tokenName;

    public MSDLEvent(String str, String str2, String str3) {
        this.tokenName = str;
        this.properties = str2;
        this.timeStamp = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MSDLEvent)) {
            return false;
        }
        MSDLEvent mSDLEvent = (MSDLEvent) obj;
        return Intrinsics.areEqual(this.tokenName, mSDLEvent.tokenName) && Intrinsics.areEqual(this.properties, mSDLEvent.properties) && Intrinsics.areEqual(this.timeStamp, mSDLEvent.timeStamp);
    }

    public final int hashCode() {
        int hashCode = this.tokenName.hashCode() * 31;
        String str = this.properties;
        return this.timeStamp.hashCode() + ((hashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        return this.timeStamp + " | token: " + this.tokenName + " | properties: " + this.properties;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MSDLEvent(com.google.android.msdl.data.model.MSDLToken r4, com.google.android.msdl.domain.InteractionProperties r5) {
        /*
            r3 = this;
            java.lang.String r4 = r4.name()
            if (r5 == 0) goto Lb
            java.lang.String r5 = r5.toString()
            goto Lc
        Lb:
            r5 = 0
        Lc:
            com.google.android.msdl.logging.MSDLHistoryLogger$Companion r0 = com.google.android.msdl.logging.MSDLHistoryLogger.Companion
            r0.getClass()
            java.text.SimpleDateFormat r0 = com.google.android.msdl.logging.MSDLHistoryLogger.Companion.DATE_FORMAT
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            java.lang.String r0 = r0.format(r1)
            r3.<init>(r4, r5, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.msdl.logging.MSDLEvent.<init>(com.google.android.msdl.data.model.MSDLToken, com.google.android.msdl.domain.InteractionProperties):void");
    }
}
