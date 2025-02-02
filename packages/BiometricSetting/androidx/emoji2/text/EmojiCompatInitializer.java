package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import androidx.core.provider.FontRequest;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ProcessLifecycleInitializer;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class EmojiCompatInitializer implements Initializer<Boolean> {

    static class BackgroundDefaultConfig extends EmojiCompat.Config {
        protected BackgroundDefaultConfig(Context context) {
            super(new BackgroundDefaultLoader(context));
            this.mMetadataLoadStrategy = 1;
        }
    }

    static class BackgroundDefaultLoader implements EmojiCompat.MetadataRepoLoader {
        private final Context mContext;

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigFactory] */
        public static void $r8$lambda$2V1iWTiAwNxOBlVvz73bbuEdzIw(BackgroundDefaultLoader backgroundDefaultLoader, final EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback, final ThreadPoolExecutor threadPoolExecutor) {
            backgroundDefaultLoader.getClass();
            try {
                FontRequestEmojiCompatConfig create = new Object() { // from class: androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigFactory
                    private final DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper mHelper = new DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper_API28();

                    /* JADX WARN: Removed duplicated region for block: B:12:0x003e A[EDGE_INSN: B:12:0x003e->B:13:0x003e BREAK  A[LOOP:0: B:2:0x001a->B:29:?], SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:16:0x0073  */
                    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:29:? A[LOOP:0: B:2:0x001a->B:29:?, LOOP_END, SYNTHETIC] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final FontRequestEmojiCompatConfig create(Context context) {
                        int i;
                        ProviderInfo providerInfo;
                        FontRequest fontRequest;
                        boolean z;
                        ApplicationInfo applicationInfo;
                        PackageManager packageManager = context.getPackageManager();
                        Preconditions.checkNotNull(packageManager, "Package manager required to locate emoji font provider");
                        Intent intent = new Intent("androidx.content.action.LOAD_EMOJI_FONT");
                        DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper defaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper = this.mHelper;
                        Iterator it = defaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper.queryIntentContentProviders(packageManager, intent).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                providerInfo = null;
                                break;
                            }
                            providerInfo = defaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper.getProviderInfo((ResolveInfo) it.next());
                            if (providerInfo != null && (applicationInfo = providerInfo.applicationInfo) != null) {
                                z = true;
                                if ((applicationInfo.flags & 1) == 1) {
                                    if (!z) {
                                        break;
                                    }
                                }
                            }
                            z = false;
                            if (!z) {
                            }
                        }
                        if (providerInfo != null) {
                            try {
                                String str = providerInfo.authority;
                                String str2 = providerInfo.packageName;
                                Signature[] signingSignatures = defaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper.getSigningSignatures(packageManager, str2);
                                ArrayList arrayList = new ArrayList();
                                for (Signature signature : signingSignatures) {
                                    arrayList.add(signature.toByteArray());
                                }
                                fontRequest = new FontRequest(str, str2, "emojicompat-emoji-font", Collections.singletonList(arrayList));
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.wtf("emoji2.text.DefaultEmojiConfig", e);
                            }
                            if (fontRequest != null) {
                                return null;
                            }
                            return new FontRequestEmojiCompatConfig(context, fontRequest);
                        }
                        fontRequest = null;
                        if (fontRequest != null) {
                        }
                    }
                }.create(backgroundDefaultLoader.mContext);
                if (create == null) {
                    throw new RuntimeException("EmojiCompat font provider not available on this device.");
                }
                EmojiCompat.MetadataRepoLoader metadataRepoLoader = create.mMetadataLoader;
                ((FontRequestEmojiCompatConfig.FontRequestMetadataLoader) metadataRepoLoader).setExecutor(threadPoolExecutor);
                metadataRepoLoader.load(new EmojiCompat.MetadataRepoLoaderCallback() { // from class: androidx.emoji2.text.EmojiCompatInitializer.BackgroundDefaultLoader.1
                    @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                    public final void onFailed(Throwable th) {
                        ThreadPoolExecutor threadPoolExecutor2 = threadPoolExecutor;
                        try {
                            EmojiCompat.MetadataRepoLoaderCallback.this.onFailed(th);
                        } finally {
                            threadPoolExecutor2.shutdown();
                        }
                    }

                    @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                    public final void onLoaded(MetadataRepo metadataRepo) {
                        ThreadPoolExecutor threadPoolExecutor2 = threadPoolExecutor;
                        try {
                            EmojiCompat.MetadataRepoLoaderCallback.this.onLoaded(metadataRepo);
                        } finally {
                            threadPoolExecutor2.shutdown();
                        }
                    }
                });
            } catch (Throwable th) {
                metadataRepoLoaderCallback.onFailed(th);
                threadPoolExecutor.shutdown();
            }
        }

        BackgroundDefaultLoader(Context context) {
            this.mContext = context.getApplicationContext();
        }

        @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoader
        public final void load(final EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback) {
            final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ConcurrencyHelpers$$ExternalSyntheticLambda0("EmojiCompatInitializer"));
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            threadPoolExecutor.execute(new Runnable() { // from class: androidx.emoji2.text.EmojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiCompatInitializer.BackgroundDefaultLoader.$r8$lambda$2V1iWTiAwNxOBlVvz73bbuEdzIw(EmojiCompatInitializer.BackgroundDefaultLoader.this, metadataRepoLoaderCallback, threadPoolExecutor);
                }
            });
        }
    }

    static class LoadEmojiCompatRunnable implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                Trace.beginSection("EmojiCompat.EmojiCompatInitializer.run");
                if (EmojiCompat.isConfigured()) {
                    EmojiCompat.get().load();
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    @Override // androidx.startup.Initializer
    public final List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    public final Boolean create(Context context) {
        EmojiCompat.init(new BackgroundDefaultConfig(context));
        final LifecycleRegistry lifecycle = ((LifecycleOwner) AppInitializer.getInstance(context).initializeComponent()).getLifecycle();
        lifecycle.addObserver(new DefaultLifecycleObserver() { // from class: androidx.emoji2.text.EmojiCompatInitializer.1
            @Override // androidx.lifecycle.FullLifecycleObserver
            public final void onResume() {
                EmojiCompatInitializer.this.getClass();
                Handler.createAsync(Looper.getMainLooper()).postDelayed(new LoadEmojiCompatRunnable(), 500L);
                lifecycle.removeObserver(this);
            }
        });
        return Boolean.TRUE;
    }
}
