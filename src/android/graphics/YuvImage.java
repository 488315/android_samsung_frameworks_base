package android.graphics;

import android.graphics.ColorSpace;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class YuvImage {
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private static final String[] sSupportedFormats = {"NV21", "YUY2", "YCBCR_P010", "YUV_420_888"};
    private static final ColorSpace.Named[] sSupportedJpegRHdrColorSpaces = {ColorSpace.Named.BT2020_HLG, ColorSpace.Named.BT2020_PQ};
    private static final ColorSpace.Named[] sSupportedJpegRSdrColorSpaces = {ColorSpace.Named.SRGB, ColorSpace.Named.DISPLAY_P3};
    private ColorSpace mColorSpace;
    private byte[] mData;
    private int mFormat;
    private int mHeight;
    private int[] mStrides;
    private int mWidth;

    private static native boolean nativeCompressToJpeg(byte[] bArr, int i, int i2, int i3, int[] iArr, int[] iArr2, int i4, OutputStream outputStream, byte[] bArr2);

    private static native boolean nativeCompressToJpegR(byte[] bArr, int i, byte[] bArr2, int i2, int i3, int i4, int i5, OutputStream outputStream, byte[] bArr3, byte[] bArr4, int[] iArr, int[] iArr2);

    private static String printSupportedFormats() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            String[] strArr = sSupportedFormats;
            if (i < strArr.length) {
                sb.append(strArr[i]);
                if (i != strArr.length - 1) {
                    sb.append(", ");
                }
                i++;
            } else {
                return sb.toString();
            }
        }
    }

    private static String printSupportedJpegRColorSpaces(boolean z) {
        ColorSpace.Named[] namedArr;
        if (z) {
            namedArr = sSupportedJpegRHdrColorSpaces;
        } else {
            namedArr = sSupportedJpegRSdrColorSpaces;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < namedArr.length; i++) {
            sb.append(ColorSpace.get(namedArr[i]).getName());
            if (i != namedArr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static boolean isSupportedJpegRColorSpace(boolean z, int i) {
        ColorSpace.Named[] namedArr;
        if (z) {
            namedArr = sSupportedJpegRHdrColorSpaces;
        } else {
            namedArr = sSupportedJpegRSdrColorSpaces;
        }
        for (ColorSpace.Named named : namedArr) {
            if (named.ordinal() == i) {
                return true;
            }
        }
        return false;
    }

    public YuvImage(byte[] bArr, int i, int i2, int i3, int[] iArr) {
        this(bArr, i, i2, i3, iArr, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public YuvImage(byte[] bArr, int i, int i2, int i3, int[] iArr, ColorSpace colorSpace) {
        if (i != 17 && i != 20 && i != 54 && i != 35) {
            throw new IllegalArgumentException("only supports the following ImageFormat:" + printSupportedFormats());
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("width and height must large than 0");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("yuv cannot be null");
        }
        if (colorSpace == null) {
            throw new IllegalArgumentException("ColorSpace cannot be null");
        }
        if (iArr == null) {
            this.mStrides = calculateStrides(i2, i);
        } else {
            this.mStrides = iArr;
        }
        this.mData = bArr;
        this.mFormat = i;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mColorSpace = colorSpace;
    }

    public boolean compressToJpeg(Rect rect, int i, OutputStream outputStream) {
        int i2 = this.mFormat;
        if (i2 != 17 && i2 != 20) {
            throw new IllegalArgumentException("Only ImageFormat.NV21 and ImageFormat.YUY2 are supported.");
        }
        if (this.mColorSpace.getId() != ColorSpace.Named.SRGB.ordinal()) {
            throw new IllegalArgumentException("Only SRGB color space is supported.");
        }
        if (!new Rect(0, 0, this.mWidth, this.mHeight).contains(rect)) {
            throw new IllegalArgumentException("rectangle is not inside the image");
        }
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("quality must be 0..100");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("stream cannot be null");
        }
        adjustRectangle(rect);
        return nativeCompressToJpeg(this.mData, this.mFormat, rect.width(), rect.height(), calculateOffsets(rect.left, rect.top), this.mStrides, i, outputStream, new byte[4096]);
    }

    public boolean compressToJpegR(YuvImage yuvImage, int i, OutputStream outputStream) {
        return compressToJpegR(yuvImage, i, outputStream, new byte[0]);
    }

    public boolean compressToJpegR(YuvImage yuvImage, int i, OutputStream outputStream, byte[] bArr) {
        if (yuvImage == null) {
            throw new IllegalArgumentException("SDR input cannot be null");
        }
        if (this.mData.length == 0 || yuvImage.getYuvData().length == 0) {
            throw new IllegalArgumentException("Input images cannot be empty");
        }
        if (this.mFormat != 54 || yuvImage.getYuvFormat() != 35) {
            throw new IllegalArgumentException("only support ImageFormat.YCBCR_P010 and ImageFormat.YUV_420_888");
        }
        if (yuvImage.getWidth() != this.mWidth || yuvImage.getHeight() != this.mHeight) {
            throw new IllegalArgumentException("HDR and SDR resolution mismatch");
        }
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("quality must be 0..100");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("stream cannot be null");
        }
        if (!isSupportedJpegRColorSpace(true, this.mColorSpace.getId()) || !isSupportedJpegRColorSpace(false, yuvImage.getColorSpace().getId())) {
            throw new IllegalArgumentException("Not supported color space. SDR only supports: " + printSupportedJpegRColorSpaces(false) + "HDR only supports: " + printSupportedJpegRColorSpaces(true));
        }
        return nativeCompressToJpegR(this.mData, this.mColorSpace.getDataSpace(), yuvImage.getYuvData(), yuvImage.getColorSpace().getDataSpace(), this.mWidth, this.mHeight, i, outputStream, new byte[4096], bArr, this.mStrides, yuvImage.getStrides());
    }

    public byte[] getYuvData() {
        return this.mData;
    }

    public int getYuvFormat() {
        return this.mFormat;
    }

    public int[] getStrides() {
        return this.mStrides;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    int[] calculateOffsets(int i, int i2) {
        int i3 = this.mFormat;
        if (i3 == 17) {
            int[] iArr = this.mStrides;
            int i4 = iArr[0];
            return new int[]{(i2 * i4) + i, (this.mHeight * i4) + ((i2 / 2) * iArr[1]) + ((i / 2) * 2)};
        }
        if (i3 == 20) {
            return new int[]{(i2 * this.mStrides[0]) + ((i / 2) * 4)};
        }
        return null;
    }

    private int[] calculateStrides(int i, int i2) {
        if (i2 == 17) {
            return new int[]{i, i};
        }
        if (i2 == 20) {
            return new int[]{i * 2};
        }
        if (i2 == 35) {
            int i3 = (i + 1) / 2;
            return new int[]{i, i3, i3};
        }
        if (i2 == 54) {
            int i4 = i * 2;
            return new int[]{i4, i4};
        }
        throw new IllegalArgumentException("only supports the following ImageFormat:" + printSupportedFormats());
    }

    private void adjustRectangle(Rect rect) {
        int iWidth = rect.width();
        int iHeight = rect.height();
        if (this.mFormat == 17) {
            iWidth &= -2;
            rect.left &= -2;
            rect.top &= -2;
            rect.right = rect.left + iWidth;
            rect.bottom = rect.top + (iHeight & (-2));
        }
        if (this.mFormat == 20) {
            rect.left &= -2;
            rect.right = rect.left + (iWidth & (-2));
        }
    }
}
