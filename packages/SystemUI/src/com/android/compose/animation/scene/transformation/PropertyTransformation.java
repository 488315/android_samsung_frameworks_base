package com.android.compose.animation.scene.transformation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PropertyTransformation extends Transformation {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Property {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Alpha extends Property {
            public static final Alpha INSTANCE = new Alpha();

            private Alpha() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Alpha);
            }

            public final int hashCode() {
                return -907944060;
            }

            public final String toString() {
                return "Alpha";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Offset extends Property {
            public static final Offset INSTANCE = new Offset();

            private Offset() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Offset);
            }

            public final int hashCode() {
                return -1981482195;
            }

            public final String toString() {
                return "Offset";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Scale extends Property {
            public static final Scale INSTANCE = null;

            static {
                new Scale();
            }

            private Scale() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Scale);
            }

            public final int hashCode() {
                return -891603088;
            }

            public final String toString() {
                return "Scale";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Size extends Property {
            public static final Size INSTANCE = null;

            static {
                new Size();
            }

            private Size() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Size);
            }

            public final int hashCode() {
                return -1968417509;
            }

            public final String toString() {
                return "Size";
            }
        }

        public /* synthetic */ Property(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Property() {
        }
    }

    Property getProperty();
}
