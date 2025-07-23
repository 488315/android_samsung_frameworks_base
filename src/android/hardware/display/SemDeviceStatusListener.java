package android.hardware.display;

/* loaded from: classes2.dex */
public interface SemDeviceStatusListener {
    void onConnectionStatusChanged(int i);

    void onDlnaConnectionStatusChanged(boolean z);

    void onQosLevelChanged(int i);

    void onScreenSharingStatusChanged(int i);
}
