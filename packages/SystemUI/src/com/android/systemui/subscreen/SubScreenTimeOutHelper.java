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
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SubScreenTimeOutHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SubScreenTimeOutHelper$contentObserver$1 contentObserver;
    public ContentResolver contentResolver;
    public final Supplier layoutParamsSupplier;
    public int screenTimeOut = 10000;
    public final Supplier subScreenQsWindowViewSupplier;
    public final Supplier windowManagerSupplier;

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

    public SubScreenTimeOutHelper(Supplier<WindowManager.LayoutParams> supplier, Supplier<SubScreenQuickPanelWindowView> supplier2, Supplier<WindowManager> supplier3) {
        this.layoutParamsSupplier = supplier;
        this.subScreenQsWindowViewSupplier = supplier2;
        this.windowManagerSupplier = supplier3;
        final Handler handler = (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER);
        this.contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.subscreen.SubScreenTimeOutHelper$contentObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                onChange(z);
                SubScreenTimeOutHelper subScreenTimeOutHelper = SubScreenTimeOutHelper.this;
                int i = SubScreenTimeOutHelper.$r8$clinit;
                int readScreenTimeOut = subScreenTimeOutHelper.readScreenTimeOut();
                SubScreenTimeOutHelper subScreenTimeOutHelper2 = SubScreenTimeOutHelper.this;
                if (readScreenTimeOut != subScreenTimeOutHelper2.screenTimeOut) {
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) subScreenTimeOutHelper2.layoutParamsSupplier.get();
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
        int i = Settings.System.getInt(contentResolver, SettingsHelper.COVER_SCREEN_TIME_OUT, 10) * 1000;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "readScreenTimeOut: ", "SubScreenTimeOutHelper");
        return i;
    }
}
