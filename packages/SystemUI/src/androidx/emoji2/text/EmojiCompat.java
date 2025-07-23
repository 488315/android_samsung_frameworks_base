package androidx.emoji2.text;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.EditorInfo;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class EmojiCompat {
    public static final Object INSTANCE_LOCK = new Object();
    public static volatile EmojiCompat sInstance;
    public final DefaultGlyphChecker mGlyphChecker;
    public final CompatInternal19 mHelper;
    public final ArraySet mInitCallbacks;
    public final ReadWriteLock mInitLock;
    public volatile int mLoadState;
    public final Handler mMainHandler;
    public final int mMetadataLoadStrategy;
    public final MetadataRepoLoader mMetadataLoader;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CompatInternal {
        public final EmojiCompat mEmojiCompat;

        public CompatInternal(EmojiCompat emojiCompat) {
            this.mEmojiCompat = emojiCompat;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CompatInternal19 extends CompatInternal {
        public volatile MetadataRepo mMetadataRepo;
        public volatile EmojiProcessor mProcessor;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.emoji2.text.EmojiCompat$CompatInternal19$1, reason: invalid class name */
        public class AnonymousClass1 extends MetadataRepoLoaderCallback {
            public AnonymousClass1() {
            }

            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
            public final void onFailed(Throwable th) {
                CompatInternal19.this.mEmojiCompat.onMetadataLoadFailed(th);
            }

            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
            public final void onLoaded(MetadataRepo metadataRepo) {
                CompatInternal19 compatInternal19 = CompatInternal19.this;
                compatInternal19.mMetadataRepo = metadataRepo;
                compatInternal19.mProcessor = new EmojiProcessor(compatInternal19.mMetadataRepo, new SpanFactory(), compatInternal19.mEmojiCompat.mGlyphChecker, false, null);
                EmojiCompat emojiCompat = compatInternal19.mEmojiCompat;
                emojiCompat.getClass();
                ArrayList arrayList = new ArrayList();
                emojiCompat.mInitLock.writeLock().lock();
                try {
                    emojiCompat.mLoadState = 1;
                    arrayList.addAll(emojiCompat.mInitCallbacks);
                    emojiCompat.mInitCallbacks.clear();
                    emojiCompat.mInitLock.writeLock().unlock();
                    emojiCompat.mMainHandler.post(new ListenerDispatcher(arrayList, emojiCompat.mLoadState));
                } catch (Throwable th) {
                    emojiCompat.mInitLock.writeLock().unlock();
                    throw th;
                }
            }
        }

        public CompatInternal19(EmojiCompat emojiCompat) {
            super(emojiCompat);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Config {
        public final MetadataRepoLoader mMetadataLoader;
        public int mMetadataLoadStrategy = 0;
        public final DefaultGlyphChecker mGlyphChecker = new DefaultGlyphChecker();

        public Config(MetadataRepoLoader metadataRepoLoader) {
            Preconditions.checkNotNull(metadataRepoLoader, "metadataLoader cannot be null.");
            this.mMetadataLoader = metadataRepoLoader;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface GlyphChecker {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ListenerDispatcher implements Runnable {
        public final List mInitCallbacks;
        public final int mLoadState;
        public final Throwable mThrowable;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public ListenerDispatcher(InitCallback initCallback, int i) {
            this(Arrays.asList(initCallback), i, null);
            Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        }

        @Override // java.lang.Runnable
        public final void run() {
            int size = ((ArrayList) this.mInitCallbacks).size();
            int i = 0;
            if (this.mLoadState != 1) {
                while (i < size) {
                    ((InitCallback) ((ArrayList) this.mInitCallbacks).get(i)).onFailed();
                    i++;
                }
            } else {
                while (i < size) {
                    ((InitCallback) ((ArrayList) this.mInitCallbacks).get(i)).onInitialized();
                    i++;
                }
            }
        }

        public ListenerDispatcher(Collection<InitCallback> collection, int i) {
            this(collection, i, null);
        }

        public ListenerDispatcher(Collection<InitCallback> collection, int i, Throwable th) {
            Preconditions.checkNotNull(collection, "initCallbacks cannot be null");
            this.mInitCallbacks = new ArrayList(collection);
            this.mLoadState = i;
            this.mThrowable = th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface MetadataRepoLoader {
        void load(MetadataRepoLoaderCallback metadataRepoLoaderCallback);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class MetadataRepoLoaderCallback {
        public abstract void onFailed(Throwable th);

        public abstract void onLoaded(MetadataRepo metadataRepo);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SpanFactory {
    }

    private EmojiCompat(Config config) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mInitLock = reentrantReadWriteLock;
        this.mLoadState = 3;
        config.getClass();
        this.mMetadataLoader = config.mMetadataLoader;
        int i = config.mMetadataLoadStrategy;
        this.mMetadataLoadStrategy = i;
        this.mGlyphChecker = config.mGlyphChecker;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mInitCallbacks = new ArraySet();
        CompatInternal19 compatInternal19 = new CompatInternal19(this);
        this.mHelper = compatInternal19;
        reentrantReadWriteLock.writeLock().lock();
        if (i == 0) {
            try {
                this.mLoadState = 0;
            } catch (Throwable th) {
                this.mInitLock.writeLock().unlock();
                throw th;
            }
        }
        reentrantReadWriteLock.writeLock().unlock();
        if (getLoadState() == 0) {
            EmojiCompat emojiCompat = compatInternal19.mEmojiCompat;
            try {
                emojiCompat.mMetadataLoader.load(compatInternal19.new AnonymousClass1());
            } catch (Throwable th2) {
                emojiCompat.onMetadataLoadFailed(th2);
            }
        }
    }

    public static EmojiCompat get() {
        EmojiCompat emojiCompat;
        synchronized (INSTANCE_LOCK) {
            try {
                emojiCompat = sInstance;
                if (!(emojiCompat != null)) {
                    throw new IllegalStateException("EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
                }
            } finally {
            }
        }
        return emojiCompat;
    }

    public static void init(EmojiCompatInitializer.BackgroundDefaultConfig backgroundDefaultConfig) {
        if (sInstance == null) {
            synchronized (INSTANCE_LOCK) {
                try {
                    if (sInstance == null) {
                        sInstance = new EmojiCompat(backgroundDefaultConfig);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static boolean isConfigured() {
        return sInstance != null;
    }

    public final int getLoadState() {
        this.mInitLock.readLock().lock();
        try {
            return this.mLoadState;
        } finally {
            this.mInitLock.readLock().unlock();
        }
    }

    public final void load() {
        if (!(this.mMetadataLoadStrategy == 1)) {
            throw new IllegalStateException("Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
        }
        if (getLoadState() == 1) {
            return;
        }
        this.mInitLock.writeLock().lock();
        try {
            if (this.mLoadState == 0) {
                return;
            }
            this.mLoadState = 0;
            this.mInitLock.writeLock().unlock();
            CompatInternal19 compatInternal19 = this.mHelper;
            EmojiCompat emojiCompat = compatInternal19.mEmojiCompat;
            try {
                emojiCompat.mMetadataLoader.load(compatInternal19.new AnonymousClass1());
            } catch (Throwable th) {
                emojiCompat.onMetadataLoadFailed(th);
            }
        } finally {
            this.mInitLock.writeLock().unlock();
        }
    }

    public final void onMetadataLoadFailed(Throwable th) {
        ArrayList arrayList = new ArrayList();
        this.mInitLock.writeLock().lock();
        try {
            this.mLoadState = 2;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            this.mInitLock.writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, th));
        } catch (Throwable th2) {
            this.mInitLock.writeLock().unlock();
            throw th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0180, code lost:
    
        if (r3 != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0182, code lost:
    
        ((androidx.emoji2.text.SpannableBuilder) r14).endBatchEdit();
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0188, code lost:
    
        return r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x015e A[Catch: all -> 0x007d, TryCatch #0 {all -> 0x007d, blocks: (B:120:0x005f, B:123:0x0064, B:125:0x0068, B:127:0x0077, B:30:0x008a, B:32:0x0096, B:34:0x0099, B:36:0x009d, B:38:0x00ad, B:40:0x00b0, B:44:0x00bd, B:47:0x00c5, B:52:0x00e4, B:77:0x00f2, B:81:0x00fe, B:82:0x0108, B:64:0x011c, B:67:0x0123, B:55:0x0128, B:57:0x0133, B:88:0x013a, B:90:0x013e, B:92:0x0144, B:94:0x0148, B:98:0x0152, B:101:0x015e, B:102:0x0163, B:104:0x0176, B:28:0x0080), top: B:119:0x005f }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0176 A[Catch: all -> 0x007d, TRY_LEAVE, TryCatch #0 {all -> 0x007d, blocks: (B:120:0x005f, B:123:0x0064, B:125:0x0068, B:127:0x0077, B:30:0x008a, B:32:0x0096, B:34:0x0099, B:36:0x009d, B:38:0x00ad, B:40:0x00b0, B:44:0x00bd, B:47:0x00c5, B:52:0x00e4, B:77:0x00f2, B:81:0x00fe, B:82:0x0108, B:64:0x011c, B:67:0x0123, B:55:0x0128, B:57:0x0133, B:88:0x013a, B:90:0x013e, B:92:0x0144, B:94:0x0148, B:98:0x0152, B:101:0x015e, B:102:0x0163, B:104:0x0176, B:28:0x0080), top: B:119:0x005f }] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009d A[Catch: all -> 0x007d, TryCatch #0 {all -> 0x007d, blocks: (B:120:0x005f, B:123:0x0064, B:125:0x0068, B:127:0x0077, B:30:0x008a, B:32:0x0096, B:34:0x0099, B:36:0x009d, B:38:0x00ad, B:40:0x00b0, B:44:0x00bd, B:47:0x00c5, B:52:0x00e4, B:77:0x00f2, B:81:0x00fe, B:82:0x0108, B:64:0x011c, B:67:0x0123, B:55:0x0128, B:57:0x0133, B:88:0x013a, B:90:0x013e, B:92:0x0144, B:94:0x0148, B:98:0x0152, B:101:0x015e, B:102:0x0163, B:104:0x0176, B:28:0x0080), top: B:119:0x005f }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0128 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ea A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.CharSequence process(int r11, int r12, int r13, java.lang.CharSequence r14) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiCompat.process(int, int, int, java.lang.CharSequence):java.lang.CharSequence");
    }

    public final void registerInitCallback(InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        this.mInitLock.writeLock().lock();
        try {
            if (this.mLoadState != 1 && this.mLoadState != 2) {
                this.mInitCallbacks.add(initCallback);
                this.mInitLock.writeLock().unlock();
            }
            this.mMainHandler.post(new ListenerDispatcher(initCallback, this.mLoadState));
            this.mInitLock.writeLock().unlock();
        } catch (Throwable th) {
            this.mInitLock.writeLock().unlock();
            throw th;
        }
    }

    public final void updateEditorInfo(EditorInfo editorInfo) {
        if (getLoadState() != 1 || editorInfo == null) {
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        CompatInternal19 compatInternal19 = this.mHelper;
        compatInternal19.getClass();
        Bundle bundle = editorInfo.extras;
        MetadataList metadataList = compatInternal19.mMetadataRepo.mMetadataList;
        int __offset = metadataList.__offset(4);
        bundle.putInt("android.support.text.emoji.emojiCompat_metadataVersion", __offset != 0 ? metadataList.bb.getInt(__offset + metadataList.bb_pos) : 0);
        Bundle bundle2 = editorInfo.extras;
        compatInternal19.mEmojiCompat.getClass();
        bundle2.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", false);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class InitCallback {
        public void onFailed() {
        }

        public void onInitialized() {
        }
    }
}
