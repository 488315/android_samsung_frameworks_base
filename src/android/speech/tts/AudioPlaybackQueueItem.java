package android.speech.tts;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.ConditionVariable;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

/* loaded from: classes3.dex */
class AudioPlaybackQueueItem extends PlaybackQueueItem {
    private static final String TAG = "TTS.AudioQueueItem";
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private final Context mContext;
    private final ConditionVariable mDone;
    private volatile boolean mFinished;
    private MediaPlayer mPlayer;
    private final Uri mUri;

    private static final float clip(float f, float f2, float f3) {
        return f < f2 ? f2 : f < f3 ? f : f3;
    }

    AudioPlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object obj, Context context, Uri uri, TextToSpeechService.AudioOutputParams audioOutputParams) {
        super(utteranceProgressDispatcher, obj);
        this.mContext = context;
        this.mUri = uri;
        this.mAudioParams = audioOutputParams;
        this.mDone = new ConditionVariable();
        this.mPlayer = null;
        this.mFinished = false;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
        dispatcher.dispatchOnStart();
        int i = this.mAudioParams.mSessionId;
        Context context = this.mContext;
        Uri uri = this.mUri;
        AudioAttributes audioAttributes = this.mAudioParams.mAudioAttributes;
        if (i <= 0) {
            i = 0;
        }
        MediaPlayer create = MediaPlayer.create(context, uri, null, audioAttributes, i);
        this.mPlayer = create;
        if (create == null) {
            dispatcher.dispatchOnError(-5);
            return;
        }
        try {
            create.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: android.speech.tts.AudioPlaybackQueueItem.1
                @Override // android.media.MediaPlayer.OnErrorListener
                public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
                    Log.w(AudioPlaybackQueueItem.TAG, "Audio playback error: " + i2 + ", " + i3);
                    AudioPlaybackQueueItem.this.mDone.open();
                    return true;
                }
            });
            this.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: android.speech.tts.AudioPlaybackQueueItem.2
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer) {
                    AudioPlaybackQueueItem.this.mFinished = true;
                    AudioPlaybackQueueItem.this.mDone.open();
                }
            });
            setupVolume(this.mPlayer, this.mAudioParams.mVolume, this.mAudioParams.mPan);
            this.mPlayer.start();
            this.mDone.block();
            finish();
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "MediaPlayer failed", e);
            this.mDone.open();
        }
        if (this.mFinished) {
            dispatcher.dispatchOnSuccess();
        } else {
            dispatcher.dispatchOnStop();
        }
    }

    private static void setupVolume(MediaPlayer mediaPlayer, float f, float f2) {
        float f3;
        float clip = clip(f, 0.0f, 1.0f);
        float clip2 = clip(f2, -1.0f, 1.0f);
        if (clip2 > 0.0f) {
            float f4 = (1.0f - clip2) * clip;
            f3 = clip;
            clip = f4;
        } else {
            f3 = clip2 < 0.0f ? (clip2 + 1.0f) * clip : clip;
        }
        mediaPlayer.setVolume(clip, f3);
    }

    private void finish() {
        try {
            this.mPlayer.stop();
        } catch (IllegalStateException unused) {
        }
        this.mPlayer.release();
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        this.mDone.open();
    }
}
