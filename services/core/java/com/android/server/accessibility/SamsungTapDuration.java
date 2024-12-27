package com.android.server.accessibility;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SamsungTapDuration extends BaseEventStreamTransformation {
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public EventStreamTransformation mNext;
    public TapDurationObserver mTapDurationObserver;
    public float mTapDurationThreshold;
    public long mTapDurationThresholdMilli;
    public final MotionEvent[] mDownEvent = new MotionEvent[4];
    public final MotionEvent[] mRawEvent = new MotionEvent[4];
    public final SamsungTapDurationProgressUI[] mDurationProgress = new SamsungTapDurationProgressUI[4];
    public final int[] mPolicyFlags = new int[4];
    public boolean mHoldingState = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TapDurationObserver extends ContentObserver {
        public ContentResolver mContentResolver;
        public final Uri mRemoveAnimationSettingUri;
        public final Uri mTapDurationSettingUri;
        public final int mUserId;

        public TapDurationObserver(int i, AnonymousClass1 anonymousClass1) {
            super(anonymousClass1);
            this.mTapDurationSettingUri = Settings.Secure.getUriFor("tap_duration_threshold");
            this.mRemoveAnimationSettingUri = Settings.Global.getUriFor("remove_animations");
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mTapDurationSettingUri.equals(uri)) {
                SamsungTapDuration.this.mTapDurationThreshold = Settings.Secure.getFloatForUser(this.mContentResolver, "tap_duration_threshold", 0.1f, this.mUserId);
                SamsungTapDuration samsungTapDuration = SamsungTapDuration.this;
                samsungTapDuration.mTapDurationThresholdMilli = (long) (samsungTapDuration.mTapDurationThreshold * 1000.0f);
            } else if (this.mRemoveAnimationSettingUri.equals(uri)) {
                boolean z2 = Settings.Global.getInt(this.mContentResolver, "remove_animations", 0) == 1;
                for (int i = 0; i < 4; i++) {
                    SamsungTapDurationProgressUI samsungTapDurationProgressUI = SamsungTapDuration.this.mDurationProgress[i];
                    if (samsungTapDurationProgressUI != null) {
                        samsungTapDurationProgressUI.setRemoveAnimationEnabled(z2);
                    }
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.accessibility.SamsungTapDuration$1] */
    public SamsungTapDuration(Context context, int i) {
        ?? r1 = new Handler() { // from class: com.android.server.accessibility.SamsungTapDuration.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 != 1) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unkwown message type: "));
                }
                int i3 = 0;
                while (true) {
                    SamsungTapDuration samsungTapDuration = SamsungTapDuration.this;
                    if (i3 >= 4) {
                        Log.d("SamsungTapDuration", "Tap is valid");
                        samsungTapDuration.mHoldingState = false;
                        return;
                    }
                    SamsungTapDurationProgressUI samsungTapDurationProgressUI = samsungTapDuration.mDurationProgress[i3];
                    if (samsungTapDurationProgressUI != null) {
                        samsungTapDurationProgressUI.getClass();
                        samsungTapDurationProgressUI.runOnUiThread(new SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(samsungTapDurationProgressUI, 3));
                    }
                    MotionEvent motionEvent = samsungTapDuration.mDownEvent[i3];
                    if (motionEvent != null) {
                        motionEvent.setDownTime(SystemClock.uptimeMillis());
                        try {
                            try {
                                samsungTapDuration.onActionTap(samsungTapDuration.mDownEvent[i3], samsungTapDuration.mRawEvent[i3], samsungTapDuration.mPolicyFlags[i3]);
                            } catch (IllegalArgumentException unused) {
                                Log.e("SamsungTapDuration", "idBits did not match any ids in the event");
                            }
                        } finally {
                            samsungTapDuration.clearMotionEvents(i3);
                        }
                    }
                    i3++;
                }
            }
        };
        this.mHandler = r1;
        this.mContext = context;
        TapDurationObserver tapDurationObserver = new TapDurationObserver(i, r1);
        this.mTapDurationObserver = tapDurationObserver;
        ContentResolver contentResolver = context.getContentResolver();
        if (tapDurationObserver.mContentResolver != null) {
            throw new IllegalStateException("Observer already started.");
        }
        tapDurationObserver.mContentResolver = contentResolver;
        contentResolver.registerContentObserver(tapDurationObserver.mTapDurationSettingUri, false, tapDurationObserver, tapDurationObserver.mUserId);
        tapDurationObserver.mContentResolver.registerContentObserver(tapDurationObserver.mRemoveAnimationSettingUri, false, tapDurationObserver, tapDurationObserver.mUserId);
        tapDurationObserver.onChange(true, tapDurationObserver.mTapDurationSettingUri);
        tapDurationObserver.onChange(true, tapDurationObserver.mRemoveAnimationSettingUri);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.clearEvents(i);
        }
    }

    public final void clearMotionEvents(int i) {
        MotionEvent motionEvent = this.mDownEvent[i];
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mDownEvent[i] = null;
        }
        MotionEvent motionEvent2 = this.mRawEvent[i];
        if (motionEvent2 != null) {
            motionEvent2.recycle();
            this.mRawEvent[i] = null;
        }
        this.mPolicyFlags[i] = 0;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onAccessibilityEvent(accessibilityEvent);
        }
    }

    public final void onActionTap(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mNext != null) {
            if (motionEvent.getActionMasked() != 2) {
                Log.d("SamsungTapDuration", "Inject this event : " + motionEvent);
            }
            this.mNext.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        removeMessages(1);
        for (int i = 0; i < 4; i++) {
            SamsungTapDurationProgressUI[] samsungTapDurationProgressUIArr = this.mDurationProgress;
            SamsungTapDurationProgressUI samsungTapDurationProgressUI = samsungTapDurationProgressUIArr[i];
            if (samsungTapDurationProgressUI != null) {
                samsungTapDurationProgressUI.mContext = null;
                samsungTapDurationProgressUI.mWindowManager.removeView(samsungTapDurationProgressUI.mView);
                samsungTapDurationProgressUIArr[i] = null;
            }
        }
        TapDurationObserver tapDurationObserver = this.mTapDurationObserver;
        if (tapDurationObserver != null) {
            ContentResolver contentResolver = tapDurationObserver.mContentResolver;
            if (contentResolver == null) {
                throw new IllegalStateException("ClickDelayObserver not started.");
            }
            contentResolver.unregisterContentObserver(tapDurationObserver);
            this.mTapDurationObserver = null;
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onKeyEvent(KeyEvent keyEvent, int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onKeyEvent(keyEvent, i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        SamsungTapDurationProgressUI[] samsungTapDurationProgressUIArr;
        Display display;
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount > 3 || pointerId >= 3) {
            return;
        }
        int displayId = motionEvent.getDisplayId();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            samsungTapDurationProgressUIArr = this.mDurationProgress;
            if (i3 >= 4) {
                break;
            }
            if (samsungTapDurationProgressUIArr[i3] == null) {
                DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
                samsungTapDurationProgressUIArr[i3] = new SamsungTapDurationProgressUI((displayManager == null || (display = displayManager.getDisplay(displayId)) == null) ? this.mContext : new ContextThemeWrapper(this.mContext.createDisplayContext(display), this.mContext.getTheme()));
            }
            i3++;
        }
        int actionMasked = motionEvent.getActionMasked();
        AnonymousClass1 anonymousClass1 = this.mHandler;
        int[] iArr = this.mPolicyFlags;
        if (actionMasked == 0) {
            this.mHoldingState = true;
            Log.d("SamsungTapDuration", "Start checking if this tap is valid ");
            this.mDownEvent[0] = MotionEvent.obtain(motionEvent);
            this.mRawEvent[0] = MotionEvent.obtain(motionEvent2);
            iArr[0] = i;
            anonymousClass1.sendEmptyMessageDelayed(1, this.mTapDurationThresholdMilli);
            setXY(0, motionEvent.getX(0), motionEvent.getY(0));
            for (int i4 = 0; i4 < 4; i4++) {
                SamsungTapDurationProgressUI samsungTapDurationProgressUI = samsungTapDurationProgressUIArr[i4];
                if (samsungTapDurationProgressUI != null) {
                    long j = this.mTapDurationThresholdMilli;
                    samsungTapDurationProgressUI.mDuration = j;
                    if (samsungTapDurationProgressUI.mIsShortThreshold != (j < 300)) {
                        boolean z = j < 300;
                        samsungTapDurationProgressUI.mIsShortThreshold = z;
                        samsungTapDurationProgressUI.mCircle.setVisibility((z || samsungTapDurationProgressUI.mIsRemoveAnimationEnabled) ? 8 : 0);
                        samsungTapDurationProgressUI.mArrow.setVisibility((z || samsungTapDurationProgressUI.mIsRemoveAnimationEnabled) ? 8 : 0);
                        samsungTapDurationProgressUI.mBackground.setVisibility((z || samsungTapDurationProgressUI.mIsRemoveAnimationEnabled) ? 8 : 0);
                        samsungTapDurationProgressUI.mProgress.setVisibility((z || samsungTapDurationProgressUI.mIsRemoveAnimationEnabled) ? 8 : 0);
                        samsungTapDurationProgressUI.mStandBy.setVisibility((z || samsungTapDurationProgressUI.mIsRemoveAnimationEnabled) ? 0 : 8);
                        samsungTapDurationProgressUI.mHold.setVisibility(8);
                        samsungTapDurationProgressUI.mSize = samsungTapDurationProgressUI.mContext.getResources().getDimensionPixelSize(z ? R.dimen.config_inCallNotificationVolume : 17106301);
                        samsungTapDurationProgressUI.mStandBy.getLayoutParams().width = samsungTapDurationProgressUI.mSize;
                        samsungTapDurationProgressUI.mStandBy.getLayoutParams().height = samsungTapDurationProgressUI.mSize;
                        samsungTapDurationProgressUI.mHold.getLayoutParams().width = samsungTapDurationProgressUI.mSize;
                        samsungTapDurationProgressUI.mHold.getLayoutParams().height = samsungTapDurationProgressUI.mSize;
                        samsungTapDurationProgressUI.mStandBy.requestLayout();
                        samsungTapDurationProgressUI.mHold.requestLayout();
                    }
                    SamsungTapDurationProgressUI samsungTapDurationProgressUI2 = samsungTapDurationProgressUIArr[i4];
                    samsungTapDurationProgressUI2.getClass();
                    samsungTapDurationProgressUI2.runOnUiThread(new SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(samsungTapDurationProgressUI2, 0));
                }
            }
            final SamsungTapDurationProgressUI samsungTapDurationProgressUI3 = samsungTapDurationProgressUIArr[0];
            if (samsungTapDurationProgressUI3 != null) {
                samsungTapDurationProgressUI3.runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda2
                    public final /* synthetic */ boolean f$1 = true;

                    @Override // java.lang.Runnable
                    public final void run() {
                        SamsungTapDurationProgressUI.this.mView.setVisibility(this.f$1 ? 0 : 8);
                    }
                });
                return;
            }
            return;
        }
        if (actionMasked == 1) {
            if (!this.mHoldingState) {
                onActionTap(motionEvent, motionEvent2, i);
            }
            anonymousClass1.removeMessages(1);
            this.mHoldingState = false;
            Log.d("SamsungTapDuration", "Finish checking if this tap is valid ");
            clearMotionEvents(pointerId);
            while (i2 < 4) {
                SamsungTapDurationProgressUI samsungTapDurationProgressUI4 = samsungTapDurationProgressUIArr[i2];
                if (samsungTapDurationProgressUI4 != null) {
                    samsungTapDurationProgressUI4.runOnUiThread(new SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(samsungTapDurationProgressUI4, 1));
                }
                i2++;
            }
            return;
        }
        if (actionMasked == 2) {
            if (!this.mHoldingState) {
                onActionTap(motionEvent, motionEvent2, i);
            }
            while (i2 < pointerCount) {
                setXY(motionEvent.getPointerId(i2), motionEvent.getX(i2), motionEvent.getY(i2));
                i2++;
            }
            return;
        }
        if (actionMasked != 5) {
            if (actionMasked != 6) {
                return;
            }
            if (!this.mHoldingState) {
                onActionTap(motionEvent, motionEvent2, i);
                return;
            }
            clearMotionEvents(pointerId);
            SamsungTapDurationProgressUI samsungTapDurationProgressUI5 = samsungTapDurationProgressUIArr[pointerId];
            if (samsungTapDurationProgressUI5 != null) {
                samsungTapDurationProgressUI5.runOnUiThread(new SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(samsungTapDurationProgressUI5, 1));
                return;
            }
            return;
        }
        if (!this.mHoldingState) {
            onActionTap(motionEvent, motionEvent2, i);
        }
        this.mDownEvent[pointerId] = MotionEvent.obtain(motionEvent);
        this.mRawEvent[pointerId] = MotionEvent.obtain(motionEvent2);
        iArr[pointerId] = i;
        setXY(pointerId, motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        final SamsungTapDurationProgressUI samsungTapDurationProgressUI6 = samsungTapDurationProgressUIArr[pointerId];
        if (samsungTapDurationProgressUI6 != null) {
            samsungTapDurationProgressUI6.runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda2
                public final /* synthetic */ boolean f$1 = true;

                @Override // java.lang.Runnable
                public final void run() {
                    SamsungTapDurationProgressUI.this.mView.setVisibility(this.f$1 ? 0 : 8);
                }
            });
        }
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public final void setNext(EventStreamTransformation eventStreamTransformation) {
        super.mNext = eventStreamTransformation;
        this.mNext = eventStreamTransformation;
    }

    public final void setXY(int i, float f, float f2) {
        if (i > 3) {
            Log.e("SamsungTapDuration", "setXY()_pointerId is invalid!!");
            return;
        }
        SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.mDurationProgress[i];
        if (samsungTapDurationProgressUI != null) {
            if (samsungTapDurationProgressUI.mDisplay.getRotation() == 3) {
                samsungTapDurationProgressUI.mParams.x = (((int) f) - (samsungTapDurationProgressUI.mSize / 2)) - samsungTapDurationProgressUI.mNavigationBarHeight;
            } else {
                samsungTapDurationProgressUI.mParams.x = ((int) f) - (samsungTapDurationProgressUI.mSize / 2);
            }
            samsungTapDurationProgressUI.mParams.y = ((int) f2) - (samsungTapDurationProgressUI.mSize / 2);
            samsungTapDurationProgressUI.runOnUiThread(new SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(samsungTapDurationProgressUI, 2));
        }
    }
}
