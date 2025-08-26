package com.android.systemui.settings.brightness.ui;

import android.view.View;
import android.view.WindowManager;
import com.android.systemui.toast.ToastFactory;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BrightnessWarningToast {
    public final ToastFactory toastFactory;
    public View toastView;
    public final WindowManager windowManager;

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

    public BrightnessWarningToast(ToastFactory toastFactory, WindowManager windowManager) {
        this.toastFactory = toastFactory;
        this.windowManager = windowManager;
    }
}
