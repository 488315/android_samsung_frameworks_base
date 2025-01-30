package com.android.server.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
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

/* loaded from: classes.dex */
public class SamsungTapDuration extends BaseEventStreamTransformation {
  public final Context mContext;
  public final Handler mHandler;
  public EventStreamTransformation mNext;
  public TapDurationObserver mTapDurationObserver;
  public float mTapDurationThreshold;
  public long mTapDurationThresholdMilli;
  public final MotionEvent[] mDownEvent = new MotionEvent[4];
  public final MotionEvent[] mRawEvent = new MotionEvent[4];
  public final SamsungTapDurationProgressUI[] mDurationProgress =
      new SamsungTapDurationProgressUI[4];
  public final int[] mPolicyFlags = new int[4];
  public boolean mHoldingState = false;

  public SamsungTapDuration(Context context, int i) {
    Handler handler =
        new Handler() { // from class: com.android.server.accessibility.SamsungTapDuration.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == 1) {
              int i3 = 0;
              while (i3 < 4) {
                if (SamsungTapDuration.this.mDurationProgress[i3] != null) {
                  SamsungTapDuration.this.mDurationProgress[i3].end();
                }
                if (SamsungTapDuration.this.mDownEvent[i3] != null) {
                  SamsungTapDuration.this.mDownEvent[i3].setDownTime(SystemClock.uptimeMillis());
                  try {
                    try {
                      SamsungTapDuration samsungTapDuration = SamsungTapDuration.this;
                      samsungTapDuration.onActionTap(
                          samsungTapDuration.mDownEvent[i3],
                          SamsungTapDuration.this.mRawEvent[i3],
                          SamsungTapDuration.this.mPolicyFlags[i3]);
                    } catch (IllegalArgumentException unused) {
                      Log.e("SamsungTapDuration", "idBits did not match any ids in the event");
                    }
                  } finally {
                    SamsungTapDuration.this.clearMotionEvents(i3);
                  }
                }
                i3++;
              }
              Log.d("SamsungTapDuration", "Tap is valid");
              SamsungTapDuration.this.mHoldingState = false;
              return;
            }
            throw new IllegalArgumentException("Unkwown message type: " + i2);
          }
        };
    this.mHandler = handler;
    this.mContext = context;
    TapDurationObserver tapDurationObserver = new TapDurationObserver(i, handler);
    this.mTapDurationObserver = tapDurationObserver;
    tapDurationObserver.start(context.getContentResolver());
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    int actionIndex = motionEvent.getActionIndex();
    int pointerId = motionEvent.getPointerId(actionIndex);
    int pointerCount = motionEvent.getPointerCount();
    if (pointerCount > 3 || pointerId >= 3) {
      return;
    }
    createDurationProgress(motionEvent.getDisplayId());
    int actionMasked = motionEvent.getActionMasked();
    int i2 = 0;
    if (actionMasked == 0) {
      this.mHoldingState = true;
      Log.d("SamsungTapDuration", "Start checking if this tap is valid ");
      this.mDownEvent[0] = MotionEvent.obtain(motionEvent);
      this.mRawEvent[0] = MotionEvent.obtain(motionEvent2);
      this.mPolicyFlags[0] = i;
      this.mHandler.sendEmptyMessageDelayed(1, this.mTapDurationThresholdMilli);
      setXY(motionEvent.getX(0), motionEvent.getY(0), 0);
      for (int i3 = 0; i3 < 4; i3++) {
        SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.mDurationProgress[i3];
        if (samsungTapDurationProgressUI != null) {
          samsungTapDurationProgressUI.setDurationTime(this.mTapDurationThresholdMilli);
          this.mDurationProgress[i3].start();
        }
      }
      SamsungTapDurationProgressUI samsungTapDurationProgressUI2 = this.mDurationProgress[0];
      if (samsungTapDurationProgressUI2 != null) {
        samsungTapDurationProgressUI2.setViewOnOff(true);
        return;
      }
      return;
    }
    if (actionMasked == 1) {
      if (!this.mHoldingState) {
        onActionTap(motionEvent, motionEvent2, i);
      }
      this.mHandler.removeMessages(1);
      this.mHoldingState = false;
      Log.d("SamsungTapDuration", "Finish checking if this tap is valid ");
      clearMotionEvents(pointerId);
      while (i2 < 4) {
        SamsungTapDurationProgressUI samsungTapDurationProgressUI3 = this.mDurationProgress[i2];
        if (samsungTapDurationProgressUI3 != null) {
          samsungTapDurationProgressUI3.cancel();
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
        setXY(motionEvent.getX(i2), motionEvent.getY(i2), motionEvent.getPointerId(i2));
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
      SamsungTapDurationProgressUI samsungTapDurationProgressUI4 =
          this.mDurationProgress[pointerId];
      if (samsungTapDurationProgressUI4 != null) {
        samsungTapDurationProgressUI4.cancel();
        return;
      }
      return;
    }
    if (!this.mHoldingState) {
      onActionTap(motionEvent, motionEvent2, i);
    }
    this.mDownEvent[pointerId] = MotionEvent.obtain(motionEvent);
    this.mRawEvent[pointerId] = MotionEvent.obtain(motionEvent2);
    this.mPolicyFlags[pointerId] = i;
    setXY(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex), pointerId);
    SamsungTapDurationProgressUI samsungTapDurationProgressUI5 = this.mDurationProgress[pointerId];
    if (samsungTapDurationProgressUI5 != null) {
      samsungTapDurationProgressUI5.setViewOnOff(true);
    }
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onKeyEvent(KeyEvent keyEvent, int i) {
    EventStreamTransformation eventStreamTransformation = this.mNext;
    if (eventStreamTransformation != null) {
      eventStreamTransformation.onKeyEvent(keyEvent, i);
    }
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    EventStreamTransformation eventStreamTransformation = this.mNext;
    if (eventStreamTransformation != null) {
      eventStreamTransformation.onAccessibilityEvent(accessibilityEvent);
    }
  }

  @Override // com.android.server.accessibility.BaseEventStreamTransformation,
  // com.android.server.accessibility.EventStreamTransformation
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
    for (int i = 0; i < 4; i++) {
      SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.mDurationProgress[i];
      if (samsungTapDurationProgressUI != null) {
        samsungTapDurationProgressUI.destroy();
        this.mDurationProgress[i] = null;
      }
    }
    TapDurationObserver tapDurationObserver = this.mTapDurationObserver;
    if (tapDurationObserver != null) {
      tapDurationObserver.stop();
      this.mTapDurationObserver = null;
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

  public final void createDurationProgress(int i) {
    for (int i2 = 0; i2 < 4; i2++) {
      SamsungTapDurationProgressUI[] samsungTapDurationProgressUIArr = this.mDurationProgress;
      if (samsungTapDurationProgressUIArr[i2] == null) {
        samsungTapDurationProgressUIArr[i2] =
            new SamsungTapDurationProgressUI(getDisplayContext(i));
      }
    }
  }

  public final void setXY(float f, float f2, int i) {
    if (i > 3) {
      Log.e("SamsungTapDuration", "setXY()_pointerId is invalid!!");
      return;
    }
    SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.mDurationProgress[i];
    if (samsungTapDurationProgressUI != null) {
      samsungTapDurationProgressUI.updateView(f, f2);
    }
  }

  public Context getDisplayContext(int i) {
    Display display;
    DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
    if (displayManager != null && (display = displayManager.getDisplay(i)) != null) {
      return new ContextThemeWrapper(
          this.mContext.createDisplayContext(display), this.mContext.getTheme());
    }
    return this.mContext;
  }

  public final class TapDurationObserver extends ContentObserver {
    public ContentResolver mContentResolver;
    public final Uri mRemoveAnimationSettingUri;
    public final Uri mTapDurationSettingUri;
    public final int mUserId;

    public TapDurationObserver(int i, Handler handler) {
      super(handler);
      this.mTapDurationSettingUri = Settings.Secure.getUriFor("tap_duration_threshold");
      this.mRemoveAnimationSettingUri = Settings.Global.getUriFor("remove_animations");
      this.mUserId = i;
    }

    public void start(ContentResolver contentResolver) {
      if (this.mContentResolver != null) {
        throw new IllegalStateException("Observer already started.");
      }
      if (contentResolver == null) {
        throw new NullPointerException("contentResolver not set.");
      }
      this.mContentResolver = contentResolver;
      contentResolver.registerContentObserver(
          this.mTapDurationSettingUri, false, this, this.mUserId);
      this.mContentResolver.registerContentObserver(
          this.mRemoveAnimationSettingUri, false, this, this.mUserId);
      onChange(true, this.mTapDurationSettingUri);
      onChange(true, this.mRemoveAnimationSettingUri);
    }

    public void stop() {
      ContentResolver contentResolver = this.mContentResolver;
      if (contentResolver == null) {
        throw new IllegalStateException("ClickDelayObserver not started.");
      }
      contentResolver.unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (this.mTapDurationSettingUri.equals(uri)) {
        SamsungTapDuration.this.mTapDurationThreshold =
            Settings.Secure.getFloatForUser(
                this.mContentResolver, "tap_duration_threshold", 0.1f, this.mUserId);
        SamsungTapDuration samsungTapDuration = SamsungTapDuration.this;
        samsungTapDuration.mTapDurationThresholdMilli =
            (long) (samsungTapDuration.mTapDurationThreshold * 1000.0f);
      } else if (this.mRemoveAnimationSettingUri.equals(uri)) {
        boolean z2 = Settings.Global.getInt(this.mContentResolver, "remove_animations", 0) == 1;
        for (int i = 0; i < 4; i++) {
          if (SamsungTapDuration.this.mDurationProgress[i] != null) {
            SamsungTapDuration.this.mDurationProgress[i].setRemoveAnimationEnabled(z2);
          }
        }
      }
    }
  }
}
