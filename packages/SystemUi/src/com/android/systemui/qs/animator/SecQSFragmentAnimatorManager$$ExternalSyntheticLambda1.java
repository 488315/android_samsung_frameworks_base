package com.android.systemui.qs.animator;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SecQSFragmentAnimatorBase) obj).setFullyExpanded(this.f$0);
                break;
            case 1:
                ((SecQSFragmentAnimatorBase) obj).setStackScrollerOverscrolling(this.f$0);
                break;
            case 2:
                ((SecQSFragmentAnimatorBase) obj).setQsExpanded(this.f$0);
                break;
            case 3:
                ((SecQSFragmentAnimatorBase) obj).updatePanelExpanded(this.f$0);
                break;
            case 4:
                ((SecQSFragmentAnimatorBase) obj).mIsDetailOpening = this.f$0;
                break;
            case 5:
                ((SecQSFragmentAnimatorBase) obj).mIsDetailClosing = this.f$0;
                break;
            case 6:
                ((SecQSFragmentAnimatorBase) obj).mIsDetailShowing = this.f$0;
                break;
            default:
                ((SecQSFragmentAnimatorBase) obj).mDetailTriggeredExpand = this.f$0;
                break;
        }
    }
}
