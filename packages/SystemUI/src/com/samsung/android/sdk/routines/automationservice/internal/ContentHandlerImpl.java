package com.samsung.android.sdk.routines.automationservice.internal;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.samsung.android.sdk.routines.automationservice.interfaces.ChangeObserver;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ContentHandlerImpl implements ContentHandler {
    public ContentHandlerImpl$register$1 contentObserver;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static void notifyChange(Context context, String str, String str2) {
        context.getContentResolver().notifyChange(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/" + str + '/' + str2), null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl$register$1] */
    public final void register(final Context context, String str, final ChangeObserver changeObserver) {
        ContentHandlerImpl$register$1 contentHandlerImpl$register$1 = this.contentObserver;
        if (contentHandlerImpl$register$1 != null) {
            context.getContentResolver().unregisterContentObserver(contentHandlerImpl$register$1);
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        this.contentObserver = new ContentObserver(context, handler) { // from class: com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl$register$1
            {
                super(handler);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                Log.INSTANCE.getClass();
                Log.i("ContentHandlerImpl", "onChange: " + uri);
                if (uri == null || uri.getLastPathSegment() == null) {
                    return;
                }
                ChangeObserver.this.getClass();
            }
        };
        Uri parse = Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/" + str + '/');
        parse.getClass();
        ContentHandlerImpl$register$1 contentHandlerImpl$register$12 = this.contentObserver;
        if (contentHandlerImpl$register$12 != null) {
            context.getContentResolver().registerContentObserver(parse, true, contentHandlerImpl$register$12);
        }
    }
}
