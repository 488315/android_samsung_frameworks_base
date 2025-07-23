package com.android.systemui.plank.protocol;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.plank.dagger.PlankComponent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TestProtocolProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public SystemUIInitializer mInitializer;
    public PlankComponent plankComponent;

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

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.");
        }
        SystemUIInitializer onContextAvailable = contextAvailableCallback.onContextAvailable(context);
        this.mInitializer = onContextAvailable;
        onContextAvailable.getSysUIComponent().inject(this);
        super.attachInfo(context, providerInfo);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e2, code lost:
    
        if (r10 == null) goto L58;
     */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle call(java.lang.String r8, java.lang.String r9, android.os.Bundle r10) {
        /*
            Method dump skipped, instructions count: 675
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.plank.protocol.TestProtocolProvider.call(java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.contextAvailableCallback = contextAvailableCallback;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
