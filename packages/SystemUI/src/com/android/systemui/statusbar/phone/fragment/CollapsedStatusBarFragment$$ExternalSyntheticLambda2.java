package com.android.systemui.statusbar.phone.fragment;

import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
