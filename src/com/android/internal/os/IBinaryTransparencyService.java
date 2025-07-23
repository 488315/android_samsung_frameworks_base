package com.android.internal.os;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IBinaryTransparencyService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.os.IBinaryTransparencyService";

    public static class Default implements IBinaryTransparencyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public List<ApexInfo> collectAllApexInfo(boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public List<AppInfo> collectAllSilentInstalledMbaInfo(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public List<AppInfo> collectAllUpdatedPreloadInfo(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public String getSignedImageInfo() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IBinaryTransparencyService
        public void recordMeasurementsForAllPackages() throws RemoteException {
        }
    }

    List<ApexInfo> collectAllApexInfo(boolean z) throws RemoteException;

    List<AppInfo> collectAllSilentInstalledMbaInfo(Bundle bundle) throws RemoteException;

    List<AppInfo> collectAllUpdatedPreloadInfo(Bundle bundle) throws RemoteException;

    String getSignedImageInfo() throws RemoteException;

    void recordMeasurementsForAllPackages() throws RemoteException;

    public static abstract class Stub extends Binder implements IBinaryTransparencyService {
        static final int TRANSACTION_collectAllApexInfo = 3;
        static final int TRANSACTION_collectAllSilentInstalledMbaInfo = 5;
        static final int TRANSACTION_collectAllUpdatedPreloadInfo = 4;
        static final int TRANSACTION_getSignedImageInfo = 1;
        static final int TRANSACTION_recordMeasurementsForAllPackages = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IBinaryTransparencyService.DESCRIPTOR);
        }

        public static IBinaryTransparencyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBinaryTransparencyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBinaryTransparencyService)) {
                return (IBinaryTransparencyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSignedImageInfo";
            }
            if (i == 2) {
                return "recordMeasurementsForAllPackages";
            }
            if (i == 3) {
                return "collectAllApexInfo";
            }
            if (i == 4) {
                return "collectAllUpdatedPreloadInfo";
            }
            if (i != 5) {
                return null;
            }
            return "collectAllSilentInstalledMbaInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBinaryTransparencyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBinaryTransparencyService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String signedImageInfo = getSignedImageInfo();
                parcel2.writeNoException();
                parcel2.writeString(signedImageInfo);
            } else if (i == 2) {
                recordMeasurementsForAllPackages();
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                List<ApexInfo> collectAllApexInfo = collectAllApexInfo(readBoolean);
                parcel2.writeNoException();
                parcel2.writeTypedList(collectAllApexInfo, 1);
            } else if (i == 4) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                List<AppInfo> collectAllUpdatedPreloadInfo = collectAllUpdatedPreloadInfo(bundle);
                parcel2.writeNoException();
                parcel2.writeTypedList(collectAllUpdatedPreloadInfo, 1);
            } else if (i == 5) {
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                List<AppInfo> collectAllSilentInstalledMbaInfo = collectAllSilentInstalledMbaInfo(bundle2);
                parcel2.writeNoException();
                parcel2.writeTypedList(collectAllSilentInstalledMbaInfo, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBinaryTransparencyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBinaryTransparencyService.DESCRIPTOR;
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public String getSignedImageInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public void recordMeasurementsForAllPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<ApexInfo> collectAllApexInfo(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ApexInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<AppInfo> collectAllUpdatedPreloadInfo(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<AppInfo> collectAllSilentInstalledMbaInfo(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    public static class ApexInfo implements Parcelable {
        public static final Parcelable.Creator<ApexInfo> CREATOR = new Parcelable.Creator<ApexInfo>() { // from class: com.android.internal.os.IBinaryTransparencyService.ApexInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ApexInfo createFromParcel(Parcel parcel) {
                ApexInfo apexInfo = new ApexInfo();
                apexInfo.readFromParcel(parcel);
                return apexInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ApexInfo[] newArray(int i) {
                return new ApexInfo[i];
            }
        };
        public byte[] digest;
        public String moduleName;
        public String packageName;
        public String[] signerDigests;
        public long longVersion = 0;
        public int digestAlgorithm = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.longVersion);
            parcel.writeByteArray(this.digest);
            parcel.writeInt(this.digestAlgorithm);
            parcel.writeStringArray(this.signerDigests);
            parcel.writeString(this.moduleName);
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
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.longVersion = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.digest = parcel.createByteArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.digestAlgorithm = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.signerDigests = parcel.createStringArray();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.moduleName = parcel.readString();
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

    public static class AppInfo implements Parcelable {
        public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() { // from class: com.android.internal.os.IBinaryTransparencyService.AppInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppInfo createFromParcel(Parcel parcel) {
                AppInfo appInfo = new AppInfo();
                appInfo.readFromParcel(parcel);
                return appInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppInfo[] newArray(int i) {
                return new AppInfo[i];
            }
        };
        public byte[] digest;
        public String initiator;
        public String[] initiatorSignerDigests;
        public String installer;
        public String originator;
        public String packageName;
        public String[] signerDigests;
        public String splitName;
        public long longVersion = 0;
        public int digestAlgorithm = 0;
        public int mbaStatus = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.longVersion);
            parcel.writeString(this.splitName);
            parcel.writeByteArray(this.digest);
            parcel.writeInt(this.digestAlgorithm);
            parcel.writeStringArray(this.signerDigests);
            parcel.writeInt(this.mbaStatus);
            parcel.writeString(this.initiator);
            parcel.writeStringArray(this.initiatorSignerDigests);
            parcel.writeString(this.installer);
            parcel.writeString(this.originator);
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
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.longVersion = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.splitName = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.digest = parcel.createByteArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.digestAlgorithm = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.signerDigests = parcel.createStringArray();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.mbaStatus = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.initiator = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.initiatorSignerDigests = parcel.createStringArray();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.installer = parcel.readString();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.originator = parcel.readString();
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
}
