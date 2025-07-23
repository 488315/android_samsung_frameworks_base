package com.android.systemui.settings.brightness.ui;

import android.view.View;
import android.view.WindowManager;
import com.android.systemui.toast.ToastFactory;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BrightnessWarningToast {
    public final ToastFactory toastFactory;
    public View toastView;
    public final WindowManager windowManager;

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

    public BrightnessWarningToast(ToastFactory toastFactory, WindowManager windowManager) {
        this.toastFactory = toastFactory;
        this.windowManager = windowManager;
    }
}
