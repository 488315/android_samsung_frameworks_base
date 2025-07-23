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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pid);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.deviceId);
        parcel.writeString(this.packageName);
        parcel.writeString(this.attributionTag);
        parcel.writeStrongBinder(this.token);
        parcel.writeStringArray(this.renouncedPermissions);
        parcel.writeTypedArray(this.next, i);
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
                this.pid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.deviceId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.packageName = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.attributionTag = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.token = parcel.readStrongBinder();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.renouncedPermissions = parcel.createStringArray();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.next = (AttributionSourceState[]) parcel.createTypedArray(CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.next);
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
