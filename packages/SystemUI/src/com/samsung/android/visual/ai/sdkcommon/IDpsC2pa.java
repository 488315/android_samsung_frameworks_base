package com.samsung.android.visual.ai.sdkcommon;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback;
import com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback;

/* loaded from: classes4.dex */
public interface IDpsC2pa extends IInterface {

    public class _Parcel {
        /* renamed from: -$$Nest$smwriteTypedObject, reason: not valid java name */
        public static void m3331$$Nest$smwriteTypedObject(Parcel parcel, Parcelable parcelable) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                ((Bundle) parcelable).writeToParcel(parcel, 0);
            }
        }
    }

    void clearAllManifestsFromCache();

    boolean clearManifestsFromCache(String str);

    void embedManifestToFile();

    void embedManifestToPfd(Bundle bundle, IC2paEmbedCallback iC2paEmbedCallback);

    void getManifestsAsString();

    void getManifestsAsStringWithPfd(Bundle bundle, IC2paManifestsCallback iC2paManifestsCallback);

    boolean isC2paInfoExist();

    boolean isC2paInfoExistWithPfd(Bundle bundle);

    String saveManifestsToCache();

    String saveManifestsToCacheWithPfd(Bundle bundle);

    public abstract class Stub extends Binder implements IDpsC2pa {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IDpsC2pa {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final void clearAllManifestsFromCache() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final boolean clearManifestsFromCache(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final void embedManifestToPfd(Bundle bundle, IC2paEmbedCallback iC2paEmbedCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    _Parcel.m3331$$Nest$smwriteTypedObject(parcelObtain, bundle);
                    parcelObtain.writeStrongInterface(iC2paEmbedCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final void getManifestsAsStringWithPfd(Bundle bundle, IC2paManifestsCallback iC2paManifestsCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    _Parcel.m3331$$Nest$smwriteTypedObject(parcelObtain, bundle);
                    parcelObtain.writeStrongInterface(iC2paManifestsCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final boolean isC2paInfoExistWithPfd(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    _Parcel.m3331$$Nest$smwriteTypedObject(parcelObtain, bundle);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.visual.ai.sdkcommon.IDpsC2pa
            public final String saveManifestsToCacheWithPfd(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                    _Parcel.m3331$$Nest$smwriteTypedObject(parcelObtain, bundle);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.visual.ai.sdkcommon.IDpsC2pa");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.readString();
                    IC2paManifestsCallback.Stub.asInterface(parcel.readStrongBinder());
                    getManifestsAsString();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.readString();
                    parcel.readString();
                    parcel.readString();
                    parcel.createStringArrayList();
                    IC2paEmbedCallback.Stub.asInterface(parcel.readStrongBinder());
                    embedManifestToFile();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.readString();
                    String strSaveManifestsToCache = saveManifestsToCache();
                    parcel2.writeNoException();
                    parcel2.writeString(strSaveManifestsToCache);
                    return true;
                case 4:
                    boolean zClearManifestsFromCache = clearManifestsFromCache(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zClearManifestsFromCache ? 1 : 0);
                    return true;
                case 5:
                    clearAllManifestsFromCache();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.readString();
                    boolean zIsC2paInfoExist = isC2paInfoExist();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsC2paInfoExist ? 1 : 0);
                    return true;
                case 7:
                    getManifestsAsStringWithPfd((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null), IC2paManifestsCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    embedManifestToPfd((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null), IC2paEmbedCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String strSaveManifestsToCacheWithPfd = saveManifestsToCacheWithPfd((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeString(strSaveManifestsToCacheWithPfd);
                    return true;
                case 10:
                    boolean zIsC2paInfoExistWithPfd = isC2paInfoExistWithPfd((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsC2paInfoExistWithPfd ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
