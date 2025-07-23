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
        int max = Math.max(Math.max(255, Resources.getSystem().getInteger(R.integer.config_screenBrightnessExtendedMaximum)), Resources.getSystem().getInteger(R.integer.config_coverScreenBrightnessExtendedMaximum));
        sScreenExtendedBrightnessRangeMaximumInt = max;
        sScreenExtendedBrightnessRangeMaximumFloat = max / 255.0f;
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
                int screenBrightnessInt;
                screenBrightnessInt = BrightnessSynchronizer.this.getScreenBrightnessInt();
                return Integer.valueOf(screenBrightnessInt);
            }
        }, new Consumer() { // from class: com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BrightnessSynchronizer.this.setScreenBrightnessInt(((Integer) obj).intValue());
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
    /* JADX WARN: Code restructure failed: missing block: B:21:0x009d, code lost:
    
        if (r9 == 1) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateScreenBrightness(int r9) {
        /*
            r8 = this;
            int r0 = r8.getScreenBrightnessInt()
            float r1 = r8.getScreenBrightnessFloat()
            int r2 = brightnessFloatToInt(r1)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "updateScreenBrightness: type="
            r3.<init>(r4)
            r3.append(r9)
            java.lang.String r4 = " mPreferredSettingValue="
            r3.append(r4)
            float r4 = r8.mPreferredSettingValue
            r3.append(r4)
            java.lang.String r4 = "("
            r3.append(r4)
            float r5 = r8.mPreferredSettingValue
            int r5 = brightnessFloatToInt(r5)
            r3.append(r5)
            java.lang.String r5 = ") currentBrightnessInt="
            r3.append(r5)
            r3.append(r0)
            java.lang.String r5 = " currentBrightnessIntFromFloat="
            r3.append(r5)
            r3.append(r2)
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = ")"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "BrightnessSynchronizer"
            android.util.Slog.d(r4, r3)
            if (r0 != r2) goto L7a
            float r9 = r8.mPreferredSettingValue
            int r9 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r9 == 0) goto L79
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "synced: mPreferredSettingValue: "
            r9.<init>(r0)
            float r0 = r8.mPreferredSettingValue
            r9.append(r0)
            java.lang.String r0 = " currentBrightnessFloat: "
            r9.append(r0)
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            android.util.Slog.d(r4, r9)
            r8.mPreferredSettingValue = r1
        L79:
            return
        L7a:
            float r3 = r8.mPreferredSettingValue
            boolean r3 = java.lang.Float.isNaN(r3)
            java.lang.String r5 = " -> "
            r6 = 2
            r7 = 1
            if (r3 == 0) goto L8c
            if (r9 != r7) goto L89
            goto Lca
        L89:
            if (r9 != r6) goto Lca
            goto L9f
        L8c:
            float r3 = r8.mPreferredSettingValue
            int r3 = brightnessFloatToInt(r3)
            if (r0 != r3) goto L95
            goto L9f
        L95:
            if (r2 != r3) goto L98
            goto Lca
        L98:
            java.lang.String r2 = "onChange: both changed"
            android.util.Slog.e(r4, r2)
            if (r9 != r7) goto Lca
        L9f:
            int r9 = brightnessFloatToInt(r1)
            r8.mPreferredSettingValue = r1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "onChange: last float changed: "
            r0.<init>(r2)
            r0.append(r1)
            r0.append(r5)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            android.util.Slog.d(r4, r0)
            android.content.Context r8 = r8.mContext
            android.content.ContentResolver r8 = r8.getContentResolver()
            java.lang.String r0 = "screen_brightness"
            r1 = -2
            android.provider.Settings.System.putIntForUser(r8, r0, r9, r1)
            return
        Lca:
            float r9 = brightnessIntToFloat(r0)
            r8.mPreferredSettingValue = r9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "onChange: last int changed: "
            r1.<init>(r2)
            r1.append(r0)
            r1.append(r5)
            r1.append(r9)
            java.lang.String r0 = r1.toString()
            android.util.Slog.d(r4, r0)
            android.hardware.display.DisplayManager r8 = r8.mDisplayManager
            r0 = 0
            r8.setBrightness(r0, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.display.BrightnessSynchronizer.updateScreenBrightness(int):void");
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
        float convertGammaToLinear = BrightnessUtils.convertGammaToLinear(MathUtils.norm(1.0f, 255.0f, i));
        Display display = context.getDisplay();
        if (display == null || (brightnessInfo = display.getBrightnessInfo()) == null) {
            return Float.NaN;
        }
        return MathUtils.lerp(brightnessInfo.brightnessMinimum, brightnessInfo.brightnessMaximum, convertGammaToLinear);
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
            int constrainBrightnessInt = constrainBrightnessInt(this.mScreenBrightnessIntGetter.get().intValue());
            float screenBrightnessFloat = getScreenBrightnessFloat();
            int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(screenBrightnessFloat);
            Slog.d(BrightnessSynchronizer.TAG, "updateScreenBrightness: displayId=" + this.mDisplayId + " type=" + i + " mPreferredSettingValue=" + this.mPreferredSettingValue + NavigationBarInflaterView.KEY_CODE_START + BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue) + ") currentBrightnessInt=" + constrainBrightnessInt + " currentBrightnessIntFromFloat=" + brightnessFloatToInt + NavigationBarInflaterView.KEY_CODE_START + screenBrightnessFloat + NavigationBarInflaterView.KEY_CODE_END);
            if (constrainBrightnessInt != brightnessFloatToInt) {
                syncBrightnessValue(constrainBrightnessInt, screenBrightnessFloat, checkFloatTypeChanged(i, constrainBrightnessInt, brightnessFloatToInt));
                return;
            }
            if (this.mPreferredSettingValue != screenBrightnessFloat) {
                Slog.d(BrightnessSynchronizer.TAG, "synced: mPreferredSettingValue: " + this.mPreferredSettingValue + " currentBrightnessFloat: " + screenBrightnessFloat);
                this.mPreferredSettingValue = screenBrightnessFloat;
            }
        }

        private void syncBrightnessValue(int i, float f, boolean z) {
            if (z) {
                int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f);
                this.mPreferredSettingValue = f;
                Slog.d(BrightnessSynchronizer.TAG, "onChange: last float changed: " + f + " -> " + brightnessFloatToInt);
                this.mScreenBrightnessIntSetter.accept(Integer.valueOf(brightnessFloatToInt));
                return;
            }
            float brightnessIntToFloat = BrightnessSynchronizer.brightnessIntToFloat(i);
            this.mPreferredSettingValue = brightnessIntToFloat;
            Slog.d(BrightnessSynchronizer.TAG, "onChange: last int changed: " + i + " -> " + brightnessIntToFloat);
            setScreenBrightnessFloat(brightnessIntToFloat);
        }

        private boolean checkFloatTypeChanged(int i, int i2, int i3) {
            if (i2 == -1) {
                return true;
            }
            if (Float.isNaN(this.mPreferredSettingValue)) {
                return i != 1 && i == 2;
            }
            int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(this.mPreferredSettingValue);
            if (i2 == brightnessFloatToInt) {
                return true;
            }
            if (i3 == brightnessFloatToInt) {
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
