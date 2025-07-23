package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class GetValueResult implements Parcelable {
    public static final Parcelable.Creator<GetValueResult> CREATOR = new Parcelable.Creator<GetValueResult>() { // from class: android.service.settings.preferences.GetValueResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetValueResult createFromParcel(Parcel parcel) {
            return new GetValueResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetValueResult[] newArray(int i) {
            return new GetValueResult[i];
        }
    };
    public static final int RESULT_DISALLOW = 4;
    public static final int RESULT_INTERNAL_ERROR = 6;
    public static final int RESULT_INVALID_REQUEST = 5;
    public static final int RESULT_OK = 0;
    public static final int RESULT_REQUIRE_APP_PERMISSION = 3;
    public static final int RESULT_UNAVAILABLE = 2;
    public static final int RESULT_UNSUPPORTED = 1;
    private final SettingsPreferenceMetadata mMetadata;
    private final int mResultCode;
    private final SettingsPreferenceValue mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public SettingsPreferenceValue getValue() {
        return this.mValue;
    }

    public SettingsPreferenceMetadata getMetadata() {
        return this.mMetadata;
    }

    private GetValueResult(Builder builder) {
        this.mResultCode = builder.mResultCode;
        this.mValue = builder.mValue;
        this.mMetadata = builder.mMetadata;
    }

    private GetValueResult(Parcel parcel) {
        this.mResultCode = parcel.readInt();
        this.mValue = (SettingsPreferenceValue) parcel.readParcelable(SettingsPreferenceValue.class.getClassLoader(), SettingsPreferenceValue.class);
        this.mMetadata = (SettingsPreferenceMetadata) parcel.readParcelable(SettingsPreferenceMetadata.class.getClassLoader(), SettingsPreferenceMetadata.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeParcelable(this.mValue, i);
        parcel.writeParcelable(this.mMetadata, i);
    }

    public static final class Builder {
        private SettingsPreferenceMetadata mMetadata;
        private final int mResultCode;
        private SettingsPreferenceValue mValue;

        public Builder(int i) {
            this.mResultCode = i;
        }

        public Builder setValue(SettingsPreferenceValue settingsPreferenceValue) {
            this.mValue = settingsPreferenceValue;
            return this;
        }

        public Builder setMetadata(SettingsPreferenceMetadata settingsPreferenceMetadata) {
            this.mMetadata = settingsPreferenceMetadata;
            return this;
        }

        public GetValueResult build() {
            return new GetValueResult(this);
        }
    }
}
