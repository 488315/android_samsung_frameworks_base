package com.samsung.android.gesture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMotionRecognitionService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.gesture.IMotionRecognitionService";

    public static class Default implements IMotionRecognitionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void enableSARDevice(boolean z, long j, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public String getEvLuxTableInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public float[] getEvToLux(float[] fArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean getPickUpMotionStatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean getSSPstatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean isAvailable(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void registerCallback(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public int resetMotionEngine() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void setMotionAngle(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean setTestSensor() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void startAdaptiveBrightness() throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void stopAdaptiveBrightness() throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void unregisterCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void useMotionAlways(IBinder iBinder, boolean z) throws RemoteException {
        }
    }

    void enableSARDevice(boolean z, long j, int i, int i2) throws RemoteException;

    String getEvLuxTableInfo(String str) throws RemoteException;

    float[] getEvToLux(float[] fArr) throws RemoteException;

    boolean getPickUpMotionStatus() throws RemoteException;

    boolean getSSPstatus() throws RemoteException;

    boolean isAvailable(int i) throws RemoteException;

    void registerCallback(IBinder iBinder, int i, int i2) throws RemoteException;

    int resetMotionEngine() throws RemoteException;

    void setMotionAngle(IBinder iBinder, int i) throws RemoteException;

    void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    boolean setTestSensor() throws RemoteException;

    void startAdaptiveBrightness() throws RemoteException;

    void stopAdaptiveBrightness() throws RemoteException;

    void unregisterCallback(IBinder iBinder) throws RemoteException;

    void useMotionAlways(IBinder iBinder, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IMotionRecognitionService {
        static final int TRANSACTION_enableSARDevice = 15;
        static final int TRANSACTION_getEvLuxTableInfo = 11;
        static final int TRANSACTION_getEvToLux = 10;
        static final int TRANSACTION_getPickUpMotionStatus = 3;
        static final int TRANSACTION_getSSPstatus = 2;
        static final int TRANSACTION_isAvailable = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_resetMotionEngine = 8;
        static final int TRANSACTION_setMotionAngle = 6;
        static final int TRANSACTION_setMotionTiltLevel = 7;
        static final int TRANSACTION_setTestSensor = 12;
        static final int TRANSACTION_startAdaptiveBrightness = 13;
        static final int TRANSACTION_stopAdaptiveBrightness = 14;
        static final int TRANSACTION_unregisterCallback = 4;
        static final int TRANSACTION_useMotionAlways = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, IMotionRecognitionService.DESCRIPTOR);
        }

        public static IMotionRecognitionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMotionRecognitionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMotionRecognitionService)) {
                return (IMotionRecognitionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "getSSPstatus";
                case 3:
                    return "getPickUpMotionStatus";
                case 4:
                    return "unregisterCallback";
                case 5:
                    return "useMotionAlways";
                case 6:
                    return "setMotionAngle";
                case 7:
                    return "setMotionTiltLevel";
                case 8:
                    return "resetMotionEngine";
                case 9:
                    return "isAvailable";
                case 10:
                    return "getEvToLux";
                case 11:
                    return "getEvLuxTableInfo";
                case 12:
                    return "setTestSensor";
                case 13:
                    return "startAdaptiveBrightness";
                case 14:
                    return "stopAdaptiveBrightness";
                case 15:
                    return "enableSARDevice";
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
                parcel.enforceInterface(IMotionRecognitionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMotionRecognitionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(strongBinder, i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean sSPstatus = getSSPstatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sSPstatus);
                    return true;
                case 3:
                    boolean pickUpMotionStatus = getPickUpMotionStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pickUpMotionStatus);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    useMotionAlways(strongBinder3, z);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMotionAngle(strongBinder4, i5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMotionTiltLevel(i6, i7, i8, i9, i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int iResetMotionEngine = resetMotionEngine();
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetMotionEngine);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAvailable = isAvailable(i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAvailable);
                    return true;
                case 10:
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    float[] evToLux = getEvToLux(fArrCreateFloatArray);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(evToLux);
                    return true;
                case 11:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String evLuxTableInfo = getEvLuxTableInfo(string);
                    parcel2.writeNoException();
                    parcel2.writeString(evLuxTableInfo);
                    return true;
                case 12:
                    boolean testSensor = setTestSensor();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(testSensor);
                    return true;
                case 13:
                    startAdaptiveBrightness();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    stopAdaptiveBrightness();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z2 = parcel.readBoolean();
                    long j = parcel.readLong();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableSARDevice(z2, j, i13, i14);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMotionRecognitionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMotionRecognitionService.DESCRIPTOR;
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void registerCallback(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getSSPstatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getPickUpMotionStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void unregisterCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void useMotionAlways(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionAngle(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public int resetMotionEngine() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean isAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public float[] getEvToLux(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public String getEvLuxTableInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean setTestSensor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void startAdaptiveBrightness() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void stopAdaptiveBrightness() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void enableSARDevice(boolean z, long j, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
