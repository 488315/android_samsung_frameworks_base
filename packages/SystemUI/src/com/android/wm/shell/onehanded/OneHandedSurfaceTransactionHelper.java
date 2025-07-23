package com.android.wm.shell.onehanded;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OneHandedSurfaceTransactionHelper {
    public final float mCornerRadius;
    public final float mCornerRadiusAdjustment;
    public final boolean mEnableCornerRadius;

    public OneHandedSurfaceTransactionHelper(Context context) {
        Resources resources = context.getResources();
        float dimension = resources.getDimension(17105919);
        this.mCornerRadiusAdjustment = dimension;
        this.mCornerRadius = resources.getDimension(17105918) - dimension;
        this.mEnableCornerRadius = resources.getBoolean(R.bool.config_one_handed_enable_round_corner);
    }
}
