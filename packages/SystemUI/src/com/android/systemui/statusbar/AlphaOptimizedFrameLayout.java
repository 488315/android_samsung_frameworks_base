package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class AlphaOptimizedFrameLayout extends FrameLayout implements LaunchableView {
    public final LaunchableViewDelegate mLaunchableViewDelegate;

    public static /* synthetic */ Unit $r8$lambda$rAl_2oS5ZJUxCdwum8_jgd2txzs(AlphaOptimizedFrameLayout alphaOptimizedFrameLayout, Integer num) {
        alphaOptimizedFrameLayout.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public AlphaOptimizedFrameLayout(Context context) {
        super(context);
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedFrameLayout.$r8$lambda$rAl_2oS5ZJUxCdwum8_jgd2txzs(AlphaOptimizedFrameLayout.this, (Integer) obj);
            }
        });
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
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
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedFrameLayout.$r8$lambda$rAl_2oS5ZJUxCdwum8_jgd2txzs(AlphaOptimizedFrameLayout.this, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedFrameLayout.$r8$lambda$rAl_2oS5ZJUxCdwum8_jgd2txzs(AlphaOptimizedFrameLayout.this, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedFrameLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedFrameLayout.$r8$lambda$rAl_2oS5ZJUxCdwum8_jgd2txzs(AlphaOptimizedFrameLayout.this, (Integer) obj);
            }
        });
    }
}
