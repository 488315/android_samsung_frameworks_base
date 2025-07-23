package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public interface IGnssDebug extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssDebug".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface SatelliteEphemerisHealth {
        public static final int BAD = 1;
        public static final int GOOD = 0;
        public static final int UNKNOWN = 2;
    }

    public @interface SatelliteEphemerisType {
        public static final int ALMANAC_ONLY = 1;
        public static final int EPHEMERIS = 0;
        public static final int NOT_AVAILABLE = 2;
    }

    DebugData getDebugData() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    public static class Default implements IGnssDebug {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssDebug
        public DebugData getDebugData() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnssDebug
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssDebug
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssDebug {
        static final int TRANSACTION_getDebugData = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;

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

        public static IGnssDebug asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssDebug)) {
                return (IGnssDebug) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getDebugData";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            if (i == 1) {
                DebugData debugData = getDebugData();
                parcel2.writeNoException();
                parcel2.writeTypedObject(debugData, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGnssDebug {
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

            @Override // android.hardware.gnss.IGnssDebug
            public DebugData getDebugData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDebugData is unimplemented.");
                    }
                    obtain2.readException();
                    return (DebugData) obtain2.readTypedObject(DebugData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssDebug
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnssDebug
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
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
                return this.mCachedHash;
            }
        }
    }

    public static class TimeDebug implements Parcelable {
        public static final Parcelable.Creator<TimeDebug> CREATOR = new Parcelable.Creator<TimeDebug>() { // from class: android.hardware.gnss.IGnssDebug.TimeDebug.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TimeDebug createFromParcel(Parcel parcel) {
                TimeDebug timeDebug = new TimeDebug();
                timeDebug.readFromParcel(parcel);
                return timeDebug;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TimeDebug[] newArray(int i) {
                return new TimeDebug[i];
            }
        };
        public long timeEstimateMs = 0;
        public float timeUncertaintyNs = 0.0f;
        public float frequencyUncertaintyNsPerSec = 0.0f;

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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.timeEstimateMs);
            parcel.writeFloat(this.timeUncertaintyNs);
            parcel.writeFloat(this.frequencyUncertaintyNsPerSec);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.timeEstimateMs = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.timeUncertaintyNs = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.frequencyUncertaintyNsPerSec = parcel.readFloat();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class PositionDebug implements Parcelable {
        public static final Parcelable.Creator<PositionDebug> CREATOR = new Parcelable.Creator<PositionDebug>() { // from class: android.hardware.gnss.IGnssDebug.PositionDebug.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PositionDebug createFromParcel(Parcel parcel) {
                PositionDebug positionDebug = new PositionDebug();
                positionDebug.readFromParcel(parcel);
                return positionDebug;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PositionDebug[] newArray(int i) {
                return new PositionDebug[i];
            }
        };
        public boolean valid = false;
        public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public float altitudeMeters = 0.0f;
        public float speedMetersPerSec = 0.0f;
        public float bearingDegrees = 0.0f;
        public double horizontalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double verticalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double speedAccuracyMetersPerSecond = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double bearingAccuracyDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public float ageSeconds = 0.0f;

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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeBoolean(this.valid);
            parcel.writeDouble(this.latitudeDegrees);
            parcel.writeDouble(this.longitudeDegrees);
            parcel.writeFloat(this.altitudeMeters);
            parcel.writeFloat(this.speedMetersPerSec);
            parcel.writeFloat(this.bearingDegrees);
            parcel.writeDouble(this.horizontalAccuracyMeters);
            parcel.writeDouble(this.verticalAccuracyMeters);
            parcel.writeDouble(this.speedAccuracyMetersPerSecond);
            parcel.writeDouble(this.bearingAccuracyDegrees);
            parcel.writeFloat(this.ageSeconds);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.valid = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.latitudeDegrees = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.longitudeDegrees = parcel.readDouble();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.altitudeMeters = parcel.readFloat();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.speedMetersPerSec = parcel.readFloat();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.bearingDegrees = parcel.readFloat();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.horizontalAccuracyMeters = parcel.readDouble();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.verticalAccuracyMeters = parcel.readDouble();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.speedAccuracyMetersPerSecond = parcel.readDouble();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.bearingAccuracyDegrees = parcel.readDouble();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.ageSeconds = parcel.readFloat();
                                                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                                            }
                                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class SatelliteData implements Parcelable {
        public static final Parcelable.Creator<SatelliteData> CREATOR = new Parcelable.Creator<SatelliteData>() { // from class: android.hardware.gnss.IGnssDebug.SatelliteData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SatelliteData createFromParcel(Parcel parcel) {
                SatelliteData satelliteData = new SatelliteData();
                satelliteData.readFromParcel(parcel);
                return satelliteData;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SatelliteData[] newArray(int i) {
                return new SatelliteData[i];
            }
        };
        public int constellation;
        public int ephemerisHealth;
        public int ephemerisSource;
        public int ephemerisType;
        public int svid = 0;
        public float ephemerisAgeSeconds = 0.0f;
        public boolean serverPredictionIsAvailable = false;
        public float serverPredictionAgeSeconds = 0.0f;

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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.svid);
            parcel.writeInt(this.constellation);
            parcel.writeInt(this.ephemerisType);
            parcel.writeInt(this.ephemerisSource);
            parcel.writeInt(this.ephemerisHealth);
            parcel.writeFloat(this.ephemerisAgeSeconds);
            parcel.writeBoolean(this.serverPredictionIsAvailable);
            parcel.writeFloat(this.serverPredictionAgeSeconds);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.svid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.constellation = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.ephemerisType = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.ephemerisSource = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ephemerisHealth = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.ephemerisAgeSeconds = parcel.readFloat();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.serverPredictionIsAvailable = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.serverPredictionAgeSeconds = parcel.readFloat();
                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class DebugData implements Parcelable {
        public static final Parcelable.Creator<DebugData> CREATOR = new Parcelable.Creator<DebugData>() { // from class: android.hardware.gnss.IGnssDebug.DebugData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DebugData createFromParcel(Parcel parcel) {
                DebugData debugData = new DebugData();
                debugData.readFromParcel(parcel);
                return debugData;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DebugData[] newArray(int i) {
                return new DebugData[i];
            }
        };
        public PositionDebug position;
        public List<SatelliteData> satelliteDataArray;
        public TimeDebug time;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.position, i);
            parcel.writeTypedObject(this.time, i);
            parcel.writeTypedList(this.satelliteDataArray, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.position = (PositionDebug) parcel.readTypedObject(PositionDebug.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.time = (TimeDebug) parcel.readTypedObject(TimeDebug.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.satelliteDataArray = parcel.createTypedArrayList(SatelliteData.CREATOR);
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.satelliteDataArray) | describeContents(this.position) | describeContents(this.time);
        }

        private int describeContents(Object obj) {
            int i = 0;
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Collection) {
                Iterator it = ((Collection) obj).iterator();
                while (it.hasNext()) {
                    i |= describeContents(it.next());
                }
                return i;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
