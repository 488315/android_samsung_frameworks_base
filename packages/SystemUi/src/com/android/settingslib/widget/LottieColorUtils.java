package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieColorUtils {
    public static final Map DARK_TO_LIGHT_THEME_COLOR_MAP;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(".grey600", Integer.valueOf(R.color.settingslib_color_grey400));
        hashMap.put(".grey700", Integer.valueOf(R.color.settingslib_color_grey500));
        hashMap.put(".grey800", Integer.valueOf(R.color.settingslib_color_grey300));
        hashMap.put(".grey900", Integer.valueOf(R.color.settingslib_color_grey50));
        hashMap.put(".red400", Integer.valueOf(R.color.settingslib_color_red600));
        hashMap.put(".black", Integer.valueOf(android.R.color.white));
        hashMap.put(".blue400", Integer.valueOf(R.color.settingslib_color_blue600));
        hashMap.put(".green400", Integer.valueOf(R.color.settingslib_color_green600));
        DARK_TO_LIGHT_THEME_COLOR_MAP = Collections.unmodifiableMap(hashMap);
    }

    private LottieColorUtils() {
    }

    public static void applyDynamicColors(Context context, LottieAnimationView lottieAnimationView) {
        if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
            return;
        }
        Map map = DARK_TO_LIGHT_THEME_COLOR_MAP;
        for (String str : map.keySet()) {
            final int color = context.getColor(((Integer) map.get(str)).intValue());
            lottieAnimationView.addValueCallback(new KeyPath("**", str, "**"), LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback() { // from class: com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final Object getValue() {
                    return new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
            });
        }
    }
}
