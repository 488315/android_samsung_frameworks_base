package com.android.p038wm.shell.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface IDesktopMode extends IInterface {
    int getVisibleTaskCount(int i);

    void showDesktopApps(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements IDesktopMode {
        public Stub() {
            attachInterface(this, "com.android.wm.shell.desktopmode.IDesktopMode");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.desktopmode.IDesktopMode");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.desktopmode.IDesktopMode");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                showDesktopApps(readInt);
                parcel2.writeNoException();
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int visibleTaskCount = getVisibleTaskCount(readInt2);
                parcel2.writeNoException();
                parcel2.writeInt(visibleTaskCount);
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
