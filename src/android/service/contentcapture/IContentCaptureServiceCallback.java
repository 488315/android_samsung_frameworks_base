package android.service.contentcapture;

import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.contentcapture.ContentCaptureCondition;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IContentCaptureServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureServiceCallback";

    public static class Default implements IContentCaptureServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void disableSelf() throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureConditions(String str, List<ContentCaptureCondition> list) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureServiceCallback
        public void writeSessionFlush(int i, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int i2) throws RemoteException {
        }
    }

    void disableSelf() throws RemoteException;

    void setContentCaptureConditions(String str, List<ContentCaptureCondition> list) throws RemoteException;

    void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException;

    void writeSessionFlush(int i, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentCaptureServiceCallback {
        static final int TRANSACTION_disableSelf = 3;
        static final int TRANSACTION_setContentCaptureConditions = 2;
        static final int TRANSACTION_setContentCaptureWhitelist = 1;
        static final int TRANSACTION_writeSessionFlush = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IContentCaptureServiceCallback.DESCRIPTOR);
        }

        public static IContentCaptureServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContentCaptureServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentCaptureServiceCallback)) {
                return (IContentCaptureServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setContentCaptureWhitelist";
            }
            if (i == 2) {
                return "setContentCaptureConditions";
            }
            if (i == 3) {
                return "disableSelf";
            }
            if (i != 4) {
                return null;
            }
            return "writeSessionFlush";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentCaptureServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentCaptureServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                parcel.enforceNoDataAvail();
                setContentCaptureWhitelist(createStringArrayList, createTypedArrayList);
            } else if (i == 2) {
                String readString = parcel.readString();
                ArrayList createTypedArrayList2 = parcel.createTypedArrayList(ContentCaptureCondition.CREATOR);
                parcel.enforceNoDataAvail();
                setContentCaptureConditions(readString, createTypedArrayList2);
            } else if (i == 3) {
                disableSelf();
            } else if (i == 4) {
                int readInt = parcel.readInt();
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                FlushMetrics flushMetrics = (FlushMetrics) parcel.readTypedObject(FlushMetrics.CREATOR);
                ContentCaptureOptions contentCaptureOptions = (ContentCaptureOptions) parcel.readTypedObject(ContentCaptureOptions.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                writeSessionFlush(readInt, componentName, flushMetrics, contentCaptureOptions, readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContentCaptureServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentCaptureServiceCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureWhitelist(List<String> list, List<ComponentName> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void setContentCaptureConditions(String str, List<ContentCaptureCondition> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void disableSelf() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureServiceCallback
            public void writeSessionFlush(int i, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(flushMetrics, 0);
                    obtain.writeTypedObject(contentCaptureOptions, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
