package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class StackedMobileIconBinder$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StackedMobileIconBinder$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                StackedMobileIconBinder stackedMobileIconBinder = StackedMobileIconBinder.INSTANCE;
                Boolean bool = (Boolean) ((MobileIconsViewModel) obj).isStackable.$$delegate_0.getValue();
                bool.getClass();
                return bool;
            default:
                return ((StackedMobileIconViewModelImpl.Factory) obj).create();
        }
    }
}
