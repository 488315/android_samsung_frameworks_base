package com.samsung.android.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IAODManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.aod.IAODManager";

    public static class Default implements IAODManager {
        @Override // com.samsung.android.aod.IAODManager
        public void acquireDoze(IBinder iBinder, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void addLogText(List<String> list) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.aod.IAODManager
        public String getActiveImageInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.aod.IAODManager
        public String getAodActiveArea(boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.aod.IAODManager
        public boolean isAODState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.aod.IAODManager
        public boolean isSViewCoverBrightnessHigh() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.aod.IAODManager
        public void readyToScreenTurningOn() throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void registerAODDozeCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void registerAODListener(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void releaseDoze(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void requestAODToast(String str, AODToast aODToast) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void setGripData(String str) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public int setLiveClockCommand(int i, int i2, int i3, int[] iArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.aod.IAODManager
        public int setLiveClockImage(int i, int i2, byte[] bArr, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.aod.IAODManager
        public int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.aod.IAODManager
        public void setLiveClockNeedle(byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void unregisterAODDozeCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void unregisterAODListener(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void updateAODTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODManager
        public void writeAODCommand(String str, String str2, String str3, String str4, String str5) throws RemoteException {
        }
    }

    void acquireDoze(IBinder iBinder, String str, String str2) throws RemoteException;

    void addLogText(List<String> list) throws RemoteException;

    String getActiveImageInfo() throws RemoteException;

    String getAodActiveArea(boolean z) throws RemoteException;

    boolean isAODState() throws RemoteException;

    boolean isSViewCoverBrightnessHigh() throws RemoteException;

    void readyToScreenTurningOn() throws RemoteException;

    void registerAODDozeCallback(IBinder iBinder) throws RemoteException;

    void registerAODListener(IBinder iBinder) throws RemoteException;

    void releaseDoze(IBinder iBinder) throws RemoteException;

    void requestAODToast(String str, AODToast aODToast) throws RemoteException;

    void setGripData(String str) throws RemoteException;

    int setLiveClockCommand(int i, int i2, int i3, int[] iArr) throws RemoteException;

    int setLiveClockImage(int i, int i2, byte[] bArr, String str) throws RemoteException;

    int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) throws RemoteException;

    void setLiveClockNeedle(byte[] bArr) throws RemoteException;

    void unregisterAODDozeCallback(IBinder iBinder) throws RemoteException;

    void unregisterAODListener(IBinder iBinder) throws RemoteException;

    void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void updateAODTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void writeAODCommand(String str, String str2, String str3, String str4, String str5) throws RemoteException;

    public static abstract class Stub extends Binder implements IAODManager {
        static final int TRANSACTION_acquireDoze = 14;
        static final int TRANSACTION_addLogText = 5;
        static final int TRANSACTION_getActiveImageInfo = 8;
        static final int TRANSACTION_getAodActiveArea = 20;
        static final int TRANSACTION_isAODState = 1;
        static final int TRANSACTION_isSViewCoverBrightnessHigh = 17;
        static final int TRANSACTION_readyToScreenTurningOn = 9;
        static final int TRANSACTION_registerAODDozeCallback = 12;
        static final int TRANSACTION_registerAODListener = 10;
        static final int TRANSACTION_releaseDoze = 15;
        static final int TRANSACTION_requestAODToast = 16;
        static final int TRANSACTION_setGripData = 21;
        static final int TRANSACTION_setLiveClockCommand = 19;
        static final int TRANSACTION_setLiveClockImage = 18;
        static final int TRANSACTION_setLiveClockInfo = 6;
        static final int TRANSACTION_setLiveClockNeedle = 7;
        static final int TRANSACTION_unregisterAODDozeCallback = 13;
        static final int TRANSACTION_unregisterAODListener = 11;
        static final int TRANSACTION_updateAODNotiTspRect = 3;
        static final int TRANSACTION_updateAODTspRect = 2;
        static final int TRANSACTION_writeAODCommand = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }

        public Stub() {
            attachInterface(this, IAODManager.DESCRIPTOR);
        }

        public static IAODManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAODManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAODManager)) {
                return (IAODManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isAODState";
                case 2:
                    return "updateAODTspRect";
                case 3:
                    return "updateAODNotiTspRect";
                case 4:
                    return "writeAODCommand";
                case 5:
                    return "addLogText";
                case 6:
                    return "setLiveClockInfo";
                case 7:
                    return "setLiveClockNeedle";
                case 8:
                    return "getActiveImageInfo";
                case 9:
                    return "readyToScreenTurningOn";
                case 10:
                    return "registerAODListener";
                case 11:
                    return "unregisterAODListener";
                case 12:
                    return "registerAODDozeCallback";
                case 13:
                    return "unregisterAODDozeCallback";
                case 14:
                    return "acquireDoze";
                case 15:
                    return "releaseDoze";
                case 16:
                    return "requestAODToast";
                case 17:
                    return "isSViewCoverBrightnessHigh";
                case 18:
                    return "setLiveClockImage";
                case 19:
                    return "setLiveClockCommand";
                case 20:
                    return "getAodActiveArea";
                case 21:
                    return "setGripData";
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
                parcel.enforceInterface(IAODManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAODManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsAODState = isAODState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAODState);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAODTspRect(i3, i4, i5, i6, string);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAODNotiTspRect(i7, i8, i9, i10, string2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    writeAODCommand(string3, string4, string5, string6, string7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addLogText(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    long j5 = parcel.readLong();
                    long j6 = parcel.readLong();
                    long j7 = parcel.readLong();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int liveClockInfo = setLiveClockInfo(i11, j, j2, j3, j4, j5, j6, j7, j8);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockInfo);
                    return true;
                case 7:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setLiveClockNeedle(bArrCreateByteArray);
                    parcel2.writeNoException();
                    break;
                case 8:
                    String activeImageInfo = getActiveImageInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(activeImageInfo);
                    break;
                case 9:
                    readyToScreenTurningOn();
                    parcel2.writeNoException();
                    break;
                case 10:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerAODListener(strongBinder);
                    parcel2.writeNoException();
                    break;
                case 11:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterAODListener(strongBinder2);
                    parcel2.writeNoException();
                    break;
                case 12:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerAODDozeCallback(strongBinder3);
                    parcel2.writeNoException();
                    break;
                case 13:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterAODDozeCallback(strongBinder4);
                    parcel2.writeNoException();
                    break;
                case 14:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireDoze(strongBinder5, string8, string9);
                    parcel2.writeNoException();
                    break;
                case 15:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    releaseDoze(strongBinder6);
                    parcel2.writeNoException();
                    break;
                case 16:
                    String string10 = parcel.readString();
                    AODToast aODToast = (AODToast) parcel.readTypedObject(AODToast.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAODToast(string10, aODToast);
                    parcel2.writeNoException();
                    break;
                case 17:
                    boolean zIsSViewCoverBrightnessHigh = isSViewCoverBrightnessHigh();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSViewCoverBrightnessHigh);
                    break;
                case 18:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int liveClockImage = setLiveClockImage(i12, i13, bArrCreateByteArray2, string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockImage);
                    break;
                case 19:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int liveClockCommand = setLiveClockCommand(i14, i15, i16, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockCommand);
                    break;
                case 20:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String aodActiveArea = getAodActiveArea(z);
                    parcel2.writeNoException();
                    parcel2.writeString(aodActiveArea);
                    break;
                case 21:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGripData(string12);
                    parcel2.writeNoException();
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAODManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAODManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.aod.IAODManager
            public boolean isAODState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void updateAODTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void writeAODCommand(String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void addLogText(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeLong(j4);
                    parcelObtain.writeLong(j5);
                    parcelObtain.writeLong(j6);
                    parcelObtain.writeLong(j7);
                    parcelObtain.writeLong(j8);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void setLiveClockNeedle(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public String getActiveImageInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void readyToScreenTurningOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void registerAODListener(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void unregisterAODListener(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void registerAODDozeCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void unregisterAODDozeCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void acquireDoze(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void releaseDoze(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void requestAODToast(String str, AODToast aODToast) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(aODToast, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public boolean isSViewCoverBrightnessHigh() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockImage(int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockCommand(int i, int i2, int i3, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public String getAodActiveArea(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void setGripData(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
