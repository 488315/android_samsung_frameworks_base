package com.android.wm.shell.compatui.coverlauncher;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverLauncherAppCompatFloatingIcon {
    public final Context mContext;
    public View mIconArea;
    public int mIconSize;
    public WindowManager.LayoutParams mLayoutParam;
    public View mOverlayView;
    public final String mType;
    public final WindowManager mWindowManager;

    public CoverLauncherAppCompatFloatingIcon(Context context, String str) {
        Display display;
        this.mContext = context;
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        int length = displays.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                display = null;
                break;
            }
            display = displays[i];
            if (display.getDisplayId() == 1) {
                break;
            } else {
                i++;
            }
        }
        if (display != null) {
            this.mWindowManager = (WindowManager) this.mContext.createDisplayContext(display).getSystemService(WindowManager.class);
        } else {
            this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        }
        this.mType = str;
    }

    public final void fadeOutAnimation() {
        this.mIconArea.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.fw_cover_launcher_app_compat_floating_icon_fadeout));
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CoverLauncherAppCompatFloatingIcon coverLauncherAppCompatFloatingIcon = CoverLauncherAppCompatFloatingIcon.this;
                View view = coverLauncherAppCompatFloatingIcon.mOverlayView;
                if (view != null) {
                    view.setVisibility(8);
                    if (coverLauncherAppCompatFloatingIcon.mOverlayView.isAttachedToWindow()) {
                        View view2 = coverLauncherAppCompatFloatingIcon.mOverlayView;
                        if (view2 != null) {
                            coverLauncherAppCompatFloatingIcon.mWindowManager.removeView(view2);
                        }
                        coverLauncherAppCompatFloatingIcon.mOverlayView = null;
                    }
                }
            }
        }, 100L);
    }
}
