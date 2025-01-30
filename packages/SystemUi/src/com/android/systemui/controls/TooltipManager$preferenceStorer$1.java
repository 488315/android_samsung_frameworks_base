package com.android.systemui.controls;

import android.content.Context;
import com.android.systemui.Prefs;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TooltipManager$preferenceStorer$1 extends Lambda implements Function1 {
    final /* synthetic */ Context $context;
    final /* synthetic */ TooltipManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TooltipManager$preferenceStorer$1(Context context, TooltipManager tooltipManager) {
        super(1);
        this.$context = context;
        this.this$0 = tooltipManager;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Prefs.putInt(this.$context, this.this$0.preferenceName, ((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
