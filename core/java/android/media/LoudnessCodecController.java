package android.media;

import android.media.permission.SafeCloseable;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LoudnessCodecController implements SafeCloseable {
    private static final String TAG = "LoudnessCodecController";
    private final LoudnessCodecDispatcher mLcDispatcher;
    private final int mSessionId;
    private final Object mControllerLock = new Object();
    private final HashMap<LoudnessCodecInfo, Set<MediaCodec>> mMediaCodecs = new HashMap<>();

    public interface OnLoudnessCodecUpdateListener {
        default Bundle onLoudnessCodecUpdate(MediaCodec mediaCodec, Bundle codecValues) {
            return codecValues;
        }
    }

    public static LoudnessCodecController create(int sessionId) {
        LoudnessCodecDispatcher dispatcher = new LoudnessCodecDispatcher(AudioManager.getService());
        LoudnessCodecController controller = new LoudnessCodecController(dispatcher, sessionId);
        dispatcher.addLoudnessCodecListener(
                controller,
                Executors.newSingleThreadExecutor(),
                new OnLoudnessCodecUpdateListener() { // from class:
                    // android.media.LoudnessCodecController.1
                });
        dispatcher.startLoudnessCodecUpdates(sessionId);
        return controller;
    }

    public static LoudnessCodecController create(
            int sessionId, Executor executor, OnLoudnessCodecUpdateListener listener) {
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(listener, "OnLoudnessCodecUpdateListener cannot be null");
        LoudnessCodecDispatcher dispatcher = new LoudnessCodecDispatcher(AudioManager.getService());
        LoudnessCodecController controller = new LoudnessCodecController(dispatcher, sessionId);
        dispatcher.addLoudnessCodecListener(controller, executor, listener);
        dispatcher.startLoudnessCodecUpdates(sessionId);
        return controller;
    }

    public static LoudnessCodecController createForTesting(
            int sessionId,
            Executor executor,
            OnLoudnessCodecUpdateListener listener,
            IAudioService service) {
        Objects.requireNonNull(service, "IAudioService cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(listener, "OnLoudnessCodecUpdateListener cannot be null");
        LoudnessCodecDispatcher dispatcher = new LoudnessCodecDispatcher(service);
        LoudnessCodecController controller = new LoudnessCodecController(dispatcher, sessionId);
        dispatcher.addLoudnessCodecListener(controller, executor, listener);
        dispatcher.startLoudnessCodecUpdates(sessionId);
        return controller;
    }

    private LoudnessCodecController(LoudnessCodecDispatcher lcDispatcher, int sessionId) {
        this.mLcDispatcher =
                (LoudnessCodecDispatcher)
                        Objects.requireNonNull(lcDispatcher, "Dispatcher cannot be null");
        this.mSessionId = sessionId;
    }

    public boolean addMediaCodec(MediaCodec mediaCodec) {
        final MediaCodec mc =
                (MediaCodec)
                        Objects.requireNonNull(
                                mediaCodec, "MediaCodec for addMediaCodec cannot be null");
        LoudnessCodecInfo mcInfo = getCodecInfo(mc);
        if (mcInfo == null) {
            Log.v(TAG, "Could not extract codec loudness information");
            return false;
        }
        synchronized (this.mControllerLock) {
            final AtomicBoolean containsCodec = new AtomicBoolean(false);
            if (this.mMediaCodecs.computeIfPresent(
                            mcInfo,
                            new BiFunction() { // from class:
                                // android.media.LoudnessCodecController$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiFunction
                                public final Object apply(Object obj, Object obj2) {
                                    return LoudnessCodecController.lambda$addMediaCodec$0(
                                            containsCodec, mc, (LoudnessCodecInfo) obj, (Set) obj2);
                                }
                            })
                    == null) {
                Set<MediaCodec> newSet = new HashSet<>();
                newSet.add(mc);
                this.mMediaCodecs.put(mcInfo, newSet);
            }
            if (containsCodec.get()) {
                throw new IllegalArgumentException(
                        "Loudness controller already added " + mediaCodec);
            }
        }
        this.mLcDispatcher.addLoudnessCodecInfo(this.mSessionId, mediaCodec.hashCode(), mcInfo);
        return true;
    }

    static /* synthetic */ Set lambda$addMediaCodec$0(
            AtomicBoolean containsCodec, MediaCodec mc, LoudnessCodecInfo info, Set codecSet) {
        containsCodec.set(!codecSet.add(mc));
        return codecSet;
    }

    public void removeMediaCodec(final MediaCodec mediaCodec) {
        final AtomicBoolean removedMc = new AtomicBoolean(false);
        final AtomicBoolean removeInfo = new AtomicBoolean(false);
        LoudnessCodecInfo mcInfo =
                getCodecInfo(
                        (MediaCodec)
                                Objects.requireNonNull(
                                        mediaCodec,
                                        "MediaCodec for removeMediaCodec cannot be null"));
        if (mcInfo == null) {
            throw new IllegalArgumentException("Could not extract codec loudness information");
        }
        synchronized (this.mControllerLock) {
            this.mMediaCodecs.computeIfPresent(
                    mcInfo,
                    new BiFunction() { // from class:
                        // android.media.LoudnessCodecController$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return LoudnessCodecController.lambda$removeMediaCodec$1(
                                    removedMc,
                                    mediaCodec,
                                    removeInfo,
                                    (LoudnessCodecInfo) obj,
                                    (Set) obj2);
                        }
                    });
            if (!removedMc.get()) {
                throw new IllegalArgumentException(
                        "Loudness controller does not contain " + mediaCodec);
            }
        }
        if (removeInfo.get()) {
            this.mLcDispatcher.removeLoudnessCodecInfo(this.mSessionId, mcInfo);
        }
    }

    static /* synthetic */ Set lambda$removeMediaCodec$1(
            AtomicBoolean removedMc,
            MediaCodec mediaCodec,
            AtomicBoolean removeInfo,
            LoudnessCodecInfo format,
            Set mcs) {
        removedMc.set(mcs.remove(mediaCodec));
        if (mcs.isEmpty()) {
            removeInfo.set(true);
            return null;
        }
        return mcs;
    }

    public Bundle getLoudnessCodecParams(MediaCodec mediaCodec) {
        Objects.requireNonNull(mediaCodec, "MediaCodec cannot be null");
        LoudnessCodecInfo codecInfo = getCodecInfo(mediaCodec);
        if (codecInfo == null) {
            throw new IllegalArgumentException("MediaCodec does not have valid codec information");
        }
        synchronized (this.mControllerLock) {
            Set<MediaCodec> codecs = this.mMediaCodecs.get(codecInfo);
            if (codecs == null || !codecs.contains(mediaCodec)) {
                throw new IllegalArgumentException(
                        "MediaCodec was not added for loudness annotation");
            }
        }
        return this.mLcDispatcher.getLoudnessCodecParams(codecInfo);
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mControllerLock) {
            this.mMediaCodecs.clear();
        }
        this.mLcDispatcher.stopLoudnessCodecUpdates(this.mSessionId);
    }

    int getSessionId() {
        return this.mSessionId;
    }

    void mediaCodecsConsume(Consumer<Map.Entry<LoudnessCodecInfo, Set<MediaCodec>>> consumer) {
        synchronized (this.mControllerLock) {
            for (Map.Entry<LoudnessCodecInfo, Set<MediaCodec>> entry :
                    this.mMediaCodecs.entrySet()) {
                consumer.accept(entry);
            }
        }
    }

    private static LoudnessCodecInfo getCodecInfo(MediaCodec mediaCodec) {
        LoudnessCodecInfo lci = new LoudnessCodecInfo();
        MediaCodecInfo codecInfo = mediaCodec.getCodecInfo();
        if (codecInfo.isEncoder()) {
            Log.w(TAG, "MediaCodec used for encoding does not support loudness annotation");
            return null;
        }
        try {
            MediaFormat inputFormat = mediaCodec.getInputFormat();
            String mimeType = inputFormat.getString("mime");
            if ("audio/mp4a-latm".equalsIgnoreCase(mimeType)) {
                int aacProfile = -1;
                int profile = -1;
                try {
                    aacProfile = inputFormat.getInteger(MediaFormat.KEY_AAC_PROFILE);
                } catch (NullPointerException e) {
                }
                try {
                    profile = inputFormat.getInteger("profile");
                } catch (NullPointerException e2) {
                }
                boolean z = true;
                if (aacProfile == 42 || profile == 42) {
                    lci.metadataType = 2;
                } else {
                    lci.metadataType = 1;
                }
                MediaFormat outputFormat = mediaCodec.getOutputFormat();
                if (outputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                        >= inputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)) {
                    z = false;
                }
                lci.isDownmixing = z;
                return lci;
            }
            Log.w(TAG, "MediaCodec mime type not supported for loudness annotation");
            return null;
        } catch (IllegalStateException e3) {
            Log.e(TAG, "MediaCodec is not configured", e3);
            return null;
        }
    }
}
