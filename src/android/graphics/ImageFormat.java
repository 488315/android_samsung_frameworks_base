package android.graphics;

import android.media.codec.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ImageFormat {
    public static final int DEPTH16 = 1144402265;
    public static final int DEPTH_JPEG = 1768253795;
    public static final int DEPTH_POINT_CLOUD = 257;
    public static final int FLEX_RGBA_8888 = 42;
    public static final int FLEX_RGB_888 = 41;
    public static final int HEIC = 1212500294;
    public static final int HEIC_ULTRAHDR = 4102;
    public static final int JPEG = 256;
    public static final int JPEG_R = 4101;
    public static final int NV16 = 16;
    public static final int NV21 = 17;
    public static final int PRIVATE = 34;
    public static final int RAW10 = 37;
    public static final int RAW12 = 38;
    public static final int RAW_DEPTH = 4098;
    public static final int RAW_DEPTH10 = 4099;
    public static final int RAW_PRIVATE = 36;
    public static final int RAW_SENSOR = 32;
    public static final int RGB_565 = 4;
    public static final int UNKNOWN = 0;
    public static final int Y16 = 540422489;
    public static final int Y8 = 538982489;
    public static final int YCBCR_P010 = 54;
    public static final int YCBCR_P210 = 60;
    public static final int YUV_420_888 = 35;
    public static final int YUV_422_888 = 39;
    public static final int YUV_444_888 = 40;
    public static final int YUY2 = 20;
    public static final int YV12 = 842094169;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Format {
    }

    public static int getBitsPerPixel(int i) {
        if (i != 4 && i != 20 && i != 32) {
            if (i == 35) {
                return 12;
            }
            if (i == 54) {
                return 24;
            }
            if (i == 60) {
                return 32;
            }
            if (i == 538982489) {
                return 8;
            }
            if (i != 540422489) {
                if (i == 842094169) {
                    return 12;
                }
                if (i == 1144402265 || i == 16) {
                    return 16;
                }
                if (i == 17) {
                    return 12;
                }
                if (i != 4098) {
                    if (i == 4099) {
                        return 10;
                    }
                    switch (i) {
                        case 37:
                            return 10;
                        case 38:
                            return 12;
                        case 39:
                            return 16;
                        case 40:
                        case 41:
                            return 24;
                        case 42:
                            return 32;
                        default:
                            return -1;
                    }
                }
            }
            return 16;
        }
        return 16;
    }

    public static boolean isPublicFormat(int i) {
        if (i != 16 && i != 17 && i != 256 && i != 257 && i != 4098 && i != 4099) {
            switch (i) {
                default:
                    switch (i) {
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                            break;
                        default:
                            if (!Flags.p210FormatSupport() || i != 60) {
                                if (!com.android.internal.camera.flags.Flags.cameraHeifGainmap() || i != 4102) {
                                }
                            }
                            break;
                    }
                    return true;
                case 4:
                case 20:
                case 32:
                case 54:
                case 4101:
                case 538982489:
                case 842094169:
                case DEPTH16 /* 1144402265 */:
                case HEIC /* 1212500294 */:
                case DEPTH_JPEG /* 1768253795 */:
                    return true;
            }
        }
        return true;
    }
}
