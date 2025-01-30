package android.hardware.hdmi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiVendorCommandListener extends IInterface {
  void onControlStateChanged(boolean z, int i) throws RemoteException;

  void onReceived(int i, int i2, byte[] bArr, boolean z) throws RemoteException;

  public static class Default implements IHdmiVendorCommandListener {
    @Override // android.hardware.hdmi.IHdmiVendorCommandListener
    public void onReceived(
        int logicalAddress, int destAddress, byte[] operands, boolean hasVendorId)
        throws RemoteException {}

    @Override // android.hardware.hdmi.IHdmiVendorCommandListener
    public void onControlStateChanged(boolean enabled, int reason) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IHdmiVendorCommandListener {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiVendorCommandListener";
    static final int TRANSACTION_onControlStateChanged = 2;
    static final int TRANSACTION_onReceived = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IHdmiVendorCommandListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IHdmiVendorCommandListener)) {
        return (IHdmiVendorCommandListener) iin;
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
          return "onReceived";
        case 2:
          return "onControlStateChanged";
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
              int _arg1 = data.readInt();
              byte[] _arg2 = data.createByteArray();
              boolean _arg3 = data.readBoolean();
              data.enforceNoDataAvail();
              onReceived(_arg0, _arg1, _arg2, _arg3);
              return true;
            case 2:
              boolean _arg02 = data.readBoolean();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              onControlStateChanged(_arg02, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IHdmiVendorCommandListener {
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

      @Override // android.hardware.hdmi.IHdmiVendorCommandListener
      public void onReceived(
          int logicalAddress, int destAddress, byte[] operands, boolean hasVendorId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(logicalAddress);
          _data.writeInt(destAddress);
          _data.writeByteArray(operands);
          _data.writeBoolean(hasVendorId);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.hdmi.IHdmiVendorCommandListener
      public void onControlStateChanged(boolean enabled, int reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeInt(reason);
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
