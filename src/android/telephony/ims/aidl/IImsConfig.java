package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.telephony.ims.RcsClientConfiguration;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IRcsConfigCallback;

/* loaded from: classes4.dex */
public interface IImsConfig extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsConfig";

    public static class Default implements IImsConfig {
        @Override // android.telephony.ims.aidl.IImsConfig
        public void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int getConfigInt(int i) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getConfigString(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getRcsClientConfiguration(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyIntImsConfigChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedIntValueChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedStringValueChanged(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationErrorReceived(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationRemoved() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsPreConfigurationReceived(byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyStringImsConfigChanged(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigInt(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigString(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void setRcsClientConfiguration(RcsClientConfiguration rcsClientConfiguration) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void triggerRcsReconfiguration() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void updateImsCarrierConfigs(PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void addRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    int getConfigInt(int i) throws RemoteException;

    String getConfigString(int i) throws RemoteException;

    String getRcsClientConfiguration(int i) throws RemoteException;

    void notifyIntImsConfigChanged(int i, int i2) throws RemoteException;

    void notifyProvisionedIntValueChanged(int i, int i2) throws RemoteException;

    void notifyProvisionedStringValueChanged(int i, String str) throws RemoteException;

    void notifyRcsAutoConfigurationErrorReceived(int i, String str) throws RemoteException;

    void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws RemoteException;

    void notifyRcsAutoConfigurationRemoved() throws RemoteException;

    void notifyRcsPreConfigurationReceived(byte[] bArr) throws RemoteException;

    void notifyStringImsConfigChanged(int i, String str) throws RemoteException;

    void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void removeRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    int setConfigInt(int i, int i2) throws RemoteException;

    int setConfigString(int i, String str) throws RemoteException;

    void setRcsClientConfiguration(RcsClientConfiguration rcsClientConfiguration) throws RemoteException;

    void triggerRcsReconfiguration() throws RemoteException;

    void updateImsCarrierConfigs(PersistableBundle persistableBundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsConfig {
        static final int TRANSACTION_addImsConfigCallback = 1;
        static final int TRANSACTION_addRcsConfigCallback = 14;
        static final int TRANSACTION_getConfigInt = 3;
        static final int TRANSACTION_getConfigString = 4;
        static final int TRANSACTION_getRcsClientConfiguration = 17;
        static final int TRANSACTION_notifyIntImsConfigChanged = 19;
        static final int TRANSACTION_notifyProvisionedIntValueChanged = 12;
        static final int TRANSACTION_notifyProvisionedStringValueChanged = 13;
        static final int TRANSACTION_notifyRcsAutoConfigurationErrorReceived = 10;
        static final int TRANSACTION_notifyRcsAutoConfigurationReceived = 8;
        static final int TRANSACTION_notifyRcsAutoConfigurationRemoved = 9;
        static final int TRANSACTION_notifyRcsPreConfigurationReceived = 11;
        static final int TRANSACTION_notifyStringImsConfigChanged = 20;
        static final int TRANSACTION_removeImsConfigCallback = 2;
        static final int TRANSACTION_removeRcsConfigCallback = 15;
        static final int TRANSACTION_setConfigInt = 5;
        static final int TRANSACTION_setConfigString = 6;
        static final int TRANSACTION_setRcsClientConfiguration = 18;
        static final int TRANSACTION_triggerRcsReconfiguration = 16;
        static final int TRANSACTION_updateImsCarrierConfigs = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, IImsConfig.DESCRIPTOR);
        }

        public static IImsConfig asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsConfig.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsConfig)) {
                return (IImsConfig) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addImsConfigCallback";
                case 2:
                    return "removeImsConfigCallback";
                case 3:
                    return "getConfigInt";
                case 4:
                    return "getConfigString";
                case 5:
                    return "setConfigInt";
                case 6:
                    return "setConfigString";
                case 7:
                    return "updateImsCarrierConfigs";
                case 8:
                    return "notifyRcsAutoConfigurationReceived";
                case 9:
                    return "notifyRcsAutoConfigurationRemoved";
                case 10:
                    return "notifyRcsAutoConfigurationErrorReceived";
                case 11:
                    return "notifyRcsPreConfigurationReceived";
                case 12:
                    return "notifyProvisionedIntValueChanged";
                case 13:
                    return "notifyProvisionedStringValueChanged";
                case 14:
                    return "addRcsConfigCallback";
                case 15:
                    return "removeRcsConfigCallback";
                case 16:
                    return "triggerRcsReconfiguration";
                case 17:
                    return "getRcsClientConfiguration";
                case 18:
                    return "setRcsClientConfiguration";
                case 19:
                    return "notifyIntImsConfigChanged";
                case 20:
                    return "notifyStringImsConfigChanged";
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
                parcel.enforceInterface(IImsConfig.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsConfig.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsConfigCallback iImsConfigCallbackAsInterface = IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addImsConfigCallback(iImsConfigCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IImsConfigCallback iImsConfigCallbackAsInterface2 = IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeImsConfigCallback(iImsConfigCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int configInt = getConfigInt(i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(configInt);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String configString = getConfigString(i4);
                    parcel2.writeNoException();
                    parcel2.writeString(configString);
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int configInt2 = setConfigInt(i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(configInt2);
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int configString2 = setConfigString(i7, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(configString2);
                    return true;
                case 7:
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateImsCarrierConfigs(persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRcsAutoConfigurationReceived(bArrCreateByteArray, z);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    notifyRcsAutoConfigurationRemoved();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i8 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyRcsAutoConfigurationErrorReceived(i8, string2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyRcsPreConfigurationReceived(bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyProvisionedIntValueChanged(i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i11 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyProvisionedStringValueChanged(i11, string3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IRcsConfigCallback iRcsConfigCallbackAsInterface = IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRcsConfigCallback(iRcsConfigCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IRcsConfigCallback iRcsConfigCallbackAsInterface2 = IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRcsConfigCallback(iRcsConfigCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    triggerRcsReconfiguration();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String rcsClientConfiguration = getRcsClientConfiguration(i12);
                    parcel2.writeNoException();
                    parcel2.writeString(rcsClientConfiguration);
                    return true;
                case 18:
                    RcsClientConfiguration rcsClientConfiguration2 = (RcsClientConfiguration) parcel.readTypedObject(RcsClientConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRcsClientConfiguration(rcsClientConfiguration2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyIntImsConfigChanged(i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i15 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyStringImsConfigChanged(i15, string4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsConfig {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsConfig.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int getConfigInt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public String getConfigString(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigInt(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public int setConfigString(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void updateImsCarrierConfigs(PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationRemoved() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsAutoConfigurationErrorReceived(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyRcsPreConfigurationReceived(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyProvisionedIntValueChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyProvisionedStringValueChanged(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void addRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void removeRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void triggerRcsReconfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public String getRcsClientConfiguration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void setRcsClientConfiguration(RcsClientConfiguration rcsClientConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rcsClientConfiguration, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyIntImsConfigChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsConfig
            public void notifyStringImsConfigChanged(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsConfig.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
