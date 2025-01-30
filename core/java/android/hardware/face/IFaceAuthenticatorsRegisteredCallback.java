package android.hardware.face;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFaceAuthenticatorsRegisteredCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.hardware.face.IFaceAuthenticatorsRegisteredCallback";

  void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> list)
      throws RemoteException;

  public static class Default implements IFaceAuthenticatorsRegisteredCallback {
    @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
    public void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> sensors)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder
      implements IFaceAuthenticatorsRegisteredCallback {
    static final int TRANSACTION_onAllAuthenticatorsRegistered = 1;

    public Stub() {
      attachInterface(this, IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
    }

    public static IFaceAuthenticatorsRegisteredCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IFaceAuthenticatorsRegisteredCallback)) {
        return (IFaceAuthenticatorsRegisteredCallback) iin;
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
          return "onAllAuthenticatorsRegistered";
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
        data.enforceInterface(IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<FaceSensorPropertiesInternal> _arg0 =
                  data.createTypedArrayList(FaceSensorPropertiesInternal.CREATOR);
              data.enforceNoDataAvail();
              onAllAuthenticatorsRegistered(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IFaceAuthenticatorsRegisteredCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR;
      }

      @Override // android.hardware.face.IFaceAuthenticatorsRegisteredCallback
      public void onAllAuthenticatorsRegistered(List<FaceSensorPropertiesInternal> sensors)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IFaceAuthenticatorsRegisteredCallback.DESCRIPTOR);
          _data.writeTypedList(sensors, 0);
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
