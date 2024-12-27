package com.android.systemui.aibrief;

import android.os.Bundle;
import android.view.View;
import com.android.systemui.aibrief.data.NowBarData;

public interface AiBriefManager {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int NOW_BAR_VERSION = 1;

    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int NOW_BAR_VERSION = 1;

        private Companion() {
        }
    }

    void createNowBar(Bundle bundle);

    void createRemoteNowBar(Bundle bundle);

    void hideNotification();

    void hideNowBar();

    void hideRemoteNowBar();

    void showNotification();

    void showNowBar(View view, View view2, NowBarData nowBarData);

    void showReport();
}
