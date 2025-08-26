package com.android.systemui.biometrics.data.repository;

import kotlinx.coroutines.flow.Flow;

/* loaded from: classes.dex */
public interface FaceUserSettingsRepository {
    Flow getAlwaysRequireConfirmationInApps();
}
