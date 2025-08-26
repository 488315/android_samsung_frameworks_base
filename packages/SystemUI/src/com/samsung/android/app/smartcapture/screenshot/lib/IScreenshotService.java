package com.samsung.android.app.smartcapture.screenshot.lib;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

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

    public abstract class Stub extends Binder implements IScreenshotService {
        public static final /* synthetic */ int $r8$clinit = 0;

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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(1);
                    bundle.writeToParcel(parcelObtain, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService
            public final boolean onGlobalScreenshotStarted(long j, String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(1);
                    bundle.writeToParcel(parcelObtain, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                    boolean zCanScrollCapture = canScrollCapture();
                    parcel2.writeNoException();
                    parcel2.writeInt(zCanScrollCapture ? 1 : 0);
                    return true;
                case 2:
                    boolean zIsUiActivated = isUiActivated();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsUiActivated ? 1 : 0);
                    return true;
                case 3:
                    parcel.readString();
                    boolean zStartCapture = startCapture();
                    parcel2.writeNoException();
                    parcel2.writeInt(zStartCapture ? 1 : 0);
                    return true;
                case 4:
                    boolean zOnGlobalScreenshotStarted = onGlobalScreenshotStarted(parcel.readLong(), parcel.readString(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(zOnGlobalScreenshotStarted ? 1 : 0);
                    return true;
                case 5:
                    boolean zOnGlobalScreenshotFinished = onGlobalScreenshotFinished(parcel.readLong(), parcel.readString(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
                    parcel2.writeNoException();
                    parcel2.writeInt(zOnGlobalScreenshotFinished ? 1 : 0);
                    return true;
                case 6:
                    parcel.readString();
                    boolean customSettingFilePath = setCustomSettingFilePath();
                    parcel2.writeNoException();
                    parcel2.writeInt(customSettingFilePath ? 1 : 0);
                    return true;
                case 7:
                    parcel.readInt();
                    boolean zRegisterNotification = registerNotification();
                    parcel2.writeNoException();
                    parcel2.writeInt(zRegisterNotification ? 1 : 0);
                    return true;
                case 8:
                    parcel.readInt();
                    boolean zCancelNotification = cancelNotification();
                    parcel2.writeNoException();
                    parcel2.writeInt(zCancelNotification ? 1 : 0);
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
