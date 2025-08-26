package com.samsung.android.knox.net.nap.serviceprovider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface INetworkAnalyticsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService";

    int onActivateProfile(String str, int i, String str2) throws RemoteException;

    void onDataAvailable(String str, List<String> list) throws RemoteException;

    int onDeactivateProfile(String str, int i) throws RemoteException;

    public abstract class Stub extends Binder implements INetworkAnalyticsService {
        public static final int TRANSACTION_onActivateProfile = 1;
        public static final int TRANSACTION_onDataAvailable = 3;
        public static final int TRANSACTION_onDeactivateProfile = 2;

        class Proxy implements INetworkAnalyticsService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INetworkAnalyticsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public int onActivateProfile(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public void onDataAvailable(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public int onDeactivateProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, INetworkAnalyticsService.DESCRIPTOR);
        }

        public static INetworkAnalyticsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INetworkAnalyticsService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INetworkAnalyticsService)) ? new Proxy(iBinder) : (INetworkAnalyticsService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INetworkAnalyticsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INetworkAnalyticsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int iOnActivateProfile = onActivateProfile(string, i3, string2);
                parcel2.writeNoException();
                parcel2.writeInt(iOnActivateProfile);
            } else if (i == 2) {
                String string3 = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iOnDeactivateProfile = onDeactivateProfile(string3, i4);
                parcel2.writeNoException();
                parcel2.writeInt(iOnDeactivateProfile);
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String string4 = parcel.readString();
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onDataAvailable(string4, arrayListCreateStringArrayList);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements INetworkAnalyticsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public int onActivateProfile(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public int onDeactivateProfile(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public void onDataAvailable(String str, List<String> list) throws RemoteException {
        }
    }
}
