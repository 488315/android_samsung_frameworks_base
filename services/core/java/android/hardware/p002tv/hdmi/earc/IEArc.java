package android.hardware.p002tv.hdmi.earc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IEArc extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$tv$hdmi$earc$IEArc".replace('$', '.');

  public class Default implements IEArc {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.p002tv.hdmi.earc.IEArc
    public byte[] getLastReportedAudioCapabilities(int i) {
      return null;
    }

    @Override // android.hardware.p002tv.hdmi.earc.IEArc
    public byte getState(int i) {
      return (byte) 0;
    }

    @Override // android.hardware.p002tv.hdmi.earc.IEArc
    public boolean isEArcEnabled() {
      return false;
    }

    @Override // android.hardware.p002tv.hdmi.earc.IEArc
    public void setCallback(IEArcCallback iEArcCallback) {}

    @Override // android.hardware.p002tv.hdmi.earc.IEArc
    public void setEArcEnabled(boolean z) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  byte[] getLastReportedAudioCapabilities(int i);

  byte getState(int i);

  boolean isEArcEnabled();

  void setCallback(IEArcCallback iEArcCallback);

  void setEArcEnabled(boolean z);

  public abstract class Stub extends Binder implements IEArc {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IEArc.DESCRIPTOR);
    }

    public static IEArc asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IEArc.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IEArc)) {
        return (IEArc) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IEArc.DESCRIPTOR;
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case 16777214:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          break;
        case 16777215:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          break;
        case 1598968902:
          parcel2.writeString(str);
          break;
        default:
          if (i == 1) {
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setEArcEnabled(readBoolean);
            parcel2.writeNoException();
            break;
          } else if (i == 2) {
            boolean isEArcEnabled = isEArcEnabled();
            parcel2.writeNoException();
            parcel2.writeBoolean(isEArcEnabled);
            break;
          } else if (i == 3) {
            IEArcCallback asInterface = IEArcCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCallback(asInterface);
            parcel2.writeNoException();
            break;
          } else if (i == 4) {
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            byte state = getState(readInt);
            parcel2.writeNoException();
            parcel2.writeByte(state);
            break;
          } else if (i == 5) {
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            byte[] lastReportedAudioCapabilities = getLastReportedAudioCapabilities(readInt2);
            parcel2.writeNoException();
            parcel2.writeByteArray(lastReportedAudioCapabilities);
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IEArc {
      public IBinder mRemote;
      public int mCachedVersion = -1;
      public String mCachedHash = "-1";

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.hardware.p002tv.hdmi.earc.IEArc
      public void setEArcEnabled(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IEArc.DESCRIPTOR);
          obtain.writeBoolean(z);
          if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
            throw new RemoteException("Method setEArcEnabled is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.earc.IEArc
      public boolean isEArcEnabled() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IEArc.DESCRIPTOR);
          if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
            throw new RemoteException("Method isEArcEnabled is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.earc.IEArc
      public void setCallback(IEArcCallback iEArcCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IEArc.DESCRIPTOR);
          obtain.writeStrongInterface(iEArcCallback);
          if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
            throw new RemoteException("Method setCallback is unimplemented.");
          }
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.earc.IEArc
      public byte getState(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IEArc.DESCRIPTOR);
          obtain.writeInt(i);
          if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
            throw new RemoteException("Method getState is unimplemented.");
          }
          obtain2.readException();
          return obtain2.readByte();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.hardware.p002tv.hdmi.earc.IEArc
      public byte[] getLastReportedAudioCapabilities(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IEArc.DESCRIPTOR);
          obtain.writeInt(i);
          if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
            throw new RemoteException("Method getLastReportedAudioCapabilities is unimplemented.");
          }
          obtain2.readException();
          return obtain2.createByteArray();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }
    }
  }
}
