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
        public CredentialProviderInfo[] newArray(int i) {
            return new CredentialProviderInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialProviderInfo createFromParcel(Parcel parcel) {
            return new CredentialProviderInfo(parcel);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CredentialProviderInfo(Builder builder) {
        ArrayList arrayList = new ArrayList();
        this.mCapabilities = arrayList;
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = builder.mServiceInfo;
        arrayList.addAll(builder.mCapabilities);
        this.mIsSystemProvider = builder.mIsSystemProvider;
        this.mSettingsSubtitle = builder.mSettingsSubtitle;
        this.mIsEnabled = builder.mIsEnabled;
        this.mIsPrimary = builder.mIsPrimary;
        this.mOverrideLabel = builder.mOverrideLabel;
        this.mSettingsActivity = builder.mSettingsActivity;
    }

    public boolean hasCapability(String str) {
        return this.mCapabilities.contains(str);
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
        return charSequence != null ? charSequence : this.mServiceInfo.loadSafeLabel(context.getPackageManager());
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
        if (Flags.settingsActivityEnabled()) {
            return this.mSettingsActivity;
        }
        return null;
    }

    public ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mServiceInfo, i);
        parcel.writeBoolean(this.mIsSystemProvider);
        parcel.writeStringList(this.mCapabilities);
        parcel.writeBoolean(this.mIsEnabled);
        parcel.writeBoolean(this.mIsPrimary);
        TextUtils.writeToParcel(this.mOverrideLabel, parcel, i);
        TextUtils.writeToParcel(this.mSettingsSubtitle, parcel, i);
        TextUtils.writeToParcel(this.mSettingsActivity, parcel, i);
    }

    public String toString() {
        return "CredentialProviderInfo {serviceInfo=" + this.mServiceInfo + ", isSystemProvider=" + this.mIsSystemProvider + ", isEnabled=" + this.mIsEnabled + ", isPrimary=" + this.mIsPrimary + ", overrideLabel=" + ((Object) this.mOverrideLabel) + ", settingsSubtitle=" + ((Object) this.mSettingsSubtitle) + ", settingsActivity=" + ((Object) this.mSettingsActivity) + ", capabilities=" + String.join(",", this.mCapabilities) + "}";
    }

    private CredentialProviderInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mCapabilities = arrayList;
        this.mSettingsSubtitle = null;
        this.mSettingsActivity = null;
        this.mServiceInfo = (ServiceInfo) parcel.readTypedObject(ServiceInfo.CREATOR);
        this.mIsSystemProvider = parcel.readBoolean();
        parcel.readStringList(arrayList);
        this.mIsEnabled = parcel.readBoolean();
        this.mIsPrimary = parcel.readBoolean();
        this.mOverrideLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSettingsSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSettingsActivity = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
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

        public Builder setSystemProvider(boolean z) {
            this.mIsSystemProvider = z;
            return this;
        }

        public Builder setOverrideLabel(CharSequence charSequence) {
            this.mOverrideLabel = charSequence;
            return this;
        }

        public Builder setSettingsSubtitle(CharSequence charSequence) {
            this.mSettingsSubtitle = charSequence;
            return this;
        }

        public Builder setSettingsActivity(CharSequence charSequence) {
            this.mSettingsActivity = charSequence;
            return this;
        }

        public Builder addCapabilities(List<String> list) {
            this.mCapabilities.addAll(list);
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public Builder setPrimary(boolean z) {
            this.mIsPrimary = z;
            return this;
        }

        public CredentialProviderInfo build() {
            return new CredentialProviderInfo(this);
        }
    }
}
