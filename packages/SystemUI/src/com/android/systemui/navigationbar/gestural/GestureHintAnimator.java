package com.android.systemui.navigationbar.gestural;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
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
    public final List hintList = Arrays.asList(0, 1, 2);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
        return ActionRow$$ExternalSyntheticOutline0.m(this.context, 1, f);
    }

    public final View getHintView(int i) {
        GestureHintGroup gestureHintGroup;
        ButtonDispatcher buttonDispatcher;
        int i2 = this.navigationMode;
        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
        if (i2 == 2) {
            ButtonDispatcher buttonDispatcher2 = this.homeHandle;
            if (buttonDispatcher2 != null) {
                return buttonDispatcher2.mCurrentView;
            }
            return null;
        }
        if (!NavigationModeUtil.isBottomGesture(i2) || (gestureHintGroup = this.gestureHintGroup) == null || (buttonDispatcher = (ButtonDispatcher) gestureHintGroup.hintGroup.get(i)) == null) {
            return null;
        }
        return buttonDispatcher.mCurrentView;
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.navigationMode = i;
    }
}
