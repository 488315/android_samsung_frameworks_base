package com.android.systemui.media.mediaoutput.ext;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MultiSequenceString implements CharSequence {
    public static final Companion Companion = new Companion(null);
    public final List texts;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MultiSequenceString(List<? extends CharSequence> list) {
        this.texts = list;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return '!';
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MultiSequenceString) && Intrinsics.areEqual(this.texts, ((MultiSequenceString) obj).texts);
    }

    public final int hashCode() {
        return this.texts.hashCode();
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return 1;
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return "";
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return "MultiSequenceString(texts=" + this.texts + ")";
    }
}
