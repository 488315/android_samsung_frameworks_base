package com.android.modules.utils;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class StringParceledListSlice extends BaseParceledListSlice<String> {
    public static final Parcelable.ClassLoaderCreator<StringParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<StringParceledListSlice>() { // from class: com.android.modules.utils.StringParceledListSlice.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Parcelable.Creator
        public StringParceledListSlice createFromParcel(Parcel parcel) {
            return new StringParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public StringParceledListSlice createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new StringParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public StringParceledListSlice[] newArray(int i) {
            return new StringParceledListSlice[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeParcelableCreator(String str, Parcel parcel) {
    }

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

    private StringParceledListSlice(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static StringParceledListSlice emptyList() {
        return new StringParceledListSlice(Collections.EMPTY_LIST);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeElement(String str, Parcel parcel, int i) {
        parcel.writeString(str);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    protected Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
        return Parcel.STRING_CREATOR;
    }
}
