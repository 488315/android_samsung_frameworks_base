package com.samsung.android.sdk.moneta.preference.entity;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RelationType {
    public static final /* synthetic */ RelationType[] $VALUES;
    public static final RelationType UNKNOWN;
    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        RelationType relationType = new RelationType("FAMILY", 0, 1);
        RelationType relationType2 = new RelationType("UNKNOWN", 1, -1);
        UNKNOWN = relationType2;
        RelationType[] relationTypeArr = {relationType, relationType2};
        $VALUES = relationTypeArr;
        EnumEntriesKt.enumEntries(relationTypeArr);
        new Companion(null);
    }

    private RelationType(String str, int i, int i2) {
        this.value = i2;
    }

    public static RelationType valueOf(String str) {
        return (RelationType) Enum.valueOf(RelationType.class, str);
    }

    public static RelationType[] values() {
        return (RelationType[]) $VALUES.clone();
    }
}
