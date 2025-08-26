package android.media;

import android.media.CallbackUtil;
import android.media.ILoudnessCodecUpdatesDispatcher;
import android.media.LoudnessCodecController;
import android.media.LoudnessCodecDispatcher;
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

/* loaded from: classes2.dex */
public class LoudnessCodecDispatcher implements CallbackUtil.DispatcherStub {
    private static final boolean DEBUG = false;
    private static final String TAG = "LoudnessCodecDispatcher";
    private final IAudioService mAudioService;

    /* JADX INFO: Access modifiers changed from: private */
    static final class LoudnessCodecUpdatesDispatcherStub extends ILoudnessCodecUpdatesDispatcher.Stub {
        private static LoudnessCodecUpdatesDispatcherStub sLoudnessCodecStub;
        private final CallbackUtil.LazyListenerManager<LoudnessCodecController.OnLoudnessCodecUpdateListener> mLoudnessListenerMgr = new CallbackUtil.LazyListenerManager<>();
        private final Object mLock = new Object();
        private final HashMap<LoudnessCodecController.OnLoudnessCodecUpdateListener, LoudnessCodecController> mConfiguratorListener = new HashMap<>();

        static /* synthetic */ CallbackUtil.DispatcherStub lambda$addLoudnessCodecListener$3(CallbackUtil.DispatcherStub dispatcherStub) {
            return dispatcherStub;
        }

        public static synchronized LoudnessCodecUpdatesDispatcherStub getInstance() {
            if (sLoudnessCodecStub == null) {
                sLoudnessCodecStub = new LoudnessCodecUpdatesDispatcherStub();
            }
            return sLoudnessCodecStub;
        }

        private LoudnessCodecUpdatesDispatcherStub() {
        }

        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(final int i, final PersistableBundle persistableBundle) {
            this.mLoudnessListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchLoudnessCodecParameterChange$2(i, persistableBundle, (LoudnessCodecController.OnLoudnessCodecUpdateListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$2(final int i, final PersistableBundle persistableBundle, LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
            synchronized (this.mLock) {
                this.mConfiguratorListener.computeIfPresent(onLoudnessCodecUpdateListener, new BiFunction() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$dispatchLoudnessCodecParameterChange$1(i, persistableBundle, (LoudnessCodecController.OnLoudnessCodecUpdateListener) obj, (LoudnessCodecController) obj2);
                    }
                });
            }
        }

        static /* synthetic */ LoudnessCodecController lambda$dispatchLoudnessCodecParameterChange$1(int i, final PersistableBundle persistableBundle, final LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, LoudnessCodecController loudnessCodecController) {
            if (loudnessCodecController.getSessionId() == i) {
                loudnessCodecController.mediaCodecsConsume(new Consumer() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$dispatchLoudnessCodecParameterChange$0(persistableBundle, onLoudnessCodecUpdateListener, (Map.Entry) obj);
                    }
                });
            }
            return loudnessCodecController;
        }

        static /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$0(PersistableBundle persistableBundle, LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, Map.Entry entry) {
            boolean z;
            String string = Integer.toString(((LoudnessCodecInfo) entry.getKey()).hashCode());
            Bundle bundle = persistableBundle.containsKey(string) ? new Bundle(persistableBundle.getPersistableBundle(string)) : null;
            for (MediaCodec mediaCodec : (Set) entry.getValue()) {
                String string2 = Integer.toString(mediaCodec.hashCode());
                if (bundle != null || persistableBundle.containsKey(string2)) {
                    if (bundle == null) {
                        bundle = new Bundle(persistableBundle.getPersistableBundle(string2));
                        z = true;
                    } else {
                        z = false;
                    }
                    bundle = filterLoudnessParams(onLoudnessCodecUpdateListener.onLoudnessCodecUpdate(mediaCodec, bundle));
                    if (!bundle.isDefinitelyEmpty()) {
                        try {
                            mediaCodec.setParameters(bundle);
                        } catch (IllegalStateException unused) {
                            Log.w(LoudnessCodecDispatcher.TAG, "Cannot set loudness bundle on media codec " + mediaCodec);
                        }
                    }
                    if (z) {
                        return;
                    }
                }
            }
        }

        private static Bundle filterLoudnessParams(Bundle bundle) {
            Bundle bundle2 = new Bundle();
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL, bundle.getInt(MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION, bundle.getInt(MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_EFFECT_TYPE)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_EFFECT_TYPE, bundle.getInt(MediaFormat.KEY_AAC_DRC_EFFECT_TYPE));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_BOOST_FACTOR)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_BOOST_FACTOR, bundle.getInt(MediaFormat.KEY_AAC_DRC_BOOST_FACTOR));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR, bundle.getInt(MediaFormat.KEY_AAC_DRC_ATTENUATION_FACTOR));
            }
            if (bundle.containsKey(MediaFormat.KEY_AAC_DRC_ALBUM_MODE)) {
                bundle2.putInt(MediaFormat.KEY_AAC_DRC_ALBUM_MODE, bundle.getInt(MediaFormat.KEY_AAC_DRC_ALBUM_MODE));
            }
            return bundle2;
        }

        void addLoudnessCodecListener(final CallbackUtil.DispatcherStub dispatcherStub, LoudnessCodecController loudnessCodecController, Executor executor, LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
            Objects.requireNonNull(loudnessCodecController);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(onLoudnessCodecUpdateListener);
            this.mLoudnessListenerMgr.addListener(executor, onLoudnessCodecUpdateListener, "addLoudnessCodecListener", new Supplier() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$addLoudnessCodecListener$3(dispatcherStub);
                }
            });
            synchronized (this.mLock) {
                this.mConfiguratorListener.put(onLoudnessCodecUpdateListener, loudnessCodecController);
            }
        }

        void removeLoudnessCodecListener(LoudnessCodecController loudnessCodecController) {
            LoudnessCodecController.OnLoudnessCodecUpdateListener key;
            Objects.requireNonNull(loudnessCodecController);
            synchronized (this.mLock) {
                Iterator<Map.Entry<LoudnessCodecController.OnLoudnessCodecUpdateListener, LoudnessCodecController>> it = this.mConfiguratorListener.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        key = null;
                        break;
                    }
                    Map.Entry<LoudnessCodecController.OnLoudnessCodecUpdateListener, LoudnessCodecController> next = it.next();
                    if (next.getValue() == loudnessCodecController) {
                        key = next.getKey();
                        it.remove();
                        break;
                    }
                }
            }
            if (key != null) {
                this.mLoudnessListenerMgr.removeListener(key, "removeLoudnessCodecListener");
            }
        }
    }

    public LoudnessCodecDispatcher(IAudioService iAudioService) {
        this.mAudioService = (IAudioService) Objects.requireNonNull(iAudioService);
    }

    @Override // android.media.CallbackUtil.DispatcherStub
    public void register(boolean z) {
        try {
            if (z) {
                this.mAudioService.registerLoudnessCodecUpdatesDispatcher(LoudnessCodecUpdatesDispatcherStub.getInstance());
            } else {
                this.mAudioService.unregisterLoudnessCodecUpdatesDispatcher(LoudnessCodecUpdatesDispatcherStub.getInstance());
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecListener(LoudnessCodecController loudnessCodecController, Executor executor, LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
        LoudnessCodecUpdatesDispatcherStub.getInstance().addLoudnessCodecListener(this, loudnessCodecController, executor, onLoudnessCodecUpdateListener);
    }

    public void removeLoudnessCodecListener(LoudnessCodecController loudnessCodecController) {
        LoudnessCodecUpdatesDispatcherStub.getInstance().removeLoudnessCodecListener(loudnessCodecController);
    }

    public void startLoudnessCodecUpdates(int i) {
        try {
            this.mAudioService.startLoudnessCodecUpdates(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopLoudnessCodecUpdates(int i) {
        try {
            this.mAudioService.stopLoudnessCodecUpdates(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecInfo(int i, int i2, LoudnessCodecInfo loudnessCodecInfo) {
        try {
            this.mAudioService.addLoudnessCodecInfo(i, i2, loudnessCodecInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void removeLoudnessCodecInfo(int i, LoudnessCodecInfo loudnessCodecInfo) {
        try {
            this.mAudioService.removeLoudnessCodecInfo(i, loudnessCodecInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public Bundle getLoudnessCodecParams(LoudnessCodecInfo loudnessCodecInfo) {
        try {
            return new Bundle(this.mAudioService.getLoudnessParams(loudnessCodecInfo));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }
}
