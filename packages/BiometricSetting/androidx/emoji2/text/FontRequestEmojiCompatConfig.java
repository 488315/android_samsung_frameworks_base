package androidx.emoji2.text;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Trace;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class FontRequestEmojiCompatConfig extends EmojiCompat.Config {
    private static final FontProviderHelper DEFAULT_FONTS_CONTRACT = new FontProviderHelper();

    public static class FontProviderHelper {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FontRequestMetadataLoader implements EmojiCompat.MetadataRepoLoader {
        EmojiCompat.MetadataRepoLoaderCallback mCallback;
        private final Context mContext;
        private Executor mExecutor;
        private final FontProviderHelper mFontProviderHelper;
        private final Object mLock = new Object();
        private Handler mMainHandler;
        private RunnableC0117xc2d47b97 mMainHandlerLoadCallback;
        private ThreadPoolExecutor mMyThreadPoolExecutor;
        private ContentObserver mObserver;
        private final FontRequest mRequest;

        FontRequestMetadataLoader(Context context, FontRequest fontRequest, FontProviderHelper fontProviderHelper) {
            Preconditions.checkNotNull(context, "Context cannot be null");
            this.mContext = context.getApplicationContext();
            this.mRequest = fontRequest;
            this.mFontProviderHelper = fontProviderHelper;
        }

        private void cleanUp() {
            synchronized (this.mLock) {
                this.mCallback = null;
                ContentObserver contentObserver = this.mObserver;
                if (contentObserver != null) {
                    FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                    Context context = this.mContext;
                    fontProviderHelper.getClass();
                    context.getContentResolver().unregisterContentObserver(contentObserver);
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

        private FontsContractCompat.FontInfo retrieveFontInfo() {
            try {
                FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                Context context = this.mContext;
                FontRequest fontRequest = this.mRequest;
                fontProviderHelper.getClass();
                FontsContractCompat.FontFamilyResult fetchFonts = FontsContractCompat.fetchFonts(context, fontRequest);
                if (fetchFonts.getStatusCode() != 0) {
                    throw new RuntimeException("fetchFonts failed (" + fetchFonts.getStatusCode() + ")");
                }
                FontsContractCompat.FontInfo[] fonts = fetchFonts.getFonts();
                if (fonts == null || fonts.length == 0) {
                    throw new RuntimeException("fetchFonts failed (empty result)");
                }
                return fonts[0];
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("provider not found", e);
            }
        }

        final void createMetadata() {
            synchronized (this.mLock) {
                if (this.mCallback == null) {
                    return;
                }
                try {
                    FontsContractCompat.FontInfo retrieveFontInfo = retrieveFontInfo();
                    int resultCode = retrieveFontInfo.getResultCode();
                    if (resultCode == 2) {
                        synchronized (this.mLock) {
                        }
                    }
                    if (resultCode != 0) {
                        throw new RuntimeException("fetchFonts result is not OK. (" + resultCode + ")");
                    }
                    try {
                        Trace.beginSection("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
                        FontProviderHelper fontProviderHelper = this.mFontProviderHelper;
                        Context context = this.mContext;
                        fontProviderHelper.getClass();
                        Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, new FontsContractCompat.FontInfo[]{retrieveFontInfo}, 0);
                        ByteBuffer mmap = TypefaceCompatUtil.mmap(this.mContext, retrieveFontInfo.getUri());
                        if (mmap == null || createFromFontInfo == null) {
                            throw new RuntimeException("Unable to open file.");
                        }
                        MetadataRepo create = MetadataRepo.create(createFromFontInfo, mmap);
                        Trace.endSection();
                        synchronized (this.mLock) {
                            EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback = this.mCallback;
                            if (metadataRepoLoaderCallback != null) {
                                metadataRepoLoaderCallback.onLoaded(create);
                            }
                        }
                        cleanUp();
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
                    }
                } catch (Throwable th2) {
                    synchronized (this.mLock) {
                        EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback2 = this.mCallback;
                        if (metadataRepoLoaderCallback2 != null) {
                            metadataRepoLoaderCallback2.onFailed(th2);
                        }
                        cleanUp();
                    }
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

        final void loadInternal() {
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
                                this.f$0.createMetadata();
                                break;
                            default:
                                this.f$0.loadInternal();
                                break;
                        }
                    }
                });
            }
        }

        public final void setExecutor(Executor executor) {
            synchronized (this.mLock) {
                this.mExecutor = executor;
            }
        }
    }

    public FontRequestEmojiCompatConfig(Context context, FontRequest fontRequest) {
        super(new FontRequestMetadataLoader(context, fontRequest, DEFAULT_FONTS_CONTRACT));
    }
}
