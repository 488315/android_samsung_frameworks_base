package vendor.samsung.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioDataIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$data$ISehRadioDataIndication".replace('$', '.');
    public static final String HASH = "1c18f89373d68cf0030dbdb95f4a9287fe232a2e";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    SehApnProfile needApnProfileIndication(String str) throws RemoteException;

    SehPacketUsage needPacketUsage(String str) throws RemoteException;

    int needSettingValueIndication(String str, String str2) throws RemoteException;

    void rrcStateChanged(int i, SehRrcStateInfo sehRrcStateInfo) throws RemoteException;

    void timerStatusChangedInd(int i, int[] iArr) throws RemoteException;

    public static class Default implements ISehRadioDataIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public SehApnProfile needApnProfileIndication(String str) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public SehPacketUsage needPacketUsage(String str) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public int needSettingValueIndication(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public void rrcStateChanged(int i, SehRrcStateInfo sehRrcStateInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public void timerStatusChangedInd(int i, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioDataIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_needApnProfileIndication = 3;
        static final int TRANSACTION_needPacketUsage = 5;
        static final int TRANSACTION_needSettingValueIndication = 4;
        static final int TRANSACTION_rrcStateChanged = 1;
        static final int TRANSACTION_timerStatusChangedInd = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioDataIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioDataIndication)) {
                return (ISehRadioDataIndication) queryLocalInterface;
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
                SehRrcStateInfo sehRrcStateInfo = (SehRrcStateInfo) parcel.readTypedObject(SehRrcStateInfo.CREATOR);
                parcel.enforceNoDataAvail();
                rrcStateChanged(readInt, sehRrcStateInfo);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                timerStatusChangedInd(readInt2, createIntArray);
            } else if (i == 3) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                SehApnProfile needApnProfileIndication = needApnProfileIndication(readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(needApnProfileIndication, 1);
            } else if (i == 4) {
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                int needSettingValueIndication = needSettingValueIndication(readString2, readString3);
                parcel2.writeNoException();
                parcel2.writeInt(needSettingValueIndication);
            } else if (i == 5) {
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                SehPacketUsage needPacketUsage = needPacketUsage(readString4);
                parcel2.writeNoException();
                parcel2.writeTypedObject(needPacketUsage, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISehRadioDataIndication {
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
            public void rrcStateChanged(int i, SehRrcStateInfo sehRrcStateInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehRrcStateInfo, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method rrcStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
            public void timerStatusChangedInd(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method timerStatusChangedInd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
            public SehApnProfile needApnProfileIndication(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method needApnProfileIndication is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehApnProfile) obtain2.readTypedObject(SehApnProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
            public int needSettingValueIndication(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method needSettingValueIndication is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
            public SehPacketUsage needPacketUsage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method needPacketUsage is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehPacketUsage) obtain2.readTypedObject(SehPacketUsage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
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
