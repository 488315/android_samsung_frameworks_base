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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.precedence);
        parcel.writeTypedArray(this.trafficDescriptors, i);
        parcel.writeTypedArray(this.routeSelectionDescriptor, i);
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
                this.precedence = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.trafficDescriptors = (TrafficDescriptor[]) parcel.createTypedArray(TrafficDescriptor.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.routeSelectionDescriptor = (RouteSelectionDescriptor[]) parcel.createTypedArray(RouteSelectionDescriptor.CREATOR);
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
}
