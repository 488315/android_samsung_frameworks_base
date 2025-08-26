package com.android.systemui.display.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SamsungSecondScreenUtil {
    public static final Uri URI;
    public final Context context;
    public boolean isConnectedState;
    public final SamsungSecondScreenUtil$observer$1 observer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        URI = Uri.parse("content://com.samsung.android.secondscreen/second_screen_connected");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.database.ContentObserver, com.android.systemui.display.util.SamsungSecondScreenUtil$observer$1] */
    public SamsungSecondScreenUtil(Context context, final Handler handler) {
        this.context = context;
        ?? r0 = new ContentObserver(handler) { // from class: com.android.systemui.display.util.SamsungSecondScreenUtil$observer$1
            /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
            
                r3 = r3.this$0;
             */
            @Override // android.database.ContentObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onChange(boolean z, Uri uri) {
                SamsungSecondScreenUtil samsungSecondScreenUtil;
                boolean z2;
                boolean z3;
                String type = this.this$0.context.getContentResolver().getType(SamsungSecondScreenUtil.URI);
                if (type == null || (z3 = samsungSecondScreenUtil.isConnectedState) == (z2 = Boolean.parseBoolean(type))) {
                    return;
                }
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isConnectedState: ", " >> ", "SamsungSecondScreenUtil", z3, z2);
                samsungSecondScreenUtil.isConnectedState = z2;
            }
        };
        this.observer = r0;
        try {
            if (context.getPackageManager().getPackageInfo("com.samsung.android.secondscreen", 128) != null) {
                context.getContentResolver().registerContentObserver(URI, false, r0);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }
}
