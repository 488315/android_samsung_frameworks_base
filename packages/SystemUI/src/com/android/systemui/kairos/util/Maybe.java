package com.android.systemui.kairos.util;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface Maybe {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Absent implements Maybe {
        public static final Absent INSTANCE = new Absent();

        private Absent() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Absent);
        }

        public final int hashCode() {
            return -1387036993;
        }

        public final String toString() {
            return "Absent";
        }
    }

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final Absent absent = Absent.INSTANCE;

        private Companion() {
        }
    }

    public final class Present implements Maybe {
        public final Object value;

        private /* synthetic */ Present(Object obj) {
            this.value = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Present m2590boximpl(Object obj) {
            return new Present(obj);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Present) {
                return Intrinsics.areEqual(this.value, ((Present) obj).value);
            }
            return false;
        }

        public final int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final String toString() {
            return "Present(value=" + this.value + ")";
        }
    }
}
