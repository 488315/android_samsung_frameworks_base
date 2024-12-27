package com.android.server.inputmethod;

import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ZeroJankProxy$$ExternalSyntheticLambda2 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ ZeroJankProxy f$0;
    public final /* synthetic */ IInputMethodClient f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;
    public final /* synthetic */ Object f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ Object f$6;

    public /* synthetic */ ZeroJankProxy$$ExternalSyntheticLambda2(ZeroJankProxy zeroJankProxy, IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) {
        this.f$0 = zeroJankProxy;
        this.f$1 = iInputMethodClient;
        this.f$2 = i;
        this.f$3 = str;
        this.f$4 = str2;
        this.f$5 = i2;
        this.f$6 = iBooleanListener;
    }

    public /* synthetic */ ZeroJankProxy$$ExternalSyntheticLambda2(ZeroJankProxy zeroJankProxy, IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2) {
        this.f$0 = zeroJankProxy;
        this.f$1 = iInputMethodClient;
        this.f$3 = iBinder;
        this.f$4 = token;
        this.f$2 = i;
        this.f$6 = resultReceiver;
        this.f$5 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                ZeroJankProxy zeroJankProxy = this.f$0;
                ((InputMethodManagerService) zeroJankProxy.mInner).acceptStylusHandwritingDelegationAsync(this.f$1, this.f$2, (String) this.f$3, (String) this.f$4, this.f$5, (IBooleanListener) this.f$6);
                break;
            default:
                ZeroJankProxy zeroJankProxy2 = this.f$0;
                IInputMethodClient iInputMethodClient = this.f$1;
                IBinder iBinder = (IBinder) this.f$3;
                ImeTracker.Token token = (ImeTracker.Token) this.f$4;
                int i = this.f$2;
                ResultReceiver resultReceiver = (ResultReceiver) this.f$6;
                if (!((InputMethodManagerService) zeroJankProxy2.mInner).hideSoftInput(iInputMethodClient, iBinder, token, i, resultReceiver, this.f$5)) {
                    zeroJankProxy2.sendResultReceiverFailure(resultReceiver);
                    break;
                }
                break;
        }
    }
}
