package com.android.systemui.statusbar;

import com.android.systemui.display.data.repository.DisplayMetricsRepository;

public abstract class QsFrameTranslateController {
    public QsFrameTranslateController(DisplayMetricsRepository displayMetricsRepository) {
    }

    public abstract float getNotificationsTopPadding(float f);
}
