package com.android.systemui.qs.panels.ui.compose.selection;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSDragAnchor {
    public static final /* synthetic */ QSDragAnchor[] $VALUES;
    public static final QSDragAnchor Icon;
    public static final QSDragAnchor Large;

    static {
        QSDragAnchor qSDragAnchor = new QSDragAnchor("Icon", 0);
        Icon = qSDragAnchor;
        QSDragAnchor qSDragAnchor2 = new QSDragAnchor("Large", 1);
        Large = qSDragAnchor2;
        QSDragAnchor[] qSDragAnchorArr = {qSDragAnchor, qSDragAnchor2};
        $VALUES = qSDragAnchorArr;
        EnumEntriesKt.enumEntries(qSDragAnchorArr);
    }

    private QSDragAnchor(String str, int i) {
    }

    public static QSDragAnchor valueOf(String str) {
        return (QSDragAnchor) Enum.valueOf(QSDragAnchor.class, str);
    }

    public static QSDragAnchor[] values() {
        return (QSDragAnchor[]) $VALUES.clone();
    }
}
