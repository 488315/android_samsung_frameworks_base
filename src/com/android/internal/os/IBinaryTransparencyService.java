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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBinaryTransparencyService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBinaryTransparencyService)) {
                return (IBinaryTransparencyService) iInterfaceQueryLocalInterface;
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
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                List<ApexInfo> listCollectAllApexInfo = collectAllApexInfo(z);
                parcel2.writeNoException();
                parcel2.writeTypedList(listCollectAllApexInfo, 1);
            } else if (i == 4) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                List<AppInfo> listCollectAllUpdatedPreloadInfo = collectAllUpdatedPreloadInfo(bundle);
                parcel2.writeNoException();
                parcel2.writeTypedList(listCollectAllUpdatedPreloadInfo, 1);
            } else if (i == 5) {
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                List<AppInfo> listCollectAllSilentInstalledMbaInfo = collectAllSilentInstalledMbaInfo(bundle2);
                parcel2.writeNoException();
                parcel2.writeTypedList(listCollectAllSilentInstalledMbaInfo, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public void recordMeasurementsForAllPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<ApexInfo> collectAllApexInfo(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ApexInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<AppInfo> collectAllUpdatedPreloadInfo(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IBinaryTransparencyService
            public List<AppInfo> collectAllSilentInstalledMbaInfo(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBinaryTransparencyService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.longVersion);
            parcel.writeByteArray(this.digest);
            parcel.writeInt(this.digestAlgorithm);
            parcel.writeStringArray(this.signerDigests);
            parcel.writeString(this.moduleName);
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
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.longVersion = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.digest = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.digestAlgorithm = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.signerDigests = parcel.createStringArray();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.moduleName = parcel.readString();
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
            int iDataPosition = parcel.dataPosition();
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
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.longVersion = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.splitName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.digest = parcel.createByteArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.digestAlgorithm = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.signerDigests = parcel.createStringArray();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.mbaStatus = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.initiator = parcel.readString();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.initiatorSignerDigests = parcel.createStringArray();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.installer = parcel.readString();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.originator = parcel.readString();
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
}
