package android.webkit;

import android.annotation.SystemApi;
import android.content.p002pm.Signature;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Base64;

@SystemApi
/* loaded from: classes4.dex */
public final class WebViewProviderInfo implements Parcelable {
  public static final Parcelable.Creator<WebViewProviderInfo> CREATOR =
      new Parcelable.Creator<
          WebViewProviderInfo>() { // from class: android.webkit.WebViewProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderInfo createFromParcel(Parcel in) {
          return new WebViewProviderInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebViewProviderInfo[] newArray(int size) {
          return new WebViewProviderInfo[size];
        }
      };
  public final boolean availableByDefault;
  public final String description;
  public final boolean isFallback;
  public final String packageName;
  public final Signature[] signatures;

  public WebViewProviderInfo(
      String packageName,
      String description,
      boolean availableByDefault,
      boolean isFallback,
      String[] signatures) {
    this.packageName = packageName;
    this.description = description;
    this.availableByDefault = availableByDefault;
    this.isFallback = isFallback;
    if (signatures == null) {
      this.signatures = new Signature[0];
      return;
    }
    this.signatures = new Signature[signatures.length];
    for (int n = 0; n < signatures.length; n++) {
      this.signatures[n] = new Signature(Base64.decode(signatures[n], 0));
    }
  }

  private WebViewProviderInfo(Parcel in) {
    this.packageName = in.readString();
    this.description = in.readString();
    this.availableByDefault = in.readInt() > 0;
    this.isFallback = in.readInt() > 0;
    this.signatures = (Signature[]) in.createTypedArray(Signature.CREATOR);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.packageName);
    parcel.writeString(this.description);
    parcel.writeInt(this.availableByDefault ? 1 : 0);
    parcel.writeInt(this.isFallback ? 1 : 0);
    parcel.writeTypedArray(this.signatures, 0);
  }
}
