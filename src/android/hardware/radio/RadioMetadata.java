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
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes2.dex */
public final class RadioMetadata implements Parcelable {
    public static final Parcelable.Creator<RadioMetadata> CREATOR;
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_ALBUM = "android.hardware.radio.metadata.ALBUM";
    public static final String METADATA_KEY_ART = "android.hardware.radio.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.hardware.radio.metadata.ARTIST";
    public static final String METADATA_KEY_CLOCK = "android.hardware.radio.metadata.CLOCK";
    public static final String METADATA_KEY_COMMENT_ACTUAL_TEXT = "android.hardware.radio.metadata.COMMENT_ACTUAL_TEXT";
    public static final String METADATA_KEY_COMMENT_SHORT_DESCRIPTION = "android.hardware.radio.metadata.COMMENT_SHORT_DESCRIPTION";
    public static final String METADATA_KEY_COMMERCIAL = "android.hardware.radio.metadata.COMMERCIAL";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME = "android.hardware.radio.metadata.DAB_COMPONENT_NAME";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME_SHORT = "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT";
    public static final String METADATA_KEY_DAB_SERVICE_NAME = "android.hardware.radio.metadata.DAB_SERVICE_NAME";
    public static final String METADATA_KEY_DAB_SERVICE_NAME_SHORT = "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT";
    public static final String METADATA_KEY_GENRE = "android.hardware.radio.metadata.GENRE";
    public static final String METADATA_KEY_HD_STATION_NAME_LONG = "android.hardware.radio.metadata.HD_STATION_NAME_LONG";
    public static final String METADATA_KEY_HD_STATION_NAME_SHORT = "android.hardware.radio.metadata.HD_STATION_NAME_SHORT";
    public static final String METADATA_KEY_HD_SUBCHANNELS_AVAILABLE = "android.hardware.radio.metadata.HD_SUBCHANNELS_AVAILABLE";
    public static final String METADATA_KEY_ICON = "android.hardware.radio.metadata.ICON";
    public static final String METADATA_KEY_PROGRAM_NAME = "android.hardware.radio.metadata.PROGRAM_NAME";
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
        METADATA_KEYS_TYPE = arrayMap;
        arrayMap.put(METADATA_KEY_RDS_PI, 0);
        arrayMap.put(METADATA_KEY_RDS_PS, 1);
        arrayMap.put(METADATA_KEY_RDS_PTY, 0);
        arrayMap.put(METADATA_KEY_RBDS_PTY, 0);
        arrayMap.put(METADATA_KEY_RDS_RT, 1);
        arrayMap.put(METADATA_KEY_TITLE, 1);
        arrayMap.put(METADATA_KEY_ARTIST, 1);
        arrayMap.put(METADATA_KEY_ALBUM, 1);
        arrayMap.put(METADATA_KEY_GENRE, 1);
        arrayMap.put(METADATA_KEY_ICON, 2);
        arrayMap.put(METADATA_KEY_ART, 2);
        arrayMap.put(METADATA_KEY_CLOCK, 3);
        arrayMap.put(METADATA_KEY_PROGRAM_NAME, 1);
        arrayMap.put(METADATA_KEY_DAB_ENSEMBLE_NAME, 1);
        arrayMap.put(METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT, 1);
        arrayMap.put(METADATA_KEY_DAB_SERVICE_NAME, 1);
        arrayMap.put(METADATA_KEY_DAB_SERVICE_NAME_SHORT, 1);
        arrayMap.put(METADATA_KEY_DAB_COMPONENT_NAME, 1);
        arrayMap.put(METADATA_KEY_DAB_COMPONENT_NAME_SHORT, 1);
        arrayMap.put(METADATA_KEY_COMMENT_SHORT_DESCRIPTION, 1);
        arrayMap.put(METADATA_KEY_COMMENT_ACTUAL_TEXT, 1);
        arrayMap.put(METADATA_KEY_COMMERCIAL, 1);
        arrayMap.put(METADATA_KEY_UFIDS, 4);
        arrayMap.put(METADATA_KEY_HD_STATION_NAME_SHORT, 1);
        arrayMap.put(METADATA_KEY_HD_STATION_NAME_LONG, 1);
        arrayMap.put(METADATA_KEY_HD_SUBCHANNELS_AVAILABLE, 0);
        SparseArray<String> sparseArray = new SparseArray<>();
        NATIVE_KEY_MAPPING = sparseArray;
        sparseArray.put(0, METADATA_KEY_RDS_PI);
        sparseArray.put(1, METADATA_KEY_RDS_PS);
        sparseArray.put(2, METADATA_KEY_RDS_PTY);
        sparseArray.put(3, METADATA_KEY_RBDS_PTY);
        sparseArray.put(4, METADATA_KEY_RDS_RT);
        sparseArray.put(5, METADATA_KEY_TITLE);
        sparseArray.put(6, METADATA_KEY_ARTIST);
        sparseArray.put(7, METADATA_KEY_ALBUM);
        sparseArray.put(8, METADATA_KEY_GENRE);
        sparseArray.put(9, METADATA_KEY_ICON);
        sparseArray.put(10, METADATA_KEY_ART);
        sparseArray.put(11, METADATA_KEY_CLOCK);
        CREATOR = new Parcelable.Creator<RadioMetadata>() { // from class: android.hardware.radio.RadioMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RadioMetadata createFromParcel(Parcel parcel) {
                return new RadioMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RadioMetadata[] newArray(int i) {
                return new RadioMetadata[i];
            }
        };
    }

    @SystemApi
    public static final class Clock implements Parcelable {
        public static final Parcelable.Creator<Clock> CREATOR = new Parcelable.Creator<Clock>() { // from class: android.hardware.radio.RadioMetadata.Clock.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Clock createFromParcel(Parcel parcel) {
                return new Clock(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Clock[] newArray(int i) {
                return new Clock[i];
            }
        };
        private final int mTimezoneOffsetMinutes;
        private final long mUtcEpochSeconds;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mUtcEpochSeconds);
            parcel.writeInt(this.mTimezoneOffsetMinutes);
        }

        public Clock(long j, int i) {
            this.mUtcEpochSeconds = j;
            this.mTimezoneOffsetMinutes = i;
        }

        private Clock(Parcel parcel) {
            this.mUtcEpochSeconds = parcel.readLong();
            this.mTimezoneOffsetMinutes = parcel.readInt();
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
            ArrayList arrayList = new ArrayList(this.mBundle.keySet());
            arrayList.sort(null);
            Object[] objArr = new Object[arrayList.size() * 2];
            for (int i = 0; i < arrayList.size(); i++) {
                int i2 = i * 2;
                objArr[i2] = arrayList.get(i);
                objArr[i2 + 1] = this.mBundle.get((String) arrayList.get(i));
            }
            this.mHashCode = Integer.valueOf(Arrays.hashCode(objArr));
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
        Bundle bundle = ((RadioMetadata) obj).mBundle;
        if (!this.mBundle.keySet().equals(bundle.keySet())) {
            return false;
        }
        for (String str : this.mBundle.keySet()) {
            if (Flags.hdRadioImproved() && Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
                if (!Arrays.equals(this.mBundle.getStringArray(str), bundle.getStringArray(str))) {
                    return false;
                }
            } else if (!Objects.equals(this.mBundle.get(str), bundle.get(str))) {
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

    private RadioMetadata(Parcel parcel) {
        this.mBundle = parcel.readBundle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RadioMetadata[");
        boolean z = true;
        for (String str : this.mBundle.keySet()) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str.startsWith("android.hardware.radio.metadata") ? str.substring(31) : str);
            sb.append('=');
            if (Flags.hdRadioImproved() && Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
                String[] stringArray = this.mBundle.getStringArray(str);
                sb.append('[');
                for (int i = 0; i < stringArray.length; i++) {
                    if (i != 0) {
                        sb.append(',');
                    }
                    sb.append(stringArray[i]);
                }
                sb.append(']');
            } else {
                sb.append(this.mBundle.get(str));
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public boolean containsKey(String str) {
        return this.mBundle.containsKey(str);
    }

    public String getString(String str) {
        return this.mBundle.getString(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putInt(Bundle bundle, String str, int i) {
        int intValue = METADATA_KEYS_TYPE.getOrDefault(str, -1).intValue();
        if (intValue != 0 && intValue != 2) {
            throw new IllegalArgumentException("The " + str + " key cannot be used to put an int");
        }
        bundle.putInt(str, i);
    }

    public int getInt(String str) {
        return this.mBundle.getInt(str, 0);
    }

    @Deprecated
    public Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.mBundle.getParcelable(str, Bitmap.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
            return null;
        }
    }

    public int getBitmapId(String str) {
        Objects.requireNonNull(str, "Metadata key can not be null");
        if (!METADATA_KEY_ICON.equals(str) && !METADATA_KEY_ART.equals(str)) {
            throw new IllegalArgumentException("Failed to retrieve key " + str + " as bitmap key");
        }
        return getInt(str);
    }

    public Clock getClock(String str) {
        try {
            return (Clock) this.mBundle.getParcelable(str, Clock.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to retrieve a key as Clock.", e);
            return null;
        }
    }

    public String[] getStringArray(String str) {
        Objects.requireNonNull(str, "Metadata key can not be null");
        if (!Objects.equals(METADATA_KEYS_TYPE.get(str), 4)) {
            throw new IllegalArgumentException("Failed to retrieve key " + str + " as string array");
        }
        String[] stringArray = this.mBundle.getStringArray(str);
        if (stringArray != null) {
            return stringArray;
        }
        throw new IllegalArgumentException("Key " + str + " is not found in metadata");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public int size() {
        return this.mBundle.size();
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public static String getKeyFromNativeKey(int i) {
        return NATIVE_KEY_MAPPING.get(i, null);
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(RadioMetadata radioMetadata) {
            this.mBundle = new Bundle(radioMetadata.mBundle);
        }

        public Builder(RadioMetadata radioMetadata, int i) {
            this(radioMetadata);
            for (String str : this.mBundle.keySet()) {
                Object obj = this.mBundle.get(str);
                if (obj != null && (obj instanceof Bitmap)) {
                    Bitmap bitmap = (Bitmap) obj;
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        putBitmap(str, scaleBitmap(bitmap, i));
                    }
                }
            }
        }

        public Builder putString(String str, String str2) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a String");
            }
            this.mBundle.putString(str, str2);
            return this;
        }

        public Builder putInt(String str, int i) {
            RadioMetadata.putInt(this.mBundle, str, i);
            return this;
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 2) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(str, bitmap);
            return this;
        }

        public Builder putClock(String str, long j, int i) {
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || ((Integer) RadioMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 3) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a RadioMetadata.Clock.");
            }
            this.mBundle.putParcelable(str, new Clock(j, i));
            return this;
        }

        public Builder putStringArray(String str, String[] strArr) {
            Objects.requireNonNull(str, "Key can not be null");
            Objects.requireNonNull(strArr, "Value can not be null");
            if (!RadioMetadata.METADATA_KEYS_TYPE.containsKey(str) || !Objects.equals(RadioMetadata.METADATA_KEYS_TYPE.get(str), 4)) {
                throw new IllegalArgumentException("The " + str + " key cannot be used to put a RadioMetadata String Array.");
            }
            this.mBundle.putStringArray(str, strArr);
            return this;
        }

        public RadioMetadata build() {
            return new RadioMetadata(this.mBundle);
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int i) {
            float f = i;
            float min = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), true);
        }
    }

    int putIntFromNative(int i, int i2) {
        try {
            putInt(this.mBundle, getKeyFromNativeKey(i), i2);
            this.mHashCode = null;
            return 0;
        } catch (IllegalArgumentException unused) {
            return -1;
        }
    }

    int putStringFromNative(int i, String str) {
        String keyFromNativeKey = getKeyFromNativeKey(i);
        ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
        if (!arrayMap.containsKey(keyFromNativeKey) || arrayMap.get(keyFromNativeKey).intValue() != 1) {
            return -1;
        }
        this.mBundle.putString(keyFromNativeKey, str);
        this.mHashCode = null;
        return 0;
    }

    int putBitmapFromNative(int i, byte[] bArr) {
        String keyFromNativeKey = getKeyFromNativeKey(i);
        ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
        if (arrayMap.containsKey(keyFromNativeKey) && arrayMap.get(keyFromNativeKey).intValue() == 2) {
            try {
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                if (decodeByteArray != null) {
                    this.mBundle.putParcelable(keyFromNativeKey, decodeByteArray);
                    this.mHashCode = null;
                    return 0;
                }
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    int putClockFromNative(int i, long j, int i2) {
        String keyFromNativeKey = getKeyFromNativeKey(i);
        ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
        if (!arrayMap.containsKey(keyFromNativeKey) || arrayMap.get(keyFromNativeKey).intValue() != 3) {
            return -1;
        }
        this.mBundle.putParcelable(keyFromNativeKey, new Clock(j, i2));
        this.mHashCode = null;
        return 0;
    }
}
