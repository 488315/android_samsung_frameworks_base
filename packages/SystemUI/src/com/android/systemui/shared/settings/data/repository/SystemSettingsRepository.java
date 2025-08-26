package com.android.systemui.shared.settings.data.repository;

import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public interface SystemSettingsRepository {
    Flow boolSetting(String str, boolean z);

    Flow intSetting(String str, int i);
}
