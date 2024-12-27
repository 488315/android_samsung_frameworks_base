package com.android.systemui.statusbar;

import com.android.systemui.display.data.repository.DisplayMetricsRepository;

public final class QsFrameTranslateImpl extends QsFrameTranslateController {
    public QsFrameTranslateImpl(DisplayMetricsRepository displayMetricsRepository) {
        super(displayMetricsRepository);
    }

    @Override // com.android.systemui.statusbar.QsFrameTranslateController
    public final float getNotificationsTopPadding(float f) {
        return f;
    }
}
