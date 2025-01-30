package com.samsung.android.biometrics;

import android.hardware.biometrics.PromptInfo;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import java.io.FileDescriptor;

/* loaded from: classes5.dex */
public interface ISemBiometricSysUiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiService";

    void hideBiometricDialog(int i, int i2, int i3) throws RemoteException;

    void onBiometricAuthenticated(int i, int i2, boolean z, String str) throws RemoteException;

    void onBiometricError(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void onBiometricHelp(int i, int i2, int i3, int i4, String str) throws RemoteException;

    void sendCommand(int i, int i2, int i3, Bundle bundle) throws RemoteException;

    void setBiometricTheme(int i, String str, byte[] bArr, FileDescriptor fileDescriptor) throws RemoteException;

    void showBiometricDialog(int i, int i2, Bundle bundle, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, boolean z, int i3, String str, long j, PromptInfo promptInfo) throws RemoteException;

    public static class Default implements ISemBiometricSysUiService {
        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void showBiometricDialog(int sessionId, int type, Bundle bundle, ISemBiometricSysUiCallback receiver, boolean requireConfirmation, int userId, String opPackageName, long operationId, PromptInfo promptInfo) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricAuthenticated(int sessionId, int modality, boolean authenticated, String extraInfo) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricHelp(int sessionId, int modality, int helpCode, int vendorCode, String message) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void onBiometricError(int sessionId, int modality, int errCode, int vendorCode, String message) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void hideBiometricDialog(int sessionId, int reason, int flag) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void sendCommand(int sessionId, int cmd, int code, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
        public void setBiometricTheme(int type, String extra, byte[] data, FileDescriptor fd) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemBiometricSysUiService {
        static final int TRANSACTION_hideBiometricDialog = 5;
        static final int TRANSACTION_onBiometricAuthenticated = 2;
        static final int TRANSACTION_onBiometricError = 4;
        static final int TRANSACTION_onBiometricHelp = 3;
        static final int TRANSACTION_sendCommand = 6;
        static final int TRANSACTION_setBiometricTheme = 7;
        static final int TRANSACTION_showBiometricDialog = 1;

        public Stub() {
            attachInterface(this, ISemBiometricSysUiService.DESCRIPTOR);
        }

        public static ISemBiometricSysUiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemBiometricSysUiService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemBiometricSysUiService)) {
                return (ISemBiometricSysUiService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemBiometricSysUiService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemBiometricSysUiService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            ISemBiometricSysUiCallback _arg3 = ISemBiometricSysUiCallback.Stub.asInterface(data.readStrongBinder());
                            boolean _arg4 = data.readBoolean();
                            int _arg5 = data.readInt();
                            String _arg6 = data.readString();
                            long _arg7 = data.readLong();
                            PromptInfo _arg8 = (PromptInfo) data.readTypedObject(PromptInfo.CREATOR);
                            data.enforceNoDataAvail();
                            showBiometricDialog(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            boolean _arg22 = data.readBoolean();
                            String _arg32 = data.readString();
                            data.enforceNoDataAvail();
                            onBiometricAuthenticated(_arg02, _arg12, _arg22, _arg32);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg13 = data.readInt();
                            int _arg23 = data.readInt();
                            int _arg33 = data.readInt();
                            String _arg42 = data.readString();
                            data.enforceNoDataAvail();
                            onBiometricHelp(_arg03, _arg13, _arg23, _arg33, _arg42);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            int _arg14 = data.readInt();
                            int _arg24 = data.readInt();
                            int _arg34 = data.readInt();
                            String _arg43 = data.readString();
                            data.enforceNoDataAvail();
                            onBiometricError(_arg04, _arg14, _arg24, _arg34, _arg43);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            int _arg15 = data.readInt();
                            int _arg25 = data.readInt();
                            data.enforceNoDataAvail();
                            hideBiometricDialog(_arg05, _arg15, _arg25);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            int _arg16 = data.readInt();
                            int _arg26 = data.readInt();
                            Bundle _arg35 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            sendCommand(_arg06, _arg16, _arg26, _arg35);
                            return true;
                        case 7:
                            int _arg07 = data.readInt();
                            String _arg17 = data.readString();
                            byte[] _arg27 = data.createByteArray();
                            FileDescriptor _arg36 = data.readRawFileDescriptor();
                            data.enforceNoDataAvail();
                            setBiometricTheme(_arg07, _arg17, _arg27, _arg36);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemBiometricSysUiService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void showBiometricDialog(int sessionId, int type, Bundle bundle, ISemBiometricSysUiCallback receiver, boolean requireConfirmation, int userId, String opPackageName, long operationId, PromptInfo promptInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(type);
                    _data.writeTypedObject(bundle, 0);
                    _data.writeStrongInterface(receiver);
                    _data.writeBoolean(requireConfirmation);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeLong(operationId);
                    _data.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricAuthenticated(int sessionId, int modality, boolean authenticated, String extraInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(modality);
                    _data.writeBoolean(authenticated);
                    _data.writeString(extraInfo);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricHelp(int sessionId, int modality, int helpCode, int vendorCode, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(modality);
                    _data.writeInt(helpCode);
                    _data.writeInt(vendorCode);
                    _data.writeString(message);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void onBiometricError(int sessionId, int modality, int errCode, int vendorCode, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(modality);
                    _data.writeInt(errCode);
                    _data.writeInt(vendorCode);
                    _data.writeString(message);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void hideBiometricDialog(int sessionId, int reason, int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(reason);
                    _data.writeInt(flag);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void sendCommand(int sessionId, int cmd, int code, Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(cmd);
                    _data.writeInt(code);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiService
            public void setBiometricTheme(int type, String extra, byte[] data, FileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiService.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(extra);
                    _data.writeByteArray(data);
                    _data.writeRawFileDescriptor(fd);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
