package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class MetadataItem extends Table {
    public final int codepoints(int i) {
        int __offset = __offset(16);
        if (__offset == 0) {
            return 0;
        }
        ByteBuffer byteBuffer = this.f17bb;
        int i2 = __offset + this.bb_pos;
        return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
    }

    public final int codepointsLength() {
        int __offset = __offset(16);
        if (__offset == 0) {
            return 0;
        }
        int i = __offset + this.bb_pos;
        return this.f17bb.getInt(this.f17bb.getInt(i) + i);
    }

    public final boolean emojiStyle() {
        int __offset = __offset(6);
        return (__offset == 0 || this.f17bb.get(__offset + this.bb_pos) == 0) ? false : true;
    }

    public final short height() {
        int __offset = __offset(14);
        if (__offset != 0) {
            return this.f17bb.getShort(__offset + this.bb_pos);
        }
        return (short) 0;
    }

    /* renamed from: id */
    public final int m6id() {
        int __offset = __offset(4);
        if (__offset != 0) {
            return this.f17bb.getInt(__offset + this.bb_pos);
        }
        return 0;
    }

    public final short sdkAdded() {
        int __offset = __offset(8);
        if (__offset != 0) {
            return this.f17bb.getShort(__offset + this.bb_pos);
        }
        return (short) 0;
    }

    public final short width() {
        int __offset = __offset(12);
        if (__offset != 0) {
            return this.f17bb.getShort(__offset + this.bb_pos);
        }
        return (short) 0;
    }
}
