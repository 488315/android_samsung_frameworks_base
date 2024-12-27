package com.android.server.policy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.keyguard.KeyguardServiceDelegate;
import com.android.server.policy.keyguard.KeyguardServiceWrapper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda13(PhoneWindowManagerExt.AnonymousClass2 anonymousClass2, Context context, ComponentName componentName) {
        this.f$0 = anonymousClass2;
        this.f$1 = context;
        this.f$2 = componentName;
    }

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda13(PhoneWindowManagerExt phoneWindowManagerExt, Context context, String str) {
        this.f$0 = phoneWindowManagerExt;
        this.f$1 = context;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PhoneWindowManagerExt phoneWindowManagerExt = (PhoneWindowManagerExt) this.f$0;
                Context context = this.f$1;
                String str = (String) this.f$2;
                Toast toast = phoneWindowManagerExt.mToast;
                if (toast != null) {
                    toast.cancel();
                }
                Toast makeText = Toast.makeText(context, str, 0);
                phoneWindowManagerExt.mToast = makeText;
                makeText.show();
                break;
            default:
                PhoneWindowManagerExt.AnonymousClass2 anonymousClass2 = (PhoneWindowManagerExt.AnonymousClass2) this.f$0;
                Context context2 = this.f$1;
                ComponentName componentName = (ComponentName) this.f$2;
                anonymousClass2.getClass();
                context2.startServiceAsUser(new Intent().setComponent(componentName).addFlags(256), UserHandle.SYSTEM);
                PhoneWindowManagerExt phoneWindowManagerExt2 = anonymousClass2.this$0;
                PhoneWindowManager phoneWindowManager = phoneWindowManagerExt2.mPolicy;
                if (phoneWindowManager.mKeyguardDelegate != null) {
                    Log.d("PhoneWindowManagerExt", "bind KeyguardService due to updating SystemUI pkg");
                    phoneWindowManager.mKeyguardDelegate.bindService(phoneWindowManagerExt2.mContext);
                    KeyguardServiceDelegate keyguardServiceDelegate = phoneWindowManager.mKeyguardDelegate;
                    KeyguardServiceWrapper keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService;
                    if (keyguardServiceWrapper != null) {
                        keyguardServiceWrapper.onBootCompleted();
                    }
                    keyguardServiceDelegate.mKeyguardState.bootCompleted = true;
                    break;
                }
                break;
        }
    }
}
