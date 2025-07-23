package com.android.wm.shell.back;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;
import android.view.IRemoteAnimationRunner;
import android.window.IOnBackInvokedCallback;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda10;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IBackAnimation$Stub extends Binder implements IInterface {
    public IBackAnimation$Stub() {
        attachInterface(this, "com.android.wm.shell.back.IBackAnimation");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.back.IBackAnimation");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.back.IBackAnimation");
            return true;
        }
        if (i != 1) {
            if (i == 2) {
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((BackAnimationController.IBackAnimationImpl) this).mController, "clearBackToLauncherCallback", new BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda1(), false);
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            final AppearanceRegion appearanceRegion = (AppearanceRegion) parcel.readTypedObject(AppearanceRegion.CREATOR);
            parcel.enforceNoDataAvail();
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((BackAnimationController.IBackAnimationImpl) this).mController, "useLauncherSysBarFlags", new Consumer() { // from class: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppearanceRegion appearanceRegion2 = appearanceRegion;
                    int i3 = BackAnimationController.IBackAnimationImpl.$r8$clinit;
                    EdgeBackGestureHandler$$ExternalSyntheticLambda10 edgeBackGestureHandler$$ExternalSyntheticLambda10 = ((BackAnimationController) obj).mCustomizer;
                    if (edgeBackGestureHandler$$ExternalSyntheticLambda10 != null) {
                        edgeBackGestureHandler$$ExternalSyntheticLambda10.customizeStatusBarAppearance(appearanceRegion2);
                    }
                }
            }, false);
            parcel2.writeNoException();
            return true;
        }
        final IOnBackInvokedCallback asInterface = IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
        final IRemoteAnimationRunner asInterface2 = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
        parcel.enforceNoDataAvail();
        final BackAnimationController.IBackAnimationImpl iBackAnimationImpl = (BackAnimationController.IBackAnimationImpl) this;
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
            Log.i("ShellBackPreview", "setBackToLauncherCallback, callback=" + asInterface + ", runner=" + asInterface2);
        }
        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iBackAnimationImpl.mController, "setBackToLauncherCallback", new Consumer() { // from class: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BackAnimationController.IBackAnimationImpl iBackAnimationImpl2 = BackAnimationController.IBackAnimationImpl.this;
                IOnBackInvokedCallback iOnBackInvokedCallback = asInterface;
                IRemoteAnimationRunner iRemoteAnimationRunner = asInterface2;
                BackAnimationController backAnimationController = (BackAnimationController) obj;
                int i3 = BackAnimationController.IBackAnimationImpl.$r8$clinit;
                iBackAnimationImpl2.getClass();
                BackAnimationRunner backAnimationRunner = new BackAnimationRunner(iOnBackInvokedCallback, iRemoteAnimationRunner, backAnimationController.mContext, 86, BackAnimationController.this.mHandler);
                ShellBackAnimationRegistry shellBackAnimationRegistry = backAnimationController.mShellBackAnimationRegistry;
                shellBackAnimationRegistry.mAnimationDefinition.set(1, backAnimationRunner);
                shellBackAnimationRegistry.updateSupportedAnimators();
            }
        }, false);
        parcel2.writeNoException();
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
