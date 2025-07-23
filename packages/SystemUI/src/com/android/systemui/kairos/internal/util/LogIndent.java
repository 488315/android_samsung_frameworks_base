package com.android.systemui.kairos.internal.util;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogIndent {
    public final int currentLogIndent;

    private /* synthetic */ LogIndent(int i) {
        this.currentLogIndent = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LogIndent m2570boximpl() {
        return new LogIndent(0);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LogIndent) {
            return this.currentLogIndent == ((LogIndent) obj).currentLogIndent;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.currentLogIndent);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.currentLogIndent, ")", new StringBuilder("LogIndent(currentLogIndent="));
    }
}
