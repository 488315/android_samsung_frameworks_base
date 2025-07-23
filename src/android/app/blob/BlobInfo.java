package android.app.blob;

import android.app.AppGlobals;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Formatter;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class BlobInfo implements Parcelable {
    public static final Parcelable.Creator<BlobInfo> CREATOR = new Parcelable.Creator<BlobInfo>() { // from class: android.app.blob.BlobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlobInfo createFromParcel(Parcel parcel) {
            return new BlobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlobInfo[] newArray(int i) {
            return new BlobInfo[i];
        }
    };
    private final long mExpiryTimeMs;
    private final long mId;
    private final CharSequence mLabel;
    private final List<LeaseInfo> mLeaseInfos;
    private final long mSizeBytes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BlobInfo(long j, long j2, CharSequence charSequence, long j3, List<LeaseInfo> list) {
        this.mId = j;
        this.mExpiryTimeMs = j2;
        this.mLabel = charSequence;
        this.mSizeBytes = j3;
        this.mLeaseInfos = list;
    }

    private BlobInfo(Parcel parcel) {
        this.mId = parcel.readLong();
        this.mExpiryTimeMs = parcel.readLong();
        this.mLabel = parcel.readCharSequence();
        this.mSizeBytes = parcel.readLong();
        this.mLeaseInfos = parcel.readArrayList(null);
    }

    public long getId() {
        return this.mId;
    }

    public long getExpiryTimeMs() {
        return this.mExpiryTimeMs;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public long getSizeBytes() {
        return this.mSizeBytes;
    }

    public List<LeaseInfo> getLeases() {
        return Collections.unmodifiableList(this.mLeaseInfos);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeLong(this.mExpiryTimeMs);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeLong(this.mSizeBytes);
        parcel.writeList(this.mLeaseInfos);
    }

    public String toString() {
        return toShortString();
    }

    private String toShortString() {
        return "BlobInfo {id: " + this.mId + ",expiryMs: " + this.mExpiryTimeMs + ",label: " + ((Object) this.mLabel) + ",size: " + formatBlobSize(this.mSizeBytes) + ",leases: " + LeaseInfo.toShortString(this.mLeaseInfos) + ",}";
    }

    private static String formatBlobSize(long j) {
        return Formatter.formatFileSize(AppGlobals.getInitialApplication(), j, 8);
    }
}
