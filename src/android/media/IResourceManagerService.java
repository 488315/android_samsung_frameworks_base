package android.media;

import android.media.IResourceManagerClient;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.media.IResourceManagerObserver;
import com.samsung.android.media.IResourceManagerObserverClient;
import com.samsung.android.media.MediaInfoParcel;

/* loaded from: classes2.dex */
public interface IResourceManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.media.IResourceManagerService";
    public static final String kPolicySupportsMultipleSecureCodecs = "supports-multiple-secure-codecs";
    public static final String kPolicySupportsSecureWithNonSecureCodec = "supports-secure-with-non-secure-codec";

    public static class Default implements IResourceManagerService {
        @Override // android.media.IResourceManagerService
        public void addMediaInfo(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void addResource(ClientInfoParcel clientInfoParcel, IResourceManagerClient iResourceManagerClient, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IResourceManagerService
        public void config(MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient iResourceManagerObserverClient) throws RemoteException {
            return null;
        }

        @Override // android.media.IResourceManagerService
        public void getMediaResourceUsageReport(MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public float getRemainedFrameRateFor(String str, int i, int i2) throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IResourceManagerService
        public float getSupportedFrameRateFor(String str, int i, int i2) throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.IResourceManagerService
        public void markClientForPendingRemoval(ClientInfoParcel clientInfoParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientConfigChanged(ClientConfigParcel clientConfigParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientCreated(ClientInfoParcel clientInfoParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientStarted(ClientConfigParcel clientConfigParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void notifyClientStopped(ClientConfigParcel clientConfigParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void overridePid(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void overrideProcessInfo(IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public boolean reclaimResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
            return false;
        }

        @Override // android.media.IResourceManagerService
        public void reclaimResourcesFromClientsPendingRemoval(int i) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void removeClient(ClientInfoParcel clientInfoParcel) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void removeResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void sendCapacityError(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void setCodecState(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, int i3) throws RemoteException {
        }

        @Override // android.media.IResourceManagerService
        public void updateResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
        }
    }

    void addMediaInfo(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException;

    void addResource(ClientInfoParcel clientInfoParcel, IResourceManagerClient iResourceManagerClient, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException;

    void config(MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws RemoteException;

    IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient iResourceManagerObserverClient) throws RemoteException;

    void getMediaResourceUsageReport(MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException;

    float getRemainedFrameRateFor(String str, int i, int i2) throws RemoteException;

    float getSupportedFrameRateFor(String str, int i, int i2) throws RemoteException;

    void markClientForPendingRemoval(ClientInfoParcel clientInfoParcel) throws RemoteException;

    void notifyClientConfigChanged(ClientConfigParcel clientConfigParcel) throws RemoteException;

    void notifyClientCreated(ClientInfoParcel clientInfoParcel) throws RemoteException;

    void notifyClientStarted(ClientConfigParcel clientConfigParcel) throws RemoteException;

    void notifyClientStopped(ClientConfigParcel clientConfigParcel) throws RemoteException;

    void overridePid(int i, int i2) throws RemoteException;

    void overrideProcessInfo(IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws RemoteException;

    boolean reclaimResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException;

    void reclaimResourcesFromClientsPendingRemoval(int i) throws RemoteException;

    void removeClient(ClientInfoParcel clientInfoParcel) throws RemoteException;

    void removeResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException;

    void sendCapacityError(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException;

    void setCodecState(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, int i3) throws RemoteException;

    void updateResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IResourceManagerService {
        static final int TRANSACTION_addMediaInfo = 17;
        static final int TRANSACTION_addResource = 2;
        static final int TRANSACTION_config = 1;
        static final int TRANSACTION_createResourceObserver = 16;
        static final int TRANSACTION_getMediaResourceUsageReport = 15;
        static final int TRANSACTION_getRemainedFrameRateFor = 21;
        static final int TRANSACTION_getSupportedFrameRateFor = 20;
        static final int TRANSACTION_markClientForPendingRemoval = 9;
        static final int TRANSACTION_notifyClientConfigChanged = 14;
        static final int TRANSACTION_notifyClientCreated = 11;
        static final int TRANSACTION_notifyClientStarted = 12;
        static final int TRANSACTION_notifyClientStopped = 13;
        static final int TRANSACTION_overridePid = 7;
        static final int TRANSACTION_overrideProcessInfo = 8;
        static final int TRANSACTION_reclaimResource = 6;
        static final int TRANSACTION_reclaimResourcesFromClientsPendingRemoval = 10;
        static final int TRANSACTION_removeClient = 5;
        static final int TRANSACTION_removeResource = 4;
        static final int TRANSACTION_sendCapacityError = 19;
        static final int TRANSACTION_setCodecState = 18;
        static final int TRANSACTION_updateResource = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }

        public Stub() {
            attachInterface(this, IResourceManagerService.DESCRIPTOR);
        }

        public static IResourceManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResourceManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourceManagerService)) {
                return (IResourceManagerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "config";
                case 2:
                    return "addResource";
                case 3:
                    return "updateResource";
                case 4:
                    return "removeResource";
                case 5:
                    return "removeClient";
                case 6:
                    return "reclaimResource";
                case 7:
                    return "overridePid";
                case 8:
                    return "overrideProcessInfo";
                case 9:
                    return "markClientForPendingRemoval";
                case 10:
                    return "reclaimResourcesFromClientsPendingRemoval";
                case 11:
                    return "notifyClientCreated";
                case 12:
                    return "notifyClientStarted";
                case 13:
                    return "notifyClientStopped";
                case 14:
                    return "notifyClientConfigChanged";
                case 15:
                    return "getMediaResourceUsageReport";
                case 16:
                    return "createResourceObserver";
                case 17:
                    return "addMediaInfo";
                case 18:
                    return "setCodecState";
                case 19:
                    return "sendCapacityError";
                case 20:
                    return "getSupportedFrameRateFor";
                case 21:
                    return "getRemainedFrameRateFor";
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
                parcel.enforceInterface(IResourceManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourceManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr = (MediaResourcePolicyParcel[]) parcel.createTypedArray(MediaResourcePolicyParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    config(mediaResourcePolicyParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ClientInfoParcel clientInfoParcel = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    IResourceManagerClient asInterface = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaResourceParcel[] mediaResourceParcelArr = (MediaResourceParcel[]) parcel.createTypedArray(MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    addResource(clientInfoParcel, asInterface, mediaResourceParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ClientInfoParcel clientInfoParcel2 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    MediaResourceParcel[] mediaResourceParcelArr2 = (MediaResourceParcel[]) parcel.createTypedArray(MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateResource(clientInfoParcel2, mediaResourceParcelArr2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ClientInfoParcel clientInfoParcel3 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    MediaResourceParcel[] mediaResourceParcelArr3 = (MediaResourceParcel[]) parcel.createTypedArray(MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeResource(clientInfoParcel3, mediaResourceParcelArr3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ClientInfoParcel clientInfoParcel4 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeClient(clientInfoParcel4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ClientInfoParcel clientInfoParcel5 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    MediaResourceParcel[] mediaResourceParcelArr4 = (MediaResourceParcel[]) parcel.createTypedArray(MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean reclaimResource = reclaimResource(clientInfoParcel5, mediaResourceParcelArr4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reclaimResource);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePid(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IResourceManagerClient asInterface2 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideProcessInfo(asInterface2, readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ClientInfoParcel clientInfoParcel6 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    markClientForPendingRemoval(clientInfoParcel6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reclaimResourcesFromClientsPendingRemoval(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ClientInfoParcel clientInfoParcel7 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientCreated(clientInfoParcel7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ClientConfigParcel clientConfigParcel = (ClientConfigParcel) parcel.readTypedObject(ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientStarted(clientConfigParcel);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ClientConfigParcel clientConfigParcel2 = (ClientConfigParcel) parcel.readTypedObject(ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientStopped(clientConfigParcel2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    ClientConfigParcel clientConfigParcel3 = (ClientConfigParcel) parcel.readTypedObject(ClientConfigParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyClientConfigChanged(clientConfigParcel3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt7 = parcel.readInt();
                    if (readInt7 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt7);
                    }
                    MediaResourceParcel[] mediaResourceParcelArr5 = readInt7 < 0 ? null : new MediaResourceParcel[readInt7];
                    parcel.enforceNoDataAvail();
                    getMediaResourceUsageReport(mediaResourceParcelArr5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(mediaResourceParcelArr5, 1);
                    return true;
                case 16:
                    IResourceManagerObserverClient asInterface3 = IResourceManagerObserverClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IResourceManagerObserver createResourceObserver = createResourceObserver(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createResourceObserver);
                    return true;
                case 17:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    long readLong = parcel.readLong();
                    IResourceManagerClient asInterface4 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaInfoParcel[] mediaInfoParcelArr = (MediaInfoParcel[]) parcel.createTypedArray(MediaInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    addMediaInfo(readInt8, readInt9, readLong, asInterface4, mediaInfoParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    IResourceManagerClient asInterface5 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCodecState(readInt10, readInt11, readLong2, asInterface5, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    IResourceManagerClient asInterface6 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaInfoParcel[] mediaInfoParcelArr2 = (MediaInfoParcel[]) parcel.createTypedArray(MediaInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCapacityError(readInt13, readInt14, readLong3, asInterface6, mediaInfoParcelArr2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float supportedFrameRateFor = getSupportedFrameRateFor(readString, readInt15, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeFloat(supportedFrameRateFor);
                    return true;
                case 21:
                    String readString2 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float remainedFrameRateFor = getRemainedFrameRateFor(readString2, readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeFloat(remainedFrameRateFor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IResourceManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerService.DESCRIPTOR;
            }

            @Override // android.media.IResourceManagerService
            public void config(MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedArray(mediaResourcePolicyParcelArr, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void addResource(ClientInfoParcel clientInfoParcel, IResourceManagerClient iResourceManagerClient, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void updateResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeClient(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public boolean reclaimResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    obtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overridePid(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overrideProcessInfo(IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResourceManagerClient);
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

            @Override // android.media.IResourceManagerService
            public void markClientForPendingRemoval(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void reclaimResourcesFromClientsPendingRemoval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientCreated(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStarted(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStopped(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientConfigChanged(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void getMediaResourceUsageReport(MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(mediaResourceParcelArr.length);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readTypedArray(mediaResourceParcelArr, MediaResourceParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient iResourceManagerObserverClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iResourceManagerObserverClient);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return IResourceManagerObserver.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void addMediaInfo(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeTypedArray(mediaInfoParcelArr, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void setCodecState(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void sendCapacityError(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iResourceManagerClient);
                    obtain.writeTypedArray(mediaInfoParcelArr, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public float getSupportedFrameRateFor(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public float getRemainedFrameRateFor(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
