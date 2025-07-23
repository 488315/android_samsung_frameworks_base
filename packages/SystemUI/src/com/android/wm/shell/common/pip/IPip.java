package com.android.wm.shell.common.pip;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface IPip extends IInterface {
    void abortSwipePipToHome(int i, ComponentName componentName);

    void setLauncherAppIconSize(int i);

    void setLauncherKeepClearAreaHeight(int i, boolean z);

    void setPipAnimationListener(IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy);

    void setPipAnimationTypeToAlpha();

    void setShelfHeight(int i, boolean z);

    Rect startSwipePipToHome(ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect);

    void stopSwipePipToHome(int i, ComponentName componentName, Rect rect, SurfaceControl surfaceControl, Rect rect2, Rect rect3);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IPip {
        public Stub() {
            attachInterface(this, "com.android.wm.shell.common.pip.IPip");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.common.pip.IPip");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.common.pip.IPip");
                return true;
            }
            switch (i) {
                case 2:
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect startSwipePipToHome = startSwipePipToHome(runningTaskInfo, readInt, rect);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startSwipePipToHome, 1);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Parcelable.Creator creator = Rect.CREATOR;
                    Rect rect2 = (Rect) parcel.readTypedObject(creator);
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    Rect rect3 = (Rect) parcel.readTypedObject(creator);
                    Rect rect4 = (Rect) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    stopSwipePipToHome(readInt2, componentName, rect2, surfaceControl, rect3, rect4);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    abortSwipePipToHome(readInt3, componentName2);
                    return true;
                case 5:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder == null) {
                        iPipAnimationListener$Stub$Proxy = null;
                    } else {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.common.pip.IPipAnimationListener");
                        iPipAnimationListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IPipAnimationListener$Stub$Proxy)) ? new IPipAnimationListener$Stub$Proxy(readStrongBinder) : (IPipAnimationListener$Stub$Proxy) queryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    setPipAnimationListener(iPipAnimationListener$Stub$Proxy);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setShelfHeight(readInt4, readBoolean);
                    return true;
                case 7:
                    setPipAnimationTypeToAlpha();
                    return true;
                case 8:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLauncherKeepClearAreaHeight(readInt5, readBoolean2);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLauncherAppIconSize(readInt6);
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
