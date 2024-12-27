package com.android.systemui.dreams.homecontrols.domain.interactor;

import com.android.systemui.common.shared.model.PackageChangeModel;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;

final /* synthetic */ class HomeControlsComponentInteractor$monitorUpdatesAndRestart$4 extends AdaptedFunctionReference implements Function3 {
    public static final HomeControlsComponentInteractor$monitorUpdatesAndRestart$4 INSTANCE = new HomeControlsComponentInteractor$monitorUpdatesAndRestart$4();

    public HomeControlsComponentInteractor$monitorUpdatesAndRestart$4() {
        super(3, HomeControlsComponentInteractorKt.class, "validateUpdatePair", "validateUpdatePair(Lcom/android/systemui/common/shared/model/PackageChangeModel;Lcom/android/systemui/common/shared/model/PackageChangeModel;)Lcom/android/systemui/common/shared/model/PackageChangeModel$UpdateStarted;", 5);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PackageChangeModel packageChangeModel = (PackageChangeModel) obj;
        PackageChangeModel packageChangeModel2 = (PackageChangeModel) obj2;
        int i = HomeControlsComponentInteractor.$r8$clinit;
        if (Intrinsics.areEqual(packageChangeModel.getPackageName(), packageChangeModel2.getPackageName()) && packageChangeModel.getPackageUid() == packageChangeModel2.getPackageUid() && (packageChangeModel instanceof PackageChangeModel.UpdateStarted) && (packageChangeModel2 instanceof PackageChangeModel.UpdateFinished)) {
            return (PackageChangeModel.UpdateStarted) packageChangeModel;
        }
        return null;
    }
}
