package com.android.wm.shell.transition;

import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.shared.IFocusTransitionListener$Stub$Proxy;
import com.android.wm.shell.shared.IHomeTransitionListener$Stub$Proxy;
import com.android.wm.shell.transition.Transitions;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final int i = 0;
        int i2 = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i2) {
            case 0:
                int i3 = Transitions.IShellTransitionsImpl.$r8$clinit;
                ((Transitions) obj).mRemoteTransitionHandler.removeFiltered((RemoteTransition) obj2);
                break;
            case 1:
                int i4 = Transitions.IShellTransitionsImpl.$r8$clinit;
                ((SurfaceControl[]) obj2)[0] = ((Transitions) obj).mOrganizer.mHomeTaskOverlayContainer;
                break;
            case 2:
                IFocusTransitionListener$Stub$Proxy iFocusTransitionListener$Stub$Proxy = (IFocusTransitionListener$Stub$Proxy) obj2;
                int i5 = Transitions.IShellTransitionsImpl.$r8$clinit;
                FocusTransitionObserver focusTransitionObserver = ((Transitions) obj).mFocusTransitionObserver;
                focusTransitionObserver.mRemoteListener = iFocusTransitionListener$Stub$Proxy;
                if (iFocusTransitionListener$Stub$Proxy != null) {
                    try {
                        iFocusTransitionListener$Stub$Proxy.onFocusedDisplayChanged(focusTransitionObserver.mFocusedDisplayId);
                        break;
                    } catch (RemoteException e) {
                        Slog.w("FocusTransitionObserver", "Failed call notifyFocusedDisplayChangedToRemote", e);
                        return;
                    }
                }
                break;
            default:
                IHomeTransitionListener$Stub$Proxy iHomeTransitionListener$Stub$Proxy = (IHomeTransitionListener$Stub$Proxy) obj2;
                final Transitions transitions = (Transitions) obj;
                int i6 = Transitions.IShellTransitionsImpl.$r8$clinit;
                final HomeTransitionObserver homeTransitionObserver = transitions.mHomeTransitionObserver;
                if (homeTransitionObserver.mListener == null) {
                    final int i7 = 1;
                    homeTransitionObserver.mListener = new SingleInstanceRemoteListener(homeTransitionObserver, new Consumer() { // from class: com.android.wm.shell.transition.HomeTransitionObserver$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj3) {
                            switch (i) {
                                case 0:
                                    HomeTransitionObserver homeTransitionObserver2 = homeTransitionObserver;
                                    Transitions transitions2 = transitions;
                                    homeTransitionObserver2.getClass();
                                    transitions2.registerObserver(homeTransitionObserver2);
                                    break;
                                default:
                                    HomeTransitionObserver homeTransitionObserver3 = homeTransitionObserver;
                                    Transitions transitions3 = transitions;
                                    homeTransitionObserver3.getClass();
                                    transitions3.mObservers.remove(homeTransitionObserver3);
                                    break;
                            }
                        }
                    }, new Consumer() { // from class: com.android.wm.shell.transition.HomeTransitionObserver$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj3) {
                            switch (i7) {
                                case 0:
                                    HomeTransitionObserver homeTransitionObserver2 = homeTransitionObserver;
                                    Transitions transitions2 = transitions;
                                    homeTransitionObserver2.getClass();
                                    transitions2.registerObserver(homeTransitionObserver2);
                                    break;
                                default:
                                    HomeTransitionObserver homeTransitionObserver3 = homeTransitionObserver;
                                    Transitions transitions3 = transitions;
                                    homeTransitionObserver3.getClass();
                                    transitions3.mObservers.remove(homeTransitionObserver3);
                                    break;
                            }
                        }
                    });
                }
                if (iHomeTransitionListener$Stub$Proxy == null) {
                    homeTransitionObserver.mListener.unregister();
                    break;
                } else {
                    homeTransitionObserver.mListener.register(iHomeTransitionListener$Stub$Proxy);
                    break;
                }
        }
    }
}
