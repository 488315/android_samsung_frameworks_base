package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.IWindow;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowStateInsetsControlChangeItem extends WindowStateTransactionItem {
    public static final Parcelable.Creator<WindowStateInsetsControlChangeItem> CREATOR = new Parcelable.Creator<WindowStateInsetsControlChangeItem>() { // from class: android.app.servertransaction.WindowStateInsetsControlChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowStateInsetsControlChangeItem createFromParcel(Parcel parcel) {
            return new WindowStateInsetsControlChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowStateInsetsControlChangeItem[] newArray(int i) {
            return new WindowStateInsetsControlChangeItem[i];
        }
    };
    private static final String TAG = "WindowStateInsetsControlChangeItem";
    private final InsetsSourceControl.Array mActiveControls;
    private final InsetsState mInsetsState;

    public WindowStateInsetsControlChangeItem(IWindow iWindow, InsetsState insetsState, InsetsSourceControl.Array array) {
        this(iWindow, insetsState, array, true);
    }

    public WindowStateInsetsControlChangeItem(IWindow iWindow, InsetsState insetsState, InsetsSourceControl.Array array, boolean z) {
        super(iWindow);
        this.mInsetsState = new InsetsState(insetsState, true);
        if (z) {
            this.mActiveControls = copy((InsetsSourceControl.Array) Objects.requireNonNull(array));
        } else {
            this.mActiveControls = (InsetsSourceControl.Array) Objects.requireNonNull(array);
        }
    }

    private static InsetsSourceControl.Array copy(InsetsSourceControl.Array array) {
        InsetsSourceControl.Array array2 = new InsetsSourceControl.Array(array, true);
        array2.setParcelableFlags(1);
        return array2;
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, IWindow iWindow, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(32L, "windowInsetsControlChanged");
        try {
            iWindow.insetsControlChanged(this.mInsetsState, this.mActiveControls);
        } catch (RemoteException e) {
            Log.w(TAG, "The original window no longer exists in the new process", e);
            this.mActiveControls.release();
        }
        Trace.traceEnd(32L);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mInsetsState, i);
        parcel.writeTypedObject(this.mActiveControls, i);
    }

    private WindowStateInsetsControlChangeItem(Parcel parcel) {
        super(parcel);
        this.mInsetsState = (InsetsState) Objects.requireNonNull((InsetsState) parcel.readTypedObject(InsetsState.CREATOR));
        this.mActiveControls = (InsetsSourceControl.Array) Objects.requireNonNull((InsetsSourceControl.Array) parcel.readTypedObject(InsetsSourceControl.Array.CREATOR));
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        WindowStateInsetsControlChangeItem windowStateInsetsControlChangeItem = (WindowStateInsetsControlChangeItem) obj;
        return Objects.equals(this.mInsetsState, windowStateInsetsControlChangeItem.mInsetsState) && Objects.equals(this.mActiveControls, windowStateInsetsControlChangeItem.mActiveControls);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + Objects.hashCode(this.mInsetsState)) * 31) + Objects.hashCode(this.mActiveControls);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public String toString() {
        return "WindowStateInsetsControlChangeItem{" + super.toString() + "}";
    }
}
