package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class HeadTracking implements Parcelable {
    public static final Parcelable.Creator<HeadTracking> CREATOR = new Parcelable.Creator<HeadTracking>() { // from class: android.media.audio.common.HeadTracking.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeadTracking createFromParcel(Parcel parcel) {
            HeadTracking headTracking = new HeadTracking();
            headTracking.readFromParcel(parcel);
            return headTracking;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HeadTracking[] newArray(int i) {
            return new HeadTracking[i];
        }
    };

    public @interface ConnectionMode {
        public static final byte DIRECT_TO_SENSOR_SW = 1;
        public static final byte DIRECT_TO_SENSOR_TUNNEL = 2;
        public static final byte FRAMEWORK_PROCESSED = 0;
    }

    public @interface Mode {
        public static final byte DISABLED = 1;
        public static final byte OTHER = 0;
        public static final byte RELATIVE_SCREEN = 3;
        public static final byte RELATIVE_WORLD = 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt >= 4) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } else {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public String toString() {
        return "HeadTracking" + new StringJoiner(", ", "{", "}").toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof HeadTracking)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(new Object[0]).toArray());
    }

    public static final class SensorData implements Parcelable {
        public static final Parcelable.Creator<SensorData> CREATOR = new Parcelable.Creator<SensorData>() { // from class: android.media.audio.common.HeadTracking.SensorData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SensorData createFromParcel(Parcel parcel) {
                return new SensorData(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SensorData[] newArray(int i) {
                return new SensorData[i];
            }
        };
        public static final int headToStage = 0;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final int headToStage = 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        public SensorData() {
            this._tag = 0;
            this._value = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }

        private SensorData(Parcel parcel) {
            readFromParcel(parcel);
        }

        private SensorData(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static SensorData headToStage(float[] fArr) {
            return new SensorData(0, fArr);
        }

        public float[] getHeadToStage() {
            _assertTag(0);
            return (float[]) this._value;
        }

        public void setHeadToStage(float[] fArr) {
            _set(0, fArr);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            if (this._tag != 0) {
                return;
            }
            parcel.writeFixedArray(getHeadToStage(), i, 6);
        }

        public void readFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 0) {
                _set(readInt, (float[]) parcel.createFixedArray(float[].class, 6));
            } else {
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            getTag();
            return 0;
        }

        private void _assertTag(int i) {
            if (getTag() == i) {
                return;
            }
            throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }

        private String _tagString(int i) {
            if (i == 0) {
                return "headToStage";
            }
            throw new IllegalStateException("unknown field: " + i);
        }

        private void _set(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }
    }
}
