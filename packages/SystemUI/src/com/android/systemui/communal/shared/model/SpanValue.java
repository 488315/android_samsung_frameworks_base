package com.android.systemui.communal.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public interface SpanValue {

    public final class Fixed implements SpanValue {
        public final int value;

        private /* synthetic */ Fixed(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Fixed m1077boximpl(int i) {
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

    public final class Responsive implements SpanValue {
        public final int value;

        private /* synthetic */ Responsive(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Responsive m1078boximpl(int i) {
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
