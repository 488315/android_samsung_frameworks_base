package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecFgsManagerControllerConstraintLayout extends ConstraintLayout {
    public final ConstraintSet constraintSet;
    public int orientation;

    public SecFgsManagerControllerConstraintLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.orientation;
        int i2 = configuration.orientation;
        if (i == i2) {
            return;
        }
        this.orientation = i2;
        ConstraintSet constraintSet = this.constraintSet;
        constraintSet.clone(this);
        constraintSet.get(R.id.sec_fgs_manager_recycler_view).layout.heightMax = getContext().getResources().getDimensionPixelSize(R.dimen.sec_fgs_max_height);
        constraintSet.applyTo(this);
    }

    public SecFgsManagerControllerConstraintLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ SecFgsManagerControllerConstraintLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public SecFgsManagerControllerConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.orientation = -1;
        this.constraintSet = new ConstraintSet();
    }
}
