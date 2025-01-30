package com.android.server.sepunion.cover;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.format.DateFormat;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public final class NfcLedCoverController extends BaseNfcLedCoverController {
  public static final String TAG = "CoverManager_" + NfcLedCoverController.class.getSimpleName();
  public static final byte[] mResponsePattern = {
    HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 6, -47, 0, -1, -1
  };
  public Timer mCallDurationTimer;
  public CallDurationTask mCallDurationUpdateTask;
  public boolean mCallInProgressDisplay;
  public long mCallStartTime;
  public ReentrantLock mCallTimerLock;
  public boolean mCoverEventsDisabledForSamsungPay;
  public boolean mFactoryTransceiveResponseIntentSent;
  public final NfcLedCoverControllerHandler mHandler;
  public boolean mIsLedOn;
  public long mLedCoverRetryPostTime;
  public int mLedCoverStartRetryCount;
  public int mLedCoverTransceiveRetryCount;
  public PowerManager.WakeLock mLedOnOffWakeLock;
  public ArrayList mListeners;
  public Command mOngoingEvent;
  public PowerManager.WakeLock mPollTouchWakeLock;
  public boolean mPollingTouchEvents;
  public int mPrevCommand;
  public PowerManager.WakeLock mSendLedDataWakeLock;
  public int mTestCount;
  public int mTestMode;
  public PowerManager.WakeLock mTouchResponseWakeLock;

  public NfcLedCoverController(Looper looper, Context context) {
    super(looper, context);
    this.mIsLedOn = false;
    this.mCallTimerLock = new ReentrantLock(true);
    this.mCallStartTime = -1L;
    this.mLedCoverStartRetryCount = 0;
    this.mLedCoverTransceiveRetryCount = 0;
    this.mListeners = new ArrayList();
    this.mHandler = new NfcLedCoverControllerHandler(looper);
    PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "send leddata");
    this.mSendLedDataWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
    PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, "pollTouch ledcover");
    this.mPollTouchWakeLock = newWakeLock2;
    newWakeLock2.setReferenceCounted(false);
    PowerManager.WakeLock newWakeLock3 =
        this.mPowerManager.newWakeLock(1, "touchResponse ledcover");
    this.mTouchResponseWakeLock = newWakeLock3;
    newWakeLock3.setReferenceCounted(false);
    PowerManager.WakeLock newWakeLock4 = this.mPowerManager.newWakeLock(1, "onoff ledcover");
    this.mLedOnOffWakeLock = newWakeLock4;
    newWakeLock4.setReferenceCounted(false);
    this.mTestMode = Settings.Secure.getInt(context.getContentResolver(), "nfc_led_cover_test", 0);
  }

  public final class NfcLedCoverControllerHandler extends Handler {
    public NfcLedCoverControllerHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 0:
          NfcLedCoverController.this.clearRetryCountDelayedMsg();
          break;
        case 1:
          NfcLedCoverController.this.clearRetryCountDelayedMsg();
          if (NfcLedCoverController.this.mPrevCommand == 12) {
            NfcLedCoverController.this.sendCurrentClockCommand();
          } else {
            NfcLedCoverController.this.handleSendDataToNfcLedCover(18, null);
          }
          NfcLedCoverController nfcLedCoverController = NfcLedCoverController.this;
          nfcLedCoverController.releaseWakeLockWithPermission(
              nfcLedCoverController.mLedOnOffWakeLock);
          return;
        case 2:
          NfcLedCoverController.this.handlePollEventTouch(message.arg1);
          return;
        case 3:
          NfcLedCoverController.this.handleEventResponse(message.arg1, message.arg2);
          return;
        case 4:
          break;
        case 5:
          NfcLedCoverController.this.handleClearOngoingEvent(message.arg1);
          if (NfcLedCoverController.this.mHandler.hasMessages(0)
              || NfcLedCoverController.this.mHandler.hasMessages(4)
              || NfcLedCoverController.this.mHandler.hasMessages(5)) {
            return;
          }
          NfcLedCoverController nfcLedCoverController2 = NfcLedCoverController.this;
          nfcLedCoverController2.releaseWakeLockWithPermission(
              nfcLedCoverController2.mSendLedDataWakeLock);
          return;
        case 6:
          NfcLedCoverController.this.handleSendPowerKeyToCover();
          return;
        case 7:
          if (message.arg1 == 1) {
            return;
          }
          NfcLedCoverController.this.handleCoverDetachedLocked();
          return;
        case 8:
          NfcLedCoverController.this.handleAddLedNotification((Bundle) message.obj);
          return;
        case 9:
          NfcLedCoverController.this.handleRemoveLedNotification((Bundle) message.obj);
          return;
        case 10:
          NfcLedCoverController.this.handleLcdOffDisabledByCover((Bundle) message.obj);
          return;
        default:
          return;
      }
      NfcLedCoverController.this.handleSendDataToNfcLedCover(message.arg1, (byte[]) message.obj);
      if (NfcLedCoverController.this.mHandler.hasMessages(0)
          || NfcLedCoverController.this.mHandler.hasMessages(4)
          || NfcLedCoverController.this.mHandler.hasMessages(5)) {
        return;
      }
      NfcLedCoverController nfcLedCoverController3 = NfcLedCoverController.this;
      nfcLedCoverController3.releaseWakeLockWithPermission(
          nfcLedCoverController3.mSendLedDataWakeLock);
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void updateNfcLedCoverAttachStateLocked(boolean z, int i) {
    boolean z2 = z && (i == 7 || i == 14);
    if (this.mIsLedCoverAttached != z2) {
      this.mIsLedCoverAttached = z2;
      if (!z2) {
        Log.d(TAG, "NfcLedCover detached, start clearing all flags, messages, wakelocks");
        Message obtainMessage = this.mHandler.obtainMessage(7);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessageAtFrontOfQueue(obtainMessage);
      }
    }
    this.mTestMode =
        Settings.Secure.getInt(this.mContext.getContentResolver(), "nfc_led_cover_test", 0);
  }

  public final void handleCoverDetachedLocked() {
    Log.d(TAG, "handleCoverDetached()");
    this.mCallTimerLock.lock();
    try {
      stopCallInProgressDisplayTimer();
      this.mHandler.removeCallbacksAndMessages(null);
      releaseWakeLockWithPermission(this.mSendLedDataWakeLock);
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      releaseWakeLockWithPermission(this.mTouchResponseWakeLock);
      releaseWakeLockWithPermission(this.mLedOnOffWakeLock);
      this.mPollingTouchEvents = false;
      this.mPrevCommand = 0;
      this.mOngoingEvent = null;
      clearRetryCountDelayedMsg();
      if (getNfcAdapter() != null) {
        stopLedCover();
      }
    } finally {
      this.mCallTimerLock.unlock();
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendDataToNfcLedCover(int i, byte[] bArr) {
    if (!this.mIsLedCoverAttached
        && !FactoryTest.isFactoryBinary()
        && this.mCoverEventsDisabledForSamsungPay) {
      Log.d(TAG, "sendDataToLedCover : Not attached LED Cover or Disabled by SamsungPay");
      return;
    }
    if (this.mNfcAdapter == null && getNfcAdapter() == null) {
      Log.d(TAG, "sendDataToLedCover : Nfc Service not available");
      return;
    }
    if (i == 65535) {
      if (this.mPrevCommand != 15 || !this.mIsLedOn) {
        Log.d(TAG, "Time tick: clock not displayed, ignore");
        return;
      }
      i = 15;
    }
    if (i == 15) {
      bArr = getCurrentClockData();
    }
    if (i == 12 && this.mPrevCommand == 11) {
      Log.e(TAG, "Ignore battery chargin, battery low already shown");
      return;
    }
    acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
    Message obtain = Message.obtain();
    obtain.arg1 = i;
    obtain.obj = bArr;
    if (i == 65520) {
      if (bArr == null || bArr.length <= 0) {
        return;
      }
      obtain.what = 5;
      obtain.arg1 = bArr[0];
      obtain.obj = null;
      this.mHandler.sendMessage(obtain);
      return;
    }
    if (i == 19) {
      obtain.what = 4;
      this.mHandler.removeMessages(4);
      this.mHandler.sendMessageDelayed(obtain, 150L);
      return;
    }
    obtain.what = 0;
    if (this.mHandler.hasMessages(4)) {
      this.mHandler.removeMessages(0);
      long currentTimeMillis = 500 - (System.currentTimeMillis() - this.mLedCoverRetryPostTime);
      r3 = currentTimeMillis >= 0 ? currentTimeMillis : 0L;
      Log.w(
          TAG,
          "There is pending DELAYED message due to Retry mechanism send this command with proper"
              + " delay:"
              + r3);
    }
    clearRetryCountDelayedMsg();
    this.mHandler.sendMessageDelayed(obtain, r3);
  }

  public final void scheduleLedOffTimeout(int i, byte[] bArr) {
    String str = TAG;
    Log.d(str, "scheduleLedOffTimerout, command: " + i);
    if (i == 1 || i == 2 || i == 18 || i == 20 || i == 224) {
      if (this.mHandler.hasMessages(1)) {
        this.mHandler.removeMessages(1);
      }
      releaseWakeLockWithPermission(this.mLedOnOffWakeLock);
      return;
    }
    if (this.mIsLedOn
        && ((i == 15 || i == 3) && this.mPrevCommand == i && this.mHandler.hasMessages(1))) {
      if (i == 15) {
        Log.d(str, "Time update");
        return;
      } else {
        Log.d(str, "Call InProgress duration time update");
        return;
      }
    }
    acquireWakeLockWithPermission(this.mLedOnOffWakeLock);
    if (this.mHandler.hasMessages(1)) {
      this.mHandler.removeMessages(1);
    }
    Message.obtain();
    Message obtain = Message.obtain();
    obtain.what = 1;
    if (i == 6) {
      this.mHandler.sendMessageDelayed(obtain, 60000L);
      return;
    }
    if (i != 9) {
      switch (i) {
        case 12:
          this.mHandler.sendMessageDelayed(obtain, 4600L);
          break;
        case 13:
        case 14:
          this.mHandler.sendMessageDelayed(obtain, 9000L);
          break;
        default:
          this.mHandler.sendMessageDelayed(obtain, 3700L);
          break;
      }
      return;
    }
    if (isSingleMissedEvent(bArr)) {
      this.mHandler.sendMessageDelayed(obtain, 3700L);
    } else {
      this.mHandler.sendMessageDelayed(obtain, 5100L);
    }
  }

  public final boolean isSingleMissedEvent(byte[] bArr) {
    if (bArr == null || bArr.length < 5) {
      return false;
    }
    return (bArr[1] == 48 && bArr[2] == 48) || (bArr[3] == 48 && bArr[4] == 48);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName) {
    if (i < 0 || i > 5) {
      Log.e(TAG, "Unsupported touch listener type: " + i);
      return;
    }
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null && iBinder.equals(nfcLedTouchListenerInfo.token)) {
          Log.e(TAG, "sendDataToNfcLedCover : duplicated listener handle");
          return;
        }
      }
      NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 =
          new NfcLedTouchListenerInfo(
              iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), i);
      iBinder.linkToDeath(nfcLedTouchListenerInfo2, 0);
      this.mListeners.add(nfcLedTouchListenerInfo2);
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null && iBinder.equals(nfcLedTouchListenerInfo.token)) {
          Log.e(TAG, "remove listener: " + nfcLedTouchListenerInfo.pid);
          this.mListeners.remove(nfcLedTouchListenerInfo);
          iBinder.unlinkToDeath(nfcLedTouchListenerInfo, 0);
          return true;
        }
      }
      Log.e(TAG, "UnregisterNfcTouchListener: listener does not exist");
      return false;
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void addLedNotification(Bundle bundle) {
    Log.d(TAG, "addLedNotification");
    Message obtainMessage = this.mHandler.obtainMessage(8);
    obtainMessage.obj = bundle;
    this.mHandler.sendMessage(obtainMessage);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void removeLedNotification(Bundle bundle) {
    Log.d(TAG, "removeLedNotification");
    Message obtainMessage = this.mHandler.obtainMessage(9);
    obtainMessage.obj = bundle;
    this.mHandler.sendMessage(obtainMessage);
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendSystemEvent(Bundle bundle) {
    int i = bundle.getInt("event_type");
    if (i != 0) {
      if (i != 1) {
        return;
      }
      Message obtainMessage = this.mHandler.obtainMessage(10);
      obtainMessage.obj = bundle;
      this.mHandler.sendMessage(obtainMessage);
      return;
    }
    int i2 = bundle.getInt("lcd_touch_listener_type", -1);
    int i3 = bundle.getInt("lcd_touch_listener_respone", -1);
    Message obtain = Message.obtain();
    obtain.what = 3;
    obtain.arg1 = i2;
    obtain.arg2 = i3;
    this.mHandler.sendMessage(obtain);
  }

  public final void handleAddLedNotification(Bundle bundle) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(2, bundle);
        }
      }
    }
  }

  public final void handleRemoveLedNotification(Bundle bundle) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(3, bundle);
        }
      }
    }
  }

  public final void handleLcdOffDisabledByCover(Bundle bundle) {
    boolean z = bundle.getBoolean("lcd_off_disabled_by_cover");
    if (this.mCoverEventsDisabledForSamsungPay != z) {
      this.mCoverEventsDisabledForSamsungPay = z;
      if (z && this.mIsLedOn) {
        sendDataToNfcLedCover(18, null);
      }
    }
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(4, bundle);
        }
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendPowerKeyToCover() {
    if (this.mCoverEventsDisabledForSamsungPay) {
      Log.d(
          TAG,
          "handleMessage MSG_EVENT_POWER_BUTTON: SamsungPay active - ignore power button events");
    } else {
      this.mHandler.sendEmptyMessage(6);
    }
  }

  public final void handleSendPowerKeyToCover() {
    notifyPowerButtonPressListeners();
    byte[] bArr = {0};
    if (this.mIsLedOn) {
      releaseWakeLockWithPermission(this.mLedOnOffWakeLock);
      sendDataToNfcLedCover(18, bArr);
      return;
    }
    this.mCallTimerLock.lock();
    try {
      Command command = this.mOngoingEvent;
      if (command != null) {
        int i = command.code;
        if (i == 3) {
          if (this.mCallDurationTimer != null && this.mCallDurationUpdateTask != null) {
            this.mCallInProgressDisplay = true;
          }
          Log.e(
              TAG,
              "There is no time update task but we've got call duration ongoing event... displaying"
                  + " clock instead");
          clearOngoingEvent();
          sendCurrentClockCommand();
        } else {
          sendDataToNfcLedCover(i, command.data);
        }
      } else {
        sendCurrentClockCommand();
      }
    } finally {
      this.mCallTimerLock.unlock();
    }
  }

  public final void sendCurrentClockCommand() {
    sendDataToNfcLedCover(15, null);
  }

  public final byte[] getCurrentClockData() {
    String format;
    boolean is24HourFormat =
        DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser());
    long currentTimeMillis = System.currentTimeMillis();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(currentTimeMillis);
    int i = calendar.get(11);
    int i2 = calendar.get(12);
    if (is24HourFormat) {
      format = String.format(null, "%02d%02d", Integer.valueOf(i), Integer.valueOf(i2));
    } else {
      int i3 = i % 12;
      format =
          String.format(null, "%2d%02d", Integer.valueOf(i3 != 0 ? i3 : 12), Integer.valueOf(i2));
    }
    return format.getBytes();
  }

  public final boolean tryStartLedCover() {
    String str = TAG;
    Log.d(str, "Trying to start NFC LED Cover mIsLedOn=" + String.valueOf(this.mIsLedOn));
    if (!this.mIsLedOn) {
      if (this.mLedCoverStartRetryCount == 0) {
        this.mNfcAdapter.semSetWirelessChargeEnabled(false);
      }
      byte[] semStartLedCoverMode = this.mNfcAdapter.semStartLedCoverMode();
      if (isValidCoverStartData(semStartLedCoverMode)) {
        Log.d(str, "Started NFC LED Cover");
        this.mLedCoverStartRetryCount = 0;
        this.mIsLedOn = true;
      } else {
        Log.w(str, "Failed to start NFC LED Cover");
        sendNfcFailIntentForFactoryMode(semStartLedCoverMode);
        return false;
      }
    } else {
      Log.d(str, "NFC LED Cover already started");
    }
    return true;
  }

  public final void handleSendDataToNfcLedCover(int i, byte[] bArr) {
    byte[] bArr2;
    this.mCallTimerLock.lock();
    if (i == 4) {
      try {
        stopCallInProgressDisplayTimer();
        if (bArr != null && bArr.length > 0 && bArr[0] == 0) {
          if (this.mPrevCommand == 4 && this.mHandler.hasMessages(1)) {
            Log.d(TAG, "CMD_LED_CALL_END was already processed so return");
            return;
          } else {
            Log.d(
                TAG,
                "CMD_LED_CALL_END called but with dummy data, switch to LED_OFF and stop"
                    + " CallTimer");
            i = 18;
          }
        }
      } finally {
        this.mCallTimerLock.unlock();
      }
    }
    if (i == 65534) {
      startCallInProgressDisplayTimer(bArr);
      i = 3;
    } else {
      if (i == 18 && !this.mIsLedOn) {
        this.mNfcAdapter.semSetWirelessChargeEnabled(true);
        Log.d(TAG, "Led cover already off");
        this.mCallInProgressDisplay = false;
      } else {
        String str = TAG;
        Log.d(str, "Ensuring NFC LED Cover started");
        if (!tryStartLedCover()) {
          if (this.mLedCoverStartRetryCount < 13) {
            Log.e(
                str,
                "Repeat command after LED_COVER_RETRY_DELAY: 500 count: "
                    + this.mLedCoverStartRetryCount);
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.arg1 = i;
            obtain.obj = bArr;
            this.mHandler.sendMessageDelayed(obtain, 500L);
            this.mLedCoverRetryPostTime = System.currentTimeMillis();
            this.mLedCoverStartRetryCount++;
            this.mNfcAdapter.semStopLedCoverMode();
          } else {
            Log.e(str, "Could not start NFC LED Cover");
            this.mLedCoverStartRetryCount = 0;
            this.mContext.sendBroadcastAsUser(
                new Intent("android.intent.action.NFC_LED_COVER_MAX_RETRY_DONE"), UserHandle.ALL);
            Log.d(str, "Sent done intent, fail start");
            stopLedCover();
            this.mFactoryTransceiveResponseIntentSent = false;
          }
        } else {
          this.mLedCoverStartRetryCount = 0;
          try {
            bArr2 = this.mNfcAdapter.semTransceiveDataWithLedCover(buildNfcCoverLedData(i, bArr));
          } catch (Exception e) {
            Log.e(TAG, "Error in trancieve command", e);
            bArr2 = null;
          }
          if (!isValidResponse(i, bArr2)) {
            String str2 = TAG;
            Log.e(
                str2, "Error parsing response for command " + i + ": " + getByteDataString(bArr2));
            handleInvalidCommand(i, bArr, bArr2);
            sendNfcFailIntentForFactoryMode(bArr2);
            if (this.mTestMode != 0) {
              Log.d(str2, "TEST mode enabled, ignore NFC Led Cover response");
            }
          }
          this.mFactoryTransceiveResponseIntentSent = false;
          this.mContext.sendBroadcastAsUser(
              new Intent("android.intent.action.NFC_LED_COVER_MAX_RETRY_DONE"), UserHandle.ALL);
          Log.d(TAG, "Sent done intent, sucess");
          this.mLedCoverTransceiveRetryCount = 0;
          scheduleLedOffTimeout(i, bArr);
        }
      }
      return;
    }
    int i2 = this.mPrevCommand;
    this.mPrevCommand = i;
    setOngoingEventIfValid(i, bArr);
    if (i == 2) {
      this.mCallInProgressDisplay = false;
    } else if (i != 6) {
      if (i != 10) {
        if (i == 18) {
          stopLedCover();
          notifyLedOffListeners(i2);
        } else if (i != 224) {
          this.mPollingTouchEvents = false;
          this.mHandler.removeMessages(2);
          releaseWakeLockWithPermission(this.mPollTouchWakeLock);
          this.mCallTimerLock.unlock();
        }
      }
      this.mCallInProgressDisplay = false;
      this.mPollingTouchEvents = false;
      this.mHandler.removeMessages(2);
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      this.mCallTimerLock.unlock();
    }
    if (this.mTestMode > 0) {
      this.mTestCount = 0;
    }
    Message obtain2 = Message.obtain();
    obtain2.what = 2;
    int listenerTypeForCommand = getListenerTypeForCommand(i, bArr);
    if (listenerTypeForCommand < 0) {
      Log.e(TAG, "Wrong listener type requested for command:" + i + "; return");
      return;
    }
    acquireWakeLockWithPermission(this.mPollTouchWakeLock);
    obtain2.arg1 = listenerTypeForCommand;
    if (this.mPollingTouchEvents) {
      Log.e(TAG, "Already polling for touch events");
      this.mHandler.removeMessages(2);
    } else {
      this.mPollingTouchEvents = true;
    }
    this.mHandler.sendMessageDelayed(obtain2, 100L);
    this.mCallTimerLock.unlock();
  }

  public final void setOngoingEventIfValid(int i, byte[] bArr) {
    if (i != 2 && i != 3) {
      if (i != 13) {
        if (i != 14 && i != 20) {
          return;
        }
      } else {
        if (bArr == null || bArr.length <= 0) {
          return;
        }
        byte b = bArr[0];
        if (b != 1 && b != 3) {
          return;
        }
      }
    }
    Command command = this.mOngoingEvent;
    if (command == null) {
      this.mOngoingEvent = new Command(i, bArr);
    } else {
      command.code = i;
      command.data = bArr;
    }
  }

  public final void clearOngoingEvent() {
    this.mOngoingEvent = null;
  }

  public final boolean isValidCoverStartData(byte[] bArr) {
    return bArr != null && bArr.length > 1;
  }

  public final void handleInvalidCommand(int i, byte[] bArr, byte[] bArr2) {
    boolean z;
    this.mPrevCommand = i;
    if (bArr2 != null && i != 18 && bArr2.length >= 1) {
      byte b = bArr2[0];
      if (b != -32 && b != 1 && b != 3 && b != 5 && b != 6) {
        switch (b) {
          case -80:
          case -79:
          case -78:
            break;
          default:
            Log.e(TAG, "Transceive error - unknown error value: " + ((int) bArr2[0]));
            z = false;
            break;
        }
        if (!z && bArr2.length > 2 && bArr2[2] == -48) {
          byte[] bArr3 = new byte[bArr2.length];
          System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
          bArr3[2] = -47;
          z = isValidResponse(i, bArr3);
        }
        if (!z && this.mLedCoverTransceiveRetryCount < 13) {
          Log.e(TAG, "Repeat command " + i + " count: " + this.mLedCoverTransceiveRetryCount);
          this.mIsLedOn = false;
          this.mNfcAdapter.semStopLedCoverMode();
          Message obtain = Message.obtain();
          obtain.what = 4;
          obtain.arg1 = i;
          obtain.obj = bArr;
          this.mHandler.sendMessageDelayed(obtain, 500L);
          this.mLedCoverRetryPostTime = System.currentTimeMillis();
          this.mLedCoverTransceiveRetryCount++;
          return;
        }
        this.mLedCoverTransceiveRetryCount = 0;
        Log.e(TAG, "Could not transceive command to cover so turn off led cover");
        this.mFactoryTransceiveResponseIntentSent = false;
      }
      z = true;
      if (!z) {
        byte[] bArr32 = new byte[bArr2.length];
        System.arraycopy(bArr2, 0, bArr32, 0, bArr2.length);
        bArr32[2] = -47;
        z = isValidResponse(i, bArr32);
      }
      if (!z) {}
      this.mLedCoverTransceiveRetryCount = 0;
      Log.e(TAG, "Could not transceive command to cover so turn off led cover");
      this.mFactoryTransceiveResponseIntentSent = false;
    }
    this.mContext.sendBroadcastAsUser(
        new Intent("android.intent.action.NFC_LED_COVER_MAX_RETRY_DONE"), UserHandle.ALL);
    Log.d(TAG, "Sent done intent, fail transceive");
    if ((i == 2 || i == 18) && this.mCallInProgressDisplay) {
      this.mCallInProgressDisplay = false;
    }
    stopLedCover();
    this.mPollingTouchEvents = false;
    this.mHandler.removeMessages(2);
    releaseWakeLockWithPermission(this.mPollTouchWakeLock);
  }

  public final byte[] buildNfcCoverLedData(int i, byte[] bArr) {
    if (bArr == null) {
      bArr = new byte[] {0};
    }
    int lenByteValue = getLenByteValue(bArr);
    int i2 = lenByteValue + 5;
    byte[] bArr2 = new byte[i2];
    bArr2[0] = 0;
    bArr2[1] = -94;
    bArr2[2] = 0;
    bArr2[3] = 0;
    byte b = (byte) lenByteValue;
    bArr2[4] = b;
    bArr2[5] = HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED;
    bArr2[6] = b;
    bArr2[7] = (byte) i;
    System.arraycopy(bArr, 0, bArr2, 8, bArr.length);
    bArr2[i2 - 2] = -1;
    bArr2[i2 - 1] = -1;
    return bArr2;
  }

  public final int getLenByteValue(byte[] bArr) {
    if (bArr != null) {
      return bArr.length + 5;
    }
    return 6;
  }

  public final void handlePollEventTouch(int i) {
    if (!this.mPollingTouchEvents) {
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      return;
    }
    if (this.mTestMode > 0) {
      this.mTestCount++;
    }
    byte[] buildNfcCoverLedData = buildNfcCoverLedData(17, new byte[] {0});
    byte[] bArr = null;
    try {
      if (this.mTestMode == 0) {
        bArr = this.mNfcAdapter.semTransceiveDataWithLedCover(buildNfcCoverLedData);
      }
    } catch (Exception e) {
      Log.e(TAG, "Error sending data to NFC", e);
    }
    int i2 = this.mTestMode;
    if (i2 > 0 && this.mTestCount > 19) {
      bArr = new byte[5];
      bArr[1] = 0;
      bArr[0] = 0;
      bArr[2] = -47;
      bArr[3] = 17;
      if (i2 == 2) {
        bArr[4] = 1;
      } else if (i2 == 1) {
        bArr[4] = 2;
      } else {
        Log.e(TAG, "Unknown test value: " + this.mTestMode + ", reject by default");
        bArr[4] = 2;
      }
    }
    if (isFinishedTouchReply(bArr)) {
      acquireWakeLockWithPermission(this.mTouchResponseWakeLock);
      Message obtain = Message.obtain();
      obtain.what = 3;
      obtain.arg1 = i;
      obtain.arg2 = bArr[4];
      this.mHandler.sendMessage(obtain);
      this.mPollingTouchEvents = false;
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      return;
    }
    Log.d(TAG, "No touch event from LED cover, keep listening");
    Message obtain2 = Message.obtain();
    obtain2.what = 2;
    obtain2.arg1 = i;
    this.mHandler.sendMessageDelayed(obtain2, 100L);
  }

  public final boolean isValidResponse(int i, byte[] bArr) {
    if (bArr == null) {
      return false;
    }
    int length = bArr.length;
    byte[] bArr2 = mResponsePattern;
    if (length < bArr2.length) {
      return false;
    }
    bArr2[3] = (byte) i;
    int i2 = 0;
    for (byte b : bArr) {
      byte[] bArr3 = mResponsePattern;
      if (b == bArr3[i2] || i2 == 1) {
        i2++;
        if (i2 == bArr3.length) {
          return true;
        }
      } else if (i2 != 0) {
        return false;
      }
    }
    return false;
  }

  public final boolean isFinishedTouchReply(byte[] bArr) {
    byte b;
    return bArr != null
        && bArr.length >= 5
        && bArr[2] == -47
        && bArr[3] == 17
        && ((b = bArr[4]) == 1 || b == 2);
  }

  public final void handleClearOngoingEvent(int i) {
    Command command;
    if (i == 65534) {
      i = 3;
    }
    if (i == 0 || ((command = this.mOngoingEvent) != null && command.code == i)) {
      clearOngoingEvent();
    }
  }

  public final void handleEventResponse(int i, int i2) {
    String str = TAG;
    Log.d(str, "HandleEventResponse: type: " + i + " action: " + i2);
    if (i2 == 1) {
      Log.d(str, "Event touch: accept");
      synchronized (this.mListeners) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
          if (i == nfcLedTouchListenerInfo.type) {
            nfcLedTouchListenerInfo.onCoverTouchAccept();
          }
        }
      }
    } else if (i2 == 2) {
      Log.d(str, "Event touch: reject");
      synchronized (this.mListeners) {
        Iterator it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
          NfcLedTouchListenerInfo nfcLedTouchListenerInfo2 = (NfcLedTouchListenerInfo) it2.next();
          if (i == nfcLedTouchListenerInfo2.type) {
            nfcLedTouchListenerInfo2.onCoverTouchReject();
          }
        }
      }
    } else {
      Log.d(str, "Unknown event action: " + i2);
    }
    releaseWakeLockWithPermission(this.mTouchResponseWakeLock);
  }

  public final void startCallInProgressDisplayTimer(byte[] bArr) {
    ByteBuffer allocate = ByteBuffer.allocate(64);
    try {
      allocate.put(bArr);
      allocate.flip();
      this.mCallStartTime = allocate.getLong();
      Log.d(TAG, "CallStartTime: " + this.mCallStartTime);
      Timer timer = this.mCallDurationTimer;
      if (timer != null) {
        timer.cancel();
        this.mCallDurationTimer = null;
        this.mCallDurationUpdateTask = null;
      }
      this.mCallInProgressDisplay = true;
      this.mCallDurationTimer = new Timer(true);
      CallDurationTask callDurationTask = new CallDurationTask();
      this.mCallDurationUpdateTask = callDurationTask;
      try {
        this.mCallDurationTimer.scheduleAtFixedRate(callDurationTask, 0L, 500L);
      } catch (RejectedExecutionException e) {
        Log.e(TAG, "Error while scheduling TimerTask", e);
        this.mCallInProgressDisplay = false;
        this.mCallDurationTimer = null;
        this.mCallDurationUpdateTask = null;
      }
    } catch (BufferOverflowException e2) {
      Log.e(TAG, "CallStartTime incorrect", e2);
    } catch (BufferUnderflowException e3) {
      Log.e(TAG, "CallStartTime incorrect", e3);
    }
  }

  public final void stopCallInProgressDisplayTimer() {
    this.mCallInProgressDisplay = false;
    Timer timer = this.mCallDurationTimer;
    if (timer != null) {
      timer.cancel();
      this.mCallDurationTimer = null;
      this.mCallDurationUpdateTask = null;
    } else {
      Log.e(TAG, "Call duration should not be null in stop or was already stopped");
    }
    this.mCallStartTime = -1L;
  }

  public class CallDurationTask extends TimerTask {
    public byte prevSecond;

    public CallDurationTask() {
      this.prevSecond = (byte) -1;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
      NfcLedCoverController.this.mCallTimerLock.lock();
      try {
        if (NfcLedCoverController.this.mCallInProgressDisplay) {
          byte[] callDuration = NfcLedCoverController.this.getCallDuration();
          if (callDuration[3] != this.prevSecond) {
            NfcLedCoverController.this.handleSendDataToNfcLedCover(3, callDuration);
            if (!NfcLedCoverController.this.mHandler.hasMessages(4)) {
              this.prevSecond = callDuration[3];
            }
            NfcLedCoverController.this.clearRetryCountDelayedMsg();
          }
        }
      } finally {
        NfcLedCoverController.this.mCallTimerLock.unlock();
      }
    }
  }

  public final void clearRetryCountDelayedMsg() {
    this.mLedCoverStartRetryCount = 0;
    this.mLedCoverTransceiveRetryCount = 0;
    if (this.mHandler.hasMessages(4)) {
      this.mHandler.removeMessages(4);
    }
  }

  public final void stopLedCover() {
    this.mNfcAdapter.semStopLedCoverMode();
    this.mIsLedOn = false;
    this.mNfcAdapter.semSetWirelessChargeEnabled(true);
  }

  public final byte[] getCallDuration() {
    byte[] bArr = {0, 0, 0, 0};
    if (this.mCallStartTime == -1) {
      return bArr;
    }
    long elapsedRealtime = (SystemClock.elapsedRealtime() - this.mCallStartTime) / 1000;
    byte[] bytes =
        String.format(null, "%02d", Long.valueOf((elapsedRealtime / 60) % 100)).getBytes();
    byte[] bytes2 = String.format(null, "%02d", Long.valueOf(elapsedRealtime % 60)).getBytes();
    bArr[0] = bytes[0];
    bArr[1] = bytes[1];
    bArr[2] = bytes2[0];
    bArr[3] = bytes2[1];
    return bArr;
  }

  public final void sendNfcFailIntentForFactoryMode(byte[] bArr) {
    if ((FactoryTest.isFactoryBinary() || FactoryTest.isRunningFactoryApp() || this.mTestMode == 42)
        && !this.mFactoryTransceiveResponseIntentSent
        && bArr != null
        && bArr.length > 2
        && bArr[0] == 105
        && bArr[1] == -123
        && bArr[2] == 0) {
      this.mFactoryTransceiveResponseIntentSent = true;
      this.mContext.sendBroadcastAsUser(
          new Intent("android.intent.action.NFC_LED_COVER_FPCB_DISCONNECT"), UserHandle.ALL);
    }
  }

  public final void notifyLedOffListeners(int i) {
    Bundle bundle = new Bundle();
    bundle.putInt("led_off_command", i);
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(0, bundle);
        }
      }
    }
  }

  public final void notifyPowerButtonPressListeners() {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo.type == 4) {
          nfcLedTouchListenerInfo.onSystemCoverEvent(1, null);
        }
      }
    }
  }

  public final int getListenerTypeForCommand(int i, byte[] bArr) {
    if (i == 2) {
      return 0;
    }
    if (i != 6) {
      if (i != 224 || bArr == null || bArr.length <= 0) {
        return -1;
      }
      byte b = bArr[0];
      return (b == 1 || b == 3) ? 0 : -1;
    }
    if (bArr == null || bArr.length <= 0) {
      return -1;
    }
    byte b2 = bArr[0];
    if (b2 == 1) {
      return 1;
    }
    if (b2 != 2) {
      return b2 != 3 ? -1 : 3;
    }
    return 2;
  }

  public class NfcLedTouchListenerInfo implements IBinder.DeathRecipient {
    public final ComponentName component;
    public final int pid;
    public final IBinder token;
    public final int type;
    public final int uid;

    public NfcLedTouchListenerInfo(
        IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
      this.token = iBinder;
      this.component = componentName;
      this.pid = i;
      this.uid = i2;
      this.type = i3;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.v(NfcLedCoverController.TAG, "binderDied : binder = " + this.token);
      synchronized (NfcLedCoverController.this.mListeners) {
        NfcLedCoverController.this.mListeners.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }

    public void onCoverTouchAccept() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(NfcLedCoverController.TAG, "null listener received TouchAccept!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchAccept();
        }
      } catch (RemoteException e) {
        Log.e(NfcLedCoverController.TAG, "Failed onCoverTouchAccept callback", e);
      }
    }

    public void onCoverTouchReject() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(NfcLedCoverController.TAG, "null listener received TouchReject!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchReject();
        }
      } catch (RemoteException e) {
        Log.e(NfcLedCoverController.TAG, "Failed onCoverTouchReject callback", e);
      }
    }

    public void onSystemCoverEvent(int i, Bundle bundle) {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(NfcLedCoverController.TAG, "null listener received TouchReject!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onSystemCoverEvent(i, bundle);
        }
      } catch (RemoteException e) {
        Log.e(NfcLedCoverController.TAG, "Failed onSystemCoverEvent callback", e);
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current NfcLedCoverController state:");
    printWriter.print("  mIsLEDCoverAttached=");
    printWriter.println(this.mIsLedCoverAttached);
    printWriter.println("  ");
    printWriter.println(" Current NFC Callback state:");
    synchronized (this.mListeners) {
      printWriter.println("  Live callbacks (" + this.mListeners.size() + "):");
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        NfcLedTouchListenerInfo nfcLedTouchListenerInfo = (NfcLedTouchListenerInfo) it.next();
        if (nfcLedTouchListenerInfo != null) {
          printWriter.println(
              "    "
                  + nfcLedTouchListenerInfo.component
                  + " (pid="
                  + nfcLedTouchListenerInfo.pid
                  + " uid="
                  + nfcLedTouchListenerInfo.uid
                  + " type="
                  + nfcLedTouchListenerInfo.type
                  + ")");
        }
      }
      printWriter.println("  ");
    }
  }

  public class Command {
    public int code;
    public byte[] data;

    public Command(int i, byte[] bArr) {
      this.code = i;
      this.data = bArr;
    }

    public String toString() {
      return "Command [code="
          + this.code
          + ", data="
          + NfcLedCoverController.this.getByteDataString(this.data)
          + "]";
    }
  }
}
