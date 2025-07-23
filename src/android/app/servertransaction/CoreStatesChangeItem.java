package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import java.util.Objects;

/* loaded from: classes.dex */
public class CoreStatesChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<CoreStatesChangeItem> CREATOR = new Parcelable.Creator<CoreStatesChangeItem>() { // from class: android.app.servertransaction.CoreStatesChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoreStatesChangeItem createFromParcel(Parcel parcel) {
            return new CoreStatesChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoreStatesChangeItem[] newArray(int i) {
            return new CoreStatesChangeItem[i];
        }
    };
    private Bundle mState;

    public CoreStatesChangeItem(Bundle bundle) {
        this.mState = bundle;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "coreStatesChanged");
        clientTransactionHandler.handleCoreStatesChanged(this.mState);
        Trace.traceEnd(64L);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mState, i);
    }

    private CoreStatesChangeItem(Parcel parcel) {
        this.mState = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mState, ((CoreStatesChangeItem) obj).mState);
    }

    public int hashCode() {
        return this.mState.hashCode();
    }

    public String toString() {
        return "CoreStatesChangeItem{State=" + this.mState + "}";
    }
}
