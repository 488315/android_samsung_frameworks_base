package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.RotationLockController;

/* loaded from: classes2.dex */
public final class RotationLockInteractor {
    public RotationLockInteractor$addCallback$2 rotationLockCallback;
    public final RotationLockController rotationLockController = (RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class);
}
