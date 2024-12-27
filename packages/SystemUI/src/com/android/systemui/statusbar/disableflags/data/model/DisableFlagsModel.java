package com.android.systemui.statusbar.disableflags.data.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DisableFlagsModel {
    public final int disable1;
    public final int disable2;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DisableFlagsModel() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel.<init>():void");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisableFlagsModel)) {
            return false;
        }
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) obj;
        return this.disable1 == disableFlagsModel.disable1 && this.disable2 == disableFlagsModel.disable2;
    }

    public final int hashCode() {
        return Integer.hashCode(this.disable2) + (Integer.hashCode(this.disable1) * 31);
    }

    public final void logChange(LogBuffer logBuffer, final DisableFlagsLogger disableFlagsLogger) {
        LogMessage obtain = logBuffer.obtain("DisableFlagsModel", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel$logChange$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return DisableFlagsLogger.this.getDisableFlagsString(new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), null);
            }
        }, null);
        ((LogMessageImpl) obtain).int1 = this.disable1;
        ((LogMessageImpl) obtain).int2 = this.disable2;
        logBuffer.commit(obtain);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DisableFlagsModel(disable1=");
        sb.append(this.disable1);
        sb.append(", disable2=");
        return Anchor$$ExternalSyntheticOutline0.m(this.disable2, ")", sb);
    }

    public DisableFlagsModel(int i, int i2) {
        this.disable1 = i;
        this.disable2 = i2;
    }

    public /* synthetic */ DisableFlagsModel(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
