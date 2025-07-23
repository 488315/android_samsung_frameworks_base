package com.android.systemui.kairos.internal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Schedulable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
