package androidx.emoji2.text;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat$FontFamilyResult;
import androidx.core.provider.FontsContractCompat$FontInfo;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontRequestEmojiCompatConfig extends EmojiCompat.Config {
    public static final FontProviderHelper DEFAULT_FONTS_CONTRACT = new FontProviderHelper();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FontProviderHelper {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FontRequestMetadataLoader implements EmojiCompat.MetadataRepoLoader {
        public EmojiCompat.MetadataRepoLoaderCallback mCallback;
        public final Context mContext;
        public Executor mExecutor;
        public final FontProviderHelper mFontProviderHelper;
        public final Object mLock = new Object();
        public Handler mMainHandler;
        public RunnableC0204xc2d47b97 mMainHandlerLoadCallback;
        public ThreadPoolExecutor mMyThreadPoolExecutor;
        public C02051 mObserver;
        public final FontRequest mRequest;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$1 */
        public final class C02051 extends ContentObserver {
            public C02051(Handler handler) {
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
                this.mCallback = null;
                C02051 c02051 = this.mObserver;
                if (c02051 != null) {
                    FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                    Context context = this.mContext;
                    fontProviderHelper.getClass();
                    context.getContentResolver().unregisterContentObserver(c02051);
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
                if (this.mCallback == null) {
                    return;
                }
                if (this.mExecutor == null) {
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ConcurrencyHelpers$$ExternalSyntheticLambda0("emojiCompat"));
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                    this.mMyThreadPoolExecutor = threadPoolExecutor;
                    this.mExecutor = threadPoolExecutor;
                }
                final int i = 0;
                this.mExecutor.execute(new Runnable(this) { // from class: androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda0
                    public final /* synthetic */ FontRequestEmojiCompatConfig.FontRequestMetadataLoader f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader = this.f$0;
                                synchronized (fontRequestMetadataLoader.mLock) {
                                    if (fontRequestMetadataLoader.mCallback == null) {
                                        return;
                                    }
                                    try {
                                        FontsContractCompat$FontInfo retrieveFontInfo = fontRequestMetadataLoader.retrieveFontInfo();
                                        int i2 = retrieveFontInfo.mResultCode;
                                        if (i2 == 2) {
                                            synchronized (fontRequestMetadataLoader.mLock) {
                                            }
                                        }
                                        if (i2 != 0) {
                                            throw new RuntimeException("fetchFonts result is not OK. (" + i2 + ")");
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
                                                EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback = fontRequestMetadataLoader.mCallback;
                                                if (metadataRepoLoaderCallback != null) {
                                                    metadataRepoLoaderCallback.onLoaded(create);
                                                }
                                            }
                                            fontRequestMetadataLoader.cleanUp();
                                            return;
                                        } catch (Throwable th) {
                                            Trace.endSection();
                                            throw th;
                                        }
                                    } catch (Throwable th2) {
                                        synchronized (fontRequestMetadataLoader.mLock) {
                                            EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback2 = fontRequestMetadataLoader.mCallback;
                                            if (metadataRepoLoaderCallback2 != null) {
                                                metadataRepoLoaderCallback2.onFailed(th2);
                                            }
                                            fontRequestMetadataLoader.cleanUp();
                                            return;
                                        }
                                    }
                                }
                            default:
                                this.f$0.loadInternal();
                                return;
                        }
                    }
                });
            }
        }

        public final FontsContractCompat$FontInfo retrieveFontInfo() {
            try {
                FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                Context context = this.mContext;
                FontRequest fontRequest = this.mRequest;
                fontProviderHelper.getClass();
                FontsContractCompat$FontFamilyResult fontFamilyResult = FontProvider.getFontFamilyResult(context, fontRequest);
                int i = fontFamilyResult.mStatusCode;
                if (i != 0) {
                    throw new RuntimeException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("fetchFonts failed (", i, ")"));
                }
                FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr = fontFamilyResult.mFonts;
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
