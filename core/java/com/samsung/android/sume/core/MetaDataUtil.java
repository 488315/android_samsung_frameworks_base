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

/* loaded from: classes4.dex */
public final class MetaDataUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int JPEG_LENGTH_SIZE = 2;
    private static final int JPEG_MARKER_SIZE = 2;
    private static final String TAG = MetaDataUtil.class.getSimpleName();
    private static final String[] exifTags = {"FNumber", ExifInterface.TAG_APERTURE_VALUE, ExifInterface.TAG_ARTIST, ExifInterface.TAG_BITS_PER_SAMPLE, ExifInterface.TAG_BRIGHTNESS_VALUE, ExifInterface.TAG_CFA_PATTERN, ExifInterface.TAG_COLOR_SPACE, ExifInterface.TAG_COMPONENTS_CONFIGURATION, ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, ExifInterface.TAG_COMPRESSION, ExifInterface.TAG_CONTRAST, ExifInterface.TAG_COPYRIGHT, ExifInterface.TAG_CUSTOM_RENDERED, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_DATETIME_ORIGINAL, ExifInterface.TAG_DEFAULT_CROP_SIZE, ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, ExifInterface.TAG_DIGITAL_ZOOM_RATIO, ExifInterface.TAG_DNG_VERSION, ExifInterface.TAG_EXIF_VERSION, ExifInterface.TAG_EXPOSURE_BIAS_VALUE, ExifInterface.TAG_EXPOSURE_INDEX, ExifInterface.TAG_EXPOSURE_MODE, ExifInterface.TAG_EXPOSURE_PROGRAM, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FILE_SOURCE, ExifInterface.TAG_FLASH, ExifInterface.TAG_FLASHPIX_VERSION, ExifInterface.TAG_FLASH_ENERGY, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, "FNumber", ExifInterface.TAG_GAIN_CONTROL, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_AREA_INFORMATION, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_DEST_BEARING, ExifInterface.TAG_GPS_DEST_BEARING_REF, ExifInterface.TAG_GPS_DEST_DISTANCE, ExifInterface.TAG_GPS_DEST_DISTANCE_REF, ExifInterface.TAG_GPS_DEST_LATITUDE, ExifInterface.TAG_GPS_DEST_LATITUDE_REF, ExifInterface.TAG_GPS_DEST_LONGITUDE, ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, ExifInterface.TAG_GPS_DIFFERENTIAL, ExifInterface.TAG_GPS_DOP, ExifInterface.TAG_GPS_IMG_DIRECTION, ExifInterface.TAG_GPS_IMG_DIRECTION_REF, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_MAP_DATUM, ExifInterface.TAG_GPS_MEASURE_MODE, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_SATELLITES, ExifInterface.TAG_GPS_SPEED, ExifInterface.TAG_GPS_SPEED_REF, ExifInterface.TAG_GPS_STATUS, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_GPS_TRACK, ExifInterface.TAG_GPS_TRACK_REF, ExifInterface.TAG_GPS_VERSION_ID, ExifInterface.TAG_IMAGE_DESCRIPTION, ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.TAG_IMAGE_UNIQUE_ID, ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.TAG_INTEROPERABILITY_INDEX, "ISOSpeedRatings", "ISOSpeedRatings", ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, ExifInterface.TAG_LIGHT_SOURCE, ExifInterface.TAG_MAKE, ExifInterface.TAG_MAKER_NOTE, ExifInterface.TAG_MAX_APERTURE_VALUE, ExifInterface.TAG_METERING_MODE, ExifInterface.TAG_MODEL, ExifInterface.TAG_NEW_SUBFILE_TYPE, ExifInterface.TAG_OECF, ExifInterface.TAG_ORF_ASPECT_FRAME, ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, ExifInterface.TAG_ORF_THUMBNAIL_IMAGE, ExifInterface.TAG_ORIENTATION, ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, ExifInterface.TAG_PIXEL_X_DIMENSION, ExifInterface.TAG_PIXEL_Y_DIMENSION, ExifInterface.TAG_PLANAR_CONFIGURATION, ExifInterface.TAG_PRIMARY_CHROMATICITIES, ExifInterface.TAG_REFERENCE_BLACK_WHITE, ExifInterface.TAG_RELATED_SOUND_FILE, ExifInterface.TAG_RESOLUTION_UNIT, ExifInterface.TAG_ROWS_PER_STRIP, ExifInterface.TAG_RW2_ISO, ExifInterface.TAG_RW2_JPG_FROM_RAW, ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, ExifInterface.TAG_SAMPLES_PER_PIXEL, ExifInterface.TAG_SATURATION, ExifInterface.TAG_SCENE_CAPTURE_TYPE, ExifInterface.TAG_SCENE_TYPE, ExifInterface.TAG_SENSING_METHOD, ExifInterface.TAG_SHARPNESS, ExifInterface.TAG_SHUTTER_SPEED_VALUE, ExifInterface.TAG_SOFTWARE, ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, ExifInterface.TAG_SPECTRAL_SENSITIVITY, ExifInterface.TAG_STRIP_BYTE_COUNTS, ExifInterface.TAG_STRIP_OFFSETS, ExifInterface.TAG_SUBFILE_TYPE, ExifInterface.TAG_SUBJECT_AREA, ExifInterface.TAG_SUBJECT_DISTANCE, ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, ExifInterface.TAG_SUBJECT_LOCATION, ExifInterface.TAG_SUBSEC_TIME, "SubSecTimeDigitized", "SubSecTimeDigitized", "SubSecTimeOriginal", "SubSecTimeOriginal", ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, ExifInterface.TAG_TRANSFER_FUNCTION, ExifInterface.TAG_USER_COMMENT, ExifInterface.TAG_WHITE_BALANCE, ExifInterface.TAG_WHITE_POINT, ExifInterface.TAG_X_RESOLUTION, ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, ExifInterface.TAG_Y_CB_CR_POSITIONING, ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, ExifInterface.TAG_Y_RESOLUTION, ExifInterface.TAG_OFFSET_TIME_ORIGINAL, ExifInterface.TAG_OFFSET_TIME, ExifInterface.TAG_OFFSET_TIME_DIGITIZED};

    public static String[] getExifTags() {
        return exifTags;
    }

    public static ExifInterface copyExif(FileInputStream ifs, RandomAccessFile ofs) {
        Log.m94d(TAG, "in: " + ifs + ", out: " + ofs);
        ExifInterface dst = null;
        try {
            ifs.getChannel().position(0L);
            ofs.getChannel().position(0L);
            ExifInterface src = new ExifInterface(ifs.getFD());
            dst = new ExifInterface(ofs.getFD());
            for (String tag : exifTags) {
                if (src.hasAttribute(tag)) {
                    dst.setAttribute(tag, src.getAttribute(tag));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dst;
    }

    public static ArrayList<ByteBuffer> getAppNMetadata(FileInputStream ifs) {
        Log.m94d(TAG, "getAppNMetadata E");
        ArrayList<ByteBuffer> data = new ArrayList<>();
        byte[] buffer = new byte[1024];
        try {
            ifs.getChannel().position(0L);
            ifs.read(buffer, 0, 2);
            while (true) {
                if (ifs.read(buffer, 0, 2) <= 0) {
                    break;
                }
                int[] markers = {buffer[0] & 255, buffer[1] & 255};
                String str = TAG;
                Log.m94d(str, "marker: " + Integer.toHexString(markers[0]) + Integer.toHexString(markers[1]));
                if (markers[0] != 255) {
                    throw new IllegalArgumentException("this is not valid markers");
                }
                if (208 > markers[1] || 215 < markers[1]) {
                    ifs.read(buffer, 0, 2);
                    int length = (255 & buffer[1]) | ((buffer[0] & 255) << 8);
                    if (226 <= markers[1] && 239 >= markers[1]) {
                        Log.m94d(str, "add APP" + (markers[1] & 15) + " meta(" + length + ')');
                        ByteBuffer meta = ByteBuffer.allocateDirect(length + 2);
                        meta.put((byte) markers[0]);
                        meta.put((byte) markers[1]);
                        meta.put(buffer, 0, 2);
                        ifs.getChannel().read(meta);
                        meta.rewind();
                        data.add(meta);
                    } else {
                        if (markers[1] == 218) {
                            Log.m94d(str, "EOS reached");
                            break;
                        }
                        ifs.skip(length - 2);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.m94d(TAG, "getAppNMetadata X");
        return data;
    }

    public static void setAppNMetadata(ArrayList<ByteBuffer> meta, RandomAccessFile ofs) {
        Log.m94d(TAG, "setICCProfile E");
        try {
            FileChannel channel = ofs.getChannel();
            int totalMetaSize = meta.stream().mapToInt(new ToIntFunction() { // from class: com.samsung.android.sume.core.MetaDataUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((ByteBuffer) obj).limit();
                }
            }).sum();
            ByteBuffer buffer = ByteBuffer.allocateDirect(((int) channel.size()) + totalMetaSize);
            buffer.put((byte) -1);
            buffer.put((byte) -40);
            Iterator<ByteBuffer> it = meta.iterator();
            while (it.hasNext()) {
                ByteBuffer m = it.next();
                buffer.put(m);
            }
            channel.position(2L);
            channel.read(buffer);
            channel.position(0L);
            buffer.rewind();
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.m94d(TAG, "setICCProfile X");
    }

    public static boolean copyMetadata(String src, String dst) {
        String str = TAG;
        Log.m94d(str, "copyMetadata: src=" + src + ", dst=" + dst);
        if (!Pattern.compile(".(jpg|jpeg)$").matcher(src.toLowerCase(Locale.getDefault())).find()) {
            Log.m102w(str, "not supported file format: " + src);
            return false;
        }
        FileInputStream ifs = null;
        RandomAccessFile ofs = null;
        try {
            try {
                try {
                    try {
                        ifs = new FileInputStream(src);
                        ofs = new RandomAccessFile(dst, "rw");
                        ArrayList<ByteBuffer> meta = getAppNMetadata(ifs);
                        if (!meta.isEmpty()) {
                            setAppNMetadata(meta, ofs);
                        }
                        try {
                            ifs.close();
                            ofs.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return true;
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                    if (ifs != null) {
                        ifs.close();
                    }
                    if (ofs == null) {
                        return false;
                    }
                    ofs.close();
                    return false;
                }
            } catch (IllegalArgumentException e4) {
                Log.m102w(TAG, "src has invalid meta: " + src);
                if (ifs != null) {
                    ifs.close();
                }
                if (ofs == null) {
                    return false;
                }
                ofs.close();
                return false;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    ifs.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                    throw th;
                }
            }
            if (0 != 0) {
                ofs.close();
            }
            throw th;
        }
    }

    public static boolean copyMetadataAndExif(String src, String dst, Consumer<ExifInterface> exifHandler) {
        String str = TAG;
        Log.m94d(str, "copyMetadataAndExif: src=" + src + ", dst=" + dst);
        if (!Pattern.compile(".(jpg|jpeg)$").matcher(src.toLowerCase(Locale.getDefault())).find()) {
            Log.m102w(str, "not supported file format: " + src);
            return false;
        }
        FileInputStream ifs = null;
        RandomAccessFile ofs = null;
        try {
            try {
                try {
                    ifs = new FileInputStream(src);
                    ofs = new RandomAccessFile(dst, "rw");
                    ArrayList<ByteBuffer> meta = getAppNMetadata(ifs);
                    if (!meta.isEmpty()) {
                        setAppNMetadata(meta, ofs);
                    }
                    ExifInterface result = copyExif(ifs, ofs);
                    Log.m94d(str, "exif: " + result);
                    if (exifHandler != null) {
                        exifHandler.accept(result);
                    }
                    result.saveAttributes();
                    try {
                        ifs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ofs.close();
                        return true;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return true;
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return false;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                if (ifs != null) {
                    try {
                        ifs.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (ofs == null) {
                    return false;
                }
                ofs.close();
                return false;
            } catch (IllegalArgumentException e6) {
                Log.m102w(TAG, "src has invalid meta: " + src);
                if (ifs != null) {
                    try {
                        ifs.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (ofs == null) {
                    return false;
                }
                ofs.close();
                return false;
            }
        } finally {
        }
    }
}
