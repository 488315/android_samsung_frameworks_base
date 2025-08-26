package android.view;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IWindowFocusObserver;
import android.view.IWindowId;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class WindowId implements Parcelable {
    public static final Parcelable.Creator<WindowId> CREATOR = new Parcelable.Creator<WindowId>() { // from class: android.view.WindowId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowId createFromParcel(Parcel parcel) {
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                return new WindowId(strongBinder);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowId[] newArray(int i) {
            return new WindowId[i];
        }
    };
    private final IWindowId mToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static abstract class FocusObserver {
        final IWindowFocusObserver.Stub mIObserver = new IWindowFocusObserver.Stub() { // from class: android.view.WindowId.FocusObserver.1
            @Override // android.view.IWindowFocusObserver
            public void focusGained(IBinder iBinder) {
                WindowId windowId;
                synchronized (FocusObserver.this.mRegistrations) {
                    windowId = FocusObserver.this.mRegistrations.get(iBinder);
                }
                if (FocusObserver.this.mHandler != null) {
                    FocusObserver.this.mHandler.sendMessage(FocusObserver.this.mHandler.obtainMessage(1, windowId));
                } else {
                    FocusObserver.this.onFocusGained(windowId);
                }
            }

            @Override // android.view.IWindowFocusObserver
            public void focusLost(IBinder iBinder) {
                WindowId windowId;
                synchronized (FocusObserver.this.mRegistrations) {
                    windowId = FocusObserver.this.mRegistrations.get(iBinder);
                }
                if (FocusObserver.this.mHandler != null) {
                    FocusObserver.this.mHandler.sendMessage(FocusObserver.this.mHandler.obtainMessage(2, windowId));
                } else {
                    FocusObserver.this.onFocusLost(windowId);
                }
            }
        };
        final HashMap<IBinder, WindowId> mRegistrations = new HashMap<>();
        final Handler mHandler = new H();

        public abstract void onFocusGained(WindowId windowId);

        public abstract void onFocusLost(WindowId windowId);

        class H extends Handler {
            H() {
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    FocusObserver.this.onFocusGained((WindowId) message.obj);
                } else if (i == 2) {
                    FocusObserver.this.onFocusLost((WindowId) message.obj);
                } else {
                    super.handleMessage(message);
                }
            }
        }
    }

    public boolean isFocused() {
        try {
            return this.mToken.isFocused();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void registerFocusObserver(FocusObserver focusObserver) {
        synchronized (focusObserver.mRegistrations) {
            if (focusObserver.mRegistrations.containsKey(this.mToken.asBinder())) {
                throw new IllegalStateException("Focus observer already registered with input token");
            }
            focusObserver.mRegistrations.put(this.mToken.asBinder(), this);
            try {
                this.mToken.registerFocusObserver(focusObserver.mIObserver);
            } catch (RemoteException unused) {
            }
        }
    }

    public void unregisterFocusObserver(FocusObserver focusObserver) {
        synchronized (focusObserver.mRegistrations) {
            if (focusObserver.mRegistrations.remove(this.mToken.asBinder()) == null) {
                throw new IllegalStateException("Focus observer not registered with input token");
            }
            try {
                this.mToken.unregisterFocusObserver(focusObserver.mIObserver);
            } catch (RemoteException unused) {
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof WindowId) {
            return this.mToken.asBinder().equals(((WindowId) obj).mToken.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mToken.asBinder().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("IntentSender{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.mToken.asBinder());
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken.asBinder());
    }

    public IWindowId getTarget() {
        return this.mToken;
    }

    public WindowId(IWindowId iWindowId) {
        this.mToken = iWindowId;
    }

    public WindowId(IBinder iBinder) {
        this.mToken = IWindowId.Stub.asInterface(iBinder);
    }
}
