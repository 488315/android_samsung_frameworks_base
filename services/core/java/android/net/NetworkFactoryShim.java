package android.net;

import android.os.Looper;
import android.os.Message;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
