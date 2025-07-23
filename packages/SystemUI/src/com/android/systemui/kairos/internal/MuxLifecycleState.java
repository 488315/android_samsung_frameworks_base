package com.android.systemui.kairos.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MuxLifecycleState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Active implements MuxLifecycleState {
        public final MuxNode node;

        public Active(MuxNode muxNode) {
            this.node = muxNode;
        }

        public final String toString() {
            return "Active(node=" + this.node + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Dead implements MuxLifecycleState {
        public static final Dead INSTANCE = new Dead();

        private Dead() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Dead);
        }

        public final int hashCode() {
            return 1269218528;
        }

        public final String toString() {
            return "Dead";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Inactive implements MuxLifecycleState {
        public final MuxActivator spec;

        public Inactive(MuxActivator muxActivator) {
            this.spec = muxActivator;
        }

        public final String toString() {
            return "Inactive";
        }
    }
}
