package vendor.samsung.frameworks.codecsolution;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
/* loaded from: classes.dex */
public interface ISehCodecSolution extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$frameworks$codecsolution$ISehCodecSolution".replace('$', '.');
    public static final String HASH = "051b4eeec63a9059be5d446da356f2abf92c3874";
    public static final int VERSION = 2;

    SehDisplaySize getDisplaySize() throws RemoteException;

    int getH2SCAllowlistStatus(String str, String str2) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getSmartFittingAllowlistStatus() throws RemoteException;

    int getSmartFittingMode() throws RemoteException;

    SehVideoRecordingParameter getVideoRecordingParameter(long j) throws RemoteException;

    void hideSmartFittingButton() throws RemoteException;

    boolean isDesktopMode() throws RemoteException;

    void setAutoFitMode(boolean z) throws RemoteException;

    void setBlackbarState(boolean z) throws RemoteException;

    void setSmartFittingMode(int i) throws RemoteException;

    void setSmartFittingPid(int i) throws RemoteException;

    void setVideoRecordingParameter(long j, SehVideoRecordingParameter sehVideoRecordingParameter) throws RemoteException;

    void showSmartFittingButton() throws RemoteException;

    void startSmartFittingService() throws RemoteException;

    void stopSmartFittingService() throws RemoteException;

    void updateMediaStatisticsData(String str) throws RemoteException;

    void updateStreamStatus(int i, boolean z, int i2) throws RemoteException;

    /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
    public static class Default implements ISehCodecSolution {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public SehDisplaySize getDisplaySize() throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public int getH2SCAllowlistStatus(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public String getInterfaceHash() {
            return "";
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public int getSmartFittingAllowlistStatus() throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public int getSmartFittingMode() throws RemoteException {
            return 0;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public SehVideoRecordingParameter getVideoRecordingParameter(long j) throws RemoteException {
            return null;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public boolean isDesktopMode() throws RemoteException {
            return false;
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void updateStreamStatus(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void setVideoRecordingParameter(long j, SehVideoRecordingParameter sehVideoRecordingParameter) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void setAutoFitMode(boolean z) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void setBlackbarState(boolean z) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void setSmartFittingMode(int i) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void setSmartFittingPid(int i) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void updateMediaStatisticsData(String str) throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void hideSmartFittingButton() throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void showSmartFittingButton() throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void startSmartFittingService() throws RemoteException {
        }

        @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
        public void stopSmartFittingService() throws RemoteException {
        }
    }

    /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
    public static abstract class Stub extends Binder implements ISehCodecSolution {
        static final int TRANSACTION_getDisplaySize = 2;
        static final int TRANSACTION_getH2SCAllowlistStatus = 13;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSmartFittingAllowlistStatus = 12;
        static final int TRANSACTION_getSmartFittingMode = 9;
        static final int TRANSACTION_getVideoRecordingParameter = 16;
        static final int TRANSACTION_hideSmartFittingButton = 6;
        static final int TRANSACTION_isDesktopMode = 1;
        static final int TRANSACTION_setAutoFitMode = 10;
        static final int TRANSACTION_setBlackbarState = 7;
        static final int TRANSACTION_setSmartFittingMode = 8;
        static final int TRANSACTION_setSmartFittingPid = 11;
        static final int TRANSACTION_setVideoRecordingParameter = 15;
        static final int TRANSACTION_showSmartFittingButton = 5;
        static final int TRANSACTION_startSmartFittingService = 3;
        static final int TRANSACTION_stopSmartFittingService = 4;
        static final int TRANSACTION_updateMediaStatisticsData = 14;
        static final int TRANSACTION_updateStreamStatus = 17;

        /* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
        private static class Proxy implements ISehCodecSolution {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public SehDisplaySize getDisplaySize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDisplaySize is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehDisplaySize) obtain2.readTypedObject(SehDisplaySize.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public int getH2SCAllowlistStatus(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getH2SCAllowlistStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISehCodecSolution.DESCRIPTOR;
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public synchronized String getInterfaceHash() throws RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain(asBinder());
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                            this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public int getSmartFittingAllowlistStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSmartFittingAllowlistStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public int getSmartFittingMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSmartFittingMode is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public SehVideoRecordingParameter getVideoRecordingParameter(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getVideoRecordingParameter is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehVideoRecordingParameter) obtain2.readTypedObject(SehVideoRecordingParameter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void hideSmartFittingButton() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method hideSmartFittingButton is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public boolean isDesktopMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isDesktopMode is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void setAutoFitMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setAutoFitMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void setBlackbarState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setBlackbarState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void setSmartFittingMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setSmartFittingMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void setSmartFittingPid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setSmartFittingPid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void setVideoRecordingParameter(long j, SehVideoRecordingParameter sehVideoRecordingParameter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(sehVideoRecordingParameter, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setVideoRecordingParameter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void showSmartFittingButton() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method showSmartFittingButton is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void startSmartFittingService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method startSmartFittingService is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void stopSmartFittingService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stopSmartFittingService is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void updateMediaStatisticsData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateMediaStatisticsData is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.frameworks.codecsolution.ISehCodecSolution
            public void updateStreamStatus(int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehCodecSolution.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(Stub.TRANSACTION_updateStreamStatus, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateStreamStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehCodecSolution.DESCRIPTOR);
        }

        public static ISehCodecSolution asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehCodecSolution.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISehCodecSolution)) ? new Proxy(iBinder) : (ISehCodecSolution) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isDesktopMode";
                case 2:
                    return "getDisplaySize";
                case 3:
                    return "startSmartFittingService";
                case 4:
                    return "stopSmartFittingService";
                case 5:
                    return "showSmartFittingButton";
                case 6:
                    return "hideSmartFittingButton";
                case 7:
                    return "setBlackbarState";
                case 8:
                    return "setSmartFittingMode";
                case 9:
                    return "getSmartFittingMode";
                case 10:
                    return "setAutoFitMode";
                case 11:
                    return "setSmartFittingPid";
                case 12:
                    return "getSmartFittingAllowlistStatus";
                case 13:
                    return "getH2SCAllowlistStatus";
                case 14:
                    return "updateMediaStatisticsData";
                case 15:
                    return "setVideoRecordingParameter";
                case 16:
                    return "getVideoRecordingParameter";
                case TRANSACTION_updateStreamStatus /* 17 */:
                    return "updateStreamStatus";
                default:
                    switch (i) {
                        case TRANSACTION_getInterfaceHash /* 16777214 */:
                            return "getInterfaceHash";
                        case TRANSACTION_getInterfaceVersion /* 16777215 */:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = ISehCodecSolution.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == TRANSACTION_getInterfaceVersion) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    boolean isDesktopMode = isDesktopMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDesktopMode);
                    return true;
                case 2:
                    SehDisplaySize displaySize = getDisplaySize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(displaySize, 1);
                    return true;
                case 3:
                    startSmartFittingService();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    stopSmartFittingService();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    showSmartFittingButton();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    hideSmartFittingButton();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlackbarState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSmartFittingMode(readInt);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int smartFittingMode = getSmartFittingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(smartFittingMode);
                    return true;
                case 10:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoFitMode(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSmartFittingPid(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int smartFittingAllowlistStatus = getSmartFittingAllowlistStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(smartFittingAllowlistStatus);
                    return true;
                case 13:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int h2SCAllowlistStatus = getH2SCAllowlistStatus(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(h2SCAllowlistStatus);
                    return true;
                case 14:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateMediaStatisticsData(readString3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong = parcel.readLong();
                    SehVideoRecordingParameter sehVideoRecordingParameter = (SehVideoRecordingParameter) parcel.readTypedObject(SehVideoRecordingParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoRecordingParameter(readLong, sehVideoRecordingParameter);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    SehVideoRecordingParameter videoRecordingParameter = getVideoRecordingParameter(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(videoRecordingParameter, 1);
                    return true;
                case TRANSACTION_updateStreamStatus /* 17 */:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateStreamStatus(readInt3, readBoolean3, readInt4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
