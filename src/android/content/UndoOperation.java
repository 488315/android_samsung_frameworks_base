package android.content;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public abstract class UndoOperation<DATA> implements Parcelable {
    UndoOwner mOwner;

    public boolean allowMerge() {
        return true;
    }

    public abstract void commit();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean hasData() {
        return true;
    }

    public abstract void redo();

    public abstract void undo();

    public UndoOperation(UndoOwner undoOwner) {
        this.mOwner = undoOwner;
    }

    protected UndoOperation(Parcel parcel, ClassLoader classLoader) {
    }

    public UndoOwner getOwner() {
        return this.mOwner;
    }

    public DATA getOwnerData() {
        return (DATA) this.mOwner.getData();
    }

    public boolean matchOwner(UndoOwner undoOwner) {
        return undoOwner == getOwner();
    }
}
