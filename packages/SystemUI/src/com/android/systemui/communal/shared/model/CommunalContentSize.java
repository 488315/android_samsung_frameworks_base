package com.android.systemui.communal.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes2.dex */
public interface CommunalContentSize {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static FixedSize toSize(int i) {
            Object next;
            AbstractList abstractList = (AbstractList) FixedSize.$ENTRIES;
            abstractList.getClass();
            AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
            while (true) {
                if (!iteratorImpl.hasNext()) {
                    next = null;
                    break;
                }
                next = iteratorImpl.next();
                if (((FixedSize) next).getSpan() == i) {
                    break;
                }
            }
            FixedSize fixedSize = (FixedSize) next;
            if (fixedSize != null) {
                return fixedSize;
            }
            throw new IllegalArgumentException(i + " is not a valid span size");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class FixedSize implements CommunalContentSize {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ FixedSize[] $VALUES;
        public static final FixedSize FULL;
        public static final FixedSize HALF;
        public static final FixedSize THIRD;
        private final int span;

        static {
            FixedSize fixedSize = new FixedSize("FULL", 0, 6);
            FULL = fixedSize;
            FixedSize fixedSize2 = new FixedSize("HALF", 1, 3);
            HALF = fixedSize2;
            FixedSize fixedSize3 = new FixedSize("THIRD", 2, 2);
            THIRD = fixedSize3;
            FixedSize[] fixedSizeArr = {fixedSize, fixedSize2, fixedSize3};
            $VALUES = fixedSizeArr;
            $ENTRIES = EnumEntriesKt.enumEntries(fixedSizeArr);
        }

        private FixedSize(String str, int i, int i2) {
            this.span = i2;
        }

        public static FixedSize valueOf(String str) {
            return (FixedSize) Enum.valueOf(FixedSize.class, str);
        }

        public static FixedSize[] values() {
            return (FixedSize[]) $VALUES.clone();
        }

        @Override // com.android.systemui.communal.shared.model.CommunalContentSize
        public final int getSpan() {
            return this.span;
        }
    }

    public final class Responsive implements CommunalContentSize {
        public final int span;

        private /* synthetic */ Responsive(int i) {
            this.span = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Responsive m1076boximpl(int i) {
            return new Responsive(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Responsive) {
                return this.span == ((Responsive) obj).span;
            }
            return false;
        }

        @Override // com.android.systemui.communal.shared.model.CommunalContentSize
        public final int getSpan() {
            return this.span;
        }

        public final int hashCode() {
            return Integer.hashCode(this.span);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.span, ")", new StringBuilder("Responsive(span="));
        }
    }

    int getSpan();
}
