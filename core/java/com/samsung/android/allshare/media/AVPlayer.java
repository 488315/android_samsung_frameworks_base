package com.samsung.android.allshare.media;

import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;

import java.util.List;

public abstract class AVPlayer extends Device {

    public interface IAVPlayerEventListener {
        void onDeviceChanged(AVPlayerState aVPlayerState, ERROR error);
    }

    public interface IAVPlayerExtensionEventListener {
        void onExtensionEvent(String str, String str2, ERROR error);
    }

    public interface IAVPlayerExtensionResponseListener {
        void onAspectRatioStateResponseReceived(String str, ERROR error);

        void onCaptionStateResponseReceived(List<Caption> list, List<Caption> list2, ERROR error);

        void onControlCaptionResponseReceived(ERROR error);

        void onMove360ViewResponseReceived(ERROR error);

        void onReset360ViewResponseReceived(ERROR error);

        void onSetAspectRatioResponseReceived(ERROR error);

        void onZoom360ViewResponseReceived(ERROR error);
    }

    public interface IAVPlayerPlaybackResponseListener {
        void onGetMediaInfoResponseReceived(MediaInfo mediaInfo, ERROR error);

        void onGetPlayPositionResponseReceived(long j, ERROR error);

        void onGetStateResponseReceived(AVPlayerState aVPlayerState, ERROR error);

        void onPauseResponseReceived(ERROR error);

        void onPlayResponseReceived(Item item, ContentInfo contentInfo, ERROR error);

        void onResumeResponseReceived(ERROR error);

        void onSeekResponseReceived(long j, ERROR error);

        void onStopResponseReceived(ERROR error);
    }

    public interface IAVPlayerVolumeResponseListener {
        void onGetMuteResponseReceived(boolean z, ERROR error);

        void onGetVolumeResponseReceived(int i, ERROR error);

        void onSetMuteResponseReceived(boolean z, ERROR error);

        void onSetVolumeResponseReceived(int i, ERROR error);
    }

    public abstract void controlCaption(Caption.CaptionOperation captionOperation, Caption caption);

    public abstract String getCaptionFilePathFromURI(String str);

    public abstract void getMediaInfo();

    public abstract void getMute();

    public abstract void getPlayPosition();

    public abstract AVPlayerState getPlayerState();

    public abstract void getState();

    public abstract void getVolume();

    @Deprecated
    public abstract boolean isRedirectSupportable();

    public abstract boolean isSupport360View();

    public abstract boolean isSupportAspectRatio();

    public abstract boolean isSupportAudio();

    public abstract boolean isSupportCaptionControl();

    public abstract boolean isSupportDynamicBuffering();

    public abstract boolean isSupportRedirect();

    public abstract boolean isSupportVideo();

    public abstract void move360View(float f, float f2);

    public abstract void pause();

    public abstract void play(Item item, ContentInfo contentInfo);

    public abstract void prepare(Item item);

    public abstract void requestAspectRatioState();

    public abstract void requestCaptionState();

    public abstract void reset360View();

    public abstract void resume();

    public abstract void seek(long j);

    public abstract void setAspectRatio(String str);

    public abstract void setEventListener(IAVPlayerEventListener iAVPlayerEventListener);

    public abstract void setExtensionEventListener(
            IAVPlayerExtensionEventListener iAVPlayerExtensionEventListener);

    public abstract void setExtensionResponseListener(
            IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener);

    public abstract void setMute(boolean z);

    public abstract void setResponseListener(
            IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener);

    public abstract void setVolume(int i);

    public abstract void setVolumeResponseListener(
            IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener);

    public abstract void skipDynamicBuffering();

    public abstract void stop();

    public abstract void zoom360View(float f);

    protected AVPlayer() {}

    public enum AVPlayerState {
        STOPPED("STOPPED"),
        BUFFERING("BUFFERING"),
        PLAYING("PLAYING"),
        PAUSED("PAUSED"),
        FINISHED("FINISHED"),
        CONTENT_CHANGED("CONTENT_CHANGED"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        AVPlayerState(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static AVPlayerState stringToEnum(String enumStr) {
            if (enumStr == null) {
                return UNKNOWN;
            }
            if (enumStr.equals("BUFFERING")) {
                return BUFFERING;
            }
            if (enumStr.equals("CONTENT_CHANGED")) {
                return CONTENT_CHANGED;
            }
            if (enumStr.equals("PLAYING")) {
                return PLAYING;
            }
            if (enumStr.equals("PAUSED")) {
                return PAUSED;
            }
            if (enumStr.equals("STOPPED")) {
                return STOPPED;
            }
            if (enumStr.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }
}
