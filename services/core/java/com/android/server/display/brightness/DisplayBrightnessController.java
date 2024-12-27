package com.android.server.display.brightness;

import android.hardware.display.DisplayManagerInternal;
import android.os.HandlerExecutor;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.AutomaticBrightnessController;
import com.android.server.display.BrightnessSetting;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.PersistentDataStore;
import com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2;
import com.android.server.display.brightness.strategy.DisplayBrightnessStrategy;
import com.android.server.display.brightness.strategy.FollowerBrightnessStrategy;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayBrightnessController {
    AutomaticBrightnessController mAutomaticBrightnessController;
    public final HandlerExecutor mBrightnessChangeExecutor;
    public final BrightnessSetting mBrightnessSetting;
    public BrightnessSetting.BrightnessSettingListener mBrightnessSettingListener;
    public float mCurrentScreenBrightness;
    public DisplayBrightnessStrategy mDisplayBrightnessStrategy;
    public final DisplayBrightnessStrategySelector mDisplayBrightnessStrategySelector;
    public final int mDisplayId;
    public final Runnable mOnBrightnessChangeRunnable;
    public float mPendingScreenBrightness;
    public final boolean mPersistBrightnessNitsForDefaultDisplay;
    public final float mScreenBrightnessDefault;
    public final String mTag;
    public boolean mUserSetScreenBrightnessUpdated;
    public final Object mLock = new Object();
    public float mLastUserSetScreenBrightness = Float.NaN;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0078, code lost:
    
        if (java.lang.Float.isNaN(r0) != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DisplayBrightnessController(android.content.Context r5, int r6, float r7, com.android.server.display.BrightnessSetting r8, com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2 r9, android.os.HandlerExecutor r10, com.android.server.display.feature.DisplayManagerFlags r11) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            r4.mLock = r0
            r0 = 2143289344(0x7fc00000, float:NaN)
            r4.mLastUserSetScreenBrightness = r0
            com.android.server.display.brightness.DisplayBrightnessController$Injector r1 = new com.android.server.display.brightness.DisplayBrightnessController$Injector
            r1.<init>()
            r4.mDisplayId = r6
            r1 = 1
            if (r6 != r1) goto L1d
            boolean r2 = com.android.server.power.PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
            if (r2 == 0) goto L1d
            goto L1e
        L1d:
            r1 = 0
        L1e:
            r4.mBrightnessSetting = r8
            r4.mPendingScreenBrightness = r0
            float r7 = com.android.server.display.brightness.BrightnessUtils.clampAbsoluteBrightness(r7)
            r4.mScreenBrightnessDefault = r7
            r0 = -2
            if (r6 != 0) goto L58
            android.content.ContentResolver r1 = r5.getContentResolver()
            java.lang.String r2 = "screen_brightness"
            r3 = -1
            int r0 = android.provider.Settings.System.getIntForUser(r1, r2, r3, r0)
            float r0 = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(r0)
            boolean r1 = java.lang.Float.isNaN(r0)
            if (r1 == 0) goto L56
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getScreenBrightnessSettingOnBootPhase: default: "
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = r4.mTag
            com.android.server.power.Slog.d(r1, r0)
            goto L7a
        L56:
            r7 = r0
            goto L7a
        L58:
            if (r1 == 0) goto L7a
            android.content.res.Resources r1 = r5.getResources()
            r2 = 17695066(0x10e015a, float:2.608225E-38)
            int r1 = r1.getInteger(r2)
            android.content.ContentResolver r2 = r5.getContentResolver()
            java.lang.String r3 = "sub_screen_brightness"
            int r0 = android.provider.Settings.System.getIntForUser(r2, r3, r1, r0)
            float r0 = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(r0)
            boolean r1 = java.lang.Float.isNaN(r0)
            if (r1 == 0) goto L56
        L7a:
            r8.setBrightness(r7)
            r4.mCurrentScreenBrightness = r7
            r4.mOnBrightnessChangeRunnable = r9
            com.android.server.display.brightness.DisplayBrightnessStrategySelector r7 = new com.android.server.display.brightness.DisplayBrightnessStrategySelector
            r7.<init>(r5, r6, r11)
            r4.mDisplayBrightnessStrategySelector = r7
            r4.mBrightnessChangeExecutor = r10
            android.content.res.Resources r5 = r5.getResources()
            r7 = 17891835(0x11101fb, float:2.6633715E-38)
            boolean r5 = r5.getBoolean(r7)
            r4.mPersistBrightnessNitsForDefaultDisplay = r5
            java.lang.String r5 = "DisplayBrightnessController["
            java.lang.String r7 = "]"
            java.lang.String r5 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r6, r5, r7)
            r4.mTag = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.brightness.DisplayBrightnessController.<init>(android.content.Context, int, float, com.android.server.display.BrightnessSetting, com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2, android.os.HandlerExecutor, com.android.server.display.feature.DisplayManagerFlags):void");
    }

    public final float convertToAdjustedNits(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return -1.0f;
        }
        return automaticBrightnessController.mCurrentBrightnessMapper.convertToAdjustedNits(f);
    }

    public final float convertToNits(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return -1.0f;
        }
        return automaticBrightnessController.mCurrentBrightnessMapper.convertToNits(f);
    }

    public final float getBrightnessFromNits(float f) {
        AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return Float.NaN;
        }
        return automaticBrightnessController.mCurrentBrightnessMapper.getBrightnessFromNits(f);
    }

    public BrightnessSetting.BrightnessSettingListener getBrightnessSettingListener() {
        return this.mBrightnessSettingListener;
    }

    public final float getCurrentBrightness() {
        float f;
        synchronized (this.mLock) {
            f = this.mCurrentScreenBrightness;
        }
        return f;
    }

    public DisplayBrightnessStrategy getCurrentDisplayBrightnessStrategy() {
        DisplayBrightnessStrategy displayBrightnessStrategy;
        synchronized (this.mLock) {
            displayBrightnessStrategy = this.mDisplayBrightnessStrategy;
        }
        return displayBrightnessStrategy;
    }

    public final boolean getIsUserSetScreenBrightnessUpdated() {
        return this.mUserSetScreenBrightnessUpdated;
    }

    public final float getLastUserSetScreenBrightness() {
        float f;
        synchronized (this.mLock) {
            f = this.mLastUserSetScreenBrightness;
        }
        return f;
    }

    public final float getScreenBrightnessSetting() {
        float f;
        float clampAbsoluteBrightness;
        BrightnessSetting brightnessSetting = this.mBrightnessSetting;
        synchronized (brightnessSetting.mSyncRoot) {
            f = brightnessSetting.mBrightness;
        }
        synchronized (this.mLock) {
            try {
                if (Float.isNaN(f)) {
                    Slog.d(this.mTag, "getScreenBrightnessSetting: default: " + this.mScreenBrightnessDefault);
                    f = this.mScreenBrightnessDefault;
                }
                clampAbsoluteBrightness = BrightnessUtils.clampAbsoluteBrightness(f);
            } catch (Throwable th) {
                throw th;
            }
        }
        return clampAbsoluteBrightness;
    }

    public final boolean isAllowAutoBrightnessWhileDozing() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDisplayBrightnessStrategySelector.mAllowAutoBrightnessWhileDozing;
        }
        return z;
    }

    public final boolean isAllowAutoBrightnessWhileDozingConfig() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDisplayBrightnessStrategySelector.mAllowAutoBrightnessWhileDozingConfig;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setAutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController r6) {
        /*
            r5 = this;
            r5.mAutomaticBrightnessController = r6
            com.android.server.display.brightness.DisplayBrightnessStrategySelector r0 = r5.mDisplayBrightnessStrategySelector
            com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2 r0 = r0.mAutomaticBrightnessStrategy
            r0.setAutomaticBrightnessController(r6)
            int r6 = r5.mDisplayId
            if (r6 != 0) goto L56
            boolean r6 = r5.mPersistBrightnessNitsForDefaultDisplay
            if (r6 == 0) goto L56
            com.android.server.display.BrightnessSetting r6 = r5.mBrightnessSetting
            com.android.server.display.PersistentDataStore r6 = r6.mPersistentDataStore
            float r6 = r6.mBrightnessNitsForDefaultDisplay
            r0 = 0
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 < 0) goto L56
            float r6 = r5.getBrightnessFromNits(r6)
            boolean r0 = com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(r6)
            if (r0 == 0) goto L56
            com.android.server.display.BrightnessSetting r0 = r5.mBrightnessSetting
            r0.getClass()
            boolean r1 = java.lang.Float.isNaN(r6)
            if (r1 == 0) goto L39
            java.lang.String r0 = "BrightnessSetting"
            java.lang.String r1 = "Attempting to init invalid brightness"
            android.util.Slog.w(r0, r1)
            goto L58
        L39:
            com.android.server.display.DisplayManagerService$SyncRoot r1 = r0.mSyncRoot
            monitor-enter(r1)
            float r2 = r0.mBrightness     // Catch: java.lang.Throwable -> L4e
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 == 0) goto L50
            com.android.server.display.PersistentDataStore r2 = r0.mPersistentDataStore     // Catch: java.lang.Throwable -> L4e
            com.android.server.display.LogicalDisplay r3 = r0.mLogicalDisplay     // Catch: java.lang.Throwable -> L4e
            com.android.server.display.DisplayDevice r3 = r3.mPrimaryDisplayDevice     // Catch: java.lang.Throwable -> L4e
            int r4 = r0.mUserSerial     // Catch: java.lang.Throwable -> L4e
            r2.setBrightness(r3, r6, r4)     // Catch: java.lang.Throwable -> L4e
            goto L50
        L4e:
            r5 = move-exception
            goto L54
        L50:
            r0.mBrightness = r6     // Catch: java.lang.Throwable -> L4e
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
            goto L58
        L54:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
            throw r5
        L56:
            r6 = 2143289344(0x7fc00000, float:NaN)
        L58:
            boolean r0 = java.lang.Float.isNaN(r6)
            if (r0 == 0) goto L62
            float r6 = r5.getScreenBrightnessSetting()
        L62:
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            r5.mCurrentScreenBrightness = r6     // Catch: java.lang.Throwable -> L69
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L69
            return
        L69:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L69
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.brightness.DisplayBrightnessController.setAutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController):void");
    }

    public final void setBrightness(float f, float f2) {
        BrightnessSetting brightnessSetting = this.mBrightnessSetting;
        brightnessSetting.setBrightness(f);
        if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
            float convertToNits = convertToNits(f);
            PersistentDataStore persistentDataStore = brightnessSetting.mPersistentDataStore;
            float f3 = persistentDataStore.mBrightnessNitsForDefaultDisplay;
            if (convertToNits >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                if ((f != f2 || f3 <= convertToNits) && convertToNits != f3) {
                    persistentDataStore.mBrightnessNitsForDefaultDisplay = convertToNits;
                    persistentDataStore.mDirty = true;
                }
            }
        }
    }

    public final void setBrightnessToFollow(boolean z, float f) {
        synchronized (this.mLock) {
            FollowerBrightnessStrategy followerBrightnessStrategy = this.mDisplayBrightnessStrategySelector.mFollowerBrightnessStrategy;
            followerBrightnessStrategy.mBrightnessToFollow = f;
            followerBrightnessStrategy.mBrightnessToFollowSlowChange = z;
        }
    }

    public final DisplayBrightnessState updateBrightness(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i, DisplayManagerInternal.DisplayOffloadSession displayOffloadSession) {
        float f;
        DisplayBrightnessState updateBrightness;
        synchronized (this.mLock) {
            DisplayBrightnessStrategySelector displayBrightnessStrategySelector = this.mDisplayBrightnessStrategySelector;
            boolean updateUserSetScreenBrightness = updateUserSetScreenBrightness();
            synchronized (this.mLock) {
                f = this.mLastUserSetScreenBrightness;
            }
            StrategySelectionRequest strategySelectionRequest = new StrategySelectionRequest();
            strategySelectionRequest.mDisplayPowerRequest = displayPowerRequest;
            strategySelectionRequest.mTargetDisplayState = i;
            strategySelectionRequest.mLastUserSetScreenBrightness = f;
            strategySelectionRequest.mUserSetBrightnessChanged = updateUserSetScreenBrightness;
            strategySelectionRequest.mDisplayOffloadSession = displayOffloadSession;
            DisplayBrightnessStrategy selectStrategy = displayBrightnessStrategySelector.selectStrategy(strategySelectionRequest);
            this.mDisplayBrightnessStrategy = selectStrategy;
            updateBrightness = selectStrategy.updateBrightness(new StrategyExecutionRequest(displayPowerRequest, getCurrentBrightness(), this.mUserSetScreenBrightnessUpdated));
        }
        AutomaticBrightnessStrategy2 automaticBrightnessStrategy2 = this.mDisplayBrightnessStrategySelector.mAutomaticBrightnessStrategy;
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mBrightness = updateBrightness.mBrightness;
        builder.mSdrBrightness = updateBrightness.mSdrBrightness;
        builder.mBrightnessReason = updateBrightness.mBrightnessReason;
        builder.mDisplayBrightnessStrategyName = updateBrightness.mDisplayBrightnessStrategyName;
        builder.mShouldUseAutoBrightness = updateBrightness.mShouldUseAutoBrightness;
        builder.mIsSlowChange = updateBrightness.mIsSlowChange;
        builder.mMaxBrightness = updateBrightness.mMaxBrightness;
        builder.mMinBrightness = updateBrightness.mMinBrightness;
        builder.mCustomAnimationRate = updateBrightness.mCustomAnimationRate;
        builder.mShouldUpdateScreenBrightnessSetting = updateBrightness.mShouldUpdateScreenBrightnessSetting;
        builder.mBrightnessEvent = updateBrightness.mBrightnessEvent;
        builder.mBrightnessAdjustmentFlag = updateBrightness.mBrightnessAdjustmentFlag;
        builder.mIsUserInitiatedChange = updateBrightness.mIsUserInitiatedChange;
        builder.mShouldUseAutoBrightness = automaticBrightnessStrategy2 != null && automaticBrightnessStrategy2.shouldUseAutoBrightness();
        return new DisplayBrightnessState(builder);
    }

    public final boolean updateScreenBrightnessSetting(float f, float f2) {
        float clampAbsoluteBrightness = BrightnessUtils.clampAbsoluteBrightness(f);
        synchronized (this.mLock) {
            if (BrightnessUtils.isValidBrightnessValue(clampAbsoluteBrightness)) {
                float f3 = this.mCurrentScreenBrightness;
                if (clampAbsoluteBrightness != f3) {
                    if (clampAbsoluteBrightness != f3) {
                        this.mCurrentScreenBrightness = clampAbsoluteBrightness;
                    }
                    this.mBrightnessChangeExecutor.execute(this.mOnBrightnessChangeRunnable);
                    setBrightness(clampAbsoluteBrightness, f2);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean updateUserSetScreenBrightness() {
        this.mUserSetScreenBrightnessUpdated = false;
        synchronized (this.mLock) {
            try {
                if (!BrightnessUtils.isValidBrightnessValue(this.mPendingScreenBrightness)) {
                    if (BrightnessSynchronizer.floatEquals(this.mDisplayBrightnessStrategySelector.mTemporaryBrightnessStrategy.mTemporaryScreenBrightness, this.mCurrentScreenBrightness)) {
                        this.mDisplayBrightnessStrategySelector.mTemporaryBrightnessStrategy.mTemporaryScreenBrightness = Float.NaN;
                    }
                    return false;
                }
                float f = this.mCurrentScreenBrightness;
                float f2 = this.mPendingScreenBrightness;
                if (f == f2) {
                    this.mPendingScreenBrightness = Float.NaN;
                    this.mDisplayBrightnessStrategySelector.mTemporaryBrightnessStrategy.mTemporaryScreenBrightness = Float.NaN;
                    return false;
                }
                if (f2 != f) {
                    this.mCurrentScreenBrightness = f2;
                }
                this.mLastUserSetScreenBrightness = f2;
                this.mPendingScreenBrightness = Float.NaN;
                this.mDisplayBrightnessStrategySelector.mTemporaryBrightnessStrategy.mTemporaryScreenBrightness = Float.NaN;
                this.mBrightnessChangeExecutor.execute(this.mOnBrightnessChangeRunnable);
                this.mUserSetScreenBrightnessUpdated = true;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
