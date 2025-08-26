package android.content.pm;

import android.annotation.SystemApi;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class InstantAppIntentFilter implements Parcelable {
    public static final Parcelable.Creator<InstantAppIntentFilter> CREATOR = new Parcelable.Creator<InstantAppIntentFilter>() { // from class: android.content.pm.InstantAppIntentFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppIntentFilter createFromParcel(Parcel parcel) {
            return new InstantAppIntentFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppIntentFilter[] newArray(int i) {
            return new InstantAppIntentFilter[i];
        }
    };
    private final List<IntentFilter> mFilters;
    private final String mSplitName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InstantAppIntentFilter(String str, List<IntentFilter> list) {
        ArrayList arrayList = new ArrayList();
        this.mFilters = arrayList;
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.mSplitName = str;
        arrayList.addAll(list);
    }

    InstantAppIntentFilter(Parcel parcel) throws ClassNotFoundException, IOException {
        ArrayList arrayList = new ArrayList();
        this.mFilters = arrayList;
        this.mSplitName = parcel.readString();
        parcel.readList(arrayList, getClass().getClassLoader(), IntentFilter.class);
    }

    public String getSplitName() {
        return this.mSplitName;
    }

    public List<IntentFilter> getFilters() {
        return this.mFilters;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSplitName);
        parcel.writeList(this.mFilters);
    }
}
