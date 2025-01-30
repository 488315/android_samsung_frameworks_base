package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class GracefulNfcLedCoverController extends BaseNfcLedCoverController {
  public static int TEST;
  public Object mCallTimerLock;
  public String mFirmwareVersion;
  public final NfcLedCoverControllerHandler mHandler;
  public boolean mIsLedOn;
  public long mLastWcControlResetTime;
  public int mLedCoverStartRetryCount;
  public int mLedCoverTransceiveRetryCount;
  public PowerManager.WakeLock mLedVersionCheckWakeLock;
  public ArrayList mListeners;
  public PowerManager.WakeLock mPollTouchWakeLock;
  public boolean mPollingTouchEvents;
  public PowerManager.WakeLock mSendLedDataWakeLock;
  public PowerManager.WakeLock mTouchResponseWakeLock;
  public int testCount;
  public static final String TAG =
      "CoverManager_" + GracefulNfcLedCoverController.class.getSimpleName();
  public static final byte[] VERSION_CHECK_COMMAND = {0, -94, 0, 0, 7, 7, 113, 0, 0, 0, -1, -1};

  public GracefulNfcLedCoverController(Looper looper, Context context) {
    super(looper, context);
    this.mIsLedOn = false;
    this.mCallTimerLock = new Object();
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
    PowerManager.WakeLock newWakeLock4 =
        this.mPowerManager.newWakeLock(1, "version check ledcover");
    this.mLedVersionCheckWakeLock = newWakeLock4;
    newWakeLock4.setReferenceCounted(false);
    TEST = Settings.Secure.getInt(context.getContentResolver(), "nfc_led_cover_test", 0);
  }

  public final class NfcLedCoverControllerHandler extends Handler {
    public NfcLedCoverControllerHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 0:
          GracefulNfcLedCoverController.this.clearRetryCountDelayedMsg();
          break;
        case 1:
        case 5:
        default:
          return;
        case 2:
          GracefulNfcLedCoverController.this.handlePollEventTouch(message.arg1);
          return;
        case 3:
          GracefulNfcLedCoverController.this.handleEventResponse(message.arg1, message.arg2);
          return;
        case 4:
          break;
        case 6:
          GracefulNfcLedCoverController.this.handleSendPowerKeyToCover();
          return;
        case 7:
          if (message.arg1 == 1) {
            return;
          }
          GracefulNfcLedCoverController.this.handleCoverDetachedLocked();
          return;
        case 8:
          GracefulNfcLedCoverController.this.handleAddLedNotification((Bundle) message.obj);
          return;
        case 9:
          GracefulNfcLedCoverController.this.handleRemoveLedNotification((Bundle) message.obj);
          return;
        case 10:
          GracefulNfcLedCoverController.this.handleCoverVersionCheck();
          return;
        case 11:
          GracefulNfcLedCoverController.this.handleLcdOffDisabledByCover((Bundle) message.obj);
          return;
      }
      GracefulNfcLedCoverController.this.handleSendDataToNfcLedCover(
          message.arg1, (byte[]) message.obj);
      if (GracefulNfcLedCoverController.this.mHandler.hasMessages(0)
          || GracefulNfcLedCoverController.this.mHandler.hasMessages(4)) {
        return;
      }
      GracefulNfcLedCoverController gracefulNfcLedCoverController =
          GracefulNfcLedCoverController.this;
      gracefulNfcLedCoverController.releaseWakeLockWithPermission(
          gracefulNfcLedCoverController.mSendLedDataWakeLock);
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void updateNfcLedCoverAttachStateLocked(boolean z, int i) {
    boolean z2 = z && i == 7;
    if (this.mIsLedCoverAttached != z2) {
      this.mIsLedCoverAttached = z2;
      if (!z2) {
        Log.d(TAG, "NfcLedCover detached, start clearing all flags, messages, wakelocks");
        Message obtainMessage = this.mHandler.obtainMessage(7);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessageAtFrontOfQueue(obtainMessage);
      } else if (FactoryTest.isFactoryBinary() && getNfcAdapter() != null) {
        acquireWakeLockWithPermission(this.mLedVersionCheckWakeLock);
        this.mHandler.sendEmptyMessage(10);
      }
    }
    TEST = Settings.Secure.getInt(this.mContext.getContentResolver(), "nfc_led_cover_test", 0);
  }

  public final void handleCoverDetachedLocked() {
    Log.d(TAG, "handleCoverDetached()");
    synchronized (this.mCallTimerLock) {
      this.mHandler.removeCallbacksAndMessages(null);
      releaseWakeLockWithPermission(this.mSendLedDataWakeLock);
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      releaseWakeLockWithPermission(this.mTouchResponseWakeLock);
      this.mPollingTouchEvents = false;
      clearRetryCountDelayedMsg();
      if (getNfcAdapter() != null) {
        stopLedCover();
      }
      this.mFirmwareVersion = null;
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void sendDataToNfcLedCover(int i, byte[] bArr) {
    if (!this.mIsLedCoverAttached && !FactoryTest.isFactoryBinary()) {
      Log.d(TAG, "sendDataToLedCover : Not attached LED Cover");
      return;
    }
    if (this.mNfcAdapter == null && getNfcAdapter() == null) {
      Log.d(TAG, "sendDataToLedCover : Nfc Service not available");
      return;
    }
    acquireWakeLockWithPermission(this.mSendLedDataWakeLock);
    Message obtain = Message.obtain();
    obtain.arg1 = i;
    obtain.obj = bArr;
    obtain.what = 0;
    clearRetryCountDelayedMsg();
    this.mHandler.sendMessage(obtain);
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
      Message obtainMessage = this.mHandler.obtainMessage(11);
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
    this.mHandler.sendEmptyMessage(6);
  }

  public final void handleSendPowerKeyToCover() {
    notifyPowerButtonPressListeners();
  }

  public final boolean tryStartLedCover(Intent intent) {
    String str = TAG;
    Log.d(str, "Trying to start NFC LED Cover mIsLedOn=" + String.valueOf(this.mIsLedOn));
    if (!this.mIsLedOn) {
      if (this.mLedCoverStartRetryCount == 0) {
        this.mLastWcControlResetTime = System.currentTimeMillis();
        this.mNfcAdapter.semSetWirelessChargeEnabled(false);
      }
      byte[] semStartLedCoverMode = this.mNfcAdapter.semStartLedCoverMode();
      intent.putExtra("start_result", semStartLedCoverMode);
      if (isValidCoverStartData(semStartLedCoverMode)) {
        Log.d(str, "Started NFC LED Cover");
        this.mLedCoverStartRetryCount = 0;
        this.mIsLedOn = true;
      } else {
        Log.w(str, "Failed to start NFC LED Cover");
        return false;
      }
    } else {
      Log.d(str, "NFC LED Cover already started");
    }
    return true;
  }

  public final void handleSendDataToNfcLedCover(int i, byte[] bArr) {
    byte[] bArr2;
    synchronized (this.mCallTimerLock) {
      Intent intent = new Intent("android.intent.action.NFC_LED_COVER_RETURN_VALUE");
      byte[] buildNfcCoverLedData = buildNfcCoverLedData(i, bArr);
      if (buildNfcCoverLedData == null) {
        Log.e(TAG, "Invalid data");
        return;
      }
      if (i == 18 && !this.mIsLedOn) {
        this.mNfcAdapter.semSetWirelessChargeEnabled(true);
        Log.d(TAG, "Led cover already off");
        return;
      }
      String str = TAG;
      Log.d(str, "Ensuring NFC LED Cover started");
      boolean tryStartLedCover = tryStartLedCover(intent);
      intent.putExtra("command_data", buildNfcCoverLedData);
      intent.putExtra("start_success", tryStartLedCover);
      if (!tryStartLedCover) {
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
          this.mLedCoverStartRetryCount++;
          this.mNfcAdapter.semStopLedCoverMode();
        } else {
          Log.e(str, "Could not start NFC LED Cover");
          this.mLedCoverStartRetryCount = 0;
          stopLedCover();
        }
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        return;
      }
      this.mLedCoverStartRetryCount = 0;
      try {
        resetWcControlTimer();
        bArr2 = this.mNfcAdapter.semTransceiveDataWithLedCover(buildNfcCoverLedData);
      } catch (Exception e) {
        Log.e(TAG, "Error in trancieve command", e);
        bArr2 = null;
      }
      intent.putExtra("transceive_result", bArr2);
      if (!isValidResponse(buildNfcCoverLedData, bArr2)) {
        String str2 = TAG;
        Log.e(str2, "Error parsing response for command " + i + ": " + getByteDataString(bArr2));
        handleInvalidCommand(i, bArr, bArr2);
        intent.putExtra("transceive_success", false);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (TEST == 0) {
          return;
        } else {
          Log.d(str2, "TEST mode enabled, ignore NFC Led Cover response");
        }
      } else {
        intent.putExtra("transceive_success", true);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
      }
      String str3 = TAG;
      Log.d(str3, "Done, sucess");
      this.mLedCoverTransceiveRetryCount = 0;
      if (i != 2 && i != 6) {
        if (i != 18) {
          if (i != 112) {
            this.mPollingTouchEvents = false;
            this.mHandler.removeMessages(2);
            releaseWakeLockWithPermission(this.mPollTouchWakeLock);
            return;
          }
        }
        stopLedCover();
        this.mPollingTouchEvents = false;
        this.mHandler.removeMessages(2);
        releaseWakeLockWithPermission(this.mPollTouchWakeLock);
        return;
      }
      if (TEST > 0) {
        this.testCount = 0;
      }
      Message obtain2 = Message.obtain();
      obtain2.what = 2;
      int listenerTypeForCommand = getListenerTypeForCommand(i, buildNfcCoverLedData);
      if (listenerTypeForCommand >= 0) {
        acquireWakeLockWithPermission(this.mPollTouchWakeLock);
        obtain2.arg1 = listenerTypeForCommand;
        if (this.mPollingTouchEvents) {
          Log.e(str3, "Already polling for touch events");
          this.mHandler.removeMessages(2);
        } else {
          this.mPollingTouchEvents = true;
        }
        this.mHandler.sendMessageDelayed(obtain2, 100L);
        return;
      }
      if (i == 112 && buildNfcCoverLedData.length > 8 && buildNfcCoverLedData[8] == 2) {
        Log.i(str3, "Factory Mode Full LED off was sent, so turn off LedCover");
        stopLedCover();
        this.mPollingTouchEvents = false;
        this.mHandler.removeMessages(2);
        releaseWakeLockWithPermission(this.mPollTouchWakeLock);
        return;
      }
      Log.e(str3, "Wrong listener type requested for command:" + i + "; return");
    }
  }

  public final boolean isValidCoverStartData(byte[] bArr) {
    return bArr != null && bArr.length > 1;
  }

  public final void handleInvalidCommand(int i, byte[] bArr, byte[] bArr2) {
    if (bArr2 != null && i != 18 && bArr2.length == 1) {
      byte b = bArr2[0];
      if (b != -32 && b != 1 && b != 3 && b != 5 && b != 6) {
        switch (b) {
          case -80:
          case -79:
          case -78:
            break;
          default:
            Log.e(TAG, "Transceive error - unknown error value: " + ((int) bArr2[0]));
            break;
        }
      }
      if (this.mLedCoverTransceiveRetryCount < 13) {
        Log.e(TAG, "Repeat command " + i + " count: " + this.mLedCoverTransceiveRetryCount);
        this.mIsLedOn = false;
        this.mNfcAdapter.semStopLedCoverMode();
        Message obtain = Message.obtain();
        obtain.what = 4;
        obtain.arg1 = i;
        obtain.obj = bArr;
        this.mHandler.sendMessageDelayed(obtain, 500L);
        this.mLedCoverTransceiveRetryCount++;
        return;
      }
      this.mLedCoverTransceiveRetryCount = 0;
      Log.e(TAG, "Could not transceive command to cover so turn off led cover");
    }
    Log.d(TAG, "Sent done intent, fail transceive");
    stopLedCover();
    this.mPollingTouchEvents = false;
    this.mHandler.removeMessages(2);
    releaseWakeLockWithPermission(this.mPollTouchWakeLock);
  }

  public final byte[] buildNfcCoverLedData(int i, byte[] bArr) {
    if (bArr == null) {
      bArr = new byte[] {0, 0};
    }
    if (bArr.length < 2) {
      bArr = new byte[] {0, bArr[0]};
    }
    boolean z = bArr.length > 3;
    int lenByteValue = getLenByteValue(bArr);
    if (z) {
      lenByteValue = bArr[0] & 255;
    }
    int i2 = 5 + lenByteValue;
    byte[] bArr2 = new byte[i2];
    if (i2 < bArr.length + (z ? 5 : 7)) {
      Log.e(
          TAG,
          "buildNfcCoverLedData : Invalid genData length data.length="
              + String.valueOf(bArr.length)
              + " genData.length="
              + String.valueOf(i2));
      return null;
    }
    bArr2[0] = 0;
    bArr2[1] = -94;
    bArr2[2] = 0;
    bArr2[3] = 0;
    byte b = (byte) lenByteValue;
    bArr2[4] = b;
    if (z) {
      System.arraycopy(bArr, 0, bArr2, 5, bArr.length);
    } else {
      bArr2[5] = b;
      bArr2[6] = (byte) i;
      System.arraycopy(bArr, 0, bArr2, 7, bArr.length);
      bArr2[i2 - 2] = -1;
      bArr2[i2 - 1] = -1;
    }
    return bArr2;
  }

  public final int getLenByteValue(byte[] bArr) {
    if (bArr != null) {
      return bArr.length + 4;
    }
    return 6;
  }

  public final void handlePollEventTouch(int i) {
    if (!this.mPollingTouchEvents) {
      releaseWakeLockWithPermission(this.mPollTouchWakeLock);
      return;
    }
    if (TEST > 0) {
      this.testCount++;
    }
    byte[] buildNfcCoverLedData = buildNfcCoverLedData(17, new byte[] {0, 0});
    if (buildNfcCoverLedData == null) {
      Log.e(TAG, "Invalid data");
      return;
    }
    byte[] bArr = null;
    try {
      if (TEST == 0) {
        resetWcControlTimer();
        bArr = this.mNfcAdapter.semTransceiveDataWithLedCover(buildNfcCoverLedData);
      }
    } catch (Exception e) {
      Log.e(TAG, "Error sending data to NFC", e);
    }
    int i2 = TEST;
    if (i2 > 0 && this.testCount > 19) {
      byte[] bArr2 = new byte[6];
      bArr2[0] = 6;
      bArr2[1] = 17;
      bArr2[2] = 0;
      if (i2 == 2) {
        bArr2[3] = 1;
      } else if (i2 == 1) {
        bArr2[3] = 2;
      } else {
        Log.e(TAG, "Unknown test value: " + TEST + ", reject by default");
        bArr2[3] = 2;
      }
      bArr2[4] = -1;
      bArr2[5] = -1;
      bArr = bArr2;
    }
    if (isFinishedTouchReply(bArr)) {
      acquireWakeLockWithPermission(this.mTouchResponseWakeLock);
      Message obtain = Message.obtain();
      obtain.what = 3;
      obtain.arg1 = i;
      obtain.arg2 = bArr[3];
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

  public final boolean isValidResponse(byte[] bArr, byte[] bArr2) {
    if (bArr2 == null || bArr2.length < bArr.length - 5) {
      return false;
    }
    for (int i = 0; i < bArr.length - 5; i++) {
      if (bArr2[i] != bArr[i + 5]) {
        return false;
      }
    }
    return true;
  }

  public final boolean isFinishedTouchReply(byte[] bArr) {
    byte b;
    return bArr != null && bArr.length >= 6 && bArr[1] == 17 && ((b = bArr[3]) == 1 || b == 2);
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
    if (FactoryTest.isFactoryBinary()) {
      acquireWakeLockWithPermission(this.mLedVersionCheckWakeLock);
      this.mHandler.sendEmptyMessageDelayed(10, 500L);
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

  public final void handleCoverVersionCheck() {
    if (this.mFirmwareVersion != null) {
      Log.e(TAG, "Firmware version already retrieved: " + this.mFirmwareVersion);
      releaseWakeLockWithPermission(this.mLedVersionCheckWakeLock);
      return;
    }
    if (this.mIsLedOn) {
      Log.d(TAG, "Led is on, try checking version later");
      releaseWakeLockWithPermission(this.mLedVersionCheckWakeLock);
      return;
    }
    if (!isValidCoverStartData(this.mNfcAdapter.semStartLedCoverMode())) {
      this.mNfcAdapter.semStopLedCoverMode();
      if (!this.mHandler.hasMessages(4)) {
        this.mNfcAdapter.semSetWirelessChargeEnabled(true);
      }
      releaseWakeLockWithPermission(this.mLedVersionCheckWakeLock);
      return;
    }
    if (transceiveVersionCheck()) {
      Log.e(TAG, "Firmware version retrieved: " + this.mFirmwareVersion);
      Settings.Secure.putString(
          this.mContext.getContentResolver(), "led_cover_firmware_version", this.mFirmwareVersion);
    }
    this.mNfcAdapter.semStopLedCoverMode();
    if (!this.mHandler.hasMessages(4)) {
      this.mNfcAdapter.semSetWirelessChargeEnabled(true);
    }
    releaseWakeLockWithPermission(this.mLedVersionCheckWakeLock);
  }

  public final boolean transceiveVersionCheck() {
    NfcAdapter nfcAdapter = this.mNfcAdapter;
    byte[] bArr = VERSION_CHECK_COMMAND;
    byte[] semTransceiveDataWithLedCover = nfcAdapter.semTransceiveDataWithLedCover(bArr);
    Log.d(TAG, "Verison check response: " + getByteDataString(semTransceiveDataWithLedCover));
    boolean z = false;
    if (semTransceiveDataWithLedCover != null
        && semTransceiveDataWithLedCover.length >= bArr.length - 5
        && semTransceiveDataWithLedCover[0] == bArr[5]
        && semTransceiveDataWithLedCover[1] == bArr[6]
        && semTransceiveDataWithLedCover[2] == bArr[7]
        && semTransceiveDataWithLedCover[5] == bArr[10]
        && semTransceiveDataWithLedCover[6] == bArr[11]) {
      z = true;
    }
    if (z) {
      this.mFirmwareVersion =
          String.format(
              "%02X %02X",
              Byte.valueOf(semTransceiveDataWithLedCover[3]),
              Byte.valueOf(semTransceiveDataWithLedCover[4]));
    }
    return z;
  }

  public final int getListenerTypeForCommand(int i, byte[] bArr) {
    byte b;
    if (i == 2) {
      return 0;
    }
    if (i != 6) {
      if (i == 112 && bArr != null && bArr.length > 8 && ((b = bArr[8]) == 1 || b == 3)) {
        return 0;
      }
    } else if (bArr != null && bArr.length > 8) {
      byte b2 = bArr[8];
      if (b2 == 1) {
        return 1;
      }
      if (b2 == 2) {
        return 2;
      }
      if (b2 == 3) {
        return 3;
      }
    }
    return -1;
  }

  public final void resetWcControlTimer() {
    long currentTimeMillis = System.currentTimeMillis();
    if (currentTimeMillis - this.mLastWcControlResetTime > 30000) {
      this.mNfcAdapter.semSetWirelessChargeEnabled(false);
      this.mLastWcControlResetTime = currentTimeMillis;
    }
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
      Log.v(GracefulNfcLedCoverController.TAG, "binderDied : binder = " + this.token);
      synchronized (GracefulNfcLedCoverController.this.mListeners) {
        GracefulNfcLedCoverController.this.mListeners.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }

    public void onCoverTouchAccept() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(GracefulNfcLedCoverController.TAG, "null listener received TouchAccept!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchAccept();
        }
      } catch (RemoteException e) {
        Log.e(GracefulNfcLedCoverController.TAG, "Failed onCoverTouchAccept callback", e);
      }
    }

    public void onCoverTouchReject() {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(GracefulNfcLedCoverController.TAG, "null listener received TouchReject!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCoverTouchReject();
        }
      } catch (RemoteException e) {
        Log.e(GracefulNfcLedCoverController.TAG, "Failed onCoverTouchReject callback", e);
      }
    }

    public void onSystemCoverEvent(int i, Bundle bundle) {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Log.w(GracefulNfcLedCoverController.TAG, "null listener received TouchReject!");
        return;
      }
      try {
        INfcLedCoverTouchListenerCallback asInterface =
            INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onSystemCoverEvent(i, bundle);
        }
      } catch (RemoteException e) {
        Log.e(GracefulNfcLedCoverController.TAG, "Failed onSystemCoverEvent callback", e);
      }
    }
  }

  @Override // com.android.server.sepunion.cover.BaseNfcLedCoverController
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current NfcLedCoverController state:");
    printWriter.print("  mIsLEDCoverAttached=");
    printWriter.println(this.mIsLedCoverAttached);
    printWriter.print("  TEST=");
    printWriter.println(TEST);
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
}
