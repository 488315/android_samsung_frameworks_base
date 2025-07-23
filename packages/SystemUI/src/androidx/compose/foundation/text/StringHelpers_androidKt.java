package androidx.compose.foundation.text;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class StringHelpers_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if (r0.getLoadState() == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int findFollowingBreak(int r4, java.lang.String r5) {
        /*
            boolean r0 = androidx.emoji2.text.EmojiCompat.isConfigured()
            r1 = 0
            if (r0 == 0) goto L13
            androidx.emoji2.text.EmojiCompat r0 = androidx.emoji2.text.EmojiCompat.get()
            int r2 = r0.getLoadState()
            r3 = 1
            if (r2 != r3) goto L13
            goto L14
        L13:
            r0 = r1
        L14:
            if (r0 == 0) goto L23
            int r0 = r0.getEmojiEnd(r5, r4)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r3 = -1
            if (r0 != r3) goto L22
            goto L23
        L22:
            r1 = r2
        L23:
            if (r1 == 0) goto L2a
            int r4 = r1.intValue()
            return r4
        L2a:
            java.text.BreakIterator r0 = java.text.BreakIterator.getCharacterInstance()
            r0.setText(r5)
            int r4 = r0.following(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.StringHelpers_androidKt.findFollowingBreak(int, java.lang.String):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if (r0.getLoadState() == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int findPrecedingBreak(int r4, java.lang.String r5) {
        /*
            boolean r0 = androidx.emoji2.text.EmojiCompat.isConfigured()
            r1 = 0
            if (r0 == 0) goto L13
            androidx.emoji2.text.EmojiCompat r0 = androidx.emoji2.text.EmojiCompat.get()
            int r2 = r0.getLoadState()
            r3 = 1
            if (r2 != r3) goto L13
            goto L14
        L13:
            r0 = r1
        L14:
            if (r0 == 0) goto L2a
            int r2 = r4 + (-1)
            r3 = 0
            int r2 = java.lang.Math.max(r3, r2)
            int r0 = r0.getEmojiStart(r5, r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r3 = -1
            if (r0 != r3) goto L29
            goto L2a
        L29:
            r1 = r2
        L2a:
            if (r1 == 0) goto L31
            int r4 = r1.intValue()
            return r4
        L31:
            java.text.BreakIterator r0 = java.text.BreakIterator.getCharacterInstance()
            r0.setText(r5)
            int r4 = r0.preceding(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.StringHelpers_androidKt.findPrecedingBreak(int, java.lang.String):int");
    }
}
