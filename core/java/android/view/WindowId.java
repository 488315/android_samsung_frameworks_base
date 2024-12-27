package android.view;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class WindowId implements Parcelable {
    public static final Parcelable.Creator<WindowId> CREATOR =
            new Parcelable.Creator<WindowId>() { // from class: android.view.WindowId.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowId createFromParcel(Parcel in) {
                    IBinder target = in.readStrongBinder();
                    if (target != null) {
                        return new WindowId(target);
                    }
                    return null;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowId[] newArray(int size) {
                    return new WindowId[size];
                }
            };
    private final IWindowId mToken;

    public abstract static class FocusObserver {
        final IWindowFocusObserver.Stub mIObserver =
                new IWindowFocusObserver
                        .Stub() { // from class: android.view.WindowId.FocusObserver.1
                    @Override // android.view.IWindowFocusObserver
                    public void focusGained(IBinder inputToken) {
                        WindowId token;
                        synchronized (FocusObserver.this.mRegistrations) {
                            token = FocusObserver.this.mRegistrations.get(inputToken);
                        }
                        if (FocusObserver.this.mHandler != null) {
                            FocusObserver.this.mHandler.sendMessage(
                                    FocusObserver.this.mHandler.obtainMessage(1, token));
                        } else {
                            FocusObserver.this.onFocusGained(token);
                        }
                    }

                    @Override // android.view.IWindowFocusObserver
                    public void focusLost(IBinder inputToken) {
                        WindowId token;
                        synchronized (FocusObserver.this.mRegistrations) {
                            token = FocusObserver.this.mRegistrations.get(inputToken);
                        }
                        if (FocusObserver.this.mHandler != null) {
                            FocusObserver.this.mHandler.sendMessage(
                                    FocusObserver.this.mHandler.obtainMessage(2, token));
                        } else {
                            FocusObserver.this.onFocusLost(token);
                        }
                    }
                };
        final HashMap<IBinder, WindowId> mRegistrations = new HashMap<>();
        final Handler mHandler = new H();

        public abstract void onFocusGained(WindowId windowId);

        public abstract void onFocusLost(WindowId windowId);

        class H extends Handler {
            H() {}

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        FocusObserver.this.onFocusGained((WindowId) msg.obj);
                        break;
                    case 2:
                        FocusObserver.this.onFocusLost((WindowId) msg.obj);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        }
    }

    public boolean isFocused() {
        try {
            return this.mToken.isFocused();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void registerFocusObserver(FocusObserver observer) {
        synchronized (observer.mRegistrations) {
            if (observer.mRegistrations.containsKey(this.mToken.asBinder())) {
                throw new IllegalStateException(
                        "Focus observer already registered with input token");
            }
            observer.mRegistrations.put(this.mToken.asBinder(), this);
            try {
                this.mToken.registerFocusObserver(observer.mIObserver);
            } catch (RemoteException e) {
            }
        }
    }

    public void unregisterFocusObserver(FocusObserver observer) {
        synchronized (observer.mRegistrations) {
            if (observer.mRegistrations.remove(this.mToken.asBinder()) == null) {
                throw new IllegalStateException("Focus observer not registered with input token");
            }
            try {
                this.mToken.unregisterFocusObserver(observer.mIObserver);
            } catch (RemoteException e) {
            }
        }
    }

    public boolean equals(Object otherObj) {
        if (otherObj instanceof WindowId) {
            return this.mToken.asBinder().equals(((WindowId) otherObj).mToken.asBinder());
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeStrongBinder(this.mToken.asBinder());
    }

    public IWindowId getTarget() {
        return this.mToken;
    }

    public WindowId(IWindowId target) {
        this.mToken = target;
    }

    public WindowId(IBinder target) {
        this.mToken = IWindowId.Stub.asInterface(target);
    }
}
