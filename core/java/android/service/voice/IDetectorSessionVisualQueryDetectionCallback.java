package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDetectorSessionVisualQueryDetectionCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.service.voice.IDetectorSessionVisualQueryDetectionCallback";

  void onAttentionGained() throws RemoteException;

  void onAttentionLost() throws RemoteException;

  void onQueryDetected(String str) throws RemoteException;

  void onQueryFinished() throws RemoteException;

  void onQueryRejected() throws RemoteException;

  public static class Default implements IDetectorSessionVisualQueryDetectionCallback {
    @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
    public void onAttentionGained() throws RemoteException {}

    @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
    public void onAttentionLost() throws RemoteException {}

    @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
    public void onQueryDetected(String partialQuery) throws RemoteException {}

    @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
    public void onQueryFinished() throws RemoteException {}

    @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
    public void onQueryRejected() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder
      implements IDetectorSessionVisualQueryDetectionCallback {
    static final int TRANSACTION_onAttentionGained = 1;
    static final int TRANSACTION_onAttentionLost = 2;
    static final int TRANSACTION_onQueryDetected = 3;
    static final int TRANSACTION_onQueryFinished = 4;
    static final int TRANSACTION_onQueryRejected = 5;

    public Stub() {
      attachInterface(this, IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
    }

    public static IDetectorSessionVisualQueryDetectionCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin =
          obj.queryLocalInterface(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IDetectorSessionVisualQueryDetectionCallback)) {
        return (IDetectorSessionVisualQueryDetectionCallback) iin;
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
          return "onAttentionGained";
        case 2:
          return "onAttentionLost";
        case 3:
          return "onQueryDetected";
        case 4:
          return "onQueryFinished";
        case 5:
          return "onQueryRejected";
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
        data.enforceInterface(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onAttentionGained();
              return true;
            case 2:
              onAttentionLost();
              return true;
            case 3:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              onQueryDetected(_arg0);
              return true;
            case 4:
              onQueryFinished();
              return true;
            case 5:
              onQueryRejected();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDetectorSessionVisualQueryDetectionCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR;
      }

      @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
      public void onAttentionGained() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
      public void onAttentionLost() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
      public void onQueryDetected(String partialQuery) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          _data.writeString(partialQuery);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
      public void onQueryFinished() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
      public void onQueryRejected() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
