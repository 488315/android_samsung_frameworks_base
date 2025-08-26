package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class MetadataListReader {

    public class ByteBufferReader {
        public final ByteBuffer mByteBuffer;

        public ByteBufferReader(ByteBuffer byteBuffer) {
            this.mByteBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public final void skip(int i) {
            ByteBuffer byteBuffer = this.mByteBuffer;
            byteBuffer.position(byteBuffer.position() + i);
        }
    }

    public class OffsetInfo {
        public final long mStartOffset;

        public OffsetInfo(long j, long j2) {
            this.mStartOffset = j;
        }
    }

    private MetadataListReader() {
    }

    public static MetadataList read(ByteBuffer byteBuffer) throws IOException {
        long j;
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        ByteBufferReader byteBufferReader = new ByteBufferReader(byteBufferDuplicate);
        byteBufferReader.skip(4);
        int i = byteBufferReader.mByteBuffer.getShort() & 65535;
        if (i > 100) {
            throw new IOException("Cannot read metadata.");
        }
        byteBufferReader.skip(6);
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                j = -1;
                break;
            }
            int i3 = byteBufferReader.mByteBuffer.getInt();
            byteBufferReader.skip(4);
            j = byteBufferReader.mByteBuffer.getInt() & 4294967295L;
            byteBufferReader.skip(4);
            if (1835365473 == i3) {
                break;
            }
            i2++;
        }
        if (j != -1) {
            byteBufferReader.skip((int) (j - byteBufferReader.mByteBuffer.position()));
            byteBufferReader.skip(12);
            long j2 = byteBufferReader.mByteBuffer.getInt() & 4294967295L;
            for (int i4 = 0; i4 < j2; i4++) {
                int i5 = byteBufferReader.mByteBuffer.getInt();
                long j3 = byteBufferReader.mByteBuffer.getInt() & 4294967295L;
                long j4 = byteBufferReader.mByteBuffer.getInt() & 4294967295L;
                if (1164798569 == i5 || 1701669481 == i5) {
                    byteBufferDuplicate.position((int) new OffsetInfo(j3 + j, j4).mStartOffset);
                    MetadataList metadataList = new MetadataList();
                    byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN);
                    int iPosition = byteBufferDuplicate.position() + byteBufferDuplicate.getInt(byteBufferDuplicate.position());
                    metadataList.bb = byteBufferDuplicate;
                    metadataList.bb_pos = iPosition;
                    int i6 = iPosition - byteBufferDuplicate.getInt(iPosition);
                    metadataList.vtable_start = i6;
                    metadataList.vtable_size = metadataList.bb.getShort(i6);
                    return metadataList;
                }
            }
        }
        throw new IOException("Cannot read metadata.");
    }
}
