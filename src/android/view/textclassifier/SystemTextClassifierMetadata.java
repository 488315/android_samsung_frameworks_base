package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SystemTextClassifierMetadata implements Parcelable {
    public static final Parcelable.Creator<SystemTextClassifierMetadata> CREATOR = new Parcelable.Creator<SystemTextClassifierMetadata>() { // from class: android.view.textclassifier.SystemTextClassifierMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemTextClassifierMetadata createFromParcel(Parcel parcel) {
            return SystemTextClassifierMetadata.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemTextClassifierMetadata[] newArray(int i) {
            return new SystemTextClassifierMetadata[i];
        }
    };
    private final String mCallingPackageName;
    private final boolean mUseDefaultTextClassifier;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SystemTextClassifierMetadata(String str, int i, boolean z) {
        Objects.requireNonNull(str);
        this.mCallingPackageName = str;
        this.mUserId = i;
        this.mUseDefaultTextClassifier = z;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public String getCallingPackageName() {
        return this.mCallingPackageName;
    }

    public boolean useDefaultTextClassifier() {
        return this.mUseDefaultTextClassifier;
    }

    public String toString() {
        return String.format(Locale.US, "SystemTextClassifierMetadata {callingPackageName=%s, userId=%d, useDefaultTextClassifier=%b}", this.mCallingPackageName, Integer.valueOf(this.mUserId), Boolean.valueOf(this.mUseDefaultTextClassifier));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SystemTextClassifierMetadata readFromParcel(Parcel parcel) {
        return new SystemTextClassifierMetadata(parcel.readString(), parcel.readInt(), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCallingPackageName);
        parcel.writeInt(this.mUserId);
        parcel.writeBoolean(this.mUseDefaultTextClassifier);
    }
}
