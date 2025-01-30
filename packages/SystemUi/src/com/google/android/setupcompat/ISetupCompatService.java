package com.google.android.setupcompat;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ISetupCompatService extends IInterface {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class _Parcel {
        /* renamed from: -$$Nest$smreadTypedObject, reason: not valid java name */
        public static Object m2765$$Nest$smreadTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* renamed from: -$$Nest$smwriteTypedObject, reason: not valid java name */
        public static void m2766$$Nest$smwriteTypedObject(Parcel parcel, Parcelable parcelable) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, 0);
            }
        }
    }

    void logMetric(int i, Bundle bundle, Bundle bundle2);

    void onFocusStatusChanged(Bundle bundle);

    void validateActivity(Bundle bundle, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISetupCompatService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements ISetupCompatService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.google.android.setupcompat.ISetupCompatService
            public final void logMetric(int i, Bundle bundle, Bundle bundle2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeInt(i);
                    _Parcel.m2766$$Nest$smwriteTypedObject(obtain, bundle);
                    _Parcel.m2766$$Nest$smwriteTypedObject(obtain, bundle2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.google.android.setupcompat.ISetupCompatService
            public final void onFocusStatusChanged(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    _Parcel.m2766$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.google.android.setupcompat.ISetupCompatService
            public final void validateActivity(Bundle bundle, String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeString(str);
                    _Parcel.m2766$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.google.android.setupcompat.ISetupCompatService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.google.android.setupcompat.ISetupCompatService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.google.android.setupcompat.ISetupCompatService");
                return true;
            }
            if (i == 1) {
                validateActivity((Bundle) _Parcel.m2765$$Nest$smreadTypedObject(parcel, Bundle.CREATOR), parcel.readString());
            } else if (i == 2) {
                int readInt = parcel.readInt();
                Parcelable.Creator creator = Bundle.CREATOR;
                logMetric(readInt, (Bundle) _Parcel.m2765$$Nest$smreadTypedObject(parcel, creator), (Bundle) _Parcel.m2765$$Nest$smreadTypedObject(parcel, creator));
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onFocusStatusChanged((Bundle) _Parcel.m2765$$Nest$smreadTypedObject(parcel, Bundle.CREATOR));
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
