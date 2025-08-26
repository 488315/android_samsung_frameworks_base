package com.android.systemui.aibrief;

import android.os.Bundle;
import android.view.View;

/* loaded from: classes.dex */
public interface AiBriefManager {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int NOW_BAR_VERSION = 0;

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
