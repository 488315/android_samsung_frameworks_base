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

public class WindowStateInsetsControlChangeItem extends WindowStateTransactionItem {
    public static final Parcelable.Creator<WindowStateInsetsControlChangeItem> CREATOR =
            new Parcelable.Creator<WindowStateInsetsControlChangeItem>() { // from class:
                // android.app.servertransaction.WindowStateInsetsControlChangeItem.1
                @Override // android.os.Parcelable.Creator
                public WindowStateInsetsControlChangeItem createFromParcel(Parcel in) {
                    return new WindowStateInsetsControlChangeItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public WindowStateInsetsControlChangeItem[] newArray(int size) {
                    return new WindowStateInsetsControlChangeItem[size];
                }
            };
    private static final String TAG = "WindowStateInsetsControlChangeItem";
    public InsetsSourceControl.Array mActiveControls;
    private InsetsState mInsetsState;

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public void execute(
            ClientTransactionHandler client,
            IWindow window,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(32L, "windowInsetsControlChanged");
        try {
            window.insetsControlChanged(this.mInsetsState, this.mActiveControls);
        } catch (RemoteException e) {
            Log.w(TAG, "The original window no longer exists in the new process", e);
            this.mActiveControls.release();
        }
        Trace.traceEnd(32L);
    }

    private WindowStateInsetsControlChangeItem() {}

    public static WindowStateInsetsControlChangeItem obtain(
            IWindow window, InsetsState insetsState, InsetsSourceControl.Array activeControls) {
        WindowStateInsetsControlChangeItem instance =
                (WindowStateInsetsControlChangeItem)
                        ObjectPool.obtain(WindowStateInsetsControlChangeItem.class);
        if (instance == null) {
            instance = new WindowStateInsetsControlChangeItem();
        }
        instance.setWindow(window);
        instance.mInsetsState = new InsetsState(insetsState, true);
        instance.mActiveControls = new InsetsSourceControl.Array(activeControls, true);
        instance.mActiveControls.setParcelableFlags(1);
        return instance;
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mInsetsState = null;
        this.mActiveControls = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mInsetsState, flags);
        dest.writeTypedObject(this.mActiveControls, flags);
    }

    private WindowStateInsetsControlChangeItem(Parcel in) {
        super(in);
        this.mInsetsState = (InsetsState) in.readTypedObject(InsetsState.CREATOR);
        this.mActiveControls =
                (InsetsSourceControl.Array) in.readTypedObject(InsetsSourceControl.Array.CREATOR);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        WindowStateInsetsControlChangeItem other = (WindowStateInsetsControlChangeItem) o;
        return Objects.equals(this.mInsetsState, other.mInsetsState)
                && Objects.equals(this.mActiveControls, other.mActiveControls);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (((result * 31) + Objects.hashCode(this.mInsetsState)) * 31)
                + Objects.hashCode(this.mActiveControls);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public String toString() {
        return "WindowStateInsetsControlChangeItem{" + super.toString() + "}";
    }
}
