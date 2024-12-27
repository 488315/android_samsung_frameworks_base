package com.android.systemui.volume.panel.domain;

import kotlinx.coroutines.flow.Flow;

public interface ComponentAvailabilityCriteria {
    Flow isAvailable();
}
