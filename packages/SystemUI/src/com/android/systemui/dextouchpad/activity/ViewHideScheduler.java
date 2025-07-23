package com.android.systemui.dextouchpad.activity;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.util.Features;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ViewHideScheduler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long ICON_REMOVE_DELAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public ImageView mIcon;
    public final String mTitle;

    public ViewHideScheduler(ImageView imageView, String str) {
        this.mIcon = imageView;
        this.mTitle = str;
    }

    public final void cancel() {
        if (Features.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("cancel(), "), this.mTitle, "DexTouchpadViewHideScheduler");
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mIcon = null;
    }
}
