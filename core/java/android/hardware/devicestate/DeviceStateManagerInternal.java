package android.hardware.devicestate;

/* loaded from: classes2.dex */
public abstract class DeviceStateManagerInternal {
    public abstract void displayEnabled();

    public abstract int[] getSupportedStateIdentifiers();

    public abstract void requestTentModeIfNeeded();

    public abstract void setTableModeEnabled(boolean z);

    public abstract void updateFoldingSensorState(boolean z);
}
