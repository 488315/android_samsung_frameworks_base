package com.android.systemui.plugins.qs;

import android.graphics.Rect;
import android.view.View;
import com.android.systemui.plugins.FragmentBase;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = QS.ACTION, version = 16)
@DependsOn(target = HeightListener.class)
/* loaded from: classes2.dex */
public interface QS extends FragmentBase {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_QS";
    public static final String TAG = "QS";
    public static final int VERSION = 16;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @ProvidesInterface(version = 1)
    public interface HeightListener {
        public static final int VERSION = 1;

        void onQsHeightChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @ProvidesInterface(version = 1)
    public interface ScrollListener {
        public static final int VERSION = 1;

        void onQsPanelScrollChanged(int i);
    }

    void animateHeaderSlidingOut();

    void closeCustomizer();

    void closeDetail();

    default boolean disallowPanelTouches() {
        return isShowingDetail();
    }

    int getDesiredHeight();

    View getHeader();

    int getHeaderBottom();

    void getHeaderBoundsOnScreen(Rect rect);

    default int getHeaderHeight() {
        return getHeaderBottom() - getHeaderTop();
    }

    int getHeaderLeft();

    int getHeaderTop();

    int getHeightDiff();

    int getQsMinExpansionHeight();

    void hideImmediately();

    boolean isCustomizing();

    default boolean isFullyCollapsed() {
        return true;
    }

    boolean isHeaderShown();

    boolean isShowingDetail();

    void notifyCustomizeChanged();

    void setCollapseExpandAction(Runnable runnable);

    void setCollapsedMediaVisibilityChangedListener(Consumer<Boolean> consumer);

    void setContainerController(QSContainerController qSContainerController);

    void setExpanded(boolean z);

    void setFancyClipping(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2);

    void setHeaderClickable(boolean z);

    void setHeaderListening(boolean z);

    void setHeightOverride(int i);

    void setInSplitShade(boolean z);

    void setIsNotificationPanelFullWidth(boolean z);

    void setListening(boolean z);

    void setOverscrolling(boolean z);

    void setPanelView(HeightListener heightListener);

    void setQsExpansion(float f, float f2, float f3, float f4);

    void setQsVisible(boolean z);

    default void setHasNotifications(boolean z) {
    }

    default void setOverScrollAmount(int i) {
    }

    default void setScrollListener(ScrollListener scrollListener) {
    }

    default void setShouldUpdateSquishinessOnMedia(boolean z) {
    }

    default void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
    }
}
