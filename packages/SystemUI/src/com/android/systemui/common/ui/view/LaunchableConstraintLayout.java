package com.android.systemui.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public class LaunchableConstraintLayout extends ConstraintLayout implements LaunchableView {
    public final LaunchableViewDelegate delegate;

    public static Unit $r8$lambda$g5oClQW8S3BSD4i3oAV3ZAyV1lw(LaunchableConstraintLayout launchableConstraintLayout, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    public LaunchableConstraintLayout(Context context) {
        super(context);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.common.ui.view.LaunchableConstraintLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableConstraintLayout.$r8$lambda$g5oClQW8S3BSD4i3oAV3ZAyV1lw(this.f$0, ((Integer) obj).intValue());
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

    public LaunchableConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.common.ui.view.LaunchableConstraintLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableConstraintLayout.$r8$lambda$g5oClQW8S3BSD4i3oAV3ZAyV1lw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.common.ui.view.LaunchableConstraintLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableConstraintLayout.$r8$lambda$g5oClQW8S3BSD4i3oAV3ZAyV1lw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    public LaunchableConstraintLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.common.ui.view.LaunchableConstraintLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LaunchableConstraintLayout.$r8$lambda$g5oClQW8S3BSD4i3oAV3ZAyV1lw(this.f$0, ((Integer) obj).intValue());
            }
        });
    }
}
