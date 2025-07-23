package com.samsung.android.authnrservice.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemAuthnrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.authnrservice.manager.ISemAuthnrService";

    public static class Default implements ISemAuthnrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean deleteFile(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] getDrkKeyHandle() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public List<String> getFiles(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public List<String> getMatchedFilePaths(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public int getVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] getWrappedObject(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializeDrk() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializePreloadedTa(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializeWithPreloadedTa() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] process(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] processPreloadedTa(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] processWithPreloadedTa(byte[] bArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public String readFile(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean setChallenge(byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminate() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminateDrk() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminatePreloadedTa(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminateWithPreloadedTa() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean writeFile(byte[] bArr, String str) throws RemoteException {
            return false;
        }
    }

    boolean deleteFile(String str) throws RemoteException;

    byte[] getDrkKeyHandle() throws RemoteException;

    List<String> getFiles(String str, String str2) throws RemoteException;

    List<String> getMatchedFilePaths(String str, String str2) throws RemoteException;

    int getVersion() throws RemoteException;

    byte[] getWrappedObject(byte[] bArr) throws RemoteException;

    boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException;

    boolean initializeDrk() throws RemoteException;

    boolean initializePreloadedTa(int i) throws RemoteException;

    boolean initializeWithPreloadedTa() throws RemoteException;

    byte[] process(byte[] bArr) throws RemoteException;

    byte[] processPreloadedTa(int i, byte[] bArr) throws RemoteException;

    byte[] processWithPreloadedTa(byte[] bArr, String str) throws RemoteException;

    String readFile(String str) throws RemoteException;

    boolean setChallenge(byte[] bArr) throws RemoteException;

    boolean terminate() throws RemoteException;

    boolean terminateDrk() throws RemoteException;

    boolean terminatePreloadedTa(int i) throws RemoteException;

    boolean terminateWithPreloadedTa() throws RemoteException;

    boolean writeFile(byte[] bArr, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemAuthnrService {
        static final int TRANSACTION_deleteFile = 11;
        static final int TRANSACTION_getDrkKeyHandle = 9;
        static final int TRANSACTION_getFiles = 12;
        static final int TRANSACTION_getMatchedFilePaths = 17;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_getWrappedObject = 6;
        static final int TRANSACTION_initialize = 2;
        static final int TRANSACTION_initializeDrk = 7;
        static final int TRANSACTION_initializePreloadedTa = 18;
        static final int TRANSACTION_initializeWithPreloadedTa = 13;
        static final int TRANSACTION_process = 4;
        static final int TRANSACTION_processPreloadedTa = 20;
        static final int TRANSACTION_processWithPreloadedTa = 15;
        static final int TRANSACTION_readFile = 16;
        static final int TRANSACTION_setChallenge = 5;
        static final int TRANSACTION_terminate = 3;
        static final int TRANSACTION_terminateDrk = 8;
        static final int TRANSACTION_terminatePreloadedTa = 19;
        static final int TRANSACTION_terminateWithPreloadedTa = 14;
        static final int TRANSACTION_writeFile = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, ISemAuthnrService.DESCRIPTOR);
        }

        public static ISemAuthnrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemAuthnrService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemAuthnrService)) {
                return (ISemAuthnrService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersion";
                case 2:
                    return "initialize";
                case 3:
                    return "terminate";
                case 4:
                    return "process";
                case 5:
                    return "setChallenge";
                case 6:
                    return "getWrappedObject";
                case 7:
                    return "initializeDrk";
                case 8:
                    return "terminateDrk";
                case 9:
                    return "getDrkKeyHandle";
                case 10:
                    return "writeFile";
                case 11:
                    return "deleteFile";
                case 12:
                    return "getFiles";
                case 13:
                    return "initializeWithPreloadedTa";
                case 14:
                    return "terminateWithPreloadedTa";
                case 15:
                    return "processWithPreloadedTa";
                case 16:
                    return "readFile";
                case 17:
                    return "getMatchedFilePaths";
                case 18:
                    return "initializePreloadedTa";
                case 19:
                    return "terminatePreloadedTa";
                case 20:
                    return "processPreloadedTa";
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
                parcel.enforceInterface(ISemAuthnrService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemAuthnrService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(version);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean initialize = initialize(parcelFileDescriptor, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(initialize);
                    return true;
                case 3:
                    boolean terminate = terminate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(terminate);
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] process = process(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(process);
                    return true;
                case 5:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean challenge = setChallenge(createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(challenge);
                    return true;
                case 6:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] wrappedObject = getWrappedObject(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(wrappedObject);
                    return true;
                case 7:
                    boolean initializeDrk = initializeDrk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(initializeDrk);
                    return true;
                case 8:
                    boolean terminateDrk = terminateDrk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(terminateDrk);
                    return true;
                case 9:
                    byte[] drkKeyHandle = getDrkKeyHandle();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(drkKeyHandle);
                    return true;
                case 10:
                    byte[] createByteArray4 = parcel.createByteArray();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean writeFile = writeFile(createByteArray4, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeFile);
                    return true;
                case 11:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteFile = deleteFile(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteFile);
                    return true;
                case 12:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> files = getFiles(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeStringList(files);
                    return true;
                case 13:
                    boolean initializeWithPreloadedTa = initializeWithPreloadedTa();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(initializeWithPreloadedTa);
                    return true;
                case 14:
                    boolean terminateWithPreloadedTa = terminateWithPreloadedTa();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(terminateWithPreloadedTa);
                    return true;
                case 15:
                    byte[] createByteArray5 = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] processWithPreloadedTa = processWithPreloadedTa(createByteArray5, readString5);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(processWithPreloadedTa);
                    return true;
                case 16:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String readFile = readFile(readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(readFile);
                    return true;
                case 17:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> matchedFilePaths = getMatchedFilePaths(readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeStringList(matchedFilePaths);
                    return true;
                case 18:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean initializePreloadedTa = initializePreloadedTa(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(initializePreloadedTa);
                    return true;
                case 19:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean terminatePreloadedTa = terminatePreloadedTa(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(terminatePreloadedTa);
                    return true;
                case 20:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] processPreloadedTa = processPreloadedTa(readInt3, createByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(processPreloadedTa);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemAuthnrService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAuthnrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public int getVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] process(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean setChallenge(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] getWrappedObject(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializeDrk() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminateDrk() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] getDrkKeyHandle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean writeFile(byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean deleteFile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public List<String> getFiles(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializeWithPreloadedTa() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminateWithPreloadedTa() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] processWithPreloadedTa(byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public String readFile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public List<String> getMatchedFilePaths(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializePreloadedTa(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminatePreloadedTa(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] processPreloadedTa(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
