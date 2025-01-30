package com.android.systemui.qs.bar;

import android.view.View;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Appendable f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BarController$$ExternalSyntheticLambda10(Appendable appendable, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = appendable;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StringBuilder sb = (StringBuilder) this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                BarItemImpl barItemImpl = (BarItemImpl) obj;
                sb.setLength(0);
                sb.append("  ");
                sb.append(barItemImpl.getClass().getSimpleName());
                sb.append(" : ");
                sb.append("isAvailable = ");
                sb.append(barItemImpl.isAvailable());
                sb.append(", isShowing = ");
                sb.append(barItemImpl.mShowing);
                sb.append(", underneathQqs = ");
                sb.append(barItemImpl.mIsUnderneathQqs);
                View view = barItemImpl.mBarRootView;
                if (view != null) {
                    sb.append(", alpha = ");
                    sb.append(view.getAlpha());
                    sb.append(", translationY = ");
                    sb.append(view.getTranslationY());
                    sb.append(", visibility = ");
                    sb.append(view.getVisibility());
                } else {
                    sb.append(", bar.getBarView() = ");
                    sb.append("null");
                }
                consumer.accept(sb.toString());
                break;
            default:
                ((BarItemImpl) obj).getClass();
                break;
        }
    }
}
