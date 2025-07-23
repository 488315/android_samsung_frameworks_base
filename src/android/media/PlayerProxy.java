package android.media;

import android.annotation.SystemApi;
import android.media.VolumeShaper;
import android.os.RemoteException;

@SystemApi
/* loaded from: classes2.dex */
public class PlayerProxy {
    private static final boolean DEBUG = false;
    private static final String TAG = "PlayerProxy";
    private final AudioPlaybackConfiguration mConf;

    PlayerProxy(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (audioPlaybackConfiguration == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackConfiguration");
        }
        this.mConf = audioPlaybackConfiguration;
    }

    @SystemApi
    public void start() {
        try {
            this.mConf.getIPlayer().start();
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for start operation, player already released?", e);
        }
    }

    @SystemApi
    public void pause() {
        try {
            this.mConf.getIPlayer().pause();
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for pause operation, player already released?", e);
        }
    }

    @SystemApi
    public void stop() {
        try {
            this.mConf.getIPlayer().stop();
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for stop operation, player already released?", e);
        }
    }

    @SystemApi
    public void setVolume(float f) {
        try {
            this.mConf.getIPlayer().setVolume(f);
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for setVolume operation, player already released?", e);
        }
    }

    @SystemApi
    public void setPan(float f) {
        try {
            this.mConf.getIPlayer().setPan(f);
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for setPan operation, player already released?", e);
        }
    }

    @SystemApi
    public void setStartDelayMs(int i) {
        try {
            this.mConf.getIPlayer().setStartDelayMs(i);
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for setStartDelayMs operation, player already released?", e);
        }
    }

    public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        try {
            this.mConf.getIPlayer().applyVolumeShaper(configuration.toParcelable(), operation.toParcelable());
        } catch (RemoteException | NullPointerException e) {
            throw new IllegalStateException("No player to proxy for applyVolumeShaper operation, player already released?", e);
        }
    }
}
