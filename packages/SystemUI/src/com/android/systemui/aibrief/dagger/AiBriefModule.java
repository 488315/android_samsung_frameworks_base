package com.android.systemui.aibrief.dagger;

import com.android.systemui.aibrief.AiBriefManager;
import com.android.systemui.aibrief.AiBriefManagerImpl;

public abstract class AiBriefModule {
    public abstract AiBriefManager bindAiBriefManager(AiBriefManagerImpl aiBriefManagerImpl);
}
