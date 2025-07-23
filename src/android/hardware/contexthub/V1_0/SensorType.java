package android.hardware.contexthub.V1_0;

import com.samsung.android.media.AudioTag;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SensorType {
    public static final int ACCELEROMETER = 1;
    public static final int AMBIENT_LIGHT_SENSOR = 6;
    public static final int AUDIO = 768;
    public static final int BAROMETER = 4;
    public static final int BLE = 1280;
    public static final int CAMERA = 1024;
    public static final int GPS = 256;
    public static final int GYROSCOPE = 2;
    public static final int INSTANT_MOTION_DETECT = 8;
    public static final int MAGNETOMETER = 3;
    public static final int PRIVATE_SENSOR_BASE = 65536;
    public static final int PROXIMITY_SENSOR = 5;
    public static final int RESERVED = 0;
    public static final int STATIONARY_DETECT = 7;
    public static final int WIFI = 512;
    public static final int WWAN = 1536;

    public static final String toString(int i) {
        if (i == 0) {
            return "RESERVED";
        }
        if (i == 1) {
            return "ACCELEROMETER";
        }
        if (i == 2) {
            return "GYROSCOPE";
        }
        if (i == 3) {
            return "MAGNETOMETER";
        }
        if (i == 4) {
            return "BAROMETER";
        }
        if (i == 5) {
            return "PROXIMITY_SENSOR";
        }
        if (i == 6) {
            return "AMBIENT_LIGHT_SENSOR";
        }
        if (i == 7) {
            return "STATIONARY_DETECT";
        }
        if (i == 8) {
            return "INSTANT_MOTION_DETECT";
        }
        if (i == 256) {
            return "GPS";
        }
        if (i == 512) {
            return "WIFI";
        }
        if (i == 768) {
            return "AUDIO";
        }
        if (i == 1024) {
            return AudioTag.TAG_CAMERA;
        }
        if (i == 1280) {
            return "BLE";
        }
        if (i == 1536) {
            return "WWAN";
        }
        if (i == 65536) {
            return "PRIVATE_SENSOR_BASE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RESERVED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ACCELEROMETER");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("GYROSCOPE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("MAGNETOMETER");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("BAROMETER");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("PROXIMITY_SENSOR");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("AMBIENT_LIGHT_SENSOR");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("STATIONARY_DETECT");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("INSTANT_MOTION_DETECT");
            i2 |= 8;
        }
        if ((i & 256) == 256) {
            arrayList.add("GPS");
            i2 |= 256;
        }
        if ((i & 512) == 512) {
            arrayList.add("WIFI");
            i2 |= 512;
        }
        if ((i & 768) == 768) {
            arrayList.add("AUDIO");
            i2 |= 768;
        }
        if ((i & 1024) == 1024) {
            arrayList.add(AudioTag.TAG_CAMERA);
            i2 |= 1024;
        }
        if ((i & 1280) == 1280) {
            arrayList.add("BLE");
            i2 |= 1280;
        }
        if ((i & 1536) == 1536) {
            arrayList.add("WWAN");
            i2 |= 1536;
        }
        if ((i & 65536) == 65536) {
            arrayList.add("PRIVATE_SENSOR_BASE");
            i2 |= 65536;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
