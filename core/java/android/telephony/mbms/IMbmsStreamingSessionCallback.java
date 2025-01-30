package android.telephony.mbms;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IMbmsStreamingSessionCallback extends IInterface {
  void onError(int i, String str) throws RemoteException;

  void onMiddlewareReady() throws RemoteException;

  void onStreamingServicesUpdated(List<StreamingServiceInfo> list) throws RemoteException;

  public static class Default implements IMbmsStreamingSessionCallback {
    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onError(int errorCode, String message) throws RemoteException {}

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onStreamingServicesUpdated(List<StreamingServiceInfo> services)
        throws RemoteException {}

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onMiddlewareReady() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IMbmsStreamingSessionCallback {
    public static final String DESCRIPTOR = "android.telephony.mbms.IMbmsStreamingSessionCallback";
    static final int TRANSACTION_onError = 1;
    static final int TRANSACTION_onMiddlewareReady = 3;
    static final int TRANSACTION_onStreamingServicesUpdated = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IMbmsStreamingSessionCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IMbmsStreamingSessionCallback)) {
        return (IMbmsStreamingSessionCallback) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "onError";
        case 2:
          return "onStreamingServicesUpdated";
        case 3:
          return "onMiddlewareReady";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              onError(_arg0, _arg1);
              return true;
            case 2:
              List<StreamingServiceInfo> _arg02 =
                  data.createTypedArrayList(StreamingServiceInfo.CREATOR);
              data.enforceNoDataAvail();
              onStreamingServicesUpdated(_arg02);
              return true;
            case 3:
              onMiddlewareReady();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMbmsStreamingSessionCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
      public void onError(int errorCode, String message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(errorCode);
          _data.writeString(message);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
      public void onStreamingServicesUpdated(List<StreamingServiceInfo> services)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedList(services, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
      public void onMiddlewareReady() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
