package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataItem;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.nio.ByteBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiMetadata {
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
        ByteBuffer byteBuffer = metadataItem.f170bb;
        int i2 = __offset + metadataItem.bb_pos;
        return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
    }

    public final int getCodepointsLength() {
        MetadataItem metadataItem = getMetadataItem();
        int __offset = metadataItem.__offset(16);
        if (__offset == 0) {
            return 0;
        }
        int i = __offset + metadataItem.bb_pos;
        return metadataItem.f170bb.getInt(metadataItem.f170bb.getInt(i) + i);
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
            int i2 = (this.mIndex * 4) + metadataList.f170bb.getInt(i) + i + 4;
            metadataItem.__reset(metadataList.f170bb.getInt(i2) + i2, metadataList.f170bb);
        }
        return metadataItem;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        MetadataItem metadataItem = getMetadataItem();
        int __offset = metadataItem.__offset(4);
        sb.append(Integer.toHexString(__offset != 0 ? metadataItem.f170bb.getInt(__offset + metadataItem.bb_pos) : 0));
        sb.append(", codepoints:");
        int codepointsLength = getCodepointsLength();
        for (int i = 0; i < codepointsLength; i++) {
            sb.append(Integer.toHexString(getCodepointAt(i)));
            sb.append(" ");
        }
        return sb.toString();
    }
}
