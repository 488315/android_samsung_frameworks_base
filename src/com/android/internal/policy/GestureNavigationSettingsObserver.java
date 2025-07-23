package com.android.internal.policy;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.config.sysui.SystemUiDeviceConfigFlags;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class GestureNavigationSettingsObserver extends ContentObserver {
    private Handler mBgHandler;
    private Context mContext;
    private Handler mMainHandler;
    private Runnable mOnChangeRunnable;
    private final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;

    public GestureNavigationSettingsObserver(Handler handler, Handler handler2, Context context, Runnable runnable) {
        super(handler);
        this.mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver.1
            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (!"systemui".equals(properties.getNamespace()) || GestureNavigationSettingsObserver.this.mOnChangeRunnable == null) {
                    return;
                }
                GestureNavigationSettingsObserver.this.mOnChangeRunnable.run();
            }
        };
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mContext = context;
        this.mOnChangeRunnable = runnable;
    }

    public void register() {
        this.mBgHandler.post(new Runnable() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsObserver.this.lambda$register$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$register$1() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), false, this, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.NAVIGATION_MODE), false, this, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.BOTTOM_GESTURE_INSET_SCALE), false, this, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.NAVIGATIONBAR_GESTURES_DETAIL_TYPE), false, this, -1);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                GestureNavigationSettingsObserver.this.lambda$register$0(runnable);
            }
        }, this.mOnPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$register$0(Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void registerForCallingUser() {
        this.mBgHandler.post(new Runnable() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsObserver.this.lambda$registerForCallingUser$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerForCallingUser$3() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), false, this);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda4
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                GestureNavigationSettingsObserver.this.lambda$registerForCallingUser$2(runnable);
            }
        }, this.mOnPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerForCallingUser$2(Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    public void unregister() {
        this.mBgHandler.post(new Runnable() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsObserver.this.lambda$unregister$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregister$4() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
        DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        Runnable runnable = this.mOnChangeRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    public int getLeftSensitivity(Resources resources) {
        return (int) (getUnscaledInset(resources) * Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f, -2));
    }

    public int getLeftSensitivityForCallingUser(Resources resources) {
        return (int) (getUnscaledInset(resources) * Settings.Secure.getFloat(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f));
    }

    public int getRightSensitivity(Resources resources) {
        return (int) (getUnscaledInset(resources) * Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f, -2));
    }

    public int getRightSensitivityForCallingUser(Resources resources) {
        return (int) (getUnscaledInset(resources) * Settings.Secure.getFloat(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f));
    }

    public boolean areNavigationButtonForcedVisible() {
        String str = SystemProperties.get("setupwizard.theme", "");
        return (str.equals("glif_expressive") || str.equals("glif_expressive_light") || Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, -2) != 0) ? false : true;
    }

    private float getUnscaledInset(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float dimension = resources.getDimension(R.dimen.config_backGestureInset) / displayMetrics.density;
        if (dimension > 0.0f) {
            dimension = DeviceConfig.getFloat("systemui", SystemUiDeviceConfigFlags.BACK_GESTURE_EDGE_WIDTH, dimension);
        }
        return TypedValue.applyDimension(1, dimension, displayMetrics);
    }

    public int getBottomSensitivity(Resources resources) {
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) == 0;
        boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURE_HINT, 1) == 1;
        if (z || z2) {
            return resources.getDimensionPixelSize(R.dimen.navigation_bar_gesture_height);
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) (TypedValue.applyDimension(1, resources.getDimension(R.dimen.navigation_bar_gesture_height) / displayMetrics.density, displayMetrics) * Settings.Global.getFloat(this.mContext.getContentResolver(), Settings.Global.BOTTOM_GESTURE_INSET_SCALE, 1.0f));
    }
}
