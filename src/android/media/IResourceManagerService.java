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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IResourceManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IResourceManagerService)) {
                return (IResourceManagerService) iInterfaceQueryLocalInterface;
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
                    IResourceManagerClient iResourceManagerClientAsInterface = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaResourceParcel[] mediaResourceParcelArr = (MediaResourceParcel[]) parcel.createTypedArray(MediaResourceParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    addResource(clientInfoParcel, iResourceManagerClientAsInterface, mediaResourceParcelArr);
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
                    boolean zReclaimResource = reclaimResource(clientInfoParcel5, mediaResourceParcelArr4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReclaimResource);
                    return true;
                case 7:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePid(i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IResourceManagerClient iResourceManagerClientAsInterface2 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideProcessInfo(iResourceManagerClientAsInterface2, i5, i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ClientInfoParcel clientInfoParcel6 = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    markClientForPendingRemoval(clientInfoParcel6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reclaimResourcesFromClientsPendingRemoval(i8);
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
                    int i9 = parcel.readInt();
                    if (i9 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i9);
                    }
                    MediaResourceParcel[] mediaResourceParcelArr5 = i9 < 0 ? null : new MediaResourceParcel[i9];
                    parcel.enforceNoDataAvail();
                    getMediaResourceUsageReport(mediaResourceParcelArr5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(mediaResourceParcelArr5, 1);
                    return true;
                case 16:
                    IResourceManagerObserverClient iResourceManagerObserverClientAsInterface = IResourceManagerObserverClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IResourceManagerObserver iResourceManagerObserverCreateResourceObserver = createResourceObserver(iResourceManagerObserverClientAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iResourceManagerObserverCreateResourceObserver);
                    return true;
                case 17:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    long j = parcel.readLong();
                    IResourceManagerClient iResourceManagerClientAsInterface3 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaInfoParcel[] mediaInfoParcelArr = (MediaInfoParcel[]) parcel.createTypedArray(MediaInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    addMediaInfo(i10, i11, j, iResourceManagerClientAsInterface3, mediaInfoParcelArr);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    long j2 = parcel.readLong();
                    IResourceManagerClient iResourceManagerClientAsInterface4 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCodecState(i12, i13, j2, iResourceManagerClientAsInterface4, i14);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    long j3 = parcel.readLong();
                    IResourceManagerClient iResourceManagerClientAsInterface5 = IResourceManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    MediaInfoParcel[] mediaInfoParcelArr2 = (MediaInfoParcel[]) parcel.createTypedArray(MediaInfoParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCapacityError(i15, i16, j3, iResourceManagerClientAsInterface5, mediaInfoParcelArr2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string = parcel.readString();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float supportedFrameRateFor = getSupportedFrameRateFor(string, i17, i18);
                    parcel2.writeNoException();
                    parcel2.writeFloat(supportedFrameRateFor);
                    return true;
                case 21:
                    String string2 = parcel.readString();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float remainedFrameRateFor = getRemainedFrameRateFor(string2, i19, i20);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedArray(mediaResourcePolicyParcelArr, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void addResource(ClientInfoParcel clientInfoParcel, IResourceManagerClient iResourceManagerClient, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    parcelObtain.writeStrongInterface(iResourceManagerClient);
                    parcelObtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void updateResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    parcelObtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    parcelObtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void removeClient(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public boolean reclaimResource(ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    parcelObtain.writeTypedArray(mediaResourceParcelArr, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overridePid(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void overrideProcessInfo(IResourceManagerClient iResourceManagerClient, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResourceManagerClient);
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

            @Override // android.media.IResourceManagerService
            public void markClientForPendingRemoval(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void reclaimResourcesFromClientsPendingRemoval(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientCreated(ClientInfoParcel clientInfoParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientInfoParcel, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStarted(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientStopped(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void notifyClientConfigChanged(ClientConfigParcel clientConfigParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clientConfigParcel, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void getMediaResourceUsageReport(MediaResourceParcel[] mediaResourceParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(mediaResourceParcelArr.length);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readTypedArray(mediaResourceParcelArr, MediaResourceParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient iResourceManagerObserverClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResourceManagerObserverClient);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IResourceManagerObserver.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void addMediaInfo(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iResourceManagerClient);
                    parcelObtain.writeTypedArray(mediaInfoParcelArr, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void setCodecState(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iResourceManagerClient);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public void sendCapacityError(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, MediaInfoParcel[] mediaInfoParcelArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iResourceManagerClient);
                    parcelObtain.writeTypedArray(mediaInfoParcelArr, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public float getSupportedFrameRateFor(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerService
            public float getRemainedFrameRateFor(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
