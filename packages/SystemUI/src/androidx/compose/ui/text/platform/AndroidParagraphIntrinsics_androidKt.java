package androidx.compose.ui.text.platform;

import androidx.compose.ui.text.EmojiSupportMatch;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidParagraphIntrinsics_androidKt {
    public static final boolean access$getHasEmojiCompat(TextStyle textStyle) {
        PlatformParagraphStyle platformParagraphStyle;
        PlatformTextStyle platformTextStyle = textStyle.platformStyle;
        EmojiSupportMatch m729boximpl = (platformTextStyle == null || (platformParagraphStyle = platformTextStyle.paragraphStyle) == null) ? null : EmojiSupportMatch.m729boximpl(platformParagraphStyle.emojiSupportMatch);
        EmojiSupportMatch.Companion.getClass();
        int i = EmojiSupportMatch.None;
        boolean z = false;
        if (m729boximpl != null && m729boximpl.value == i) {
            z = true;
        }
        return !z;
    }
}
