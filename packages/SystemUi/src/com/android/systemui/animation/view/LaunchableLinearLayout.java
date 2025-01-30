package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LaunchableLinearLayout extends LinearLayout implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public LaunchableLinearLayout(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
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

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableLinearLayout$delegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }
}
