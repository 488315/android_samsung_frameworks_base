package android.media;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LoudnessCodecDispatcher implements CallbackUtil.DispatcherStub {
    private static final boolean DEBUG = false;
    private static final String TAG = "LoudnessCodecDispatcher";
    private final IAudioService mAudioService;

    static final class LoudnessCodecUpdatesDispatcherStub
            extends ILoudnessCodecUpdatesDispatcher.Stub {
        private static LoudnessCodecUpdatesDispatcherStub sLoudnessCodecStub;
        private final CallbackUtil.LazyListenerManager<
                        LoudnessCodecController.OnLoudnessCodecUpdateListener>
                mLoudnessListenerMgr = new CallbackUtil.LazyListenerManager<>();
        private final Object mLock = new Object();
        private final HashMap<
                        LoudnessCodecController.OnLoudnessCodecUpdateListener,
                        LoudnessCodecController>
                mConfiguratorListener = new HashMap<>();

        public static synchronized LoudnessCodecUpdatesDispatcherStub getInstance() {
            LoudnessCodecUpdatesDispatcherStub loudnessCodecUpdatesDispatcherStub;
            synchronized (LoudnessCodecUpdatesDispatcherStub.class) {
                if (sLoudnessCodecStub == null) {
                    sLoudnessCodecStub = new LoudnessCodecUpdatesDispatcherStub();
                }
                loudnessCodecUpdatesDispatcherStub = sLoudnessCodecStub;
            }
            return loudnessCodecUpdatesDispatcherStub;
        }

        private LoudnessCodecUpdatesDispatcherStub() {}

        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(
                final int sessionId, final PersistableBundle params) {
            this.mLoudnessListenerMgr.callListeners(
                    new CallbackUtil.CallbackMethod() { // from class:
                        // android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda0
                        @Override // android.media.CallbackUtil.CallbackMethod
                        public final void callbackMethod(Object obj) {
                            LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.this
                                    .lambda$dispatchLoudnessCodecParameterChange$2(
                                            sessionId,
                                            params,
                                            (LoudnessCodecController.OnLoudnessCodecUpdateListener)
                                                    obj);
                        }
                    });
        }

        public /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$2(
                final int sessionId,
                final PersistableBundle params,
                LoudnessCodecController.OnLoudnessCodecUpdateListener listener) {
            synchronized (this.mLock) {
                this.mConfiguratorListener.computeIfPresent(
                        listener,
                        new BiFunction() { // from class:
                            // android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                return LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub
                                        .lambda$dispatchLoudnessCodecParameterChange$1(
                                                sessionId,
                                                params,
                                                (LoudnessCodecController
                                                                .OnLoudnessCodecUpdateListener)
                                                        obj,
                                                (LoudnessCodecController) obj2);
                            }
                        });
            }
        }

        static /* synthetic */ LoudnessCodecController
                lambda$dispatchLoudnessCodecParameterChange$1(
                        int sessionId,
                        final PersistableBundle params,
                        final LoudnessCodecController.OnLoudnessCodecUpdateListener l,
                        LoudnessCodecController lcConfig) {
            if (lcConfig.getSessionId() == sessionId) {
                lcConfig.mediaCodecsConsume(
                        new Consumer() { // from class:
                            // android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub
                                        .lambda$dispatchLoudnessCodecParameterChange$0(
                                                PersistableBundle.this, l, (Map.Entry) obj);
                            }
                        });
            }
            return lcConfig;
        }

        static /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$0(
                PersistableBundle params,
                LoudnessCodecController.OnLoudnessCodecUpdateListener l,
                Map.Entry mcEntry) {
            LoudnessCodecInfo codecInfo = (LoudnessCodecInfo) mcEntry.getKey();
            String infoKey = Integer.toString(codecInfo.hashCode());
            Bundle bundle = null;
            if (params.containsKey(infoKey)) {
                bundle = new Bundle(params.getPersistableBundle(infoKey));
            }
            Set<MediaCodec> mediaCodecs = (Set) mcEntry.getValue();
            for (MediaCodec mediaCodec : mediaCodecs) {
                String mediaCodecKey = Integer.toString(mediaCodec.hashCode());
                if (bundle != null || params.containsKey(mediaCodecKey)) {
                    boolean canBreak = false;
                    if (bundle == null) {
                        bundle = new Bundle(params.getPersistableBundle(mediaCodecKey));
                        canBreak = true;
                    }
                    bundle = filterLoudnessParams(l.onLoudnessCodecUpdate(mediaCodec, bundle));
                    if (!bundle.isDefinitelyEmpty()) {
                        try {
                            mediaCodec.setParameters(bundle);
                        } catch (IllegalStateException e) {
                            Log.w(
                                    LoudnessCodecDispatcher.TAG,
                                    "Cannot set loudness bundle on media codec " + mediaCodec);
                        }
                    }
                    if (canBreak) {
                        return;
                    }
                }
            }
        }

        private static Bundle filterLoudnessParams(Bundle bundle) {
            Bundle filteredBundle = new Bundle();
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_EFFECT_TYPE)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_EFFECT_TYPE,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_EFFECT_TYPE));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_BOOST_FACTOR)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_BOOST_FACTOR,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_BOOST_FACTOR));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_ALBUM_MODE)) {
                filteredBundle.putInt(
                        MediaFormat.KEY_AAC_DRC_ALBUM_MODE,
                        bundle.getInt(MediaFormat.KEY_AAC_DRC_ALBUM_MODE));
            }
            return filteredBundle;
        }

        void addLoudnessCodecListener(
                final CallbackUtil.DispatcherStub dispatcher,
                LoudnessCodecController configurator,
                Executor executor,
                LoudnessCodecController.OnLoudnessCodecUpdateListener listener) {
            Objects.requireNonNull(configurator);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(listener);
            this.mLoudnessListenerMgr.addListener(
                    executor,
                    listener,
                    "addLoudnessCodecListener",
                    new Supplier() { // from class:
                        // android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub
                                    .lambda$addLoudnessCodecListener$3(
                                            CallbackUtil.DispatcherStub.this);
                        }
                    });
            synchronized (this.mLock) {
                this.mConfiguratorListener.put(listener, configurator);
            }
        }

        static /* synthetic */ CallbackUtil.DispatcherStub lambda$addLoudnessCodecListener$3(
                CallbackUtil.DispatcherStub dispatcher) {
            return dispatcher;
        }

        void removeLoudnessCodecListener(LoudnessCodecController configurator) {
            Objects.requireNonNull(configurator);
            LoudnessCodecController.OnLoudnessCodecUpdateListener listenerToRemove = null;
            synchronized (this.mLock) {
                Iterator<
                                Map.Entry<
                                        LoudnessCodecController.OnLoudnessCodecUpdateListener,
                                        LoudnessCodecController>>
                        iterator = this.mConfiguratorListener.entrySet().iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    Map.Entry<
                                    LoudnessCodecController.OnLoudnessCodecUpdateListener,
                                    LoudnessCodecController>
                            e = iterator.next();
                    if (e.getValue() == configurator) {
                        LoudnessCodecController.OnLoudnessCodecUpdateListener listener = e.getKey();
                        iterator.remove();
                        listenerToRemove = listener;
                        break;
                    }
                }
            }
            if (listenerToRemove != null) {
                this.mLoudnessListenerMgr.removeListener(
                        listenerToRemove, "removeLoudnessCodecListener");
            }
        }
    }

    public LoudnessCodecDispatcher(IAudioService audioService) {
        this.mAudioService = (IAudioService) Objects.requireNonNull(audioService);
    }

    @Override // android.media.CallbackUtil.DispatcherStub
    public void register(boolean register) {
        try {
            if (register) {
                this.mAudioService.registerLoudnessCodecUpdatesDispatcher(
                        LoudnessCodecUpdatesDispatcherStub.getInstance());
            } else {
                this.mAudioService.unregisterLoudnessCodecUpdatesDispatcher(
                        LoudnessCodecUpdatesDispatcherStub.getInstance());
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecListener(
            LoudnessCodecController configurator,
            Executor executor,
            LoudnessCodecController.OnLoudnessCodecUpdateListener listener) {
        LoudnessCodecUpdatesDispatcherStub.getInstance()
                .addLoudnessCodecListener(this, configurator, executor, listener);
    }

    public void removeLoudnessCodecListener(LoudnessCodecController configurator) {
        LoudnessCodecUpdatesDispatcherStub.getInstance().removeLoudnessCodecListener(configurator);
    }

    public void startLoudnessCodecUpdates(int sessionId) {
        try {
            this.mAudioService.startLoudnessCodecUpdates(sessionId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopLoudnessCodecUpdates(int sessionId) {
        try {
            this.mAudioService.stopLoudnessCodecUpdates(sessionId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecInfo(int sessionId, int mediaCodecHash, LoudnessCodecInfo mcInfo) {
        try {
            this.mAudioService.addLoudnessCodecInfo(sessionId, mediaCodecHash, mcInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void removeLoudnessCodecInfo(int sessionId, LoudnessCodecInfo mcInfo) {
        try {
            this.mAudioService.removeLoudnessCodecInfo(sessionId, mcInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public Bundle getLoudnessCodecParams(LoudnessCodecInfo mcInfo) {
        try {
            Bundle loudnessParams = new Bundle(this.mAudioService.getLoudnessParams(mcInfo));
            return loudnessParams;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }
}
