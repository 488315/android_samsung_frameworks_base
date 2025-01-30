package androidx.emoji2.text;

import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.EmojiProcessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiCompat {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class CompatInternal {
        public final EmojiCompat mEmojiCompat;

        public CompatInternal(EmojiCompat emojiCompat) {
            this.mEmojiCompat = emojiCompat;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CompatInternal19 extends CompatInternal {
        public volatile MetadataRepo mMetadataRepo;
        public volatile EmojiProcessor mProcessor;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: androidx.emoji2.text.EmojiCompat$CompatInternal19$1 */
        public final class C02001 extends MetadataRepoLoaderCallback {
            public C02001() {
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
                compatInternal19.mEmojiCompat.onMetadataLoadSuccess();
            }
        }

        public CompatInternal19(EmojiCompat emojiCompat) {
            super(emojiCompat);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Config {
        public final MetadataRepoLoader mMetadataLoader;
        public final int mEmojiSpanIndicatorColor = -16711936;
        public int mMetadataLoadStrategy = 0;
        public final DefaultGlyphChecker mGlyphChecker = new DefaultGlyphChecker();

        public Config(MetadataRepoLoader metadataRepoLoader) {
            Preconditions.checkNotNull(metadataRepoLoader, "metadataLoader cannot be null.");
            this.mMetadataLoader = metadataRepoLoader;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface GlyphChecker {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListenerDispatcher implements Runnable {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MetadataRepoLoader {
        void load(MetadataRepoLoaderCallback metadataRepoLoaderCallback);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MetadataRepoLoaderCallback {
        public abstract void onFailed(Throwable th);

        public abstract void onLoaded(MetadataRepo metadataRepo);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SpanFactory {
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
                ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
                throw th;
            }
        }
        reentrantReadWriteLock.writeLock().unlock();
        if (getLoadState() == 0) {
            EmojiCompat emojiCompat = compatInternal19.mEmojiCompat;
            try {
                emojiCompat.mMetadataLoader.load(compatInternal19.new C02001());
            } catch (Throwable th2) {
                emojiCompat.onMetadataLoadFailed(th2);
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

    public static void init(EmojiCompatInitializer.BackgroundDefaultConfig backgroundDefaultConfig) {
        if (sInstance == null) {
            synchronized (INSTANCE_LOCK) {
                if (sInstance == null) {
                    sInstance = new EmojiCompat(backgroundDefaultConfig);
                }
            }
        }
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
                emojiCompat.mMetadataLoader.load(compatInternal19.new C02001());
            } catch (Throwable th) {
                emojiCompat.onMetadataLoadFailed(th);
            }
        } finally {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
        }
    }

    public final void onMetadataLoadFailed(Throwable th) {
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

    public final void onMetadataLoadSuccess() {
        ArrayList arrayList = new ArrayList();
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            this.mLoadState = 1;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState));
        } catch (Throwable th) {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0162 A[Catch: all -> 0x0192, TryCatch #0 {all -> 0x0192, blocks: (B:123:0x005c, B:126:0x0061, B:128:0x0065, B:130:0x0074, B:29:0x0084, B:31:0x008e, B:33:0x0091, B:35:0x0095, B:37:0x00a1, B:39:0x00a4, B:43:0x00b1, B:49:0x00c0, B:50:0x00ce, B:55:0x00e9, B:79:0x00f5, B:82:0x0101, B:83:0x010b, B:67:0x0120, B:70:0x0127, B:58:0x012c, B:60:0x0137, B:88:0x013d, B:90:0x0141, B:92:0x0147, B:94:0x014b, B:99:0x0156, B:102:0x0162, B:103:0x0168, B:105:0x017b, B:27:0x007a), top: B:122:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x017b A[Catch: all -> 0x0192, TRY_LEAVE, TryCatch #0 {all -> 0x0192, blocks: (B:123:0x005c, B:126:0x0061, B:128:0x0065, B:130:0x0074, B:29:0x0084, B:31:0x008e, B:33:0x0091, B:35:0x0095, B:37:0x00a1, B:39:0x00a4, B:43:0x00b1, B:49:0x00c0, B:50:0x00ce, B:55:0x00e9, B:79:0x00f5, B:82:0x0101, B:83:0x010b, B:67:0x0120, B:70:0x0127, B:58:0x012c, B:60:0x0137, B:88:0x013d, B:90:0x0141, B:92:0x0147, B:94:0x014b, B:99:0x0156, B:102:0x0162, B:103:0x0168, B:105:0x017b, B:27:0x007a), top: B:122:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0095 A[Catch: all -> 0x0192, TryCatch #0 {all -> 0x0192, blocks: (B:123:0x005c, B:126:0x0061, B:128:0x0065, B:130:0x0074, B:29:0x0084, B:31:0x008e, B:33:0x0091, B:35:0x0095, B:37:0x00a1, B:39:0x00a4, B:43:0x00b1, B:49:0x00c0, B:50:0x00ce, B:55:0x00e9, B:79:0x00f5, B:82:0x0101, B:83:0x010b, B:67:0x0120, B:70:0x0127, B:58:0x012c, B:60:0x0137, B:88:0x013d, B:90:0x0141, B:92:0x0147, B:94:0x014b, B:99:0x0156, B:102:0x0162, B:103:0x0168, B:105:0x017b, B:27:0x007a), top: B:122:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x012c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ef A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CharSequence process(int i, int i2, CharSequence charSequence, int i3) {
        EmojiProcessor.ProcessorSm processorSm;
        int codePointAt;
        int i4;
        int i5;
        int check;
        EmojiSpan[] emojiSpanArr;
        boolean z = false;
        if (!(getLoadState() == 1)) {
            throw new IllegalStateException("Not initialized yet");
        }
        if (i < 0) {
            throw new IllegalArgumentException("start cannot be negative");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("end cannot be negative");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("maxEmojiCount cannot be negative");
        }
        Preconditions.checkArgument("start should be <= than end", i <= i2);
        UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable = null;
        if (charSequence == null) {
            return null;
        }
        Preconditions.checkArgument("start should be < than charSequence length", i <= charSequence.length());
        Preconditions.checkArgument("end should be < than charSequence length", i2 <= charSequence.length());
        if (charSequence.length() == 0 || i == i2) {
            return charSequence;
        }
        EmojiProcessor emojiProcessor = this.mHelper.mProcessor;
        emojiProcessor.getClass();
        boolean z2 = charSequence instanceof SpannableBuilder;
        if (z2) {
            ((SpannableBuilder) charSequence).blockWatchers();
        }
        if (!z2) {
            try {
                if (!(charSequence instanceof Spannable)) {
                    if ((charSequence instanceof Spanned) && ((Spanned) charSequence).nextSpanTransition(i - 1, i2 + 1, EmojiSpan.class) <= i2) {
                        unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable(charSequence);
                    }
                    if (unprecomputeTextOnModificationSpannable != null && (emojiSpanArr = (EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(i, i2, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
                        for (EmojiSpan emojiSpan : emojiSpanArr) {
                            int spanStart = unprecomputeTextOnModificationSpannable.getSpanStart(emojiSpan);
                            int spanEnd = unprecomputeTextOnModificationSpannable.getSpanEnd(emojiSpan);
                            if (spanStart != i2) {
                                unprecomputeTextOnModificationSpannable.removeSpan(emojiSpan);
                            }
                            i = Math.min(spanStart, i);
                            i2 = Math.max(spanEnd, i2);
                        }
                    }
                    if (i != i2 && i < charSequence.length()) {
                        if (i3 != Integer.MAX_VALUE && unprecomputeTextOnModificationSpannable != null) {
                            i3 -= ((EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(0, unprecomputeTextOnModificationSpannable.length(), EmojiSpan.class)).length;
                        }
                        processorSm = new EmojiProcessor.ProcessorSm(emojiProcessor.mMetadataRepo.mRootNode, emojiProcessor.mUseEmojiAsDefaultStyle, emojiProcessor.mEmojiAsDefaultStyleExceptions);
                        codePointAt = Character.codePointAt(charSequence, i);
                        int i6 = 0;
                        loop1: while (true) {
                            i4 = codePointAt;
                            i5 = i;
                            while (i < i2 && i6 < i3) {
                                check = processorSm.check(i4);
                                if (check != 1) {
                                    i5 += Character.charCount(Character.codePointAt(charSequence, i5));
                                    if (i5 < i2) {
                                        i4 = Character.codePointAt(charSequence, i5);
                                    }
                                    i = i5;
                                } else if (check == 2) {
                                    i += Character.charCount(i4);
                                    if (i < i2) {
                                        i4 = Character.codePointAt(charSequence, i);
                                    }
                                } else if (check == 3) {
                                    if (!emojiProcessor.hasGlyph(charSequence, i5, i, processorSm.mFlushNode.mData)) {
                                        if (unprecomputeTextOnModificationSpannable == null) {
                                            unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable((Spannable) new SpannableString(charSequence));
                                        }
                                        EmojiMetadata emojiMetadata = processorSm.mFlushNode.mData;
                                        emojiProcessor.mSpanFactory.getClass();
                                        unprecomputeTextOnModificationSpannable.setSpan(new TypefaceEmojiSpan(emojiMetadata), i5, i, 33);
                                        i6++;
                                    }
                                    codePointAt = i4;
                                }
                            }
                        }
                        if (processorSm.mState == 2 && processorSm.mCurrentNode.mData != null && (processorSm.mCurrentDepth > 1 || processorSm.shouldUseEmojiPresentationStyleForSingleCodepoint())) {
                            z = true;
                        }
                        if (z && i6 < i3 && !emojiProcessor.hasGlyph(charSequence, i5, i, processorSm.mCurrentNode.mData)) {
                            if (unprecomputeTextOnModificationSpannable == null) {
                                unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable(charSequence);
                            }
                            EmojiMetadata emojiMetadata2 = processorSm.mCurrentNode.mData;
                            emojiProcessor.mSpanFactory.getClass();
                            unprecomputeTextOnModificationSpannable.setSpan(new TypefaceEmojiSpan(emojiMetadata2), i5, i, 33);
                        }
                        if (unprecomputeTextOnModificationSpannable != null) {
                            if (!z2) {
                                return charSequence;
                            }
                            return charSequence;
                        }
                        Spannable spannable = unprecomputeTextOnModificationSpannable.mDelegate;
                        if (z2) {
                            ((SpannableBuilder) charSequence).endBatchEdit();
                        }
                        return spannable;
                    }
                    return charSequence;
                }
            } finally {
                if (z2) {
                    ((SpannableBuilder) charSequence).endBatchEdit();
                }
            }
        }
        unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable((Spannable) charSequence);
        if (unprecomputeTextOnModificationSpannable != null) {
            while (r6 < r5) {
            }
        }
        if (i != i2) {
            if (i3 != Integer.MAX_VALUE) {
                i3 -= ((EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(0, unprecomputeTextOnModificationSpannable.length(), EmojiSpan.class)).length;
            }
            processorSm = new EmojiProcessor.ProcessorSm(emojiProcessor.mMetadataRepo.mRootNode, emojiProcessor.mUseEmojiAsDefaultStyle, emojiProcessor.mEmojiAsDefaultStyleExceptions);
            codePointAt = Character.codePointAt(charSequence, i);
            int i62 = 0;
            loop1: while (true) {
                i4 = codePointAt;
                i5 = i;
                while (i < i2) {
                    check = processorSm.check(i4);
                    if (check != 1) {
                    }
                }
                codePointAt = i4;
            }
            if (processorSm.mState == 2) {
                z = true;
            }
            if (z) {
                if (unprecomputeTextOnModificationSpannable == null) {
                }
                EmojiMetadata emojiMetadata22 = processorSm.mCurrentNode.mData;
                emojiProcessor.mSpanFactory.getClass();
                unprecomputeTextOnModificationSpannable.setSpan(new TypefaceEmojiSpan(emojiMetadata22), i5, i, 33);
            }
            if (unprecomputeTextOnModificationSpannable != null) {
            }
        }
        return charSequence;
    }

    public final void registerInitCallback(InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        ((ReentrantReadWriteLock) this.mInitLock).writeLock().lock();
        try {
            if (this.mLoadState != 1 && this.mLoadState != 2) {
                this.mInitCallbacks.add(initCallback);
            }
            this.mMainHandler.post(new ListenerDispatcher(initCallback, this.mLoadState));
        } finally {
            ((ReentrantReadWriteLock) this.mInitLock).writeLock().unlock();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class InitCallback {
        public void onFailed() {
        }

        public void onInitialized() {
        }
    }
}
