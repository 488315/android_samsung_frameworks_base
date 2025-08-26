package com.android.systemui.lifecycle;

import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((View) this.f$0).removeOnAttachStateChangeListener((RepeatWhenAttachedKt$isAttached$1$onAttachListener$1) this.f$1);
                break;
            case 1:
                ((ViewTreeObserver) this.f$0).removeOnWindowFocusChangeListener((RepeatWhenAttachedKt$isWindowFocused$2$listener$1) this.f$1);
                break;
            default:
                ((ViewTreeObserver) this.f$0).removeOnWindowVisibilityChangeListener((RepeatWhenAttachedKt$isWindowVisible$2$listener$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
