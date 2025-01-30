package android.frameworks.location.altitude;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IAltitudeService extends IInterface {
  public static final String DESCRIPTOR =
      "android$frameworks$location$altitude$IAltitudeService".replace('$', '.');

  public class Default implements IAltitudeService {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  AddMslAltitudeToLocationResponse addMslAltitudeToLocation(
      AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest);

  String getInterfaceHash();

  int getInterfaceVersion();

  public abstract class Stub extends Binder implements IAltitudeService {
    public static String getDefaultTransactionName(int i) {
      if (i == 1) {
        return "addMslAltitudeToLocation";
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
      attachInterface(this, IAltitudeService.DESCRIPTOR);
    }

    public String getTransactionName(int i) {
      return getDefaultTransactionName(i);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IAltitudeService.DESCRIPTOR;
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
            AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest =
                (AddMslAltitudeToLocationRequest)
                    parcel.readTypedObject(AddMslAltitudeToLocationRequest.CREATOR);
            parcel.enforceNoDataAvail();
            AddMslAltitudeToLocationResponse addMslAltitudeToLocation =
                addMslAltitudeToLocation(addMslAltitudeToLocationRequest);
            parcel2.writeNoException();
            parcel2.writeTypedObject(addMslAltitudeToLocation, 1);
            return true;
          }
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IAltitudeService {
      public IBinder mRemote;

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }
    }
  }
}
