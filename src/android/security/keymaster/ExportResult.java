package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ExportResult implements Parcelable {
    public static final Parcelable.Creator<ExportResult> CREATOR = new Parcelable.Creator<ExportResult>() { // from class: android.security.keymaster.ExportResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExportResult createFromParcel(Parcel parcel) {
            return new ExportResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExportResult[] newArray(int i) {
            return new ExportResult[i];
        }
    };
    public final byte[] exportData;
    public final int resultCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExportResult(int i) {
        this.resultCode = i;
        this.exportData = new byte[0];
    }

    protected ExportResult(Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.exportData = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.resultCode);
        parcel.writeByteArray(this.exportData);
    }
}
