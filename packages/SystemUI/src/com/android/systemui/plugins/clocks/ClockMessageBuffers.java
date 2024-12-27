package com.android.systemui.plugins.clocks;

import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ClockMessageBuffers {
    private final MessageBuffer infraMessageBuffer;
    private final MessageBuffer largeClockMessageBuffer;
    private final MessageBuffer smallClockMessageBuffer;

    public ClockMessageBuffers(MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3) {
        this.infraMessageBuffer = messageBuffer;
        this.smallClockMessageBuffer = messageBuffer2;
        this.largeClockMessageBuffer = messageBuffer3;
    }

    public static /* synthetic */ ClockMessageBuffers copy$default(ClockMessageBuffers clockMessageBuffers, MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3, int i, Object obj) {
        if ((i & 1) != 0) {
            messageBuffer = clockMessageBuffers.infraMessageBuffer;
        }
        if ((i & 2) != 0) {
            messageBuffer2 = clockMessageBuffers.smallClockMessageBuffer;
        }
        if ((i & 4) != 0) {
            messageBuffer3 = clockMessageBuffers.largeClockMessageBuffer;
        }
        return clockMessageBuffers.copy(messageBuffer, messageBuffer2, messageBuffer3);
    }

    public final MessageBuffer component1() {
        return this.infraMessageBuffer;
    }

    public final MessageBuffer component2() {
        return this.smallClockMessageBuffer;
    }

    public final MessageBuffer component3() {
        return this.largeClockMessageBuffer;
    }

    public final ClockMessageBuffers copy(MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3) {
        return new ClockMessageBuffers(messageBuffer, messageBuffer2, messageBuffer3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockMessageBuffers)) {
            return false;
        }
        ClockMessageBuffers clockMessageBuffers = (ClockMessageBuffers) obj;
        return Intrinsics.areEqual(this.infraMessageBuffer, clockMessageBuffers.infraMessageBuffer) && Intrinsics.areEqual(this.smallClockMessageBuffer, clockMessageBuffers.smallClockMessageBuffer) && Intrinsics.areEqual(this.largeClockMessageBuffer, clockMessageBuffers.largeClockMessageBuffer);
    }

    public final MessageBuffer getInfraMessageBuffer() {
        return this.infraMessageBuffer;
    }

    public final MessageBuffer getLargeClockMessageBuffer() {
        return this.largeClockMessageBuffer;
    }

    public final MessageBuffer getSmallClockMessageBuffer() {
        return this.smallClockMessageBuffer;
    }

    public int hashCode() {
        return this.largeClockMessageBuffer.hashCode() + ((this.smallClockMessageBuffer.hashCode() + (this.infraMessageBuffer.hashCode() * 31)) * 31);
    }

    public String toString() {
        return "ClockMessageBuffers(infraMessageBuffer=" + this.infraMessageBuffer + ", smallClockMessageBuffer=" + this.smallClockMessageBuffer + ", largeClockMessageBuffer=" + this.largeClockMessageBuffer + ")";
    }
}
