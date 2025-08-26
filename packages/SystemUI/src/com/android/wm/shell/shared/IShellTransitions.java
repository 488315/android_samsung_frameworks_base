package com.android.wm.shell.shared;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1;

/* loaded from: classes3.dex */
public interface IShellTransitions extends IInterface {

    public abstract class Stub extends Binder implements IShellTransitions {
        public Stub() {
            attachInterface(this, "com.android.wm.shell.shared.IShellTransitions");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.shared.IShellTransitions");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.shared.IShellTransitions");
                return true;
            }
            Object iHomeTransitionListener$Stub$Proxy = null;
            switch (i) {
                case 2:
                    TransitionFilter transitionFilter = (TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR);
                    RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "registerRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0(transitionFilter, remoteTransition, 0), false);
                    return true;
                case 3:
                    RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "unregisterRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(remoteTransition2, 0), false);
                    return true;
                case 4:
                    IBinder defaultApplyToken = SurfaceControl.Transaction.getDefaultApplyToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(defaultApplyToken);
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.wm.shell.shared.IHomeTransitionListener");
                        iHomeTransitionListener$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IHomeTransitionListener$Stub$Proxy)) ? new IHomeTransitionListener$Stub$Proxy(strongBinder) : (IHomeTransitionListener$Stub$Proxy) iInterfaceQueryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "setHomeTransitionListener", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(iHomeTransitionListener$Stub$Proxy, 3), false);
                    return true;
                case 6:
                    SurfaceControl[] surfaceControlArr = new SurfaceControl[1];
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "getHomeTaskOverlayContainer", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(surfaceControlArr, 1), true);
                    SurfaceControl surfaceControl = new SurfaceControl(surfaceControlArr[0], "Transitions.HomeOverlay");
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceControl, 1);
                    return true;
                case 7:
                    TransitionFilter transitionFilter2 = (TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR);
                    RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "registerRemoteForTakeover", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0(transitionFilter2, remoteTransition3, 1), false);
                    return true;
                case 8:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    if (strongBinder2 != null) {
                        IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.wm.shell.shared.IFocusTransitionListener");
                        iHomeTransitionListener$Stub$Proxy = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IFocusTransitionListener$Stub$Proxy)) ? new IFocusTransitionListener$Stub$Proxy(strongBinder2) : (IFocusTransitionListener$Stub$Proxy) iInterfaceQueryLocalInterface2;
                    }
                    parcel.enforceNoDataAvail();
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) this).mTransitions, "setFocusTransitionListener", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(iHomeTransitionListener$Stub$Proxy, 2), false);
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
