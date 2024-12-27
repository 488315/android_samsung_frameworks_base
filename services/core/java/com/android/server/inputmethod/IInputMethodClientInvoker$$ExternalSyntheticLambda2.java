package com.android.server.inputmethod;

import android.os.RemoteException;

public final /* synthetic */ class IInputMethodClientInvoker$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IInputMethodClientInvoker f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ IInputMethodClientInvoker$$ExternalSyntheticLambda2(
            IInputMethodClientInvoker iInputMethodClientInvoker, boolean z, boolean z2, int i) {
        this.$r8$classId = i;
        this.f$0 = iInputMethodClientInvoker;
        this.f$1 = z;
        this.f$2 = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IInputMethodClientInvoker iInputMethodClientInvoker = this.f$0;
                boolean z = this.f$1;
                boolean z2 = this.f$2;
                iInputMethodClientInvoker.getClass();
                try {
                    iInputMethodClientInvoker.mTarget.setInteractive(z, z2);
                    break;
                } catch (RemoteException e) {
                    IInputMethodClientInvoker.logRemoteException(e);
                    return;
                }
            default:
                IInputMethodClientInvoker iInputMethodClientInvoker2 = this.f$0;
                boolean z3 = this.f$1;
                boolean z4 = this.f$2;
                iInputMethodClientInvoker2.getClass();
                try {
                    iInputMethodClientInvoker2.mTarget.setActive(z3, z4);
                    break;
                } catch (RemoteException e2) {
                    IInputMethodClientInvoker.logRemoteException(e2);
                }
        }
    }
}
