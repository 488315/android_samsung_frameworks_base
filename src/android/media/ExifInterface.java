package android.media;

import android.app.admin.DevicePolicyResources;
import android.bluetooth.hci.BluetoothHciProtoEnums;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.gnss.GnssSignalType;
import android.hardware.scontext.SContextConstants;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.opengl.GLES30;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import com.android.internal.telephony.gsm.SmsCbConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/* loaded from: classes2.dex */
public class ExifInterface {
    private static final Charset ASCII;
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final int DATA_DEFLATE_ZIP = 8;
    private static final int DATA_HUFFMAN_COMPRESSED = 2;
    private static final int DATA_JPEG = 6;
    private static final int DATA_JPEG_COMPRESSED = 7;
    private static final int DATA_LOSSY_JPEG = 34892;
    private static final int DATA_PACK_BITS_COMPRESSED = 32773;
    private static final int DATA_UNCOMPRESSED = 1;
    private static final ExifTag[] EXIF_POINTER_TAGS;
    private static final ExifTag[][] EXIF_TAGS;
    private static final byte[] IDENTIFIER_EXIF_APP1;
    private static final byte[] IDENTIFIER_XMP_APP1;
    private static final ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    private static final int IFD_TYPE_PREVIEW = 5;
    private static final int IFD_TYPE_PRIMARY = 0;
    private static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_PNG = 13;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final int IMAGE_TYPE_WEBP = 14;
    private static final byte MARKER = -1;
    private static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS;
    private static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    private static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    private static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    private static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int PNG_CHUNK_CRC_BYTE_LENGTH = 4;
    private static final int PNG_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    private static final int REDUCED_RESOLUTION_IMAGE = 1;
    private static final short RW2_SIGNATURE = 85;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    private static final byte START_CODE = 42;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;

    @Deprecated
    public static final String TAG_APERTURE = "FNumber";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";

    @Deprecated
    public static final String TAG_ISO = "ISOSpeedRatings";
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE;
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIG = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIG = "SubSecTimeOriginal";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    private static final int WEBP_CHUNK_SIZE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_VP8X_DEFAULT_LENGTH = 10;
    private static final int WEBP_FILE_SIZE_BYTE_LENGTH = 4;
    private static final byte WEBP_VP8L_SIGNATURE = 47;
    public static final int WHITEBALANCE_AUTO = 0;
    public static final int WHITEBALANCE_MANUAL = 1;
    private static final HashMap<Integer, Integer> sExifPointerTagMap;
    private static final HashMap[] sExifTagMapsForReading;
    private static final HashMap[] sExifTagMapsForWriting;
    private static SimpleDateFormat sFormatter;
    private static SimpleDateFormat sFormatterTz;
    private static final Pattern sGpsTimestampPattern;
    private static final Pattern sNonZeroTimePattern;
    private static final HashSet<String> sTagSetForCompatibility;
    private boolean mAreThumbnailStripsConsecutive;
    private AssetManager.AssetInputStream mAssetInputStream;
    private final HashMap[] mAttributes;
    private ByteOrder mExifByteOrder;
    private boolean mExifHasLength;
    private boolean mExifHasOrientation;
    private boolean mExifHasWidth;
    private int mExifOffset;
    private String mFilename;
    private Set<Integer> mHandledIfdOffsets;
    private boolean mHasThumbnail;
    private boolean mHasThumbnailStrips;
    private boolean mIsExifDataOnly;
    private boolean mIsInputStream;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private boolean mModified;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;
    private boolean mXmpIsFromSeparateMarker;
    private static final String TAG = "ExifInterface";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final byte MARKER_SOI = -40;
    private static final byte[] JPEG_SIGNATURE = {-1, MARKER_SOI, -1};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, SprAttributeBase.TYPE_SHADOW};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33};
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] HEIF_BRAND_AVIF = {SprAttributeBase.TYPE_ANIMATOR_SET, 118, 105, 102};
    private static final byte[] HEIF_BRAND_AVIS = {SprAttributeBase.TYPE_ANIMATOR_SET, 118, 105, 115};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    private static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    private static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    private static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    private static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    private static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    private static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, 42};
    private static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());
    private static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", DevicePolicyResources.UNDEFINED, "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    private static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExifStreamType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static {
        int i = 3;
        int i2 = 4;
        int i3 = 6;
        int i4 = 10;
        int i5 = 1;
        int i6 = 2;
        int i7 = 5;
        int i8 = 7;
        int i9 = 23;
        ExifTag[] exifTagArr = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new ExifTag(TAG_SUBFILE_TYPE, 255, i2), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new ExifTag(TAG_COMPRESSION, 259, i), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, i6), new ExifTag(TAG_MAKE, 271, i6), new ExifTag(TAG_MODEL, 272, i6), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, i), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, i7), new ExifTag(TAG_Y_RESOLUTION, 283, i7), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new ExifTag(TAG_RESOLUTION_UNIT, 296, i), new ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new ExifTag(TAG_SOFTWARE, 305, i6), new ExifTag(TAG_DATETIME, 306, i6), new ExifTag(TAG_ARTIST, 315, i6), new ExifTag(TAG_WHITE_POINT, 318, i7), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i7), new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i7), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i7), new ExifTag(TAG_COPYRIGHT, 33432, i6), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, i2, i2), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, i7, i2), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, i3, i2), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, i8, i2), new ExifTag(TAG_RW2_ISO, i9, i), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, i8), new ExifTag(TAG_XMP, 700, i5)};
        IFD_TIFF_TAGS = exifTagArr;
        ExifTag[] exifTagArr2 = {new ExifTag(TAG_EXPOSURE_TIME, 33434, i7), new ExifTag("FNumber", 33437, i7), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, i), new ExifTag(TAG_SPECTRAL_SENSITIVITY, GLES30.GL_MAX_DRAW_BUFFERS, i6), new ExifTag("ISOSpeedRatings", GLES30.GL_DRAW_BUFFER2, i), new ExifTag(TAG_OECF, GLES30.GL_DRAW_BUFFER3, i8), new ExifTag(TAG_EXIF_VERSION, 36864, i6), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, i6), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, i6), new ExifTag(TAG_OFFSET_TIME, 36880, i6), new ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, i6), new ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, i6), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, i8), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, i7), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, i4), new ExifTag(TAG_APERTURE_VALUE, 37378, i7), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, i4), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, i4), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, i7), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, i7), new ExifTag(TAG_METERING_MODE, 37383, i), new ExifTag(TAG_LIGHT_SOURCE, 37384, i), new ExifTag(TAG_FLASH, 37385, i), new ExifTag(TAG_FOCAL_LENGTH, 37386, i7), new ExifTag(TAG_SUBJECT_AREA, 37396, i), new ExifTag(TAG_MAKER_NOTE, 37500, i8), new ExifTag(TAG_USER_COMMENT, 37510, i8), new ExifTag(TAG_SUBSEC_TIME, 37520, i6), new ExifTag("SubSecTimeOriginal", 37521, i6), new ExifTag("SubSecTimeDigitized", 37522, i6), new ExifTag(TAG_FLASHPIX_VERSION, UsbManager.USB_DATA_TRANSFER_RATE_40G, i8), new ExifTag(TAG_COLOR_SPACE, 40961, i), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag(TAG_RELATED_SOUND_FILE, 40964, i6), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new ExifTag(TAG_FLASH_ENERGY, 41483, i7), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, i8), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, i7), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, i7), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, i), new ExifTag(TAG_SUBJECT_LOCATION, 41492, i), new ExifTag(TAG_EXPOSURE_INDEX, 41493, i7), new ExifTag(TAG_SENSING_METHOD, 41495, i), new ExifTag(TAG_FILE_SOURCE, 41728, i8), new ExifTag(TAG_SCENE_TYPE, 41729, i8), new ExifTag(TAG_CFA_PATTERN, 41730, i8), new ExifTag(TAG_CUSTOM_RENDERED, 41985, i), new ExifTag(TAG_EXPOSURE_MODE, 41986, i), new ExifTag(TAG_WHITE_BALANCE, 41987, i), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, i7), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, i), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, i), new ExifTag(TAG_GAIN_CONTROL, 41991, i), new ExifTag(TAG_CONTRAST, 41992, i), new ExifTag(TAG_SATURATION, 41993, i), new ExifTag(TAG_SHARPNESS, 41994, i), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, i8), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, i), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, i6), new ExifTag(TAG_DNG_VERSION, 50706, i5), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_EXIF_TAGS = exifTagArr2;
        ExifTag[] exifTagArr3 = {new ExifTag(TAG_GPS_VERSION_ID, 0, i5), new ExifTag(TAG_GPS_LATITUDE_REF, i5, i6), new ExifTag(TAG_GPS_LATITUDE, i6, i7), new ExifTag(TAG_GPS_LONGITUDE_REF, i, i6), new ExifTag(TAG_GPS_LONGITUDE, i2, i7), new ExifTag(TAG_GPS_ALTITUDE_REF, i7, i5), new ExifTag(TAG_GPS_ALTITUDE, i3, i7), new ExifTag(TAG_GPS_TIMESTAMP, i8, i7), new ExifTag(TAG_GPS_SATELLITES, 8, i6), new ExifTag(TAG_GPS_STATUS, 9, i6), new ExifTag(TAG_GPS_MEASURE_MODE, i4, i6), new ExifTag(TAG_GPS_DOP, 11, i7), new ExifTag(TAG_GPS_SPEED_REF, 12, i6), new ExifTag(TAG_GPS_SPEED, 13, i7), new ExifTag(TAG_GPS_TRACK_REF, 14, i6), new ExifTag(TAG_GPS_TRACK, 15, i7), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, i6), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, i7), new ExifTag(TAG_GPS_MAP_DATUM, 18, i6), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, i6), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, i7), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, i6), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, i7), new ExifTag(TAG_GPS_DEST_BEARING_REF, i9, i6), new ExifTag(TAG_GPS_DEST_BEARING, 24, i7), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, i6), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, i7), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, i8), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, i8), new ExifTag(TAG_GPS_DATESTAMP, 29, i6), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, i)};
        IFD_GPS_TAGS = exifTagArr3;
        ExifTag[] exifTagArr4 = {new ExifTag(TAG_INTEROPERABILITY_INDEX, i5, i6)};
        IFD_INTEROPERABILITY_TAGS = exifTagArr4;
        int i10 = 4;
        int i11 = 3;
        ExifTag[] exifTagArr5 = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new ExifTag(TAG_SUBFILE_TYPE, 255, i2), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new ExifTag(TAG_COMPRESSION, 259, i), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, i6), new ExifTag(TAG_MAKE, 271, i6), new ExifTag(TAG_MODEL, 272, i6), new ExifTag(TAG_STRIP_OFFSETS, 273, i11, i10), new ExifTag(TAG_THUMBNAIL_ORIENTATION, 274, i), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new ExifTag(TAG_ROWS_PER_STRIP, 278, i11, i10), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, i7), new ExifTag(TAG_Y_RESOLUTION, 283, i7), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new ExifTag(TAG_RESOLUTION_UNIT, 296, i), new ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new ExifTag(TAG_SOFTWARE, 305, i6), new ExifTag(TAG_DATETIME, 306, i6), new ExifTag(TAG_ARTIST, 315, i6), new ExifTag(TAG_WHITE_POINT, 318, i7), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i7), new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i7), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i7), new ExifTag(TAG_COPYRIGHT, 33432, i6), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_DNG_VERSION, 50706, i5), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_THUMBNAIL_TAGS = exifTagArr5;
        TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, i);
        ExifTag[] exifTagArr6 = {new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, i8), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i2), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i2)};
        ORF_MAKER_NOTE_TAGS = exifTagArr6;
        ExifTag[] exifTagArr7 = {new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, i2), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, i2)};
        ORF_CAMERA_SETTINGS_TAGS = exifTagArr7;
        ExifTag[] exifTagArr8 = {new ExifTag(TAG_ORF_ASPECT_FRAME, SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED, i)};
        ORF_IMAGE_PROCESSING_TAGS = exifTagArr8;
        ExifTag[] exifTagArr9 = {new ExifTag(TAG_COLOR_SPACE, 55, i)};
        PEF_TAGS = exifTagArr9;
        ExifTag[][] exifTagArr10 = {exifTagArr, exifTagArr2, exifTagArr3, exifTagArr4, exifTagArr5, exifTagArr, exifTagArr6, exifTagArr7, exifTagArr8, exifTagArr9};
        EXIF_TAGS = exifTagArr10;
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i5), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i5)};
        sExifTagMapsForReading = new HashMap[exifTagArr10.length];
        sExifTagMapsForWriting = new HashMap[exifTagArr10.length];
        sTagSetForCompatibility = new HashSet<>(Arrays.asList("FNumber", TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
        sExifPointerTagMap = new HashMap<>();
        Charset forName = Charset.forName("US-ASCII");
        ASCII = forName;
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(forName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(forName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
        sFormatter = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss XXX", Locale.US);
        sFormatterTz = simpleDateFormat2;
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        int i12 = 0;
        while (true) {
            ExifTag[][] exifTagArr11 = EXIF_TAGS;
            if (i12 < exifTagArr11.length) {
                sExifTagMapsForReading[i12] = new HashMap();
                sExifTagMapsForWriting[i12] = new HashMap();
                for (ExifTag exifTag : exifTagArr11[i12]) {
                    sExifTagMapsForReading[i12].put(Integer.valueOf(exifTag.number), exifTag);
                    sExifTagMapsForWriting[i12].put(exifTag.name, exifTag);
                }
                i12++;
            } else {
                HashMap<Integer, Integer> hashMap = sExifPointerTagMap;
                ExifTag[] exifTagArr12 = EXIF_POINTER_TAGS;
                hashMap.put(Integer.valueOf(exifTagArr12[0].number), 5);
                hashMap.put(Integer.valueOf(exifTagArr12[1].number), 1);
                hashMap.put(Integer.valueOf(exifTagArr12[2].number), 2);
                hashMap.put(Integer.valueOf(exifTagArr12[3].number), 3);
                hashMap.put(Integer.valueOf(exifTagArr12[4].number), 7);
                hashMap.put(Integer.valueOf(exifTagArr12[5].number), 8);
                sNonZeroTimePattern = Pattern.compile(".*[1-9].*");
                sGpsTimestampPattern = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
                return;
            }
        }
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        private Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = j;
                this.denominator = j2;
            }
        }

        public String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public double calculate() {
            return this.numerator / this.denominator;
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        private ExifAttribute(int i, int i2, byte[] bArr) {
            this(i, i2, -1L, bArr);
        }

        private ExifAttribute(int i, int i2, long j, byte[] bArr) {
            this.format = i;
            this.numberOfComponents = i2;
            this.bytesOffset = j;
            this.bytes = bArr;
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, wrap.array());
        }

        public static ExifAttribute createUShort(int i, ByteOrder byteOrder) {
            return createUShort(new int[]{i}, byteOrder);
        }

        public static ExifAttribute createULong(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, wrap.array());
        }

        public static ExifAttribute createULong(long j, ByteOrder byteOrder) {
            return createULong(new long[]{j}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putInt(i);
            }
            return new ExifAttribute(9, iArr.length, wrap.array());
        }

        public static ExifAttribute createSLong(int i, ByteOrder byteOrder) {
            return createSLong(new int[]{i}, byteOrder);
        }

        public static ExifAttribute createByte(String str) {
            if (str.length() == 1 && str.charAt(0) >= '0' && str.charAt(0) <= '1') {
                return new ExifAttribute(1, 1, new byte[]{(byte) (str.charAt(0) - '0')});
            }
            byte[] bytes = str.getBytes(ExifInterface.ASCII);
            return new ExifAttribute(1, bytes.length, bytes);
        }

        public static ExifAttribute createString(String str) {
            byte[] bytes = (str + (char) 0).getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, bytes.length, bytes);
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

        public static ExifAttribute createURational(Rational rational, ByteOrder byteOrder) {
            return createURational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * rationalArr.length]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(10, rationalArr.length, wrap.array());
        }

        public static ExifAttribute createSRational(Rational rational, ByteOrder byteOrder) {
            return createSRational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] dArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * dArr.length]);
            wrap.order(byteOrder);
            for (double d : dArr) {
                wrap.putDouble(d);
            }
            return new ExifAttribute(12, dArr.length, wrap.array());
        }

        public static ExifAttribute createDouble(double d, ByteOrder byteOrder) {
            return createDouble(new double[]{d}, byteOrder);
        }

        public String toString() {
            return NavigationBarInflaterView.KEY_CODE_START + ExifInterface.IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + NavigationBarInflaterView.KEY_CODE_END;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Object getValue(ByteOrder byteOrder) {
            byte b;
            byte b2;
            try {
                ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(this.bytes);
                byteOrderedDataInputStream.setByteOrder(byteOrder);
                int i = 0;
                switch (this.format) {
                    case 1:
                    case 6:
                        byte[] bArr = this.bytes;
                        if (bArr.length == 1 && (b = bArr[0]) >= 0 && b <= 1) {
                            return new String(new char[]{(char) (this.bytes[0] + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90)});
                        }
                        return new String(this.bytes, ExifInterface.ASCII);
                    case 2:
                    case 7:
                        if (this.numberOfComponents >= ExifInterface.EXIF_ASCII_PREFIX.length) {
                            int i2 = 0;
                            while (true) {
                                if (i2 < ExifInterface.EXIF_ASCII_PREFIX.length) {
                                    if (this.bytes[i2] == ExifInterface.EXIF_ASCII_PREFIX[i2]) {
                                        i2++;
                                    }
                                } else {
                                    i = ExifInterface.EXIF_ASCII_PREFIX.length;
                                }
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        while (i < this.numberOfComponents && (b2 = this.bytes[i]) != 0) {
                            if (b2 >= 32) {
                                sb.append((char) b2);
                            } else {
                                sb.append('?');
                            }
                            i++;
                        }
                        return sb.toString();
                    case 3:
                        int[] iArr = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr[i] = byteOrderedDataInputStream.readUnsignedShort();
                            i++;
                        }
                        return iArr;
                    case 4:
                        long[] jArr = new long[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            jArr[i] = byteOrderedDataInputStream.readUnsignedInt();
                            i++;
                        }
                        return jArr;
                    case 5:
                        Rational[] rationalArr = new Rational[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            rationalArr[i] = new Rational(byteOrderedDataInputStream.readUnsignedInt(), byteOrderedDataInputStream.readUnsignedInt());
                            i++;
                        }
                        return rationalArr;
                    case 8:
                        int[] iArr2 = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr2[i] = byteOrderedDataInputStream.readShort();
                            i++;
                        }
                        return iArr2;
                    case 9:
                        int[] iArr3 = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr3[i] = byteOrderedDataInputStream.readInt();
                            i++;
                        }
                        return iArr3;
                    case 10:
                        Rational[] rationalArr2 = new Rational[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            rationalArr2[i] = new Rational(byteOrderedDataInputStream.readInt(), byteOrderedDataInputStream.readInt());
                            i++;
                        }
                        return rationalArr2;
                    case 11:
                        double[] dArr = new double[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            dArr[i] = byteOrderedDataInputStream.readFloat();
                            i++;
                        }
                        return dArr;
                    case 12:
                        double[] dArr2 = new double[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            dArr2[i] = byteOrderedDataInputStream.readDouble();
                            i++;
                        }
                        return dArr2;
                    default:
                        return null;
                }
            } catch (IOException e) {
                Log.w(ExifInterface.TAG, "IOException occurred during reading a value", e);
                return null;
            }
        }

        public double getDoubleValue(ByteOrder byteOrder) {
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
            if (value instanceof Rational[]) {
                Rational[] rationalArr = (Rational[]) value;
                if (rationalArr.length == 1) {
                    return rationalArr[0].calculate();
                }
                throw new NumberFormatException("There are more than one component");
            }
            throw new NumberFormatException("Couldn't find a double value");
        }

        public int getIntValue(ByteOrder byteOrder) {
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
            if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                if (iArr.length == 1) {
                    return iArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            throw new NumberFormatException("Couldn't find a integer value");
        }

        public String getStringValue(ByteOrder byteOrder) {
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

        public int size() {
            return ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    private static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        private ExifTag(String str, int i, int i2) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = -1;
        }

        private ExifTag(String str, int i, int i2, int i3) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = i3;
        }
    }

    public ExifInterface(File file) throws IOException {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mHandledIfdOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (file == null) {
            throw new NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    public ExifInterface(String str) throws IOException {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mHandledIfdOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (str == null) {
            throw new NullPointerException("filename cannot be null");
        }
        initForFilename(str);
    }

    public ExifInterface(FileDescriptor fileDescriptor) throws IOException {
        boolean z;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mHandledIfdOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (fileDescriptor == null) {
            throw new NullPointerException("fileDescriptor cannot be null");
        }
        ParcelFileDescriptor convertToModernFd = FileUtils.convertToModernFd(fileDescriptor);
        fileDescriptor = convertToModernFd != null ? convertToModernFd.getFileDescriptor() : fileDescriptor;
        FileInputStream fileInputStream = null;
        this.mAssetInputStream = null;
        this.mFilename = null;
        if (isSeekableFD(fileDescriptor) && convertToModernFd == null) {
            this.mSeekableFileDescriptor = fileDescriptor;
            try {
                fileDescriptor = Os.dup(fileDescriptor);
                z = true;
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            this.mSeekableFileDescriptor = null;
            z = false;
        }
        this.mIsInputStream = false;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(fileDescriptor);
            try {
                loadAttributes(fileInputStream2);
                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                if (z) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                ExifInterfaceUtils.closeQuietly(fileInputStream);
                if (z) {
                    ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public ExifInterface(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public ExifInterface(InputStream inputStream, int i) throws IOException {
        this(inputStream, i == 1);
    }

    private ExifInterface(InputStream inputStream, boolean z) throws IOException {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mHandledIfdOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (inputStream == null) {
            throw new NullPointerException("inputStream cannot be null");
        }
        this.mFilename = null;
        if (z) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
            if (!isExifDataOnly(bufferedInputStream)) {
                Log.w(TAG, "Given data does not follow the structure of an Exif-only data.");
                return;
            }
            this.mIsExifDataOnly = true;
            this.mAssetInputStream = null;
            this.mSeekableFileDescriptor = null;
            inputStream = bufferedInputStream;
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
    }

    public static boolean isSupportedMimeType(String str) {
        if (str == null) {
            throw new NullPointerException("mimeType shouldn't be null");
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "image/x-fuji-raf":
            case "image/x-samsung-srw":
            case "image/x-sony-arw":
            case "image/heic":
            case "image/heif":
            case "image/jpeg":
            case "image/webp":
            case "image/x-adobe-dng":
            case "image/x-panasonic-rw2":
            case "image/png":
            case "image/x-pentax-pef":
            case "image/x-olympus-orf":
            case "image/x-nikon-nef":
            case "image/x-nikon-nrw":
            case "image/x-canon-cr2":
                return true;
            default:
                return false;
        }
    }

    private ExifAttribute getExifAttribute(String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            Object obj = this.mAttributes[i].get(str);
            if (obj != null) {
                return (ExifAttribute) obj;
            }
        }
        return null;
    }

    public String getAttribute(String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                if (this.mMimeType == 12 && this.mExifByteOrder == ByteOrder.LITTLE_ENDIAN) {
                    if (str.equals(TAG_IMAGE_LENGTH)) {
                        if (!this.mExifHasLength) {
                            return exifAttribute.getStringValue(ByteOrder.BIG_ENDIAN);
                        }
                    } else if (str.equals(TAG_IMAGE_WIDTH)) {
                        if (!this.mExifHasWidth) {
                            return exifAttribute.getStringValue(ByteOrder.BIG_ENDIAN);
                        }
                    } else if (str.equals(TAG_ORIENTATION) && !this.mExifHasOrientation) {
                        return exifAttribute.getStringValue(ByteOrder.BIG_ENDIAN);
                    }
                }
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            if (str.equals(TAG_GPS_TIMESTAMP)) {
                if (exifAttribute.format != 5 && exifAttribute.format != 10) {
                    return null;
                }
                if (((Rational[]) exifAttribute.getValue(this.mExifByteOrder)).length != 3) {
                    return null;
                }
                return String.format("%02d:%02d:%02d", Integer.valueOf((int) (r5[0].numerator / r5[0].denominator)), Integer.valueOf((int) (r5[1].numerator / r5[1].denominator)), Integer.valueOf((int) (r5[2].numerator / r5[2].denominator)));
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public int getAttributeInt(String str, int i) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            try {
                return exifAttribute.getIntValue(this.mExifByteOrder);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public double getAttributeDouble(String str, double d) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            try {
                return exifAttribute.getDoubleValue(this.mExifByteOrder);
            } catch (NumberFormatException unused) {
            }
        }
        return d;
    }

    public void setAttribute(String str, String str2) {
        Object obj;
        int i;
        int i2;
        int i3;
        String str3 = str2;
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        int i4 = 1;
        if (str3 != null && sTagSetForCompatibility.contains(str)) {
            if (!str.equals(TAG_GPS_TIMESTAMP)) {
                try {
                    str3 = ((long) (Double.parseDouble(str3) * 10000.0d)) + "/10000";
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Invalid value for " + str + " : " + str3);
                    return;
                }
            } else {
                Matcher matcher = sGpsTimestampPattern.matcher(str3);
                if (!matcher.find()) {
                    Log.w(TAG, "Invalid value for " + str + " : " + str3);
                    return;
                }
                str3 = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            }
        }
        int i5 = 0;
        int i6 = 0;
        while (i6 < EXIF_TAGS.length) {
            if ((i6 != 4 || this.mHasThumbnail) && (obj = sExifTagMapsForWriting[i6].get(str)) != null) {
                if (str3 == null) {
                    this.mAttributes[i6].remove(str);
                } else {
                    ExifTag exifTag = (ExifTag) obj;
                    Pair<Integer, Integer> guessDataFormat = guessDataFormat(str3);
                    if (exifTag.primaryFormat == guessDataFormat.first.intValue() || exifTag.primaryFormat == guessDataFormat.second.intValue()) {
                        i = exifTag.primaryFormat;
                    } else if (exifTag.secondaryFormat != -1 && (exifTag.secondaryFormat == guessDataFormat.first.intValue() || exifTag.secondaryFormat == guessDataFormat.second.intValue())) {
                        i = exifTag.secondaryFormat;
                    } else if (exifTag.primaryFormat == i4 || exifTag.primaryFormat == 7 || exifTag.primaryFormat == 2) {
                        i = exifTag.primaryFormat;
                    } else if (DEBUG) {
                        StringBuilder sb = new StringBuilder("Given tag (");
                        sb.append(str);
                        sb.append(") value didn't match with one of expected formats: ");
                        String[] strArr = IFD_FORMAT_NAMES;
                        sb.append(strArr[exifTag.primaryFormat]);
                        sb.append(exifTag.secondaryFormat == -1 ? "" : ", " + strArr[exifTag.secondaryFormat]);
                        sb.append(" (guess: ");
                        sb.append(strArr[guessDataFormat.first.intValue()]);
                        sb.append(guessDataFormat.second.intValue() != -1 ? ", " + strArr[guessDataFormat.second.intValue()] : "");
                        sb.append(NavigationBarInflaterView.KEY_CODE_END);
                        Log.d(TAG, sb.toString());
                    }
                    switch (i) {
                        case 1:
                            i2 = i4;
                            i3 = i5;
                            this.mAttributes[i6].put(str, ExifAttribute.createByte(str3));
                            continue;
                        case 2:
                        case 7:
                            i2 = i4;
                            i3 = i5;
                            this.mAttributes[i6].put(str, ExifAttribute.createString(str3));
                            continue;
                        case 3:
                            i2 = i4;
                            i3 = i5;
                            String[] split = str3.split(",");
                            int[] iArr = new int[split.length];
                            for (int i7 = i3; i7 < split.length; i7++) {
                                iArr[i7] = Integer.parseInt(split[i7]);
                            }
                            this.mAttributes[i6].put(str, ExifAttribute.createUShort(iArr, this.mExifByteOrder));
                            continue;
                        case 4:
                            i2 = i4;
                            i3 = i5;
                            String[] split2 = str3.split(",");
                            long[] jArr = new long[split2.length];
                            for (int i8 = i3; i8 < split2.length; i8++) {
                                jArr[i8] = Long.parseLong(split2[i8]);
                            }
                            this.mAttributes[i6].put(str, ExifAttribute.createULong(jArr, this.mExifByteOrder));
                            continue;
                        case 5:
                            i2 = i4;
                            i3 = i5;
                            String[] split3 = str3.split(",");
                            Rational[] rationalArr = new Rational[split3.length];
                            for (int i9 = i3; i9 < split3.length; i9++) {
                                String[] split4 = split3[i9].split("/");
                                rationalArr[i9] = new Rational((long) Double.parseDouble(split4[i3]), (long) Double.parseDouble(split4[i2]));
                            }
                            this.mAttributes[i6].put(str, ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                            continue;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            i2 = i4;
                            i3 = i5;
                            if (DEBUG) {
                                Log.d(TAG, "Data format isn't one of expected formats: " + i);
                                break;
                            } else {
                                continue;
                            }
                        case 9:
                            i2 = i4;
                            i3 = i5;
                            String[] split5 = str3.split(",");
                            int[] iArr2 = new int[split5.length];
                            for (int i10 = i3; i10 < split5.length; i10++) {
                                iArr2[i10] = Integer.parseInt(split5[i10]);
                            }
                            this.mAttributes[i6].put(str, ExifAttribute.createSLong(iArr2, this.mExifByteOrder));
                            continue;
                        case 10:
                            String[] split6 = str3.split(",");
                            Rational[] rationalArr2 = new Rational[split6.length];
                            int i11 = i5;
                            while (i11 < split6.length) {
                                String[] split7 = split6[i11].split("/");
                                rationalArr2[i11] = new Rational((long) Double.parseDouble(split7[i5]), (long) Double.parseDouble(split7[i4]));
                                i11++;
                                i5 = i5;
                                i4 = i4;
                            }
                            i2 = i4;
                            i3 = i5;
                            this.mAttributes[i6].put(str, ExifAttribute.createSRational(rationalArr2, this.mExifByteOrder));
                            continue;
                        case 12:
                            String[] split8 = str3.split(",");
                            double[] dArr = new double[split8.length];
                            for (int i12 = i5; i12 < split8.length; i12++) {
                                dArr[i12] = Double.parseDouble(split8[i12]);
                            }
                            this.mAttributes[i6].put(str, ExifAttribute.createDouble(dArr, this.mExifByteOrder));
                            break;
                    }
                }
            }
            i2 = i4;
            i3 = i5;
            i6++;
            i5 = i3;
            i4 = i2;
        }
    }

    private boolean updateAttribute(String str, ExifAttribute exifAttribute) {
        boolean z = false;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            if (this.mAttributes[i].containsKey(str)) {
                this.mAttributes[i].put(str, exifAttribute);
                z = true;
            }
        }
        return z;
    }

    private void removeAttribute(String str) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(str);
        }
    }

    private void loadAttributes(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("inputstream shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            try {
                try {
                    this.mAttributes[i] = new HashMap();
                } catch (IOException | OutOfMemoryError e) {
                    this.mIsSupportedFile = false;
                    Log.d(TAG, "Invalid image: ExifInterface got an unsupported or corrupted image file", e);
                    addDefaultValuesForCompatibility();
                    if (DEBUG) {
                        printAttributes();
                        return;
                    }
                    return;
                }
            } catch (Throwable th) {
                addDefaultValuesForCompatibility();
                if (DEBUG) {
                    printAttributes();
                }
                throw th;
            }
        }
        if (!this.mIsExifDataOnly) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
            BufferedInputStream bufferedInputStream2 = bufferedInputStream;
            this.mMimeType = getMimeType(bufferedInputStream);
            inputStream = bufferedInputStream;
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        if (!this.mIsExifDataOnly) {
            switch (this.mMimeType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 8:
                case 11:
                    getRawAttributes(byteOrderedDataInputStream);
                    break;
                case 4:
                    getJpegAttributes(byteOrderedDataInputStream, 0, 0);
                    break;
                case 7:
                    getOrfAttributes(byteOrderedDataInputStream);
                    break;
                case 9:
                    getRafAttributes(byteOrderedDataInputStream);
                    break;
                case 10:
                    getRw2Attributes(byteOrderedDataInputStream);
                    break;
                case 12:
                    getHeifAttributes(byteOrderedDataInputStream);
                    break;
                case 13:
                    getPngAttributes(byteOrderedDataInputStream);
                    break;
                case 14:
                    getWebpAttributes(byteOrderedDataInputStream);
                    break;
            }
        } else {
            getStandaloneAttributes(byteOrderedDataInputStream);
        }
        setThumbnailData(byteOrderedDataInputStream);
        this.mIsSupportedFile = true;
        addDefaultValuesForCompatibility();
        if (DEBUG) {
            printAttributes();
        }
    }

    private static boolean isSeekableFD(FileDescriptor fileDescriptor) {
        try {
            Os.lseek(fileDescriptor, 0L, OsConstants.SEEK_CUR);
            return true;
        } catch (ErrnoException unused) {
            if (!DEBUG) {
                return false;
            }
            Log.d(TAG, "The file descriptor for the given input is not seekable");
            return false;
        }
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            Log.d(TAG, "The size of tag group[" + i + "]: " + this.mAttributes[i].size());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) entry.getValue();
                Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + exifAttribute.toString() + ", tagValue: '" + exifAttribute.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public void saveAttributes() throws IOException {
        FileOutputStream fileOutputStream;
        File createTempFile;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3;
        if (!isSupportedFormatForSavingAttributes()) {
            throw new IOException("ExifInterface only supports saving attributes for JPEG, PNG, and WebP formats.");
        }
        if (this.mIsInputStream || (this.mSeekableFileDescriptor == null && this.mFilename == null)) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
        if (this.mHasThumbnail && this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
            throw new IOException("ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips");
        }
        this.mModified = true;
        this.mThumbnailBytes = getThumbnail();
        FileInputStream fileInputStream2 = null;
        r0 = null;
        FileOutputStream fileOutputStream4 = null;
        FileInputStream fileInputStream3 = null;
        fileInputStream2 = null;
        try {
            createTempFile = File.createTempFile("temp", "tmp");
            if (this.mFilename != null) {
                fileInputStream = new FileInputStream(this.mFilename);
            } else {
                FileDescriptor fileDescriptor = this.mSeekableFileDescriptor;
                if (fileDescriptor != null) {
                    Os.lseek(fileDescriptor, 0L, OsConstants.SEEK_SET);
                    fileInputStream = new FileInputStream(this.mSeekableFileDescriptor);
                } else {
                    fileInputStream = null;
                }
            }
            try {
                fileOutputStream = new FileOutputStream(createTempFile);
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
                try {
                    fileInputStream = new FileInputStream(createTempFile);
                    try {
                        if (this.mFilename != null) {
                            fileOutputStream2 = new FileOutputStream(this.mFilename);
                        } else {
                            FileDescriptor fileDescriptor2 = this.mSeekableFileDescriptor;
                            if (fileDescriptor2 != null) {
                                Os.lseek(fileDescriptor2, 0L, OsConstants.SEEK_SET);
                                fileOutputStream2 = new FileOutputStream(this.mSeekableFileDescriptor);
                            } else {
                                fileOutputStream2 = null;
                            }
                        }
                        try {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                            try {
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream2);
                                try {
                                    int i = this.mMimeType;
                                    if (i == 4) {
                                        saveJpegAttributes(bufferedInputStream, bufferedOutputStream);
                                    } else if (i == 13) {
                                        savePngAttributes(bufferedInputStream, bufferedOutputStream);
                                    } else if (i == 14) {
                                        saveWebpAttributes(bufferedInputStream, bufferedOutputStream);
                                    }
                                    bufferedOutputStream.close();
                                    bufferedInputStream.close();
                                    ExifInterfaceUtils.closeQuietly(fileInputStream);
                                    ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                    createTempFile.delete();
                                    this.mThumbnailBytes = null;
                                } finally {
                                }
                            } catch (Throwable th3) {
                                try {
                                    bufferedInputStream.close();
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                }
                                throw th3;
                            }
                        } catch (Exception e3) {
                            FileOutputStream fileOutputStream5 = fileOutputStream2;
                            e = e3;
                            fileOutputStream4 = fileOutputStream5;
                            FileInputStream fileInputStream4 = new FileInputStream(createTempFile);
                            try {
                                if (this.mFilename != null) {
                                    fileOutputStream3 = new FileOutputStream(this.mFilename);
                                } else {
                                    FileDescriptor fileDescriptor3 = this.mSeekableFileDescriptor;
                                    if (fileDescriptor3 != null) {
                                        try {
                                            Os.lseek(fileDescriptor3, 0L, OsConstants.SEEK_SET);
                                            fileOutputStream3 = new FileOutputStream(this.mSeekableFileDescriptor);
                                        } catch (ErrnoException e4) {
                                            throw new IOException("Failed to save new file. Original file may be corrupted since error occurred while trying to restore it.", e4);
                                        }
                                    }
                                    ExifInterfaceUtils.copy(fileInputStream4, fileOutputStream4);
                                    ExifInterfaceUtils.closeQuietly(fileInputStream4);
                                    ExifInterfaceUtils.closeQuietly(fileOutputStream4);
                                    throw new IOException("Failed to save new file", e);
                                }
                                fileOutputStream4 = fileOutputStream3;
                                ExifInterfaceUtils.copy(fileInputStream4, fileOutputStream4);
                                ExifInterfaceUtils.closeQuietly(fileInputStream4);
                                ExifInterfaceUtils.closeQuietly(fileOutputStream4);
                                throw new IOException("Failed to save new file", e);
                            } catch (Throwable th5) {
                                th = th5;
                                fileOutputStream2 = fileOutputStream4;
                                fileInputStream3 = fileInputStream4;
                                ExifInterfaceUtils.closeQuietly(fileInputStream3);
                                ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                createTempFile.delete();
                                throw th;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            fileInputStream3 = fileInputStream;
                            ExifInterfaceUtils.closeQuietly(fileInputStream3);
                            ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                            createTempFile.delete();
                            throw th;
                        }
                    } catch (Exception e5) {
                        e = e5;
                    }
                } catch (Exception e6) {
                    e = e6;
                } catch (Throwable th7) {
                    th = th7;
                    fileOutputStream2 = null;
                    ExifInterfaceUtils.closeQuietly(fileInputStream3);
                    ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                    createTempFile.delete();
                    throw th;
                }
            } catch (Throwable th8) {
                th = th8;
                fileOutputStream2 = null;
            }
        } catch (Exception e7) {
            e = e7;
            fileInputStream2 = fileInputStream;
            try {
                throw new IOException("Failed to copy original file to temp file", e);
            } catch (Throwable th9) {
                th = th9;
                ExifInterfaceUtils.closeQuietly(fileInputStream2);
                ExifInterfaceUtils.closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th10) {
            th = th10;
            fileInputStream2 = fileInputStream;
            ExifInterfaceUtils.closeQuietly(fileInputStream2);
            ExifInterfaceUtils.closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean hasAttribute(String str) {
        return getExifAttribute(str) != null;
    }

    public byte[] getThumbnail() {
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c A[Catch: ErrnoException | IOException -> 0x008f, all -> 0x00ab, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00ab, blocks: (B:18:0x005c, B:21:0x006c, B:23:0x0078, B:28:0x0083, B:29:0x0088, B:30:0x0089, B:31:0x008e, B:32:0x0091, B:33:0x0096, B:36:0x009d), top: B:6:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0091 A[Catch: ErrnoException | IOException -> 0x008f, ErrnoException | IOException -> 0x008f, all -> 0x00ab, TryCatch #6 {all -> 0x00ab, blocks: (B:18:0x005c, B:21:0x006c, B:23:0x0078, B:28:0x0083, B:29:0x0088, B:30:0x0089, B:31:0x008e, B:32:0x0091, B:33:0x0096, B:36:0x009d), top: B:6:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b2  */
    /* JADX WARN: Type inference failed for: r1v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.content.res.AssetManager$AssetInputStream, java.io.Closeable, java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getThumbnailBytes() {
        /*
            r9 = this;
            java.lang.String r0 = "ExifInterface"
            boolean r1 = r9.mHasThumbnail
            r2 = 0
            if (r1 != 0) goto L8
            return r2
        L8:
            byte[] r1 = r9.mThumbnailBytes
            if (r1 == 0) goto Ld
            return r1
        Ld:
            android.content.res.AssetManager$AssetInputStream r1 = r9.mAssetInputStream     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a
            if (r1 == 0) goto L2d
            boolean r3 = r1.markSupported()     // Catch: java.lang.Throwable -> L25 java.lang.Throwable -> L29
            if (r3 == 0) goto L1c
            r1.reset()     // Catch: java.lang.Throwable -> L25 java.lang.Throwable -> L29
        L1a:
            r3 = r2
            goto L5a
        L1c:
            java.lang.String r9 = "Cannot read thumbnail from inputstream without mark/reset support"
            android.util.Log.d(r0, r9)     // Catch: java.lang.Throwable -> L25 java.lang.Throwable -> L29
            android.media.ExifInterfaceUtils.closeQuietly(r1)
            return r2
        L25:
            r9 = move-exception
            r3 = r2
            goto Lac
        L29:
            r9 = move-exception
            r3 = r2
            goto L9d
        L2d:
            java.lang.String r1 = r9.mFilename     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            if (r1 == 0) goto L39
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            java.lang.String r3 = r9.mFilename     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            goto L1a
        L39:
            java.io.FileDescriptor r1 = r9.mSeekableFileDescriptor     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            if (r1 == 0) goto L58
            java.io.FileDescriptor r1 = android.system.Os.dup(r1)     // Catch: java.lang.Throwable -> L97 java.lang.Throwable -> L9a java.lang.Throwable -> L9a
            int r3 = android.system.OsConstants.SEEK_SET     // Catch: java.lang.Throwable -> L51 java.lang.Throwable -> L54
            r4 = 0
            android.system.Os.lseek(r1, r4, r3)     // Catch: java.lang.Throwable -> L51 java.lang.Throwable -> L54
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L51 java.lang.Throwable -> L54
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L51 java.lang.Throwable -> L54
            r8 = r3
            r3 = r1
            r1 = r8
            goto L5a
        L51:
            r9 = move-exception
            r3 = r1
            goto Lad
        L54:
            r9 = move-exception
            r3 = r1
            r1 = r2
            goto L9d
        L58:
            r1 = r2
            r3 = r1
        L5a:
            if (r1 == 0) goto L91
            int r4 = r9.mThumbnailOffset     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            long r4 = (long) r4     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            long r4 = r1.skip(r4)     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            int r6 = r9.mThumbnailOffset     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            long r6 = (long) r6
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            java.lang.String r5 = "Corrupted image"
            if (r4 != 0) goto L89
            int r4 = r9.mThumbnailLength     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            int r6 = r1.read(r4)     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            int r7 = r9.mThumbnailLength     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            if (r6 != r7) goto L83
            r9.mThumbnailBytes = r4     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            android.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r3 == 0) goto L82
            android.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        L82:
            return r4
        L83:
            java.io.IOException r9 = new java.io.IOException     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            r9.<init>(r5)     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            throw r9     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
        L89:
            java.io.IOException r9 = new java.io.IOException     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            r9.<init>(r5)     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            throw r9     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
        L8f:
            r9 = move-exception
            goto L9d
        L91:
            java.io.FileNotFoundException r9 = new java.io.FileNotFoundException     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            r9.<init>()     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
            throw r9     // Catch: java.lang.Throwable -> L8f java.lang.Throwable -> L8f java.lang.Throwable -> Lab
        L97:
            r9 = move-exception
            r3 = r2
            goto Lad
        L9a:
            r9 = move-exception
            r1 = r2
            r3 = r1
        L9d:
            java.lang.String r4 = "Encountered exception while getting thumbnail"
            android.util.Log.d(r0, r4, r9)     // Catch: java.lang.Throwable -> Lab
            android.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r3 == 0) goto Laa
            android.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        Laa:
            return r2
        Lab:
            r9 = move-exception
        Lac:
            r2 = r1
        Lad:
            android.media.ExifInterfaceUtils.closeQuietly(r2)
            if (r3 == 0) goto Lb5
            android.media.ExifInterfaceUtils.closeFileDescriptor(r3)
        Lb5:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.getThumbnailBytes():byte[]");
    }

    public Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (i == 1) {
            int length = this.mThumbnailBytes.length / 3;
            int[] iArr = new int[length];
            for (int i2 = 0; i2 < length; i2++) {
                byte[] bArr = this.mThumbnailBytes;
                int i3 = i2 * 3;
                iArr[i2] = (bArr[i3] << 16) + (bArr[i3 + 1] << 8) + bArr[i3 + 2];
            }
            ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_LENGTH);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_WIDTH);
            if (exifAttribute != null && exifAttribute2 != null) {
                return Bitmap.createBitmap(iArr, exifAttribute2.getIntValue(this.mExifByteOrder), exifAttribute.getIntValue(this.mExifByteOrder), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (!this.mHasThumbnail) {
            return false;
        }
        int i = this.mThumbnailCompression;
        return i == 6 || i == 7;
    }

    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        if (!this.mHasThumbnail) {
            return null;
        }
        if (!this.mHasThumbnailStrips || this.mAreThumbnailStripsConsecutive) {
            return new long[]{this.mThumbnailOffset, this.mThumbnailLength};
        }
        return null;
    }

    public long[] getAttributeRange(String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return new long[]{exifAttribute.bytesOffset, exifAttribute.bytes.length};
        }
        return null;
    }

    public byte[] getAttributeBytes(String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return exifAttribute.bytes;
        }
        return null;
    }

    public void semSetAttributeBytes(String str, byte[] bArr) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if (!str.equals(TAG_MAKER_NOTE)) {
            Log.d(TAG, "setAttributeBytes only support makernote");
            return;
        }
        if (bArr == null) {
            removeAttribute(str);
            if (DEBUG) {
                Log.d(TAG, "setAttributeBytes: remove makernote");
                return;
            }
            return;
        }
        this.mAttributes[1].put(str, new ExifAttribute(1, bArr.length, bArr));
        if (DEBUG) {
            Log.d(TAG, "setAttributeBytes: set makernote");
        }
    }

    public boolean getLatLong(float[] fArr) {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (attribute != null && attribute2 != null && attribute3 != null && attribute4 != null) {
            try {
                fArr[0] = convertRationalLatLonToFloat(attribute, attribute2);
                fArr[1] = convertRationalLatLonToFloat(attribute3, attribute4);
                return true;
            } catch (IllegalArgumentException unused) {
            }
        }
        return false;
    }

    public double getAltitude(double d) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || attributeInt < 0) {
            return d;
        }
        return attributeDouble * (attributeInt != 1 ? 1 : -1);
    }

    public long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    public long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute("SubSecTimeDigitized"), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    public long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute("SubSecTimeOriginal"), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    private static long parseDateTime(String str, String str2, String str3) {
        Date parse;
        Date parse2;
        if (str != null && sNonZeroTimePattern.matcher(str).matches()) {
            ParsePosition parsePosition = new ParsePosition(0);
            try {
                synchronized (sFormatter) {
                    parse = sFormatter.parse(str, parsePosition);
                }
                if (str3 != null) {
                    String str4 = str + " " + str3;
                    ParsePosition parsePosition2 = new ParsePosition(0);
                    synchronized (sFormatterTz) {
                        parse2 = sFormatterTz.parse(str4, parsePosition2);
                    }
                    parse = parse2;
                }
                if (parse == null) {
                    return -1L;
                }
                long time = parse.getTime();
                if (str2 == null) {
                    return time;
                }
                try {
                    long parseLong = Long.parseLong(str2);
                    while (parseLong > 1000) {
                        parseLong /= 10;
                    }
                    return time + parseLong;
                } catch (NumberFormatException unused) {
                    return time;
                }
            } catch (IllegalArgumentException unused2) {
            }
        }
        return -1L;
    }

    public long getGpsDateTime() {
        Date parse;
        String attribute = getAttribute(TAG_GPS_DATESTAMP);
        String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute != null && attribute2 != null) {
            Pattern pattern = sNonZeroTimePattern;
            if (pattern.matcher(attribute).matches() || pattern.matcher(attribute2).matches()) {
                String str = attribute + ' ' + attribute2;
                ParsePosition parsePosition = new ParsePosition(0);
                try {
                    synchronized (sFormatter) {
                        parse = sFormatter.parse(str, parsePosition);
                    }
                    if (parse == null) {
                        return -1L;
                    }
                    return parse.getTime();
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException unused) {
                }
            }
        }
        return -1L;
    }

    public static float convertRationalLatLonToFloat(String str, String str2) {
        try {
            String[] split = str.split(",");
            String[] split2 = split[0].split("/");
            double parseDouble = Double.parseDouble(split2[0].trim()) / Double.parseDouble(split2[1].trim());
            String[] split3 = split[1].split("/");
            double parseDouble2 = Double.parseDouble(split3[0].trim()) / Double.parseDouble(split3[1].trim());
            String[] split4 = split[2].split("/");
            double parseDouble3 = parseDouble + (parseDouble2 / 60.0d) + ((Double.parseDouble(split4[0].trim()) / Double.parseDouble(split4[1].trim())) / 3600.0d);
            if (!str2.equals(GnssSignalType.CODE_TYPE_S)) {
                if (!str2.equals(GnssSignalType.CODE_TYPE_W)) {
                    return (float) parseDouble3;
                }
            }
            return (float) (-parseDouble3);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            throw new IllegalArgumentException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initForFilename(java.lang.String r5) throws java.io.IOException {
        /*
            r4 = this;
            r0 = 0
            r4.mAssetInputStream = r0
            r4.mFilename = r5
            r1 = 0
            r4.mIsInputStream = r1
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4f
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L4f
            java.io.FileDescriptor r5 = r1.getFD()     // Catch: java.lang.Throwable -> L4b
            android.os.ParcelFileDescriptor r5 = android.os.FileUtils.convertToModernFd(r5)     // Catch: java.lang.Throwable -> L4b
            if (r5 == 0) goto L2a
            android.media.ExifInterfaceUtils.closeQuietly(r1)     // Catch: java.lang.Throwable -> L49
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L49
            java.io.FileDescriptor r3 = r5.getFileDescriptor()     // Catch: java.lang.Throwable -> L49
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L49
            r4.mSeekableFileDescriptor = r0     // Catch: java.lang.Throwable -> L27
            r0 = r2
            goto L3b
        L27:
            r4 = move-exception
            r0 = r2
            goto L51
        L2a:
            java.io.FileDescriptor r0 = r1.getFD()     // Catch: java.lang.Throwable -> L49
            boolean r0 = isSeekableFD(r0)     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L3a
            java.io.FileDescriptor r0 = r1.getFD()     // Catch: java.lang.Throwable -> L49
            r4.mSeekableFileDescriptor = r0     // Catch: java.lang.Throwable -> L49
        L3a:
            r0 = r1
        L3b:
            r4.loadAttributes(r0)     // Catch: java.lang.Throwable -> L47
            android.media.ExifInterfaceUtils.closeQuietly(r0)
            if (r5 == 0) goto L46
            r5.close()
        L46:
            return
        L47:
            r4 = move-exception
            goto L51
        L49:
            r4 = move-exception
            goto L4d
        L4b:
            r4 = move-exception
            r5 = r0
        L4d:
            r0 = r1
            goto L51
        L4f:
            r4 = move-exception
            r5 = r0
        L51:
            android.media.ExifInterfaceUtils.closeQuietly(r0)
            if (r5 == 0) goto L59
            r5.close()
        L59:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.initForFilename(java.lang.String):void");
    }

    private int getMimeType(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (isJpegFormat(bArr)) {
            return 4;
        }
        if (isRafFormat(bArr)) {
            return 9;
        }
        if (isHeifFormat(bArr)) {
            return 12;
        }
        if (isOrfFormat(bArr)) {
            return 7;
        }
        if (isRw2Format(bArr)) {
            return 10;
        }
        if (isPngFormat(bArr)) {
            return 13;
        }
        return isWebpFormat(bArr) ? 14 : 0;
    }

    private static boolean isJpegFormat(byte[] bArr) throws IOException {
        int i = 0;
        while (true) {
            byte[] bArr2 = JPEG_SIGNATURE;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }

    private boolean isRafFormat(byte[] bArr) throws IOException {
        byte[] bytes = RAF_SIGNATURE.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bArr[i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeifFormat(byte[] bArr) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        long readInt;
        byte[] bArr2;
        long j;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            try {
                byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
                try {
                    readInt = byteOrderedDataInputStream.readInt();
                    bArr2 = new byte[4];
                    byteOrderedDataInputStream.read(bArr2);
                } catch (Exception e) {
                    e = e;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (DEBUG) {
                        Log.d(TAG, "Exception parsing HEIF file type box.", e);
                    }
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            if (!Arrays.equals(bArr2, HEIF_TYPE_FTYP)) {
                byteOrderedDataInputStream.close();
                return false;
            }
            if (readInt == 1) {
                readInt = byteOrderedDataInputStream.readLong();
                j = 16;
                if (readInt < 16) {
                    byteOrderedDataInputStream.close();
                    return false;
                }
            } else {
                j = 8;
            }
            if (readInt > bArr.length) {
                readInt = bArr.length;
            }
            long j2 = readInt - j;
            if (j2 < 8) {
                byteOrderedDataInputStream.close();
                return false;
            }
            byte[] bArr3 = new byte[4];
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (long j3 = 0; j3 < j2 / 4; j3++) {
                if (byteOrderedDataInputStream.read(bArr3) != 4) {
                    byteOrderedDataInputStream.close();
                    return false;
                }
                if (j3 != 1) {
                    if (Arrays.equals(bArr3, HEIF_BRAND_MIF1)) {
                        z = true;
                    } else if (Arrays.equals(bArr3, HEIF_BRAND_HEIC)) {
                        z2 = true;
                    } else if (Arrays.equals(bArr3, HEIF_BRAND_AVIF) || Arrays.equals(bArr3, HEIF_BRAND_AVIS)) {
                        z3 = true;
                    }
                    if (z && (z2 || z3)) {
                        byteOrderedDataInputStream.close();
                        return true;
                    }
                }
            }
            byteOrderedDataInputStream.close();
            return false;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private boolean isOrfFormat(byte[] bArr) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream);
            this.mExifByteOrder = readByteOrder;
            byteOrderedDataInputStream.setByteOrder(readByteOrder);
            short readShort = byteOrderedDataInputStream.readShort();
            boolean z = readShort == 20306 || readShort == 21330;
            byteOrderedDataInputStream.close();
            return z;
        } catch (Exception unused2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private boolean isRw2Format(byte[] bArr) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream);
            this.mExifByteOrder = readByteOrder;
            byteOrderedDataInputStream.setByteOrder(readByteOrder);
            short readShort = byteOrderedDataInputStream.readShort();
            byteOrderedDataInputStream.close();
            boolean z = readShort == 85;
            byteOrderedDataInputStream.close();
            return z;
        } catch (Exception unused2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private boolean isPngFormat(byte[] bArr) throws IOException {
        int i = 0;
        while (true) {
            byte[] bArr2 = PNG_SIGNATURE;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }

    private boolean isWebpFormat(byte[] bArr) throws IOException {
        int i = 0;
        while (true) {
            byte[] bArr2 = WEBP_SIGNATURE_1;
            if (i >= bArr2.length) {
                int i2 = 0;
                while (true) {
                    byte[] bArr3 = WEBP_SIGNATURE_2;
                    if (i2 >= bArr3.length) {
                        return true;
                    }
                    if (bArr[WEBP_SIGNATURE_1.length + i2 + 4] != bArr3[i2]) {
                        return false;
                    }
                    i2++;
                }
            } else {
                if (bArr[i] != bArr2[i]) {
                    return false;
                }
                i++;
            }
        }
    }

    private static boolean isExifDataOnly(BufferedInputStream bufferedInputStream) throws IOException {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        bufferedInputStream.mark(bArr.length);
        byte[] bArr2 = new byte[bArr.length];
        bufferedInputStream.read(bArr2);
        bufferedInputStream.reset();
        int i = 0;
        while (true) {
            byte[] bArr3 = IDENTIFIER_EXIF_APP1;
            if (i >= bArr3.length) {
                return true;
            }
            if (bArr2[i] != bArr3[i]) {
                return false;
            }
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x0191, code lost:
    
        r22.setByteOrder(r21.mExifByteOrder);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0196, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0185 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b3 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getJpegAttributes(android.media.ExifInterface.ByteOrderedDataInputStream r22, int r23, int r24) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 524
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.getJpegAttributes(android.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    private void getRawAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        ExifAttribute exifAttribute;
        parseTiffHeaders(byteOrderedDataInputStream, byteOrderedDataInputStream.available());
        readImageFileDirectory(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 5);
        updateImageSizeValues(byteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType != 8 || (exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE)) == null) {
            return;
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.bytes);
        byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
        byteOrderedDataInputStream2.seek(6L);
        readImageFileDirectory(byteOrderedDataInputStream2, 9);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[9].get(TAG_COLOR_SPACE);
        if (exifAttribute2 != null) {
            this.mAttributes[1].put(TAG_COLOR_SPACE, exifAttribute2);
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        byteOrderedDataInputStream.skipBytes(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byteOrderedDataInputStream.read(bArr);
        byteOrderedDataInputStream.skipBytes(4);
        byteOrderedDataInputStream.read(bArr2);
        int i = ByteBuffer.wrap(bArr).getInt();
        int i2 = ByteBuffer.wrap(bArr2).getInt();
        getJpegAttributes(byteOrderedDataInputStream, i, 5);
        byteOrderedDataInputStream.seek(i2);
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        int readInt = byteOrderedDataInputStream.readInt();
        if (DEBUG) {
            Log.d(TAG, "numberOfDirectoryEntry: " + readInt);
        }
        for (int i3 = 0; i3 < readInt; i3++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                ExifAttribute createUShort = ExifAttribute.createUShort(readShort, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(readShort2, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, createUShort2);
                if (DEBUG) {
                    Log.d(TAG, "Updated to length: " + ((int) readShort) + ", width: " + ((int) readShort2));
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipBytes(readUnsignedShort2);
        }
    }

    private void getHeifAttributes(final ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        String str;
        String str2;
        String str3;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(new MediaDataSource(this) { // from class: android.media.ExifInterface.1
                long mPosition;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                }

                @Override // android.media.MediaDataSource
                public long getSize() throws IOException {
                    return -1L;
                }

                @Override // android.media.MediaDataSource
                public int readAt(long j, byte[] bArr, int i, int i2) throws IOException {
                    if (i2 == 0) {
                        return 0;
                    }
                    if (j < 0) {
                        return -1;
                    }
                    try {
                        long j2 = this.mPosition;
                        if (j2 != j) {
                            if (j2 >= 0 && j >= j2 + byteOrderedDataInputStream.available()) {
                                return -1;
                            }
                            byteOrderedDataInputStream.seek(j);
                            this.mPosition = j;
                        }
                        if (i2 > byteOrderedDataInputStream.available()) {
                            i2 = byteOrderedDataInputStream.available();
                        }
                        int read = byteOrderedDataInputStream.read(bArr, i, i2);
                        if (read >= 0) {
                            this.mPosition += read;
                            return read;
                        }
                    } catch (IOException unused) {
                    }
                    this.mPosition = -1L;
                    return -1;
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
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createUShort(Integer.parseInt(str), this.mExifByteOrder));
            }
            if (str2 != null) {
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createUShort(Integer.parseInt(str2), this.mExifByteOrder));
            }
            if (str3 != null) {
                int parseInt = Integer.parseInt(str3);
                this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(parseInt != 90 ? parseInt != 180 ? parseInt != 270 ? 1 : 8 : 3 : 6, this.mExifByteOrder));
            }
            if (extractMetadata != null && extractMetadata2 != null) {
                int parseInt2 = Integer.parseInt(extractMetadata);
                int parseInt3 = Integer.parseInt(extractMetadata2);
                if (parseInt3 <= 6) {
                    throw new IOException("Invalid exif length");
                }
                byteOrderedDataInputStream.seek(parseInt2);
                byte[] bArr = new byte[6];
                if (byteOrderedDataInputStream.read(bArr) != 6) {
                    throw new IOException("Can't read identifier");
                }
                int i = parseInt2 + 6;
                int i2 = parseInt3 - 6;
                if (!Arrays.equals(bArr, IDENTIFIER_EXIF_APP1)) {
                    throw new IOException("Invalid identifier");
                }
                byte[] bArr2 = new byte[i2];
                if (byteOrderedDataInputStream.read(bArr2) != i2) {
                    throw new IOException("Can't read exif");
                }
                this.mExifOffset = i;
                readExifSegment(bArr2, 0);
            }
            String extractMetadata5 = mediaMetadataRetriever.extractMetadata(41);
            String extractMetadata6 = mediaMetadataRetriever.extractMetadata(42);
            if (extractMetadata5 != null && extractMetadata6 != null) {
                int parseInt4 = Integer.parseInt(extractMetadata5);
                int parseInt5 = Integer.parseInt(extractMetadata6);
                long j = parseInt4;
                byteOrderedDataInputStream.seek(j);
                byte[] bArr3 = new byte[parseInt5];
                if (byteOrderedDataInputStream.read(bArr3) != parseInt5) {
                    throw new IOException("Failed to read XMP from HEIF");
                }
                if (getAttribute(TAG_XMP) == null) {
                    this.mAttributes[0].put(TAG_XMP, new ExifAttribute(1, parseInt5, j, bArr3));
                }
            }
            if (DEBUG) {
                Log.d(TAG, "Heif meta: " + str + "x" + str2 + ", rotation " + str3);
            }
        } finally {
            mediaMetadataRetriever.release();
        }
    }

    private void getStandaloneAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        byteOrderedDataInputStream.skipBytes(bArr.length);
        byte[] bArr2 = new byte[byteOrderedDataInputStream.available()];
        byteOrderedDataInputStream.readFully(bArr2);
        this.mExifOffset = bArr.length;
        readExifSegment(bArr2, 0);
    }

    private void getOrfAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        int[] iArr;
        int i;
        int i2;
        int i3;
        int i4;
        getRawAttributes(byteOrderedDataInputStream);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (exifAttribute != null) {
            ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.bytes);
            byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
            byte[] bArr = ORF_MAKER_NOTE_HEADER_1;
            byte[] bArr2 = new byte[bArr.length];
            byteOrderedDataInputStream2.readFully(bArr2);
            byteOrderedDataInputStream2.seek(0L);
            byte[] bArr3 = ORF_MAKER_NOTE_HEADER_2;
            byte[] bArr4 = new byte[bArr3.length];
            byteOrderedDataInputStream2.readFully(bArr4);
            if (Arrays.equals(bArr2, bArr)) {
                byteOrderedDataInputStream2.seek(8L);
            } else if (Arrays.equals(bArr4, bArr3)) {
                byteOrderedDataInputStream2.seek(12L);
            }
            readImageFileDirectory(byteOrderedDataInputStream2, 6);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (exifAttribute2 != null && exifAttribute3 != null) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, exifAttribute2);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, exifAttribute3);
            }
            ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (exifAttribute4 == null || (i = (iArr = (int[]) exifAttribute4.getValue(this.mExifByteOrder))[2]) <= (i2 = iArr[0]) || (i3 = iArr[3]) <= (i4 = iArr[1])) {
                return;
            }
            int i5 = (i - i2) + 1;
            int i6 = (i3 - i4) + 1;
            if (i5 < i6) {
                int i7 = i5 + i6;
                i6 = i7 - i6;
                i5 = i7 - i6;
            }
            ExifAttribute createUShort = ExifAttribute.createUShort(i5, this.mExifByteOrder);
            ExifAttribute createUShort2 = ExifAttribute.createUShort(i6, this.mExifByteOrder);
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, createUShort);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort2);
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        getRawAttributes(byteOrderedDataInputStream);
        if (((ExifAttribute) this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW)) != null) {
            getJpegAttributes(byteOrderedDataInputStream, this.mRw2JpgFromRawOffset, 5);
        }
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[1].get("ISOSpeedRatings");
        if (exifAttribute == null || exifAttribute2 != null) {
            return;
        }
        this.mAttributes[1].put("ISOSpeedRatings", exifAttribute);
    }

    private void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        byte[] bArr = PNG_SIGNATURE;
        byteOrderedDataInputStream.skipBytes(bArr.length);
        int length = bArr.length;
        while (true) {
            try {
                int readInt = byteOrderedDataInputStream.readInt();
                byte[] bArr2 = new byte[4];
                if (byteOrderedDataInputStream.read(bArr2) != 4) {
                    throw new IOException("Encountered invalid length while parsing PNG chunktype");
                }
                int i = length + 8;
                if (i == 16 && !Arrays.equals(bArr2, PNG_CHUNK_TYPE_IHDR)) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_IEND)) {
                    return;
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_EXIF)) {
                    byte[] bArr3 = new byte[readInt];
                    if (byteOrderedDataInputStream.read(bArr3) != readInt) {
                        throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(bArr2));
                    }
                    int readInt2 = byteOrderedDataInputStream.readInt();
                    CRC32 crc32 = new CRC32();
                    crc32.update(bArr2);
                    crc32.update(bArr3);
                    if (((int) crc32.getValue()) != readInt2) {
                        throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt2 + ", calculated CRC value: " + crc32.getValue());
                    }
                    this.mExifOffset = i;
                    readExifSegment(bArr3, 0);
                    validateImages();
                    return;
                }
                int i2 = readInt + 4;
                byteOrderedDataInputStream.skipBytes(i2);
                length = i + i2;
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void getWebpAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        byteOrderedDataInputStream.skipBytes(WEBP_SIGNATURE_1.length);
        int readInt = byteOrderedDataInputStream.readInt() + 8;
        int skipBytes = byteOrderedDataInputStream.skipBytes(WEBP_SIGNATURE_2.length) + 8;
        while (true) {
            try {
                byte[] bArr = new byte[4];
                if (byteOrderedDataInputStream.read(bArr) != 4) {
                    throw new IOException("Encountered invalid length while parsing WebP chunktype");
                }
                int readInt2 = byteOrderedDataInputStream.readInt();
                int i = skipBytes + 8;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr)) {
                    byte[] bArr2 = new byte[readInt2];
                    if (byteOrderedDataInputStream.read(bArr2) != readInt2) {
                        throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(bArr));
                    }
                    this.mExifOffset = i;
                    readExifSegment(bArr2, 0);
                    this.mExifOffset = i;
                    return;
                }
                if (readInt2 % 2 == 1) {
                    readInt2++;
                }
                int i2 = i + readInt2;
                if (i2 == readInt) {
                    return;
                }
                if (i2 > readInt) {
                    throw new IOException("Encountered WebP file with invalid chunk size");
                }
                int skipBytes2 = byteOrderedDataInputStream.skipBytes(readInt2);
                if (skipBytes2 != readInt2) {
                    throw new IOException("Encountered WebP file with invalid chunk size");
                }
                skipBytes = i + skipBytes2;
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() != -1) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-1);
        if (dataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-40);
        ExifAttribute exifAttribute = (getAttribute(TAG_XMP) == null || !this.mXmpIsFromSeparateMarker) ? null : (ExifAttribute) this.mAttributes[0].remove(TAG_XMP);
        byteOrderedDataOutputStream.writeByte(-1);
        byteOrderedDataOutputStream.writeByte(-31);
        writeExifSegment(byteOrderedDataOutputStream);
        if (exifAttribute != null) {
            this.mAttributes[0].put(TAG_XMP, exifAttribute);
        }
        byte[] bArr = new byte[4096];
        while (dataInputStream.readByte() == -1) {
            byte readByte = dataInputStream.readByte();
            if (readByte == -39 || readByte == -38) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream);
                return;
            }
            if (readByte == -31) {
                int readUnsignedShort = dataInputStream.readUnsignedShort();
                int i = readUnsignedShort - 2;
                if (i < 0) {
                    throw new IOException("Invalid length");
                }
                byte[] bArr2 = new byte[6];
                if (i >= 6) {
                    if (dataInputStream.read(bArr2) != 6) {
                        throw new IOException("Invalid exif");
                    }
                    if (Arrays.equals(bArr2, IDENTIFIER_EXIF_APP1)) {
                        int i2 = readUnsignedShort - 8;
                        if (dataInputStream.skipBytes(i2) != i2) {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort);
                if (i >= 6) {
                    i = readUnsignedShort - 8;
                    byteOrderedDataOutputStream.write(bArr2);
                }
                while (i > 0) {
                    int read = dataInputStream.read(bArr, 0, Math.min(i, 4096));
                    if (read >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, read);
                        i -= read;
                    }
                }
            } else {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(readByte);
                int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort2);
                int i3 = readUnsignedShort2 - 2;
                if (i3 < 0) {
                    throw new IOException("Invalid length");
                }
                while (i3 > 0) {
                    int read2 = dataInputStream.read(bArr, 0, Math.min(i3, 4096));
                    if (read2 >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, read2);
                        i3 -= read2;
                    }
                }
            }
        }
        throw new IOException("Invalid marker");
    }

    private void savePngAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, PNG_SIGNATURE.length);
        if (this.mExifOffset == 0) {
            int readInt = dataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(readInt);
            ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, readInt + 8);
        } else {
            ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, (r1 - r6.length) - 8);
            dataInputStream.skipBytes(dataInputStream.readInt() + 8);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream, ByteOrder.BIG_ENDIAN);
            writeExifSegment(byteOrderedDataOutputStream2);
            byte[] byteArray = ((ByteArrayOutputStream) byteOrderedDataOutputStream2.mOutputStream).toByteArray();
            byteOrderedDataOutputStream.write(byteArray);
            CRC32 crc32 = new CRC32();
            crc32.update(byteArray, 4, byteArray.length - 4);
            byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
            byteArrayOutputStream.close();
            ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream);
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        char c;
        int i;
        int i2;
        int i3;
        if (DEBUG) {
            Log.d(TAG, "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream, ByteOrder.LITTLE_ENDIAN);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.LITTLE_ENDIAN);
        byte[] bArr = WEBP_SIGNATURE_1;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr.length);
        byte[] bArr2 = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipBytes(bArr2.length + 4);
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, ByteOrder.LITTLE_ENDIAN);
                    int i4 = this.mExifOffset;
                    if (i4 != 0) {
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, (i4 - ((bArr.length + 4) + bArr2.length)) - 8);
                        byteOrderedDataInputStream.skipBytes(4);
                        int readInt = byteOrderedDataInputStream.readInt();
                        if (readInt % 2 != 0) {
                            readInt++;
                        }
                        byteOrderedDataInputStream.skipBytes(readInt);
                        writeExifSegment(byteOrderedDataOutputStream2);
                    } else {
                        byte[] bArr3 = new byte[4];
                        if (byteOrderedDataInputStream.read(bArr3) != 4) {
                            throw new IOException("Encountered invalid length while parsing WebP chunk type");
                        }
                        byte[] bArr4 = WEBP_CHUNK_TYPE_VP8X;
                        boolean z = true;
                        if (Arrays.equals(bArr3, bArr4)) {
                            int readInt2 = byteOrderedDataInputStream.readInt();
                            byte[] bArr5 = new byte[readInt2 % 2 == 1 ? readInt2 + 1 : readInt2];
                            byteOrderedDataInputStream.read(bArr5);
                            byte b = (byte) (8 | bArr5[0]);
                            bArr5[0] = b;
                            boolean z2 = ((b >> 1) & 1) == 1;
                            byteOrderedDataOutputStream2.write(bArr4);
                            byteOrderedDataOutputStream2.writeInt(readInt2);
                            byteOrderedDataOutputStream2.write(bArr5);
                            if (z2) {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_ANIM, null);
                                while (true) {
                                    byte[] bArr6 = new byte[4];
                                    inputStream.read(bArr6);
                                    if (!Arrays.equals(bArr6, WEBP_CHUNK_TYPE_ANMF)) {
                                        break;
                                    } else {
                                        copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream2, bArr6);
                                    }
                                }
                                writeExifSegment(byteOrderedDataOutputStream2);
                            } else {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        } else {
                            byte[] bArr7 = WEBP_CHUNK_TYPE_VP8;
                            if (Arrays.equals(bArr3, bArr7) || Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                int readInt3 = byteOrderedDataInputStream.readInt();
                                int i5 = readInt3 % 2 == 1 ? readInt3 + 1 : readInt3;
                                byte[] bArr8 = new byte[3];
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataInputStream.read(bArr8);
                                    byte[] bArr9 = new byte[3];
                                    c = '\b';
                                    if (byteOrderedDataInputStream.read(bArr9) != 3 || !Arrays.equals(WEBP_VP8_SIGNATURE, bArr9)) {
                                        throw new IOException("Encountered error while checking VP8 signature");
                                    }
                                    i = byteOrderedDataInputStream.readInt();
                                    i5 -= 10;
                                    i3 = (i << 2) >> 18;
                                    i2 = (i << 18) >> 18;
                                    z = false;
                                } else {
                                    c = '\b';
                                    if (!Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                        i = 0;
                                        z = false;
                                        i2 = 0;
                                        i3 = 0;
                                    } else {
                                        if (byteOrderedDataInputStream.readByte() != 47) {
                                            throw new IOException("Encountered error while checking VP8L signature");
                                        }
                                        i = byteOrderedDataInputStream.readInt();
                                        i2 = ((i << 18) >> 18) + 1;
                                        i3 = ((i << 4) >> 18) + 1;
                                        if ((i & 268435456) == 0) {
                                            z = false;
                                        }
                                        i5 -= 5;
                                    }
                                }
                                byteOrderedDataOutputStream2.write(bArr4);
                                byteOrderedDataOutputStream2.writeInt(10);
                                byte[] bArr10 = new byte[10];
                                if (z) {
                                    bArr10[0] = (byte) (bArr10[0] | 16);
                                }
                                bArr10[0] = (byte) (bArr10[0] | 8);
                                int i6 = i2 - 1;
                                int i7 = i3 - 1;
                                bArr10[4] = (byte) i6;
                                bArr10[5] = (byte) (i6 >> 8);
                                bArr10[6] = (byte) (i6 >> 16);
                                bArr10[7] = (byte) i7;
                                bArr10[c] = (byte) (i7 >> 8);
                                bArr10[9] = (byte) (i7 >> 16);
                                byteOrderedDataOutputStream2.write(bArr10);
                                byteOrderedDataOutputStream2.write(bArr3);
                                byteOrderedDataOutputStream2.writeInt(readInt3);
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataOutputStream2.write(bArr8);
                                    byteOrderedDataOutputStream2.write(WEBP_VP8_SIGNATURE);
                                    byteOrderedDataOutputStream2.writeInt(i);
                                } else if (Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                    byteOrderedDataOutputStream2.write(47);
                                    byteOrderedDataOutputStream2.writeInt(i);
                                }
                                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, i5);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        }
                    }
                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2);
                    int size = byteArrayOutputStream2.size();
                    byte[] bArr11 = WEBP_SIGNATURE_2;
                    byteOrderedDataOutputStream.writeInt(size + bArr11.length);
                    byteOrderedDataOutputStream.write(bArr11);
                    byteArrayOutputStream2.writeTo(byteOrderedDataOutputStream);
                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                } catch (Exception e) {
                    e = e;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    throw new IOException("Failed to save WebP file", e);
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private void copyChunksUpToGivenChunkType(ByteOrderedDataInputStream byteOrderedDataInputStream, ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr, byte[] bArr2) throws IOException {
        while (true) {
            byte[] bArr3 = new byte[4];
            if (byteOrderedDataInputStream.read(bArr3) != 4) {
                StringBuilder sb = new StringBuilder("Encountered invalid length while copying WebP chunks up tochunk type ");
                Charset charset = ASCII;
                sb.append(new String(bArr, charset));
                sb.append(bArr2 == null ? "" : " or ".concat(new String(bArr2, charset)));
                throw new IOException(sb.toString());
            }
            copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr3);
            if (Arrays.equals(bArr3, bArr)) {
                return;
            }
            if (bArr2 != null && Arrays.equals(bArr3, bArr2)) {
                return;
            }
        }
    }

    private void copyWebPChunk(ByteOrderedDataInputStream byteOrderedDataInputStream, ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr) throws IOException {
        int readInt = byteOrderedDataInputStream.readInt();
        byteOrderedDataOutputStream.write(bArr);
        byteOrderedDataOutputStream.writeInt(readInt);
        if (readInt % 2 == 1) {
            readInt++;
        }
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt);
    }

    private void readExifSegment(byte[] bArr, int i) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        parseTiffHeaders(byteOrderedDataInputStream, bArr.length);
        readImageFileDirectory(byteOrderedDataInputStream, i);
    }

    private void addDefaultValuesForCompatibility() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        short readShort = byteOrderedDataInputStream.readShort();
        if (readShort == 18761) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (readShort == 19789) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        }
        throw new IOException("Invalid byte order: " + Integer.toHexString(readShort));
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream);
        this.mExifByteOrder = readByteOrder;
        byteOrderedDataInputStream.setByteOrder(readByteOrder);
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        int i2 = this.mMimeType;
        if (i2 != 7 && i2 != 10 && readUnsignedShort != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(readUnsignedShort));
        }
        int readInt = byteOrderedDataInputStream.readInt();
        if (readInt < 8 || readInt >= i) {
            throw new IOException("Invalid first Ifd offset: " + readInt);
        }
        int i3 = readInt - 8;
        if (i3 <= 0 || byteOrderedDataInputStream.skipBytes(i3) == i3) {
            return;
        }
        throw new IOException("Couldn't jump to first Ifd: " + i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x028f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readImageFileDirectory(android.media.ExifInterface.ByteOrderedDataInputStream r28, int r29) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 929
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.readImageFileDirectory(android.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        ExifAttribute exifAttribute;
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_WIDTH);
        if ((exifAttribute2 == null || exifAttribute3 == null) && (exifAttribute = (ExifAttribute) this.mAttributes[i].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            getJpegAttributes(byteOrderedDataInputStream, exifAttribute.getIntValue(this.mExifByteOrder), i);
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        HashMap hashMap = this.mAttributes[4];
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_COMPRESSION);
        if (exifAttribute != null) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            this.mThumbnailCompression = intValue;
            if (intValue != 1) {
                if (intValue == 6) {
                    handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                    return;
                } else if (intValue != 7) {
                    return;
                }
            }
            if (isSupportedDataType(hashMap)) {
                handleThumbnailFromStrips(byteOrderedDataInputStream, hashMap);
                return;
            }
            return;
        }
        handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        if (this.mMimeType == 7) {
            intValue += this.mOrfMakerNoteOffset;
        }
        int min = Math.min(intValue2, byteOrderedDataInputStream.getLength() - intValue);
        if (intValue > 0 && min > 0) {
            this.mHasThumbnail = true;
            int i = this.mExifOffset + intValue;
            this.mThumbnailOffset = i;
            this.mThumbnailLength = min;
            this.mThumbnailCompression = 6;
            if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                byte[] bArr = new byte[min];
                byteOrderedDataInputStream.seek(i);
                byteOrderedDataInputStream.readFully(bArr);
                this.mThumbnailBytes = bArr;
            }
        }
        if (DEBUG) {
            Log.d(TAG, "Setting thumbnail attributes with offset: " + intValue + ", length: " + min);
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (exifAttribute == null || exifAttribute2 == null) {
            return;
        }
        long[] convertToLongArray = ExifInterfaceUtils.convertToLongArray(exifAttribute.getValue(this.mExifByteOrder));
        long[] convertToLongArray2 = ExifInterfaceUtils.convertToLongArray(exifAttribute2.getValue(this.mExifByteOrder));
        if (convertToLongArray == null || convertToLongArray.length == 0) {
            Log.w(TAG, "stripOffsets should not be null or have zero length.");
            return;
        }
        if (convertToLongArray2 == null || convertToLongArray2.length == 0) {
            Log.w(TAG, "stripByteCounts should not be null or have zero length.");
            return;
        }
        if (convertToLongArray.length != convertToLongArray2.length) {
            Log.w(TAG, "stripOffsets and stripByteCounts should have same length.");
            return;
        }
        int sum = (int) Arrays.stream(convertToLongArray2).sum();
        byte[] bArr = new byte[sum];
        this.mAreThumbnailStripsConsecutive = true;
        this.mHasThumbnailStrips = true;
        this.mHasThumbnail = true;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < convertToLongArray.length; i3++) {
            int i4 = (int) convertToLongArray[i3];
            int i5 = (int) convertToLongArray2[i3];
            if (i3 < convertToLongArray.length - 1 && i4 + i5 != convertToLongArray[i3 + 1]) {
                this.mAreThumbnailStripsConsecutive = false;
            }
            int i6 = i4 - i;
            if (i6 < 0) {
                Log.d(TAG, "Invalid strip offset value");
            }
            byteOrderedDataInputStream.seek(i6);
            int i7 = i + i6;
            byte[] bArr2 = new byte[i5];
            byteOrderedDataInputStream.read(bArr2);
            i = i7 + i5;
            System.arraycopy(bArr2, 0, bArr, i2, i5);
            i2 += i5;
        }
        this.mThumbnailBytes = bArr;
        if (this.mAreThumbnailStripsConsecutive) {
            this.mThumbnailOffset = ((int) convertToLongArray[0]) + this.mExifOffset;
            this.mThumbnailLength = sum;
        }
    }

    private boolean isSupportedDataType(HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute;
        int intValue;
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_BITS_PER_SAMPLE);
        if (exifAttribute2 != null) {
            int[] iArr = (int[]) exifAttribute2.getValue(this.mExifByteOrder);
            int[] iArr2 = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr2, iArr)) {
                return true;
            }
            if (this.mMimeType == 3 && (exifAttribute = (ExifAttribute) hashMap.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, iArr2)))) {
                return true;
            }
        }
        if (!DEBUG) {
            return false;
        }
        Log.d(TAG, "Unsupported data type value");
        return false;
    }

    private boolean isThumbnail(HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            return false;
        }
        return exifAttribute.getIntValue(this.mExifByteOrder) <= 512 && exifAttribute2.getIntValue(this.mExifByteOrder) <= 512;
    }

    private void validateImages() throws IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (exifAttribute != null && exifAttribute2 != null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap[] hashMapArr = this.mAttributes;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(4, TAG_ORIENTATION, TAG_THUMBNAIL_ORIENTATION);
        replaceInvalidTags(4, TAG_IMAGE_LENGTH, TAG_THUMBNAIL_IMAGE_LENGTH);
        replaceInvalidTags(4, TAG_IMAGE_WIDTH, TAG_THUMBNAIL_IMAGE_WIDTH);
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        ExifAttribute createUShort;
        ExifAttribute createUShort2;
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute exifAttribute5 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (exifAttribute != null) {
            if (exifAttribute.format == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                createUShort = ExifAttribute.createURational(rationalArr[0], this.mExifByteOrder);
                createUShort2 = ExifAttribute.createURational(rationalArr[1], this.mExifByteOrder);
            } else {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                createUShort = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                createUShort2 = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            this.mAttributes[i].put(TAG_IMAGE_WIDTH, createUShort);
            this.mAttributes[i].put(TAG_IMAGE_LENGTH, createUShort2);
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
            this.mAttributes[i].put(TAG_IMAGE_LENGTH, createUShort3);
            this.mAttributes[i].put(TAG_IMAGE_WIDTH, createUShort4);
            return;
        }
        retrieveJpegImageSize(byteOrderedDataInputStream, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0244  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int writeExifSegment(android.media.ExifInterface.ByteOrderedDataOutputStream r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 881
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.writeExifSegment(android.media.ExifInterface$ByteOrderedDataOutputStream):int");
    }

    private static Pair<Integer, Integer> guessDataFormat(String str) {
        if (str.contains(",")) {
            String[] split = str.split(",");
            Pair<Integer, Integer> guessDataFormat = guessDataFormat(split[0]);
            if (guessDataFormat.first.intValue() == 2) {
                return guessDataFormat;
            }
            for (int i = 1; i < split.length; i++) {
                Pair<Integer, Integer> guessDataFormat2 = guessDataFormat(split[i]);
                int intValue = (Objects.equals(guessDataFormat2.first, guessDataFormat.first) || Objects.equals(guessDataFormat2.second, guessDataFormat.first)) ? guessDataFormat.first.intValue() : -1;
                int intValue2 = (guessDataFormat.second.intValue() == -1 || !(Objects.equals(guessDataFormat2.first, guessDataFormat.second) || Objects.equals(guessDataFormat2.second, guessDataFormat.second))) ? -1 : guessDataFormat.second.intValue();
                if (intValue == -1 && intValue2 == -1) {
                    return new Pair<>(2, -1);
                }
                if (intValue == -1) {
                    guessDataFormat = new Pair<>(Integer.valueOf(intValue2), -1);
                } else if (intValue2 == -1) {
                    guessDataFormat = new Pair<>(Integer.valueOf(intValue), -1);
                }
            }
            return guessDataFormat;
        }
        if (str.contains("/")) {
            String[] split2 = str.split("/");
            if (split2.length == 2) {
                try {
                    long parseDouble = (long) Double.parseDouble(split2[0]);
                    long parseDouble2 = (long) Double.parseDouble(split2[1]);
                    if (parseDouble >= 0 && parseDouble2 >= 0) {
                        if (parseDouble <= 2147483647L && parseDouble2 <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException unused) {
                }
            }
            return new Pair<>(2, -1);
        }
        try {
            try {
                long parseLong = Long.parseLong(str);
                Long valueOf = Long.valueOf(parseLong);
                valueOf.getClass();
                if (parseLong >= 0) {
                    valueOf.getClass();
                    if (parseLong <= 65535) {
                        return new Pair<>(3, 4);
                    }
                }
                valueOf.getClass();
                if (parseLong < 0) {
                    return new Pair<>(9, -1);
                }
                return new Pair<>(4, -1);
            } catch (NumberFormatException unused2) {
                return new Pair<>(2, -1);
            }
        } catch (NumberFormatException unused3) {
            Double.parseDouble(str);
            return new Pair<>(12, -1);
        }
    }

    private static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private ByteOrder mByteOrder;
        private DataInputStream mDataInputStream;
        private InputStream mInputStream;
        private final int mLength;
        private int mPosition;
        private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;

        public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            this(inputStream, ByteOrder.BIG_ENDIAN);
        }

        ByteOrderedDataInputStream(InputStream inputStream, ByteOrder byteOrder) throws IOException {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            this.mInputStream = inputStream;
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.mDataInputStream = dataInputStream;
            int available = dataInputStream.available();
            this.mLength = available;
            this.mPosition = 0;
            this.mDataInputStream.mark(available);
            this.mByteOrder = byteOrder;
        }

        public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
            this(new ByteArrayInputStream(bArr));
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long j) throws IOException {
            int i = this.mPosition;
            if (i > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                j -= i;
            }
            int i2 = (int) j;
            if (skipBytes(i2) != i2) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.mDataInputStream.available();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.mPosition + i2;
            this.mPosition = i3;
            if (i3 > this.mLength) {
                throw new EOFException();
            }
            if (this.mDataInputStream.read(bArr, i, i2) != i2) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            int length = this.mPosition + bArr.length;
            this.mPosition = length;
            if (length > this.mLength) {
                throw new EOFException();
            }
            if (this.mDataInputStream.read(bArr, 0, bArr.length) != bArr.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            int i = this.mPosition + 1;
            this.mPosition = i;
            if (i > this.mLength) {
                throw new EOFException();
            }
            int read = this.mDataInputStream.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            int i;
            int i2 = this.mPosition + 2;
            this.mPosition = i2;
            if (i2 > this.mLength) {
                throw new EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == LITTLE_ENDIAN) {
                i = (read2 << 8) + read;
            } else {
                if (byteOrder != BIG_ENDIAN) {
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                i = (read << 8) + read2;
            }
            return (short) i;
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            int i = this.mPosition + 4;
            this.mPosition = i;
            if (i > this.mLength) {
                throw new EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == LITTLE_ENDIAN) {
                return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            }
            if (byteOrder == BIG_ENDIAN) {
                return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws IOException {
            int min = Math.min(i, this.mLength - this.mPosition);
            int i2 = 0;
            while (i2 < min) {
                int skipBytes = this.mDataInputStream.skipBytes(min - i2);
                if (skipBytes <= 0) {
                    break;
                }
                i2 += skipBytes;
            }
            this.mPosition += i2;
            return i2;
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            int i = this.mPosition + 2;
            this.mPosition = i;
            if (i > this.mLength) {
                throw new EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == LITTLE_ENDIAN) {
                return (read2 << 8) + read;
            }
            if (byteOrder == BIG_ENDIAN) {
                return (read << 8) + read2;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        public long readUnsignedInt() throws IOException {
            return readInt() & 4294967295L;
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            int i = this.mPosition + 8;
            this.mPosition = i;
            if (i > this.mLength) {
                throw new EOFException();
            }
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
            if (byteOrder == LITTLE_ENDIAN) {
                return (read8 << 56) + (read7 << 48) + (read6 << 40) + (read5 << 32) + (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            }
            if (byteOrder == BIG_ENDIAN) {
                return (read << 56) + (read2 << 48) + (read3 << 40) + (read4 << 32) + (read5 << 24) + (read6 << 16) + (read7 << 8) + read8;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        public int getLength() {
            return this.mLength;
        }
    }

    private static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private ByteOrder mByteOrder;
        final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.mOutputStream.write(bArr);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.mOutputStream.write(bArr, i, i2);
        }

        public void writeByte(int i) throws IOException {
            this.mOutputStream.write(i);
        }

        public void writeShort(short s) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(s & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write(s & 255);
            }
        }

        public void writeInt(int i) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(i & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
                return;
            }
            if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write(i & 255);
            }
        }

        public void writeUnsignedShort(int i) throws IOException {
            writeShort((short) i);
        }

        public void writeUnsignedInt(long j) throws IOException {
            writeInt((int) j);
        }
    }

    private void swapBasedOnImageSize(int i, int i2) throws IOException {
        if (this.mAttributes[i].isEmpty() || this.mAttributes[i2].isEmpty()) {
            if (DEBUG) {
                Log.d(TAG, "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_WIDTH);
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i2].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i2].get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            if (DEBUG) {
                Log.d(TAG, "First image does not contain valid size information");
                return;
            }
            return;
        }
        if (exifAttribute3 == null || exifAttribute4 == null) {
            if (DEBUG) {
                Log.d(TAG, "Second image does not contain valid size information");
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

    private void replaceInvalidTags(int i, String str, String str2) {
        if (this.mAttributes[i].isEmpty() || this.mAttributes[i].get(str) == null) {
            return;
        }
        HashMap hashMap = this.mAttributes[i];
        hashMap.put(str2, hashMap.get(str));
        this.mAttributes[i].remove(str);
    }

    private boolean isSupportedFormatForSavingAttributes() {
        if (!this.mIsSupportedFile) {
            return false;
        }
        int i = this.mMimeType;
        return i == 4 || i == 13 || i == 14;
    }
}
