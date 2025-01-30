package android.hardware.power;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IPowerHintSession extends IInterface {
  public static final String DESCRIPTOR =
      "android$hardware$power$IPowerHintSession".replace('$', '.');

  public class Default implements IPowerHintSession {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  void close();

  String getInterfaceHash();

  int getInterfaceVersion();

  void pause();

  void reportActualWorkDuration(WorkDuration[] workDurationArr);

  void resume();

  void sendHint(int i);

  void setThreads(int[] iArr);

  void updateTargetWorkDuration(long j);

  public abstract class Stub extends Binder implements IPowerHintSession {
    public static String getDefaultTransactionName(int i) {
      switch (i) {
        case 1:
          return "updateTargetWorkDuration";
        case 2:
          return "reportActualWorkDuration";
        case 3:
          return "pause";
        case 4:
          return "resume";
        case 5:
          return "close";
        case 6:
          return "sendHint";
        case 7:
          return "setThreads";
        default:
          switch (i) {
            case 16777214:
              return "getInterfaceHash";
            case 16777215:
              return "getInterfaceVersion";
            default:
              return null;
          }
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
      attachInterface(this, IPowerHintSession.DESCRIPTOR);
    }

    public String getTransactionName(int i) {
      return getDefaultTransactionName(i);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IPowerHintSession.DESCRIPTOR;
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
              long readLong = parcel.readLong();
              parcel.enforceNoDataAvail();
              updateTargetWorkDuration(readLong);
              return true;
            case 2:
              WorkDuration[] workDurationArr =
                  (WorkDuration[]) parcel.createTypedArray(WorkDuration.CREATOR);
              parcel.enforceNoDataAvail();
              reportActualWorkDuration(workDurationArr);
              return true;
            case 3:
              pause();
              return true;
            case 4:
              resume();
              return true;
            case 5:
              close();
              return true;
            case 6:
              int readInt = parcel.readInt();
              parcel.enforceNoDataAvail();
              sendHint(readInt);
              return true;
            case 7:
              int[] createIntArray = parcel.createIntArray();
              parcel.enforceNoDataAvail();
              setThreads(createIntArray);
              parcel2.writeNoException();
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements IPowerHintSession {
      public IBinder mRemote;

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }
    }
  }
}
