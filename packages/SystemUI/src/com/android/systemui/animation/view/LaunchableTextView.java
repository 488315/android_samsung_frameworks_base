package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class LaunchableTextView extends TextView implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public LaunchableTextView(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.TextView*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
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
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.TextView*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }

    public LaunchableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableTextView$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.TextView*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }
}
