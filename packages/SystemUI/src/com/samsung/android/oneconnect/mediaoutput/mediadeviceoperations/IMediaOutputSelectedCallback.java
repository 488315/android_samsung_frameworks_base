package com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IMediaOutputSelectedCallback extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMediaOutputSelectedCallback {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IMediaOutputSelectedCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            String readString = parcel.readString();
            parcel.readString();
            parcel.readInt();
            ((DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1) this).onMediaOutputSelected(readString);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
