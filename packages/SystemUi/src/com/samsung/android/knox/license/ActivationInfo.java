package com.samsung.android.knox.license;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ActivationInfo implements Parcelable {
    public static final Parcelable.Creator<ActivationInfo> CREATOR = new Parcelable.Creator<ActivationInfo>() { // from class: com.samsung.android.knox.license.ActivationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ActivationInfo[] newArray(int i) {
            return new ActivationInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ActivationInfo createFromParcel(Parcel parcel) {
            return new ActivationInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ActivationInfo[] newArray(int i) {
            return new ActivationInfo[i];
        }
    };
    public Date activationDate;
    public String maskedLicenseKey;
    public String packageName;
    public String productType;
    public String sku;
    public State state;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum State {
        ACTIVE,
        EXPIRED,
        TERMINATED;

        public static State fromInt(int i) {
            if (i == 1) {
                return ACTIVE;
            }
            if (i == 3) {
                return EXPIRED;
            }
            if (i != 4) {
                return null;
            }
            return TERMINATED;
        }
    }

    public /* synthetic */ ActivationInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public static ActivationInfo fromCursor(Cursor cursor) {
        ActivationInfo activationInfo = new ActivationInfo();
        activationInfo.packageName = cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_PACKAGE_NAME));
        activationInfo.state = State.fromInt(cursor.getInt(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_LICENSE_STATUS)));
        activationInfo.maskedLicenseKey = cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_LICENSE_KEY));
        activationInfo.sku = cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_SKU));
        activationInfo.productType = cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_PRODUCT_TYPE));
        try {
            activationInfo.activationDate = new Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_ACTIVATION_TS))).longValue());
        } catch (NumberFormatException unused) {
            activationInfo.activationDate = null;
        }
        return activationInfo;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final Date getActivationDate() {
        return this.activationDate;
    }

    public final String getMaskedLicenseKey() {
        return this.maskedLicenseKey;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getProductType() {
        return this.productType;
    }

    public final String getSku() {
        return this.sku;
    }

    public final State getState() {
        return this.state;
    }

    public final void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        String readString = parcel.readString();
        if (readString != null) {
            this.state = State.valueOf(readString);
        }
        this.maskedLicenseKey = parcel.readString();
        this.sku = parcel.readString();
        this.productType = parcel.readString();
        long readLong = parcel.readLong();
        this.activationDate = readLong == -1 ? null : new Date(readLong);
    }

    public final void setActivationDate(Date date) {
        this.activationDate = date;
    }

    public final void setMaskedLicenseKey(String str) {
        this.maskedLicenseKey = str;
    }

    public final void setPackageName(String str) {
        this.packageName = str;
    }

    public final void setProductType(String str) {
        this.productType = str;
    }

    public final void setSku(String str) {
        this.sku = str;
    }

    public final void setState(State state) {
        this.state = state;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        State state = this.state;
        parcel.writeString(state != null ? state.name() : null);
        parcel.writeString(this.maskedLicenseKey);
        parcel.writeString(this.sku);
        parcel.writeString(this.productType);
        Date date = this.activationDate;
        parcel.writeLong(date != null ? date.getTime() : -1L);
    }

    public ActivationInfo() {
    }

    private ActivationInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
