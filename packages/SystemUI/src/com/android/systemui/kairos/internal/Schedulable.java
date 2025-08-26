package com.android.systemui.kairos.internal;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface Schedulable {

    public final class M implements Schedulable {
        public final MuxDeferredNode muxMover;

        public M(MuxDeferredNode muxDeferredNode) {
            this.muxMover = muxDeferredNode;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof M) && Intrinsics.areEqual(this.muxMover, ((M) obj).muxMover);
        }

        public final int hashCode() {
            return this.muxMover.hashCode();
        }

        public final String toString() {
            return "M(muxMover=" + this.muxMover + ")";
        }
    }

    public final class N implements Schedulable {
        public final SchedulableNode node;

        public N(SchedulableNode schedulableNode) {
            this.node = schedulableNode;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof N) && Intrinsics.areEqual(this.node, ((N) obj).node);
        }

        public final int hashCode() {
            return this.node.hashCode();
        }

        public final String toString() {
            return "N(node=" + this.node + ")";
        }
    }

    public final class O implements Schedulable {
        public final Output output;

        public O(Output output) {
            this.output = output;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof O) && Intrinsics.areEqual(this.output, ((O) obj).output);
        }

        public final int hashCode() {
            return this.output.hashCode();
        }

        public final String toString() {
            return "O(output=" + this.output + ")";
        }
    }

    public final class S implements Schedulable {
        public final StateSource state;

        public S(StateSource stateSource) {
            this.state = stateSource;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof S) && Intrinsics.areEqual(this.state, ((S) obj).state);
        }

        public final int hashCode() {
            return this.state.hashCode();
        }

        public final String toString() {
            return "S(state=" + this.state + ")";
        }
    }
}
