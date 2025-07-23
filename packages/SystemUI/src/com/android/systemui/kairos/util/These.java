package com.android.systemui.kairos.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class These {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Both extends These {
        public final Object first;
        public final Object second;

        public Both(Object obj, Object obj2) {
            super(null);
            this.first = obj;
            this.second = obj2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class First extends These {
        public final Object value;

        public First(Object obj) {
            super(null);
            this.value = obj;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Second extends These {
        public final Object value;

        public Second(Object obj) {
            super(null);
            this.value = obj;
        }
    }

    public /* synthetic */ These(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private These() {
    }
}
