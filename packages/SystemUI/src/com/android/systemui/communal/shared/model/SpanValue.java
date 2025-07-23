package com.android.systemui.communal.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface SpanValue {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Fixed implements SpanValue {
        public final int value;

        private /* synthetic */ Fixed(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Fixed m1075boximpl(int i) {
            return new Fixed(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Fixed) {
                return this.value == ((Fixed) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.value, ")", new StringBuilder("Fixed(value="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Responsive implements SpanValue {
        public final int value;

        private /* synthetic */ Responsive(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Responsive m1076boximpl(int i) {
            return new Responsive(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Responsive) {
                return this.value == ((Responsive) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.value, ")", new StringBuilder("Responsive(value="));
        }
    }
}
