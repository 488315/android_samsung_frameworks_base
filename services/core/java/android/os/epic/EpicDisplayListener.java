package android.os.epic;

import android.hardware.display.DisplayManager;
import android.view.Display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EpicDisplayListener implements DisplayManager.DisplayListener {
    private DisplayManager mDisplayManager;

    public EpicDisplayListener(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {}

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        Display display;
        Display.Mode mode;
        if (i == -1
                || (display = this.mDisplayManager.getDisplay(i)) == null
                || (mode = display.getMode()) == null) {
            return;
        }
        mode.getRefreshRate();
        display.getState();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {}
}
