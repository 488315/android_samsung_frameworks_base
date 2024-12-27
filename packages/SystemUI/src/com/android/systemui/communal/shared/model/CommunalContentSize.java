package com.android.systemui.communal.shared.model;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class CommunalContentSize {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ CommunalContentSize[] $VALUES;
    public static final Companion Companion;
    public static final CommunalContentSize FULL;
    public static final CommunalContentSize HALF;
    public static final CommunalContentSize THIRD;
    private final int span;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        CommunalContentSize communalContentSize = new CommunalContentSize("FULL", 0, 6);
        FULL = communalContentSize;
        CommunalContentSize communalContentSize2 = new CommunalContentSize("HALF", 1, 3);
        HALF = communalContentSize2;
        CommunalContentSize communalContentSize3 = new CommunalContentSize("THIRD", 2, 2);
        THIRD = communalContentSize3;
        CommunalContentSize[] communalContentSizeArr = {communalContentSize, communalContentSize2, communalContentSize3};
        $VALUES = communalContentSizeArr;
        $ENTRIES = EnumEntriesKt.enumEntries(communalContentSizeArr);
        Companion = new Companion(null);
    }

    private CommunalContentSize(String str, int i, int i2) {
        this.span = i2;
    }

    public static CommunalContentSize valueOf(String str) {
        return (CommunalContentSize) Enum.valueOf(CommunalContentSize.class, str);
    }

    public static CommunalContentSize[] values() {
        return (CommunalContentSize[]) $VALUES.clone();
    }

    public final int getSpan() {
        return this.span;
    }
}
