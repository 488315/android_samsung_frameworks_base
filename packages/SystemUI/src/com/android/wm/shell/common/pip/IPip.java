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
                    int i3 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect rectStartSwipePipToHome = startSwipePipToHome(runningTaskInfo, i3, rect);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rectStartSwipePipToHome, 1);
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Parcelable.Creator creator = Rect.CREATOR;
                    Rect rect2 = (Rect) parcel.readTypedObject(creator);
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    Rect rect3 = (Rect) parcel.readTypedObject(creator);
                    Rect rect4 = (Rect) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    stopSwipePipToHome(i4, componentName, rect2, surfaceControl, rect3, rect4);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    abortSwipePipToHome(i5, componentName2);
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder == null) {
                        iPipAnimationListener$Stub$Proxy = null;
                    } else {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.wm.shell.common.pip.IPipAnimationListener");
                        iPipAnimationListener$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPipAnimationListener$Stub$Proxy)) ? new IPipAnimationListener$Stub$Proxy(strongBinder) : (IPipAnimationListener$Stub$Proxy) iInterfaceQueryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    setPipAnimationListener(iPipAnimationListener$Stub$Proxy);
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setShelfHeight(i6, z);
                    return true;
                case 7:
                    setPipAnimationTypeToAlpha();
                    return true;
                case 8:
                    boolean z2 = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLauncherKeepClearAreaHeight(i7, z2);
                    return true;
                case 9:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLauncherAppIconSize(i8);
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
