package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AlphaOptimizedFrameLayout extends FrameLayout implements LaunchableView {
    public final LaunchableViewDelegate mLaunchableViewDelegate;

    public static /* synthetic */ Unit $r8$lambda$z2tzVSS17HYjY_wGHC7WJRNYNFc(AlphaOptimizedFrameLayout alphaOptimizedFrameLayout, Integer num) {
        alphaOptimizedFrameLayout.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public AlphaOptimizedFrameLayout(Context context) {
        super(context);
        final int i = 3;
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedFrameLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i2 = i;
                AlphaOptimizedFrameLayout alphaOptimizedFrameLayout = this.f$0;
                switch (i2) {
                }
                return AlphaOptimizedFrameLayout.$r8$lambda$z2tzVSS17HYjY_wGHC7WJRNYNFc(alphaOptimizedFrameLayout, (Integer) obj);
            }
        });
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.mLaunchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.mLaunchableViewDelegate.setVisibility(i);
    }

    public AlphaOptimizedFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 2;
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedFrameLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i2 = i;
                AlphaOptimizedFrameLayout alphaOptimizedFrameLayout = this.f$0;
                switch (i2) {
                }
                return AlphaOptimizedFrameLayout.$r8$lambda$z2tzVSS17HYjY_wGHC7WJRNYNFc(alphaOptimizedFrameLayout, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final int i2 = 1;
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedFrameLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i22 = i2;
                AlphaOptimizedFrameLayout alphaOptimizedFrameLayout = this.f$0;
                switch (i22) {
                }
                return AlphaOptimizedFrameLayout.$r8$lambda$z2tzVSS17HYjY_wGHC7WJRNYNFc(alphaOptimizedFrameLayout, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 0;
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedFrameLayout f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i22 = i3;
                AlphaOptimizedFrameLayout alphaOptimizedFrameLayout = this.f$0;
                switch (i22) {
                }
                return AlphaOptimizedFrameLayout.$r8$lambda$z2tzVSS17HYjY_wGHC7WJRNYNFc(alphaOptimizedFrameLayout, (Integer) obj);
            }
        });
    }
}
