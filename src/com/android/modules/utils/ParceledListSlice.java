package com.android.modules.utils;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class ParceledListSlice<T extends Parcelable> extends BaseParceledListSlice<T> {
    public static final Parcelable.ClassLoaderCreator<ParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<ParceledListSlice>() { // from class: com.android.modules.utils.ParceledListSlice.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Parcelable.Creator
        public ParceledListSlice createFromParcel(Parcel parcel) {
            return new ParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public ParceledListSlice createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public ParceledListSlice[] newArray(int i) {
            return new ParceledListSlice[i];
        }
    };

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ List getList() {
        return super.getList();
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
        super.setInlineCountLimit(i);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice, android.os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public ParceledListSlice(List<T> list) {
        super(list);
    }

    private ParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static <T extends Parcelable> ParceledListSlice<T> emptyList() {
        return new ParceledListSlice<>(Collections.EMPTY_LIST);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        List list = getList();
        int iDescribeContents = 0;
        for (int i = 0; i < list.size(); i++) {
            iDescribeContents |= ((Parcelable) list.get(i)).describeContents();
        }
        return iDescribeContents;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeElement(T t, Parcel parcel, int i) {
        t.writeToParcel(parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeParcelableCreator(T t, Parcel parcel) {
        parcel.writeParcelableCreator(t);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    protected Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
        return parcel.readParcelableCreator(classLoader);
    }
}
