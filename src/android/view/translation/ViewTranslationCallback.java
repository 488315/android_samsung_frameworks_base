package android.view.translation;

import android.view.View;

/* loaded from: classes4.dex */
public interface ViewTranslationCallback {
    default void enableContentPadding() {
    }

    boolean onClearTranslation(View view);

    boolean onHideTranslation(View view);

    boolean onShowTranslation(View view);

    default void setAnimationDurationMillis(int i) {
    }
}
