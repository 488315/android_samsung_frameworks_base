package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
