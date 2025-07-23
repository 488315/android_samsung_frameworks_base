package com.sec.android.diagmonagent.sa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IDMAInterface extends IInterface {
    String checkToken();

    int sendCommon(String str, String str2, String str3);

    int sendLog(String str, String str2, String str3, long j);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IDMAInterface {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IDMAInterface {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final String checkToken() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendCommon(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendLog(String str, String str2, String str3, long j) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.sec.android.diagmonagent.sa.IDMAInterface");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.sec.android.diagmonagent.sa.IDMAInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.sec.android.diagmonagent.sa.IDMAInterface");
                return true;
            }
            if (i == 1) {
                String checkToken = checkToken();
                parcel2.writeNoException();
                parcel2.writeString(checkToken);
                return true;
            }
            if (i == 2) {
                parcel.readInt();
                int sendCommon = sendCommon(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(sendCommon);
                return true;
            }
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.readInt();
            int sendLog = sendLog(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readLong());
            parcel2.writeNoException();
            parcel2.writeInt(sendLog);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
