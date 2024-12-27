package com.android.internal.policy;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
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

    public GestureNavigationSettingsObserver(Handler mainHandler, Handler bgHandler, Context context, Runnable onChangeRunnable) {
        super(mainHandler);
        this.mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.policy.GestureNavigationSettingsObserver.1
            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                if ("systemui".equals(properties.getNamespace()) && GestureNavigationSettingsObserver.this.mOnChangeRunnable != null) {
                    GestureNavigationSettingsObserver.this.mOnChangeRunnable.run();
                }
            }
        };
        this.mMainHandler = mainHandler;
        this.mBgHandler = bgHandler;
        this.mContext = context;
        this.mOnChangeRunnable = onChangeRunnable;
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
        ContentResolver r = this.mContext.getContentResolver();
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this, -1);
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this, -1);
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), false, this, -1);
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.NAVIGATION_MODE), false, this, -1);
        r.registerContentObserver(Settings.Global.getUriFor(Settings.Global.BOTTOM_GESTURE_INSET_SCALE), false, this, -1);
        r.registerContentObserver(Settings.Global.getUriFor(Settings.Global.NAVIGATIONBAR_GESTURES_DETAIL_TYPE), false, this, -1);
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
        ContentResolver r = this.mContext.getContentResolver();
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT), false, this);
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT), false, this);
        r.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.USER_SETUP_COMPLETE), false, this);
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
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (this.mOnChangeRunnable != null) {
            this.mOnChangeRunnable.run();
        }
    }

    public int getLeftSensitivity(Resources userRes) {
        float scale = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f, -2);
        return (int) (getUnscaledInset(userRes) * scale);
    }

    public int getLeftSensitivityForCallingUser(Resources userRes) {
        float scale = Settings.Secure.getFloat(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT, 1.0f);
        return (int) (getUnscaledInset(userRes) * scale);
    }

    public int getRightSensitivity(Resources userRes) {
        float scale = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f, -2);
        return (int) (getUnscaledInset(userRes) * scale);
    }

    public int getRightSensitivityForCallingUser(Resources userRes) {
        float scale = Settings.Secure.getFloat(this.mContext.getContentResolver(), Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT, 1.0f);
        return (int) (getUnscaledInset(userRes) * scale);
    }

    public boolean areNavigationButtonForcedVisible() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, -2) == 0;
    }

    private float getUnscaledInset(Resources userRes) {
        float backGestureInset;
        DisplayMetrics dm = userRes.getDisplayMetrics();
        float defaultInset = userRes.getDimension(R.dimen.config_backGestureInset) / dm.density;
        if (defaultInset > 0.0f) {
            backGestureInset = DeviceConfig.getFloat("systemui", SystemUiDeviceConfigFlags.BACK_GESTURE_EDGE_WIDTH, defaultInset);
        } else {
            backGestureInset = defaultInset;
        }
        float inset = TypedValue.applyDimension(1, backGestureInset, dm);
        return inset;
    }

    public int getBottomSensitivity(Resources userRes) {
        boolean isButtonMode = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) == 0;
        boolean gestureHint = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURE_HINT, 1) == 1;
        if (isButtonMode || gestureHint) {
            return userRes.getDimensionPixelSize(R.dimen.navigation_bar_gesture_height);
        }
        DisplayMetrics dm = userRes.getDisplayMetrics();
        float bottomInset = userRes.getDimension(R.dimen.navigation_bar_gesture_height) / dm.density;
        float inset = TypedValue.applyDimension(1, bottomInset, dm);
        float scale = Settings.Global.getFloat(this.mContext.getContentResolver(), Settings.Global.BOTTOM_GESTURE_INSET_SCALE, 1.0f);
        return (int) (inset * scale);
    }
}
