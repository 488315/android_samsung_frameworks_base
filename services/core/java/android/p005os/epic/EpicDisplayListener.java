package android.p005os.epic;

import android.hardware.display.DisplayManager;
import android.view.Display;

/* loaded from: classes.dex */
public final class EpicDisplayListener implements DisplayManager.DisplayListener {
  public DisplayManager mDisplayManager;

  @Override // android.hardware.display.DisplayManager.DisplayListener
  public void onDisplayAdded(int i) {}

  @Override // android.hardware.display.DisplayManager.DisplayListener
  public void onDisplayRemoved(int i) {}

  public EpicDisplayListener(DisplayManager displayManager) {
    this.mDisplayManager = displayManager;
  }

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
}
