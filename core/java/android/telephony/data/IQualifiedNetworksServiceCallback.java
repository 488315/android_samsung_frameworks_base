package android.telephony.data;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IQualifiedNetworksServiceCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.telephony.data.IQualifiedNetworksServiceCallback";

  void onHandoverEnabledChanged(int i) throws RemoteException;

  void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws RemoteException;

  public static class Default implements IQualifiedNetworksServiceCallback {
    @Override // android.telephony.data.IQualifiedNetworksServiceCallback
    public void onQualifiedNetworkTypesChanged(int apnTypes, int[] qualifiedNetworkTypes)
        throws RemoteException {}

    @Override // android.telephony.data.IQualifiedNetworksServiceCallback
    public void onHandoverEnabledChanged(int supportedApnTypes) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IQualifiedNetworksServiceCallback {
    static final int TRANSACTION_onHandoverEnabledChanged = 2;
    static final int TRANSACTION_onQualifiedNetworkTypesChanged = 1;

    public Stub() {
      attachInterface(this, IQualifiedNetworksServiceCallback.DESCRIPTOR);
    }

    public static IQualifiedNetworksServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IQualifiedNetworksServiceCallback)) {
        return (IQualifiedNetworksServiceCallback) iin;
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
          return "onQualifiedNetworkTypesChanged";
        case 2:
          return "onHandoverEnabledChanged";
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
        data.enforceInterface(IQualifiedNetworksServiceCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IQualifiedNetworksServiceCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int[] _arg1 = data.createIntArray();
              data.enforceNoDataAvail();
              onQualifiedNetworkTypesChanged(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onHandoverEnabledChanged(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IQualifiedNetworksServiceCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IQualifiedNetworksServiceCallback.DESCRIPTOR;
      }

      @Override // android.telephony.data.IQualifiedNetworksServiceCallback
      public void onQualifiedNetworkTypesChanged(int apnTypes, int[] qualifiedNetworkTypes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
          _data.writeInt(apnTypes);
          _data.writeIntArray(qualifiedNetworkTypes);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.data.IQualifiedNetworksServiceCallback
      public void onHandoverEnabledChanged(int supportedApnTypes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IQualifiedNetworksServiceCallback.DESCRIPTOR);
          _data.writeInt(supportedApnTypes);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
