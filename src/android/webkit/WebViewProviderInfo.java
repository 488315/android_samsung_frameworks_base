package android.webkit;

import android.annotation.SystemApi;
import android.content.pm.Signature;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class WebViewProviderInfo implements Parcelable {
    public static final Parcelable.Creator<WebViewProviderInfo> CREATOR = new Parcelable.Creator<WebViewProviderInfo>() { // from class: android.webkit.WebViewProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderInfo createFromParcel(Parcel parcel) {
            return new WebViewProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderInfo[] newArray(int i) {
            return new WebViewProviderInfo[i];
        }
    };
    public final boolean availableByDefault;
    public final String description;
    public final boolean isFallback;
    public final String packageName;
    public final Signature[] signatures;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WebViewProviderInfo(String str, String str2, boolean z, boolean z2, String[] strArr) {
        this.packageName = str;
        this.description = str2;
        this.availableByDefault = z;
        this.isFallback = z2;
        if (strArr == null) {
            this.signatures = new Signature[0];
            return;
        }
        this.signatures = new Signature[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this.signatures[i] = new Signature(Base64.decode(strArr[i], 0));
        }
    }

    private WebViewProviderInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.description = parcel.readString();
        this.availableByDefault = parcel.readInt() > 0;
        this.isFallback = parcel.readInt() > 0;
        this.signatures = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.description);
        parcel.writeInt(this.availableByDefault ? 1 : 0);
        parcel.writeInt(this.isFallback ? 1 : 0);
        parcel.writeTypedArray(this.signatures, 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WebViewProviderInfo) {
            WebViewProviderInfo webViewProviderInfo = (WebViewProviderInfo) obj;
            if (this.packageName.equals(webViewProviderInfo.packageName) && this.description.equals(webViewProviderInfo.description) && this.availableByDefault == webViewProviderInfo.availableByDefault && this.isFallback == webViewProviderInfo.isFallback && Arrays.equals(this.signatures, webViewProviderInfo.signatures)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.packageName, this.description, Boolean.valueOf(this.availableByDefault), Boolean.valueOf(this.isFallback), Integer.valueOf(Arrays.hashCode(this.signatures)));
    }

    public String toString() {
        return "WebViewProviderInfo; packageName=" + this.packageName + " description=\"" + this.description + "\" availableByDefault=" + this.availableByDefault + " isFallback=" + this.isFallback + " signatures=" + Arrays.toString(this.signatures);
    }
}
