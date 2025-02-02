package androidx.emoji2.text;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public final class EmojiCompat {
    private static final Object INSTANCE_LOCK = new Object();
    private static volatile EmojiCompat sInstance;
    private final int mEmojiSpanIndicatorColor;
    private final GlyphChecker mGlyphChecker;
    private final CompatInternal19 mHelper;
    private final ArraySet mInitCallbacks;
    private final ReadWriteLock mInitLock;
    private volatile int mLoadState;
    private final Handler mMainHandler;
    private final int mMetadataLoadStrategy;
    final MetadataRepoLoader mMetadataLoader;
    private final DefaultSpanFactory mSpanFactory;

    private static class CompatInternal {
        final EmojiCompat mEmojiCompat;

        CompatInternal(EmojiCompat emojiCompat) {
            this.mEmojiCompat = emojiCompat;
        }
    }

    private static final class CompatInternal19 extends CompatInternal {
        private volatile MetadataRepo mMetadataRepo;
        private volatile EmojiProcessor mProcessor;

        /* renamed from: androidx.emoji2.text.EmojiCompat$CompatInternal19$1 */
        final class C01131 extends MetadataRepoLoaderCallback {
            C01131() {
            }

            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
            public final void onFailed(Throwable th) {
                CompatInternal19.this.mEmojiCompat.onMetadataLoadFailed(th);
            }

            @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
            public final void onLoaded(MetadataRepo metadataRepo) {
                CompatInternal19.this.onMetadataLoadSuccess(metadataRepo);
            }
        }

        final void onMetadataLoadSuccess(MetadataRepo metadataRepo) {
            Set emptySet;
            this.mMetadataRepo = metadataRepo;
            MetadataRepo metadataRepo2 = this.mMetadataRepo;
            DefaultSpanFactory defaultSpanFactory = this.mEmojiCompat.mSpanFactory;
            GlyphChecker glyphChecker = this.mEmojiCompat.mGlyphChecker;
            this.mEmojiCompat.getClass();
            this.mEmojiCompat.getClass();
            try {
                Object invoke = Class.forName("android.text.EmojiConsistency").getMethod("getEmojiConsistencySet", new Class[0]).invoke(null, new Object[0]);
                if (invoke == null) {
                    emptySet = Collections.emptySet();
                } else {
                    emptySet = (Set) invoke;
                    Iterator it = emptySet.iterator();
                    while (it.hasNext()) {
                        if (!(it.next() instanceof int[])) {
                            emptySet = Collections.emptySet();
                            break;
                        }
                    }
                }
            } catch (Throwable unused) {
                emptySet = Collections.emptySet();
            }
            this.mProcessor = new EmojiProcessor(metadataRepo2, defaultSpanFactory, glyphChecker, emptySet);
            this.mEmojiCompat.onMetadataLoadSuccess();
        }

        final CharSequence process(CharSequence charSequence, int i, int i2, boolean z) {
            return this.mProcessor.process(charSequence, i, i2, z);
        }

        final void updateEditorInfoAttrs(EditorInfo editorInfo) {
            editorInfo.extras.putInt("android.support.text.emoji.emojiCompat_metadataVersion", this.mMetadataRepo.getMetadataVersion());
            Bundle bundle = editorInfo.extras;
            this.mEmojiCompat.getClass();
            bundle.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", false);
        }
    }

    public static abstract class Config {
        final MetadataRepoLoader mMetadataLoader;
        int mMetadataLoadStrategy = 0;
        GlyphChecker mGlyphChecker = new DefaultGlyphChecker();

        protected Config(MetadataRepoLoader metadataRepoLoader) {
            this.mMetadataLoader = metadataRepoLoader;
        }
    }

    public static class DefaultSpanFactory implements SpanFactory {
    }

    public interface GlyphChecker {
    }

    private static class ListenerDispatcher implements Runnable {
        private final List<InitCallback> mInitCallbacks;
        private final int mLoadState;
        private final Throwable mThrowable;

        ListenerDispatcher(Collection<InitCallback> collection, int i, Throwable th) {
            Preconditions.checkNotNull(collection, "initCallbacks cannot be null");
            this.mInitCallbacks = new ArrayList(collection);
            this.mLoadState = i;
            this.mThrowable = th;
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
    }

    public interface MetadataRepoLoader {
        void load(MetadataRepoLoaderCallback metadataRepoLoaderCallback);
    }

    public static abstract class MetadataRepoLoaderCallback {
        public abstract void onFailed(Throwable th);

        public abstract void onLoaded(MetadataRepo metadataRepo);
    }

    public interface SpanFactory {
    }

    private EmojiCompat(Config config) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mInitLock = reentrantReadWriteLock;
        this.mLoadState = 3;
        this.mEmojiSpanIndicatorColor = -16711936;
        MetadataRepoLoader metadataRepoLoader = config.mMetadataLoader;
        this.mMetadataLoader = metadataRepoLoader;
        int i = config.mMetadataLoadStrategy;
        this.mMetadataLoadStrategy = i;
        this.mGlyphChecker = config.mGlyphChecker;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mInitCallbacks = new ArraySet();
        this.mSpanFactory = new DefaultSpanFactory();
        CompatInternal19 compatInternal19 = new CompatInternal19(this);
        this.mHelper = compatInternal19;
        reentrantReadWriteLock.writeLock().lock();
        if (i == 0) {
            try {
                this.mLoadState = 0;
            } catch (Throwable th) {
                ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
                throw th;
            }
        }
        reentrantReadWriteLock.writeLock().unlock();
        if (getLoadState() == 0) {
            try {
                metadataRepoLoader.load(compatInternal19.new C01131());
            } catch (Throwable th2) {
                onMetadataLoadFailed(th2);
            }
        }
    }

    public static EmojiCompat get() {
        EmojiCompat emojiCompat;
        synchronized (INSTANCE_LOCK) {
            emojiCompat = sInstance;
            if (!(emojiCompat != null)) {
                throw new IllegalStateException("EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
            }
        }
        return emojiCompat;
    }

    public static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int i, int i2, boolean z) {
        return EmojiProcessor.handleDeleteSurroundingText(inputConnection, editable, i, i2, z);
    }

    public static boolean handleOnKeyDown(Editable editable, int i, KeyEvent keyEvent) {
        return EmojiProcessor.handleOnKeyDown(editable, i, keyEvent);
    }

    public static void init(Config config) {
        if (sInstance == null) {
            synchronized (INSTANCE_LOCK) {
                if (sInstance == null) {
                    sInstance = new EmojiCompat(config);
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
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            if (this.mLoadState == 0) {
                return;
            }
            this.mLoadState = 0;
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            CompatInternal19 compatInternal19 = this.mHelper;
            EmojiCompat emojiCompat = compatInternal19.mEmojiCompat;
            try {
                emojiCompat.mMetadataLoader.load(compatInternal19.new C01131());
            } catch (Throwable th) {
                emojiCompat.onMetadataLoadFailed(th);
            }
        } finally {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
        }
    }

    final void onMetadataLoadFailed(Throwable th) {
        ArrayList arrayList = new ArrayList();
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            this.mLoadState = 2;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, th));
        } catch (Throwable th2) {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            throw th2;
        }
    }

    final void onMetadataLoadSuccess() {
        ArrayList arrayList = new ArrayList();
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            this.mLoadState = 1;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, null));
        } catch (Throwable th) {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            throw th;
        }
    }

    public final CharSequence process(int i, int i2, CharSequence charSequence) {
        if (!(getLoadState() == 1)) {
            throw new IllegalStateException("Not initialized yet");
        }
        if (i < 0) {
            throw new IllegalArgumentException("start cannot be negative");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("end cannot be negative");
        }
        Preconditions.checkArgument(i <= i2, "start should be <= than end");
        if (charSequence == null) {
            return null;
        }
        Preconditions.checkArgument(i <= charSequence.length(), "start should be < than charSequence length");
        Preconditions.checkArgument(i2 <= charSequence.length(), "end should be < than charSequence length");
        return (charSequence.length() == 0 || i == i2) ? charSequence : this.mHelper.process(charSequence, i, i2, false);
    }

    public final void registerInitCallback(InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            if (this.mLoadState != 1 && this.mLoadState != 2) {
                this.mInitCallbacks.add(initCallback);
            }
            this.mMainHandler.post(new ListenerDispatcher(Arrays.asList(initCallback), this.mLoadState, null));
        } finally {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
        }
    }

    public final void unregisterInitCallback(InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        ReentrantReadWriteLock reentrantReadWriteLock = (ReentrantReadWriteLock) this.mInitLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            this.mInitCallbacks.remove(initCallback);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public final void updateEditorInfo(EditorInfo editorInfo) {
        if (!(getLoadState() == 1) || editorInfo == null) {
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        this.mHelper.updateEditorInfoAttrs(editorInfo);
    }

    public static abstract class InitCallback {
        public void onFailed() {
        }

        public void onInitialized() {
        }
    }
}
