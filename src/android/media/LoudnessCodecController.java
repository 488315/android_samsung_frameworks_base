package android.media;

import android.media.permission.SafeCloseable;
import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class LoudnessCodecController implements SafeCloseable {
    private static final String TAG = "LoudnessCodecController";
    private final LoudnessCodecDispatcher mLcDispatcher;
    private final int mSessionId;
    private final Object mControllerLock = new Object();
    private final HashMap<LoudnessCodecInfo, Set<MediaCodec>> mMediaCodecs = new HashMap<>();

    public interface OnLoudnessCodecUpdateListener {
        default Bundle onLoudnessCodecUpdate(MediaCodec mediaCodec, Bundle bundle) {
            return bundle;
        }
    }

    public static LoudnessCodecController create(int i) {
        LoudnessCodecDispatcher loudnessCodecDispatcher = new LoudnessCodecDispatcher(AudioManager.getService());
        LoudnessCodecController loudnessCodecController = new LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, Executors.newSingleThreadExecutor(), new OnLoudnessCodecUpdateListener() { // from class: android.media.LoudnessCodecController.1
        });
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    public static LoudnessCodecController create(int i, Executor executor, OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(onLoudnessCodecUpdateListener, "OnLoudnessCodecUpdateListener cannot be null");
        LoudnessCodecDispatcher loudnessCodecDispatcher = new LoudnessCodecDispatcher(AudioManager.getService());
        LoudnessCodecController loudnessCodecController = new LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, executor, onLoudnessCodecUpdateListener);
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    public static LoudnessCodecController createForTesting(int i, Executor executor, OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, IAudioService iAudioService) {
        Objects.requireNonNull(iAudioService, "IAudioService cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(onLoudnessCodecUpdateListener, "OnLoudnessCodecUpdateListener cannot be null");
        LoudnessCodecDispatcher loudnessCodecDispatcher = new LoudnessCodecDispatcher(iAudioService);
        LoudnessCodecController loudnessCodecController = new LoudnessCodecController(loudnessCodecDispatcher, i);
        loudnessCodecDispatcher.addLoudnessCodecListener(loudnessCodecController, executor, onLoudnessCodecUpdateListener);
        loudnessCodecDispatcher.startLoudnessCodecUpdates(i);
        return loudnessCodecController;
    }

    private LoudnessCodecController(LoudnessCodecDispatcher loudnessCodecDispatcher, int i) {
        this.mLcDispatcher = (LoudnessCodecDispatcher) Objects.requireNonNull(loudnessCodecDispatcher, "Dispatcher cannot be null");
        this.mSessionId = i;
    }

    public boolean addMediaCodec(MediaCodec mediaCodec) {
        final MediaCodec mediaCodec2 = (MediaCodec) Objects.requireNonNull(mediaCodec, "MediaCodec for addMediaCodec cannot be null");
        LoudnessCodecInfo codecInfo = getCodecInfo(mediaCodec2);
        if (codecInfo == null) {
            Log.v(TAG, "Could not extract codec loudness information");
            return false;
        }
        synchronized (this.mControllerLock) {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            if (this.mMediaCodecs.computeIfPresent(codecInfo, new BiFunction() { // from class: android.media.LoudnessCodecController$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return LoudnessCodecController.lambda$addMediaCodec$0(atomicBoolean, mediaCodec2, (LoudnessCodecInfo) obj, (Set) obj2);
                }
            }) == null) {
                HashSet hashSet = new HashSet();
                hashSet.add(mediaCodec2);
                this.mMediaCodecs.put(codecInfo, hashSet);
            }
            if (atomicBoolean.get()) {
                throw new IllegalArgumentException("Loudness controller already added " + mediaCodec);
            }
        }
        this.mLcDispatcher.addLoudnessCodecInfo(this.mSessionId, mediaCodec.hashCode(), codecInfo);
        return true;
    }

    static /* synthetic */ Set lambda$addMediaCodec$0(AtomicBoolean atomicBoolean, MediaCodec mediaCodec, LoudnessCodecInfo loudnessCodecInfo, Set set) {
        atomicBoolean.set(!set.add(mediaCodec));
        return set;
    }

    public void removeMediaCodec(final MediaCodec mediaCodec) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        LoudnessCodecInfo codecInfo = getCodecInfo((MediaCodec) Objects.requireNonNull(mediaCodec, "MediaCodec for removeMediaCodec cannot be null"));
        if (codecInfo == null) {
            throw new IllegalArgumentException("Could not extract codec loudness information");
        }
        synchronized (this.mControllerLock) {
            this.mMediaCodecs.computeIfPresent(codecInfo, new BiFunction() { // from class: android.media.LoudnessCodecController$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return LoudnessCodecController.lambda$removeMediaCodec$1(atomicBoolean, mediaCodec, atomicBoolean2, (LoudnessCodecInfo) obj, (Set) obj2);
                }
            });
            if (!atomicBoolean.get()) {
                throw new IllegalArgumentException("Loudness controller does not contain " + mediaCodec);
            }
        }
        if (atomicBoolean2.get()) {
            this.mLcDispatcher.removeLoudnessCodecInfo(this.mSessionId, codecInfo);
        }
    }

    static /* synthetic */ Set lambda$removeMediaCodec$1(AtomicBoolean atomicBoolean, MediaCodec mediaCodec, AtomicBoolean atomicBoolean2, LoudnessCodecInfo loudnessCodecInfo, Set set) {
        atomicBoolean.set(set.remove(mediaCodec));
        if (!set.isEmpty()) {
            return set;
        }
        atomicBoolean2.set(true);
        return null;
    }

    public Bundle getLoudnessCodecParams(MediaCodec mediaCodec) {
        Objects.requireNonNull(mediaCodec, "MediaCodec cannot be null");
        LoudnessCodecInfo codecInfo = getCodecInfo(mediaCodec);
        if (codecInfo == null) {
            throw new IllegalArgumentException("MediaCodec does not have valid codec information");
        }
        synchronized (this.mControllerLock) {
            Set<MediaCodec> set = this.mMediaCodecs.get(codecInfo);
            if (set == null || !set.contains(mediaCodec)) {
                throw new IllegalArgumentException("MediaCodec was not added for loudness annotation");
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
            Iterator<Map.Entry<LoudnessCodecInfo, Set<MediaCodec>>> it = this.mMediaCodecs.entrySet().iterator();
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }
    }

    private static LoudnessCodecInfo getCodecInfo(MediaCodec mediaCodec) {
        int i;
        LoudnessCodecInfo loudnessCodecInfo = new LoudnessCodecInfo();
        if (mediaCodec.getCodecInfo().isEncoder()) {
            Log.w(TAG, "MediaCodec used for encoding does not support loudness annotation");
            return null;
        }
        try {
            MediaFormat inputFormat = mediaCodec.getInputFormat();
            if ("audio/mp4a-latm".equalsIgnoreCase(inputFormat.getString("mime"))) {
                int i2 = -1;
                try {
                    i = inputFormat.getInteger(MediaFormat.KEY_AAC_PROFILE);
                } catch (NullPointerException unused) {
                    i = -1;
                }
                try {
                    i2 = inputFormat.getInteger("profile");
                } catch (NullPointerException unused2) {
                }
                boolean z = true;
                if (i == 42 || i2 == 42) {
                    loudnessCodecInfo.metadataType = 2;
                } else {
                    loudnessCodecInfo.metadataType = 1;
                }
                if (mediaCodec.getOutputFormat().getInteger(MediaFormat.KEY_CHANNEL_COUNT) >= inputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)) {
                    z = false;
                }
                loudnessCodecInfo.isDownmixing = z;
                return loudnessCodecInfo;
            }
            Log.w(TAG, "MediaCodec mime type not supported for loudness annotation");
            return null;
        } catch (IllegalStateException e) {
            Log.e(TAG, "MediaCodec is not configured", e);
            return null;
        }
    }
}
