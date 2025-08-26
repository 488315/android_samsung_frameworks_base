package android.hardware.location;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class HubInfo implements Parcelable {
    public static final Parcelable.Creator<HubInfo> CREATOR = new Parcelable.Creator<HubInfo>() { // from class: android.hardware.location.HubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubInfo createFromParcel(Parcel parcel) {
            return new HubInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubInfo[] newArray(int i) {
            return new HubInfo[i];
        }
    };
    public static final int TYPE_CONTEXT_HUB = 0;
    public static final int TYPE_VENDOR_HUB = 1;
    private final ContextHubInfo mContextHubInfo;
    private final long mId;
    private final int mType;
    private final VendorHubInfo mVendorHubInfo;

    @Retention(RetentionPolicy.SOURCE)
    private @interface HubType {
    }

    public HubInfo(long j, ContextHubInfo contextHubInfo) {
        this.mId = j;
        this.mType = 0;
        this.mContextHubInfo = contextHubInfo;
        this.mVendorHubInfo = null;
    }

    public HubInfo(long j, VendorHubInfo vendorHubInfo) {
        this.mId = j;
        this.mType = 1;
        this.mContextHubInfo = null;
        this.mVendorHubInfo = vendorHubInfo;
    }

    private HubInfo(Parcel parcel) {
        this.mId = parcel.readLong();
        int i = parcel.readInt();
        this.mType = i;
        if (i == 0) {
            this.mContextHubInfo = ContextHubInfo.CREATOR.createFromParcel(parcel);
            this.mVendorHubInfo = null;
        } else {
            if (i == 1) {
                this.mVendorHubInfo = VendorHubInfo.CREATOR.createFromParcel(parcel);
                this.mContextHubInfo = null;
                return;
            }
            throw new BadParcelableException("Parcelable has invalid type");
        }
    }

    public long getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public ContextHubInfo getContextHubInfo() {
        return this.mContextHubInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        VendorHubInfo vendorHubInfo;
        ContextHubInfo contextHubInfo;
        int i = this.mType;
        if (i == 0 && (contextHubInfo = this.mContextHubInfo) != null) {
            return contextHubInfo.describeContents();
        }
        if (i != 1 || (vendorHubInfo = this.mVendorHubInfo) == null) {
            return 0;
        }
        return vendorHubInfo.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        VendorHubInfo vendorHubInfo;
        ContextHubInfo contextHubInfo;
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mType);
        if (this.mType == 0 && (contextHubInfo = this.mContextHubInfo) != null) {
            contextHubInfo.writeToParcel(parcel, i);
        }
        if (this.mType != 1 || (vendorHubInfo = this.mVendorHubInfo) == null) {
            return;
        }
        vendorHubInfo.writeToParcel(parcel, i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("HubInfo ID: 0x");
        sb.append(Long.toHexString(this.mId));
        sb.append(ShaderAssembler.NEWLINE);
        if (this.mType == 0 && this.mContextHubInfo != null) {
            sb.append(" ContextHubDetails: ");
            sb.append(this.mContextHubInfo);
        }
        if (this.mType == 1 && this.mVendorHubInfo != null) {
            sb.append(" VendorHubDetails: ");
            sb.append(this.mVendorHubInfo);
        }
        return sb.toString();
    }
}
