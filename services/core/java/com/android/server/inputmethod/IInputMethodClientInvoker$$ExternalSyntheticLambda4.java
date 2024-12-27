package com.android.server.inputmethod;

import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IInputMethodClientInvoker$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IInputMethodClientInvoker f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ IInputMethodClientInvoker$$ExternalSyntheticLambda4(IInputMethodClientInvoker iInputMethodClientInvoker, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = iInputMethodClientInvoker;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IInputMethodClientInvoker iInputMethodClientInvoker = this.f$0;
                boolean z = this.f$1;
                iInputMethodClientInvoker.getClass();
                try {
                    iInputMethodClientInvoker.mTarget.setImeVisibility(z);
                    break;
                } catch (RemoteException e) {
                    IInputMethodClientInvoker.logRemoteException(e);
                    return;
                }
            case 1:
                IInputMethodClientInvoker iInputMethodClientInvoker2 = this.f$0;
                boolean z2 = this.f$1;
                iInputMethodClientInvoker2.getClass();
                try {
                    iInputMethodClientInvoker2.mTarget.reportFullscreenMode(z2);
                    break;
                } catch (RemoteException e2) {
                    IInputMethodClientInvoker.logRemoteException(e2);
                    return;
                }
            case 2:
                IInputMethodClientInvoker iInputMethodClientInvoker3 = this.f$0;
                boolean z3 = this.f$1;
                iInputMethodClientInvoker3.getClass();
                try {
                    iInputMethodClientInvoker3.mTarget.scheduleStartInputIfNecessary(z3);
                    break;
                } catch (RemoteException e3) {
                    IInputMethodClientInvoker.logRemoteException(e3);
                    return;
                }
            default:
                IInputMethodClientInvoker iInputMethodClientInvoker4 = this.f$0;
                boolean z4 = this.f$1;
                iInputMethodClientInvoker4.getClass();
                try {
                    iInputMethodClientInvoker4.mTarget.setImeTraceEnabled(z4);
                    break;
                } catch (RemoteException e4) {
                    IInputMethodClientInvoker.logRemoteException(e4);
                }
        }
    }
}
