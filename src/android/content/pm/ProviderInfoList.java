package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ProviderInfoList implements Parcelable {
    public static final Parcelable.Creator<ProviderInfoList> CREATOR = new Parcelable.Creator<ProviderInfoList>() { // from class: android.content.pm.ProviderInfoList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProviderInfoList createFromParcel(Parcel parcel) {
            return new ProviderInfoList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProviderInfoList[] newArray(int i) {
            return new ProviderInfoList[i];
        }
    };
    private final List<ProviderInfo> mList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ProviderInfoList(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, ProviderInfo.CREATOR);
        this.mList = arrayList;
    }

    private ProviderInfoList(List<ProviderInfo> list) {
        this.mList = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean allowSquashing = parcel.allowSquashing();
        parcel.writeTypedList(this.mList, i);
        parcel.restoreAllowSquashing(allowSquashing);
    }

    public List<ProviderInfo> getList() {
        return this.mList;
    }

    public static ProviderInfoList fromList(List<ProviderInfo> list) {
        return new ProviderInfoList(list);
    }
}
