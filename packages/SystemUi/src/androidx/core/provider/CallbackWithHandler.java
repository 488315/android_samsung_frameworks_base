package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import androidx.core.provider.FontRequestWorker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CallbackWithHandler {
    public final FontsContractCompat$FontRequestCallback mCallback;
    public final Handler mCallbackHandler;

    public CallbackWithHandler(FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback, Handler handler) {
        this.mCallback = fontsContractCompat$FontRequestCallback;
        this.mCallbackHandler = handler;
    }

    public final void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        final int i = typefaceResult.mResult;
        boolean z = i == 0;
        Handler handler = this.mCallbackHandler;
        final FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback = this.mCallback;
        if (!z) {
            handler.post(new Runnable(this) { // from class: androidx.core.provider.CallbackWithHandler.2
                @Override // java.lang.Runnable
                public final void run() {
                    fontsContractCompat$FontRequestCallback.onTypefaceRequestFailed(i);
                }
            });
        } else {
            final Typeface typeface = typefaceResult.mTypeface;
            handler.post(new Runnable(this) { // from class: androidx.core.provider.CallbackWithHandler.1
                @Override // java.lang.Runnable
                public final void run() {
                    fontsContractCompat$FontRequestCallback.onTypefaceRetrieved(typeface);
                }
            });
        }
    }

    public CallbackWithHandler(FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback) {
        Handler handler;
        this.mCallback = fontsContractCompat$FontRequestCallback;
        if (Looper.myLooper() == null) {
            handler = new Handler(Looper.getMainLooper());
        } else {
            handler = new Handler();
        }
        this.mCallbackHandler = handler;
    }
}
