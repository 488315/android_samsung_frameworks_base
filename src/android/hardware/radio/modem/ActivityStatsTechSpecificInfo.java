package android.hardware.radio.modem;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ActivityStatsTechSpecificInfo implements Parcelable {
    public static final Parcelable.Creator<ActivityStatsTechSpecificInfo> CREATOR = new Parcelable.Creator<ActivityStatsTechSpecificInfo>() { // from class: android.hardware.radio.modem.ActivityStatsTechSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsTechSpecificInfo createFromParcel(Parcel parcel) {
            ActivityStatsTechSpecificInfo activityStatsTechSpecificInfo = new ActivityStatsTechSpecificInfo();
            activityStatsTechSpecificInfo.readFromParcel(parcel);
            return activityStatsTechSpecificInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsTechSpecificInfo[] newArray(int i) {
            return new ActivityStatsTechSpecificInfo[i];
        }
    };
    public static final int FREQUENCY_RANGE_HIGH = 3;
    public static final int FREQUENCY_RANGE_LOW = 1;
    public static final int FREQUENCY_RANGE_MID = 2;
    public static final int FREQUENCY_RANGE_MMWAVE = 4;
    public static final int FREQUENCY_RANGE_UNKNOWN = 0;
    public int[] txmModetimeMs;
    public int rat = 0;
    public int frequencyRange = 0;
    public int rxModeTimeMs = 0;

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
        parcel.writeInt(this.rat);
        parcel.writeInt(this.frequencyRange);
        parcel.writeIntArray(this.txmModetimeMs);
        parcel.writeInt(this.rxModeTimeMs);
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
                this.rat = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.frequencyRange = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.txmModetimeMs = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.rxModeTimeMs = parcel.readInt();
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
        stringJoiner.add("rat: " + AccessNetwork$$.toString(this.rat));
        stringJoiner.add("frequencyRange: " + this.frequencyRange);
        stringJoiner.add("txmModetimeMs: " + Arrays.toString(this.txmModetimeMs));
        stringJoiner.add("rxModeTimeMs: " + this.rxModeTimeMs);
        return "ActivityStatsTechSpecificInfo" + stringJoiner.toString();
    }
}
