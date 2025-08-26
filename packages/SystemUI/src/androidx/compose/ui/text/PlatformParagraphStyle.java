package androidx.compose.ui.text;

import androidx.compose.ui.text.EmojiSupportMatch;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PlatformParagraphStyle {
    public static final Companion Companion = new Companion(null);
    public static final PlatformParagraphStyle Default = new PlatformParagraphStyle();
    public final int emojiSupportMatch;
    public final boolean includeFontPadding;

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
        int iHashCode = Boolean.hashCode(this.includeFontPadding) * 31;
        EmojiSupportMatch.Companion companion = EmojiSupportMatch.Companion;
        return Integer.hashCode(this.emojiSupportMatch) + iHashCode;
    }

    public final String toString() {
        return "PlatformParagraphStyle(includeFontPadding=" + this.includeFontPadding + ", emojiSupportMatch=" + ((Object) EmojiSupportMatch.m732toStringimpl(this.emojiSupportMatch)) + ')';
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
    public PlatformParagraphStyle(int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 1) != 0) {
            EmojiSupportMatch.Companion.getClass();
            i = 0;
        }
        this(i, (i2 & 2) != 0 ? false : z, (DefaultConstructorMarker) null);
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
    public PlatformParagraphStyle(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 1) != 0) {
            EmojiSupportMatch.Companion.getClass();
            i = 0;
        }
        this(i, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PlatformParagraphStyle() {
        this(0, (boolean) (0 == true ? 1 : 0), (DefaultConstructorMarker) null);
        EmojiSupportMatch.Companion.getClass();
    }
}
