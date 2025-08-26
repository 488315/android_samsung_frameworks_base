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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITAController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITAController)) {
                return (ITAController) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zLoadTA = loadTA(parcelFileDescriptor, j, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zLoadTA);
                    return true;
                case 2:
                    unloadTA();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    TACommandRequest tACommandRequest = (TACommandRequest) parcel.readTypedObject(TACommandRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    TACommandResponse tACommandResponseProcessTACommand = processTACommand(tACommandRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tACommandResponseProcessTACommand, 1);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMakeSystemCall = makeSystemCall(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMakeSystemCall);
                    return true;
                case 5:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearDeviceCertificates = clearDeviceCertificates(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDeviceCertificates);
                    return true;
                case 6:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    CertInfo certInfoCheckCertInfo = checkCertInfo(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certInfoCheckCertInfo, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public void unloadTA() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tACommandRequest, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TACommandResponse) parcelObtain2.readTypedObject(TACommandResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean makeSystemCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean clearDeviceCertificates(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.spay.ITAController
            public CertInfo checkCertInfo(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITAController.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CertInfo) parcelObtain2.readTypedObject(CertInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
