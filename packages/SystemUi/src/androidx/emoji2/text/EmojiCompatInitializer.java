package androidx.emoji2.text;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ProcessLifecycleInitializer;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EmojiCompatInitializer implements Initializer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackgroundDefaultConfig extends EmojiCompat.Config {
        public BackgroundDefaultConfig(Context context) {
            super(new BackgroundDefaultLoader(context));
            this.mMetadataLoadStrategy = 1;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackgroundDefaultLoader implements EmojiCompat.MetadataRepoLoader {
        public final Context mContext;

        public BackgroundDefaultLoader(Context context) {
            this.mContext = context.getApplicationContext();
        }

        @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoader
        public final void load(final EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback) {
            final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ConcurrencyHelpers$$ExternalSyntheticLambda0("EmojiCompatInitializer"));
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            threadPoolExecutor.execute(new Runnable() { // from class: androidx.emoji2.text.EmojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiCompatInitializer.BackgroundDefaultLoader backgroundDefaultLoader = EmojiCompatInitializer.BackgroundDefaultLoader.this;
                    EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback2 = metadataRepoLoaderCallback;
                    ThreadPoolExecutor threadPoolExecutor2 = threadPoolExecutor;
                    backgroundDefaultLoader.getClass();
                    try {
                        FontRequestEmojiCompatConfig create = DefaultEmojiCompatConfig.create(backgroundDefaultLoader.mContext);
                        if (create == null) {
                            throw new RuntimeException("EmojiCompat font provider not available on this device.");
                        }
                        FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader = (FontRequestEmojiCompatConfig.FontRequestMetadataLoader) create.mMetadataLoader;
                        synchronized (fontRequestMetadataLoader.mLock) {
                            fontRequestMetadataLoader.mExecutor = threadPoolExecutor2;
                        }
                        create.mMetadataLoader.load(new EmojiCompat.MetadataRepoLoaderCallback(backgroundDefaultLoader, metadataRepoLoaderCallback2, threadPoolExecutor2) { // from class: androidx.emoji2.text.EmojiCompatInitializer.BackgroundDefaultLoader.1
                            public final /* synthetic */ ThreadPoolExecutor val$executor;
                            public final /* synthetic */ EmojiCompat.MetadataRepoLoaderCallback val$loaderCallback;

                            {
                                this.val$loaderCallback = metadataRepoLoaderCallback2;
                                this.val$executor = threadPoolExecutor2;
                            }

                            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                            public final void onFailed(Throwable th) {
                                ThreadPoolExecutor threadPoolExecutor3 = this.val$executor;
                                try {
                                    this.val$loaderCallback.onFailed(th);
                                } finally {
                                    threadPoolExecutor3.shutdown();
                                }
                            }

                            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                            public final void onLoaded(MetadataRepo metadataRepo) {
                                ThreadPoolExecutor threadPoolExecutor3 = this.val$executor;
                                try {
                                    this.val$loaderCallback.onLoaded(metadataRepo);
                                } finally {
                                    threadPoolExecutor3.shutdown();
                                }
                            }
                        });
                    } catch (Throwable th) {
                        metadataRepoLoaderCallback2.onFailed(th);
                        threadPoolExecutor2.shutdown();
                    }
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoadEmojiCompatRunnable implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                Trace.beginSection("EmojiCompat.EmojiCompatInitializer.run");
                if (EmojiCompat.sInstance != null) {
                    EmojiCompat.get().load();
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    @Override // androidx.startup.Initializer
    public final List dependencies() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    @Override // androidx.startup.Initializer
    public final Boolean create(Context context) {
        Object obj;
        EmojiCompat.init(new BackgroundDefaultConfig(context));
        AppInitializer appInitializer = AppInitializer.getInstance(context);
        appInitializer.getClass();
        synchronized (AppInitializer.sLock) {
            obj = ((HashMap) appInitializer.mInitialized).get(ProcessLifecycleInitializer.class);
            if (obj == null) {
                obj = appInitializer.doInitialize(ProcessLifecycleInitializer.class, new HashSet());
            }
        }
        final LifecycleRegistry lifecycle = ((LifecycleOwner) obj).getLifecycle();
        lifecycle.addObserver(new DefaultLifecycleObserver() { // from class: androidx.emoji2.text.EmojiCompatInitializer.1
            @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
            public final void onResume$1() {
                EmojiCompatInitializer.this.getClass();
                Handler.createAsync(Looper.getMainLooper()).postDelayed(new LoadEmojiCompatRunnable(), 500L);
                lifecycle.removeObserver(this);
            }
        });
        return Boolean.TRUE;
    }
}
