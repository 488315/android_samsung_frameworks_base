package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IExynosDisplaySolutionManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IExynosDisplaySolutionManager";

    public static class Default implements IExynosDisplaySolutionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public String getColorEnhancementMode() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorEnhancementSettingValue(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorTempSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setColorTempSettingValue(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setDisplayFeature(String str, int i, int i2, String str2) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEdgeSharpnessSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEdgeSharpnessSettingValue(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEyeTempSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setEyeTempSettingValue(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setHsvGainSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setHsvGainSettingValue(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbGainSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbGainSettingValue(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbWeightSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setRgbWeightSettingValue(float f, float f2, float f3) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setSkinColorSettingOn(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IExynosDisplaySolutionManager
        public void setWhitePointColorSettingOn(int i) throws RemoteException {
        }
    }

    String getColorEnhancementMode() throws RemoteException;

    void setColorEnhancementSettingValue(int i) throws RemoteException;

    void setColorTempSettingOn(int i) throws RemoteException;

    void setColorTempSettingValue(int i, int i2) throws RemoteException;

    void setDisplayFeature(String str, int i, int i2, String str2) throws RemoteException;

    void setEdgeSharpnessSettingOn(int i) throws RemoteException;

    void setEdgeSharpnessSettingValue(int i) throws RemoteException;

    void setEyeTempSettingOn(int i) throws RemoteException;

    void setEyeTempSettingValue(int i) throws RemoteException;

    void setHsvGainSettingOn(int i) throws RemoteException;

    void setHsvGainSettingValue(int i, int i2, int i3) throws RemoteException;

    void setRgbGainSettingOn(int i) throws RemoteException;

    void setRgbGainSettingValue(int i, int i2, int i3) throws RemoteException;

    void setRgbWeightSettingOn(int i) throws RemoteException;

    void setRgbWeightSettingValue(float f, float f2, float f3) throws RemoteException;

    void setSkinColorSettingOn(int i) throws RemoteException;

    void setWhitePointColorSettingOn(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IExynosDisplaySolutionManager {
        static final int TRANSACTION_getColorEnhancementMode = 2;
        static final int TRANSACTION_setColorEnhancementSettingValue = 3;
        static final int TRANSACTION_setColorTempSettingOn = 5;
        static final int TRANSACTION_setColorTempSettingValue = 4;
        static final int TRANSACTION_setDisplayFeature = 1;
        static final int TRANSACTION_setEdgeSharpnessSettingOn = 17;
        static final int TRANSACTION_setEdgeSharpnessSettingValue = 16;
        static final int TRANSACTION_setEyeTempSettingOn = 7;
        static final int TRANSACTION_setEyeTempSettingValue = 6;
        static final int TRANSACTION_setHsvGainSettingOn = 14;
        static final int TRANSACTION_setHsvGainSettingValue = 13;
        static final int TRANSACTION_setRgbGainSettingOn = 9;
        static final int TRANSACTION_setRgbGainSettingValue = 8;
        static final int TRANSACTION_setRgbWeightSettingOn = 11;
        static final int TRANSACTION_setRgbWeightSettingValue = 10;
        static final int TRANSACTION_setSkinColorSettingOn = 12;
        static final int TRANSACTION_setWhitePointColorSettingOn = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }

        public Stub() {
            attachInterface(this, IExynosDisplaySolutionManager.DESCRIPTOR);
        }

        public static IExynosDisplaySolutionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IExynosDisplaySolutionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IExynosDisplaySolutionManager)) {
                return (IExynosDisplaySolutionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDisplayFeature";
                case 2:
                    return "getColorEnhancementMode";
                case 3:
                    return "setColorEnhancementSettingValue";
                case 4:
                    return "setColorTempSettingValue";
                case 5:
                    return "setColorTempSettingOn";
                case 6:
                    return "setEyeTempSettingValue";
                case 7:
                    return "setEyeTempSettingOn";
                case 8:
                    return "setRgbGainSettingValue";
                case 9:
                    return "setRgbGainSettingOn";
                case 10:
                    return "setRgbWeightSettingValue";
                case 11:
                    return "setRgbWeightSettingOn";
                case 12:
                    return "setSkinColorSettingOn";
                case 13:
                    return "setHsvGainSettingValue";
                case 14:
                    return "setHsvGainSettingOn";
                case 15:
                    return "setWhitePointColorSettingOn";
                case 16:
                    return "setEdgeSharpnessSettingValue";
                case 17:
                    return "setEdgeSharpnessSettingOn";
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
                parcel.enforceInterface(IExynosDisplaySolutionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExynosDisplaySolutionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDisplayFeature(readString, readInt, readInt2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String colorEnhancementMode = getColorEnhancementMode();
                    parcel2.writeNoException();
                    parcel2.writeString(colorEnhancementMode);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorEnhancementSettingValue(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorTempSettingValue(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorTempSettingOn(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEyeTempSettingValue(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEyeTempSettingOn(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbGainSettingValue(readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbGainSettingOn(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    float readFloat3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setRgbWeightSettingValue(readFloat, readFloat2, readFloat3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbWeightSettingOn(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSkinColorSettingOn(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHsvGainSettingValue(readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHsvGainSettingOn(readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWhitePointColorSettingOn(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeSharpnessSettingValue(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeSharpnessSettingOn(readInt21);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IExynosDisplaySolutionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExynosDisplaySolutionManager.DESCRIPTOR;
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setDisplayFeature(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public String getColorEnhancementMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorEnhancementSettingValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingValue(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingValue(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbWeightSettingValue(float f, float f2, float f3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeFloat(f3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbWeightSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setSkinColorSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingValue(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setWhitePointColorSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
