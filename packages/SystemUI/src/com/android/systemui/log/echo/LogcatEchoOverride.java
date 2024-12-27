package com.android.systemui.log.echo;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LogcatEchoOverride {
    public final LogLevel level;
    public final String name;
    public final EchoOverrideType type;

    public LogcatEchoOverride(EchoOverrideType echoOverrideType, String str, LogLevel logLevel) {
        this.type = echoOverrideType;
        this.name = str;
        this.level = logLevel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LogcatEchoOverride)) {
            return false;
        }
        LogcatEchoOverride logcatEchoOverride = (LogcatEchoOverride) obj;
        return this.type == logcatEchoOverride.type && Intrinsics.areEqual(this.name, logcatEchoOverride.name) && this.level == logcatEchoOverride.level;
    }

    public final int hashCode() {
        return this.level.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.name);
    }

    public final String toString() {
        return "LogcatEchoOverride(type=" + this.type + ", name=" + this.name + ", level=" + this.level + ")";
    }
}
