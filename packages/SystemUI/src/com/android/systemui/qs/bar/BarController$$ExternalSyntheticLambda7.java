package com.android.systemui.qs.bar;

import android.view.View;
import java.util.function.Consumer;

public final /* synthetic */ class BarController$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ StringBuilder f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ BarController$$ExternalSyntheticLambda7(StringBuilder sb, Consumer consumer) {
        this.f$0 = sb;
        this.f$1 = consumer;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        StringBuilder sb = this.f$0;
        Consumer consumer = this.f$1;
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
    }
}
