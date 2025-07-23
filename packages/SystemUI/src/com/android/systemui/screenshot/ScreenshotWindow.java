package com.android.systemui.screenshot;

import android.R;
import android.content.Context;
import android.os.IBinder;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.policy.PhoneWindow;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotWindow {
    public final WindowManager.LayoutParams params;
    public final PhoneWindow window;
    public final WindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ScreenshotWindow create(Display display);
    }

    static {
        new Companion(null);
    }

    public ScreenshotWindow(WindowManager windowManager, Context context, Display display) {
        this.windowManager = windowManager;
        PhoneWindow phoneWindow = new PhoneWindow(context.createDisplayContext(display).createWindowContext(2036, null));
        this.window = phoneWindow;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 918816, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        layoutParams.setTitle("ScreenshotUI");
        this.params = layoutParams;
        phoneWindow.requestFeature(1);
        phoneWindow.requestFeature(13);
        phoneWindow.setBackgroundDrawableResource(R.color.transparent);
        phoneWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
    }
}
