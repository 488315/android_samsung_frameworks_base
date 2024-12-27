package com.android.systemui.statusbar.phone.fragment;

import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;

public final /* synthetic */ class CollapsedStatusBarFragment$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CollapsedStatusBarFragment$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((CollapsedStatusBarFragment) obj).updateStatusBarVisibilitiesInner(false);
                break;
            default:
                CollapsedStatusBarFragment collapsedStatusBarFragment = ((CollapsedStatusBarFragment.AnonymousClass6) obj).this$0;
                collapsedStatusBarFragment.mPrivacyLogger.logStatusBarAlpha(1);
                StatusBarSystemEventDefaultAnimator statusBarSystemEventDefaultAnimator = collapsedStatusBarFragment.mSystemEventAnimator;
                statusBarSystemEventDefaultAnimator.onAlphaChanged.invoke(Float.valueOf(1.0f));
                break;
        }
    }
}
