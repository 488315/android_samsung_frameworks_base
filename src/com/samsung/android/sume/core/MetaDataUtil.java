package com.samsung.android.sume.core;

import android.media.ExifInterface;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public final class MetaDataUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int JPEG_LENGTH_SIZE = 2;
    private static final int JPEG_MARKER_SIZE = 2;
    private static final String TAG = "MetaDataUtil";
    private static final String[] exifTags = {"FNumber", ExifInterface.TAG_APERTURE_VALUE, ExifInterface.TAG_ARTIST, ExifInterface.TAG_BITS_PER_SAMPLE, ExifInterface.TAG_BRIGHTNESS_VALUE, ExifInterface.TAG_CFA_PATTERN, ExifInterface.TAG_COLOR_SPACE, ExifInterface.TAG_COMPONENTS_CONFIGURATION, ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, ExifInterface.TAG_COMPRESSION, ExifInterface.TAG_CONTRAST, ExifInterface.TAG_COPYRIGHT, ExifInterface.TAG_CUSTOM_RENDERED, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_DATETIME_ORIGINAL, ExifInterface.TAG_DEFAULT_CROP_SIZE, ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, ExifInterface.TAG_DIGITAL_ZOOM_RATIO, ExifInterface.TAG_DNG_VERSION, ExifInterface.TAG_EXIF_VERSION, ExifInterface.TAG_EXPOSURE_BIAS_VALUE, ExifInterface.TAG_EXPOSURE_INDEX, ExifInterface.TAG_EXPOSURE_MODE, ExifInterface.TAG_EXPOSURE_PROGRAM, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FILE_SOURCE, ExifInterface.TAG_FLASH, ExifInterface.TAG_FLASHPIX_VERSION, ExifInterface.TAG_FLASH_ENERGY, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, "FNumber", ExifInterface.TAG_GAIN_CONTROL, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_AREA_INFORMATION, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_DEST_BEARING, ExifInterface.TAG_GPS_DEST_BEARING_REF, ExifInterface.TAG_GPS_DEST_DISTANCE, ExifInterface.TAG_GPS_DEST_DISTANCE_REF, ExifInterface.TAG_GPS_DEST_LATITUDE, ExifInterface.TAG_GPS_DEST_LATITUDE_REF, ExifInterface.TAG_GPS_DEST_LONGITUDE, ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, ExifInterface.TAG_GPS_DIFFERENTIAL, ExifInterface.TAG_GPS_DOP, ExifInterface.TAG_GPS_IMG_DIRECTION, ExifInterface.TAG_GPS_IMG_DIRECTION_REF, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_MAP_DATUM, ExifInterface.TAG_GPS_MEASURE_MODE, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_SATELLITES, ExifInterface.TAG_GPS_SPEED, ExifInterface.TAG_GPS_SPEED_REF, ExifInterface.TAG_GPS_STATUS, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_GPS_TRACK, ExifInterface.TAG_GPS_TRACK_REF, ExifInterface.TAG_GPS_VERSION_ID, ExifInterface.TAG_IMAGE_DESCRIPTION, ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.TAG_IMAGE_UNIQUE_ID, ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.TAG_INTEROPERABILITY_INDEX, "ISOSpeedRatings", "ISOSpeedRatings", ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, ExifInterface.TAG_LIGHT_SOURCE, ExifInterface.TAG_MAKE, ExifInterface.TAG_MAKER_NOTE, ExifInterface.TAG_MAX_APERTURE_VALUE, ExifInterface.TAG_METERING_MODE, ExifInterface.TAG_MODEL, ExifInterface.TAG_NEW_SUBFILE_TYPE, ExifInterface.TAG_OECF, ExifInterface.TAG_ORF_ASPECT_FRAME, ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, ExifInterface.TAG_ORF_THUMBNAIL_IMAGE, ExifInterface.TAG_ORIENTATION, ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, ExifInterface.TAG_PIXEL_X_DIMENSION, ExifInterface.TAG_PIXEL_Y_DIMENSION, ExifInterface.TAG_PLANAR_CONFIGURATION, ExifInterface.TAG_PRIMARY_CHROMATICITIES, ExifInterface.TAG_REFERENCE_BLACK_WHITE, ExifInterface.TAG_RELATED_SOUND_FILE, ExifInterface.TAG_RESOLUTION_UNIT, ExifInterface.TAG_ROWS_PER_STRIP, ExifInterface.TAG_RW2_ISO, ExifInterface.TAG_RW2_JPG_FROM_RAW, ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, ExifInterface.TAG_SAMPLES_PER_PIXEL, ExifInterface.TAG_SATURATION, ExifInterface.TAG_SCENE_CAPTURE_TYPE, ExifInterface.TAG_SCENE_TYPE, ExifInterface.TAG_SENSING_METHOD, ExifInterface.TAG_SHARPNESS, ExifInterface.TAG_SHUTTER_SPEED_VALUE, ExifInterface.TAG_SOFTWARE, ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, ExifInterface.TAG_SPECTRAL_SENSITIVITY, ExifInterface.TAG_STRIP_BYTE_COUNTS, ExifInterface.TAG_STRIP_OFFSETS, ExifInterface.TAG_SUBFILE_TYPE, ExifInterface.TAG_SUBJECT_AREA, ExifInterface.TAG_SUBJECT_DISTANCE, ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, ExifInterface.TAG_SUBJECT_LOCATION, ExifInterface.TAG_SUBSEC_TIME, "SubSecTimeDigitized", "SubSecTimeDigitized", "SubSecTimeOriginal", "SubSecTimeOriginal", ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, ExifInterface.TAG_TRANSFER_FUNCTION, ExifInterface.TAG_USER_COMMENT, ExifInterface.TAG_WHITE_BALANCE, ExifInterface.TAG_WHITE_POINT, ExifInterface.TAG_X_RESOLUTION, ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, ExifInterface.TAG_Y_CB_CR_POSITIONING, ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, ExifInterface.TAG_Y_RESOLUTION, ExifInterface.TAG_OFFSET_TIME_ORIGINAL, ExifInterface.TAG_OFFSET_TIME, ExifInterface.TAG_OFFSET_TIME_DIGITIZED};

    public static String[] getExifTags() {
        return exifTags;
    }

    public static ExifInterface copyExif(FileInputStream fileInputStream, RandomAccessFile randomAccessFile) throws IOException, NumberFormatException {
        ExifInterface exifInterface;
        ExifInterface exifInterface2;
        Log.d(TAG, "in: " + fileInputStream + ", out: " + randomAccessFile);
        ExifInterface exifInterface3 = null;
        try {
            fileInputStream.getChannel().position(0L);
            randomAccessFile.getChannel().position(0L);
            exifInterface = new ExifInterface(fileInputStream.getFD());
            exifInterface2 = new ExifInterface(randomAccessFile.getFD());
        } catch (IOException e) {
            e = e;
        }
        try {
            for (String str : exifTags) {
                if (exifInterface.hasAttribute(str)) {
                    exifInterface2.setAttribute(str, exifInterface.getAttribute(str));
                }
            }
            return exifInterface2;
        } catch (IOException e2) {
            e = e2;
            exifInterface3 = exifInterface2;
            e.printStackTrace();
            return exifInterface3;
        }
    }

    public static ArrayList<ByteBuffer> getAppNMetadata(FileInputStream fileInputStream) throws IOException {
        Log.d(TAG, "getAppNMetadata E");
        ArrayList<ByteBuffer> arrayList = new ArrayList<>();
        byte[] bArr = new byte[1024];
        try {
            fileInputStream.getChannel().position(0L);
            fileInputStream.read(bArr, 0, 2);
            while (true) {
                if (fileInputStream.read(bArr, 0, 2) <= 0) {
                    break;
                }
                int[] iArr = {bArr[0] & 255, bArr[1] & 255};
                String str = TAG;
                Log.d(str, "marker: " + Integer.toHexString(iArr[0]) + Integer.toHexString(iArr[1]));
                if (iArr[0] != 255) {
                    throw new IllegalArgumentException("this is not valid markers");
                }
                int i = iArr[1];
                if (208 > i || 215 < i) {
                    fileInputStream.read(bArr, 0, 2);
                    int i2 = (255 & bArr[1]) | ((bArr[0] & 255) << 8);
                    int i3 = iArr[1];
                    if (226 <= i3 && 239 >= i3) {
                        Log.d(str, "add APP" + (iArr[1] & 15) + " meta(" + i2 + ')');
                        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i2 + 2);
                        byteBufferAllocateDirect.put((byte) iArr[0]);
                        byteBufferAllocateDirect.put((byte) iArr[1]);
                        byteBufferAllocateDirect.put(bArr, 0, 2);
                        fileInputStream.getChannel().read(byteBufferAllocateDirect);
                        byteBufferAllocateDirect.rewind();
                        arrayList.add(byteBufferAllocateDirect);
                    } else {
                        if (i3 == 218) {
                            Log.d(str, "EOS reached");
                            break;
                        }
                        fileInputStream.skip(i2 - 2);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAppNMetadata X");
        return arrayList;
    }

    public static void setAppNMetadata(ArrayList<ByteBuffer> arrayList, RandomAccessFile randomAccessFile) throws IOException {
        Log.d(TAG, "setICCProfile E");
        try {
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(((int) channel.size()) + arrayList.stream().mapToInt(new ToIntFunction() { // from class: com.samsung.android.sume.core.MetaDataUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((ByteBuffer) obj).limit();
                }
            }).sum());
            byteBufferAllocateDirect.put((byte) -1);
            byteBufferAllocateDirect.put((byte) -40);
            Iterator<ByteBuffer> it = arrayList.iterator();
            while (it.hasNext()) {
                byteBufferAllocateDirect.put(it.next());
            }
            channel.position(2L);
            channel.read(byteBufferAllocateDirect);
            channel.position(0L);
            byteBufferAllocateDirect.rewind();
            channel.write(byteBufferAllocateDirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "setICCProfile X");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v6 */
    public static boolean copyMetadata(String str, String str2) throws Throwable {
        FileInputStream fileInputStream;
        String str3 = TAG;
        Log.d(str3, "copyMetadata: src=" + str + ", dst=" + str2);
        Pattern patternCompile = Pattern.compile(".(jpg|jpeg)$");
        RandomAccessFile lowerCase = str.toLowerCase(Locale.getDefault());
        if (!patternCompile.matcher(lowerCase).find()) {
            Log.w(str3, "not supported file format: " + str);
            return false;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream(str);
                    try {
                        lowerCase = new RandomAccessFile(str2, "rw");
                    } catch (FileNotFoundException e) {
                        e = e;
                        lowerCase = 0;
                    } catch (IllegalArgumentException unused) {
                        lowerCase = 0;
                    } catch (Throwable th) {
                        th = th;
                        lowerCase = 0;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                lowerCase = 0;
            } catch (IllegalArgumentException unused2) {
                lowerCase = 0;
            } catch (Throwable th3) {
                th = th3;
                lowerCase = 0;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            ArrayList<ByteBuffer> appNMetadata = getAppNMetadata(fileInputStream);
            if (!appNMetadata.isEmpty()) {
                setAppNMetadata(appNMetadata, lowerCase);
            }
            try {
                fileInputStream.close();
                lowerCase.close();
                return true;
            } catch (IOException e4) {
                e4.printStackTrace();
                return true;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            fileInputStream2 = fileInputStream;
            lowerCase = lowerCase;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (lowerCase != 0) {
                lowerCase.close();
            }
            return false;
        } catch (IllegalArgumentException unused3) {
            fileInputStream2 = fileInputStream;
            lowerCase = lowerCase;
            Log.w(TAG, "src has invalid meta: " + str);
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (lowerCase != 0) {
                lowerCase.close();
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                    throw th;
                }
            }
            if (lowerCase != 0) {
                lowerCase.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x00ca -> B:83:0x00f6). Please report as a decompilation issue!!! */
    public static boolean copyMetadataAndExif(String str, String str2, Consumer<ExifInterface> consumer) throws Throwable {
        RandomAccessFile randomAccessFile;
        FileInputStream fileInputStream;
        String str3 = TAG;
        Log.d(str3, "copyMetadataAndExif: src=" + str + ", dst=" + str2);
        if (!Pattern.compile(".(jpg|jpeg)$").matcher(str.toLowerCase(Locale.getDefault())).find()) {
            Log.w(str3, "not supported file format: " + str);
            return false;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream(str);
                    try {
                        randomAccessFile = new RandomAccessFile(str2, "rw");
                    } catch (IOException e) {
                        e = e;
                        randomAccessFile = null;
                    } catch (IllegalArgumentException unused) {
                        randomAccessFile = null;
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e2) {
                e = e2;
                randomAccessFile = null;
            } catch (IllegalArgumentException unused2) {
                randomAccessFile = null;
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            ArrayList<ByteBuffer> appNMetadata = getAppNMetadata(fileInputStream);
            if (!appNMetadata.isEmpty()) {
                setAppNMetadata(appNMetadata, randomAccessFile);
            }
            ExifInterface exifInterfaceCopyExif = copyExif(fileInputStream, randomAccessFile);
            Log.d(str3, "exif: " + exifInterfaceCopyExif);
            if (consumer != null) {
                consumer.accept(exifInterfaceCopyExif);
            }
            exifInterfaceCopyExif.saveAttributes();
            try {
                fileInputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            try {
                randomAccessFile.close();
                return true;
            } catch (IOException e5) {
                e5.printStackTrace();
                return true;
            }
        } catch (IOException e6) {
            e = e6;
            fileInputStream2 = fileInputStream;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return false;
        } catch (IllegalArgumentException unused3) {
            fileInputStream2 = fileInputStream;
            Log.w(TAG, "src has invalid meta: " + str);
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            if (randomAccessFile == null) {
                throw th;
            }
            try {
                randomAccessFile.close();
                throw th;
            } catch (IOException e10) {
                e10.printStackTrace();
                throw th;
            }
        }
    }
}
