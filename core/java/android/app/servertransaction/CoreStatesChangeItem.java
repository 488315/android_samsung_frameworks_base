package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.Trace;
import java.util.Objects;

/* loaded from: classes.dex */
public class CoreStatesChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<CoreStatesChangeItem> CREATOR = new Parcelable.Creator<CoreStatesChangeItem>() { // from class: android.app.servertransaction.CoreStatesChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoreStatesChangeItem createFromParcel(Parcel in) {
            return new CoreStatesChangeItem(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoreStatesChangeItem[] newArray(int size) {
            return new CoreStatesChangeItem[size];
        }
    };
    private Bundle mState;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, IBinder token, PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "coreStatesChanged");
        client.handleCoreStatesChanged(this.mState);
        Trace.traceEnd(64L);
    }

    private CoreStatesChangeItem() {
    }

    public static CoreStatesChangeItem obtain(Bundle states) {
        CoreStatesChangeItem instance = (CoreStatesChangeItem) ObjectPool.obtain(CoreStatesChangeItem.class);
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

    @Override // android.p009os.Parcelable
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
