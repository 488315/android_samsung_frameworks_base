package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SetValueRequest implements Parcelable {
    public static final Parcelable.Creator<SetValueRequest> CREATOR = new Parcelable.Creator<SetValueRequest>() { // from class: android.service.settings.preferences.SetValueRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetValueRequest createFromParcel(Parcel parcel) {
            return new SetValueRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetValueRequest[] newArray(int i) {
            return new SetValueRequest[i];
        }
    };
    private final String mPreferenceKey;
    private final SettingsPreferenceValue mPreferenceValue;
    private final String mScreenKey;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getScreenKey() {
        return this.mScreenKey;
    }

    public String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    public SettingsPreferenceValue getPreferenceValue() {
        return this.mPreferenceValue;
    }

    private SetValueRequest(Builder builder) {
        this.mScreenKey = builder.mScreenKey;
        this.mPreferenceKey = builder.mPreferenceKey;
        this.mPreferenceValue = builder.mPreferenceValue;
    }

    private SetValueRequest(Parcel parcel) {
        this.mScreenKey = (String) Objects.requireNonNull(parcel.readString8());
        this.mPreferenceKey = (String) Objects.requireNonNull(parcel.readString8());
        this.mPreferenceValue = (SettingsPreferenceValue) Objects.requireNonNull((SettingsPreferenceValue) parcel.readParcelable(SettingsPreferenceValue.class.getClassLoader(), SettingsPreferenceValue.class));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mScreenKey);
        parcel.writeString8(this.mPreferenceKey);
        parcel.writeParcelable(this.mPreferenceValue, i);
    }

    public static final class Builder {
        private final String mPreferenceKey;
        private final SettingsPreferenceValue mPreferenceValue;
        private final String mScreenKey;

        public Builder(String str, String str2, SettingsPreferenceValue settingsPreferenceValue) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("screenKey cannot be empty");
            }
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("preferenceKey cannot be empty");
            }
            this.mScreenKey = str;
            this.mPreferenceKey = str2;
            this.mPreferenceValue = settingsPreferenceValue;
        }

        public SetValueRequest build() {
            return new SetValueRequest(this);
        }
    }
}
