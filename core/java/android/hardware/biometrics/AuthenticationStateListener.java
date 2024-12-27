package android.hardware.biometrics;

import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationFailedInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.events.AuthenticationSucceededInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface AuthenticationStateListener extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.biometrics.AuthenticationStateListener";

    void onAuthenticationAcquired(AuthenticationAcquiredInfo authenticationAcquiredInfo)
            throws RemoteException;

    void onAuthenticationError(AuthenticationErrorInfo authenticationErrorInfo)
            throws RemoteException;

    void onAuthenticationFailed(AuthenticationFailedInfo authenticationFailedInfo)
            throws RemoteException;

    void onAuthenticationHelp(AuthenticationHelpInfo authenticationHelpInfo) throws RemoteException;

    void onAuthenticationStarted(AuthenticationStartedInfo authenticationStartedInfo)
            throws RemoteException;

    void onAuthenticationStopped(AuthenticationStoppedInfo authenticationStoppedInfo)
            throws RemoteException;

    void onAuthenticationSucceeded(AuthenticationSucceededInfo authenticationSucceededInfo)
            throws RemoteException;

    public static class Default implements AuthenticationStateListener {
        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationAcquired(AuthenticationAcquiredInfo authInfo)
                throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationError(AuthenticationErrorInfo authInfo)
                throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationFailed(AuthenticationFailedInfo authInfo)
                throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationHelp(AuthenticationHelpInfo authInfo) throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStarted(AuthenticationStartedInfo authInfo)
                throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStopped(AuthenticationStoppedInfo authInfo)
                throws RemoteException {}

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationSucceeded(AuthenticationSucceededInfo authInfo)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements AuthenticationStateListener {
        static final int TRANSACTION_onAuthenticationAcquired = 1;
        static final int TRANSACTION_onAuthenticationError = 2;
        static final int TRANSACTION_onAuthenticationFailed = 3;
        static final int TRANSACTION_onAuthenticationHelp = 4;
        static final int TRANSACTION_onAuthenticationStarted = 5;
        static final int TRANSACTION_onAuthenticationStopped = 6;
        static final int TRANSACTION_onAuthenticationSucceeded = 7;

        public Stub() {
            attachInterface(this, AuthenticationStateListener.DESCRIPTOR);
        }

        public static AuthenticationStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(AuthenticationStateListener.DESCRIPTOR);
            if (iin != null && (iin instanceof AuthenticationStateListener)) {
                return (AuthenticationStateListener) iin;
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
                    return "onAuthenticationAcquired";
                case 2:
                    return "onAuthenticationError";
                case 3:
                    return "onAuthenticationFailed";
                case 4:
                    return "onAuthenticationHelp";
                case 5:
                    return "onAuthenticationStarted";
                case 6:
                    return "onAuthenticationStopped";
                case 7:
                    return "onAuthenticationSucceeded";
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
                data.enforceInterface(AuthenticationStateListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(AuthenticationStateListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AuthenticationAcquiredInfo _arg0 =
                            (AuthenticationAcquiredInfo)
                                    data.readTypedObject(AuthenticationAcquiredInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationAcquired(_arg0);
                    return true;
                case 2:
                    AuthenticationErrorInfo _arg02 =
                            (AuthenticationErrorInfo)
                                    data.readTypedObject(AuthenticationErrorInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationError(_arg02);
                    return true;
                case 3:
                    AuthenticationFailedInfo _arg03 =
                            (AuthenticationFailedInfo)
                                    data.readTypedObject(AuthenticationFailedInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationFailed(_arg03);
                    return true;
                case 4:
                    AuthenticationHelpInfo _arg04 =
                            (AuthenticationHelpInfo)
                                    data.readTypedObject(AuthenticationHelpInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationHelp(_arg04);
                    return true;
                case 5:
                    AuthenticationStartedInfo _arg05 =
                            (AuthenticationStartedInfo)
                                    data.readTypedObject(AuthenticationStartedInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationStarted(_arg05);
                    return true;
                case 6:
                    AuthenticationStoppedInfo _arg06 =
                            (AuthenticationStoppedInfo)
                                    data.readTypedObject(AuthenticationStoppedInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationStopped(_arg06);
                    return true;
                case 7:
                    AuthenticationSucceededInfo _arg07 =
                            (AuthenticationSucceededInfo)
                                    data.readTypedObject(AuthenticationSucceededInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onAuthenticationSucceeded(_arg07);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements AuthenticationStateListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return AuthenticationStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationAcquired(AuthenticationAcquiredInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationError(AuthenticationErrorInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationFailed(AuthenticationFailedInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationHelp(AuthenticationHelpInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStarted(AuthenticationStartedInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStopped(AuthenticationStoppedInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationSucceeded(AuthenticationSucceededInfo authInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    _data.writeTypedObject(authInfo, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
