package com.android.wm.shell.compatui.coverlauncher;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class CoverLauncherAppCompatService extends Service {
    public IActivityTaskManager mActivityTaskManager;
    public DisplayManager mDisplayManager;
    public CoverLauncherAppCompatFloatingIcon mFloatingIcon;
    public String mType = "";
    public final CoverLauncherAppCompatServiceBinder mBinder = new CoverLauncherAppCompatServiceBinder(this);
    public int mLastRotation = 0;
    public final AnonymousClass1 mDisplayListener = new AnonymousClass1();

    public class CoverLauncherAppCompatServiceBinder extends Binder {
        public CoverLauncherAppCompatServiceBinder(CoverLauncherAppCompatService coverLauncherAppCompatService) {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mActivityTaskManager = ActivityTaskManager.getService();
        DisplayManager displayManager = (DisplayManager) getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, null);
    }

    @Override // android.app.Service
    public final void onDestroy() throws Resources.NotFoundException {
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        updateFloatingIcon(this.mLastRotation, "clear");
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) throws Resources.NotFoundException {
        if (intent == null) {
            return 1;
        }
        String stringExtra = intent.getStringExtra("compatModeType");
        boolean booleanExtra = intent.getBooleanExtra("compatStart", false);
        Log.i("CoverLauncherAppCompatService", "startId : " + i2 + " compatModeType : " + stringExtra);
        if (stringExtra != null && !this.mType.equals(stringExtra)) {
            this.mType = stringExtra;
            updateFloatingIcon(this.mLastRotation, stringExtra);
            if ("clear".equals(stringExtra) || booleanExtra) {
                return 1;
            }
            try {
                this.mActivityTaskManager.scheduleRecomputeConfigurationLocked();
            } catch (RemoteException e) {
                Log.e("CoverLauncherAppCompatService", "Failed to scheduleRecomputeConfigurationLocked", e);
            }
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateFloatingIcon(int i, String str) throws Resources.NotFoundException {
        CoverLauncherAppCompatFloatingIcon coverLauncherAppCompatFloatingIcon = this.mFloatingIcon;
        if (coverLauncherAppCompatFloatingIcon != null) {
            coverLauncherAppCompatFloatingIcon.fadeOutAnimation();
        }
        if ("".equals(str) || "clear".equals(str)) {
            return;
        }
        final CoverLauncherAppCompatFloatingIcon coverLauncherAppCompatFloatingIcon2 = new CoverLauncherAppCompatFloatingIcon(this, str);
        this.mFloatingIcon = coverLauncherAppCompatFloatingIcon2;
        coverLauncherAppCompatFloatingIcon2.mIconSize = coverLauncherAppCompatFloatingIcon2.mContext.getResources().getDimensionPixelSize(R.dimen.fw_cover_launcher_app_compat_type_button_size);
        int i2 = coverLauncherAppCompatFloatingIcon2.mIconSize;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i2, i2, 2605, 552, -3);
        coverLauncherAppCompatFloatingIcon2.mLayoutParam = layoutParams;
        layoutParams.setFitInsetsTypes(0);
        coverLauncherAppCompatFloatingIcon2.mLayoutParam.semAddExtensionFlags(131072);
        WindowManager.LayoutParams layoutParams2 = coverLauncherAppCompatFloatingIcon2.mLayoutParam;
        layoutParams2.layoutInDisplayCutoutMode = 3;
        if (i == 2) {
            layoutParams2.gravity = 49;
        } else {
            layoutParams2.gravity = 81;
        }
        layoutParams2.setTitle("CoverLauncherAppCompatFloatingIcon");
        String str2 = coverLauncherAppCompatFloatingIcon2.mType;
        boolean zEquals = "fullscreen".equals(str2);
        int i3 = R.layout.fw_cover_launcher_app_compat_fullscreen_button_layout;
        if (zEquals) {
            if (i == 2) {
                i3 = R.layout.fw_cover_launcher_app_compat_fullscreen_button_layout_rotation_180;
            }
        } else if ("landscape".equals(str2)) {
            i3 = i == 2 ? R.layout.fw_cover_launcher_app_compat_landscape_button_layout_rotation_180 : R.layout.fw_cover_launcher_app_compat_landscape_button_layout;
        } else if ("portrait".equals(str2)) {
            i3 = i == 2 ? R.layout.fw_cover_launcher_app_compat_portrait_button_layout_rotation_180 : R.layout.fw_cover_launcher_app_compat_portrait_button_layout;
        } else if (i == 2) {
        }
        View viewInflate = View.inflate(coverLauncherAppCompatFloatingIcon2.mContext, i3, null);
        coverLauncherAppCompatFloatingIcon2.mOverlayView = viewInflate;
        coverLauncherAppCompatFloatingIcon2.mIconArea = viewInflate.findViewById(R.id.icon_area);
        coverLauncherAppCompatFloatingIcon2.mOverlayView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                CoverLauncherAppCompatFloatingIcon coverLauncherAppCompatFloatingIcon3 = coverLauncherAppCompatFloatingIcon2;
                coverLauncherAppCompatFloatingIcon3.getClass();
                Intent intent = new Intent();
                intent.setComponent(ComponentName.unflattenFromString("com.android.systemui/com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService"));
                String str3 = coverLauncherAppCompatFloatingIcon3.mType;
                if ("fullscreen".equals(str3)) {
                    intent.putExtra("compatModeType", "landscape");
                } else if ("landscape".equals(str3)) {
                    intent.putExtra("compatModeType", "portrait");
                } else if ("portrait".equals(str3)) {
                    intent.putExtra("compatModeType", "fullscreen");
                }
                coverLauncherAppCompatFloatingIcon3.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
                coverLauncherAppCompatFloatingIcon3.fadeOutAnimation();
                Settings.System.putString(coverLauncherAppCompatFloatingIcon3.mContext.getContentResolver(), "cover_launcher_app_compat_mode", str3);
            }
        });
        coverLauncherAppCompatFloatingIcon2.mOverlayView.forceHasOverlappingRendering(true);
        Log.i("CoverLauncherAppCompatFloatingIcon", "addView ");
        coverLauncherAppCompatFloatingIcon2.mWindowManager.addView(coverLauncherAppCompatFloatingIcon2.mOverlayView, coverLauncherAppCompatFloatingIcon2.mLayoutParam);
        coverLauncherAppCompatFloatingIcon2.mIconArea.startAnimation(AnimationUtils.loadAnimation(coverLauncherAppCompatFloatingIcon2.mContext, R.anim.fw_cover_launcher_app_compat_floating_icon_fadein));
    }

    /* renamed from: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService$1, reason: invalid class name */
    public class AnonymousClass1 implements DisplayManager.DisplayListener {
        public AnonymousClass1() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) throws Resources.NotFoundException {
            if (CoverLauncherAppCompatService.this.mDisplayManager.getDisplay(i) == null) {
                return;
            }
            int rotation = CoverLauncherAppCompatService.this.mDisplayManager.getDisplay(i).getRotation();
            CoverLauncherAppCompatService coverLauncherAppCompatService = CoverLauncherAppCompatService.this;
            if (coverLauncherAppCompatService.mLastRotation != rotation) {
                if (rotation == 0 || rotation == 2) {
                    coverLauncherAppCompatService.mLastRotation = rotation;
                    coverLauncherAppCompatService.updateFloatingIcon(rotation, "clear");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            CoverLauncherAppCompatService coverLauncherAppCompatService2 = CoverLauncherAppCompatService.this;
                            coverLauncherAppCompatService2.updateFloatingIcon(coverLauncherAppCompatService2.mLastRotation, coverLauncherAppCompatService2.mType);
                        }
                    }, 500L);
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    }
}
