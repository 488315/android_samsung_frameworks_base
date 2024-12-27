package com.android.systemui.statusbar;

import com.android.systemui.display.data.repository.DisplayMetricsRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QsFrameTranslateImpl extends QsFrameTranslateController {
    public QsFrameTranslateImpl(DisplayMetricsRepository displayMetricsRepository) {
        super(displayMetricsRepository);
    }

    @Override // com.android.systemui.statusbar.QsFrameTranslateController
    public final float getNotificationsTopPadding(float f) {
        return f;
    }
}
