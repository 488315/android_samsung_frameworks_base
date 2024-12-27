package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BroadcastResponseStatsList implements Parcelable {
    public static final Parcelable.Creator<BroadcastResponseStatsList> CREATOR =
            new Parcelable.Creator<BroadcastResponseStatsList>() { // from class:
                // android.app.usage.BroadcastResponseStatsList.1
                @Override // android.os.Parcelable.Creator
                public BroadcastResponseStatsList createFromParcel(Parcel source) {
                    return new BroadcastResponseStatsList(source);
                }

                @Override // android.os.Parcelable.Creator
                public BroadcastResponseStatsList[] newArray(int size) {
                    return new BroadcastResponseStatsList[size];
                }
            };
    private List<BroadcastResponseStats> mBroadcastResponseStats;

    public BroadcastResponseStatsList(List<BroadcastResponseStats> broadcastResponseStats) {
        this.mBroadcastResponseStats = broadcastResponseStats;
    }

    private BroadcastResponseStatsList(Parcel in) {
        this.mBroadcastResponseStats = new ArrayList();
        byte[] bytes = in.readBlob();
        Parcel data = Parcel.obtain();
        try {
            data.unmarshall(bytes, 0, bytes.length);
            data.setDataPosition(0);
            data.readTypedList(this.mBroadcastResponseStats, BroadcastResponseStats.CREATOR);
        } finally {
            data.recycle();
        }
    }

    public List<BroadcastResponseStats> getList() {
        return this.mBroadcastResponseStats == null
                ? Collections.emptyList()
                : this.mBroadcastResponseStats;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Parcel data = Parcel.obtain();
        try {
            data.writeTypedList(this.mBroadcastResponseStats);
            dest.writeBlob(data.marshall());
        } finally {
            data.recycle();
        }
    }
}
