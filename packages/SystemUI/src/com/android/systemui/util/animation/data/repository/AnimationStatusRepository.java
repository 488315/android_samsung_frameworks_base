package com.android.systemui.util.animation.data.repository;

import kotlinx.coroutines.flow.Flow;

public interface AnimationStatusRepository {
    Flow areAnimationsEnabled();
}
