package android.hardware.broadcastradio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface ITunerCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$broadcastradio$ITunerCallback".replace('$', '.');

  public class Default implements ITunerCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onAntennaStateChange(boolean z);

  void onConfigFlagUpdated(int i, boolean z);

  void onCurrentProgramInfoChanged(ProgramInfo programInfo);

  void onParametersUpdated(VendorKeyValue[] vendorKeyValueArr);

  void onProgramListUpdated(ProgramListChunk programListChunk);

  void onTuneFailed(int i, ProgramSelector programSelector);

  public abstract class Stub extends Binder implements ITunerCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, ITunerCallback.DESCRIPTOR);
    }

    public static ITunerCallback asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(ITunerCallback.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof ITunerCallback)) {
        return (ITunerCallback) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = ITunerCallback.DESCRIPTOR;
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
              int readInt = parcel.readInt();
              ProgramSelector programSelector =
                  (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
              parcel.enforceNoDataAvail();
              onTuneFailed(readInt, programSelector);
              return true;
            case 2:
              ProgramInfo programInfo = (ProgramInfo) parcel.readTypedObject(ProgramInfo.CREATOR);
              parcel.enforceNoDataAvail();
              onCurrentProgramInfoChanged(programInfo);
              return true;
            case 3:
              ProgramListChunk programListChunk =
                  (ProgramListChunk) parcel.readTypedObject(ProgramListChunk.CREATOR);
              parcel.enforceNoDataAvail();
              onProgramListUpdated(programListChunk);
              return true;
            case 4:
              boolean readBoolean = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              onAntennaStateChange(readBoolean);
              return true;
            case 5:
              int readInt2 = parcel.readInt();
              boolean readBoolean2 = parcel.readBoolean();
              parcel.enforceNoDataAvail();
              onConfigFlagUpdated(readInt2, readBoolean2);
              return true;
            case 6:
              VendorKeyValue[] vendorKeyValueArr =
                  (VendorKeyValue[]) parcel.createTypedArray(VendorKeyValue.CREATOR);
              parcel.enforceNoDataAvail();
              onParametersUpdated(vendorKeyValueArr);
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements ITunerCallback {
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
