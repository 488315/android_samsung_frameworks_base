package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public class LaunchableImageView extends ImageView implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public static Unit $r8$lambda$TWE7brEbS2UPhh_U6KxdCEkIziY(LaunchableImageView launchableImageView, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    public LaunchableImageView(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableImageView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableImageView.$r8$lambda$TWE7brEbS2UPhh_U6KxdCEkIziY(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }

    public LaunchableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableImageView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableImageView.$r8$lambda$TWE7brEbS2UPhh_U6KxdCEkIziY(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableImageView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableImageView.$r8$lambda$TWE7brEbS2UPhh_U6KxdCEkIziY(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableImageView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableImageView.$r8$lambda$TWE7brEbS2UPhh_U6KxdCEkIziY(this.f$0, ((Integer) obj).intValue());
            }
        });
    }
}
