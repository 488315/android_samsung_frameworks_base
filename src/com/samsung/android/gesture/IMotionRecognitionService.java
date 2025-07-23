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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMotionRecognitionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMotionRecognitionService)) {
                return (IMotionRecognitionService) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(readStrongBinder, readInt, readInt2);
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
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    useMotionAlways(readStrongBinder3, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMotionAngle(readStrongBinder4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMotionTiltLevel(readInt4, readInt5, readInt6, readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int resetMotionEngine = resetMotionEngine();
                    parcel2.writeNoException();
                    parcel2.writeInt(resetMotionEngine);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAvailable = isAvailable(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailable);
                    return true;
                case 10:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    float[] evToLux = getEvToLux(createFloatArray);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(evToLux);
                    return true;
                case 11:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String evLuxTableInfo = getEvLuxTableInfo(readString);
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
                    boolean readBoolean2 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableSARDevice(readBoolean2, readLong, readInt11, readInt12);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getSSPstatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getPickUpMotionStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void unregisterCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void useMotionAlways(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionAngle(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public int resetMotionEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean isAvailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public float[] getEvToLux(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public String getEvLuxTableInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean setTestSensor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void startAdaptiveBrightness() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void stopAdaptiveBrightness() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void enableSARDevice(boolean z, long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
