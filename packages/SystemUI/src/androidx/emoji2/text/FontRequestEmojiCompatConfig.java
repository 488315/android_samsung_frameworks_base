package androidx.emoji2.text;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontProvider;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat$FontFamilyResult;
import androidx.core.provider.FontsContractCompat$FontInfo;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FontRequestEmojiCompatConfig extends EmojiCompat.Config {
    public static final FontProviderHelper DEFAULT_FONTS_CONTRACT = new FontProviderHelper();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FontProviderHelper {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FontRequestMetadataLoader implements EmojiCompat.MetadataRepoLoader {
        public EmojiCompat.MetadataRepoLoaderCallback mCallback;
        public final Context mContext;
        public Executor mExecutor;
        public final FontProviderHelper mFontProviderHelper;
        public final Object mLock = new Object();
        public Handler mMainHandler;
        public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mMainHandlerLoadCallback;
        public ThreadPoolExecutor mMyThreadPoolExecutor;
        public AnonymousClass1 mObserver;
        public final FontRequest mRequest;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$1, reason: invalid class name */
        public class AnonymousClass1 extends ContentObserver {
            public AnonymousClass1(Handler handler) {
                super(handler);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                FontRequestMetadataLoader.this.loadInternal();
            }
        }

        public FontRequestMetadataLoader(Context context, FontRequest fontRequest, FontProviderHelper fontProviderHelper) {
            Preconditions.checkNotNull(context, "Context cannot be null");
            Preconditions.checkNotNull(fontRequest, "FontRequest cannot be null");
            this.mContext = context.getApplicationContext();
            this.mRequest = fontRequest;
            this.mFontProviderHelper = fontProviderHelper;
        }

        public final void cleanUp() {
            synchronized (this.mLock) {
                try {
                    this.mCallback = null;
                    AnonymousClass1 anonymousClass1 = this.mObserver;
                    if (anonymousClass1 != null) {
                        FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                        Context context = this.mContext;
                        fontProviderHelper.getClass();
                        context.getContentResolver().unregisterContentObserver(anonymousClass1);
                        this.mObserver = null;
                    }
                    Handler handler = this.mMainHandler;
                    if (handler != null) {
                        handler.removeCallbacks(this.mMainHandlerLoadCallback);
                    }
                    this.mMainHandler = null;
                    ThreadPoolExecutor threadPoolExecutor = this.mMyThreadPoolExecutor;
                    if (threadPoolExecutor != null) {
                        threadPoolExecutor.shutdown();
                    }
                    this.mExecutor = null;
                    this.mMyThreadPoolExecutor = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoader
        public final void load(EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback) {
            synchronized (this.mLock) {
                this.mCallback = metadataRepoLoaderCallback;
            }
            loadInternal();
        }

        public final void loadInternal() {
            synchronized (this.mLock) {
                try {
                    if (this.mCallback == null) {
                        return;
                    }
                    if (this.mExecutor == null) {
                        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ConcurrencyHelpers$$ExternalSyntheticLambda0("emojiCompat"));
                        threadPoolExecutor.allowCoreThreadTimeOut(true);
                        this.mMyThreadPoolExecutor = threadPoolExecutor;
                        this.mExecutor = threadPoolExecutor;
                    }
                    ((ThreadPoolExecutor) this.mExecutor).execute(new Runnable() { // from class: androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader = FontRequestEmojiCompatConfig.FontRequestMetadataLoader.this;
                            synchronized (fontRequestMetadataLoader.mLock) {
                                try {
                                    if (fontRequestMetadataLoader.mCallback == null) {
                                        return;
                                    }
                                    try {
                                        FontsContractCompat$FontInfo retrieveFontInfo = fontRequestMetadataLoader.retrieveFontInfo();
                                        int i = retrieveFontInfo.mResultCode;
                                        if (i == 2) {
                                            synchronized (fontRequestMetadataLoader.mLock) {
                                            }
                                        }
                                        if (i != 0) {
                                            throw new RuntimeException("fetchFonts result is not OK. (" + i + ")");
                                        }
                                        try {
                                            Trace.beginSection("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
                                            FontRequestEmojiCompatConfig.FontProviderHelper fontProviderHelper = fontRequestMetadataLoader.mFontProviderHelper;
                                            Context context = fontRequestMetadataLoader.mContext;
                                            fontProviderHelper.getClass();
                                            Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, new FontsContractCompat$FontInfo[]{retrieveFontInfo}, 0);
                                            ByteBuffer mmap = TypefaceCompatUtil.mmap(fontRequestMetadataLoader.mContext, retrieveFontInfo.mUri);
                                            if (mmap == null || createFromFontInfo == null) {
                                                throw new RuntimeException("Unable to open file.");
                                            }
                                            MetadataRepo create = MetadataRepo.create(createFromFontInfo, mmap);
                                            Trace.endSection();
                                            synchronized (fontRequestMetadataLoader.mLock) {
                                                try {
                                                    EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback = fontRequestMetadataLoader.mCallback;
                                                    if (metadataRepoLoaderCallback != null) {
                                                        metadataRepoLoaderCallback.onLoaded(create);
                                                    }
                                                } finally {
                                                }
                                            }
                                            fontRequestMetadataLoader.cleanUp();
                                        } catch (Throwable th) {
                                            Trace.endSection();
                                            throw th;
                                        }
                                    } catch (Throwable th2) {
                                        synchronized (fontRequestMetadataLoader.mLock) {
                                            try {
                                                EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback2 = fontRequestMetadataLoader.mCallback;
                                                if (metadataRepoLoaderCallback2 != null) {
                                                    metadataRepoLoaderCallback2.onFailed(th2);
                                                }
                                                fontRequestMetadataLoader.cleanUp();
                                            } finally {
                                            }
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final FontsContractCompat$FontInfo retrieveFontInfo() {
            try {
                FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                Context context = this.mContext;
                FontRequest fontRequest = this.mRequest;
                fontProviderHelper.getClass();
                FontsContractCompat$FontFamilyResult fontFamilyResult = FontProvider.getFontFamilyResult(context, List.of(fontRequest));
                int i = fontFamilyResult.mStatusCode;
                if (i != 0) {
                    throw new RuntimeException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "fetchFonts failed (", ")"));
                }
                FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr = (FontsContractCompat$FontInfo[]) fontFamilyResult.mFonts.get(0);
                if (fontsContractCompat$FontInfoArr == null || fontsContractCompat$FontInfoArr.length == 0) {
                    throw new RuntimeException("fetchFonts failed (empty result)");
                }
                return fontsContractCompat$FontInfoArr[0];
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("provider not found", e);
            }
        }
    }

    public FontRequestEmojiCompatConfig(Context context, FontRequest fontRequest) {
        super(new FontRequestMetadataLoader(context, fontRequest, DEFAULT_FONTS_CONTRACT));
    }

    public FontRequestEmojiCompatConfig(Context context, FontRequest fontRequest, FontProviderHelper fontProviderHelper) {
        super(new FontRequestMetadataLoader(context, fontRequest, fontProviderHelper));
    }
}
