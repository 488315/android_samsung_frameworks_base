package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.RotationLockController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RotationLockInteractor {
    public RotationLockInteractor$addCallback$2 rotationLockCallback;
    public final RotationLockController rotationLockController = (RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class);
}
