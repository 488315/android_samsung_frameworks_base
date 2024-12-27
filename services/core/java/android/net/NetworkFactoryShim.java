package android.net;

import android.os.Looper;
import android.os.Message;

import java.io.PrintWriter;

public interface NetworkFactoryShim {
    void dump(PrintWriter printWriter);

    Looper getLooper();

    int getRequestCount();

    Message obtainMessage(int i, int i2, int i3, Object obj);

    void reevaluateAllRequests();

    void register(String str);

    default void registerIgnoringScore(String str) {
        throw new UnsupportedOperationException();
    }

    void setCapabilityFilter(NetworkCapabilities networkCapabilities);

    void setScoreFilter(int i);

    void setScoreFilter(NetworkScore networkScore);
}
