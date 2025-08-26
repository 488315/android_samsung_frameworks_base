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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioData)) {
                return (ISehRadioData) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                boolean z = parcel.readBoolean();
                SehAllowDataParam sehAllowDataParam = (SehAllowDataParam) parcel.readTypedObject(SehAllowDataParam.CREATOR);
                parcel.enforceNoDataAvail();
                setDataAllowed(i3, z, sehAllowDataParam);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setMobileDataSetting(i4, z2, z3);
            } else if (i == 3) {
                ISehRadioDataResponse iSehRadioDataResponseAsInterface = ISehRadioDataResponse.Stub.asInterface(parcel.readStrongBinder());
                ISehRadioDataIndication iSehRadioDataIndicationAsInterface = ISehRadioDataIndication.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setResponseFunctions(iSehRadioDataResponseAsInterface, iSehRadioDataIndicationAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(sehAllowDataParam, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataAllowed is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
            public void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMobileDataSetting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
            public void setResponseFunctions(ISehRadioDataResponse iSehRadioDataResponse, ISehRadioDataIndication iSehRadioDataIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSehRadioDataResponse);
                    parcelObtain.writeStrongInterface(iSehRadioDataIndication);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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
