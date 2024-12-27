package com.android.systemui.volume.panel.shared.model;

import kotlin.jvm.internal.Intrinsics;

public interface Result {

    public final class Data implements Result {
        public final Object data;

        public Data(Object obj) {
            this.data = obj;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Data) && Intrinsics.areEqual(this.data, ((Data) obj).data);
        }

        public final int hashCode() {
            Object obj = this.data;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final String toString() {
            return "Data(data=" + this.data + ")";
        }
    }

    public final class Loading implements Result {
    }
}
