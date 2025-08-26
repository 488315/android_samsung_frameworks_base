package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class BroadcastResponseStatsList implements Parcelable {
    public static final Parcelable.Creator<BroadcastResponseStatsList> CREATOR = new Parcelable.Creator<BroadcastResponseStatsList>() { // from class: android.app.usage.BroadcastResponseStatsList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastResponseStatsList createFromParcel(Parcel parcel) {
            return new BroadcastResponseStatsList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastResponseStatsList[] newArray(int i) {
            return new BroadcastResponseStatsList[i];
        }
    };
    private List<BroadcastResponseStats> mBroadcastResponseStats;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BroadcastResponseStatsList(List<BroadcastResponseStats> list) {
        this.mBroadcastResponseStats = list;
    }

    private BroadcastResponseStatsList(Parcel parcel) {
        this.mBroadcastResponseStats = new ArrayList();
        byte[] blob = parcel.readBlob();
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.unmarshall(blob, 0, blob.length);
            parcelObtain.setDataPosition(0);
            parcelObtain.readTypedList(this.mBroadcastResponseStats, BroadcastResponseStats.CREATOR);
        } finally {
            parcelObtain.recycle();
        }
    }

    public List<BroadcastResponseStats> getList() {
        List<BroadcastResponseStats> list = this.mBroadcastResponseStats;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeTypedList(this.mBroadcastResponseStats);
            parcel.writeBlob(parcelObtain.marshall());
        } finally {
            parcelObtain.recycle();
        }
    }
}
