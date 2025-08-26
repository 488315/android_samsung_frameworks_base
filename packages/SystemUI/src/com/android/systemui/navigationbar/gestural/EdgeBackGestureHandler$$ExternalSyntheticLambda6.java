package com.android.systemui.navigationbar.gestural;

import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda6(EdgeBackGestureHandler edgeBackGestureHandler, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 2;
        switch (this.$r8$classId) {
            case 0:
                EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
                Executor executor = (Executor) this.f$1;
                int i2 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                executor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(edgeBackGestureHandler, i));
                break;
            case 1:
                EdgeBackGestureHandler edgeBackGestureHandler2 = this.f$0;
                Executor executor2 = (Executor) this.f$1;
                int i3 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler2.getClass();
                executor2.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(edgeBackGestureHandler2, i));
                break;
            default:
                EdgeBackGestureHandler edgeBackGestureHandler3 = this.f$0;
                AppearanceRegion appearanceRegion = (AppearanceRegion) this.f$1;
                LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) ((LightBarController) edgeBackGestureHandler3.mLightBarControllerProvider.get());
                int i4 = 0;
                if (appearanceRegion == null) {
                    lightBarControllerImpl.mIsCustomizingForBackNav = false;
                    lightBarControllerImpl.updateStatus(lightBarControllerImpl.mAppearanceRegions);
                    break;
                } else {
                    lightBarControllerImpl.getClass();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(appearanceRegion);
                    while (true) {
                        AppearanceRegion[] appearanceRegionArr = lightBarControllerImpl.mAppearanceRegions;
                        if (i4 >= appearanceRegionArr.length) {
                            lightBarControllerImpl.updateStatus((AppearanceRegion[]) arrayList.toArray(new AppearanceRegion[arrayList.size()]));
                            lightBarControllerImpl.mIsCustomizingForBackNav = true;
                            break;
                        } else {
                            AppearanceRegion appearanceRegion2 = appearanceRegionArr[i4];
                            if (!appearanceRegion.getBounds().contains(appearanceRegion2.getBounds())) {
                                arrayList.add(appearanceRegion2);
                            }
                            i4++;
                        }
                    }
                }
        }
    }
}
