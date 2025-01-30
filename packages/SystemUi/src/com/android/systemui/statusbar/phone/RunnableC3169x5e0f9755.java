package com.android.systemui.statusbar.phone;

import android.os.TraceNameSupplier;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 */
/* loaded from: classes2.dex */
public final class RunnableC3169x5e0f9755 implements Runnable, TraceNameSupplier {
    public final /* synthetic */ String $tag;
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    public RunnableC3169x5e0f9755(String str, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.$tag = str;
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final String getTraceName() {
        return this.$tag;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.lightRevealAnimator.start();
        boolean z = LsRune.AOD_FULLSCREEN;
        final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.this$0.helper;
        Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$startLightRevealCallback$1$1
            @Override // java.lang.Runnable
            public final void run() {
                SecUnlockedScreenOffAnimationHelper.this.getClass();
                SecUnlockedScreenOffAnimationHelper.logD("onStartAnimation");
            }
        }, z);
    }
}
