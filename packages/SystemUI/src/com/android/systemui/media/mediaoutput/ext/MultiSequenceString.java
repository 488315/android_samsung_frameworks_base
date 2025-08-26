package com.android.systemui.media.mediaoutput.ext;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MultiSequenceString implements CharSequence {
    public static final Companion Companion = new Companion(null);
    public final CharSequence separator;
    public final List texts;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MultiSequenceString(List<? extends CharSequence> list, CharSequence charSequence) {
        this.texts = list;
        this.separator = charSequence;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return '!';
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultiSequenceString)) {
            return false;
        }
        MultiSequenceString multiSequenceString = (MultiSequenceString) obj;
        return Intrinsics.areEqual(this.texts, multiSequenceString.texts) && Intrinsics.areEqual(this.separator, multiSequenceString.separator);
    }

    public final int hashCode() {
        return this.separator.hashCode() + (this.texts.hashCode() * 31);
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
        return "MultiSequenceString(texts=" + this.texts + ", separator=" + ((Object) this.separator) + ")";
    }

    public /* synthetic */ MultiSequenceString(List list, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? " " : str);
    }
}
