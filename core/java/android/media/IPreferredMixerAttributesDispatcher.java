package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IPreferredMixerAttributesDispatcher extends IInterface {
  public static final String DESCRIPTOR = "android.media.IPreferredMixerAttributesDispatcher";

  void dispatchPrefMixerAttributesChanged(
      AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes)
      throws RemoteException;

  public static class Default implements IPreferredMixerAttributesDispatcher {
    @Override // android.media.IPreferredMixerAttributesDispatcher
    public void dispatchPrefMixerAttributesChanged(
        AudioAttributes attributes, int deviceId, AudioMixerAttributes mixerAttributes)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IPreferredMixerAttributesDispatcher {
    static final int TRANSACTION_dispatchPrefMixerAttributesChanged = 1;

    public Stub() {
      attachInterface(this, IPreferredMixerAttributesDispatcher.DESCRIPTOR);
    }

    public static IPreferredMixerAttributesDispatcher asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
      if (iin != null && (iin instanceof IPreferredMixerAttributesDispatcher)) {
        return (IPreferredMixerAttributesDispatcher) iin;
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
          return "dispatchPrefMixerAttributesChanged";
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
        data.enforceInterface(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              AudioAttributes _arg0 =
                  (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
              int _arg1 = data.readInt();
              AudioMixerAttributes _arg2 =
                  (AudioMixerAttributes) data.readTypedObject(AudioMixerAttributes.CREATOR);
              data.enforceNoDataAvail();
              dispatchPrefMixerAttributesChanged(_arg0, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IPreferredMixerAttributesDispatcher {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IPreferredMixerAttributesDispatcher.DESCRIPTOR;
      }

      @Override // android.media.IPreferredMixerAttributesDispatcher
      public void dispatchPrefMixerAttributesChanged(
          AudioAttributes attributes, int deviceId, AudioMixerAttributes mixerAttributes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
          _data.writeTypedObject(attributes, 0);
          _data.writeInt(deviceId);
          _data.writeTypedObject(mixerAttributes, 0);
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
