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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAODManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAODManager)) {
                return (IAODManager) queryLocalInterface;
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
                    boolean isAODState = isAODState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAODState);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAODTspRect(readInt, readInt2, readInt3, readInt4, readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAODNotiTspRect(readInt5, readInt6, readInt7, readInt8, readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    writeAODCommand(readString3, readString4, readString5, readString6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addLogText(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    long readLong5 = parcel.readLong();
                    long readLong6 = parcel.readLong();
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int liveClockInfo = setLiveClockInfo(readInt9, readLong, readLong2, readLong3, readLong4, readLong5, readLong6, readLong7, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockInfo);
                    return true;
                case 7:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setLiveClockNeedle(createByteArray);
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerAODListener(readStrongBinder);
                    parcel2.writeNoException();
                    break;
                case 11:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterAODListener(readStrongBinder2);
                    parcel2.writeNoException();
                    break;
                case 12:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerAODDozeCallback(readStrongBinder3);
                    parcel2.writeNoException();
                    break;
                case 13:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterAODDozeCallback(readStrongBinder4);
                    parcel2.writeNoException();
                    break;
                case 14:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireDoze(readStrongBinder5, readString8, readString9);
                    parcel2.writeNoException();
                    break;
                case 15:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    releaseDoze(readStrongBinder6);
                    parcel2.writeNoException();
                    break;
                case 16:
                    String readString10 = parcel.readString();
                    AODToast aODToast = (AODToast) parcel.readTypedObject(AODToast.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAODToast(readString10, aODToast);
                    parcel2.writeNoException();
                    break;
                case 17:
                    boolean isSViewCoverBrightnessHigh = isSViewCoverBrightnessHigh();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSViewCoverBrightnessHigh);
                    break;
                case 18:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int liveClockImage = setLiveClockImage(readInt10, readInt11, createByteArray2, readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockImage);
                    break;
                case 19:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int liveClockCommand = setLiveClockCommand(readInt12, readInt13, readInt14, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(liveClockCommand);
                    break;
                case 20:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String aodActiveArea = getAodActiveArea(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeString(aodActiveArea);
                    break;
                case 21:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGripData(readString12);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void updateAODTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void writeAODCommand(String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void addLogText(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeLong(j4);
                    obtain.writeLong(j5);
                    obtain.writeLong(j6);
                    obtain.writeLong(j7);
                    obtain.writeLong(j8);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void setLiveClockNeedle(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public String getActiveImageInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void readyToScreenTurningOn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void registerAODListener(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void unregisterAODListener(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void registerAODDozeCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void unregisterAODDozeCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void acquireDoze(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void releaseDoze(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void requestAODToast(String str, AODToast aODToast) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(aODToast, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public boolean isSViewCoverBrightnessHigh() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockImage(int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public int setLiveClockCommand(int i, int i2, int i3, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public String getAodActiveArea(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODManager
            public void setGripData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAODManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
