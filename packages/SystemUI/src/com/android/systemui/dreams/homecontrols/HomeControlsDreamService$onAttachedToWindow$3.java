package com.android.systemui.dreams.homecontrols;

import android.window.TaskFragmentInfo;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamService;
import com.android.systemui.log.core.Logger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class HomeControlsDreamService$onAttachedToWindow$3 extends FunctionReferenceImpl implements Function1 {
    public HomeControlsDreamService$onAttachedToWindow$3(Object obj) {
        super(1, obj, HomeControlsDreamService.class, "onTaskFragmentInfoChanged", "onTaskFragmentInfoChanged(Landroid/window/TaskFragmentInfo;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        HomeControlsDreamService homeControlsDreamService = (HomeControlsDreamService) this.receiver;
        HomeControlsDreamService.Companion companion = HomeControlsDreamService.Companion;
        homeControlsDreamService.getClass();
        if (((TaskFragmentInfo) obj).isEmpty()) {
            Logger.d$default(homeControlsDreamService.logger, "Finishing dream due to TaskFragment being empty", null, 2, null);
            homeControlsDreamService.endDream(true);
        }
        return Unit.INSTANCE;
    }
}
