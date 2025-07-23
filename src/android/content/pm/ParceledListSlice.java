package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class ParceledListSlice<T extends Parcelable> extends BaseParceledListSlice<T> {
    public static final Parcelable.ClassLoaderCreator<ParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<ParceledListSlice>() { // from class: android.content.pm.ParceledListSlice.1
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

    @Override // android.content.pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ List getList() {
        return super.getList();
    }

    @Override // android.content.pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
        super.setInlineCountLimit(i);
    }

    @Override // android.content.pm.BaseParceledListSlice, android.os.Parcelable
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
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i |= ((Parcelable) list.get(i2)).describeContents();
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.pm.BaseParceledListSlice
    public void writeElement(T t, Parcel parcel, int i) {
        t.writeToParcel(parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.pm.BaseParceledListSlice
    public void writeParcelableCreator(T t, Parcel parcel) {
        parcel.writeParcelableCreator(t);
    }

    @Override // android.content.pm.BaseParceledListSlice
    protected Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
        return parcel.readParcelableCreator(classLoader);
    }
}
