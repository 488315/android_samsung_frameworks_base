package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public class LaunchableFrameLayout extends FrameLayout implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    /* renamed from: $r8$lambda$5Hgvmrt_AD-VT7ilELImumKrnKw, reason: not valid java name */
    public static Unit m1013$r8$lambda$5Hgvmrt_ADVT7ilELImumKrnKw(LaunchableFrameLayout launchableFrameLayout, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    public LaunchableFrameLayout(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableFrameLayout.m1013$r8$lambda$5Hgvmrt_ADVT7ilELImumKrnKw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }

    public LaunchableFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableFrameLayout.m1013$r8$lambda$5Hgvmrt_ADVT7ilELImumKrnKw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableFrameLayout.m1013$r8$lambda$5Hgvmrt_ADVT7ilELImumKrnKw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableFrameLayout.m1013$r8$lambda$5Hgvmrt_ADVT7ilELImumKrnKw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }
}
