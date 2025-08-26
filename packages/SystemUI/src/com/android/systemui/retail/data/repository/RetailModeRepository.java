package com.android.systemui.retail.data.repository;

import com.android.systemui.retail.data.repository.impl.RetailModeSettingsRepository;

/* loaded from: classes2.dex */
public interface RetailModeRepository {
    default boolean getInRetailMode() {
        return ((Boolean) ((RetailModeSettingsRepository) this).retailMode.$$delegate_0.getValue()).booleanValue();
    }
}
