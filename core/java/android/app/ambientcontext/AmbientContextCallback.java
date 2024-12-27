package android.app.ambientcontext;

import java.util.List;

public interface AmbientContextCallback {
    void onEvents(List<AmbientContextEvent> list);

    void onRegistrationComplete(int i);
}
