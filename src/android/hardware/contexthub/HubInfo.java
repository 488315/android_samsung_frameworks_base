package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HubInfo implements Parcelable {
    public static final Parcelable.Creator<HubInfo> CREATOR = new Parcelable.Creator<HubInfo>() { // from class: android.hardware.contexthub.HubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubInfo createFromParcel(Parcel parcel) {
            HubInfo hubInfo = new HubInfo();
            hubInfo.readFromParcel(parcel);
            return hubInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubInfo[] newArray(int i) {
            return new HubInfo[i];
        }
    };
    public static final long HUB_ID_INVALID = 0;
    public static final long HUB_ID_RESERVED = -1;
    public HubDetails hubDetails;
    public long hubId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.hubId);
        parcel.writeTypedObject(this.hubDetails, i);
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
                this.hubId = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.hubDetails = (HubDetails) parcel.readTypedObject(HubDetails.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.hubDetails);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static final class HubDetails implements Parcelable {
        public static final Parcelable.Creator<HubDetails> CREATOR = new Parcelable.Creator<HubDetails>() { // from class: android.hardware.contexthub.HubInfo.HubDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HubDetails createFromParcel(Parcel parcel) {
                return new HubDetails(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HubDetails[] newArray(int i) {
                return new HubDetails[i];
            }
        };
        public static final int contextHubInfo = 0;
        public static final int vendorHubInfo = 1;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final int contextHubInfo = 0;
            public static final int vendorHubInfo = 1;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        public HubDetails() {
            this._tag = 0;
            this._value = null;
        }

        private HubDetails(Parcel parcel) {
            readFromParcel(parcel);
        }

        private HubDetails(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static HubDetails contextHubInfo(ContextHubInfo contextHubInfo2) {
            return new HubDetails(0, contextHubInfo2);
        }

        public ContextHubInfo getContextHubInfo() {
            _assertTag(0);
            return (ContextHubInfo) this._value;
        }

        public void setContextHubInfo(ContextHubInfo contextHubInfo2) {
            _set(0, contextHubInfo2);
        }

        public static HubDetails vendorHubInfo(VendorHubInfo vendorHubInfo2) {
            return new HubDetails(1, vendorHubInfo2);
        }

        public VendorHubInfo getVendorHubInfo() {
            _assertTag(1);
            return (VendorHubInfo) this._value;
        }

        public void setVendorHubInfo(VendorHubInfo vendorHubInfo2) {
            _set(1, vendorHubInfo2);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            int i2 = this._tag;
            if (i2 == 0) {
                parcel.writeTypedObject(getContextHubInfo(), i);
            } else {
                if (i2 != 1) {
                    return;
                }
                parcel.writeTypedObject(getVendorHubInfo(), i);
            }
        }

        public void readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 0) {
                _set(i, (ContextHubInfo) parcel.readTypedObject(ContextHubInfo.CREATOR));
            } else if (i == 1) {
                _set(i, (VendorHubInfo) parcel.readTypedObject(VendorHubInfo.CREATOR));
            } else {
                throw new IllegalArgumentException("union: unknown tag: " + i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            int tag = getTag();
            if (tag == 0) {
                return describeContents(getContextHubInfo());
            }
            if (tag != 1) {
                return 0;
            }
            return describeContents(getVendorHubInfo());
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
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
                return "contextHubInfo";
            }
            if (i == 1) {
                return "vendorHubInfo";
            }
            throw new IllegalStateException("unknown field: " + i);
        }

        private void _set(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }
    }
}
