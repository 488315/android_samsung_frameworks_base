package android.telephony.gba;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.IBootstrapAuthenticationCallback;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class GbaAuthRequest implements Parcelable {
    private int mAppType;
    private IBootstrapAuthenticationCallback mCallback;
    private boolean mForceBootStrapping;
    private Uri mNafUrl;
    private byte[] mSecurityProtocol;
    private int mSubId;
    private int mToken;
    private static AtomicInteger sUniqueToken = new AtomicInteger(0);
    public static final Parcelable.Creator<GbaAuthRequest> CREATOR = new Parcelable.Creator<GbaAuthRequest>() { // from class: android.telephony.gba.GbaAuthRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GbaAuthRequest createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            Uri uri = (Uri) parcel.readParcelable(GbaAuthRequest.class.getClassLoader(), Uri.class);
            byte[] bArr = new byte[parcel.readInt()];
            parcel.readByteArray(bArr);
            return new GbaAuthRequest(i, i2, i3, uri, bArr, parcel.readBoolean(), IBootstrapAuthenticationCallback.Stub.asInterface(parcel.readStrongBinder()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GbaAuthRequest[] newArray(int i) {
            return new GbaAuthRequest[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GbaAuthRequest(int i, int i2, Uri uri, byte[] bArr, boolean z, IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this(nextUniqueToken(), i, i2, uri, bArr, z, iBootstrapAuthenticationCallback);
    }

    public GbaAuthRequest(GbaAuthRequest gbaAuthRequest) {
        this(gbaAuthRequest.mToken, gbaAuthRequest.mSubId, gbaAuthRequest.mAppType, gbaAuthRequest.mNafUrl, gbaAuthRequest.mSecurityProtocol, gbaAuthRequest.mForceBootStrapping, gbaAuthRequest.mCallback);
    }

    public GbaAuthRequest(int i, int i2, int i3, Uri uri, byte[] bArr, boolean z, IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this.mToken = i;
        this.mSubId = i2;
        this.mAppType = i3;
        this.mNafUrl = uri;
        this.mSecurityProtocol = bArr;
        this.mCallback = iBootstrapAuthenticationCallback;
        this.mForceBootStrapping = z;
    }

    public int getToken() {
        return this.mToken;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getAppType() {
        return this.mAppType;
    }

    public Uri getNafUrl() {
        return this.mNafUrl;
    }

    public byte[] getSecurityProtocol() {
        return this.mSecurityProtocol;
    }

    public boolean isForceBootStrapping() {
        return this.mForceBootStrapping;
    }

    public void setCallback(IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this.mCallback = iBootstrapAuthenticationCallback;
    }

    public IBootstrapAuthenticationCallback getCallback() {
        return this.mCallback;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mToken);
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mAppType);
        parcel.writeParcelable(this.mNafUrl, 0);
        parcel.writeInt(this.mSecurityProtocol.length);
        parcel.writeByteArray(this.mSecurityProtocol);
        parcel.writeBoolean(this.mForceBootStrapping);
        parcel.writeStrongInterface(this.mCallback);
    }

    private static int nextUniqueToken() {
        return (sUniqueToken.getAndIncrement() << 16) | (((int) System.currentTimeMillis()) & 65535);
    }

    public String toString() {
        return "Token: " + this.mToken + "SubId:" + this.mSubId + ", AppType:" + this.mAppType + ", NafUrl:" + this.mNafUrl + ", SecurityProtocol:" + IccUtils.bytesToHexString(this.mSecurityProtocol) + ", ForceBootStrapping:" + this.mForceBootStrapping + ", CallBack:" + this.mCallback;
    }
}
