package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public interface MuxLifecycleState {

    public final class Active implements MuxLifecycleState {
        public final MuxNode node;

        public Active(MuxNode muxNode) {
            this.node = muxNode;
        }

        public final String toString() {
            return "Active(node=" + this.node + ")";
        }
    }

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
