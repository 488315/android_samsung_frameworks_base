package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataItem;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.mRatio = (Math.abs(fontMetricsInt2.descent - fontMetricsInt2.ascent) * 1.0f) / (this.mMetadata.getMetadataItem().__offset(14) != 0 ? r4.f170bb.getShort(r6 + r4.bb_pos) : (short) 0);
        MetadataItem metadataItem = this.mMetadata.getMetadataItem();
        int __offset = metadataItem.__offset(14);
        if (__offset != 0) {
            metadataItem.f170bb.getShort(__offset + metadataItem.bb_pos);
        }
        short s = (short) ((this.mMetadata.getMetadataItem().__offset(12) != 0 ? r3.f170bb.getShort(r4 + r3.bb_pos) : (short) 0) * this.mRatio);
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
