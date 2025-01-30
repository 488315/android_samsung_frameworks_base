package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.util.Log;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardFMMViewController$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardFMMViewController f$0;

    public /* synthetic */ KeyguardFMMViewController$$ExternalSyntheticLambda2(KeyguardFMMViewController keyguardFMMViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardFMMViewController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardFMMViewController keyguardFMMViewController = this.f$0;
                ((KeyguardFMMView) keyguardFMMViewController.mView).doHapticKeyClick();
                if (keyguardFMMViewController.mPasswordEntry.isEnabled()) {
                    keyguardFMMViewController.verifyPasswordAndUnlock();
                    break;
                }
                break;
            default:
                KeyguardFMMViewController keyguardFMMViewController2 = this.f$0;
                keyguardFMMViewController2.getClass();
                try {
                    Log.d("KeyguardFMMView", "click call button : " + keyguardFMMViewController2.mIsVoiceCapacity);
                    break;
                } catch (ActivityNotFoundException e) {
                    Log.w("KeyguardFMMView", "Can't find the component " + e);
                    return;
                }
        }
    }
}
