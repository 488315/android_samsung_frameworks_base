package android.webkit;

import android.annotation.SystemApi;
import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public final class WebViewProviderResponse implements Parcelable {
    public static final Parcelable.Creator<WebViewProviderResponse> CREATOR = new Parcelable.Creator<WebViewProviderResponse>() { // from class: android.webkit.WebViewProviderResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderResponse createFromParcel(Parcel parcel) {
            return new WebViewProviderResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderResponse[] newArray(int i) {
            return new WebViewProviderResponse[i];
        }
    };
    public static final int STATUS_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    public static final int STATUS_FAILED_OTHER = 11;
    public static final int STATUS_FAILED_WAITING_FOR_RELRO = 3;
    public static final int STATUS_SUCCESS = 0;
    public final PackageInfo packageInfo;
    public final int status;

    @Retention(RetentionPolicy.SOURCE)
    private @interface WebViewProviderStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WebViewProviderResponse(PackageInfo packageInfo, int i) {
        this.packageInfo = packageInfo;
        this.status = i;
    }

    private WebViewProviderResponse(Parcel parcel) {
        this.packageInfo = (PackageInfo) parcel.readTypedObject(PackageInfo.CREATOR);
        this.status = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.packageInfo, i);
        parcel.writeInt(this.status);
    }
}
