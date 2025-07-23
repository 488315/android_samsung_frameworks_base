package android.app.backup;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public class BackupProgress implements Parcelable {
    public static final Parcelable.Creator<BackupProgress> CREATOR = new Parcelable.Creator<BackupProgress>() { // from class: android.app.backup.BackupProgress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackupProgress createFromParcel(Parcel parcel) {
            return new BackupProgress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackupProgress[] newArray(int i) {
            return new BackupProgress[i];
        }
    };
    public final long bytesExpected;
    public final long bytesTransferred;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BackupProgress(long j, long j2) {
        this.bytesExpected = j;
        this.bytesTransferred = j2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.bytesExpected);
        parcel.writeLong(this.bytesTransferred);
    }

    private BackupProgress(Parcel parcel) {
        this.bytesExpected = parcel.readLong();
        this.bytesTransferred = parcel.readLong();
    }
}
