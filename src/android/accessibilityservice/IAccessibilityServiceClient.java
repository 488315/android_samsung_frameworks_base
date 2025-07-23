package android.accessibilityservice;

import android.accessibilityservice.IAccessibilityServiceConnection;
import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

/* loaded from: classes.dex */
public interface IAccessibilityServiceClient extends IInterface {

    public static class Default implements IAccessibilityServiceClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void bindInput() throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void clearAccessibilityCache() throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonAvailabilityChanged(boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonClicked(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintCapturingGesturesChanged(boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintGesture(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onGesture(AccessibilityGestureEvent accessibilityGestureEvent) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onInterrupt() throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onKeyEvent(KeyEvent keyEvent, int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMotionEvent(MotionEvent motionEvent) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onPerformGestureResult(int i, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSoftKeyboardShowModeChanged(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSystemActionsChanged() throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onTouchStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void setImeSessionEnabled(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void startInput(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void unbindInput() throws RemoteException {
        }
    }

    void bindInput() throws RemoteException;

    void clearAccessibilityCache() throws RemoteException;

    void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws RemoteException;

    void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, IBinder iBinder) throws RemoteException;

    void onAccessibilityButtonAvailabilityChanged(boolean z) throws RemoteException;

    void onAccessibilityButtonClicked(int i) throws RemoteException;

    void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z) throws RemoteException;

    void onFingerprintCapturingGesturesChanged(boolean z) throws RemoteException;

    void onFingerprintGesture(int i) throws RemoteException;

    void onGesture(AccessibilityGestureEvent accessibilityGestureEvent) throws RemoteException;

    void onInterrupt() throws RemoteException;

    void onKeyEvent(KeyEvent keyEvent, int i) throws RemoteException;

    void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) throws RemoteException;

    void onMotionEvent(MotionEvent motionEvent) throws RemoteException;

    void onPerformGestureResult(int i, boolean z) throws RemoteException;

    void onSoftKeyboardShowModeChanged(int i) throws RemoteException;

    void onSystemActionsChanged() throws RemoteException;

    void onTouchStateChanged(int i, int i2) throws RemoteException;

    void setImeSessionEnabled(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws RemoteException;

    void startInput(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) throws RemoteException;

    void unbindInput() throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityServiceClient {
        public static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceClient";
        static final int TRANSACTION_bindInput = 19;
        static final int TRANSACTION_clearAccessibilityCache = 5;
        static final int TRANSACTION_createImeSession = 17;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_onAccessibilityButtonAvailabilityChanged = 15;
        static final int TRANSACTION_onAccessibilityButtonClicked = 14;
        static final int TRANSACTION_onAccessibilityEvent = 2;
        static final int TRANSACTION_onFingerprintCapturingGesturesChanged = 12;
        static final int TRANSACTION_onFingerprintGesture = 13;
        static final int TRANSACTION_onGesture = 4;
        static final int TRANSACTION_onInterrupt = 3;
        static final int TRANSACTION_onKeyEvent = 6;
        static final int TRANSACTION_onMagnificationChanged = 7;
        static final int TRANSACTION_onMotionEvent = 8;
        static final int TRANSACTION_onPerformGestureResult = 11;
        static final int TRANSACTION_onSoftKeyboardShowModeChanged = 10;
        static final int TRANSACTION_onSystemActionsChanged = 16;
        static final int TRANSACTION_onTouchStateChanged = 9;
        static final int TRANSACTION_setImeSessionEnabled = 18;
        static final int TRANSACTION_startInput = 21;
        static final int TRANSACTION_unbindInput = 20;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityServiceClient)) {
                return (IAccessibilityServiceClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "init";
                case 2:
                    return "onAccessibilityEvent";
                case 3:
                    return "onInterrupt";
                case 4:
                    return "onGesture";
                case 5:
                    return "clearAccessibilityCache";
                case 6:
                    return "onKeyEvent";
                case 7:
                    return "onMagnificationChanged";
                case 8:
                    return "onMotionEvent";
                case 9:
                    return "onTouchStateChanged";
                case 10:
                    return "onSoftKeyboardShowModeChanged";
                case 11:
                    return "onPerformGestureResult";
                case 12:
                    return "onFingerprintCapturingGesturesChanged";
                case 13:
                    return "onFingerprintGesture";
                case 14:
                    return "onAccessibilityButtonClicked";
                case 15:
                    return "onAccessibilityButtonAvailabilityChanged";
                case 16:
                    return "onSystemActionsChanged";
                case 17:
                    return "createImeSession";
                case 18:
                    return "setImeSessionEnabled";
                case 19:
                    return "bindInput";
                case 20:
                    return "unbindInput";
                case 21:
                    return "startInput";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IAccessibilityServiceConnection asInterface = IAccessibilityServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    init(asInterface, readInt, readStrongBinder);
                    return true;
                case 2:
                    AccessibilityEvent accessibilityEvent = (AccessibilityEvent) parcel.readTypedObject(AccessibilityEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAccessibilityEvent(accessibilityEvent, readBoolean);
                    return true;
                case 3:
                    onInterrupt();
                    return true;
                case 4:
                    AccessibilityGestureEvent accessibilityGestureEvent = (AccessibilityGestureEvent) parcel.readTypedObject(AccessibilityGestureEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGesture(accessibilityGestureEvent);
                    return true;
                case 5:
                    clearAccessibilityCache();
                    return true;
                case 6:
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onKeyEvent(keyEvent, readInt2);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    MagnificationConfig magnificationConfig = (MagnificationConfig) parcel.readTypedObject(MagnificationConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMagnificationChanged(readInt3, region, magnificationConfig);
                    return true;
                case 8:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMotionEvent(motionEvent);
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTouchStateChanged(readInt4, readInt5);
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSoftKeyboardShowModeChanged(readInt6);
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPerformGestureResult(readInt7, readBoolean2);
                    return true;
                case 12:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFingerprintCapturingGesturesChanged(readBoolean3);
                    return true;
                case 13:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFingerprintGesture(readInt8);
                    return true;
                case 14:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAccessibilityButtonClicked(readInt9);
                    return true;
                case 15:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAccessibilityButtonAvailabilityChanged(readBoolean4);
                    return true;
                case 16:
                    onSystemActionsChanged();
                    return true;
                case 17:
                    IAccessibilityInputMethodSessionCallback asInterface2 = IAccessibilityInputMethodSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createImeSession(asInterface2);
                    return true;
                case 18:
                    IAccessibilityInputMethodSession asInterface3 = IAccessibilityInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeSessionEnabled(asInterface3, readBoolean5);
                    return true;
                case 19:
                    bindInput();
                    return true;
                case 20:
                    unbindInput();
                    return true;
                case 21:
                    IRemoteAccessibilityInputConnection asInterface4 = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startInput(asInterface4, editorInfo, readBoolean6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccessibilityServiceClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceConnection);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityEvent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onInterrupt() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onGesture(AccessibilityGestureEvent accessibilityGestureEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityGestureEvent, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void clearAccessibilityCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onKeyEvent(KeyEvent keyEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeTypedObject(magnificationConfig, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onMotionEvent(MotionEvent motionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onTouchStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onSoftKeyboardShowModeChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onPerformGestureResult(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onFingerprintCapturingGesturesChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onFingerprintGesture(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityButtonClicked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityButtonAvailabilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onSystemActionsChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInputMethodSessionCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void setImeSessionEnabled(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInputMethodSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void bindInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void unbindInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void startInput(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
