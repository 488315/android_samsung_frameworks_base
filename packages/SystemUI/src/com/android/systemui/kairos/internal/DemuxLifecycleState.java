package com.android.systemui.kairos.internal;

/* loaded from: classes2.dex */
public interface DemuxLifecycleState {

    public final class Active implements DemuxLifecycleState {
        public final DemuxNode node;

        public Active(DemuxNode demuxNode) {
            this.node = demuxNode;
        }

        public final String toString() {
            return "Active(node=" + this.node + ")";
        }
    }

    public final class Dead implements DemuxLifecycleState {
        public static final Dead INSTANCE = new Dead();

        private Dead() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Dead);
        }

        public final int hashCode() {
            return 560469345;
        }

        public final String toString() {
            return "Dead";
        }
    }

    public final class Inactive implements DemuxLifecycleState {
        public final DemuxActivator spec;

        public Inactive(DemuxActivator demuxActivator) {
            this.spec = demuxActivator;
        }

        public final String toString() {
            return "Inactive";
        }
    }
}
