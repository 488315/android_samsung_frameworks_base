package android.content.p002pm;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class StringParceledListSlice extends BaseParceledListSlice<String> {
    public static final Parcelable.ClassLoaderCreator<StringParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<StringParceledListSlice>() { // from class: android.content.pm.StringParceledListSlice.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Parcelable.Creator
        public StringParceledListSlice createFromParcel(Parcel parcel) {
            return new StringParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public StringParceledListSlice createFromParcel(Parcel in, ClassLoader loader) {
            return new StringParceledListSlice(in, loader);
        }

        @Override // android.os.Parcelable.Creator
        public StringParceledListSlice[] newArray(int size) {
            return new StringParceledListSlice[size];
        }
    };

    @Override // android.content.p002pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ List<String> getList() {
        return super.getList();
    }

    @Override // android.content.p002pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
        super.setInlineCountLimit(i);
    }

    @Override // android.content.p002pm.BaseParceledListSlice, android.p009os.Parcelable
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

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.p002pm.BaseParceledListSlice
    public void writeElement(String parcelable, Parcel reply, int callFlags) {
        reply.writeString(parcelable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.p002pm.BaseParceledListSlice
    public void writeParcelableCreator(String parcelable, Parcel dest) {
    }

    @Override // android.content.p002pm.BaseParceledListSlice
    protected Parcelable.Creator<?> readParcelableCreator(Parcel from, ClassLoader loader) {
        return Parcel.STRING_CREATOR;
    }
}
