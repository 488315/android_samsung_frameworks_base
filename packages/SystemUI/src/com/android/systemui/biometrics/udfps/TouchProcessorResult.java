package com.android.systemui.biometrics.udfps;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class TouchProcessorResult {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Failure extends TouchProcessorResult {
        public final String reason;

        public Failure() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Failure) && Intrinsics.areEqual(this.reason, ((Failure) obj).reason);
        }

        public final int hashCode() {
            return this.reason.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Failure(reason="), this.reason, ")");
        }

        public Failure(String str) {
            super(null);
            this.reason = str;
        }

        public /* synthetic */ Failure(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? "" : str);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ProcessedTouch extends TouchProcessorResult {
        public final InteractionEvent event;
        public final int pointerOnSensorId;
        public final NormalizedTouchData touchData;

        public ProcessedTouch(InteractionEvent interactionEvent, int i, NormalizedTouchData normalizedTouchData) {
            super(null);
            this.event = interactionEvent;
            this.pointerOnSensorId = i;
            this.touchData = normalizedTouchData;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ProcessedTouch)) {
                return false;
            }
            ProcessedTouch processedTouch = (ProcessedTouch) obj;
            return this.event == processedTouch.event && this.pointerOnSensorId == processedTouch.pointerOnSensorId && Intrinsics.areEqual(this.touchData, processedTouch.touchData);
        }

        public final int hashCode() {
            return this.touchData.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.pointerOnSensorId, this.event.hashCode() * 31, 31);
        }

        public final String toString() {
            return "ProcessedTouch(event=" + this.event + ", pointerOnSensorId=" + this.pointerOnSensorId + ", touchData=" + this.touchData + ")";
        }
    }

    private TouchProcessorResult() {
    }

    public /* synthetic */ TouchProcessorResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
