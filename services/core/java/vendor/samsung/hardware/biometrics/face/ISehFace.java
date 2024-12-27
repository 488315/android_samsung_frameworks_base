package vendor.samsung.hardware.biometrics.face;

import android.hardware.biometrics.face.ISessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.biometrics.face.ISehSession;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ISehFace extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$biometrics$face$ISehFace".replace('$', '.');

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements ISehFace {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements ISehFace {
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final ISehSession createSession(int i, int i2, ISessionCallback iSessionCallback) {
                ISehSession iSehSession;
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehFace.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSessionCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createSession is unimplemented.");
                    }
                    obtain2.readException();
                    IBinder readStrongBinder = obtain2.readStrongBinder();
                    int i3 = ISehSession.Stub.$r8$clinit;
                    if (readStrongBinder == null) {
                        iSehSession = null;
                    } else {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface(ISehSession.DESCRIPTOR);
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof ISehSession)) {
                            ISehSession.Stub.Proxy proxy = new ISehSession.Stub.Proxy();
                            proxy.mRemote = readStrongBinder;
                            iSehSession = proxy;
                        } else {
                            iSehSession = (ISehSession) queryLocalInterface;
                        }
                    }
                    return iSehSession;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(this.mRemote);
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehFace.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }
        }
    }
}
