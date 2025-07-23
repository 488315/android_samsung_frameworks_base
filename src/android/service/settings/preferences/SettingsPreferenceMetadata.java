package android.service.settings.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class SettingsPreferenceMetadata implements Parcelable {
    public static final Parcelable.Creator<SettingsPreferenceMetadata> CREATOR = new Parcelable.Creator<SettingsPreferenceMetadata>() { // from class: android.service.settings.preferences.SettingsPreferenceMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SettingsPreferenceMetadata createFromParcel(Parcel parcel) {
            return new SettingsPreferenceMetadata(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SettingsPreferenceMetadata[] newArray(int i) {
            return new SettingsPreferenceMetadata[i];
        }
    };
    public static final int DEEPLINK_ONLY = 2;
    public static final int EXPECT_POST_CONFIRMATION = 1;
    public static final int NO_DIRECT_ACCESS = 3;
    public static final int NO_SENSITIVITY = 0;
    private final boolean mAvailable;
    private final List<String> mBreadcrumbs;
    private final boolean mEnabled;
    private final Bundle mExtras;
    private final String mKey;
    private final Intent mLaunchIntent;
    private final List<String> mReadPermissions;
    private final boolean mRestricted;
    private final String mScreenKey;
    private final int mSensitivity;
    private final String mSummary;
    private final String mTitle;
    private final boolean mWritable;
    private final List<String> mWritePermissions;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WriteSensitivity {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getScreenKey() {
        return this.mScreenKey;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getSummary() {
        return this.mSummary;
    }

    public List<String> getBreadcrumbs() {
        return this.mBreadcrumbs;
    }

    public List<String> getReadPermissions() {
        return this.mReadPermissions;
    }

    public List<String> getWritePermissions() {
        return this.mWritePermissions;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isAvailable() {
        return this.mAvailable;
    }

    public boolean isWritable() {
        return this.mWritable;
    }

    public boolean isRestricted() {
        return this.mRestricted;
    }

    public int getWriteSensitivity() {
        return this.mSensitivity;
    }

    public Intent getLaunchIntent() {
        return this.mLaunchIntent;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    private SettingsPreferenceMetadata(Builder builder) {
        this.mKey = builder.mKey;
        this.mScreenKey = builder.mScreenKey;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mBreadcrumbs = builder.mBreadcrumbs;
        this.mReadPermissions = builder.mReadPermissions;
        this.mWritePermissions = builder.mWritePermissions;
        this.mEnabled = builder.mEnabled;
        this.mAvailable = builder.mAvailable;
        this.mWritable = builder.mWritable;
        this.mRestricted = builder.mRestricted;
        this.mSensitivity = builder.mSensitivity;
        this.mLaunchIntent = builder.mLaunchIntent;
        this.mExtras = (Bundle) Objects.requireNonNullElseGet(builder.mExtras, new Supplier() { // from class: android.service.settings.preferences.SettingsPreferenceMetadata$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return new Bundle();
            }
        });
    }

    private SettingsPreferenceMetadata(Parcel parcel) {
        this.mKey = (String) Objects.requireNonNull(parcel.readString8());
        this.mScreenKey = (String) Objects.requireNonNull(parcel.readString8());
        this.mTitle = parcel.readString8();
        this.mSummary = parcel.readString8();
        ArrayList arrayList = new ArrayList();
        this.mBreadcrumbs = arrayList;
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        this.mReadPermissions = arrayList2;
        parcel.readStringList(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        this.mWritePermissions = arrayList3;
        parcel.readStringList(arrayList3);
        this.mEnabled = parcel.readBoolean();
        this.mAvailable = parcel.readBoolean();
        this.mWritable = parcel.readBoolean();
        this.mRestricted = parcel.readBoolean();
        this.mSensitivity = parcel.readInt();
        this.mLaunchIntent = (Intent) parcel.readParcelable(Intent.class.getClassLoader(), Intent.class);
        this.mExtras = (Bundle) Objects.requireNonNullElseGet(parcel.readBundle(), new Supplier() { // from class: android.service.settings.preferences.SettingsPreferenceMetadata$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return new Bundle();
            }
        });
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mKey);
        parcel.writeString8(this.mScreenKey);
        parcel.writeString8(this.mTitle);
        parcel.writeString8(this.mSummary);
        parcel.writeStringList(this.mBreadcrumbs);
        parcel.writeStringList(this.mReadPermissions);
        parcel.writeStringList(this.mWritePermissions);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeBoolean(this.mAvailable);
        parcel.writeBoolean(this.mWritable);
        parcel.writeBoolean(this.mRestricted);
        parcel.writeInt(this.mSensitivity);
        parcel.writeParcelable(this.mLaunchIntent, i);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private Bundle mExtras;
        private final String mKey;
        private Intent mLaunchIntent;
        private final String mScreenKey;
        private String mSummary;
        private String mTitle;
        private List<String> mBreadcrumbs = Collections.EMPTY_LIST;
        private List<String> mReadPermissions = Collections.EMPTY_LIST;
        private List<String> mWritePermissions = Collections.EMPTY_LIST;
        private boolean mEnabled = false;
        private boolean mAvailable = false;
        private boolean mWritable = false;
        private boolean mRestricted = false;
        private int mSensitivity = 3;

        public Builder(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("screenKey cannot be empty");
            }
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("key cannot be empty");
            }
            this.mScreenKey = str;
            this.mKey = str2;
        }

        public Builder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public Builder setSummary(String str) {
            this.mSummary = str;
            return this;
        }

        public Builder setBreadcrumbs(List<String> list) {
            this.mBreadcrumbs = list;
            return this;
        }

        public Builder setReadPermissions(List<String> list) {
            this.mReadPermissions = list;
            return this;
        }

        public Builder setWritePermissions(List<String> list) {
            this.mWritePermissions = list;
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public Builder setAvailable(boolean z) {
            this.mAvailable = z;
            return this;
        }

        public Builder setWritable(boolean z) {
            this.mWritable = z;
            return this;
        }

        public Builder setRestricted(boolean z) {
            this.mRestricted = z;
            return this;
        }

        public Builder setWriteSensitivity(int i) {
            this.mSensitivity = i;
            return this;
        }

        public Builder setLaunchIntent(Intent intent) {
            this.mLaunchIntent = intent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public SettingsPreferenceMetadata build() {
            if (this.mSensitivity == 3) {
                this.mLaunchIntent = null;
            }
            return new SettingsPreferenceMetadata(this);
        }
    }
}
