package com.android.systemui.statusbar.phone.fragment;

import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.mPrivacyLogger.logStatusBarAlpha(1);
                StatusBarSystemEventAnimator statusBarSystemEventAnimator = collapsedStatusBarFragment.mSystemEventAnimator;
                statusBarSystemEventAnimator.onAlphaChanged.invoke(Float.valueOf(1.0f));
                break;
            default:
                CollapsedStatusBarFragment.StatusBarHideChecker statusBarHideChecker = (CollapsedStatusBarFragment.StatusBarHideChecker) this.f$0;
                statusBarHideChecker.getClass();
                int i = CollapsedStatusBarFragment.$r8$clinit;
                CollapsedStatusBarFragment.this.updateStatusBarVisibilities(false);
                break;
        }
    }
}
