package android.telecom;

import java.util.function.Consumer;

public interface CallControlCallback {
    void onAnswer(int i, Consumer<Boolean> consumer);

    void onCallStreamingStarted(Consumer<Boolean> consumer);

    void onDisconnect(DisconnectCause disconnectCause, Consumer<Boolean> consumer);

    void onSetActive(Consumer<Boolean> consumer);

    void onSetInactive(Consumer<Boolean> consumer);
}
