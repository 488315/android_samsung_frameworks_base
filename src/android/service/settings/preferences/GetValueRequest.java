package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class GetValueRequest implements Parcelable {
    public static final Parcelable.Creator<GetValueRequest> CREATOR = new Parcelable.Creator<GetValueRequest>() { // from class: android.service.settings.preferences.GetValueRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetValueRequest createFromParcel(Parcel parcel) {
            return new GetValueRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetValueRequest[] newArray(int i) {
            return new GetValueRequest[i];
        }
    };
    private final String mPreferenceKey;
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

    private GetValueRequest(Builder builder) {
        this.mScreenKey = builder.mScreenKey;
        this.mPreferenceKey = builder.mPreferenceKey;
    }

    private GetValueRequest(Parcel parcel) {
        this.mScreenKey = (String) Objects.requireNonNull(parcel.readString8());
        this.mPreferenceKey = (String) Objects.requireNonNull(parcel.readString8());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mScreenKey);
        parcel.writeString8(this.mPreferenceKey);
    }

    public static final class Builder {
        private final String mPreferenceKey;
        private final String mScreenKey;

        public Builder(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("screenKey cannot be empty");
            }
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("preferenceKey cannot be empty");
            }
            this.mScreenKey = str;
            this.mPreferenceKey = str2;
        }

        public GetValueRequest build() {
            return new GetValueRequest(this);
        }
    }
}
