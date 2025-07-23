package com.android.settingslib.widget;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieColorUtils$$ExternalSyntheticLambda0 implements SimpleLottieValueCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LottieColorUtils$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
    public final Object getValue() {
        int i = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Map map = LottieColorUtils.DARK_TO_LIGHT_THEME_COLOR_MAP;
                break;
            default:
                Map map2 = LottieColorUtils.DARK_TO_LIGHT_THEME_COLOR_MAP;
                break;
        }
        return new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }
}
