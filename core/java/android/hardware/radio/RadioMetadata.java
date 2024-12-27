package android.hardware.radio;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.hidden_from_bootclasspath.android.hardware.radio.Flags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SystemApi
public final class RadioMetadata implements Parcelable {
    public static final Parcelable.Creator<RadioMetadata> CREATOR;
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE = new ArrayMap<>();
    public static final String METADATA_KEY_ALBUM = "android.hardware.radio.metadata.ALBUM";
    public static final String METADATA_KEY_ART = "android.hardware.radio.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.hardware.radio.metadata.ARTIST";
    public static final String METADATA_KEY_CLOCK = "android.hardware.radio.metadata.CLOCK";
    public static final String METADATA_KEY_COMMENT_ACTUAL_TEXT =
            "android.hardware.radio.metadata.COMMENT_ACTUAL_TEXT";
    public static final String METADATA_KEY_COMMENT_SHORT_DESCRIPTION =
            "android.hardware.radio.metadata.COMMENT_SHORT_DESCRIPTION";
    public static final String METADATA_KEY_COMMERCIAL =
            "android.hardware.radio.metadata.COMMERCIAL";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME =
            "android.hardware.radio.metadata.DAB_COMPONENT_NAME";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME_SHORT =
            "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME =
            "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT =
            "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT";
    public static final String METADATA_KEY_DAB_SERVICE_NAME =
            "android.hardware.radio.metadata.DAB_SERVICE_NAME";
    public static final String METADATA_KEY_DAB_SERVICE_NAME_SHORT =
            "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT";
    public static final String METADATA_KEY_GENRE = "android.hardware.radio.metadata.GENRE";
    public static final String METADATA_KEY_HD_STATION_NAME_LONG =
            "android.hardware.radio.metadata.HD_STATION_NAME_LONG";
    public static final String METADATA_KEY_HD_STATION_NAME_SHORT =
            "android.hardware.radio.metadata.HD_STATION_NAME_SHORT";
    public static final String METADATA_KEY_HD_SUBCHANNELS_AVAILABLE =
            "android.hardware.radio.metadata.HD_SUBCHANNELS_AVAILABLE";
    public static final String METADATA_KEY_ICON = "android.hardware.radio.metadata.ICON";
    public static final String METADATA_KEY_PROGRAM_NAME =
            "android.hardware.radio.metadata.PROGRAM_NAME";
    public static final String METADATA_KEY_RBDS_PTY = "android.hardware.radio.metadata.RBDS_PTY";
    public static final String METADATA_KEY_RDS_PI = "android.hardware.radio.metadata.RDS_PI";
    public static final String METADATA_KEY_RDS_PS = "android.hardware.radio.metadata.RDS_PS";
    public static final String METADATA_KEY_RDS_PTY = "android.hardware.radio.metadata.RDS_PTY";
    public static final String METADATA_KEY_RDS_RT = "android.hardware.radio.metadata.RDS_RT";
    public static final String METADATA_KEY_TITLE = "android.hardware.radio.metadata.TITLE";
    public static final String METADATA_KEY_UFIDS = "android.hardware.radio.metadata.UFIDS";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_CLOCK = 3;
    private static final int METADATA_TYPE_INT = 0;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final int METADATA_TYPE_TEXT_ARRAY = 4;
    private static final int NATIVE_KEY_ALBUM = 7;
    private static final int NATIVE_KEY_ART = 10;
    private static final int NATIVE_KEY_ARTIST = 6;
    private static final int NATIVE_KEY_CLOCK = 11;
    private static final int NATIVE_KEY_GENRE = 8;
    private static final int NATIVE_KEY_ICON = 9;
    private static final int NATIVE_KEY_INVALID = -1;
    private static final SparseArray<String> NATIVE_KEY_MAPPING;
    private static final int NATIVE_KEY_RBDS_PTY = 3;
    private static final int NATIVE_KEY_RDS_PI = 0;
    private static final int NATIVE_KEY_RDS_PS = 1;
    private static final int NATIVE_KEY_RDS_PTY = 2;
    private static final int NATIVE_KEY_RDS_RT = 4;
    private static final int NATIVE_KEY_TITLE = 5;
    private static final String TAG = "BroadcastRadio.metadata";
    private final Bundle mBundle;
    private Integer mHashCode;

    static {
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PI, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PS, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PTY, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RBDS_PTY, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_RT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ICON, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_CLOCK, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_PROGRAM_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMENT_SHORT_DESCRIPTION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMENT_ACTUAL_TEXT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMMERCIAL, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_UFIDS, 4);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_STATION_NAME_SHORT, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_STATION_NAME_LONG, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_HD_SUBCHANNELS_AVAILABLE, 0);
        NATIVE_KEY_MAPPING = new SparseArray<>();
        NATIVE_KEY_MAPPING.put(0, METADATA_KEY_RDS_PI);
        NATIVE_KEY_MAPPING.put(1, METADATA_KEY_RDS_PS);
        NATIVE_KEY_MAPPING.put(2, METADATA_KEY_RDS_PTY);
        NATIVE_KEY_MAPPING.put(3, METADATA_KEY_RBDS_PTY);
        NATIVE_KEY_MAPPING.put(4, METADATA_KEY_RDS_RT);
        NATIVE_KEY_MAPPING.put(5, METADATA_KEY_TITLE);
        NATIVE_KEY_MAPPING.put(6, METADATA_KEY_ARTIST);
        NATIVE_KEY_MAPPING.put(7, METADATA_KEY_ALBUM);
        NATIVE_KEY_MAPPING.put(8, METADATA_KEY_GENRE);
        NATIVE_KEY_MAPPING.put(9, METADATA_KEY_ICON);
        NATIVE_KEY_MAPPING.put(10, METADATA_KEY_ART);
        NATIVE_KEY_MAPPING.put(11, METADATA_KEY_CLOCK);
        CREATOR =
                new Parcelable.Creator<
                        RadioMetadata>() { // from class: android.hardware.radio.RadioMetadata.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public RadioMetadata createFromParcel(Parcel in) {
                        return new RadioMetadata(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public RadioMetadata[] newArray(int size) {
                        return new RadioMetadata[size];
                    }
                };
    }

    @SystemApi
    public static final class Clock implements Parcelable {
        public static final Parcelable.Creator<Clock> CREATOR =
                new Parcelable.Creator<
                        Clock>() { // from class: android.hardware.radio.RadioMetadata.Clock.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Clock createFromParcel(Parcel in) {
                        return new Clock(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Clock[] newArray(int size) {
                        return new Clock[size];
                    }
                };
        private final int mTimezoneOffsetMinutes;
        private final long mUtcEpochSeconds;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.mUtcEpochSeconds);
            out.writeInt(this.mTimezoneOffsetMinutes);
        }

        public Clock(long utcEpochSeconds, int timezoneOffsetMinutes) {
            this.mUtcEpochSeconds = utcEpochSeconds;
            this.mTimezoneOffsetMinutes = timezoneOffsetMinutes;
        }

        private Clock(Parcel in) {
            this.mUtcEpochSeconds = in.readLong();
            this.mTimezoneOffsetMinutes = in.readInt();
        }

        public long getUtcEpochSeconds() {
            return this.mUtcEpochSeconds;
        }

        public int getTimezoneOffsetMinutes() {
            return this.mTimezoneOffsetMinutes;
        }
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            List<String> keys = new ArrayList<>(this.mBundle.keySet());
            keys.sort(null);
            Object[] objs = new Object[keys.size() * 2];
            for (int i = 0; i < keys.size(); i++) {
                objs[i * 2] = keys.get(i);
                objs[(i * 2) + 1] = this.mBundle.get(keys.get(i));
            }
            int i2 = Arrays.hashCode(objs);
            this.mHashCode = Integer.valueOf(i2);
        }
        return this.mHashCode.intValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RadioMetadata)) {
            return false;
        }
        Bundle otherBundle = ((RadioMetadata) obj).mBundle;
        if (!this.mBundle.keySet().equals(otherBundle.keySet())) {
            return false;
        }
        for (String key : this.mBundle.keySet()) {
            if (Flags.hdRadioImproved() && Objects.equals(METADATA_KEYS_TYPE.get(key), 4)) {
                if (!Arrays.equals(
                        this.mBundle.getStringArray(key), otherBundle.getStringArray(key))) {
                    return false;
                }
            } else if (!Objects.equals(this.mBundle.get(key), otherBundle.get(key))) {
                return false;
            }
        }
        return true;
    }

    RadioMetadata() {
        this.mBundle = new Bundle();
    }

    private RadioMetadata(Bundle bundle) {
        this.mBundle = new Bundle(bundle);
    }

    private RadioMetadata(Parcel in) {
        this.mBundle = in.readBundle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RadioMetadata[");
        boolean first = true;
        for (String key : this.mBundle.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            String keyDisp = key;
            if (key.startsWith("android.hardware.radio.metadata")) {
                keyDisp = key.substring("android.hardware.radio.metadata".length());
            }
            sb.append(keyDisp);
            sb.append('=');
            if (Flags.hdRadioImproved() && Objects.equals(METADATA_KEYS_TYPE.get(key), 4)) {
                String[] stringArrayValue = this.mBundle.getStringArray(key);
                sb.append('[');
                for (int i = 0; i < stringArrayValue.length; i++) {
                    if (i != 0) {
                        sb.append(',');
                    }
                    sb.append(stringArrayValue[i]);
                }
                sb.append(']');
            } else {
                sb.append(this.mBundle.get(key));
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public boolean containsKey(String key) {
        return this.mBundle.containsKey(key);
    }

    public String getString(String key) {
        return this.mBundle.getString(key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putInt(Bundle bundle, String key, int value) {
        int type = METADATA_KEYS_TYPE.getOrDefault(key, -1).intValue();
        if (type != 0 && type != 2) {
            throw new IllegalArgumentException("The " + key + " key cannot be used to put an int");
        }
        bundle.putInt(key, value);
    }

    public int getInt(String key) {
        return this.mBundle.getInt(key, 0);
    }

    @Deprecated
    public Bitmap getBitmap(String key) {
        try {
            Bitmap bmp = (Bitmap) this.mBundle.getParcelable(key, Bitmap.class);
            return bmp;
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
            return null;
        }
    }

    public int getBitmapId(String key) {
        Objects.requireNonNull(key, "Metadata key can not be null");
        if (!METADATA_KEY_ICON.equals(key) && !METADATA_KEY_ART.equals(key)) {
            throw new IllegalArgumentException("Failed to retrieve key " + key + " as bitmap key");
        }
        return getInt(key);
    }

    public Clock getClock(String key) {
        try {
            Clock clock = (Clock) this.mBundle.getParcelable(key, Clock.class);
            return clock;
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Clock.", e);
            return null;
        }
    }

    public String[] getStringArray(String key) {
        Objects.requireNonNull(key, "Metadata key can not be null");
        if (!Objects.equals(METADATA_KEYS_TYPE.get(key), 4)) {
            throw new IllegalArgumentException(
                    "Failed to retrieve key " + key + " as string array");
        }
        String[] stringArrayValue = this.mBundle.getStringArray(key);
        if (stringArrayValue == null) {
            throw new IllegalArgumentException("Key " + key + " is not found in metadata");
        }
        return stringArrayValue;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mBundle);
    }

    public int size() {
        return this.mBundle.size();
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public static String getKeyFromNativeKey(int nativeKey) {
        return NATIVE_KEY_MAPPING.get(nativeKey, null);
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(RadioMetadata source) {
            this.mBundle = new Bundle(source.mBundle);
        }

        public Builder(RadioMetadata source, int maxBitmapSize) {
            this(source);
            for (String key : this.mBundle.keySet()) {
                Object value = this.mBundle.get(key);
                if (value != null && (value instanceof Bitmap)) {
                    Bitmap bmp = (Bitmap) value;
                    if (bmp.getHeight() > maxBitmapSize || bmp.getWidth() > maxBitmapSize) {
                        putBitmap(key, scaleBitmap(bmp, maxBitmapSize));
                    }
                }
            }
        }

        public Builder putString(String key, String value) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(key)
                    || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(key)).intValue() != 1) {
                throw new IllegalArgumentException(
                        "The " + key + " key cannot be used to put a String");
            }
            this.mBundle.putString(key, value);
            return this;
        }

        public Builder putInt(String key, int value) {
            RadioMetadata.putInt(this.mBundle, key, value);
            return this;
        }

        public Builder putBitmap(String key, Bitmap value) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(key)
                    || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(key)).intValue() != 2) {
                throw new IllegalArgumentException(
                        "The " + key + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(key, value);
            return this;
        }

        public Builder putClock(String key, long utcSecondsSinceEpoch, int timezoneOffsetMinutes) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(key)
                    || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(key)).intValue() != 3) {
                throw new IllegalArgumentException(
                        "The " + key + " key cannot be used to put a RadioMetadata.Clock.");
            }
            this.mBundle.putParcelable(key, new Clock(utcSecondsSinceEpoch, timezoneOffsetMinutes));
            return this;
        }

        public Builder putStringArray(String key, String[] value) {
            Objects.requireNonNull(key, "Key can not be null");
            Objects.requireNonNull(value, "Value can not be null");
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(key)
                    || !Objects.equals(RadioMetadata.METADATA_KEYS_TYPE.get(key), 4)) {
                throw new IllegalArgumentException(
                        "The " + key + " key cannot be used to put a RadioMetadata String Array.");
            }
            this.mBundle.putStringArray(key, value);
            return this;
        }

        public RadioMetadata build() {
            return new RadioMetadata(this.mBundle);
        }

        private Bitmap scaleBitmap(Bitmap bmp, int maxSize) {
            float maxSizeF = maxSize;
            float widthScale = maxSizeF / bmp.getWidth();
            float heightScale = maxSizeF / bmp.getHeight();
            float scale = Math.min(widthScale, heightScale);
            int height = (int) (bmp.getHeight() * scale);
            int width = (int) (bmp.getWidth() * scale);
            return Bitmap.createScaledBitmap(bmp, width, height, true);
        }
    }

    int putIntFromNative(int nativeKey, int value) {
        String key = getKeyFromNativeKey(nativeKey);
        try {
            putInt(this.mBundle, key, value);
            this.mHashCode = null;
            return 0;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    int putStringFromNative(int nativeKey, String value) {
        String key = getKeyFromNativeKey(nativeKey);
        if (!METADATA_KEYS_TYPE.containsKey(key) || METADATA_KEYS_TYPE.get(key).intValue() != 1) {
            return -1;
        }
        this.mBundle.putString(key, value);
        this.mHashCode = null;
        return 0;
    }

    int putBitmapFromNative(int nativeKey, byte[] value) {
        String key = getKeyFromNativeKey(nativeKey);
        if (!METADATA_KEYS_TYPE.containsKey(key) || METADATA_KEYS_TYPE.get(key).intValue() != 2) {
            return -1;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(value, 0, value.length);
            if (bmp != null) {
                this.mBundle.putParcelable(key, bmp);
                this.mHashCode = null;
                return 0;
            }
        } catch (Exception e) {
        }
        return -1;
    }

    int putClockFromNative(int nativeKey, long utcEpochSeconds, int timezoneOffsetInMinutes) {
        String key = getKeyFromNativeKey(nativeKey);
        if (!METADATA_KEYS_TYPE.containsKey(key) || METADATA_KEYS_TYPE.get(key).intValue() != 3) {
            return -1;
        }
        this.mBundle.putParcelable(key, new Clock(utcEpochSeconds, timezoneOffsetInMinutes));
        this.mHashCode = null;
        return 0;
    }
}
