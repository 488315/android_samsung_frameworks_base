package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AlphaOptimizedButton extends Button implements LaunchableView {
    public final LaunchableViewDelegate mDelegate;

    /* renamed from: $r8$lambda$QRtv8B4ZALXYDv4nUz-jA3Egms0, reason: not valid java name */
    public static /* synthetic */ Unit m1697$r8$lambda$QRtv8B4ZALXYDv4nUzjA3Egms0(AlphaOptimizedButton alphaOptimizedButton, Integer num) {
        alphaOptimizedButton.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public AlphaOptimizedButton(Context context) {
        super(context);
        final int i = 2;
        this.mDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedButton f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i2 = i;
                AlphaOptimizedButton alphaOptimizedButton = this.f$0;
                switch (i2) {
                }
                return AlphaOptimizedButton.m1697$r8$lambda$QRtv8B4ZALXYDv4nUzjA3Egms0(alphaOptimizedButton, (Integer) obj);
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
        final int i = 1;
        this.mDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedButton f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i2 = i;
                AlphaOptimizedButton alphaOptimizedButton = this.f$0;
                switch (i2) {
                }
                return AlphaOptimizedButton.m1697$r8$lambda$QRtv8B4ZALXYDv4nUzjA3Egms0(alphaOptimizedButton, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final int i2 = 0;
        this.mDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedButton f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i22 = i2;
                AlphaOptimizedButton alphaOptimizedButton = this.f$0;
                switch (i22) {
                }
                return AlphaOptimizedButton.m1697$r8$lambda$QRtv8B4ZALXYDv4nUzjA3Egms0(alphaOptimizedButton, (Integer) obj);
            }
        });
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 3;
        this.mDelegate = new LaunchableViewDelegate(this, new Function1(this) { // from class: com.android.systemui.statusbar.AlphaOptimizedButton$$ExternalSyntheticLambda0
            public final /* synthetic */ AlphaOptimizedButton f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0004. Please report as an issue. */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i22 = i3;
                AlphaOptimizedButton alphaOptimizedButton = this.f$0;
                switch (i22) {
                }
                return AlphaOptimizedButton.m1697$r8$lambda$QRtv8B4ZALXYDv4nUzjA3Egms0(alphaOptimizedButton, (Integer) obj);
            }
        });
    }
}
