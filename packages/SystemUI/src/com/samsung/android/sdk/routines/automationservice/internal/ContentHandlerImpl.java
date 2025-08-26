package com.samsung.android.sdk.routines.automationservice.internal;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.samsung.android.sdk.routines.automationservice.interfaces.ChangeObserver;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class ContentHandlerImpl implements ContentHandler {
    public AnonymousClass1 contentObserver;

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
    public final void register(Context context, String str, ChangeObserver changeObserver) {
        AnonymousClass1 anonymousClass1 = this.contentObserver;
        if (anonymousClass1 != null) {
            context.getContentResolver().unregisterContentObserver(anonymousClass1);
        }
        this.contentObserver = new ContentObserver(context, new Handler(Looper.getMainLooper())) { // from class: com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl.register.1
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
                this.$observer.getClass();
            }
        };
        Uri uri = Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/" + str + '/');
        uri.getClass();
        AnonymousClass1 anonymousClass12 = this.contentObserver;
        if (anonymousClass12 != null) {
            context.getContentResolver().registerContentObserver(uri, true, anonymousClass12);
        }
    }
}
