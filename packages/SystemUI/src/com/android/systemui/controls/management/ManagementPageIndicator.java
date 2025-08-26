package com.android.systemui.controls.management;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.qs.PageIndicator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ManagementPageIndicator extends PageIndicator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ManagementPageIndicator$$ExternalSyntheticLambda0 visibilityListener;

    public ManagementPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.visibilityListener = new ManagementPageIndicator$$ExternalSyntheticLambda0();
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (Intrinsics.areEqual(view, this)) {
            this.visibilityListener.mo781invoke(Integer.valueOf(i));
        }
    }

    @Override // com.android.systemui.qs.PageIndicator
    public final void setLocation(float f) {
        if (getLayoutDirection() == 1) {
            super.setLocation((getChildCount() - 1) - f);
        } else {
            super.setLocation(f);
        }
    }
}
