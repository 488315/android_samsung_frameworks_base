package androidx.emoji2.text;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.emoji2.text.flatbuffer.MetadataItem;

/* loaded from: classes.dex */
public final class TypefaceEmojiRasterizer {
    private static final ThreadLocal<MetadataItem> sMetadataItem = new ThreadLocal<>();
    private volatile int mCache = 0;
    private final int mIndex;
    private final MetadataRepo mMetadataRepo;

    TypefaceEmojiRasterizer(MetadataRepo metadataRepo, int i) {
        this.mMetadataRepo = metadataRepo;
        this.mIndex = i;
    }

    private MetadataItem getMetadataItem() {
        ThreadLocal<MetadataItem> threadLocal = sMetadataItem;
        MetadataItem metadataItem = threadLocal.get();
        if (metadataItem == null) {
            metadataItem = new MetadataItem();
            threadLocal.set(metadataItem);
        }
        this.mMetadataRepo.getMetadataList().list(metadataItem, this.mIndex);
        return metadataItem;
    }

    public final void draw(Canvas canvas, float f, float f2, Paint paint) {
        MetadataRepo metadataRepo = this.mMetadataRepo;
        Typeface typeface = metadataRepo.getTypeface();
        Typeface typeface2 = paint.getTypeface();
        paint.setTypeface(typeface);
        canvas.drawText(metadataRepo.getEmojiCharArray(), this.mIndex * 2, 2, f, f2, paint);
        paint.setTypeface(typeface2);
    }

    public final int getCodepointAt(int i) {
        return getMetadataItem().codepoints(i);
    }

    public final int getCodepointsLength() {
        return getMetadataItem().codepointsLength();
    }

    @SuppressLint({"KotlinPropertyAccess"})
    public final int getHasGlyph() {
        return this.mCache & 3;
    }

    public final int getHeight() {
        return getMetadataItem().height();
    }

    public final int getId() {
        return getMetadataItem().m6id();
    }

    public final short getSdkAdded() {
        return getMetadataItem().sdkAdded();
    }

    public final int getWidth() {
        return getMetadataItem().width();
    }

    public final boolean isDefaultEmoji() {
        return getMetadataItem().emojiStyle();
    }

    public final boolean isPreferredSystemRender() {
        return (this.mCache & 4) > 0;
    }

    public final void setExclusion() {
        this.mCache = (this.mCache & 3) | 4;
    }

    @SuppressLint({"KotlinPropertyAccess"})
    public final void setHasGlyph(boolean z) {
        int i = this.mCache & 4;
        this.mCache = z ? i | 2 : i | 1;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        sb.append(Integer.toHexString(getId()));
        sb.append(", codepoints:");
        int codepointsLength = getCodepointsLength();
        for (int i = 0; i < codepointsLength; i++) {
            sb.append(Integer.toHexString(getCodepointAt(i)));
            sb.append(" ");
        }
        return sb.toString();
    }
}
