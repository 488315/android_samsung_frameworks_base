package com.android.systemui.communal.posturing.data.model;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class PositionState {
    public final OrientationState orientation;
    public final StationaryState stationary;

    public interface OrientationState {

        public final class Postured implements OrientationState {
            public final float confidence;

            public Postured(float f) {
                this.confidence = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Postured) && Float.compare(this.confidence, ((Postured) obj).confidence) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.confidence);
            }

            public final String toString() {
                return "Postured(confidence=" + this.confidence + ")";
            }
        }

        public final class Unknown implements OrientationState {
            public static final Unknown INSTANCE = new Unknown();

            private Unknown() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Unknown);
            }

            public final int hashCode() {
                return -953835740;
            }

            public final String toString() {
                return C2paManifestList.UNKNOWN_VALUE;
            }
        }
    }

    public interface StationaryState {

        public final class Stationary implements StationaryState {
            public final float confidence;

            public Stationary(float f) {
                this.confidence = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Stationary) && Float.compare(this.confidence, ((Stationary) obj).confidence) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.confidence);
            }

            public final String toString() {
                return "Stationary(confidence=" + this.confidence + ")";
            }
        }

        public final class Unknown implements StationaryState {
            public static final Unknown INSTANCE = new Unknown();

            private Unknown() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Unknown);
            }

            public final int hashCode() {
                return 406155090;
            }

            public final String toString() {
                return C2paManifestList.UNKNOWN_VALUE;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PositionState() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PositionState)) {
            return false;
        }
        PositionState positionState = (PositionState) obj;
        return Intrinsics.areEqual(this.stationary, positionState.stationary) && Intrinsics.areEqual(this.orientation, positionState.orientation);
    }

    public final int hashCode() {
        return this.orientation.hashCode() + (this.stationary.hashCode() * 31);
    }

    public final String toString() {
        return "PositionState(stationary=" + this.stationary + ", orientation=" + this.orientation + ")";
    }

    public PositionState(StationaryState stationaryState, OrientationState orientationState) {
        this.stationary = stationaryState;
        this.orientation = orientationState;
    }

    public /* synthetic */ PositionState(StationaryState stationaryState, OrientationState orientationState, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? StationaryState.Unknown.INSTANCE : stationaryState, (i & 2) != 0 ? OrientationState.Unknown.INSTANCE : orientationState);
    }
}
