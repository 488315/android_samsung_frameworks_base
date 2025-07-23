package com.samsung.android.app.smartcapture.screenshot.lib;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IScreenshotService extends IInterface {
    boolean canScrollCapture();

    boolean cancelNotification();

    boolean isUiActivated();

    boolean onGlobalScreenshotFinished(long j, String str, Bundle bundle);

    boolean onGlobalScreenshotStarted(long j, String str, Bundle bundle);

    boolean registerNotification();

    boolean setCustomSettingFilePath();

    boolean startCapture();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IScreenshotService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IScreenshotService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService
            public final boolean onGlobalScreenshotFinished(long j, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(1);
                    bundle.writeToParcel(obtain, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService
            public final boolean onGlobalScreenshotStarted(long j, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(1);
                    bundle.writeToParcel(obtain, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.readStrongBinder();
                    boolean canScrollCapture = canScrollCapture();
                    parcel2.writeNoException();
                    parcel2.writeInt(canScrollCapture ? 1 : 0);
                    return true;
                case 2:
                    boolean isUiActivated = isUiActivated();
                    parcel2.writeNoException();
                    parcel2.writeInt(isUiActivated ? 1 : 0);
                    return true;
                case 3:
                    parcel.readString();
                    boolean startCapture = startCapture();
                    parcel2.writeNoException();
                    parcel2.writeInt(startCapture ? 1 : 0);
                    return true;
                case 4:
                    boolean onGlobalScreenshotStarted = onGlobalScreenshotStarted(parcel.readLong(), parcel.readString(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(onGlobalScreenshotStarted ? 1 : 0);
                    return true;
                case 5:
                    boolean onGlobalScreenshotFinished = onGlobalScreenshotFinished(parcel.readLong(), parcel.readString(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(onGlobalScreenshotFinished ? 1 : 0);
                    return true;
                case 6:
                    parcel.readString();
                    boolean customSettingFilePath = setCustomSettingFilePath();
                    parcel2.writeNoException();
                    parcel2.writeInt(customSettingFilePath ? 1 : 0);
                    return true;
                case 7:
                    parcel.readInt();
                    boolean registerNotification = registerNotification();
                    parcel2.writeNoException();
                    parcel2.writeInt(registerNotification ? 1 : 0);
                    return true;
                case 8:
                    parcel.readInt();
                    boolean cancelNotification = cancelNotification();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelNotification ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
