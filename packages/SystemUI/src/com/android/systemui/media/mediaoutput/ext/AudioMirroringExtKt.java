package com.android.systemui.media.mediaoutput.ext;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            m3422exceptionOrNullimpl.printStackTrace();
        }
        Bundle bundle = (Bundle) (failure instanceof Result.Failure ? null : failure);
        if (bundle == null || (string = bundle.getString("mediaPackageName", "")) == null) {
            return "";
        }
        Log.i("AudioMirroringExt", "getAudioMirroringPackageName() - ".concat(string));
        return string;
    }
}
