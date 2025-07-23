package android.service.credentials;

import android.content.pm.SigningInfo;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CallingAppInfo implements Parcelable {
    public static final Parcelable.Creator<CallingAppInfo> CREATOR = new Parcelable.Creator<CallingAppInfo>() { // from class: android.service.credentials.CallingAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallingAppInfo createFromParcel(Parcel parcel) {
            return new CallingAppInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallingAppInfo[] newArray(int i) {
            return new CallingAppInfo[i];
        }
    };
    private final String mOrigin;
    private final String mPackageName;
    private final SigningInfo mSigningInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallingAppInfo(String str, SigningInfo signingInfo) {
        this(str, signingInfo, null);
    }

    public CallingAppInfo(String str, SigningInfo signingInfo, String str2) {
        this.mPackageName = (String) Preconditions.checkStringNotEmpty(str, "package namemust not be null or empty");
        this.mSigningInfo = (SigningInfo) Objects.requireNonNull(signingInfo);
        this.mOrigin = str2;
    }

    private CallingAppInfo(Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mSigningInfo = (SigningInfo) parcel.readTypedObject(SigningInfo.CREATOR);
        this.mOrigin = parcel.readString8();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public SigningInfo getSigningInfo() {
        return this.mSigningInfo;
    }

    public String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeTypedObject(this.mSigningInfo, i);
        parcel.writeString8(this.mOrigin);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CallingAppInfo {packageName= " + this.mPackageName);
        if (this.mSigningInfo != null) {
            sb.append(", mSigningInfo : No. of signatures: " + this.mSigningInfo.getApkContentsSigners().length);
        } else {
            sb.append(", mSigningInfo: null");
        }
        sb.append(",mOrigin: " + this.mOrigin);
        sb.append(" }");
        return sb.toString();
    }
}
