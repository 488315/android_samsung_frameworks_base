package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.IApplicationThread;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.android.window.flags.Flags;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientTransaction implements Parcelable, ObjectPoolItem {
    public static final Parcelable.Creator<ClientTransaction> CREATOR =
            new Parcelable.Creator<ClientTransaction>() { // from class:
                // android.app.servertransaction.ClientTransaction.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ClientTransaction createFromParcel(Parcel in) {
                    return new ClientTransaction(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ClientTransaction[] newArray(int size) {
                    return new ClientTransaction[size];
                }
            };

    @Deprecated private List<ClientTransactionItem> mActivityCallbacks;
    private IBinder mActivityToken;
    private IApplicationThread mClient;
    private ActivityLifecycleItem mLifecycleStateRequest;
    private List<ClientTransactionItem> mTransactionItems;

    public IApplicationThread getClient() {
        return this.mClient;
    }

    public void addTransactionItem(ClientTransactionItem item) {
        if (Flags.bundleClientTransactionFlag()) {
            if (this.mTransactionItems == null) {
                this.mTransactionItems = new ArrayList();
            }
            this.mTransactionItems.add(item);
        }
        if (item.isActivityLifecycleItem()) {
            setLifecycleStateRequest((ActivityLifecycleItem) item);
        } else {
            addCallback(item);
        }
    }

    public List<ClientTransactionItem> getTransactionItems() {
        return this.mTransactionItems;
    }

    @Deprecated
    private void addCallback(ClientTransactionItem activityCallback) {
        if (this.mActivityCallbacks == null) {
            this.mActivityCallbacks = new ArrayList();
        }
        this.mActivityCallbacks.add(activityCallback);
        setActivityTokenIfNotSet(activityCallback);
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
    private void setLifecycleStateRequest(ActivityLifecycleItem stateRequest) {
        if (this.mLifecycleStateRequest != null) {
            return;
        }
        this.mLifecycleStateRequest = stateRequest;
        setActivityTokenIfNotSet(stateRequest);
    }

    private void setActivityTokenIfNotSet(ClientTransactionItem item) {
        if (this.mActivityToken == null && item != null) {
            this.mActivityToken = item.getActivityToken();
        }
    }

    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        if (this.mTransactionItems != null) {
            int size = this.mTransactionItems.size();
            for (int i = 0; i < size; i++) {
                this.mTransactionItems.get(i).preExecute(clientTransactionHandler);
            }
            return;
        }
        if (this.mActivityCallbacks != null) {
            int size2 = this.mActivityCallbacks.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mActivityCallbacks.get(i2).preExecute(clientTransactionHandler);
            }
        }
        if (this.mLifecycleStateRequest != null) {
            this.mLifecycleStateRequest.preExecute(clientTransactionHandler);
        }
    }

    public void schedule() throws RemoteException {
        this.mClient.scheduleTransaction(this);
    }

    private ClientTransaction() {}

    public static ClientTransaction obtain(IApplicationThread client) {
        ClientTransaction instance = (ClientTransaction) ObjectPool.obtain(ClientTransaction.class);
        if (instance == null) {
            instance = new ClientTransaction();
        }
        instance.mClient = client;
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        if (Flags.disableObjectPool()) {
            return;
        }
        if (this.mTransactionItems != null) {
            int size = this.mTransactionItems.size();
            for (int i = 0; i < size; i++) {
                this.mTransactionItems.get(i).recycle();
            }
            this.mTransactionItems = null;
            this.mActivityCallbacks = null;
            this.mLifecycleStateRequest = null;
        } else {
            if (this.mActivityCallbacks != null) {
                int size2 = this.mActivityCallbacks.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    this.mActivityCallbacks.get(i2).recycle();
                }
                this.mActivityCallbacks = null;
            }
            if (this.mLifecycleStateRequest != null) {
                this.mLifecycleStateRequest.recycle();
                this.mLifecycleStateRequest = null;
            }
        }
        this.mClient = null;
        this.mActivityToken = null;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        boolean writeTransactionItems = this.mTransactionItems != null;
        dest.writeBoolean(writeTransactionItems);
        if (writeTransactionItems) {
            dest.writeParcelableList(this.mTransactionItems, flags);
            return;
        }
        dest.writeParcelable(this.mLifecycleStateRequest, flags);
        boolean writeActivityCallbacks = this.mActivityCallbacks != null;
        dest.writeBoolean(writeActivityCallbacks);
        if (writeActivityCallbacks) {
            dest.writeParcelableList(this.mActivityCallbacks, flags);
        }
    }

    private ClientTransaction(Parcel in) {
        boolean readTransactionItems = in.readBoolean();
        if (readTransactionItems) {
            this.mTransactionItems = new ArrayList();
            in.readParcelableList(
                    this.mTransactionItems,
                    getClass().getClassLoader(),
                    ClientTransactionItem.class);
            int size = this.mTransactionItems.size();
            for (int i = 0; i < size; i++) {
                ClientTransactionItem item = this.mTransactionItems.get(i);
                if (item.isActivityLifecycleItem()) {
                    setLifecycleStateRequest((ActivityLifecycleItem) item);
                } else {
                    addCallback(item);
                }
            }
            return;
        }
        this.mLifecycleStateRequest =
                (ActivityLifecycleItem)
                        in.readParcelable(getClass().getClassLoader(), ActivityLifecycleItem.class);
        setActivityTokenIfNotSet(this.mLifecycleStateRequest);
        boolean readActivityCallbacks = in.readBoolean();
        if (readActivityCallbacks) {
            this.mActivityCallbacks = new ArrayList();
            in.readParcelableList(
                    this.mActivityCallbacks,
                    getClass().getClassLoader(),
                    ClientTransactionItem.class);
            int size2 = this.mActivityCallbacks.size();
            for (int i2 = 0; this.mActivityToken == null && i2 < size2; i2++) {
                setActivityTokenIfNotSet(this.mActivityCallbacks.get(i2));
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientTransaction other = (ClientTransaction) o;
        if (Objects.equals(this.mTransactionItems, other.mTransactionItems)
                && Objects.equals(this.mActivityCallbacks, other.mActivityCallbacks)
                && Objects.equals(this.mLifecycleStateRequest, other.mLifecycleStateRequest)
                && this.mClient == other.mClient
                && Objects.equals(this.mActivityToken, other.mActivityToken)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (17 * 31) + Objects.hashCode(this.mTransactionItems);
        return (((((((result * 31) + Objects.hashCode(this.mActivityCallbacks)) * 31)
                                                + Objects.hashCode(this.mLifecycleStateRequest))
                                        * 31)
                                + Objects.hashCode(this.mClient))
                        * 31)
                + Objects.hashCode(this.mActivityToken);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientTransaction{");
        if (this.mTransactionItems != null) {
            sb.append("\n  transactionItems=[");
            int size = this.mTransactionItems.size();
            for (int i = 0; i < size; i++) {
                sb.append("\n    ").append(this.mTransactionItems.get(i));
            }
            sb.append("\n  ]");
        } else {
            sb.append("\n  callbacks=[");
            int size2 = this.mActivityCallbacks != null ? this.mActivityCallbacks.size() : 0;
            for (int i2 = 0; i2 < size2; i2++) {
                sb.append("\n    ").append(this.mActivityCallbacks.get(i2));
            }
            sb.append("\n  ]");
            sb.append("\n  stateRequest=").append(this.mLifecycleStateRequest);
        }
        sb.append("\n}");
        return sb.toString();
    }

    void dump(String prefix, PrintWriter pw, ClientTransactionHandler transactionHandler) {
        pw.append((CharSequence) prefix).println("ClientTransaction{");
        if (this.mTransactionItems != null) {
            pw.append((CharSequence) prefix).print("  transactionItems=[");
            String itemPrefix = prefix + "    ";
            int size = this.mTransactionItems.size();
            if (size > 0) {
                pw.println();
                for (int i = 0; i < size; i++) {
                    this.mTransactionItems.get(i).dump(itemPrefix, pw, transactionHandler);
                }
                pw.append((CharSequence) prefix).println("  ]");
            } else {
                pw.println(NavigationBarInflaterView.SIZE_MOD_END);
            }
            pw.append((CharSequence) prefix).println("}");
            return;
        }
        pw.append((CharSequence) prefix).print("  callbacks=[");
        String itemPrefix2 = prefix + "    ";
        int size2 = this.mActivityCallbacks != null ? this.mActivityCallbacks.size() : 0;
        if (size2 > 0) {
            pw.println();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mActivityCallbacks.get(i2).dump(itemPrefix2, pw, transactionHandler);
            }
            pw.append((CharSequence) prefix).println("  ]");
        } else {
            pw.println(NavigationBarInflaterView.SIZE_MOD_END);
        }
        pw.append((CharSequence) prefix).println("  stateRequest=");
        if (this.mLifecycleStateRequest != null) {
            this.mLifecycleStateRequest.dump(itemPrefix2, pw, transactionHandler);
        } else {
            pw.append((CharSequence) itemPrefix2).println("null");
        }
        pw.append((CharSequence) prefix).println("}");
    }
}
