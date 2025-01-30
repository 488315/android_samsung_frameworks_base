package com.android.systemui.subscreen;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import com.android.systemui.Dependency;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubScreenTimeOutHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SubScreenTimeOutHelper$contentObserver$1 contentObserver;
    public ContentResolver contentResolver;
    public final Supplier layoutParamsSupplier;
    public final SecPanelLogger panelLogger;
    public int screenTimeOut = 10000;
    public final Supplier subScreenQsWindowViewSupplier;
    public final Supplier windowManagerSupplier;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.subscreen.SubScreenTimeOutHelper$contentObserver$1] */
    public SubScreenTimeOutHelper(Supplier<WindowManager.LayoutParams> supplier, SecPanelLogger secPanelLogger, Supplier<SubScreenQuickPanelWindowView> supplier2, Supplier<WindowManager> supplier3) {
        this.layoutParamsSupplier = supplier;
        this.panelLogger = secPanelLogger;
        this.subScreenQsWindowViewSupplier = supplier2;
        this.windowManagerSupplier = supplier3;
        final Handler handler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
        this.contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.subscreen.SubScreenTimeOutHelper$contentObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                onChange(z);
                SubScreenTimeOutHelper subScreenTimeOutHelper = SubScreenTimeOutHelper.this;
                int i = SubScreenTimeOutHelper.$r8$clinit;
                int readScreenTimeOut = subScreenTimeOutHelper.readScreenTimeOut();
                SubScreenTimeOutHelper subScreenTimeOutHelper2 = SubScreenTimeOutHelper.this;
                int i2 = subScreenTimeOutHelper2.screenTimeOut;
                if (readScreenTimeOut != i2) {
                    ((SecPanelLoggerImpl) subScreenTimeOutHelper2.panelLogger).addCoverPanelStateLog(AbstractC0970x34f4116a.m94m("onChange: ", i2, " -> ", readScreenTimeOut));
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) SubScreenTimeOutHelper.this.layoutParamsSupplier.get();
                    if (layoutParams != null) {
                        SubScreenTimeOutHelper subScreenTimeOutHelper3 = SubScreenTimeOutHelper.this;
                        layoutParams.semSetScreenTimeout(readScreenTimeOut);
                        WindowManager windowManager = (WindowManager) subScreenTimeOutHelper3.windowManagerSupplier.get();
                        if (windowManager != null) {
                            windowManager.updateViewLayout((View) subScreenTimeOutHelper3.subScreenQsWindowViewSupplier.get(), layoutParams);
                        }
                    }
                    subScreenTimeOutHelper2.screenTimeOut = readScreenTimeOut;
                }
            }
        };
    }

    public final int readScreenTimeOut() {
        ContentResolver contentResolver = this.contentResolver;
        if (contentResolver == null) {
            Log.w("SubScreenTimeOutHelper", "readScreenTimeOut: contentResolver is not initialized");
            return 10000;
        }
        if (contentResolver == null) {
            contentResolver = null;
        }
        int i = Settings.System.getInt(contentResolver, "cover_screen_timeout", 10) * 1000;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("readScreenTimeOut: ", i, "SubScreenTimeOutHelper");
        return i;
    }
}
