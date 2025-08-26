package com.sec.android.diagmonagent.sa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface IDMAInterface extends IInterface {
    String checkToken();

    int sendCommon(String str, String str2, String str3);

    int sendLog(String str, String str2, String str3, long j);

    public abstract class Stub extends Binder implements IDMAInterface {
        public static final /* synthetic */ int $r8$clinit = 0;

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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendCommon(String str, String str2, String str3) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    parcelObtain.writeInt(0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendLog(String str, String str2, String str3, long j) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    parcelObtain.writeInt(0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                String strCheckToken = checkToken();
                parcel2.writeNoException();
                parcel2.writeString(strCheckToken);
                return true;
            }
            if (i == 2) {
                parcel.readInt();
                int iSendCommon = sendCommon(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(iSendCommon);
                return true;
            }
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.readInt();
            int iSendLog = sendLog(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readLong());
            parcel2.writeNoException();
            parcel2.writeInt(iSendLog);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
