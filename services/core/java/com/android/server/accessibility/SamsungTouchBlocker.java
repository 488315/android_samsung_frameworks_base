package com.android.server.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SamsungTouchBlocker extends BaseEventStreamTransformation {
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public boolean mIsBlocking;
    public boolean mIsLastEventDown;
    public EventStreamTransformation mNext;
    public boolean mTapDurationEnabled;
    public SamsungAccessibilityTappingUI mTapIgnore = null;
    public TouchBlockingObserver mTouchBlockingObserver;
    public float mTouchBlockingPeriod;
    public long mTouchBlockingPeriodMilli;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TouchBlockingObserver extends ContentObserver {
        public ContentResolver mContentResolver;
        public final Uri mTapDurationSettingsUri;
        public final Uri mTouchBlockingSettingsUri;
        public final int mUserId;

        public TouchBlockingObserver(int i, AnonymousClass1 anonymousClass1) {
            super(anonymousClass1);
            this.mTouchBlockingSettingsUri = Settings.Secure.getUriFor("touch_blocking_period");
            this.mTapDurationSettingsUri = Settings.Secure.getUriFor("tap_duration_enabled");
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mTouchBlockingSettingsUri.equals(uri)) {
                SamsungTouchBlocker.this.mTouchBlockingPeriod = Settings.Secure.getFloatForUser(this.mContentResolver, "touch_blocking_period", 0.1f, this.mUserId);
                SamsungTouchBlocker samsungTouchBlocker = SamsungTouchBlocker.this;
                samsungTouchBlocker.mTouchBlockingPeriodMilli = (long) (samsungTouchBlocker.mTouchBlockingPeriod * 1000.0f);
            } else if (this.mTapDurationSettingsUri.equals(uri)) {
                SamsungTouchBlocker.this.mTapDurationEnabled = Settings.Secure.getIntForUser(this.mContentResolver, "tap_duration_enabled", 0, this.mUserId) == 1;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.os.Handler, com.android.server.accessibility.SamsungTouchBlocker$1] */
    public SamsungTouchBlocker(Context context, int i) {
        ?? r0 = new Handler() { // from class: com.android.server.accessibility.SamsungTouchBlocker.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                Log.d("SamsungTouchBlocker", "Touch Blocker is deactivated");
                SamsungTouchBlocker.this.mIsBlocking = false;
            }
        };
        this.mHandler = r0;
        this.mContext = context;
        this.mIsLastEventDown = false;
        TouchBlockingObserver touchBlockingObserver = new TouchBlockingObserver(i, r0);
        this.mTouchBlockingObserver = touchBlockingObserver;
        ContentResolver contentResolver = context.getContentResolver();
        if (touchBlockingObserver.mContentResolver != null) {
            throw new IllegalStateException("Observer has already been started.");
        }
        touchBlockingObserver.mContentResolver = contentResolver;
        contentResolver.registerContentObserver(touchBlockingObserver.mTouchBlockingSettingsUri, false, touchBlockingObserver, touchBlockingObserver.mUserId);
        touchBlockingObserver.mContentResolver.registerContentObserver(touchBlockingObserver.mTapDurationSettingsUri, false, touchBlockingObserver, touchBlockingObserver.mUserId);
        touchBlockingObserver.onChange(true, touchBlockingObserver.mTouchBlockingSettingsUri);
        touchBlockingObserver.onChange(true, touchBlockingObserver.mTapDurationSettingsUri);
        r0.sendEmptyMessageDelayed(0, this.mTouchBlockingPeriodMilli);
        this.mIsBlocking = true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
    }

    public final Context getDisplayContext(int i) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        return (displayManager == null || (display = displayManager.getDisplay(i)) == null) ? this.mContext : new ContextThemeWrapper(this.mContext.createDisplayContext(display), this.mContext.getTheme());
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            samsungAccessibilityTappingUI.mContext = null;
            samsungAccessibilityTappingUI.mWindowManager.removeView(samsungAccessibilityTappingUI.view);
            samsungAccessibilityTappingUI.mStandbyImageView.setImageBitmap(null);
            samsungAccessibilityTappingUI.mProgressImageView.setImageBitmap(null);
            samsungAccessibilityTappingUI.mIgnoreImageView.setImageBitmap(null);
            samsungAccessibilityTappingUI.mTapImageView.setImageBitmap(null);
            this.mTapIgnore = null;
        }
        TouchBlockingObserver touchBlockingObserver = this.mTouchBlockingObserver;
        if (touchBlockingObserver != null) {
            ContentResolver contentResolver = touchBlockingObserver.mContentResolver;
            if (contentResolver == null) {
                throw new IllegalStateException("TouchBlockingObserver has not been started");
            }
            contentResolver.unregisterContentObserver(touchBlockingObserver);
            this.mTouchBlockingObserver = null;
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
        int actionMasked = motionEvent.getActionMasked();
        if (this.mIsBlocking) {
            if (actionMasked == 0) {
                if (this.mTapIgnore == null) {
                    this.mTapIgnore = new SamsungAccessibilityTappingUI(getDisplayContext(motionEvent.getDisplayId()));
                }
                setXY(motionEvent.getX(), motionEvent.getY());
                onTapIgnore();
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    return;
                }
                setXY(motionEvent.getX(), motionEvent.getY());
                return;
            } else {
                SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
                if (samsungAccessibilityTappingUI != null) {
                    samsungAccessibilityTappingUI.runOnUiThread(new SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0(samsungAccessibilityTappingUI, false, 0));
                    return;
                }
                return;
            }
        }
        if (actionMasked == 0) {
            this.mIsLastEventDown = true;
            if (this.mTapIgnore == null) {
                this.mTapIgnore = new SamsungAccessibilityTappingUI(getDisplayContext(motionEvent.getDisplayId()));
            }
            setXY(motionEvent.getX(), motionEvent.getY());
            onTapIgnore();
        } else if (actionMasked == 1) {
            SamsungAccessibilityTappingUI samsungAccessibilityTappingUI2 = this.mTapIgnore;
            if (samsungAccessibilityTappingUI2 != null) {
                samsungAccessibilityTappingUI2.runOnUiThread(new SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0(samsungAccessibilityTappingUI2, false, 0));
            }
            if (this.mIsLastEventDown) {
                Log.d("SamsungTouchBlocker", "Touch Blocker is activated");
                sendEmptyMessageDelayed(0, this.mTouchBlockingPeriodMilli);
                this.mIsBlocking = true;
                this.mIsLastEventDown = false;
            }
        } else if (actionMasked == 2) {
            setXY(motionEvent.getX(), motionEvent.getY());
        }
        if (this.mNext != null) {
            if (motionEvent.getActionMasked() != 2) {
                Log.d("SamsungTouchBlocker", "Inject this event : " + motionEvent);
            }
            this.mNext.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    public final void onTapIgnore() {
        SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            samsungAccessibilityTappingUI.runOnUiThread(new SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0(samsungAccessibilityTappingUI, this.mIsBlocking, 1));
            SamsungAccessibilityTappingUI samsungAccessibilityTappingUI2 = this.mTapIgnore;
            boolean z = this.mIsBlocking || !this.mTapDurationEnabled;
            samsungAccessibilityTappingUI2.getClass();
            samsungAccessibilityTappingUI2.runOnUiThread(new SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0(samsungAccessibilityTappingUI2, z, 0));
        }
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public final void setNext(EventStreamTransformation eventStreamTransformation) {
        super.mNext = eventStreamTransformation;
        this.mNext = eventStreamTransformation;
    }

    public final void setXY(float f, float f2) {
        final SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            int rotation = samsungAccessibilityTappingUI.mDisplay.getRotation();
            int i = samsungAccessibilityTappingUI.size;
            if (rotation == 3) {
                samsungAccessibilityTappingUI.mParams.x = (((int) f) - (i / 2)) - samsungAccessibilityTappingUI.navigationBarHeight;
            } else {
                samsungAccessibilityTappingUI.mParams.x = ((int) f) - (i / 2);
            }
            samsungAccessibilityTappingUI.mParams.y = ((int) f2) - (i / 2);
            samsungAccessibilityTappingUI.runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungAccessibilityTappingUI$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SamsungAccessibilityTappingUI samsungAccessibilityTappingUI2 = SamsungAccessibilityTappingUI.this;
                    samsungAccessibilityTappingUI2.mWindowManager.updateViewLayout(samsungAccessibilityTappingUI2.view, samsungAccessibilityTappingUI2.mParams);
                }
            });
        }
    }
}
