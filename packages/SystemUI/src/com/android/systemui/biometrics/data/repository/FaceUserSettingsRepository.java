package com.android.systemui.biometrics.data.repository;

import kotlinx.coroutines.flow.Flow;

public interface FaceUserSettingsRepository {
    Flow getAlwaysRequireConfirmationInApps();
}
