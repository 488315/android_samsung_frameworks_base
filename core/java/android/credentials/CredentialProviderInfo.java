package android.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class CredentialProviderInfo implements Parcelable {
  public static final Parcelable.Creator<CredentialProviderInfo> CREATOR =
      new Parcelable.Creator<
          CredentialProviderInfo>() { // from class: android.credentials.CredentialProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialProviderInfo[] newArray(int size) {
          return new CredentialProviderInfo[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialProviderInfo createFromParcel(Parcel in) {
          return new CredentialProviderInfo(in);
        }
      };
  private final Set<String> mCapabilities;
  private final boolean mIsEnabled;
  private final boolean mIsPrimary;
  private final boolean mIsSystemProvider;
  private final CharSequence mOverrideLabel;
  private final ServiceInfo mServiceInfo;
  private CharSequence mSettingsSubtitle;

  private CredentialProviderInfo(Builder builder) {
    HashSet hashSet = new HashSet();
    this.mCapabilities = hashSet;
    this.mSettingsSubtitle = null;
    this.mServiceInfo = builder.mServiceInfo;
    hashSet.addAll(builder.mCapabilities);
    this.mIsSystemProvider = builder.mIsSystemProvider;
    this.mSettingsSubtitle = builder.mSettingsSubtitle;
    this.mIsEnabled = builder.mIsEnabled;
    this.mIsPrimary = builder.mIsPrimary;
    this.mOverrideLabel = builder.mOverrideLabel;
  }

  public boolean hasCapability(String credentialType) {
    return this.mCapabilities.contains(credentialType);
  }

  public ServiceInfo getServiceInfo() {
    return this.mServiceInfo;
  }

  public boolean isSystemProvider() {
    return this.mIsSystemProvider;
  }

  public Drawable getServiceIcon(Context context) {
    return this.mServiceInfo.loadIcon(context.getPackageManager());
  }

  public CharSequence getLabel(Context context) {
    CharSequence charSequence = this.mOverrideLabel;
    if (charSequence != null) {
      return charSequence;
    }
    return this.mServiceInfo.loadSafeLabel(context.getPackageManager());
  }

  public List<String> getCapabilities() {
    List<String> capabilities = new ArrayList<>();
    for (String capability : this.mCapabilities) {
      capabilities.add(capability);
    }
    return Collections.unmodifiableList(capabilities);
  }

  public boolean isEnabled() {
    return this.mIsEnabled;
  }

  public boolean isPrimary() {
    return this.mIsPrimary;
  }

  public CharSequence getSettingsSubtitle() {
    return this.mSettingsSubtitle;
  }

  public ComponentName getComponentName() {
    return this.mServiceInfo.getComponentName();
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedObject(this.mServiceInfo, flags);
    dest.writeBoolean(this.mIsSystemProvider);
    dest.writeBoolean(this.mIsEnabled);
    dest.writeBoolean(this.mIsPrimary);
    TextUtils.writeToParcel(this.mOverrideLabel, dest, flags);
    TextUtils.writeToParcel(this.mSettingsSubtitle, dest, flags);
    List<String> capabilities = getCapabilities();
    dest.writeStringList(capabilities);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    return "CredentialProviderInfo {serviceInfo="
        + this.mServiceInfo
        + ", isSystemProvider="
        + this.mIsSystemProvider
        + ", isEnabled="
        + this.mIsEnabled
        + ", isPrimary="
        + this.mIsPrimary
        + ", overrideLabel="
        + ((Object) this.mOverrideLabel)
        + ", settingsSubtitle="
        + ((Object) this.mSettingsSubtitle)
        + ", capabilities="
        + String.join(",", this.mCapabilities)
        + "}";
  }

  private CredentialProviderInfo(Parcel in) {
    HashSet hashSet = new HashSet();
    this.mCapabilities = hashSet;
    this.mSettingsSubtitle = null;
    this.mServiceInfo = (ServiceInfo) in.readTypedObject(ServiceInfo.CREATOR);
    this.mIsSystemProvider = in.readBoolean();
    this.mIsEnabled = in.readBoolean();
    this.mIsPrimary = in.readBoolean();
    this.mOverrideLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    this.mSettingsSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    ArrayList arrayList = new ArrayList();
    in.readStringList(arrayList);
    hashSet.addAll(arrayList);
  }

  public static final class Builder {
    private ServiceInfo mServiceInfo;
    private Set<String> mCapabilities = new HashSet();
    private boolean mIsSystemProvider = false;
    private CharSequence mSettingsSubtitle = null;
    private boolean mIsEnabled = false;
    private boolean mIsPrimary = false;
    private CharSequence mOverrideLabel = null;

    public Builder(ServiceInfo serviceInfo) {
      this.mServiceInfo = serviceInfo;
    }

    public Builder setSystemProvider(boolean isSystemProvider) {
      this.mIsSystemProvider = isSystemProvider;
      return this;
    }

    public Builder setOverrideLabel(CharSequence overrideLabel) {
      this.mOverrideLabel = overrideLabel;
      return this;
    }

    public Builder setSettingsSubtitle(CharSequence settingsSubtitle) {
      this.mSettingsSubtitle = settingsSubtitle;
      return this;
    }

    public Builder addCapabilities(List<String> capabilities) {
      this.mCapabilities.addAll(capabilities);
      return this;
    }

    public Builder addCapabilities(Set<String> capabilities) {
      this.mCapabilities.addAll(capabilities);
      return this;
    }

    public Builder setEnabled(boolean isEnabled) {
      this.mIsEnabled = isEnabled;
      return this;
    }

    public Builder setPrimary(boolean isPrimary) {
      this.mIsPrimary = isPrimary;
      return this;
    }

    public CredentialProviderInfo build() {
      return new CredentialProviderInfo(this);
    }
  }
}
