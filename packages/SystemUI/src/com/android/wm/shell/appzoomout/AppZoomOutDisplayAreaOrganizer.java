package com.android.wm.shell.appzoomout;

import android.content.Context;
import android.util.ArrayMap;
import android.view.SurfaceControl;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.wm.shell.common.DisplayLayout;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class AppZoomOutDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float mCornerRadius;
    public final Map mDisplayAreaTokenMap;
    public final DisplayLayout mDisplayLayout;
    public Boolean mIsHomeTaskFocused;
    public float mProgress;

    public AppZoomOutDisplayAreaOrganizer(Context context, DisplayLayout displayLayout, Executor executor) {
        super(executor);
        DisplayLayout displayLayout2 = new DisplayLayout();
        this.mDisplayLayout = displayLayout2;
        this.mDisplayAreaTokenMap = new ArrayMap();
        this.mProgress = -1.0f;
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        displayLayout2.set(displayLayout);
    }

    public final void apply() {
        if (this.mIsHomeTaskFocused == null || this.mProgress == -1.0f) {
            return;
        }
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        final float f = this.mProgress * (this.mIsHomeTaskFocused.booleanValue() ? 0.05f : 0.025f);
        ((ArrayMap) this.mDisplayAreaTokenMap).forEach(new BiConsumer() { // from class: com.android.wm.shell.appzoomout.AppZoomOutDisplayAreaOrganizer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AppZoomOutDisplayAreaOrganizer appZoomOutDisplayAreaOrganizer = this.f$0;
                SurfaceControl.Transaction transaction2 = transaction;
                float f2 = f;
                SurfaceControl surfaceControl = (SurfaceControl) obj2;
                if (f2 == 0.0f) {
                    int i = AppZoomOutDisplayAreaOrganizer.$r8$clinit;
                    appZoomOutDisplayAreaOrganizer.getClass();
                    transaction2.setCrop(surfaceControl, null).setScale(surfaceControl, 1.0f, 1.0f).setPosition(surfaceControl, 0.0f, 0.0f).setCornerRadius(surfaceControl, 0.0f);
                } else {
                    DisplayLayout displayLayout = appZoomOutDisplayAreaOrganizer.mDisplayLayout;
                    float f3 = 1.0f - f2;
                    SurfaceControl.Transaction scale = transaction2.setCrop(surfaceControl, 0.0f, 0.0f, displayLayout.mWidth, displayLayout.mHeight).setScale(surfaceControl, f3, f3);
                    DisplayLayout displayLayout2 = appZoomOutDisplayAreaOrganizer.mDisplayLayout;
                    scale.setPosition(surfaceControl, displayLayout2.mWidth * f2 * 0.5f, f2 * displayLayout2.mHeight * 0.5f).setCornerRadius(surfaceControl, appZoomOutDisplayAreaOrganizer.mCornerRadius * f3);
                }
            }
        });
        transaction.apply();
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        surfaceControl.setUnreleasedWarningCallSite("AppZoomOutDisplayAreaOrganizer.onDisplayAreaAppeared");
        ((ArrayMap) this.mDisplayAreaTokenMap).put(displayAreaInfo.token, surfaceControl);
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        SurfaceControl surfaceControl = (SurfaceControl) ((ArrayMap) this.mDisplayAreaTokenMap).get(displayAreaInfo.token);
        if (surfaceControl != null) {
            surfaceControl.release();
        }
        ((ArrayMap) this.mDisplayAreaTokenMap).remove(displayAreaInfo.token);
    }

    public final void unregisterOrganizer() {
        super.unregisterOrganizer();
        if (this.mProgress != 0.0f) {
            this.mProgress = 0.0f;
            apply();
        }
        this.mProgress = -1.0f;
        this.mIsHomeTaskFocused = null;
    }
}
