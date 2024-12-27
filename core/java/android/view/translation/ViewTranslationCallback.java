package android.view.translation;

import android.view.View;

public interface ViewTranslationCallback {
    boolean onClearTranslation(View view);

    boolean onHideTranslation(View view);

    boolean onShowTranslation(View view);

    default void enableContentPadding() {}

    default void setAnimationDurationMillis(int durationMillis) {}
}
