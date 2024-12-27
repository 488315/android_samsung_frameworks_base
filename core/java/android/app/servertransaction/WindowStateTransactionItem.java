package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Parcel;
import android.view.IWindow;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class WindowStateTransactionItem extends ClientTransactionItem {
    private IWindow mWindow;

    public interface TransactionListener {
        void onExecutingWindowStateTransactionItem();
    }

    public abstract void execute(ClientTransactionHandler clientTransactionHandler, IWindow iWindow, PendingTransactionActions pendingTransactionActions);

    WindowStateTransactionItem() {
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public final void execute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        IWindow iWindow = this.mWindow;
        if (iWindow instanceof TransactionListener) {
            TransactionListener listener = (TransactionListener) iWindow;
            listener.onExecutingWindowStateTransactionItem();
        }
        execute(client, this.mWindow, pendingActions);
    }

    void setWindow(IWindow window) {
        this.mWindow = (IWindow) Objects.requireNonNull(window);
    }

    WindowStateTransactionItem(Parcel in) {
        this.mWindow = IWindow.Stub.asInterface(in.readStrongBinder());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mWindow.asBinder());
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mWindow = null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WindowStateTransactionItem other = (WindowStateTransactionItem) o;
        return Objects.equals(this.mWindow, other.mWindow);
    }

    public int hashCode() {
        return Objects.hashCode(this.mWindow);
    }

    public String toString() {
        return "mWindow=" + this.mWindow;
    }
}
