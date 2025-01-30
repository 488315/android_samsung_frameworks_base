package com.android.systemui.navigationbar.gestural;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final NavigationModeController navigationModeController = (NavigationModeController) Dependency.get(NavigationModeController.class);
    public final List hintList = CollectionsKt__CollectionsKt.listOf(0, 1, 2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.navBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(context.getDisplayId());
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
