package com.samsung.android.remoteappmode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStartActivityInterceptListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.remoteappmode.IStartActivityInterceptListener";

    void onStartActivityIntercepted(
            Intent intent,
            Bundle bundle,
            ActivityInfo activityInfo,
            int i,
            boolean z,
            int i2,
            int i3,
            int i4)
            throws RemoteException;

    public static class Default implements IStartActivityInterceptListener {
        @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
        public void onStartActivityIntercepted(
                Intent intent,
                Bundle activityOptionsBundle,
                ActivityInfo activityInfo,
                int interceptedDisplayId,
                boolean isVisibleTask,
                int runningTaskId,
                int runningTaskDisplayId,
                int interceptReason)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IStartActivityInterceptListener {
        static final int TRANSACTION_onStartActivityIntercepted = 1;

        public Stub() {
            attachInterface(this, IStartActivityInterceptListener.DESCRIPTOR);
        }

        public static IStartActivityInterceptListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStartActivityInterceptListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IStartActivityInterceptListener)) {
                return (IStartActivityInterceptListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onStartActivityIntercepted";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IStartActivityInterceptListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStartActivityInterceptListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    ActivityInfo _arg2 = (ActivityInfo) data.readTypedObject(ActivityInfo.CREATOR);
                    int _arg3 = data.readInt();
                    boolean _arg4 = data.readBoolean();
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    int _arg7 = data.readInt();
                    data.enforceNoDataAvail();
                    onStartActivityIntercepted(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStartActivityInterceptListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStartActivityInterceptListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
            public void onStartActivityIntercepted(
                    Intent intent,
                    Bundle activityOptionsBundle,
                    ActivityInfo activityInfo,
                    int interceptedDisplayId,
                    boolean isVisibleTask,
                    int runningTaskId,
                    int runningTaskDisplayId,
                    int interceptReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStartActivityInterceptListener.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(activityOptionsBundle, 0);
                    _data.writeTypedObject(activityInfo, 0);
                    _data.writeInt(interceptedDisplayId);
                    _data.writeBoolean(isVisibleTask);
                    _data.writeInt(runningTaskId);
                    _data.writeInt(runningTaskDisplayId);
                    _data.writeInt(interceptReason);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
