package androidx.compose.foundation.text;

import androidx.emoji2.text.EmojiCompat;
import java.text.BreakIterator;

/* loaded from: classes.dex */
public abstract class StringHelpers_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int findFollowingBreak(int i, String str) {
        EmojiCompat emojiCompat;
        Integer num = null;
        if (EmojiCompat.isConfigured()) {
            emojiCompat = EmojiCompat.get();
            if (emojiCompat.getLoadState() != 1) {
                emojiCompat = null;
            }
        }
        if (emojiCompat != null) {
            int emojiEnd = emojiCompat.getEmojiEnd(str, i);
            Integer numValueOf = Integer.valueOf(emojiEnd);
            if (emojiEnd != -1) {
                num = numValueOf;
            }
        }
        if (num != null) {
            return num.intValue();
        }
        BreakIterator characterInstance = BreakIterator.getCharacterInstance();
        characterInstance.setText(str);
        return characterInstance.following(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int findPrecedingBreak(int i, String str) {
        EmojiCompat emojiCompat;
        Integer num = null;
        if (EmojiCompat.isConfigured()) {
            emojiCompat = EmojiCompat.get();
            if (emojiCompat.getLoadState() != 1) {
                emojiCompat = null;
            }
        }
        if (emojiCompat != null) {
            int emojiStart = emojiCompat.getEmojiStart(str, Math.max(0, i - 1));
            Integer numValueOf = Integer.valueOf(emojiStart);
            if (emojiStart != -1) {
                num = numValueOf;
            }
        }
        if (num != null) {
            return num.intValue();
        }
        BreakIterator characterInstance = BreakIterator.getCharacterInstance();
        characterInstance.setText(str);
        return characterInstance.preceding(i);
    }
}
