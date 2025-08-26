package com.samsung.android.biometrics;

import android.hardware.biometrics.PromptInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public interface ISemBiometricSysUiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiService";

    public static class Default implements ISemBiometricSysUiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void hideBiometricDialog(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricAuthenticated(int i, int i2, boolean z, String str) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricError(int i, int i2, int i3, int i4, String str) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricHelp(int i, int i2, int i3, int i4, String str) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void sendCommand(int i, int i2, int i3, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void showBiometricDialog(int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) throws RemoteException {
        }
    }

    void hideBiometricDialog(int i, int i2, int i3) throws RemoteException;

    void onBiometricAuthenticated(int i, int i2, boolean z, String str) throws RemoteException;

    void onBiometricError(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void onBiometricHelp(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void sendCommand(int i, int i2, int i3, Bundle bundle) throws RemoteException;

    void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) throws RemoteException;

    void showBiometricDialog(int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemBiometricSysUiService {
        static final int TRANSACTION_hideBiometricDialog = 5;
        static final int TRANSACTION_onBiometricAuthenticated = 2;
        static final int TRANSACTION_onBiometricError = 4;
        static final int TRANSACTION_onBiometricHelp = 3;
        static final int TRANSACTION_sendCommand = 6;
        static final int TRANSACTION_setBiometricTheme = 7;
        static final int TRANSACTION_showBiometricDialog = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ISemBiometricSysUiService.DESCRIPTOR);
        }

        public static ISemBiometricSysUiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemBiometricSysUiService)) {
                return (ISemBiometricSysUiService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showBiometricDialog";
                case 2:
                    return "onBiometricAuthenticated";
                case 3:
                    return "onBiometricHelp";
                case 4:
                    return "onBiometricError";
                case 5:
                    return "hideBiometricDialog";
                case 6:
                    return "sendCommand";
                case 7:
                    return "setBiometricTheme";
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
                parcel.enforceInterface(ISemBiometricSysUiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemBiometricSysUiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    ISemBiometricSysUiCallback iSemBiometricSysUiCallbackAsInterface = ISemBiometricSysUiCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    showBiometricDialog(i3, i4, bundle, iSemBiometricSysUiCallbackAsInterface, z, i5, string, j, promptInfo);
                    return true;
                case 2:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(i6, i7, z2, string2);
                    return true;
                case 3:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(i8, i9, i10, i11, string3);
                    return true;
                case 4:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricError(i12, i13, i14, i15, string4);
                    return true;
                case 5:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideBiometricDialog(i16, i17, i18);
                    return true;
                case 6:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCommand(i19, i20, i21, bundle2);
                    return true;
                case 7:
                    int i22 = parcel.readInt();
                    String string5 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setBiometricTheme(i22, string5, bArrCreateByteArray, rawFileDescriptor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemBiometricSysUiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void showBiometricDialog(int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iSemBiometricSysUiCallback);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricAuthenticated(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricHelp(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricError(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void hideBiometricDialog(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void sendCommand(int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
