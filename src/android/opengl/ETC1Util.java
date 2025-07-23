package android.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes3.dex */
public class ETC1Util {
    public static void loadTexture(int i, int i2, int i3, int i4, int i5, InputStream inputStream) throws IOException {
        loadTexture(i, i2, i3, i4, i5, createTexture(inputStream));
    }

    public static void loadTexture(int i, int i2, int i3, int i4, int i5, ETC1Texture eTC1Texture) {
        if (i4 != 6407) {
            throw new IllegalArgumentException("fallbackFormat must be GL_RGB");
        }
        if (i5 != 33635 && i5 != 5121) {
            throw new IllegalArgumentException("Unsupported fallbackType");
        }
        int width = eTC1Texture.getWidth();
        int height = eTC1Texture.getHeight();
        ByteBuffer data = eTC1Texture.getData();
        if (isETC1Supported()) {
            GLES10.glCompressedTexImage2D(i, i2, 36196, width, height, i3, data.remaining(), data);
            return;
        }
        int i6 = i5 != 5121 ? 2 : 3;
        int i7 = i6 * width;
        ByteBuffer order = ByteBuffer.allocateDirect(i7 * height).order(ByteOrder.nativeOrder());
        ETC1.decodeImage(data, order, width, height, i6, i7);
        GLES10.glTexImage2D(i, i2, i4, width, height, i3, i4, i5, order);
    }

    public static boolean isETC1Supported() {
        int[] iArr = new int[20];
        GLES10.glGetIntegerv(34466, iArr, 0);
        int i = iArr[0];
        if (i > 20) {
            iArr = new int[i];
        }
        GLES10.glGetIntegerv(34467, iArr, 0);
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] == 36196) {
                return true;
            }
        }
        return false;
    }

    public static class ETC1Texture {
        private ByteBuffer mData;
        private int mHeight;
        private int mWidth;

        public ETC1Texture(int i, int i2, ByteBuffer byteBuffer) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mData = byteBuffer;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public ByteBuffer getData() {
            return this.mData;
        }
    }

    public static ETC1Texture createTexture(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4096];
        if (inputStream.read(bArr, 0, 16) != 16) {
            throw new IOException("Unable to read PKM file header.");
        }
        ByteBuffer order = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
        order.put(bArr, 0, 16).position(0);
        if (!ETC1.isValid(order)) {
            throw new IOException("Not a PKM file.");
        }
        int width = ETC1.getWidth(order);
        int height = ETC1.getHeight(order);
        int encodedDataSize = ETC1.getEncodedDataSize(width, height);
        ByteBuffer order2 = ByteBuffer.allocateDirect(encodedDataSize).order(ByteOrder.nativeOrder());
        int i = 0;
        while (i < encodedDataSize) {
            int min = Math.min(4096, encodedDataSize - i);
            if (inputStream.read(bArr, 0, min) != min) {
                throw new IOException("Unable to read PKM file data.");
            }
            order2.put(bArr, 0, min);
            i += min;
        }
        order2.position(0);
        return new ETC1Texture(width, height, order2);
    }

    public static ETC1Texture compressTexture(Buffer buffer, int i, int i2, int i3, int i4) {
        ByteBuffer order = ByteBuffer.allocateDirect(ETC1.getEncodedDataSize(i, i2)).order(ByteOrder.nativeOrder());
        ETC1.encodeImage(buffer, i, i2, i3, i4, order);
        return new ETC1Texture(i, i2, order);
    }

    public static void writeTexture(ETC1Texture eTC1Texture, OutputStream outputStream) throws IOException {
        ByteBuffer data = eTC1Texture.getData();
        int position = data.position();
        try {
            int width = eTC1Texture.getWidth();
            int height = eTC1Texture.getHeight();
            ByteBuffer order = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
            ETC1.formatHeader(order, width, height);
            byte[] bArr = new byte[4096];
            order.get(bArr, 0, 16);
            outputStream.write(bArr, 0, 16);
            int encodedDataSize = ETC1.getEncodedDataSize(width, height);
            int i = 0;
            while (i < encodedDataSize) {
                int min = Math.min(4096, encodedDataSize - i);
                data.get(bArr, 0, min);
                outputStream.write(bArr, 0, min);
                i += min;
            }
        } finally {
            data.position(position);
        }
    }
}
