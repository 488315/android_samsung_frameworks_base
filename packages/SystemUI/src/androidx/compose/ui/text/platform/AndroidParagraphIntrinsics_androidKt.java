package androidx.compose.ui.text.platform;

import androidx.compose.ui.text.EmojiSupportMatch;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;

/* loaded from: classes.dex */
public abstract class AndroidParagraphIntrinsics_androidKt {
    public static final boolean access$getHasEmojiCompat(TextStyle textStyle) {
        PlatformParagraphStyle platformParagraphStyle;
        PlatformTextStyle platformTextStyle = textStyle.platformStyle;
        EmojiSupportMatch emojiSupportMatchM731boximpl = (platformTextStyle == null || (platformParagraphStyle = platformTextStyle.paragraphStyle) == null) ? null : EmojiSupportMatch.m731boximpl(platformParagraphStyle.emojiSupportMatch);
        EmojiSupportMatch.Companion.getClass();
        int i = EmojiSupportMatch.None;
        boolean z = false;
        if (emojiSupportMatchM731boximpl != null && emojiSupportMatchM731boximpl.value == i) {
            z = true;
        }
        return !z;
    }
}
