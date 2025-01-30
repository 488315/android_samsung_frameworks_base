package android.telephony.ims.aidl;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IFeatureProvisioningCallback extends IInterface {
  public static final String DESCRIPTOR = "android.telephony.ims.aidl.IFeatureProvisioningCallback";

  void onFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException;

  void onRcsFeatureProvisioningChanged(int i, int i2, boolean z) throws RemoteException;

  public static class Default implements IFeatureProvisioningCallback {
    @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
    public void onFeatureProvisioningChanged(int capability, int tech, boolean isProvisioned)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
    public void onRcsFeatureProvisioningChanged(int capability, int tech, boolean isProvisioned)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IFeatureProvisioningCallback {
    static final int TRANSACTION_onFeatureProvisioningChanged = 1;
    static final int TRANSACTION_onRcsFeatureProvisioningChanged = 2;

    public Stub() {
      attachInterface(this, IFeatureProvisioningCallback.DESCRIPTOR);
    }

    public static IFeatureProvisioningCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IFeatureProvisioningCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IFeatureProvisioningCallback)) {
        return (IFeatureProvisioningCallback) iin;
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
          return "onFeatureProvisioningChanged";
        case 2:
          return "onRcsFeatureProvisioningChanged";
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
        data.enforceInterface(IFeatureProvisioningCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IFeatureProvisioningCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              boolean _arg2 = data.readBoolean();
              data.enforceNoDataAvail();
              onFeatureProvisioningChanged(_arg0, _arg1, _arg2);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              boolean _arg22 = data.readBoolean();
              data.enforceNoDataAvail();
              onRcsFeatureProvisioningChanged(_arg02, _arg12, _arg22);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IFeatureProvisioningCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IFeatureProvisioningCallback.DESCRIPTOR;
      }

      @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
      public void onFeatureProvisioningChanged(int capability, int tech, boolean isProvisioned)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
          _data.writeInt(capability);
          _data.writeInt(tech);
          _data.writeBoolean(isProvisioned);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IFeatureProvisioningCallback
      public void onRcsFeatureProvisioningChanged(int capability, int tech, boolean isProvisioned)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IFeatureProvisioningCallback.DESCRIPTOR);
          _data.writeInt(capability);
          _data.writeInt(tech);
          _data.writeBoolean(isProvisioned);
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
