package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssAntennaInfoCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssAntennaInfoCallback".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void gnssAntennaInfoCb(GnssAntennaInfo[] gnssAntennaInfoArr) throws RemoteException;

    public static class Default implements IGnssAntennaInfoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public void gnssAntennaInfoCb(GnssAntennaInfo[] gnssAntennaInfoArr) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssAntennaInfoCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssAntennaInfoCb = 1;

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

        public static IGnssAntennaInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGnssAntennaInfoCallback)) {
                return (IGnssAntennaInfoCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "gnssAntennaInfoCb";
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
                GnssAntennaInfo[] gnssAntennaInfoArr = (GnssAntennaInfo[]) parcel.createTypedArray(GnssAntennaInfo.CREATOR);
                parcel.enforceNoDataAvail();
                gnssAntennaInfoCb(gnssAntennaInfoArr);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGnssAntennaInfoCallback {
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

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
            public void gnssAntennaInfoCb(GnssAntennaInfo[] gnssAntennaInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(gnssAntennaInfoArr, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method gnssAntennaInfoCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
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

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
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

    public static class Row implements Parcelable {
        public static final Parcelable.Creator<Row> CREATOR = new Parcelable.Creator<Row>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.Row.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Row createFromParcel(Parcel parcel) {
                Row row = new Row();
                row.readFromParcel(parcel);
                return row;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Row[] newArray(int i) {
                return new Row[i];
            }
        };
        public double[] row;

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
            parcel.writeDoubleArray(this.row);
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
                    this.row = parcel.createDoubleArray();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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

    public static class Coord implements Parcelable {
        public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.Coord.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coord createFromParcel(Parcel parcel) {
                Coord coord = new Coord();
                coord.readFromParcel(parcel);
                return coord;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coord[] newArray(int i) {
                return new Coord[i];
            }
        };
        public double x = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double xUncertainty = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double y = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double yUncertainty = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double z = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double zUncertainty = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
            parcel.writeDouble(this.x);
            parcel.writeDouble(this.xUncertainty);
            parcel.writeDouble(this.y);
            parcel.writeDouble(this.yUncertainty);
            parcel.writeDouble(this.z);
            parcel.writeDouble(this.zUncertainty);
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
                    this.x = parcel.readDouble();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.xUncertainty = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.y = parcel.readDouble();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.yUncertainty = parcel.readDouble();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.z = parcel.readDouble();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.zUncertainty = parcel.readDouble();
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

    public static class GnssAntennaInfo implements Parcelable {
        public static final Parcelable.Creator<GnssAntennaInfo> CREATOR = new Parcelable.Creator<GnssAntennaInfo>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssAntennaInfo createFromParcel(Parcel parcel) {
                GnssAntennaInfo gnssAntennaInfo = new GnssAntennaInfo();
                gnssAntennaInfo.readFromParcel(parcel);
                return gnssAntennaInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssAntennaInfo[] newArray(int i) {
                return new GnssAntennaInfo[i];
            }
        };
        public long carrierFrequencyHz = 0;
        public Coord phaseCenterOffsetCoordinateMillimeters;
        public Row[] phaseCenterVariationCorrectionMillimeters;
        public Row[] phaseCenterVariationCorrectionUncertaintyMillimeters;
        public Row[] signalGainCorrectionDbi;
        public Row[] signalGainCorrectionUncertaintyDbi;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.carrierFrequencyHz);
            parcel.writeTypedObject(this.phaseCenterOffsetCoordinateMillimeters, i);
            parcel.writeTypedArray(this.phaseCenterVariationCorrectionMillimeters, i);
            parcel.writeTypedArray(this.phaseCenterVariationCorrectionUncertaintyMillimeters, i);
            parcel.writeTypedArray(this.signalGainCorrectionDbi, i);
            parcel.writeTypedArray(this.signalGainCorrectionUncertaintyDbi, i);
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
                    this.carrierFrequencyHz = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.phaseCenterOffsetCoordinateMillimeters = (Coord) parcel.readTypedObject(Coord.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.phaseCenterVariationCorrectionMillimeters = (Row[]) parcel.createTypedArray(Row.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.phaseCenterVariationCorrectionUncertaintyMillimeters = (Row[]) parcel.createTypedArray(Row.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.signalGainCorrectionDbi = (Row[]) parcel.createTypedArray(Row.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.signalGainCorrectionUncertaintyDbi = (Row[]) parcel.createTypedArray(Row.CREATOR);
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
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.signalGainCorrectionUncertaintyDbi) | describeContents(this.phaseCenterOffsetCoordinateMillimeters) | describeContents(this.phaseCenterVariationCorrectionMillimeters) | describeContents(this.phaseCenterVariationCorrectionUncertaintyMillimeters) | describeContents(this.signalGainCorrectionDbi);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int iDescribeContents = 0;
                for (Object obj2 : (Object[]) obj) {
                    iDescribeContents |= describeContents(obj2);
                }
                return iDescribeContents;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
