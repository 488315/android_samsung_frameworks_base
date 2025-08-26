package com.android.wm.shell.onehanded;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class OneHandedSurfaceTransactionHelper {
    public final float mCornerRadius;
    public final float mCornerRadiusAdjustment;
    public final boolean mEnableCornerRadius;

    public OneHandedSurfaceTransactionHelper(Context context) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        float dimension = resources.getDimension(17105920);
        this.mCornerRadiusAdjustment = dimension;
        this.mCornerRadius = resources.getDimension(17105919) - dimension;
        this.mEnableCornerRadius = resources.getBoolean(R.bool.config_one_handed_enable_round_corner);
    }
}
