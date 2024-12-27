package com.android.server.security;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.security.keystore.KeyAttestationApplicationId;
import android.security.keystore.KeyAttestationPackageInfo;
import android.security.keystore.Signature;

public final class KeyAttestationApplicationIdProviderService extends Binder implements IInterface {
    public final PackageManager mPackageManager;

    public KeyAttestationApplicationIdProviderService(Context context) {
        attachInterface(this, "android.security.keystore.IKeyAttestationApplicationIdProvider");
        this.mPackageManager = context.getPackageManager();
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface(
                    "android.security.keystore.IKeyAttestationApplicationIdProvider");
        }
        if (i == 1598968902) {
            parcel2.writeString("android.security.keystore.IKeyAttestationApplicationIdProvider");
            return true;
        }
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        int readInt = parcel.readInt();
        parcel.enforceNoDataAvail();
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1017 && callingUid != 1076) {
            throw new SecurityException("This service can only be used by Keystore or Credstore");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String[] packagesForUid = this.mPackageManager.getPackagesForUid(readInt);
                if (packagesForUid == null) {
                    throw new ServiceSpecificException(1, "No package for uid: " + readInt);
                }
                int userId = UserHandle.getUserId(readInt);
                KeyAttestationPackageInfo[] keyAttestationPackageInfoArr =
                        new KeyAttestationPackageInfo[packagesForUid.length];
                for (int i3 = 0; i3 < packagesForUid.length; i3++) {
                    PackageInfo packageInfoAsUser =
                            this.mPackageManager.getPackageInfoAsUser(
                                    packagesForUid[i3], 64, userId);
                    KeyAttestationPackageInfo keyAttestationPackageInfo =
                            new KeyAttestationPackageInfo();
                    keyAttestationPackageInfo.packageName = new String(packagesForUid[i3]);
                    keyAttestationPackageInfo.versionCode = packageInfoAsUser.getLongVersionCode();
                    keyAttestationPackageInfo.signatures =
                            new Signature[packageInfoAsUser.signatures.length];
                    for (int i4 = 0; i4 < packageInfoAsUser.signatures.length; i4++) {
                        Signature signature = new Signature();
                        signature.data = packageInfoAsUser.signatures[i4].toByteArray();
                        keyAttestationPackageInfo.signatures[i4] = signature;
                    }
                    keyAttestationPackageInfoArr[i3] = keyAttestationPackageInfo;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                KeyAttestationApplicationId keyAttestationApplicationId =
                        new KeyAttestationApplicationId();
                keyAttestationApplicationId.packageInfos = keyAttestationPackageInfoArr;
                parcel2.writeNoException();
                parcel2.writeTypedObject(keyAttestationApplicationId, 1);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RemoteException(e.getMessage());
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
