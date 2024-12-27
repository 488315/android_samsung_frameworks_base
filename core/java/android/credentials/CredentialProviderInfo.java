package android.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class CredentialProviderInfo implements Parcelable {
    public static final Parcelable.Creator<CredentialProviderInfo> CREATOR = new Parcelable.Creator<CredentialProviderInfo>() { // from class: android.credentials.CredentialProviderInfo.1
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
    private final List<String> mCapabilities;
    private final boolean mIsEnabled;
    private final boolean mIsPrimary;
    private final boolean mIsSystemProvider;
    private final CharSequence mOverrideLabel;
    private final ServiceInfo mServiceInfo;
    private CharSequence mSettingsActivity;
    private CharSequence mSettingsSubtitle;

    private CredentialProviderInfo(Builder builder) {
        this.mCapabilities = new ArrayList();
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = builder.mServiceInfo;
        this.mCapabilities.addAll(builder.mCapabilities);
        this.mIsSystemProvider = builder.mIsSystemProvider;
        this.mSettingsSubtitle = builder.mSettingsSubtitle;
        this.mIsEnabled = builder.mIsEnabled;
        this.mIsPrimary = builder.mIsPrimary;
        this.mOverrideLabel = builder.mOverrideLabel;
        this.mSettingsActivity = builder.mSettingsActivity;
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
        if (this.mOverrideLabel != null) {
            return this.mOverrideLabel;
        }
        return this.mServiceInfo.loadSafeLabel(context.getPackageManager());
    }

    public List<String> getCapabilities() {
        return Collections.unmodifiableList(this.mCapabilities);
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

    public CharSequence getSettingsActivity() {
        if (!Flags.settingsActivityEnabled()) {
            return null;
        }
        return this.mSettingsActivity;
    }

    public ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mServiceInfo, flags);
        dest.writeBoolean(this.mIsSystemProvider);
        dest.writeStringList(this.mCapabilities);
        dest.writeBoolean(this.mIsEnabled);
        dest.writeBoolean(this.mIsPrimary);
        TextUtils.writeToParcel(this.mOverrideLabel, dest, flags);
        TextUtils.writeToParcel(this.mSettingsSubtitle, dest, flags);
        TextUtils.writeToParcel(this.mSettingsActivity, dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "CredentialProviderInfo {serviceInfo=" + this.mServiceInfo + ", isSystemProvider=" + this.mIsSystemProvider + ", isEnabled=" + this.mIsEnabled + ", isPrimary=" + this.mIsPrimary + ", overrideLabel=" + ((Object) this.mOverrideLabel) + ", settingsSubtitle=" + ((Object) this.mSettingsSubtitle) + ", settingsActivity=" + ((Object) this.mSettingsActivity) + ", capabilities=" + String.join(",", this.mCapabilities) + "}";
    }

    private CredentialProviderInfo(Parcel in) {
        this.mCapabilities = new ArrayList();
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = (ServiceInfo) in.readTypedObject(ServiceInfo.CREATOR);
        this.mIsSystemProvider = in.readBoolean();
        in.readStringList(this.mCapabilities);
        this.mIsEnabled = in.readBoolean();
        this.mIsPrimary = in.readBoolean();
        this.mOverrideLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mSettingsSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mSettingsActivity = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
    }

    public static final class Builder {
        private ServiceInfo mServiceInfo;
        private List<String> mCapabilities = new ArrayList();
        private boolean mIsSystemProvider = false;
        private CharSequence mSettingsSubtitle = null;
        private CharSequence mSettingsActivity = null;
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

        public Builder setSettingsActivity(CharSequence settingsActivity) {
            this.mSettingsActivity = settingsActivity;
            return this;
        }

        public Builder addCapabilities(List<String> capabilities) {
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
