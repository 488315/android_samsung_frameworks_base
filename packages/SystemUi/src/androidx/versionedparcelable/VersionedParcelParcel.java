package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import androidx.collection.SimpleArrayMap;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VersionedParcelParcel extends VersionedParcel {
    public int mCurrentField;
    public final int mEnd;
    public int mFieldId;
    public int mNextRead;
    public final int mOffset;
    public final Parcel mParcel;
    public final SparseIntArray mPositionLookup;
    public final String mPrefix;

    public VersionedParcelParcel(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new SimpleArrayMap(), new SimpleArrayMap(), new SimpleArrayMap());
    }

    public final void closeField() {
        int i = this.mCurrentField;
        if (i >= 0) {
            int i2 = this.mPositionLookup.get(i);
            Parcel parcel = this.mParcel;
            int dataPosition = parcel.dataPosition();
            parcel.setDataPosition(i2);
            parcel.writeInt(dataPosition - i2);
            parcel.setDataPosition(dataPosition);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final VersionedParcelParcel createSubParcel() {
        Parcel parcel = this.mParcel;
        int dataPosition = parcel.dataPosition();
        int i = this.mNextRead;
        if (i == this.mOffset) {
            i = this.mEnd;
        }
        return new VersionedParcelParcel(parcel, dataPosition, i, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), this.mPrefix, "  "), this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final boolean readBoolean() {
        return this.mParcel.readInt() != 0;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final Bundle readBundle() {
        return this.mParcel.readBundle(VersionedParcelParcel.class.getClassLoader());
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final byte[] readByteArray() {
        Parcel parcel = this.mParcel;
        int readInt = parcel.readInt();
        if (readInt < 0) {
            return null;
        }
        byte[] bArr = new byte[readInt];
        parcel.readByteArray(bArr);
        return bArr;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final CharSequence readCharSequence() {
        return (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.mParcel);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final boolean readField(int i) {
        while (this.mNextRead < this.mEnd) {
            int i2 = this.mFieldId;
            if (i2 == i) {
                return true;
            }
            if (String.valueOf(i2).compareTo(String.valueOf(i)) > 0) {
                return false;
            }
            int i3 = this.mNextRead;
            Parcel parcel = this.mParcel;
            parcel.setDataPosition(i3);
            int readInt = parcel.readInt();
            this.mFieldId = parcel.readInt();
            this.mNextRead += readInt;
        }
        return this.mFieldId == i;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final int readInt() {
        return this.mParcel.readInt();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final long readLong() {
        return this.mParcel.readLong();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final Parcelable readParcelable() {
        return this.mParcel.readParcelable(VersionedParcelParcel.class.getClassLoader());
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final String readString() {
        return this.mParcel.readString();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final IBinder readStrongBinder() {
        return this.mParcel.readStrongBinder();
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void setOutputField(int i) {
        closeField();
        this.mCurrentField = i;
        this.mPositionLookup.put(i, this.mParcel.dataPosition());
        writeInt(0);
        writeInt(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeBoolean(boolean z) {
        this.mParcel.writeInt(z ? 1 : 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeBundle(Bundle bundle) {
        this.mParcel.writeBundle(bundle);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeByteArray(byte[] bArr) {
        Parcel parcel = this.mParcel;
        if (bArr == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(bArr);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeCharSequence(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this.mParcel, 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeInt(int i) {
        this.mParcel.writeInt(i);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeLong(long j) {
        this.mParcel.writeLong(j);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeParcelable(Parcelable parcelable) {
        this.mParcel.writeParcelable(parcelable, 0);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeString(String str) {
        this.mParcel.writeString(str);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public final void writeStrongBinder(IBinder iBinder) {
        this.mParcel.writeStrongBinder(iBinder);
    }

    private VersionedParcelParcel(Parcel parcel, int i, int i2, String str, SimpleArrayMap simpleArrayMap, SimpleArrayMap simpleArrayMap2, SimpleArrayMap simpleArrayMap3) {
        super(simpleArrayMap, simpleArrayMap2, simpleArrayMap3);
        this.mPositionLookup = new SparseIntArray();
        this.mCurrentField = -1;
        this.mFieldId = -1;
        this.mParcel = parcel;
        this.mOffset = i;
        this.mEnd = i2;
        this.mNextRead = i;
        this.mPrefix = str;
    }
}
