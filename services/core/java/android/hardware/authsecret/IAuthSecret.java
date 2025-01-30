package android.hardware.authsecret;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAuthSecret extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$authsecret$IAuthSecret".replace('$', '.');

  public class Default implements IAuthSecret {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public void setPrimaryUserCredential(byte[] bArr) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void setPrimaryUserCredential(byte[] bArr);

  public abstract class Stub extends Binder implements IAuthSecret {
    public static String getDefaultTransactionName(int i) {
      if (i == 1) {
        return "setPrimaryUserCredential";
      }
      switch (i) {
        case 16777214:
          return "getInterfaceHash";
        case 16777215:
          return "getInterfaceVersion";
        default:
          return null;
      }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public int getMaxTransactionId() {
      return 16777214;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IAuthSecret.DESCRIPTOR);
    }

    public static IAuthSecret asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthSecret.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IAuthSecret)) {
        return (IAuthSecret) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    public String getTransactionName(int i) {
      return getDefaultTransactionName(i);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IAuthSecret.DESCRIPTOR;
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case 16777214:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          return true;
        case 16777215:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          return true;
        case 1598968902:
          parcel2.writeString(str);
          return true;
        default:
          if (i == 1) {
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            setPrimaryUserCredential(createByteArray);
            return true;
          }
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IAuthSecret {
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

      @Override // android.hardware.authsecret.IAuthSecret
      public void setPrimaryUserCredential(byte[] bArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(IAuthSecret.DESCRIPTOR);
          obtain.writeByteArray(bArr);
          if (this.mRemote.transact(1, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method setPrimaryUserCredential is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }
    }
  }
}
