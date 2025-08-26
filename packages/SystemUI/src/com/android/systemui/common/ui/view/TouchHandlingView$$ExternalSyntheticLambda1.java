package com.android.systemui.common.ui.view;

import android.view.ViewConfiguration;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class TouchHandlingView$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                int i = TouchHandlingView.$r8$clinit;
                return Long.valueOf(ViewConfiguration.getLongPressTimeout());
            default:
                int i2 = TouchHandlingView.$r8$clinit;
                return Unit.INSTANCE;
        }
    }
}
