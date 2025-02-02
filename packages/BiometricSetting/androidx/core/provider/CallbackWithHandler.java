package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat;

/* loaded from: classes.dex */
final class CallbackWithHandler {
    private final FontsContractCompat.FontRequestCallback mCallback;
    private final Handler mCallbackHandler;

    CallbackWithHandler(TypefaceCompat.ResourcesCallbackAdapter resourcesCallbackAdapter, Handler handler) {
        this.mCallback = resourcesCallbackAdapter;
        this.mCallbackHandler = handler;
    }

    final void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        final int i = typefaceResult.mResult;
        boolean z = i == 0;
        Handler handler = this.mCallbackHandler;
        final FontsContractCompat.FontRequestCallback fontRequestCallback = this.mCallback;
        if (!z) {
            handler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.2
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContractCompat.FontRequestCallback.this.getClass();
                }
            });
        } else {
            final Typeface typeface = typefaceResult.mTypeface;
            handler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.1
                @Override // java.lang.Runnable
                public final void run() {
                    FontsContractCompat.FontRequestCallback.this.onTypefaceRetrieved(typeface);
                }
            });
        }
    }
}
