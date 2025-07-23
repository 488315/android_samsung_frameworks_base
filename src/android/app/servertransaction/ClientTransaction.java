package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.IApplicationThread;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ClientTransaction implements Parcelable {
    public static final Parcelable.Creator<ClientTransaction> CREATOR = new Parcelable.Creator<ClientTransaction>() { // from class: android.app.servertransaction.ClientTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientTransaction createFromParcel(Parcel parcel) {
            return new ClientTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientTransaction[] newArray(int i) {
            return new ClientTransaction[i];
        }
    };

    @Deprecated
    private List<ClientTransactionItem> mActivityCallbacks;
    private IBinder mActivityToken;
    private final IApplicationThread mClient;
    private ActivityLifecycleItem mLifecycleStateRequest;
    private final List<ClientTransactionItem> mTransactionItems;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ClientTransaction() {
        this.mTransactionItems = new ArrayList();
        this.mClient = null;
    }

    public ClientTransaction(IApplicationThread iApplicationThread) {
        this.mTransactionItems = new ArrayList();
        this.mClient = (IApplicationThread) Objects.requireNonNull(iApplicationThread);
    }

    public IApplicationThread getClient() {
        return this.mClient;
    }

    public void addTransactionItem(ClientTransactionItem clientTransactionItem) {
        this.mTransactionItems.add(clientTransactionItem);
        if (clientTransactionItem.isActivityLifecycleItem()) {
            setLifecycleStateRequest((ActivityLifecycleItem) clientTransactionItem);
        } else {
            addCallback(clientTransactionItem);
        }
    }

    public List<ClientTransactionItem> getTransactionItems() {
        return this.mTransactionItems;
    }

    @Deprecated
    private void addCallback(ClientTransactionItem clientTransactionItem) {
        if (this.mActivityCallbacks == null) {
            this.mActivityCallbacks = new ArrayList();
        }
        this.mActivityCallbacks.add(clientTransactionItem);
        setActivityTokenIfNotSet(clientTransactionItem);
    }

    @Deprecated
    public List<ClientTransactionItem> getCallbacks() {
        return this.mActivityCallbacks;
    }

    @Deprecated
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @Deprecated
    public ActivityLifecycleItem getLifecycleStateRequest() {
        return this.mLifecycleStateRequest;
    }

    @Deprecated
    private void setLifecycleStateRequest(ActivityLifecycleItem activityLifecycleItem) {
        if (this.mLifecycleStateRequest != null) {
            return;
        }
        this.mLifecycleStateRequest = activityLifecycleItem;
        setActivityTokenIfNotSet(activityLifecycleItem);
    }

    private void setActivityTokenIfNotSet(ClientTransactionItem clientTransactionItem) {
        if (this.mActivityToken != null || clientTransactionItem == null) {
            return;
        }
        this.mActivityToken = clientTransactionItem.getActivityToken();
    }

    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        int size = this.mTransactionItems.size();
        for (int i = 0; i < size; i++) {
            this.mTransactionItems.get(i).preExecute(clientTransactionHandler);
        }
    }

    public RemoteException schedule() {
        try {
            this.mClient.scheduleTransaction(this);
            return null;
        } catch (RemoteException e) {
            return e;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableList(this.mTransactionItems, i);
    }

    private ClientTransaction(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mTransactionItems = arrayList;
        this.mClient = null;
        parcel.readParcelableList(arrayList, getClass().getClassLoader(), ClientTransactionItem.class);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ClientTransactionItem clientTransactionItem = this.mTransactionItems.get(i);
            if (clientTransactionItem.isActivityLifecycleItem()) {
                setLifecycleStateRequest((ActivityLifecycleItem) clientTransactionItem);
            } else {
                addCallback(clientTransactionItem);
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ClientTransaction clientTransaction = (ClientTransaction) obj;
            if (Objects.equals(this.mTransactionItems, clientTransaction.mTransactionItems) && Objects.equals(this.mActivityCallbacks, clientTransaction.mActivityCallbacks) && Objects.equals(this.mLifecycleStateRequest, clientTransaction.mLifecycleStateRequest) && this.mClient == clientTransaction.mClient && Objects.equals(this.mActivityToken, clientTransaction.mActivityToken)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((527 + Objects.hashCode(this.mTransactionItems)) * 31) + Objects.hashCode(this.mActivityCallbacks)) * 31) + Objects.hashCode(this.mLifecycleStateRequest)) * 31) + Objects.hashCode(this.mClient)) * 31) + Objects.hashCode(this.mActivityToken);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ClientTransaction{\n  transactionItems=[");
        int size = this.mTransactionItems.size();
        for (int i = 0; i < size; i++) {
            sb.append("\n    ");
            sb.append(this.mTransactionItems.get(i));
        }
        sb.append("\n  ]\n}");
        return sb.toString();
    }

    void dump(String str, PrintWriter printWriter, ClientTransactionHandler clientTransactionHandler) {
        printWriter.append((CharSequence) str).println("ClientTransaction{");
        printWriter.append((CharSequence) str).print("  transactionItems=[");
        String str2 = str + "    ";
        int size = this.mTransactionItems.size();
        if (size > 0) {
            printWriter.println();
            for (int i = 0; i < size; i++) {
                this.mTransactionItems.get(i).dump(str2, printWriter, clientTransactionHandler);
            }
            printWriter.append((CharSequence) str).println("  ]");
        } else {
            printWriter.println(NavigationBarInflaterView.SIZE_MOD_END);
        }
        printWriter.append((CharSequence) str).println("}");
    }
}
