package android.view.translation;

import android.icu.util.ULocale;

/* loaded from: classes4.dex */
public interface UiTranslationStateCallback {
    void onFinished();

    void onPaused();

    default void onResumed(ULocale uLocale, ULocale uLocale2) {
    }

    @Deprecated
    default void onStarted(String str, String str2) {
    }

    default void onStarted(ULocale uLocale, ULocale uLocale2) {
        onStarted(uLocale.getLanguage(), uLocale2.getLanguage());
    }

    default void onStarted(ULocale uLocale, ULocale uLocale2, String str) {
        onStarted(uLocale, uLocale2);
    }

    default void onPaused(String str) {
        onPaused();
    }

    default void onResumed(ULocale uLocale, ULocale uLocale2, String str) {
        onResumed(uLocale, uLocale2);
    }

    default void onFinished(String str) {
        onFinished();
    }
}
