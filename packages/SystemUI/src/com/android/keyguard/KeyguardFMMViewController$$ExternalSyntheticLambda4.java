package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.util.Log;
import android.view.View;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardFMMViewController$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardFMMViewController f$0;

    public /* synthetic */ KeyguardFMMViewController$$ExternalSyntheticLambda4(KeyguardFMMViewController keyguardFMMViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardFMMViewController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        KeyguardFMMViewController keyguardFMMViewController = this.f$0;
        switch (i) {
            case 0:
                keyguardFMMViewController.getClass();
                try {
                    Log.d("KeyguardFMMView", "click call button : " + keyguardFMMViewController.mIsVoiceCapacity);
                    break;
                } catch (ActivityNotFoundException e) {
                    Log.w("KeyguardFMMView", "Can't find the component " + e);
                    return;
                }
            default:
                KeyguardFMMViewController.$r8$lambda$vSXyHOoqR37qpP3IKz4Ut8H24q8(keyguardFMMViewController);
                break;
        }
    }
}
