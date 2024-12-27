package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

import java.util.Objects;

public class CoreStatesChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<CoreStatesChangeItem> CREATOR =
            new Parcelable.Creator<CoreStatesChangeItem>() { // from class:
                // android.app.servertransaction.CoreStatesChangeItem.1
                @Override // android.os.Parcelable.Creator
                public CoreStatesChangeItem createFromParcel(Parcel in) {
                    return new CoreStatesChangeItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public CoreStatesChangeItem[] newArray(int size) {
                    return new CoreStatesChangeItem[size];
                }
            };
    private Bundle mState;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "coreStatesChanged");
        client.handleCoreStatesChanged(this.mState);
        Trace.traceEnd(64L);
    }

    private CoreStatesChangeItem() {}

    public static CoreStatesChangeItem obtain(Bundle states) {
        CoreStatesChangeItem instance =
                (CoreStatesChangeItem) ObjectPool.obtain(CoreStatesChangeItem.class);
        if (instance == null) {
            instance = new CoreStatesChangeItem();
        }
        instance.mState = states;
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mState = null;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mState, flags);
    }

    private CoreStatesChangeItem(Parcel in) {
        this.mState = (Bundle) in.readTypedObject(Bundle.CREATOR);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoreStatesChangeItem other = (CoreStatesChangeItem) o;
        return Objects.equals(this.mState, other.mState);
    }

    public int hashCode() {
        return this.mState.hashCode();
    }

    public String toString() {
        return "CoreStatesChangeItem{State=" + this.mState + "}";
    }
}
