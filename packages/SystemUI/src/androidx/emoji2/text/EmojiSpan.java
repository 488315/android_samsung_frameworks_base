package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataItem;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EmojiSpan extends ReplacementSpan {
    public final EmojiMetadata mMetadata;
    public final Paint.FontMetricsInt mTmpFontMetrics = new Paint.FontMetricsInt();
    public float mRatio = 1.0f;

    public EmojiSpan(EmojiMetadata emojiMetadata) {
        Preconditions.checkNotNull(emojiMetadata, "metadata cannot be null");
        this.mMetadata = emojiMetadata;
    }

    @Override // android.text.style.ReplacementSpan
    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        paint.getFontMetricsInt(this.mTmpFontMetrics);
        Paint.FontMetricsInt fontMetricsInt2 = this.mTmpFontMetrics;
        this.mRatio = (Math.abs(fontMetricsInt2.descent - fontMetricsInt2.ascent) * 1.0f) / (this.mMetadata.getMetadataItem().__offset(14) != 0 ? r4.bb.getShort(r6 + r4.bb_pos) : (short) 0);
        MetadataItem metadataItem = this.mMetadata.getMetadataItem();
        int __offset = metadataItem.__offset(14);
        if (__offset != 0) {
            metadataItem.bb.getShort(__offset + metadataItem.bb_pos);
        }
        short s = (short) ((this.mMetadata.getMetadataItem().__offset(12) != 0 ? r3.bb.getShort(r4 + r3.bb_pos) : (short) 0) * this.mRatio);
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt3 = this.mTmpFontMetrics;
            fontMetricsInt.ascent = fontMetricsInt3.ascent;
            fontMetricsInt.descent = fontMetricsInt3.descent;
            fontMetricsInt.top = fontMetricsInt3.top;
            fontMetricsInt.bottom = fontMetricsInt3.bottom;
        }
        return s;
    }
}
