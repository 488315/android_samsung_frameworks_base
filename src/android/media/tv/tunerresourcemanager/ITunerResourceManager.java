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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITunerResourceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITunerResourceManager)) {
                return (ITunerResourceManager) queryLocalInterface;
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
                    IResourcesReclaimListener asInterface = IResourcesReclaimListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    if (readInt > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt);
                    }
                    int[] iArr = readInt >= 0 ? new int[readInt] : null;
                    parcel.enforceNoDataAvail();
                    registerClientProfile(resourceClientProfile, asInterface, iArr);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterClientProfile(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateClientPriority = updateClientPriority(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateClientPriority);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUnusedFrontend = hasUnusedFrontend(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUnusedFrontend);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isLowestPriority = isLowestPriority(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowestPriority);
                    return true;
                case 6:
                    TunerFrontendInfo[] tunerFrontendInfoArr = (TunerFrontendInfo[]) parcel.createTypedArray(TunerFrontendInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setFrontendInfoList(tunerFrontendInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCasInfo(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    TunerDemuxInfo[] tunerDemuxInfoArr = (TunerDemuxInfo[]) parcel.createTypedArray(TunerDemuxInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDemuxInfoList(tunerDemuxInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long[] createLongArray = parcel.createLongArray();
                    parcel.enforceNoDataAvail();
                    setLnbInfoList(createLongArray);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setResourceOwnershipRetention(readInt11, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    TunerFrontendRequest tunerFrontendRequest = (TunerFrontendRequest) parcel.readTypedObject(TunerFrontendRequest.CREATOR);
                    int readInt12 = parcel.readInt();
                    if (readInt12 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt12);
                    }
                    long[] jArr = readInt12 >= 0 ? new long[readInt12] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestFrontend = requestFrontend(tunerFrontendRequest, jArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestFrontend);
                    parcel2.writeLongArray(jArr);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maxNumberOfFrontends = setMaxNumberOfFrontends(readInt13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxNumberOfFrontends);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxNumberOfFrontends2 = getMaxNumberOfFrontends(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumberOfFrontends2);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    shareFrontend(readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean transferOwner = transferOwner(readInt18, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transferOwner);
                    return true;
                case 16:
                    TunerDemuxRequest tunerDemuxRequest = (TunerDemuxRequest) parcel.readTypedObject(TunerDemuxRequest.CREATOR);
                    int readInt21 = parcel.readInt();
                    if (readInt21 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt21);
                    }
                    long[] jArr2 = readInt21 >= 0 ? new long[readInt21] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestDemux = requestDemux(tunerDemuxRequest, jArr2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestDemux);
                    parcel2.writeLongArray(jArr2);
                    return true;
                case 17:
                    TunerDescramblerRequest tunerDescramblerRequest = (TunerDescramblerRequest) parcel.readTypedObject(TunerDescramblerRequest.CREATOR);
                    int readInt22 = parcel.readInt();
                    if (readInt22 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt22);
                    }
                    long[] jArr3 = readInt22 >= 0 ? new long[readInt22] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestDescrambler = requestDescrambler(tunerDescramblerRequest, jArr3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestDescrambler);
                    parcel2.writeLongArray(jArr3);
                    return true;
                case 18:
                    CasSessionRequest casSessionRequest = (CasSessionRequest) parcel.readTypedObject(CasSessionRequest.CREATOR);
                    int readInt23 = parcel.readInt();
                    if (readInt23 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt23);
                    }
                    long[] jArr4 = readInt23 >= 0 ? new long[readInt23] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestCasSession = requestCasSession(casSessionRequest, jArr4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCasSession);
                    parcel2.writeLongArray(jArr4);
                    return true;
                case 19:
                    TunerCiCamRequest tunerCiCamRequest = (TunerCiCamRequest) parcel.readTypedObject(TunerCiCamRequest.CREATOR);
                    int readInt24 = parcel.readInt();
                    if (readInt24 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt24);
                    }
                    long[] jArr5 = readInt24 >= 0 ? new long[readInt24] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestCiCam = requestCiCam(tunerCiCamRequest, jArr5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCiCam);
                    parcel2.writeLongArray(jArr5);
                    return true;
                case 20:
                    TunerLnbRequest tunerLnbRequest = (TunerLnbRequest) parcel.readTypedObject(TunerLnbRequest.CREATOR);
                    int readInt25 = parcel.readInt();
                    if (readInt25 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt25);
                    }
                    long[] jArr6 = readInt25 >= 0 ? new long[readInt25] : null;
                    parcel.enforceNoDataAvail();
                    boolean requestLnb = requestLnb(tunerLnbRequest, jArr6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestLnb);
                    parcel2.writeLongArray(jArr6);
                    return true;
                case 21:
                    long readLong = parcel.readLong();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFrontend(readLong, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    long readLong2 = parcel.readLong();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDemux(readLong2, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    long readLong3 = parcel.readLong();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDescrambler(readLong3, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    long readLong4 = parcel.readLong();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCasSession(readLong4, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    long readLong5 = parcel.readLong();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCiCam(readLong5, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    long readLong6 = parcel.readLong();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseLnb(readLong6, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    ResourceClientProfile resourceClientProfile2 = (ResourceClientProfile) parcel.readTypedObject(ResourceClientProfile.CREATOR);
                    ResourceClientProfile resourceClientProfile3 = (ResourceClientProfile) parcel.readTypedObject(ResourceClientProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isHigherPriority = isHigherPriority(resourceClientProfile2, resourceClientProfile3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHigherPriority);
                    return true;
                case 28:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    storeResourceMap(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearResourceMap(readInt33);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreResourceMap(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt35 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean acquireLock = acquireLock(readInt35, readLong7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquireLock);
                    return true;
                case 32:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean releaseLock = releaseLock(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseLock);
                    return true;
                case 33:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(readInt37, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 34:
                    int readInt39 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int configPriority = getConfigPriority(readInt39, readBoolean2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(resourceClientProfile, 0);
                    obtain.writeStrongInterface(iResourcesReclaimListener);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void unregisterClientProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean updateClientPriority(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean hasUnusedFrontend(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isLowestPriority(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedArray(tunerFrontendInfoArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void updateCasInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedArray(tunerDemuxInfoArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setLnbInfoList(long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLongArray(jArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setResourceOwnershipRetention(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerFrontendRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean setMaxNumberOfFrontends(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getMaxNumberOfFrontends(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void shareFrontend(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean transferOwner(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerDemuxRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerDescramblerRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCasSession(CasSessionRequest casSessionRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(casSessionRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerCiCamRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestLnb(TunerLnbRequest tunerLnbRequest, long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerLnbRequest, 0);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseFrontend(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDemux(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDescrambler(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCasSession(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCiCam(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseLnb(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(resourceClientProfile, 0);
                    obtain.writeTypedObject(resourceClientProfile2, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void storeResourceMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void clearResourceMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void restoreResourceMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean acquireLock(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean releaseLock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getClientPriority(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getConfigPriority(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
