package com.android.systemui.qs.tiles.impl.rotation.domain.model;

public final class RotationLockTileModel {
    public final boolean isCameraRotationEnabled;
    public final boolean isRotationLocked;

    public RotationLockTileModel(boolean z, boolean z2) {
        this.isRotationLocked = z;
        this.isCameraRotationEnabled = z2;
    }
}
