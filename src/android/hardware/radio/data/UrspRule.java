package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class UrspRule implements Parcelable {
    public static final Parcelable.Creator<UrspRule> CREATOR = new Parcelable.Creator<UrspRule>() { // from class: android.hardware.radio.data.UrspRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrspRule createFromParcel(Parcel parcel) {
            UrspRule urspRule = new UrspRule();
            urspRule.readFromParcel(parcel);
            return urspRule;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrspRule[] newArray(int i) {
            return new UrspRule[i];
        }
    };
    public int precedence = 0;
    public RouteSelectionDescriptor[] routeSelectionDescriptor;
    public TrafficDescriptor[] trafficDescriptors;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.precedence);
        parcel.writeTypedArray(this.trafficDescriptors, i);
        parcel.writeTypedArray(this.routeSelectionDescriptor, i);
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
                this.precedence = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.trafficDescriptors = (TrafficDescriptor[]) parcel.createTypedArray(TrafficDescriptor.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.routeSelectionDescriptor = (RouteSelectionDescriptor[]) parcel.createTypedArray(RouteSelectionDescriptor.CREATOR);
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
        stringJoiner.add("precedence: " + this.precedence);
        stringJoiner.add("trafficDescriptors: " + Arrays.toString(this.trafficDescriptors));
        stringJoiner.add("routeSelectionDescriptor: " + Arrays.toString(this.routeSelectionDescriptor));
        return "UrspRule" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.routeSelectionDescriptor) | describeContents(this.trafficDescriptors);
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
