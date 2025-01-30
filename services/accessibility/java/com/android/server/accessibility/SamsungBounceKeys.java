package com.android.server.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

/* loaded from: classes.dex */
public class SamsungBounceKeys extends BaseEventStreamTransformation {
  public BounceKeysObserver mBounceKeysObserver;
  public long mBounceKeysPeriodMilli;
  public final Handler mHandler;
  public boolean mIsBlocking;
  public EventStreamTransformation mNext;
  public int mOldKeyCode;

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void clearEvents(int i) {}

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {}

  public SamsungBounceKeys(Context context, int i) {
    Handler handler =
        new Handler() { // from class: com.android.server.accessibility.SamsungBounceKeys.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            Log.d("SamsungBounceKeys", "Touch Blocker is deactivated");
            SamsungBounceKeys.this.mIsBlocking = false;
          }
        };
    this.mHandler = handler;
    this.mOldKeyCode = -1;
    BounceKeysObserver bounceKeysObserver = new BounceKeysObserver(i, handler);
    this.mBounceKeysObserver = bounceKeysObserver;
    bounceKeysObserver.start(context.getContentResolver());
    handler.sendEmptyMessageDelayed(0, this.mBounceKeysPeriodMilli);
    this.mIsBlocking = true;
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    EventStreamTransformation eventStreamTransformation = this.mNext;
    if (eventStreamTransformation != null) {
      eventStreamTransformation.onMotionEvent(motionEvent, motionEvent2, i);
    }
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onKeyEvent(KeyEvent keyEvent, int i) {
    int action = keyEvent.getAction();
    int keyCode = keyEvent.getKeyCode();
    Log.i(
        "SamsungBounceKeys",
        "onKeyEvent action : "
            + action
            + ", keyCode : "
            + keyCode
            + ", mIsBlocking : "
            + this.mIsBlocking
            + ", mOldKeyCode : "
            + this.mOldKeyCode);
    if (keyCode == 24 || keyCode == 25) {
      this.mHandler.removeMessages(0);
      EventStreamTransformation eventStreamTransformation = this.mNext;
      if (eventStreamTransformation != null) {
        eventStreamTransformation.onKeyEvent(keyEvent, i);
        return;
      }
      return;
    }
    if (this.mIsBlocking && this.mOldKeyCode == keyCode) {
      this.mHandler.removeMessages(0);
      this.mHandler.sendEmptyMessageDelayed(0, this.mBounceKeysPeriodMilli);
      return;
    }
    if (action == 1) {
      Log.d("SamsungBounceKeys", "BounceKeys is activated");
      this.mHandler.sendEmptyMessageDelayed(0, this.mBounceKeysPeriodMilli);
      this.mOldKeyCode = keyCode;
      this.mIsBlocking = true;
    }
    EventStreamTransformation eventStreamTransformation2 = this.mNext;
    if (eventStreamTransformation2 != null) {
      eventStreamTransformation2.onKeyEvent(keyEvent, i);
    }
  }

  @Override // com.android.server.accessibility.BaseEventStreamTransformation,
  // com.android.server.accessibility.EventStreamTransformation
  public void setNext(EventStreamTransformation eventStreamTransformation) {
    super.setNext(eventStreamTransformation);
    this.mNext = eventStreamTransformation;
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onDestroy() {
    this.mHandler.removeMessages(0);
    BounceKeysObserver bounceKeysObserver = this.mBounceKeysObserver;
    if (bounceKeysObserver != null) {
      bounceKeysObserver.stop();
      this.mBounceKeysObserver = null;
    }
  }

  public final class BounceKeysObserver extends ContentObserver {
    public final Uri mBounceKeysSettingsUri;
    public ContentResolver mContentResolver;
    public final int mUserId;

    public BounceKeysObserver(int i, Handler handler) {
      super(handler);
      this.mBounceKeysSettingsUri = Settings.Secure.getUriFor("bounce_keys_period");
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
      contentResolver.registerContentObserver(
          this.mBounceKeysSettingsUri, false, this, this.mUserId);
      onChange(true, this.mBounceKeysSettingsUri);
    }

    public void stop() {
      ContentResolver contentResolver = this.mContentResolver;
      if (contentResolver == null) {
        throw new IllegalStateException("BounceKeysObserver has not been started");
      }
      contentResolver.unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (this.mBounceKeysSettingsUri.equals(uri)) {
        SamsungBounceKeys.this.mBounceKeysPeriodMilli =
            (long)
                (Settings.Secure.getFloatForUser(
                        this.mContentResolver, "bounce_keys_period", 0.5f, this.mUserId)
                    * 1000.0f);
      }
    }
  }
}
