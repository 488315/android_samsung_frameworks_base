package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator() { // from class: androidx.fragment.app.BackStackState.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new BackStackState[i];
        }
    };
    public final List mFragments;
    public final List mTransactions;

    public BackStackState(List<String> list, List<BackStackRecordState> list2) {
        this.mFragments = list;
        this.mTransactions = list2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mFragments);
        parcel.writeTypedList(this.mTransactions);
    }

    public BackStackState(Parcel parcel) {
        this.mFragments = parcel.createStringArrayList();
        this.mTransactions = parcel.createTypedArrayList(BackStackRecordState.CREATOR);
    }
}
