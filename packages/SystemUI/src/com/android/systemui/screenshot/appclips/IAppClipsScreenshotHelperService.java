package com.android.systemui.screenshot.appclips;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.screenshot.appclips.AppClipsScreenshotHelperService;

/* loaded from: classes2.dex */
public interface IAppClipsScreenshotHelperService extends IInterface {
    ScreenshotHardwareBufferInternal takeScreenshot(int i);

    public abstract class Stub extends Binder implements IAppClipsScreenshotHelperService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IAppClipsScreenshotHelperService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService
            public final ScreenshotHardwareBufferInternal takeScreenshot(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ScreenshotHardwareBufferInternal) parcelObtain2.readTypedObject(ScreenshotHardwareBufferInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            ScreenshotHardwareBufferInternal screenshotHardwareBufferInternalTakeScreenshot = ((AppClipsScreenshotHelperService.AnonymousClass1) this).takeScreenshot(i3);
            parcel2.writeNoException();
            parcel2.writeTypedObject(screenshotHardwareBufferInternalTakeScreenshot, 1);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
