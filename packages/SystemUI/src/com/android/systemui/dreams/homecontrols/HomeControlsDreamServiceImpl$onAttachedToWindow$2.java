package com.android.systemui.dreams.homecontrols;

import android.window.TaskFragmentInfo;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl;
import com.android.systemui.log.core.Logger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class HomeControlsDreamServiceImpl$onAttachedToWindow$2 extends FunctionReferenceImpl implements Function1 {
    public HomeControlsDreamServiceImpl$onAttachedToWindow$2(Object obj) {
        super(1, obj, HomeControlsDreamServiceImpl.class, "onTaskFragmentInfoChanged", "onTaskFragmentInfoChanged(Landroid/window/TaskFragmentInfo;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = (HomeControlsDreamServiceImpl) this.receiver;
        HomeControlsDreamServiceImpl.Companion companion = HomeControlsDreamServiceImpl.Companion;
        homeControlsDreamServiceImpl.getClass();
        if (((TaskFragmentInfo) obj).isEmpty()) {
            Logger.d$default(homeControlsDreamServiceImpl.logger, "Finishing dream due to TaskFragment being empty", null, 2, null);
            homeControlsDreamServiceImpl.endDream(true);
        }
        return Unit.INSTANCE;
    }
}
