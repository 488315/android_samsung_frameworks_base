package com.android.systemui.dreams.homecontrols.domain.interactor;

import com.android.systemui.common.shared.model.PackageChangeModel;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class HomeControlsComponentInteractor$monitorUpdatesAndRestart$6 extends AdaptedFunctionReference implements Function3 {
    public static final HomeControlsComponentInteractor$monitorUpdatesAndRestart$6 INSTANCE = new HomeControlsComponentInteractor$monitorUpdatesAndRestart$6();

    public HomeControlsComponentInteractor$monitorUpdatesAndRestart$6() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        long longValue = ((Number) obj2).longValue();
        int i = HomeControlsComponentInteractor.$r8$clinit;
        return new Pair((PackageChangeModel.UpdateStarted) obj, new Long(longValue));
    }
}
