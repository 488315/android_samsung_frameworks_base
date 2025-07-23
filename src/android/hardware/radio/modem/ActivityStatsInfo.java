package android.hardware.radio.modem;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ActivityStatsInfo implements Parcelable {
    public static final Parcelable.Creator<ActivityStatsInfo> CREATOR = new Parcelable.Creator<ActivityStatsInfo>() { // from class: android.hardware.radio.modem.ActivityStatsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsInfo createFromParcel(Parcel parcel) {
            ActivityStatsInfo activityStatsInfo = new ActivityStatsInfo();
            activityStatsInfo.readFromParcel(parcel);
            return activityStatsInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsInfo[] newArray(int i) {
            return new ActivityStatsInfo[i];
        }
    };
    public ActivityStatsTechSpecificInfo[] techSpecificInfo;
    public int sleepModeTimeMs = 0;
    public int idleModeTimeMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sleepModeTimeMs);
        parcel.writeInt(this.idleModeTimeMs);
        parcel.writeTypedArray(this.techSpecificInfo, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.sleepModeTimeMs = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.idleModeTimeMs = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.techSpecificInfo = (ActivityStatsTechSpecificInfo[]) parcel.createTypedArray(ActivityStatsTechSpecificInfo.CREATOR);
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("sleepModeTimeMs: " + this.sleepModeTimeMs);
        stringJoiner.add("idleModeTimeMs: " + this.idleModeTimeMs);
        stringJoiner.add("techSpecificInfo: " + Arrays.toString(this.techSpecificInfo));
        return "ActivityStatsInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.techSpecificInfo);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
