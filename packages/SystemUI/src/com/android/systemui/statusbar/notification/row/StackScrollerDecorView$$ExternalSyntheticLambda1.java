package com.android.systemui.statusbar.notification.row;

import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class StackScrollerDecorView$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = StackScrollerDecorView.$r8$clinit;
                ((StackScrollerDecorView) obj2).onContentVisibilityAnimationEnd();
                break;
            default:
                int i3 = StackScrollerDecorView.$r8$clinit;
                ((Runnable) obj2).run();
                break;
        }
    }
}
