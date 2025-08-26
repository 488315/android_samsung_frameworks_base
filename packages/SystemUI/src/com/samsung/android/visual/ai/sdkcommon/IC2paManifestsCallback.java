package com.samsung.android.visual.ai.sdkcommon;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IC2paManifestsCallback extends IInterface {
    void onError(String str);

    void onPfdCreation(ParcelFileDescriptor parcelFileDescriptor, boolean z);

    void onResult(String str, boolean z, boolean z2);

    public abstract class Stub extends Binder implements IC2paManifestsCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onPfdCreation = 3;
        static final int TRANSACTION_onResult = 1;

        public class Proxy implements IC2paManifestsCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback");
        }

        public static IC2paManifestsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IC2paManifestsCallback)) ? new Proxy(iBinder) : (IC2paManifestsCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback");
                return true;
            }
            if (i == 1) {
                onResult(parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0);
                parcel2.writeNoException();
            } else if (i == 2) {
                onError(parcel.readString());
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onPfdCreation((ParcelFileDescriptor) (parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null), parcel.readInt() != 0);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
