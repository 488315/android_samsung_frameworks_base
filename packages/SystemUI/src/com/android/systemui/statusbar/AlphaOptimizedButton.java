package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class AlphaOptimizedButton extends Button implements LaunchableView {
    public final LaunchableViewDelegate mDelegate;

    /* renamed from: $r8$lambda$Lb2Gbc-N9LM2UnIz4YKjbm0rnFQ, reason: not valid java name */
    public static /* synthetic */ Unit m2112$r8$lambda$Lb2GbcN9LM2UnIz4YKjbm0rnFQ(AlphaOptimizedButton alphaOptimizedButton, Integer num) {
        alphaOptimizedButton.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public AlphaOptimizedButton(Context context) {
        super(context);
        this.mDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedButton.m2112$r8$lambda$Lb2GbcN9LM2UnIz4YKjbm0rnFQ(AlphaOptimizedButton.this, (Integer) obj);
            }
        });
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.mDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.mDelegate.setVisibility(i);
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedButton.m2112$r8$lambda$Lb2GbcN9LM2UnIz4YKjbm0rnFQ(AlphaOptimizedButton.this, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedButton.m2112$r8$lambda$Lb2GbcN9LM2UnIz4YKjbm0rnFQ(AlphaOptimizedButton.this, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AlphaOptimizedButton.m2112$r8$lambda$Lb2GbcN9LM2UnIz4YKjbm0rnFQ(AlphaOptimizedButton.this, (Integer) obj);
            }
        });
    }
}
