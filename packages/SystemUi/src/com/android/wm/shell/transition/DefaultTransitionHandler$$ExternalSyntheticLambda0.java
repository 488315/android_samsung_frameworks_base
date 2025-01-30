package com.android.wm.shell.transition;

import android.animation.Animator;
import android.content.IntentFilter;
import com.android.internal.policy.TransitionAnimation;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultTransitionHandler defaultTransitionHandler = (DefaultTransitionHandler) this.f$0;
                defaultTransitionHandler.mEnterpriseThumbnailDrawable = defaultTransitionHandler.mDevicePolicyManager.getResources().getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new DefaultTransitionHandler$$ExternalSyntheticLambda1(defaultTransitionHandler));
                defaultTransitionHandler.mContext.registerReceiver(defaultTransitionHandler.mEnterpriseResourceUpdatedReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_RESOURCE_UPDATED"), null, defaultTransitionHandler.mMainHandler, 2);
                TransitionAnimation.initAttributeCache(defaultTransitionHandler.mContext, defaultTransitionHandler.mMainHandler);
                break;
            case 1:
                ((Animator) this.f$0).end();
                break;
            default:
                ArrayList arrayList = (ArrayList) this.f$0;
                for (int i = 0; i < arrayList.size(); i++) {
                    ((Animator) arrayList.get(i)).start();
                }
                break;
        }
    }
}
