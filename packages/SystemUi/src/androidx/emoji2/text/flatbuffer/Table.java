package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Table {

    /* renamed from: bb */
    public ByteBuffer f170bb;
    public int bb_pos;
    public int vtable_size;
    public int vtable_start;

    public Table() {
        if (Utf8.DEFAULT == null) {
            Utf8.DEFAULT = new Utf8Safe();
        }
    }

    public final int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f170bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public final void __reset(int i, ByteBuffer byteBuffer) {
        this.f170bb = byteBuffer;
        if (byteBuffer == null) {
            this.bb_pos = 0;
            this.vtable_start = 0;
            this.vtable_size = 0;
        } else {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.f170bb.getShort(i2);
        }
    }
}
