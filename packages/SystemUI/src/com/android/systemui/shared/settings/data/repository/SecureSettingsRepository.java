package com.android.systemui.shared.settings.data.repository;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public interface SecureSettingsRepository {
    Flow boolSetting(String str, boolean z);

    Object getInt(String str, int i, Continuation continuation);

    Flow intSetting(String str, int i);
}
