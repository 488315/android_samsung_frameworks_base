package android.hardware.radio.ims;

import android.hardware.radio.ims.IRadioImsIndication;
import android.hardware.radio.ims.IRadioImsResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioIms extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$ims$IRadioIms".replace('$', '.');
    public static final String HASH = "b2a615a151c7114c4216b1987fd32d40c797d00a";
    public static final int VERSION = 3;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void sendAnbrQuery(int i, int i2, int i3, int i4) throws RemoteException;

    void setResponseFunctions(IRadioImsResponse iRadioImsResponse, IRadioImsIndication iRadioImsIndication) throws RemoteException;

    void setSrvccCallInfo(int i, SrvccCall[] srvccCallArr) throws RemoteException;

    void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void stopImsTraffic(int i, int i2) throws RemoteException;

    void triggerEpsFallback(int i, int i2) throws RemoteException;

    void updateImsCallStatus(int i, ImsCall[] imsCallArr) throws RemoteException;

    void updateImsRegistrationInfo(int i, ImsRegistration imsRegistration) throws RemoteException;

    public static class Default implements IRadioIms {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void sendAnbrQuery(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void setResponseFunctions(IRadioImsResponse iRadioImsResponse, IRadioImsIndication iRadioImsIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void setSrvccCallInfo(int i, SrvccCall[] srvccCallArr) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void stopImsTraffic(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void triggerEpsFallback(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void updateImsCallStatus(int i, ImsCall[] imsCallArr) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public void updateImsRegistrationInfo(int i, ImsRegistration imsRegistration) throws RemoteException {
        }

        @Override // android.hardware.radio.ims.IRadioIms
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioIms {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_sendAnbrQuery = 7;
        static final int TRANSACTION_setResponseFunctions = 6;
        static final int TRANSACTION_setSrvccCallInfo = 1;
        static final int TRANSACTION_startImsTraffic = 3;
        static final int TRANSACTION_stopImsTraffic = 4;
        static final int TRANSACTION_triggerEpsFallback = 5;
        static final int TRANSACTION_updateImsCallStatus = 8;
        static final int TRANSACTION_updateImsRegistrationInfo = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioIms asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioIms)) {
                return (IRadioIms) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    SrvccCall[] srvccCallArr = (SrvccCall[]) parcel.createTypedArray(SrvccCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSrvccCallInfo(readInt, srvccCallArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ImsRegistration imsRegistration = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsRegistrationInfo(readInt2, imsRegistration);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startImsTraffic(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopImsTraffic(readInt8, readInt9);
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerEpsFallback(readInt10, readInt11);
                    return true;
                case 6:
                    IRadioImsResponse asInterface = IRadioImsResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioImsIndication asInterface2 = IRadioImsIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 7:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAnbrQuery(readInt12, readInt13, readInt14, readInt15);
                    return true;
                case 8:
                    int readInt16 = parcel.readInt();
                    ImsCall[] imsCallArr = (ImsCall[]) parcel.createTypedArray(ImsCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCallStatus(readInt16, imsCallArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioIms {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void setSrvccCallInfo(int i, SrvccCall[] srvccCallArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(srvccCallArr, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSrvccCallInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsRegistrationInfo(int i, ImsRegistration imsRegistration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsRegistration, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateImsRegistrationInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startImsTraffic is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void stopImsTraffic(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopImsTraffic is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void triggerEpsFallback(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method triggerEpsFallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void setResponseFunctions(IRadioImsResponse iRadioImsResponse, IRadioImsIndication iRadioImsIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioImsResponse);
                    obtain.writeStrongInterface(iRadioImsIndication);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void sendAnbrQuery(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendAnbrQuery is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsCallStatus(int i, ImsCall[] imsCallArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(imsCallArr, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateImsCallStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
