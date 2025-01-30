package com.android.wm.shell.bubbles;

import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((Bubble) obj).mKey.equals((String) this.f$0);
            case 1:
                return ((Bubble) obj).mPackageName.equals((String) this.f$0);
            default:
                return !((Bubble) obj).equals(((BubbleData) this.f$0).mSelectedBubble);
        }
    }
}
