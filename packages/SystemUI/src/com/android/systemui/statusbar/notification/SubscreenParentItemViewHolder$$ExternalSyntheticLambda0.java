package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubscreenParentItemViewHolder$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SubscreenParentItemViewHolder f$0;
    public final /* synthetic */ OngoingActivityData f$1;
    public final /* synthetic */ Context f$2;
    public final /* synthetic */ View f$3;

    public /* synthetic */ SubscreenParentItemViewHolder$$ExternalSyntheticLambda0(SubscreenParentItemViewHolder subscreenParentItemViewHolder, OngoingActivityData ongoingActivityData, Context context, View view) {
        this.f$0 = subscreenParentItemViewHolder;
        this.f$1 = ongoingActivityData;
        this.f$2 = context;
        this.f$3 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SubscreenParentItemViewHolder subscreenParentItemViewHolder = this.f$0;
        OngoingActivityData ongoingActivityData = this.f$1;
        Context context = this.f$2;
        View view = this.f$3;
        int i = SubscreenParentItemViewHolder.$r8$clinit;
        subscreenParentItemViewHolder.getClass();
        boolean z = ongoingActivityData.mCustomExpandedCardView == null;
        CardStackViewUtils cardStackViewUtils = CardStackViewUtils.INSTANCE;
        boolean z2 = z;
        int measuredWidth = view.getMeasuredWidth();
        boolean z3 = false;
        int measuredHeight = view.getMeasuredHeight();
        int i2 = ongoingActivityData.mChipBackground;
        Drawable background = view.getBackground();
        if (subscreenParentItemViewHolder.mInfo.mRow.mEntry.isPromotedState() && z2) {
            z3 = true;
        }
        cardStackViewUtils.getClass();
        CardStackViewUtils.addGradientBackground(context, measuredWidth, measuredHeight, i2, background, true, z3);
    }
}
