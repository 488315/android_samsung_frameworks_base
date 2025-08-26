package android.content;

import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AttributionSourceState implements Parcelable {
    public static final Parcelable.Creator<AttributionSourceState> CREATOR = new Parcelable.Creator<AttributionSourceState>() { // from class: android.content.AttributionSourceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSourceState createFromParcel(Parcel parcel) {
            AttributionSourceState attributionSourceState = new AttributionSourceState();
            attributionSourceState.readFromParcel(parcel);
            return attributionSourceState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSourceState[] newArray(int i) {
            return new AttributionSourceState[i];
        }
    };
    public String attributionTag;
    public AttributionSourceState[] next;
    public String packageName;
    public String[] renouncedPermissions;
    public IBinder token;
    public int pid = -1;
    public int uid = -1;
    public int deviceId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pid);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.deviceId);
        parcel.writeString(this.packageName);
        parcel.writeString(this.attributionTag);
        parcel.writeStrongBinder(this.token);
        parcel.writeStringArray(this.renouncedPermissions);
        parcel.writeTypedArray(this.next, i);
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
                this.pid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.deviceId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.packageName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.attributionTag = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.token = parcel.readStrongBinder();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.renouncedPermissions = parcel.createStringArray();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.next = (AttributionSourceState[]) parcel.createTypedArray(CREATOR);
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
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.next);
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
