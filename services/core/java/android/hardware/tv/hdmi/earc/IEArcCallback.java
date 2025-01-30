package android.hardware.tv.hdmi.earc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IEArcCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$tv$hdmi$earc$IEArcCallback".replace('$', '.');

  public class Default implements IEArcCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onCapabilitiesReported(byte[] bArr, int i);

  void onStateChange(byte b, int i);

  public abstract class Stub extends Binder implements IEArcCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IEArcCallback.DESCRIPTOR);
    }

    public static IEArcCallback asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IEArcCallback.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IEArcCallback)) {
        return (IEArcCallback) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IEArcCallback.DESCRIPTOR;
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
            byte readByte = parcel.readByte();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            onStateChange(readByte, readInt);
            break;
          } else if (i == 2) {
            byte[] createByteArray = parcel.createByteArray();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            onCapabilitiesReported(createByteArray, readInt2);
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IEArcCallback {
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
    }
  }
}
