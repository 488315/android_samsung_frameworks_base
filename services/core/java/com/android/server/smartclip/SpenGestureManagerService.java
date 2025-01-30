package com.android.server.smartclip;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
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
import android.telephony.TelephonyManager;
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
import com.android.internal.util.FrameworkStatsLog;
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
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class SpenGestureManagerService extends ISpenGestureService.Stub {
    public static boolean AC_DEBUG = true;
    public static final int EVENT_STATE_PEN_BUTTON_PRESSED = 32;
    public static String TAG = "SpenGestureManagerService";
    public static String USE_APP_FEATURE_NAME_SPAT = "SPAT";
    public static String USE_APP_FEATURE_NAME_SPEN = "SPEN";
    public static Context mContext;
    public int ALLOWANCE_HOVER_TIME;
    public int ALLOWANCE_RANGE_X;
    public int ALLOWANCE_RANGE_Y;
    public ServiceConnection mAcServiceConnection;
    public RemoteCallbackList mAirGestureListener;
    public RemoteCallbackList mBleSpenChargeLockStateChangedListeners;
    public BleSpenManager mBleSpenManager;
    public EditorInfo mEditorInfo;
    public GestureDetector mGestureDetector;
    public final Handler mHandler;
    public RemoteCallbackList mHoverListeners;
    public boolean mHoverStayDetectEnabled;
    public IRemoteInputConnection mInputConnection;
    public RemoteCallbackList mInputInfoChangeListeners;
    public InputManager mInputManager;
    public Boolean mIsPenInserted;
    public Boolean mIsPenReversed;
    public Point mLastDoubleTapPosition;
    public int mMissingMethodFlags;
    public List mPenDataStructArray;
    public SPenGestureInputEventReceiver mSPenGestureInputEventReceiver;
    public SemInputDeviceManager mSemInputDeviceManager;
    public TelephonyManager mTelephonyManager;
    public SpenThemeManager mThemeManager;
    public boolean mIsEnableLockScreenQuickNote = false;
    public Object mDataExtractionSync = new Object();
    public Messenger mAcService = null;
    public boolean mAcIsBound = false;
    public boolean mAcIsHoverOccuredBeforeTouchDown = false;
    public boolean mAcIsPenButtonPressed = false;
    public boolean mAcIsTouchDowned = false;
    public boolean mAcIsFloatingIconEnabled = false;
    public boolean mAcIsScreenOffMemoShowing = false;
    public boolean mAcAutoFloatingIconMode = true;
    public long mAcButtonPressTouchDownTime = 0;
    public long mAcPenButtonPressedTime = 0;
    public long mAcLastStartTime = 0;
    public int mBatteryOnlineStatus = 1;
    public PenDetectionInfo mAcPendingPenDetectionInfo = null;
    public boolean mClearCoverOpened = true;
    public boolean mInboxSPen = false;
    public int mSpenUspLevel = -1;
    public int mScreenOffReason = -1;
    public CoverManager mCoverManager = null;
    public long mSpenUsingStartTime = 0;
    public SmartClipRemoteRequestSyncManager mSmartClipRemoteRequestSyncManager = new SmartClipRemoteRequestSyncManager();
    public boolean mBootComplete = false;
    public long mLastScreenOffDoubleTapTime = 0;
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.smartclip.SpenGestureManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String str;
            String action = intent.getAction();
            if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                SpenGestureManagerService.this.mBatteryOnlineStatus = intent.getIntExtra("online", 1);
                return;
            }
            Log.i(SpenGestureManagerService.TAG, "Receive broadcast : " + action);
            String str2 = null;
            if ("com.samsung.pen.INSERT".equals(action)) {
                if (isInitialStickyBroadcast()) {
                    return;
                }
                boolean booleanExtra = intent.getBooleanExtra("penInsert", true);
                boolean booleanExtra2 = intent.getBooleanExtra("isBoot", true);
                boolean booleanExtra3 = intent.getBooleanExtra("isReversed", false);
                Log.i(SpenGestureManagerService.TAG, "penInsert : " + booleanExtra + ", isBoot : " + booleanExtra2 + ", isReversed : " + booleanExtra3);
                ComponentName topMostPackage = SpenGestureManagerService.this.getTopMostPackage();
                String packageName = topMostPackage != null ? topMostPackage.getPackageName() : "";
                if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen() && (booleanExtra2 || booleanExtra)) {
                    if (SpenGestureManagerService.this.mBleSpenManager.isAirActionSettingEnabled()) {
                        SpenGestureManagerService.this.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext, null);
                    } else {
                        Log.i(SpenGestureManagerService.TAG, "onReceive : air action is disabled. startBlindChargeService");
                        SpenGestureManagerService.this.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext, null);
                    }
                }
                if (booleanExtra) {
                    long currentTimeMillis = System.currentTimeMillis() - SpenGestureManagerService.this.mSpenUsingStartTime;
                    str = "";
                    if (SpenGestureManagerService.this.mSpenUsingStartTime != 0) {
                        SpenGestureManagerService.this.calculateSpenUsingDuration(currentTimeMillis);
                    }
                    SpenGestureManagerService.this.mSpenUsingStartTime = 0L;
                    SpenGestureManagerService.this.sendLogSpenInsertInfo(packageName, SpenGestureManagerService.USE_APP_FEATURE_NAME_SPAT);
                } else {
                    str = "";
                    SpenGestureManagerService.this.mSpenUsingStartTime = System.currentTimeMillis();
                    SpenGestureManagerService.this.sendLogSpenInsertInfo(packageName, SpenGestureManagerService.USE_APP_FEATURE_NAME_SPEN);
                }
                SpenGestureManagerService.this.mIsPenInserted = Boolean.valueOf(booleanExtra);
                SpenGestureManagerService.this.mIsPenReversed = Boolean.valueOf(booleanExtra3);
                Log.i(SpenGestureManagerService.TAG, "Start AirCommandUiService. #1");
                Bundle bundle = new Bundle(intent.getExtras());
                bundle.putString("action", intent.getAction());
                bundle.putString("topComponent", topMostPackage != null ? topMostPackage.getClassName() : str);
                bundle.putInt("batteryStatus", SpenGestureManagerService.this.mBatteryOnlineStatus);
                bundle.putBoolean("coverOpened", SpenGestureManagerService.this.mClearCoverOpened);
                bundle.putBoolean("isReversed", SpenGestureManagerService.this.mIsPenReversed.booleanValue());
                SpenGestureManagerService.this.startAirCommandUiService(bundle);
                return;
            }
            if ("com.sec.android.intent.action.BLACK_MEMO".equals(action)) {
                try {
                    str2 = intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                } catch (Exception unused) {
                }
                SpenGestureManagerService.this.mAcIsScreenOffMemoShowing = KnoxCustomManagerService.SHOW.equals(str2);
                return;
            }
            if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                    boolean booleanExtra4 = intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                    Intent intent2 = new Intent("com.samsung.android.service.aircommand.remotespen.action.COMMON_BROADCAST");
                    intent2.putExtra("action", "android.intent.action.AIRPLANE_MODE");
                    intent2.putExtra("isEnabled", booleanExtra4);
                    intent2.setPackage("com.samsung.android.service.aircommand");
                    context.sendBroadcast(intent2);
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                Log.i(SpenGestureManagerService.TAG, "onReceive : User switched");
                if (SpenGestureManagerService.this.mBleSpenManager.isSupportBleSpen()) {
                    if (SpenGestureManagerService.this.mIsPenInserted != null) {
                        if (SpenGestureManagerService.this.mBleSpenManager.isAirActionSettingEnabled()) {
                            SpenGestureManagerService.this.mBleSpenManager.startRemoteSpenService(SpenGestureManagerService.mContext, null);
                        } else {
                            Log.i(SpenGestureManagerService.TAG, "onReceive : air action is disabled. startBlindChargeService");
                            SpenGestureManagerService.this.mBleSpenManager.startBlindChargeService(SpenGestureManagerService.mContext, null);
                        }
                        Log.i(SpenGestureManagerService.TAG, "Start AirCommandUiService. #4");
                        Bundle bundle2 = new Bundle(intent.getExtras());
                        bundle2.putString("action", intent.getAction());
                        bundle2.putBoolean("penInsert", SpenGestureManagerService.this.mIsPenInserted.booleanValue());
                        bundle2.putBoolean("isBoot", true);
                        SpenGestureManagerService.this.startAirCommandUiService(bundle2);
                        return;
                    }
                    Log.i(SpenGestureManagerService.TAG, "onReceive : SPen insertion state is not detected yet");
                    return;
                }
                return;
            }
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                SpenGestureManagerService.this.mBootComplete = true;
                if (!SpenGestureManagerService.this.isSupportSpen()) {
                    Log.i(SpenGestureManagerService.TAG, "onReceive : This model is not support S pen");
                    return;
                }
                if (SpenGarageSpecManager.getInstance().isBundledSpenSupported()) {
                    Log.i(SpenGestureManagerService.TAG, "onReceive : This model is bundle pen provided model");
                } else {
                    if (SpenGestureManagerService.this.isDigitizerEnabled(SpenGestureManagerService.mContext)) {
                        return;
                    }
                    Log.i(SpenGestureManagerService.TAG, "onReceive : isDigitizerEnabled = false");
                    SpenGestureManagerService.this.writeDigitizerOnOffCommand(false);
                }
            }
        }
    };
    public CoverManager.StateListener mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.smartclip.SpenGestureManagerService.2
        public void onCoverStateChanged(CoverState coverState) {
            if (coverState.getSwitchState()) {
                Log.i(SpenGestureManagerService.TAG, " onCoverStateChanged : SWITCH_STATE_COVER_OPEN");
                SpenGestureManagerService.this.mClearCoverOpened = true;
            } else {
                Log.i(SpenGestureManagerService.TAG, " onCoverStateChanged : SWITCH_STATE_COVER_CLOSE");
                SpenGestureManagerService.this.mClearCoverOpened = false;
            }
            if (SpenGestureManagerService.this.mIsPenInserted == null || SpenGestureManagerService.this.mIsPenInserted.booleanValue()) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("cover_opened", SpenGestureManagerService.this.mClearCoverOpened);
            SpenGestureManagerService.this.sendAirCommandStateChangeIntent(bundle);
        }
    };
    public Runnable mPenDoubleTap = new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.5
        @Override // java.lang.Runnable
        public void run() {
            ContentResolver contentResolver = SpenGestureManagerService.mContext.getContentResolver();
            SpenGestureManagerService.this.mIsEnableLockScreenQuickNote = Settings.Secure.getIntForUser(contentResolver, "lock_screen_quick_note", 0, -2) != 0;
            if (!(Settings.System.semGetIntForUser(contentResolver, "spen_double_tap_launch", 1, -2) != 0)) {
                Log.i(SpenGestureManagerService.TAG, "Double tap is disable : Spen double tap launch disabled");
                return;
            }
            if (SpenGestureManagerService.this.keyguardOn() && !SpenGestureManagerService.this.mIsEnableLockScreenQuickNote) {
                Log.i(SpenGestureManagerService.TAG, "Double tap is disable : " + SpenGestureManagerService.this.mIsEnableLockScreenQuickNote);
                return;
            }
            if (SpenGestureManagerService.this.isScreenPinningEnabled()) {
                Log.e(SpenGestureManagerService.TAG, "Double tap is disabled : Screen pinning mode enabled");
                return;
            }
            if (SpenGestureManagerService.this.isAppSwitchingBlocked()) {
                Log.e(SpenGestureManagerService.TAG, "Double tap is disabled : App switching is blocked");
            } else if (SpenGestureManagerService.this.isDoubleTapBlockedAppsByMetaData()) {
                Log.e(SpenGestureManagerService.TAG, "Double tap is disabled by MetaData : This is a double-tap blocked app.");
            } else {
                Log.i(SpenGestureManagerService.TAG, "Double tapped!");
                SpenGestureManagerService.this.launchDoubleTapAction();
            }
        }
    };

    public class PenDataStruct {
        public long EnterTime;
        public int RawX;
        public int RawY;

        public PenDataStruct() {
        }
    }

    public class PenDetectionInfo {
        public int action;
        public long eventTime;
        public String penName;

        public PenDetectionInfo() {
        }
    }

    public SpenGestureManagerService(Context context, WindowManagerService windowManagerService) {
        this.mInputManager = null;
        this.mSPenGestureInputEventReceiver = null;
        Handler handler = new Handler() { // from class: com.android.server.smartclip.SpenGestureManagerService.6
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 12312:
                        SpenGestureManagerService.this.broastcastHoverEvent(10);
                        break;
                    case 12313:
                        Object obj = message.obj;
                        if (obj instanceof PenDetectionInfo) {
                            SpenGestureManagerService.this.sendPenDetectionInfo((PenDetectionInfo) obj);
                            break;
                        }
                        break;
                    case 12314:
                        SpenGestureManagerService.this.sendDefferedPenDetectionInfo();
                        break;
                    case 12315:
                        SpenGestureManagerService.this.broastcastHoverEvent(9);
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        };
        this.mHandler = handler;
        this.mAcServiceConnection = new ServiceConnection() { // from class: com.android.server.smartclip.SpenGestureManagerService.7
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(SpenGestureManagerService.TAG, "onServiceConnected : " + componentName + ", " + iBinder);
                SpenGestureManagerService.this.mAcService = new Messenger(iBinder);
                SpenGestureManagerService.this.mAcIsBound = true;
                SpenGestureManagerService.this.sendDefferedPenDetectionInfo();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(SpenGestureManagerService.TAG, "onServiceDisconnected : " + componentName);
                SpenGestureManagerService.mContext.unbindService(SpenGestureManagerService.this.mAcServiceConnection);
                SpenGestureManagerService.this.mAcService = null;
                SpenGestureManagerService.this.mAcIsBound = false;
            }
        };
        this.ALLOWANCE_RANGE_X = 10;
        this.ALLOWANCE_RANGE_Y = 10;
        this.ALLOWANCE_HOVER_TIME = 300;
        this.mHoverStayDetectEnabled = false;
        this.mPenDataStructArray = new ArrayList();
        mContext = context;
        Log.i(TAG, "SpenGestureManagerService(Context context, WindowManagerService Wm)");
        initSpenGesture();
        registerPackageMonitor();
        this.mInputManager = (InputManager) mContext.getSystemService("input");
        if (isSupportSpen()) {
            SPenGestureInputEventReceiver sPenGestureInputEventReceiver = new SPenGestureInputEventReceiver();
            this.mSPenGestureInputEventReceiver = sPenGestureInputEventReceiver;
            windowManagerService.registerPointerEventListener(sPenGestureInputEventReceiver, 0);
        }
        this.mBleSpenManager = new BleSpenManager(context);
        this.mSemInputDeviceManager = (SemInputDeviceManager) mContext.getSystemService("SemInputDeviceManagerService");
        checkSettingCondition();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.pen.INSERT");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        if (this.mInboxSPen) {
            intentFilter.addAction("com.sec.android.intent.action.BLACK_MEMO");
        }
        if (this.mBleSpenManager.isSupportBleSpen()) {
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        }
        mContext.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
        new AcSettingsObserver(handler).observe();
        if (isSupportSpen()) {
            this.mThemeManager = new SpenThemeManager(mContext);
        } else {
            Log.i(TAG, "Theme isn't supported. Not a spen model.");
        }
    }

    public final void initSpenGesture() {
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
        this.mSpenUspLevel = i;
        if (i % 10 == 5) {
            this.mInboxSPen = true;
        } else {
            this.mInboxSPen = false;
        }
        this.mTelephonyManager = (TelephonyManager) mContext.getSystemService("phone");
        this.mCoverManager = new CoverManager(mContext);
        this.mClearCoverOpened = initCoverState();
        CoverManager coverManager = this.mCoverManager;
        if (coverManager != null) {
            coverManager.registerListener(this.mCoverStateListener);
        } else {
            Log.i(TAG, "initSpenGesture() : mCoverManager is null!!!!");
        }
        this.mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.server.smartclip.SpenGestureManagerService.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                Log.i(SpenGestureManagerService.TAG, "Pen DoubleTap : x=" + rawX + ", y=" + rawY + ", action=" + motionEvent.getAction());
                Cursor query = SpenGestureManagerService.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider2/KnoxCustomManagerService1"), null, "getSealedState", null, null);
                if (query != null) {
                    try {
                        query.moveToFirst();
                        if (query.getString(query.getColumnIndex("getSealedState")).equals("true")) {
                            Log.i(SpenGestureManagerService.TAG, "now KNOX state : can't excute Double Tap");
                            query.close();
                            return true;
                        }
                    } finally {
                        query.close();
                    }
                }
                SpenGestureManagerService.this.mLastDoubleTapPosition = new Point(rawX, rawY);
                SpenGestureManagerService.this.mHandler.post(SpenGestureManagerService.this.mPenDoubleTap);
                return true;
            }
        });
    }

    public final void registerPackageMonitor() {
        PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.android.server.smartclip.SpenGestureManagerService.4
            public void onPackageDataCleared(String str, int i) {
                if ("com.samsung.android.service.aircommand".equals(str)) {
                    Settings.System.putInt(SpenGestureManagerService.mContext.getContentResolver(), "air_cmd_mode", 0);
                }
            }
        };
        Context context = mContext;
        packageMonitor.register(context, context.getMainLooper(), UserHandle.ALL, true);
    }

    public final boolean initCoverState() {
        CoverManager coverManager = this.mCoverManager;
        if (coverManager != null) {
            CoverState coverState = coverManager.getCoverState();
            if (coverState != null) {
                Log.i(TAG, "initCoverState() : " + coverState.getSwitchState());
                return coverState.getSwitchState();
            }
            Log.i(TAG, "initCoverState() : state is null");
            return true;
        }
        Log.i(TAG, "initCoverState() : mCoverManager is null!!!!");
        return true;
    }

    public final boolean isSupportSpen() {
        return this.mSpenUspLevel > 0;
    }

    public void sendSmartClipRemoteRequestResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) {
        if (smartClipRemoteRequestResult != null) {
            Log.i(TAG, "sendSmartClipRemoteRequestResult : requestId=" + smartClipRemoteRequestResult.mRequestId + " requestType=" + smartClipRemoteRequestResult.mRequestType);
            this.mSmartClipRemoteRequestSyncManager.notifyResult(smartClipRemoteRequestResult);
            return;
        }
        Log.e(TAG, "sendSmartClipRemoteRequestResult : result is null!");
    }

    public final void checkSmartClipMetaExtractionPermission() {
        checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA");
    }

    public final void checkInputEventInjectionPermission() {
        checkPermission("android.permission.INJECT_EVENTS");
    }

    public final void checkChangeSpenThemePermission() {
        checkPermission("com.samsung.android.permission.CHANGE_SPEN_THEME");
    }

    public final void checkPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == Process.myUid()) {
            return;
        }
        if (mContext.checkCallingPermission(str) == 0) {
            return;
        }
        Log.e(TAG, "checkPermission : Requires " + str + " permission. caller PID=" + Binder.getCallingPid() + " UID=" + callingUid);
        throw new SecurityException("Requires " + str + " permission");
    }

    public SemSmartClipDataRepository getSmartClipDataByScreenRect(Rect rect, IBinder iBinder, int i, int i2) {
        SemSmartClipDataRepository semSmartClipDataRepository;
        checkSmartClipMetaExtractionPermission();
        try {
            synchronized (this.mDataExtractionSync) {
                if (rect == null) {
                    rect = getFullScreenRect();
                }
                IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
                asInterface.dispatchSmartClipRemoteRequest(rect.centerX(), rect.centerY(), new SmartClipRemoteRequestInfo(allocateNewRequestId, 1, i2, new SmartClipDataExtractionEvent(allocateNewRequestId, rect, i)), iBinder);
                SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId, 500);
                if (waitResult != null) {
                    SmartClipDataExtractionResponse smartClipDataExtractionResponse = waitResult.mResultData;
                    if (smartClipDataExtractionResponse != null) {
                        semSmartClipDataRepository = smartClipDataExtractionResponse.mRepository;
                        moveMetaFilesToLocalStorage(semSmartClipDataRepository);
                    } else {
                        semSmartClipDataRepository = null;
                    }
                    Log.i(TAG, "getSmartClipDataByScreenRect : wait is unlocked. Repository = " + semSmartClipDataRepository);
                    return semSmartClipDataRepository;
                }
                Log.e(TAG, "getSmartClipDataByScreenRect : result is null!!");
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "getSmartClipDataByScreenRect : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public Bundle getScrollableAreaInfo(Rect rect, IBinder iBinder) {
        Log.i(TAG, "getScrollableRect()");
        checkSmartClipMetaExtractionPermission();
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
            asInterface.dispatchSmartClipRemoteRequest(rect.left + (rect.width() / 2), rect.top + (rect.height() / 2), new SmartClipRemoteRequestInfo(allocateNewRequestId, 4, 1, (Parcelable) null), iBinder);
            SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId, 500);
            if (waitResult != null) {
                Log.i(TAG, "getScrollableRect : Result=" + waitResult.mResultData);
                return (Bundle) waitResult.mResultData;
            }
            Log.e(TAG, "getScrollableRect : Result is null!!");
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getScrollableAreaInfo : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public Bundle getScrollableViewInfo(Rect rect, int i, IBinder iBinder) {
        Log.i(TAG, "getScrollableViewInfo()");
        checkSmartClipMetaExtractionPermission();
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(true);
            Bundle bundle = new Bundle();
            bundle.putInt("hashCode", i);
            asInterface.dispatchSmartClipRemoteRequest(rect.left + (rect.width() / 2), rect.top + (rect.height() / 2), new SmartClipRemoteRequestInfo(allocateNewRequestId, 5, 1, bundle), iBinder);
            SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId, 500);
            if (waitResult != null) {
                Log.i(TAG, "getScrollableViewInfo : Result=" + waitResult.mResultData);
                return (Bundle) waitResult.mResultData;
            }
            Log.e(TAG, "getScrollableViewInfo : Result is null!!");
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getScrollableViewInfo : Exception thrown! e = " + e.toString(), e);
            return null;
        }
    }

    public void injectInputEvent(int i, int i2, InputEvent[] inputEventArr, boolean z, IBinder iBinder) {
        checkInputEventInjectionPermission();
        try {
            synchronized (this.mDataExtractionSync) {
                IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                int allocateNewRequestId = this.mSmartClipRemoteRequestSyncManager.allocateNewRequestId(z);
                Bundle bundle = new Bundle();
                bundle.putParcelableArray("events", inputEventArr);
                bundle.putBoolean("waitUntilConsume", z);
                asInterface.dispatchSmartClipRemoteRequest(i, i2, new SmartClipRemoteRequestInfo(allocateNewRequestId, 3, 1, bundle), iBinder);
                if (z) {
                    SmartClipRemoteRequestResult waitResult = this.mSmartClipRemoteRequestSyncManager.waitResult(allocateNewRequestId, 500);
                    if (waitResult != null) {
                        Log.i(TAG, "injectInputEvent : Result=" + waitResult.mResultData);
                    } else {
                        Log.e(TAG, "injectInputEvent : Result is null!!");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "injectInputEvent : Exception thrown! e = " + e.toString(), e);
        }
    }

    public final Rect getFullScreenRect() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return new Rect(0, 0, Math.abs(displayMetrics.widthPixels), Math.abs(displayMetrics.heightPixels));
    }

    public final void checkSettingCondition() {
        this.mIsEnableLockScreenQuickNote = Settings.Secure.getIntForUser(mContext.getContentResolver(), "lock_screen_quick_note", 0, -2) == 1;
        Log.i(TAG, "checkSettingCondition, LOCK_SCREEN_QUICK_NOTE : " + this.mIsEnableLockScreenQuickNote);
    }

    public final class SPenGestureInputEventReceiver implements WindowManagerPolicyConstants.PointerEventListener {
        public ICustomFrequencyManager sCfmsService;

        public SPenGestureInputEventReceiver() {
            this.sCfmsService = null;
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

        public void onPointerEvent(MotionEvent motionEvent) {
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            try {
                int action = motionEvent.getAction();
                boolean z = (motionEvent.getButtonState() & SpenGestureManagerService.EVENT_STATE_PEN_BUTTON_PRESSED) != 0;
                if (SpenGestureManagerService.this.mSpenUspLevel >= 3) {
                    SpenGestureManagerService.this.processMotionEventForAirCMD(motionEvent, action, z);
                    if (SpenGestureManagerService.this.mHoverListeners != null && SpenGestureManagerService.this.mHoverListeners.getRegisteredCallbackCount() > 0) {
                        SpenGestureManagerService.this.checkHoverStay(motionEvent, action, z);
                    }
                    if (z && action != 7 && action != 2) {
                        Log.i(SpenGestureManagerService.TAG, "onPointerEvent 4 : Action=" + MotionEvent.actionToString(action) + ", sideButtonPressed=" + z + ", coverOpened=" + SpenGestureManagerService.this.mClearCoverOpened);
                    }
                    boolean z2 = z && SpenGestureManagerService.this.mClearCoverOpened;
                    if (action == 9) {
                        sendEventToCfmsService(action);
                        Message message = new Message();
                        message.what = 12315;
                        SpenGestureManagerService.this.mHandler.sendMessage(message);
                    } else if (action == 10) {
                        sendEventToCfmsService(action);
                        Message message2 = new Message();
                        message2.what = 12312;
                        SpenGestureManagerService.this.mHandler.sendMessageDelayed(message2, 50L);
                    } else if (action == 0) {
                        if (SpenGestureManagerService.this.mHandler.hasMessages(12312)) {
                            SpenGestureManagerService.this.mHandler.removeMessages(12312);
                            Log.i(SpenGestureManagerService.TAG, "[HOVER] sending hover exit br is canceled by touch event.");
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
                                if (!"sec_unused_e-pen".equals(SpenGestureManagerService.this.getPenName(motionEvent))) {
                                    SpenGestureManagerService.this.mGestureDetector.onTouchEvent(motionEvent);
                                    break;
                                } else {
                                    break;
                                }
                            case 1:
                            case 3:
                            case 6:
                                if (!"sec_unused_e-pen".equals(SpenGestureManagerService.this.getPenName(motionEvent))) {
                                    SpenGestureManagerService.this.mGestureDetector.onTouchEvent(motionEvent);
                                    break;
                                } else {
                                    break;
                                }
                            case 4:
                            case 8:
                            default:
                                Log.d(SpenGestureManagerService.TAG, "onPointerEvent : default");
                                break;
                            case 7:
                            case 9:
                            case 10:
                                break;
                        }
                    }
                }
            } finally {
                long elapsedRealtimeNanos2 = SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos;
                if (elapsedRealtimeNanos2 > 1000000000) {
                    Log.e(SpenGestureManagerService.TAG, "onPointerEvent : total time = " + elapsedRealtimeNanos2);
                }
            }
        }
    }

    public final void processMotionEventForAirCMD(MotionEvent motionEvent, int i, boolean z) {
        boolean z2 = false;
        if (motionEvent.getToolType(0) != 2) {
            return;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 5) {
                    if (i != 6) {
                        if (i != 7) {
                            if (i == 9) {
                                if (this.mAcAutoFloatingIconMode && !this.mAcIsScreenOffMemoShowing) {
                                    if (motionEvent.getDownTime() <= 0) {
                                        this.mAcIsHoverOccuredBeforeTouchDown = true;
                                        dispatchPenDetectionInfo(i, motionEvent.getEventTime(), getPenName(motionEvent), 100L);
                                    }
                                }
                                this.mAcIsPenButtonPressed = false;
                                long j = this.mAcButtonPressTouchDownTime;
                                if (j > 0 && j == motionEvent.getDownTime()) {
                                    z2 = true;
                                }
                                this.mAcIsTouchDowned = z2;
                                return;
                            }
                            if (i == 10 && this.mAcAutoFloatingIconMode && !this.mAcIsScreenOffMemoShowing) {
                                InputManager inputManager = this.mInputManager;
                                if (inputManager != null && inputManager.semGetScanCodeState(-1, -256, 320) == 0) {
                                    if (this.mHandler.hasMessages(12313)) {
                                        this.mHandler.removeMessages(12313);
                                    }
                                    dispatchPenDetectionInfo(i, motionEvent.getEventTime(), getPenName(motionEvent), 0L);
                                    this.mAcIsHoverOccuredBeforeTouchDown = false;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (this.mAcButtonPressTouchDownTime > 0 && !z) {
                            this.mAcIsTouchDowned = false;
                        }
                        if (this.mAcIsTouchDowned) {
                            return;
                        }
                        if (z && !this.mAcIsPenButtonPressed) {
                            this.mAcIsPenButtonPressed = true;
                            this.mAcPenButtonPressedTime = motionEvent.getEventTime();
                            return;
                        }
                        if (z || !this.mAcIsPenButtonPressed) {
                            return;
                        }
                        this.mAcIsPenButtonPressed = false;
                        long eventTime = motionEvent.getEventTime() - this.mAcPenButtonPressedTime;
                        if (eventTime <= 0 || eventTime > 800) {
                            return;
                        }
                        int rawX = (int) motionEvent.getRawX();
                        int rawY = (int) motionEvent.getRawY();
                        String str = rawX < mContext.getResources().getDisplayMetrics().widthPixels / 2 ? "left" : "right";
                        if (!this.mClearCoverOpened) {
                            Log.i(TAG, "Can not start AirCommandUiService. #2");
                            return;
                        }
                        long uptimeMillis = SystemClock.uptimeMillis();
                        long j2 = uptimeMillis - this.mAcLastStartTime;
                        if (j2 > 0 && j2 < 400) {
                            Log.i(TAG, "Can not start AirCommandUiService. #3." + j2);
                            return;
                        }
                        this.mAcLastStartTime = uptimeMillis;
                        Log.i(TAG, "Start AirCommandUiService. #2");
                        Bundle bundle = new Bundle();
                        bundle.putString("cause", "button_pressed");
                        bundle.putString("direction", str);
                        bundle.putInt("button_press_x", rawX);
                        bundle.putInt("button_press_y", rawY);
                        startAirCommandUiService(bundle);
                        return;
                    }
                }
            }
            if (z && this.mAcButtonPressTouchDownTime == -1) {
                this.mAcButtonPressTouchDownTime = motionEvent.getDownTime();
                return;
            }
            return;
        }
        if (z) {
            this.mAcButtonPressTouchDownTime = motionEvent.getDownTime();
        } else {
            this.mAcButtonPressTouchDownTime = -1L;
        }
        if (!this.mAcAutoFloatingIconMode || this.mAcIsScreenOffMemoShowing || this.mAcIsHoverOccuredBeforeTouchDown) {
            return;
        }
        dispatchPenDetectionInfo(i, motionEvent.getEventTime(), getPenName(motionEvent), 100L);
    }

    public final void startAirCommandUiService(Bundle bundle) {
        if (canStartAirCommand()) {
            if (!SpenGarageSpecManager.getInstance().isBundledSpenSupported()) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("action", "start_remote_spen_service");
                startAirCommandSpenTriggerService(bundle2);
            }
            try {
                Intent intent = new Intent("com.samsung.android.service.aircommand.action.AIR_COMMAND_SERVICE");
                intent.setPackage("com.samsung.android.service.aircommand");
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startServiceAsUser(intent, UserHandle.CURRENT);
                Log.i(TAG, "startAirCommandUiService");
                if (this.mAcAutoFloatingIconMode) {
                    boolean bindServiceAsUser = this.mAcIsBound ? false : mContext.bindServiceAsUser(intent, this.mAcServiceConnection, 0, UserHandle.CURRENT);
                    if (AC_DEBUG) {
                        Log.i(TAG, "bindService.isBound : " + this.mAcIsBound + ".ret : " + bindServiceAsUser);
                    }
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to start AirCommandUiService. " + e);
            }
        }
    }

    public final void startAirCommandSpenTriggerService(Bundle bundle) {
        if (canStartAirCommand()) {
            try {
                Intent intent = new Intent("com.samsung.android.service.aircommand.action.SPEN_TRIGGER_SERVICE");
                intent.setPackage("com.samsung.android.service.aircommand");
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startServiceAsUser(intent, UserHandle.CURRENT);
                Log.i(TAG, "start start spentrigger service.");
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to start Spen trigger AirCommand. e = " + e);
            }
        }
    }

    public final boolean canStartAirCommand() {
        String str;
        if (Settings.Global.getInt(mContext.getContentResolver(), "device_provisioned", 0) == 0) {
            str = "SetupWizard on";
        } else if (FactoryTest.isFactoryBinary()) {
            str = "FactoryBinary";
        } else {
            str = this.mAcIsScreenOffMemoShowing ? "ScreenOffMemo on" : null;
        }
        if (str == null) {
            return true;
        }
        Log.i(TAG, "Failed to start AirCommand." + str);
        return false;
    }

    public final void sendAirCommandStateChangeIntent(Bundle bundle) {
        Intent intent = new Intent("com.samsung.android.aircommand.intent.action.STATE_CHANGE");
        intent.setPackage("com.samsung.android.service.aircommand");
        intent.putExtras(bundle);
        mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public final void dispatchPenDetectionInfo(int i, long j, String str, long j2) {
        PenDetectionInfo penDetectionInfo = new PenDetectionInfo();
        penDetectionInfo.action = i;
        penDetectionInfo.eventTime = j;
        penDetectionInfo.penName = str;
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 12313;
        obtainMessage.obj = penDetectionInfo;
        this.mHandler.sendMessageDelayed(obtainMessage, j2);
    }

    public final void sendPenDetectionInfo(PenDetectionInfo penDetectionInfo) {
        if (AC_DEBUG) {
            Log.i(TAG, "sendPenDetectionInfo." + penDetectionInfo.action);
        }
        if (!this.mAcIsBound) {
            Log.i(TAG, "Start AirCommandUiService. #0");
            Bundle bundle = new Bundle();
            bundle.putString("cause", "pen_detected");
            String str = penDetectionInfo.penName;
            if (str != null) {
                bundle.putString("penName", str);
            }
            startAirCommandUiService(bundle);
            this.mAcPendingPenDetectionInfo = penDetectionInfo;
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("eventTime", penDetectionInfo.eventTime);
        bundle2.putString("penName", penDetectionInfo.penName);
        Message obtain = Message.obtain(null, 1, penDetectionInfo.action, 0);
        obtain.setData(bundle2);
        try {
            Messenger messenger = this.mAcService;
            if (messenger != null) {
                messenger.send(obtain);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send the pen detection info", e);
        }
    }

    public final void sendDefferedPenDetectionInfo() {
        if (this.mAcPendingPenDetectionInfo == null) {
            return;
        }
        if (AC_DEBUG) {
            Log.i(TAG, "sendDefferedPenDetectionInfo." + this.mAcPendingPenDetectionInfo.action);
        }
        Bundle bundle = new Bundle();
        bundle.putLong("eventTime", this.mAcPendingPenDetectionInfo.eventTime);
        bundle.putString("penName", this.mAcPendingPenDetectionInfo.penName);
        Message obtain = Message.obtain(null, 1, this.mAcPendingPenDetectionInfo.action, 0);
        obtain.setData(bundle);
        try {
            Messenger messenger = this.mAcService;
            if (messenger != null) {
                messenger.send(obtain);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send the pen detection info", e);
        }
        this.mAcPendingPenDetectionInfo = null;
    }

    public final void onFloatingIconSettingChanged(boolean z) {
        Log.i(TAG, "onFloatingIconSettingChanged : " + z);
        if (z) {
            this.mAcIsFloatingIconEnabled = true;
            if (true == this.mBootComplete || true == SpenGarageSpecManager.getInstance().isBundledSpenSupported()) {
                Bundle bundle = new Bundle();
                bundle.putString("cause", "floating_on");
                startAirCommandUiService(bundle);
                return;
            }
            return;
        }
        this.mAcIsFloatingIconEnabled = false;
    }

    public final void onSpenDigitizerOnOffChanged(boolean z) {
        Log.i(TAG, "onSpenDigitizerOnOffChanged : " + z);
        writeDigitizerOnOffCommand(z);
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putString("action", "digitizer_enable_changed");
            bundle.putBoolean("digitizer_enable", z);
            startAirCommandSpenTriggerService(bundle);
        }
    }

    public class AcSettingsObserver extends ContentObserver {
        public AcSettingsObserver(Handler handler) {
            super(handler);
        }

        public void observe() {
            ContentResolver contentResolver = SpenGestureManagerService.mContext.getContentResolver();
            SpenGestureManagerService.this.onFloatingIconSettingChanged(isFloatingIconEnabled() && isAirCommandSettingEnabled());
            contentResolver.registerContentObserver(Settings.System.getUriFor("air_cmd_use_minimized"), false, this, -1);
            contentResolver.registerContentObserver(Settings.System.getUriFor("air_button_onoff"), false, this);
            contentResolver.registerContentObserver(Settings.System.getUriFor("pen_digitizer_enabled"), false, new ContentObserver(SpenGestureManagerService.this.mHandler) { // from class: com.android.server.smartclip.SpenGestureManagerService.AcSettingsObserver.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    SpenGestureManagerService.this.onSpenDigitizerOnOffChanged(Settings.System.semGetIntForUser(SpenGestureManagerService.mContext.getContentResolver(), "pen_digitizer_enabled", 1, -2) == 1);
                }
            }, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            SpenGestureManagerService.this.onFloatingIconSettingChanged(isFloatingIconEnabled() && isAirCommandSettingEnabled());
        }

        public final boolean isFloatingIconEnabled() {
            return Settings.System.semGetIntForUser(SpenGestureManagerService.mContext.getContentResolver(), "air_cmd_use_minimized", 0, -2) != 0;
        }

        public final boolean isAirCommandSettingEnabled() {
            return Settings.System.getInt(SpenGestureManagerService.mContext.getContentResolver(), "air_button_onoff", 0) == 1;
        }
    }

    public final void checkHoverStay(MotionEvent motionEvent, int i, boolean z) {
        if (motionEvent.getToolType(0) != 2) {
            return;
        }
        if (i == 7) {
            PenDataStruct penDataStruct = new PenDataStruct();
            penDataStruct.RawX = (int) motionEvent.getRawX();
            penDataStruct.RawY = (int) motionEvent.getRawY();
            penDataStruct.EnterTime = System.currentTimeMillis();
            this.mPenDataStructArray.add(penDataStruct);
            int size = this.mPenDataStructArray.size() - 2;
            while (size >= 0) {
                new PenDataStruct();
                int i2 = size - 1;
                PenDataStruct penDataStruct2 = (PenDataStruct) this.mPenDataStructArray.get(size);
                int i3 = penDataStruct2.RawX;
                int i4 = penDataStruct.RawX;
                int i5 = this.ALLOWANCE_RANGE_X;
                if (i3 <= i4 - i5 || i3 >= i4 + i5) {
                    return;
                }
                int i6 = penDataStruct2.RawY;
                int i7 = penDataStruct.RawY;
                int i8 = this.ALLOWANCE_RANGE_Y;
                if (i6 <= i7 - i8 || i6 >= i7 + i8) {
                    return;
                }
                if (penDataStruct2.EnterTime < penDataStruct.EnterTime - this.ALLOWANCE_HOVER_TIME) {
                    this.mPenDataStructArray.clear();
                    Bundle bundle = new Bundle();
                    bundle.putInt("RawX", penDataStruct.RawX);
                    bundle.putInt("RawY", penDataStruct.RawY);
                    Log.i(TAG, "dictionary service start : hover time = " + this.ALLOWANCE_HOVER_TIME + " x = " + this.ALLOWANCE_RANGE_X + " y = " + this.ALLOWANCE_RANGE_Y);
                    broastcastHoverStayEvent(penDataStruct.RawX, penDataStruct.RawY);
                    return;
                }
                size = i2;
            }
            return;
        }
        this.mPenDataStructArray.clear();
    }

    public void setHoverStayDetectEnabled(boolean z) {
        this.mHoverStayDetectEnabled = z;
    }

    public void setHoverStayValues(int i, int i2, int i3) {
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

    public boolean keyguardOn() {
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService("keyguard");
        if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
            return false;
        }
        Log.i(TAG, "SpenGestureManagerService :isKeyguardLocked : true");
        return true;
    }

    public final void launchDoubleTapAction() {
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null && spenThemeManager.canLaunchCustomDoubleTapAction()) {
            this.mThemeManager.launchCustomDoubleTapAction(getFocusedUserHandle(mContext), this.mLastDoubleTapPosition);
        } else {
            launchActionMemo(this.mLastDoubleTapPosition);
        }
        this.mLastDoubleTapPosition = null;
    }

    public final void launchActionMemo(Point point) {
        UserHandle focusedUserHandle = getFocusedUserHandle(mContext);
        if (this.mSpenUspLevel >= 20) {
            Intent intent = new Intent("com.samsung.action.POPUP_NOTE_DOUBLETAB");
            intent.setClassName("com.samsung.android.app.notes", "com.samsung.android.app.notes.popupnote.PopupNoteService");
            if (point != null) {
                intent.putExtra("tapX", point.x);
                intent.putExtra("tapY", point.y);
            }
            mContext.startServiceAsUser(intent, focusedUserHandle);
            Log.d(TAG, "launchActionMemo : Trying to launch Samsung notes action memo. tapPos=" + point);
            return;
        }
        Intent intent2 = new Intent("com.samsung.action.MINI_MODE_SERVICE");
        intent2.setClassName("com.samsung.android.snote", "com.samsung.android.snote.control.ui.quickmemo.service.QuickMemo_Service");
        Log.d(TAG, "launchActionMemo : Trying to launch Snote aciton memo..");
        if (mContext.startServiceAsUser(intent2, focusedUserHandle) == null) {
            Log.d(TAG, "launchActionMemo : Snote action memo launch failed. Trying to launch diotek PenMemo...");
            Intent intent3 = new Intent("com.samsung.action.MINI_MODE_SERVICE");
            intent3.setClassName("com.diotek.mini_penmemo", "com.diotek.mini_penmemo.Mini_PenMemo_Service");
            if (mContext.startServiceAsUser(intent3, focusedUserHandle) == null) {
                Log.e(TAG, "launchActionMemo : Diotek PenMemo launch failed. Sending legacy quick memo broadcasting");
                mContext.sendBroadcastAsUser(new Intent("android.intent.action.QUICKMEMO_LAUNCH"), focusedUserHandle);
            }
        }
    }

    public final UserHandle getFocusedUserHandle(Context context) {
        SemPersonaManager semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
        int kioskId = semPersonaManager.getKioskId();
        if (kioskId == -1 && (kioskId = semPersonaManager.getFocusedKnoxId()) == 0) {
            kioskId = -2;
        }
        return new UserHandle(kioskId);
    }

    public final boolean moveMetaFilesToLocalStorage(SemSmartClipDataRepository semSmartClipDataRepository) {
        if (semSmartClipDataRepository != null) {
            return true;
        }
        Log.e(TAG, "moveMetaFilesToLocalStorage : Empty repository!");
        return false;
    }

    public void registerHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        if (this.mHoverListeners == null) {
            this.mHoverListeners = new RemoteCallbackList();
        }
        this.mHoverListeners.register(iSpenGestureHoverListener);
    }

    public void unregisterHoverListener(ISpenGestureHoverListener iSpenGestureHoverListener) {
        RemoteCallbackList remoteCallbackList = this.mHoverListeners;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iSpenGestureHoverListener);
        }
    }

    public void registerAirGestureListener(IAirGestureListener iAirGestureListener) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use registerAirGestureListener() : " + Binder.getCallingUid());
            return;
        }
        Log.i(TAG, "registerAirGestureListener");
        if (this.mAirGestureListener == null) {
            this.mAirGestureListener = new RemoteCallbackList();
        }
        this.mAirGestureListener.register(iAirGestureListener);
    }

    public void unregisterAirGestureListener(IAirGestureListener iAirGestureListener) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use unregisterAirGestureListener() : " + Binder.getCallingUid());
            return;
        }
        Log.i(TAG, "unregisterAirGestureListener");
        RemoteCallbackList remoteCallbackList = this.mAirGestureListener;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iAirGestureListener);
        }
    }

    public void setSpenPowerSavingModeEnabled(boolean z) {
        try {
            this.mSemInputDeviceManager.setSpenPowerSavingMode(z ? 1 : 0);
        } catch (Exception e) {
            Log.e(TAG, "mSemInputDeviceManager.setSpenPowerSavingMode: Exception: " + e);
        }
    }

    public void showTouchPointer(final boolean z) {
        Log.i(TAG, "showTouchPointer : " + z);
        checkSmartClipMetaExtractionPermission();
        this.mHandler.post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.8
            @Override // java.lang.Runnable
            public void run() {
                Settings.System.putInt(SpenGestureManagerService.mContext.getContentResolver(), "show_touches", z ? 1 : 0);
            }
        });
    }

    public void setSpenInsertionState(boolean z) {
        Log.i(TAG, "setSpenInsertionState : " + z);
        this.mIsPenInserted = Boolean.valueOf(z);
    }

    public void setScreenOffDoubleTabTime() {
        Log.i(TAG, "setScreenOffDoubleTabTime");
        this.mLastScreenOffDoubleTapTime = System.currentTimeMillis();
    }

    public long getScreenOffDoubleTabTime() {
        return this.mLastScreenOffDoubleTapTime;
    }

    public boolean isSpenInserted() {
        Boolean bool = this.mIsPenInserted;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public boolean isSpenReversed() {
        Boolean bool = this.mIsPenReversed;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public String getBleSpenAddress() {
        Log.i(TAG, "getBleSpenAddress");
        checkSmartClipMetaExtractionPermission();
        return this.mBleSpenManager.getBleSpenAddress();
    }

    public void setBleSpenAddress(String str) {
        Log.i(TAG, "setBleSpenAddress : " + str);
        checkSmartClipMetaExtractionPermission();
        this.mBleSpenManager.setBleSpenAddress(str);
    }

    public String getBleSpenCmfCode() {
        Log.i(TAG, "getBleSpenCmfCode");
        checkSmartClipMetaExtractionPermission();
        return this.mBleSpenManager.getBleSpenCmfCode();
    }

    public void setBleSpenCmfCode(String str) {
        Log.i(TAG, "setBleSpenCmfCode : " + str);
        checkSmartClipMetaExtractionPermission();
        this.mBleSpenManager.setBleSpenCmfCode(str);
    }

    public boolean isSupportBleSpen() {
        boolean isSupportBleSpen = this.mBleSpenManager.isSupportBleSpen();
        Log.i(TAG, "isSupportBleSpen : " + isSupportBleSpen);
        return isSupportBleSpen;
    }

    public void writeBleSpenCommand(String str) {
        Log.i(TAG, "writeBleSpenCommand : " + str);
        checkSmartClipMetaExtractionPermission();
        this.mBleSpenManager.writeBleSpenCommand(str);
    }

    public void setSpenPdctLowSensitivityEnable() {
        Log.i(TAG, "setSpenPdctLowSensitivityEnable");
        checkSmartClipMetaExtractionPermission();
        this.mBleSpenManager.setSpenPdctLowSensitivityEnable();
    }

    public void saveBleSpenLogFile(byte[] bArr) {
        Log.i(TAG, "saveBleSpenLogFile");
        checkSmartClipMetaExtractionPermission();
        this.mBleSpenManager.saveBleSpenLogFile(bArr);
    }

    public void notifyBleSpenChargeLockState(boolean z) {
        Log.i(TAG, "notifyBleSpenChargeLockState : " + z);
        checkSmartClipMetaExtractionPermission();
        broastBleSpenChargeLockState(z);
    }

    public final void broastBleSpenChargeLockState(boolean z) {
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

    public void registerBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        checkSmartClipMetaExtractionPermission();
        if (this.mBleSpenChargeLockStateChangedListeners == null) {
            this.mBleSpenChargeLockStateChangedListeners = new RemoteCallbackList();
        }
        this.mBleSpenChargeLockStateChangedListeners.register(iBleSpenChargeLockStateChangedListener);
    }

    public void unregisterBleSpenChargeLockStateChangedListener(IBleSpenChargeLockStateChangedListener iBleSpenChargeLockStateChangedListener) {
        checkSmartClipMetaExtractionPermission();
        RemoteCallbackList remoteCallbackList = this.mBleSpenChargeLockStateChangedListeners;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iBleSpenChargeLockStateChangedListener);
        }
    }

    public void setScreenOffReason(int i) {
        Log.i(TAG, "setScreenOffReason : " + i);
        this.mScreenOffReason = i;
    }

    public int getScreenOffReason() {
        return this.mScreenOffReason;
    }

    public final void broastcastHoverEvent(int i) {
        RemoteCallbackList remoteCallbackList = this.mHoverListeners;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            if (i == 9) {
                try {
                    this.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverEnter();
                } catch (RemoteException unused) {
                }
            } else if (i == 10) {
                this.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverExit();
            }
        }
        this.mHoverListeners.finishBroadcast();
        Log.i(TAG, "mHoverListeners.getRegisteredCallbackCount() = " + this.mHoverListeners.getRegisteredCallbackCount());
    }

    public final void broastcastHoverStayEvent(int i, int i2) {
        RemoteCallbackList remoteCallbackList = this.mHoverListeners;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mHoverListeners.getBroadcastItem(beginBroadcast).onHoverStay(i, i2);
            } catch (RemoteException unused) {
            }
        }
        this.mHoverListeners.finishBroadcast();
    }

    public final ComponentName getTopMostPackage() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) mContext.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks.size() == 0) {
            Log.e(TAG, "getTopMostPackage : Failed to get running task info");
            return null;
        }
        return runningTasks.get(0).topActivity;
    }

    public final void sendLogSpenInsertInfo(String str, String str2) {
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

    public final void calculateSpenUsingDuration(long j) {
        float f = j >= 1000 ? (j / 1000.0f) / 60.0f : 1.0f;
        sendLogSpenInsertInfo(f > 60.0f ? "MoreThan1Hour" : f > 30.0f ? "30to60Minutes" : f > 10.0f ? "10to30Minutes" : f > 5.0f ? "5to10Minutes" : f > 1.0f ? "1to5Minutes" : "1MinuteOrLess", USE_APP_FEATURE_NAME_SPEN);
    }

    public final boolean isScreenPinningEnabled() {
        boolean z = ((ActivityManager) mContext.getSystemService("activity")).getLockTaskModeState() == 2;
        if (z) {
            Log.d(TAG, "isScreenPinningEnabled : Screen pinning mode enabled");
        }
        return z;
    }

    public final boolean isAppSwitchingBlocked() {
        return isSystemKeyEventRequested(getTopMostPackage(), FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
    }

    public final boolean isDoubleTapBlockedAppsByMetaData() {
        ComponentName topMostPackage = getTopMostPackage();
        Intent intent = new Intent("com.samsung.android.sdk.spen.BLOCK_DOUBLE_TAP_ACTION");
        intent.setComponent(topMostPackage);
        Iterator<ResolveInfo> it = mContext.getPackageManager().queryIntentActivities(intent, 128).iterator();
        while (it.hasNext()) {
            Bundle bundle = it.next().activityInfo.metaData;
            if (bundle != null && bundle.getBoolean("com.samsung.android.sdk.spen.BLOCK_DOUBLE_TAP_ACTION")) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSystemKeyEventRequested(ComponentName componentName, int i) {
        Log.i(TAG, "isSystemKeyEventRequested : " + i);
        if (componentName == null) {
            Log.e(TAG, "isSystemKeyEventRequested : component name is null");
            return false;
        }
        IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        if (asInterface != null) {
            try {
                boolean isSystemKeyEventRequested = asInterface.isSystemKeyEventRequested(i, componentName);
                if (isSystemKeyEventRequested) {
                    Log.d(TAG, "isSystemKeyEventRequested : system key is requested");
                }
                return isSystemKeyEventRequested;
            } catch (Exception e) {
                Log.e(TAG, "isSystemKeyEventRequested : e=" + e);
            }
        } else {
            Log.e(TAG, "isSystemKeyEventRequested : wm is null");
        }
        return false;
    }

    public void registerInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use registerInputMethodInfoChangeListener() : " + Binder.getCallingUid());
            return;
        }
        if (this.mInputInfoChangeListeners == null) {
            this.mInputInfoChangeListeners = new RemoteCallbackList();
        }
        this.mInputInfoChangeListeners.register(iInputMethodInfoChangeListener);
    }

    public void unregisterInputMethodInfoChangeListener(IInputMethodInfoChangeListener iInputMethodInfoChangeListener) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use unregisterInputMethodInfoChangeListener() : " + Binder.getCallingUid());
            return;
        }
        RemoteCallbackList remoteCallbackList = this.mInputInfoChangeListeners;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iInputMethodInfoChangeListener);
        }
    }

    public void setCurrentInputInfo(IRemoteInputConnection iRemoteInputConnection, EditorInfo editorInfo, int i) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use setCurrentInputInfo() : " + Binder.getCallingUid());
            return;
        }
        this.mInputConnection = iRemoteInputConnection;
        this.mEditorInfo = editorInfo;
        this.mMissingMethodFlags = i;
        this.mHandler.post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.9
            @Override // java.lang.Runnable
            public void run() {
                SpenGestureManagerService.this.broastcastInputContextChanged();
            }
        });
    }

    public EditorInfo getCurrentEditorInfo() {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use getCurrentEditorInfo() : " + Binder.getCallingUid());
            return null;
        }
        return this.mEditorInfo;
    }

    public IRemoteInputConnection getCurrentInputContext() {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use getCurrentInputContext() : " + Binder.getCallingUid());
            return null;
        }
        return this.mInputConnection;
    }

    public int getCurrentMissingMethodFlags() {
        return this.mMissingMethodFlags;
    }

    public final void broastcastInputContextChanged() {
        if (this.mInputInfoChangeListeners == null) {
            return;
        }
        Log.i(TAG, "broastcastInputContextChanged getRegisteredCallbackCount() = " + this.mInputInfoChangeListeners.getRegisteredCallbackCount());
        int beginBroadcast = this.mInputInfoChangeListeners.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mInputInfoChangeListeners.getBroadcastItem(beginBroadcast).onInputInfoChanged(this.mInputConnection, this.mEditorInfo);
            } catch (RemoteException unused) {
            }
        }
        this.mInputInfoChangeListeners.finishBroadcast();
    }

    public final boolean isUidSignature() {
        return mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) == 0;
    }

    public void notifyKeyboardClosed() {
        this.mHandler.post(new Runnable() { // from class: com.android.server.smartclip.SpenGestureManagerService.10
            @Override // java.lang.Runnable
            public void run() {
                SpenGestureManagerService.this.broastcastKeyboardClosed();
            }
        });
    }

    public void notifyAirGesture(String str) {
        if (!isUidSignature()) {
            Log.e(TAG, "no permission to use notifyAirGesture() : " + Binder.getCallingUid());
            return;
        }
        RemoteCallbackList remoteCallbackList = this.mAirGestureListener;
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        Log.i(TAG, "notifyAirGesture :  i: " + beginBroadcast + "  / " + str);
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mAirGestureListener.getBroadcastItem(beginBroadcast).onGesture(str);
            } catch (RemoteException unused) {
            }
        }
        this.mAirGestureListener.finishBroadcast();
    }

    public final void broastcastKeyboardClosed() {
        if (this.mInputInfoChangeListeners == null) {
            return;
        }
        Log.i(TAG, "broastcastKeyboardClosed getRegisteredCallbackCount() = " + this.mInputInfoChangeListeners.getRegisteredCallbackCount());
        int beginBroadcast = this.mInputInfoChangeListeners.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                this.mInputInfoChangeListeners.getBroadcastItem(beginBroadcast).onKeyboardClosed();
            } catch (RemoteException unused) {
            }
        }
        this.mInputInfoChangeListeners.finishBroadcast();
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.smartclip.SpenGestureScreenShotManager$Host] */
    public Bitmap screenshot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
        Log.i(TAG, "screenshot()");
        checkSmartClipMetaExtractionPermission();
        return new Object() { // from class: com.android.server.smartclip.SpenGestureScreenShotManager$Host
            public SpenGestureScreenShotManager$FutureScreenShot request(final int i5, final int i6, final boolean z3, final Rect rect2, final int i7, final int i8, final boolean z4) {
                SpenGestureScreenShotManager$FutureScreenShot spenGestureScreenShotManager$FutureScreenShot = new SpenGestureScreenShotManager$FutureScreenShot(new Callable() { // from class: com.android.server.smartclip.SpenGestureScreenShotManager$Host.1
                    @Override // java.util.concurrent.Callable
                    public SpenGestureScreenShotManager$RealScreenShot call() {
                        return new SpenGestureScreenShotManager$RealScreenShot(i5, i6, z3, rect2, i7, i8, z4);
                    }
                });
                new Thread(spenGestureScreenShotManager$FutureScreenShot).start();
                return spenGestureScreenShotManager$FutureScreenShot;
            }
        }.request(i, i2, z, rect, i3, i4, z2).getScreenShot();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Permission Denial: can't dump from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        printWriter.println("AirCommand:");
        StringBuilder sb = new StringBuilder();
        sb.append("  UspLevel : ");
        sb.append(this.mSpenUspLevel);
        printWriter.print(sb.toString());
        printWriter.print("  InBoxType : " + this.mInboxSPen);
        printWriter.print("  PenReversed : " + this.mIsPenReversed);
        printWriter.println("  PenInserted : " + this.mIsPenInserted);
        printWriter.print("  mAcIsBound : " + this.mAcIsBound);
        printWriter.print("  mAcIsFloatingIconEnabled : " + this.mAcIsFloatingIconEnabled + ", " + this.mAcAutoFloatingIconMode);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  mAcIsScreenOffMemoShowing : ");
        sb2.append(this.mAcIsScreenOffMemoShowing);
        printWriter.println(sb2.toString());
    }

    public void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenHoverIcon(str, fileDescriptor, f, f2);
        }
    }

    public void resetPenHoverIcon(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.resetPenHoverIcon(str);
        }
    }

    public void setPenAttachSound(String str, FileDescriptor fileDescriptor) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenAttachSound(str, fileDescriptor);
        }
    }

    public void resetPenAttachSound(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.resetPenAttachSound(str);
        }
    }

    public void setPenDetachSound(String str, FileDescriptor fileDescriptor) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.setPenDetachSound(str, fileDescriptor);
        }
    }

    public void resetPenDetachSound(String str) {
        if (str.isEmpty()) {
            return;
        }
        checkChangeSpenThemePermission();
        SpenThemeManager spenThemeManager = this.mThemeManager;
        if (spenThemeManager != null) {
            spenThemeManager.resetPenDetachSound(str);
        }
    }

    public final synchronized void writeDigitizerOnOffCommand(boolean z) {
        Log.i(TAG, "writeDigitizerOnOffCommand = " + z);
        try {
            this.mSemInputDeviceManager.setSpenPower(z ? 1 : 0);
        } catch (Exception e) {
            Log.e(TAG, "mSemInputDeviceManager.setSpenPower: Exception: " + e);
        }
    }

    public final boolean isDigitizerEnabled(Context context) {
        return Settings.System.semGetIntForUser(context.getContentResolver(), "pen_digitizer_enabled", 1, -2) != 0;
    }

    public final String getPenName(MotionEvent motionEvent) {
        InputDevice device = motionEvent.getDevice();
        if (device != null) {
            return device.getName();
        }
        return null;
    }
}
