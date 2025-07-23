package com.samsung.android.sdk.command.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.command.Command;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CommandProvider extends ContentProvider {
    public static final String[] WELL_KNOWN_CALLING_PACKAGES = {"com.android.settings.intelligence", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.app.routines", "com.samsung.android.app.settings.bixby", "com.samsung.accessibility", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};
    public static final String[] CORE_SYSTEM_PACKAGES = {"com.android.settings.intelligence", "com.samsung.android.app.galaxyfinder", "com.samsung.android.app.galaxyregistry", "com.sec.android.app.launcher"};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.samsung.android.sdk.command.provider.CommandProvider$1, reason: invalid class name */
    public class AnonymousClass1 implements ICommandActionCallback {
        public final /* synthetic */ Bundle val$bundle;
        public final /* synthetic */ String val$commandId;
        public final /* synthetic */ ICommandActionHandler val$handler;

        public AnonymousClass1(CommandProvider commandProvider, Bundle bundle, ICommandActionHandler iCommandActionHandler, String str) {
            this.val$bundle = bundle;
            this.val$handler = iCommandActionHandler;
            this.val$commandId = str;
        }

        public final void onActionFinished(int i, String str) {
            this.val$bundle.putInt("response_code", i);
            this.val$bundle.putString("response_message", str);
            Command loadStatefulCommand = this.val$handler.loadStatefulCommand(this.val$commandId);
            if (loadStatefulCommand != null) {
                this.val$bundle.putBundle("command", loadStatefulCommand.getDataBundle());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x02bc, code lost:
    
        if (r11.containsKey("response_code") == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x02be, code lost:
    
        r11.putInt("response_code", 2);
        com.samsung.android.sdk.command.util.LogWrapper.e("CommandProvider", "failed to load all commands");
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02ed, code lost:
    
        if (r11.containsKey("response_code") != false) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0135, code lost:
    
        if (r11.containsKey("response_code") == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0137, code lost:
    
        r11.putInt("response_code", r7);
        com.samsung.android.sdk.command.util.LogWrapper.e("CommandProvider", "cannot create command list");
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0165, code lost:
    
        if (r11.containsKey("response_code") != false) goto L180;
     */
    /* JADX WARN: Removed duplicated region for block: B:90:0x03f3 A[Catch: all -> 0x03e8, Exception -> 0x03eb, TryCatch #1 {Exception -> 0x03eb, blocks: (B:111:0x03cb, B:113:0x03d3, B:115:0x03e3, B:90:0x03f3, B:91:0x03ff, B:88:0x03ed), top: B:110:0x03cb, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0415  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle call(java.lang.String r17, java.lang.String r18, android.os.Bundle r19) {
        /*
            Method dump skipped, instructions count: 1290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.command.provider.CommandProvider.call(java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
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

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
