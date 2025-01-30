package android.hardware.broadcastradio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IAnnouncementListener extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$broadcastradio$IAnnouncementListener".replace('$', '.');

  public class Default implements IAnnouncementListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onListUpdated(Announcement[] announcementArr);

  public abstract class Stub extends Binder implements IAnnouncementListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      markVintfStability();
      attachInterface(this, IAnnouncementListener.DESCRIPTOR);
    }

    public static IAnnouncementListener asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface(IAnnouncementListener.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IAnnouncementListener)) {
        return (IAnnouncementListener) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IAnnouncementListener.DESCRIPTOR;
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
            Announcement[] announcementArr =
                (Announcement[]) parcel.createTypedArray(Announcement.CREATOR);
            parcel.enforceNoDataAvail();
            onListUpdated(announcementArr);
            return true;
          }
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IAnnouncementListener {
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
