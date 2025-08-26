package com.android.systemui.kairos.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class These {
    public static final Companion Companion = new Companion(null);

    public final class Both extends These {
        public final Object first;
        public final Object second;

        public Both(Object obj, Object obj2) {
            super(null);
            this.first = obj;
            this.second = obj2;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class First extends These {
        public final Object value;

        public First(Object obj) {
            super(null);
            this.value = obj;
        }
    }

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
