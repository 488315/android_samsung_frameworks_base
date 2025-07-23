package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextClassificationContext implements Parcelable {
    public static final Parcelable.Creator<TextClassificationContext> CREATOR = new Parcelable.Creator<TextClassificationContext>() { // from class: android.view.textclassifier.TextClassificationContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassificationContext createFromParcel(Parcel parcel) {
            return new TextClassificationContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassificationContext[] newArray(int i) {
            return new TextClassificationContext[i];
        }
    };
    private String mPackageName;
    private SystemTextClassifierMetadata mSystemTcMetadata;
    private final String mWidgetType;
    private final String mWidgetVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextClassificationContext(String str, String str2, String str3) {
        this.mPackageName = (String) Objects.requireNonNull(str);
        this.mWidgetType = (String) Objects.requireNonNull(str2);
        this.mWidgetVersion = str3;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    void setSystemTextClassifierMetadata(SystemTextClassifierMetadata systemTextClassifierMetadata) {
        this.mSystemTcMetadata = systemTextClassifierMetadata;
    }

    public SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
        return this.mSystemTcMetadata;
    }

    public String getWidgetType() {
        return this.mWidgetType;
    }

    public String getWidgetVersion() {
        return this.mWidgetVersion;
    }

    public String toString() {
        return String.format(Locale.US, "TextClassificationContext{packageName=%s, widgetType=%s, widgetVersion=%s, systemTcMetadata=%s}", this.mPackageName, this.mWidgetType, this.mWidgetVersion, this.mSystemTcMetadata);
    }

    public static final class Builder {
        private final String mPackageName;
        private final String mWidgetType;
        private String mWidgetVersion;

        public Builder(String str, String str2) {
            this.mPackageName = (String) Objects.requireNonNull(str);
            this.mWidgetType = (String) Objects.requireNonNull(str2);
        }

        public Builder setWidgetVersion(String str) {
            this.mWidgetVersion = str;
            return this;
        }

        public TextClassificationContext build() {
            return new TextClassificationContext(this.mPackageName, this.mWidgetType, this.mWidgetVersion);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mWidgetType);
        parcel.writeString(this.mWidgetVersion);
        parcel.writeParcelable(this.mSystemTcMetadata, i);
    }

    private TextClassificationContext(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mWidgetType = parcel.readString();
        this.mWidgetVersion = parcel.readString();
        this.mSystemTcMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
    }
}
