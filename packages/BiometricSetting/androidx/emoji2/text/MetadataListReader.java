package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
final class MetadataListReader {

    private static class ByteBufferReader {
        private final ByteBuffer mByteBuffer;

        ByteBufferReader(ByteBuffer byteBuffer) {
            this.mByteBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public final long getPosition() {
            return this.mByteBuffer.position();
        }

        public final int readTag() throws IOException {
            return this.mByteBuffer.getInt();
        }

        public final long readUnsignedInt() throws IOException {
            return this.mByteBuffer.getInt() & 4294967295L;
        }

        public final int readUnsignedShort() throws IOException {
            return this.mByteBuffer.getShort() & 65535;
        }

        public final void skip(int i) throws IOException {
            ByteBuffer byteBuffer = this.mByteBuffer;
            byteBuffer.position(byteBuffer.position() + i);
        }
    }

    private static class OffsetInfo {
        private final long mStartOffset;

        OffsetInfo(long j) {
            this.mStartOffset = j;
        }

        final long getStartOffset() {
            return this.mStartOffset;
        }
    }

    static MetadataList read(ByteBuffer byteBuffer) throws IOException {
        long j;
        ByteBuffer duplicate = byteBuffer.duplicate();
        ByteBufferReader byteBufferReader = new ByteBufferReader(duplicate);
        byteBufferReader.skip(4);
        int readUnsignedShort = byteBufferReader.readUnsignedShort();
        if (readUnsignedShort > 100) {
            throw new IOException("Cannot read metadata.");
        }
        byteBufferReader.skip(6);
        int i = 0;
        while (true) {
            if (i >= readUnsignedShort) {
                j = -1;
                break;
            }
            int readTag = byteBufferReader.readTag();
            byteBufferReader.skip(4);
            j = byteBufferReader.readUnsignedInt();
            byteBufferReader.skip(4);
            if (1835365473 == readTag) {
                break;
            }
            i++;
        }
        if (j != -1) {
            byteBufferReader.skip((int) (j - byteBufferReader.getPosition()));
            byteBufferReader.skip(12);
            long readUnsignedInt = byteBufferReader.readUnsignedInt();
            for (int i2 = 0; i2 < readUnsignedInt; i2++) {
                int readTag2 = byteBufferReader.readTag();
                long readUnsignedInt2 = byteBufferReader.readUnsignedInt();
                byteBufferReader.readUnsignedInt();
                if (1164798569 == readTag2 || 1701669481 == readTag2) {
                    duplicate.position((int) new OffsetInfo(readUnsignedInt2 + j).getStartOffset());
                    return MetadataList.getRootAsMetadataList(duplicate);
                }
            }
        }
        throw new IOException("Cannot read metadata.");
    }
}
