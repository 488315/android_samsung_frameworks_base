package com.android.systemui.screenshot;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda8;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActionIntentCreator {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final PackageManager packageManager;

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

    public ActionIntentCreator(Context context, PackageManager packageManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.packageManager = packageManager;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public static Intent createShare(Uri uri, String str, String str2) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uriWithoutUserId, "image/png");
        intent.putExtra("android.intent.extra.STREAM", uriWithoutUserId);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uriWithoutUserId)));
        if (str != null) {
            intent.putExtra("android.intent.extra.SUBJECT", str);
        }
        if (str2 != null) {
            intent.putExtra("android.intent.extra.TEXT", str2);
        }
        intent.addFlags(1);
        intent.addFlags(2);
        return Intent.createChooser(intent, null).addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID).addFlags(268435456).addFlags(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object createEdit(android.net.Uri r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.screenshot.ActionIntentCreator$createEdit$2
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.screenshot.ActionIntentCreator$createEdit$2 r0 = (com.android.systemui.screenshot.ActionIntentCreator$createEdit$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.ActionIntentCreator$createEdit$2 r0 = new com.android.systemui.screenshot.ActionIntentCreator$createEdit$2
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 2131952730(0x7f13045a, float:1.954191E38)
            r3 = 1
            if (r1 == 0) goto L69
            if (r1 != r3) goto L61
            java.lang.Object r4 = r0.L$3
            android.content.Intent r4 = (android.content.Intent) r4
            java.lang.Object r5 = r0.L$2
            android.content.Intent r5 = (android.content.Intent) r5
            java.lang.Object r1 = r0.L$1
            android.net.Uri r1 = (android.net.Uri) r1
            java.lang.Object r0 = r0.L$0
            com.android.systemui.screenshot.ActionIntentCreator r0 = (com.android.systemui.screenshot.ActionIntentCreator) r0
            kotlin.ResultKt.throwOnFailure(r6)
            android.content.ComponentName r6 = (android.content.ComponentName) r6
            if (r6 != 0) goto L5d
            r0.getClass()
            int r6 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L4d
            android.content.Context r6 = r0.context     // Catch: java.lang.Throwable -> L4d
            java.lang.String r6 = r6.getString(r2)     // Catch: java.lang.Throwable -> L4d
            android.content.ComponentName r6 = android.content.ComponentName.unflattenFromString(r6)     // Catch: java.lang.Throwable -> L4d
            goto L56
        L4d:
            r6 = move-exception
            int r0 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r0 = new kotlin.Result$Failure
            r0.<init>(r6)
            r6 = r0
        L56:
            boolean r0 = r6 instanceof kotlin.Result.Failure
            if (r0 == 0) goto L5b
            r6 = 0
        L5b:
            android.content.ComponentName r6 = (android.content.ComponentName) r6
        L5d:
            r4.setComponent(r6)
            goto L8a
        L61:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L69:
            kotlin.ResultKt.throwOnFailure(r6)
            android.net.Uri r1 = android.content.ContentProvider.getUriWithoutUserId(r5)
            android.content.Intent r5 = new android.content.Intent
            java.lang.String r6 = "android.intent.action.EDIT"
            r5.<init>(r6)
            android.content.Context r4 = r4.context
            java.lang.String r4 = r4.getString(r2)
            int r6 = r4.length()
            if (r6 <= 0) goto L8a
            android.content.ComponentName r4 = android.content.ComponentName.unflattenFromString(r4)
            r5.setComponent(r4)
        L8a:
            java.lang.String r4 = "image/png"
            android.content.Intent r4 = r5.setDataAndType(r1, r4)
            java.lang.String r5 = "edit_source"
            java.lang.String r6 = "screenshot"
            android.content.Intent r4 = r4.putExtra(r5, r6)
            android.content.Intent r4 = r4.addFlags(r3)
            r5 = 2
            android.content.Intent r4 = r4.addFlags(r5)
            r5 = 268435456(0x10000000, float:2.524355E-29)
            android.content.Intent r4 = r4.addFlags(r5)
            r5 = 32768(0x8000, float:4.5918E-41)
            android.content.Intent r4 = r4.addFlags(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ActionIntentCreator.createEdit(android.net.Uri, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void createEdit(Uri uri, LongScreenshotActivity$$ExternalSyntheticLambda8 longScreenshotActivity$$ExternalSyntheticLambda8) {
        BuildersKt.launch$default(this.applicationScope, null, null, new ActionIntentCreator$createEdit$1(longScreenshotActivity$$ExternalSyntheticLambda8, this, uri, null), 3);
    }
}
