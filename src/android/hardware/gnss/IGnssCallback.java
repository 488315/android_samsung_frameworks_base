package android.hardware.gnss;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssCallback extends IInterface {
    public static final int CAPABILITY_ANTENNA_INFO = 2048;
    public static final int CAPABILITY_CORRELATION_VECTOR = 4096;
    public static final int CAPABILITY_GEOFENCING = 32;
    public static final int CAPABILITY_LOW_POWER_MODE = 256;
    public static final int CAPABILITY_MEASUREMENTS = 64;
    public static final int CAPABILITY_MEASUREMENT_CORRECTIONS = 1024;
    public static final int CAPABILITY_MEASUREMENT_CORRECTIONS_FOR_DRIVING = 16384;
    public static final int CAPABILITY_MSA = 4;
    public static final int CAPABILITY_MSB = 2;
    public static final int CAPABILITY_NAV_MESSAGES = 128;
    public static final int CAPABILITY_ON_DEMAND_TIME = 16;
    public static final int CAPABILITY_SATELLITE_BLOCKLIST = 512;
    public static final int CAPABILITY_SATELLITE_PVT = 8192;
    public static final int CAPABILITY_SCHEDULING = 1;
    public static final int CAPABILITY_SINGLE_SHOT = 8;
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssCallback".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface GnssStatusValue {
        public static final int ENGINE_OFF = 4;
        public static final int ENGINE_ON = 3;
        public static final int NONE = 0;
        public static final int SESSION_BEGIN = 1;
        public static final int SESSION_END = 2;
    }

    public @interface GnssSvFlags {
        public static final int HAS_ALMANAC_DATA = 2;
        public static final int HAS_CARRIER_FREQUENCY = 8;
        public static final int HAS_EPHEMERIS_DATA = 1;
        public static final int NONE = 0;
        public static final int USED_IN_FIX = 4;
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void gnssAcquireWakelockCb() throws RemoteException;

    void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException;

    void gnssNmeaCb(long j, String str) throws RemoteException;

    void gnssReleaseWakelockCb() throws RemoteException;

    void gnssRequestLocationCb(boolean z, boolean z2) throws RemoteException;

    void gnssRequestTimeCb() throws RemoteException;

    void gnssSetCapabilitiesCb(int i) throws RemoteException;

    void gnssSetSystemInfoCb(GnssSystemInfo gnssSystemInfo) throws RemoteException;

    void gnssStatusCb(int i) throws RemoteException;

    void gnssSvStatusCb(GnssSvInfo[] gnssSvInfoArr) throws RemoteException;

    public static class Default implements IGnssCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssAcquireWakelockCb() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssNmeaCb(long j, String str) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssReleaseWakelockCb() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssRequestLocationCb(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssRequestTimeCb() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSetCapabilitiesCb(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSetSystemInfoCb(GnssSystemInfo gnssSystemInfo) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssStatusCb(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSvStatusCb(GnssSvInfo[] gnssSvInfoArr) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssAcquireWakelockCb = 6;
        static final int TRANSACTION_gnssLocationCb = 4;
        static final int TRANSACTION_gnssNmeaCb = 5;
        static final int TRANSACTION_gnssReleaseWakelockCb = 7;
        static final int TRANSACTION_gnssRequestLocationCb = 10;
        static final int TRANSACTION_gnssRequestTimeCb = 9;
        static final int TRANSACTION_gnssSetCapabilitiesCb = 1;
        static final int TRANSACTION_gnssSetSystemInfoCb = 8;
        static final int TRANSACTION_gnssStatusCb = 2;
        static final int TRANSACTION_gnssSvStatusCb = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IGnssCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGnssCallback)) {
                return (IGnssCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "gnssSetCapabilitiesCb";
                case 2:
                    return "gnssStatusCb";
                case 3:
                    return "gnssSvStatusCb";
                case 4:
                    return "gnssLocationCb";
                case 5:
                    return "gnssNmeaCb";
                case 6:
                    return "gnssAcquireWakelockCb";
                case 7:
                    return "gnssReleaseWakelockCb";
                case 8:
                    return "gnssSetSystemInfoCb";
                case 9:
                    return "gnssRequestTimeCb";
                case 10:
                    return "gnssRequestLocationCb";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssSetCapabilitiesCb(i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssStatusCb(i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    GnssSvInfo[] gnssSvInfoArr = (GnssSvInfo[]) parcel.createTypedArray(GnssSvInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssSvStatusCb(gnssSvInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    GnssLocation gnssLocation = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssLocationCb(gnssLocation);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long j = parcel.readLong();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    gnssNmeaCb(j, string);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    gnssAcquireWakelockCb();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    gnssReleaseWakelockCb();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    GnssSystemInfo gnssSystemInfo = (GnssSystemInfo) parcel.readTypedObject(GnssSystemInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssSetSystemInfoCb(gnssSystemInfo);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    gnssRequestTimeCb();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    gnssRequestLocationCb(z, z2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGnssCallback {
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

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSetCapabilitiesCb(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssSetCapabilitiesCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssStatusCb(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssStatusCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSvStatusCb(GnssSvInfo[] gnssSvInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(gnssSvInfoArr, 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssSvStatusCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssLocationCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssNmeaCb(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssNmeaCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssAcquireWakelockCb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssAcquireWakelockCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssReleaseWakelockCb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssReleaseWakelockCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSetSystemInfoCb(GnssSystemInfo gnssSystemInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(gnssSystemInfo, 0);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssSetSystemInfoCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssRequestTimeCb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssRequestTimeCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssRequestLocationCb(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssRequestLocationCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnssCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }

    public static class GnssSvInfo implements Parcelable {
        public static final Parcelable.Creator<GnssSvInfo> CREATOR = new Parcelable.Creator<GnssSvInfo>() { // from class: android.hardware.gnss.IGnssCallback.GnssSvInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssSvInfo createFromParcel(Parcel parcel) {
                GnssSvInfo gnssSvInfo = new GnssSvInfo();
                gnssSvInfo.readFromParcel(parcel);
                return gnssSvInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssSvInfo[] newArray(int i) {
                return new GnssSvInfo[i];
            }
        };
        public int constellation;
        public int svid = 0;
        public float cN0Dbhz = 0.0f;
        public float basebandCN0DbHz = 0.0f;
        public float elevationDegrees = 0.0f;
        public float azimuthDegrees = 0.0f;
        public long carrierFrequencyHz = 0;
        public int svFlag = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.svid);
            parcel.writeInt(this.constellation);
            parcel.writeFloat(this.cN0Dbhz);
            parcel.writeFloat(this.basebandCN0DbHz);
            parcel.writeFloat(this.elevationDegrees);
            parcel.writeFloat(this.azimuthDegrees);
            parcel.writeLong(this.carrierFrequencyHz);
            parcel.writeInt(this.svFlag);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.svid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.constellation = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cN0Dbhz = parcel.readFloat();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.basebandCN0DbHz = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.elevationDegrees = parcel.readFloat();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.azimuthDegrees = parcel.readFloat();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.carrierFrequencyHz = parcel.readLong();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.svFlag = parcel.readInt();
                                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }

    public static class GnssSystemInfo implements Parcelable {
        public static final Parcelable.Creator<GnssSystemInfo> CREATOR = new Parcelable.Creator<GnssSystemInfo>() { // from class: android.hardware.gnss.IGnssCallback.GnssSystemInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssSystemInfo createFromParcel(Parcel parcel) {
                GnssSystemInfo gnssSystemInfo = new GnssSystemInfo();
                gnssSystemInfo.readFromParcel(parcel);
                return gnssSystemInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssSystemInfo[] newArray(int i) {
                return new GnssSystemInfo[i];
            }
        };
        public String name;
        public int yearOfHw = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.yearOfHw);
            parcel.writeString(this.name);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.yearOfHw = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.name = parcel.readString();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
