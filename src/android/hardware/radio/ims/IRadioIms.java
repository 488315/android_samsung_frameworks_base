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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioIms)) {
                return (IRadioIms) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    SrvccCall[] srvccCallArr = (SrvccCall[]) parcel.createTypedArray(SrvccCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSrvccCallInfo(i3, srvccCallArr);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    ImsRegistration imsRegistration = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsRegistrationInfo(i4, imsRegistration);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startImsTraffic(i5, i6, i7, i8, i9);
                    return true;
                case 4:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopImsTraffic(i10, i11);
                    return true;
                case 5:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerEpsFallback(i12, i13);
                    return true;
                case 6:
                    IRadioImsResponse iRadioImsResponseAsInterface = IRadioImsResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioImsIndication iRadioImsIndicationAsInterface = IRadioImsIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioImsResponseAsInterface, iRadioImsIndicationAsInterface);
                    return true;
                case 7:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAnbrQuery(i14, i15, i16, i17);
                    return true;
                case 8:
                    int i18 = parcel.readInt();
                    ImsCall[] imsCallArr = (ImsCall[]) parcel.createTypedArray(ImsCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCallStatus(i18, imsCallArr);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(srvccCallArr, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSrvccCallInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsRegistrationInfo(int i, ImsRegistration imsRegistration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsRegistration, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateImsRegistrationInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void startImsTraffic(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startImsTraffic is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void stopImsTraffic(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopImsTraffic is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void triggerEpsFallback(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method triggerEpsFallback is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void setResponseFunctions(IRadioImsResponse iRadioImsResponse, IRadioImsIndication iRadioImsIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioImsResponse);
                    parcelObtain.writeStrongInterface(iRadioImsIndication);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void sendAnbrQuery(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendAnbrQuery is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public void updateImsCallStatus(int i, ImsCall[] imsCallArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(imsCallArr, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateImsCallStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.ims.IRadioIms
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
