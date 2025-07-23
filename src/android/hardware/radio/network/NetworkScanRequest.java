package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class NetworkScanRequest implements Parcelable {
    public static final Parcelable.Creator<NetworkScanRequest> CREATOR = new Parcelable.Creator<NetworkScanRequest>() { // from class: android.hardware.radio.network.NetworkScanRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkScanRequest createFromParcel(Parcel parcel) {
            NetworkScanRequest networkScanRequest = new NetworkScanRequest();
            networkScanRequest.readFromParcel(parcel);
            return networkScanRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkScanRequest[] newArray(int i) {
            return new NetworkScanRequest[i];
        }
    };
    public static final int INCREMENTAL_RESULTS_PREIODICITY_RANGE_MAX = 10;
    public static final int INCREMENTAL_RESULTS_PREIODICITY_RANGE_MIN = 1;
    public static final int MAX_SEARCH_TIME_RANGE_MAX = 3600;
    public static final int MAX_SEARCH_TIME_RANGE_MIN = 60;
    public static final int RADIO_ACCESS_SPECIFIER_MAX_SIZE = 8;
    public static final int SCAN_INTERVAL_RANGE_MAX = 300;
    public static final int SCAN_INTERVAL_RANGE_MIN = 5;
    public static final int SCAN_TYPE_ONE_SHOT = 0;
    public static final int SCAN_TYPE_PERIODIC = 1;
    public String[] mccMncs;
    public RadioAccessSpecifier[] specifiers;
    public int type = 0;
    public int interval = 0;
    public int maxSearchTime = 0;
    public boolean incrementalResults = false;
    public int incrementalResultsPeriodicity = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeInt(this.interval);
        parcel.writeTypedArray(this.specifiers, i);
        parcel.writeInt(this.maxSearchTime);
        parcel.writeBoolean(this.incrementalResults);
        parcel.writeInt(this.incrementalResultsPeriodicity);
        parcel.writeStringArray(this.mccMncs);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.interval = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.specifiers = (RadioAccessSpecifier[]) parcel.createTypedArray(RadioAccessSpecifier.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxSearchTime = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.incrementalResults = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.incrementalResultsPeriodicity = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.mccMncs = parcel.createStringArray();
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("interval: " + this.interval);
        stringJoiner.add("specifiers: " + Arrays.toString(this.specifiers));
        stringJoiner.add("maxSearchTime: " + this.maxSearchTime);
        stringJoiner.add("incrementalResults: " + this.incrementalResults);
        stringJoiner.add("incrementalResultsPeriodicity: " + this.incrementalResultsPeriodicity);
        stringJoiner.add("mccMncs: " + Arrays.toString(this.mccMncs));
        return "NetworkScanRequest" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.specifiers);
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
