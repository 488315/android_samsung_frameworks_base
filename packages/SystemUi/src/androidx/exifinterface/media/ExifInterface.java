package androidx.exifinterface.media;

import android.content.res.AssetManager;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ExifInterface {
    public static final Charset ASCII;
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2;
    public static final int[] BITS_PER_SAMPLE_RGB;
    public static final Pattern DATETIME_PRIMARY_FORMAT_PATTERN;
    public static final Pattern DATETIME_SECONDARY_FORMAT_PATTERN;
    public static final boolean DEBUG = Log.isLoggable("ExifInterface", 3);
    public static final byte[] EXIF_ASCII_PREFIX;
    public static final ExifTag[] EXIF_POINTER_TAGS;
    public static final ExifTag[][] EXIF_TAGS;
    public static final Pattern GPS_TIMESTAMP_PATTERN;
    public static final byte[] HEIF_BRAND_HEIC;
    public static final byte[] HEIF_BRAND_MIF1;
    public static final byte[] HEIF_TYPE_FTYP;
    public static final byte[] IDENTIFIER_EXIF_APP1;
    public static final byte[] IDENTIFIER_XMP_APP1;
    public static final int[] IFD_FORMAT_BYTES_PER_FORMAT;
    public static final String[] IFD_FORMAT_NAMES;
    public static final byte[] JPEG_SIGNATURE;
    public static final byte[] ORF_MAKER_NOTE_HEADER_1;
    public static final byte[] ORF_MAKER_NOTE_HEADER_2;
    public static final byte[] PNG_CHUNK_TYPE_EXIF;
    public static final byte[] PNG_CHUNK_TYPE_IEND;
    public static final byte[] PNG_CHUNK_TYPE_IHDR;
    public static final byte[] PNG_SIGNATURE;
    public static final ExifTag TAG_RAF_IMAGE_SIZE;
    public static final byte[] WEBP_CHUNK_TYPE_ANIM;
    public static final byte[] WEBP_CHUNK_TYPE_ANMF;
    public static final byte[] WEBP_CHUNK_TYPE_EXIF;
    public static final byte[] WEBP_CHUNK_TYPE_VP8;
    public static final byte[] WEBP_CHUNK_TYPE_VP8L;
    public static final byte[] WEBP_CHUNK_TYPE_VP8X;
    public static final byte[] WEBP_SIGNATURE_1;
    public static final byte[] WEBP_SIGNATURE_2;
    public static final byte[] WEBP_VP8_SIGNATURE;
    public static final HashMap sExifPointerTagMap;
    public static final HashMap[] sExifTagMapsForReading;
    public static final HashMap[] sExifTagMapsForWriting;
    public static final HashSet sTagSetForCompatibility;
    public boolean mAreThumbnailStripsConsecutive;
    public AssetManager.AssetInputStream mAssetInputStream;
    public final HashMap[] mAttributes;
    public final Set mAttributesOffsets;
    public ByteOrder mExifByteOrder;
    public String mFilename;
    public boolean mHasThumbnail;
    public boolean mHasThumbnailStrips;
    public final boolean mIsExifDataOnly;
    public int mMimeType;
    public int mOffsetToExifData;
    public int mOrfMakerNoteOffset;
    public int mOrfThumbnailLength;
    public int mOrfThumbnailOffset;
    public FileDescriptor mSeekableFileDescriptor;
    public byte[] mThumbnailBytes;
    public int mThumbnailCompression;
    public int mThumbnailLength;
    public int mThumbnailOffset;
    public boolean mXmpIsFromSeparateMarker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ByteOrderedDataOutputStream extends FilterOutputStream {
        public ByteOrder mByteOrder;
        public final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final void write(byte[] bArr) {
            this.mOutputStream.write(bArr);
        }

        public final void writeByte(int i) {
            this.mOutputStream.write(i);
        }

        public final void writeInt(int i) {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((i >>> 0) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
                return;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 0) & 255);
            }
        }

        public final void writeShort(short s) {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((s >>> 0) & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write((s >>> 0) & 255);
            }
        }

        public final void writeUnsignedInt(long j) {
            if (j > 4294967295L) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 32-bit unsigned integer");
            }
            writeInt((int) j);
        }

        public final void writeUnsignedShort(int i) {
            if (i > 65535) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 16-bit unsigned integer");
            }
            writeShort((short) i);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) {
            this.mOutputStream.write(bArr, i, i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExifAttribute {
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        public ExifAttribute(int i, int i2, byte[] bArr) {
            this(i, i2, -1L, bArr);
        }

        public static ExifAttribute createString(String str) {
            byte[] bytes = str.concat("\u0000").getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, bytes.length, bytes);
        }

        public static ExifAttribute createULong(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, wrap.array());
        }

        public static ExifAttribute createURational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * rationalArr.length]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(5, rationalArr.length, wrap.array());
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, wrap.array());
        }

        public final double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            }
            if (value instanceof String) {
                return Double.parseDouble((String) value);
            }
            if (value instanceof long[]) {
                if (((long[]) value).length == 1) {
                    return r3[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof int[]) {
                if (((int[]) value).length == 1) {
                    return r3[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                if (dArr.length == 1) {
                    return dArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (!(value instanceof Rational[])) {
                throw new NumberFormatException("Couldn't find a double value");
            }
            Rational[] rationalArr = (Rational[]) value;
            if (rationalArr.length != 1) {
                throw new NumberFormatException("There are more than one component");
            }
            Rational rational = rationalArr[0];
            return rational.numerator / rational.denominator;
        }

        public final int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            }
            if (value instanceof String) {
                return Integer.parseInt((String) value);
            }
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                if (jArr.length == 1) {
                    return (int) jArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (!(value instanceof int[])) {
                throw new NumberFormatException("Couldn't find a integer value");
            }
            int[] iArr = (int[]) value;
            if (iArr.length == 1) {
                return iArr[0];
            }
            throw new NumberFormatException("There are more than one component");
        }

        public final String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (!(value instanceof Rational[])) {
                return null;
            }
            Rational[] rationalArr = (Rational[]) value;
            while (i < rationalArr.length) {
                sb.append(rationalArr[i].numerator);
                sb.append('/');
                sb.append(rationalArr[i].denominator);
                i++;
                if (i != rationalArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        /* JADX WARN: Can't wrap try/catch for region: R(9:89|(3:91|(2:92|(1:101)(2:94|(2:97|98)(1:96)))|(1:100))|102|(2:104|(6:113|114|115|116|117|118)(3:106|(2:108|109)(2:111|112)|110))|122|115|116|117|118) */
        /* JADX WARN: Code restructure failed: missing block: B:120:0x012a, code lost:
        
            r14 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:121:0x012b, code lost:
        
            android.util.Log.e("ExifInterface", "IOException occurred while closing InputStream", r14);
         */
        /* JADX WARN: Not initialized variable reg: 4, insn: 0x0167: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]) (LINE:360), block:B:159:0x0167 */
        /* JADX WARN: Removed duplicated region for block: B:162:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object getValue(ByteOrder byteOrder) {
            ByteOrderedDataInputStream byteOrderedDataInputStream;
            InputStream inputStream;
            byte b;
            byte[] bArr;
            byte[] bArr2 = this.bytes;
            InputStream inputStream2 = null;
            try {
                try {
                    byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr2);
                    try {
                        byteOrderedDataInputStream.mByteOrder = byteOrder;
                        int i = this.format;
                        boolean z = true;
                        int i2 = 0;
                        int i3 = this.numberOfComponents;
                        switch (i) {
                            case 1:
                            case 6:
                                if (bArr2.length != 1 || (b = bArr2[0]) < 0 || b > 1) {
                                    String str = new String(bArr2, ExifInterface.ASCII);
                                    try {
                                        byteOrderedDataInputStream.close();
                                    } catch (IOException e) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e);
                                    }
                                    return str;
                                }
                                String str2 = new String(new char[]{(char) (b + 48)});
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e2) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e2);
                                }
                                return str2;
                            case 2:
                            case 7:
                                if (i3 >= ExifInterface.EXIF_ASCII_PREFIX.length) {
                                    int i4 = 0;
                                    while (true) {
                                        bArr = ExifInterface.EXIF_ASCII_PREFIX;
                                        if (i4 < bArr.length) {
                                            if (bArr2[i4] != bArr[i4]) {
                                                z = false;
                                            } else {
                                                i4++;
                                            }
                                        }
                                    }
                                    if (z) {
                                        i2 = bArr.length;
                                    }
                                }
                                StringBuilder sb = new StringBuilder();
                                while (i2 < i3) {
                                    byte b2 = bArr2[i2];
                                    if (b2 == 0) {
                                        String sb2 = sb.toString();
                                        byteOrderedDataInputStream.close();
                                        return sb2;
                                    }
                                    if (b2 >= 32) {
                                        sb.append((char) b2);
                                    } else {
                                        sb.append('?');
                                    }
                                    i2++;
                                }
                                String sb22 = sb.toString();
                                byteOrderedDataInputStream.close();
                                return sb22;
                            case 3:
                                int[] iArr = new int[i3];
                                while (i2 < i3) {
                                    iArr[i2] = byteOrderedDataInputStream.readUnsignedShort();
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e3) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e3);
                                }
                                return iArr;
                            case 4:
                                long[] jArr = new long[i3];
                                while (i2 < i3) {
                                    jArr[i2] = byteOrderedDataInputStream.readInt() & 4294967295L;
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e4) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e4);
                                }
                                return jArr;
                            case 5:
                                Rational[] rationalArr = new Rational[i3];
                                while (i2 < i3) {
                                    rationalArr[i2] = new Rational(byteOrderedDataInputStream.readInt() & 4294967295L, byteOrderedDataInputStream.readInt() & 4294967295L);
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e5) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e5);
                                }
                                return rationalArr;
                            case 8:
                                int[] iArr2 = new int[i3];
                                while (i2 < i3) {
                                    iArr2[i2] = byteOrderedDataInputStream.readShort();
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e6) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e6);
                                }
                                return iArr2;
                            case 9:
                                int[] iArr3 = new int[i3];
                                while (i2 < i3) {
                                    iArr3[i2] = byteOrderedDataInputStream.readInt();
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e7) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e7);
                                }
                                return iArr3;
                            case 10:
                                Rational[] rationalArr2 = new Rational[i3];
                                while (i2 < i3) {
                                    rationalArr2[i2] = new Rational(byteOrderedDataInputStream.readInt(), byteOrderedDataInputStream.readInt());
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e8) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e8);
                                }
                                return rationalArr2;
                            case 11:
                                double[] dArr = new double[i3];
                                while (i2 < i3) {
                                    dArr[i2] = byteOrderedDataInputStream.readFloat();
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e9) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e9);
                                }
                                return dArr;
                            case 12:
                                double[] dArr2 = new double[i3];
                                while (i2 < i3) {
                                    dArr2[i2] = byteOrderedDataInputStream.readDouble();
                                    i2++;
                                }
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e10) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e10);
                                }
                                return dArr2;
                            default:
                                try {
                                    byteOrderedDataInputStream.close();
                                } catch (IOException e11) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e11);
                                }
                                return null;
                        }
                    } catch (IOException e12) {
                        e = e12;
                        Log.w("ExifInterface", "IOException occurred during reading a value", e);
                        if (byteOrderedDataInputStream != null) {
                            try {
                                byteOrderedDataInputStream.close();
                            } catch (IOException e13) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e13);
                            }
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e14) {
                            Log.e("ExifInterface", "IOException occurred while closing InputStream", e14);
                        }
                    }
                    throw th;
                }
            } catch (IOException e15) {
                e = e15;
                byteOrderedDataInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (inputStream2 != null) {
                }
                throw th;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("(");
            sb.append(ExifInterface.IFD_FORMAT_NAMES[this.format]);
            sb.append(", data length:");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.bytes.length, ")");
        }

        public ExifAttribute(int i, int i2, long j, byte[] bArr) {
            this.format = i;
            this.numberOfComponents = i2;
            this.bytesOffset = j;
            this.bytes = bArr;
        }

        public static ExifAttribute createULong(long j, ByteOrder byteOrder) {
            return createULong(new long[]{j}, byteOrder);
        }

        public static ExifAttribute createUShort(int i, ByteOrder byteOrder) {
            return createUShort(new int[]{i}, byteOrder);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Rational {
        public final long denominator;
        public final long numerator;

        public Rational(double d) {
            this((long) (d * 10000.0d), 10000L);
        }

        public final String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = j;
                this.denominator = j2;
            }
        }
    }

    static {
        Arrays.asList(1, 6, 3, 8);
        Arrays.asList(2, 7, 4, 5);
        BITS_PER_SAMPLE_RGB = new int[]{8, 8, 8};
        BITS_PER_SAMPLE_GREYSCALE_2 = new int[]{8};
        JPEG_SIGNATURE = new byte[]{-1, -40, -1};
        HEIF_TYPE_FTYP = new byte[]{102, 116, 121, 112};
        HEIF_BRAND_MIF1 = new byte[]{109, 105, 102, 49};
        HEIF_BRAND_HEIC = new byte[]{104, 101, 105, 99};
        ORF_MAKER_NOTE_HEADER_1 = new byte[]{79, 76, 89, 77, 80, 0};
        ORF_MAKER_NOTE_HEADER_2 = new byte[]{79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
        PNG_SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
        PNG_CHUNK_TYPE_EXIF = new byte[]{101, 88, 73, 102};
        PNG_CHUNK_TYPE_IHDR = new byte[]{73, 72, 68, 82};
        PNG_CHUNK_TYPE_IEND = new byte[]{73, 69, 78, 68};
        WEBP_SIGNATURE_1 = new byte[]{82, 73, 70, 70};
        WEBP_SIGNATURE_2 = new byte[]{87, 69, 66, 80};
        WEBP_CHUNK_TYPE_EXIF = new byte[]{69, 88, 73, 70};
        WEBP_VP8_SIGNATURE = new byte[]{-99, 1, 42};
        WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
        WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
        WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
        WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
        WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());
        IFD_FORMAT_NAMES = new String[]{"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", PeripheralBarcodeConstants.Symbology.UNDEFINED, "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
        IFD_FORMAT_BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
        EXIF_ASCII_PREFIX = new byte[]{65, 83, 67, 73, 73, 0, 0, 0};
        ExifTag[] exifTagArr = {new ExifTag("NewSubfileType", 254, 4), new ExifTag("SubfileType", 255, 4), new ExifTag("ImageWidth", 256, 3, 4), new ExifTag("ImageLength", 257, 3, 4), new ExifTag("BitsPerSample", 258, 3), new ExifTag("Compression", 259, 3), new ExifTag("PhotometricInterpretation", 262, 3), new ExifTag("ImageDescription", 270, 2), new ExifTag("Make", 271, 2), new ExifTag("Model", 272, 2), new ExifTag("StripOffsets", IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal, 3, 4), new ExifTag("Orientation", IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal, 3), new ExifTag("SamplesPerPixel", IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity, 3), new ExifTag("RowsPerStrip", IKnoxCustomManager.Stub.TRANSACTION_startSmartView, 3, 4), new ExifTag("StripByteCounts", IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView, 3, 4), new ExifTag("XResolution", IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub, 5), new ExifTag("YResolution", IKnoxCustomManager.Stub.TRANSACTION_getLoadingLogoPath, 5), new ExifTag("PlanarConfiguration", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback, 3), new ExifTag("ResolutionUnit", IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal, 3), new ExifTag("TransferFunction", 301, 3), new ExifTag("Software", 305, 2), new ExifTag("DateTime", VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING, 2), new ExifTag("Artist", 315, 2), new ExifTag("WhitePoint", 318, 5), new ExifTag("PrimaryChromaticities", 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag("JPEGInterchangeFormat", 513, 4), new ExifTag("JPEGInterchangeFormatLength", 514, 4), new ExifTag("YCbCrCoefficients", 529, 5), new ExifTag("YCbCrSubSampling", 530, 3), new ExifTag("YCbCrPositioning", 531, 3), new ExifTag("ReferenceBlackWhite", 532, 5), new ExifTag("Copyright", 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("SensorTopBorder", 4, 4), new ExifTag("SensorLeftBorder", 5, 4), new ExifTag("SensorBottomBorder", 6, 4), new ExifTag("SensorRightBorder", 7, 4), new ExifTag("ISO", 23, 3), new ExifTag("JpgFromRaw", 46, 7), new ExifTag("Xmp", KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 1)};
        ExifTag[] exifTagArr2 = {new ExifTag("ExposureTime", 33434, 5), new ExifTag("FNumber", 33437, 5), new ExifTag("ExposureProgram", 34850, 3), new ExifTag("SpectralSensitivity", 34852, 2), new ExifTag("PhotographicSensitivity", 34855, 3), new ExifTag("OECF", 34856, 7), new ExifTag("SensitivityType", 34864, 3), new ExifTag("StandardOutputSensitivity", 34865, 4), new ExifTag("RecommendedExposureIndex", 34866, 4), new ExifTag("ISOSpeed", 34867, 4), new ExifTag("ISOSpeedLatitudeyyy", 34868, 4), new ExifTag("ISOSpeedLatitudezzz", 34869, 4), new ExifTag("ExifVersion", 36864, 2), new ExifTag("DateTimeOriginal", 36867, 2), new ExifTag("DateTimeDigitized", 36868, 2), new ExifTag("OffsetTime", 36880, 2), new ExifTag("OffsetTimeOriginal", 36881, 2), new ExifTag("OffsetTimeDigitized", 36882, 2), new ExifTag("ComponentsConfiguration", 37121, 7), new ExifTag("CompressedBitsPerPixel", 37122, 5), new ExifTag("ShutterSpeedValue", 37377, 10), new ExifTag("ApertureValue", 37378, 5), new ExifTag("BrightnessValue", 37379, 10), new ExifTag("ExposureBiasValue", 37380, 10), new ExifTag("MaxApertureValue", 37381, 5), new ExifTag("SubjectDistance", 37382, 5), new ExifTag("MeteringMode", 37383, 3), new ExifTag("LightSource", 37384, 3), new ExifTag("Flash", 37385, 3), new ExifTag("FocalLength", 37386, 5), new ExifTag("SubjectArea", 37396, 3), new ExifTag("MakerNote", 37500, 7), new ExifTag("UserComment", 37510, 7), new ExifTag("SubSecTime", 37520, 2), new ExifTag("SubSecTimeOriginal", 37521, 2), new ExifTag("SubSecTimeDigitized", 37522, 2), new ExifTag("FlashpixVersion", 40960, 7), new ExifTag("ColorSpace", 40961, 3), new ExifTag("PixelXDimension", 40962, 3, 4), new ExifTag("PixelYDimension", 40963, 3, 4), new ExifTag("RelatedSoundFile", 40964, 2), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag("FlashEnergy", 41483, 5), new ExifTag("SpatialFrequencyResponse", 41484, 7), new ExifTag("FocalPlaneXResolution", 41486, 5), new ExifTag("FocalPlaneYResolution", 41487, 5), new ExifTag("FocalPlaneResolutionUnit", 41488, 3), new ExifTag("SubjectLocation", 41492, 3), new ExifTag("ExposureIndex", 41493, 5), new ExifTag("SensingMethod", 41495, 3), new ExifTag("FileSource", 41728, 7), new ExifTag("SceneType", 41729, 7), new ExifTag("CFAPattern", 41730, 7), new ExifTag("CustomRendered", 41985, 3), new ExifTag("ExposureMode", 41986, 3), new ExifTag("WhiteBalance", 41987, 3), new ExifTag("DigitalZoomRatio", 41988, 5), new ExifTag("FocalLengthIn35mmFilm", 41989, 3), new ExifTag("SceneCaptureType", 41990, 3), new ExifTag("GainControl", 41991, 3), new ExifTag("Contrast", 41992, 3), new ExifTag("Saturation", 41993, 3), new ExifTag("Sharpness", 41994, 3), new ExifTag("DeviceSettingDescription", 41995, 7), new ExifTag("SubjectDistanceRange", 41996, 3), new ExifTag("ImageUniqueID", 42016, 2), new ExifTag("CameraOwnerName", 42032, 2), new ExifTag("BodySerialNumber", 42033, 2), new ExifTag("LensSpecification", 42034, 5), new ExifTag("LensMake", 42035, 2), new ExifTag("LensModel", 42036, 2), new ExifTag("Gamma", 42240, 5), new ExifTag("DNGVersion", 50706, 1), new ExifTag("DefaultCropSize", 50720, 3, 4)};
        ExifTag[] exifTagArr3 = {new ExifTag("GPSVersionID", 0, 1), new ExifTag("GPSLatitudeRef", 1, 2), new ExifTag("GPSLatitude", 2, 5, 10), new ExifTag("GPSLongitudeRef", 3, 2), new ExifTag("GPSLongitude", 4, 5, 10), new ExifTag("GPSAltitudeRef", 5, 1), new ExifTag("GPSAltitude", 6, 5), new ExifTag("GPSTimeStamp", 7, 5), new ExifTag("GPSSatellites", 8, 2), new ExifTag("GPSStatus", 9, 2), new ExifTag("GPSMeasureMode", 10, 2), new ExifTag("GPSDOP", 11, 5), new ExifTag("GPSSpeedRef", 12, 2), new ExifTag("GPSSpeed", 13, 5), new ExifTag("GPSTrackRef", 14, 2), new ExifTag("GPSTrack", 15, 5), new ExifTag("GPSImgDirectionRef", 16, 2), new ExifTag("GPSImgDirection", 17, 5), new ExifTag("GPSMapDatum", 18, 2), new ExifTag("GPSDestLatitudeRef", 19, 2), new ExifTag("GPSDestLatitude", 20, 5), new ExifTag("GPSDestLongitudeRef", 21, 2), new ExifTag("GPSDestLongitude", 22, 5), new ExifTag("GPSDestBearingRef", 23, 2), new ExifTag("GPSDestBearing", 24, 5), new ExifTag("GPSDestDistanceRef", 25, 2), new ExifTag("GPSDestDistance", 26, 5), new ExifTag("GPSProcessingMethod", 27, 7), new ExifTag("GPSAreaInformation", 28, 7), new ExifTag("GPSDateStamp", 29, 2), new ExifTag("GPSDifferential", 30, 3), new ExifTag("GPSHPositioningError", 31, 5)};
        ExifTag[] exifTagArr4 = {new ExifTag("InteroperabilityIndex", 1, 2)};
        ExifTag[] exifTagArr5 = {new ExifTag("NewSubfileType", 254, 4), new ExifTag("SubfileType", 255, 4), new ExifTag("ThumbnailImageWidth", 256, 3, 4), new ExifTag("ThumbnailImageLength", 257, 3, 4), new ExifTag("BitsPerSample", 258, 3), new ExifTag("Compression", 259, 3), new ExifTag("PhotometricInterpretation", 262, 3), new ExifTag("ImageDescription", 270, 2), new ExifTag("Make", 271, 2), new ExifTag("Model", 272, 2), new ExifTag("StripOffsets", IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal, 3, 4), new ExifTag("ThumbnailOrientation", IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal, 3), new ExifTag("SamplesPerPixel", IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity, 3), new ExifTag("RowsPerStrip", IKnoxCustomManager.Stub.TRANSACTION_startSmartView, 3, 4), new ExifTag("StripByteCounts", IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView, 3, 4), new ExifTag("XResolution", IKnoxCustomManager.Stub.TRANSACTION_setShuttingDownAnimationSub, 5), new ExifTag("YResolution", IKnoxCustomManager.Stub.TRANSACTION_getLoadingLogoPath, 5), new ExifTag("PlanarConfiguration", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback, 3), new ExifTag("ResolutionUnit", IKnoxCustomManager.Stub.TRANSACTION_setApplicationRestrictionsInternal, 3), new ExifTag("TransferFunction", 301, 3), new ExifTag("Software", 305, 2), new ExifTag("DateTime", VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING, 2), new ExifTag("Artist", 315, 2), new ExifTag("WhitePoint", 318, 5), new ExifTag("PrimaryChromaticities", 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag("JPEGInterchangeFormat", 513, 4), new ExifTag("JPEGInterchangeFormatLength", 514, 4), new ExifTag("YCbCrCoefficients", 529, 5), new ExifTag("YCbCrSubSampling", 530, 3), new ExifTag("YCbCrPositioning", 531, 3), new ExifTag("ReferenceBlackWhite", 532, 5), new ExifTag("Copyright", 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("DNGVersion", 50706, 1), new ExifTag("DefaultCropSize", 50720, 3, 4)};
        TAG_RAF_IMAGE_SIZE = new ExifTag("StripOffsets", IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal, 3);
        EXIF_TAGS = new ExifTag[][]{exifTagArr, exifTagArr2, exifTagArr3, exifTagArr4, exifTagArr5, exifTagArr, new ExifTag[]{new ExifTag("ThumbnailImage", 256, 7), new ExifTag("CameraSettingsIFDPointer", 8224, 4), new ExifTag("ImageProcessingIFDPointer", 8256, 4)}, new ExifTag[]{new ExifTag("PreviewImageStart", 257, 4), new ExifTag("PreviewImageLength", 258, 4)}, new ExifTag[]{new ExifTag("AspectFrame", 4371, 3)}, new ExifTag[]{new ExifTag("ColorSpace", 55, 3)}};
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag("SubIFDPointer", 330, 4), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag("CameraSettingsIFDPointer", 8224, 1), new ExifTag("ImageProcessingIFDPointer", 8256, 1)};
        sExifTagMapsForReading = new HashMap[10];
        sExifTagMapsForWriting = new HashMap[10];
        sTagSetForCompatibility = new HashSet(Arrays.asList("FNumber", "DigitalZoomRatio", "ExposureTime", "SubjectDistance", "GPSTimeStamp"));
        sExifPointerTagMap = new HashMap();
        Charset forName = Charset.forName("US-ASCII");
        ASCII = forName;
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(forName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(forName);
        Locale locale = Locale.US;
        new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        int i = 0;
        while (true) {
            ExifTag[][] exifTagArr6 = EXIF_TAGS;
            if (i >= exifTagArr6.length) {
                HashMap hashMap = sExifPointerTagMap;
                ExifTag[] exifTagArr7 = EXIF_POINTER_TAGS;
                hashMap.put(Integer.valueOf(exifTagArr7[0].number), 5);
                hashMap.put(Integer.valueOf(exifTagArr7[1].number), 1);
                hashMap.put(Integer.valueOf(exifTagArr7[2].number), 2);
                hashMap.put(Integer.valueOf(exifTagArr7[3].number), 3);
                hashMap.put(Integer.valueOf(exifTagArr7[4].number), 7);
                hashMap.put(Integer.valueOf(exifTagArr7[5].number), 8);
                Pattern.compile(".*[1-9].*");
                GPS_TIMESTAMP_PATTERN = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
                DATETIME_PRIMARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
                DATETIME_SECONDARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
                return;
            }
            sExifTagMapsForReading[i] = new HashMap();
            sExifTagMapsForWriting[i] = new HashMap();
            for (ExifTag exifTag : exifTagArr6[i]) {
                sExifTagMapsForReading[i].put(Integer.valueOf(exifTag.number), exifTag);
                sExifTagMapsForWriting[i].put(exifTag.name, exifTag);
            }
            i++;
        }
    }

    public ExifInterface(File file) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (file == null) {
            throw new NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    public static Pair guessDataFormat(String str) {
        if (str.contains(",")) {
            String[] split = str.split(",", -1);
            Pair guessDataFormat = guessDataFormat(split[0]);
            if (((Integer) guessDataFormat.first).intValue() == 2) {
                return guessDataFormat;
            }
            for (int i = 1; i < split.length; i++) {
                Pair guessDataFormat2 = guessDataFormat(split[i]);
                int intValue = (((Integer) guessDataFormat2.first).equals(guessDataFormat.first) || ((Integer) guessDataFormat2.second).equals(guessDataFormat.first)) ? ((Integer) guessDataFormat.first).intValue() : -1;
                int intValue2 = (((Integer) guessDataFormat.second).intValue() == -1 || !(((Integer) guessDataFormat2.first).equals(guessDataFormat.second) || ((Integer) guessDataFormat2.second).equals(guessDataFormat.second))) ? -1 : ((Integer) guessDataFormat.second).intValue();
                if (intValue == -1 && intValue2 == -1) {
                    return new Pair(2, -1);
                }
                if (intValue == -1) {
                    guessDataFormat = new Pair(Integer.valueOf(intValue2), -1);
                } else if (intValue2 == -1) {
                    guessDataFormat = new Pair(Integer.valueOf(intValue), -1);
                }
            }
            return guessDataFormat;
        }
        if (!str.contains("/")) {
            try {
                try {
                    Long valueOf = Long.valueOf(Long.parseLong(str));
                    return (valueOf.longValue() < 0 || valueOf.longValue() > 65535) ? valueOf.longValue() < 0 ? new Pair(9, -1) : new Pair(4, -1) : new Pair(3, 4);
                } catch (NumberFormatException unused) {
                    return new Pair(2, -1);
                }
            } catch (NumberFormatException unused2) {
                Double.parseDouble(str);
                return new Pair(12, -1);
            }
        }
        String[] split2 = str.split("/", -1);
        if (split2.length == 2) {
            try {
                long parseDouble = (long) Double.parseDouble(split2[0]);
                long parseDouble2 = (long) Double.parseDouble(split2[1]);
                if (parseDouble >= 0 && parseDouble2 >= 0) {
                    if (parseDouble <= 2147483647L && parseDouble2 <= 2147483647L) {
                        return new Pair(10, 5);
                    }
                    return new Pair(5, -1);
                }
                return new Pair(10, -1);
            } catch (NumberFormatException unused3) {
            }
        }
        return new Pair(2, -1);
    }

    public static boolean isSeekableFD(FileDescriptor fileDescriptor) {
        try {
            Os.lseek(fileDescriptor, 0L, OsConstants.SEEK_CUR);
            return true;
        } catch (Exception unused) {
            if (!DEBUG) {
                return false;
            }
            Log.d("ExifInterface", "The file descriptor for the given input is not seekable");
            return false;
        }
    }

    public static ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        short readShort = byteOrderedDataInputStream.readShort();
        boolean z = DEBUG;
        if (readShort == 18761) {
            if (z) {
                Log.d("ExifInterface", "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (readShort == 19789) {
            if (z) {
                Log.d("ExifInterface", "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        }
        throw new IOException("Invalid byte order: " + Integer.toHexString(readShort));
    }

    public final void addDefaultValuesForCompatibility() {
        String attribute = getAttribute("DateTimeOriginal");
        HashMap[] hashMapArr = this.mAttributes;
        if (attribute != null && getAttribute("DateTime") == null) {
            hashMapArr[0].put("DateTime", ExifAttribute.createString(attribute));
        }
        if (getAttribute("ImageWidth") == null) {
            hashMapArr[0].put("ImageWidth", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("ImageLength") == null) {
            hashMapArr[0].put("ImageLength", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("Orientation") == null) {
            hashMapArr[0].put("Orientation", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("LightSource") == null) {
            hashMapArr[1].put("LightSource", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    public final String getAttribute(String str) {
        String str2;
        ExifAttribute exifAttribute;
        if ("ISOSpeedRatings".equals(str)) {
            if (DEBUG) {
                Log.d("ExifInterface", "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str2 = "PhotographicSensitivity";
        } else {
            str2 = str;
        }
        int i = 0;
        while (true) {
            if (i >= EXIF_TAGS.length) {
                exifAttribute = null;
                break;
            }
            exifAttribute = (ExifAttribute) this.mAttributes[i].get(str2);
            if (exifAttribute != null) {
                break;
            }
            i++;
        }
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            if (str.equals("GPSTimeStamp")) {
                int i2 = exifAttribute.format;
                if (i2 != 5 && i2 != 10) {
                    IconCompat$$ExternalSyntheticOutline0.m30m("GPS Timestamp format is not rational. format=", i2, "ExifInterface");
                    return null;
                }
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 3) {
                    Log.w("ExifInterface", "Invalid GPS Timestamp array. array=" + Arrays.toString(rationalArr));
                    return null;
                }
                Rational rational = rationalArr[0];
                Integer valueOf = Integer.valueOf((int) (rational.numerator / rational.denominator));
                Rational rational2 = rationalArr[1];
                Integer valueOf2 = Integer.valueOf((int) (rational2.numerator / rational2.denominator));
                Rational rational3 = rationalArr[2];
                return String.format("%02d:%02d:%02d", valueOf, valueOf2, Integer.valueOf((int) (rational3.numerator / rational3.denominator)));
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public final void getHeifAttributes(final SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        String str;
        String str2;
        String str3;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(new MediaDataSource(this) { // from class: androidx.exifinterface.media.ExifInterface.1
                    public long mPosition;

                    @Override // android.media.MediaDataSource
                    public final long getSize() {
                        return -1L;
                    }

                    @Override // android.media.MediaDataSource
                    public final int readAt(long j, byte[] bArr, int i, int i2) {
                        if (i2 == 0) {
                            return 0;
                        }
                        if (j < 0) {
                            return -1;
                        }
                        try {
                            long j2 = this.mPosition;
                            if (j2 != j) {
                                if (j2 >= 0 && j >= j2 + seekableByteOrderedDataInputStream.available()) {
                                    return -1;
                                }
                                seekableByteOrderedDataInputStream.seek(j);
                                this.mPosition = j;
                            }
                            if (i2 > seekableByteOrderedDataInputStream.available()) {
                                i2 = seekableByteOrderedDataInputStream.available();
                            }
                            int read = seekableByteOrderedDataInputStream.read(bArr, i, i2);
                            if (read >= 0) {
                                this.mPosition += read;
                                return read;
                            }
                        } catch (IOException unused) {
                        }
                        this.mPosition = -1L;
                        return -1;
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public final void close() {
                    }
                });
                String extractMetadata = mediaMetadataRetriever.extractMetadata(33);
                String extractMetadata2 = mediaMetadataRetriever.extractMetadata(34);
                String extractMetadata3 = mediaMetadataRetriever.extractMetadata(26);
                String extractMetadata4 = mediaMetadataRetriever.extractMetadata(17);
                if ("yes".equals(extractMetadata3)) {
                    str = mediaMetadataRetriever.extractMetadata(29);
                    str2 = mediaMetadataRetriever.extractMetadata(30);
                    str3 = mediaMetadataRetriever.extractMetadata(31);
                } else if ("yes".equals(extractMetadata4)) {
                    str = mediaMetadataRetriever.extractMetadata(18);
                    str2 = mediaMetadataRetriever.extractMetadata(19);
                    str3 = mediaMetadataRetriever.extractMetadata(24);
                } else {
                    str = null;
                    str2 = null;
                    str3 = null;
                }
                HashMap[] hashMapArr = this.mAttributes;
                if (str != null) {
                    hashMapArr[0].put("ImageWidth", ExifAttribute.createUShort(Integer.parseInt(str), this.mExifByteOrder));
                }
                if (str2 != null) {
                    hashMapArr[0].put("ImageLength", ExifAttribute.createUShort(Integer.parseInt(str2), this.mExifByteOrder));
                }
                if (str3 != null) {
                    int parseInt = Integer.parseInt(str3);
                    hashMapArr[0].put("Orientation", ExifAttribute.createUShort(parseInt != 90 ? parseInt != 180 ? parseInt != 270 ? 1 : 8 : 3 : 6, this.mExifByteOrder));
                }
                if (extractMetadata != null && extractMetadata2 != null) {
                    int parseInt2 = Integer.parseInt(extractMetadata);
                    int parseInt3 = Integer.parseInt(extractMetadata2);
                    if (parseInt3 <= 6) {
                        throw new IOException("Invalid exif length");
                    }
                    seekableByteOrderedDataInputStream.seek(parseInt2);
                    byte[] bArr = new byte[6];
                    seekableByteOrderedDataInputStream.readFully(bArr);
                    int i = parseInt2 + 6;
                    int i2 = parseInt3 - 6;
                    if (!Arrays.equals(bArr, IDENTIFIER_EXIF_APP1)) {
                        throw new IOException("Invalid identifier");
                    }
                    byte[] bArr2 = new byte[i2];
                    seekableByteOrderedDataInputStream.readFully(bArr2);
                    this.mOffsetToExifData = i;
                    readExifSegment(0, bArr2);
                }
                String extractMetadata5 = mediaMetadataRetriever.extractMetadata(41);
                String extractMetadata6 = mediaMetadataRetriever.extractMetadata(42);
                if (extractMetadata5 != null && extractMetadata6 != null) {
                    int parseInt4 = Integer.parseInt(extractMetadata5);
                    int parseInt5 = Integer.parseInt(extractMetadata6);
                    long j = parseInt4;
                    seekableByteOrderedDataInputStream.seek(j);
                    byte[] bArr3 = new byte[parseInt5];
                    seekableByteOrderedDataInputStream.readFully(bArr3);
                    if (getAttribute("Xmp") == null) {
                        hashMapArr[0].put("Xmp", new ExifAttribute(1, parseInt5, j, bArr3));
                    }
                }
                if (DEBUG) {
                    Log.d("ExifInterface", "Heif meta: " + str + "x" + str2 + ", rotation " + str3);
                }
            } catch (RuntimeException unused) {
                throw new UnsupportedOperationException("Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported.");
            }
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException unused2) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0184 A[LOOP:0: B:9:0x0034->B:32:0x0184, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x018c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0158  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getJpegAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream, int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3 = DEBUG;
        if (z3) {
            Log.d("ExifInterface", "getJpegAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        byte readByte = byteOrderedDataInputStream.readByte();
        byte b = -1;
        if (readByte != -1) {
            throw new IOException("Invalid marker: " + Integer.toHexString(readByte & 255));
        }
        if (byteOrderedDataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker: " + Integer.toHexString(readByte & 255));
        }
        int i3 = 2;
        int i4 = 2;
        while (true) {
            byte readByte2 = byteOrderedDataInputStream.readByte();
            if (readByte2 != b) {
                throw new IOException("Invalid marker:" + Integer.toHexString(readByte2 & 255));
            }
            int i5 = i4 + 1;
            byte readByte3 = byteOrderedDataInputStream.readByte();
            if (z3) {
                Log.d("ExifInterface", "Found JPEG segment indicator: " + Integer.toHexString(readByte3 & 255));
            }
            int i6 = i5 + 1;
            if (readByte3 != -39 && readByte3 != -38) {
                int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort() - i3;
                int i7 = i6 + i3;
                if (z3) {
                    Log.d("ExifInterface", "JPEG segment: " + Integer.toHexString(readByte3 & 255) + " (length: " + (readUnsignedShort + 2) + ")");
                }
                if (readUnsignedShort < 0) {
                    throw new IOException("Invalid length");
                }
                HashMap[] hashMapArr = this.mAttributes;
                if (readByte3 == -31) {
                    byte[] bArr = new byte[readUnsignedShort];
                    byteOrderedDataInputStream.readFully(bArr);
                    int i8 = i7 + readUnsignedShort;
                    byte[] bArr2 = IDENTIFIER_EXIF_APP1;
                    if (bArr2 != null && readUnsignedShort >= bArr2.length) {
                        for (int i9 = 0; i9 < bArr2.length; i9++) {
                            if (bArr[i9] == bArr2[i9]) {
                            }
                        }
                        z = true;
                        if (z) {
                            byte[] bArr3 = IDENTIFIER_XMP_APP1;
                            if (bArr3 != null && readUnsignedShort >= bArr3.length) {
                                for (int i10 = 0; i10 < bArr3.length; i10++) {
                                    if (bArr[i10] == bArr3[i10]) {
                                    }
                                }
                                z2 = true;
                                if (z2) {
                                    int length = bArr3.length + i7;
                                    byte[] copyOfRange = Arrays.copyOfRange(bArr, bArr3.length, readUnsignedShort);
                                    if (getAttribute("Xmp") == null) {
                                        hashMapArr[0].put("Xmp", new ExifAttribute(1, copyOfRange.length, length, copyOfRange));
                                        this.mXmpIsFromSeparateMarker = true;
                                    }
                                }
                            }
                            z2 = false;
                            if (z2) {
                            }
                        } else {
                            byte[] copyOfRange2 = Arrays.copyOfRange(bArr, bArr2.length, readUnsignedShort);
                            this.mOffsetToExifData = i + i7 + bArr2.length;
                            readExifSegment(i2, copyOfRange2);
                            setThumbnailData(new ByteOrderedDataInputStream(copyOfRange2));
                        }
                        i7 = i8;
                    }
                    z = false;
                    if (z) {
                    }
                    i7 = i8;
                } else if (readByte3 != -2) {
                    switch (readByte3) {
                        case -64:
                        case -63:
                        case -62:
                        case -61:
                            break;
                        default:
                            switch (readByte3) {
                                case -59:
                                case -58:
                                case CustomDeviceManager.ERROR_INVALID_MEDIA /* -57 */:
                                    break;
                                default:
                                    switch (readByte3) {
                                        case CustomDeviceManager.ERROR_ALREADY_EXISTS /* -55 */:
                                        case CustomDeviceManager.ERROR_NOT_FOUND /* -54 */:
                                        case CustomDeviceManager.ERROR_INVALID_CURRENT /* -53 */:
                                            break;
                                        default:
                                            switch (readByte3) {
                                            }
                                    }
                            }
                    }
                    byteOrderedDataInputStream.skipFully(1);
                    hashMapArr[i2].put(i2 != 4 ? "ImageLength" : "ThumbnailImageLength", ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                    hashMapArr[i2].put(i2 != 4 ? "ImageWidth" : "ThumbnailImageWidth", ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                    readUnsignedShort -= 5;
                    if (readUnsignedShort >= 0) {
                        throw new IOException("Invalid length");
                    }
                    byteOrderedDataInputStream.skipFully(readUnsignedShort);
                    i4 = i7 + readUnsignedShort;
                    i3 = 2;
                    b = -1;
                } else {
                    byte[] bArr4 = new byte[readUnsignedShort];
                    byteOrderedDataInputStream.readFully(bArr4);
                    if (getAttribute("UserComment") == null) {
                        hashMapArr[1].put("UserComment", ExifAttribute.createString(new String(bArr4, ASCII)));
                    }
                }
                readUnsignedShort = 0;
                if (readUnsignedShort >= 0) {
                }
            }
        }
        byteOrderedDataInputStream.mByteOrder = this.mExifByteOrder;
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x00ca, code lost:
    
        if (r8 != null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0177, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x010c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x013e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getMimeType(BufferedInputStream bufferedInputStream) {
        boolean z;
        boolean z2;
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        ByteOrderedDataInputStream byteOrderedDataInputStream2;
        boolean z3;
        ByteOrderedDataInputStream byteOrderedDataInputStream3;
        ByteOrderedDataInputStream byteOrderedDataInputStream4;
        boolean z4;
        ByteOrderedDataInputStream byteOrderedDataInputStream5;
        ByteOrderedDataInputStream byteOrderedDataInputStream6;
        boolean z5;
        boolean z6;
        boolean z7;
        long j;
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        int i = 0;
        while (true) {
            byte[] bArr2 = JPEG_SIGNATURE;
            if (i >= bArr2.length) {
                z = true;
                break;
            }
            if (bArr[i] != bArr2[i]) {
                z = false;
                break;
            }
            i++;
        }
        if (z) {
            return 4;
        }
        byte[] bytes = "FUJIFILMCCD-RAW".getBytes(Charset.defaultCharset());
        int i2 = 0;
        while (true) {
            if (i2 >= bytes.length) {
                z2 = true;
                break;
            }
            if (bArr[i2] != bytes[i2]) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (z2) {
            return 9;
        }
        try {
            byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(bArr);
            try {
                long readInt = byteOrderedDataInputStream2.readInt();
                byte[] bArr3 = new byte[4];
                byteOrderedDataInputStream2.readFully(bArr3);
                if (Arrays.equals(bArr3, HEIF_TYPE_FTYP)) {
                    if (readInt == 1) {
                        readInt = byteOrderedDataInputStream2.readLong();
                        j = 16;
                        if (readInt < 16) {
                        }
                    } else {
                        j = 8;
                    }
                    long j2 = 5000;
                    if (readInt > j2) {
                        readInt = j2;
                    }
                    long j3 = readInt - j;
                    if (j3 >= 8) {
                        byte[] bArr4 = new byte[4];
                        boolean z8 = false;
                        boolean z9 = false;
                        for (long j4 = 0; j4 < j3 / 4; j4++) {
                            try {
                                byteOrderedDataInputStream2.readFully(bArr4);
                                if (j4 != 1) {
                                    if (Arrays.equals(bArr4, HEIF_BRAND_MIF1)) {
                                        z8 = true;
                                    } else if (Arrays.equals(bArr4, HEIF_BRAND_HEIC)) {
                                        z9 = true;
                                    }
                                    if (z8 && z9) {
                                        byteOrderedDataInputStream2.close();
                                        z3 = true;
                                        break;
                                    }
                                }
                            } catch (EOFException unused) {
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e = e;
                try {
                    if (DEBUG) {
                        Log.d("ExifInterface", "Exception parsing HEIF file type box.", e);
                    }
                } catch (Throwable th) {
                    th = th;
                    byteOrderedDataInputStream = byteOrderedDataInputStream2;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (byteOrderedDataInputStream2 != null) {
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            byteOrderedDataInputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            byteOrderedDataInputStream = null;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
            }
            throw th;
        }
        byteOrderedDataInputStream2.close();
        z3 = false;
        if (z3) {
            return 12;
        }
        try {
            byteOrderedDataInputStream4 = new ByteOrderedDataInputStream(bArr);
            try {
                ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream4);
                this.mExifByteOrder = readByteOrder;
                byteOrderedDataInputStream4.mByteOrder = readByteOrder;
                short readShort = byteOrderedDataInputStream4.readShort();
                z4 = readShort == 20306 || readShort == 21330;
                byteOrderedDataInputStream4.close();
            } catch (Exception unused2) {
                if (byteOrderedDataInputStream4 != null) {
                    byteOrderedDataInputStream4.close();
                }
                z4 = false;
                if (!z4) {
                }
            } catch (Throwable th4) {
                th = th4;
                byteOrderedDataInputStream3 = byteOrderedDataInputStream4;
                if (byteOrderedDataInputStream3 != null) {
                    byteOrderedDataInputStream3.close();
                }
                throw th;
            }
        } catch (Exception unused3) {
            byteOrderedDataInputStream4 = null;
        } catch (Throwable th5) {
            th = th5;
            byteOrderedDataInputStream3 = null;
        }
        if (!z4) {
            return 7;
        }
        try {
            ByteOrderedDataInputStream byteOrderedDataInputStream7 = new ByteOrderedDataInputStream(bArr);
            try {
                ByteOrder readByteOrder2 = readByteOrder(byteOrderedDataInputStream7);
                this.mExifByteOrder = readByteOrder2;
                byteOrderedDataInputStream7.mByteOrder = readByteOrder2;
                z5 = byteOrderedDataInputStream7.readShort() == 85;
                byteOrderedDataInputStream7.close();
            } catch (Exception unused4) {
                byteOrderedDataInputStream6 = byteOrderedDataInputStream7;
                if (byteOrderedDataInputStream6 != null) {
                    byteOrderedDataInputStream6.close();
                }
                z5 = false;
                if (!z5) {
                }
            } catch (Throwable th6) {
                th = th6;
                byteOrderedDataInputStream5 = byteOrderedDataInputStream7;
                if (byteOrderedDataInputStream5 != null) {
                    byteOrderedDataInputStream5.close();
                }
                throw th;
            }
        } catch (Exception unused5) {
            byteOrderedDataInputStream6 = null;
        } catch (Throwable th7) {
            th = th7;
            byteOrderedDataInputStream5 = null;
        }
        if (!z5) {
            return 10;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr5 = PNG_SIGNATURE;
            if (i3 >= bArr5.length) {
                z6 = true;
                break;
            }
            if (bArr[i3] != bArr5[i3]) {
                z6 = false;
                break;
            }
            i3++;
        }
        if (z6) {
            return 13;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr6 = WEBP_SIGNATURE_1;
            if (i4 >= bArr6.length) {
                int i5 = 0;
                while (true) {
                    byte[] bArr7 = WEBP_SIGNATURE_2;
                    if (i5 >= bArr7.length) {
                        z7 = true;
                        break;
                    }
                    if (bArr[bArr6.length + i5 + 4] != bArr7[i5]) {
                        break;
                    }
                    i5++;
                }
            } else {
                if (bArr[i4] != bArr6[i4]) {
                    break;
                }
                i4++;
            }
        }
        return z7 ? 14 : 0;
    }

    public final void getOrfAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        int i;
        int i2;
        getRawAttributes(seekableByteOrderedDataInputStream);
        HashMap[] hashMapArr = this.mAttributes;
        ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[1].get("MakerNote");
        if (exifAttribute != null) {
            SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
            seekableByteOrderedDataInputStream2.mByteOrder = this.mExifByteOrder;
            byte[] bArr = ORF_MAKER_NOTE_HEADER_1;
            byte[] bArr2 = new byte[bArr.length];
            seekableByteOrderedDataInputStream2.readFully(bArr2);
            seekableByteOrderedDataInputStream2.seek(0L);
            byte[] bArr3 = ORF_MAKER_NOTE_HEADER_2;
            byte[] bArr4 = new byte[bArr3.length];
            seekableByteOrderedDataInputStream2.readFully(bArr4);
            if (Arrays.equals(bArr2, bArr)) {
                seekableByteOrderedDataInputStream2.seek(8L);
            } else if (Arrays.equals(bArr4, bArr3)) {
                seekableByteOrderedDataInputStream2.seek(12L);
            }
            readImageFileDirectory(seekableByteOrderedDataInputStream2, 6);
            ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[7].get("PreviewImageStart");
            ExifAttribute exifAttribute3 = (ExifAttribute) hashMapArr[7].get("PreviewImageLength");
            if (exifAttribute2 != null && exifAttribute3 != null) {
                hashMapArr[5].put("JPEGInterchangeFormat", exifAttribute2);
                hashMapArr[5].put("JPEGInterchangeFormatLength", exifAttribute3);
            }
            ExifAttribute exifAttribute4 = (ExifAttribute) hashMapArr[8].get("AspectFrame");
            if (exifAttribute4 != null) {
                int[] iArr = (int[]) exifAttribute4.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 4) {
                    Log.w("ExifInterface", "Invalid aspect frame values. frame=" + Arrays.toString(iArr));
                    return;
                }
                int i3 = iArr[2];
                int i4 = iArr[0];
                if (i3 <= i4 || (i = iArr[3]) <= (i2 = iArr[1])) {
                    return;
                }
                int i5 = (i3 - i4) + 1;
                int i6 = (i - i2) + 1;
                if (i5 < i6) {
                    int i7 = i5 + i6;
                    i6 = i7 - i6;
                    i5 = i7 - i6;
                }
                ExifAttribute createUShort = ExifAttribute.createUShort(i5, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(i6, this.mExifByteOrder);
                hashMapArr[0].put("ImageWidth", createUShort);
                hashMapArr[0].put("ImageLength", createUShort2);
            }
        }
    }

    public final void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        if (DEBUG) {
            Log.d("ExifInterface", "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArr = PNG_SIGNATURE;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 0;
        while (true) {
            try {
                int readInt = byteOrderedDataInputStream.readInt();
                byte[] bArr2 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr2);
                int i = length + 4 + 4;
                if (i == 16 && !Arrays.equals(bArr2, PNG_CHUNK_TYPE_IHDR)) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_IEND)) {
                    return;
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_EXIF)) {
                    byte[] bArr3 = new byte[readInt];
                    byteOrderedDataInputStream.readFully(bArr3);
                    int readInt2 = byteOrderedDataInputStream.readInt();
                    CRC32 crc32 = new CRC32();
                    crc32.update(bArr2);
                    crc32.update(bArr3);
                    if (((int) crc32.getValue()) == readInt2) {
                        this.mOffsetToExifData = i;
                        readExifSegment(0, bArr3);
                        validateImages();
                        setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                        return;
                    }
                    throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt2 + ", calculated CRC value: " + crc32.getValue());
                }
                int i2 = readInt + 4;
                byteOrderedDataInputStream.skipFully(i2);
                length = i + i2;
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    public final void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        boolean z = DEBUG;
        if (z) {
            Log.d("ExifInterface", "getRafAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.skipFully(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[4];
        byteOrderedDataInputStream.readFully(bArr);
        byteOrderedDataInputStream.readFully(bArr2);
        byteOrderedDataInputStream.readFully(bArr3);
        int i = ByteBuffer.wrap(bArr).getInt();
        int i2 = ByteBuffer.wrap(bArr2).getInt();
        int i3 = ByteBuffer.wrap(bArr3).getInt();
        byte[] bArr4 = new byte[i2];
        byteOrderedDataInputStream.skipFully(i - byteOrderedDataInputStream.mPosition);
        byteOrderedDataInputStream.readFully(bArr4);
        getJpegAttributes(new ByteOrderedDataInputStream(bArr4), i, 5);
        byteOrderedDataInputStream.skipFully(i3 - byteOrderedDataInputStream.mPosition);
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        int readInt = byteOrderedDataInputStream.readInt();
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("numberOfDirectoryEntry: ", readInt, "ExifInterface");
        }
        for (int i4 = 0; i4 < readInt; i4++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                ExifAttribute createUShort = ExifAttribute.createUShort(readShort, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(readShort2, this.mExifByteOrder);
                HashMap[] hashMapArr = this.mAttributes;
                hashMapArr[0].put("ImageLength", createUShort);
                hashMapArr[0].put("ImageWidth", createUShort2);
                if (z) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("Updated to length: ", readShort, ", width: ", readShort2, "ExifInterface");
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipFully(readUnsignedShort2);
        }
    }

    public final void getRawAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 5);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType == 8) {
            HashMap[] hashMapArr = this.mAttributes;
            ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[1].get("MakerNote");
            if (exifAttribute != null) {
                SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
                seekableByteOrderedDataInputStream2.mByteOrder = this.mExifByteOrder;
                seekableByteOrderedDataInputStream2.skipFully(6);
                readImageFileDirectory(seekableByteOrderedDataInputStream2, 9);
                ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[9].get("ColorSpace");
                if (exifAttribute2 != null) {
                    hashMapArr[1].put("ColorSpace", exifAttribute2);
                }
            }
        }
    }

    public final void getRw2Attributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        if (DEBUG) {
            Log.d("ExifInterface", "getRw2Attributes starting with: " + seekableByteOrderedDataInputStream);
        }
        getRawAttributes(seekableByteOrderedDataInputStream);
        HashMap[] hashMapArr = this.mAttributes;
        ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[0].get("JpgFromRaw");
        if (exifAttribute != null) {
            getJpegAttributes(new ByteOrderedDataInputStream(exifAttribute.bytes), (int) exifAttribute.bytesOffset, 5);
        }
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[0].get("ISO");
        ExifAttribute exifAttribute3 = (ExifAttribute) hashMapArr[1].get("PhotographicSensitivity");
        if (exifAttribute2 == null || exifAttribute3 != null) {
            return;
        }
        hashMapArr[1].put("PhotographicSensitivity", exifAttribute2);
    }

    public final boolean getStandaloneAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        byte[] bArr2 = new byte[bArr.length];
        seekableByteOrderedDataInputStream.readFully(bArr2);
        if (!Arrays.equals(bArr2, bArr)) {
            Log.w("ExifInterface", "Given data is not EXIF-only.");
            return false;
        }
        byte[] bArr3 = new byte[1024];
        int i = 0;
        while (true) {
            if (i == bArr3.length) {
                bArr3 = Arrays.copyOf(bArr3, bArr3.length * 2);
            }
            int read = seekableByteOrderedDataInputStream.mDataInputStream.read(bArr3, i, bArr3.length - i);
            if (read == -1) {
                byte[] copyOf = Arrays.copyOf(bArr3, i);
                this.mOffsetToExifData = bArr.length;
                readExifSegment(0, copyOf);
                return true;
            }
            i += read;
            seekableByteOrderedDataInputStream.mPosition += read;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] getThumbnailBytes() {
        FileDescriptor fileDescriptor;
        InputStream inputStream;
        InputStream inputStream2 = null;
        if (!this.mHasThumbnail) {
            return null;
        }
        byte[] bArr = this.mThumbnailBytes;
        if (bArr != null) {
            return bArr;
        }
        try {
            inputStream = this.mAssetInputStream;
            try {
                try {
                    if (inputStream != null) {
                        try {
                            if (!inputStream.markSupported()) {
                                Log.d("ExifInterface", "Cannot read thumbnail from inputstream without mark/reset support");
                                ExifInterfaceUtils.closeQuietly(inputStream);
                                return null;
                            }
                            inputStream.reset();
                        } catch (Exception e) {
                            e = e;
                            fileDescriptor = null;
                            Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                            ExifInterfaceUtils.closeQuietly(inputStream);
                            if (fileDescriptor != null) {
                            }
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            fileDescriptor = null;
                            inputStream2 = inputStream;
                            ExifInterfaceUtils.closeQuietly(inputStream2);
                            if (fileDescriptor != null) {
                            }
                            throw th;
                        }
                    } else {
                        if (this.mFilename == null) {
                            FileDescriptor dup = Os.dup(this.mSeekableFileDescriptor);
                            try {
                                Os.lseek(dup, 0L, OsConstants.SEEK_SET);
                                fileDescriptor = dup;
                                inputStream = new FileInputStream(dup);
                                ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
                                byteOrderedDataInputStream.skipFully(this.mThumbnailOffset + this.mOffsetToExifData);
                                byte[] bArr2 = new byte[this.mThumbnailLength];
                                byteOrderedDataInputStream.readFully(bArr2);
                                this.mThumbnailBytes = bArr2;
                                ExifInterfaceUtils.closeQuietly(inputStream);
                                if (fileDescriptor != null) {
                                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                                }
                                return bArr2;
                            } catch (Exception e2) {
                                e = e2;
                                fileDescriptor = dup;
                                inputStream = null;
                                Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                                ExifInterfaceUtils.closeQuietly(inputStream);
                                if (fileDescriptor != null) {
                                }
                                return null;
                            } catch (Throwable th2) {
                                th = th2;
                                fileDescriptor = dup;
                                ExifInterfaceUtils.closeQuietly(inputStream2);
                                if (fileDescriptor != null) {
                                }
                                throw th;
                            }
                        }
                        inputStream = new FileInputStream(this.mFilename);
                    }
                    ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(inputStream);
                    byteOrderedDataInputStream2.skipFully(this.mThumbnailOffset + this.mOffsetToExifData);
                    byte[] bArr22 = new byte[this.mThumbnailLength];
                    byteOrderedDataInputStream2.readFully(bArr22);
                    this.mThumbnailBytes = bArr22;
                    ExifInterfaceUtils.closeQuietly(inputStream);
                    if (fileDescriptor != null) {
                    }
                    return bArr22;
                } catch (Exception e3) {
                    e = e3;
                    Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                    ExifInterfaceUtils.closeQuietly(inputStream);
                    if (fileDescriptor != null) {
                        ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                    }
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream2 = inputStream;
                ExifInterfaceUtils.closeQuietly(inputStream2);
                if (fileDescriptor != null) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                throw th;
            }
            fileDescriptor = null;
        } catch (Exception e4) {
            e = e4;
            inputStream = null;
            fileDescriptor = null;
        } catch (Throwable th4) {
            th = th4;
            fileDescriptor = null;
        }
    }

    public final void getWebpAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        if (DEBUG) {
            Log.d("ExifInterface", "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.LITTLE_ENDIAN;
        byteOrderedDataInputStream.skipFully(WEBP_SIGNATURE_1.length);
        int readInt = byteOrderedDataInputStream.readInt() + 8;
        byte[] bArr = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 8;
        while (true) {
            try {
                byte[] bArr2 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr2);
                int readInt2 = byteOrderedDataInputStream.readInt();
                int i = length + 4 + 4;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr2)) {
                    byte[] bArr3 = new byte[readInt2];
                    byteOrderedDataInputStream.readFully(bArr3);
                    this.mOffsetToExifData = i;
                    readExifSegment(0, bArr3);
                    setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                    return;
                }
                if (readInt2 % 2 == 1) {
                    readInt2++;
                }
                length = i + readInt2;
                if (length == readInt) {
                    return;
                }
                if (length > readInt) {
                    throw new IOException("Encountered WebP file with invalid chunk size");
                }
                byteOrderedDataInputStream.skipFully(readInt2);
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    public final void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get("JPEGInterchangeFormat");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get("JPEGInterchangeFormatLength");
        if (exifAttribute == null || exifAttribute2 == null) {
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        if (this.mMimeType == 7) {
            intValue += this.mOrfMakerNoteOffset;
        }
        if (intValue > 0 && intValue2 > 0) {
            this.mHasThumbnail = true;
            if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                byte[] bArr = new byte[intValue2];
                byteOrderedDataInputStream.skipFully(intValue);
                byteOrderedDataInputStream.readFully(bArr);
                this.mThumbnailBytes = bArr;
            }
            this.mThumbnailOffset = intValue;
            this.mThumbnailLength = intValue2;
        }
        if (DEBUG) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("Setting thumbnail attributes with offset: ", intValue, ", length: ", intValue2, "ExifInterface");
        }
    }

    public final void initForFilename(String str) {
        if (str == null) {
            throw new NullPointerException("filename cannot be null");
        }
        FileInputStream fileInputStream = null;
        this.mAssetInputStream = null;
        this.mFilename = str;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                if (isSeekableFD(fileInputStream2.getFD())) {
                    this.mSeekableFileDescriptor = fileInputStream2.getFD();
                } else {
                    this.mSeekableFileDescriptor = null;
                }
                loadAttributes(fileInputStream2);
                ExifInterfaceUtils.closeQuietly(fileInputStream2);
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                ExifInterfaceUtils.closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final boolean isThumbnail(HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get("ImageLength");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get("ImageWidth");
        if (exifAttribute == null || exifAttribute2 == null) {
            return false;
        }
        return exifAttribute.getIntValue(this.mExifByteOrder) <= 512 && exifAttribute2.getIntValue(this.mExifByteOrder) <= 512;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003e A[Catch: all -> 0x009e, IOException | UnsupportedOperationException -> 0x00a0, IOException | UnsupportedOperationException -> 0x00a0, TryCatch #0 {IOException | UnsupportedOperationException -> 0x00a0, blocks: (B:3:0x0004, B:5:0x0009, B:10:0x0019, B:10:0x0019, B:11:0x0027, B:11:0x0027, B:19:0x003e, B:19:0x003e, B:21:0x0045, B:21:0x0045, B:29:0x0070, B:29:0x0070, B:35:0x0054, B:35:0x0054, B:37:0x005a, B:37:0x005a, B:40:0x0061, B:40:0x0061, B:43:0x0069, B:43:0x0069, B:44:0x006d, B:44:0x006d, B:45:0x007a, B:45:0x007a, B:47:0x0083, B:47:0x0083, B:49:0x0089, B:49:0x0089, B:51:0x008f, B:51:0x008f, B:53:0x0095, B:53:0x0095), top: B:2:0x0004, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007a A[Catch: all -> 0x009e, IOException | UnsupportedOperationException -> 0x00a0, IOException | UnsupportedOperationException -> 0x00a0, TryCatch #0 {IOException | UnsupportedOperationException -> 0x00a0, blocks: (B:3:0x0004, B:5:0x0009, B:10:0x0019, B:10:0x0019, B:11:0x0027, B:11:0x0027, B:19:0x003e, B:19:0x003e, B:21:0x0045, B:21:0x0045, B:29:0x0070, B:29:0x0070, B:35:0x0054, B:35:0x0054, B:37:0x005a, B:37:0x005a, B:40:0x0061, B:40:0x0061, B:43:0x0069, B:43:0x0069, B:44:0x006d, B:44:0x006d, B:45:0x007a, B:45:0x007a, B:47:0x0083, B:47:0x0083, B:49:0x0089, B:49:0x0089, B:51:0x008f, B:51:0x008f, B:53:0x0095, B:53:0x0095), top: B:2:0x0004, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadAttributes(InputStream inputStream) {
        boolean z;
        boolean z2 = DEBUG;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            try {
                try {
                    this.mAttributes[i] = new HashMap();
                } catch (IOException | UnsupportedOperationException e) {
                    if (z2) {
                        Log.w("ExifInterface", "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    }
                    addDefaultValuesForCompatibility();
                    if (!z2) {
                        return;
                    }
                }
            } catch (Throwable th) {
                addDefaultValuesForCompatibility();
                if (z2) {
                    printAttributes();
                }
                throw th;
            }
        }
        boolean z3 = this.mIsExifDataOnly;
        if (!z3) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
            this.mMimeType = getMimeType(bufferedInputStream);
            inputStream = bufferedInputStream;
        }
        int i2 = this.mMimeType;
        if (i2 != 4 && i2 != 9 && i2 != 13 && i2 != 14) {
            z = true;
            if (z) {
                ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
                int i3 = this.mMimeType;
                if (i3 == 4) {
                    getJpegAttributes(byteOrderedDataInputStream, 0, 0);
                } else if (i3 == 13) {
                    getPngAttributes(byteOrderedDataInputStream);
                } else if (i3 == 9) {
                    getRafAttributes(byteOrderedDataInputStream);
                } else if (i3 == 14) {
                    getWebpAttributes(byteOrderedDataInputStream);
                }
            } else {
                SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(inputStream);
                if (!z3) {
                    int i4 = this.mMimeType;
                    if (i4 == 12) {
                        getHeifAttributes(seekableByteOrderedDataInputStream);
                    } else if (i4 == 7) {
                        getOrfAttributes(seekableByteOrderedDataInputStream);
                    } else if (i4 == 10) {
                        getRw2Attributes(seekableByteOrderedDataInputStream);
                    } else {
                        getRawAttributes(seekableByteOrderedDataInputStream);
                    }
                } else if (!getStandaloneAttributes(seekableByteOrderedDataInputStream)) {
                    addDefaultValuesForCompatibility();
                    if (z2) {
                        printAttributes();
                        return;
                    }
                    return;
                }
                seekableByteOrderedDataInputStream.seek(this.mOffsetToExifData);
                setThumbnailData(seekableByteOrderedDataInputStream);
            }
            addDefaultValuesForCompatibility();
            if (!z2) {
                return;
            }
            printAttributes();
        }
        z = false;
        if (z) {
        }
        addDefaultValuesForCompatibility();
        if (!z2) {
        }
        printAttributes();
    }

    public final void parseTiffHeaders(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream);
        this.mExifByteOrder = readByteOrder;
        byteOrderedDataInputStream.mByteOrder = readByteOrder;
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        int i = this.mMimeType;
        if (i != 7 && i != 10 && readUnsignedShort != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(readUnsignedShort));
        }
        int readInt = byteOrderedDataInputStream.readInt();
        if (readInt < 8) {
            throw new IOException(AbstractC0000x2c234b15.m0m("Invalid first Ifd offset: ", readInt));
        }
        int i2 = readInt - 8;
        if (i2 > 0) {
            byteOrderedDataInputStream.skipFully(i2);
        }
    }

    public final void printAttributes() {
        int i = 0;
        while (true) {
            HashMap[] hashMapArr = this.mAttributes;
            if (i >= hashMapArr.length) {
                return;
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("The size of tag group[", i, "]: ");
            m1m.append(hashMapArr[i].size());
            Log.d("ExifInterface", m1m.toString());
            for (Map.Entry entry : hashMapArr[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) entry.getValue();
                Log.d("ExifInterface", "tagName: " + ((String) entry.getKey()) + ", tagType: " + exifAttribute.toString() + ", tagValue: '" + exifAttribute.getStringValue(this.mExifByteOrder) + "'");
            }
            i++;
        }
    }

    public final void readExifSegment(int i, byte[] bArr) {
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(bArr);
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x026e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readImageFileDirectory(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) {
        HashMap[] hashMapArr;
        String str;
        short s;
        boolean z;
        boolean z2;
        HashSet hashSet;
        short s2;
        HashMap[] hashMapArr2;
        int i2;
        boolean z3;
        HashMap[] hashMapArr3;
        long j;
        int i3;
        long j2;
        int i4;
        boolean z4;
        String str2;
        HashSet hashSet2;
        int readUnsignedShort;
        long j3;
        HashSet hashSet3;
        int i5;
        String str3;
        int i6 = i;
        Integer valueOf = Integer.valueOf(seekableByteOrderedDataInputStream.mPosition);
        HashSet hashSet4 = (HashSet) this.mAttributesOffsets;
        hashSet4.add(valueOf);
        short readShort = seekableByteOrderedDataInputStream.readShort();
        boolean z5 = DEBUG;
        String str4 = "ExifInterface";
        if (z5) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("numberOfDirectoryEntry: ", readShort, "ExifInterface");
        }
        if (readShort <= 0) {
            return;
        }
        short s3 = 0;
        while (true) {
            hashMapArr = this.mAttributes;
            if (s3 >= readShort) {
                break;
            }
            int readUnsignedShort2 = seekableByteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort3 = seekableByteOrderedDataInputStream.readUnsignedShort();
            int readInt = seekableByteOrderedDataInputStream.readInt();
            long j4 = seekableByteOrderedDataInputStream.mPosition + 4;
            ExifTag exifTag = (ExifTag) sExifTagMapsForReading[i6].get(Integer.valueOf(readUnsignedShort2));
            if (z5) {
                Object[] objArr = new Object[5];
                z2 = false;
                objArr[0] = Integer.valueOf(i);
                z = true;
                objArr[1] = Integer.valueOf(readUnsignedShort2);
                if (exifTag != null) {
                    s = readShort;
                    str3 = exifTag.name;
                } else {
                    s = readShort;
                    str3 = null;
                }
                objArr[2] = str3;
                objArr[3] = Integer.valueOf(readUnsignedShort3);
                objArr[4] = Integer.valueOf(readInt);
                Log.d(str4, String.format("ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d", objArr));
            } else {
                s = readShort;
                z = true;
                z2 = false;
            }
            if (exifTag == null) {
                if (z5) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("Skip the tag entry since tag number is not defined: ", readUnsignedShort2, str4);
                }
                hashSet = hashSet4;
                s2 = s3;
            } else {
                if (readUnsignedShort3 > 0) {
                    if (readUnsignedShort3 < IFD_FORMAT_BYTES_PER_FORMAT.length) {
                        int i7 = exifTag.primaryFormat;
                        s2 = s3;
                        if (i7 == 7 || readUnsignedShort3 == 7 || i7 == readUnsignedShort3 || (i3 = exifTag.secondaryFormat) == readUnsignedShort3) {
                            hashSet = hashSet4;
                        } else {
                            hashSet = hashSet4;
                            if (((i7 != 4 && i3 != 4) || readUnsignedShort3 != 3) && (((i7 != 9 && i3 != 9) || readUnsignedShort3 != 8) && ((i7 != 12 && i3 != 12) || readUnsignedShort3 != 11))) {
                                z3 = z2;
                                if (!z3) {
                                    if (readUnsignedShort3 == 7) {
                                        hashMapArr3 = hashMapArr;
                                        i2 = readUnsignedShort2;
                                        readUnsignedShort3 = i7;
                                    } else {
                                        hashMapArr3 = hashMapArr;
                                        i2 = readUnsignedShort2;
                                    }
                                    hashMapArr2 = hashMapArr3;
                                    j = readInt * r3[readUnsignedShort3];
                                    if (j >= 0 && j <= 2147483647L) {
                                        z2 = z;
                                    } else if (z5) {
                                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Skip the tag entry since the number of components is invalid: ", readInt, str4);
                                    }
                                    if (z2) {
                                        if (j > 4) {
                                            int readInt2 = seekableByteOrderedDataInputStream.readInt();
                                            if (z5) {
                                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("seek to data offset: ", readInt2, str4);
                                            }
                                            j2 = j4;
                                            if (this.mMimeType == 7) {
                                                if ("MakerNote".equals(exifTag.name)) {
                                                    this.mOrfMakerNoteOffset = readInt2;
                                                } else if (i6 == 6 && "ThumbnailImage".equals(exifTag.name)) {
                                                    this.mOrfThumbnailOffset = readInt2;
                                                    this.mOrfThumbnailLength = readInt;
                                                    ExifAttribute createUShort = ExifAttribute.createUShort(6, this.mExifByteOrder);
                                                    ExifAttribute createULong = ExifAttribute.createULong(this.mOrfThumbnailOffset, this.mExifByteOrder);
                                                    i4 = readInt;
                                                    ExifAttribute createULong2 = ExifAttribute.createULong(this.mOrfThumbnailLength, this.mExifByteOrder);
                                                    hashMapArr2[4].put("Compression", createUShort);
                                                    hashMapArr2[4].put("JPEGInterchangeFormat", createULong);
                                                    hashMapArr2[4].put("JPEGInterchangeFormatLength", createULong2);
                                                    seekableByteOrderedDataInputStream.seek(readInt2);
                                                }
                                            }
                                            i4 = readInt;
                                            seekableByteOrderedDataInputStream.seek(readInt2);
                                        } else {
                                            j2 = j4;
                                            i4 = readInt;
                                        }
                                        Integer num = (Integer) sExifPointerTagMap.get(Integer.valueOf(i2));
                                        if (z5) {
                                            Log.d(str4, "nextIfdType: " + num + " byteCount: " + j);
                                        }
                                        if (num != null) {
                                            if (readUnsignedShort3 != 3) {
                                                if (readUnsignedShort3 == 4) {
                                                    j3 = seekableByteOrderedDataInputStream.readInt() & 4294967295L;
                                                } else if (readUnsignedShort3 == 8) {
                                                    readUnsignedShort = seekableByteOrderedDataInputStream.readShort();
                                                } else if (readUnsignedShort3 == 9 || readUnsignedShort3 == 13) {
                                                    readUnsignedShort = seekableByteOrderedDataInputStream.readInt();
                                                } else {
                                                    j3 = -1;
                                                }
                                                if (z5) {
                                                    Log.d(str4, String.format("Offset: %d, tagName: %s", Long.valueOf(j3), exifTag.name));
                                                }
                                                if (j3 > 0 || ((i5 = seekableByteOrderedDataInputStream.mLength) != -1 && j3 >= i5)) {
                                                    hashSet3 = hashSet;
                                                    if (z5) {
                                                        String m25m = ValueAnimator$$ExternalSyntheticOutline0.m25m("Skip jump into the IFD since its offset is invalid: ", j3);
                                                        if (seekableByteOrderedDataInputStream.mLength != -1) {
                                                            m25m = ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m2m(m25m, " (total length: "), seekableByteOrderedDataInputStream.mLength, ")");
                                                        }
                                                        Log.d(str4, m25m);
                                                    }
                                                } else {
                                                    hashSet3 = hashSet;
                                                    if (!hashSet3.contains(Integer.valueOf((int) j3))) {
                                                        seekableByteOrderedDataInputStream.seek(j3);
                                                        readImageFileDirectory(seekableByteOrderedDataInputStream, num.intValue());
                                                    } else if (z5) {
                                                        StringBuilder sb = new StringBuilder("Skip jump into the IFD since it has already been read: IfdType ");
                                                        sb.append(num);
                                                        sb.append(" (at ");
                                                        sb.append(j3);
                                                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, ")", str4);
                                                    }
                                                }
                                                seekableByteOrderedDataInputStream.seek(j2);
                                                z4 = z5;
                                                str2 = str4;
                                                hashSet2 = hashSet3;
                                            } else {
                                                readUnsignedShort = seekableByteOrderedDataInputStream.readUnsignedShort();
                                            }
                                            j3 = readUnsignedShort;
                                            if (z5) {
                                            }
                                            if (j3 > 0) {
                                            }
                                            hashSet3 = hashSet;
                                            if (z5) {
                                            }
                                            seekableByteOrderedDataInputStream.seek(j2);
                                            z4 = z5;
                                            str2 = str4;
                                            hashSet2 = hashSet3;
                                        } else {
                                            long j5 = j2;
                                            int i8 = seekableByteOrderedDataInputStream.mPosition + this.mOffsetToExifData;
                                            byte[] bArr = new byte[(int) j];
                                            seekableByteOrderedDataInputStream.readFully(bArr);
                                            long j6 = i8;
                                            z4 = z5;
                                            str2 = str4;
                                            hashSet2 = hashSet;
                                            ExifAttribute exifAttribute = new ExifAttribute(readUnsignedShort3, i4, j6, bArr);
                                            hashMapArr2[i].put(exifTag.name, exifAttribute);
                                            String str5 = exifTag.name;
                                            if ("DNGVersion".equals(str5)) {
                                                this.mMimeType = 3;
                                            }
                                            if ((("Make".equals(str5) || "Model".equals(str5)) && exifAttribute.getStringValue(this.mExifByteOrder).contains("PENTAX")) || ("Compression".equals(str5) && exifAttribute.getIntValue(this.mExifByteOrder) == 65535)) {
                                                this.mMimeType = 8;
                                            }
                                            if (seekableByteOrderedDataInputStream.mPosition != j5) {
                                                seekableByteOrderedDataInputStream.seek(j5);
                                            }
                                        }
                                    } else {
                                        seekableByteOrderedDataInputStream.seek(j4);
                                        z4 = z5;
                                        str2 = str4;
                                        hashSet2 = hashSet;
                                    }
                                    s3 = (short) (s2 + 1);
                                    z5 = z4;
                                    hashSet4 = hashSet2;
                                    str4 = str2;
                                    readShort = s;
                                    i6 = i;
                                } else if (z5) {
                                    StringBuilder sb2 = new StringBuilder("Skip the tag entry since data format (");
                                    sb2.append(IFD_FORMAT_NAMES[readUnsignedShort3]);
                                    sb2.append(") is unexpected for tag: ");
                                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb2, exifTag.name, str4);
                                }
                            }
                        }
                        z3 = z;
                        if (!z3) {
                        }
                    }
                }
                hashSet = hashSet4;
                s2 = s3;
                hashMapArr2 = hashMapArr;
                i2 = readUnsignedShort2;
                if (z5) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("Skip the tag entry since data format is invalid: ", readUnsignedShort3, str4);
                }
                j = 0;
                if (z2) {
                }
                s3 = (short) (s2 + 1);
                z5 = z4;
                hashSet4 = hashSet2;
                str4 = str2;
                readShort = s;
                i6 = i;
            }
            hashMapArr2 = hashMapArr;
            i2 = readUnsignedShort2;
            j = 0;
            if (z2) {
            }
            s3 = (short) (s2 + 1);
            z5 = z4;
            hashSet4 = hashSet2;
            str4 = str2;
            readShort = s;
            i6 = i;
        }
        HashSet hashSet5 = hashSet4;
        boolean z6 = z5;
        String str6 = str4;
        int readInt3 = seekableByteOrderedDataInputStream.readInt();
        if (z6) {
            str = str6;
            Log.d(str, String.format("nextIfdOffset: %d", Integer.valueOf(readInt3)));
        } else {
            str = str6;
        }
        long j7 = readInt3;
        if (j7 <= 0) {
            if (z6) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Stop reading file since a wrong offset may cause an infinite loop: ", readInt3, str);
            }
        } else {
            if (hashSet5.contains(Integer.valueOf(readInt3))) {
                if (z6) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("Stop reading file since re-reading an IFD may cause an infinite loop: ", readInt3, str);
                    return;
                }
                return;
            }
            seekableByteOrderedDataInputStream.seek(j7);
            if (hashMapArr[4].isEmpty()) {
                readImageFileDirectory(seekableByteOrderedDataInputStream, 4);
            } else if (hashMapArr[5].isEmpty()) {
                readImageFileDirectory(seekableByteOrderedDataInputStream, 5);
            }
        }
    }

    public final void removeAttribute(String str) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(str);
        }
    }

    public final void replaceInvalidTags(int i, String str, String str2) {
        HashMap[] hashMapArr = this.mAttributes;
        if (hashMapArr[i].isEmpty() || hashMapArr[i].get(str) == null) {
            return;
        }
        HashMap hashMap = hashMapArr[i];
        hashMap.put(str2, (ExifAttribute) hashMap.get(str));
        hashMapArr[i].remove(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ee A[Catch: all -> 0x0116, Exception -> 0x0119, TryCatch #15 {Exception -> 0x0119, all -> 0x0116, blocks: (B:64:0x00ea, B:66:0x00ee, B:67:0x0104, B:71:0x00fd), top: B:63:0x00ea }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00fd A[Catch: all -> 0x0116, Exception -> 0x0119, TryCatch #15 {Exception -> 0x0119, all -> 0x0116, blocks: (B:64:0x00ea, B:66:0x00ee, B:67:0x0104, B:71:0x00fd), top: B:63:0x00ea }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0148  */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.io.Closeable, java.io.FileOutputStream, java.io.OutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void saveAttributes() {
        InputStream inputStream;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        Closeable closeable;
        FileInputStream fileInputStream2;
        FileOutputStream fileOutputStream2;
        FileInputStream fileInputStream3;
        FileOutputStream fileOutputStream3;
        BufferedInputStream bufferedInputStream;
        int i = this.mMimeType;
        if (!(i == 4 || i == 13 || i == 14)) {
            throw new IOException("ExifInterface only supports saving attributes for JPEG, PNG, and WebP formats.");
        }
        if (this.mSeekableFileDescriptor == null && this.mFilename == null) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
        if (this.mHasThumbnail && this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
            throw new IOException("ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips");
        }
        int i2 = this.mThumbnailCompression;
        InputStream inputStream2 = null;
        this.mThumbnailBytes = (i2 == 6 || i2 == 7) ? getThumbnailBytes() : null;
        try {
            File createTempFile = File.createTempFile("temp", "tmp");
            if (this.mFilename != null) {
                fileInputStream = new FileInputStream(this.mFilename);
            } else {
                Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                fileInputStream = new FileInputStream(this.mSeekableFileDescriptor);
            }
            try {
                ?? fileOutputStream4 = new FileOutputStream(createTempFile);
                try {
                    ExifInterfaceUtils.copy(fileInputStream, fileOutputStream4);
                    ExifInterfaceUtils.closeQuietly(fileInputStream);
                    ExifInterfaceUtils.closeQuietly(fileOutputStream4);
                    try {
                        try {
                            try {
                                fileInputStream3 = new FileInputStream(createTempFile);
                                try {
                                    if (this.mFilename != null) {
                                        fileOutputStream3 = new FileOutputStream(this.mFilename);
                                    } else {
                                        Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                                        fileOutputStream3 = new FileOutputStream(this.mSeekableFileDescriptor);
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    fileOutputStream3 = null;
                                }
                                try {
                                    bufferedInputStream = new BufferedInputStream(fileInputStream3);
                                } catch (Exception e2) {
                                    e = e2;
                                    fileOutputStream = fileOutputStream3;
                                    inputStream2 = fileInputStream3;
                                    try {
                                        fileInputStream2 = new FileInputStream(createTempFile);
                                    } catch (Exception e3) {
                                        e = e3;
                                    } catch (Throwable th) {
                                        th = th;
                                    }
                                    try {
                                        if (this.mFilename != null) {
                                        }
                                        fileOutputStream = fileOutputStream2;
                                        ExifInterfaceUtils.copy(fileInputStream2, fileOutputStream);
                                        ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                        ExifInterfaceUtils.closeQuietly(fileOutputStream);
                                        throw new IOException("Failed to save new file", e);
                                    } catch (Exception e4) {
                                        e = e4;
                                        inputStream2 = fileInputStream2;
                                        try {
                                            throw new IOException("Failed to save new file. Original file is stored in " + createTempFile.getAbsolutePath(), e);
                                        } catch (Throwable th2) {
                                            th = th2;
                                            ExifInterfaceUtils.closeQuietly(inputStream2);
                                            ExifInterfaceUtils.closeQuietly(fileOutputStream);
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        inputStream2 = fileInputStream2;
                                        ExifInterfaceUtils.closeQuietly(inputStream2);
                                        ExifInterfaceUtils.closeQuietly(fileOutputStream);
                                        throw th;
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            closeable = null;
                            ExifInterfaceUtils.closeQuietly(inputStream2);
                            ExifInterfaceUtils.closeQuietly(closeable);
                            if (0 == 0) {
                                createTempFile.delete();
                            }
                            throw th;
                        }
                        try {
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream3);
                            try {
                                int i3 = this.mMimeType;
                                if (i3 == 4) {
                                    saveJpegAttributes(bufferedInputStream, bufferedOutputStream);
                                } else if (i3 == 13) {
                                    savePngAttributes(bufferedInputStream, bufferedOutputStream);
                                } else if (i3 == 14) {
                                    saveWebpAttributes(bufferedInputStream, bufferedOutputStream);
                                }
                                ExifInterfaceUtils.closeQuietly(bufferedInputStream);
                                ExifInterfaceUtils.closeQuietly(bufferedOutputStream);
                                createTempFile.delete();
                                this.mThumbnailBytes = null;
                            } catch (Exception e5) {
                                e = e5;
                                fileOutputStream = fileOutputStream3;
                                inputStream2 = fileInputStream3;
                                fileInputStream2 = new FileInputStream(createTempFile);
                                if (this.mFilename != null) {
                                    Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                                    fileOutputStream2 = new FileOutputStream(this.mSeekableFileDescriptor);
                                } else {
                                    fileOutputStream2 = new FileOutputStream(this.mFilename);
                                }
                                fileOutputStream = fileOutputStream2;
                                ExifInterfaceUtils.copy(fileInputStream2, fileOutputStream);
                                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                ExifInterfaceUtils.closeQuietly(fileOutputStream);
                                throw new IOException("Failed to save new file", e);
                            }
                        } catch (Exception e6) {
                            e = e6;
                        } catch (Throwable th6) {
                            th = th6;
                            closeable = null;
                            inputStream2 = bufferedInputStream;
                            ExifInterfaceUtils.closeQuietly(inputStream2);
                            ExifInterfaceUtils.closeQuietly(closeable);
                            if (0 == 0) {
                            }
                            throw th;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        fileOutputStream = null;
                    }
                } catch (Exception e8) {
                    e = e8;
                    inputStream2 = fileOutputStream4;
                    inputStream = inputStream2;
                    inputStream2 = fileInputStream;
                    try {
                        throw new IOException("Failed to copy original file to temp file", e);
                    } catch (Throwable th7) {
                        th = th7;
                        ExifInterfaceUtils.closeQuietly(inputStream2);
                        ExifInterfaceUtils.closeQuietly(inputStream);
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    inputStream2 = fileOutputStream4;
                    inputStream = inputStream2;
                    inputStream2 = fileInputStream;
                    ExifInterfaceUtils.closeQuietly(inputStream2);
                    ExifInterfaceUtils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Exception e9) {
                e = e9;
            } catch (Throwable th9) {
                th = th9;
            }
        } catch (Exception e10) {
            e = e10;
            inputStream = null;
        } catch (Throwable th10) {
            th = th10;
            inputStream = null;
        }
    }

    public final void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) {
        if (DEBUG) {
            Log.d("ExifInterface", "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (byteOrderedDataInputStream.readByte() != -1) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-1);
        if (byteOrderedDataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-40);
        String attribute = getAttribute("Xmp");
        HashMap[] hashMapArr = this.mAttributes;
        ExifAttribute exifAttribute = (attribute == null || !this.mXmpIsFromSeparateMarker) ? null : (ExifAttribute) hashMapArr[0].remove("Xmp");
        byteOrderedDataOutputStream.writeByte(-1);
        byteOrderedDataOutputStream.writeByte(-31);
        writeExifSegment(byteOrderedDataOutputStream);
        if (exifAttribute != null) {
            hashMapArr[0].put("Xmp", exifAttribute);
        }
        byte[] bArr = new byte[4096];
        while (byteOrderedDataInputStream.readByte() == -1) {
            byte readByte = byteOrderedDataInputStream.readByte();
            if (readByte == -39 || readByte == -38) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
                return;
            }
            if (readByte != -31) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort);
                int i = readUnsignedShort - 2;
                if (i < 0) {
                    throw new IOException("Invalid length");
                }
                while (i > 0) {
                    int read = byteOrderedDataInputStream.read(bArr, 0, Math.min(i, 4096));
                    if (read >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, read);
                        i -= read;
                    }
                }
            } else {
                int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort() - 2;
                if (readUnsignedShort2 < 0) {
                    throw new IOException("Invalid length");
                }
                byte[] bArr2 = new byte[6];
                if (readUnsignedShort2 >= 6) {
                    byteOrderedDataInputStream.readFully(bArr2);
                    if (Arrays.equals(bArr2, IDENTIFIER_EXIF_APP1)) {
                        byteOrderedDataInputStream.skipFully(readUnsignedShort2 - 6);
                    }
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort2 + 2);
                if (readUnsignedShort2 >= 6) {
                    readUnsignedShort2 -= 6;
                    byteOrderedDataOutputStream.write(bArr2);
                }
                while (readUnsignedShort2 > 0) {
                    int read2 = byteOrderedDataInputStream.read(bArr, 0, Math.min(readUnsignedShort2, 4096));
                    if (read2 >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, read2);
                        readUnsignedShort2 -= read2;
                    }
                }
            }
        }
        throw new IOException("Invalid marker");
    }

    public final void savePngAttributes(InputStream inputStream, OutputStream outputStream) {
        ByteArrayOutputStream byteArrayOutputStream;
        if (DEBUG) {
            Log.d("ExifInterface", "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        byte[] bArr = PNG_SIGNATURE;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr.length);
        int i = this.mOffsetToExifData;
        if (i == 0) {
            int readInt = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(readInt);
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt + 4 + 4);
        } else {
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, ((i - bArr.length) - 4) - 4);
            byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt() + 4 + 4);
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream, byteOrder);
                writeExifSegment(byteOrderedDataOutputStream2);
                byte[] byteArray = ((ByteArrayOutputStream) byteOrderedDataOutputStream2.mOutputStream).toByteArray();
                byteOrderedDataOutputStream.write(byteArray);
                CRC32 crc32 = new CRC32();
                crc32.update(byteArray, 4, byteArray.length - 4);
                byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
            } catch (Throwable th) {
                th = th;
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
    }

    public final void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) {
        ByteArrayOutputStream byteArrayOutputStream;
        int i;
        int i2;
        boolean z;
        int i3;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream;
        byte[] bArr;
        byte[] bArr2;
        boolean z2;
        if (DEBUG) {
            Log.d("ExifInterface", "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream, byteOrder);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        byte[] bArr3 = WEBP_SIGNATURE_1;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, bArr3.length);
        byte[] bArr4 = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr4.length + 4);
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            byteArrayOutputStream = byteArrayOutputStream2;
        }
        try {
            ByteOrderedDataOutputStream byteOrderedDataOutputStream3 = new ByteOrderedDataOutputStream(byteArrayOutputStream, byteOrder);
            int i4 = this.mOffsetToExifData;
            if (i4 != 0) {
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, ((i4 - ((bArr3.length + 4) + bArr4.length)) - 4) - 4);
                byteOrderedDataInputStream.skipFully(4);
                int readInt = byteOrderedDataInputStream.readInt();
                if (readInt % 2 != 0) {
                    readInt++;
                }
                byteOrderedDataInputStream.skipFully(readInt);
                writeExifSegment(byteOrderedDataOutputStream3);
            } else {
                byte[] bArr5 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr5);
                byte[] bArr6 = WEBP_CHUNK_TYPE_VP8X;
                boolean equals = Arrays.equals(bArr5, bArr6);
                byte[] bArr7 = WEBP_CHUNK_TYPE_VP8;
                byte[] bArr8 = WEBP_CHUNK_TYPE_VP8L;
                if (!equals) {
                    if (!Arrays.equals(bArr5, bArr7)) {
                        if (Arrays.equals(bArr5, bArr8)) {
                        }
                    }
                    int readInt2 = byteOrderedDataInputStream.readInt();
                    int i5 = readInt2 % 2 == 1 ? readInt2 + 1 : readInt2;
                    byte[] bArr9 = new byte[3];
                    boolean equals2 = Arrays.equals(bArr5, bArr7);
                    byte[] bArr10 = WEBP_VP8_SIGNATURE;
                    if (equals2) {
                        byteOrderedDataInputStream.readFully(bArr9);
                        byte[] bArr11 = new byte[3];
                        byteOrderedDataInputStream.readFully(bArr11);
                        if (!Arrays.equals(bArr10, bArr11)) {
                            throw new IOException("Error checking VP8 signature");
                        }
                        i = byteOrderedDataInputStream.readInt();
                        i5 -= 10;
                        i2 = (i << 18) >> 18;
                        i3 = (i << 2) >> 18;
                        z = false;
                    } else if (!Arrays.equals(bArr5, bArr8)) {
                        i = 0;
                        i2 = 0;
                        z = false;
                        i3 = 0;
                    } else {
                        if (byteOrderedDataInputStream.readByte() != 47) {
                            throw new IOException("Error checking VP8L signature");
                        }
                        i = byteOrderedDataInputStream.readInt();
                        z = true;
                        i2 = (i & 16383) + 1;
                        i3 = ((i & 268419072) >>> 14) + 1;
                        if ((i & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) == 0) {
                            z = false;
                        }
                        i5 -= 5;
                    }
                    byteOrderedDataOutputStream3.write(bArr6);
                    byteOrderedDataOutputStream3.writeInt(10);
                    byte[] bArr12 = new byte[10];
                    if (z) {
                        byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                        bArr12[0] = (byte) (bArr12[0] | 16);
                    } else {
                        byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                    }
                    bArr = bArr4;
                    bArr12[0] = (byte) (bArr12[0] | 8);
                    int i6 = i2 - 1;
                    int i7 = i3 - 1;
                    bArr12[4] = (byte) i6;
                    bArr12[5] = (byte) (i6 >> 8);
                    bArr12[6] = (byte) (i6 >> 16);
                    bArr12[7] = (byte) i7;
                    bArr12[8] = (byte) (i7 >> 8);
                    bArr12[9] = (byte) (i7 >> 16);
                    byteOrderedDataOutputStream3.write(bArr12);
                    byteOrderedDataOutputStream3.write(bArr5);
                    byteOrderedDataOutputStream3.writeInt(readInt2);
                    if (Arrays.equals(bArr5, bArr7)) {
                        byteOrderedDataOutputStream3.write(bArr9);
                        byteOrderedDataOutputStream3.write(bArr10);
                        byteOrderedDataOutputStream3.writeInt(i);
                    } else if (Arrays.equals(bArr5, bArr8)) {
                        byteOrderedDataOutputStream3.write(47);
                        byteOrderedDataOutputStream3.writeInt(i);
                    }
                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i5);
                    writeExifSegment(byteOrderedDataOutputStream3);
                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                    byte[] bArr13 = bArr;
                    ByteOrderedDataOutputStream byteOrderedDataOutputStream4 = byteOrderedDataOutputStream;
                    byteOrderedDataOutputStream4.writeInt(byteArrayOutputStream.size() + bArr13.length);
                    byteOrderedDataOutputStream4.write(bArr13);
                    byteArrayOutputStream.writeTo(byteOrderedDataOutputStream4);
                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                }
                int readInt3 = byteOrderedDataInputStream.readInt();
                byte[] bArr14 = new byte[readInt3 % 2 == 1 ? readInt3 + 1 : readInt3];
                byteOrderedDataInputStream.readFully(bArr14);
                byte b = (byte) (8 | bArr14[0]);
                bArr14[0] = b;
                boolean z3 = ((b >> 1) & 1) == 1;
                byteOrderedDataOutputStream3.write(bArr6);
                byteOrderedDataOutputStream3.writeInt(readInt3);
                byteOrderedDataOutputStream3.write(bArr14);
                if (z3) {
                    byte[] bArr15 = WEBP_CHUNK_TYPE_ANIM;
                    do {
                        bArr2 = new byte[4];
                        byteOrderedDataInputStream.readFully(bArr2);
                        int readInt4 = byteOrderedDataInputStream.readInt();
                        byteOrderedDataOutputStream3.write(bArr2);
                        byteOrderedDataOutputStream3.writeInt(readInt4);
                        if (readInt4 % 2 == 1) {
                            readInt4++;
                        }
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt4);
                    } while (!Arrays.equals(bArr2, bArr15));
                    while (true) {
                        byte[] bArr16 = new byte[4];
                        try {
                            byteOrderedDataInputStream.readFully(bArr16);
                            z2 = !Arrays.equals(bArr16, WEBP_CHUNK_TYPE_ANMF);
                        } catch (EOFException unused) {
                            z2 = true;
                        }
                        if (z2) {
                            break;
                        }
                        int readInt5 = byteOrderedDataInputStream.readInt();
                        byteOrderedDataOutputStream3.write(bArr16);
                        byteOrderedDataOutputStream3.writeInt(readInt5);
                        if (readInt5 % 2 == 1) {
                            readInt5++;
                        }
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt5);
                    }
                    writeExifSegment(byteOrderedDataOutputStream3);
                } else {
                    while (true) {
                        byte[] bArr17 = new byte[4];
                        byteOrderedDataInputStream.readFully(bArr17);
                        int readInt6 = byteOrderedDataInputStream.readInt();
                        byteOrderedDataOutputStream3.write(bArr17);
                        byteOrderedDataOutputStream3.writeInt(readInt6);
                        if (readInt6 % 2 == 1) {
                            readInt6++;
                        }
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt6);
                        if (Arrays.equals(bArr17, bArr7) || (bArr8 != null && Arrays.equals(bArr17, bArr8))) {
                            break;
                        }
                    }
                    writeExifSegment(byteOrderedDataOutputStream3);
                }
            }
            byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
            bArr = bArr4;
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
            byte[] bArr132 = bArr;
            ByteOrderedDataOutputStream byteOrderedDataOutputStream42 = byteOrderedDataOutputStream;
            byteOrderedDataOutputStream42.writeInt(byteArrayOutputStream.size() + bArr132.length);
            byteOrderedDataOutputStream42.write(bArr132);
            byteArrayOutputStream.writeTo(byteOrderedDataOutputStream42);
            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
        } catch (Exception e2) {
            e = e2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            throw new IOException("Failed to save WebP file", e);
        } catch (Throwable th2) {
            th = th2;
            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
            throw th;
        }
    }

    public final void setAttribute(String str, String str2) {
        ExifTag exifTag;
        String str3;
        boolean z;
        ExifAttribute exifAttribute;
        String str4;
        String str5 = str;
        String str6 = str2;
        String str7 = "ExifInterface";
        if (("DateTime".equals(str5) || "DateTimeOriginal".equals(str5) || "DateTimeDigitized".equals(str5)) && str6 != null) {
            boolean find = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(str6).find();
            boolean find2 = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(str6).find();
            if (str2.length() != 19 || (!find && !find2)) {
                Log.w("ExifInterface", "Invalid value for " + str5 + " : " + str6);
                return;
            }
            if (find2) {
                str6 = str6.replaceAll("-", ":");
            }
        }
        boolean equals = "ISOSpeedRatings".equals(str5);
        boolean z2 = DEBUG;
        if (equals) {
            if (z2) {
                Log.d("ExifInterface", "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str5 = "PhotographicSensitivity";
        }
        int i = 2;
        int i2 = 1;
        if (str6 != null && sTagSetForCompatibility.contains(str5)) {
            if (str5.equals("GPSTimeStamp")) {
                Matcher matcher = GPS_TIMESTAMP_PATTERN.matcher(str6);
                if (!matcher.find()) {
                    Log.w("ExifInterface", "Invalid value for " + str5 + " : " + str6);
                    return;
                }
                str6 = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            } else {
                try {
                    str6 = new Rational(Double.parseDouble(str6)).toString();
                } catch (NumberFormatException unused) {
                    Log.w("ExifInterface", "Invalid value for " + str5 + " : " + str6);
                    return;
                }
            }
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < EXIF_TAGS.length) {
            if ((i3 != 4 || this.mHasThumbnail) && (exifTag = (ExifTag) sExifTagMapsForWriting[i3].get(str5)) != null) {
                HashMap[] hashMapArr = this.mAttributes;
                if (str6 == null) {
                    hashMapArr[i3].remove(str5);
                } else {
                    Pair guessDataFormat = guessDataFormat(str6);
                    int intValue = ((Integer) guessDataFormat.first).intValue();
                    int i5 = -1;
                    int i6 = exifTag.primaryFormat;
                    if (i6 != intValue && i6 != ((Integer) guessDataFormat.second).intValue()) {
                        int i7 = exifTag.secondaryFormat;
                        if (i7 != -1 && (i7 == ((Integer) guessDataFormat.first).intValue() || i7 == ((Integer) guessDataFormat.second).intValue())) {
                            i6 = i7;
                        } else if (i6 != i2 && i6 != 7 && i6 != i) {
                            if (z2) {
                                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Given tag (", str5, ") value didn't match with one of expected formats: ");
                                String[] strArr = IFD_FORMAT_NAMES;
                                m4m.append(strArr[i6]);
                                m4m.append(i7 == -1 ? "" : ", " + strArr[i7]);
                                m4m.append(" (guess: ");
                                m4m.append(strArr[((Integer) guessDataFormat.first).intValue()]);
                                ExifInterface$$ExternalSyntheticOutline0.m36m(m4m, ((Integer) guessDataFormat.second).intValue() != -1 ? ", " + strArr[((Integer) guessDataFormat.second).intValue()] : "", ")", str7);
                            }
                        }
                    }
                    int[] iArr = IFD_FORMAT_BYTES_PER_FORMAT;
                    switch (i6) {
                        case 1:
                            str3 = str7;
                            z = z2;
                            HashMap hashMap = hashMapArr[i3];
                            i2 = 1;
                            if (str6.length() == 1) {
                                i4 = 0;
                                if (str6.charAt(0) >= '0' && str6.charAt(0) <= '1') {
                                    exifAttribute = new ExifAttribute(1, 1, new byte[]{(byte) (str6.charAt(0) - '0')});
                                    hashMap.put(str5, exifAttribute);
                                    str7 = str3;
                                    break;
                                }
                            } else {
                                i4 = 0;
                            }
                            byte[] bytes = str6.getBytes(ASCII);
                            exifAttribute = new ExifAttribute(1, bytes.length, bytes);
                            hashMap.put(str5, exifAttribute);
                            str7 = str3;
                            break;
                        case 2:
                        case 7:
                            str4 = str7;
                            z = z2;
                            hashMapArr[i3].put(str5, ExifAttribute.createString(str6));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 3:
                            str4 = str7;
                            z = z2;
                            String[] split = str6.split(",", -1);
                            int[] iArr2 = new int[split.length];
                            for (int i8 = 0; i8 < split.length; i8++) {
                                iArr2[i8] = Integer.parseInt(split[i8]);
                            }
                            hashMapArr[i3].put(str5, ExifAttribute.createUShort(iArr2, this.mExifByteOrder));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 4:
                            str4 = str7;
                            z = z2;
                            String[] split2 = str6.split(",", -1);
                            long[] jArr = new long[split2.length];
                            for (int i9 = 0; i9 < split2.length; i9++) {
                                jArr[i9] = Long.parseLong(split2[i9]);
                            }
                            hashMapArr[i3].put(str5, ExifAttribute.createULong(jArr, this.mExifByteOrder));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 5:
                            str4 = str7;
                            z = z2;
                            String[] split3 = str6.split(",", -1);
                            Rational[] rationalArr = new Rational[split3.length];
                            int i10 = 0;
                            while (i10 < split3.length) {
                                String[] split4 = split3[i10].split("/", i5);
                                rationalArr[i10] = new Rational((long) Double.parseDouble(split4[0]), (long) Double.parseDouble(split4[1]));
                                i10++;
                                i5 = -1;
                            }
                            hashMapArr[i3].put(str5, ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            str3 = str7;
                            z = z2;
                            if (z) {
                                str7 = str3;
                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Data format isn't one of expected formats: ", i6, str7);
                                break;
                            }
                            str7 = str3;
                            break;
                        case 9:
                            str4 = str7;
                            z = z2;
                            String[] split5 = str6.split(",", -1);
                            int length = split5.length;
                            int[] iArr3 = new int[length];
                            for (int i11 = 0; i11 < split5.length; i11++) {
                                iArr3[i11] = Integer.parseInt(split5[i11]);
                            }
                            HashMap hashMap2 = hashMapArr[i3];
                            ByteOrder byteOrder = this.mExifByteOrder;
                            ByteBuffer wrap = ByteBuffer.wrap(new byte[iArr[9] * length]);
                            wrap.order(byteOrder);
                            for (int i12 = 0; i12 < length; i12++) {
                                wrap.putInt(iArr3[i12]);
                            }
                            hashMap2.put(str5, new ExifAttribute(9, length, wrap.array()));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 10:
                            String[] split6 = str6.split(",", -1);
                            int length2 = split6.length;
                            Rational[] rationalArr2 = new Rational[length2];
                            int i13 = -1;
                            int i14 = i4;
                            while (i4 < split6.length) {
                                String[] split7 = split6[i4].split("/", i13);
                                rationalArr2[i4] = new Rational((long) Double.parseDouble(split7[i14]), (long) Double.parseDouble(split7[1]));
                                i4++;
                                i14 = 0;
                                i13 = -1;
                                z2 = z2;
                                str7 = str7;
                            }
                            str4 = str7;
                            z = z2;
                            HashMap hashMap3 = hashMapArr[i3];
                            ByteOrder byteOrder2 = this.mExifByteOrder;
                            ByteBuffer wrap2 = ByteBuffer.wrap(new byte[iArr[10] * length2]);
                            wrap2.order(byteOrder2);
                            for (int i15 = 0; i15 < length2; i15++) {
                                Rational rational = rationalArr2[i15];
                                wrap2.putInt((int) rational.numerator);
                                wrap2.putInt((int) rational.denominator);
                            }
                            hashMap3.put(str5, new ExifAttribute(10, length2, wrap2.array()));
                            i2 = 1;
                            str7 = str4;
                            i4 = 0;
                            break;
                        case 12:
                            String[] split8 = str6.split(",", -1);
                            int length3 = split8.length;
                            double[] dArr = new double[length3];
                            for (int i16 = i4; i16 < split8.length; i16++) {
                                dArr[i16] = Double.parseDouble(split8[i16]);
                            }
                            HashMap hashMap4 = hashMapArr[i3];
                            ByteOrder byteOrder3 = this.mExifByteOrder;
                            ByteBuffer wrap3 = ByteBuffer.wrap(new byte[iArr[12] * length3]);
                            wrap3.order(byteOrder3);
                            for (int i17 = i4; i17 < length3; i17++) {
                                wrap3.putDouble(dArr[i17]);
                            }
                            hashMap4.put(str5, new ExifAttribute(12, length3, wrap3.array()));
                            break;
                    }
                    i3++;
                    i = 2;
                    z2 = z;
                }
            }
            z = z2;
            i3++;
            i = 2;
            z2 = z;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        boolean z;
        int i;
        ExifAttribute exifAttribute;
        int intValue;
        HashMap hashMap = this.mAttributes[4];
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get("Compression");
        if (exifAttribute2 == null) {
            this.mThumbnailCompression = 6;
            handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
            return;
        }
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        this.mThumbnailCompression = intValue2;
        int i2 = 1;
        if (intValue2 != 1) {
            if (intValue2 == 6) {
                handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                return;
            } else if (intValue2 != 7) {
                return;
            }
        }
        ExifAttribute exifAttribute3 = (ExifAttribute) hashMap.get("BitsPerSample");
        if (exifAttribute3 != null) {
            int[] iArr = (int[]) exifAttribute3.getValue(this.mExifByteOrder);
            int[] iArr2 = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr2, iArr) || (this.mMimeType == 3 && (exifAttribute = (ExifAttribute) hashMap.get("PhotometricInterpretation")) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, iArr2))))) {
                z = true;
                if (z) {
                    return;
                }
                ExifAttribute exifAttribute4 = (ExifAttribute) hashMap.get("StripOffsets");
                ExifAttribute exifAttribute5 = (ExifAttribute) hashMap.get("StripByteCounts");
                if (exifAttribute4 == null || exifAttribute5 == null) {
                    return;
                }
                long[] convertToLongArray = ExifInterfaceUtils.convertToLongArray(exifAttribute4.getValue(this.mExifByteOrder));
                long[] convertToLongArray2 = ExifInterfaceUtils.convertToLongArray(exifAttribute5.getValue(this.mExifByteOrder));
                if (convertToLongArray == null || convertToLongArray.length == 0) {
                    Log.w("ExifInterface", "stripOffsets should not be null or have zero length.");
                    return;
                }
                if (convertToLongArray2 == null || convertToLongArray2.length == 0) {
                    Log.w("ExifInterface", "stripByteCounts should not be null or have zero length.");
                    return;
                }
                if (convertToLongArray.length != convertToLongArray2.length) {
                    Log.w("ExifInterface", "stripOffsets and stripByteCounts should have same length.");
                    return;
                }
                long j = 0;
                for (long j2 : convertToLongArray2) {
                    j += j2;
                }
                int i3 = (int) j;
                byte[] bArr = new byte[i3];
                this.mAreThumbnailStripsConsecutive = true;
                this.mHasThumbnailStrips = true;
                this.mHasThumbnail = true;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (i4 < convertToLongArray.length) {
                    int i7 = (int) convertToLongArray[i4];
                    int i8 = (int) convertToLongArray2[i4];
                    if (i4 < convertToLongArray.length - i2) {
                        i = i3;
                        if (i7 + i8 != convertToLongArray[i4 + 1]) {
                            this.mAreThumbnailStripsConsecutive = false;
                        }
                    } else {
                        i = i3;
                    }
                    int i9 = i7 - i5;
                    if (i9 < 0) {
                        Log.d("ExifInterface", "Invalid strip offset value");
                        return;
                    }
                    try {
                        byteOrderedDataInputStream.skipFully(i9);
                        int i10 = i5 + i9;
                        byte[] bArr2 = new byte[i8];
                        try {
                            byteOrderedDataInputStream.readFully(bArr2);
                            i5 = i10 + i8;
                            System.arraycopy(bArr2, 0, bArr, i6, i8);
                            i6 += i8;
                            i4++;
                            i2 = 1;
                            i3 = i;
                        } catch (EOFException unused) {
                            AbstractC0147x487e7be7.m26m("Failed to read ", i8, " bytes.", "ExifInterface");
                            return;
                        }
                    } catch (EOFException unused2) {
                        AbstractC0147x487e7be7.m26m("Failed to skip ", i9, " bytes.", "ExifInterface");
                        return;
                    }
                }
                int i11 = i3;
                this.mThumbnailBytes = bArr;
                if (this.mAreThumbnailStripsConsecutive) {
                    this.mThumbnailOffset = (int) convertToLongArray[0];
                    this.mThumbnailLength = i11;
                    return;
                }
                return;
            }
        }
        if (DEBUG) {
            Log.d("ExifInterface", "Unsupported data type value");
        }
        z = false;
        if (z) {
        }
    }

    public final void swapBasedOnImageSize(int i, int i2) {
        HashMap[] hashMapArr = this.mAttributes;
        boolean isEmpty = hashMapArr[i].isEmpty();
        boolean z = DEBUG;
        if (isEmpty || hashMapArr[i2].isEmpty()) {
            if (z) {
                Log.d("ExifInterface", "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[i].get("ImageLength");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[i].get("ImageWidth");
        ExifAttribute exifAttribute3 = (ExifAttribute) hashMapArr[i2].get("ImageLength");
        ExifAttribute exifAttribute4 = (ExifAttribute) hashMapArr[i2].get("ImageWidth");
        if (exifAttribute == null || exifAttribute2 == null) {
            if (z) {
                Log.d("ExifInterface", "First image does not contain valid size information");
                return;
            }
            return;
        }
        if (exifAttribute3 == null || exifAttribute4 == null) {
            if (z) {
                Log.d("ExifInterface", "Second image does not contain valid size information");
                return;
            }
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
        int intValue4 = exifAttribute4.getIntValue(this.mExifByteOrder);
        if (intValue >= intValue3 || intValue2 >= intValue4) {
            return;
        }
        HashMap hashMap = hashMapArr[i];
        hashMapArr[i] = hashMapArr[i2];
        hashMapArr[i2] = hashMap;
    }

    public final void updateImageSizeValues(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) {
        ExifAttribute createUShort;
        ExifAttribute createUShort2;
        HashMap[] hashMapArr = this.mAttributes;
        ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[i].get("DefaultCropSize");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[i].get("SensorTopBorder");
        ExifAttribute exifAttribute3 = (ExifAttribute) hashMapArr[i].get("SensorLeftBorder");
        ExifAttribute exifAttribute4 = (ExifAttribute) hashMapArr[i].get("SensorBottomBorder");
        ExifAttribute exifAttribute5 = (ExifAttribute) hashMapArr[i].get("SensorRightBorder");
        if (exifAttribute != null) {
            if (exifAttribute.format == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(rationalArr));
                    return;
                } else {
                    createUShort = ExifAttribute.createURational(new Rational[]{rationalArr[0]}, this.mExifByteOrder);
                    createUShort2 = ExifAttribute.createURational(new Rational[]{rationalArr[1]}, this.mExifByteOrder);
                }
            } else {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(iArr));
                    return;
                }
                createUShort = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                createUShort2 = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            hashMapArr[i].put("ImageWidth", createUShort);
            hashMapArr[i].put("ImageLength", createUShort2);
            return;
        }
        if (exifAttribute2 != null && exifAttribute3 != null && exifAttribute4 != null && exifAttribute5 != null) {
            int intValue = exifAttribute2.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute4.getIntValue(this.mExifByteOrder);
            int intValue3 = exifAttribute5.getIntValue(this.mExifByteOrder);
            int intValue4 = exifAttribute3.getIntValue(this.mExifByteOrder);
            if (intValue2 <= intValue || intValue3 <= intValue4) {
                return;
            }
            ExifAttribute createUShort3 = ExifAttribute.createUShort(intValue2 - intValue, this.mExifByteOrder);
            ExifAttribute createUShort4 = ExifAttribute.createUShort(intValue3 - intValue4, this.mExifByteOrder);
            hashMapArr[i].put("ImageLength", createUShort3);
            hashMapArr[i].put("ImageWidth", createUShort4);
            return;
        }
        ExifAttribute exifAttribute6 = (ExifAttribute) hashMapArr[i].get("ImageLength");
        ExifAttribute exifAttribute7 = (ExifAttribute) hashMapArr[i].get("ImageWidth");
        if (exifAttribute6 == null || exifAttribute7 == null) {
            ExifAttribute exifAttribute8 = (ExifAttribute) hashMapArr[i].get("JPEGInterchangeFormat");
            ExifAttribute exifAttribute9 = (ExifAttribute) hashMapArr[i].get("JPEGInterchangeFormatLength");
            if (exifAttribute8 == null || exifAttribute9 == null) {
                return;
            }
            int intValue5 = exifAttribute8.getIntValue(this.mExifByteOrder);
            int intValue6 = exifAttribute8.getIntValue(this.mExifByteOrder);
            seekableByteOrderedDataInputStream.seek(intValue5);
            byte[] bArr = new byte[intValue6];
            seekableByteOrderedDataInputStream.readFully(bArr);
            getJpegAttributes(new ByteOrderedDataInputStream(bArr), intValue5, i);
        }
    }

    public final void validateImages() {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        HashMap[] hashMapArr = this.mAttributes;
        ExifAttribute exifAttribute = (ExifAttribute) hashMapArr[1].get("PixelXDimension");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMapArr[1].get("PixelYDimension");
        if (exifAttribute != null && exifAttribute2 != null) {
            hashMapArr[0].put("ImageWidth", exifAttribute);
            hashMapArr[0].put("ImageLength", exifAttribute2);
        }
        if (hashMapArr[4].isEmpty() && isThumbnail(hashMapArr[5])) {
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap();
        }
        if (!isThumbnail(hashMapArr[4])) {
            Log.d("ExifInterface", "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, "ThumbnailOrientation", "Orientation");
        replaceInvalidTags(0, "ThumbnailImageLength", "ImageLength");
        replaceInvalidTags(0, "ThumbnailImageWidth", "ImageWidth");
        replaceInvalidTags(5, "ThumbnailOrientation", "Orientation");
        replaceInvalidTags(5, "ThumbnailImageLength", "ImageLength");
        replaceInvalidTags(5, "ThumbnailImageWidth", "ImageWidth");
        replaceInvalidTags(4, "Orientation", "ThumbnailOrientation");
        replaceInvalidTags(4, "ImageLength", "ThumbnailImageLength");
        replaceInvalidTags(4, "ImageWidth", "ThumbnailImageWidth");
    }

    public final void writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream) {
        HashMap[] hashMapArr;
        int[] iArr;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        int[] iArr2 = new int[exifTagArr.length];
        int[] iArr3 = new int[exifTagArr.length];
        ExifTag[] exifTagArr2 = EXIF_POINTER_TAGS;
        for (ExifTag exifTag : exifTagArr2) {
            removeAttribute(exifTag.name);
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                removeAttribute("StripOffsets");
                removeAttribute("StripByteCounts");
            } else {
                removeAttribute("JPEGInterchangeFormat");
                removeAttribute("JPEGInterchangeFormatLength");
            }
        }
        int i = 0;
        while (true) {
            int length = exifTagArr.length;
            hashMapArr = this.mAttributes;
            if (i >= length) {
                break;
            }
            Iterator it = hashMapArr[i].entrySet().iterator();
            while (it.hasNext()) {
                if (((Map.Entry) it.next()).getValue() == null) {
                    it.remove();
                }
            }
            i++;
        }
        if (!hashMapArr[1].isEmpty()) {
            hashMapArr[0].put(exifTagArr2[1].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!hashMapArr[2].isEmpty()) {
            hashMapArr[0].put(exifTagArr2[2].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!hashMapArr[3].isEmpty()) {
            hashMapArr[1].put(exifTagArr2[3].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                hashMapArr[4].put("StripOffsets", ExifAttribute.createUShort(0, this.mExifByteOrder));
                hashMapArr[4].put("StripByteCounts", ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                hashMapArr[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(0L, this.mExifByteOrder));
                hashMapArr[4].put("JPEGInterchangeFormatLength", ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        int i2 = 0;
        while (true) {
            int length2 = exifTagArr.length;
            iArr = IFD_FORMAT_BYTES_PER_FORMAT;
            if (i2 >= length2) {
                break;
            }
            Iterator it2 = hashMapArr[i2].entrySet().iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                ExifAttribute exifAttribute = (ExifAttribute) ((Map.Entry) it2.next()).getValue();
                exifAttribute.getClass();
                int i4 = iArr[exifAttribute.format] * exifAttribute.numberOfComponents;
                if (i4 > 4) {
                    i3 += i4;
                }
            }
            iArr3[i2] = iArr3[i2] + i3;
            i2++;
        }
        int i5 = 8;
        for (int i6 = 0; i6 < exifTagArr.length; i6++) {
            if (!hashMapArr[i6].isEmpty()) {
                iArr2[i6] = i5;
                i5 = (hashMapArr[i6].size() * 12) + 2 + 4 + iArr3[i6] + i5;
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                hashMapArr[4].put("StripOffsets", ExifAttribute.createUShort(i5, this.mExifByteOrder));
            } else {
                hashMapArr[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(i5, this.mExifByteOrder));
            }
            this.mThumbnailOffset = i5;
            i5 += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            i5 += 8;
        }
        if (DEBUG) {
            for (int i7 = 0; i7 < exifTagArr.length; i7++) {
                Log.d("ExifInterface", String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i7), Integer.valueOf(iArr2[i7]), Integer.valueOf(hashMapArr[i7].size()), Integer.valueOf(iArr3[i7]), Integer.valueOf(i5)));
            }
        }
        if (!hashMapArr[1].isEmpty()) {
            hashMapArr[0].put(exifTagArr2[1].name, ExifAttribute.createULong(iArr2[1], this.mExifByteOrder));
        }
        if (!hashMapArr[2].isEmpty()) {
            hashMapArr[0].put(exifTagArr2[2].name, ExifAttribute.createULong(iArr2[2], this.mExifByteOrder));
        }
        if (!hashMapArr[3].isEmpty()) {
            hashMapArr[1].put(exifTagArr2[3].name, ExifAttribute.createULong(iArr2[3], this.mExifByteOrder));
        }
        int i8 = this.mMimeType;
        if (i8 == 4) {
            if (i5 > 65535) {
                throw new IllegalStateException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Size of exif data (", i5, " bytes) exceeds the max size of a JPEG APP1 segment (65536 bytes)"));
            }
            byteOrderedDataOutputStream.writeUnsignedShort(i5);
            byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        } else if (i8 == 13) {
            byteOrderedDataOutputStream.writeInt(i5);
            byteOrderedDataOutputStream.write(PNG_CHUNK_TYPE_EXIF);
        } else if (i8 == 14) {
            byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
            byteOrderedDataOutputStream.writeInt(i5);
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
        byteOrderedDataOutputStream.mByteOrder = this.mExifByteOrder;
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (int i9 = 0; i9 < exifTagArr.length; i9++) {
            if (!hashMapArr[i9].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(hashMapArr[i9].size());
                int size = (hashMapArr[i9].size() * 12) + iArr2[i9] + 2 + 4;
                for (Map.Entry entry : hashMapArr[i9].entrySet()) {
                    int i10 = ((ExifTag) sExifTagMapsForWriting[i9].get(entry.getKey())).number;
                    ExifAttribute exifAttribute2 = (ExifAttribute) entry.getValue();
                    exifAttribute2.getClass();
                    int i11 = iArr[exifAttribute2.format] * exifAttribute2.numberOfComponents;
                    byteOrderedDataOutputStream.writeUnsignedShort(i10);
                    byteOrderedDataOutputStream.writeUnsignedShort(exifAttribute2.format);
                    byteOrderedDataOutputStream.writeInt(exifAttribute2.numberOfComponents);
                    if (i11 > 4) {
                        byteOrderedDataOutputStream.writeUnsignedInt(size);
                        size += i11;
                    } else {
                        byteOrderedDataOutputStream.write(exifAttribute2.bytes);
                        if (i11 < 4) {
                            while (i11 < 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i11++;
                            }
                        }
                    }
                }
                if (i9 != 0 || hashMapArr[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0L);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt(iArr2[4]);
                }
                Iterator it3 = hashMapArr[i9].entrySet().iterator();
                while (it3.hasNext()) {
                    byte[] bArr = ((ExifAttribute) ((Map.Entry) it3.next()).getValue()).bytes;
                    if (bArr.length > 4) {
                        byteOrderedDataOutputStream.write(bArr, 0, bArr.length);
                    }
                }
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && i5 % 2 == 1) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ByteOrderedDataInputStream extends InputStream implements DataInput {
        public ByteOrder mByteOrder;
        public final DataInputStream mDataInputStream;
        public final int mLength;
        public int mPosition;
        public byte[] mSkipBuffer;

        public ByteOrderedDataInputStream(byte[] bArr) {
            this(new ByteArrayInputStream(bArr), ByteOrder.BIG_ENDIAN);
            this.mLength = bArr.length;
        }

        @Override // java.io.InputStream
        public final int available() {
            return this.mDataInputStream.available();
        }

        @Override // java.io.InputStream
        public final void mark(int i) {
            throw new UnsupportedOperationException("Mark is currently unsupported");
        }

        @Override // java.io.InputStream
        public final int read() {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        @Override // java.io.DataInput
        public final boolean readBoolean() {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        @Override // java.io.DataInput
        public final byte readByte() {
            this.mPosition++;
            int read = this.mDataInputStream.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public final char readChar() {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        @Override // java.io.DataInput
        public final double readDouble() {
            return Double.longBitsToDouble(readLong());
        }

        @Override // java.io.DataInput
        public final float readFloat() {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public final void readFully(byte[] bArr, int i, int i2) {
            this.mPosition += i2;
            this.mDataInputStream.readFully(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public final int readInt() {
            this.mPosition += 4;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public final String readLine() {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public final long readLong() {
            this.mPosition += 8;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            int read5 = this.mDataInputStream.read();
            int read6 = this.mDataInputStream.read();
            int read7 = this.mDataInputStream.read();
            int read8 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (read8 << 56) + (read7 << 48) + (read6 << 40) + (read5 << 32) + (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (read << 56) + (read2 << 48) + (read3 << 40) + (read4 << 32) + (read5 << 24) + (read6 << 16) + (read7 << 8) + read8;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public final short readShort() {
            int i;
            this.mPosition += 2;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                i = (read2 << 8) + read;
            } else {
                if (byteOrder != ByteOrder.BIG_ENDIAN) {
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                i = (read << 8) + read2;
            }
            return (short) i;
        }

        @Override // java.io.DataInput
        public final String readUTF() {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        @Override // java.io.DataInput
        public final int readUnsignedByte() {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public final int readUnsignedShort() {
            this.mPosition += 2;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (read2 << 8) + read;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (read << 8) + read2;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.InputStream
        public final void reset() {
            throw new UnsupportedOperationException("Reset is currently unsupported");
        }

        @Override // java.io.DataInput
        public final int skipBytes(int i) {
            throw new UnsupportedOperationException("skipBytes is currently unsupported");
        }

        public final void skipFully(int i) {
            int i2 = 0;
            while (i2 < i) {
                int i3 = i - i2;
                int skip = (int) this.mDataInputStream.skip(i3);
                if (skip <= 0) {
                    if (this.mSkipBuffer == null) {
                        this.mSkipBuffer = new byte[8192];
                    }
                    skip = this.mDataInputStream.read(this.mSkipBuffer, 0, Math.min(8192, i3));
                    if (skip == -1) {
                        throw new EOFException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Reached EOF while skipping ", i, " bytes."));
                    }
                }
                i2 += skip;
            }
            this.mPosition += i2;
        }

        public ByteOrderedDataInputStream(InputStream inputStream) {
            this(inputStream, ByteOrder.BIG_ENDIAN);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) {
            int read = this.mDataInputStream.read(bArr, i, i2);
            this.mPosition += read;
            return read;
        }

        @Override // java.io.DataInput
        public final void readFully(byte[] bArr) {
            this.mPosition += bArr.length;
            this.mDataInputStream.readFully(bArr);
        }

        public ByteOrderedDataInputStream(InputStream inputStream, ByteOrder byteOrder) {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.mDataInputStream = dataInputStream;
            dataInputStream.mark(0);
            this.mPosition = 0;
            this.mByteOrder = byteOrder;
            this.mLength = inputStream instanceof ByteOrderedDataInputStream ? ((ByteOrderedDataInputStream) inputStream).mLength : -1;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekableByteOrderedDataInputStream extends ByteOrderedDataInputStream {
        public SeekableByteOrderedDataInputStream(byte[] bArr) {
            super(bArr);
            this.mDataInputStream.mark(Integer.MAX_VALUE);
        }

        public final void seek(long j) {
            int i = this.mPosition;
            if (i > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
            } else {
                j -= i;
            }
            skipFully((int) j);
        }

        public SeekableByteOrderedDataInputStream(InputStream inputStream) {
            super(inputStream);
            if (inputStream.markSupported()) {
                this.mDataInputStream.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        public ExifTag(String str, int i, int i2) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = -1;
        }

        public ExifTag(String str, int i, int i2, int i3) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = i3;
        }
    }

    public ExifInterface(String str) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (str != null) {
            initForFilename(str);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    public ExifInterface(FileDescriptor fileDescriptor) {
        boolean z;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (fileDescriptor != null) {
            FileInputStream fileInputStream = null;
            this.mAssetInputStream = null;
            this.mFilename = null;
            if (isSeekableFD(fileDescriptor)) {
                this.mSeekableFileDescriptor = fileDescriptor;
                try {
                    fileDescriptor = Os.dup(fileDescriptor);
                    z = true;
                } catch (Exception e) {
                    throw new IOException("Failed to duplicate file descriptor", e);
                }
            } else {
                this.mSeekableFileDescriptor = null;
                z = false;
            }
            try {
                FileInputStream fileInputStream2 = new FileInputStream(fileDescriptor);
                try {
                    loadAttributes(fileInputStream2);
                    ExifInterfaceUtils.closeQuietly(fileInputStream2);
                    if (z) {
                        ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                    }
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    ExifInterfaceUtils.closeQuietly(fileInputStream);
                    if (z) {
                        ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            throw new NullPointerException("fileDescriptor cannot be null");
        }
    }

    public ExifInterface(InputStream inputStream) {
        this(inputStream, 0);
    }

    public ExifInterface(InputStream inputStream, int i) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (inputStream != null) {
            this.mFilename = null;
            boolean z = i == 1;
            this.mIsExifDataOnly = z;
            if (z) {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
            } else if (inputStream instanceof AssetManager.AssetInputStream) {
                this.mAssetInputStream = (AssetManager.AssetInputStream) inputStream;
                this.mSeekableFileDescriptor = null;
            } else {
                if (inputStream instanceof FileInputStream) {
                    FileInputStream fileInputStream = (FileInputStream) inputStream;
                    if (isSeekableFD(fileInputStream.getFD())) {
                        this.mAssetInputStream = null;
                        this.mSeekableFileDescriptor = fileInputStream.getFD();
                    }
                }
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
            }
            loadAttributes(inputStream);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }
}
