package com.android.systemui.plugins.qs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
public interface DetailAdapter {
    public static final UiEventLogger.UiEventEnum INVALID = new DetailAdapter$$ExternalSyntheticLambda0();
    public static final int VERSION = 1;

    static /* synthetic */ int lambda$static$0() {
        return 0;
    }

    default UiEventLogger.UiEventEnum closeDetailEvent() {
        return INVALID;
    }

    View createDetailView(Context context, View view, ViewGroup viewGroup);

    default boolean disallowStartSettingsIntent() {
        return false;
    }

    default String getDetailAdapterSummary() {
        return null;
    }

    default int getDoneText() {
        return 0;
    }

    int getMetricsCategory();

    Intent getSettingsIntent();

    default int getSettingsText() {
        return 0;
    }

    CharSequence getTitle();

    default boolean getToggleEnabled() {
        return true;
    }

    Boolean getToggleState();

    default boolean hasHeader() {
        return true;
    }

    default UiEventLogger.UiEventEnum moreSettingsEvent() {
        return INVALID;
    }

    default boolean onDoneButtonClicked() {
        return false;
    }

    default UiEventLogger.UiEventEnum openDetailEvent() {
        return INVALID;
    }

    void setToggleState(boolean z);

    default boolean shouldAnimate() {
        return true;
    }

    default boolean shouldUseFullScreen() {
        return false;
    }

    default void dismissListPopupWindow() {
    }
}
