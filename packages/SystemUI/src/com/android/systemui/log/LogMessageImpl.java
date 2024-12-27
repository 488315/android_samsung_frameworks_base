package com.android.systemui.log;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LogMessageImpl implements LogMessage {
    public static final Factory Factory = new Factory(null);
    public boolean bool1;
    public boolean bool2;
    public boolean bool3;
    public boolean bool4;
    public boolean bool5;
    public double double1;
    public Throwable exception;
    public int int1;
    public int int2;
    public LogLevel level;
    public long long1;
    public long long2;
    public Function1 messagePrinter;
    public String str1;
    public String str2;
    public String str3;
    public String tag;
    public Character tagSeparator;
    public long threadId;
    public long timestamp;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        private Factory() {
        }

        public static LogMessageImpl create() {
            return new LogMessageImpl(LogLevel.DEBUG, "UnknownTag", 0L, LogMessageImplKt.DEFAULT_PRINTER, null, null, null, null, 0, 0, 0L, 0L, 0.0d, false, false, false, false, false, 0L, null);
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LogMessageImpl(LogLevel logLevel, String str, long j, Function1 function1, Throwable th, String str2, String str3, String str4, int i, int i2, long j2, long j3, double d, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, long j4, Character ch) {
        this.level = logLevel;
        this.tag = str;
        this.timestamp = j;
        this.messagePrinter = function1;
        this.exception = th;
        this.str1 = str2;
        this.str2 = str3;
        this.str3 = str4;
        this.int1 = i;
        this.int2 = i2;
        this.long1 = j2;
        this.long2 = j3;
        this.double1 = d;
        this.bool1 = z;
        this.bool2 = z2;
        this.bool3 = z3;
        this.bool4 = z4;
        this.bool5 = z5;
        this.threadId = j4;
        this.tagSeparator = ch;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LogMessageImpl)) {
            return false;
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) obj;
        return this.level == logMessageImpl.level && Intrinsics.areEqual(this.tag, logMessageImpl.tag) && this.timestamp == logMessageImpl.timestamp && Intrinsics.areEqual(this.messagePrinter, logMessageImpl.messagePrinter) && Intrinsics.areEqual(this.exception, logMessageImpl.exception) && Intrinsics.areEqual(this.str1, logMessageImpl.str1) && Intrinsics.areEqual(this.str2, logMessageImpl.str2) && Intrinsics.areEqual(this.str3, logMessageImpl.str3) && this.int1 == logMessageImpl.int1 && this.int2 == logMessageImpl.int2 && this.long1 == logMessageImpl.long1 && this.long2 == logMessageImpl.long2 && Double.compare(this.double1, logMessageImpl.double1) == 0 && this.bool1 == logMessageImpl.bool1 && this.bool2 == logMessageImpl.bool2 && this.bool3 == logMessageImpl.bool3 && this.bool4 == logMessageImpl.bool4 && this.bool5 == logMessageImpl.bool5 && this.threadId == logMessageImpl.threadId && Intrinsics.areEqual(this.tagSeparator, logMessageImpl.tagSeparator);
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final boolean getBool1() {
        return this.bool1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final boolean getBool2() {
        return this.bool2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final boolean getBool3() {
        return this.bool3;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final boolean getBool4() {
        return this.bool4;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final boolean getBool5() {
        return this.bool5;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final double getDouble1() {
        return this.double1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final Throwable getException() {
        return this.exception;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final int getInt1() {
        return this.int1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final int getInt2() {
        return this.int2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final LogLevel getLevel() {
        return this.level;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final long getLong1() {
        return this.long1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final long getLong2() {
        return this.long2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final Function1 getMessagePrinter() {
        return this.messagePrinter;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final String getStr1() {
        return this.str1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final String getStr2() {
        return this.str2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final String getStr3() {
        return this.str3;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final String getTag() {
        return this.tag;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final Character getTagSeparator() {
        return this.tagSeparator;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final long getThreadId() {
        return this.threadId;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final long getTimestamp() {
        return this.timestamp;
    }

    public final int hashCode() {
        int hashCode = (this.messagePrinter.hashCode() + Scale$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.level.hashCode() * 31, 31, this.tag), 31, this.timestamp)) * 31;
        Throwable th = this.exception;
        int hashCode2 = (hashCode + (th == null ? 0 : th.hashCode())) * 31;
        String str = this.str1;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.str2;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.str3;
        int m = Scale$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((Double.hashCode(this.double1) + Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.int2, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.int1, (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31, 31), 31), 31, this.long1), 31, this.long2)) * 31, 31, this.bool1), 31, this.bool2), 31, this.bool3), 31, this.bool4), 31, this.bool5), 31, this.threadId);
        Character ch = this.tagSeparator;
        return m + (ch != null ? ch.hashCode() : 0);
    }

    public final void reset(String str, LogLevel logLevel, long j, Function1 function1, Throwable th) {
        this.level = logLevel;
        this.tag = str;
        this.timestamp = j;
        this.messagePrinter = function1;
        this.exception = th;
        this.str1 = null;
        this.str2 = null;
        this.str3 = null;
        this.int1 = 0;
        this.int2 = 0;
        this.long1 = 0L;
        this.long2 = 0L;
        this.double1 = 0.0d;
        this.bool1 = false;
        this.bool2 = false;
        this.bool3 = false;
        this.bool4 = false;
        this.bool5 = false;
        this.threadId = 0L;
        this.tagSeparator = null;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setBool1(boolean z) {
        this.bool1 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setBool2(boolean z) {
        this.bool2 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setBool3(boolean z) {
        this.bool3 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setBool4(boolean z) {
        this.bool4 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setBool5(boolean z) {
        this.bool5 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setDouble1(double d) {
        this.double1 = d;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setInt1(int i) {
        this.int1 = i;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setInt2(int i) {
        this.int2 = i;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setLong1(long j) {
        this.long1 = j;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setLong2(long j) {
        this.long2 = j;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setStr1(String str) {
        this.str1 = str;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setStr2(String str) {
        this.str2 = str;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setStr3(String str) {
        this.str3 = str;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setTagSeparator(Character ch) {
        this.tagSeparator = ch;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public final void setThreadId(long j) {
        this.threadId = j;
    }

    public final String toString() {
        LogLevel logLevel = this.level;
        String str = this.tag;
        long j = this.timestamp;
        Function1 function1 = this.messagePrinter;
        Throwable th = this.exception;
        String str2 = this.str1;
        String str3 = this.str2;
        String str4 = this.str3;
        int i = this.int1;
        int i2 = this.int2;
        long j2 = this.long1;
        long j3 = this.long2;
        double d = this.double1;
        boolean z = this.bool1;
        boolean z2 = this.bool2;
        boolean z3 = this.bool3;
        boolean z4 = this.bool4;
        boolean z5 = this.bool5;
        long j4 = this.threadId;
        Character ch = this.tagSeparator;
        StringBuilder sb = new StringBuilder("LogMessageImpl(level=");
        sb.append(logLevel);
        sb.append(", tag=");
        sb.append(str);
        sb.append(", timestamp=");
        sb.append(j);
        sb.append(", messagePrinter=");
        sb.append(function1);
        sb.append(", exception=");
        sb.append(th);
        sb.append(", str1=");
        sb.append(str2);
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, ", str2=", str3, ", str3=", str4);
        sb.append(", int1=");
        sb.append(i);
        sb.append(", int2=");
        sb.append(i2);
        sb.append(", long1=");
        sb.append(j2);
        sb.append(", long2=");
        sb.append(j3);
        sb.append(", double1=");
        sb.append(d);
        sb.append(", bool1=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z, ", bool2=", z2, ", bool3=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z3, ", bool4=", z4, ", bool5=");
        sb.append(z5);
        sb.append(", threadId=");
        sb.append(j4);
        sb.append(", tagSeparator=");
        sb.append(ch);
        sb.append(")");
        return sb.toString();
    }
}
