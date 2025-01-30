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

/* loaded from: classes.dex */
public class SamsungTouchBlocker extends BaseEventStreamTransformation {
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsBlocking;
    public boolean mIsLastEventDown;
    public EventStreamTransformation mNext;
    public boolean mTapDurationEnabled;
    public SamsungAccessibilityTappingUI mTapIgnore = null;
    public TouchBlockingObserver mTouchBlockingObserver;
    public float mTouchBlockingPeriod;
    public long mTouchBlockingPeriodMilli;

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public SamsungTouchBlocker(Context context, int i) {
        Handler handler = new Handler() { // from class: com.android.server.accessibility.SamsungTouchBlocker.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Log.d("SamsungTouchBlocker", "Touch Blocker is deactivated");
                SamsungTouchBlocker.this.mIsBlocking = false;
            }
        };
        this.mHandler = handler;
        this.mContext = context;
        this.mIsLastEventDown = false;
        TouchBlockingObserver touchBlockingObserver = new TouchBlockingObserver(i, handler);
        this.mTouchBlockingObserver = touchBlockingObserver;
        touchBlockingObserver.start(context.getContentResolver());
        handler.sendEmptyMessageDelayed(0, this.mTouchBlockingPeriodMilli);
        this.mIsBlocking = true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.mIsBlocking) {
            if (actionMasked == 0) {
                if (this.mTapIgnore == null) {
                    this.mTapIgnore = new SamsungAccessibilityTappingUI(getDisplayContext(motionEvent.getDisplayId()), 1);
                }
                setXY(motionEvent.getX(), motionEvent.getY());
                onTapIgnore();
                return;
            }
            if (actionMasked == 1) {
                offTapIgnore();
                return;
            } else {
                if (actionMasked != 2) {
                    return;
                }
                setXY(motionEvent.getX(), motionEvent.getY());
                return;
            }
        }
        if (actionMasked == 0) {
            this.mIsLastEventDown = true;
            if (this.mTapIgnore == null) {
                this.mTapIgnore = new SamsungAccessibilityTappingUI(getDisplayContext(motionEvent.getDisplayId()), 1);
            }
            setXY(motionEvent.getX(), motionEvent.getY());
            onTapIgnore();
        } else if (actionMasked == 1) {
            offTapIgnore();
            if (this.mIsLastEventDown) {
                Log.d("SamsungTouchBlocker", "Touch Blocker is activated");
                this.mHandler.sendEmptyMessageDelayed(0, this.mTouchBlockingPeriodMilli);
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
            samsungAccessibilityTappingUI.setIgnoreView(this.mIsBlocking);
            this.mTapIgnore.setViewOnOff(this.mIsBlocking || !this.mTapDurationEnabled);
        }
    }

    public final void offTapIgnore() {
        SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            samsungAccessibilityTappingUI.setViewOnOff(false);
        }
    }

    public final void setXY(float f, float f2) {
        SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            samsungAccessibilityTappingUI.updateView(f, f2);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(KeyEvent keyEvent, int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onKeyEvent(keyEvent, i);
        }
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public void setNext(EventStreamTransformation eventStreamTransformation) {
        super.setNext(eventStreamTransformation);
        this.mNext = eventStreamTransformation;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        SamsungAccessibilityTappingUI samsungAccessibilityTappingUI = this.mTapIgnore;
        if (samsungAccessibilityTappingUI != null) {
            samsungAccessibilityTappingUI.destroy();
            this.mTapIgnore = null;
        }
        TouchBlockingObserver touchBlockingObserver = this.mTouchBlockingObserver;
        if (touchBlockingObserver != null) {
            touchBlockingObserver.stop();
            this.mTouchBlockingObserver = null;
        }
    }

    public Context getDisplayContext(int i) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(i)) != null) {
            return new ContextThemeWrapper(this.mContext.createDisplayContext(display), this.mContext.getTheme());
        }
        return this.mContext;
    }

    public final class TouchBlockingObserver extends ContentObserver {
        public ContentResolver mContentResolver;
        public final Uri mTapDurationSettingsUri;
        public final Uri mTouchBlockingSettingsUri;
        public final int mUserId;

        public TouchBlockingObserver(int i, Handler handler) {
            super(handler);
            this.mTouchBlockingSettingsUri = Settings.Secure.getUriFor("touch_blocking_period");
            this.mTapDurationSettingsUri = Settings.Secure.getUriFor("tap_duration_enabled");
            this.mUserId = i;
        }

        public void start(ContentResolver contentResolver) {
            if (this.mContentResolver != null) {
                throw new IllegalStateException("Observer has already been started.");
            }
            if (contentResolver == null) {
                throw new NullPointerException("ContentResolver is not set.");
            }
            this.mContentResolver = contentResolver;
            contentResolver.registerContentObserver(this.mTouchBlockingSettingsUri, false, this, this.mUserId);
            this.mContentResolver.registerContentObserver(this.mTapDurationSettingsUri, false, this, this.mUserId);
            onChange(true, this.mTouchBlockingSettingsUri);
            onChange(true, this.mTapDurationSettingsUri);
        }

        public void stop() {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                throw new IllegalStateException("TouchBlockingObserver has not been started");
            }
            contentResolver.unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (this.mTouchBlockingSettingsUri.equals(uri)) {
                SamsungTouchBlocker.this.mTouchBlockingPeriod = Settings.Secure.getFloatForUser(this.mContentResolver, "touch_blocking_period", 0.1f, this.mUserId);
                SamsungTouchBlocker samsungTouchBlocker = SamsungTouchBlocker.this;
                samsungTouchBlocker.mTouchBlockingPeriodMilli = (long) (samsungTouchBlocker.mTouchBlockingPeriod * 1000.0f);
            } else if (this.mTapDurationSettingsUri.equals(uri)) {
                SamsungTouchBlocker.this.mTapDurationEnabled = Settings.Secure.getIntForUser(this.mContentResolver, "tap_duration_enabled", 0, this.mUserId) == 1;
            }
        }
    }
}
