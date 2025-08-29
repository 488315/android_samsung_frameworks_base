package com.android.systemui.media.mediaoutput.ext;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import kotlin.Result;

/* loaded from: classes2.dex */
public abstract class AudioMirroringExtKt {
    public static final String getAudioMirroringPackageName(Context context) {
        Object failure;
        String string;
        try {
            int i = Result.$r8$clinit;
            failure = context.getContentResolver().call(Uri.parse("content://com.samsung.android.audiomirroring"), "request_package_name", (String) null, (Bundle) null);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
        if (thM3441exceptionOrNullimpl != null) {
            thM3441exceptionOrNullimpl.printStackTrace();
        }
        Bundle bundle = (Bundle) (failure instanceof Result.Failure ? null : failure);
        if (bundle == null || (string = bundle.getString("mediaPackageName", "")) == null) {
            return "";
        }
        Log.i("AudioMirroringExt", "getAudioMirroringPackageName() - ".concat(string));
        return string;
    }
}
