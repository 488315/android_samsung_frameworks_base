package android.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioDataIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$data$IRadioDataIndication".replace('$', '.');
    public static final String HASH = "70713939dbe39fdbd3a294b3a3e3d2842b3bf4eb";
    public static final int VERSION = 4;

    void dataCallListChanged(int i, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void keepaliveStatus(int i, KeepaliveStatus keepaliveStatus) throws RemoteException;

    void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException;

    void slicingConfigChanged(int i, SlicingConfig slicingConfig) throws RemoteException;

    void unthrottleApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException;

    public static class Default implements IRadioDataIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void dataCallListChanged(int i, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void keepaliveStatus(int i, KeepaliveStatus keepaliveStatus) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void slicingConfigChanged(int i, SlicingConfig slicingConfig) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public void unthrottleApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioDataIndication {
        static final int TRANSACTION_dataCallListChanged = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_keepaliveStatus = 2;
        static final int TRANSACTION_pcoData = 3;
        static final int TRANSACTION_slicingConfigChanged = 5;
        static final int TRANSACTION_unthrottleApn = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioDataIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioDataIndication)) {
                return (IRadioDataIndication) queryLocalInterface;
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
            if (i == 1) {
                int readInt = parcel.readInt();
                SetupDataCallResult[] setupDataCallResultArr = (SetupDataCallResult[]) parcel.createTypedArray(SetupDataCallResult.CREATOR);
                parcel.enforceNoDataAvail();
                dataCallListChanged(readInt, setupDataCallResultArr);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                KeepaliveStatus keepaliveStatus = (KeepaliveStatus) parcel.readTypedObject(KeepaliveStatus.CREATOR);
                parcel.enforceNoDataAvail();
                keepaliveStatus(readInt2, keepaliveStatus);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                PcoDataInfo pcoDataInfo = (PcoDataInfo) parcel.readTypedObject(PcoDataInfo.CREATOR);
                parcel.enforceNoDataAvail();
                pcoData(readInt3, pcoDataInfo);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                DataProfileInfo dataProfileInfo = (DataProfileInfo) parcel.readTypedObject(DataProfileInfo.CREATOR);
                parcel.enforceNoDataAvail();
                unthrottleApn(readInt4, dataProfileInfo);
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                SlicingConfig slicingConfig = (SlicingConfig) parcel.readTypedObject(SlicingConfig.CREATOR);
                parcel.enforceNoDataAvail();
                slicingConfigChanged(readInt5, slicingConfig);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRadioDataIndication {
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

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void dataCallListChanged(int i, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(setupDataCallResultArr, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dataCallListChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void keepaliveStatus(int i, KeepaliveStatus keepaliveStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(keepaliveStatus, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method keepaliveStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pcoDataInfo, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method pcoData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void unthrottleApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method unthrottleApn is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
            public void slicingConfigChanged(int i, SlicingConfig slicingConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(slicingConfig, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method slicingConfigChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataIndication
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

            @Override // android.hardware.radio.data.IRadioDataIndication
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
