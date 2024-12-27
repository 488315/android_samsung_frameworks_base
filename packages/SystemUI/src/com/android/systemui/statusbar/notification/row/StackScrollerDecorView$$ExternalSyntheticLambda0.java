package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StackScrollerDecorView$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ StackScrollerDecorView f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda0(StackScrollerDecorView stackScrollerDecorView, FooterViewBinder$bindClearAllButton$2.AnonymousClass3.AnonymousClass1.C01911 c01911) {
        this.f$0 = stackScrollerDecorView;
        this.f$1 = c01911;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StackScrollerDecorView stackScrollerDecorView = this.f$0;
                Consumer consumer = this.f$1;
                Boolean bool = (Boolean) obj;
                stackScrollerDecorView.onContentVisibilityAnimationEnd();
                if (consumer != null) {
                    consumer.accept(bool);
                    break;
                }
                break;
            default:
                StackScrollerDecorView stackScrollerDecorView2 = this.f$0;
                Consumer consumer2 = this.f$1;
                Boolean bool2 = (Boolean) obj;
                stackScrollerDecorView2.onContentVisibilityAnimationEnd();
                if (consumer2 != null) {
                    consumer2.accept(bool2);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda0(StackScrollerDecorView stackScrollerDecorView, Consumer consumer) {
        this.f$0 = stackScrollerDecorView;
        this.f$1 = consumer;
    }
}
