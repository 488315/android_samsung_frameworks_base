package com.android.systemui.aibrief;

import android.os.Bundle;
import android.view.View;
import com.android.systemui.aibrief.data.NowBarData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AiBriefManager {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int NOW_BAR_VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
