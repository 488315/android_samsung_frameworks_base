package android.hardware.display;

public interface SemDeviceStatusListener {
    void onConnectionStatusChanged(int i);

    void onDlnaConnectionStatusChanged(boolean z);

    void onQosLevelChanged(int i);

    void onScreenSharingStatusChanged(int i);
}
