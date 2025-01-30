package com.android.server.accessibility.autoaction;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.android.server.accessibility.BaseEventStreamTransformation;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AutoActionController extends BaseEventStreamTransformation {
  public ActionScheduler mActionScheduler;
  public AutoActionObserver mAutoActionObserver;
  public final Context mContext;
  public final int mUserId;

  public AutoActionController(Context context, int i) {
    this.mContext = context;
    this.mUserId = i;
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    if (motionEvent.isFromSource(8194)) {
      if (this.mActionScheduler == null) {
        Handler handler = new Handler(this.mContext.getMainLooper());
        this.mActionScheduler = new ActionScheduler(handler, 600, 0, 0);
        AutoActionObserver autoActionObserver = new AutoActionObserver(this.mUserId, handler);
        this.mAutoActionObserver = autoActionObserver;
        autoActionObserver.start(this.mContext.getContentResolver(), this.mActionScheduler);
      }
      handleMouseMotion(motionEvent, i);
    } else {
      ActionScheduler actionScheduler = this.mActionScheduler;
      if (actionScheduler != null) {
        actionScheduler.cancel();
      }
    }
    super.onMotionEvent(motionEvent, motionEvent2, i);
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onKeyEvent(KeyEvent keyEvent, int i) {
    if (this.mActionScheduler != null) {
      if (KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
        this.mActionScheduler.updateMetaState(keyEvent.getMetaState());
      } else {
        this.mActionScheduler.cancel();
      }
    }
    super.onKeyEvent(keyEvent, i);
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void clearEvents(int i) {
    ActionScheduler actionScheduler;
    if (i == 8194 && (actionScheduler = this.mActionScheduler) != null) {
      actionScheduler.cancel();
    }
    super.clearEvents(i);
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onDestroy() {
    AutoActionObserver autoActionObserver = this.mAutoActionObserver;
    if (autoActionObserver != null) {
      autoActionObserver.stop();
      this.mAutoActionObserver = null;
    }
    ActionScheduler actionScheduler = this.mActionScheduler;
    if (actionScheduler != null) {
      actionScheduler.cancel();
      this.mActionScheduler = null;
    }
  }

  public final void handleMouseMotion(MotionEvent motionEvent, int i) {
    int actionMasked = motionEvent.getActionMasked();
    if (actionMasked != 7) {
      if (actionMasked == 9 || actionMasked == 10) {
        return;
      }
      this.mActionScheduler.cancel();
      return;
    }
    if (motionEvent.getPointerCount() == 1) {
      this.mActionScheduler.update(motionEvent, i);
    } else {
      this.mActionScheduler.cancel();
    }
  }

  public final class AutoActionObserver extends ContentObserver {
    public ActionScheduler mActionScheduler;
    public final Uri mAutoActionDelaySettingUri;
    public final Uri mAutoActionTypeSettingUri;
    public ContentResolver mContentResolver;
    public final Uri mCornerActionEnabledSettingUri;
    public final Uri mPauseAutoClickWithSettingUri;
    public final int mUserId;

    public AutoActionObserver(int i, Handler handler) {
      super(handler);
      this.mAutoActionTypeSettingUri = Settings.Secure.getUriFor("accessibility_auto_action_type");
      this.mAutoActionDelaySettingUri =
          Settings.Secure.getUriFor("accessibility_auto_action_delay");
      this.mCornerActionEnabledSettingUri =
          Settings.Secure.getUriFor("accessibility_corner_action_enabled");
      this.mPauseAutoClickWithSettingUri =
          Settings.Secure.getUriFor("accessibility_pause_auto_click_with");
      this.mUserId = i;
    }

    public void start(ContentResolver contentResolver, ActionScheduler actionScheduler) {
      if (this.mContentResolver != null || this.mActionScheduler != null) {
        throw new IllegalStateException("Observer already started.");
      }
      if (contentResolver == null) {
        throw new NullPointerException("contentResolver not set.");
      }
      if (actionScheduler == null) {
        throw new NullPointerException("actionScheduler not set.");
      }
      this.mContentResolver = contentResolver;
      this.mActionScheduler = actionScheduler;
      contentResolver.registerContentObserver(
          this.mAutoActionTypeSettingUri, false, this, this.mUserId);
      this.mContentResolver.registerContentObserver(
          this.mAutoActionDelaySettingUri, false, this, this.mUserId);
      this.mContentResolver.registerContentObserver(
          this.mCornerActionEnabledSettingUri, false, this, this.mUserId);
      this.mContentResolver.registerContentObserver(
          this.mPauseAutoClickWithSettingUri, false, this, this.mUserId);
      onChange(true, this.mAutoActionTypeSettingUri);
      onChange(true, this.mAutoActionDelaySettingUri);
      onChange(true, this.mCornerActionEnabledSettingUri);
      onChange(true, this.mPauseAutoClickWithSettingUri);
    }

    public void stop() {
      ContentResolver contentResolver = this.mContentResolver;
      if (contentResolver == null || this.mActionScheduler == null) {
        throw new IllegalStateException("AutoActionObserver not started.");
      }
      contentResolver.unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (this.mAutoActionTypeSettingUri.equals(uri)) {
        this.mActionScheduler.updateType(
            Settings.Secure.getIntForUser(
                this.mContentResolver, "accessibility_auto_action_type", 0, this.mUserId));
        return;
      }
      if (this.mAutoActionDelaySettingUri.equals(uri)) {
        this.mActionScheduler.updateDelay(
            Settings.Secure.getIntForUser(
                this.mContentResolver, "accessibility_auto_action_delay", 600, this.mUserId));
        return;
      }
      if (this.mCornerActionEnabledSettingUri.equals(uri)) {
        this.mActionScheduler.setCornerActionController(
            Settings.Secure.getIntForUser(
                    this.mContentResolver, "accessibility_corner_action_enabled", 0, this.mUserId)
                != 0);
      } else if (this.mPauseAutoClickWithSettingUri.equals(uri)) {
        if (Settings.Secure.getIntForUser(
                this.mContentResolver, "accessibility_pause_auto_click_with", 0, this.mUserId)
            == -1) {
          this.mActionScheduler.updateIsPauseAutoClick(false);
        }
      }
    }
  }

  public final class ActionScheduler implements Runnable {
    public boolean mActive;
    public MotionEvent.PointerCoords mAnchorCoords;
    public CornerActionController mCornerActionController;
    public int mCornerActionType;
    public int mDelay;
    public int mDisplayId;
    public int mEventPolicyFlags;
    public Handler mHandler;
    public boolean mIsPauseAutoClick;
    public MotionEvent mLastMotionEvent = null;
    public int mMetaState;
    public long mScheduledActionTime;
    public MotionEvent.PointerCoords[] mTempPointerCoords;
    public MotionEvent.PointerProperties[] mTempPointerProperties;
    public int mType;

    public ActionScheduler(Handler handler, int i, int i2, int i3) {
      this.mHandler = handler;
      resetInternalState();
      this.mDelay = i;
      this.mType = i2;
      this.mCornerActionType = i3;
      this.mAnchorCoords = new MotionEvent.PointerCoords();
      updateIsPauseAutoClick(false);
    }

    @Override // java.lang.Runnable
    public void run() {
      long uptimeMillis = SystemClock.uptimeMillis();
      long j = this.mScheduledActionTime;
      if (uptimeMillis < j) {
        this.mHandler.postDelayed(this, j - uptimeMillis);
      } else {
        sendAction();
        resetInternalState();
      }
    }

    public void update(MotionEvent motionEvent, int i) {
      this.mMetaState = motionEvent.getMetaState();
      this.mDisplayId = motionEvent.getDisplayId();
      boolean detectMovement = detectMovement(motionEvent);
      cacheLastEvent(motionEvent, i, this.mLastMotionEvent == null || detectMovement);
      if (detectMovement) {
        rescheduleAction(this.mDelay);
      }
    }

    public void cancel() {
      if (this.mActive) {
        CornerActionController cornerActionController = this.mCornerActionController;
        if (cornerActionController != null) {
          cornerActionController.setDurationViewOnOff(false);
          this.mCornerActionController.clearDuration();
        }
        resetInternalState();
        this.mHandler.removeCallbacks(this);
      }
    }

    public void updateMetaState(int i) {
      this.mMetaState = i;
    }

    public void updateDelay(int i) {
      this.mDelay = i;
    }

    public void updateType(int i) {
      this.mType = i;
    }

    public void updateIsPauseAutoClick(boolean z) {
      this.mIsPauseAutoClick = z;
      Settings.Secure.putInt(
          AutoActionController.this.mContext.getContentResolver(),
          "accessibility_auto_click_paused_state",
          z ? 1 : 0);
    }

    public final void rescheduleAction(int i) {
      MotionEvent motionEvent;
      if (this.mCornerActionController != null && (motionEvent = this.mLastMotionEvent) != null) {
        int actionIndex = motionEvent.getActionIndex();
        float x = this.mLastMotionEvent.getX(actionIndex);
        float y = this.mLastMotionEvent.getY(actionIndex);
        this.mCornerActionController.setDurationViewOnOff(false);
        this.mCornerActionController.clearDuration();
        this.mCornerActionController.createDurationProgress(this.mDisplayId);
        if (this.mCornerActionController.getCorner(x, y, this.mDisplayId) != -1
            || this.mCornerActionType == 1) {
          this.mCornerActionController.updateDurationViewXY(x, y);
          this.mCornerActionController.setDurationViewOnOff(true);
          this.mCornerActionController.startDuration(i);
        }
      }
      long j = i;
      long uptimeMillis = SystemClock.uptimeMillis() + j;
      boolean z = this.mActive;
      if (z && uptimeMillis > this.mScheduledActionTime) {
        this.mScheduledActionTime = uptimeMillis;
        return;
      }
      if (z) {
        this.mHandler.removeCallbacks(this);
      }
      this.mActive = true;
      this.mScheduledActionTime = uptimeMillis;
      this.mHandler.postDelayed(this, j);
    }

    public final void cacheLastEvent(MotionEvent motionEvent, int i, boolean z) {
      MotionEvent motionEvent2 = this.mLastMotionEvent;
      if (motionEvent2 != null) {
        motionEvent2.recycle();
      }
      MotionEvent obtain = MotionEvent.obtain(motionEvent);
      this.mLastMotionEvent = obtain;
      this.mEventPolicyFlags = i;
      if (z) {
        this.mLastMotionEvent.getPointerCoords(obtain.getActionIndex(), this.mAnchorCoords);
      }
    }

    public final void resetInternalState() {
      this.mActive = false;
      MotionEvent motionEvent = this.mLastMotionEvent;
      if (motionEvent != null) {
        motionEvent.recycle();
        this.mLastMotionEvent = null;
      }
      this.mScheduledActionTime = -1L;
    }

    public final boolean detectMovement(MotionEvent motionEvent) {
      if (this.mLastMotionEvent == null) {
        return false;
      }
      int actionIndex = motionEvent.getActionIndex();
      return Math.hypot(
              (double) (this.mAnchorCoords.x - motionEvent.getX(actionIndex)),
              (double) (this.mAnchorCoords.y - motionEvent.getY(actionIndex)))
          > 20.0d;
    }

    public final void sendAction() {
      if (this.mLastMotionEvent == null || AutoActionController.this.getNext() == null) {
        return;
      }
      int actionIndex = this.mLastMotionEvent.getActionIndex();
      if (this.mTempPointerProperties == null) {
        this.mTempPointerProperties =
            new MotionEvent.PointerProperties[] {new MotionEvent.PointerProperties()};
      }
      this.mLastMotionEvent.getPointerProperties(actionIndex, this.mTempPointerProperties[0]);
      if (this.mTempPointerCoords == null) {
        this.mTempPointerCoords = new MotionEvent.PointerCoords[] {new MotionEvent.PointerCoords()};
      }
      this.mLastMotionEvent.getPointerCoords(actionIndex, this.mTempPointerCoords[0]);
      long uptimeMillis = SystemClock.uptimeMillis();
      CornerActionController cornerActionController = this.mCornerActionController;
      if (cornerActionController != null) {
        cornerActionController.clearDuration();
        this.mCornerActionController.setDurationViewOnOff(false);
        CornerActionController cornerActionController2 = this.mCornerActionController;
        MotionEvent.PointerCoords pointerCoords = this.mTempPointerCoords[0];
        int corner =
            cornerActionController2.getCorner(pointerCoords.x, pointerCoords.y, this.mDisplayId);
        int i = this.mCornerActionType;
        if (i == 0) {
          if (corner != -1) {
            int handleCornerAction =
                this.mCornerActionController.handleCornerAction(corner, this.mDisplayId);
            this.mCornerActionType = handleCornerAction;
            if (handleCornerAction == 2) {
              showActionSelectPopup(corner, this.mDisplayId);
              return;
            } else {
              if (handleCornerAction == 3) {
                updateIsPauseAutoClick(!this.mIsPauseAutoClick);
                this.mCornerActionType = 0;
                showToastToggleAutoClick();
                return;
              }
              return;
            }
          }
        } else if (i == 1) {
          this.mCornerActionType =
              this.mCornerActionController.performGestureAction(this.mLastMotionEvent);
          return;
        } else if (i == 2 && corner != -1) {
          return;
        }
      }
      if (this.mType == 0 || this.mIsPauseAutoClick) {
        return;
      }
      click(uptimeMillis, uptimeMillis);
    }

    public final void click(long j, long j2) {
      InputManager inputManager =
          (InputManager) AutoActionController.this.mContext.getSystemService("input");
      if (inputManager != null) {
        MotionEvent.PointerCoords pointerCoords = this.mTempPointerCoords[0];
        MotionEvent obtain = MotionEvent.obtain(j, j2, 0, pointerCoords.x, pointerCoords.y, 1);
        obtain.setSource(4098);
        obtain.setFlags(8388608);
        obtain.setDisplayId(this.mDisplayId);
        MotionEvent obtain2 = MotionEvent.obtain(obtain);
        obtain2.setAction(11);
        obtain2.setActionButton(1);
        MotionEvent obtain3 = MotionEvent.obtain(obtain);
        obtain3.setAction(12);
        obtain3.setActionButton(1);
        obtain3.setButtonState(0);
        MotionEvent obtain4 = MotionEvent.obtain(obtain);
        obtain4.setAction(1);
        obtain4.setButtonState(0);
        inputManager.semInjectInputEvent(obtain, 0);
        obtain.recycle();
        inputManager.semInjectInputEvent(obtain2, 0);
        obtain2.recycle();
        inputManager.semInjectInputEvent(obtain3, 0);
        obtain3.recycle();
        inputManager.semInjectInputEvent(obtain4, 0);
        obtain4.recycle();
      }
    }

    public final void setCornerActionController(boolean z) {
      if (z) {
        this.mCornerActionController =
            new CornerActionController(
                AutoActionController.this.mContext, AutoActionController.this.mUserId);
      } else {
        this.mCornerActionController = null;
      }
    }

    public final void showActionSelectPopup(final int i, final int i2) {
      final String[] cornerActions = this.mCornerActionController.getCornerActions(i);
      ArrayList arrayList = new ArrayList();
      for (String str : cornerActions) {
        if (str.equals("pause_resume_auto_click")) {
          str = this.mIsPauseAutoClick ? "resume_auto_click" : "pause_auto_click";
        } else if (str.equals("sound_vibrate_mute")) {
          Vibrator vibrator =
              (Vibrator) AutoActionController.this.mContext.getSystemService("vibrator");
          if (!(vibrator != null && vibrator.hasVibrator())) {
            str = "sound_mute";
          }
        }
        arrayList.add(this.mCornerActionController.convertKeyToTitle(str));
      }
      final CornerActionSelectPopup cornerActionSelectPopup =
          new CornerActionSelectPopup(this.mCornerActionController.getDisplayContext(i2), i);
      cornerActionSelectPopup.setCornerActionList(arrayList);
      cornerActionSelectPopup.setOnDismissListener(
          new PopupMenu.OnDismissListener() { // from class:
            // com.android.server.accessibility.autoaction.AutoActionController.ActionScheduler.1
            @Override // android.widget.PopupMenu.OnDismissListener
            public void onDismiss(PopupMenu popupMenu) {
              ActionScheduler.this.mCornerActionType = 0;
              cornerActionSelectPopup.destroy();
            }
          });
      cornerActionSelectPopup.setOnMenuItemClickListener(
          new PopupMenu.OnMenuItemClickListener() { // from class:
            // com.android.server.accessibility.autoaction.AutoActionController.ActionScheduler.2
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
              cornerActionSelectPopup.dismiss();
              int itemId = menuItem.getItemId();
              ActionScheduler actionScheduler = ActionScheduler.this;
              actionScheduler.mCornerActionType =
                  actionScheduler.mCornerActionController.performCornerAction(
                      cornerActions[itemId], i2, i);
              if (ActionScheduler.this.mCornerActionType == 3) {
                ActionScheduler.this.updateIsPauseAutoClick(!r5.mIsPauseAutoClick);
                ActionScheduler.this.mCornerActionType = 0;
                ActionScheduler.this.showToastToggleAutoClick();
              }
              return false;
            }
          });
      cornerActionSelectPopup.show();
    }

    public final void showToastToggleAutoClick() {
      this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.accessibility.autoaction.AutoActionController.ActionScheduler.3
            @Override // java.lang.Runnable
            public void run() {
              Toast.makeText(
                      AutoActionController.this.mContext,
                      ActionScheduler.this.mIsPauseAutoClick
                          ? R.string.config_batterymeterErrorPerimeterPath
                          : R.string.config_batterymeterFillMask,
                      0)
                  .show();
            }
          });
    }

    public String toString() {
      return "ActionScheduler: { active="
          + this.mActive
          + ", type="
          + this.mType
          + ", delay="
          + this.mDelay
          + ", scheduledActionTime="
          + this.mScheduledActionTime
          + ", anchor={x:"
          + this.mAnchorCoords.x
          + ", y:"
          + this.mAnchorCoords.y
          + "}, metastate="
          + this.mMetaState
          + ", policyFlags="
          + this.mEventPolicyFlags
          + ", lastMotionEvent="
          + this.mLastMotionEvent
          + " }";
    }
  }
}
