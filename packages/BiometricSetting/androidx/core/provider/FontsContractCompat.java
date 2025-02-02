package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import androidx.core.graphics.TypefaceCompat;

/* loaded from: classes.dex */
public final class FontsContractCompat {

    public static class FontFamilyResult {
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @Deprecated
        public FontFamilyResult(int i, FontInfo[] fontInfoArr) {
            this.mStatusCode = i;
            this.mFonts = fontInfoArr;
        }

        public final FontInfo[] getFonts() {
            return this.mFonts;
        }

        public final int getStatusCode() {
            return this.mStatusCode;
        }
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @Deprecated
        public FontInfo(Uri uri, int i, int i2, boolean z, int i3) {
            uri.getClass();
            this.mUri = uri;
            this.mTtcIndex = i;
            this.mWeight = i2;
            this.mItalic = z;
            this.mResultCode = i3;
        }

        public final int getResultCode() {
            return this.mResultCode;
        }

        public final int getTtcIndex() {
            return this.mTtcIndex;
        }

        public final Uri getUri() {
            return this.mUri;
        }

        public final int getWeight() {
            return this.mWeight;
        }

        public final boolean isItalic() {
            return this.mItalic;
        }
    }

    public static class FontRequestCallback {
        public void onTypefaceRetrieved(Typeface typeface) {
            throw null;
        }
    }

    public static FontFamilyResult fetchFonts(Context context, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        return FontProvider.getFontFamilyResult(context, fontRequest);
    }

    public static Typeface requestFont(Context context, FontRequest fontRequest, int i, boolean z, int i2, Handler handler, TypefaceCompat.ResourcesCallbackAdapter resourcesCallbackAdapter) {
        CallbackWithHandler callbackWithHandler = new CallbackWithHandler(resourcesCallbackAdapter, handler);
        return z ? FontRequestWorker.requestFontSync(context, fontRequest, callbackWithHandler, i, i2) : FontRequestWorker.requestFontAsync(context, fontRequest, i, callbackWithHandler);
    }
}
