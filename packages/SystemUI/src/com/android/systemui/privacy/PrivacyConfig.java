package com.android.systemui.privacy;

import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrivacyConfig implements Dumpable {
    public final List callbacks = new ArrayList();
    public final PrivacyConfig$devicePropertiesChangedListener$1 devicePropertiesChangedListener;
    public boolean locationAvailable;
    public boolean mediaProjectionAvailable;
    public boolean micCameraAvailable;
    public final DelayableExecutor uiExecutor;

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

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0022, code lost:
    
        if (r6.getBoolean("privacy", "location_indicators_enabled", false) != false) goto L6;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PrivacyConfig(com.android.systemui.util.concurrency.DelayableExecutor r5, com.android.systemui.util.DeviceConfigProxy r6, com.android.systemui.dump.DumpManager r7) {
        /*
            r4 = this;
            r4.<init>()
            r4.uiExecutor = r5
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r4.callbacks = r0
            java.lang.String r0 = "privacy"
            java.lang.String r1 = "camera_mic_icons_enabled"
            r2 = 1
            boolean r1 = r6.getBoolean(r0, r1, r2)
            r4.micCameraAvailable = r1
            boolean r1 = com.android.systemui.ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP
            if (r1 != 0) goto L24
            java.lang.String r1 = "location_indicators_enabled"
            r3 = 0
            boolean r1 = r6.getBoolean(r0, r1, r3)
            if (r1 == 0) goto L25
        L24:
            r3 = r2
        L25:
            r4.locationAvailable = r3
            java.lang.String r1 = "media_projection_indicators_enabled"
            boolean r1 = r6.getBoolean(r0, r1, r2)
            r4.mediaProjectionAvailable = r1
            com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1 r1 = new com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1
            r1.<init>()
            r4.devicePropertiesChangedListener = r1
            java.lang.String r2 = "PrivacyConfig"
            r7.registerNormalDumpable(r2, r4)
            r6.addOnPropertiesChangedListener(r0, r5, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.PrivacyConfig.<init>(com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.util.DeviceConfigProxy, com.android.systemui.dump.DumpManager):void");
    }

    public final void addCallback(Callback callback) {
        final WeakReference weakReference = new WeakReference(callback);
        this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyConfig$addCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                ((ArrayList) PrivacyConfig.this.callbacks).add(weakReference);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("PrivacyConfig state:");
        asIndenting.increaseIndent();
        try {
            asIndenting.println("micCameraAvailable: " + this.micCameraAvailable);
            asIndenting.println("locationAvailable: " + this.locationAvailable);
            asIndenting.println("mediaProjectionAvailable: " + this.mediaProjectionAvailable);
            asIndenting.println("Callbacks:");
            asIndenting.increaseIndent();
            try {
                Iterator it = this.callbacks.iterator();
                while (it.hasNext()) {
                    Callback callback = (Callback) ((WeakReference) it.next()).get();
                    if (callback != null) {
                        asIndenting.println(callback);
                    }
                }
                asIndenting.decreaseIndent();
                asIndenting.flush();
            } finally {
                asIndenting.decreaseIndent();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onFlagLocationChanged(boolean z) {
        }

        default void onFlagMicCameraChanged(boolean z) {
        }

        default void onFlagMediaProjectionChanged() {
        }
    }
}
