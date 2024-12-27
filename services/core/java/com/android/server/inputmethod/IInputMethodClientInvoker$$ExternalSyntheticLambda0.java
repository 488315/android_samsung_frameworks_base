package com.android.server.inputmethod;

import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class IInputMethodClientInvoker$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IInputMethodClientInvoker f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ IInputMethodClientInvoker$$ExternalSyntheticLambda0(
            IInputMethodClientInvoker iInputMethodClientInvoker, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = iInputMethodClientInvoker;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IInputMethodClientInvoker iInputMethodClientInvoker = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                iInputMethodClientInvoker.getClass();
                try {
                    iInputMethodClientInvoker.mTarget.onUnbindMethod(i, i2);
                    break;
                } catch (RemoteException e) {
                    IInputMethodClientInvoker.logRemoteException(e);
                    return;
                }
            default:
                IInputMethodClientInvoker iInputMethodClientInvoker2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                iInputMethodClientInvoker2.getClass();
                try {
                    iInputMethodClientInvoker2.mTarget.onUnbindAccessibilityService(i3, i4);
                    break;
                } catch (RemoteException e2) {
                    IInputMethodClientInvoker.logRemoteException(e2);
                }
        }
    }
}
