package com.android.systemui.shared.settings.data.repository;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SecureSettingsRepository {
    Flow boolSetting(String str, boolean z);

    Object getInt(String str, int i, Continuation continuation);

    Flow intSetting(String str, int i);
}
