package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class MetadataList extends Table {
    public static MetadataList getRootAsMetadataList(ByteBuffer byteBuffer) {
        MetadataList metadataList = new MetadataList();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        metadataList.__reset(byteBuffer.position() + byteBuffer.getInt(byteBuffer.position()), byteBuffer);
        return metadataList;
    }

    public final void list(MetadataItem metadataItem, int i) {
        int __offset = __offset(6);
        if (__offset != 0) {
            int i2 = __offset + this.bb_pos;
            int i3 = (i * 4) + this.f17bb.getInt(i2) + i2 + 4;
            metadataItem.__reset(this.f17bb.getInt(i3) + i3, this.f17bb);
        }
    }

    public final int listLength() {
        int __offset = __offset(6);
        if (__offset == 0) {
            return 0;
        }
        int i = __offset + this.bb_pos;
        return this.f17bb.getInt(this.f17bb.getInt(i) + i);
    }

    public final int version() {
        int __offset = __offset(4);
        if (__offset != 0) {
            return this.f17bb.getInt(__offset + this.bb_pos);
        }
        return 0;
    }
}
