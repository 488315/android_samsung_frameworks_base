package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsAnimationViewController;
import com.android.systemui.biometrics.UdfpsAnimationViewController$dialogListener$1;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUIDialogManager implements Dumpable {
    public final KeyguardViewController mKeyguardViewController;
    public final Set mDialogsShowing = new HashSet();
    public final Set mListeners = new HashSet();

    public SystemUIDialogManager(DumpManager dumpManager, KeyguardViewController keyguardViewController) {
        dumpManager.registerDumpable(this);
        this.mKeyguardViewController = keyguardViewController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("listeners:");
        Iterator it = ((HashSet) this.mListeners).iterator();
        while (it.hasNext()) {
            printWriter.println("\t" + ((UdfpsAnimationViewController$dialogListener$1) it.next()));
        }
        printWriter.println("dialogs tracked:");
        Iterator it2 = ((HashSet) this.mDialogsShowing).iterator();
        while (it2.hasNext()) {
            printWriter.println("\t" + ((SystemUIDialog) it2.next()));
        }
    }

    public final void setShowing(SystemUIDialog systemUIDialog, boolean z) {
        boolean shouldHideAffordance = shouldHideAffordance();
        Set set = this.mDialogsShowing;
        if (z) {
            ((HashSet) set).add(systemUIDialog);
        } else {
            ((HashSet) set).remove(systemUIDialog);
        }
        if (shouldHideAffordance != shouldHideAffordance()) {
            if (shouldHideAffordance()) {
                this.mKeyguardViewController.hideAlternateBouncer(true);
            }
            Iterator it = ((HashSet) this.mListeners).iterator();
            while (it.hasNext()) {
                UdfpsAnimationViewController$dialogListener$1 udfpsAnimationViewController$dialogListener$1 = (UdfpsAnimationViewController$dialogListener$1) it.next();
                shouldHideAffordance();
                final UdfpsAnimationViewController udfpsAnimationViewController = udfpsAnimationViewController$dialogListener$1.this$0;
                boolean shouldHideAffordance2 = udfpsAnimationViewController.dialogManager.shouldHideAffordance();
                ValueAnimator valueAnimator = udfpsAnimationViewController.dialogAlphaAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                float[] fArr = new float[2];
                fArr[0] = udfpsAnimationViewController.getView().calculateAlpha() / 255.0f;
                fArr[1] = shouldHideAffordance2 ? 0.0f : 1.0f;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                ofFloat.setDuration(shouldHideAffordance2 ? 83L : 200L);
                ofFloat.setInterpolator(shouldHideAffordance2 ? Interpolators.LINEAR : Interpolators.ALPHA_IN);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$runDialogAlphaAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        UdfpsAnimationView view = UdfpsAnimationViewController.this.getView();
                        view.mDialogSuggestedAlpha = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        view.updateAlpha();
                        UdfpsAnimationViewController.this.updateAlpha();
                        UdfpsAnimationViewController.this.updatePauseAuth();
                    }
                });
                ofFloat.start();
                udfpsAnimationViewController.dialogAlphaAnimator = ofFloat;
            }
        }
    }

    public final boolean shouldHideAffordance() {
        return !((HashSet) this.mDialogsShowing).isEmpty();
    }
}
