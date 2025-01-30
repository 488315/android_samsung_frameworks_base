package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IUsbCallback extends IInterface {
  public static final String DESCRIPTOR = "android$hardware$usb$IUsbCallback".replace('$', '.');

  public class Default implements IUsbCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void notifyContaminantEnabledStatus(String str, boolean z, int i, long j);

  void notifyEnableUsbDataStatus(String str, boolean z, int i, long j);

  void notifyEnableUsbDataWhileDockedStatus(String str, int i, long j);

  void notifyLimitPowerTransferStatus(String str, boolean z, int i, long j);

  void notifyPortStatusChange(PortStatus[] portStatusArr, int i);

  void notifyQueryPortStatus(String str, int i, long j);

  void notifyResetUsbPortStatus(String str, int i, long j);

  void notifyRoleSwitchStatus(String str, PortRole portRole, int i, long j);

  public abstract class Stub extends Binder implements IUsbCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IUsbCallback.DESCRIPTOR);
    }

    public static IUsbCallback asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(IUsbCallback.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbCallback)) {
        return (IUsbCallback) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IUsbCallback.DESCRIPTOR;
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
          switch (i) {
            case 1:
              PortStatus[] portStatusArr =
                  (PortStatus[]) parcel.createTypedArray(PortStatus.CREATOR);
              int readInt = parcel.readInt();
              parcel.enforceNoDataAvail();
              notifyPortStatusChange(portStatusArr, readInt);
              return true;
            case 2:
              String readString = parcel.readString();
              PortRole portRole = (PortRole) parcel.readTypedObject(PortRole.CREATOR);
              int readInt2 = parcel.readInt();
              long readLong = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyRoleSwitchStatus(readString, portRole, readInt2, readLong);
              return true;
            case 3:
              String readString2 = parcel.readString();
              boolean readBoolean = parcel.readBoolean();
              int readInt3 = parcel.readInt();
              long readLong2 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyEnableUsbDataStatus(readString2, readBoolean, readInt3, readLong2);
              return true;
            case 4:
              String readString3 = parcel.readString();
              int readInt4 = parcel.readInt();
              long readLong3 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyEnableUsbDataWhileDockedStatus(readString3, readInt4, readLong3);
              return true;
            case 5:
              String readString4 = parcel.readString();
              boolean readBoolean2 = parcel.readBoolean();
              int readInt5 = parcel.readInt();
              long readLong4 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyContaminantEnabledStatus(readString4, readBoolean2, readInt5, readLong4);
              return true;
            case 6:
              String readString5 = parcel.readString();
              int readInt6 = parcel.readInt();
              long readLong5 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyQueryPortStatus(readString5, readInt6, readLong5);
              return true;
            case 7:
              String readString6 = parcel.readString();
              boolean readBoolean3 = parcel.readBoolean();
              int readInt7 = parcel.readInt();
              long readLong6 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyLimitPowerTransferStatus(readString6, readBoolean3, readInt7, readLong6);
              return true;
            case 8:
              String readString7 = parcel.readString();
              int readInt8 = parcel.readInt();
              long readLong7 = parcel.readLong();
              parcel.enforceNoDataAvail();
              notifyResetUsbPortStatus(readString7, readInt8, readLong7);
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements IUsbCallback {
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
