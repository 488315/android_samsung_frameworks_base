package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EdgeBackGestureHandler$Factory$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ EdgeBackGestureHandler.Factory f$0;
    public final /* synthetic */ Context f$1;

    public /* synthetic */ EdgeBackGestureHandler$Factory$$ExternalSyntheticLambda0(EdgeBackGestureHandler.Factory factory, Context context) {
        this.f$0 = factory;
        this.f$1 = context;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Context context = this.f$1;
        EdgeBackGestureHandler.Factory factory = this.f$0;
        factory.getClass();
        return new EdgeBackGestureHandler(context, factory.mOverviewProxyService, factory.mSysUiState, factory.mPluginManager, factory.mUiThreadContext, factory.mBackgroundExecutor, factory.mBgHandler, factory.mUserTracker, factory.mNavigationModeController, factory.mBackPanelControllerFactory, factory.mViewConfiguration, factory.mWindowManager, factory.mWindowManagerService, factory.mInputManager, factory.mPipOptional, factory.mDesktopModeOptional, factory.mFalsingManager, factory.mBackGestureTfClassifierProviderProvider, factory.mLightBarControllerProvider);
    }
}
