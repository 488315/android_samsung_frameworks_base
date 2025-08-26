package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalVolumeCurve implements Parcelable {
    public static final Parcelable.Creator<AudioHalVolumeCurve> CREATOR = new Parcelable.Creator<AudioHalVolumeCurve>() { // from class: android.media.audio.common.AudioHalVolumeCurve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalVolumeCurve createFromParcel(Parcel parcel) {
            AudioHalVolumeCurve audioHalVolumeCurve = new AudioHalVolumeCurve();
            audioHalVolumeCurve.readFromParcel(parcel);
            return audioHalVolumeCurve;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalVolumeCurve[] newArray(int i) {
            return new AudioHalVolumeCurve[i];
        }
    };
    public CurvePoint[] curvePoints;
    public byte deviceCategory = 1;

    public @interface DeviceCategory {
        public static final byte EARPIECE = 2;
        public static final byte EXT_MEDIA = 3;
        public static final byte HEADSET = 0;
        public static final byte HEARING_AID = 4;
        public static final byte SPEAKER = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.deviceCategory);
        parcel.writeTypedArray(this.curvePoints, i);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.deviceCategory = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.curvePoints = (CurvePoint[]) parcel.createTypedArray(CurvePoint.CREATOR);
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("deviceCategory: " + ((int) this.deviceCategory));
        stringJoiner.add("curvePoints: " + Arrays.toString(this.curvePoints));
        return "AudioHalVolumeCurve" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalVolumeCurve)) {
            return false;
        }
        AudioHalVolumeCurve audioHalVolumeCurve = (AudioHalVolumeCurve) obj;
        return Objects.deepEquals(java.lang.Byte.valueOf(this.deviceCategory), java.lang.Byte.valueOf(audioHalVolumeCurve.deviceCategory)) && Objects.deepEquals(this.curvePoints, audioHalVolumeCurve.curvePoints);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(java.lang.Byte.valueOf(this.deviceCategory), this.curvePoints).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.curvePoints);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class CurvePoint implements Parcelable {
        public static final Parcelable.Creator<CurvePoint> CREATOR = new Parcelable.Creator<CurvePoint>() { // from class: android.media.audio.common.AudioHalVolumeCurve.CurvePoint.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CurvePoint createFromParcel(Parcel parcel) {
                CurvePoint curvePoint = new CurvePoint();
                curvePoint.readFromParcel(parcel);
                return curvePoint;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CurvePoint[] newArray(int i) {
                return new CurvePoint[i];
            }
        };
        public static final byte MAX_INDEX = 100;
        public static final byte MIN_INDEX = 0;
        public byte index = 0;
        public int attenuationMb = 0;

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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeByte(this.index);
            parcel.writeInt(this.attenuationMb);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.index = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.attenuationMb = parcel.readInt();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
