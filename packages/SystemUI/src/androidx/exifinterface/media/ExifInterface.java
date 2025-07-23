package androidx.exifinterface.media;

import android.content.res.AssetManager;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
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
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ByteOrderedDataOutputStream extends FilterOutputStream {
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

        public final void writeShort(short s) {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(s & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write(s & 255);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Not initialized variable reg: 6, insn: 0x0033: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]) (LINE:52), block:B:162:0x0033 */
        /* JADX WARN: Removed duplicated region for block: B:165:0x016d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object getValue(java.nio.ByteOrder r14) {
            /*
                Method dump skipped, instructions count: 402
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public ExifInterface(File file) throws IOException {
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
                    long parseLong = Long.parseLong(str);
                    return (parseLong < 0 || parseLong > 65535) ? parseLong < 0 ? new Pair(9, -1) : new Pair(4, -1) : new Pair(3, 4);
                } catch (NumberFormatException unused) {
                    Double.parseDouble(str);
                    return new Pair(12, -1);
                }
            } catch (NumberFormatException unused2) {
                return new Pair(2, -1);
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

    public static int intFromBytes(int i, int i2, int i3, int i4) {
        return ((i & 255) << 24) | ((i2 & 255) << 16) | ((i3 & 255) << 8) | (i4 & 255);
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
                Integer valueOf = Integer.valueOf((int) (rational.numerator / rational.denominator));
                Rational rational2 = rationalArr[1];
                Integer valueOf2 = Integer.valueOf((int) (rational2.numerator / rational2.denominator));
                Rational rational3 = rationalArr[2];
                return String.format("%02d:%02d:%02d", valueOf, valueOf2, Integer.valueOf((int) (rational3.numerator / rational3.denominator)));
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
                                if (j2 >= 0 && j >= j2 + seekableByteOrderedDataInputStream.mDataInputStream.available()) {
                                    return -1;
                                }
                                seekableByteOrderedDataInputStream.seek(j);
                                this.mPosition = j;
                            }
                            if (i2 > seekableByteOrderedDataInputStream.mDataInputStream.available()) {
                                i2 = seekableByteOrderedDataInputStream.mDataInputStream.available();
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
                if (str != null) {
                    this.mAttributes[0].put("ImageWidth", ExifAttribute.createUShort(Integer.parseInt(str), this.mExifByteOrder));
                }
                if (str2 != null) {
                    this.mAttributes[0].put("ImageLength", ExifAttribute.createUShort(Integer.parseInt(str2), this.mExifByteOrder));
                }
                if (str3 != null) {
                    int parseInt = Integer.parseInt(str3);
                    this.mAttributes[0].put("Orientation", ExifAttribute.createUShort(parseInt != 90 ? parseInt != 180 ? parseInt != 270 ? 1 : 8 : 3 : 6, this.mExifByteOrder));
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
                        this.mAttributes[0].put("Xmp", new ExifAttribute(1, parseInt5, j, bArr3));
                    }
                }
                if (DEBUG) {
                    Log.d("ExifInterface", "Heif meta: " + str + "x" + str2 + ", rotation " + str3);
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

    /* JADX WARN: Code restructure failed: missing block: B:69:0x018a, code lost:
    
        r23.mByteOrder = r22.mExifByteOrder;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x018e, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getJpegAttributes(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r23, int r24, int r25) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getJpegAttributes(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:14|15|16|17|(2:18|19)|(16:106|(2:108|109)(1:152)|111|112|(1:114)|115|(4:118|119|(7:123|124|125|(3:127|(1:129)(2:138|(1:140))|(3:132|133|134))(1:141)|136|120|121)|144)|117|22|23|25|26|27|(1:93)(1:31)|32|(1:34)(8:36|37|39|40|41|(1:43)(1:80)|44|(1:46)(3:47|(2:48|(2:50|(2:53|54)(1:52))(2:78|79))|(1:56)(3:57|(2:58|(2:60|(1:63)(1:62))(3:68|69|(2:70|(2:72|(1:75)(1:74))(2:76|77))))|(1:66)(1:67)))))|21|22|23|25|26|27|(1:29)|93|32|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x00f0, code lost:
    
        r6 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x00f6, code lost:
    
        if (r6 != null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00f8, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00fb, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00f4, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00f2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0061, code lost:
    
        if (r9 < 16) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x00ca, code lost:
    
        if (r8 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0162, code lost:
    
        r5 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00fc, code lost:
    
        if (r2 != null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00fe, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0101, code lost:
    
        r0 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00ef, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0105 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0107 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0139 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getMimeType(java.io.BufferedInputStream r18) {
        /*
            Method dump skipped, instructions count: 409
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getMimeType(java.io.BufferedInputStream):int");
    }

    public final void getOrfAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
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
                ExifAttribute createUShort = ExifAttribute.createUShort(i5, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(i6, this.mExifByteOrder);
                this.mAttributes[0].put("ImageWidth", createUShort);
                this.mAttributes[0].put("ImageLength", createUShort2);
            }
        }
    }

    public final void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        if (DEBUG) {
            Log.d("ExifInterface", "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        int i = byteOrderedDataInputStream.mPosition;
        byteOrderedDataInputStream.skipFully(PNG_SIGNATURE.length);
        while (true) {
            try {
                int readInt = byteOrderedDataInputStream.readInt();
                int readInt2 = byteOrderedDataInputStream.readInt();
                int i2 = byteOrderedDataInputStream.mPosition - i;
                if (i2 == 16 && readInt2 != PNG_CHUNK_TYPE_IHDR) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appear as the first chunk");
                }
                if (readInt2 == PNG_CHUNK_TYPE_IEND) {
                    return;
                }
                if (readInt2 == PNG_CHUNK_TYPE_EXIF) {
                    this.mOffsetToExifData = i2;
                    byte[] bArr = new byte[readInt];
                    byteOrderedDataInputStream.readFully(bArr);
                    int readInt3 = byteOrderedDataInputStream.readInt();
                    CRC32 crc32 = new CRC32();
                    crc32.update(readInt2 >>> 24);
                    crc32.update(readInt2 >>> 16);
                    crc32.update(readInt2 >>> 8);
                    crc32.update(readInt2);
                    crc32.update(bArr);
                    if (((int) crc32.getValue()) == readInt3) {
                        readExifSegment(0, bArr);
                        validateImages();
                        setThumbnailData(new ByteOrderedDataInputStream(bArr));
                        return;
                    } else {
                        throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt3 + ", calculated CRC value: " + crc32.getValue());
                    }
                }
                byteOrderedDataInputStream.skipFully(readInt + 4);
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt PNG file.", e);
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
            ListPopupWindow$$ExternalSyntheticOutline0.m(readInt, "numberOfDirectoryEntry: ", "ExifInterface");
        }
        for (int i4 = 0; i4 < readInt; i4++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                ExifAttribute createUShort = ExifAttribute.createUShort(readShort, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(readShort2, this.mExifByteOrder);
                this.mAttributes[0].put("ImageLength", createUShort);
                this.mAttributes[0].put("ImageWidth", createUShort2);
                if (z) {
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(readShort, readShort2, "Updated to length: ", ", width: ", "ExifInterface");
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipFully(readUnsignedShort2);
        }
    }

    public final void getRawAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
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

    public final void getRw2Attributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0092  */
    /* JADX WARN: Type inference failed for: r1v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.content.res.AssetManager$AssetInputStream, java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.Closeable, java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] getThumbnailBytes() {
        /*
            r8 = this;
            java.lang.String r0 = "ExifInterface"
            boolean r1 = r8.mHasThumbnail
            r2 = 0
            if (r1 != 0) goto L9
            goto L8c
        L9:
            byte[] r1 = r8.mThumbnailBytes
            if (r1 == 0) goto Le
            return r1
        Le:
            android.content.res.AssetManager$AssetInputStream r1 = r8.mAssetInputStream     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            if (r1 == 0) goto L2e
            boolean r3 = r1.markSupported()     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L22
            if (r3 == 0) goto L25
            r1.reset()     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L22
        L1b:
            r3 = r2
            goto L56
        L1d:
            r8 = move-exception
            r3 = r2
        L1f:
            r2 = r1
            goto L8d
        L22:
            r8 = move-exception
            r3 = r2
            goto L7f
        L25:
            java.lang.String r8 = "Cannot read thumbnail from inputstream without mark/reset support"
            android.util.Log.d(r0, r8)     // Catch: java.lang.Throwable -> L1d java.lang.Exception -> L22
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r1)
            return r2
        L2e:
            java.lang.String r1 = r8.mFilename     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            if (r1 == 0) goto L41
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            java.lang.String r3 = r8.mFilename     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            goto L1b
        L3a:
            r8 = move-exception
            r3 = r2
            goto L8d
        L3d:
            r8 = move-exception
            r1 = r2
            r3 = r1
            goto L7f
        L41:
            java.io.FileDescriptor r1 = r8.mSeekableFileDescriptor     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            java.io.FileDescriptor r1 = android.system.Os.dup(r1)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L3d
            int r3 = android.system.OsConstants.SEEK_SET     // Catch: java.lang.Throwable -> L79 java.lang.Exception -> L7c
            r4 = 0
            android.system.Os.lseek(r1, r4, r3)     // Catch: java.lang.Throwable -> L79 java.lang.Exception -> L7c
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L79 java.lang.Exception -> L7c
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L79 java.lang.Exception -> L7c
            r7 = r3
            r3 = r1
            r1 = r7
        L56:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r4 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            int r5 = r8.mThumbnailOffset     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            int r6 = r8.mOffsetToExifData     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            int r5 = r5 + r6
            r4.skipFully(r5)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            int r5 = r8.mThumbnailLength     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            r4.readFully(r5)     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            r8.mThumbnailBytes = r5     // Catch: java.lang.Throwable -> L75 java.lang.Exception -> L77
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r3 == 0) goto L74
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        L74:
            return r5
        L75:
            r8 = move-exception
            goto L1f
        L77:
            r8 = move-exception
            goto L7f
        L79:
            r8 = move-exception
            r3 = r1
            goto L8d
        L7c:
            r8 = move-exception
            r3 = r1
            r1 = r2
        L7f:
            java.lang.String r4 = "Encountered exception while getting thumbnail"
            android.util.Log.d(r0, r4, r8)     // Catch: java.lang.Throwable -> L75
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r3 == 0) goto L8c
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        L8c:
            return r2
        L8d:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r2)
            if (r3 == 0) goto L95
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        L95:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getThumbnailBytes():byte[]");
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
                int i = length + 8;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr2)) {
                    byte[] bArr3 = new byte[readInt2];
                    byteOrderedDataInputStream.readFully(bArr3);
                    byte[] bArr4 = IDENTIFIER_EXIF_APP1;
                    if (ExifInterfaceUtils.startsWith(bArr3, bArr4)) {
                        bArr3 = Arrays.copyOfRange(bArr3, bArr4.length, readInt2 - bArr4.length);
                    }
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
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt WebP file.", e);
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
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(intValue, intValue2, "Setting thumbnail attributes with offset: ", ", length: ", "ExifInterface");
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

    /* JADX WARN: Removed duplicated region for block: B:30:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadAttributes(java.io.InputStream r9) {
        /*
            r8 = this;
            boolean r0 = androidx.exifinterface.media.ExifInterface.DEBUG
            r1 = 0
            r2 = r1
        L4:
            androidx.exifinterface.media.ExifInterface$ExifTag[][] r3 = androidx.exifinterface.media.ExifInterface.EXIF_TAGS     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            int r3 = r3.length     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            if (r2 >= r3) goto L1b
            java.util.HashMap[] r3 = r8.mAttributes     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            java.util.HashMap r4 = new java.util.HashMap     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            r4.<init>()     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            r3[r2] = r4     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18
            int r2 = r2 + 1
            goto L4
        L15:
            r9 = move-exception
            goto Lac
        L18:
            r9 = move-exception
            goto La2
        L1b:
            boolean r2 = r8.mIsExifDataOnly
            if (r2 != 0) goto L2d
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r4 = 5000(0x1388, float:7.006E-42)
            r3.<init>(r9, r4)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            int r9 = r8.getMimeType(r3)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r8.mMimeType = r9     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r9 = r3
        L2d:
            int r3 = r8.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r4 = 14
            r5 = 13
            r6 = 9
            r7 = 4
            if (r3 == r7) goto L7b
            if (r3 == r6) goto L7b
            if (r3 == r5) goto L7b
            if (r3 != r4) goto L3f
            goto L7b
        L3f:
            androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream r1 = new androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r1.<init>(r9)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            if (r2 == 0) goto L55
            boolean r9 = r8.getStandaloneAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            if (r9 != 0) goto L71
            r8.addDefaultValuesForCompatibility()
            if (r0 == 0) goto Lbd
            r8.printAttributes()
            return
        L55:
            int r9 = r8.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r2 = 12
            if (r9 != r2) goto L5f
            r8.getHeifAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L71
        L5f:
            r2 = 7
            if (r9 != r2) goto L66
            r8.getOrfAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L71
        L66:
            r2 = 10
            if (r9 != r2) goto L6e
            r8.getRw2Attributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L71
        L6e:
            r8.getRawAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
        L71:
            int r9 = r8.mOffsetToExifData     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            long r2 = (long) r9     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r1.seek(r2)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r8.setThumbnailData(r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L99
        L7b:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r2 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            r2.<init>(r9)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            int r9 = r8.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            if (r9 != r7) goto L88
            r8.getJpegAttributes(r2, r1, r1)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L99
        L88:
            if (r9 != r5) goto L8e
            r8.getPngAttributes(r2)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L99
        L8e:
            if (r9 != r6) goto L94
            r8.getRafAttributes(r2)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
            goto L99
        L94:
            if (r9 != r4) goto L99
            r8.getWebpAttributes(r2)     // Catch: java.lang.Throwable -> L15 java.lang.Throwable -> L18 java.lang.Throwable -> L18
        L99:
            r8.addDefaultValuesForCompatibility()
            if (r0 == 0) goto Lbd
            r8.printAttributes()
            return
        La2:
            if (r0 == 0) goto Lb5
            java.lang.String r1 = "ExifInterface"
            java.lang.String r2 = "Invalid image: ExifInterface got an unsupported image format file (ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface."
            android.util.Log.w(r1, r2, r9)     // Catch: java.lang.Throwable -> L15
            goto Lb5
        Lac:
            r8.addDefaultValuesForCompatibility()
            if (r0 == 0) goto Lb4
            r8.printAttributes()
        Lb4:
            throw r9
        Lb5:
            r8.addDefaultValuesForCompatibility()
            if (r0 == 0) goto Lbd
            r8.printAttributes()
        Lbd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.loadAttributes(java.io.InputStream):void");
    }

    public final void parseTiffHeaders(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) {
        ByteOrder readByteOrder = readByteOrder(seekableByteOrderedDataInputStream);
        this.mExifByteOrder = readByteOrder;
        seekableByteOrderedDataInputStream.mByteOrder = readByteOrder;
        int readUnsignedShort = seekableByteOrderedDataInputStream.readUnsignedShort();
        int i = this.mMimeType;
        if (i != 7 && i != 10 && readUnsignedShort != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(readUnsignedShort));
        }
        int readInt = seekableByteOrderedDataInputStream.readInt();
        if (readInt < 8) {
            throw new IOException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readInt, "Invalid first Ifd offset: "));
        }
        int i2 = readInt - 8;
        if (i2 > 0) {
            seekableByteOrderedDataInputStream.skipFully(i2);
        }
    }

    public final void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "The size of tag group[", "]: ");
            m.append(this.mAttributes[i].size());
            Log.d("ExifInterface", m.toString());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) entry.getValue();
                Log.d("ExifInterface", "tagName: " + ((String) entry.getKey()) + ", tagType: " + exifAttribute.toString() + ", tagValue: '" + exifAttribute.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public final void readExifSegment(int i, byte[] bArr) {
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(bArr);
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0239  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readImageFileDirectory(androidx.exifinterface.media.ExifInterface.SeekableByteOrderedDataInputStream r25, int r26) {
        /*
            Method dump skipped, instructions count: 817
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.readImageFileDirectory(androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream, int):void");
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
        HashMap hashMap = this.mAttributes[i];
        hashMap.put(str2, (ExifAttribute) hashMap.get(str));
        this.mAttributes[i].remove(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x00fb A[Catch: all -> 0x010b, Exception -> 0x010e, TryCatch #19 {Exception -> 0x010e, all -> 0x010b, blocks: (B:68:0x00f7, B:70:0x00fb, B:72:0x0119, B:76:0x0111), top: B:67:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0111 A[Catch: all -> 0x010b, Exception -> 0x010e, TryCatch #19 {Exception -> 0x010e, all -> 0x010b, blocks: (B:68:0x00f7, B:70:0x00fb, B:72:0x0119, B:76:0x0111), top: B:67:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void saveAttributes() {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.saveAttributes():void");
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
                    int read = byteOrderedDataInputStream.read(bArr3, 0, Math.min(i, 4096));
                    if (read >= 0) {
                        byteOrderedDataOutputStream.write(bArr3, 0, read);
                        i -= read;
                    }
                }
            } else {
                int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
                int i2 = readUnsignedShort2 - 2;
                if (i2 < 0) {
                    throw new IOException("Invalid length");
                }
                int length2 = bArr.length;
                byte[] bArr4 = IDENTIFIER_EXIF_APP1;
                byte[] bArr5 = i2 >= length2 ? new byte[bArr.length] : i2 >= bArr4.length ? new byte[bArr4.length] : null;
                if (bArr5 != null) {
                    byteOrderedDataInputStream.readFully(bArr5);
                    if (ExifInterfaceUtils.startsWith(bArr5, bArr4) || (ExifInterfaceUtils.startsWith(bArr5, bArr) && this.mXmpIsFromSeparateMarker)) {
                        byteOrderedDataInputStream.skipFully(i2 - bArr5.length);
                    }
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort2);
                if (bArr5 != null) {
                    i2 -= bArr5.length;
                    byteOrderedDataOutputStream.write(bArr5);
                }
                while (i2 > 0) {
                    int read2 = byteOrderedDataInputStream.read(bArr3, 0, Math.min(i2, 4096));
                    if (read2 >= 0) {
                        byteOrderedDataOutputStream.write(bArr3, 0, read2);
                        i2 -= read2;
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
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, PNG_SIGNATURE.length);
        if (this.mOffsetToExifData == 0) {
            int readInt = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(readInt);
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt + 8);
        } else {
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, (r2 - r7.length) - 8);
            byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt() + 8);
        }
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th) {
            th = th;
        }
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
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
            throw th;
        }
    }

    public final void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) {
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
                    } catch (Exception e) {
                        e = e;
                        byteArrayOutputStream3 = byteArrayOutputStream4;
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream3 = byteArrayOutputStream4;
                    }
                    try {
                        if (i5 != 0) {
                            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, (i5 - ((bArr2.length + 4) + bArr3.length)) - 8);
                            byteOrderedDataInputStream.skipFully(4);
                            int readInt = byteOrderedDataInputStream.readInt();
                            if (readInt % 2 != 0) {
                                readInt++;
                            }
                            byteOrderedDataInputStream.skipFully(readInt);
                            writeExifSegment(byteOrderedDataOutputStream3);
                        } else {
                            byte[] bArr4 = new byte[4];
                            byteOrderedDataInputStream.readFully(bArr4);
                            byte[] bArr5 = WEBP_CHUNK_TYPE_VP8X;
                            boolean equals = Arrays.equals(bArr4, bArr5);
                            byte[] bArr6 = WEBP_CHUNK_TYPE_VP8;
                            byte[] bArr7 = WEBP_CHUNK_TYPE_VP8L;
                            if (equals) {
                                int readInt2 = byteOrderedDataInputStream.readInt();
                                byte[] bArr8 = new byte[readInt2 % 2 == 1 ? readInt2 + 1 : readInt2];
                                byteOrderedDataInputStream.readFully(bArr8);
                                byte b = (byte) (8 | bArr8[0]);
                                bArr8[0] = b;
                                boolean z2 = ((b >> 1) & 1) == 1;
                                byteOrderedDataOutputStream3.write(bArr5);
                                byteOrderedDataOutputStream3.writeInt(readInt2);
                                byteOrderedDataOutputStream3.write(bArr8);
                                if (z2) {
                                    byte[] bArr9 = WEBP_CHUNK_TYPE_ANIM;
                                    do {
                                        bArr = new byte[4];
                                        byteOrderedDataInputStream.readFully(bArr);
                                        int readInt3 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr);
                                        byteOrderedDataOutputStream3.writeInt(readInt3);
                                        if (readInt3 % 2 == 1) {
                                            readInt3++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt3);
                                    } while (!Arrays.equals(bArr, bArr9));
                                    while (true) {
                                        byte[] bArr10 = new byte[4];
                                        try {
                                            byteOrderedDataInputStream.readFully(bArr10);
                                            z = !Arrays.equals(bArr10, WEBP_CHUNK_TYPE_ANMF);
                                        } catch (EOFException unused) {
                                            z = true;
                                        }
                                        if (z) {
                                            break;
                                        }
                                        int readInt4 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr10);
                                        byteOrderedDataOutputStream3.writeInt(readInt4);
                                        if (readInt4 % 2 == 1) {
                                            readInt4++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt4);
                                    }
                                    writeExifSegment(byteOrderedDataOutputStream3);
                                } else {
                                    while (true) {
                                        byte[] bArr11 = new byte[4];
                                        byteOrderedDataInputStream.readFully(bArr11);
                                        int readInt5 = byteOrderedDataInputStream.readInt();
                                        byteOrderedDataOutputStream3.write(bArr11);
                                        byteOrderedDataOutputStream3.writeInt(readInt5);
                                        if (readInt5 % 2 == 1) {
                                            readInt5++;
                                        }
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, readInt5);
                                        if (Arrays.equals(bArr11, bArr6) || (bArr7 != null && Arrays.equals(bArr11, bArr7))) {
                                            break;
                                        }
                                    }
                                    writeExifSegment(byteOrderedDataOutputStream3);
                                }
                            } else if (Arrays.equals(bArr4, bArr6) || Arrays.equals(bArr4, bArr7)) {
                                int readInt6 = byteOrderedDataInputStream.readInt();
                                int i6 = readInt6 % 2 == 1 ? readInt6 + 1 : readInt6;
                                byte[] bArr12 = new byte[3];
                                boolean equals2 = Arrays.equals(bArr4, bArr6);
                                boolean z3 = true;
                                byte[] bArr13 = WEBP_VP8_SIGNATURE;
                                if (equals2) {
                                    byteOrderedDataInputStream.readFully(bArr12);
                                    byte[] bArr14 = new byte[3];
                                    byteOrderedDataInputStream.readFully(bArr14);
                                    if (!Arrays.equals(bArr13, bArr14)) {
                                        throw new IOException("Error checking VP8 signature");
                                    }
                                    i = byteOrderedDataInputStream.readInt();
                                    i6 -= 10;
                                    i2 = (i << 18) >> 18;
                                    i3 = (i << 2) >> 18;
                                    z3 = false;
                                } else if (!Arrays.equals(bArr4, bArr7)) {
                                    i = 0;
                                    i2 = 0;
                                    z3 = false;
                                    i3 = 0;
                                } else {
                                    if (byteOrderedDataInputStream.readByte() != 47) {
                                        throw new IOException("Error checking VP8L signature");
                                    }
                                    i = byteOrderedDataInputStream.readInt();
                                    i2 = (i & 16383) + 1;
                                    i3 = ((i & 268419072) >>> 14) + 1;
                                    if ((i & 268435456) == 0) {
                                        z3 = false;
                                    }
                                    i6 -= 5;
                                }
                                byteOrderedDataOutputStream3.write(bArr5);
                                byteOrderedDataOutputStream3.writeInt(10);
                                byte[] bArr15 = new byte[10];
                                if (z3) {
                                    i4 = i2;
                                    bArr15[0] = (byte) (bArr15[0] | 16);
                                } else {
                                    i4 = i2;
                                }
                                bArr15[0] = (byte) (bArr15[0] | 8);
                                int i7 = i4 - 1;
                                byteArrayOutputStream2 = byteArrayOutputStream4;
                                int i8 = i3 - 1;
                                byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                                try {
                                    bArr15[4] = (byte) i7;
                                    bArr15[5] = (byte) (i7 >> 8);
                                    bArr15[6] = (byte) (i7 >> 16);
                                    bArr15[7] = (byte) i8;
                                    bArr15[8] = (byte) (i8 >> 8);
                                    bArr15[9] = (byte) (i8 >> 16);
                                    byteOrderedDataOutputStream3.write(bArr15);
                                    byteOrderedDataOutputStream3.write(bArr4);
                                    byteOrderedDataOutputStream3.writeInt(readInt6);
                                    try {
                                        if (!Arrays.equals(bArr4, bArr6)) {
                                            if (Arrays.equals(bArr4, bArr7)) {
                                                byteOrderedDataOutputStream3.write(47);
                                                byteOrderedDataOutputStream3.writeInt(i);
                                            }
                                            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i6);
                                            writeExifSegment(byteOrderedDataOutputStream3);
                                            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                                            ByteOrderedDataOutputStream byteOrderedDataOutputStream4 = byteOrderedDataOutputStream;
                                            byteOrderedDataOutputStream4.writeInt(byteArrayOutputStream2.size() + bArr3.length);
                                            byteOrderedDataOutputStream4.write(bArr3);
                                            byteArrayOutputStream = byteArrayOutputStream2;
                                            byteArrayOutputStream.writeTo(byteOrderedDataOutputStream4);
                                            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                                            return;
                                        }
                                        byteOrderedDataOutputStream3.write(bArr12);
                                        byteOrderedDataOutputStream3.write(bArr13);
                                        byteOrderedDataOutputStream3.writeInt(i);
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3, i6);
                                        writeExifSegment(byteOrderedDataOutputStream3);
                                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                                        ByteOrderedDataOutputStream byteOrderedDataOutputStream42 = byteOrderedDataOutputStream;
                                        byteOrderedDataOutputStream42.writeInt(byteArrayOutputStream2.size() + bArr3.length);
                                        byteOrderedDataOutputStream42.write(bArr3);
                                        byteArrayOutputStream = byteArrayOutputStream2;
                                        byteArrayOutputStream.writeTo(byteOrderedDataOutputStream42);
                                        ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                                        return;
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
                                } catch (Exception e3) {
                                    e = e3;
                                    byteArrayOutputStream = byteArrayOutputStream2;
                                    byteArrayOutputStream3 = byteArrayOutputStream;
                                    throw new IOException("Failed to save WebP file", e);
                                } catch (Throwable th3) {
                                    th = th3;
                                    byteArrayOutputStream = byteArrayOutputStream2;
                                    byteArrayOutputStream3 = byteArrayOutputStream;
                                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream3);
                                    throw th;
                                }
                            }
                        }
                        byteArrayOutputStream.writeTo(byteOrderedDataOutputStream42);
                        ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                        return;
                    } catch (Exception e4) {
                        e = e4;
                        byteArrayOutputStream3 = byteArrayOutputStream;
                        throw new IOException("Failed to save WebP file", e);
                    } catch (Throwable th4) {
                        th = th4;
                        byteArrayOutputStream3 = byteArrayOutputStream;
                        ExifInterfaceUtils.closeQuietly(byteArrayOutputStream3);
                        throw th;
                    }
                    byteOrderedDataOutputStream = byteOrderedDataOutputStream2;
                    byteArrayOutputStream2 = byteArrayOutputStream4;
                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream3);
                    ByteOrderedDataOutputStream byteOrderedDataOutputStream422 = byteOrderedDataOutputStream;
                    byteOrderedDataOutputStream422.writeInt(byteArrayOutputStream2.size() + bArr3.length);
                    byteOrderedDataOutputStream422.write(bArr3);
                    byteArrayOutputStream = byteArrayOutputStream2;
                } catch (Exception e5) {
                    e = e5;
                    byteArrayOutputStream = byteArrayOutputStream4;
                } catch (Throwable th5) {
                    th = th5;
                    byteArrayOutputStream = byteArrayOutputStream4;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (Exception e6) {
            e = e6;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x033f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAttribute(java.lang.String r37, java.lang.String r38) {
        /*
            Method dump skipped, instructions count: 1172
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.setAttribute(java.lang.String, java.lang.String):void");
    }

    public final void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        String str;
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
        int i = 1;
        if (intValue2 != 1) {
            if (intValue2 == 6) {
                handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                return;
            } else if (intValue2 != 7) {
                return;
            }
        }
        ExifAttribute exifAttribute3 = (ExifAttribute) hashMap.get("BitsPerSample");
        String str2 = "ExifInterface";
        if (exifAttribute3 != null) {
            int[] iArr = (int[]) exifAttribute3.getValue(this.mExifByteOrder);
            int[] iArr2 = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr2, iArr) || (this.mMimeType == 3 && (exifAttribute = (ExifAttribute) hashMap.get("PhotometricInterpretation")) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, iArr2))))) {
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
                int i2 = (int) j;
                byte[] bArr = new byte[i2];
                this.mAreThumbnailStripsConsecutive = true;
                this.mHasThumbnailStrips = true;
                this.mHasThumbnail = true;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i3 < convertToLongArray.length) {
                    int i6 = (int) convertToLongArray[i3];
                    int i7 = (int) convertToLongArray2[i3];
                    if (i3 < convertToLongArray.length - i) {
                        str = str2;
                        if (i6 + i7 != convertToLongArray[i3 + 1]) {
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
                    this.mThumbnailOffset = (int) convertToLongArray[0];
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

    public final void swapBasedOnImageSize(int i, int i2) {
        boolean isEmpty = this.mAttributes[i].isEmpty();
        boolean z = DEBUG;
        if (isEmpty || this.mAttributes[i2].isEmpty()) {
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
        HashMap[] hashMapArr = this.mAttributes;
        HashMap hashMap = hashMapArr[i];
        hashMapArr[i] = hashMapArr[i2];
        hashMapArr[i2] = hashMap;
    }

    public final void updateImageSizeValues(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) {
        ExifAttribute createUShort;
        ExifAttribute createUShort2;
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
            this.mAttributes[i].put("ImageWidth", createUShort);
            this.mAttributes[i].put("ImageLength", createUShort2);
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
            this.mAttributes[i].put("ImageLength", createUShort3);
            this.mAttributes[i].put("ImageWidth", createUShort4);
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

    public final void validateImages() {
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
            HashMap[] hashMapArr = this.mAttributes;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap();
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

    public final void writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream) {
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
        int i6 = 8;
        for (int i7 = 0; i7 < exifTagArr.length; i7++) {
            if (!this.mAttributes[i7].isEmpty()) {
                iArr2[i7] = i6;
                i6 = (this.mAttributes[i7].size() * 12) + 6 + iArr3[i7] + i6;
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put("StripOffsets", ExifAttribute.createUShort(i6, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(i6, this.mExifByteOrder));
            }
            this.mThumbnailOffset = i6;
            i6 += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            i6 += 8;
        }
        if (DEBUG) {
            for (int i8 = 0; i8 < exifTagArr.length; i8++) {
                Log.d("ExifInterface", String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i8), Integer.valueOf(iArr2[i8]), Integer.valueOf(this.mAttributes[i8].size()), Integer.valueOf(iArr3[i8]), Integer.valueOf(i6)));
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
        int i9 = this.mMimeType;
        if (i9 == 4) {
            if (i6 > 65535) {
                throw new IllegalStateException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i6, "Size of exif data (", " bytes) exceeds the max size of a JPEG APP1 segment (65536 bytes)"));
            }
            byteOrderedDataOutputStream.writeUnsignedShort(i6);
            byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        } else if (i9 == 13) {
            byteOrderedDataOutputStream.writeInt(i6);
            byteOrderedDataOutputStream.writeInt(PNG_CHUNK_TYPE_EXIF);
        } else if (i9 == 14) {
            byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
            byteOrderedDataOutputStream.writeInt(i6);
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
        byteOrderedDataOutputStream.mByteOrder = this.mExifByteOrder;
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (int i10 = 0; i10 < exifTagArr.length; i10++) {
            if (!this.mAttributes[i10].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[i10].size());
                int size = (this.mAttributes[i10].size() * 12) + iArr2[i10] + 2 + 4;
                for (Map.Entry entry : this.mAttributes[i10].entrySet()) {
                    int i11 = ((ExifTag) sExifTagMapsForWriting[i10].get(entry.getKey())).number;
                    ExifAttribute exifAttribute2 = (ExifAttribute) entry.getValue();
                    exifAttribute2.getClass();
                    int i12 = exifAttribute2.format;
                    int i13 = iArr[i12];
                    int i14 = exifAttribute2.numberOfComponents;
                    int i15 = i13 * i14;
                    byteOrderedDataOutputStream.writeUnsignedShort(i11);
                    byteOrderedDataOutputStream.writeUnsignedShort(i12);
                    byteOrderedDataOutputStream.writeInt(i14);
                    if (i15 > 4) {
                        byteOrderedDataOutputStream.writeUnsignedInt(size);
                        size += i15;
                    } else {
                        byteOrderedDataOutputStream.write(exifAttribute2.bytes);
                        if (i15 < 4) {
                            while (i15 < 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i15++;
                            }
                        }
                    }
                }
                if (i10 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0L);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt(iArr2[4]);
                }
                Iterator it3 = this.mAttributes[i10].entrySet().iterator();
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
        if (this.mMimeType == 14 && i6 % 2 == i) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        throw new EOFException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Reached EOF while skipping ", " bytes."));
                    }
                }
                i2 += skip;
            }
            this.mPosition += i2;
        }

        public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SeekableByteOrderedDataInputStream extends ByteOrderedDataInputStream {
        public SeekableByteOrderedDataInputStream(byte[] bArr) throws IOException {
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

        public SeekableByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
            if (inputStream.markSupported()) {
                this.mDataInputStream.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public ExifInterface(String str) throws IOException {
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

    public ExifInterface(FileDescriptor fileDescriptor) throws IOException {
        boolean z;
        FileInputStream fileInputStream;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (fileDescriptor != null) {
            FileInputStream fileInputStream2 = null;
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
                fileInputStream = new FileInputStream(fileDescriptor);
            } catch (Throwable th) {
                th = th;
            }
            try {
                loadAttributes(fileInputStream);
                ExifInterfaceUtils.closeQuietly(fileInputStream);
                if (z) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                    return;
                }
                return;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                if (z) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                throw th;
            }
        }
        throw new NullPointerException("fileDescriptor cannot be null");
    }

    public ExifInterface(InputStream inputStream) throws IOException {
        this(inputStream, 0);
    }

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
