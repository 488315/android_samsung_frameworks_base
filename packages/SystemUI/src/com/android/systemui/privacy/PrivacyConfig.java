package com.android.systemui.privacy;

import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
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

public final class PrivacyConfig implements Dumpable {
    public final List callbacks = new ArrayList();
    public final PrivacyConfig$devicePropertiesChangedListener$1 devicePropertiesChangedListener;
    public boolean locationAvailable;
    public boolean mediaProjectionAvailable;
    public boolean micCameraAvailable;
    public final DelayableExecutor uiExecutor;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public PrivacyConfig(DelayableExecutor delayableExecutor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager) {
        this.uiExecutor = delayableExecutor;
        this.micCameraAvailable = deviceConfigProxy.getBoolean("privacy", "camera_mic_icons_enabled", true);
        this.locationAvailable = deviceConfigProxy.getBoolean("privacy", "location_indicators_enabled", false);
        this.mediaProjectionAvailable = deviceConfigProxy.getBoolean("privacy", "media_projection_indicators_enabled", true);
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.privacy.PrivacyConfig$devicePropertiesChangedListener$1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if ("privacy".equals(properties.getNamespace())) {
                    if (properties.getKeyset().contains("camera_mic_icons_enabled")) {
                        PrivacyConfig.this.micCameraAvailable = properties.getBoolean("camera_mic_icons_enabled", true);
                        PrivacyConfig privacyConfig = PrivacyConfig.this;
                        Iterator it = ((ArrayList) privacyConfig.callbacks).iterator();
                        while (it.hasNext()) {
                            PrivacyConfig.Callback callback = (PrivacyConfig.Callback) ((WeakReference) it.next()).get();
                            if (callback != null) {
                                callback.onFlagMicCameraChanged(privacyConfig.micCameraAvailable);
                            }
                        }
                    }
                    if (properties.getKeyset().contains("location_indicators_enabled")) {
                        PrivacyConfig.this.locationAvailable = properties.getBoolean("location_indicators_enabled", false);
                        PrivacyConfig privacyConfig2 = PrivacyConfig.this;
                        Iterator it2 = ((ArrayList) privacyConfig2.callbacks).iterator();
                        while (it2.hasNext()) {
                            PrivacyConfig.Callback callback2 = (PrivacyConfig.Callback) ((WeakReference) it2.next()).get();
                            if (callback2 != null) {
                                callback2.onFlagLocationChanged(privacyConfig2.locationAvailable);
                            }
                        }
                    }
                    if (properties.getKeyset().contains("media_projection_indicators_enabled")) {
                        PrivacyConfig.this.mediaProjectionAvailable = properties.getBoolean("media_projection_indicators_enabled", true);
                        Iterator it3 = ((ArrayList) PrivacyConfig.this.callbacks).iterator();
                        while (it3.hasNext()) {
                            PrivacyConfig.Callback callback3 = (PrivacyConfig.Callback) ((WeakReference) it3.next()).get();
                            if (callback3 != null) {
                                callback3.onFlagMediaProjectionChanged();
                            }
                        }
                    }
                }
            }
        };
        DumpManager.registerDumpable$default(dumpManager, "PrivacyConfig", this);
        deviceConfigProxy.addOnPropertiesChangedListener("privacy", delayableExecutor, onPropertiesChangedListener);
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

    public interface Callback {
        default void onFlagMediaProjectionChanged() {
        }

        default void onFlagLocationChanged(boolean z) {
        }

        default void onFlagMicCameraChanged(boolean z) {
        }
    }
}
