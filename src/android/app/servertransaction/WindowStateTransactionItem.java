package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Parcel;
import android.view.IWindow;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class WindowStateTransactionItem extends ClientTransactionItem {
    private final IWindow mWindow;

    public interface TransactionListener {
        void onExecutingWindowStateTransactionItem();
    }

    public abstract void execute(ClientTransactionHandler clientTransactionHandler, IWindow iWindow, PendingTransactionActions pendingTransactionActions);

    public WindowStateTransactionItem(IWindow iWindow) {
        this.mWindow = (IWindow) Objects.requireNonNull(iWindow);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public final void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        IWindow iWindow = this.mWindow;
        if (iWindow instanceof TransactionListener) {
            ((TransactionListener) iWindow).onExecutingWindowStateTransactionItem();
        }
        execute(clientTransactionHandler, this.mWindow, pendingTransactionActions);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mWindow.asBinder());
    }

    WindowStateTransactionItem(Parcel parcel) {
        this.mWindow = (IWindow) Objects.requireNonNull(IWindow.Stub.asInterface(parcel.readStrongBinder()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mWindow, ((WindowStateTransactionItem) obj).mWindow);
    }

    public int hashCode() {
        return Objects.hashCode(this.mWindow);
    }

    public String toString() {
        return "mWindow=" + this.mWindow;
    }
}
