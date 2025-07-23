package com.android.systemui.kairos.util;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Maybe {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final Absent absent = Absent.INSTANCE;

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Present implements Maybe {
        public final Object value;

        private /* synthetic */ Present(Object obj) {
            this.value = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Present m2573boximpl(Object obj) {
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
