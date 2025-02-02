package android.window;

import android.os.Bundle;
import android.os.IBinder;

/* loaded from: classes4.dex */
public interface WindowProvider {
  public static final String KEY_IS_WINDOW_PROVIDER_SERVICE =
      "android.windowContext.isWindowProviderService";

  Bundle getWindowContextOptions();

  IBinder getWindowContextToken();

  int getWindowType();
}
