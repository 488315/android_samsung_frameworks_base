package com.google.android.setupcompat;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISetupCompatService extends IInterface {
    void logMetric(int i, Bundle bundle);

    void onFocusStatusChanged(Bundle bundle);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISetupCompatService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements ISetupCompatService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.google.android.setupcompat.ISetupCompatService
            public final void logMetric(int i, Bundle bundle) {
                Bundle bundle2 = Bundle.EMPTY;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeInt(i);
                    obtain.writeInt(1);
                    bundle.writeToParcel(obtain, 0);
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
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
                    obtain.writeInt(1);
                    bundle.writeToParcel(obtain, 0);
                    this.mRemote.transact(3, obtain, null, 1);
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
            if (i != 2) {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onFocusStatusChanged((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                return true;
            }
            int readInt = parcel.readInt();
            Parcelable.Creator creator = Bundle.CREATOR;
            Bundle bundle = (Bundle) (parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
            logMetric(readInt, bundle);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
