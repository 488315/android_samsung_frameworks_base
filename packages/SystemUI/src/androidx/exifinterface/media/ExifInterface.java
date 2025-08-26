package androidx.exifinterface.media;

import android.content.res.AssetManager;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
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
import java.util.Collections;
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

/* loaded from: classes.dex */
public class ExifInterface {
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
    public static final int PNG_CHUNK_TYPE_EXIF;
    public static final int PNG_CHUNK_TYPE_IEND;
    public static final int PNG_CHUNK_TYPE_IHDR;
    public static final byte[] PNG_SIGNATURE;
    public static final Set RATIONAL_TAGS_HANDLED_AS_DECIMALS_FOR_COMPATIBILITY;
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

    public class ByteOrderedDataOutputStream extends FilterOutputStream {
        public ByteOrder mByteOrder;
        public final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final void write(byte[] bArr) throws IOException {
            this.mOutputStream.write(bArr);
        }

        public final void writeByte(int i) throws IOException {
            this.mOutputStream.write(i);
        }

        public final void writeInt(int i) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(i & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
                return;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write(i & 255);
            }
        }

        public final void writeShort(short s) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(s & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write(s & 255);
            }
        }

        public final void writeUnsignedInt(long j) throws IOException {
            if (j > 4294967295L) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 32-bit unsigned integer");
            }
            writeInt((int) j);
        }

        public final void writeUnsignedShort(int i) throws IOException {
            if (i > 65535) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 16-bit unsigned integer");
            }
            writeShort((short) i);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            this.mOutputStream.write(bArr, i, i2);
        }
    }

    public class ExifAttribute {
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
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length]);
            byteBufferWrap.order(byteOrder);
            for (long j : jArr) {
                byteBufferWrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createURational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * rationalArr.length]);
            byteBufferWrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                byteBufferWrap.putInt((int) rational.numerator);
                byteBufferWrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(5, rationalArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length]);
            byteBufferWrap.order(byteOrder);
            for (int i : iArr) {
                byteBufferWrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, byteBufferWrap.array());
        }

        public final double getDoubleValue(ByteOrder byteOrder) throws Throwable {
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

        public final int getIntValue(ByteOrder byteOrder) throws Throwable {
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

        public final String getStringValue(ByteOrder byteOrder) throws Throwable {
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

        /* JADX WARN: Not initialized variable reg: 6, insn: 0x0033: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]) (LINE:52), block:B:16:0x0033 */
        /* JADX WARN: Removed duplicated region for block: B:153:0x016d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object getValue(ByteOrder byteOrder) throws Throwable {
            ByteOrderedDataInputStream byteOrderedDataInputStream;
            InputStream inputStream;
            byte b;
            String string;
            int length = 0;
            byte[] bArr = this.bytes;
            InputStream inputStream2 = null;
            try {
                try {
                    try {
                        byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
                        try {
                            byteOrderedDataInputStream.mByteOrder = byteOrder;
                            int i = this.format;
                            int i2 = this.numberOfComponents;
                            switch (i) {
                                case 1:
                                case 6:
                                    if (bArr.length != 1 || (b = bArr[0]) < 0 || b > 1) {
                                        String str = new String(bArr, ExifInterface.ASCII);
                                        try {
                                            byteOrderedDataInputStream.close();
                                            return str;
                                        } catch (IOException e) {
                                            Log.e("ExifInterface", "IOException occurred while closing InputStream", e);
                                            return str;
                                        }
                                    }
                                    String str2 = new String(new char[]{(char) (b + 48)});
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return str2;
                                    } catch (IOException e2) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e2);
                                        return str2;
                                    }
                                case 2:
                                case 7:
                                    if (i2 >= ExifInterface.EXIF_ASCII_PREFIX.length) {
                                        int i3 = 0;
                                        while (true) {
                                            byte[] bArr2 = ExifInterface.EXIF_ASCII_PREFIX;
                                            if (i3 >= bArr2.length) {
                                                length = bArr2.length;
                                            } else if (bArr[i3] == bArr2[i3]) {
                                                i3++;
                                            }
                                        }
                                    }
                                    StringBuilder sb = new StringBuilder();
                                    try {
                                        while (length < i2) {
                                            byte b2 = bArr[length];
                                            if (b2 == 0) {
                                                string = sb.toString();
                                                byteOrderedDataInputStream.close();
                                                return string;
                                            }
                                            if (b2 >= 32) {
                                                sb.append((char) b2);
                                            } else {
                                                sb.append('?');
                                            }
                                            length++;
                                        }
                                        byteOrderedDataInputStream.close();
                                        return string;
                                    } catch (IOException e3) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e3);
                                        return string;
                                    }
                                    string = sb.toString();
                                case 3:
                                    int[] iArr = new int[i2];
                                    while (length < i2) {
                                        iArr[length] = byteOrderedDataInputStream.readUnsignedShort();
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return iArr;
                                    } catch (IOException e4) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e4);
                                        return iArr;
                                    }
                                case 4:
                                    long[] jArr = new long[i2];
                                    while (length < i2) {
                                        jArr[length] = byteOrderedDataInputStream.readInt() & 4294967295L;
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return jArr;
                                    } catch (IOException e5) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e5);
                                        return jArr;
                                    }
                                case 5:
                                    Rational[] rationalArr = new Rational[i2];
                                    while (length < i2) {
                                        rationalArr[length] = new Rational(byteOrderedDataInputStream.readInt() & 4294967295L, byteOrderedDataInputStream.readInt() & 4294967295L);
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return rationalArr;
                                    } catch (IOException e6) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e6);
                                        return rationalArr;
                                    }
                                case 8:
                                    int[] iArr2 = new int[i2];
                                    while (length < i2) {
                                        iArr2[length] = byteOrderedDataInputStream.readShort();
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return iArr2;
                                    } catch (IOException e7) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e7);
                                        return iArr2;
                                    }
                                case 9:
                                    int[] iArr3 = new int[i2];
                                    while (length < i2) {
                                        iArr3[length] = byteOrderedDataInputStream.readInt();
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return iArr3;
                                    } catch (IOException e8) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e8);
                                        return iArr3;
                                    }
                                case 10:
                                    Rational[] rationalArr2 = new Rational[i2];
                                    while (length < i2) {
                                        rationalArr2[length] = new Rational(byteOrderedDataInputStream.readInt(), byteOrderedDataInputStream.readInt());
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return rationalArr2;
                                    } catch (IOException e9) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e9);
                                        return rationalArr2;
                                    }
                                case 11:
                                    double[] dArr = new double[i2];
                                    while (length < i2) {
                                        dArr[length] = byteOrderedDataInputStream.readFloat();
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return dArr;
                                    } catch (IOException e10) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e10);
                                        return dArr;
                                    }
                                case 12:
                                    double[] dArr2 = new double[i2];
                                    while (length < i2) {
                                        dArr2[length] = byteOrderedDataInputStream.readDouble();
                                        length++;
                                    }
                                    try {
                                        byteOrderedDataInputStream.close();
                                        return dArr2;
                                    } catch (IOException e11) {
                                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e11);
                                        return dArr2;
                                    }
                                default:
                                    byteOrderedDataInputStream.close();
                                    return null;
                            }
                        } catch (IOException e12) {
                            e = e12;
                            Log.w("ExifInterface", "IOException occurred during reading a value", e);
                            if (byteOrderedDataInputStream != null) {
                                byteOrderedDataInputStream.close();
                            }
                            return null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        inputStream2 = inputStream;
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e13) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e13);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e14) {
                    e = e14;
                    byteOrderedDataInputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream2 != null) {
                    }
                    throw th;
                }
            } catch (IOException e15) {
                Log.e("ExifInterface", "IOException occurred while closing InputStream", e15);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("(");
            sb.append(ExifInterface.IFD_FORMAT_NAMES[this.format]);
            sb.append(", data length:");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.bytes.length, ")", sb);
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

    public class Rational {
        public final long denominator;
        public final long numerator;

        public Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = j;
                this.denominator = j2;
            }
        }

        public final String toString() {
            return this.numerator + "/" + this.denominator;
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
        PNG_CHUNK_TYPE_EXIF = intFromBytes(101, 88, 73, 102);
        PNG_CHUNK_TYPE_IHDR = intFromBytes(73, 72, 68, 82);
        PNG_CHUNK_TYPE_IEND = intFromBytes(73, 69, 78, 68);
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
        RATIONAL_TAGS_HANDLED_AS_DECIMALS_FOR_COMPATIBILITY = Collections.unmodifiableSet(new HashSet(Arrays.asList("FNumber", "DigitalZoomRatio", "ExposureTime", "SubjectDistance")));
        sExifPointerTagMap = new HashMap();
        Charset charsetForName = Charset.forName("US-ASCII");
        ASCII = charsetForName;
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(charsetForName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(charsetForName);
        Locale locale = Locale.US;
        new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        int i = 0;
        while (true) {
            ExifTag[][] exifTagArr6 = EXIF_TAGS;
            if (i >= exifTagArr6.length) {
                HashMap map = sExifPointerTagMap;
                ExifTag[] exifTagArr7 = EXIF_POINTER_TAGS;
                map.put(Integer.valueOf(exifTagArr7[0].number), 5);
                map.put(Integer.valueOf(exifTagArr7[1].number), 1);
                map.put(Integer.valueOf(exifTagArr7[2].number), 2);
                map.put(Integer.valueOf(exifTagArr7[3].number), 3);
                map.put(Integer.valueOf(exifTagArr7[4].number), 7);
                map.put(Integer.valueOf(exifTagArr7[5].number), 8);
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

    public ExifInterface(File file) throws Throwable {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (file == null) {
            throw new NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    public static Pair guessDataFormat(String str) throws NumberFormatException {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",", -1);
            Pair pairGuessDataFormat = guessDataFormat(strArrSplit[0]);
            if (((Integer) pairGuessDataFormat.first).intValue() == 2) {
                return pairGuessDataFormat;
            }
            for (int i = 1; i < strArrSplit.length; i++) {
                Pair pairGuessDataFormat2 = guessDataFormat(strArrSplit[i]);
                int iIntValue = (((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.first) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.first)) ? ((Integer) pairGuessDataFormat.first).intValue() : -1;
                int iIntValue2 = (((Integer) pairGuessDataFormat.second).intValue() == -1 || !(((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.second) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.second))) ? -1 : ((Integer) pairGuessDataFormat.second).intValue();
                if (iIntValue == -1 && iIntValue2 == -1) {
                    return new Pair(2, -1);
                }
                if (iIntValue == -1) {
                    pairGuessDataFormat = new Pair(Integer.valueOf(iIntValue2), -1);
                } else if (iIntValue2 == -1) {
                    pairGuessDataFormat = new Pair(Integer.valueOf(iIntValue), -1);
                }
            }
            return pairGuessDataFormat;
        }
        if (!str.contains("/")) {
            try {
                try {
                    long j = Long.parseLong(str);
                    return (j < 0 || j > 65535) ? j < 0 ? new Pair(9, -1) : new Pair(4, -1) : new Pair(3, 4);
                } catch (NumberFormatException unused) {
                    return new Pair(2, -1);
                }
            } catch (NumberFormatException unused2) {
                Double.parseDouble(str);
                return new Pair(12, -1);
            }
        }
        String[] strArrSplit2 = str.split("/", -1);
        if (strArrSplit2.length == 2) {
            try {
                long j2 = (long) Double.parseDouble(strArrSplit2[0]);
                long j3 = (long) Double.parseDouble(strArrSplit2[1]);
                if (j2 >= 0 && j3 >= 0) {
                    if (j2 <= 2147483647L && j3 <= 2147483647L) {
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

    public static int intFromBytes(int i, int i2, int i3, int i4) {
        return ((i & 255) << 24) | ((i2 & 255) << 16) | ((i3 & 255) << 8) | (i4 & 255);
    }

    public static boolean isSeekableFD(FileDescriptor fileDescriptor) throws ErrnoException {
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

    public static ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        short s = byteOrderedDataInputStream.readShort();
        boolean z = DEBUG;
        if (s == 18761) {
            if (z) {
                Log.d("ExifInterface", "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (s == 19789) {
            if (z) {
                Log.d("ExifInterface", "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        }
        throw new IOException("Invalid byte order: " + Integer.toHexString(s));
    }

    public final void addDefaultValuesForCompatibility() {
        String attribute = getAttribute("DateTimeOriginal");
        if (attribute != null && getAttribute("DateTime") == null) {
            this.mAttributes[0].put("DateTime", ExifAttribute.createString(attribute));
        }
        if (getAttribute("ImageWidth") == null) {
            this.mAttributes[0].put("ImageWidth", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("ImageLength") == null) {
            this.mAttributes[0].put("ImageLength", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("Orientation") == null) {
            this.mAttributes[0].put("Orientation", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute("LightSource") == null) {
            this.mAttributes[1].put("LightSource", ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    public final String getAttribute(String str) {
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            if (str.equals("GPSTimeStamp")) {
                int i = exifAttribute.format;
                if (i != 5 && i != 10) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "GPS Timestamp format is not rational. format=", "ExifInterface");
                    return null;
                }
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 3) {
                    Log.w("ExifInterface", "Invalid GPS Timestamp array. array=" + Arrays.toString(rationalArr));
                    return null;
                }
                Rational rational = rationalArr[0];
                Integer numValueOf = Integer.valueOf((int) (rational.numerator / rational.denominator));
                Rational rational2 = rationalArr[1];
                Integer numValueOf2 = Integer.valueOf((int) (rational2.numerator / rational2.denominator));
                Rational rational3 = rationalArr[2];
                return String.format("%02d:%02d:%02d", numValueOf, numValueOf2, Integer.valueOf((int) (rational3.numerator / rational3.denominator)));
            }
            if (!RATIONAL_TAGS_HANDLED_AS_DECIMALS_FOR_COMPATIBILITY.contains(str)) {
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public final byte[] getAttributeBytes() {
        ExifAttribute exifAttribute = getExifAttribute("Xmp");
        if (exifAttribute != null) {
            return exifAttribute.bytes;
        }
        return null;
    }

    public final ExifAttribute getExifAttribute(String str) {
        if ("ISOSpeedRatings".equals(str)) {
            if (DEBUG) {
                Log.d("ExifInterface", "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str = "PhotographicSensitivity";
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(str);
            if (exifAttribute != null) {
                return exifAttribute;
            }
        }
        return null;
    }

    public final void getHeifAttributes(final SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        String strExtractMetadata;
        String strExtractMetadata2;
        String strExtractMetadata3;
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
                                if (j2 >= 0 && j >= j2 + seekableByteOrderedDataInputStream.mDataInputStream.available()) {
                                    return -1;
                                }
                                seekableByteOrderedDataInputStream.seek(j);
                                this.mPosition = j;
                            }
                            if (i2 > seekableByteOrderedDataInputStream.mDataInputStream.available()) {
                                i2 = seekableByteOrderedDataInputStream.mDataInputStream.available();
                            }
                            int i3 = seekableByteOrderedDataInputStream.read(bArr, i, i2);
                            if (i3 >= 0) {
                                this.mPosition += i3;
                                return i3;
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
                String strExtractMetadata4 = mediaMetadataRetriever.extractMetadata(33);
                String strExtractMetadata5 = mediaMetadataRetriever.extractMetadata(34);
                String strExtractMetadata6 = mediaMetadataRetriever.extractMetadata(26);
                String strExtractMetadata7 = mediaMetadataRetriever.extractMetadata(17);
                if ("yes".equals(strExtractMetadata6)) {
                    strExtractMetadata = mediaMetadataRetriever.extractMetadata(29);
                    strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(30);
                    strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(31);
                } else if ("yes".equals(strExtractMetadata7)) {
                    strExtractMetadata = mediaMetadataRetriever.extractMetadata(18);
                    strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(19);
                    strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(24);
                } else {
                    strExtractMetadata = null;
                    strExtractMetadata2 = null;
                    strExtractMetadata3 = null;
                }
                if (strExtractMetadata != null) {
                    this.mAttributes[0].put("ImageWidth", ExifAttribute.createUShort(Integer.parseInt(strExtractMetadata), this.mExifByteOrder));
                }
                if (strExtractMetadata2 != null) {
                    this.mAttributes[0].put("ImageLength", ExifAttribute.createUShort(Integer.parseInt(strExtractMetadata2), this.mExifByteOrder));
                }
                if (strExtractMetadata3 != null) {
                    int i = Integer.parseInt(strExtractMetadata3);
                    this.mAttributes[0].put("Orientation", ExifAttribute.createUShort(i != 90 ? i != 180 ? i != 270 ? 1 : 8 : 3 : 6, this.mExifByteOrder));
                }
                if (strExtractMetadata4 != null && strExtractMetadata5 != null) {
                    int i2 = Integer.parseInt(strExtractMetadata4);
                    int i3 = Integer.parseInt(strExtractMetadata5);
                    if (i3 <= 6) {
                        throw new IOException("Invalid exif length");
                    }
                    seekableByteOrderedDataInputStream.seek(i2);
                    byte[] bArr = new byte[6];
                    seekableByteOrderedDataInputStream.readFully(bArr);
                    int i4 = i2 + 6;
                    int i5 = i3 - 6;
                    if (!Arrays.equals(bArr, IDENTIFIER_EXIF_APP1)) {
                        throw new IOException("Invalid identifier");
                    }
                    byte[] bArr2 = new byte[i5];
                    seekableByteOrderedDataInputStream.readFully(bArr2);
                    this.mOffsetToExifData = i4;
                    readExifSegment(0, bArr2);
                }
                String strExtractMetadata8 = mediaMetadataRetriever.extractMetadata(41);
                String strExtractMetadata9 = mediaMetadataRetriever.extractMetadata(42);
                if (strExtractMetadata8 != null && strExtractMetadata9 != null) {
                    int i6 = Integer.parseInt(strExtractMetadata8);
                    int i7 = Integer.parseInt(strExtractMetadata9);
                    long j = i6;
                    seekableByteOrderedDataInputStream.seek(j);
                    byte[] bArr3 = new byte[i7];
                    seekableByteOrderedDataInputStream.readFully(bArr3);
                    if (getAttribute("Xmp") == null) {
                        this.mAttributes[0].put("Xmp", new ExifAttribute(1, i7, j, bArr3));
                    }
                }
                if (DEBUG) {
                    Log.d("ExifInterface", "Heif meta: " + strExtractMetadata + "x" + strExtractMetadata2 + ", rotation " + strExtractMetadata3);
                }
                try {
                    mediaMetadataRetriever.release();
                } catch (IOException unused) {
                }
            } catch (RuntimeException e) {
                throw new UnsupportedOperationException("Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported.", e);
            }
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException unused2) {
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x018a, code lost:
    
        r23.mByteOrder = r22.mExifByteOrder;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x018e, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getJpegAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream, int i, int i2) throws Throwable {
        int i3;
        boolean z = DEBUG;
        if (z) {
            Log.d("ExifInterface", "getJpegAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        byte b = byteOrderedDataInputStream.readByte();
        byte b2 = -1;
        if (b != -1) {
            throw new IOException("Invalid marker: " + Integer.toHexString(b & 255));
        }
        if (byteOrderedDataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker: " + Integer.toHexString(b & 255));
        }
        int i4 = 2;
        while (true) {
            byte b3 = byteOrderedDataInputStream.readByte();
            if (b3 != b2) {
                throw new IOException("Invalid marker:" + Integer.toHexString(b3 & 255));
            }
            byte b4 = byteOrderedDataInputStream.readByte();
            if (z) {
                Log.d("ExifInterface", "Found JPEG segment indicator: " + Integer.toHexString(b4 & 255));
            }
            if (b4 != -39 && b4 != -38) {
                int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                int i5 = unsignedShort - 2;
                int i6 = i4 + 4;
                if (z) {
                    Log.d("ExifInterface", "JPEG segment: " + Integer.toHexString(b4 & 255) + " (length: " + unsignedShort + ")");
                }
                if (i5 < 0) {
                    throw new IOException("Invalid length");
                }
                if (b4 == -31) {
                    byte[] bArr = new byte[i5];
                    byteOrderedDataInputStream.readFully(bArr);
                    int i7 = i6 + i5;
                    byte[] bArr2 = IDENTIFIER_EXIF_APP1;
                    if (ExifInterfaceUtils.startsWith(bArr, bArr2)) {
                        byte[] attributeBytes = getAttributeBytes();
                        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, bArr2.length, i5);
                        this.mOffsetToExifData = i + i6 + bArr2.length;
                        readExifSegment(i2, bArrCopyOfRange);
                        setThumbnailData(new ByteOrderedDataInputStream(bArrCopyOfRange));
                        if (getAttributeBytes() == null) {
                            this.mXmpIsFromSeparateMarker = true;
                        } else if (attributeBytes != getAttributeBytes()) {
                            this.mXmpIsFromSeparateMarker = false;
                        }
                    } else {
                        byte[] bArr3 = IDENTIFIER_XMP_APP1;
                        if (ExifInterfaceUtils.startsWith(bArr, bArr3)) {
                            int length = i6 + bArr3.length;
                            byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr, bArr3.length, i5);
                            if (getAttribute("Xmp") == null) {
                                i3 = i7;
                                this.mAttributes[0].put("Xmp", new ExifAttribute(1, bArrCopyOfRange2.length, length, bArrCopyOfRange2));
                                this.mXmpIsFromSeparateMarker = true;
                            }
                            i5 = 0;
                            i6 = i3;
                        }
                    }
                    i3 = i7;
                    i5 = 0;
                    i6 = i3;
                } else if (b4 != -2) {
                    switch (b4) {
                        case -64:
                        case -63:
                        case -62:
                        case -61:
                            break;
                        default:
                            switch (b4) {
                                case -59:
                                case -58:
                                case CustomDeviceManager.ERROR_INVALID_MEDIA /* -57 */:
                                    break;
                                default:
                                    switch (b4) {
                                        case CustomDeviceManager.ERROR_ALREADY_EXISTS /* -55 */:
                                        case CustomDeviceManager.ERROR_NOT_FOUND /* -54 */:
                                        case CustomDeviceManager.ERROR_INVALID_CURRENT /* -53 */:
                                            break;
                                        default:
                                            switch (b4) {
                                            }
                                    }
                            }
                    }
                    byteOrderedDataInputStream.skipFully(1);
                    this.mAttributes[i2].put(i2 != 4 ? "ImageLength" : "ThumbnailImageLength", ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                    this.mAttributes[i2].put(i2 != 4 ? "ImageWidth" : "ThumbnailImageWidth", ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                    i5 = unsignedShort - 7;
                } else {
                    byte[] bArr4 = new byte[i5];
                    byteOrderedDataInputStream.readFully(bArr4);
                    if (getAttribute("UserComment") == null) {
                        this.mAttributes[1].put("UserComment", ExifAttribute.createString(new String(bArr4, ASCII)));
                    }
                    i5 = 0;
                }
                if (i5 < 0) {
                    throw new IOException("Invalid length");
                }
                byteOrderedDataInputStream.skipFully(i5);
                i4 = i6 + i5;
                b2 = -1;
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:169|12|(4:164|13|150|14)|(16:17|(2:19|20)(1:28)|23|29|(1:31)|32|(4:152|35|(7:154|39|40|(3:43|(1:45)(2:46|(1:48))|(1:179)(3:177|51|52))(1:180)|53|36|37)|176)|34|160|65|162|66|67|(1:73)(1:72)|74|(1:87)(8:156|89|158|90|91|(1:93)(1:94)|95|(1:107)(3:109|(2:110|(2:112|(2:170|114)(1:115))(2:171|116))|(1:118)(4:120|(2:121|(2:123|(1:173)(1:126))(3:172|127|(2:128|(2:130|(1:175)(1:133))(2:174|134))))|125|(1:136)(1:138)))))|16|160|65|162|66|67|(3:69|73|74)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
    
        if (r9 < 16) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ef, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f0, code lost:
    
        r6 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00f2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00f4, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00f6, code lost:
    
        if (r6 != null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00f8, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00fb, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fc, code lost:
    
        if (r2 != null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00fe, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0101, code lost:
    
        r0 = r18;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0139 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0107 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0105 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getMimeType(BufferedInputStream bufferedInputStream) throws Throwable {
        int i;
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        int i2;
        int i3;
        int i4;
        int i5;
        ByteOrderedDataInputStream byteOrderedDataInputStream2;
        long j;
        byte[] bArr;
        long j2;
        bufferedInputStream.mark(5000);
        byte[] bArr2 = new byte[5000];
        bufferedInputStream.read(bArr2);
        bufferedInputStream.reset();
        int i6 = 0;
        while (true) {
            byte[] bArr3 = JPEG_SIGNATURE;
            if (i6 >= bArr3.length) {
                return 4;
            }
            if (bArr2[i6] != bArr3[i6]) {
                byte[] bytes = "FUJIFILMCCD-RAW".getBytes(Charset.defaultCharset());
                for (int i7 = 0; i7 < bytes.length; i7++) {
                    if (bArr2[i7] != bytes[i7]) {
                        ByteOrderedDataInputStream byteOrderedDataInputStream3 = null;
                        try {
                            byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr2);
                            try {
                                try {
                                    j = byteOrderedDataInputStream.readInt();
                                    bArr = new byte[4];
                                    byteOrderedDataInputStream.readFully(bArr);
                                } catch (Exception e) {
                                    e = e;
                                    i = 0;
                                }
                            } catch (Throwable th) {
                                th = th;
                                byteOrderedDataInputStream3 = byteOrderedDataInputStream;
                                if (byteOrderedDataInputStream3 != null) {
                                    byteOrderedDataInputStream3.close();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            i = 0;
                            byteOrderedDataInputStream = null;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        if (Arrays.equals(bArr, HEIF_TYPE_FTYP)) {
                            if (j == 1) {
                                j = byteOrderedDataInputStream.readLong();
                                j2 = 16;
                            } else {
                                j2 = 8;
                            }
                            i = 0;
                            long j3 = 5000;
                            if (j > j3) {
                                j = j3;
                            }
                            long j4 = j - j2;
                            if (j4 >= 8) {
                                try {
                                    byte[] bArr4 = new byte[4];
                                    boolean z = false;
                                    boolean z2 = false;
                                    for (long j5 = 0; j5 < j4 / 4; j5++) {
                                        try {
                                            byteOrderedDataInputStream.readFully(bArr4);
                                            if (j5 != 1) {
                                                if (Arrays.equals(bArr4, HEIF_BRAND_MIF1)) {
                                                    z = true;
                                                } else if (Arrays.equals(bArr4, HEIF_BRAND_HEIC)) {
                                                    z2 = true;
                                                }
                                                if (z && z2) {
                                                    byteOrderedDataInputStream.close();
                                                    return 12;
                                                }
                                            }
                                        } catch (EOFException unused) {
                                        }
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    if (DEBUG) {
                                        Log.d("ExifInterface", "Exception parsing HEIF file type box.", e);
                                    }
                                    if (byteOrderedDataInputStream != null) {
                                        byteOrderedDataInputStream.close();
                                    }
                                    ByteOrderedDataInputStream byteOrderedDataInputStream4 = new ByteOrderedDataInputStream(bArr2);
                                    ByteOrder byteOrder = readByteOrder(byteOrderedDataInputStream4);
                                    this.mExifByteOrder = byteOrder;
                                    byteOrderedDataInputStream4.mByteOrder = byteOrder;
                                    short s = byteOrderedDataInputStream4.readShort();
                                    if (s == 20306) {
                                    }
                                    if (i2 == 0) {
                                    }
                                }
                            }
                            byteOrderedDataInputStream.close();
                            ByteOrderedDataInputStream byteOrderedDataInputStream42 = new ByteOrderedDataInputStream(bArr2);
                            ByteOrder byteOrder2 = readByteOrder(byteOrderedDataInputStream42);
                            this.mExifByteOrder = byteOrder2;
                            byteOrderedDataInputStream42.mByteOrder = byteOrder2;
                            short s2 = byteOrderedDataInputStream42.readShort();
                            i2 = (s2 == 20306 || s2 == 21330) ? 1 : i;
                            byteOrderedDataInputStream42.close();
                            if (i2 == 0) {
                                return 7;
                            }
                            try {
                                byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(bArr2);
                            } catch (Exception unused2) {
                            } catch (Throwable th3) {
                                th = th3;
                            }
                            try {
                                ByteOrder byteOrder3 = readByteOrder(byteOrderedDataInputStream2);
                                this.mExifByteOrder = byteOrder3;
                                byteOrderedDataInputStream2.mByteOrder = byteOrder3;
                                i3 = byteOrderedDataInputStream2.readShort() == 85 ? 1 : i;
                                byteOrderedDataInputStream2.close();
                            } catch (Exception unused3) {
                                byteOrderedDataInputStream3 = byteOrderedDataInputStream2;
                                if (byteOrderedDataInputStream3 != null) {
                                    byteOrderedDataInputStream3.close();
                                }
                                i3 = i;
                                if (i3 == 0) {
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                byteOrderedDataInputStream3 = byteOrderedDataInputStream2;
                                if (byteOrderedDataInputStream3 != null) {
                                    byteOrderedDataInputStream3.close();
                                }
                                throw th;
                            }
                            if (i3 == 0) {
                                return 10;
                            }
                            int i8 = i;
                            while (true) {
                                byte[] bArr5 = PNG_SIGNATURE;
                                if (i8 >= bArr5.length) {
                                    i4 = 1;
                                    break;
                                }
                                if (bArr2[i8] != bArr5[i8]) {
                                    i4 = i;
                                    break;
                                }
                                i8++;
                            }
                            if (i4 != 0) {
                                return 13;
                            }
                            int i9 = i;
                            while (true) {
                                byte[] bArr6 = WEBP_SIGNATURE_1;
                                if (i9 >= bArr6.length) {
                                    int i10 = i;
                                    while (true) {
                                        byte[] bArr7 = WEBP_SIGNATURE_2;
                                        if (i10 >= bArr7.length) {
                                            i5 = 1;
                                            break;
                                        }
                                        if (bArr2[bArr6.length + i10 + 4] != bArr7[i10]) {
                                            break;
                                        }
                                        i10++;
                                    }
                                } else {
                                    if (bArr2[i9] != bArr6[i9]) {
                                        break;
                                    }
                                    i9++;
                                }
                            }
                            i5 = i;
                            if (i5 != 0) {
                                return 14;
                            }
                            return i;
                        }
                        byteOrderedDataInputStream.close();
                        i = 0;
                        ByteOrderedDataInputStream byteOrderedDataInputStream422 = new ByteOrderedDataInputStream(bArr2);
                        ByteOrder byteOrder22 = readByteOrder(byteOrderedDataInputStream422);
                        this.mExifByteOrder = byteOrder22;
                        byteOrderedDataInputStream422.mByteOrder = byteOrder22;
                        short s22 = byteOrderedDataInputStream422.readShort();
                        if (s22 == 20306) {
                            byteOrderedDataInputStream422.close();
                        }
                        if (i2 == 0) {
                        }
                    }
                }
                return 9;
            }
            i6++;
        }
    }

    public final void getOrfAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        int i;
        int i2;
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get("MakerNote");
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
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[7].get("PreviewImageStart");
            ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[7].get("PreviewImageLength");
            if (exifAttribute2 != null && exifAttribute3 != null) {
                this.mAttributes[5].put("JPEGInterchangeFormat", exifAttribute2);
                this.mAttributes[5].put("JPEGInterchangeFormatLength", exifAttribute3);
            }
            ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[8].get("AspectFrame");
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
                ExifAttribute exifAttributeCreateUShort = ExifAttribute.createUShort(i5, this.mExifByteOrder);
                ExifAttribute exifAttributeCreateUShort2 = ExifAttribute.createUShort(i6, this.mExifByteOrder);
                this.mAttributes[0].put("ImageWidth", exifAttributeCreateUShort);
                this.mAttributes[0].put("ImageLength", exifAttributeCreateUShort2);
            }
        }
    }

    public final void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d("ExifInterface", "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        int i = byteOrderedDataInputStream.mPosition;
        byteOrderedDataInputStream.skipFully(PNG_SIGNATURE.length);
        while (true) {
            try {
                int i2 = byteOrderedDataInputStream.readInt();
                int i3 = byteOrderedDataInputStream.readInt();
                int i4 = byteOrderedDataInputStream.mPosition - i;
                if (i4 == 16 && i3 != PNG_CHUNK_TYPE_IHDR) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appear as the first chunk");
                }
                if (i3 == PNG_CHUNK_TYPE_IEND) {
                    return;
                }
                if (i3 == PNG_CHUNK_TYPE_EXIF) {
                    this.mOffsetToExifData = i4;
                    byte[] bArr = new byte[i2];
                    byteOrderedDataInputStream.readFully(bArr);
                    int i5 = byteOrderedDataInputStream.readInt();
                    CRC32 crc32 = new CRC32();
                    crc32.update(i3 >>> 24);
                    crc32.update(i3 >>> 16);
                    crc32.update(i3 >>> 8);
                    crc32.update(i3);
                    crc32.update(bArr);
                    if (((int) crc32.getValue()) == i5) {
                        readExifSegment(0, bArr);
                        validateImages();
                        setThumbnailData(new ByteOrderedDataInputStream(bArr));
                        return;
                    } else {
                        throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + i5 + ", calculated CRC value: " + crc32.getValue());
                    }
                }
                byteOrderedDataInputStream.skipFully(i2 + 4);
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt PNG file.", e);
            }
        }
    }

    public final void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
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
        int i4 = byteOrderedDataInputStream.readInt();
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i4, "numberOfDirectoryEntry: ", "ExifInterface");
        }
        for (int i5 = 0; i5 < i4; i5++) {
            int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int unsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (unsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short s = byteOrderedDataInputStream.readShort();
                short s2 = byteOrderedDataInputStream.readShort();
                ExifAttribute exifAttributeCreateUShort = ExifAttribute.createUShort(s, this.mExifByteOrder);
                ExifAttribute exifAttributeCreateUShort2 = ExifAttribute.createUShort(s2, this.mExifByteOrder);
                this.mAttributes[0].put("ImageLength", exifAttributeCreateUShort);
                this.mAttributes[0].put("ImageWidth", exifAttributeCreateUShort2);
                if (z) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(s, s2, "Updated to length: ", ", width: ", "ExifInterface");
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipFully(unsignedShort2);
        }
    }

    public final void getRawAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        ExifAttribute exifAttribute;
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 5);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType != 8 || (exifAttribute = (ExifAttribute) this.mAttributes[1].get("MakerNote")) == null) {
            return;
        }
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
        seekableByteOrderedDataInputStream2.mByteOrder = this.mExifByteOrder;
        seekableByteOrderedDataInputStream2.skipFully(6);
        readImageFileDirectory(seekableByteOrderedDataInputStream2, 9);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[9].get("ColorSpace");
        if (exifAttribute2 != null) {
            this.mAttributes[1].put("ColorSpace", exifAttribute2);
        }
    }

    public final void getRw2Attributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d("ExifInterface", "getRw2Attributes starting with: " + seekableByteOrderedDataInputStream);
        }
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[0].get("JpgFromRaw");
        if (exifAttribute != null) {
            getJpegAttributes(new ByteOrderedDataInputStream(exifAttribute.bytes), (int) exifAttribute.bytesOffset, 5);
        }
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[0].get("ISO");
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[1].get("PhotographicSensitivity");
        if (exifAttribute2 == null || exifAttribute3 != null) {
            return;
        }
        this.mAttributes[1].put("PhotographicSensitivity", exifAttribute2);
    }

    public final boolean getStandaloneAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        byte[] bArr2 = new byte[bArr.length];
        seekableByteOrderedDataInputStream.readFully(bArr2);
        if (!Arrays.equals(bArr2, bArr)) {
            Log.w("ExifInterface", "Given data is not EXIF-only.");
            return false;
        }
        byte[] bArrCopyOf = new byte[1024];
        int i = 0;
        while (true) {
            if (i == bArrCopyOf.length) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, bArrCopyOf.length * 2);
            }
            int i2 = seekableByteOrderedDataInputStream.mDataInputStream.read(bArrCopyOf, i, bArrCopyOf.length - i);
            if (i2 == -1) {
                byte[] bArrCopyOf2 = Arrays.copyOf(bArrCopyOf, i);
                this.mOffsetToExifData = bArr.length;
                readExifSegment(0, bArrCopyOf2);
                return true;
            }
            i += i2;
            seekableByteOrderedDataInputStream.mPosition += i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0092  */
    /* JADX WARN: Type inference failed for: r1v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v6, types: [android.content.res.AssetManager$AssetInputStream, java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.Closeable, java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] getThumbnailBytes() throws Throwable {
        FileDescriptor fileDescriptor;
        FileInputStream fileInputStream;
        Closeable closeable = null;
        if (this.mHasThumbnail) {
            ?? fileInputStream2 = this.mThumbnailBytes;
            try {
                if (fileInputStream2 != 0) {
                    return fileInputStream2;
                }
                try {
                    fileInputStream2 = this.mAssetInputStream;
                    if (fileInputStream2 != 0) {
                        try {
                            if (!fileInputStream2.markSupported()) {
                                Log.d("ExifInterface", "Cannot read thumbnail from inputstream without mark/reset support");
                                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                return null;
                            }
                            fileInputStream2.reset();
                            fileInputStream = fileInputStream2;
                            fileDescriptor = null;
                            fileInputStream2 = fileInputStream;
                            try {
                                ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream((InputStream) fileInputStream2);
                                byteOrderedDataInputStream.skipFully(this.mThumbnailOffset + this.mOffsetToExifData);
                                byte[] bArr = new byte[this.mThumbnailLength];
                                byteOrderedDataInputStream.readFully(bArr);
                                this.mThumbnailBytes = bArr;
                                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                if (fileDescriptor != null) {
                                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                                }
                                return bArr;
                            } catch (Exception e) {
                                e = e;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            fileDescriptor = null;
                        } catch (Throwable th) {
                            th = th;
                            fileDescriptor = null;
                            closeable = fileInputStream2;
                            ExifInterfaceUtils.closeQuietly(closeable);
                            if (fileDescriptor != null) {
                            }
                            throw th;
                        }
                    } else {
                        if (this.mFilename != null) {
                            fileInputStream = new FileInputStream(this.mFilename);
                            fileDescriptor = null;
                            fileInputStream2 = fileInputStream;
                            ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream((InputStream) fileInputStream2);
                            byteOrderedDataInputStream2.skipFully(this.mThumbnailOffset + this.mOffsetToExifData);
                            byte[] bArr2 = new byte[this.mThumbnailLength];
                            byteOrderedDataInputStream2.readFully(bArr2);
                            this.mThumbnailBytes = bArr2;
                            ExifInterfaceUtils.closeQuietly(fileInputStream2);
                            if (fileDescriptor != null) {
                            }
                            return bArr2;
                        }
                        FileDescriptor fileDescriptorDup = Os.dup(this.mSeekableFileDescriptor);
                        try {
                            Os.lseek(fileDescriptorDup, 0L, OsConstants.SEEK_SET);
                            fileDescriptor = fileDescriptorDup;
                            fileInputStream2 = new FileInputStream(fileDescriptorDup);
                            ByteOrderedDataInputStream byteOrderedDataInputStream22 = new ByteOrderedDataInputStream((InputStream) fileInputStream2);
                            byteOrderedDataInputStream22.skipFully(this.mThumbnailOffset + this.mOffsetToExifData);
                            byte[] bArr22 = new byte[this.mThumbnailLength];
                            byteOrderedDataInputStream22.readFully(bArr22);
                            this.mThumbnailBytes = bArr22;
                            ExifInterfaceUtils.closeQuietly(fileInputStream2);
                            if (fileDescriptor != null) {
                            }
                            return bArr22;
                        } catch (Exception e3) {
                            e = e3;
                            fileDescriptor = fileDescriptorDup;
                            fileInputStream2 = 0;
                        } catch (Throwable th2) {
                            th = th2;
                            fileDescriptor = fileDescriptorDup;
                            ExifInterfaceUtils.closeQuietly(closeable);
                            if (fileDescriptor != null) {
                                ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                            }
                            throw th;
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    fileInputStream2 = 0;
                    fileDescriptor = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileDescriptor = null;
                }
                Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                if (fileDescriptor != null) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
        return null;
    }

    public final void getWebpAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d("ExifInterface", "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.LITTLE_ENDIAN;
        byteOrderedDataInputStream.skipFully(WEBP_SIGNATURE_1.length);
        int i = byteOrderedDataInputStream.readInt() + 8;
        byte[] bArr = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 8;
        while (true) {
            try {
                byte[] bArr2 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr2);
                int i2 = byteOrderedDataInputStream.readInt();
                int i3 = length + 8;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr2)) {
                    byte[] bArrCopyOfRange = new byte[i2];
                    byteOrderedDataInputStream.readFully(bArrCopyOfRange);
                    byte[] bArr3 = IDENTIFIER_EXIF_APP1;
                    if (ExifInterfaceUtils.startsWith(bArrCopyOfRange, bArr3)) {
                        bArrCopyOfRange = Arrays.copyOfRange(bArrCopyOfRange, bArr3.length, i2 - bArr3.length);
                    }
                    this.mOffsetToExifData = i3;
                    readExifSegment(0, bArrCopyOfRange);
                    setThumbnailData(new ByteOrderedDataInputStream(bArrCopyOfRange));
                    return;
                }
                if (i2 % 2 == 1) {
                    i2++;
                }
                length = i3 + i2;
                if (length == i) {
                    return;
                }
                if (length > i) {
                    throw new IOException("Encountered WebP file with invalid chunk size");
                }
                byteOrderedDataInputStream.skipFully(i2);
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt WebP file.", e);
            }
        }
    }

    public final void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap map) throws Throwable {
        ExifAttribute exifAttribute = (ExifAttribute) map.get("JPEGInterchangeFormat");
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get("JPEGInterchangeFormatLength");
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
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(intValue, intValue2, "Setting thumbnail attributes with offset: ", ", length: ", "ExifInterface");
        }
    }

    public final void initForFilename(String str) throws Throwable {
        FileInputStream fileInputStream;
        if (str == null) {
            throw new NullPointerException("filename cannot be null");
        }
        FileInputStream fileInputStream2 = null;
        this.mAssetInputStream = null;
        this.mFilename = str;
        try {
            fileInputStream = new FileInputStream(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (isSeekableFD(fileInputStream.getFD())) {
                this.mSeekableFileDescriptor = fileInputStream.getFD();
            } else {
                this.mSeekableFileDescriptor = null;
            }
            loadAttributes(fileInputStream);
            ExifInterfaceUtils.closeQuietly(fileInputStream);
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            ExifInterfaceUtils.closeQuietly(fileInputStream2);
            throw th;
        }
    }

    public final boolean isThumbnail(HashMap map) {
        ExifAttribute exifAttribute = (ExifAttribute) map.get("ImageLength");
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get("ImageWidth");
        if (exifAttribute == null || exifAttribute2 == null) {
            return false;
        }
        return exifAttribute.getIntValue(this.mExifByteOrder) <= 512 && exifAttribute2.getIntValue(this.mExifByteOrder) <= 512;
    }

    public final void loadAttributes(InputStream inputStream) {
        boolean z = DEBUG;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            try {
                try {
                    this.mAttributes[i] = new HashMap();
                } catch (IOException | UnsupportedOperationException e) {
                    if (z) {
                        Log.w("ExifInterface", "Invalid image: ExifInterface got an unsupported image format file (ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    }
                    addDefaultValuesForCompatibility();
                    if (z) {
                        printAttributes();
                        return;
                    }
                    return;
                }
            } catch (Throwable th) {
                addDefaultValuesForCompatibility();
                if (z) {
                    printAttributes();
                }
                throw th;
            }
        }
        boolean z2 = this.mIsExifDataOnly;
        if (!z2) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
            this.mMimeType = getMimeType(bufferedInputStream);
            inputStream = bufferedInputStream;
        }
        int i2 = this.mMimeType;
        if (i2 == 4 || i2 == 9 || i2 == 13 || i2 == 14) {
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
            if (!z2) {
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
                if (z) {
                    printAttributes();
                    return;
                }
                return;
            }
            seekableByteOrderedDataInputStream.seek(this.mOffsetToExifData);
            setThumbnailData(seekableByteOrderedDataInputStream);
        }
        addDefaultValuesForCompatibility();
        if (z) {
            printAttributes();
        }
    }

    public final void parseTiffHeaders(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        ByteOrder byteOrder = readByteOrder(seekableByteOrderedDataInputStream);
        this.mExifByteOrder = byteOrder;
        seekableByteOrderedDataInputStream.mByteOrder = byteOrder;
        int unsignedShort = seekableByteOrderedDataInputStream.readUnsignedShort();
        int i = this.mMimeType;
        if (i != 7 && i != 10 && unsignedShort != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(unsignedShort));
        }
        int i2 = seekableByteOrderedDataInputStream.readInt();
        if (i2 < 8) {
            throw new IOException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Invalid first Ifd offset: "));
        }
        int i3 = i2 - 8;
        if (i3 > 0) {
            seekableByteOrderedDataInputStream.skipFully(i3);
        }
    }

    public final void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "The size of tag group[", "]: ");
            sbM.append(this.mAttributes[i].size());
            Log.d("ExifInterface", sbM.toString());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) entry.getValue();
                Log.d("ExifInterface", "tagName: " + ((String) entry.getKey()) + ", tagType: " + exifAttribute.toString() + ", tagValue: '" + exifAttribute.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public final void readExifSegment(int i, byte[] bArr) throws IOException {
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(bArr);
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readImageFileDirectory(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) throws IOException {
        short s;
        boolean z;
        long j;
        boolean z2;
        int i2;
        short s2;
        long j2;
        int unsignedShort;
        long j3;
        int i3;
        int i4 = i;
        ((HashSet) this.mAttributesOffsets).add(Integer.valueOf(seekableByteOrderedDataInputStream.mPosition));
        short s3 = seekableByteOrderedDataInputStream.readShort();
        boolean z3 = DEBUG;
        if (z3) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(s3, "numberOfDirectoryEntry: ", "ExifInterface");
        }
        if (s3 <= 0) {
            return;
        }
        short s4 = 0;
        while (s4 < s3) {
            int unsignedShort2 = seekableByteOrderedDataInputStream.readUnsignedShort();
            int unsignedShort3 = seekableByteOrderedDataInputStream.readUnsignedShort();
            int i5 = seekableByteOrderedDataInputStream.readInt();
            long j4 = seekableByteOrderedDataInputStream.mPosition + 4;
            ExifTag exifTag = (ExifTag) sExifTagMapsForReading[i4].get(Integer.valueOf(unsignedShort2));
            if (z3) {
                s = s3;
                z = z3;
                Log.d("ExifInterface", String.format("ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d", Integer.valueOf(i4), Integer.valueOf(unsignedShort2), exifTag != null ? exifTag.name : null, Integer.valueOf(unsignedShort3), Integer.valueOf(i5)));
            } else {
                s = s3;
                z = z3;
            }
            if (exifTag != null) {
                if (unsignedShort3 > 0) {
                    if (unsignedShort3 < IFD_FORMAT_BYTES_PER_FORMAT.length) {
                        int i6 = exifTag.primaryFormat;
                        if (i6 == 7 || unsignedShort3 == 7 || i6 == unsignedShort3 || (i2 = exifTag.secondaryFormat) == unsignedShort3 || (((i6 == 4 || i2 == 4) && unsignedShort3 == 3) || (((i6 == 9 || i2 == 9) && unsignedShort3 == 8) || ((i6 == 12 || i2 == 12) && unsignedShort3 == 11)))) {
                            if (unsignedShort3 == 7) {
                                unsignedShort3 = i6;
                            }
                            j = i5 * r10[unsignedShort3];
                            if (j < 0 || j > 2147483647L) {
                                if (z) {
                                    ListPopupWindow$$ExternalSyntheticOutline0.m(i5, "Skip the tag entry since the number of components is invalid: ", "ExifInterface");
                                }
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                        } else if (z) {
                            StringBuilder sb = new StringBuilder("Skip the tag entry since data format (");
                            sb.append(IFD_FORMAT_NAMES[unsignedShort3]);
                            sb.append(") is unexpected for tag: ");
                            ExifInterface$$ExternalSyntheticOutline0.m(sb, exifTag.name, "ExifInterface");
                        }
                    } else if (z) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(unsignedShort3, "Skip the tag entry since data format is invalid: ", "ExifInterface");
                    }
                }
                if (z2) {
                    seekableByteOrderedDataInputStream.seek(j4);
                    s2 = s4;
                } else {
                    if (j > 4) {
                        int i7 = seekableByteOrderedDataInputStream.readInt();
                        if (z) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(i7, "seek to data offset: ", "ExifInterface");
                        }
                        s2 = s4;
                        if (this.mMimeType != 7) {
                            j2 = j4;
                            seekableByteOrderedDataInputStream.seek(i7);
                        } else {
                            if ("MakerNote".equals(exifTag.name)) {
                                this.mOrfMakerNoteOffset = i7;
                            } else {
                                if (i4 == 6 && "ThumbnailImage".equals(exifTag.name)) {
                                    this.mOrfThumbnailOffset = i7;
                                    this.mOrfThumbnailLength = i5;
                                    ExifAttribute exifAttributeCreateUShort = ExifAttribute.createUShort(6, this.mExifByteOrder);
                                    j2 = j4;
                                    ExifAttribute exifAttributeCreateULong = ExifAttribute.createULong(this.mOrfThumbnailOffset, this.mExifByteOrder);
                                    ExifAttribute exifAttributeCreateULong2 = ExifAttribute.createULong(this.mOrfThumbnailLength, this.mExifByteOrder);
                                    this.mAttributes[4].put("Compression", exifAttributeCreateUShort);
                                    this.mAttributes[4].put("JPEGInterchangeFormat", exifAttributeCreateULong);
                                    this.mAttributes[4].put("JPEGInterchangeFormatLength", exifAttributeCreateULong2);
                                }
                                seekableByteOrderedDataInputStream.seek(i7);
                            }
                            j2 = j4;
                            seekableByteOrderedDataInputStream.seek(i7);
                        }
                    } else {
                        s2 = s4;
                        j2 = j4;
                    }
                    Integer num = (Integer) sExifPointerTagMap.get(Integer.valueOf(unsignedShort2));
                    if (z) {
                        Log.d("ExifInterface", "nextIfdType: " + num + " byteCount: " + j);
                    }
                    if (num != null) {
                        if (unsignedShort3 != 3) {
                            if (unsignedShort3 == 4) {
                                j3 = seekableByteOrderedDataInputStream.readInt() & 4294967295L;
                            } else if (unsignedShort3 == 8) {
                                unsignedShort = seekableByteOrderedDataInputStream.readShort();
                            } else if (unsignedShort3 == 9 || unsignedShort3 == 13) {
                                unsignedShort = seekableByteOrderedDataInputStream.readInt();
                            } else {
                                j3 = -1;
                            }
                            if (z) {
                                Log.d("ExifInterface", String.format("Offset: %d, tagName: %s", Long.valueOf(j3), exifTag.name));
                            }
                            if (j3 > 0 || ((i3 = seekableByteOrderedDataInputStream.mLength) != -1 && j3 >= i3)) {
                                if (z) {
                                    String strM = ValueAnimator$$ExternalSyntheticOutline0.m("Skip jump into the IFD since its offset is invalid: ", j3);
                                    if (seekableByteOrderedDataInputStream.mLength != -1) {
                                        strM = ReorderTile$$ExternalSyntheticOutline0.m(seekableByteOrderedDataInputStream.mLength, ")", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM, " (total length: "));
                                    }
                                    Log.d("ExifInterface", strM);
                                }
                            } else if (!((HashSet) this.mAttributesOffsets).contains(Integer.valueOf((int) j3))) {
                                seekableByteOrderedDataInputStream.seek(j3);
                                readImageFileDirectory(seekableByteOrderedDataInputStream, num.intValue());
                            } else if (z) {
                                StringBuilder sb2 = new StringBuilder("Skip jump into the IFD since it has already been read: IfdType ");
                                sb2.append(num);
                                sb2.append(" (at ");
                                sb2.append(j3);
                                ExifInterface$$ExternalSyntheticOutline0.m(sb2, ")", "ExifInterface");
                            }
                            seekableByteOrderedDataInputStream.seek(j2);
                        } else {
                            unsignedShort = seekableByteOrderedDataInputStream.readUnsignedShort();
                        }
                        j3 = unsignedShort;
                        if (z) {
                        }
                        if (j3 > 0) {
                            if (z) {
                            }
                            seekableByteOrderedDataInputStream.seek(j2);
                        }
                    } else {
                        long j5 = j2;
                        int i8 = seekableByteOrderedDataInputStream.mPosition + this.mOffsetToExifData;
                        byte[] bArr = new byte[(int) j];
                        seekableByteOrderedDataInputStream.readFully(bArr);
                        ExifAttribute exifAttribute = new ExifAttribute(unsignedShort3, i5, i8, bArr);
                        this.mAttributes[i].put(exifTag.name, exifAttribute);
                        String str = exifTag.name;
                        if ("DNGVersion".equals(str)) {
                            this.mMimeType = 3;
                        }
                        if ((("Make".equals(str) || "Model".equals(str)) && exifAttribute.getStringValue(this.mExifByteOrder).contains("PENTAX")) || ("Compression".equals(str) && exifAttribute.getIntValue(this.mExifByteOrder) == 65535)) {
                            this.mMimeType = 8;
                        }
                        if (seekableByteOrderedDataInputStream.mPosition != j5) {
                            seekableByteOrderedDataInputStream.seek(j5);
                        }
                    }
                }
                s4 = (short) (s2 + 1);
                i4 = i;
                s3 = s;
                z3 = z;
            } else if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(unsignedShort2, "Skip the tag entry since tag number is not defined: ", "ExifInterface");
            }
            j = 0;
            z2 = false;
            if (z2) {
            }
            s4 = (short) (s2 + 1);
            i4 = i;
            s3 = s;
            z3 = z;
        }
        boolean z4 = z3;
        int i9 = seekableByteOrderedDataInputStream.readInt();
        if (z4) {
            Log.d("ExifInterface", String.format("nextIfdOffset: %d", Integer.valueOf(i9)));
        }
        long j6 = i9;
        if (j6 <= 0) {
            if (z4) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i9, "Stop reading file since a wrong offset may cause an infinite loop: ", "ExifInterface");
            }
        } else {
            if (((HashSet) this.mAttributesOffsets).contains(Integer.valueOf(i9))) {
                if (z4) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i9, "Stop reading file since re-reading an IFD may cause an infinite loop: ", "ExifInterface");
                    return;
                }
                return;
            }
            seekableByteOrderedDataInputStream.seek(j6);
            if (this.mAttributes[4].isEmpty()) {
                readImageFileDirectory(seekableByteOrderedDataInputStream, 4);
            } else if (this.mAttributes[5].isEmpty()) {
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
        if (this.mAttributes[i].isEmpty() || this.mAttributes[i].get(str) == null) {
            return;
        }
        HashMap map = this.mAttributes[i];
        map.put(str2, (ExifAttribute) map.get(str));
        this.mAttributes[i].remove(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00fb A[Catch: all -> 0x010b, Exception -> 0x010e, TryCatch #19 {Exception -> 0x010e, all -> 0x010b, blocks: (B:79:0x00f7, B:81:0x00fb, B:88:0x0119, B:87:0x0111), top: B:128:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0111 A[Catch: all -> 0x010b, Exception -> 0x010e, TryCatch #19 {Exception -> 0x010e, all -> 0x010b, blocks: (B:79:0x00f7, B:81:0x00fb, B:88:0x0119, B:87:0x0111), top: B:128:0x00f7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void saveAttributes() throws Throwable {
        FileOutputStream fileOutputStream;
        File fileCreateTempFile;
        FileInputStream fileInputStream;
        Closeable closeable;
        FileOutputStream fileOutputStream2;
        FileInputStream fileInputStream2;
        FileOutputStream fileOutputStream3;
        FileInputStream fileInputStream3;
        Object obj;
        BufferedInputStream bufferedInputStream;
        int i = this.mMimeType;
        if (i != 4 && i != 13 && i != 14) {
            throw new IOException("ExifInterface only supports saving attributes for JPEG, PNG, and WebP formats.");
        }
        if (this.mSeekableFileDescriptor == null && this.mFilename == null) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
        if (this.mHasThumbnail && this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
            throw new IOException("ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips");
        }
        int i2 = this.mThumbnailCompression;
        InputStream inputStream = null;
        this.mThumbnailBytes = (i2 == 6 || i2 == 7) ? getThumbnailBytes() : null;
        try {
            fileCreateTempFile = File.createTempFile("temp", "tmp");
            if (this.mFilename != null) {
                fileInputStream = new FileInputStream(this.mFilename);
            } else {
                Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                fileInputStream = new FileInputStream(this.mSeekableFileDescriptor);
            }
            try {
                fileOutputStream = new FileOutputStream(fileCreateTempFile);
            } catch (Exception e) {
                e = e;
                fileOutputStream = null;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            ExifInterfaceUtils.copy(fileInputStream, fileOutputStream);
            ExifInterfaceUtils.closeQuietly(fileInputStream);
            ExifInterfaceUtils.closeQuietly(fileOutputStream);
            try {
            } catch (Throwable th3) {
                th = th3;
            }
            try {
                try {
                    fileInputStream3 = new FileInputStream(fileCreateTempFile);
                    try {
                        if (this.mFilename != null) {
                            fileOutputStream2 = new FileOutputStream(this.mFilename);
                        } else {
                            Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                            fileOutputStream2 = new FileOutputStream(this.mSeekableFileDescriptor);
                        }
                        try {
                            bufferedInputStream = new BufferedInputStream(fileInputStream3);
                        } catch (Exception e3) {
                            e = e3;
                            obj = null;
                            inputStream = fileInputStream3;
                            try {
                                try {
                                    fileInputStream2 = new FileInputStream(fileCreateTempFile);
                                } catch (Exception e4) {
                                    e = e4;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                            }
                            try {
                                if (this.mFilename != null) {
                                }
                                fileOutputStream2 = fileOutputStream3;
                                ExifInterfaceUtils.copy(fileInputStream2, fileOutputStream2);
                                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                throw new IOException("Failed to save new file", e);
                            } catch (Exception e5) {
                                e = e5;
                                inputStream = fileInputStream2;
                                throw new IOException("Failed to save new file. Original file is stored in " + fileCreateTempFile.getAbsolutePath(), e);
                            } catch (Throwable th5) {
                                th = th5;
                                inputStream = fileInputStream2;
                                ExifInterfaceUtils.closeQuietly(inputStream);
                                ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                throw th;
                            }
                        }
                    } catch (Exception e6) {
                        e = e6;
                        fileOutputStream2 = null;
                        obj = null;
                    }
                } catch (Exception e7) {
                    e = e7;
                    fileOutputStream2 = null;
                }
            } catch (Throwable th6) {
                th = th6;
                closeable = null;
                ExifInterfaceUtils.closeQuietly(inputStream);
                ExifInterfaceUtils.closeQuietly(closeable);
                if (0 == 0) {
                    fileCreateTempFile.delete();
                }
                throw th;
            }
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream2);
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
                    fileCreateTempFile.delete();
                    this.mThumbnailBytes = null;
                } catch (Exception e8) {
                    e = e8;
                    inputStream = fileInputStream3;
                    fileInputStream2 = new FileInputStream(fileCreateTempFile);
                    if (this.mFilename != null) {
                        Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                        fileOutputStream3 = new FileOutputStream(this.mSeekableFileDescriptor);
                    } else {
                        fileOutputStream3 = new FileOutputStream(this.mFilename);
                    }
                    fileOutputStream2 = fileOutputStream3;
                    ExifInterfaceUtils.copy(fileInputStream2, fileOutputStream2);
                    ExifInterfaceUtils.closeQuietly(fileInputStream2);
                    ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                    throw new IOException("Failed to save new file", e);
                }
            } catch (Exception e9) {
                e = e9;
            } catch (Throwable th7) {
                th = th7;
                closeable = null;
                inputStream = bufferedInputStream;
                ExifInterfaceUtils.closeQuietly(inputStream);
                ExifInterfaceUtils.closeQuietly(closeable);
                if (0 == 0) {
                }
                throw th;
            }
        } catch (Exception e10) {
            e = e10;
            inputStream = fileInputStream;
            try {
                throw new IOException("Failed to copy original file to temp file", e);
            } catch (Throwable th8) {
                th = th8;
                ExifInterfaceUtils.closeQuietly(inputStream);
                ExifInterfaceUtils.closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th9) {
            th = th9;
            inputStream = fileInputStream;
            ExifInterfaceUtils.closeQuietly(inputStream);
            ExifInterfaceUtils.closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public final void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
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
        ExifAttribute exifAttribute = (getAttribute("Xmp") == null || !this.mXmpIsFromSeparateMarker) ? null : (ExifAttribute) this.mAttributes[0].remove("Xmp");
        byteOrderedDataOutputStream.writeByte(-1);
        byteOrderedDataOutputStream.writeByte(-31);
        writeExifSegment(byteOrderedDataOutputStream);
        byte[] bArr = IDENTIFIER_XMP_APP1;
        if (exifAttribute != null && this.mXmpIsFromSeparateMarker) {
            byteOrderedDataOutputStream.write(-1);
            byteOrderedDataOutputStream.writeByte(-31);
            int length = bArr.length + 2;
            byte[] bArr2 = exifAttribute.bytes;
            byteOrderedDataOutputStream.writeUnsignedShort(length + bArr2.length);
            byteOrderedDataOutputStream.write(bArr);
            byteOrderedDataOutputStream.write(bArr2);
        }
        if (exifAttribute != null) {
            this.mAttributes[0].put("Xmp", exifAttribute);
        }
        byte[] bArr3 = new byte[4096];
        while (byteOrderedDataInputStream.readByte() == -1) {
            byte b = byteOrderedDataInputStream.readByte();
            if (b == -39 || b == -38) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b);
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
                return;
            }
            if (b != -31) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b);
                int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                byteOrderedDataOutputStream.writeUnsignedShort(unsignedShort);
                int i = unsignedShort - 2;
                if (i < 0) {
                    throw new IOException("Invalid length");
                }
                while (i > 0) {
                    int i2 = byteOrderedDataInputStream.read(bArr3, 0, Math.min(i, 4096));
                    if (i2 >= 0) {
                        byteOrderedDataOutputStream.write(bArr3, 0, i2);
                        i -= i2;
                    }
                }
            } else {
                int unsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
                int length2 = unsignedShort2 - 2;
                if (length2 < 0) {
                    throw new IOException("Invalid length");
                }
                int length3 = bArr.length;
                byte[] bArr4 = IDENTIFIER_EXIF_APP1;
                byte[] bArr5 = length2 >= length3 ? new byte[bArr.length] : length2 >= bArr4.length ? new byte[bArr4.length] : null;
                if (bArr5 != null) {
                    byteOrderedDataInputStream.readFully(bArr5);
                    if (ExifInterfaceUtils.startsWith(bArr5, bArr4) || (ExifInterfaceUtils.startsWith(bArr5, bArr) && this.mXmpIsFromSeparateMarker)) {
                        byteOrderedDataInputStream.skipFully(length2 - bArr5.length);
                    }
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b);
                byteOrderedDataOutputStream.writeUnsignedShort(unsignedShort2);
                if (bArr5 != null) {
                    length2 -= bArr5.length;
                    byteOrderedDataOutputStream.write(bArr5);
                }
                while (length2 > 0) {
                    int i3 = byteOrderedDataInputStream.read(bArr3, 0, Math.min(length2, 4096));
                    if (i3 >= 0) {
                        byteOrderedDataOutputStream.write(bArr3, 0, i3);
                        length2 -= i3;
                    }
                }
            }
        }
        throw new IOException("Invalid marker");
    }

    public final void savePngAttributes(InputStream inputStream, OutputStream outputStream) throws Throwable {
        if (DEBUG) {
            Log.d("ExifInterface", "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, PNG_SIGNATURE.length);
        if (this.mOffsetToExifData == 0) {
            int i = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(i);
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, i + 8);
        } else {
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, (r2 - r7.length) - 8);
            byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt() + 8);
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, byteOrder);
                writeExifSegment(byteOrderedDataOutputStream2);
                byte[] byteArray = ((ByteArrayOutputStream) byteOrderedDataOutputStream2.mOutputStream).toByteArray();
                byteOrderedDataOutputStream.write(byteArray);
                CRC32 crc32 = new CRC32();
                crc32.update(byteArray, 4, byteArray.length - 4);
                byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = byteArrayOutputStream2;
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        int i;
        int i2;
        int i3;
        int i4;
        ByteArrayOutputStream byteArrayOutputStream2;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream;
        byte[] bArr;
        boolean z;
        if (DEBUG) {
            Log.d("ExifInterface", "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream, byteOrder);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        byte[] bArr2 = WEBP_SIGNATURE_1;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, bArr2.length);
        byte[] bArr3 = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr3.length + 4);
        ByteArrayOutputStream byteArrayOutputStream3 = null;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                try {
                    ByteOrderedDataOutputStream byteOrderedDataOutputStream3 = new ByteOrderedDataOutputStream(byteArrayOutputStream4, byteOrder);
                    int i5 = this.mOffsetToExifData;
                    try {
                        try {
                            if (i5 != 0) {
                                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, (i5 - ((bArr2.length + 4) + bArr3.length)) - 8);
                                byteOrderedDataInputStream.skipFully(4);
                                int i6 = byteOrderedDataInputStream.readInt();
                                if (i6 % 2 != 0) {
                                    i6++;
                                }
                                byteOrderedDataInputStream.skipFully(i6);
                                writeExifSegment(byteOrderedDataOutputStream3);
                            } else {
                                byte[] bArr4 = new byte[4];
                                byteOrderedDataInputStream.readFully(bArr4);
                                byte[] bArr5 = WEBP_CHUNK_TYPE_VP8X;
                                boolean zEquals = Arrays.equals(bArr4, bArr5);
                                byte[] bArr6 = WEBP_CHUNK_TYPE_VP8;
                                byte[] bArr7 = WEBP_CHUNK_TYPE_VP8L;
                                if (!zEquals) {
                                    if (Arrays.equals(bArr4, bArr6) || Arrays.equals(bArr4, bArr7)) {
                                        int i7 = byteOrderedDataInputStream.readInt();
                                        int i8 = i7 % 2 == 1 ? i7 + 1 : i7;
                                        byte[] bArr8 = new byte[3];
                                        boolean zEquals2 = Arrays.equals(bArr4, bArr6);
                                        boolean z2 = true;
                                        byte[] bArr9 = WEBP_VP8_SIGNATURE;
                                        if (zEquals2) {
                                            byteOrderedDataInputStream.readFully(bArr8);
                                            byte[] bArr10 = new byte[3];
                                            byteOrderedDataInputStream.readFully(bArr10);
                                            if (!Arrays.equals(bArr9, bArr10)) {
                                                throw new IOException("Error checking VP8 signature");
                                            }
                                            i = byteOrderedDataInputStream.readInt();
                                            i8 -= 10;
                                            i2 = (i << 18) >> 18;
                                            i3 = (i << 2) >> 18;
                                            z2 = false;
                                        } else if (!Arrays.equals(bArr4, bArr7)) {
                                            i = 0;
                                            i2 = 0;
                                            z2 = false;
                                            i3 = 0;
                                        } else {
                                            if (byteOrderedDataInputStream.readByte() != 47) {
                                                throw new IOException("Error checking VP8L signature");
                                            }
                                            i = byteOrderedDataInputStream.readInt();
                                            i2 = (i & 16383) + 1;
                                            i3 = ((i & 268419072) >>> 14) + 1;
                                            if ((i & 268435456) == 0) {
                                                z2 = false;
                                            }
                                            i8 -= 5;
                                        }
                                        byteOrderedDataOutputStream3.write(bArr5);
                                        byteOrderedDataOutputStream3.writeInt(10);
                                        byte[] bArr11 = new byte[10];
                                        if (z2) {
                                            i4 = i2;
                                            bArr11[0] = (byte) (bArr11[0] | 16);
                                        } else {
                                            i4 = i2;
                                        }
                                        bArr11[0] = (byte) (bArr11[0] | 8);
                                        int i9 = i4 - 1;
                                        byteArrayOutputStream2 = byteArrayOutputStream4;
                                        int i10 = i3 - 1;
                                        byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                                        try {
                                            bArr11[4] = (byte) i9;
                                            bArr11[5] = (byte) (i9 >> 8);
                                            bArr11[6] = (byte) (i9 >> 16);
                                            bArr11[7] = (byte) i10;
                                            bArr11[8] = (byte) (i10 >> 8);
                                            bArr11[9] = (byte) (i10 >> 16);
                                            byteOrderedDataOutputStream3.write(bArr11);
                                            byteOrderedDataOutputStream3.write(bArr4);
                                            byteOrderedDataOutputStream3.writeInt(i7);
                                        } catch (Exception e) {
                                            e = e;
                                            byteArrayOutputStream = byteArrayOutputStream2;
                                            byteArrayOutputStream3 = byteArrayOutputStream;
                                            throw new IOException("Failed to save WebP file", e);
                                        } catch (Throwable th) {
                                            th = th;
                                            byteArrayOutputStream = byteArrayOutputStream2;
                                            byteArrayOutputStream3 = byteArrayOutputStream;
                                            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream3);
                                            throw th;
                                        }
                                        try {
                                            if (Arrays.equals(bArr4, bArr6)) {
                                                byteOrderedDataOutputStream3.write(bArr8);
                                                byteOrderedDataOutputStream3.write(bArr9);
                                                byteOrderedDataOutputStream3.writeInt(i);
                                            } else {
                                                if (Arrays.equals(bArr4, bArr7)) {
                                                    byteOrderedDataOutputStream3.write(47);
                                                    byteOrderedDataOutputStream3.writeInt(i);
                                                }
                                                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i8);
                                                writeExifSegment(byteOrderedDataOutputStream3);
                                            }
                                            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i8);
                                            writeExifSegment(byteOrderedDataOutputStream3);
                                        } catch (Exception e2) {
                                            e = e2;
                                            byteArrayOutputStream3 = byteArrayOutputStream2;
                                            throw new IOException("Failed to save WebP file", e);
                                        } catch (Throwable th2) {
                                            th = th2;
                                            byteArrayOutputStream3 = byteArrayOutputStream2;
                                            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream3);
                                            throw th;
                                        }
                                    }
                                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                                    ByteOrderedDataOutputStream byteOrderedDataOutputStream4 = byteOrderedDataOutputStream;
                                    byteOrderedDataOutputStream4.writeInt(byteArrayOutputStream2.size() + bArr3.length);
                                    byteOrderedDataOutputStream4.write(bArr3);
                                    byteArrayOutputStream = byteArrayOutputStream2;
                                    byteArrayOutputStream.writeTo(byteOrderedDataOutputStream4);
                                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                                    return;
                                }
                                int i11 = byteOrderedDataInputStream.readInt();
                                byte[] bArr12 = new byte[i11 % 2 == 1 ? i11 + 1 : i11];
                                byteOrderedDataInputStream.readFully(bArr12);
                                byte b = (byte) (8 | bArr12[0]);
                                bArr12[0] = b;
                                boolean z3 = ((b >> 1) & 1) == 1;
                                byteOrderedDataOutputStream3.write(bArr5);
                                byteOrderedDataOutputStream3.writeInt(i11);
                                byteOrderedDataOutputStream3.write(bArr12);
                                if (z3) {
                                    byte[] bArr13 = WEBP_CHUNK_TYPE_ANIM;
                                    do {
                                        bArr = new byte[4];
                                        byteOrderedDataInputStream.readFully(bArr);
                                        int i12 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr);
                                        byteOrderedDataOutputStream3.writeInt(i12);
                                        if (i12 % 2 == 1) {
                                            i12++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i12);
                                    } while (!Arrays.equals(bArr, bArr13));
                                    while (true) {
                                        byte[] bArr14 = new byte[4];
                                        try {
                                            byteOrderedDataInputStream.readFully(bArr14);
                                            z = !Arrays.equals(bArr14, WEBP_CHUNK_TYPE_ANMF);
                                        } catch (EOFException unused) {
                                            z = true;
                                        }
                                        if (z) {
                                            break;
                                        }
                                        int i13 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr14);
                                        byteOrderedDataOutputStream3.writeInt(i13);
                                        if (i13 % 2 == 1) {
                                            i13++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i13);
                                    }
                                    writeExifSegment(byteOrderedDataOutputStream3);
                                } else {
                                    while (true) {
                                        byte[] bArr15 = new byte[4];
                                        byteOrderedDataInputStream.readFully(bArr15);
                                        int i14 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr15);
                                        byteOrderedDataOutputStream3.writeInt(i14);
                                        if (i14 % 2 == 1) {
                                            i14++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i14);
                                        if (Arrays.equals(bArr15, bArr6) || (bArr7 != null && Arrays.equals(bArr15, bArr7))) {
                                            break;
                                        }
                                    }
                                    writeExifSegment(byteOrderedDataOutputStream3);
                                }
                            }
                            byteArrayOutputStream.writeTo(byteOrderedDataOutputStream4);
                            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                            return;
                        } catch (Exception e3) {
                            e = e3;
                            byteArrayOutputStream3 = byteArrayOutputStream;
                            throw new IOException("Failed to save WebP file", e);
                        } catch (Throwable th3) {
                            th = th3;
                            byteArrayOutputStream3 = byteArrayOutputStream;
                            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream3);
                            throw th;
                        }
                        byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                        byteArrayOutputStream2 = byteArrayOutputStream4;
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                        ByteOrderedDataOutputStream byteOrderedDataOutputStream42 = byteOrderedDataOutputStream;
                        byteOrderedDataOutputStream42.writeInt(byteArrayOutputStream2.size() + bArr3.length);
                        byteOrderedDataOutputStream42.write(bArr3);
                        byteArrayOutputStream = byteArrayOutputStream2;
                    } catch (Exception e4) {
                        e = e4;
                        byteArrayOutputStream3 = byteArrayOutputStream4;
                    } catch (Throwable th4) {
                        th = th4;
                        byteArrayOutputStream3 = byteArrayOutputStream4;
                    }
                } catch (Exception e5) {
                    e = e5;
                    byteArrayOutputStream = byteArrayOutputStream4;
                } catch (Throwable th5) {
                    th = th5;
                    byteArrayOutputStream = byteArrayOutputStream4;
                }
            } catch (Exception e6) {
                e = e6;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x042f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setAttribute(String str, String str2) throws NumberFormatException {
        String str3;
        boolean z;
        int i;
        ExifTag exifTag;
        String str4;
        int i2;
        int i3;
        ExifAttribute exifAttribute;
        Rational rational;
        long j;
        long j2;
        double d;
        String strReplaceAll = str2;
        boolean zEquals = "ISOSpeedRatings".equals(str);
        boolean z2 = DEBUG;
        String str5 = "ExifInterface";
        if (zEquals) {
            if (z2) {
                Log.d("ExifInterface", "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str3 = "PhotographicSensitivity";
        } else {
            str3 = str;
        }
        if (strReplaceAll == null) {
            z = z2;
            i = 0;
        } else if (!RATIONAL_TAGS_HANDLED_AS_DECIMALS_FOR_COMPATIBILITY.contains(str3) || strReplaceAll.contains("/")) {
            z = z2;
            i = 0;
            if (str3.equals("GPSTimeStamp")) {
                Matcher matcher = GPS_TIMESTAMP_PATTERN.matcher(strReplaceAll);
                if (!matcher.find()) {
                    Log.w("ExifInterface", "Invalid value for " + str3 + " : " + strReplaceAll);
                    return;
                }
                strReplaceAll = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            } else if ("DateTime".equals(str3) || "DateTimeOriginal".equals(str3) || "DateTimeDigitized".equals(str3)) {
                boolean zFind = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
                boolean zFind2 = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
                if (strReplaceAll.length() != 19 || (!zFind && !zFind2)) {
                    Log.w("ExifInterface", "Invalid value for " + str3 + " : " + strReplaceAll);
                    return;
                }
                if (zFind2) {
                    strReplaceAll = strReplaceAll.replaceAll("-", ":");
                }
            }
        } else {
            try {
                double d2 = Double.parseDouble(strReplaceAll);
                long j3 = 1;
                if (d2 >= 9.223372036854776E18d || d2 <= -9.223372036854776E18d) {
                    z = z2;
                    i = 0;
                    rational = new Rational(d2 > 0.0d ? Long.MAX_VALUE : Long.MIN_VALUE, 1L);
                } else {
                    double dAbs = Math.abs(d2);
                    long j4 = 0;
                    long j5 = 1;
                    double d3 = dAbs;
                    long j6 = 0;
                    while (true) {
                        double d4 = d3 % 1.0d;
                        z = z2;
                        long j7 = (long) (d3 - d4);
                        i = 0;
                        j = (j7 * j3) + j6;
                        j2 = (j7 * j4) + j5;
                        d3 = 1.0d / d4;
                        d = d2;
                        if (Math.abs(dAbs - (j / j2)) <= 1.0E-8d * dAbs) {
                            break;
                        }
                        j6 = j3;
                        d2 = d;
                        j3 = j;
                        j5 = j4;
                        j4 = j2;
                        z2 = z;
                    }
                    if (d < 0.0d) {
                        j = -j;
                    }
                    rational = new Rational(j, j2);
                }
                strReplaceAll = rational.toString();
            } catch (NumberFormatException unused) {
                Log.w("ExifInterface", "Invalid value for " + str3 + " : " + strReplaceAll);
                return;
            }
        }
        int i4 = i;
        while (i4 < EXIF_TAGS.length) {
            if ((i4 != 4 || this.mHasThumbnail) && !((str3.equals("Xmp") && i4 == 5 && this.mXmpIsFromSeparateMarker) || (exifTag = (ExifTag) sExifTagMapsForWriting[i4].get(str3)) == null)) {
                if (strReplaceAll == null) {
                    this.mAttributes[i4].remove(str3);
                } else {
                    Pair pairGuessDataFormat = guessDataFormat(strReplaceAll);
                    int iIntValue = ((Integer) pairGuessDataFormat.first).intValue();
                    int i5 = -1;
                    int i6 = exifTag.primaryFormat;
                    if (i6 == iIntValue || i6 == ((Integer) pairGuessDataFormat.second).intValue()) {
                        int[] iArr = IFD_FORMAT_BYTES_PER_FORMAT;
                        switch (i6) {
                            case 1:
                                str4 = str5;
                                HashMap map = this.mAttributes[i4];
                                i2 = 1;
                                if (strReplaceAll.length() == 1) {
                                    i3 = i;
                                    if (strReplaceAll.charAt(i3) >= '0' && strReplaceAll.charAt(i3) <= '1') {
                                        byte[] bArr = new byte[1];
                                        bArr[i3] = (byte) (strReplaceAll.charAt(i3) - '0');
                                        exifAttribute = new ExifAttribute(1, 1, bArr);
                                    }
                                    map.put(str3, exifAttribute);
                                    break;
                                } else {
                                    i3 = i;
                                }
                                byte[] bytes = strReplaceAll.getBytes(ASCII);
                                exifAttribute = new ExifAttribute(1, bytes.length, bytes);
                                map.put(str3, exifAttribute);
                                break;
                            case 2:
                            case 7:
                                str4 = str5;
                                this.mAttributes[i4].put(str3, ExifAttribute.createString(strReplaceAll));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 3:
                                str4 = str5;
                                String[] strArrSplit = strReplaceAll.split(",", -1);
                                int[] iArr2 = new int[strArrSplit.length];
                                for (int i7 = i; i7 < strArrSplit.length; i7++) {
                                    iArr2[i7] = Integer.parseInt(strArrSplit[i7]);
                                }
                                this.mAttributes[i4].put(str3, ExifAttribute.createUShort(iArr2, this.mExifByteOrder));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 4:
                                str4 = str5;
                                String[] strArrSplit2 = strReplaceAll.split(",", -1);
                                long[] jArr = new long[strArrSplit2.length];
                                for (int i8 = i; i8 < strArrSplit2.length; i8++) {
                                    jArr[i8] = Long.parseLong(strArrSplit2[i8]);
                                }
                                this.mAttributes[i4].put(str3, ExifAttribute.createULong(jArr, this.mExifByteOrder));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 5:
                                str4 = str5;
                                int i9 = -1;
                                String[] strArrSplit3 = strReplaceAll.split(",", -1);
                                Rational[] rationalArr = new Rational[strArrSplit3.length];
                                int i10 = i;
                                while (i10 < strArrSplit3.length) {
                                    String[] strArrSplit4 = strArrSplit3[i10].split("/", i9);
                                    rationalArr[i10] = new Rational((long) Double.parseDouble(strArrSplit4[i]), (long) Double.parseDouble(strArrSplit4[1]));
                                    i10++;
                                    i9 = -1;
                                }
                                this.mAttributes[i4].put(str3, ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 6:
                            case 8:
                            case 11:
                            default:
                                if (z) {
                                    ListPopupWindow$$ExternalSyntheticOutline0.m(i6, "Data format isn't one of expected formats: ", str5);
                                    break;
                                }
                                break;
                            case 9:
                                str4 = str5;
                                String[] strArrSplit5 = strReplaceAll.split(",", -1);
                                int length = strArrSplit5.length;
                                int[] iArr3 = new int[length];
                                for (int i11 = i; i11 < strArrSplit5.length; i11++) {
                                    iArr3[i11] = Integer.parseInt(strArrSplit5[i11]);
                                }
                                HashMap map2 = this.mAttributes[i4];
                                ByteOrder byteOrder = this.mExifByteOrder;
                                ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[iArr[9] * length]);
                                byteBufferWrap.order(byteOrder);
                                for (int i12 = i; i12 < length; i12++) {
                                    byteBufferWrap.putInt(iArr3[i12]);
                                }
                                map2.put(str3, new ExifAttribute(9, length, byteBufferWrap.array()));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 10:
                                String[] strArrSplit6 = strReplaceAll.split(",", -1);
                                int length2 = strArrSplit6.length;
                                Rational[] rationalArr2 = new Rational[length2];
                                int i13 = i;
                                while (i13 < strArrSplit6.length) {
                                    String[] strArrSplit7 = strArrSplit6[i13].split("/", i5);
                                    rationalArr2[i13] = new Rational((long) Double.parseDouble(strArrSplit7[i]), (long) Double.parseDouble(strArrSplit7[1]));
                                    i13++;
                                    length2 = length2;
                                    str5 = str5;
                                    i5 = -1;
                                }
                                str4 = str5;
                                int i14 = length2;
                                HashMap map3 = this.mAttributes[i4];
                                ByteOrder byteOrder2 = this.mExifByteOrder;
                                ByteBuffer byteBufferWrap2 = ByteBuffer.wrap(new byte[iArr[10] * i14]);
                                byteBufferWrap2.order(byteOrder2);
                                for (int i15 = i; i15 < i14; i15++) {
                                    Rational rational2 = rationalArr2[i15];
                                    byteBufferWrap2.putInt((int) rational2.numerator);
                                    byteBufferWrap2.putInt((int) rational2.denominator);
                                }
                                map3.put(str3, new ExifAttribute(10, i14, byteBufferWrap2.array()));
                                i3 = i;
                                i2 = 1;
                                break;
                            case 12:
                                String[] strArrSplit8 = strReplaceAll.split(",", -1);
                                int length3 = strArrSplit8.length;
                                double[] dArr = new double[length3];
                                for (int i16 = i; i16 < strArrSplit8.length; i16++) {
                                    dArr[i16] = Double.parseDouble(strArrSplit8[i16]);
                                }
                                HashMap map4 = this.mAttributes[i4];
                                ByteOrder byteOrder3 = this.mExifByteOrder;
                                ByteBuffer byteBufferWrap3 = ByteBuffer.wrap(new byte[iArr[12] * length3]);
                                byteBufferWrap3.order(byteOrder3);
                                for (int i17 = i; i17 < length3; i17++) {
                                    byteBufferWrap3.putDouble(dArr[i17]);
                                }
                                map4.put(str3, new ExifAttribute(12, length3, byteBufferWrap3.array()));
                                break;
                        }
                    } else {
                        int i18 = exifTag.secondaryFormat;
                        if (i18 == -1 || !(i18 == ((Integer) pairGuessDataFormat.first).intValue() || i18 == ((Integer) pairGuessDataFormat.second).intValue())) {
                            if (i6 != 1 && i6 != 7) {
                                if (i6 != 2) {
                                    if (z) {
                                        StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Given tag (", str3, ") value didn't match with one of expected formats: ");
                                        String[] strArr = IFD_FORMAT_NAMES;
                                        sbM.append(strArr[i6]);
                                        sbM.append(i18 == -1 ? "" : ", " + strArr[i18]);
                                        sbM.append(" (guess: ");
                                        sbM.append(strArr[((Integer) pairGuessDataFormat.first).intValue()]);
                                        ExifInterface$$ExternalSyntheticOutline0.m(sbM, ((Integer) pairGuessDataFormat.second).intValue() != -1 ? ", " + strArr[((Integer) pairGuessDataFormat.second).intValue()] : "", ")", str5);
                                    }
                                }
                            }
                            int[] iArr4 = IFD_FORMAT_BYTES_PER_FORMAT;
                            switch (i6) {
                            }
                        } else {
                            i6 = i18;
                        }
                        int[] iArr42 = IFD_FORMAT_BYTES_PER_FORMAT;
                        switch (i6) {
                        }
                    }
                }
                str4 = str5;
                i3 = i;
                i2 = 1;
            } else {
                str4 = str5;
                i3 = i;
                i2 = 1;
            }
            i4 += i2;
            i = i3;
            str5 = str4;
        }
    }

    public final void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        String str;
        ExifAttribute exifAttribute;
        int intValue;
        HashMap map = this.mAttributes[4];
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get("Compression");
        if (exifAttribute2 == null) {
            this.mThumbnailCompression = 6;
            handleThumbnailFromJfif(byteOrderedDataInputStream, map);
            return;
        }
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        this.mThumbnailCompression = intValue2;
        int i = 1;
        if (intValue2 != 1) {
            if (intValue2 == 6) {
                handleThumbnailFromJfif(byteOrderedDataInputStream, map);
                return;
            } else if (intValue2 != 7) {
                return;
            }
        }
        ExifAttribute exifAttribute3 = (ExifAttribute) map.get("BitsPerSample");
        String str2 = "ExifInterface";
        if (exifAttribute3 != null) {
            int[] iArr = (int[]) exifAttribute3.getValue(this.mExifByteOrder);
            int[] iArr2 = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr2, iArr) || (this.mMimeType == 3 && (exifAttribute = (ExifAttribute) map.get("PhotometricInterpretation")) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, iArr2))))) {
                ExifAttribute exifAttribute4 = (ExifAttribute) map.get("StripOffsets");
                ExifAttribute exifAttribute5 = (ExifAttribute) map.get("StripByteCounts");
                if (exifAttribute4 == null || exifAttribute5 == null) {
                    return;
                }
                long[] jArrConvertToLongArray = ExifInterfaceUtils.convertToLongArray(exifAttribute4.getValue(this.mExifByteOrder));
                long[] jArrConvertToLongArray2 = ExifInterfaceUtils.convertToLongArray(exifAttribute5.getValue(this.mExifByteOrder));
                if (jArrConvertToLongArray == null || jArrConvertToLongArray.length == 0) {
                    Log.w("ExifInterface", "stripOffsets should not be null or have zero length.");
                    return;
                }
                if (jArrConvertToLongArray2 == null || jArrConvertToLongArray2.length == 0) {
                    Log.w("ExifInterface", "stripByteCounts should not be null or have zero length.");
                    return;
                }
                if (jArrConvertToLongArray.length != jArrConvertToLongArray2.length) {
                    Log.w("ExifInterface", "stripOffsets and stripByteCounts should have same length.");
                    return;
                }
                long j = 0;
                for (long j2 : jArrConvertToLongArray2) {
                    j += j2;
                }
                int i2 = (int) j;
                byte[] bArr = new byte[i2];
                this.mAreThumbnailStripsConsecutive = true;
                this.mHasThumbnailStrips = true;
                this.mHasThumbnail = true;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i3 < jArrConvertToLongArray.length) {
                    int i6 = (int) jArrConvertToLongArray[i3];
                    int i7 = (int) jArrConvertToLongArray2[i3];
                    if (i3 < jArrConvertToLongArray.length - i) {
                        str = str2;
                        if (i6 + i7 != jArrConvertToLongArray[i3 + 1]) {
                            this.mAreThumbnailStripsConsecutive = false;
                        }
                    } else {
                        str = str2;
                    }
                    int i8 = i6 - i4;
                    if (i8 < 0) {
                        Log.d(str, "Invalid strip offset value");
                        return;
                    }
                    String str3 = str;
                    try {
                        byteOrderedDataInputStream.skipFully(i8);
                        int i9 = i4 + i8;
                        byte[] bArr2 = new byte[i7];
                        try {
                            byteOrderedDataInputStream.readFully(bArr2);
                            i4 = i9 + i7;
                            System.arraycopy(bArr2, 0, bArr, i5, i7);
                            i5 += i7;
                            i3++;
                            str2 = str3;
                            i = 1;
                        } catch (EOFException unused) {
                            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i7, "Failed to read ", " bytes.", str3);
                            return;
                        }
                    } catch (EOFException unused2) {
                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i8, "Failed to skip ", " bytes.", str3);
                        return;
                    }
                }
                this.mThumbnailBytes = bArr;
                if (this.mAreThumbnailStripsConsecutive) {
                    this.mThumbnailOffset = (int) jArrConvertToLongArray[0];
                    this.mThumbnailLength = i2;
                    return;
                }
                return;
            }
        }
        if (DEBUG) {
            Log.d("ExifInterface", "Unsupported data type value");
        }
    }

    public final void swapBasedOnImageSize(int i, int i2) throws Throwable {
        boolean zIsEmpty = this.mAttributes[i].isEmpty();
        boolean z = DEBUG;
        if (zIsEmpty || this.mAttributes[i2].isEmpty()) {
            if (z) {
                Log.d("ExifInterface", "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get("ImageLength");
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get("ImageWidth");
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i2].get("ImageLength");
        ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i2].get("ImageWidth");
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
        HashMap[] mapArr = this.mAttributes;
        HashMap map = mapArr[i];
        mapArr[i] = mapArr[i2];
        mapArr[i2] = map;
    }

    public final void updateImageSizeValues(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) throws Throwable {
        ExifAttribute exifAttributeCreateUShort;
        ExifAttribute exifAttributeCreateUShort2;
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get("DefaultCropSize");
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get("SensorTopBorder");
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i].get("SensorLeftBorder");
        ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i].get("SensorBottomBorder");
        ExifAttribute exifAttribute5 = (ExifAttribute) this.mAttributes[i].get("SensorRightBorder");
        if (exifAttribute != null) {
            if (exifAttribute.format == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(rationalArr));
                    return;
                } else {
                    exifAttributeCreateUShort = ExifAttribute.createURational(new Rational[]{rationalArr[0]}, this.mExifByteOrder);
                    exifAttributeCreateUShort2 = ExifAttribute.createURational(new Rational[]{rationalArr[1]}, this.mExifByteOrder);
                }
            } else {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(iArr));
                    return;
                }
                exifAttributeCreateUShort = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                exifAttributeCreateUShort2 = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            this.mAttributes[i].put("ImageWidth", exifAttributeCreateUShort);
            this.mAttributes[i].put("ImageLength", exifAttributeCreateUShort2);
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
            ExifAttribute exifAttributeCreateUShort3 = ExifAttribute.createUShort(intValue2 - intValue, this.mExifByteOrder);
            ExifAttribute exifAttributeCreateUShort4 = ExifAttribute.createUShort(intValue3 - intValue4, this.mExifByteOrder);
            this.mAttributes[i].put("ImageLength", exifAttributeCreateUShort3);
            this.mAttributes[i].put("ImageWidth", exifAttributeCreateUShort4);
            return;
        }
        ExifAttribute exifAttribute6 = (ExifAttribute) this.mAttributes[i].get("ImageLength");
        ExifAttribute exifAttribute7 = (ExifAttribute) this.mAttributes[i].get("ImageWidth");
        if (exifAttribute6 == null || exifAttribute7 == null) {
            ExifAttribute exifAttribute8 = (ExifAttribute) this.mAttributes[i].get("JPEGInterchangeFormat");
            ExifAttribute exifAttribute9 = (ExifAttribute) this.mAttributes[i].get("JPEGInterchangeFormatLength");
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

    public final void validateImages() throws Throwable {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get("PixelXDimension");
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[1].get("PixelYDimension");
        if (exifAttribute != null && exifAttribute2 != null) {
            this.mAttributes[0].put("ImageWidth", exifAttribute);
            this.mAttributes[0].put("ImageLength", exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap[] mapArr = this.mAttributes;
            mapArr[4] = mapArr[5];
            mapArr[5] = new HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
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

    public final void writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream) throws IOException {
        int i;
        char c;
        char c2;
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
        for (int i2 = 0; i2 < exifTagArr.length; i2++) {
            Iterator it = this.mAttributes[i2].entrySet().iterator();
            while (it.hasNext()) {
                if (((Map.Entry) it.next()).getValue() == null) {
                    it.remove();
                }
            }
        }
        if (this.mAttributes[1].isEmpty()) {
            i = 1;
        } else {
            i = 1;
            this.mAttributes[0].put(exifTagArr2[1].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mAttributes[2].isEmpty()) {
            c = 2;
        } else {
            c = 2;
            this.mAttributes[0].put(exifTagArr2[2].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mAttributes[3].isEmpty()) {
            c2 = 3;
        } else {
            c2 = 3;
            this.mAttributes[i].put(exifTagArr2[3].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put("StripOffsets", ExifAttribute.createUShort(0, this.mExifByteOrder));
                this.mAttributes[4].put("StripByteCounts", ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(0L, this.mExifByteOrder));
                this.mAttributes[4].put("JPEGInterchangeFormatLength", ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        int i3 = 0;
        while (true) {
            int length = exifTagArr.length;
            iArr = IFD_FORMAT_BYTES_PER_FORMAT;
            if (i3 >= length) {
                break;
            }
            Iterator it2 = this.mAttributes[i3].entrySet().iterator();
            int i4 = 0;
            while (it2.hasNext()) {
                ExifAttribute exifAttribute = (ExifAttribute) ((Map.Entry) it2.next()).getValue();
                exifAttribute.getClass();
                int i5 = iArr[exifAttribute.format] * exifAttribute.numberOfComponents;
                if (i5 > 4) {
                    i4 += i5;
                }
            }
            iArr3[i3] = iArr3[i3] + i4;
            i3++;
        }
        int size = 8;
        for (int i6 = 0; i6 < exifTagArr.length; i6++) {
            if (!this.mAttributes[i6].isEmpty()) {
                iArr2[i6] = size;
                size = (this.mAttributes[i6].size() * 12) + 6 + iArr3[i6] + size;
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put("StripOffsets", ExifAttribute.createUShort(size, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(size, this.mExifByteOrder));
            }
            this.mThumbnailOffset = size;
            size += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            size += 8;
        }
        if (DEBUG) {
            for (int i7 = 0; i7 < exifTagArr.length; i7++) {
                Log.d("ExifInterface", String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i7), Integer.valueOf(iArr2[i7]), Integer.valueOf(this.mAttributes[i7].size()), Integer.valueOf(iArr3[i7]), Integer.valueOf(size)));
            }
        }
        if (!this.mAttributes[i].isEmpty()) {
            this.mAttributes[0].put(exifTagArr2[i].name, ExifAttribute.createULong(iArr2[i], this.mExifByteOrder));
        }
        if (!this.mAttributes[c].isEmpty()) {
            this.mAttributes[0].put(exifTagArr2[c].name, ExifAttribute.createULong(iArr2[c], this.mExifByteOrder));
        }
        if (!this.mAttributes[c2].isEmpty()) {
            this.mAttributes[i].put(exifTagArr2[c2].name, ExifAttribute.createULong(iArr2[c2], this.mExifByteOrder));
        }
        int i8 = this.mMimeType;
        if (i8 == 4) {
            if (size > 65535) {
                throw new IllegalStateException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(size, "Size of exif data (", " bytes) exceeds the max size of a JPEG APP1 segment (65536 bytes)"));
            }
            byteOrderedDataOutputStream.writeUnsignedShort(size);
            byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        } else if (i8 == 13) {
            byteOrderedDataOutputStream.writeInt(size);
            byteOrderedDataOutputStream.writeInt(PNG_CHUNK_TYPE_EXIF);
        } else if (i8 == 14) {
            byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
            byteOrderedDataOutputStream.writeInt(size);
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
        byteOrderedDataOutputStream.mByteOrder = this.mExifByteOrder;
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (int i9 = 0; i9 < exifTagArr.length; i9++) {
            if (!this.mAttributes[i9].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[i9].size());
                int size2 = (this.mAttributes[i9].size() * 12) + iArr2[i9] + 2 + 4;
                for (Map.Entry entry : this.mAttributes[i9].entrySet()) {
                    int i10 = ((ExifTag) sExifTagMapsForWriting[i9].get(entry.getKey())).number;
                    ExifAttribute exifAttribute2 = (ExifAttribute) entry.getValue();
                    exifAttribute2.getClass();
                    int i11 = exifAttribute2.format;
                    int i12 = iArr[i11];
                    int i13 = exifAttribute2.numberOfComponents;
                    int i14 = i12 * i13;
                    byteOrderedDataOutputStream.writeUnsignedShort(i10);
                    byteOrderedDataOutputStream.writeUnsignedShort(i11);
                    byteOrderedDataOutputStream.writeInt(i13);
                    if (i14 > 4) {
                        byteOrderedDataOutputStream.writeUnsignedInt(size2);
                        size2 += i14;
                    } else {
                        byteOrderedDataOutputStream.write(exifAttribute2.bytes);
                        if (i14 < 4) {
                            while (i14 < 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i14++;
                            }
                        }
                    }
                }
                if (i9 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0L);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt(iArr2[4]);
                }
                Iterator it3 = this.mAttributes[i9].entrySet().iterator();
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
        if (this.mMimeType == 14 && size % 2 == i) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
    }

    public class ByteOrderedDataInputStream extends InputStream implements DataInput {
        public ByteOrder mByteOrder;
        public final DataInputStream mDataInputStream;
        public final int mLength;
        public int mPosition;
        public byte[] mSkipBuffer;

        public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
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
        public final byte readByte() throws EOFException {
            this.mPosition++;
            int i = this.mDataInputStream.read();
            if (i >= 0) {
                return (byte) i;
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
        public final void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mPosition += i2;
            this.mDataInputStream.readFully(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public final int readInt() throws IOException {
            this.mPosition += 4;
            int i = this.mDataInputStream.read();
            int i2 = this.mDataInputStream.read();
            int i3 = this.mDataInputStream.read();
            int i4 = this.mDataInputStream.read();
            if ((i | i2 | i3 | i4) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i4 << 24) + (i3 << 16) + (i2 << 8) + i;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i << 24) + (i2 << 16) + (i3 << 8) + i4;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public final String readLine() {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public final long readLong() throws IOException {
            this.mPosition += 8;
            int i = this.mDataInputStream.read();
            int i2 = this.mDataInputStream.read();
            int i3 = this.mDataInputStream.read();
            int i4 = this.mDataInputStream.read();
            int i5 = this.mDataInputStream.read();
            int i6 = this.mDataInputStream.read();
            int i7 = this.mDataInputStream.read();
            int i8 = this.mDataInputStream.read();
            if ((i | i2 | i3 | i4 | i5 | i6 | i7 | i8) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i8 << 56) + (i7 << 48) + (i6 << 40) + (i5 << 32) + (i4 << 24) + (i3 << 16) + (i2 << 8) + i;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i << 56) + (i2 << 48) + (i3 << 40) + (i4 << 32) + (i5 << 24) + (i6 << 16) + (i7 << 8) + i8;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public final short readShort() throws IOException {
            int i;
            this.mPosition += 2;
            int i2 = this.mDataInputStream.read();
            int i3 = this.mDataInputStream.read();
            if ((i2 | i3) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                i = (i3 << 8) + i2;
            } else {
                if (byteOrder != ByteOrder.BIG_ENDIAN) {
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                i = (i2 << 8) + i3;
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
        public final int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            int i = this.mDataInputStream.read();
            int i2 = this.mDataInputStream.read();
            if ((i | i2) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i2 << 8) + i;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i << 8) + i2;
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

        public final void skipFully(int i) throws IOException {
            int i2 = 0;
            while (i2 < i) {
                int i3 = i - i2;
                int iSkip = (int) this.mDataInputStream.skip(i3);
                if (iSkip <= 0) {
                    if (this.mSkipBuffer == null) {
                        this.mSkipBuffer = new byte[8192];
                    }
                    iSkip = this.mDataInputStream.read(this.mSkipBuffer, 0, Math.min(8192, i3));
                    if (iSkip == -1) {
                        throw new EOFException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Reached EOF while skipping ", " bytes."));
                    }
                }
                i2 += iSkip;
            }
            this.mPosition += i2;
        }

        public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            this(inputStream, ByteOrder.BIG_ENDIAN);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.mDataInputStream.read(bArr, i, i2);
            this.mPosition += i3;
            return i3;
        }

        @Override // java.io.DataInput
        public final void readFully(byte[] bArr) throws IOException {
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

    public class SeekableByteOrderedDataInputStream extends ByteOrderedDataInputStream {
        public SeekableByteOrderedDataInputStream(byte[] bArr) throws IOException {
            super(bArr);
            this.mDataInputStream.mark(Integer.MAX_VALUE);
        }

        public final void seek(long j) throws IOException {
            int i = this.mPosition;
            if (i > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
            } else {
                j -= i;
            }
            skipFully((int) j);
        }

        public SeekableByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
            if (inputStream.markSupported()) {
                this.mDataInputStream.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }
    }

    public class ExifTag {
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

    public ExifInterface(String str) throws Throwable {
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

    public ExifInterface(FileDescriptor fileDescriptor) throws Throwable {
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

    public ExifInterface(InputStream inputStream) throws IOException {
        this(inputStream, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ExifInterface(InputStream inputStream, int i) throws IOException {
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
            } else if (inputStream instanceof FileInputStream) {
                FileInputStream fileInputStream = (FileInputStream) inputStream;
                if (isSeekableFD(fileInputStream.getFD())) {
                    this.mAssetInputStream = null;
                    this.mSeekableFileDescriptor = fileInputStream.getFD();
                } else {
                    this.mAssetInputStream = null;
                    this.mSeekableFileDescriptor = null;
                }
            }
            loadAttributes(inputStream);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }
}
