package android.hardware.display;

public interface SemDisplayVolumeKeyListener {
    void onMuteKeyStateChanged(boolean z);

    void onVolumeKeyDown();

    void onVolumeKeyUp();
}
