package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUsbSerialReader extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.usb.IUsbSerialReader";

  String getSerial(String str) throws RemoteException;

  public static class Default implements IUsbSerialReader {
    @Override // android.hardware.usb.IUsbSerialReader
    public String getSerial(String packageName) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUsbSerialReader {
    static final int TRANSACTION_getSerial = 1;

    public Stub() {
      attachInterface(this, IUsbSerialReader.DESCRIPTOR);
    }

    public static IUsbSerialReader asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IUsbSerialReader.DESCRIPTOR);
      if (iin != null && (iin instanceof IUsbSerialReader)) {
        return (IUsbSerialReader) iin;
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
          return "getSerial";
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
        data.enforceInterface(IUsbSerialReader.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IUsbSerialReader.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              String _result = getSerial(_arg0);
              reply.writeNoException();
              reply.writeString(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUsbSerialReader {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IUsbSerialReader.DESCRIPTOR;
      }

      @Override // android.hardware.usb.IUsbSerialReader
      public String getSerial(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IUsbSerialReader.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
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
