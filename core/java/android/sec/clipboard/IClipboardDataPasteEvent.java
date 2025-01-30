package android.sec.clipboard;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes3.dex */
public interface IClipboardDataPasteEvent extends IInterface {
  public static final String DESCRIPTOR = "android.sec.clipboard.IClipboardDataPasteEvent";

  void onPaste(SemClipData semClipData) throws RemoteException;

  public static class Default implements IClipboardDataPasteEvent {
    @Override // android.sec.clipboard.IClipboardDataPasteEvent
    public void onPaste(SemClipData data) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IClipboardDataPasteEvent {
    static final int TRANSACTION_onPaste = 1;

    public Stub() {
      attachInterface(this, IClipboardDataPasteEvent.DESCRIPTOR);
    }

    public static IClipboardDataPasteEvent asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IClipboardDataPasteEvent.DESCRIPTOR);
      if (iin != null && (iin instanceof IClipboardDataPasteEvent)) {
        return (IClipboardDataPasteEvent) iin;
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
          return "onPaste";
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
        data.enforceInterface(IClipboardDataPasteEvent.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IClipboardDataPasteEvent.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              SemClipData _arg0 = (SemClipData) data.readTypedObject(SemClipData.CREATOR);
              data.enforceNoDataAvail();
              onPaste(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IClipboardDataPasteEvent {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IClipboardDataPasteEvent.DESCRIPTOR;
      }

      @Override // android.sec.clipboard.IClipboardDataPasteEvent
      public void onPaste(SemClipData data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IClipboardDataPasteEvent.DESCRIPTOR);
          _data.writeTypedObject(data, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
