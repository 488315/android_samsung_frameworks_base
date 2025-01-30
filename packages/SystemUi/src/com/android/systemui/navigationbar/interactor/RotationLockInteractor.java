package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.RotationLockController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RotationLockInteractor {
    public RotationLockInteractor$addCallback$2 rotationLockCallback;
    public final RotationLockController rotationLockController = (RotationLockController) Dependency.get(RotationLockController.class);
}
