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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExynosDisplaySolutionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExynosDisplaySolutionManager)) {
                return (IExynosDisplaySolutionManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDisplayFeature(string, i3, i4, string2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String colorEnhancementMode = getColorEnhancementMode();
                    parcel2.writeNoException();
                    parcel2.writeString(colorEnhancementMode);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorEnhancementSettingValue(i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorTempSettingValue(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorTempSettingOn(i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEyeTempSettingValue(i9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEyeTempSettingOn(i10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbGainSettingValue(i11, i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbGainSettingOn(i14);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    float f3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setRgbWeightSettingValue(f, f2, f3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRgbWeightSettingOn(i15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSkinColorSettingOn(i16);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHsvGainSettingValue(i17, i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHsvGainSettingOn(i20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWhitePointColorSettingOn(i21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeSharpnessSettingValue(i22);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeSharpnessSettingOn(i23);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public String getColorEnhancementMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorEnhancementSettingValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingValue(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setColorTempSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEyeTempSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingValue(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbGainSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbWeightSettingValue(float f, float f2, float f3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setRgbWeightSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setSkinColorSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingValue(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setHsvGainSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setWhitePointColorSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingValue(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IExynosDisplaySolutionManager
            public void setEdgeSharpnessSettingOn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExynosDisplaySolutionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
