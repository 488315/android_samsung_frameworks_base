package android.spay;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITAController extends IInterface {
    public static final String DESCRIPTOR = "android.spay.ITAController";

    public static class Default implements ITAController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.spay.ITAController
        public CertInfo checkCertInfo(List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.spay.ITAController
        public boolean clearDeviceCertificates(String str) throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public boolean makeSystemCall(int i) throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException {
            return null;
        }

        @Override // android.spay.ITAController
        public void unloadTA() throws RemoteException {
        }
    }

    CertInfo checkCertInfo(List<String> list) throws RemoteException;

    boolean clearDeviceCertificates(String str) throws RemoteException;

    boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException;

    boolean makeSystemCall(int i) throws RemoteException;

    TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException;

    void unloadTA() throws RemoteException;

    public static abstract class Stub extends Binder implements ITAController {
        static final int TRANSACTION_checkCertInfo = 6;
        static final int TRANSACTION_clearDeviceCertificates = 5;
        static final int TRANSACTION_loadTA = 1;
        static final int TRANSACTION_makeSystemCall = 4;
        static final int TRANSACTION_processTACommand = 3;
        static final int TRANSACTION_unloadTA = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ITAController.DESCRIPTOR);
        }

        public static ITAController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITAController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITAController)) {
                return (ITAController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "loadTA";
                case 2:
                    return "unloadTA";
                case 3:
                    return "processTACommand";
                case 4:
                    return "makeSystemCall";
                case 5:
                    return "clearDeviceCertificates";
                case 6:
                    return "checkCertInfo";
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
                parcel.enforceInterface(ITAController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITAController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean loadTA = loadTA(parcelFileDescriptor, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(loadTA);
                    return true;
                case 2:
                    unloadTA();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    TACommandRequest tACommandRequest = (TACommandRequest) parcel.readTypedObject(TACommandRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    TACommandResponse processTACommand = processTACommand(tACommandRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(processTACommand, 1);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean makeSystemCall = makeSystemCall(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(makeSystemCall);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearDeviceCertificates = clearDeviceCertificates(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDeviceCertificates);
                    return true;
                case 6:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    CertInfo checkCertInfo = checkCertInfo(createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(checkCertInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITAController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITAController.DESCRIPTOR;
            }

            @Override // android.spay.ITAController
            public boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public void unloadTA() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    obtain.writeTypedObject(tACommandRequest, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TACommandResponse) obtain2.readTypedObject(TACommandResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean makeSystemCall(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean clearDeviceCertificates(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public CertInfo checkCertInfo(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CertInfo) obtain2.readTypedObject(CertInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
