package com.android.server.accessibility.autoaction;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.server.accessibility.BaseEventStreamTransformation;

public final class AutoActionController extends BaseEventStreamTransformation {
    public ActionScheduler mActionScheduler;
    public AutoActionObserver mAutoActionObserver;
    public final Context mContext;
    public final int mUserId;

    public final class ActionScheduler implements Runnable {
        public boolean mActive;
        public final MotionEvent.PointerCoords mAnchorCoords;
        public CornerActionController mCornerActionController;
        public int mCornerActionType;
        public int mDelay;
        public int mDisplayId;
        public int mEventPolicyFlags;
        public final Handler mHandler;
        public boolean mIsPauseAutoClick;
        public MotionEvent mLastMotionEvent = null;
        public int mMetaState;
        public long mScheduledActionTime;
        public MotionEvent.PointerCoords[] mTempPointerCoords;
        public MotionEvent.PointerProperties[] mTempPointerProperties;
        public int mType;

        /* renamed from: com.android.server.accessibility.autoaction.AutoActionController$ActionScheduler$3, reason: invalid class name */
        public final class AnonymousClass3 implements Runnable {
            public AnonymousClass3() {}

            @Override // java.lang.Runnable
            public final void run() {
                ActionScheduler actionScheduler = ActionScheduler.this;
                Toast.makeText(
                                AutoActionController.this.mContext,
                                actionScheduler.mIsPauseAutoClick
                                        ? R.string.config_chooserActivity
                                        : R.string.config_clockFontFamily,
                                0)
                        .show();
            }
        }

        public ActionScheduler(Handler handler) {
            this.mHandler = handler;
            resetInternalState();
            this.mDelay = 600;
            this.mType = 0;
            this.mCornerActionType = 0;
            this.mAnchorCoords = new MotionEvent.PointerCoords();
            updateIsPauseAutoClick(false);
        }

        public final void cancel() {
            if (this.mActive) {
                CornerActionController cornerActionController = this.mCornerActionController;
                if (cornerActionController != null) {
                    CornerActionCircleCue cornerActionCircleCue =
                            cornerActionController.mDurationProgress;
                    if (cornerActionCircleCue != null) {
                        cornerActionCircleCue.runOnUiThread(
                                new CornerActionCircleCue$$ExternalSyntheticLambda2(
                                        cornerActionCircleCue, false));
                    }
                    this.mCornerActionController.clearDuration();
                }
                resetInternalState();
                this.mHandler.removeCallbacks(this);
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

        /* JADX WARN: Code restructure failed: missing block: B:84:0x01fc, code lost:

           if (r14 != (-1)) goto L88;
        */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 629
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.accessibility.autoaction.AutoActionController.ActionScheduler.run():void");
        }

        public final String toString() {
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

        public final void updateIsPauseAutoClick(boolean z) {
            this.mIsPauseAutoClick = z;
            Settings.Secure.putInt(
                    AutoActionController.this.mContext.getContentResolver(),
                    "accessibility_auto_click_paused_state",
                    z ? 1 : 0);
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
            this.mAutoActionTypeSettingUri =
                    Settings.Secure.getUriFor("accessibility_auto_action_type");
            this.mAutoActionDelaySettingUri =
                    Settings.Secure.getUriFor("accessibility_auto_action_delay");
            this.mCornerActionEnabledSettingUri =
                    Settings.Secure.getUriFor("accessibility_corner_action_enabled");
            this.mPauseAutoClickWithSettingUri =
                    Settings.Secure.getUriFor("accessibility_pause_auto_click_with");
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mAutoActionTypeSettingUri.equals(uri)) {
                this.mActionScheduler.mType =
                        Settings.Secure.getIntForUser(
                                this.mContentResolver,
                                "accessibility_auto_action_type",
                                0,
                                this.mUserId);
                return;
            }
            if (this.mAutoActionDelaySettingUri.equals(uri)) {
                this.mActionScheduler.mDelay =
                        Settings.Secure.getIntForUser(
                                this.mContentResolver,
                                "accessibility_auto_action_delay",
                                600,
                                this.mUserId);
                return;
            }
            if (!this.mCornerActionEnabledSettingUri.equals(uri)) {
                if (this.mPauseAutoClickWithSettingUri.equals(uri)
                        && Settings.Secure.getIntForUser(
                                        this.mContentResolver,
                                        "accessibility_pause_auto_click_with",
                                        0,
                                        this.mUserId)
                                == -1) {
                    this.mActionScheduler.updateIsPauseAutoClick(false);
                    return;
                }
                return;
            }
            boolean z2 =
                    Settings.Secure.getIntForUser(
                                    this.mContentResolver,
                                    "accessibility_corner_action_enabled",
                                    0,
                                    this.mUserId)
                            != 0;
            ActionScheduler actionScheduler = this.mActionScheduler;
            if (!z2) {
                actionScheduler.mCornerActionController = null;
                return;
            }
            actionScheduler.getClass();
            AutoActionController autoActionController = AutoActionController.this;
            actionScheduler.mCornerActionController =
                    new CornerActionController(
                            autoActionController.mContext, autoActionController.mUserId);
        }
    }

    public AutoActionController(Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        ActionScheduler actionScheduler;
        if (i == 8194 && (actionScheduler = this.mActionScheduler) != null) {
            actionScheduler.cancel();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        AutoActionObserver autoActionObserver = this.mAutoActionObserver;
        if (autoActionObserver != null) {
            ContentResolver contentResolver = autoActionObserver.mContentResolver;
            if (contentResolver == null || autoActionObserver.mActionScheduler == null) {
                throw new IllegalStateException("AutoActionObserver not started.");
            }
            contentResolver.unregisterContentObserver(autoActionObserver);
            this.mAutoActionObserver = null;
        }
        ActionScheduler actionScheduler = this.mActionScheduler;
        if (actionScheduler != null) {
            actionScheduler.cancel();
            this.mActionScheduler = null;
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onKeyEvent(KeyEvent keyEvent, int i) {
        if (this.mActionScheduler != null) {
            if (KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
                this.mActionScheduler.mMetaState = keyEvent.getMetaState();
            } else {
                this.mActionScheduler.cancel();
            }
        }
        super.onKeyEvent(keyEvent, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f8  */
    @Override // com.android.server.accessibility.EventStreamTransformation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMotionEvent(
            android.view.MotionEvent r10, android.view.MotionEvent r11, int r12) {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.autoaction.AutoActionController.onMotionEvent(android.view.MotionEvent,"
                    + " android.view.MotionEvent, int):void");
    }
}
