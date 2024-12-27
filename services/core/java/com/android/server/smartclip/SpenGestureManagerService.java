package com.android.server.smartclip;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.inputmethod.EditorInfo;
import com.android.internal.content.PackageMonitor;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;
import com.android.server.smartclip.SmartClipRemoteRequestSyncManager;
import com.android.server.smartclip.SpenGestureScreenShotManager;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.content.smartclip.IAirGestureListener;
import com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener;
import com.samsung.android.content.smartclip.IInputMethodInfoChangeListener;
import com.samsung.android.content.smartclip.ISpenGestureHoverListener;
import com.samsung.android.content.smartclip.ISpenGestureService;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;
import com.samsung.android.content.smartclip.SmartClipDataExtractionEvent;
import com.samsung.android.content.smartclip.SmartClipDataExtractionResponse;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestResult;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public final class SpenGestureManagerService extends ISpenGestureService.Stub {
    public static Context mContext;
    public int ALLOWANCE_HOVER_TIME;
    public int ALLOWANCE_RANGE_X;
    public int ALLOWANCE_RANGE_Y;
    public final boolean mAcAutoFloatingIconMode;
    public long mAcButtonPressTouchDownTime;
    public boolean mAcIsBound;
    public boolean mAcIsFloatingIconEnabled;
    public boolean mAcIsHoverOccuredBeforeTouchDown;
    public boolean mAcIsPenButtonPressed;
    public boolean mAcIsScreenOffMemoShowing;
    public boolean mAcIsTouchDowned;
    public long mAcLastStartTime;
    public long mAcPenButtonPressedTime;
    public PenDetectionInfo mAcPendingPenDetectionInfo;
    public Messenger mAcService;
    public final AnonymousClass6 mAcServiceConnection;
    public RemoteCallbackList mAirGestureListener;
    public int mBatteryOnlineStatus;
    public RemoteCallbackList mBleSpenChargeLockStateChangedListeners;
    public final BleSpenManager mBleSpenManager;
    public boolean mBootComplete;
    public final AnonymousClass1 mBroadcastReceiver;
    public final boolean mClearCoverOpened;
    public final Object mDataExtractionSync;
    public EditorInfo mEditorInfo;
    public final GestureDetector mGestureDetector;
    public final AnonymousClass5 mHandler;
    public RemoteCallbackList mHoverListeners;
    public final boolean mInboxSPen;
    public IRemoteInputConnection mInputConnection;
    public RemoteCallbackList mInputInfoChangeListeners;
    public final InputManager mInputManager;
    public boolean mIsEnableLockScreenQuickNote;
    public Boolean mIsPenInserted;
    public Boolean mIsPenReversed;
    public Point mLastDoubleTapPosition;
    public long mLastScreenOffDoubleTapTime;
    public int mMissingMethodFlags;
    public final List mPenDataStructArray;
    public final AnonymousClass4 mPenDoubleTap;
    public int mScreenOffReason;
    public final SemInputDeviceManager mSemInputDeviceManager;
    public final SmartClipRemoteRequestSyncManager mSmartClipRemoteRequestSyncManager;
    public long mSpenUsingStartTime;
    public final int mSpenUspLevel;
    public final SpenThemeManager mThemeManager;

    /* renamed from: com.android.server.smartclip.SpenGestureManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends PackageMonitor {
        public final void onPackageDataCleared(String str, int i) {
            if ("com.samsung.android.service.aircommand".equals(str)) {
                Settings.System.putInt(SpenGestureManagerService.mContext.getContentResolver(), "air_cmd_mode", 0);
            }
        }
    }

    public final class AcSettingsObserver extends ContentObserver {
        public AcSettingsObserver(AnonymousClass5 anonymousClass5) {
            super(anonymousClass5);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
            boolean z2 = false;
            if (Settings.System.semGetIntForUser(SpenGestureManagerService.mContext.getContentResolver(), "air_cmd_use_minimized", 0, -2) != 0 && Settings.System.getInt(SpenGestureManagerService.mContext.getContentResolver(), "air_button_onoff", 0) == 1) {
                z2 = true;
            }
            SpenGestureManagerService.m890$$Nest$monFloatingIconSettingChanged(spenGestureManagerService, z2);
        }
    }

    public final class PenDataStruct {
        public long EnterTime;
        public int RawX;
        public int RawY;
    }

    public final class PenDetectionInfo {
        public int action;
        public long eventTime;
        public String penName;
    }

    public final class SPenGestureInputEventReceiver implements WindowManagerPolicyConstants.PointerEventListener {
        public ICustomFrequencyManager sCfmsService = null;

        public SPenGestureInputEventReceiver() {
        }

        public final void onPointerEvent(MotionEvent motionEvent) {
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            try {
                int action = motionEvent.getAction();
                int buttonState = motionEvent.getButtonState();
                Context context = SpenGestureManagerService.mContext;
                boolean z = (buttonState & 32) != 0;
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                if (spenGestureManagerService.mSpenUspLevel >= 3) {
                    SpenGestureManagerService.m891$$Nest$mprocessMotionEventForAirCMD(spenGestureManagerService, motionEvent, action, z);
                    RemoteCallbackList remoteCallbackList = SpenGestureManagerService.this.mHoverListeners;
                    if (remoteCallbackList != null && remoteCallbackList.getRegisteredCallbackCount() > 0) {
                        SpenGestureManagerService.m889$$Nest$mcheckHoverStay(SpenGestureManagerService.this, motionEvent, action);
                    }
                    if (z && action != 7 && action != 2) {
                        Log.i("SpenGestureManagerService", "onPointerEvent 4 : Action=" + MotionEvent.actionToString(action) + ", sideButtonPressed=" + z + ", coverOpened=" + SpenGestureManagerService.this.mClearCoverOpened);
                    }
                    boolean z2 = z && SpenGestureManagerService.this.mClearCoverOpened;
                    if (action == 9) {
                        sendEventToCfmsService(action);
                        Message message = new Message();
                        message.what = 12315;
                        sendMessage(message);
                    } else if (action == 10) {
                        sendEventToCfmsService(action);
                        Message message2 = new Message();
                        message2.what = 12312;
                        sendMessageDelayed(message2, 50L);
                    } else if (action == 0) {
                        if (hasMessages(12312)) {
                            removeMessages(12312);
                            Log.i("SpenGestureManagerService", "[HOVER] sending hover exit br is canceled by touch event.");
                        }
                        if (motionEvent.getToolType(0) == 2) {
                            sendEventToCfmsService(action);
                        }
                    }
                    if (z2) {
                        switch (action) {
                            case 0:
                            case 2:
                            case 5:
                                SpenGestureManagerService.this.getClass();
                                if (!"sec_unused_e-pen".equals(SpenGestureManagerService.getPenName(motionEvent))) {
                                    SpenGestureManagerService.this.mGestureDetector.onTouchEvent(motionEvent);
                                    break;
                                } else {
                                    break;
                                }
                            case 1:
                            case 3:
                            case 6:
                                SpenGestureManagerService.this.getClass();
                                if (!"sec_unused_e-pen".equals(SpenGestureManagerService.getPenName(motionEvent))) {
                                    SpenGestureManagerService.this.mGestureDetector.onTouchEvent(motionEvent);
                                    break;
                                } else {
                                    break;
                                }
                            case 4:
                            case 8:
                            default:
                                Log.d("SpenGestureManagerService", "onPointerEvent : default");
                                break;
                            case 7:
                            case 9:
                            case 10:
                                break;
                        }
                    }
                }
                long elapsedRealtimeNanos2 = SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos;
                if (elapsedRealtimeNanos2 > 1000000000) {
                    Log.e("SpenGestureManagerService", "onPointerEvent : total time = " + elapsedRealtimeNanos2);
                }
            } catch (Throwable th) {
                long elapsedRealtimeNanos3 = SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos;
                if (elapsedRealtimeNanos3 > 1000000000) {
                    Context context2 = SpenGestureManagerService.mContext;
                    Log.e("SpenGestureManagerService", "onPointerEvent : total time = " + elapsedRealtimeNanos3);
                }
                throw th;
            }
        }

        public final void sendEventToCfmsService(int i) {
            IBinder service;
            try {
                if (this.sCfmsService == null && (service = ServiceManager.getService("CustomFrequencyManagerService")) != null) {
                    this.sCfmsService = ICustomFrequencyManager.Stub.asInterface(service);
                }
                ICustomFrequencyManager iCustomFrequencyManager = this.sCfmsService;
                if (iCustomFrequencyManager != null) {
                    if (i == 9) {
                        iCustomFrequencyManager.sendCommandToSSRM("HOVERING_EVENT", "1");
                    } else if (i == 10) {
                        iCustomFrequencyManager.sendCommandToSSRM("HOVERING_EVENT", "0");
                    } else if (i == 0) {
                        iCustomFrequencyManager.sendCommandToSSRM("HOVERING_EVENT", "3");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: -$$Nest$mbroastcastHoverEvent, reason: not valid java name */
    public static void m888$$Nest$mbroastcastHoverEvent(SpenGestureManagerService spenGestureManagerService, int i) {
        RemoteCallbackList remoteCallbackList = spenGestureManagerService.mHoverListeners;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            if (i == 9) {
                try {
                    spenGestureManagerService.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverEnter();
                } catch (RemoteException unused) {
                }
            } else if (i == 10) {
                spenGestureManagerService.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverExit();
            }
        }
        spenGestureManagerService.mHoverListeners.finishBroadcast();
        Log.i("SpenGestureManagerService", "mHoverListeners.getRegisteredCallbackCount() = " + spenGestureManagerService.mHoverListeners.getRegisteredCallbackCount());
    }

    /* renamed from: -$$Nest$mcheckHoverStay, reason: not valid java name */
    public static void m889$$Nest$mcheckHoverStay(SpenGestureManagerService spenGestureManagerService, MotionEvent motionEvent, int i) {
        spenGestureManagerService.getClass();
        if (motionEvent.getToolType(0) != 2) {
            return;
        }
        if (i != 7) {
            ((ArrayList) spenGestureManagerService.mPenDataStructArray).clear();
            return;
        }
        PenDataStruct penDataStruct = new PenDataStruct();
        penDataStruct.RawX = (int) motionEvent.getRawX();
        penDataStruct.RawY = (int) motionEvent.getRawY();
        penDataStruct.EnterTime = System.currentTimeMillis();
        ((ArrayList) spenGestureManagerService.mPenDataStructArray).add(penDataStruct);
        int size = ((ArrayList) spenGestureManagerService.mPenDataStructArray).size() - 2;
        while (size >= 0) {
            int i2 = size - 1;
            PenDataStruct penDataStruct2 = (PenDataStruct) ((ArrayList) spenGestureManagerService.mPenDataStructArray).get(size);
            int i3 = penDataStruct2.RawX;
            int i4 = penDataStruct.RawX;
            int i5 = spenGestureManagerService.ALLOWANCE_RANGE_X;
            if (i3 <= i4 - i5 || i3 >= i4 + i5) {
                return;
            }
            int i6 = penDataStruct2.RawY;
            int i7 = penDataStruct.RawY;
            int i8 = spenGestureManagerService.ALLOWANCE_RANGE_Y;
            if (i6 <= i7 - i8 || i6 >= i7 + i8) {
                return;
            }
            if (penDataStruct2.EnterTime < penDataStruct.EnterTime - spenGestureManagerService.ALLOWANCE_HOVER_TIME) {
                ((ArrayList) spenGestureManagerService.mPenDataStructArray).clear();
                Bundle bundle = new Bundle();
                bundle.putInt("RawX", penDataStruct.RawX);
                bundle.putInt("RawY", penDataStruct.RawY);
                StringBuilder sb = new StringBuilder("dictionary service start : hover time = ");
                sb.append(spenGestureManagerService.ALLOWANCE_HOVER_TIME);
                sb.append(" x = ");
                sb.append(spenGestureManagerService.ALLOWANCE_RANGE_X);
                sb.append(" y = ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, spenGestureManagerService.ALLOWANCE_RANGE_Y, "SpenGestureManagerService");
                int i9 = penDataStruct.RawX;
                int i10 = penDataStruct.RawY;
                RemoteCallbackList remoteCallbackList = spenGestureManagerService.mHoverListeners;
                if (remoteCallbackList == null) {
                    return;
                }
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                while (beginBroadcast > 0) {
                    beginBroadcast--;
                    try {
                        spenGestureManagerService.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverStay(i9, i10);
                    } catch (RemoteException unused) {
                    }
                }
                spenGestureManagerService.mHoverListeners.finishBroadcast();
                return;
            }
            size = i2;
        }
    }

    /* renamed from: -$$Nest$monFloatingIconSettingChanged, reason: not valid java name */
    public static void m890$$Nest$monFloatingIconSettingChanged(SpenGestureManagerService spenGestureManagerService, boolean z) {
        spenGestureManagerService.getClass();
        Log.i("SpenGestureManagerService", "onFloatingIconSettingChanged : " + z);
        if (!z) {
            spenGestureManagerService.mAcIsFloatingIconEnabled = false;
            return;
        }
        spenGestureManagerService.mAcIsFloatingIconEnabled = true;
        if (true == spenGestureManagerService.mBootComplete || true == SpenGarageSpecManager.getInstance().mIsBundledSpenSupported) {
            Bundle bundle = new Bundle();
            bundle.putString("cause", "floating_on");
            spenGestureManagerService.startAirCommandUiService(bundle);
        }
    }

    /* renamed from: -$$Nest$mprocessMotionEventForAirCMD, reason: not valid java name */
    public static void m891$$Nest$mprocessMotionEventForAirCMD(SpenGestureManagerService spenGestureManagerService, MotionEvent motionEvent, int i, boolean z) {
        InputManager inputManager;
        spenGestureManagerService.getClass();
        boolean z2 = false;
        if (motionEvent.getToolType(0) != 2) {
            return;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 5) {
                    if (i != 6) {
                        if (i != 7) {
                            if (i != 9) {
                                if (i == 10 && spenGestureManagerService.mAcAutoFloatingIconMode && !spenGestureManagerService.mAcIsScreenOffMemoShowing && (inputManager = spenGestureManagerService.mInputManager) != null && inputManager.semGetScanCodeState(-1, -256, 320) == 0) {
                                    if (spenGestureManagerService.mHandler.hasMessages(12313)) {
                                        spenGestureManagerService.mHandler.removeMessages(12313);
                                    }
                                    spenGestureManagerService.dispatchPenDetectionInfo(i, motionEvent.getEventTime(), 0L, getPenName(motionEvent));
                                    spenGestureManagerService.mAcIsHoverOccuredBeforeTouchDown = false;
                                    return;
                                }
                                return;
                            }
                            if (spenGestureManagerService.mAcAutoFloatingIconMode && !spenGestureManagerService.mAcIsScreenOffMemoShowing && motionEvent.getDownTime() <= 0) {
                                spenGestureManagerService.mAcIsHoverOccuredBeforeTouchDown = true;
                                spenGestureManagerService.dispatchPenDetectionInfo(i, motionEvent.getEventTime(), 100L, getPenName(motionEvent));
                            }
                            spenGestureManagerService.mAcIsPenButtonPressed = false;
                            long j = spenGestureManagerService.mAcButtonPressTouchDownTime;
                            if (j > 0 && j == motionEvent.getDownTime()) {
                                z2 = true;
                            }
                            spenGestureManagerService.mAcIsTouchDowned = z2;
                            return;
                        }
                        if (spenGestureManagerService.mAcButtonPressTouchDownTime > 0 && !z) {
                            spenGestureManagerService.mAcIsTouchDowned = false;
                        }
                        if (spenGestureManagerService.mAcIsTouchDowned) {
                            return;
                        }
                        if (z && !spenGestureManagerService.mAcIsPenButtonPressed) {
                            spenGestureManagerService.mAcIsPenButtonPressed = true;
                            spenGestureManagerService.mAcPenButtonPressedTime = motionEvent.getEventTime();
                            return;
                        }
                        if (z || !spenGestureManagerService.mAcIsPenButtonPressed) {
                            return;
                        }
                        spenGestureManagerService.mAcIsPenButtonPressed = false;
                        long eventTime = motionEvent.getEventTime() - spenGestureManagerService.mAcPenButtonPressedTime;
                        if (eventTime <= 0 || eventTime > 800) {
                            return;
                        }
                        int rawX = (int) motionEvent.getRawX();
                        int rawY = (int) motionEvent.getRawY();
                        String str = rawX < mContext.getResources().getDisplayMetrics().widthPixels / 2 ? "left" : "right";
                        if (!spenGestureManagerService.mClearCoverOpened) {
                            Log.i("SpenGestureManagerService", "Can not start AirCommandUiService. #2");
                            return;
                        }
                        long uptimeMillis = SystemClock.uptimeMillis();
                        long j2 = uptimeMillis - spenGestureManagerService.mAcLastStartTime;
                        if (j2 > 0 && j2 < 400) {
                            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m("Can not start AirCommandUiService. #3.", j2, "SpenGestureManagerService");
                            return;
                        }
                        spenGestureManagerService.mAcLastStartTime = uptimeMillis;
                        Log.i("SpenGestureManagerService", "Start AirCommandUiService. #2");
                        Bundle bundle = new Bundle();
                        bundle.putString("cause", "button_pressed");
                        bundle.putString("direction", str);
                        bundle.putInt("button_press_x", rawX);
                        bundle.putInt("button_press_y", rawY);
                        spenGestureManagerService.startAirCommandUiService(bundle);
                        return;
                    }
                }
            }
            if (z && spenGestureManagerService.mAcButtonPressTouchDownTime == -1) {
                spenGestureManagerService.mAcButtonPressTouchDownTime = motionEvent.getDownTime();
                return;
            }
            return;
        }
        if (z) {
            spenGestureManagerService.mAcButtonPressTouchDownTime = motionEvent.getDownTime();
        } else {
            spenGestureManagerService.mAcButtonPressTouchDownTime = -1L;
        }
        if (!spenGestureManagerService.mAcAutoFloatingIconMode || spenGestureManagerService.mAcIsScreenOffMemoShowing || spenGestureManagerService.mAcIsHoverOccuredBeforeTouchDown) {
            return;
        }
        spenGestureManagerService.dispatchPenDetectionInfo(i, motionEvent.getEventTime(), 100L, getPenName(motionEvent));
    }

    /* renamed from: -$$Nest$msendDefferedPenDetectionInfo, reason: not valid java name */
    public static void m892$$Nest$msendDefferedPenDetectionInfo(SpenGestureManagerService spenGestureManagerService) {
        if (spenGestureManagerService.mAcPendingPenDetectionInfo == null) {
            return;
        }
        Log.i("SpenGestureManagerService", "sendDefferedPenDetectionInfo." + spenGestureManagerService.mAcPendingPenDetectionInfo.action);
        Bundle bundle = new Bundle();
        bundle.putLong("eventTime", spenGestureManagerService.mAcPendingPenDetectionInfo.eventTime);
        bundle.putString("penName", spenGestureManagerService.mAcPendingPenDetectionInfo.penName);
        Message obtain = Message.obtain(null, 1, spenGestureManagerService.mAcPendingPenDetectionInfo.action, 0);
        obtain.setData(bundle);
        try {
            Messenger messenger = spenGestureManagerService.mAcService;
            if (messenger != null) {
                messenger.send(obtain);
            }
        } catch (RemoteException e) {
            Log.e("SpenGestureManagerService", "Failed to send the pen detection info", e);
        }
        spenGestureManagerService.mAcPendingPenDetectionInfo = null;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.smartclip.SpenGestureManagerService$4] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.smartclip.SpenGestureManagerService$5] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.smartclip.SpenGestureManagerService$6] */
    public SpenGestureManagerService(Context context) {
        this.mInputManager = null;
        this.mIsEnableLockScreenQuickNote = false;
        this.mDataExtractionSync = new Object();
        this.mAcService = null;
        this.mAcIsBound = false;
        this.mAcIsHoverOccuredBeforeTouchDown = false;
        this.mAcIsPenButtonPressed = false;
        this.mAcIsTouchDowned = false;
        this.mAcIsFloatingIconEnabled = false;
        this.mAcIsScreenOffMemoShowing = false;
        this.mAcAutoFloatingIconMode = true;
        this.mAcButtonPressTouchDownTime = 0L;
        this.mAcPenButtonPressedTime = 0L;
        this.mAcLastStartTime = 0L;
        this.mBatteryOnlineStatus = 1;
        this.mAcPendingPenDetectionInfo = null;
        this.mClearCoverOpened = true;
        this.mInboxSPen = false;
        this.mSpenUspLevel = -1;
        this.mScreenOffReason = -1;
        this.mSpenUsingStartTime = 0L;
        this.mSmartClipRemoteRequestSyncManager = new SmartClipRemoteRequestSyncManager();
        this.mBootComplete = false;
        this.mLastScreenOffDoubleTapTime = 0L;
        new BroadcastReceiver() { // from class: com.android.server.smartclip.SpenGestureManagerService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String str;
                boolean z;
                boolean z2;
                String action = intent.getAction();
                if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    SpenGestureManagerService.this.mBatteryOnlineStatus = intent.getIntExtra("online", 1);
                    return;
                }
                Context context3 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "Receive broadcast : " + action);
                if (!"com.samsung.pen.INSERT".equals(action)) {
                    if ("com.sec.android.intent.action.BLACK_MEMO".equals(action)) {
                        try {
                            str = intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        } catch (Exception unused) {
                            str = null;
                        }
                        SpenGestureManagerService.this.mAcIsScreenOffMemoShowing = KnoxCustomManagerService.SHOW.equals(str);
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                            boolean booleanExtra = intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                            Intent intent2 = new Intent("com.samsung.android.service.aircommand.remotespen.action.COMMON_BROADCAST");
                            intent2.putExtra("action", "android.intent.action.AIRPLANE_MODE");
                            intent2.putExtra("isEnabled", booleanExtra);
                            intent2.setPackage("com.samsung.android.service.aircommand");
                            context2.sendBroadcast(intent2);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.USER_SWITCHED".equals(action)) {
                        Log.i("SpenGestureManagerService", "onReceive : User switched");
                        if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                            SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                            if (spenGestureManagerService.mIsPenInserted == null) {
                                Log.i("SpenGestureManagerService", "onReceive : SPen insertion state is not detected yet");
                                return;
                            }
                            if (spenGestureManagerService.mBleSpenManager.isAirActionSettingEnabled()) {
                                SpenGestureManagerService.this.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                            } else {
                                Log.i("SpenGestureManagerService", "onReceive : air action is disabled. startBlindChargeService");
                                SpenGestureManagerService.this.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                            }
                            Log.i("SpenGestureManagerService", "Start AirCommandUiService. #4");
                            Bundle bundle = new Bundle(intent.getExtras());
                            bundle.putString("action", intent.getAction());
                            bundle.putBoolean("penInsert", SpenGestureManagerService.this.mIsPenInserted.booleanValue());
                            bundle.putBoolean("isBoot", true);
                            SpenGestureManagerService.this.startAirCommandUiService(bundle);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                        SpenGestureManagerService spenGestureManagerService2 = SpenGestureManagerService.this;
                        spenGestureManagerService2.mBootComplete = true;
                        if (spenGestureManagerService2.mSpenUspLevel <= 0) {
                            Log.i("SpenGestureManagerService", "onReceive : This model is not support S pen");
                            return;
                        }
                        if (SpenGarageSpecManager.getInstance().mIsBundledSpenSupported) {
                            Log.i("SpenGestureManagerService", "onReceive : This model is bundle pen provided model");
                            return;
                        }
                        SpenGestureManagerService spenGestureManagerService3 = SpenGestureManagerService.this;
                        Context context4 = SpenGestureManagerService.mContext;
                        spenGestureManagerService3.getClass();
                        if (Settings.System.semGetIntForUser(context4.getContentResolver(), "pen_digitizer_enabled", 1, -2) != 0) {
                            return;
                        }
                        Log.i("SpenGestureManagerService", "onReceive : isDigitizerEnabled = false");
                        SpenGestureManagerService.this.writeDigitizerOnOffCommand(false);
                        return;
                    }
                    return;
                }
                if (isInitialStickyBroadcast()) {
                    return;
                }
                boolean booleanExtra2 = intent.getBooleanExtra("penInsert", true);
                boolean booleanExtra3 = intent.getBooleanExtra("isBoot", true);
                boolean booleanExtra4 = intent.getBooleanExtra("isReversed", false);
                FlashNotificationsController$$ExternalSyntheticOutline0.m("SpenGestureManagerService", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("penInsert : ", booleanExtra2, ", isBoot : ", booleanExtra3, ", isReversed : "), booleanExtra4);
                SpenGestureManagerService.this.getClass();
                ComponentName topMostPackage = SpenGestureManagerService.getTopMostPackage();
                String packageName = topMostPackage != null ? topMostPackage.getPackageName() : "";
                if (booleanExtra2) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = SpenGestureManagerService.this.mSpenUsingStartTime;
                    long j2 = currentTimeMillis - j;
                    if (j != 0) {
                        float f = j2 >= 1000 ? (j2 / 1000.0f) / 60.0f : 1.0f;
                        SpenGestureManagerService.sendLogSpenInsertInfo(f > 60.0f ? "MoreThan1Hour" : f > 30.0f ? "30to60Minutes" : f > 10.0f ? "10to30Minutes" : f > 5.0f ? "5to10Minutes" : f > 1.0f ? "1to5Minutes" : "1MinuteOrLess", "SPEN");
                    }
                    SpenGestureManagerService.this.mSpenUsingStartTime = 0L;
                    SpenGestureManagerService.sendLogSpenInsertInfo(packageName, "SPAT");
                } else {
                    SpenGestureManagerService.this.mSpenUsingStartTime = System.currentTimeMillis();
                    SpenGestureManagerService.this.getClass();
                    SpenGestureManagerService.sendLogSpenInsertInfo(packageName, "SPEN");
                }
                SpenGestureManagerService.this.mIsPenInserted = Boolean.valueOf(booleanExtra2);
                SpenGestureManagerService.this.mIsPenReversed = Boolean.valueOf(booleanExtra4);
                Bundle bundle2 = new Bundle(intent.getExtras());
                bundle2.putString("action", intent.getAction());
                bundle2.putString("topComponent", topMostPackage != null ? topMostPackage.getClassName() : "");
                bundle2.putInt("batteryStatus", SpenGestureManagerService.this.mBatteryOnlineStatus);
                bundle2.putBoolean("coverOpened", SpenGestureManagerService.this.mClearCoverOpened);
                bundle2.putBoolean("isReversed", SpenGestureManagerService.this.mIsPenReversed.booleanValue());
                SpenGestureManagerService spenGestureManagerService4 = SpenGestureManagerService.this;
                boolean booleanValue = spenGestureManagerService4.mIsPenInserted.booleanValue();
                BleSpenManager bleSpenManager = SpenGestureManagerService.this.mBleSpenManager;
                synchronized (bleSpenManager) {
                    z = bleSpenManager.mBundledRemoteSpenSupport;
                }
                BleSpenManager bleSpenManager2 = SpenGestureManagerService.this.mBleSpenManager;
                synchronized (bleSpenManager2) {
                    z2 = bleSpenManager2.mUnbundledRemoteSpenSupport;
                }
                boolean isAirActionSettingEnabled = SpenGestureManagerService.this.mBleSpenManager.isAirActionSettingEnabled();
                SpenGestureManagerService spenGestureManagerService5 = SpenGestureManagerService.this;
                Context context5 = SpenGestureManagerService.mContext;
                spenGestureManagerService5.getClass();
                boolean z3 = Settings.System.semGetIntForUser(context5.getContentResolver(), "pen_digitizer_enabled", 1, -2) != 0;
                StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("InsertPenServiceHandler: isBoot=", booleanExtra3, " isPenInserted= ", booleanValue, " isBundleSupportBle= ");
                BatteryService$$ExternalSyntheticOutline0.m(m, z, " isUnBundleSupportBle= ", z2, " isAirActionOn= ");
                m.append(isAirActionSettingEnabled);
                m.append(" isDigitizerEnabled= ");
                m.append(z3);
                Log.i("SpenGestureManagerService", m.toString());
                if (!z && !z2) {
                    FlashNotificationsController$$ExternalSyntheticOutline0.m("SpenGestureManagerService", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("startNonBleService: isDigitizerEnabled=", z3, " isPenInserted= ", z, " isAirActionOn= "), isAirActionSettingEnabled);
                    if (z3) {
                        if (isAirActionSettingEnabled) {
                            spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                        }
                        if (z) {
                            return;
                        }
                        spenGestureManagerService4.startAirCommandUiService(bundle2);
                        return;
                    }
                    return;
                }
                StringBuilder m2 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("startBleService: isBoot=", booleanExtra3, " isPenInserted= ", booleanValue, " isBundleSupportBle= ");
                m2.append(z);
                m2.append(" isAirActionOn= ");
                m2.append(isAirActionSettingEnabled);
                Log.i("SpenGestureManagerService", m2.toString());
                if (!booleanExtra3) {
                    if (isAirActionSettingEnabled) {
                        spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                    } else if (z) {
                        spenGestureManagerService4.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                    }
                    spenGestureManagerService4.startAirCommandUiService(bundle2);
                    return;
                }
                if (!booleanValue) {
                    spenGestureManagerService4.startAirCommandUiService(bundle2);
                } else if (isAirActionSettingEnabled) {
                    spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                } else if (z) {
                    spenGestureManagerService4.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                }
            }
        };
        this.mPenDoubleTap = new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.4
            /* JADX WARN: Removed duplicated region for block: B:28:0x00bd  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x00c5  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 563
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.smartclip.SpenGestureManagerService.AnonymousClass4.run():void");
            }
        };
        this.mHandler = new Handler() { // from class: com.android.server.smartclip.SpenGestureManagerService.5
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                switch (i) {
                    case 12312:
                        SpenGestureManagerService.m888$$Nest$mbroastcastHoverEvent(spenGestureManagerService, 10);
                        break;
                    case 12313:
                        Object obj = message.obj;
                        if (obj instanceof PenDetectionInfo) {
                            PenDetectionInfo penDetectionInfo = (PenDetectionInfo) obj;
                            Context context2 = SpenGestureManagerService.mContext;
                            spenGestureManagerService.getClass();
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("sendPenDetectionInfo."), penDetectionInfo.action, "SpenGestureManagerService");
                            if (!spenGestureManagerService.mAcIsBound) {
                                Log.i("SpenGestureManagerService", "Start AirCommandUiService. #0");
                                Bundle bundle = new Bundle();
                                bundle.putString("cause", "pen_detected");
                                String str = penDetectionInfo.penName;
                                if (str != null) {
                                    bundle.putString("penName", str);
                                }
                                spenGestureManagerService.startAirCommandUiService(bundle);
                                spenGestureManagerService.mAcPendingPenDetectionInfo = penDetectionInfo;
                                break;
                            } else {
                                Bundle bundle2 = new Bundle();
                                bundle2.putLong("eventTime", penDetectionInfo.eventTime);
                                bundle2.putString("penName", penDetectionInfo.penName);
                                Message obtain = Message.obtain(null, 1, penDetectionInfo.action, 0);
                                obtain.setData(bundle2);
                                try {
                                    Messenger messenger = spenGestureManagerService.mAcService;
                                    if (messenger != null) {
                                        messenger.send(obtain);
                                        break;
                                    }
                                } catch (RemoteException e) {
                                    Log.e("SpenGestureManagerService", "Failed to send the pen detection info", e);
                                    return;
                                }
                            }
                        }
                        break;
                    case 12314:
                        SpenGestureManagerService.m892$$Nest$msendDefferedPenDetectionInfo(spenGestureManagerService);
                        break;
                    case 12315:
                        SpenGestureManagerService.m888$$Nest$mbroastcastHoverEvent(spenGestureManagerService, 9);
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        };
        this.mAcServiceConnection = new ServiceConnection() { // from class: com.android.server.smartclip.SpenGestureManagerService.6
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Context context2 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "onServiceConnected : " + componentName + ", " + iBinder);
                SpenGestureManagerService.this.mAcService = new Messenger(iBinder);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                spenGestureManagerService.mAcIsBound = true;
                SpenGestureManagerService.m892$$Nest$msendDefferedPenDetectionInfo(spenGestureManagerService);
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Context context2 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "onServiceDisconnected : " + componentName);
                SpenGestureManagerService.mContext.unbindService(SpenGestureManagerService.this.mAcServiceConnection);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                spenGestureManagerService.mAcService = null;
                spenGestureManagerService.mAcIsBound = false;
            }
        };
        this.ALLOWANCE_RANGE_X = 10;
        this.ALLOWANCE_RANGE_Y = 10;
        this.ALLOWANCE_HOVER_TIME = 300;
        this.mPenDataStructArray = new ArrayList();
        mContext = context;
        Log.i("SpenGestureManagerService", "SpenGestureManagerService(Context) ");
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.server.smartclip.SpenGestureManagerService$4] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.smartclip.SpenGestureManagerService$5] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.server.smartclip.SpenGestureManagerService$6] */
    public SpenGestureManagerService(Context context, WindowManagerService windowManagerService) {
        this.mInputManager = null;
        this.mIsEnableLockScreenQuickNote = false;
        this.mDataExtractionSync = new Object();
        this.mAcService = null;
        this.mAcIsBound = false;
        this.mAcIsHoverOccuredBeforeTouchDown = false;
        this.mAcIsPenButtonPressed = false;
        this.mAcIsTouchDowned = false;
        this.mAcIsFloatingIconEnabled = false;
        this.mAcIsScreenOffMemoShowing = false;
        this.mAcAutoFloatingIconMode = true;
        this.mAcButtonPressTouchDownTime = 0L;
        this.mAcPenButtonPressedTime = 0L;
        this.mAcLastStartTime = 0L;
        this.mBatteryOnlineStatus = 1;
        this.mAcPendingPenDetectionInfo = null;
        this.mClearCoverOpened = true;
        this.mInboxSPen = false;
        this.mSpenUspLevel = -1;
        this.mScreenOffReason = -1;
        this.mSpenUsingStartTime = 0L;
        this.mSmartClipRemoteRequestSyncManager = new SmartClipRemoteRequestSyncManager();
        this.mBootComplete = false;
        this.mLastScreenOffDoubleTapTime = 0L;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.smartclip.SpenGestureManagerService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String str;
                boolean z;
                boolean z2;
                String action = intent.getAction();
                if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    SpenGestureManagerService.this.mBatteryOnlineStatus = intent.getIntExtra("online", 1);
                    return;
                }
                Context context3 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "Receive broadcast : " + action);
                if (!"com.samsung.pen.INSERT".equals(action)) {
                    if ("com.sec.android.intent.action.BLACK_MEMO".equals(action)) {
                        try {
                            str = intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        } catch (Exception unused) {
                            str = null;
                        }
                        SpenGestureManagerService.this.mAcIsScreenOffMemoShowing = KnoxCustomManagerService.SHOW.equals(str);
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                            boolean booleanExtra = intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                            Intent intent2 = new Intent("com.samsung.android.service.aircommand.remotespen.action.COMMON_BROADCAST");
                            intent2.putExtra("action", "android.intent.action.AIRPLANE_MODE");
                            intent2.putExtra("isEnabled", booleanExtra);
                            intent2.setPackage("com.samsung.android.service.aircommand");
                            context2.sendBroadcast(intent2);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.USER_SWITCHED".equals(action)) {
                        Log.i("SpenGestureManagerService", "onReceive : User switched");
                        if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                            SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                            if (spenGestureManagerService.mIsPenInserted == null) {
                                Log.i("SpenGestureManagerService", "onReceive : SPen insertion state is not detected yet");
                                return;
                            }
                            if (spenGestureManagerService.mBleSpenManager.isAirActionSettingEnabled()) {
                                SpenGestureManagerService.this.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                            } else {
                                Log.i("SpenGestureManagerService", "onReceive : air action is disabled. startBlindChargeService");
                                SpenGestureManagerService.this.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                            }
                            Log.i("SpenGestureManagerService", "Start AirCommandUiService. #4");
                            Bundle bundle = new Bundle(intent.getExtras());
                            bundle.putString("action", intent.getAction());
                            bundle.putBoolean("penInsert", SpenGestureManagerService.this.mIsPenInserted.booleanValue());
                            bundle.putBoolean("isBoot", true);
                            SpenGestureManagerService.this.startAirCommandUiService(bundle);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                        SpenGestureManagerService spenGestureManagerService2 = SpenGestureManagerService.this;
                        spenGestureManagerService2.mBootComplete = true;
                        if (spenGestureManagerService2.mSpenUspLevel <= 0) {
                            Log.i("SpenGestureManagerService", "onReceive : This model is not support S pen");
                            return;
                        }
                        if (SpenGarageSpecManager.getInstance().mIsBundledSpenSupported) {
                            Log.i("SpenGestureManagerService", "onReceive : This model is bundle pen provided model");
                            return;
                        }
                        SpenGestureManagerService spenGestureManagerService3 = SpenGestureManagerService.this;
                        Context context4 = SpenGestureManagerService.mContext;
                        spenGestureManagerService3.getClass();
                        if (Settings.System.semGetIntForUser(context4.getContentResolver(), "pen_digitizer_enabled", 1, -2) != 0) {
                            return;
                        }
                        Log.i("SpenGestureManagerService", "onReceive : isDigitizerEnabled = false");
                        SpenGestureManagerService.this.writeDigitizerOnOffCommand(false);
                        return;
                    }
                    return;
                }
                if (isInitialStickyBroadcast()) {
                    return;
                }
                boolean booleanExtra2 = intent.getBooleanExtra("penInsert", true);
                boolean booleanExtra3 = intent.getBooleanExtra("isBoot", true);
                boolean booleanExtra4 = intent.getBooleanExtra("isReversed", false);
                FlashNotificationsController$$ExternalSyntheticOutline0.m("SpenGestureManagerService", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("penInsert : ", booleanExtra2, ", isBoot : ", booleanExtra3, ", isReversed : "), booleanExtra4);
                SpenGestureManagerService.this.getClass();
                ComponentName topMostPackage = SpenGestureManagerService.getTopMostPackage();
                String packageName = topMostPackage != null ? topMostPackage.getPackageName() : "";
                if (booleanExtra2) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = SpenGestureManagerService.this.mSpenUsingStartTime;
                    long j2 = currentTimeMillis - j;
                    if (j != 0) {
                        float f = j2 >= 1000 ? (j2 / 1000.0f) / 60.0f : 1.0f;
                        SpenGestureManagerService.sendLogSpenInsertInfo(f > 60.0f ? "MoreThan1Hour" : f > 30.0f ? "30to60Minutes" : f > 10.0f ? "10to30Minutes" : f > 5.0f ? "5to10Minutes" : f > 1.0f ? "1to5Minutes" : "1MinuteOrLess", "SPEN");
                    }
                    SpenGestureManagerService.this.mSpenUsingStartTime = 0L;
                    SpenGestureManagerService.sendLogSpenInsertInfo(packageName, "SPAT");
                } else {
                    SpenGestureManagerService.this.mSpenUsingStartTime = System.currentTimeMillis();
                    SpenGestureManagerService.this.getClass();
                    SpenGestureManagerService.sendLogSpenInsertInfo(packageName, "SPEN");
                }
                SpenGestureManagerService.this.mIsPenInserted = Boolean.valueOf(booleanExtra2);
                SpenGestureManagerService.this.mIsPenReversed = Boolean.valueOf(booleanExtra4);
                Bundle bundle2 = new Bundle(intent.getExtras());
                bundle2.putString("action", intent.getAction());
                bundle2.putString("topComponent", topMostPackage != null ? topMostPackage.getClassName() : "");
                bundle2.putInt("batteryStatus", SpenGestureManagerService.this.mBatteryOnlineStatus);
                bundle2.putBoolean("coverOpened", SpenGestureManagerService.this.mClearCoverOpened);
                bundle2.putBoolean("isReversed", SpenGestureManagerService.this.mIsPenReversed.booleanValue());
                SpenGestureManagerService spenGestureManagerService4 = SpenGestureManagerService.this;
                boolean booleanValue = spenGestureManagerService4.mIsPenInserted.booleanValue();
                BleSpenManager bleSpenManager = SpenGestureManagerService.this.mBleSpenManager;
                synchronized (bleSpenManager) {
                    z = bleSpenManager.mBundledRemoteSpenSupport;
                }
                BleSpenManager bleSpenManager2 = SpenGestureManagerService.this.mBleSpenManager;
                synchronized (bleSpenManager2) {
                    z2 = bleSpenManager2.mUnbundledRemoteSpenSupport;
                }
                boolean isAirActionSettingEnabled = SpenGestureManagerService.this.mBleSpenManager.isAirActionSettingEnabled();
                SpenGestureManagerService spenGestureManagerService5 = SpenGestureManagerService.this;
                Context context5 = SpenGestureManagerService.mContext;
                spenGestureManagerService5.getClass();
                boolean z3 = Settings.System.semGetIntForUser(context5.getContentResolver(), "pen_digitizer_enabled", 1, -2) != 0;
                StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("InsertPenServiceHandler: isBoot=", booleanExtra3, " isPenInserted= ", booleanValue, " isBundleSupportBle= ");
                BatteryService$$ExternalSyntheticOutline0.m(m, z, " isUnBundleSupportBle= ", z2, " isAirActionOn= ");
                m.append(isAirActionSettingEnabled);
                m.append(" isDigitizerEnabled= ");
                m.append(z3);
                Log.i("SpenGestureManagerService", m.toString());
                if (!z && !z2) {
                    FlashNotificationsController$$ExternalSyntheticOutline0.m("SpenGestureManagerService", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("startNonBleService: isDigitizerEnabled=", z3, " isPenInserted= ", z, " isAirActionOn= "), isAirActionSettingEnabled);
                    if (z3) {
                        if (isAirActionSettingEnabled) {
                            spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                        }
                        if (z) {
                            return;
                        }
                        spenGestureManagerService4.startAirCommandUiService(bundle2);
                        return;
                    }
                    return;
                }
                StringBuilder m2 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("startBleService: isBoot=", booleanExtra3, " isPenInserted= ", booleanValue, " isBundleSupportBle= ");
                m2.append(z);
                m2.append(" isAirActionOn= ");
                m2.append(isAirActionSettingEnabled);
                Log.i("SpenGestureManagerService", m2.toString());
                if (!booleanExtra3) {
                    if (isAirActionSettingEnabled) {
                        spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                    } else if (z) {
                        spenGestureManagerService4.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                    }
                    spenGestureManagerService4.startAirCommandUiService(bundle2);
                    return;
                }
                if (!booleanValue) {
                    spenGestureManagerService4.startAirCommandUiService(bundle2);
                } else if (isAirActionSettingEnabled) {
                    spenGestureManagerService4.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext);
                } else if (z) {
                    spenGestureManagerService4.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext);
                }
            }
        };
        this.mPenDoubleTap = new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.4
            @Override // java.lang.Runnable
            public final void run() {
                /*
                    Method dump skipped, instructions count: 563
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.smartclip.SpenGestureManagerService.AnonymousClass4.run():void");
            }
        };
        ?? r3 = new Handler() { // from class: com.android.server.smartclip.SpenGestureManagerService.5
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                switch (i) {
                    case 12312:
                        SpenGestureManagerService.m888$$Nest$mbroastcastHoverEvent(spenGestureManagerService, 10);
                        break;
                    case 12313:
                        Object obj = message.obj;
                        if (obj instanceof PenDetectionInfo) {
                            PenDetectionInfo penDetectionInfo = (PenDetectionInfo) obj;
                            Context context2 = SpenGestureManagerService.mContext;
                            spenGestureManagerService.getClass();
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("sendPenDetectionInfo."), penDetectionInfo.action, "SpenGestureManagerService");
                            if (!spenGestureManagerService.mAcIsBound) {
                                Log.i("SpenGestureManagerService", "Start AirCommandUiService. #0");
                                Bundle bundle = new Bundle();
                                bundle.putString("cause", "pen_detected");
                                String str = penDetectionInfo.penName;
                                if (str != null) {
                                    bundle.putString("penName", str);
                                }
                                spenGestureManagerService.startAirCommandUiService(bundle);
                                spenGestureManagerService.mAcPendingPenDetectionInfo = penDetectionInfo;
                                break;
                            } else {
                                Bundle bundle2 = new Bundle();
                                bundle2.putLong("eventTime", penDetectionInfo.eventTime);
                                bundle2.putString("penName", penDetectionInfo.penName);
                                Message obtain = Message.obtain(null, 1, penDetectionInfo.action, 0);
                                obtain.setData(bundle2);
                                try {
                                    Messenger messenger = spenGestureManagerService.mAcService;
                                    if (messenger != null) {
                                        messenger.send(obtain);
                                        break;
                                    }
                                } catch (RemoteException e) {
                                    Log.e("SpenGestureManagerService", "Failed to send the pen detection info", e);
                                    return;
                                }
                            }
                        }
                        break;
                    case 12314:
                        SpenGestureManagerService.m892$$Nest$msendDefferedPenDetectionInfo(spenGestureManagerService);
                        break;
                    case 12315:
                        SpenGestureManagerService.m888$$Nest$mbroastcastHoverEvent(spenGestureManagerService, 9);
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        };
        this.mHandler = r3;
        this.mAcServiceConnection = new ServiceConnection() { // from class: com.android.server.smartclip.SpenGestureManagerService.6
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Context context2 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "onServiceConnected : " + componentName + ", " + iBinder);
                SpenGestureManagerService.this.mAcService = new Messenger(iBinder);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                spenGestureManagerService.mAcIsBound = true;
                SpenGestureManagerService.m892$$Nest$msendDefferedPenDetectionInfo(spenGestureManagerService);
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Context context2 = SpenGestureManagerService.mContext;
                Log.i("SpenGestureManagerService", "onServiceDisconnected : " + componentName);
                SpenGestureManagerService.mContext.unbindService(SpenGestureManagerService.this.mAcServiceConnection);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                spenGestureManagerService.mAcService = null;
                spenGestureManagerService.mAcIsBound = false;
            }
        };
        this.ALLOWANCE_RANGE_X = 10;
        this.ALLOWANCE_RANGE_Y = 10;
        this.ALLOWANCE_HOVER_TIME = 300;
        this.mPenDataStructArray = new ArrayList();
        mContext = context;
        Log.i("SpenGestureManagerService", "SpenGestureManagerService(Context context, WindowManagerService Wm)");
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
        this.mSpenUspLevel = i;
        if (i % 10 == 5) {
            this.mInboxSPen = true;
        } else {
            this.mInboxSPen = false;
        }
        this.mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.server.smartclip.SpenGestureManagerService.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                Context context2 = SpenGestureManagerService.mContext;
                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(rawX, rawY, "Pen DoubleTap : x=", ", y=", ", action=");
                m.append(motionEvent.getAction());
                Log.i("SpenGestureManagerService", m.toString());
                Cursor query = SpenGestureManagerService.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), null, "getSealedState", null, null);
                if (query != null) {
                    try {
                        query.moveToFirst();
                        if (query.getString(query.getColumnIndex("getSealedState")).equals("true")) {
                            Log.i("SpenGestureManagerService", "now KNOX state : can't excute Double Tap");
                            query.close();
                            return true;
                        }
                    } finally {
                        query.close();
                    }
                }
                SpenGestureManagerService.this.mLastDoubleTapPosition = new Point(rawX, rawY);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                spenGestureManagerService.mHandler.post(spenGestureManagerService.mPenDoubleTap);
                return true;
            }
        });
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        Context context2 = mContext;
        Looper mainLooper = context2.getMainLooper();
        UserHandle userHandle = UserHandle.ALL;
        anonymousClass3.register(context2, mainLooper, userHandle, true);
        this.mInputManager = (InputManager) mContext.getSystemService("input");
        if (this.mSpenUspLevel > 0) {
            windowManagerService.registerPointerEventListener(new SPenGestureInputEventReceiver(), 0);
        }
        BleSpenManager bleSpenManager = new BleSpenManager(context);
        this.mBleSpenManager = bleSpenManager;
        this.mSemInputDeviceManager = (SemInputDeviceManager) mContext.getSystemService("SemInputDeviceManagerService");
        this.mIsEnableLockScreenQuickNote = Settings.Secure.getIntForUser(mContext.getContentResolver(), "lock_screen_quick_note", 0, -2) == 1;
        Log.i("SpenGestureManagerService", "checkSettingCondition, LOCK_SCREEN_QUICK_NOTE : " + this.mIsEnableLockScreenQuickNote);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.pen.INSERT");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        if (this.mInboxSPen) {
            intentFilter.addAction("com.sec.android.intent.action.BLACK_MEMO");
        }
        if (bleSpenManager.isSupportBleSpen()) {
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        }
        mContext.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null);
        final AcSettingsObserver acSettingsObserver = new AcSettingsObserver(r3);
        ContentResolver contentResolver = mContext.getContentResolver();
        m890$$Nest$monFloatingIconSettingChanged(this, Settings.System.semGetIntForUser(mContext.getContentResolver(), "air_cmd_use_minimized", 0, -2) != 0 && Settings.System.getInt(mContext.getContentResolver(), "air_button_onoff", 0) == 1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("air_cmd_use_minimized"), false, acSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("air_button_onoff"), false, acSettingsObserver);
        contentResolver.registerContentObserver(Settings.System.getUriFor("pen_digitizer_enabled"), false, new ContentObserver(r3) { // from class: com.android.server.smartclip.SpenGestureManagerService.AcSettingsObserver.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                int semGetIntForUser = Settings.System.semGetIntForUser(SpenGestureManagerService.mContext.getContentResolver(), "pen_digitizer_enabled", 1, -2);
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                boolean z2 = semGetIntForUser == 1;
                spenGestureManagerService.getClass();
                Log.i("SpenGestureManagerService", "onSpenDigitizerOnOffChanged : " + z2);
                spenGestureManagerService.writeDigitizerOnOffCommand(z2);
                if (z2) {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "digitizer_enable_changed");
                    bundle.putBoolean("digitizer_enable", z2);
                    spenGestureManagerService.startAirCommandSpenTriggerService(bundle);
                }
            }
        }, -1);
        if (this.mSpenUspLevel > 0) {
            this.mThemeManager = new SpenThemeManager(mContext);
        } else {
            Log.i("SpenGestureManagerService", "Theme isn't supported. Not a spen model.");
        }
    }

    public static void checkChangeSpenThemePermission() {
        checkPermission("com.samsung.android.permission.CHANGE_SPEN_THEME");
    }

    public static void checkPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == Process.myUid() || mContext.checkCallingPermission(str) == 0) {
            return;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("checkPermission : Requires ", str, " permission. caller PID=");
        m.append(Binder.getCallingPid());
        m.append(" UID=");
        m.append(callingUid);
        Log.e("SpenGestureManagerService", m.toString());
        throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Requires ", str, " permission"));
    }

    public static void checkSmartClipMetaExtractionPermission() {
        checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA");
    }

    public static String getPenName(MotionEvent motionEvent) {
        InputDevice device = motionEvent.getDevice();
        if (device != null) {
            return device.getName();
        }
        return null;
    }

    public static ComponentName getTopMostPackage() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) mContext.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks.size() != 0) {
            return runningTasks.get(0).topActivity;
        }
        Log.e("SpenGestureManagerService", "getTopMostPackage : Failed to get running task info");
        return null;
    }

    public static boolean isUidSignature() {
        return mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) == 0;
    }

    public static void sendLogSpenInsertInfo(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", "com.android.server.smartclip");
        contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, str2);
        contentValues.put("extra", str);
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
        intent.putExtra("data", contentValues);
        intent.setPackage("com.samsung.android.providers.context");
        mContext.sendBroadcast(intent);
    }

    public final boolean canStartAirCommand() {
        String str = Settings.Global.getInt(mContext.getContentResolver(), "device_provisioned", 0) == 0 ? "SetupWizard on" : FactoryTest.isFactoryBinary() ? "FactoryBinary" : this.mAcIsScreenOffMemoShowing ? "ScreenOffMemo on" : null;
        if (str == null) {
            return true;
        }
        Log.i("SpenGestureManagerService", "Failed to start AirCommand.".concat(str));
        return false;
    }

    public final void dispatchPenDetectionInfo(int i, long j, long j2, String str) {
        PenDetectionInfo penDetectionInfo = new PenDetectionInfo();
        penDetectionInfo.action = i;
        penDetectionInfo.eventTime = j;
        penDetectionInfo.penName = str;
        Message obtainMessage = obtainMessage();
        obtainMessage.what = 12313;
        obtainMessage.obj = penDetectionInfo;
        sendMessageDelayed(obtainMessage, j2);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Permission Denial: can't dump from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "AirCommand:", "  UspLevel : ");
        m$1.append(this.mSpenUspLevel);
        printWriter.print(m$1.toString());
        printWriter.print("  InBoxType : " + this.mInboxSPen);
        printWriter.print("  PenReversed : " + this.mIsPenReversed);
        printWriter.println("  PenInserted : " + this.mIsPenInserted);
        printWriter.print("  mAcIsBound : " + this.mAcIsBound);
        printWriter.print("  mAcIsFloatingIconEnabled : " + this.mAcIsFloatingIconEnabled + ", " + this.mAcAutoFloatingIconMode);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mAcIsScreenOffMemoShowing : "), this.mAcIsScreenOffMemoShowing, printWriter);
    }

    public final String getBleSpenAddress() {
        Log.i("SpenGestureManagerService", "getBleSpenAddress");
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            if (bleSpenManager.isSupportBleSpen()) {
                return BleSpenManager.readStringFromFile("/efs/spen/blespen_addr");
            }
            Log.e("BleSpenManager", "getBleSpenAddress : BLE Spen is not supported");
            return null;
        }
    }

    public final String getBleSpenCmfCode() {
        Log.i("SpenGestureManagerService", "getBleSpenCmfCode");
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            if (bleSpenManager.isSupportBleSpen()) {
                return BleSpenManager.readStringFromFile("/efs/spen/blespen_cmf");
            }
            Log.e("BleSpenManager", "getBleSpenCmfCode : BLE Spen is not supported");
            return null;
        }
    }

    public final EditorInfo getCurrentEditorInfo() {
        if (isUidSignature()) {
            return this.mEditorInfo;
        }
        Log.e("SpenGestureManagerService", "no permission to use getCurrentEditorInfo() : " + Binder.getCallingUid());
        return null;
    }

    public final IRemoteInputConnection getCurrentInputContext() {
        if (isUidSignature()) {
            return this.mInputConnection;
        }
        Log.e("SpenGestureManagerService", "no permission to use getCurrentInputContext() : " + Binder.getCallingUid());
        return null;
    }

    public final int getCurrentMissingMethodFlags() {
        return this.mMissingMethodFlags;
    }

    public final long getScreenOffDoubleTabTime() {
        return this.mLastScreenOffDoubleTapTime;
    }

    public final int getScreenOffReason() {
        return this.mScreenOffReason;
    }

    public final Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) {
        Log.i("SpenGestureManagerService", "getScrollableRect()");
        checkSmartClipMetaExtractionPermission();
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
            asInterface.dispatchSmartClipRemoteRequest(rect.left + (rect.width() / 2), rect.top + (rect.height() / 2), new SmartClipRemoteRequestInfo(allocateNewRequestId, 4, 1, (Parcelable) null), iBinder);
            SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId);
            if (waitResult == null) {
                Log.e("SpenGestureManagerService", "getScrollableRect : Result is null!!");
                return null;
            }
            Log.i("SpenGestureManagerService", "getScrollableRect : Result=" + waitResult.mResultData);
            return (Bundle) waitResult.mResultData;
        } catch (Exception e) {
            Log.e("SpenGestureManagerService", "getScrollableAreaInfo : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public final Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) {
        Log.i("SpenGestureManagerService", "getScrollableViewInfo()");
        checkSmartClipMetaExtractionPermission();
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
            Bundle bundle = new Bundle();
            bundle.putInt("hashCode", i);
            asInterface.dispatchSmartClipRemoteRequest(rect.left + (rect.width() / 2), rect.top + (rect.height() / 2), new SmartClipRemoteRequestInfo(allocateNewRequestId, 5, 1, bundle), iBinder);
            SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId);
            if (waitResult == null) {
                Log.e("SpenGestureManagerService", "getScrollableViewInfo : Result is null!!");
                return null;
            }
            Log.i("SpenGestureManagerService", "getScrollableViewInfo : Result=" + waitResult.mResultData);
            return (Bundle) waitResult.mResultData;
        } catch (Exception e) {
            Log.e("SpenGestureManagerService", "getScrollableViewInfo : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public final SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) {
        SemSmartClipDataRepository semSmartClipDataRepository;
        checkSmartClipMetaExtractionPermission();
        try {
            synchronized (this.mDataExtractionSync) {
                if (rect == null) {
                    try {
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        ((WindowManager) mContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
                        rect = new Rect(0, 0, Math.abs(displayMetrics.widthPixels), Math.abs(displayMetrics.heightPixels));
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
                asInterface.dispatchSmartClipRemoteRequest(rect.centerX(), rect.centerY(), new SmartClipRemoteRequestInfo(allocateNewRequestId, 1, i2, new SmartClipDataExtractionEvent(allocateNewRequestId, rect, i)), iBinder);
                SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId);
                if (waitResult == null) {
                    Log.e("SpenGestureManagerService", "getSmartClipDataByScreenRect : result is null!!");
                    return null;
                }
                SmartClipDataExtractionResponse smartClipDataExtractionResponse = waitResult.mResultData;
                if (smartClipDataExtractionResponse != null) {
                    semSmartClipDataRepository = smartClipDataExtractionResponse.mRepository;
                    if (semSmartClipDataRepository == null) {
                        Log.e("SpenGestureManagerService", "moveMetaFilesToLocalStorage : Empty repository!");
                    }
                } else {
                    semSmartClipDataRepository = null;
                }
                Log.i("SpenGestureManagerService", "getSmartClipDataByScreenRect : wait is unlocked. Repository = " + semSmartClipDataRepository);
                return semSmartClipDataRepository;
            }
        } catch (Exception e) {
            Log.e("SpenGestureManagerService", "getSmartClipDataByScreenRect : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public final void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) {
        checkPermission("android.permission.INJECT_EVENTS");
        try {
            synchronized (this.mDataExtractionSync) {
                try {
                    IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                    int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(z);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray("events", inputEventArr);
                    bundle.putBoolean("waitUntilConsume", z);
                    asInterface.dispatchSmartClipRemoteRequest(i, i2, new SmartClipRemoteRequestInfo(allocateNewRequestId, 3, 1, bundle), iBinder);
                    if (z) {
                        SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId);
                        if (waitResult != null) {
                            Log.i("SpenGestureManagerService", "injectInputEvent : Result=" + waitResult.mResultData);
                        } else {
                            Log.e("SpenGestureManagerService", "injectInputEvent : Result is null!!");
                        }
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            Log.e("SpenGestureManagerService", "injectInputEvent : Exception thrown! e = " + e.toString(), e);
        }
    }

    public final boolean isSpenInserted() {
        Boolean bool = this.mIsPenInserted;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public final boolean isSpenReversed() {
        Boolean bool = this.mIsPenReversed;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public final boolean isSupportBleSpen() {
        boolean isSupportBleSpen = this.mBleSpenManager.isSupportBleSpen();
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("isSupportBleSpen : ", "SpenGestureManagerService", isSupportBleSpen);
        return isSupportBleSpen;
    }

    public final void notifyAirGesture(String str) {
        if (!isUidSignature()) {
            Log.e("SpenGestureManagerService", "no permission to use notifyAirGesture() : " + Binder.getCallingUid());
            return;
        }
        RemoteCallbackList remoteCallbackList = this.mAirGestureListener;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        Log.i("SpenGestureManagerService", "notifyAirGesture :  i: " + beginBroadcast + "  / " + str);
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mAirGestureListener.getBroadcastItem(beginBroadcast).onGesture(str);
            } catch (RemoteException unused) {
            }
        }
        this.mAirGestureListener.finishBroadcast();
    }

    public final void notifyBleSpenChargeLockState(boolean z) {
        Log.i("SpenGestureManagerService", "notifyBleSpenChargeLockState : " + z);
        checkSmartClipMetaExtractionPermission();
        RemoteCallbackList remoteCallbackList = this.mBleSpenChargeLockStateChangedListeners;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mBleSpenChargeLockStateChangedListeners.getBroadcastItem(beginBroadcast).onChanged(z);
            } catch (RemoteException unused) {
            }
        }
        this.mBleSpenChargeLockStateChangedListeners.finishBroadcast();
    }

    public final void notifyKeyboardClosed() {
        post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.9
            @Override // java.lang.Runnable
            public final void run() {
                SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                if (spenGestureManagerService.mInputInfoChangeListeners == null) {
                    return;
                }
                Log.i("SpenGestureManagerService", "broastcastKeyboardClosed getRegisteredCallbackCount() = " + spenGestureManagerService.mInputInfoChangeListeners.getRegisteredCallbackCount());
                int beginBroadcast = spenGestureManagerService.mInputInfoChangeListeners.beginBroadcast();
                while (beginBroadcast > 0) {
                    beginBroadcast--;
                    try {
                        spenGestureManagerService.mInputInfoChangeListeners.getBroadcastItem(beginBroadcast).onKeyboardClosed();
                    } catch (RemoteException unused) {
                    }
                }
                spenGestureManagerService.mInputInfoChangeListeners.finishBroadcast();
            }
        });
    }

    public final void registerAirGestureListener(IAirGestureListener iAirGestureListener) {
        if (!isUidSignature()) {
            Log.e("SpenGestureManagerService", "no permission to use registerAirGestureListener() : " + Binder.getCallingUid());
        } else {
            Log.i("SpenGestureManagerService", "registerAirGestureListener");
            if (this.mAirGestureListener == null) {
                this.mAirGestureListener = new RemoteCallbackList();
            }
            this.mAirGestureListener.register(iAirGestureListener);
        }
    }

    public final void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        checkSmartClipMetaExtractionPermission();
        if (this.mBleSpenChargeLockStateChangedListeners == null) {
            this.mBleSpenChargeLockStateChangedListeners = new RemoteCallbackList();
        }
        this.mBleSpenChargeLockStateChangedListeners.register(iBleSpenChargeLockStateChangedListener);
    }

    public final void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        if (this.mHoverListeners == null) {
            this.mHoverListeners = new RemoteCallbackList();
        }
        this.mHoverListeners.register(iSpenGestureHoverListener);
    }

    public final void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        if (isUidSignature()) {
            if (this.mInputInfoChangeListeners == null) {
                this.mInputInfoChangeListeners = new RemoteCallbackList();
            }
            this.mInputInfoChangeListeners.register(iInputMethodInfoChangeListener);
        } else {
            Log.e("SpenGestureManagerService", "no permission to use registerInputMethodInfoChangeListener() : " + Binder.getCallingUid());
        }
    }

    public final void resetPenAttachSound(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenAttachSound(null, str, null);
        }
    }

    public final void resetPenDetachSound(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenDetachSound(null, str, null);
        }
    }

    public final void resetPenHoverIcon(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenHoverIcon(str, null, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
    }

    public final void saveBleSpenLogFile(byte[] bArr) {
        Log.i("SpenGestureManagerService", "saveBleSpenLogFile");
        checkSmartClipMetaExtractionPermission();
        synchronized (this.mBleSpenManager) {
            try {
                if (bArr == null) {
                    Log.e("BleSpenManager", "saveBleSpenLogFile : no buffer");
                    return;
                }
                Log.i("BleSpenManager", "saveBleSpenLogFile : length=" + bArr.length);
                File file = new File(Environment.getDataDirectory() + "/log/spen");
                if (!file.exists() && !file.mkdirs()) {
                    Log.e("BleSpenManager", "saveBleSpenLogFile : failed to make dirs");
                }
                BleSpenManager.makeFilePublic(file);
                String str = file + File.separator + "Spen_dumpState.log";
                File file2 = new File(str);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    try {
                        fileOutputStream.write(bArr);
                        BleSpenManager.makeFilePublic(file2);
                        Log.i("BleSpenManager", "saveBleSpenLogFile : " + str);
                        fileOutputStream.close();
                    } finally {
                    }
                } catch (Throwable th) {
                    Log.e("BleSpenManager", "saveBleSpenLogFile fail : " + th.toString());
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
        Log.i("SpenGestureManagerService", "screenshot()");
        checkSmartClipMetaExtractionPermission();
        SpenGestureScreenShotManager.FutureScreenShot futureScreenShot = new SpenGestureScreenShotManager.FutureScreenShot(new Callable() { // from class: com.android.server.smartclip.SpenGestureScreenShotManager.Host.1
            public final /* synthetic */ boolean val$containsTargetSystemWindow;
            public final /* synthetic */ int val$displayId;
            public final /* synthetic */ int val$height;
            public final /* synthetic */ Rect val$sourceCrop;
            public final /* synthetic */ int val$targetWindowType;
            public final /* synthetic */ boolean val$useIdentityTransform;
            public final /* synthetic */ int val$width;

            public AnonymousClass1(int i5, int i22, boolean z3, Rect rect2, int i32, int i42, boolean z22) {
                r1 = i5;
                r2 = i22;
                r3 = z3;
                r4 = rect2;
                r5 = i32;
                r6 = i42;
                r7 = z22;
            }

            @Override // java.util.concurrent.Callable
            public final Object call() {
                Rect rect2 = r4;
                return new RealScreenShot(r1, r2, r3, rect2, r5, r6, r7);
            }
        });
        new Thread(futureScreenShot).start();
        try {
            return ((SpenGestureScreenShotManager.RealScreenShot) futureScreenShot.get()).bitmap;
        } catch (InterruptedException e) {
            Log.e("FutureScreenShot", e.getMessage());
            return null;
        } catch (ExecutionException e2) {
            Log.e("FutureScreenShot", e2.getMessage());
            return null;
        }
    }

    public final void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) {
        if (smartClipRemoteRequestResult == null) {
            Log.e("SpenGestureManagerService", "sendSmartClipRemoteRequestResult : result is null!");
            return;
        }
        StringBuilder sb = new StringBuilder("sendSmartClipRemoteRequestResult : requestId=");
        sb.append(smartClipRemoteRequestResult.mRequestId);
        sb.append(" requestType=");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, smartClipRemoteRequestResult.mRequestType, "SpenGestureManagerService");
        SmartClipRemoteRequestSyncManager smartClipRemoteRequestSyncManager = this.mSmartClipRemoteRequestSyncManager;
        smartClipRemoteRequestSyncManager.getClass();
        SmartClipRemoteRequestSyncManager.RequestInfo requestItem = smartClipRemoteRequestSyncManager.getRequestItem(smartClipRemoteRequestResult.mRequestId);
        if (requestItem == null) {
            Log.e(SmartClipRemoteRequestSyncManager.TAG, "notifyResult : Could not find request information. id=" + smartClipRemoteRequestResult.mRequestId);
            return;
        }
        synchronized (requestItem.mWaitObj) {
            requestItem.mResultData = smartClipRemoteRequestResult;
            requestItem.mResponseArrived = true;
            requestItem.mWaitObj.notify();
        }
    }

    public final void setBleSpenAddress(String str) {
        Log.i("SpenGestureManagerService", "setBleSpenAddress : " + str);
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            if (bleSpenManager.isSupportBleSpen()) {
                BleSpenManager.writeStringToFile("/efs/spen/blespen_addr", str);
            } else {
                Log.e("BleSpenManager", "setBleSpenAddress : BLE Spen is not supported");
            }
        }
    }

    public final void setBleSpenCmfCode(String str) {
        Log.i("SpenGestureManagerService", "setBleSpenCmfCode : " + str);
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            if (bleSpenManager.isSupportBleSpen()) {
                BleSpenManager.writeStringToFile("/efs/spen/blespen_cmf", str);
            } else {
                Log.e("BleSpenManager", "setBleSpenCmfCode : BLE Spen is not supported");
            }
        }
    }

    public final void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) {
        if (!isUidSignature()) {
            Log.e("SpenGestureManagerService", "no permission to use setCurrentInputInfo() : " + Binder.getCallingUid());
        } else {
            this.mInputConnection = iRemoteInputConnection;
            this.mEditorInfo = editorInfo;
            this.mMissingMethodFlags = i;
            post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.8
                @Override // java.lang.Runnable
                public final void run() {
                    SpenGestureManagerService spenGestureManagerService = SpenGestureManagerService.this;
                    if (spenGestureManagerService.mInputInfoChangeListeners == null) {
                        return;
                    }
                    Log.i("SpenGestureManagerService", "broastcastInputContextChanged getRegisteredCallbackCount() = " + spenGestureManagerService.mInputInfoChangeListeners.getRegisteredCallbackCount());
                    int beginBroadcast = spenGestureManagerService.mInputInfoChangeListeners.beginBroadcast();
                    while (beginBroadcast > 0) {
                        beginBroadcast--;
                        try {
                            spenGestureManagerService.mInputInfoChangeListeners.getBroadcastItem(beginBroadcast).onInputInfoChanged(spenGestureManagerService.mInputConnection, spenGestureManagerService.mEditorInfo);
                        } catch (RemoteException unused) {
                        }
                    }
                    spenGestureManagerService.mInputInfoChangeListeners.finishBroadcast();
                }
            });
        }
    }

    public final void setHoverStayDetectEnabled(boolean z) {
    }

    public final void setHoverStayValues(int i, int i2, int i3) {
        if (i > 0) {
            this.ALLOWANCE_RANGE_X = i;
        }
        if (i2 > 0) {
            this.ALLOWANCE_RANGE_Y = i2;
        }
        if (i3 > 0) {
            this.ALLOWANCE_HOVER_TIME = i3;
        }
    }

    public final void setPenAttachSound(String str, FileDescriptor fileDescriptor) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenAttachSound(fileDescriptor, str, String.valueOf(System.nanoTime()));
        }
    }

    public final void setPenDetachSound(String str, FileDescriptor fileDescriptor) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenDetachSound(fileDescriptor, str, String.valueOf(System.nanoTime()));
        }
    }

    public final void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenHoverIcon(str, fileDescriptor, f, f2);
        }
    }

    public final void setScreenOffDoubleTabTime() {
        Log.i("SpenGestureManagerService", "setScreenOffDoubleTabTime");
        this.mLastScreenOffDoubleTapTime = System.currentTimeMillis();
    }

    public final void setScreenOffReason(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "setScreenOffReason : ", "SpenGestureManagerService");
        this.mScreenOffReason = i;
    }

    public final void setSpenInsertionState(boolean z) {
        Log.i("SpenGestureManagerService", "setSpenInsertionState : " + z);
        this.mIsPenInserted = Boolean.valueOf(z);
    }

    public final void setSpenPdctLowSensitivityEnable() {
        Log.i("SpenGestureManagerService", "setSpenPdctLowSensitivityEnable");
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            try {
                bleSpenManager.mSemInputDeviceManager.setSpenPdctLowSensitivityEnable(1);
            } catch (Exception e) {
                Log.e("BleSpenManager", "mSemInputDeviceManager.setSpenPdctLowSensitivityEnable: Exception:" + e);
            }
        }
    }

    public final void setSpenPowerSavingModeEnabled(boolean z) {
        try {
            this.mSemInputDeviceManager.setSpenPowerSavingMode(z ? 1 : 0);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e, "mSemInputDeviceManager.setSpenPowerSavingMode: Exception: ", "SpenGestureManagerService");
        }
    }

    public final void showTouchPointer(final boolean z) {
        Log.i("SpenGestureManagerService", "showTouchPointer : " + z);
        checkSmartClipMetaExtractionPermission();
        post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.7
            @Override // java.lang.Runnable
            public final void run() {
                Settings.System.putInt(SpenGestureManagerService.mContext.getContentResolver(), "show_touches", z ? 1 : 0);
            }
        });
    }

    public final void startAirCommandSpenTriggerService(Bundle bundle) {
        if (canStartAirCommand()) {
            try {
                Intent intent = new Intent("com.samsung.android.service.aircommand.action.SPEN_TRIGGER_SERVICE");
                intent.setPackage("com.samsung.android.service.aircommand");
                intent.putExtras(bundle);
                mContext.startServiceAsUser(intent, UserHandle.CURRENT);
                Log.i("SpenGestureManagerService", "start start spentrigger service.");
            } catch (RuntimeException e) {
                Log.e("SpenGestureManagerService", "Failed to start Spen trigger AirCommand. e = " + e);
            }
        }
    }

    public final void startAirCommandUiService(Bundle bundle) {
        if (canStartAirCommand()) {
            if (!SpenGarageSpecManager.getInstance().mIsBundledSpenSupported) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("action", "start_remote_spen_service");
                startAirCommandSpenTriggerService(bundle2);
            }
            try {
                Intent intent = new Intent("com.samsung.android.service.aircommand.action.AIR_COMMAND_SERVICE");
                intent.setPackage("com.samsung.android.service.aircommand");
                intent.putExtras(bundle);
                Context context = mContext;
                UserHandle userHandle = UserHandle.CURRENT;
                context.startServiceAsUser(intent, userHandle);
                Log.i("SpenGestureManagerService", "startAirCommandUiService");
                if (this.mAcAutoFloatingIconMode) {
                    Log.i("SpenGestureManagerService", "bindService.isBound : " + this.mAcIsBound + ".ret : " + (this.mAcIsBound ? false : mContext.bindServiceAsUser(intent, this.mAcServiceConnection, 0, userHandle)));
                }
            } catch (RuntimeException e) {
                Log.e("SpenGestureManagerService", "Failed to start AirCommandUiService. " + e);
            }
        }
    }

    public final void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) {
        if (!isUidSignature()) {
            Log.e("SpenGestureManagerService", "no permission to use unregisterAirGestureListener() : " + Binder.getCallingUid());
        } else {
            Log.i("SpenGestureManagerService", "unregisterAirGestureListener");
            RemoteCallbackList remoteCallbackList = this.mAirGestureListener;
            if (remoteCallbackList != null) {
                remoteCallbackList.unregister(iAirGestureListener);
            }
        }
    }

    public final void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        checkSmartClipMetaExtractionPermission();
        RemoteCallbackList remoteCallbackList = this.mBleSpenChargeLockStateChangedListeners;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iBleSpenChargeLockStateChangedListener);
        }
    }

    public final void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        RemoteCallbackList remoteCallbackList = this.mHoverListeners;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iSpenGestureHoverListener);
        }
    }

    public final void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        if (!isUidSignature()) {
            Log.e("SpenGestureManagerService", "no permission to use unregisterInputMethodInfoChangeListener() : " + Binder.getCallingUid());
        } else {
            RemoteCallbackList remoteCallbackList = this.mInputInfoChangeListeners;
            if (remoteCallbackList != null) {
                remoteCallbackList.unregister(iInputMethodInfoChangeListener);
            }
        }
    }

    public final void writeBleSpenCommand(String str) {
        Log.i("SpenGestureManagerService", "writeBleSpenCommand : " + str);
        checkSmartClipMetaExtractionPermission();
        BleSpenManager bleSpenManager = this.mBleSpenManager;
        synchronized (bleSpenManager) {
            if (!bleSpenManager.isSupportBleSpen()) {
                Log.e("BleSpenManager", "writeBleSpenCommand : BLE Spen is not supported");
                return;
            }
            try {
                bleSpenManager.mSemInputDeviceManager.setSpenBleChargeMode(Integer.parseInt(str));
            } catch (Exception e) {
                Log.e("BleSpenManager", "mSemInputDeviceManager.setSpenBleChargeMode: Exception:" + e);
            }
        }
    }

    public final synchronized void writeDigitizerOnOffCommand(boolean z) {
        Log.i("SpenGestureManagerService", "writeDigitizerOnOffCommand = " + z);
        try {
            this.mSemInputDeviceManager.setSpenPower(z ? 1 : 0);
        } catch (Exception e) {
            Log.e("SpenGestureManagerService", "mSemInputDeviceManager.setSpenPower: Exception: " + e);
        }
    }
}
