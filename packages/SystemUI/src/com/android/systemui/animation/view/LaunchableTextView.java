package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public class LaunchableTextView extends TextView implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public static Unit $r8$lambda$sjuMDRkCCcSpZVzPQiMcczIKS_Q(LaunchableTextView launchableTextView, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    public LaunchableTextView(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableTextView.$r8$lambda$sjuMDRkCCcSpZVzPQiMcczIKS_Q(this.f$0, ((Integer) obj).intValue());
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

    public LaunchableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableTextView.$r8$lambda$sjuMDRkCCcSpZVzPQiMcczIKS_Q(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableTextView.$r8$lambda$sjuMDRkCCcSpZVzPQiMcczIKS_Q(this.f$0, ((Integer) obj).intValue());
            }
        });
    }
}
