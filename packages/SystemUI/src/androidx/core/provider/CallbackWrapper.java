package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import androidx.core.provider.FontRequestWorker;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class CallbackWrapper {
    public final FontsContractCompat$FontRequestCallback mCallback;
    public final Executor mExecutor;

    public CallbackWrapper(FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback) {
        this(fontsContractCompat$FontRequestCallback, new RequestExecutor$HandlerExecutor(Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler()));
    }

    public final void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        final int i = typefaceResult.mResult;
        final FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback = this.mCallback;
        if (i != 0) {
            this.mExecutor.execute(new Runnable(this) { // from class: androidx.core.provider.CallbackWrapper.2
                @Override // java.lang.Runnable
                public final void run() {
                    fontsContractCompat$FontRequestCallback.onTypefaceRequestFailed(i);
                }
            });
        } else {
            final Typeface typeface = typefaceResult.mTypeface;
            this.mExecutor.execute(new Runnable(this) { // from class: androidx.core.provider.CallbackWrapper.1
                @Override // java.lang.Runnable
                public final void run() {
                    fontsContractCompat$FontRequestCallback.onTypefaceRetrieved(typeface);
                }
            });
        }
    }

    public CallbackWrapper(FontsContractCompat$FontRequestCallback fontsContractCompat$FontRequestCallback, Executor executor) {
        this.mCallback = fontsContractCompat$FontRequestCallback;
        this.mExecutor = executor;
    }
}
