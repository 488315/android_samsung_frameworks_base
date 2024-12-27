package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class ProviderInfoList implements Parcelable {
    public static final Parcelable.Creator<ProviderInfoList> CREATOR =
            new Parcelable.Creator<
                    ProviderInfoList>() { // from class: android.content.pm.ProviderInfoList.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProviderInfoList createFromParcel(Parcel source) {
                    return new ProviderInfoList(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProviderInfoList[] newArray(int size) {
                    return new ProviderInfoList[size];
                }
            };
    private final List<ProviderInfo> mList;

    private ProviderInfoList(Parcel source) {
        ArrayList<ProviderInfo> list = new ArrayList<>();
        source.readTypedList(list, ProviderInfo.CREATOR);
        this.mList = list;
    }

    private ProviderInfoList(List<ProviderInfo> list) {
        this.mList = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        boolean prevAllowSquashing = dest.allowSquashing();
        dest.writeTypedList(this.mList, flags);
        dest.restoreAllowSquashing(prevAllowSquashing);
    }

    public List<ProviderInfo> getList() {
        return this.mList;
    }

    public static ProviderInfoList fromList(List<ProviderInfo> list) {
        return new ProviderInfoList(list);
    }
}
