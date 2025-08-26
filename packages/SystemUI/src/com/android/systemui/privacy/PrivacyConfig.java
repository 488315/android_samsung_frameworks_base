package com.android.systemui.privacy;

import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.ScRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class PrivacyConfig implements Dumpable {
    public final List callbacks = new ArrayList();
    public final PrivacyConfig$devicePropertiesChangedListener$1 devicePropertiesChangedListener;
    public boolean locationAvailable;
    public boolean mediaProjectionAvailable;
    public boolean micCameraAvailable;
    public final DelayableExecutor uiExecutor;

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0024  */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PrivacyConfig(DelayableExecutor delayableExecutor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager) {
        boolean z;
        this.uiExecutor = delayableExecutor;
        this.micCameraAvailable = deviceConfigProxy.getBoolean("privacy", "camera_mic_icons_enabled", true);
        if (!ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP) {
            z = deviceConfigProxy.getBoolean("privacy", "location_indicators_enabled", false);
        }
        this.locationAvailable = z;
        this.mediaProjectionAvailable = deviceConfigProxy.getBoolean("privacy", "media_projection_indicators_enabled", true);
        ?? r1 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if ("privacy".equals(properties.getNamespace())) {
                    int i = 0;
                    if (properties.getKeyset().contains("camera_mic_icons_enabled")) {
                        this.this$0.micCameraAvailable = properties.getBoolean("camera_mic_icons_enabled", true);
                        PrivacyConfig privacyConfig = this.this$0;
                        ArrayList arrayList = (ArrayList) privacyConfig.callbacks;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            PrivacyConfig.Callback callback = (PrivacyConfig.Callback) ((WeakReference) obj).get();
                            if (callback != null) {
                                callback.onFlagMicCameraChanged(privacyConfig.micCameraAvailable);
                            }
                        }
                    }
                    if (!ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP && properties.getKeyset().contains("location_indicators_enabled")) {
                        this.this$0.locationAvailable = properties.getBoolean("location_indicators_enabled", false);
                        PrivacyConfig privacyConfig2 = this.this$0;
                        ArrayList arrayList2 = (ArrayList) privacyConfig2.callbacks;
                        int size2 = arrayList2.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj2 = arrayList2.get(i3);
                            i3++;
                            PrivacyConfig.Callback callback2 = (PrivacyConfig.Callback) ((WeakReference) obj2).get();
                            if (callback2 != null) {
                                callback2.onFlagLocationChanged(privacyConfig2.locationAvailable);
                            }
                        }
                    }
                    if (properties.getKeyset().contains("media_projection_indicators_enabled")) {
                        this.this$0.mediaProjectionAvailable = properties.getBoolean("media_projection_indicators_enabled", true);
                        ArrayList arrayList3 = (ArrayList) this.this$0.callbacks;
                        int size3 = arrayList3.size();
                        while (i < size3) {
                            Object obj3 = arrayList3.get(i);
                            i++;
                            PrivacyConfig.Callback callback3 = (PrivacyConfig.Callback) ((WeakReference) obj3).get();
                            if (callback3 != null) {
                                callback3.onFlagMediaProjectionChanged();
                            }
                        }
                    }
                }
            }
        };
        this.devicePropertiesChangedListener = r1;
        dumpManager.registerNormalDumpable("PrivacyConfig", this);
        deviceConfigProxy.addOnPropertiesChangedListener("privacy", delayableExecutor, r1);
    }

    public final void addCallback(Callback callback) {
        final WeakReference weakReference = new WeakReference(callback);
        this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyConfig.addCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                ((ArrayList) PrivacyConfig.this.callbacks).add(weakReference);
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("PrivacyConfig state:");
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            indentingPrintWriterAsIndenting.println("micCameraAvailable: " + this.micCameraAvailable);
            indentingPrintWriterAsIndenting.println("locationAvailable: " + this.locationAvailable);
            indentingPrintWriterAsIndenting.println("mediaProjectionAvailable: " + this.mediaProjectionAvailable);
            indentingPrintWriterAsIndenting.println("Callbacks:");
            indentingPrintWriterAsIndenting.increaseIndent();
            try {
                Iterator it = this.callbacks.iterator();
                while (it.hasNext()) {
                    Callback callback = (Callback) ((WeakReference) it.next()).get();
                    if (callback != null) {
                        indentingPrintWriterAsIndenting.println(callback);
                    }
                }
                indentingPrintWriterAsIndenting.decreaseIndent();
                indentingPrintWriterAsIndenting.flush();
            } finally {
                indentingPrintWriterAsIndenting.decreaseIndent();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public interface Callback {
        default void onFlagLocationChanged(boolean z) {
        }

        default void onFlagMicCameraChanged(boolean z) {
        }

        default void onFlagMediaProjectionChanged() {
        }
    }
}
