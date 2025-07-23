package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class ClientRequestStats implements Parcelable {
    public static final Parcelable.Creator<ClientRequestStats> CREATOR = new Parcelable.Creator<ClientRequestStats>() { // from class: android.telephony.ClientRequestStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientRequestStats createFromParcel(Parcel parcel) {
            return new ClientRequestStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientRequestStats[] newArray(int i) {
            return new ClientRequestStats[i];
        }
    };
    private static final int REQUEST_HISTOGRAM_BUCKET_COUNT = 5;
    private String mCallingPackage;
    private long mCompletedRequestsCount;
    private long mCompletedRequestsWakelockTime;
    private long mPendingRequestsCount;
    private long mPendingRequestsWakelockTime;
    private SparseArray<TelephonyHistogram> mRequestHistograms;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ClientRequestStats(Parcel parcel) {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new SparseArray<>();
        readFromParcel(parcel);
    }

    public ClientRequestStats() {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new SparseArray<>();
    }

    public ClientRequestStats(ClientRequestStats clientRequestStats) {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new SparseArray<>();
        this.mCallingPackage = clientRequestStats.getCallingPackage();
        this.mCompletedRequestsCount = clientRequestStats.getCompletedRequestsCount();
        this.mCompletedRequestsWakelockTime = clientRequestStats.getCompletedRequestsWakelockTime();
        this.mPendingRequestsCount = clientRequestStats.getPendingRequestsCount();
        this.mPendingRequestsWakelockTime = clientRequestStats.getPendingRequestsWakelockTime();
        for (TelephonyHistogram telephonyHistogram : clientRequestStats.getRequestHistograms()) {
            this.mRequestHistograms.put(telephonyHistogram.getId(), telephonyHistogram);
        }
    }

    public String getCallingPackage() {
        return this.mCallingPackage;
    }

    public void setCallingPackage(String str) {
        this.mCallingPackage = str;
    }

    public long getCompletedRequestsWakelockTime() {
        return this.mCompletedRequestsWakelockTime;
    }

    public void addCompletedWakelockTime(long j) {
        this.mCompletedRequestsWakelockTime += j;
    }

    public long getPendingRequestsWakelockTime() {
        return this.mPendingRequestsWakelockTime;
    }

    public void setPendingRequestsWakelockTime(long j) {
        this.mPendingRequestsWakelockTime = j;
    }

    public long getCompletedRequestsCount() {
        return this.mCompletedRequestsCount;
    }

    public void incrementCompletedRequestsCount() {
        this.mCompletedRequestsCount++;
    }

    public long getPendingRequestsCount() {
        return this.mPendingRequestsCount;
    }

    public void setPendingRequestsCount(long j) {
        this.mPendingRequestsCount = j;
    }

    public List<TelephonyHistogram> getRequestHistograms() {
        ArrayList arrayList;
        synchronized (this.mRequestHistograms) {
            arrayList = new ArrayList(this.mRequestHistograms.size());
            for (int i = 0; i < this.mRequestHistograms.size(); i++) {
                arrayList.add(new TelephonyHistogram(this.mRequestHistograms.valueAt(i)));
            }
        }
        return arrayList;
    }

    public void updateRequestHistograms(int i, int i2) {
        synchronized (this.mRequestHistograms) {
            TelephonyHistogram telephonyHistogram = this.mRequestHistograms.get(i);
            if (telephonyHistogram == null) {
                telephonyHistogram = new TelephonyHistogram(1, i, 5);
                this.mRequestHistograms.put(i, telephonyHistogram);
            }
            telephonyHistogram.addTimeTaken(i2);
        }
    }

    public String toString() {
        return "ClientRequestStats{mCallingPackage='" + this.mCallingPackage + "', mCompletedRequestsWakelockTime=" + this.mCompletedRequestsWakelockTime + ", mCompletedRequestsCount=" + this.mCompletedRequestsCount + ", mPendingRequestsWakelockTime=" + this.mPendingRequestsWakelockTime + ", mPendingRequestsCount=" + this.mPendingRequestsCount + '}';
    }

    public void readFromParcel(Parcel parcel) {
        this.mCallingPackage = parcel.readString();
        this.mCompletedRequestsWakelockTime = parcel.readLong();
        this.mCompletedRequestsCount = parcel.readLong();
        this.mPendingRequestsWakelockTime = parcel.readLong();
        this.mPendingRequestsCount = parcel.readLong();
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, TelephonyHistogram.CREATOR);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            TelephonyHistogram telephonyHistogram = (TelephonyHistogram) it.next();
            this.mRequestHistograms.put(telephonyHistogram.getId(), telephonyHistogram);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCallingPackage);
        parcel.writeLong(this.mCompletedRequestsWakelockTime);
        parcel.writeLong(this.mCompletedRequestsCount);
        parcel.writeLong(this.mPendingRequestsWakelockTime);
        parcel.writeLong(this.mPendingRequestsCount);
        parcel.writeTypedList(getRequestHistograms());
    }
}
