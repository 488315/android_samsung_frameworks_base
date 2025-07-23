package androidx.compose.ui.text;

import androidx.compose.ui.text.EmojiSupportMatch;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlatformParagraphStyle {
    public static final Companion Companion = new Companion(null);
    public static final PlatformParagraphStyle Default = new PlatformParagraphStyle();
    public final int emojiSupportMatch;
    public final boolean includeFontPadding;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ PlatformParagraphStyle(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlatformParagraphStyle)) {
            return false;
        }
        PlatformParagraphStyle platformParagraphStyle = (PlatformParagraphStyle) obj;
        if (this.includeFontPadding != platformParagraphStyle.includeFontPadding) {
            return false;
        }
        int i = platformParagraphStyle.emojiSupportMatch;
        EmojiSupportMatch.Companion companion = EmojiSupportMatch.Companion;
        return this.emojiSupportMatch == i;
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.includeFontPadding) * 31;
        EmojiSupportMatch.Companion companion = EmojiSupportMatch.Companion;
        return Integer.hashCode(this.emojiSupportMatch) + hashCode;
    }

    public final String toString() {
        return "PlatformParagraphStyle(includeFontPadding=" + this.includeFontPadding + ", emojiSupportMatch=" + ((Object) EmojiSupportMatch.m730toStringimpl(this.emojiSupportMatch)) + ')';
    }

    public /* synthetic */ PlatformParagraphStyle(int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z);
    }

    public PlatformParagraphStyle(boolean z) {
        this.includeFontPadding = z;
        EmojiSupportMatch.Companion.getClass();
        this.emojiSupportMatch = 0;
    }

    public /* synthetic */ PlatformParagraphStyle(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PlatformParagraphStyle(int r2, boolean r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r1 = this;
            r5 = r4 & 1
            r0 = 0
            if (r5 == 0) goto Lb
            androidx.compose.ui.text.EmojiSupportMatch$Companion r2 = androidx.compose.ui.text.EmojiSupportMatch.Companion
            r2.getClass()
            r2 = r0
        Lb:
            r4 = r4 & 2
            if (r4 == 0) goto L10
            r3 = r0
        L10:
            r4 = 0
            r1.<init>(r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.PlatformParagraphStyle.<init>(int, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private PlatformParagraphStyle(int i, boolean z) {
        this.includeFontPadding = z;
        this.emojiSupportMatch = i;
    }

    private PlatformParagraphStyle(int i) {
        this.includeFontPadding = false;
        this.emojiSupportMatch = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PlatformParagraphStyle(int r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
        /*
            r0 = this;
            r2 = r2 & 1
            if (r2 == 0) goto La
            androidx.compose.ui.text.EmojiSupportMatch$Companion r1 = androidx.compose.ui.text.EmojiSupportMatch.Companion
            r1.getClass()
            r1 = 0
        La:
            r2 = 0
            r0.<init>(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.PlatformParagraphStyle.<init>(int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PlatformParagraphStyle() {
        this(0, (boolean) (0 == true ? 1 : 0), (DefaultConstructorMarker) null);
        EmojiSupportMatch.Companion.getClass();
    }
}
