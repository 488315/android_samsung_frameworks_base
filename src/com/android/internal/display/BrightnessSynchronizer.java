package com.android.internal.display;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import com.android.internal.R;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class BrightnessSynchronizer {
    private static final boolean COVER_DISPLAY_ENABLED = false;
    private static final boolean DEBUG = false;
    public static final float EPSILON = 1.0E-4f;
    private static final int MSG_RUN_UPDATE = 1;
    private static final Uri SUB_SCREEN_BRIGHTNESS_URI;
    private static final String TAG = "BrightnessSynchronizer";
    private static final int UPDATE_TYPE_FLOAT = 2;
    private static final int UPDATE_TYPE_INT = 1;
    private static final long WAIT_FOR_RESPONSE_MILLIS = 200;
    private static float sScreenExtendedBrightnessRangeMaximumFloat;
    private static int sScreenExtendedBrightnessRangeMaximumInt;
    private final BrightnessSyncObserver mBrightnessSyncObserver;
    private final Clock mClock;
    private final Context mContext;
    private BrightnessUpdate mCurrentUpdate;
    private DisplayManager mDisplayManager;
    private final SparseArray<DisplaySynchronizer> mDisplaySynchronizers;
    private final Handler mHandler;
    private final boolean mIntRangeUserPerceptionEnabled;
    private float mLatestFloatBrightness;
    private int mLatestIntBrightness;
    private BrightnessUpdate mPendingUpdate;
    private float mPreferredSettingValue;
    private static final Uri BRIGHTNESS_URI = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private static int sBrightnessUpdateCount = 1;

    public interface Clock {
        long uptimeMillis();
    }

    static {
        int iMax = Math.max(Math.max(255, Resources.getSystem().getInteger(R.integer.config_screenBrightnessExtendedMaximum)), Resources.getSystem().getInteger(R.integer.config_coverScreenBrightnessExtendedMaximum));
        sScreenExtendedBrightnessRangeMaximumInt = iMax;
        sScreenExtendedBrightnessRangeMaximumFloat = iMax / 255.0f;
        SUB_SCREEN_BRIGHTNESS_URI = Settings.System.getUriFor(Settings.System.SUB_SCREEN_BRIGHTNESS);
    }

    public BrightnessSynchronizer(Context context, Looper looper, boolean z) {
        this(context, looper, new Clock() { // from class: com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda0
            @Override // com.android.internal.display.BrightnessSynchronizer.Clock
            public final long uptimeMillis() {
                return SystemClock.uptimeMillis();
            }
        }, z);
    }

    public BrightnessSynchronizer(Context context, Looper looper, Clock clock, boolean z) {
        this.mPreferredSettingValue = Float.NaN;
        this.mDisplaySynchronizers = new SparseArray<>();
        this.mContext = context;
        this.mClock = clock;
        this.mBrightnessSyncObserver = new BrightnessSyncObserver();
        this.mHandler = new BrightnessSynchronizerHandler(looper);
        this.mIntRangeUserPerceptionEnabled = z;
    }

    public void startSynchronizing() {
        if (this.mDisplayManager == null) {
            this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        }
        if (this.mBrightnessSyncObserver.isObserving()) {
            Slog.wtf(TAG, "Brightness sync observer requesting synchronization a second time.");
            return;
        }
        this.mLatestFloatBrightness = getScreenBrightnessFloat();
        this.mLatestIntBrightness = getScreenBrightnessInt();
        Slog.i(TAG, "Initial brightness readings: " + this.mLatestIntBrightness + "(int), " + this.mLatestFloatBrightness + "(float)");
        if (!Float.isNaN(this.mLatestFloatBrightness)) {
            this.mPendingUpdate = new BrightnessUpdate(2, this.mLatestFloatBrightness);
        } else if (this.mLatestIntBrightness != -1) {
            this.mPendingUpdate = new BrightnessUpdate(1, this.mLatestIntBrightness);
        } else {
            float f = this.mContext.getResources().getFloat(R.dimen.config_screenBrightnessSettingDefaultFloat);
            this.mPendingUpdate = new BrightnessUpdate(2, f);
            Slog.i(TAG, "Setting initial brightness to default value of: " + f);
        }
        this.mDisplaySynchronizers.append(0, new DisplaySynchronizer(0, this.mDisplayManager, new Supplier() { // from class: com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Integer.valueOf(this.f$0.getScreenBrightnessInt());
            }
        }, new Consumer() { // from class: com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.setScreenBrightnessInt(((Integer) obj).intValue());
            }
        }));
        this.mBrightnessSyncObserver.startObserving(this.mHandler);
        this.mHandler.sendEmptyMessageAtTime(1, this.mClock.uptimeMillis());
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("BrightnessSynchronizer:");
        printWriter.println("-----------------------");
        printWriter.println("  mLatestIntBrightness=" + this.mLatestIntBrightness);
        printWriter.println("  mLatestFloatBrightness=" + this.mLatestFloatBrightness);
        printWriter.println("  mCurrentUpdate=" + this.mCurrentUpdate);
        printWriter.println("  mPendingUpdate=" + this.mPendingUpdate);
        printWriter.println("  mIntRangeUserPerceptionEnabled=" + this.mIntRangeUserPerceptionEnabled);
    }

    public static float brightnessIntToFloat(int i) {
        if (i == 0) {
            return 0.0f;
        }
        if (i == -1) {
            return Float.NaN;
        }
        return MathUtils.constrainedMap(0.0f, sScreenExtendedBrightnessRangeMaximumFloat, 0.0f, sScreenExtendedBrightnessRangeMaximumInt, i);
    }

    public static int brightnessFloatToInt(float f) {
        return Math.round(brightnessFloatToIntRange(f));
    }

    public static float brightnessFloatToIntRange(float f) {
        if (floatEquals(f, -1.0f)) {
            return 0.0f;
        }
        if (Float.isNaN(f)) {
            return -1.0f;
        }
        return MathUtils.constrainedMap(0.0f, sScreenExtendedBrightnessRangeMaximumInt, 0.0f, sScreenExtendedBrightnessRangeMaximumFloat, f);
    }

    private void handleBrightnessChangeFloat(float f) {
        this.mLatestFloatBrightness = f;
        handleBrightnessChange(2, f);
    }

    private void handleBrightnessChangeInt(int i) {
        this.mLatestIntBrightness = i;
        handleBrightnessChange(1, i);
    }

    private void handleBrightnessChange(int i, float f) {
        BrightnessUpdate brightnessUpdate;
        BrightnessUpdate brightnessUpdate2 = this.mCurrentUpdate;
        boolean z = brightnessUpdate2 != null && brightnessUpdate2.swallowUpdate(i, f);
        if (z) {
            brightnessUpdate = null;
        } else {
            brightnessUpdate = this.mPendingUpdate;
            this.mPendingUpdate = new BrightnessUpdate(i, f);
        }
        runUpdate();
        if (z || this.mPendingUpdate == null) {
            return;
        }
        Slog.i(TAG, "New PendingUpdate: " + this.mPendingUpdate + ", prev=" + brightnessUpdate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runUpdate() {
        BrightnessUpdate brightnessUpdate;
        do {
            BrightnessUpdate brightnessUpdate2 = this.mCurrentUpdate;
            if (brightnessUpdate2 != null) {
                brightnessUpdate2.update();
                if (this.mCurrentUpdate.isRunning()) {
                    return;
                }
                if (this.mCurrentUpdate.isCompleted()) {
                    if (this.mCurrentUpdate.madeUpdates()) {
                        Slog.i(TAG, "Completed Update: " + this.mCurrentUpdate);
                    }
                    this.mCurrentUpdate = null;
                }
            }
            if (this.mCurrentUpdate == null && (brightnessUpdate = this.mPendingUpdate) != null) {
                this.mCurrentUpdate = brightnessUpdate;
                this.mPendingUpdate = null;
            }
        } while (this.mCurrentUpdate != null);
    }

    private float getScreenBrightnessFloat() {
        return this.mDisplayManager.getBrightness(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScreenBrightnessInt() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1, -2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenBrightnessInt(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, i, -2);
    }

    private int getSubScreenBrightnessInt() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SUB_SCREEN_BRIGHTNESS, -1, -2);
    }

    private void setSubScreenBrightnessInt(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), Settings.System.SUB_SCREEN_BRIGHTNESS, i, -2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0089, code lost:
    
        if (r9 != 2) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x009d, code lost:
    
        if (r9 == 1) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateScreenBrightness(int i) {
        int screenBrightnessInt = getScreenBrightnessInt();
        float screenBrightnessFloat = getScreenBrightnessFloat();
        int iBrightnessFloatToInt = brightnessFloatToInt(screenBrightnessFloat);
        Slog.d(TAG, "updateScreenBrightness: type=" + i + " mPreferredSettingValue=" + this.mPreferredSettingValue + NavigationBarInflaterView.KEY_CODE_START + brightnessFloatToInt(this.mPreferredSettingValue) + ") currentBrightnessInt=" + screenBrightnessInt + " currentBrightnessIntFromFloat=" + iBrightnessFloatToInt + NavigationBarInflaterView.KEY_CODE_START + screenBrightnessFloat + NavigationBarInflaterView.KEY_CODE_END);
        if (screenBrightnessInt == iBrightnessFloatToInt) {
            if (this.mPreferredSettingValue != screenBrightnessFloat) {
                Slog.d(TAG, "synced: mPreferredSettingValue: " + this.mPreferredSettingValue + " currentBrightnessFloat: " + screenBrightnessFloat);
                this.mPreferredSettingValue = screenBrightnessFloat;
                return;
            }
            return;
        }
        if (!Float.isNaN(this.mPreferredSettingValue)) {
            int iBrightnessFloatToInt2 = brightnessFloatToInt(this.mPreferredSettingValue);
            if (screenBrightnessInt != iBrightnessFloatToInt2) {
                if (iBrightnessFloatToInt != iBrightnessFloatToInt2) {
                    Slog.e(TAG, "onChange: both changed");
                }
                float fBrightnessIntToFloat = brightnessIntToFloat(screenBrightnessInt);
                this.mPreferredSettingValue = fBrightnessIntToFloat;
                Slog.d(TAG, "onChange: last int changed: " + screenBrightnessInt + " -> " + fBrightnessIntToFloat);
                this.mDisplayManager.setBrightness(0, fBrightnessIntToFloat);
            }
            int iBrightnessFloatToInt3 = brightnessFloatToInt(screenBrightnessFloat);
            this.mPreferredSettingValue = screenBrightnessFloat;
            Slog.d(TAG, "onChange: last float changed: " + screenBrightnessFloat + " -> " + iBrightnessFloatToInt3);
            Settings.System.putIntForUser(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, iBrightnessFloatToInt3, -2);
            return;
        }
        if (i != 1) {
        }
        float fBrightnessIntToFloat2 = brightnessIntToFloat(screenBrightnessInt);
        this.mPreferredSettingValue = fBrightnessIntToFloat2;
        Slog.d(TAG, "onChange: last int changed: " + screenBrightnessInt + " -> " + fBrightnessIntToFloat2);
        this.mDisplayManager.setBrightness(0, fBrightnessIntToFloat2);
    }

    public static boolean floatEquals(float f, float f2) {
        if (f == f2) {
            return true;
        }
        return (Float.isNaN(f) && Float.isNaN(f2)) || Math.abs(f - f2) < 1.0E-4f;
    }

    public static float brightnessIntSettingToFloat(Context context, int i) {
        BrightnessInfo brightnessInfo;
        if (i == 0) {
            return -1.0f;
        }
        if (i == -1) {
            return Float.NaN;
        }
        float fConvertGammaToLinear = BrightnessUtils.convertGammaToLinear(MathUtils.norm(1.0f, 255.0f, i));
        Display display = context.getDisplay();
        if (display == null || (brightnessInfo = display.getBrightnessInfo()) == null) {
            return Float.NaN;
        }
        return MathUtils.lerp(brightnessInfo.brightnessMinimum, brightnessInfo.brightnessMaximum, fConvertGammaToLinear);
    }

    public static int brightnessFloatToIntSetting(Context context, float f) {
        Display display;
        BrightnessInfo brightnessInfo;
        if (floatEquals(f, -1.0f)) {
            return 0;
        }
        if (Float.isNaN(f) || (display = context.getDisplay()) == null || (brightnessInfo = display.getBrightnessInfo()) == null) {
            return -1;
        }
        return Math.round(MathUtils.lerp(1.0f, 255.0f, BrightnessUtils.convertLinearToGamma(MathUtils.norm(brightnessInfo.brightnessMinimum, brightnessInfo.brightnessMaximum, f))));
    }

    public class BrightnessUpdate {
        private static final int STATE_COMPLETED = 3;
        private static final int STATE_NOT_STARTED = 1;
        private static final int STATE_RUNNING = 2;
        static final int TYPE_FLOAT = 2;
        static final int TYPE_INT = 1;
        private final float mBrightness;
        private int mConfirmedTypes;
        private int mId;
        private final int mSourceType;
        private int mState;
        private long mTimeUpdated;
        private int mUpdatedTypes;

        BrightnessUpdate(int i, float f) {
            int i2 = BrightnessSynchronizer.sBrightnessUpdateCount;
            BrightnessSynchronizer.sBrightnessUpdateCount = i2 + 1;
            this.mId = i2;
            this.mSourceType = i;
            this.mBrightness = f;
            this.mTimeUpdated = 0L;
            this.mUpdatedTypes = 0;
            this.mConfirmedTypes = 0;
            this.mState = 1;
        }

        public String toString() {
            return "{[" + this.mId + "] " + toStringLabel(this.mSourceType, this.mBrightness) + ", mUpdatedTypes=" + this.mUpdatedTypes + ", mConfirmedTypes=" + this.mConfirmedTypes + ", mTimeUpdated=" + this.mTimeUpdated + "}";
        }

        void update() {
            if (this.mState == 1) {
                this.mState = 2;
                int brightnessAsInt = getBrightnessAsInt();
                if (BrightnessSynchronizer.this.mLatestIntBrightness != brightnessAsInt) {
                    Settings.System.putIntForUser(BrightnessSynchronizer.this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessAsInt, -2);
                    BrightnessSynchronizer.this.mLatestIntBrightness = brightnessAsInt;
                    this.mUpdatedTypes |= 1;
                }
                float brightnessAsFloat = getBrightnessAsFloat();
                if (!BrightnessSynchronizer.floatEquals(BrightnessSynchronizer.this.mLatestFloatBrightness, brightnessAsFloat)) {
                    BrightnessSynchronizer.this.mDisplayManager.setBrightness(0, brightnessAsFloat);
                    BrightnessSynchronizer.this.mLatestFloatBrightness = brightnessAsFloat;
                    this.mUpdatedTypes |= 2;
                }
                if (this.mUpdatedTypes != 0) {
                    Slog.i(BrightnessSynchronizer.TAG, NavigationBarInflaterView.SIZE_MOD_START + this.mId + "] New Update " + toStringLabel(this.mSourceType, this.mBrightness) + " set brightness values: " + toStringLabel(this.mUpdatedTypes & 2, brightnessAsFloat) + " " + toStringLabel(this.mUpdatedTypes & 1, brightnessAsInt));
                    BrightnessSynchronizer.this.mHandler.sendEmptyMessageAtTime(1, BrightnessSynchronizer.this.mClock.uptimeMillis() + 200);
                }
                this.mTimeUpdated = BrightnessSynchronizer.this.mClock.uptimeMillis();
            }
            if (this.mState == 2) {
                if (this.mConfirmedTypes == this.mUpdatedTypes || this.mTimeUpdated + 200 < BrightnessSynchronizer.this.mClock.uptimeMillis()) {
                    this.mState = 3;
                }
            }
        }

        boolean swallowUpdate(int i, float f) {
            if ((this.mUpdatedTypes & i) != i || (this.mConfirmedTypes & i) != 0) {
                return false;
            }
            boolean z = i == 2 && BrightnessSynchronizer.floatEquals(getBrightnessAsFloat(), f);
            boolean z2 = i == 1 && getBrightnessAsInt() == ((int) f);
            if (!z && !z2) {
                return false;
            }
            this.mConfirmedTypes |= i;
            Slog.i(BrightnessSynchronizer.TAG, "Swallowing update of " + toStringLabel(i, f) + " by update: " + this);
            return true;
        }

        boolean isRunning() {
            return this.mState == 2;
        }

        boolean isCompleted() {
            return this.mState == 3;
        }

        boolean madeUpdates() {
            return this.mUpdatedTypes != 0;
        }

        private int getBrightnessAsInt() {
            if (this.mSourceType == 1) {
                return (int) this.mBrightness;
            }
            if (BrightnessSynchronizer.this.mIntRangeUserPerceptionEnabled) {
                return BrightnessSynchronizer.brightnessFloatToIntSetting(BrightnessSynchronizer.this.mContext, this.mBrightness);
            }
            return BrightnessSynchronizer.brightnessFloatToInt(this.mBrightness);
        }

        private float getBrightnessAsFloat() {
            if (this.mSourceType == 2) {
                return this.mBrightness;
            }
            if (BrightnessSynchronizer.this.mIntRangeUserPerceptionEnabled) {
                return BrightnessSynchronizer.brightnessIntSettingToFloat(BrightnessSynchronizer.this.mContext, (int) this.mBrightness);
            }
            return BrightnessSynchronizer.brightnessIntToFloat((int) this.mBrightness);
        }

        private String toStringLabel(int i, float f) {
            if (i == 1) {
                return ((int) f) + "(i)";
            }
            if (i == 2) {
                return f + "(f)";
            }
            return "";
        }
    }

    class BrightnessSynchronizerHandler extends Handler {
        BrightnessSynchronizerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                BrightnessSynchronizer.this.runUpdate();
            } else {
                super.handleMessage(message);
            }
        }
    }

    private class BrightnessSyncObserver {
        private boolean mIsObserving;
        private final DisplayManager.DisplayListener mListener;

        private BrightnessSyncObserver() {
            this.mListener = new DisplayManager.DisplayListener() { // from class: com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) {
                    Slog.d(BrightnessSynchronizer.TAG, "onDisplayChanged() : displayId=" + i);
                    if (i == 0) {
                        ((DisplaySynchronizer) BrightnessSynchronizer.this.mDisplaySynchronizers.get(i)).updateScreenBrightness(2);
                    }
                }
            };
        }

        private ContentObserver createBrightnessContentObserver(Handler handler) {
            return new ContentObserver(handler) { // from class: com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    if (z) {
                        return;
                    }
                    Slog.d(BrightnessSynchronizer.TAG, "onChange : " + uri);
                    if (BrightnessSynchronizer.BRIGHTNESS_URI.equals(uri)) {
                        ((DisplaySynchronizer) BrightnessSynchronizer.this.mDisplaySynchronizers.get(0)).updateScreenBrightness(1);
                    }
                }
            };
        }

        boolean isObserving() {
            return this.mIsObserving;
        }

        void startObserving(Handler handler) {
            BrightnessSynchronizer.this.mContext.getContentResolver().registerContentObserver(BrightnessSynchronizer.BRIGHTNESS_URI, false, createBrightnessContentObserver(handler), -1);
            BrightnessSynchronizer.this.mDisplayManager.registerDisplayListener(this.mListener, handler, 0L, 1L);
            this.mIsObserving = true;
        }
    }

    public static class DisplaySynchronizer {
        private final int mDisplayId;
        private final DisplayManager mDisplayManager;
        public float mPreferredSettingValue = Float.NaN;
        private final Supplier<Integer> mScreenBrightnessIntGetter;
        private final Consumer<Integer> mScreenBrightnessIntSetter;

        public DisplaySynchronizer(int i, DisplayManager displayManager, Supplier<Integer> supplier, Consumer<Integer> consumer) {
            this.mDisplayId = i;
            this.mDisplayManager = displayManager;
            this.mScreenBrightnessIntGetter = supplier;
            this.mScreenBrightnessIntSetter = consumer;
        }

        void setScreenBrightnessFloat(float f) {
            this.mDisplayManager.setBrightness(this.mDisplayId, f);
        }

        float getScreenBrightnessFloat() {
            return this.mDisplayManager.getBrightness(this.mDisplayId);
        }

        public void updateScreenBrightness(int i) {
            int iConstrainBrightnessInt = constrainBrightnessInt(this.mScreenBrightnessIntGetter.get().intValue());
            float screenBrightnessFloat = getScreenBrightnessFloat();
            int iBrightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(screenBrightnessFloat);
            Slog.d(BrightnessSynchronizer.TAG, "updateScreenBrightness: displayId=" + this.mDisplayId + " type=" + i + " mPreferredSettingValue=" + this.mPreferredSettingValue + NavigationBarInflaterView.KEY_CODE_START + BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue) + ") currentBrightnessInt=" + iConstrainBrightnessInt + " currentBrightnessIntFromFloat=" + iBrightnessFloatToInt + NavigationBarInflaterView.KEY_CODE_START + screenBrightnessFloat + NavigationBarInflaterView.KEY_CODE_END);
            if (iConstrainBrightnessInt != iBrightnessFloatToInt) {
                syncBrightnessValue(iConstrainBrightnessInt, screenBrightnessFloat, checkFloatTypeChanged(i, iConstrainBrightnessInt, iBrightnessFloatToInt));
                return;
            }
            if (this.mPreferredSettingValue != screenBrightnessFloat) {
                Slog.d(BrightnessSynchronizer.TAG, "synced: mPreferredSettingValue: " + this.mPreferredSettingValue + " currentBrightnessFloat: " + screenBrightnessFloat);
                this.mPreferredSettingValue = screenBrightnessFloat;
            }
        }

        private void syncBrightnessValue(int i, float f, boolean z) {
            if (z) {
                int iBrightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f);
                this.mPreferredSettingValue = f;
                Slog.d(BrightnessSynchronizer.TAG, "onChange: last float changed: " + f + " -> " + iBrightnessFloatToInt);
                this.mScreenBrightnessIntSetter.accept(Integer.valueOf(iBrightnessFloatToInt));
                return;
            }
            float fBrightnessIntToFloat = BrightnessSynchronizer.brightnessIntToFloat(i);
            this.mPreferredSettingValue = fBrightnessIntToFloat;
            Slog.d(BrightnessSynchronizer.TAG, "onChange: last int changed: " + i + " -> " + fBrightnessIntToFloat);
            setScreenBrightnessFloat(fBrightnessIntToFloat);
        }

        private boolean checkFloatTypeChanged(int i, int i2, int i3) {
            if (i2 == -1) {
                return true;
            }
            if (Float.isNaN(this.mPreferredSettingValue)) {
                return i != 1 && i == 2;
            }
            int iBrightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue);
            if (i2 == iBrightnessFloatToInt) {
                return true;
            }
            if (i3 == iBrightnessFloatToInt) {
                return false;
            }
            Slog.e(BrightnessSynchronizer.TAG, "onChange: both changed");
            return i == 1;
        }

        private static int constrainBrightnessInt(int i) {
            return MathUtils.constrain(i, -1, 255);
        }
    }
}
