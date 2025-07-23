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
    public static final String DESCRIPTOR = "android.hardware.biometrics.AuthenticationStateListener";

    public static class Default implements AuthenticationStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationAcquired(AuthenticationAcquiredInfo authenticationAcquiredInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationError(AuthenticationErrorInfo authenticationErrorInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationFailed(AuthenticationFailedInfo authenticationFailedInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationHelp(AuthenticationHelpInfo authenticationHelpInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStarted(AuthenticationStartedInfo authenticationStartedInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationStopped(AuthenticationStoppedInfo authenticationStoppedInfo) throws RemoteException {
        }

        @Override // android.hardware.biometrics.AuthenticationStateListener
        public void onAuthenticationSucceeded(AuthenticationSucceededInfo authenticationSucceededInfo) throws RemoteException {
        }
    }

    void onAuthenticationAcquired(AuthenticationAcquiredInfo authenticationAcquiredInfo) throws RemoteException;

    void onAuthenticationError(AuthenticationErrorInfo authenticationErrorInfo) throws RemoteException;

    void onAuthenticationFailed(AuthenticationFailedInfo authenticationFailedInfo) throws RemoteException;

    void onAuthenticationHelp(AuthenticationHelpInfo authenticationHelpInfo) throws RemoteException;

    void onAuthenticationStarted(AuthenticationStartedInfo authenticationStartedInfo) throws RemoteException;

    void onAuthenticationStopped(AuthenticationStoppedInfo authenticationStoppedInfo) throws RemoteException;

    void onAuthenticationSucceeded(AuthenticationSucceededInfo authenticationSucceededInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements AuthenticationStateListener {
        static final int TRANSACTION_onAuthenticationAcquired = 1;
        static final int TRANSACTION_onAuthenticationError = 2;
        static final int TRANSACTION_onAuthenticationFailed = 3;
        static final int TRANSACTION_onAuthenticationHelp = 4;
        static final int TRANSACTION_onAuthenticationStarted = 5;
        static final int TRANSACTION_onAuthenticationStopped = 6;
        static final int TRANSACTION_onAuthenticationSucceeded = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, AuthenticationStateListener.DESCRIPTOR);
        }

        public static AuthenticationStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(AuthenticationStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof AuthenticationStateListener)) {
                return (AuthenticationStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(AuthenticationStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(AuthenticationStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AuthenticationAcquiredInfo authenticationAcquiredInfo = (AuthenticationAcquiredInfo) parcel.readTypedObject(AuthenticationAcquiredInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationAcquired(authenticationAcquiredInfo);
                    return true;
                case 2:
                    AuthenticationErrorInfo authenticationErrorInfo = (AuthenticationErrorInfo) parcel.readTypedObject(AuthenticationErrorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationError(authenticationErrorInfo);
                    return true;
                case 3:
                    AuthenticationFailedInfo authenticationFailedInfo = (AuthenticationFailedInfo) parcel.readTypedObject(AuthenticationFailedInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationFailed(authenticationFailedInfo);
                    return true;
                case 4:
                    AuthenticationHelpInfo authenticationHelpInfo = (AuthenticationHelpInfo) parcel.readTypedObject(AuthenticationHelpInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationHelp(authenticationHelpInfo);
                    return true;
                case 5:
                    AuthenticationStartedInfo authenticationStartedInfo = (AuthenticationStartedInfo) parcel.readTypedObject(AuthenticationStartedInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationStarted(authenticationStartedInfo);
                    return true;
                case 6:
                    AuthenticationStoppedInfo authenticationStoppedInfo = (AuthenticationStoppedInfo) parcel.readTypedObject(AuthenticationStoppedInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationStopped(authenticationStoppedInfo);
                    return true;
                case 7:
                    AuthenticationSucceededInfo authenticationSucceededInfo = (AuthenticationSucceededInfo) parcel.readTypedObject(AuthenticationSucceededInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(authenticationSucceededInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements AuthenticationStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return AuthenticationStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationAcquired(AuthenticationAcquiredInfo authenticationAcquiredInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationAcquiredInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationError(AuthenticationErrorInfo authenticationErrorInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationErrorInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationFailed(AuthenticationFailedInfo authenticationFailedInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationFailedInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationHelp(AuthenticationHelpInfo authenticationHelpInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationHelpInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStarted(AuthenticationStartedInfo authenticationStartedInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationStartedInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationStopped(AuthenticationStoppedInfo authenticationStoppedInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationStoppedInfo, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.AuthenticationStateListener
            public void onAuthenticationSucceeded(AuthenticationSucceededInfo authenticationSucceededInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(AuthenticationStateListener.DESCRIPTOR);
                    obtain.writeTypedObject(authenticationSucceededInfo, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
