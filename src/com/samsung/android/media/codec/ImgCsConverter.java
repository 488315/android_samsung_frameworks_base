package com.samsung.android.media.codec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.media.ExifInterface;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes6.dex */
public class ImgCsConverter {
    private static final String TAG = "ImgCsConverter";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    public static String[] AllExifTags = {ExifInterface.TAG_APERTURE_VALUE, ExifInterface.TAG_ARTIST, ExifInterface.TAG_BITS_PER_SAMPLE, ExifInterface.TAG_BRIGHTNESS_VALUE, ExifInterface.TAG_CFA_PATTERN, ExifInterface.TAG_COLOR_SPACE, ExifInterface.TAG_COMPONENTS_CONFIGURATION, ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, ExifInterface.TAG_COMPRESSION, ExifInterface.TAG_CONTRAST, ExifInterface.TAG_COPYRIGHT, ExifInterface.TAG_CUSTOM_RENDERED, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_DATETIME_ORIGINAL, ExifInterface.TAG_DEFAULT_CROP_SIZE, ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, ExifInterface.TAG_DIGITAL_ZOOM_RATIO, ExifInterface.TAG_DNG_VERSION, ExifInterface.TAG_EXIF_VERSION, ExifInterface.TAG_EXPOSURE_BIAS_VALUE, ExifInterface.TAG_EXPOSURE_INDEX, ExifInterface.TAG_EXPOSURE_MODE, ExifInterface.TAG_EXPOSURE_PROGRAM, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FILE_SOURCE, ExifInterface.TAG_FLASH, ExifInterface.TAG_FLASHPIX_VERSION, ExifInterface.TAG_FLASH_ENERGY, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, "FNumber", ExifInterface.TAG_GAIN_CONTROL, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_AREA_INFORMATION, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_DEST_BEARING, ExifInterface.TAG_GPS_DEST_BEARING_REF, ExifInterface.TAG_GPS_DEST_DISTANCE, ExifInterface.TAG_GPS_DEST_DISTANCE_REF, ExifInterface.TAG_GPS_DEST_LATITUDE, ExifInterface.TAG_GPS_DEST_LATITUDE_REF, ExifInterface.TAG_GPS_DEST_LONGITUDE, ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, ExifInterface.TAG_GPS_DIFFERENTIAL, ExifInterface.TAG_GPS_DOP, ExifInterface.TAG_GPS_IMG_DIRECTION, ExifInterface.TAG_GPS_IMG_DIRECTION_REF, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_MAP_DATUM, ExifInterface.TAG_GPS_MEASURE_MODE, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_SATELLITES, ExifInterface.TAG_GPS_SPEED, ExifInterface.TAG_GPS_SPEED_REF, ExifInterface.TAG_GPS_STATUS, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_GPS_TRACK, ExifInterface.TAG_GPS_TRACK_REF, ExifInterface.TAG_GPS_VERSION_ID, ExifInterface.TAG_IMAGE_DESCRIPTION, ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.TAG_IMAGE_UNIQUE_ID, ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.TAG_INTEROPERABILITY_INDEX, "ISOSpeedRatings", ExifInterface.TAG_LIGHT_SOURCE, ExifInterface.TAG_MAKE, ExifInterface.TAG_MAKER_NOTE, ExifInterface.TAG_MAX_APERTURE_VALUE, ExifInterface.TAG_METERING_MODE, ExifInterface.TAG_MODEL, ExifInterface.TAG_NEW_SUBFILE_TYPE, ExifInterface.TAG_OECF, ExifInterface.TAG_ORF_ASPECT_FRAME, ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, ExifInterface.TAG_ORF_THUMBNAIL_IMAGE, ExifInterface.TAG_ORIENTATION, ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, ExifInterface.TAG_PIXEL_X_DIMENSION, ExifInterface.TAG_PIXEL_Y_DIMENSION, ExifInterface.TAG_PLANAR_CONFIGURATION, ExifInterface.TAG_PRIMARY_CHROMATICITIES, ExifInterface.TAG_REFERENCE_BLACK_WHITE, ExifInterface.TAG_RELATED_SOUND_FILE, ExifInterface.TAG_RESOLUTION_UNIT, ExifInterface.TAG_ROWS_PER_STRIP, ExifInterface.TAG_RW2_ISO, ExifInterface.TAG_RW2_JPG_FROM_RAW, ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, ExifInterface.TAG_SAMPLES_PER_PIXEL, ExifInterface.TAG_SATURATION, ExifInterface.TAG_SCENE_CAPTURE_TYPE, ExifInterface.TAG_SCENE_TYPE, ExifInterface.TAG_SENSING_METHOD, ExifInterface.TAG_SHARPNESS, ExifInterface.TAG_SHUTTER_SPEED_VALUE, ExifInterface.TAG_SOFTWARE, ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, ExifInterface.TAG_SPECTRAL_SENSITIVITY, ExifInterface.TAG_STRIP_BYTE_COUNTS, ExifInterface.TAG_STRIP_OFFSETS, ExifInterface.TAG_SUBFILE_TYPE, ExifInterface.TAG_SUBJECT_AREA, ExifInterface.TAG_SUBJECT_DISTANCE, ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, ExifInterface.TAG_SUBJECT_LOCATION, ExifInterface.TAG_SUBSEC_TIME, "SubSecTimeDigitized", "SubSecTimeDigitized", "SubSecTimeOriginal", "SubSecTimeOriginal", ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, ExifInterface.TAG_TRANSFER_FUNCTION, ExifInterface.TAG_USER_COMMENT, ExifInterface.TAG_WHITE_BALANCE, ExifInterface.TAG_WHITE_POINT, ExifInterface.TAG_X_RESOLUTION, ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, ExifInterface.TAG_Y_CB_CR_POSITIONING, ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, ExifInterface.TAG_Y_RESOLUTION, ExifInterface.TAG_OFFSET_TIME_ORIGINAL, ExifInterface.TAG_OFFSET_TIME, ExifInterface.TAG_OFFSET_TIME_DIGITIZED};

    public static boolean convertToSRGB(String str, String str2) {
        Log.d(TAG, "convertToSRGB");
        return convert(str, str2, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    private static boolean convert(String str, String str2, ColorSpace colorSpace) {
        FileOutputStream fileOutputStream;
        Log.d(TAG, "convert");
        if (str == null || str.length() <= 0) {
            throw new NullPointerException("inputFilePath cannot be null");
        }
        if (str2 == null || str2.length() <= 0) {
            throw new NullPointerException("outputFilePath cannot be null");
        }
        Bitmap bitmap = null;
        try {
            try {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    options.inPreferredColorSpace = colorSpace;
                    Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
                    try {
                        if (decodeFile == null) {
                            Log.e(TAG, "decode fail");
                            if (decodeFile != null) {
                                decodeFile.recycle();
                            }
                            return false;
                        }
                        Log.i(TAG, "dec x");
                        fileOutputStream = new FileOutputStream(str2);
                        try {
                            decodeFile.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                            Log.i(TAG, "enc x");
                            ExifInterface exifInterface = new ExifInterface(str);
                            ExifInterface exifInterface2 = new ExifInterface(str2);
                            int i = 0;
                            while (true) {
                                String[] strArr = AllExifTags;
                                if (i >= strArr.length) {
                                    break;
                                }
                                String attribute = exifInterface.getAttribute(strArr[i]);
                                if (attribute != null) {
                                    exifInterface2.setAttribute(AllExifTags[i], attribute);
                                }
                                i++;
                            }
                            String parent = new File(str2).getParent();
                            Log.i(TAG, " outDir : " + parent);
                            String property = System.getProperty("java.io.tmpdir");
                            Log.i(TAG, " tmpdir : " + property);
                            System.setProperty("java.io.tmpdir", parent);
                            exifInterface2.saveAttributes();
                            System.setProperty("java.io.tmpdir", property);
                            if (decodeFile != null) {
                                decodeFile.recycle();
                            }
                            try {
                                fileOutputStream.close();
                            } catch (RuntimeException e) {
                                throw e;
                            } catch (Exception unused) {
                            }
                            return true;
                        } catch (RuntimeException e2) {
                            e = e2;
                            bitmap = decodeFile;
                            Log.e(TAG, " RuntimeException ");
                            e.printStackTrace();
                            if (bitmap != null) {
                                bitmap.recycle();
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (RuntimeException e3) {
                                    throw e3;
                                }
                            }
                            return false;
                        } catch (Exception e4) {
                            e = e4;
                            bitmap = decodeFile;
                            Log.e(TAG, " Exception ");
                            e.printStackTrace();
                            if (bitmap != null) {
                                bitmap.recycle();
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (RuntimeException e5) {
                                    throw e5;
                                }
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bitmap = decodeFile;
                            if (bitmap != null) {
                                bitmap.recycle();
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (RuntimeException e6) {
                                    throw e6;
                                } catch (Exception unused2) {
                                }
                            }
                            throw th;
                        }
                    } catch (RuntimeException e7) {
                        e = e7;
                        fileOutputStream = null;
                    } catch (Exception e8) {
                        e = e8;
                        fileOutputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                    }
                } catch (RuntimeException e9) {
                    e = e9;
                    fileOutputStream = null;
                } catch (Exception e10) {
                    e = e10;
                    fileOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                }
            } catch (Exception unused3) {
                return false;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }
}
