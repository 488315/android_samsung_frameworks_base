package android.window;

import android.os.Bundle;
import android.os.IBinder;

public interface WindowProvider {
    public static final String KEY_IS_WINDOW_PROVIDER_SERVICE =
            "android.windowContext.isWindowProviderService";

    Bundle getWindowContextOptions();

    IBinder getWindowContextToken();

    int getWindowType();
}
