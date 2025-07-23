package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataItem;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.nio.ByteBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class EmojiMetadata {
    public static final ThreadLocal sMetadataItem = new ThreadLocal();
    public volatile int mHasGlyph = 0;
    public final int mIndex;
    public final MetadataRepo mMetadataRepo;

    public EmojiMetadata(MetadataRepo metadataRepo, int i) {
        this.mMetadataRepo = metadataRepo;
        this.mIndex = i;
    }

    public final int getCodepointAt(int i) {
        MetadataItem metadataItem = getMetadataItem();
        int __offset = metadataItem.__offset(16);
        if (__offset == 0) {
            return 0;
        }
        ByteBuffer byteBuffer = metadataItem.bb;
        int i2 = __offset + metadataItem.bb_pos;
        return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
    }

    public final MetadataItem getMetadataItem() {
        ThreadLocal threadLocal = sMetadataItem;
        MetadataItem metadataItem = (MetadataItem) threadLocal.get();
        if (metadataItem == null) {
            metadataItem = new MetadataItem();
            threadLocal.set(metadataItem);
        }
        MetadataList metadataList = this.mMetadataRepo.mMetadataList;
        int __offset = metadataList.__offset(6);
        if (__offset != 0) {
            int i = __offset + metadataList.bb_pos;
            int i2 = (this.mIndex * 4) + metadataList.bb.getInt(i) + i + 4;
            int i3 = metadataList.bb.getInt(i2) + i2;
            ByteBuffer byteBuffer = metadataList.bb;
            metadataItem.bb = byteBuffer;
            if (byteBuffer != null) {
                metadataItem.bb_pos = i3;
                int i4 = i3 - byteBuffer.getInt(i3);
                metadataItem.vtable_start = i4;
                metadataItem.vtable_size = metadataItem.bb.getShort(i4);
                return metadataItem;
            }
            metadataItem.bb_pos = 0;
            metadataItem.vtable_start = 0;
            metadataItem.vtable_size = 0;
        }
        return metadataItem;
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        MetadataItem metadataItem = getMetadataItem();
        int __offset = metadataItem.__offset(4);
        sb.append(Integer.toHexString(__offset != 0 ? metadataItem.bb.getInt(__offset + metadataItem.bb_pos) : 0));
        sb.append(", codepoints:");
        MetadataItem metadataItem2 = getMetadataItem();
        int __offset2 = metadataItem2.__offset(16);
        if (__offset2 != 0) {
            int i2 = __offset2 + metadataItem2.bb_pos;
            i = metadataItem2.bb.getInt(metadataItem2.bb.getInt(i2) + i2);
        } else {
            i = 0;
        }
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(Integer.toHexString(getCodepointAt(i3)));
            sb.append(" ");
        }
        return sb.toString();
    }
}
