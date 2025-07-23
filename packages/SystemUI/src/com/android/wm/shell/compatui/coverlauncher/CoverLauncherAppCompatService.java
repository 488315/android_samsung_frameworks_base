package com.android.wm.shell.compatui.coverlauncher;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverLauncherAppCompatService extends Service {
    public IActivityTaskManager mActivityTaskManager;
    public DisplayManager mDisplayManager;
    public CoverLauncherAppCompatFloatingIcon mFloatingIcon;
    public String mType = "";
    public final CoverLauncherAppCompatServiceBinder mBinder = new CoverLauncherAppCompatServiceBinder(this);
    public int mLastRotation = 0;
    public final AnonymousClass1 mDisplayListener = new AnonymousClass1();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final void onDestroy() {
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        updateFloatingIcon(this.mLastRotation, "clear");
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x006f, code lost:
    
        if (r8 == 2) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0071, code lost:
    
        r3 = com.android.systemui.R.layout.fw_cover_launcher_app_compat_fullscreen_button_layout_rotation_180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0098, code lost:
    
        if (r8 == 2) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateFloatingIcon(int r8, java.lang.String r9) {
        /*
            r7 = this;
            com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon r0 = r7.mFloatingIcon
            if (r0 == 0) goto L7
            r0.fadeOutAnimation()
        L7:
            java.lang.String r0 = ""
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto Ld9
            java.lang.String r0 = "clear"
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L19
            goto Ld9
        L19:
            com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon r0 = new com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon
            r0.<init>(r7, r9)
            r7.mFloatingIcon = r0
            android.content.Context r7 = r0.mContext
            android.content.res.Resources r7 = r7.getResources()
            r9 = 2131166366(0x7f07049e, float:1.7946975E38)
            int r7 = r7.getDimensionPixelSize(r9)
            r0.mIconSize = r7
            android.view.WindowManager$LayoutParams r1 = new android.view.WindowManager$LayoutParams
            int r2 = r0.mIconSize
            r5 = 552(0x228, float:7.74E-43)
            r6 = -3
            r4 = 2605(0xa2d, float:3.65E-42)
            r3 = r2
            r1.<init>(r2, r3, r4, r5, r6)
            r0.mLayoutParam = r1
            r7 = 0
            r1.setFitInsetsTypes(r7)
            android.view.WindowManager$LayoutParams r7 = r0.mLayoutParam
            r9 = 131072(0x20000, float:1.83671E-40)
            r7.semAddExtensionFlags(r9)
            android.view.WindowManager$LayoutParams r7 = r0.mLayoutParam
            r9 = 3
            r7.layoutInDisplayCutoutMode = r9
            r9 = 2
            if (r8 != r9) goto L56
            r1 = 49
            r7.gravity = r1
            goto L5a
        L56:
            r1 = 81
            r7.gravity = r1
        L5a:
            java.lang.String r1 = "CoverLauncherAppCompatFloatingIcon"
            r7.setTitle(r1)
            java.lang.String r7 = r0.mType
            java.lang.String r2 = "fullscreen"
            boolean r2 = r2.equals(r7)
            r3 = 2131558702(0x7f0d012e, float:1.8742727E38)
            r4 = 2131558703(0x7f0d012f, float:1.874273E38)
            if (r2 == 0) goto L73
            if (r8 != r9) goto L9b
        L71:
            r3 = r4
            goto L9b
        L73:
            java.lang.String r2 = "landscape"
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L85
            if (r8 != r9) goto L81
            r3 = 2131558705(0x7f0d0131, float:1.8742733E38)
            goto L9b
        L81:
            r3 = 2131558704(0x7f0d0130, float:1.8742731E38)
            goto L9b
        L85:
            java.lang.String r2 = "portrait"
            boolean r7 = r2.equals(r7)
            if (r7 == 0) goto L98
            if (r8 != r9) goto L94
            r3 = 2131558707(0x7f0d0133, float:1.8742737E38)
            goto L9b
        L94:
            r3 = 2131558706(0x7f0d0132, float:1.8742735E38)
            goto L9b
        L98:
            if (r8 != r9) goto L9b
            goto L71
        L9b:
            android.content.Context r7 = r0.mContext
            r8 = 0
            android.view.View r7 = android.view.View.inflate(r7, r3, r8)
            r0.mOverlayView = r7
            r8 = 2131363200(0x7f0a0580, float:1.8346202E38)
            android.view.View r7 = r7.findViewById(r8)
            r0.mIconArea = r7
            android.view.View r7 = r0.mOverlayView
            com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon$$ExternalSyntheticLambda1 r8 = new com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatFloatingIcon$$ExternalSyntheticLambda1
            r8.<init>()
            r7.setOnClickListener(r8)
            android.view.View r7 = r0.mOverlayView
            r8 = 1
            r7.forceHasOverlappingRendering(r8)
            java.lang.String r7 = "addView "
            android.util.Log.i(r1, r7)
            android.view.WindowManager r7 = r0.mWindowManager
            android.view.View r8 = r0.mOverlayView
            android.view.WindowManager$LayoutParams r9 = r0.mLayoutParam
            r7.addView(r8, r9)
            android.content.Context r7 = r0.mContext
            r8 = 2130772406(0x7f0101b6, float:1.714793E38)
            android.view.animation.Animation r7 = android.view.animation.AnimationUtils.loadAnimation(r7, r8)
            android.view.View r8 = r0.mIconArea
            r8.startAnimation(r7)
        Ld9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService.updateFloatingIcon(int, java.lang.String):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatService$1, reason: invalid class name */
    public class AnonymousClass1 implements DisplayManager.DisplayListener {
        public AnonymousClass1() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
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
                        public final void run() {
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
