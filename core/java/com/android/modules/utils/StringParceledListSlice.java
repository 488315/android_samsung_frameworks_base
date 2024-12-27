package com.android.modules.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

public class StringParceledListSlice extends BaseParceledListSlice<String> {
    public static final Parcelable.ClassLoaderCreator<StringParceledListSlice> CREATOR =
            new Parcelable.ClassLoaderCreator<
                    StringParceledListSlice>() { // from class:
                                                 // com.android.modules.utils.StringParceledListSlice.1
                @Override // android.os.Parcelable.Creator
                public StringParceledListSlice createFromParcel(Parcel parcel) {
                    return new StringParceledListSlice(parcel, null);
                }

                @Override // android.os.Parcelable.ClassLoaderCreator
                public StringParceledListSlice createFromParcel(Parcel in, ClassLoader loader) {
                    return new StringParceledListSlice(in, loader);
                }

                @Override // android.os.Parcelable.Creator
                public StringParceledListSlice[] newArray(int size) {
                    return new StringParceledListSlice[size];
                }
            };

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ List<String> getList() {
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

    public StringParceledListSlice(List<String> list) {
        super(list);
    }

    private StringParceledListSlice(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    public static StringParceledListSlice emptyList() {
        return new StringParceledListSlice(Collections.emptyList());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeElement(String parcelable, Parcel reply, int callFlags) {
        reply.writeString(parcelable);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeParcelableCreator(String parcelable, Parcel dest) {}

    @Override // com.android.modules.utils.BaseParceledListSlice
    protected Parcelable.Creator<?> readParcelableCreator(Parcel from, ClassLoader loader) {
        return Parcel.STRING_CREATOR;
    }
}
