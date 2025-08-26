package android.view.contentcapture;

import android.annotation.NonNull;
import android.app.ActivityThread;
import android.content.LocusId;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DataShareRequest implements Parcelable {
    public static final Parcelable.Creator<DataShareRequest> CREATOR = new Parcelable.Creator<DataShareRequest>() { // from class: android.view.contentcapture.DataShareRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataShareRequest[] newArray(int i) {
            return new DataShareRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataShareRequest createFromParcel(Parcel parcel) {
            return new DataShareRequest(parcel);
        }
    };
    private final LocusId mLocusId;
    private final String mMimeType;
    private final String mPackageName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataShareRequest(LocusId locusId, String str) {
        Objects.requireNonNull(str);
        this.mPackageName = ActivityThread.currentActivityThread().getApplication().getPackageName();
        this.mLocusId = locusId;
        this.mMimeType = str;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String toString() {
        return "DataShareRequest { packageName = " + this.mPackageName + ", locusId = " + this.mLocusId + ", mimeType = " + this.mMimeType + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DataShareRequest dataShareRequest = (DataShareRequest) obj;
            if (Objects.equals(this.mPackageName, dataShareRequest.mPackageName) && Objects.equals(this.mLocusId, dataShareRequest.mLocusId) && Objects.equals(this.mMimeType, dataShareRequest.mMimeType)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mPackageName) + 31) * 31) + Objects.hashCode(this.mLocusId)) * 31) + Objects.hashCode(this.mMimeType);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mLocusId != null ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mPackageName);
        LocusId locusId = this.mLocusId;
        if (locusId != null) {
            parcel.writeTypedObject(locusId, i);
        }
        parcel.writeString(this.mMimeType);
    }

    DataShareRequest(Parcel parcel) {
        byte b = parcel.readByte();
        String string = parcel.readString();
        LocusId locusId = (b & 2) == 0 ? null : (LocusId) parcel.readTypedObject(LocusId.CREATOR);
        String string2 = parcel.readString();
        this.mPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mLocusId = locusId;
        this.mMimeType = string2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string2);
    }
}
