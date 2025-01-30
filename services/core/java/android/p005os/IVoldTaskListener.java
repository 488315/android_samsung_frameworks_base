package android.p005os;

/* loaded from: classes.dex */
public interface IVoldTaskListener extends IInterface {

  public class Default implements IVoldTaskListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.p005os.IVoldTaskListener
    public void onFinished(int i, PersistableBundle persistableBundle) {}

    @Override // android.p005os.IVoldTaskListener
    public void onStatus(int i, PersistableBundle persistableBundle) {}
  }

  void onFinished(int i, PersistableBundle persistableBundle);

  void onStatus(int i, PersistableBundle persistableBundle);

  public abstract class Stub extends Binder implements IVoldTaskListener {
    public static final String DESCRIPTOR = "android.os.IVoldTaskListener";
    public static final int TRANSACTION_onFinished = 2;
    public static final int TRANSACTION_onStatus = 1;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IVoldTaskListener asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IVoldTaskListener)) {
        return (IVoldTaskListener) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(DESCRIPTOR);
      }
      if (i == 1598968902) {
        parcel2.writeString(DESCRIPTOR);
        return true;
      }
      if (i == 1) {
        int readInt = parcel.readInt();
        PersistableBundle persistableBundle =
            (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        parcel.enforceNoDataAvail();
        onStatus(readInt, persistableBundle);
      } else if (i == 2) {
        int readInt2 = parcel.readInt();
        PersistableBundle persistableBundle2 =
            (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        parcel.enforceNoDataAvail();
        onFinished(readInt2, persistableBundle2);
      } else {
        return super.onTransact(i, parcel, parcel2, i2);
      }
      return true;
    }

    public class Proxy implements IVoldTaskListener {
      public IBinder mRemote;

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.p005os.IVoldTaskListener
      public void onStatus(int i, PersistableBundle persistableBundle) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeTypedObject(persistableBundle, 0);
          this.mRemote.transact(1, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVoldTaskListener
      public void onFinished(int i, PersistableBundle persistableBundle) {
        Parcel obtain = Parcel.obtain(asBinder());
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeTypedObject(persistableBundle, 0);
          this.mRemote.transact(2, obtain, null, 1);
        } finally {
          obtain.recycle();
        }
      }
    }
  }
}
