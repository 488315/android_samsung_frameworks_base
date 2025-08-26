package android.media.tv.tunerresourcemanager;

import android.media.tv.tunerresourcemanager.IResourcesReclaimListener;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITunerResourceManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.tunerresourcemanager.ITunerResourceManager";

    public static class Default implements ITunerResourceManager {
        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean acquireLock(int i, long j) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void clearResourceMap(int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getClientPriority(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getConfigPriority(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getMaxNumberOfFrontends(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean hasUnusedFrontend(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean isLowestPriority(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void registerClientProfile(ResourceClientProfile resourceClientProfile, IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseCasSession(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseCiCam(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseDemux(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseDescrambler(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseFrontend(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseLnb(long j, int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean releaseLock(int i) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestCasSession(CasSessionRequest casSessionRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestLnb(TunerLnbRequest tunerLnbRequest, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void restoreResourceMap(int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setLnbInfoList(long[] jArr) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean setMaxNumberOfFrontends(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setResourceOwnershipRetention(int i, boolean z) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void shareFrontend(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void storeResourceMap(int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean transferOwner(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void unregisterClientProfile(int i) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void updateCasInfo(int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean updateClientPriority(int i, int i2, int i3) throws RemoteException {
            return false;
        }
    }

    boolean acquireLock(int i, long j) throws RemoteException;

    void clearResourceMap(int i) throws RemoteException;

    int getClientPriority(int i, int i2) throws RemoteException;

    int getConfigPriority(int i, boolean z) throws RemoteException;

    int getMaxNumberOfFrontends(int i) throws RemoteException;

    boolean hasUnusedFrontend(int i) throws RemoteException;

    boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) throws RemoteException;

    boolean isLowestPriority(int i, int i2) throws RemoteException;

    void registerClientProfile(ResourceClientProfile resourceClientProfile, IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws RemoteException;

    void releaseCasSession(long j, int i) throws RemoteException;

    void releaseCiCam(long j, int i) throws RemoteException;

    void releaseDemux(long j, int i) throws RemoteException;

    void releaseDescrambler(long j, int i) throws RemoteException;

    void releaseFrontend(long j, int i) throws RemoteException;

    void releaseLnb(long j, int i) throws RemoteException;

    boolean releaseLock(int i) throws RemoteException;

    boolean requestCasSession(CasSessionRequest casSessionRequest, long[] jArr) throws RemoteException;

    boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, long[] jArr) throws RemoteException;

    boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, long[] jArr) throws RemoteException;

    boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, long[] jArr) throws RemoteException;

    boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, long[] jArr) throws RemoteException;

    boolean requestLnb(TunerLnbRequest tunerLnbRequest, long[] jArr) throws RemoteException;

    void restoreResourceMap(int i) throws RemoteException;

    void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) throws RemoteException;

    void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) throws RemoteException;

    void setLnbInfoList(long[] jArr) throws RemoteException;

    boolean setMaxNumberOfFrontends(int i, int i2) throws RemoteException;

    void setResourceOwnershipRetention(int i, boolean z) throws RemoteException;

    void shareFrontend(int i, int i2) throws RemoteException;

    void storeResourceMap(int i) throws RemoteException;

    boolean transferOwner(int i, int i2, int i3) throws RemoteException;

    void unregisterClientProfile(int i) throws RemoteException;

    void updateCasInfo(int i, int i2) throws RemoteException;

    boolean updateClientPriority(int i, int i2, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements ITunerResourceManager {
        static final int TRANSACTION_acquireLock = 31;
        static final int TRANSACTION_clearResourceMap = 29;
        static final int TRANSACTION_getClientPriority = 33;
        static final int TRANSACTION_getConfigPriority = 34;
        static final int TRANSACTION_getMaxNumberOfFrontends = 13;
        static final int TRANSACTION_hasUnusedFrontend = 4;
        static final int TRANSACTION_isHigherPriority = 27;
        static final int TRANSACTION_isLowestPriority = 5;
        static final int TRANSACTION_registerClientProfile = 1;
        static final int TRANSACTION_releaseCasSession = 24;
        static final int TRANSACTION_releaseCiCam = 25;
        static final int TRANSACTION_releaseDemux = 22;
        static final int TRANSACTION_releaseDescrambler = 23;
        static final int TRANSACTION_releaseFrontend = 21;
        static final int TRANSACTION_releaseLnb = 26;
        static final int TRANSACTION_releaseLock = 32;
        static final int TRANSACTION_requestCasSession = 18;
        static final int TRANSACTION_requestCiCam = 19;
        static final int TRANSACTION_requestDemux = 16;
        static final int TRANSACTION_requestDescrambler = 17;
        static final int TRANSACTION_requestFrontend = 11;
        static final int TRANSACTION_requestLnb = 20;
        static final int TRANSACTION_restoreResourceMap = 30;
        static final int TRANSACTION_setDemuxInfoList = 8;
        static final int TRANSACTION_setFrontendInfoList = 6;
        static final int TRANSACTION_setLnbInfoList = 9;
        static final int TRANSACTION_setMaxNumberOfFrontends = 12;
        static final int TRANSACTION_setResourceOwnershipRetention = 10;
        static final int TRANSACTION_shareFrontend = 14;
        static final int TRANSACTION_storeResourceMap = 28;
        static final int TRANSACTION_transferOwner = 15;
        static final int TRANSACTION_unregisterClientProfile = 2;
        static final int TRANSACTION_updateCasInfo = 7;
        static final int TRANSACTION_updateClientPriority = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ITunerResourceManager.DESCRIPTOR);
        }

        public static ITunerResourceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITunerResourceManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITunerResourceManager)) {
                return (ITunerResourceManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITunerResourceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITunerResourceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ResourceClientProfile resourceClientProfile = (ResourceClientProfile) parcel.readTypedObject(ResourceClientProfile.CREATOR);
                    IResourcesReclaimListener iResourcesReclaimListenerAsInterface = IResourcesReclaimListener.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    if (i3 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i3);
                    }
                    int[] iArr = i3 >= 0 ? new int[i3] : null;
                    parcel.enforceNoDataAvail();
                    registerClientProfile(resourceClientProfile, iResourcesReclaimListenerAsInterface, iArr);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterClientProfile(i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateClientPriority = updateClientPriority(i5, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateClientPriority);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasUnusedFrontend = hasUnusedFrontend(i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUnusedFrontend);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsLowestPriority = isLowestPriority(i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLowestPriority);
                    return true;
                case 6:
                    TunerFrontendInfo[] tunerFrontendInfoArr = (TunerFrontendInfo[]) parcel.createTypedArray(TunerFrontendInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setFrontendInfoList(tunerFrontendInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCasInfo(i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    TunerDemuxInfo[] tunerDemuxInfoArr = (TunerDemuxInfo[]) parcel.createTypedArray(TunerDemuxInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDemuxInfoList(tunerDemuxInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    parcel.enforceNoDataAvail();
                    setLnbInfoList(jArrCreateLongArray);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setResourceOwnershipRetention(i13, z);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    TunerFrontendRequest tunerFrontendRequest = (TunerFrontendRequest) parcel.readTypedObject(TunerFrontendRequest.CREATOR);
                    int i14 = parcel.readInt();
                    if (i14 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i14);
                    }
                    long[] jArr = i14 >= 0 ? new long[i14] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestFrontend = requestFrontend(tunerFrontendRequest, jArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestFrontend);
                    parcel2.writeLongArray(jArr);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maxNumberOfFrontends = setMaxNumberOfFrontends(i15, i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxNumberOfFrontends);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxNumberOfFrontends2 = getMaxNumberOfFrontends(i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumberOfFrontends2);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    shareFrontend(i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zTransferOwner = transferOwner(i20, i21, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTransferOwner);
                    return true;
                case 16:
                    TunerDemuxRequest tunerDemuxRequest = (TunerDemuxRequest) parcel.readTypedObject(TunerDemuxRequest.CREATOR);
                    int i23 = parcel.readInt();
                    if (i23 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i23);
                    }
                    long[] jArr2 = i23 >= 0 ? new long[i23] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestDemux = requestDemux(tunerDemuxRequest, jArr2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestDemux);
                    parcel2.writeLongArray(jArr2);
                    return true;
                case 17:
                    TunerDescramblerRequest tunerDescramblerRequest = (TunerDescramblerRequest) parcel.readTypedObject(TunerDescramblerRequest.CREATOR);
                    int i24 = parcel.readInt();
                    if (i24 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i24);
                    }
                    long[] jArr3 = i24 >= 0 ? new long[i24] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestDescrambler = requestDescrambler(tunerDescramblerRequest, jArr3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestDescrambler);
                    parcel2.writeLongArray(jArr3);
                    return true;
                case 18:
                    CasSessionRequest casSessionRequest = (CasSessionRequest) parcel.readTypedObject(CasSessionRequest.CREATOR);
                    int i25 = parcel.readInt();
                    if (i25 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i25);
                    }
                    long[] jArr4 = i25 >= 0 ? new long[i25] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestCasSession = requestCasSession(casSessionRequest, jArr4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestCasSession);
                    parcel2.writeLongArray(jArr4);
                    return true;
                case 19:
                    TunerCiCamRequest tunerCiCamRequest = (TunerCiCamRequest) parcel.readTypedObject(TunerCiCamRequest.CREATOR);
                    int i26 = parcel.readInt();
                    if (i26 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i26);
                    }
                    long[] jArr5 = i26 >= 0 ? new long[i26] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestCiCam = requestCiCam(tunerCiCamRequest, jArr5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestCiCam);
                    parcel2.writeLongArray(jArr5);
                    return true;
                case 20:
                    TunerLnbRequest tunerLnbRequest = (TunerLnbRequest) parcel.readTypedObject(TunerLnbRequest.CREATOR);
                    int i27 = parcel.readInt();
                    if (i27 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i27);
                    }
                    long[] jArr6 = i27 >= 0 ? new long[i27] : null;
                    parcel.enforceNoDataAvail();
                    boolean zRequestLnb = requestLnb(tunerLnbRequest, jArr6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestLnb);
                    parcel2.writeLongArray(jArr6);
                    return true;
                case 21:
                    long j = parcel.readLong();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFrontend(j, i28);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    long j2 = parcel.readLong();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDemux(j2, i29);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    long j3 = parcel.readLong();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDescrambler(j3, i30);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    long j4 = parcel.readLong();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCasSession(j4, i31);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    long j5 = parcel.readLong();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCiCam(j5, i32);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    long j6 = parcel.readLong();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseLnb(j6, i33);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    ResourceClientProfile resourceClientProfile2 = (ResourceClientProfile) parcel.readTypedObject(ResourceClientProfile.CREATOR);
                    ResourceClientProfile resourceClientProfile3 = (ResourceClientProfile) parcel.readTypedObject(ResourceClientProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsHigherPriority = isHigherPriority(resourceClientProfile2, resourceClientProfile3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHigherPriority);
                    return true;
                case 28:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    storeResourceMap(i34);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearResourceMap(i35);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreResourceMap(i36);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i37 = parcel.readInt();
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zAcquireLock = acquireLock(i37, j7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcquireLock);
                    return true;
                case 32:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zReleaseLock = releaseLock(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReleaseLock);
                    return true;
                case 33:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(i39, i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 34:
                    int i41 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int configPriority = getConfigPriority(i41, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(configPriority);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITunerResourceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITunerResourceManager.DESCRIPTOR;
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void registerClientProfile(ResourceClientProfile resourceClientProfile, IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resourceClientProfile, 0);
                    parcelObtain.writeStrongInterface(iResourcesReclaimListener);
                    parcelObtain.writeInt(iArr.length);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readIntArray(iArr);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void unregisterClientProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean updateClientPriority(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean hasUnusedFrontend(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isLowestPriority(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedArray(tunerFrontendInfoArr, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void updateCasInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedArray(tunerDemuxInfoArr, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setLnbInfoList(long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLongArray(jArr);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setResourceOwnershipRetention(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tunerFrontendRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean setMaxNumberOfFrontends(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getMaxNumberOfFrontends(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void shareFrontend(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean transferOwner(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tunerDemuxRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tunerDescramblerRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCasSession(CasSessionRequest casSessionRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(casSessionRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tunerCiCamRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestLnb(TunerLnbRequest tunerLnbRequest, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tunerLnbRequest, 0);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseFrontend(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDemux(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDescrambler(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCasSession(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCiCam(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseLnb(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resourceClientProfile, 0);
                    parcelObtain.writeTypedObject(resourceClientProfile2, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void storeResourceMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void clearResourceMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void restoreResourceMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean acquireLock(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean releaseLock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getClientPriority(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getConfigPriority(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
