package com.android.systemui.navigationbar.gestural;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GestureHintAnimator implements NavigationModeController.ModeChangedListener {
    public final Context context;
    public int currentHintId;
    public GestureHintGroup gestureHintGroup;
    public AnimatorSet holdingViAnimator;
    public ButtonDispatcher homeHandle;
    public boolean isCanMove;
    public final NavBarStateManager navBarStateManager;
    public int navigationMode;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public final NavigationModeController navigationModeController = (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
    public final List hintList = CollectionsKt__CollectionsKt.listOf(0, 1, 2);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final LogWrapper mLogWrapper;

        public Factory(LogWrapper logWrapper) {
            this.mLogWrapper = logWrapper;
        }
    }

    static {
        new Companion(null);
    }

    public GestureHintAnimator(Context context, LogWrapper logWrapper) {
        this.context = context;
        this.navBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(context.getDisplayId());
    }

    public final float dipToPixel(float f) {
        return TypedValue.applyDimension(1, f, this.context.getResources().getDisplayMetrics());
    }

    public final View getHintView(int i) {
        GestureHintGroup gestureHintGroup;
        int i2 = this.navigationMode;
        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
        if (i2 == 2) {
            ButtonDispatcher buttonDispatcher = this.homeHandle;
            if (buttonDispatcher != null) {
                return buttonDispatcher.mCurrentView;
            }
            return null;
        }
        if (!NavigationModeUtil.isBottomGesture(i2) || (gestureHintGroup = this.gestureHintGroup) == null) {
            return null;
        }
        return ((ButtonDispatcher) gestureHintGroup.hintGroup.get(i)).mCurrentView;
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.navigationMode = i;
    }
}
