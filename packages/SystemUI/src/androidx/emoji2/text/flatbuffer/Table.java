package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Table {
    public ByteBuffer bb;
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
            return this.bb.getShort(this.vtable_start + i);
        }
        return 0;
    }
}
