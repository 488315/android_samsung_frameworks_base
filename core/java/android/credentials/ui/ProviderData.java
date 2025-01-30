package android.credentials.ui;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes.dex */
public abstract class ProviderData implements Parcelable {
  public static final String EXTRA_DISABLED_PROVIDER_DATA_LIST =
      "android.credentials.ui.extra.DISABLED_PROVIDER_DATA_LIST";
  public static final String EXTRA_ENABLED_PROVIDER_DATA_LIST =
      "android.credentials.ui.extra.ENABLED_PROVIDER_DATA_LIST";
  private final String mProviderFlattenedComponentName;

  public ProviderData(String providerFlattenedComponentName) {
    this.mProviderFlattenedComponentName = providerFlattenedComponentName;
  }

  public String getProviderFlattenedComponentName() {
    return this.mProviderFlattenedComponentName;
  }

  protected ProviderData(Parcel in) {
    String providerFlattenedComponentName = in.readString8();
    this.mProviderFlattenedComponentName = providerFlattenedComponentName;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) providerFlattenedComponentName);
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString8(this.mProviderFlattenedComponentName);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
