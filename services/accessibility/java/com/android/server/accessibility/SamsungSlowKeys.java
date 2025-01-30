package com.android.server.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

/* loaded from: classes.dex */
public class SamsungSlowKeys extends BaseEventStreamTransformation {
    public int mCurrentKeyCode;
    public KeyEvent mCurrentKeyEvent = null;
    public final Handler mHandler = new Handler() { // from class: com.android.server.accessibility.SamsungSlowKeys.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (SamsungSlowKeys.this.mCurrentKeyEvent != null) {
                SamsungSlowKeys.this.debugLog("onKeyEvent : " + SamsungSlowKeys.this.mCurrentKeyEvent.getKeyCode());
                if (SamsungSlowKeys.this.mNext != null) {
                    SamsungSlowKeys.this.mCurrentKeyEvent.setTime(SystemClock.uptimeMillis(), SystemClock.uptimeMillis());
                    SamsungSlowKeys.this.mNext.onKeyEvent(SamsungSlowKeys.this.mCurrentKeyEvent, SamsungSlowKeys.this.mPolicyFlags);
                    SamsungSlowKeys.this.mCurrentKeyEvent = null;
                }
            }
        }
    };
    public boolean mKeyEventFinished;
    public EventStreamTransformation mNext;
    public int mPolicyFlags;
    public SlowKeysObserver mSlowKeysObserver;
    public long mSlowKeysPeriodMilli;

    public SamsungSlowKeys(Context context, int i) {
        SlowKeysObserver slowKeysObserver = new SlowKeysObserver(i, null);
        this.mSlowKeysObserver = slowKeysObserver;
        slowKeysObserver.start(context.getContentResolver());
        this.mKeyEventFinished = true;
        this.mCurrentKeyCode = -1;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onKeyEvent(KeyEvent keyEvent, int i) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 24 || keyCode == 25) {
            this.mHandler.removeMessages(1);
            EventStreamTransformation eventStreamTransformation = this.mNext;
            if (eventStreamTransformation != null) {
                eventStreamTransformation.onKeyEvent(keyEvent, i);
                return;
            }
            return;
        }
        if (action == 0 && this.mKeyEventFinished) {
            this.mCurrentKeyEvent = keyEvent;
            this.mCurrentKeyCode = keyEvent.getKeyCode();
            this.mKeyEventFinished = false;
            this.mPolicyFlags = i;
            this.mHandler.sendEmptyMessageDelayed(1, this.mSlowKeysPeriodMilli);
            return;
        }
        if (this.mCurrentKeyCode != keyEvent.getKeyCode()) {
            return;
        }
        this.mHandler.removeMessages(1);
        EventStreamTransformation eventStreamTransformation2 = this.mNext;
        if (eventStreamTransformation2 != null && this.mCurrentKeyEvent == null) {
            eventStreamTransformation2.onKeyEvent(keyEvent, i);
        }
        this.mKeyEventFinished = true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.onAccessibilityEvent(accessibilityEvent);
        }
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public void setNext(EventStreamTransformation eventStreamTransformation) {
        super.setNext(eventStreamTransformation);
        this.mNext = eventStreamTransformation;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        EventStreamTransformation eventStreamTransformation = this.mNext;
        if (eventStreamTransformation != null) {
            eventStreamTransformation.clearEvents(i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        this.mHandler.removeMessages(1);
        SlowKeysObserver slowKeysObserver = this.mSlowKeysObserver;
        if (slowKeysObserver != null) {
            slowKeysObserver.stop();
            this.mSlowKeysObserver = null;
        }
    }

    public final void debugLog(String str) {
        Log.d("SamsungSlowKeys", str);
    }

    public final class SlowKeysObserver extends ContentObserver {
        public ContentResolver mContentResolver;
        public final Uri mSlowKeysSettingsUri;
        public final int mUserId;

        public SlowKeysObserver(int i, Handler handler) {
            super(handler);
            this.mSlowKeysSettingsUri = Settings.Secure.getUriFor("slow_keys_period");
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
            contentResolver.registerContentObserver(this.mSlowKeysSettingsUri, false, this, this.mUserId);
            onChange(true, this.mSlowKeysSettingsUri);
        }

        public void stop() {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                throw new IllegalStateException("SlowKeysObserver has not been started");
            }
            contentResolver.unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (this.mSlowKeysSettingsUri.equals(uri)) {
                SamsungSlowKeys.this.mSlowKeysPeriodMilli = (long) (Settings.Secure.getFloatForUser(this.mContentResolver, "slow_keys_period", 0.3f, this.mUserId) * 1000.0f);
            }
        }
    }
}
