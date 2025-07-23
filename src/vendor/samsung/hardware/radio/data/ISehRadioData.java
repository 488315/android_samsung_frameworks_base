package vendor.samsung.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.data.ISehRadioDataIndication;
import vendor.samsung.hardware.radio.data.ISehRadioDataResponse;

/* loaded from: classes6.dex */
public interface ISehRadioData extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$data$ISehRadioData".replace('$', '.');
    public static final String HASH = "1c18f89373d68cf0030dbdb95f4a9287fe232a2e";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void setDataAllowed(int i, boolean z, SehAllowDataParam sehAllowDataParam) throws RemoteException;

    void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException;

    void setResponseFunctions(ISehRadioDataResponse iSehRadioDataResponse, ISehRadioDataIndication iSehRadioDataIndication) throws RemoteException;

    public static class Default implements ISehRadioData {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioData
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioData
        public void setDataAllowed(int i, boolean z, SehAllowDataParam sehAllowDataParam) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioData
        public void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioData
        public void setResponseFunctions(ISehRadioDataResponse iSehRadioDataResponse, ISehRadioDataIndication iSehRadioDataIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioData
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioData {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setDataAllowed = 1;
        static final int TRANSACTION_setMobileDataSetting = 2;
        static final int TRANSACTION_setResponseFunctions = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioData asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioData)) {
                return (ISehRadioData) queryLocalInterface;
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
                boolean readBoolean = parcel.readBoolean();
                SehAllowDataParam sehAllowDataParam = (SehAllowDataParam) parcel.readTypedObject(SehAllowDataParam.CREATOR);
                parcel.enforceNoDataAvail();
                setDataAllowed(readInt, readBoolean, sehAllowDataParam);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                boolean readBoolean3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setMobileDataSetting(readInt2, readBoolean2, readBoolean3);
            } else if (i == 3) {
                ISehRadioDataResponse asInterface = ISehRadioDataResponse.Stub.asInterface(parcel.readStrongBinder());
                ISehRadioDataIndication asInterface2 = ISehRadioDataIndication.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setResponseFunctions(asInterface, asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISehRadioData {
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
            public void setDataAllowed(int i, boolean z, SehAllowDataParam sehAllowDataParam) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(sehAllowDataParam, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataAllowed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
            public void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMobileDataSetting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
            public void setResponseFunctions(ISehRadioDataResponse iSehRadioDataResponse, ISehRadioDataIndication iSehRadioDataIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioDataResponse);
                    obtain.writeStrongInterface(iSehRadioDataIndication);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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
