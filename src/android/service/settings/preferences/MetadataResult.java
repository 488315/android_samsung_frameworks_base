package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class MetadataResult implements Parcelable {
    public static final Parcelable.Creator<MetadataResult> CREATOR = new Parcelable.Creator<MetadataResult>() { // from class: android.service.settings.preferences.MetadataResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetadataResult createFromParcel(Parcel parcel) {
            return new MetadataResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetadataResult[] newArray(int i) {
            return new MetadataResult[i];
        }
    };
    public static final int RESULT_INTERNAL_ERROR = 2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_UNSUPPORTED = 1;
    private final List<SettingsPreferenceMetadata> mMetadataList;
    private final int mResultCode;

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

    public List<SettingsPreferenceMetadata> getMetadataList() {
        return this.mMetadataList;
    }

    private MetadataResult(Builder builder) {
        this.mResultCode = builder.mResultCode;
        this.mMetadataList = builder.mMetadataList;
    }

    private MetadataResult(Parcel parcel) {
        this.mResultCode = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.mMetadataList = arrayList;
        parcel.readTypedList(arrayList, SettingsPreferenceMetadata.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeTypedList(this.mMetadataList, i);
    }

    public static final class Builder {
        private List<SettingsPreferenceMetadata> mMetadataList = Collections.EMPTY_LIST;
        private final int mResultCode;

        public Builder(int i) {
            this.mResultCode = i;
        }

        public Builder setMetadataList(List<SettingsPreferenceMetadata> list) {
            this.mMetadataList = list;
            return this;
        }

        public MetadataResult build() {
            return new MetadataResult(this);
        }
    }
}
