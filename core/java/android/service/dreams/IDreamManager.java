package android.service.dreams;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDreamManager extends IInterface {
    void awaken() throws RemoteException;

    boolean canStartDreaming(boolean z) throws RemoteException;

    void dream() throws RemoteException;

    void finishSelf(IBinder iBinder, boolean z) throws RemoteException;

    void forceAmbientDisplayEnabled(boolean z) throws RemoteException;

    ComponentName getDefaultDreamComponentForUser(int i) throws RemoteException;

    ComponentName[] getDreamComponents() throws RemoteException;

    ComponentName[] getDreamComponentsForUser(int i) throws RemoteException;

    boolean isDreaming() throws RemoteException;

    boolean isDreamingOrInPreview() throws RemoteException;

    void registerDreamOverlayService(ComponentName componentName) throws RemoteException;

    void semStartDozing(IBinder iBinder, int i, int i2, int i3, int i4, boolean z)
            throws RemoteException;

    void setDreamComponents(ComponentName[] componentNameArr) throws RemoteException;

    void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) throws RemoteException;

    void setDreamIsObscured(boolean z) throws RemoteException;

    void setSystemDreamComponent(ComponentName componentName) throws RemoteException;

    void startDozing(IBinder iBinder, int i, int i2, int i3) throws RemoteException;

    void startDreamActivity(Intent intent) throws RemoteException;

    void stopDozing(IBinder iBinder) throws RemoteException;

    void testDream(int i, ComponentName componentName) throws RemoteException;

    public static class Default implements IDreamManager {
        @Override // android.service.dreams.IDreamManager
        public void dream() throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void awaken() throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponents(ComponentName[] componentNames) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public ComponentName[] getDreamComponents() throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public ComponentName getDefaultDreamComponentForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public void testDream(int userId, ComponentName componentName) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public boolean isDreaming() throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public boolean isDreamingOrInPreview() throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public boolean canStartDreaming(boolean isScreenOn) throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public void finishSelf(IBinder token, boolean immediate) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void startDozing(IBinder token, int screenState, int reason, int screenBrightness)
                throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void stopDozing(IBinder token) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void forceAmbientDisplayEnabled(boolean enabled) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public ComponentName[] getDreamComponentsForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponentsForUser(int userId, ComponentName[] componentNames)
                throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void setSystemDreamComponent(ComponentName componentName) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void registerDreamOverlayService(ComponentName componentName)
                throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void startDreamActivity(Intent intent) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void setDreamIsObscured(boolean isObscured) throws RemoteException {}

        @Override // android.service.dreams.IDreamManager
        public void semStartDozing(
                IBinder token,
                int screenState,
                int reason,
                int screenBrightness,
                int dozeMode,
                boolean shouldWaitForTransitionToAodUi)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDreamManager {
        public static final String DESCRIPTOR = "android.service.dreams.IDreamManager";
        static final int TRANSACTION_awaken = 2;
        static final int TRANSACTION_canStartDreaming = 9;
        static final int TRANSACTION_dream = 1;
        static final int TRANSACTION_finishSelf = 10;
        static final int TRANSACTION_forceAmbientDisplayEnabled = 13;
        static final int TRANSACTION_getDefaultDreamComponentForUser = 5;
        static final int TRANSACTION_getDreamComponents = 4;
        static final int TRANSACTION_getDreamComponentsForUser = 14;
        static final int TRANSACTION_isDreaming = 7;
        static final int TRANSACTION_isDreamingOrInPreview = 8;
        static final int TRANSACTION_registerDreamOverlayService = 17;
        static final int TRANSACTION_semStartDozing = 20;
        static final int TRANSACTION_setDreamComponents = 3;
        static final int TRANSACTION_setDreamComponentsForUser = 15;
        static final int TRANSACTION_setDreamIsObscured = 19;
        static final int TRANSACTION_setSystemDreamComponent = 16;
        static final int TRANSACTION_startDozing = 11;
        static final int TRANSACTION_startDreamActivity = 18;
        static final int TRANSACTION_stopDozing = 12;
        static final int TRANSACTION_testDream = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDreamManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDreamManager)) {
                return (IDreamManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "dream";
                case 2:
                    return "awaken";
                case 3:
                    return "setDreamComponents";
                case 4:
                    return "getDreamComponents";
                case 5:
                    return "getDefaultDreamComponentForUser";
                case 6:
                    return "testDream";
                case 7:
                    return "isDreaming";
                case 8:
                    return "isDreamingOrInPreview";
                case 9:
                    return "canStartDreaming";
                case 10:
                    return "finishSelf";
                case 11:
                    return "startDozing";
                case 12:
                    return "stopDozing";
                case 13:
                    return "forceAmbientDisplayEnabled";
                case 14:
                    return "getDreamComponentsForUser";
                case 15:
                    return "setDreamComponentsForUser";
                case 16:
                    return "setSystemDreamComponent";
                case 17:
                    return "registerDreamOverlayService";
                case 18:
                    return "startDreamActivity";
                case 19:
                    return "setDreamIsObscured";
                case 20:
                    return "semStartDozing";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    dream();
                    reply.writeNoException();
                    return true;
                case 2:
                    awaken();
                    reply.writeNoException();
                    return true;
                case 3:
                    ComponentName[] _arg0 =
                            (ComponentName[]) data.createTypedArray(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setDreamComponents(_arg0);
                    reply.writeNoException();
                    return true;
                case 4:
                    ComponentName[] _result = getDreamComponents();
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 5:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result2 = getDefaultDreamComponentForUser(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 6:
                    int _arg03 = data.readInt();
                    ComponentName _arg1 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    testDream(_arg03, _arg1);
                    reply.writeNoException();
                    return true;
                case 7:
                    boolean _result3 = isDreaming();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 8:
                    boolean _result4 = isDreamingOrInPreview();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result5 = canStartDreaming(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 10:
                    IBinder _arg05 = data.readStrongBinder();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    finishSelf(_arg05, _arg12);
                    reply.writeNoException();
                    return true;
                case 11:
                    IBinder _arg06 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    startDozing(_arg06, _arg13, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 12:
                    IBinder _arg07 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stopDozing(_arg07);
                    reply.writeNoException();
                    return true;
                case 13:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceAmbientDisplayEnabled(_arg08);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName[] _result6 = getDreamComponentsForUser(_arg09);
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 15:
                    int _arg010 = data.readInt();
                    ComponentName[] _arg14 =
                            (ComponentName[]) data.createTypedArray(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setDreamComponentsForUser(_arg010, _arg14);
                    reply.writeNoException();
                    return true;
                case 16:
                    ComponentName _arg011 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setSystemDreamComponent(_arg011);
                    reply.writeNoException();
                    return true;
                case 17:
                    ComponentName _arg012 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    registerDreamOverlayService(_arg012);
                    reply.writeNoException();
                    return true;
                case 18:
                    Intent _arg013 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    startDreamActivity(_arg013);
                    reply.writeNoException();
                    return true;
                case 19:
                    boolean _arg014 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDreamIsObscured(_arg014);
                    return true;
                case 20:
                    IBinder _arg015 = data.readStrongBinder();
                    int _arg15 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    semStartDozing(_arg015, _arg15, _arg22, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDreamManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamManager
            public void dream() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void awaken() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponents(ComponentName[] componentNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(componentNames, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponents() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ComponentName[] _result =
                            (ComponentName[]) _reply.createTypedArray(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName getDefaultDreamComponentForUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void testDream(int userId, ComponentName componentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreaming() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreamingOrInPreview() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean canStartDreaming(boolean isScreenOn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isScreenOn);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelf(IBinder token, boolean immediate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(immediate);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozing(
                    IBinder token, int screenState, int reason, int screenBrightness)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(screenState);
                    _data.writeInt(reason);
                    _data.writeInt(screenBrightness);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void stopDozing(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void forceAmbientDisplayEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponentsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    ComponentName[] _result =
                            (ComponentName[]) _reply.createTypedArray(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponentsForUser(int userId, ComponentName[] componentNames)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedArray(componentNames, 0);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setSystemDreamComponent(ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void registerDreamOverlayService(ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDreamActivity(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamIsObscured(boolean isObscured) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isObscured);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void semStartDozing(
                    IBinder token,
                    int screenState,
                    int reason,
                    int screenBrightness,
                    int dozeMode,
                    boolean shouldWaitForTransitionToAodUi)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(screenState);
                    _data.writeInt(reason);
                    _data.writeInt(screenBrightness);
                    _data.writeInt(dozeMode);
                    _data.writeBoolean(shouldWaitForTransitionToAodUi);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }
    }
}
