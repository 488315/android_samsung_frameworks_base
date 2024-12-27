package android.service.games;

import android.annotation.SystemApi;
import android.content.Intent;

@SystemApi
public interface GameSessionActivityCallback {
    void onActivityResult(int i, Intent intent);

    default void onActivityStartFailed(Throwable t) {}
}
