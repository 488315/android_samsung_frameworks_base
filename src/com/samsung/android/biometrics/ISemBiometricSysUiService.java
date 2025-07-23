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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemBiometricSysUiService)) {
                return (ISemBiometricSysUiService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    ISemBiometricSysUiCallback asInterface = ISemBiometricSysUiCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    showBiometricDialog(readInt, readInt2, bundle, asInterface, readBoolean, readInt3, readString, readLong, promptInfo);
                    return true;
                case 2:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(readInt4, readInt5, readBoolean2, readString2);
                    return true;
                case 3:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(readInt6, readInt7, readInt8, readInt9, readString3);
                    return true;
                case 4:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricError(readInt10, readInt11, readInt12, readInt13, readString4);
                    return true;
                case 5:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideBiometricDialog(readInt14, readInt15, readInt16);
                    return true;
                case 6:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCommand(readInt17, readInt18, readInt19, bundle2);
                    return true;
                case 7:
                    int readInt20 = parcel.readInt();
                    String readString5 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    setBiometricTheme(readInt20, readString5, createByteArray, readRawFileDescriptor);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iSemBiometricSysUiCallback);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricAuthenticated(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricHelp(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricError(int i, int i2, int i3, int i4, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void hideBiometricDialog(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void sendCommand(int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
