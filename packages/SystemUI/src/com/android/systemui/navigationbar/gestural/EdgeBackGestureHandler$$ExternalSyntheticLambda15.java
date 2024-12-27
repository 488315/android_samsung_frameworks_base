package com.android.systemui.navigationbar.gestural;

import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.phone.LightBarController;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ EdgeBackGestureHandler f$0;
    public final /* synthetic */ AppearanceRegion f$1;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda15(EdgeBackGestureHandler edgeBackGestureHandler, AppearanceRegion appearanceRegion) {
        this.f$0 = edgeBackGestureHandler;
        this.f$1 = appearanceRegion;
    }

    @Override // java.lang.Runnable
    public final void run() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
        AppearanceRegion appearanceRegion = this.f$1;
        LightBarController lightBarController = (LightBarController) edgeBackGestureHandler.mLightBarControllerProvider.get();
        int i = 0;
        if (appearanceRegion == null) {
            lightBarController.mIsCustomizingForBackNav = false;
            lightBarController.updateStatus(lightBarController.mAppearanceRegions);
            return;
        }
        lightBarController.getClass();
        ArrayList arrayList = new ArrayList();
        arrayList.add(appearanceRegion);
        while (true) {
            AppearanceRegion[] appearanceRegionArr = lightBarController.mAppearanceRegions;
            if (i >= appearanceRegionArr.length) {
                lightBarController.updateStatus((AppearanceRegion[]) arrayList.toArray(new AppearanceRegion[arrayList.size()]));
                lightBarController.mIsCustomizingForBackNav = true;
                return;
            } else {
                AppearanceRegion appearanceRegion2 = appearanceRegionArr[i];
                if (!appearanceRegion.getBounds().contains(appearanceRegion2.getBounds())) {
                    arrayList.add(appearanceRegion2);
                }
                i++;
            }
        }
    }
}
