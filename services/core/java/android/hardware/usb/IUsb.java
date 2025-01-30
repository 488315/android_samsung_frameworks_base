package android.hardware.usb;

import android.hardware.usb.IUsbCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUsb extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$usb$IUsb".replace('$', '.');

    public class Default implements IUsb {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.IUsb
        public void enableContaminantPresenceDetection(String str, boolean z, long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void enableUsbData(String str, boolean z, long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void enableUsbDataWhileDocked(String str, long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void limitPowerTransfer(String str, boolean z, long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void queryPortStatus(long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void resetUsbPort(String str, long j) {
        }

        @Override // android.hardware.usb.IUsb
        public void setCallback(IUsbCallback iUsbCallback) {
        }

        @Override // android.hardware.usb.IUsb
        public void switchRole(String str, PortRole portRole, long j) {
        }
    }

    void enableContaminantPresenceDetection(String str, boolean z, long j);

    void enableUsbData(String str, boolean z, long j);

    void enableUsbDataWhileDocked(String str, long j);

    String getInterfaceHash();

    int getInterfaceVersion();

    void limitPowerTransfer(String str, boolean z, long j);

    void queryPortStatus(long j);

    void resetUsbPort(String str, long j);

    void setCallback(IUsbCallback iUsbCallback);

    void switchRole(String str, PortRole portRole, long j);

    public abstract class Stub extends Binder implements IUsb {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, IUsb.DESCRIPTOR);
        }

        public static IUsb asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUsb.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsb)) {
                return (IUsb) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IUsb.DESCRIPTOR;
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
                            String readString = parcel.readString();
                            boolean readBoolean = parcel.readBoolean();
                            long readLong = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            enableContaminantPresenceDetection(readString, readBoolean, readLong);
                            return true;
                        case 2:
                            String readString2 = parcel.readString();
                            boolean readBoolean2 = parcel.readBoolean();
                            long readLong2 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            enableUsbData(readString2, readBoolean2, readLong2);
                            return true;
                        case 3:
                            String readString3 = parcel.readString();
                            long readLong3 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            enableUsbDataWhileDocked(readString3, readLong3);
                            return true;
                        case 4:
                            long readLong4 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            queryPortStatus(readLong4);
                            return true;
                        case 5:
                            IUsbCallback asInterface = IUsbCallback.Stub.asInterface(parcel.readStrongBinder());
                            parcel.enforceNoDataAvail();
                            setCallback(asInterface);
                            return true;
                        case 6:
                            String readString4 = parcel.readString();
                            PortRole portRole = (PortRole) parcel.readTypedObject(PortRole.CREATOR);
                            long readLong5 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            switchRole(readString4, portRole, readLong5);
                            return true;
                        case 7:
                            String readString5 = parcel.readString();
                            boolean readBoolean3 = parcel.readBoolean();
                            long readLong6 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            limitPowerTransfer(readString5, readBoolean3, readLong6);
                            return true;
                        case 8:
                            String readString6 = parcel.readString();
                            long readLong7 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            resetUsbPort(readString6, readLong7);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements IUsb {
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

            @Override // android.hardware.usb.IUsb
            public void enableContaminantPresenceDetection(String str, boolean z, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableContaminantPresenceDetection is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void enableUsbData(String str, boolean z, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUsbData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void enableUsbDataWhileDocked(String str, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUsbDataWhileDocked is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void queryPortStatus(long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method queryPortStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void setCallback(IUsbCallback iUsbCallback) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbCallback);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void switchRole(String str, PortRole portRole, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(portRole, 0);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method switchRole is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void limitPowerTransfer(String str, boolean z, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method limitPowerTransfer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void resetUsbPort(String str, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method resetUsbPort is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
