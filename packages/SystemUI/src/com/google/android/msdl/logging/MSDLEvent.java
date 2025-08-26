package com.google.android.msdl.logging;

import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.domain.InteractionProperties;
import com.google.android.msdl.logging.MSDLHistoryLogger;
import kotlin.jvm.internal.Intrinsics;

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
        int iHashCode = this.tokenName.hashCode() * 31;
        String str = this.properties;
        return this.timeStamp.hashCode() + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        return this.timeStamp + " | token: " + this.tokenName + " | properties: " + this.properties;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MSDLEvent(MSDLToken mSDLToken, InteractionProperties interactionProperties) {
        String strName = mSDLToken.name();
        String string = interactionProperties != null ? interactionProperties.toString() : null;
        MSDLHistoryLogger.Companion.getClass();
        this(strName, string, MSDLHistoryLogger.Companion.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis())));
    }
}
