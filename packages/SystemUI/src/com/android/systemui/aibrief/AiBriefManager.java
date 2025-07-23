package com.android.systemui.aibrief;

import android.os.Bundle;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface AiBriefManager {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int NOW_BAR_VERSION = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int NOW_BAR_VERSION = 0;

        private Companion() {
        }
    }

    void createNowBar(Bundle bundle);

    void createRemoteNowBar(Bundle bundle);

    Bundle findSportsScoreRemoteViews(Bundle bundle);

    void hideNotification();

    void hideNowBar();

    void hideRemoteNowBar();

    void showNotification();

    void showNowBar(View view, View view2, View view3);

    void showReport();

    void updateNowBarNeedToUnlock(Bundle bundle);
}
