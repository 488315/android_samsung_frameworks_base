package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TypefaceEmojiSpan extends EmojiSpan {
    public TypefaceEmojiSpan(EmojiMetadata emojiMetadata) {
        super(emojiMetadata);
    }

    @Override // android.text.style.ReplacementSpan
    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        EmojiCompat.get().getClass();
        EmojiMetadata emojiMetadata = this.mMetadata;
        MetadataRepo metadataRepo = emojiMetadata.mMetadataRepo;
        Typeface typeface = metadataRepo.mTypeface;
        Typeface typeface2 = paint.getTypeface();
        paint.setTypeface(typeface);
        canvas.drawText(metadataRepo.mEmojiCharArray, emojiMetadata.mIndex * 2, 2, f, i4, paint);
        paint.setTypeface(typeface2);
    }
}
