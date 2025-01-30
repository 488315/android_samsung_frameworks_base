package com.android.systemui.statusbar.policy;

import com.android.internal.view.RotationPolicy;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SRotationLockControllerImpl implements RotationLockController {
    public final CopyOnWriteArrayList mCallbacks;
    public final RotationPolicyWrapper mRotationPolicy;
    public final C34341 mRotationPolicyListener;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.internal.view.RotationPolicy$RotationPolicyListener, com.android.systemui.statusbar.policy.SRotationLockControllerImpl$1] */
    public SRotationLockControllerImpl(RotationPolicyWrapper rotationPolicyWrapper, DeviceStateRotationLockSettingController deviceStateRotationLockSettingController, String[] strArr) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.mCallbacks = copyOnWriteArrayList;
        ?? r1 = new RotationPolicy.RotationPolicyListener() { // from class: com.android.systemui.statusbar.policy.SRotationLockControllerImpl.1
            public final void onChange() {
                SRotationLockControllerImpl sRotationLockControllerImpl = SRotationLockControllerImpl.this;
                Iterator it = sRotationLockControllerImpl.mCallbacks.iterator();
                while (it.hasNext()) {
                    RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = (RotationLockController.RotationLockControllerCallback) it.next();
                    RotationPolicyWrapperImpl rotationPolicyWrapperImpl = (RotationPolicyWrapperImpl) sRotationLockControllerImpl.mRotationPolicy;
                    boolean isRotationLocked = RotationPolicy.isRotationLocked(rotationPolicyWrapperImpl.context);
                    RotationPolicy.isRotationLockToggleVisible(rotationPolicyWrapperImpl.context);
                    rotationLockControllerCallback.onRotationLockStateChanged(isRotationLocked);
                }
            }
        };
        this.mRotationPolicyListener = r1;
        this.mRotationPolicy = rotationPolicyWrapper;
        boolean z = strArr.length > 0;
        if (z) {
            copyOnWriteArrayList.add(deviceStateRotationLockSettingController);
        }
        RotationPolicy.registerRotationPolicyListener(((RotationPolicyWrapperImpl) rotationPolicyWrapper).context, (RotationPolicy.RotationPolicyListener) r1, -1);
        if (z) {
            deviceStateRotationLockSettingController.setListening(true);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = (RotationLockController.RotationLockControllerCallback) obj;
        this.mCallbacks.add(rotationLockControllerCallback);
        RotationPolicyWrapperImpl rotationPolicyWrapperImpl = (RotationPolicyWrapperImpl) this.mRotationPolicy;
        boolean isRotationLocked = RotationPolicy.isRotationLocked(rotationPolicyWrapperImpl.context);
        RotationPolicy.isRotationLockToggleVisible(rotationPolicyWrapperImpl.context);
        rotationLockControllerCallback.onRotationLockStateChanged(isRotationLocked);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final int getRotationLockOrientation() {
        return RotationPolicy.getRotationLockOrientation(((RotationPolicyWrapperImpl) this.mRotationPolicy).context);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final boolean isCameraRotationEnabled() {
        return ((RotationPolicyWrapperImpl) this.mRotationPolicy).secureSettings.getInt("camera_autorotate", 0) == 1;
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final boolean isRotationLocked() {
        return RotationPolicy.isRotationLocked(((RotationPolicyWrapperImpl) this.mRotationPolicy).context);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((RotationLockController.RotationLockControllerCallback) obj);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final void setRotationLocked(boolean z) {
        ((RotationPolicyWrapperImpl) this.mRotationPolicy).setRotationLock(z);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final void setRotationLockedAtAngle(int i, boolean z) {
        RotationPolicy.setRotationLockAtAngle(((RotationPolicyWrapperImpl) this.mRotationPolicy).context, z, i);
    }
}
