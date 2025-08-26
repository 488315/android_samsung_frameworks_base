package android.companion.virtual.audio;

import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.companion.virtual.audio.UserRestrictionsDetector;
import android.companion.virtual.audio.VirtualAudioDevice;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecord;
import android.media.AudioRecordingConfiguration;
import android.media.AudioTrack;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.util.IntArray;
import android.util.Log;
import java.io.Closeable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class VirtualAudioSession extends IAudioRoutingCallback.Stub implements UserRestrictionsDetector.UserRestrictionsCallback, Closeable {
    private static final String TAG = "VirtualAudioSession";
    private AudioCapture mAudioCapture;
    private final AudioConfigChangedCallback mAudioConfigChangedCallback;
    private AudioInjection mAudioInjection;
    private AudioPolicy mAudioPolicy;
    private final Context mContext;
    private final Object mLock = new Object();
    private final IntArray mReroutedAppUids = new IntArray();
    private final UserRestrictionsDetector mUserRestrictionsDetector;

    public static final class AudioConfigChangedCallback extends IAudioConfigChangedCallback.Stub {
        private final VirtualAudioDevice.AudioConfigurationChangeCallback mCallback;
        private final Executor mExecutor;

        AudioConfigChangedCallback(Context context, Executor executor, VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
            this.mExecutor = executor == null ? context.getMainExecutor() : executor;
            this.mCallback = audioConfigurationChangeCallback;
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onPlaybackConfigChanged(final List<AudioPlaybackConfiguration> list) {
            if (this.mCallback != null) {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.audio.VirtualAudioSession$AudioConfigChangedCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPlaybackConfigChanged$0(list);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPlaybackConfigChanged$0(List list) {
            this.mCallback.onPlaybackConfigChanged(list);
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onRecordingConfigChanged(final List<AudioRecordingConfiguration> list) {
            if (this.mCallback != null) {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.audio.VirtualAudioSession$AudioConfigChangedCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onRecordingConfigChanged$1(list);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecordingConfigChanged$1(List list) {
            this.mCallback.onRecordingConfigChanged(list);
        }
    }

    public VirtualAudioSession(Context context, VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback, Executor executor) {
        this.mContext = context;
        this.mUserRestrictionsDetector = new UserRestrictionsDetector(context);
        this.mAudioConfigChangedCallback = audioConfigurationChangeCallback == null ? null : new AudioConfigChangedCallback(context, executor, audioConfigurationChangeCallback);
    }

    public AudioCapture startAudioCapture(AudioFormat audioFormat) {
        AudioCapture audioCapture;
        Objects.requireNonNull(audioFormat, "captureFormat must not be null");
        synchronized (this.mLock) {
            if (this.mAudioCapture != null) {
                throw new IllegalStateException("Cannot start capture while another capture is ongoing.");
            }
            AudioCapture audioCapture2 = new AudioCapture(audioFormat);
            this.mAudioCapture = audioCapture2;
            audioCapture2.startRecording();
            audioCapture = this.mAudioCapture;
        }
        return audioCapture;
    }

    public AudioInjection startAudioInjection(AudioFormat audioFormat) {
        AudioInjection audioInjection;
        Objects.requireNonNull(audioFormat, "injectionFormat must not be null");
        synchronized (this.mLock) {
            if (this.mAudioInjection != null) {
                throw new IllegalStateException("Cannot start injection while injection is already ongoing.");
            }
            AudioInjection audioInjection2 = new AudioInjection(audioFormat);
            this.mAudioInjection = audioInjection2;
            audioInjection2.play();
            this.mUserRestrictionsDetector.register(this);
            this.mAudioInjection.setSilent(this.mUserRestrictionsDetector.isUnmuteMicrophoneDisallowed());
            audioInjection = this.mAudioInjection;
        }
        return audioInjection;
    }

    public AudioConfigChangedCallback getAudioConfigChangedListener() {
        return this.mAudioConfigChangedCallback;
    }

    public AudioCapture getAudioCapture() {
        AudioCapture audioCapture;
        synchronized (this.mLock) {
            audioCapture = this.mAudioCapture;
        }
        return audioCapture;
    }

    public AudioInjection getAudioInjection() {
        AudioInjection audioInjection;
        synchronized (this.mLock) {
            audioInjection = this.mAudioInjection;
        }
        return audioInjection;
    }

    @Override // android.companion.virtual.audio.IAudioRoutingCallback
    public void onAppsNeedingAudioRoutingChanged(int[] iArr) {
        synchronized (this.mLock) {
            if (Arrays.equals(this.mReroutedAppUids.toArray(), iArr)) {
                return;
            }
            releaseAudioStreams();
            if (iArr.length == 0) {
                return;
            }
            createAudioStreams(iArr);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mUserRestrictionsDetector.unregister();
        releaseAudioStreams();
        synchronized (this.mLock) {
            AudioCapture audioCapture = this.mAudioCapture;
            if (audioCapture != null) {
                audioCapture.close();
                this.mAudioCapture = null;
            }
            AudioInjection audioInjection = this.mAudioInjection;
            if (audioInjection != null) {
                audioInjection.close();
                this.mAudioInjection = null;
            }
        }
    }

    @Override // android.companion.virtual.audio.UserRestrictionsDetector.UserRestrictionsCallback
    public void onMicrophoneRestrictionChanged(boolean z) {
        synchronized (this.mLock) {
            AudioInjection audioInjection = this.mAudioInjection;
            if (audioInjection != null) {
                audioInjection.setSilent(z);
            }
        }
    }

    private void createAudioStreams(int[] iArr) {
        AudioMix audioMixCreateAudioRecordMix;
        AudioMix audioMixCreateAudioTrackMix;
        synchronized (this.mLock) {
            if (this.mAudioCapture == null && this.mAudioInjection == null) {
                throw new IllegalStateException("At least one of AudioCapture and AudioInjection must be started.");
            }
            if (this.mAudioPolicy != null) {
                throw new IllegalStateException("Cannot create audio streams while the audio policy is registered. Call releaseAudioStreams() first to unregister the previous audio policy.");
            }
            this.mReroutedAppUids.clear();
            for (int i : iArr) {
                this.mReroutedAppUids.add(i);
            }
            AudioPolicy.Builder builder = new AudioPolicy.Builder(this.mContext);
            AudioCapture audioCapture = this.mAudioCapture;
            if (audioCapture != null) {
                audioMixCreateAudioRecordMix = createAudioRecordMix(audioCapture.getFormat(), iArr);
                builder.addMix(audioMixCreateAudioRecordMix);
            } else {
                audioMixCreateAudioRecordMix = null;
            }
            AudioInjection audioInjection = this.mAudioInjection;
            if (audioInjection != null) {
                audioMixCreateAudioTrackMix = createAudioTrackMix(audioInjection.getFormat(), iArr);
                builder.addMix(audioMixCreateAudioTrackMix);
            } else {
                audioMixCreateAudioTrackMix = null;
            }
            this.mAudioPolicy = builder.build();
            if (((AudioManager) this.mContext.getSystemService(AudioManager.class)).registerAudioPolicy(this.mAudioPolicy) == -1) {
                Log.e(TAG, "Failed to register audio policy!");
            }
            AudioRecord audioRecordCreateAudioRecordSink = audioMixCreateAudioRecordMix != null ? this.mAudioPolicy.createAudioRecordSink(audioMixCreateAudioRecordMix) : null;
            AudioTrack audioTrackCreateAudioTrackSource = audioMixCreateAudioTrackMix != null ? this.mAudioPolicy.createAudioTrackSource(audioMixCreateAudioTrackMix) : null;
            AudioCapture audioCapture2 = this.mAudioCapture;
            if (audioCapture2 != null) {
                audioCapture2.setAudioRecord(audioRecordCreateAudioRecordSink);
            }
            AudioInjection audioInjection2 = this.mAudioInjection;
            if (audioInjection2 != null) {
                audioInjection2.setAudioTrack(audioTrackCreateAudioTrackSource);
            }
        }
    }

    private void releaseAudioStreams() {
        synchronized (this.mLock) {
            AudioCapture audioCapture = this.mAudioCapture;
            if (audioCapture != null) {
                audioCapture.setAudioRecord(null);
            }
            AudioInjection audioInjection = this.mAudioInjection;
            if (audioInjection != null) {
                audioInjection.setAudioTrack(null);
            }
            this.mReroutedAppUids.clear();
            if (this.mAudioPolicy != null) {
                ((AudioManager) this.mContext.getSystemService(AudioManager.class)).unregisterAudioPolicy(this.mAudioPolicy);
                this.mAudioPolicy = null;
                Log.i(TAG, "AudioPolicy unregistered");
            }
        }
    }

    public IntArray getReroutedAppUids() {
        IntArray intArray;
        synchronized (this.mLock) {
            intArray = this.mReroutedAppUids;
        }
        return intArray;
    }

    private static AudioMix createAudioRecordMix(AudioFormat audioFormat, int[] iArr) throws IllegalArgumentException {
        AudioMixingRule.Builder builder = new AudioMixingRule.Builder();
        builder.setTargetMixRole(0);
        for (int i : iArr) {
            builder.addMixRule(4, Integer.valueOf(i));
        }
        return new AudioMix.Builder(builder.allowPrivilegedPlaybackCapture(false).build()).setFormat(audioFormat).setRouteFlags(2).build();
    }

    private static AudioMix createAudioTrackMix(AudioFormat audioFormat, int[] iArr) throws IllegalArgumentException {
        AudioMixingRule.Builder builder = new AudioMixingRule.Builder();
        builder.setTargetMixRole(1);
        for (int i : iArr) {
            builder.addMixRule(4, Integer.valueOf(i));
        }
        return new AudioMix.Builder(builder.build()).setFormat(audioFormat).setRouteFlags(2).build();
    }
}
