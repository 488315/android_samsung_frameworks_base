package com.android.systemui.qs.pipeline.dagger;

import com.android.systemui.plugins.qs.QS;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class QSType {
    public static final /* synthetic */ QSType[] $VALUES;
    public static final QSType QQS;
    public static final QSType QS;
    public static final QSType SUBQS;

    static {
        QSType qSType = new QSType(QS.TAG, 0);
        QS = qSType;
        QSType qSType2 = new QSType("QQS", 1);
        QQS = qSType2;
        QSType qSType3 = new QSType("SUBQS", 2);
        SUBQS = qSType3;
        QSType[] qSTypeArr = {qSType, qSType2, qSType3};
        $VALUES = qSTypeArr;
        EnumEntriesKt.enumEntries(qSTypeArr);
    }

    private QSType(String str, int i) {
    }

    public static QSType valueOf(String str) {
        return (QSType) Enum.valueOf(QSType.class, str);
    }

    public static QSType[] values() {
        return (QSType[]) $VALUES.clone();
    }
}
