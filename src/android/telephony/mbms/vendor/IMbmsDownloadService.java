package android.telephony.mbms.vendor;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsDownloadService extends IInterface {

    public static class Default implements IMbmsDownloadService {
        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addServiceAnnouncement(int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public void dispose(int i) throws RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int download(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int initialize(int i, IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public List<DownloadRequest> listPendingDownloads(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int requestUpdateFileServices(int i, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsDownloadService
        public int setTempFileRootDirectory(int i, String str) throws RemoteException {
            return 0;
        }
    }

    int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException;

    int addServiceAnnouncement(int i, byte[] bArr) throws RemoteException;

    int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException;

    int cancelDownload(DownloadRequest downloadRequest) throws RemoteException;

    void dispose(int i) throws RemoteException;

    int download(DownloadRequest downloadRequest) throws RemoteException;

    int initialize(int i, IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException;

    List<DownloadRequest> listPendingDownloads(int i) throws RemoteException;

    int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException;

    int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException;

    int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException;

    int requestUpdateFileServices(int i, List<String> list) throws RemoteException;

    int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException;

    int setTempFileRootDirectory(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMbmsDownloadService {
        public static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsDownloadService";
        static final int TRANSACTION_addProgressListener = 8;
        static final int TRANSACTION_addServiceAnnouncement = 4;
        static final int TRANSACTION_addStatusListener = 6;
        static final int TRANSACTION_cancelDownload = 11;
        static final int TRANSACTION_dispose = 14;
        static final int TRANSACTION_download = 5;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_listPendingDownloads = 10;
        static final int TRANSACTION_removeProgressListener = 9;
        static final int TRANSACTION_removeStatusListener = 7;
        static final int TRANSACTION_requestDownloadState = 12;
        static final int TRANSACTION_requestUpdateFileServices = 2;
        static final int TRANSACTION_resetDownloadKnowledge = 13;
        static final int TRANSACTION_setTempFileRootDirectory = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsDownloadService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMbmsDownloadService)) {
                return (IMbmsDownloadService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initialize";
                case 2:
                    return "requestUpdateFileServices";
                case 3:
                    return "setTempFileRootDirectory";
                case 4:
                    return "addServiceAnnouncement";
                case 5:
                    return Context.DOWNLOAD_SERVICE;
                case 6:
                    return "addStatusListener";
                case 7:
                    return "removeStatusListener";
                case 8:
                    return "addProgressListener";
                case 9:
                    return "removeProgressListener";
                case 10:
                    return "listPendingDownloads";
                case 11:
                    return "cancelDownload";
                case 12:
                    return "requestDownloadState";
                case 13:
                    return "resetDownloadKnowledge";
                case 14:
                    return "dispose";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    IMbmsDownloadSessionCallback iMbmsDownloadSessionCallbackAsInterface = IMbmsDownloadSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iInitialize = initialize(i3, iMbmsDownloadSessionCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitialize);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int iRequestUpdateFileServices = requestUpdateFileServices(i4, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestUpdateFileServices);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int tempFileRootDirectory = setTempFileRootDirectory(i5, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(tempFileRootDirectory);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iAddServiceAnnouncement = addServiceAnnouncement(i6, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddServiceAnnouncement);
                    return true;
                case 5:
                    DownloadRequest downloadRequest = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iDownload = download(downloadRequest);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDownload);
                    return true;
                case 6:
                    DownloadRequest downloadRequest2 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    IDownloadStatusListener iDownloadStatusListenerAsInterface = IDownloadStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iAddStatusListener = addStatusListener(downloadRequest2, iDownloadStatusListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddStatusListener);
                    return true;
                case 7:
                    DownloadRequest downloadRequest3 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    IDownloadStatusListener iDownloadStatusListenerAsInterface2 = IDownloadStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRemoveStatusListener = removeStatusListener(downloadRequest3, iDownloadStatusListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveStatusListener);
                    return true;
                case 8:
                    DownloadRequest downloadRequest4 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    IDownloadProgressListener iDownloadProgressListenerAsInterface = IDownloadProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iAddProgressListener = addProgressListener(downloadRequest4, iDownloadProgressListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddProgressListener);
                    return true;
                case 9:
                    DownloadRequest downloadRequest5 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    IDownloadProgressListener iDownloadProgressListenerAsInterface2 = IDownloadProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRemoveProgressListener = removeProgressListener(downloadRequest5, iDownloadProgressListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveProgressListener);
                    return true;
                case 10:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DownloadRequest> listListPendingDownloads = listPendingDownloads(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listListPendingDownloads, 1);
                    return true;
                case 11:
                    DownloadRequest downloadRequest6 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCancelDownload = cancelDownload(downloadRequest6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelDownload);
                    return true;
                case 12:
                    DownloadRequest downloadRequest7 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    FileInfo fileInfo = (FileInfo) parcel.readTypedObject(FileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRequestDownloadState = requestDownloadState(downloadRequest7, fileInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestDownloadState);
                    return true;
                case 13:
                    DownloadRequest downloadRequest8 = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iResetDownloadKnowledge = resetDownloadKnowledge(downloadRequest8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetDownloadKnowledge);
                    return true;
                case 14:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispose(i8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMbmsDownloadService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int initialize(int i, IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iMbmsDownloadSessionCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestUpdateFileServices(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int setTempFileRootDirectory(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addServiceAnnouncement(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int download(DownloadRequest downloadRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    parcelObtain.writeStrongInterface(iDownloadStatusListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    parcelObtain.writeStrongInterface(iDownloadStatusListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int addProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    parcelObtain.writeStrongInterface(iDownloadProgressListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    parcelObtain.writeStrongInterface(iDownloadProgressListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public List<DownloadRequest> listPendingDownloads(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DownloadRequest.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    parcelObtain.writeTypedObject(fileInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(downloadRequest, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsDownloadService
            public void dispose(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
