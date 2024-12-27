package com.android.systemui.settings.brightness;

import android.content.Context;
import android.widget.FrameLayout;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.settings.brightness.BrightnessController;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class BrightnessDetail extends FrameLayout {
    public final Lazy adapter$delegate;
    public final SecQSDetailController qsDetailController;

    public BrightnessDetail(final Context context, SecQSDetailController secQSDetailController, final BrightnessController.Factory factory) {
        super(context);
        this.qsDetailController = secQSDetailController;
        this.adapter$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessDetail$adapter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new BrightnessDetailAdapter(context, factory);
            }
        });
    }
}
