package com.android.systemui.statusbar.notification.stack.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SharedNotificationContainer extends ConstraintLayout {
    public final ConstraintSet baseConstraintSet;

    public SharedNotificationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ConstraintSet constraintSet = new ConstraintSet();
        setOptimizationLevel(getOptimizationLevel() | 64);
        constraintSet.create(R.id.nssl_guideline, 1);
        constraintSet.setGuidelinePercent(R.id.nssl_guideline, 0.5f);
        constraintSet.applyTo(this);
    }
}
