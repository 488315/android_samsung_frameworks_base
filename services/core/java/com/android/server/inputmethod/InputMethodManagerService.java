package com.android.server.inputmethod;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.display.IDisplayManager;
import android.hardware.input.InputManager;
import android.media.AudioManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManagerPolicyConstants;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.window.ImeOnBackInvokedDispatcher;
import com.android.internal.content.PackageMonitor;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.inputmethod.InputMethodInfoSafeList;
import com.android.internal.inputmethod.InputMethodSubtypeHandle;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.AccessibilityManagerInternal;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.autofill.AutofillInlineSuggestionsRequestSession;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.input.InputManagerService;
import com.android.server.input.KeyboardLayoutManager;
import com.android.server.inputmethod.HandwritingModeController;
import com.android.server.inputmethod.IInputMethodManagerImpl;
import com.android.server.inputmethod.ImeTrackerService;
import com.android.server.inputmethod.ImeVisibilityStateComputer;
import com.android.server.inputmethod.InputMethodBindingController;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.inputmethod.InputMethodSubtypeSwitchingController;
import com.android.server.inputmethod.SamsungIMMSHWKeyboard;
import com.android.server.inputmethod.SamsungIMMSHWKeyboard.BTKeyboardReceiver;
import com.android.server.inputmethod.SamsungIMMSHWKeyboard.POGOKeyboardReceiver;
import com.android.server.inputmethod.SecureSettingsWrapper;
import com.android.server.inputmethod.SoftInputShowHideHistory;
import com.android.server.inputmethod.StartInputHistory;
import com.android.server.inputmethod.SystemLocaleWrapper;
import com.android.server.inputmethod.UserDataRepository;
import com.android.server.inputmethod.ZeroJankProxy;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.utils.PriorityDump;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputMethodManagerService implements IInputMethodManagerImpl.Callback, ZeroJankProxy.Callback, Handler.Callback {
    public static String mDefaultSIP;
    public boolean mAccessControlKeyboardBlockEnable;
    public final ActivityManagerInternal mActivityManagerInternal;
    public boolean mBoundToAccessibility;
    public boolean mBoundToMethod;
    public final ClientController mClientController;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public ClientState mCurClient;
    public EditorInfo mCurEditorInfo;
    public ImeOnBackInvokedDispatcher mCurImeDispatcher;
    public IRemoteInputConnection mCurInputConnection;
    public IRemoteAccessibilityInputConnection mCurRemoteAccessibilityInputConnection;
    public ImeTracker.Token mCurStatsToken;
    public int mCurrentUserId;
    public final SemDesktopModeManager mDesktopModeManager;
    public SessionState mEnabledSession;
    public final boolean mExperimentalConcurrentMultiUserModeEnabled;
    public final Handler mHandler;
    public HardwareKeyboardShortcutController mHardwareKeyboardShortcutController;
    public final HandwritingModeController mHwController;
    public IDisplayManager mIDisplayManager;
    public ImeBindingState mImeBindingState;
    public OverlayableSystemBooleanResourceWrapper mImeDrawsImeNavBarRes;
    public Future mImeDrawsImeNavBarResLazyInitFuture;
    public final ImePlatformCompatUtils mImePlatformCompatUtils;
    public final boolean mImeSelectedOnBoot;
    public final ImeTrackerService mImeTrackerService;
    public int mImeWindowVis;
    public boolean mInFullscreenMode;
    public boolean mInitialUserSwitch;
    public InputManager mInputManager;
    public final InputManagerService.LocalService mInputManagerInternal;
    public final InputMethodDeviceConfigs mInputMethodDeviceConfigs;
    public final Handler mIoHandler;
    public IBinder mLastImeTargetWindow;
    public final InputMethodMenuController mMenuController;
    public IRefreshRateToken mMinRefreshRateToken;
    public final InputMethodManagerService$$ExternalSyntheticLambda1 mMinRefreshRateTokenRelease;
    public final String[] mNonPreemptibleInputMethods;
    public final PackageManagerInternal mPackageManagerInternal;
    public final PowerManager mPowerManager;
    public final boolean mPreventImeStartupUnlessTextEditor;
    public final Resources mRes;
    public SamsungIMMSHWKeyboard mSamsungIMMSHWKeyboard;
    public final SemInputMethodManagerServiceUtil mSemImmsUtil;
    public final SettingsObserver mSettingsObserver;
    public boolean mShowOngoingImeSwitcherForPhones;
    public final String mSlotIme;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public IntArray mStylusIds;
    public InputMethodSubtypeSwitchingController mSwitchingController;
    public boolean mSystemReady;
    public final UserDataRepository mUserDataRepository;
    public final UserManagerInternal mUserManagerInternal;
    public UserSwitchHandlerTask mUserSwitchHandlerTask;
    public final DefaultImeVisibilityApplier mVisibilityApplier;
    public final ImeVisibilityStateComputer mVisibilityStateComputer;
    public final WindowManagerService mWMS;
    public final WindowManagerInternal mWindowManagerInternal;
    public static final boolean DEBUG_SEP = Debug.semIsProductDev();
    public static final Uri DEX_CONTENT_URI = Uri.parse("content://0@com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    public static final String FEATURE_CONFIG_SIP = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME");
    public static boolean mInputMethodSwitchDisable = false;
    public static final Uri DICTATION = Uri.parse("content://com.samsung.android.honeyboard.DictationProvider");
    public static IRemoteInputConnection mCurInputConnectionForKnox = null;
    public int mFocusedDisplayId = -1;
    public boolean mAccessControlEnable = false;
    public String mPrevInputMethodForUniversalSwitch = null;
    public boolean mIsNeedUpdateSubtypeForLocaleChanged = false;
    public boolean mSubTypeIntentReceived = false;
    public boolean mCurrentShowAuxSubtypes = false;
    public int mCurrentDisplayId = 0;
    public List mKeyboardTypeMouseIdList = new ArrayList();
    public final InputMethodManagerService$$ExternalSyntheticLambda0 mPointerEventListener = new WindowManagerPolicyConstants.PointerEventListener() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda0
        public final void onPointerEvent(MotionEvent motionEvent) {
            InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
            inputMethodManagerService.getClass();
            int action = motionEvent.getAction();
            if (action == 9 || action == 0) {
                if (!inputMethodManagerService.mSemImmsUtil.mSpenLastUsed && motionEvent.getToolType(0) == 2) {
                    Slog.d("InputMethodManagerService", "PointerEventListener set true");
                    inputMethodManagerService.mSemImmsUtil.mSpenLastUsed = true;
                } else {
                    if (!inputMethodManagerService.mSemImmsUtil.mSpenLastUsed || motionEvent.getToolType(0) == 2) {
                        return;
                    }
                    Slog.d("InputMethodManagerService", "PointerEventListener set false");
                    inputMethodManagerService.mSemImmsUtil.mSpenLastUsed = false;
                }
            }
        }
    };
    public boolean misSecurefolderIdOrDualAppId = false;
    public AudioManagerInternal mAudioManagerInternal = null;
    public VirtualDeviceManagerInternal mVdmInternal = null;
    public final SparseArray mVirtualDeviceMethodMap = new SparseArray();
    public final AnonymousClass1 mDumper = new ImeTracing.ServiceDumper() { // from class: com.android.server.inputmethod.InputMethodManagerService.1
        public final void dumpToProto(ProtoOutputStream protoOutputStream, byte[] bArr) {
            InputMethodManagerService.this.dumpDebug(protoOutputStream);
        }
    };
    public final WeakHashMap mFocusedWindowPerceptible = new WeakHashMap();
    public SparseArray mEnabledAccessibilitySessions = new SparseArray();
    public boolean mIsInteractive = true;
    public int mBackDisposition = 0;
    public final MyPackageMonitor mMyPackageMonitor = new MyPackageMonitor();
    public final CopyOnWriteArrayList mInputMethodListListeners = new CopyOnWriteArrayList();
    public final WeakHashMap mImeTargetWindowMap = new WeakHashMap();
    public final StartInputHistory mStartInputHistory = new StartInputHistory();
    public final SoftInputShowHideHistory mSoftInputShowHideHistory = new SoftInputShowHideHistory();
    public final AnonymousClass5 mPriorityDumper = new PriorityDump.PriorityDumper() { // from class: com.android.server.inputmethod.InputMethodManagerService.5
        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            dumpNormal(fileDescriptor, printWriter, strArr, z);
        }

        public final void dumpAsProtoNoCheck(FileDescriptor fileDescriptor) {
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
            long nanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos();
            protoOutputStream.write(1125281431553L, 4990904633914117449L);
            protoOutputStream.write(1125281431555L, nanos);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1125281431553L, SystemClock.elapsedRealtimeNanos());
            protoOutputStream.write(1138166333442L, "InputMethodManagerService.mPriorityDumper#dumpAsProtoNoCheck");
            InputMethodManagerService.this.dumpDebug(protoOutputStream);
            protoOutputStream.end(start);
            protoOutputStream.flush();
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public final void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            if (z) {
                dumpAsProtoNoCheck(fileDescriptor);
            } else {
                InputMethodManagerService.m591$$Nest$mdumpAsStringNoCheck(InputMethodManagerService.this, fileDescriptor, printWriter, strArr, true);
            }
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public final void dumpHigh(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            dumpNormal(fileDescriptor, printWriter, strArr, z);
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public final void dumpNormal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            if (z) {
                dumpAsProtoNoCheck(fileDescriptor);
            } else {
                InputMethodManagerService.m591$$Nest$mdumpAsStringNoCheck(InputMethodManagerService.this, fileDescriptor, printWriter, strArr, false);
            }
        }
    };
    public final DexOnPCStateChangeObserver mDexOnPCStateChangeObserver = new DexOnPCStateChangeObserver(0, this);
    public final DexOnPCStateChangeObserver mUniversalSwitchChangeObserver = new DexOnPCStateChangeObserver(4, this);
    public final DexOnPCStateChangeObserver mAccessControlEnableChangeObserver = new DexOnPCStateChangeObserver(1, this);
    public final DexOnPCStateChangeObserver mAccessControlKeyboardEnableChangeObserver = new DexOnPCStateChangeObserver(2, this);
    public final DexOnPCStateChangeObserver mDeXDualViewChangeObserver = new DexOnPCStateChangeObserver(3, this);
    public final IBinder mRefreshRateToken = new Binder();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilitySessionState {
        public final ClientState mClient;
        public final int mId;
        public IAccessibilityInputMethodSession mSession;

        public AccessibilitySessionState(ClientState clientState, int i, IAccessibilityInputMethodSession iAccessibilityInputMethodSession) {
            this.mClient = clientState;
            this.mId = i;
            this.mSession = iAccessibilityInputMethodSession;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("AccessibilitySessionState{uid=");
            ClientState clientState = this.mClient;
            sb.append(clientState.mUid);
            sb.append(" pid=");
            sb.append(clientState.mPid);
            sb.append(" id=");
            BatteryService$$ExternalSyntheticOutline0.m(this.mId, sb, " session=");
            sb.append(Integer.toHexString(System.identityHashCode(this.mSession)));
            sb.append("}");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DemoResetReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ InputMethodManagerService this$0;

        public /* synthetic */ DemoResetReceiver(int i, InputMethodManagerService inputMethodManagerService) {
            this.$r8$classId = i;
            this.this$0 = inputMethodManagerService;
        }

        /* JADX WARN: Code restructure failed: missing block: B:91:0x0195, code lost:
        
            if (r2 == false) goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x0197, code lost:
        
            android.util.Slog.d("InputMethodManagerService", "KnoxDesktop Disconnected startInputInnerLocked");
            r7.this$0.resetCurrentMethodAndClientLocked(30);
            r7.this$0.updateFromSettingsLocked(false);
         */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                Method dump skipped, instructions count: 828
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.DemoResetReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }

        public void restorePreviousUsedInputMethod() {
            String string = Settings.System.getString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex");
            if (string == null || "-1".equals(string)) {
                Slog.d("InputMethodManagerService", "KnoxDesktopModeReceiver : Failed to return the previous IME becuase the stored info is null or do not need restore");
                return;
            }
            if (!this.this$0.isInstalledInputMethod(string)) {
                Slog.d("InputMethodManagerService", "KnoxDesktopModeReceiver : Failed to return the previous IME becuase the stored ime is uninstalled pre imi id = ".concat(string));
                return;
            }
            InputMethodManagerService inputMethodManagerService = this.this$0;
            String currentInputMethodPackageName = InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService.mContext, inputMethodManagerService.mContentResolver);
            if (currentInputMethodPackageName == null || currentInputMethodPackageName.equals(string)) {
                return;
            }
            InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.this$0.mCurrentUserId);
            Slog.w("InputMethodManagerService", "Restore the Previous Used IME because KnoxDesktop Disconnected");
            this.this$0.setInputMethodLocked(inputMethodSettings.getSelectedInputMethodSubtypeId(string), 0, string);
            Settings.System.putString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex", "-1");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexOnPCStateChangeObserver extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ InputMethodManagerService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DexOnPCStateChangeObserver(int i, InputMethodManagerService inputMethodManagerService) {
            super(new Handler());
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = inputMethodManagerService;
                    super(new Handler());
                    break;
                case 2:
                    this.this$0 = inputMethodManagerService;
                    super(new Handler());
                    break;
                case 3:
                    this.this$0 = inputMethodManagerService;
                    super(new Handler());
                    break;
                case 4:
                    this.this$0 = inputMethodManagerService;
                    super(new Handler());
                    break;
                default:
                    this.this$0 = inputMethodManagerService;
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    int intForUser = Settings.System.getIntForUser(this.this$0.mContext.getContentResolver(), "dexonpc_connection_state", 0, InputMethodSettingsRepository.get(this.this$0.mCurrentUserId).mUserId);
                    SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = this.this$0.mSamsungIMMSHWKeyboard;
                    if (intForUser == 3) {
                        samsungIMMSHWKeyboard.keyboardState |= 128;
                    } else {
                        samsungIMMSHWKeyboard.keyboardState &= -129;
                    }
                    Slog.w("InputMethodManagerService", "DexOnPCStateChangeObserver :  onChange(), keyboardState - " + this.this$0.mSamsungIMMSHWKeyboard.keyboardState + " state " + intForUser);
                    super.onChange(z);
                    return;
                case 1:
                    try {
                        int i = InputMethodSettingsRepository.get(this.this$0.mCurrentUserId).mUserId;
                        boolean z2 = Settings.System.getIntForUser(this.this$0.mContext.getContentResolver(), "access_control_enabled", i) != 0;
                        Slog.i("InputMethodManagerService", "Access Control changed to:" + z2 + ", mCurrentUserId=" + i);
                        this.this$0.mAccessControlEnable = z2;
                        return;
                    } catch (Settings.SettingNotFoundException e) {
                        Slog.d("InputMethodManagerService", e.toString());
                        return;
                    }
                case 2:
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.this$0.mCurrentUserId);
                    ContentResolver contentResolver = this.this$0.mContext.getContentResolver();
                    int i2 = inputMethodSettings.mUserId;
                    boolean z3 = Settings.System.getIntForUser(contentResolver, "access_control_keyboard_block", 1, i2) != 0;
                    Slog.i("InputMethodManagerService", "Access Control keyboard block changed to:" + z3 + ", mCurrentUserId=" + i2);
                    this.this$0.mAccessControlKeyboardBlockEnable = z3;
                    return;
                case 3:
                    synchronized (ImfLock.class) {
                        try {
                            Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver: selfChange=" + z + " mFocusedDisplayId=" + this.this$0.mFocusedDisplayId);
                            boolean shouldShowImeKeyboardDefaultDisplayOnly = this.this$0.shouldShowImeKeyboardDefaultDisplayOnly();
                            boolean isSamsungDefaultMethodID = this.this$0.isSamsungDefaultMethodID();
                            InputMethodManagerService inputMethodManagerService = this.this$0;
                            int semComputeImeDisplayIdForTarget = inputMethodManagerService.semComputeImeDisplayIdForTarget(inputMethodManagerService.mFocusedDisplayId);
                            if (z) {
                                if (isSamsungDefaultMethodID) {
                                    if (semComputeImeDisplayIdForTarget != 0) {
                                        Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver  Client because DualView option is change");
                                        this.this$0.resetCurrentMethodAndClientLocked(1);
                                        this.this$0.updateFromSettingsLocked(false);
                                    } else {
                                        restorePreviousUsedInputMethod();
                                    }
                                } else if (semComputeImeDisplayIdForTarget != 0) {
                                    InputMethodManagerService inputMethodManagerService2 = this.this$0;
                                    Settings.System.putString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex", InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService2.mContext, inputMethodManagerService2.mContentResolver));
                                    Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver set Default keyboard");
                                    InputMethodManagerService.m596$$Nest$msetDefaultInputMethod(this.this$0);
                                }
                            } else if (shouldShowImeKeyboardDefaultDisplayOnly) {
                                restorePreviousUsedInputMethod();
                            } else if (isSamsungDefaultMethodID) {
                                this.this$0.updateFromSettingsLocked(false);
                                InputMethodManagerService inputMethodManagerService3 = this.this$0;
                                InputMethodManagerService.m599$$Nest$mstartInputUncheckedInnerLocked(InputMethodManagerService.m595$$Nest$msemComputeImeDisplayIdForDexTarget(inputMethodManagerService3), inputMethodManagerService3);
                            } else if (semComputeImeDisplayIdForTarget != 0) {
                                InputMethodManagerService inputMethodManagerService4 = this.this$0;
                                Settings.System.putString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex", InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService4.mContext, inputMethodManagerService4.mContentResolver));
                                Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver set Default keyboard");
                                InputMethodManagerService.m596$$Nest$msetDefaultInputMethod(this.this$0);
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    int i3 = Settings.Secure.getInt(this.this$0.mContext.getContentResolver(), "universal_switch_enabled", 0);
                    HermesService$3$$ExternalSyntheticOutline0.m(i3, "universalSwitchState changed to:", "InputMethodManagerService");
                    if (i3 == 1) {
                        InputMethodManagerService inputMethodManagerService5 = this.this$0;
                        inputMethodManagerService5.mPrevInputMethodForUniversalSwitch = InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService5.mContext, inputMethodManagerService5.mContentResolver);
                        if (this.this$0.isHoneyboardInstalled() && !"com.samsung.android.honeyboard/.service.HoneyBoardService".equals(this.this$0.getSelectedMethodIdLocked())) {
                            this.this$0.setInputMethodLocked(InputMethodSettingsRepository.get(this.this$0.mCurrentUserId).getSelectedInputMethodSubtypeId("com.samsung.android.honeyboard/.service.HoneyBoardService"), 0, "com.samsung.android.honeyboard/.service.HoneyBoardService");
                        }
                        InputMethodSettingsRepository.get(this.this$0.mCurrentUserId).setShowImeWithHardKeyboard(true);
                        return;
                    }
                    InputMethodManagerService inputMethodManagerService6 = this.this$0;
                    if (inputMethodManagerService6.mPrevInputMethodForUniversalSwitch == null) {
                        Slog.d("InputMethodManagerService", "Failed to return the previous IME becuase the stored info is null");
                        return;
                    }
                    InputMethodSettings inputMethodSettings2 = InputMethodSettingsRepository.get(inputMethodManagerService6.mCurrentUserId);
                    InputMethodInfo inputMethodInfo = inputMethodSettings2.mMethodMap.get(this.this$0.mPrevInputMethodForUniversalSwitch);
                    InputMethodManagerService inputMethodManagerService7 = this.this$0;
                    String currentInputMethodPackageName = InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService7.mContext, inputMethodManagerService7.mContentResolver);
                    StringBuilder sb = new StringBuilder("restorePreviousUsedInputMethod :  UniversalSwitch, previous inputmethod : : ");
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, this.this$0.mPrevInputMethodForUniversalSwitch, " currentIME: ", currentInputMethodPackageName, " InputMethodinfo: ");
                    sb.append(inputMethodInfo);
                    Slog.d("InputMethodManagerService", sb.toString());
                    if (inputMethodInfo == null || currentInputMethodPackageName == null || currentInputMethodPackageName.equals(this.this$0.mPrevInputMethodForUniversalSwitch)) {
                        return;
                    }
                    InputMethodManagerService inputMethodManagerService8 = this.this$0;
                    String str = inputMethodManagerService8.mPrevInputMethodForUniversalSwitch;
                    inputMethodManagerService8.setInputMethodLocked(inputMethodSettings2.getSelectedInputMethodSubtypeId(str), 0, str);
                    return;
            }
        }

        public void restorePreviousUsedInputMethod() {
            if (this.this$0.isDexSetting() && !this.this$0.shouldShowImeKeyboardDefaultDisplayOnly()) {
                Slog.i("InputMethodManagerService", "DexDualViewModeChangeObserver : isDexSetting true, so do not need restore");
                return;
            }
            String string = Settings.System.getString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex");
            if (string != null) {
                Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver : restorePreviousUsedInputMethod - ".concat(string));
            }
            if (string == null || "-1".equals(string)) {
                Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver : Failed to return the previous IME becuase the stored info is null or do not need restore");
                return;
            }
            if (!this.this$0.isInstalledInputMethod(string)) {
                Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver : Failed to return the previous IME becuase the stored ime is uninstalled pre imi id = ".concat(string));
                Settings.System.putString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex", "-1");
                return;
            }
            InputMethodManagerService inputMethodManagerService = this.this$0;
            String currentInputMethodPackageName = InputMethodManagerService.getCurrentInputMethodPackageName(inputMethodManagerService.mContext, inputMethodManagerService.mContentResolver);
            InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.this$0.mCurrentUserId);
            if (currentInputMethodPackageName != null && !currentInputMethodPackageName.equals(string) && !"-1".equals(string)) {
                Slog.w("InputMethodManagerService", "Restore the Previous Used IME because KnoxDesktop Disconnected");
                this.this$0.setInputMethodLocked(inputMethodSettings.getSelectedInputMethodSubtypeId(string), 0, string);
                Settings.System.putString(this.this$0.mContext.getContentResolver(), "com.sec.android.inputmethod.previous_inputmethod_dex", "-1");
            } else {
                Slog.d("InputMethodManagerService", "DexDualViewModeChangeObserver  restorePreviousUsedInputMethod reset Client because DualView option is change");
                this.this$0.updateFromSettingsLocked(false);
                InputMethodManagerService inputMethodManagerService2 = this.this$0;
                InputMethodManagerService.m599$$Nest$mstartInputUncheckedInnerLocked(InputMethodManagerService.m595$$Nest$msemComputeImeDisplayIdForDexTarget(inputMethodManagerService2), inputMethodManagerService2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ImmsRune {
        public static final boolean SUPPORT_SKBD_MULTI_MODAL_CONCEPT = CoreRune.ONE_UI_6_1;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InkWindowInitializer implements Runnable {
        public InkWindowInitializer() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (ImfLock.class) {
                IInputMethodInvoker curMethodLocked = InputMethodManagerService.this.getCurMethodLocked();
                if (curMethodLocked != null) {
                    try {
                        curMethodLocked.mTarget.initInkWindow();
                    } catch (RemoteException e) {
                        IInputMethodInvoker.logRemoteException(e);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputMethodPrivilegedOperationsImpl extends IInputMethodPrivilegedOperations.Stub {
        public final InputMethodManagerService mImms;
        public final IBinder mToken;

        public InputMethodPrivilegedOperationsImpl(InputMethodManagerService inputMethodManagerService, IBinder iBinder) {
            this.mImms = inputMethodManagerService;
            this.mToken = iBinder;
        }

        public final void applyImeVisibilityAsync(IBinder iBinder, boolean z, ImeTracker.Token token) {
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder2 = this.mToken;
            inputMethodManagerService.getClass();
            try {
                Trace.traceBegin(32L, "IMMS.applyImeVisibility");
                synchronized (ImfLock.class) {
                    if (inputMethodManagerService.calledWithValidTokenLocked(iBinder2)) {
                        if (z) {
                            inputMethodManagerService.notifyUserActivity();
                        }
                        ImeTracker.forLogging().onProgress(token, 47);
                        IBinder windowTokenFrom = inputMethodManagerService.mVisibilityStateComputer.getWindowTokenFrom(iBinder);
                        inputMethodManagerService.mVisibilityApplier.applyImeVisibility(windowTokenFrom, token, z ? 1 : 0, 0, inputMethodManagerService.mCurrentUserId);
                    } else {
                        ImeTracker.forLogging().onFailed(token, 47);
                    }
                }
            } finally {
                Trace.traceEnd(32L);
            }
        }

        public final void createInputContentUriToken(Uri uri, String str, AndroidFuture androidFuture) {
            try {
                androidFuture.complete(InputMethodManagerService.m590$$Nest$mcreateInputContentUriToken(this.mImms, this.mToken, uri, str).asBinder());
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void hideMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) {
            try {
                InputMethodManagerService.m594$$Nest$mhideMySoftInput(this.mImms, this.mToken, token, i, i2);
                androidFuture.complete((Object) null);
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void notifyUserActionAsync() {
            InputMethodSubtypeSwitchingController.DynamicRotationList dynamicRotationList;
            int usageRank;
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder = this.mToken;
            inputMethodManagerService.getClass();
            synchronized (ImfLock.class) {
                try {
                    if (inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId).mCurToken != iBinder) {
                        return;
                    }
                    int i = inputMethodManagerService.mCurrentUserId;
                    if (i != inputMethodManagerService.mSwitchingController.mUserId) {
                        return;
                    }
                    InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(i);
                    InputMethodInfo inputMethodInfo = InputMethodSettingsRepository.get(inputMethodBindingController.mUserId).mMethodMap.get(inputMethodBindingController.mSelectedMethodId);
                    if (inputMethodInfo != null) {
                        InputMethodSubtypeSwitchingController inputMethodSubtypeSwitchingController = inputMethodManagerService.mSwitchingController;
                        InputMethodSubtype inputMethodSubtype = inputMethodManagerService.getInputMethodBindingController(i).mCurrentSubtype;
                        InputMethodSubtypeSwitchingController.ControllerImpl controllerImpl = inputMethodSubtypeSwitchingController.mController;
                        if (controllerImpl != null && inputMethodInfo.supportsSwitchingToNextInputMethod() && (usageRank = (dynamicRotationList = controllerImpl.mSwitchingAwareRotationList).getUsageRank(inputMethodInfo, inputMethodSubtype)) > 0) {
                            int[] iArr = dynamicRotationList.mUsageHistoryOfSubtypeListItemIndex;
                            int i2 = iArr[usageRank];
                            System.arraycopy(iArr, 0, iArr, 1, usageRank);
                            iArr[0] = i2;
                        }
                    }
                } finally {
                }
            }
        }

        public final void onStylusHandwritingReady(int i, int i2) {
            this.mImms.mHandler.obtainMessage(1100, i, i2).sendToTarget();
        }

        public final void reportFullscreenModeAsync(boolean z) {
            IInputMethodClientInvoker iInputMethodClientInvoker;
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder = this.mToken;
            inputMethodManagerService.getClass();
            synchronized (ImfLock.class) {
                try {
                } catch (RemoteException e) {
                    IInputMethodClientInvoker.logRemoteException(e);
                } finally {
                }
                if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                    ClientState clientState = inputMethodManagerService.mCurClient;
                    if (clientState != null && (iInputMethodClientInvoker = clientState.mClient) != null) {
                        inputMethodManagerService.mInFullscreenMode = z;
                        if (iInputMethodClientInvoker.mIsProxy) {
                            iInputMethodClientInvoker.mTarget.reportFullscreenMode(z);
                        } else {
                            iInputMethodClientInvoker.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda4(iInputMethodClientInvoker, z, 1));
                        }
                    }
                }
            }
        }

        public final void reportStartInputAsync(IBinder iBinder) {
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder2 = this.mToken;
            inputMethodManagerService.getClass();
            synchronized (ImfLock.class) {
                try {
                    if (inputMethodManagerService.calledWithValidTokenLocked(iBinder2)) {
                        IBinder iBinder3 = (IBinder) inputMethodManagerService.mImeTargetWindowMap.get(iBinder);
                        if (iBinder3 != null) {
                            inputMethodManagerService.mWindowManagerInternal.updateInputMethodTargetWindow(iBinder2, iBinder3);
                        }
                        inputMethodManagerService.mLastImeTargetWindow = iBinder3;
                    }
                } finally {
                }
            }
        }

        public final void resetStylusHandwriting(int i) {
            InputMethodManagerService inputMethodManagerService = this.mImms;
            inputMethodManagerService.getClass();
            synchronized (ImfLock.class) {
                try {
                    OptionalInt currentRequestId = inputMethodManagerService.mHwController.getCurrentRequestId();
                    if (currentRequestId.isPresent()) {
                        if (currentRequestId.getAsInt() != i) {
                        }
                        inputMethodManagerService.removeStylusDeviceIdLocked(999999);
                        inputMethodManagerService.mHandler.obtainMessage(1090).sendToTarget();
                    }
                    Slog.w("InputMethodManagerService", "IME requested to finish handwriting with a mismatched requestId: " + i);
                    inputMethodManagerService.removeStylusDeviceIdLocked(999999);
                    inputMethodManagerService.mHandler.obtainMessage(1090).sendToTarget();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setHandwritingSurfaceNotTouchable(boolean z) {
            HandwritingModeController handwritingModeController = this.mImms.mHwController;
            if (handwritingModeController.getCurrentRequestId().isPresent()) {
                HandwritingEventReceiverSurface handwritingEventReceiverSurface = handwritingModeController.mHandwritingSurface;
                if (z) {
                    handwritingEventReceiverSurface.mWindowHandle.inputConfig |= 8;
                } else {
                    handwritingEventReceiverSurface.mWindowHandle.inputConfig &= -9;
                }
                new SurfaceControl.Transaction().setInputWindowInfo(handwritingEventReceiverSurface.mInputSurface, handwritingEventReceiverSurface.mWindowHandle).apply();
            }
        }

        public final void setImeWindowStatusAsync(int i, int i2) {
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder = this.mToken;
            int topFocusedDisplayId = inputMethodManagerService.mWindowManagerInternal.getTopFocusedDisplayId();
            synchronized (ImfLock.class) {
                try {
                    if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                        int curTokenDisplayIdLocked = inputMethodManagerService.getCurTokenDisplayIdLocked();
                        if (curTokenDisplayIdLocked == topFocusedDisplayId || curTokenDisplayIdLocked == 0) {
                            inputMethodManagerService.mImeWindowVis = i;
                            inputMethodManagerService.mBackDisposition = i2;
                            inputMethodManagerService.updateSystemUiLocked(i, i2);
                            Slog.i("InputMethodManagerService", "setImeWindowStatus: vis=" + i + ", backDisposition=" + i2);
                            inputMethodManagerService.mWindowManagerInternal.setDismissImeOnBackKeyPressed(i2 != 1 && (i2 == 2 || (i & 2) != 0), (2 & i) != 0);
                        }
                    }
                } finally {
                }
            }
        }

        public final void setInputMethod(String str, AndroidFuture androidFuture) {
            try {
                this.mImms.setInputMethod(this.mToken, str);
                androidFuture.complete((Object) null);
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void setInputMethodAndSubtype(String str, InputMethodSubtype inputMethodSubtype, AndroidFuture androidFuture) {
            try {
                InputMethodManagerService.m597$$Nest$msetInputMethodAndSubtype(this.mImms, this.mToken, str, inputMethodSubtype);
                androidFuture.complete((Object) null);
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void shouldOfferSwitchingToNextInputMethod(AndroidFuture androidFuture) {
            boolean z;
            try {
                InputMethodManagerService inputMethodManagerService = this.mImms;
                IBinder iBinder = this.mToken;
                inputMethodManagerService.getClass();
                synchronized (ImfLock.class) {
                    try {
                        if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                            InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId);
                            z = inputMethodManagerService.mSwitchingController.getNextInputMethodLocked(false, InputMethodSettingsRepository.get(inputMethodBindingController.mUserId).mMethodMap.get(inputMethodBindingController.mSelectedMethodId), inputMethodBindingController.mCurrentSubtype) != null;
                        }
                    } finally {
                    }
                }
                androidFuture.complete(Boolean.valueOf(z));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void showMySoftInput(ImeTracker.Token token, int i, int i2, AndroidFuture androidFuture) {
            try {
                InputMethodManagerService.m598$$Nest$mshowMySoftInput(this.mImms, this.mToken, token, i, i2);
                androidFuture.complete((Object) null);
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void switchKeyboardLayoutAsync(int i) {
            synchronized (ImfLock.class) {
                try {
                    if (this.mImms.calledWithValidTokenLocked(this.mToken)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            InputMethodManagerService.m600$$Nest$mswitchKeyboardLayoutLocked(i, this.mImms);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void switchToNextInputMethod(boolean z, AndroidFuture androidFuture) {
            try {
                androidFuture.complete(Boolean.valueOf(InputMethodManagerService.m601$$Nest$mswitchToNextInputMethod(this.mImms, this.mToken, z)));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void switchToPreviousInputMethod(AndroidFuture androidFuture) {
            try {
                androidFuture.complete(Boolean.valueOf(InputMethodManagerService.m602$$Nest$mswitchToPreviousInputMethod(this.mImms, this.mToken)));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public final void updateStatusIconAsync(String str, int i) {
            ApplicationInfo applicationInfo;
            InputMethodManagerService inputMethodManagerService = this.mImms;
            IBinder iBinder = this.mToken;
            inputMethodManagerService.getClass();
            synchronized (ImfLock.class) {
                try {
                    if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (i == 0) {
                                StatusBarManagerInternal statusBarManagerInternal = inputMethodManagerService.mStatusBarManagerInternal;
                                if (statusBarManagerInternal != null) {
                                    StatusBarManagerService.this.setIconVisibility(inputMethodManagerService.mSlotIme, false);
                                }
                            } else if (str != null) {
                                PackageManager packageManagerForUser = InputMethodManagerService.getPackageManagerForUser(inputMethodManagerService.mContext, inputMethodManagerService.mCurrentUserId);
                                try {
                                    applicationInfo = packageManagerForUser.getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L));
                                } catch (PackageManager.NameNotFoundException unused) {
                                    applicationInfo = null;
                                }
                                CharSequence applicationLabel = applicationInfo != null ? packageManagerForUser.getApplicationLabel(applicationInfo) : null;
                                StatusBarManagerInternal statusBarManagerInternal2 = inputMethodManagerService.mStatusBarManagerInternal;
                                if (statusBarManagerInternal2 != null) {
                                    StatusBarManagerService.this.setIcon(inputMethodManagerService.mSlotIme, str, i, 0, applicationLabel != null ? applicationLabel.toString() : null);
                                    StatusBarManagerService.this.setIconVisibility(inputMethodManagerService.mSlotIme, true);
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final InputMethodManagerService mService;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Lifecycle(Context context) {
            super(context);
            InputMethodManagerService inputMethodManagerService = new InputMethodManagerService(context, context.getPackageManager().hasSystemFeature("android.hardware.type.automotive") && UserManager.isVisibleBackgroundUsersEnabled() && context.getResources().getBoolean(R.bool.config_perDisplayFocusEnabled) && Flags.concurrentInputMethods(), null, null, null);
            this.mService = inputMethodManagerService;
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 550) {
                final InputMethodManagerService inputMethodManagerService = this.mService;
                inputMethodManagerService.getClass();
                synchronized (ImfLock.class) {
                    try {
                        if (!inputMethodManagerService.mSystemReady) {
                            inputMethodManagerService.mSystemReady = true;
                            final int i2 = inputMethodManagerService.mCurrentUserId;
                            StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
                            inputMethodManagerService.mStatusBarManagerInternal = statusBarManagerInternal;
                            if (statusBarManagerInternal != null) {
                                StatusBarManagerService.this.setIconVisibility(inputMethodManagerService.mSlotIme, false);
                            }
                            inputMethodManagerService.updateSystemUiLocked(inputMethodManagerService.mImeWindowVis, inputMethodManagerService.mBackDisposition);
                            boolean z = inputMethodManagerService.mRes.getBoolean(17892024);
                            inputMethodManagerService.mShowOngoingImeSwitcherForPhones = z;
                            if (z) {
                                inputMethodManagerService.mWindowManagerInternal.setOnHardKeyboardStatusChangeListener(new InputMethodManagerService$$ExternalSyntheticLambda5(inputMethodManagerService));
                            }
                            inputMethodManagerService.mImeDrawsImeNavBarResLazyInitFuture = SystemServerInitThreadPool.submit("Lazily initialize IMMS#mImeDrawsImeNavBarRes", new Runnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    InputMethodManagerService inputMethodManagerService2 = inputMethodManagerService;
                                    int i3 = i2;
                                    inputMethodManagerService2.getClass();
                                    synchronized (ImfLock.class) {
                                        try {
                                            inputMethodManagerService2.mImeDrawsImeNavBarResLazyInitFuture = null;
                                            if (i3 != inputMethodManagerService2.mCurrentUserId) {
                                                return;
                                            }
                                            inputMethodManagerService2.maybeInitImeNavbarConfigLocked(i3);
                                        } finally {
                                        }
                                    }
                                }
                            });
                            MyPackageMonitor myPackageMonitor = inputMethodManagerService.mMyPackageMonitor;
                            Context context = inputMethodManagerService.mContext;
                            UserHandle userHandle = UserHandle.ALL;
                            myPackageMonitor.register(context, userHandle, inputMethodManagerService.mIoHandler);
                            inputMethodManagerService.mSettingsObserver.registerContentObserverLocked(i2);
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
                            intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
                            inputMethodManagerService.mContext.registerReceiver(new DemoResetReceiver(2, inputMethodManagerService), intentFilter);
                            IntentFilter intentFilter2 = new IntentFilter();
                            intentFilter2.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
                            inputMethodManagerService.mContext.registerReceiverAsUser(new DemoResetReceiver(1, inputMethodManagerService), userHandle, intentFilter2, null, null, 2);
                            boolean isEmpty = TextUtils.isEmpty(SecureSettingsWrapper.getString("default_input_method", null, i2));
                            InputMethodSettings queryInputMethodServicesInternal = InputMethodManagerService.queryInputMethodServicesInternal(inputMethodManagerService.mContext, i2, AdditionalSubtypeMapRepository.get(i2), 0);
                            InputMethodSettingsRepository.put(i2, queryInputMethodServicesInternal);
                            inputMethodManagerService.postInputMethodSettingUpdatedLocked(isEmpty);
                            inputMethodManagerService.updateFromSettingsLocked(true);
                            InputMethodUtils.setNonSelectedSystemImesDisabledUntilUsed(InputMethodManagerService.getPackageManagerForUser(inputMethodManagerService.mContext, i2), queryInputMethodServicesInternal.getEnabledInputMethodListWithFilter(null));
                            SystemServerInitThreadPool.submit("Start AdditionalSubtypeMapRepository's writer thread", new InputMethodManagerService$$ExternalSyntheticLambda7());
                            if (inputMethodManagerService.mExperimentalConcurrentMultiUserModeEnabled) {
                                for (int i3 : inputMethodManagerService.mUserManagerInternal.getUserIds()) {
                                    if (i3 != inputMethodManagerService.mCurrentUserId) {
                                        InputMethodManagerService.experimentalInitializeVisibleBackgroundUserLocked(i3);
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            IInputMethodManagerImpl.Callback callback;
            boolean z = InputMethodManagerService.DEBUG_SEP;
            InputMethodManagerService inputMethodManagerService = this.mService;
            inputMethodManagerService.getClass();
            LocalServices.addService(InputMethodManagerInternal.class, inputMethodManagerService.new LocalServiceImpl());
            if (Flags.useZeroJankProxy()) {
                Handler handler = inputMethodManagerService.mHandler;
                Objects.requireNonNull(handler);
                callback = new ZeroJankProxy(new ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1(handler), inputMethodManagerService);
            } else {
                callback = inputMethodManagerService;
            }
            publishBinderService("input_method", new IInputMethodManagerImpl(callback), false, 21);
            if (Flags.refactorInsetsController()) {
                inputMethodManagerService.mWindowManagerInternal.setOnImeRequestedChangedListener(new InputMethodManagerService$$ExternalSyntheticLambda5(inputMethodManagerService));
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            ContentResolver contentResolver = SecureSettingsWrapper.sContentResolver;
            SecureSettingsWrapper.putOrGet(userIdentifier, SecureSettingsWrapper.createImpl((UserManagerInternal) LocalServices.getService(UserManagerInternal.class), userIdentifier));
            synchronized (ImfLock.class) {
                try {
                    this.mService.mUserDataRepository.getOrCreate(userIdentifier);
                    InputMethodManagerService inputMethodManagerService = this.mService;
                    if (inputMethodManagerService.mExperimentalConcurrentMultiUserModeEnabled && inputMethodManagerService.mCurrentUserId != userIdentifier && inputMethodManagerService.mSystemReady) {
                        InputMethodManagerService.experimentalInitializeVisibleBackgroundUserLocked(userIdentifier);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = this.mService;
                    if (inputMethodManagerService.mExperimentalConcurrentMultiUserModeEnabled) {
                        return;
                    }
                    inputMethodManagerService.scheduleSwitchUserTaskLocked(targetUser2.getUserIdentifier(), null);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            SecureSettingsWrapper.putOrGet(userIdentifier, new SecureSettingsWrapper.UnlockedUserImpl(userIdentifier, SecureSettingsWrapper.sContentResolver));
            this.mService.mHandler.obtainMessage(5000, targetUser.getUserIdentifier(), 0).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalServiceImpl extends InputMethodManagerInternal {
        public LocalServiceImpl() {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final List getEnabledInputMethodListAsUser(int i) {
            List enabledInputMethodListLocked;
            synchronized (ImfLock.class) {
                enabledInputMethodListLocked = InputMethodManagerService.this.getEnabledInputMethodListLocked(i, 1000);
            }
            return enabledInputMethodListLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final List getInputMethodListAsUser(int i) {
            List inputMethodListLocked;
            synchronized (ImfLock.class) {
                inputMethodListLocked = InputMethodManagerService.this.getInputMethodListLocked(i, 0, 1000);
            }
            return inputMethodListLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void hideAllInputMethods(int i) {
            InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
            inputMethodManagerService.mHandler.removeMessages(1035);
            inputMethodManagerService.mHandler.obtainMessage(1035, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void maybeFinishStylusHandwriting() {
            InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
            inputMethodManagerService.mHandler.removeMessages(1110);
            inputMethodManagerService.mHandler.obtainMessage(1110).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onCreateInlineSuggestionsRequest(int i, InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, AutofillInlineSuggestionsRequestSession.InlineSuggestionsRequestCallbackImpl inlineSuggestionsRequestCallbackImpl) {
            boolean isTouchExplorationEnabled = AccessibilityManagerInternal.get().isTouchExplorationEnabled(i);
            synchronized (ImfLock.class) {
                InputMethodManagerService.this.getInputMethodBindingController(i).onCreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, inlineSuggestionsRequestCallbackImpl, isTouchExplorationEnabled);
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onImeParentChanged() {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                    if (inputMethodManagerService.mLastImeTargetWindow != inputMethodManagerService.mImeBindingState.mFocusedWindow) {
                        inputMethodManagerService.mMenuController.hideInputMethodMenuLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onSessionForAccessibilityCreated(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i) {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                    InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId);
                    ClientState clientState = InputMethodManagerService.this.mCurClient;
                    if (clientState != null) {
                        AccessibilitySessionState accessibilitySessionState = (AccessibilitySessionState) clientState.mAccessibilitySessions.get(i);
                        if (accessibilitySessionState != null) {
                            InputMethodManagerService.finishSessionForAccessibilityLocked(accessibilitySessionState);
                            clientState.mAccessibilitySessions.remove(i);
                        }
                        ClientState clientState2 = InputMethodManagerService.this.mCurClient;
                        clientState2.mAccessibilitySessions.put(i, new AccessibilitySessionState(clientState2, i, iAccessibilityInputMethodSession));
                        InputMethodManagerService.this.attachNewAccessibilityLocked(11, true);
                        ClientState clientState3 = InputMethodManagerService.this.mCurClient;
                        SessionState sessionState = clientState3.mCurSession;
                        InputBindResult inputBindResult = new InputBindResult(16, sessionState == null ? null : sessionState.mSession, InputMethodManagerService.createAccessibilityInputMethodSessions(clientState3.mAccessibilitySessions), (InputChannel) null, inputMethodBindingController.mCurId, inputMethodBindingController.mCurSeq, false);
                        IInputMethodClientInvoker iInputMethodClientInvoker = InputMethodManagerService.this.mCurClient.mClient;
                        if (iInputMethodClientInvoker.mIsProxy) {
                            iInputMethodClientInvoker.onBindAccessibilityServiceInternal(inputBindResult, i);
                        } else {
                            iInputMethodClientInvoker.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda5(iInputMethodClientInvoker, inputBindResult, i, 1));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void onSwitchKeyboardLayoutShortcut(int i) {
            synchronized (ImfLock.class) {
                InputMethodManagerService.m600$$Nest$mswitchKeyboardLayoutLocked(i, InputMethodManagerService.this);
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void removeImeSurface() {
            InputMethodManagerService.this.mHandler.obtainMessage(1060).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void reportImeControl(IBinder iBinder) {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                    if (inputMethodManagerService.mImeBindingState.mFocusedWindow != iBinder) {
                        inputMethodManagerService.mFocusedWindowPerceptible.put(iBinder, Boolean.TRUE);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean setInputMethodEnabled(int i, String str, boolean z) {
            synchronized (ImfLock.class) {
                try {
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
                    if (!inputMethodSettings.mMethodMap.mMap.containsKey(str)) {
                        return false;
                    }
                    InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                    if (i == inputMethodManagerService.mCurrentUserId) {
                        inputMethodManagerService.setInputMethodEnabledLocked(str, z);
                        return true;
                    }
                    if (z) {
                        String enabledInputMethodsStr = inputMethodSettings.getEnabledInputMethodsStr();
                        String concatEnabledImeIds = InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
                        if (!TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds)) {
                            inputMethodSettings.putEnabledInputMethodsStr(concatEnabledImeIds);
                        }
                    } else {
                        inputMethodSettings.buildAndPutEnabledInputMethodsStrRemovingId(new StringBuilder(), inputMethodSettings.getEnabledInputMethodsAndSubtypeList(), str);
                    }
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void setInteractive(boolean z) {
            InputMethodManagerService.this.mHandler.obtainMessage(3030, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void setVirtualDeviceInputMethodForAllUsers(int i, String str) {
            Preconditions.checkArgument(i != 0, TextUtils.formatSimple("DeviceId %d is not a virtual device id.", new Object[]{Integer.valueOf(i)}));
            synchronized (ImfLock.class) {
                try {
                    if (str == null) {
                        InputMethodManagerService.this.mVirtualDeviceMethodMap.remove(i);
                    } else {
                        if (InputMethodManagerService.this.mVirtualDeviceMethodMap.contains(i)) {
                            throw new IllegalArgumentException("Virtual device " + i + " already has a custom input method component");
                        }
                        InputMethodManagerService.this.mVirtualDeviceMethodMap.put(i, str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean switchToInputMethod(int i, String str) {
            boolean switchToInputMethodLocked;
            synchronized (ImfLock.class) {
                switchToInputMethodLocked = InputMethodManagerService.this.switchToInputMethodLocked(i, str);
            }
            return switchToInputMethodLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final boolean transferTouchFocusToImeWindow(int i, IBinder iBinder, int i2) {
            synchronized (ImfLock.class) {
                try {
                    if (i != InputMethodManagerService.this.getCurTokenDisplayIdLocked()) {
                        return false;
                    }
                    IBinder iBinder2 = InputMethodManagerService.this.getInputMethodBindingController(i2).mAutofillController.mCurHostInputToken;
                    if (iBinder2 == null) {
                        return false;
                    }
                    return InputManagerService.this.transferTouchGesture(iBinder, iBinder2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void unbindAccessibilityFromCurrentClient(final int i) {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                    InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId);
                    ClientState clientState = InputMethodManagerService.this.mCurClient;
                    if (clientState != null) {
                        IInputMethodClientInvoker iInputMethodClientInvoker = clientState.mClient;
                        int i2 = inputMethodBindingController.mCurSeq;
                        if (iInputMethodClientInvoker.mIsProxy) {
                            try {
                                iInputMethodClientInvoker.mTarget.onUnbindAccessibilityService(i2, i);
                            } catch (RemoteException e) {
                                IInputMethodClientInvoker.logRemoteException(e);
                            }
                        } else {
                            iInputMethodClientInvoker.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda0(iInputMethodClientInvoker, i2, i, 1));
                        }
                    }
                    if (InputMethodManagerService.this.getCurMethodLocked() != null) {
                        InputMethodManagerService.this.mClientController.forAllClients(new Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$LocalServiceImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                InputMethodManagerService.LocalServiceImpl localServiceImpl = InputMethodManagerService.LocalServiceImpl.this;
                                int i3 = i;
                                ClientState clientState2 = (ClientState) obj;
                                InputMethodManagerService.this.getClass();
                                InputMethodManagerService.AccessibilitySessionState accessibilitySessionState = (InputMethodManagerService.AccessibilitySessionState) clientState2.mAccessibilitySessions.get(i3);
                                if (accessibilitySessionState != null) {
                                    InputMethodManagerService.finishSessionForAccessibilityLocked(accessibilitySessionState);
                                    clientState2.mAccessibilitySessions.remove(i3);
                                }
                            }
                        });
                        AccessibilitySessionState accessibilitySessionState = (AccessibilitySessionState) InputMethodManagerService.this.mEnabledAccessibilitySessions.get(i);
                        if (accessibilitySessionState != null) {
                            InputMethodManagerService.this.getClass();
                            InputMethodManagerService.finishSessionForAccessibilityLocked(accessibilitySessionState);
                            InputMethodManagerService.this.mEnabledAccessibilitySessions.remove(i);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public final void updateImeWindowStatus(boolean z) {
            InputMethodManagerService.this.mHandler.obtainMessage(1070, z ? 1 : 0, 0).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public final ArrayList mDataClearedPackages;

        public MyPackageMonitor() {
            super(true);
            this.mDataClearedPackages = new ArrayList();
        }

        public final void onBeginPackageChanges() {
            this.mDataClearedPackages.clear();
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x00f9 A[Catch: all -> 0x005c, TryCatch #3 {all -> 0x005c, blocks: (B:4:0x0016, B:7:0x001f, B:9:0x003a, B:12:0x004c, B:14:0x0058, B:15:0x005f, B:17:0x006a, B:19:0x0086, B:23:0x00cb, B:24:0x0094, B:27:0x00a8, B:31:0x00d1, B:35:0x00f9, B:36:0x00fe, B:39:0x0204, B:43:0x0108, B:46:0x0152, B:48:0x015c, B:50:0x015f, B:52:0x0167, B:56:0x0175, B:58:0x017d, B:60:0x018f, B:62:0x01b5, B:64:0x01ca, B:66:0x01e0, B:67:0x01e6, B:70:0x01d3, B:78:0x011a, B:80:0x012d, B:86:0x013b, B:92:0x0149, B:94:0x01e8, B:100:0x01f5, B:101:0x01f8, B:108:0x0200, B:109:0x0203, B:116:0x00db, B:118:0x00e8, B:120:0x00ee, B:121:0x00f1), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0106  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0108 A[Catch: all -> 0x005c, TryCatch #3 {all -> 0x005c, blocks: (B:4:0x0016, B:7:0x001f, B:9:0x003a, B:12:0x004c, B:14:0x0058, B:15:0x005f, B:17:0x006a, B:19:0x0086, B:23:0x00cb, B:24:0x0094, B:27:0x00a8, B:31:0x00d1, B:35:0x00f9, B:36:0x00fe, B:39:0x0204, B:43:0x0108, B:46:0x0152, B:48:0x015c, B:50:0x015f, B:52:0x0167, B:56:0x0175, B:58:0x017d, B:60:0x018f, B:62:0x01b5, B:64:0x01ca, B:66:0x01e0, B:67:0x01e6, B:70:0x01d3, B:78:0x011a, B:80:0x012d, B:86:0x013b, B:92:0x0149, B:94:0x01e8, B:100:0x01f5, B:101:0x01f8, B:108:0x0200, B:109:0x0203, B:116:0x00db, B:118:0x00e8, B:120:0x00ee, B:121:0x00f1), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:64:0x01ca A[Catch: all -> 0x005c, TryCatch #3 {all -> 0x005c, blocks: (B:4:0x0016, B:7:0x001f, B:9:0x003a, B:12:0x004c, B:14:0x0058, B:15:0x005f, B:17:0x006a, B:19:0x0086, B:23:0x00cb, B:24:0x0094, B:27:0x00a8, B:31:0x00d1, B:35:0x00f9, B:36:0x00fe, B:39:0x0204, B:43:0x0108, B:46:0x0152, B:48:0x015c, B:50:0x015f, B:52:0x0167, B:56:0x0175, B:58:0x017d, B:60:0x018f, B:62:0x01b5, B:64:0x01ca, B:66:0x01e0, B:67:0x01e6, B:70:0x01d3, B:78:0x011a, B:80:0x012d, B:86:0x013b, B:92:0x0149, B:94:0x01e8, B:100:0x01f5, B:101:0x01f8, B:108:0x0200, B:109:0x0203, B:116:0x00db, B:118:0x00e8, B:120:0x00ee, B:121:0x00f1), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01e0 A[Catch: all -> 0x005c, TryCatch #3 {all -> 0x005c, blocks: (B:4:0x0016, B:7:0x001f, B:9:0x003a, B:12:0x004c, B:14:0x0058, B:15:0x005f, B:17:0x006a, B:19:0x0086, B:23:0x00cb, B:24:0x0094, B:27:0x00a8, B:31:0x00d1, B:35:0x00f9, B:36:0x00fe, B:39:0x0204, B:43:0x0108, B:46:0x0152, B:48:0x015c, B:50:0x015f, B:52:0x0167, B:56:0x0175, B:58:0x017d, B:60:0x018f, B:62:0x01b5, B:64:0x01ca, B:66:0x01e0, B:67:0x01e6, B:70:0x01d3, B:78:0x011a, B:80:0x012d, B:86:0x013b, B:92:0x0149, B:94:0x01e8, B:100:0x01f5, B:101:0x01f8, B:108:0x0200, B:109:0x0203, B:116:0x00db, B:118:0x00e8, B:120:0x00ee, B:121:0x00f1), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x01d1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onFinishPackageChanges() {
            /*
                Method dump skipped, instructions count: 525
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.MyPackageMonitor.onFinishPackageChanges():void");
        }

        public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
            synchronized (ImfLock.class) {
                try {
                    int changingUserId = getChangingUserId();
                    int i2 = InputMethodManagerService.this.mCurrentUserId;
                    if (changingUserId != i2) {
                        return false;
                    }
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i2);
                    String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
                    List list = inputMethodSettings.mMethodList;
                    int size = list.size();
                    if (selectedInputMethod != null) {
                        for (int i3 = 0; i3 < size; i3++) {
                            InputMethodInfo inputMethodInfo = (InputMethodInfo) list.get(i3);
                            if (inputMethodInfo.getId().equals(selectedInputMethod)) {
                                for (String str : strArr) {
                                    if (inputMethodInfo.getPackageName().equals(str)) {
                                        if (!z) {
                                            return true;
                                        }
                                        InputMethodManagerService.this.resetSelectedInputMethodAndSubtypeLocked("");
                                        InputMethodManagerService.this.chooseNewDefaultIMELocked();
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageDataCleared(String str, int i) {
            this.mDataClearedPackages.add(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionState {
        public InputChannel mChannel;
        public final ClientState mClient;
        public final IInputMethodInvoker mMethod;
        public IInputMethodSession mSession;

        public SessionState(ClientState clientState, IInputMethodInvoker iInputMethodInvoker, IInputMethodSession iInputMethodSession, InputChannel inputChannel) {
            this.mClient = clientState;
            this.mMethod = iInputMethodInvoker;
            this.mSession = iInputMethodSession;
            this.mChannel = inputChannel;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SessionState{uid=");
            ClientState clientState = this.mClient;
            sb.append(clientState.mUid);
            sb.append(" pid=");
            sb.append(clientState.mPid);
            sb.append(" method=");
            IInputMethodInvoker iInputMethodInvoker = this.mMethod;
            BatteryService$$ExternalSyntheticOutline0.m(iInputMethodInvoker == null ? 0 : System.identityHashCode(iInputMethodInvoker.mTarget), sb, " session=");
            sb.append(Integer.toHexString(System.identityHashCode(this.mSession)));
            sb.append(" channel=");
            sb.append(this.mChannel);
            sb.append("}");
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public String mLastEnabled;
        public boolean mRegistered;
        public int mUserId;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mRegistered = false;
            this.mLastEnabled = "";
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Slog.d("InputMethodManagerService", "onChange " + uri);
            Uri uriFor = Settings.Secure.getUriFor("show_ime_with_hard_keyboard");
            Uri uriFor2 = Settings.Secure.getUriFor("accessibility_soft_keyboard_mode");
            Uri uriFor3 = Settings.Secure.getUriFor("stylus_handwriting_enabled");
            Uri uriFor4 = Settings.Secure.getUriFor("sip_keyboard_type_mouse_id_list");
            synchronized (ImfLock.class) {
                try {
                    if (uriFor.equals(uri)) {
                        InputMethodManagerService.this.mMenuController.updateKeyboardFromSettingsLocked();
                    } else {
                        boolean equals = uriFor2.equals(uri);
                        boolean z2 = true;
                        if (equals) {
                            int intForUser = Settings.Secure.getIntForUser(InputMethodManagerService.this.mContext.getContentResolver(), "accessibility_soft_keyboard_mode", 0, this.mUserId);
                            ImeVisibilityStateComputer.ImeVisibilityPolicy imeVisibilityPolicy = InputMethodManagerService.this.mVisibilityStateComputer.mPolicy;
                            imeVisibilityPolicy.getClass();
                            boolean z3 = (intForUser & 3) == 1;
                            imeVisibilityPolicy.mA11yRequestingNoSoftKeyboard = z3;
                            if (z3) {
                                imeVisibilityPolicy.mPendingA11yRequestingHideKeyboard = true;
                            }
                            InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                            if (inputMethodManagerService.mVisibilityStateComputer.mPolicy.mA11yRequestingNoSoftKeyboard) {
                                inputMethodManagerService.hideCurrentInputLocked(16, inputMethodManagerService.mImeBindingState.mFocusedWindow);
                            } else if (inputMethodManagerService.isShowRequestedForCurrentWindow()) {
                                InputMethodManagerService inputMethodManagerService2 = InputMethodManagerService.this;
                                inputMethodManagerService2.showCurrentInputLocked(inputMethodManagerService2.mImeBindingState.mFocusedWindow, inputMethodManagerService2.createStatsTokenForFocusedClient(9, true), 1, 0, null, 9);
                            }
                        } else if (uriFor3.equals(uri)) {
                            InputMethodManager.invalidateLocalStylusHandwritingAvailabilityCaches();
                            InputMethodManager.invalidateLocalConnectionlessStylusHandwritingAvailabilityCaches();
                        } else if (uriFor4.equals(uri)) {
                            InputMethodManagerService inputMethodManagerService3 = InputMethodManagerService.this;
                            inputMethodManagerService3.buildKeyboardTypeMouseList(inputMethodManagerService3.mContext);
                        } else {
                            String enabledInputMethodsStr = InputMethodSettingsRepository.get(InputMethodManagerService.this.mCurrentUserId).getEnabledInputMethodsStr();
                            if (this.mLastEnabled.equals(enabledInputMethodsStr)) {
                                z2 = false;
                            } else {
                                this.mLastEnabled = enabledInputMethodsStr;
                            }
                            InputMethodManagerService.this.updateInputMethodsFromSettingsLocked(z2);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerContentObserverLocked(int i) {
            if (this.mRegistered && this.mUserId == i) {
                return;
            }
            ContentResolver contentResolver = InputMethodManagerService.this.mContext.getContentResolver();
            if (this.mRegistered) {
                InputMethodManagerService.this.mContext.getContentResolver().unregisterContentObserver(this);
                this.mRegistered = false;
            }
            if (this.mUserId != i) {
                this.mLastEnabled = "";
                this.mUserId = i;
            }
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("default_input_method"), false, this, i);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("enabled_input_methods"), false, this, i);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("selected_input_method_subtype"), false, this, i);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("show_ime_with_hard_keyboard"), false, this, i);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_soft_keyboard_mode"), false, this, i);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("stylus_handwriting_enabled"), false, this);
            InputMethodManagerService.this.mContext.getContentResolver().registerContentObserver(Uri.withAppendedPath(InputMethodManagerService.DEX_CONTENT_URI, "touch_keyboard"), false, InputMethodManagerService.this.mDeXDualViewChangeObserver);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("sip_keyboard_type_mouse_id_list"), false, this, i);
            this.mRegistered = true;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SettingsObserver{mUserId=");
            sb.append(this.mUserId);
            sb.append(" mRegistered=");
            sb.append(this.mRegistered);
            sb.append(" mLastEnabled=");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mLastEnabled, "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShellCommandImpl extends ShellCommand {
        public final InputMethodManagerService mService;

        public ShellCommandImpl(InputMethodManagerService inputMethodManagerService) {
            this.mService = inputMethodManagerService;
        }

        public final int onCommand(String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return onCommandWithSystemIdentity(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Finally extract failed */
        /* JADX WARN: Removed duplicated region for block: B:196:0x0351 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onCommandWithSystemIdentity(java.lang.String r18) {
            /*
                Method dump skipped, instructions count: 1318
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.ShellCommandImpl.onCommandWithSystemIdentity(java.lang.String):int");
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                outPrintWriter.println("InputMethodManagerService commands:");
                outPrintWriter.println("  help");
                outPrintWriter.println("    Prints this help text.");
                outPrintWriter.println("  dump [options]");
                outPrintWriter.println("    Synonym of dumpsys.");
                outPrintWriter.println("  ime <command> [options]");
                outPrintWriter.println("    Manipulate IMEs.  Run \"ime help\" for details.");
                outPrintWriter.println("  tracing <command>");
                outPrintWriter.println("    start: Start tracing.");
                outPrintWriter.println("    stop : Stop tracing.");
                outPrintWriter.println("    help : Show help.");
                outPrintWriter.close();
            } catch (Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserSwitchHandlerTask implements Runnable {
        public IInputMethodClientInvoker mClientToBeReset;
        public final InputMethodManagerService mService;
        public final int mToUserId;

        public UserSwitchHandlerTask(InputMethodManagerService inputMethodManagerService, int i, IInputMethodClientInvoker iInputMethodClientInvoker) {
            this.mService = inputMethodManagerService;
            this.mToUserId = i;
            this.mClientToBeReset = iInputMethodClientInvoker;
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (ImfLock.class) {
                try {
                    InputMethodManagerService inputMethodManagerService = this.mService;
                    UserSwitchHandlerTask userSwitchHandlerTask = inputMethodManagerService.mUserSwitchHandlerTask;
                    if (userSwitchHandlerTask != this) {
                        return;
                    }
                    InputMethodManagerService.m603$$Nest$mswitchUserOnHandlerLocked(inputMethodManagerService, userSwitchHandlerTask.mToUserId, this.mClientToBeReset);
                    this.mService.mUserSwitchHandlerTask = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcreateInputContentUriToken, reason: not valid java name */
    public static InputContentUriTokenHandler m590$$Nest$mcreateInputContentUriToken(InputMethodManagerService inputMethodManagerService, IBinder iBinder, Uri uri, String str) {
        inputMethodManagerService.getClass();
        if (iBinder == null) {
            throw new NullPointerException(KnoxCustomManagerService.SPCM_KEY_TOKEN);
        }
        if (str == null) {
            throw new NullPointerException("packageName");
        }
        if (uri == null) {
            throw new NullPointerException("contentUri");
        }
        if (!"content".equals(uri.getScheme())) {
            throw new InvalidParameterException("contentUri must have content scheme");
        }
        synchronized (ImfLock.class) {
            try {
                int callingUid = Binder.getCallingUid();
                int userId = UserHandle.getUserId(callingUid);
                if (userId != inputMethodManagerService.mCurrentUserId) {
                    Slog.i("InputMethodManagerService", "Ignoring createInputContentUriToken due to user ID mismatch. imeUserId=" + userId + " mCurrentUserId=" + inputMethodManagerService.mCurrentUserId);
                    return null;
                }
                InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(userId);
                if (inputMethodBindingController.mSelectedMethodId == null) {
                    return null;
                }
                if (inputMethodBindingController.mCurToken != iBinder) {
                    Slog.e("InputMethodManagerService", "Ignoring createInputContentUriToken mCurToken=" + inputMethodBindingController.mCurToken + " token=" + iBinder);
                    return null;
                }
                EditorInfo editorInfo = inputMethodManagerService.mCurEditorInfo;
                String str2 = editorInfo != null ? editorInfo.packageName : null;
                if (TextUtils.equals(str2, str)) {
                    return new InputContentUriTokenHandler(callingUid, str, ContentProvider.getUriWithoutUserId(uri), ContentProvider.getUserIdFromUri(uri, userId), UserHandle.getUserId(inputMethodManagerService.mCurClient.mUid));
                }
                Slog.e("InputMethodManagerService", "Ignoring createInputContentUriToken mCurEditorInfo.packageName=" + str2 + " packageName=" + str);
                return null;
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mdumpAsStringNoCheck, reason: not valid java name */
    public static void m591$$Nest$mdumpAsStringNoCheck(InputMethodManagerService inputMethodManagerService, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        ClientState clientState;
        IInputMethodInvoker curMethodLocked;
        inputMethodManagerService.getClass();
        PrintWriterPrinter printWriterPrinter = new PrintWriterPrinter(printWriter);
        synchronized (ImfLock.class) {
            try {
                InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(inputMethodManagerService.mCurrentUserId);
                printWriterPrinter.println("Current SEP Input Method Manager Service state:");
                printWriterPrinter.println("  mImeWindowVis=" + inputMethodManagerService.mImeWindowVis);
                printWriterPrinter.println("Current Input Method Manager state:");
                List list = inputMethodSettings.mMethodList;
                int size = list.size();
                printWriterPrinter.println("  Input Methods:");
                for (int i = 0; i < size; i++) {
                    InputMethodInfo inputMethodInfo = (InputMethodInfo) list.get(i);
                    printWriterPrinter.println("  InputMethod #" + i + ":");
                    inputMethodInfo.dump(printWriterPrinter, "    ");
                }
                printWriterPrinter.println("  ClientStates:");
                inputMethodManagerService.mClientController.forAllClients(new InputMethodManagerService$$ExternalSyntheticLambda19(1, printWriterPrinter));
                InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId);
                printWriterPrinter.println("  mCurrentUserId=" + inputMethodManagerService.mCurrentUserId);
                printWriterPrinter.println("  mCurMethodId=" + inputMethodManagerService.getSelectedMethodIdLocked());
                clientState = inputMethodManagerService.mCurClient;
                printWriterPrinter.println("  mCurClient=" + clientState + " mCurSeq=" + inputMethodBindingController.mCurSeq);
                StringBuilder sb = new StringBuilder();
                sb.append("  mFocusedWindowPerceptible=");
                sb.append(inputMethodManagerService.mFocusedWindowPerceptible);
                printWriterPrinter.println(sb.toString());
                inputMethodManagerService.mImeBindingState.dump(printWriterPrinter);
                printWriterPrinter.println("  mCurId=" + inputMethodBindingController.mCurId + " mHaveConnection=" + inputMethodBindingController.mHasMainConnection + " mBoundToMethod=" + inputMethodManagerService.mBoundToMethod + " mVisibleBound=" + inputMethodBindingController.mVisibleBound);
                printWriterPrinter.println("  mUserDataRepository=");
                UserDataRepository userDataRepository = inputMethodManagerService.mUserDataRepository;
                for (int i2 = 0; i2 < userDataRepository.mUserData.size(); i2++) {
                    UserDataRepository.UserData userData = (UserDataRepository.UserData) userDataRepository.mUserData.valueAt(i2);
                    printWriterPrinter.println("    mUserId=" + userData.mUserId);
                    StringBuilder sb2 = new StringBuilder("      hasMainConnection=");
                    InputMethodBindingController inputMethodBindingController2 = userData.mBindingController;
                    sb2.append(inputMethodBindingController2.mHasMainConnection);
                    printWriterPrinter.println(sb2.toString());
                    printWriterPrinter.println("      isVisibleBound=" + inputMethodBindingController2.mVisibleBound);
                }
                printWriterPrinter.println("  mCurToken=" + inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId).mCurToken);
                printWriterPrinter.println("  mCurTokenDisplayId=" + inputMethodManagerService.getCurTokenDisplayIdLocked());
                printWriterPrinter.println("  mCurHostInputToken=" + inputMethodBindingController.mAutofillController.mCurHostInputToken);
                printWriterPrinter.println("  mCurIntent=" + inputMethodBindingController.mCurIntent);
                curMethodLocked = inputMethodManagerService.getCurMethodLocked();
                printWriterPrinter.println("  mCurMethod=" + inputMethodManagerService.getCurMethodLocked());
                printWriterPrinter.println("  mEnabledSession=" + inputMethodManagerService.mEnabledSession);
                inputMethodManagerService.mVisibilityStateComputer.dump(printWriter);
                printWriterPrinter.println("  mInFullscreenMode=" + inputMethodManagerService.mInFullscreenMode);
                printWriterPrinter.println("  mSystemReady=" + inputMethodManagerService.mSystemReady + " mInteractive=" + inputMethodManagerService.mIsInteractive);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("  mExperimentalConcurrentMultiUserModeEnabled=");
                sb3.append(inputMethodManagerService.mExperimentalConcurrentMultiUserModeEnabled);
                printWriterPrinter.println(sb3.toString());
                printWriterPrinter.println("  ENABLE_HIDE_IME_CAPTION_BAR=true");
                printWriterPrinter.println("  mSettingsObserver=" + inputMethodManagerService.mSettingsObserver);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("  mStylusIds=");
                IntArray intArray = inputMethodManagerService.mStylusIds;
                sb4.append(intArray != null ? Arrays.toString(intArray.toArray()) : "");
                printWriterPrinter.println(sb4.toString());
                printWriterPrinter.println("  mSwitchingController:");
                inputMethodManagerService.mSwitchingController.dump(printWriterPrinter);
                printWriterPrinter.println("  mStartInputHistory:");
                inputMethodManagerService.mStartInputHistory.dump(printWriter);
                printWriterPrinter.println("  mSoftInputShowHideHistory:");
                inputMethodManagerService.mSoftInputShowHideHistory.dump(printWriter);
                printWriterPrinter.println("  mImeTrackerService#History:");
                ImeTrackerService imeTrackerService = inputMethodManagerService.mImeTrackerService;
                synchronized (imeTrackerService.mLock) {
                    ImeTrackerService.History.m587$$Nest$mdump(imeTrackerService.mHistory, printWriter);
                }
                printWriterPrinter.println("  mSoftInputShowHideHistory End");
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            return;
        }
        printWriterPrinter.println(" ");
        if (clientState != null) {
            printWriter.flush();
            try {
                TransferPipe.dumpAsync(clientState.mClient.mTarget.asBinder(), fileDescriptor, strArr);
            } catch (RemoteException | IOException e) {
                printWriterPrinter.println("Failed to dump input method client: " + e);
            }
        } else {
            printWriterPrinter.println("No input method client.");
        }
        ClientState clientState2 = inputMethodManagerService.mImeBindingState.mFocusedWindowClient;
        if (clientState2 != null && clientState != clientState2) {
            printWriterPrinter.println(" ");
            printWriterPrinter.println("Warning: Current input method client doesn't match the last focused. window.");
            printWriterPrinter.println("Dumping input method client in the last focused window just in case.");
            printWriterPrinter.println(" ");
            printWriter.flush();
            try {
                TransferPipe.dumpAsync(inputMethodManagerService.mImeBindingState.mFocusedWindowClient.mClient.mTarget.asBinder(), fileDescriptor, strArr);
            } catch (RemoteException | IOException e2) {
                printWriterPrinter.println("Failed to dump input method client in focused window: " + e2);
            }
        }
        printWriterPrinter.println(" ");
        if (curMethodLocked == null) {
            printWriterPrinter.println("No input method service.");
            return;
        }
        printWriter.flush();
        try {
            TransferPipe.dumpAsync(curMethodLocked.mTarget.asBinder(), fileDescriptor, strArr);
        } catch (RemoteException | IOException e3) {
            printWriterPrinter.println("Failed to dump input method service: " + e3);
        }
    }

    /* renamed from: -$$Nest$mhandleShellCommandEnableDisableInputMethod, reason: not valid java name */
    public static int m592$$Nest$mhandleShellCommandEnableDisableInputMethod(InputMethodManagerService inputMethodManagerService, ShellCommand shellCommand, boolean z) {
        String nextOption;
        int i;
        boolean z2;
        inputMethodManagerService.getClass();
        do {
            nextOption = shellCommand.getNextOption();
            if (nextOption == null) {
                i = -2;
                break;
            }
            if (nextOption.equals("-u")) {
                break;
            }
        } while (!nextOption.equals("--user"));
        i = UserHandle.parseUserArg(shellCommand.getNextArgRequired());
        String nextArgRequired = shellCommand.getNextArgRequired();
        PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        try {
            PrintWriter errPrintWriter = shellCommand.getErrPrintWriter();
            try {
                synchronized (ImfLock.class) {
                    try {
                        z2 = false;
                        for (int i2 : InputMethodUtils.resolveUserId(i, inputMethodManagerService.mCurrentUserId, shellCommand.getErrPrintWriter())) {
                            if (inputMethodManagerService.userHasDebugPriv(i2, shellCommand)) {
                                z2 |= !inputMethodManagerService.handleShellCommandEnableDisableInputMethodInternalLocked(r2, nextArgRequired, z, outPrintWriter, errPrintWriter);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (errPrintWriter != null) {
                    errPrintWriter.close();
                }
                if (outPrintWriter != null) {
                    outPrintWriter.close();
                }
                return z2 ? -1 : 0;
            } finally {
            }
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0097 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0080 A[Catch: all -> 0x0030, TRY_LEAVE, TryCatch #0 {all -> 0x0030, blocks: (B:3:0x000d, B:14:0x0050, B:18:0x006a, B:23:0x0078, B:36:0x0080, B:37:0x0025, B:40:0x0033, B:43:0x003e), top: B:2:0x000d }] */
    /* renamed from: -$$Nest$mhandleShellCommandTraceInputMethod, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m593$$Nest$mhandleShellCommandTraceInputMethod(com.android.server.inputmethod.InputMethodManagerService r8, android.os.ShellCommand r9) {
        /*
            r8.getClass()
            java.lang.String r0 = "Unknown command: "
            java.lang.String r1 = r9.getNextArgRequired()
            java.io.PrintWriter r9 = r9.getOutPrintWriter()
            int r2 = r1.hashCode()     // Catch: java.lang.Throwable -> L30
            r3 = -390772652(0xffffffffe8b54854, float:-6.8486604E24)
            r4 = 2
            r5 = 1
            r6 = -1
            r7 = 0
            if (r2 == r3) goto L3e
            r3 = 3540994(0x360802, float:4.96199E-39)
            if (r2 == r3) goto L33
            r3 = 109757538(0x68ac462, float:5.219839E-35)
            if (r2 == r3) goto L25
            goto L49
        L25:
            java.lang.String r2 = "start"
            boolean r2 = r1.equals(r2)     // Catch: java.lang.Throwable -> L30
            if (r2 == 0) goto L49
            r2 = r7
            goto L4a
        L30:
            r8 = move-exception
            goto La7
        L33:
            java.lang.String r2 = "stop"
            boolean r2 = r1.equals(r2)     // Catch: java.lang.Throwable -> L30
            if (r2 == 0) goto L49
            r2 = r5
            goto L4a
        L3e:
            java.lang.String r2 = "save-for-bugreport"
            boolean r2 = r1.equals(r2)     // Catch: java.lang.Throwable -> L30
            if (r2 == 0) goto L49
            r2 = r4
            goto L4a
        L49:
            r2 = r6
        L4a:
            if (r2 == 0) goto L80
            if (r2 == r5) goto L78
            if (r2 == r4) goto L6a
            java.lang.String r8 = r0.concat(r1)     // Catch: java.lang.Throwable -> L30
            r9.println(r8)     // Catch: java.lang.Throwable -> L30
            java.lang.String r8 = "Input method trace options:"
            r9.println(r8)     // Catch: java.lang.Throwable -> L30
            java.lang.String r8 = "  start: Start tracing"
            r9.println(r8)     // Catch: java.lang.Throwable -> L30
            java.lang.String r8 = "  stop: Stop tracing"
            r9.println(r8)     // Catch: java.lang.Throwable -> L30
            r9.close()
            goto La3
        L6a:
            com.android.internal.inputmethod.ImeTracing r8 = com.android.internal.inputmethod.ImeTracing.getInstance()     // Catch: java.lang.Throwable -> L30
            r8.saveForBugreport(r9)     // Catch: java.lang.Throwable -> L30
            if (r9 == 0) goto L76
            r9.close()
        L76:
            r6 = r7
            goto La3
        L78:
            com.android.internal.inputmethod.ImeTracing r0 = com.android.internal.inputmethod.ImeTracing.getInstance()     // Catch: java.lang.Throwable -> L30
            r0.stopTrace(r9)     // Catch: java.lang.Throwable -> L30
            goto L87
        L80:
            com.android.internal.inputmethod.ImeTracing r0 = com.android.internal.inputmethod.ImeTracing.getInstance()     // Catch: java.lang.Throwable -> L30
            r0.startTrace(r9)     // Catch: java.lang.Throwable -> L30
        L87:
            if (r9 == 0) goto L8c
            r9.close()
        L8c:
            com.android.internal.inputmethod.ImeTracing r9 = com.android.internal.inputmethod.ImeTracing.getInstance()
            boolean r9 = r9.isEnabled()
            java.lang.Class<com.android.server.inputmethod.ImfLock> r0 = com.android.server.inputmethod.ImfLock.class
            monitor-enter(r0)
            com.android.server.inputmethod.ClientController r8 = r8.mClientController     // Catch: java.lang.Throwable -> La4
            com.android.server.inputmethod.InputMethodManagerService$6 r1 = new com.android.server.inputmethod.InputMethodManagerService$6     // Catch: java.lang.Throwable -> La4
            r1.<init>()     // Catch: java.lang.Throwable -> La4
            r8.forAllClients(r1)     // Catch: java.lang.Throwable -> La4
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La4
            goto L76
        La3:
            return r6
        La4:
            r8 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> La4
            throw r8
        La7:
            if (r9 == 0) goto Lb1
            r9.close()     // Catch: java.lang.Throwable -> Lad
            goto Lb1
        Lad:
            r9 = move-exception
            r8.addSuppressed(r9)
        Lb1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.m593$$Nest$mhandleShellCommandTraceInputMethod(com.android.server.inputmethod.InputMethodManagerService, android.os.ShellCommand):int");
    }

    /* renamed from: -$$Nest$mhideMySoftInput, reason: not valid java name */
    public static void m594$$Nest$mhideMySoftInput(InputMethodManagerService inputMethodManagerService, IBinder iBinder, ImeTracker.Token token, int i, int i2) {
        ClientState clientState;
        IInputMethodClientInvoker iInputMethodClientInvoker;
        inputMethodManagerService.getClass();
        Slog.i("InputMethodManagerService", "hideMySoftInput: flags=" + i);
        try {
            Trace.traceBegin(32L, "IMMS.hideMySoftInput");
            synchronized (ImfLock.class) {
                if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                    ImeTracker.forLogging().onProgress(token, 47);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (Flags.refactorInsetsController()) {
                            inputMethodManagerService.mCurClient.mClient.setImeVisibility(false);
                            ImeBindingState imeBindingState = inputMethodManagerService.mImeBindingState;
                            if (imeBindingState != null && (clientState = imeBindingState.mFocusedWindowClient) != null && (iInputMethodClientInvoker = clientState.mClient) != null) {
                                iInputMethodClientInvoker.setImeVisibility(false);
                            }
                        } else {
                            inputMethodManagerService.hideCurrentInputLocked(inputMethodManagerService.mLastImeTargetWindow, token, i, null, i2);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } else {
                    ImeTracker.forLogging().onFailed(token, 47);
                }
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    /* renamed from: -$$Nest$msemComputeImeDisplayIdForDexTarget, reason: not valid java name */
    public static int m595$$Nest$msemComputeImeDisplayIdForDexTarget(InputMethodManagerService inputMethodManagerService) {
        if (!inputMethodManagerService.isDeskTopMode() || inputMethodManagerService.shouldShowImeKeyboardDefaultDisplayOnly()) {
            return 0;
        }
        int currentFocusDisplayID = inputMethodManagerService.getCurrentFocusDisplayID();
        ProxyManager$$ExternalSyntheticOutline0.m(currentFocusDisplayID, "semComputeImeDisplayIdForTarget: displayId=", "InputMethodManagerService");
        return currentFocusDisplayID;
    }

    /* renamed from: -$$Nest$msetDefaultInputMethod, reason: not valid java name */
    public static void m596$$Nest$msetDefaultInputMethod(InputMethodManagerService inputMethodManagerService) {
        if (inputMethodManagerService.isSamsungDefaultMethodID() || !inputMethodManagerService.isHoneyboardInstalled() || "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(inputMethodManagerService.getSelectedMethodIdLocked())) {
            return;
        }
        try {
            inputMethodManagerService.setInputMethodLocked(InputMethodSettingsRepository.get(inputMethodManagerService.mCurrentUserId).getSelectedInputMethodSubtypeId("com.samsung.android.honeyboard/.service.HoneyBoardService"), 0, "com.samsung.android.honeyboard/.service.HoneyBoardService");
        } catch (IllegalArgumentException e) {
            Slog.e("InputMethodManagerService", e.getMessage());
        }
    }

    /* renamed from: -$$Nest$msetInputMethodAndSubtype, reason: not valid java name */
    public static void m597$$Nest$msetInputMethodAndSubtype(InputMethodManagerService inputMethodManagerService, IBinder iBinder, String str, InputMethodSubtype inputMethodSubtype) {
        inputMethodManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        synchronized (ImfLock.class) {
            try {
                if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                    if (inputMethodManagerService.isDeskTopMode() && !"com.samsung.android.honeyboard/.service.HoneyBoardService".equals(str)) {
                        Slog.w("InputMethodManagerService", "setInputMethodAndSubtype ignore id : " + str + " in dex mode");
                        return;
                    }
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(inputMethodManagerService.mCurrentUserId);
                    InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(str);
                    if (inputMethodInfo == null || !inputMethodManagerService.canCallerAccessInputMethod(inputMethodInfo.getPackageName(), callingUid, userId, inputMethodSettings)) {
                        throw getExceptionForUnknownImeId(str);
                    }
                    if (inputMethodSubtype != null) {
                        inputMethodManagerService.setInputMethodWithSubtypeIdLocked(SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, inputMethodSubtype.hashCode()), iBinder, str);
                    } else {
                        inputMethodManagerService.setInputMethod(iBinder, str);
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mshowMySoftInput, reason: not valid java name */
    public static void m598$$Nest$mshowMySoftInput(InputMethodManagerService inputMethodManagerService, IBinder iBinder, ImeTracker.Token token, int i, int i2) {
        ClientState clientState;
        IInputMethodClientInvoker iInputMethodClientInvoker;
        inputMethodManagerService.getClass();
        Slog.i("InputMethodManagerService", "showMySoftInput: flags=" + i);
        try {
            Trace.traceBegin(32L, "IMMS.showMySoftInput");
            synchronized (ImfLock.class) {
                if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                    ImeTracker.forLogging().onProgress(token, 47);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (Flags.refactorInsetsController()) {
                            inputMethodManagerService.mCurClient.mClient.setImeVisibility(false);
                            ImeBindingState imeBindingState = inputMethodManagerService.mImeBindingState;
                            if (imeBindingState != null && (clientState = imeBindingState.mFocusedWindowClient) != null && (iInputMethodClientInvoker = clientState.mClient) != null) {
                                iInputMethodClientInvoker.setImeVisibility(true);
                            }
                        } else {
                            inputMethodManagerService.showCurrentInputLocked(inputMethodManagerService.mLastImeTargetWindow, token, i, 0, null, i2);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } else {
                    ImeTracker.forLogging().onFailed(token, 47);
                }
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    /* renamed from: -$$Nest$mstartInputUncheckedInnerLocked, reason: not valid java name */
    public static void m599$$Nest$mstartInputUncheckedInnerLocked(int i, InputMethodManagerService inputMethodManagerService) {
        ClientState clientState = inputMethodManagerService.mImeBindingState.mFocusedWindowClient;
        if (clientState != null) {
            inputMethodManagerService.setDisplayImePolicyDexDeskTopMode(clientState.mSelfReportedDisplayId);
        } else {
            Slog.w("InputMethodManagerService", "startInputUncheckedInnerLocked: mImeBindingState.mFocusedWindowClient = null, displayIdToShowIme=" + i);
            inputMethodManagerService.setDisplayImePolicyDexDeskTopMode(i);
        }
        InputMethodBindingController inputMethodBindingController = inputMethodManagerService.mUserDataRepository.getOrCreate(inputMethodManagerService.mCurrentUserId).mBindingController;
        inputMethodBindingController.unbindCurrentMethod();
        inputMethodBindingController.bindCurrentMethod();
    }

    /* renamed from: -$$Nest$mswitchKeyboardLayoutLocked, reason: not valid java name */
    public static void m600$$Nest$mswitchKeyboardLayoutLocked(int i, InputMethodManagerService inputMethodManagerService) {
        Object obj;
        InputMethodInfo inputMethodInfo;
        int i2 = inputMethodManagerService.mCurrentUserId;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i2);
        String selectedMethodIdLocked = inputMethodManagerService.getSelectedMethodIdLocked();
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        InputMethodInfo inputMethodInfo2 = inputMethodMap.get(selectedMethodIdLocked);
        if (inputMethodInfo2 == null) {
            return;
        }
        InputMethodSubtypeHandle of = InputMethodSubtypeHandle.of(inputMethodInfo2, inputMethodManagerService.getInputMethodBindingController(i2).mCurrentSubtype);
        HardwareKeyboardShortcutController hardwareKeyboardShortcutController = inputMethodManagerService.mHardwareKeyboardShortcutController;
        boolean z = i > 0;
        ArrayList arrayList = hardwareKeyboardShortcutController.mSubtypeHandles;
        int size = arrayList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                obj = null;
                break;
            } else if (Objects.equals(of, arrayList.get(i3))) {
                obj = arrayList.get(((i3 + (z ? 1 : -1)) + size) % size);
            } else {
                i3++;
            }
        }
        InputMethodSubtypeHandle inputMethodSubtypeHandle = (InputMethodSubtypeHandle) obj;
        if (inputMethodSubtypeHandle == null || (inputMethodInfo = inputMethodMap.get(inputMethodSubtypeHandle.getImeId())) == null) {
            return;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        if (subtypeCount == 0) {
            if (inputMethodSubtypeHandle.equals(InputMethodSubtypeHandle.of(inputMethodInfo, (InputMethodSubtype) null))) {
                inputMethodManagerService.setInputMethodLocked(-1, 0, inputMethodInfo.getId());
            }
        } else {
            for (int i4 = 0; i4 < subtypeCount; i4++) {
                if (inputMethodSubtypeHandle.equals(InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodInfo.getSubtypeAt(i4)))) {
                    inputMethodManagerService.setInputMethodLocked(i4, 0, inputMethodInfo.getId());
                    return;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mswitchToNextInputMethod, reason: not valid java name */
    public static boolean m601$$Nest$mswitchToNextInputMethod(InputMethodManagerService inputMethodManagerService, IBinder iBinder, boolean z) {
        boolean z2;
        inputMethodManagerService.getClass();
        synchronized (ImfLock.class) {
            try {
                z2 = false;
                if (inputMethodManagerService.calledWithValidTokenLocked(iBinder)) {
                    InputMethodBindingController inputMethodBindingController = inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId);
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(inputMethodBindingController.mUserId);
                    InputMethodSubtypeSwitchingController.ImeSubtypeListItem nextInputMethodLocked = inputMethodManagerService.mSwitchingController.getNextInputMethodLocked(z, inputMethodSettings.mMethodMap.get(inputMethodBindingController.mSelectedMethodId), inputMethodBindingController.mCurrentSubtype);
                    if (nextInputMethodLocked != null) {
                        inputMethodManagerService.setInputMethodWithSubtypeIdLocked(nextInputMethodLocked.mSubtypeId, iBinder, nextInputMethodLocked.mImi.getId());
                        z2 = true;
                    }
                }
            } finally {
            }
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ea A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:4:0x0006, B:6:0x000d, B:10:0x0013, B:12:0x0024, B:13:0x0030, B:16:0x0037, B:21:0x0055, B:22:0x005f, B:27:0x0071, B:29:0x007b, B:31:0x0085, B:34:0x0099, B:36:0x00a5, B:38:0x00ab, B:40:0x00b7, B:42:0x00c1, B:44:0x00ca, B:49:0x00e1, B:54:0x008a, B:55:0x006a, B:56:0x00e4, B:58:0x00ea, B:59:0x00ed, B:62:0x00f0, B:64:0x004d), top: B:3:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f0 A[Catch: all -> 0x0010, DONT_GENERATE, TryCatch #0 {all -> 0x0010, blocks: (B:4:0x0006, B:6:0x000d, B:10:0x0013, B:12:0x0024, B:13:0x0030, B:16:0x0037, B:21:0x0055, B:22:0x005f, B:27:0x0071, B:29:0x007b, B:31:0x0085, B:34:0x0099, B:36:0x00a5, B:38:0x00ab, B:40:0x00b7, B:42:0x00c1, B:44:0x00ca, B:49:0x00e1, B:54:0x008a, B:55:0x006a, B:56:0x00e4, B:58:0x00ea, B:59:0x00ed, B:62:0x00f0, B:64:0x004d), top: B:3:0x0006 }] */
    /* renamed from: -$$Nest$mswitchToPreviousInputMethod, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean m602$$Nest$mswitchToPreviousInputMethod(com.android.server.inputmethod.InputMethodManagerService r14, android.os.IBinder r15) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.m602$$Nest$mswitchToPreviousInputMethod(com.android.server.inputmethod.InputMethodManagerService, android.os.IBinder):boolean");
    }

    /* renamed from: -$$Nest$mswitchUserOnHandlerLocked, reason: not valid java name */
    public static void m603$$Nest$mswitchUserOnHandlerLocked(InputMethodManagerService inputMethodManagerService, int i, IInputMethodClientInvoker iInputMethodClientInvoker) {
        inputMethodManagerService.onUnbindCurrentMethodByReset();
        inputMethodManagerService.getInputMethodBindingController(inputMethodManagerService.mCurrentUserId).unbindCurrentMethod();
        inputMethodManagerService.unbindCurrentClientLocked(6);
        inputMethodManagerService.maybeInitImeNavbarConfigLocked(i);
        inputMethodManagerService.mSettingsObserver.registerContentObserverLocked(i);
        inputMethodManagerService.mCurrentUserId = i;
        boolean isEmpty = TextUtils.isEmpty(SecureSettingsWrapper.getString("default_input_method", null, i));
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        inputMethodManagerService.postInputMethodSettingUpdatedLocked(isEmpty);
        if (TextUtils.isEmpty(inputMethodSettings.getSelectedInputMethod())) {
            inputMethodManagerService.resetDefaultImeLocked(inputMethodManagerService.mContext);
        }
        inputMethodManagerService.updateFromSettingsLocked(true);
        if (isEmpty) {
            InputMethodUtils.setNonSelectedSystemImesDisabledUntilUsed(getPackageManagerForUser(inputMethodManagerService.mContext, i), inputMethodSettings.getEnabledInputMethodListWithFilter(null));
        }
        if (inputMethodManagerService.mIsInteractive && iInputMethodClientInvoker != null) {
            ClientState client = inputMethodManagerService.mClientController.getClient(iInputMethodClientInvoker.mTarget.asBinder());
            if (client == null) {
                return;
            }
            boolean z = inputMethodManagerService.mInFullscreenMode;
            IInputMethodClientInvoker iInputMethodClientInvoker2 = client.mClient;
            if (iInputMethodClientInvoker2.mIsProxy) {
                try {
                    iInputMethodClientInvoker2.mTarget.scheduleStartInputIfNecessary(z);
                } catch (RemoteException e) {
                    IInputMethodClientInvoker.logRemoteException(e);
                }
            } else {
                iInputMethodClientInvoker2.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda4(iInputMethodClientInvoker2, z, 2));
            }
        }
        inputMethodManagerService.mInitialUserSwitch = isEmpty;
        ContentResolver contentResolver = inputMethodManagerService.mContext.getContentResolver();
        int i2 = inputMethodSettings.mUserId;
        inputMethodManagerService.mAccessControlEnable = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, i2) != 0;
        inputMethodManagerService.mAccessControlKeyboardBlockEnable = Settings.System.getIntForUser(inputMethodManagerService.mContext.getContentResolver(), "access_control_keyboard_block", 1, i2) != 0;
    }

    /* JADX WARN: Type inference failed for: r10v28, types: [com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.inputmethod.InputMethodManagerService$1] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.server.inputmethod.InputMethodManagerService$5] */
    public InputMethodManagerService(final Context context, boolean z, ServiceThread serviceThread, ServiceThread serviceThread2, IntFunction intFunction) {
        Pair lastSubtypeForInputMethodInternal;
        final int i = 0;
        this.mAccessControlKeyboardBlockEnable = false;
        boolean z2 = true;
        boolean z3 = true;
        this.mMinRefreshRateTokenRelease = new Runnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                InputMethodManagerService inputMethodManagerService = this;
                switch (i2) {
                    case 0:
                        IRefreshRateToken iRefreshRateToken = inputMethodManagerService.mMinRefreshRateToken;
                        if (iRefreshRateToken != null) {
                            try {
                                iRefreshRateToken.release();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            inputMethodManagerService.mMinRefreshRateToken = null;
                            return;
                        }
                        return;
                    default:
                        inputMethodManagerService.getClass();
                        synchronized (ImfLock.class) {
                            IInputMethodInvoker curMethodLocked = inputMethodManagerService.getCurMethodLocked();
                            if (curMethodLocked != null) {
                                try {
                                    curMethodLocked.mTarget.discardHandwritingDelegationText();
                                } catch (RemoteException e2) {
                                    IInputMethodInvoker.logRemoteException(e2);
                                }
                            }
                        }
                        return;
                }
            }
        };
        synchronized (ImfLock.class) {
            try {
                this.mExperimentalConcurrentMultiUserModeEnabled = z;
                this.mContext = context;
                this.mRes = context.getResources();
                SecureSettingsWrapper.onStart(context);
                ServiceThread serviceThread3 = serviceThread != null ? serviceThread : new ServiceThread(-2, "android.imms", true);
                serviceThread3.start();
                final Handler createAsync = Handler.createAsync(serviceThread3.getLooper(), this);
                this.mHandler = createAsync;
                if (serviceThread2 == null) {
                    serviceThread2 = new ServiceThread(-2, "android.imms2", true);
                }
                serviceThread2.start();
                this.mIoHandler = Handler.createAsync(serviceThread2.getLooper());
                InputMethodManagerService$$ExternalSyntheticLambda2 inputMethodManagerService$$ExternalSyntheticLambda2 = new InputMethodManagerService$$ExternalSyntheticLambda2(this);
                SystemLocaleWrapper.sSystemLocale.set(context.getResources().getConfiguration().getLocales());
                context.registerReceiver(new SystemLocaleWrapper.LocaleChangeListener(context, inputMethodManagerService$$ExternalSyntheticLambda2), new IntentFilter("android.intent.action.LOCALE_CHANGED"), null, createAsync);
                this.mImeTrackerService = new ImeTrackerService(serviceThread != null ? serviceThread.getLooper() : Looper.getMainLooper());
                this.mSettingsObserver = new SettingsObserver(createAsync);
                this.mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                this.mActivityManagerInternal = activityManagerInternal;
                this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                this.mInputManagerInternal = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);
                this.mImePlatformCompatUtils = new ImePlatformCompatUtils();
                this.mInputMethodDeviceConfigs = new InputMethodDeviceConfigs();
                UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                this.mUserManagerInternal = userManagerInternal;
                this.mSlotIme = context.getString(17043138);
                this.mShowOngoingImeSwitcherForPhones = false;
                SparseArray sparseArray = InputMethodSettingsRepository.sPerUserMap;
                final UserManagerInternal userManagerInternal2 = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                createAsync.post(new Runnable() { // from class: com.android.server.inputmethod.InputMethodSettingsRepository$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserManagerInternal userManagerInternal3 = UserManagerInternal.this;
                        Handler handler = createAsync;
                        Context context2 = context;
                        userManagerInternal3.addUserLifecycleListener(new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.inputmethod.InputMethodSettingsRepository.1
                            public final /* synthetic */ Handler val$handler;

                            public AnonymousClass1(Handler handler2) {
                                r1 = handler2;
                            }

                            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
                            public final void onUserRemoved(UserInfo userInfo) {
                                final int i2 = userInfo.id;
                                r1.post(new Runnable() { // from class: com.android.server.inputmethod.InputMethodSettingsRepository$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i3 = i2;
                                        synchronized (ImfLock.class) {
                                            InputMethodSettingsRepository.sPerUserMap.remove(i3);
                                        }
                                    }
                                });
                            }
                        });
                        synchronized (ImfLock.class) {
                            try {
                                for (int i2 : userManagerInternal3.getUserIds()) {
                                    InputMethodSettingsRepository.put(i2, InputMethodManagerService.queryInputMethodServicesInternal(context2, i2, AdditionalSubtypeMapRepository.get(i2), 0));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                SparseArray sparseArray2 = AdditionalSubtypeMapRepository.sPerUserMap;
                final UserManagerInternal userManagerInternal3 = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                createAsync.post(new Runnable() { // from class: com.android.server.inputmethod.AdditionalSubtypeMapRepository$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserManagerInternal userManagerInternal4 = UserManagerInternal.this;
                        userManagerInternal4.addUserLifecycleListener(new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.inputmethod.AdditionalSubtypeMapRepository.1
                            public final /* synthetic */ Context val$context;
                            public final /* synthetic */ Handler val$handler;

                            public AnonymousClass1(Context context2, Handler handler) {
                                r2 = handler;
                                r1 = context2;
                            }

                            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
                            public final void onUserCreated(UserInfo userInfo, Object obj) {
                                final int i2 = userInfo.id;
                                SingleThreadedBackgroundWriter singleThreadedBackgroundWriter = AdditionalSubtypeMapRepository.sWriter;
                                singleThreadedBackgroundWriter.mLock.lock();
                                try {
                                    for (int size = singleThreadedBackgroundWriter.mRemovedUsers.size() - 1; size >= 0; size--) {
                                        if (singleThreadedBackgroundWriter.mRemovedUsers.get(size) == i2) {
                                            singleThreadedBackgroundWriter.mRemovedUsers.remove(size);
                                        }
                                    }
                                    singleThreadedBackgroundWriter.mLock.unlock();
                                    final Context context2 = r1;
                                    r2.post(new Runnable() { // from class: com.android.server.inputmethod.AdditionalSubtypeMapRepository$1$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i3 = i2;
                                            Context context3 = context2;
                                            synchronized (ImfLock.class) {
                                                try {
                                                    SparseArray sparseArray3 = AdditionalSubtypeMapRepository.sPerUserMap;
                                                    if (!sparseArray3.contains(i3)) {
                                                        AdditionalSubtypeMap load = AdditionalSubtypeUtils.load(i3);
                                                        sparseArray3.put(i3, load);
                                                        InputMethodSettingsRepository.put(i3, InputMethodManagerService.queryInputMethodServicesInternal(context3, i3, load, 0));
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                        }
                                    });
                                } catch (Throwable th) {
                                    singleThreadedBackgroundWriter.mLock.unlock();
                                    throw th;
                                }
                            }

                            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
                            public final void onUserRemoved(UserInfo userInfo) {
                                final int i2 = userInfo.id;
                                SingleThreadedBackgroundWriter singleThreadedBackgroundWriter = AdditionalSubtypeMapRepository.sWriter;
                                singleThreadedBackgroundWriter.mLock.lock();
                                try {
                                    singleThreadedBackgroundWriter.mRemovedUsers.add(i2);
                                    singleThreadedBackgroundWriter.mPendingTasks.remove(i2);
                                    singleThreadedBackgroundWriter.mLock.unlock();
                                    r2.post(new Runnable() { // from class: com.android.server.inputmethod.AdditionalSubtypeMapRepository$1$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i3 = i2;
                                            synchronized (ImfLock.class) {
                                                AdditionalSubtypeMapRepository.sPerUserMap.remove(i3);
                                            }
                                        }
                                    });
                                } catch (Throwable th) {
                                    singleThreadedBackgroundWriter.mLock.unlock();
                                    throw th;
                                }
                            }
                        });
                        synchronized (ImfLock.class) {
                            try {
                                for (int i2 : userManagerInternal4.getUserIds()) {
                                    AdditionalSubtypeMapRepository.sPerUserMap.put(i2, AdditionalSubtypeUtils.load(i2));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                this.mCurrentUserId = activityManagerInternal.getCurrentUserId();
                IntFunction intFunction2 = new IntFunction() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda3
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i2) {
                        InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                        inputMethodManagerService.getClass();
                        return new InputMethodBindingController(i2, inputMethodManagerService);
                    }
                };
                if (intFunction == null) {
                    intFunction = intFunction2;
                }
                this.mUserDataRepository = new UserDataRepository(createAsync, userManagerInternal, intFunction);
                for (int i2 : userManagerInternal.getUserIds()) {
                    this.mUserDataRepository.getOrCreate(i2);
                }
                InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
                this.mSwitchingController = new InputMethodSubtypeSwitchingController(context, inputMethodSettings.mMethodMap, inputMethodSettings.mUserId);
                this.mHardwareKeyboardShortcutController = new HardwareKeyboardShortcutController(inputMethodSettings.mMethodMap, inputMethodSettings.mUserId);
                this.mMenuController = new InputMethodMenuController(this);
                WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                WindowManagerInternal windowManagerInternal2 = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                Objects.requireNonNull(windowManagerInternal2);
                this.mVisibilityStateComputer = new ImeVisibilityStateComputer(this, windowManagerInternal, new ImeVisibilityStateComputer$$ExternalSyntheticLambda0(windowManagerInternal2), new ImeVisibilityStateComputer.ImeVisibilityPolicy());
                this.mVisibilityApplier = new DefaultImeVisibilityApplier(this);
                ClientController clientController = new ClientController(this.mPackageManagerInternal);
                this.mClientController = clientController;
                ((ArrayList) clientController.mCallbacks).add(new InputMethodManagerService$$ExternalSyntheticLambda2(this));
                this.mImeBindingState = new ImeBindingState(null, 0, null, null);
                this.mPreventImeStartupUnlessTextEditor = this.mRes.getBoolean(R.bool.config_preventImeStartupUnlessTextEditor);
                this.mNonPreemptibleInputMethods = this.mRes.getStringArray(17236265);
                final boolean z4 = z3 ? 1 : 0;
                this.mHwController = new HandwritingModeController(this.mContext, serviceThread3.getLooper(), new InkWindowInitializer(), new Runnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = z4;
                        InputMethodManagerService inputMethodManagerService = this;
                        switch (i22) {
                            case 0:
                                IRefreshRateToken iRefreshRateToken = inputMethodManagerService.mMinRefreshRateToken;
                                if (iRefreshRateToken != null) {
                                    try {
                                        iRefreshRateToken.release();
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                    inputMethodManagerService.mMinRefreshRateToken = null;
                                    return;
                                }
                                return;
                            default:
                                inputMethodManagerService.getClass();
                                synchronized (ImfLock.class) {
                                    IInputMethodInvoker curMethodLocked = inputMethodManagerService.getCurMethodLocked();
                                    if (curMethodLocked != null) {
                                        try {
                                            curMethodLocked.mTarget.discardHandwritingDelegationText();
                                        } catch (RemoteException e2) {
                                            IInputMethodInvoker.logRemoteException(e2);
                                        }
                                    }
                                }
                                return;
                        }
                    }
                });
                registerDeviceListenerAndCheckStylusSupport();
                this.mSemImmsUtil = new SemInputMethodManagerServiceUtil(context, this);
                buildKeyboardTypeMouseList(context);
                this.mInitialUserSwitch = false;
                WindowManagerService asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                this.mWMS = asInterface;
                context.getPackageManager().hasSystemFeature("com.sec.feature.folder_type");
                this.mDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
                this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
                this.mContentResolver = context.getContentResolver();
                registerSamsungAdditionalReceivers();
                String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
                boolean z5 = !TextUtils.isEmpty(selectedInputMethod);
                this.mImeSelectedOnBoot = z5;
                Slog.d("InputMethodManagerService", "Initial default ime = " + selectedInputMethod + " mImeSelectedOnBoot :" + z5);
                if (ViewRune.SUPPORT_WRITING_TOOLKIT && "com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(selectedInputMethod) && (lastSubtypeForInputMethodInternal = inputMethodSettings.getLastSubtypeForInputMethodInternal(null)) != null) {
                    Slog.d("InputMethodManagerService", "InputMethodManagerService: restore last ime before toolkitHbd=" + ((String) lastSubtypeForInputMethodInternal.first));
                    inputMethodSettings.putSelectedInputMethod((String) lastSubtypeForInputMethodInternal.first);
                }
                if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "access_control_keyboard_block", 1, inputMethodSettings.mUserId) == 0) {
                    z2 = false;
                }
                this.mAccessControlKeyboardBlockEnable = z2;
                if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION") <= 0) {
                    Slog.i("InputMethodManagerService", "Spen input disable");
                } else {
                    Slog.i("InputMethodManagerService", "Spen input enable");
                    asInterface.registerPointerEventListener(this.mPointerEventListener, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void clearClientSessionForAccessibilityLocked(ClientState clientState) {
        for (int i = 0; i < clientState.mAccessibilitySessions.size(); i++) {
            finishSessionForAccessibilityLocked((AccessibilitySessionState) clientState.mAccessibilitySessions.valueAt(i));
        }
        clientState.mAccessibilitySessions.clear();
        clientState.mSessionRequestedForAccessibility = false;
    }

    public static SparseArray createAccessibilityInputMethodSessions(SparseArray sparseArray) {
        SparseArray sparseArray2 = new SparseArray();
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                sparseArray2.append(sparseArray.keyAt(i), ((AccessibilitySessionState) sparseArray.valueAt(i)).mSession);
            }
        }
        return sparseArray2;
    }

    public static void experimentalInitializeVisibleBackgroundUserLocked(int i) {
        InputMethodInfo mostApplicableDefaultIME;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        String enabledInputMethodsStr = inputMethodSettings.getEnabledInputMethodsStr();
        for (InputMethodInfo inputMethodInfo : inputMethodSettings.mMethodList) {
            if (!inputMethodInfo.isSystem()) {
                return;
            } else {
                enabledInputMethodsStr = InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, inputMethodInfo.getId());
            }
        }
        if (!TextUtils.equals(inputMethodSettings.getEnabledInputMethodsStr(), enabledInputMethodsStr)) {
            inputMethodSettings.putEnabledInputMethodsStr(enabledInputMethodsStr);
        }
        if (!TextUtils.isEmpty(inputMethodSettings.getSelectedInputMethod()) || (mostApplicableDefaultIME = InputMethodInfoUtils.getMostApplicableDefaultIME(inputMethodSettings.getEnabledInputMethodListWithFilter(null))) == null) {
            return;
        }
        inputMethodSettings.putSelectedInputMethod(mostApplicableDefaultIME.getId());
    }

    public static void finishSessionForAccessibilityLocked(AccessibilitySessionState accessibilitySessionState) {
        IAccessibilityInputMethodSession iAccessibilityInputMethodSession;
        if (accessibilitySessionState == null || (iAccessibilityInputMethodSession = accessibilitySessionState.mSession) == null) {
            return;
        }
        try {
            iAccessibilityInputMethodSession.finishSession();
        } catch (RemoteException e) {
            Slog.w("InputMethodManagerService", "Session failed to close due to remote exception", e);
        }
        accessibilitySessionState.mSession = null;
    }

    public static String getCurrentInputMethodPackageName(Context context, ContentResolver contentResolver) {
        if (contentResolver == null) {
            return null;
        }
        return Settings.Secure.getString(context.getContentResolver(), "default_input_method");
    }

    public static String getDexSettings(ContentResolver contentResolver, String str, String str2) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("def", str2);
        try {
            Bundle call = contentResolver.call(DEX_CONTENT_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                return call.getString(str);
            }
        } catch (IllegalArgumentException unused) {
            Slog.e("InputMethodManagerService", "Failed to get settings");
        }
        return str2;
    }

    public static IllegalArgumentException getExceptionForUnknownImeId(String str) {
        return new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown id: ", str));
    }

    public static PackageManager getPackageManagerForUser(Context context, int i) {
        return context.getUserId() == i ? context.getPackageManager() : context.createContextAsUser(UserHandle.of(i), 0).getPackageManager();
    }

    public static boolean isStylusDevice(InputDevice inputDevice) {
        return inputDevice.supportsSource(16386) || inputDevice.supportsSource(49154);
    }

    public static InputMethodSettings queryInputMethodServicesInternal(Context context, int i, AdditionalSubtypeMap additionalSubtypeMap, int i2) {
        if (context.getUserId() != i) {
            context = context.createContextAsUser(UserHandle.of(i), 0);
        }
        int i3 = 268435456;
        if (i2 != 0) {
            if (i2 != 1) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i2, "Unknown directBootAwareness=", ". Falling back to DirectBootAwareness.AUTO", "InputMethodManagerService");
            } else {
                i3 = 786432;
            }
        }
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new Intent("android.view.InputMethod"), PackageManager.ResolveInfoFlags.of(32896 | i3));
        String nullIfEmpty = TextUtils.nullIfEmpty(SecureSettingsWrapper.getString("enabled_input_methods", null, i));
        ArrayList arrayList = new ArrayList();
        InputMethodUtils.splitEnabledImeStr(nullIfEmpty, new InputMethodUtils$$ExternalSyntheticLambda0(arrayList));
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap(queryIntentServices.size());
        for (int i4 = 0; i4 < queryIntentServices.size(); i4++) {
            ResolveInfo resolveInfo = queryIntentServices.get(i4);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            String computeId = InputMethodInfo.computeId(resolveInfo);
            if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)) {
                try {
                    InputMethodInfo inputMethodInfo = new InputMethodInfo(context, resolveInfo, (List) additionalSubtypeMap.mMap.get(computeId));
                    if (!inputMethodInfo.isVrOnly()) {
                        String str = serviceInfo.packageName;
                        if (!serviceInfo.applicationInfo.isSystemApp() && !arrayList.contains(inputMethodInfo.getId()) && ((Integer) arrayMap.getOrDefault(str, 0)).intValue() >= 20) {
                        }
                        arrayMap.put(str, Integer.valueOf(((Integer) arrayMap.getOrDefault(str, 0)).intValue() + 1));
                        arrayMap2.put(inputMethodInfo.getId(), inputMethodInfo);
                    }
                } catch (Exception e) {
                    Slog.wtf("InputMethodManagerService", "Unable to load input method " + computeId, e);
                }
            } else {
                PinnerService$$ExternalSyntheticOutline0.m("Skipping input method ", computeId, ": it does not require the permission android.permission.BIND_INPUT_METHOD", "InputMethodManagerService");
            }
        }
        return new InputMethodSettings(new InputMethodMap(arrayMap2), i);
    }

    public static void requestClientSessionForAccessibilityLocked(ClientState clientState) {
        if (clientState.mSessionRequestedForAccessibility) {
            return;
        }
        clientState.mSessionRequestedForAccessibility = true;
        ArraySet arraySet = new ArraySet();
        for (int i = 0; i < clientState.mAccessibilitySessions.size(); i++) {
            arraySet.add(Integer.valueOf(clientState.mAccessibilitySessions.keyAt(i)));
        }
        AccessibilityManagerInternal.get().createImeSession(arraySet);
    }

    public static void setDexSettings(ContentResolver contentResolver, String str) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", "keyboard_dex");
        bundle.putString("val", str);
        try {
            Slog.d("InputMethodManagerService", "setDexSettings of keyboard_dex ".concat(str));
            contentResolver.call(DEX_CONTENT_URI, "setSettings", (String) null, bundle);
        } catch (IllegalArgumentException unused) {
            Slog.e("InputMethodManagerService", "Failed to set settings");
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) {
        if (!isStylusHandwritingEnabled(this.mContext, i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Can not accept stylus handwriting delegation. Stylus handwriting pref is disabled for user: ", "InputMethodManagerService");
            return false;
        }
        synchronized (ImfLock.class) {
            try {
                if (this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str)) {
                    boolean z = (i2 & 1) != 0;
                    if (!str2.equals(this.mHwController.mDelegatorPackageName) && (!z || !this.mHwController.mDelegatorFromDefaultHomePackage)) {
                        Slog.w("InputMethodManagerService", "Delegator package does not match. Ignoring startStylusHandwriting");
                    } else {
                        if (str.equals(this.mHwController.mDelegatePackageName)) {
                            synchronized (ImfLock.class) {
                                if (this.mHwController.mDelegationConnectionlessFlow) {
                                    IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                                    if (curMethodLocked == null) {
                                        return false;
                                    }
                                    try {
                                        curMethodLocked.mTarget.commitHandwritingDelegationTextIfAvailable();
                                    } catch (RemoteException e) {
                                        IInputMethodInvoker.logRemoteException(e);
                                    }
                                    this.mHwController.clearPendingHandwritingDelegation();
                                } else {
                                    startStylusHandwriting(iInputMethodClient, true, null, null, false);
                                }
                                return true;
                            }
                        }
                        Slog.w("InputMethodManagerService", "Delegate package does not match. Ignoring startStylusHandwriting");
                    }
                } else {
                    Slog.w("InputMethodManagerService", "Delegate package does not belong to the same user. Ignoring startStylusHandwriting");
                }
                return false;
            } finally {
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) {
        boolean acceptStylusHandwritingDelegation = acceptStylusHandwritingDelegation(iInputMethodClient, i, str, str2, i2);
        try {
            iBooleanListener.onResult(acceptStylusHandwritingDelegation);
        } catch (RemoteException e) {
            Slog.e("InputMethodManagerService", "Failed to report result=" + acceptStylusHandwritingDelegation, e);
            e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void addClient(IInputMethodClient iInputMethodClient, IRemoteInputConnection iRemoteInputConnection, int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        Handler handler = this.mHandler;
        IInputMethodClientInvoker iInputMethodClientInvoker = null;
        if (iInputMethodClient != null) {
            boolean isProxy = Binder.isProxy(iInputMethodClient);
            if (isProxy) {
                handler = null;
            }
            iInputMethodClientInvoker = new IInputMethodClientInvoker(iInputMethodClient, isProxy, handler);
        }
        synchronized (ImfLock.class) {
            this.mClientController.addClient(iInputMethodClientInvoker, iRemoteInputConnection, i, callingUid, callingPid);
        }
    }

    public final void addStylusDeviceIdLocked(int i) {
        IntArray intArray = this.mStylusIds;
        if (intArray == null) {
            this.mStylusIds = new IntArray();
        } else if (intArray.indexOf(i) != -1) {
            return;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "New Stylus deviceId", " added.", "InputMethodManagerService");
        this.mStylusIds.add(i);
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
        if (this.mHwController.getCurrentRequestId().isPresent() || !inputMethodBindingController.mSupportsStylusHw) {
            return;
        }
        this.mHandler.obtainMessage(1090).sendToTarget();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) {
        int callingUid = Binder.getCallingUid();
        synchronized (ImfLock.class) {
            try {
                if (canInteractWithImeLocked(callingUid, iInputMethodClient, "addVirtualStylusIdForTestSession", null)) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        addStylusDeviceIdLocked(999999);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void attachNewAccessibilityLocked(int i, boolean z) {
        AccessibilitySessionState accessibilitySessionState;
        AccessibilitySessionState accessibilitySessionState2;
        if (!this.mBoundToAccessibility) {
            AccessibilityManagerInternal.get().bindInput();
            this.mBoundToAccessibility = true;
        }
        if (i != 11) {
            SparseArray sparseArray = this.mCurClient.mAccessibilitySessions;
            SparseArray sparseArray2 = new SparseArray();
            for (int i2 = 0; i2 < this.mEnabledAccessibilitySessions.size(); i2++) {
                if (!sparseArray.contains(this.mEnabledAccessibilitySessions.keyAt(i2)) && (accessibilitySessionState2 = (AccessibilitySessionState) this.mEnabledAccessibilitySessions.valueAt(i2)) != null) {
                    sparseArray2.append(this.mEnabledAccessibilitySessions.keyAt(i2), accessibilitySessionState2.mSession);
                }
            }
            if (sparseArray2.size() > 0) {
                AccessibilityManagerInternal.get().setImeSessionEnabled(sparseArray2, false);
            }
            SparseArray sparseArray3 = new SparseArray();
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                if (!this.mEnabledAccessibilitySessions.contains(sparseArray.keyAt(i3)) && (accessibilitySessionState = (AccessibilitySessionState) sparseArray.valueAt(i3)) != null) {
                    sparseArray3.append(sparseArray.keyAt(i3), accessibilitySessionState.mSession);
                }
            }
            if (sparseArray3.size() > 0) {
                AccessibilityManagerInternal.get().setImeSessionEnabled(sparseArray3, true);
            }
            this.mEnabledAccessibilitySessions = sparseArray;
            AccessibilityManagerInternal.get().startInput(this.mCurRemoteAccessibilityInputConnection, this.mCurEditorInfo, !z);
        }
    }

    public final InputBindResult attachNewInputLocked(int i, boolean z) {
        Binder binder;
        boolean z2;
        IntArray intArray;
        ImeBindingState imeBindingState;
        IBinder iBinder;
        Bundle bundle;
        IInputMethodSession iInputMethodSession;
        IInputMethodSession iInputMethodSession2;
        int i2 = this.mCurrentUserId;
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i2);
        if (!this.mBoundToMethod) {
            IInputMethodInvoker iInputMethodInvoker = inputMethodBindingController.mCurMethod;
            InputBinding inputBinding = this.mCurClient.mBinding;
            iInputMethodInvoker.getClass();
            try {
                iInputMethodInvoker.mTarget.bindInput(inputBinding);
            } catch (RemoteException e) {
                IInputMethodInvoker.logRemoteException(e);
            }
            this.mBoundToMethod = true;
        }
        boolean z3 = !z;
        Binder binder2 = new Binder();
        int i3 = this.mCurrentUserId;
        IBinder iBinder2 = inputMethodBindingController.mCurToken;
        int i4 = inputMethodBindingController.mCurTokenDisplayId;
        String str = inputMethodBindingController.mCurId;
        int userId = UserHandle.getUserId(this.mCurClient.mUid);
        int i5 = this.mCurClient.mSelfReportedDisplayId;
        ImeBindingState imeBindingState2 = this.mImeBindingState;
        StartInputInfo startInputInfo = new StartInputInfo(i3, iBinder2, i4, str, i, z3, userId, i5, imeBindingState2.mFocusedWindow, this.mCurEditorInfo, imeBindingState2.mFocusedWindowSoftInputMode, inputMethodBindingController.mCurSeq);
        this.mImeTargetWindowMap.put(binder2, this.mImeBindingState.mFocusedWindow);
        StartInputHistory startInputHistory = this.mStartInputHistory;
        int i6 = startInputHistory.mNextIndex;
        StartInputHistory.Entry[] entryArr = startInputHistory.mEntries;
        StartInputHistory.Entry entry = entryArr[i6];
        if (entry == null) {
            StartInputHistory.Entry entry2 = new StartInputHistory.Entry();
            entry2.set(startInputInfo);
            entryArr[i6] = entry2;
        } else {
            entry.set(startInputInfo);
        }
        startInputHistory.mNextIndex = (startInputHistory.mNextIndex + 1) % entryArr.length;
        if (i2 == UserHandle.getUserId(this.mCurClient.mUid)) {
            binder = binder2;
            z2 = z3;
            ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).grantImplicitAccess(i2, null, UserHandle.getAppId(inputMethodBindingController.mCurMethodUid), this.mCurClient.mUid, true, false);
        } else {
            binder = binder2;
            z2 = z3;
        }
        int inputMethodNavButtonFlagsLocked = getInputMethodNavButtonFlagsLocked();
        SessionState sessionState = this.mCurClient.mCurSession;
        SessionState sessionState2 = this.mEnabledSession;
        if (sessionState2 != sessionState) {
            if (sessionState2 != null && (iInputMethodSession2 = sessionState2.mSession) != null) {
                IInputMethodInvoker iInputMethodInvoker2 = sessionState2.mMethod;
                iInputMethodInvoker2.getClass();
                try {
                    iInputMethodInvoker2.mTarget.setSessionEnabled(iInputMethodSession2, false);
                } catch (RemoteException e2) {
                    IInputMethodInvoker.logRemoteException(e2);
                }
            }
            this.mEnabledSession = sessionState;
            if (sessionState != null && (iInputMethodSession = sessionState.mSession) != null) {
                IInputMethodInvoker iInputMethodInvoker3 = sessionState.mMethod;
                iInputMethodInvoker3.getClass();
                try {
                    iInputMethodInvoker3.mTarget.setSessionEnabled(iInputMethodSession, true);
                } catch (RemoteException e3) {
                    IInputMethodInvoker.logRemoteException(e3);
                }
            }
        }
        if (sessionState != null) {
            IRemoteInputConnection iRemoteInputConnection = this.mCurInputConnection;
            EditorInfo editorInfo = this.mCurEditorInfo;
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = this.mCurImeDispatcher;
            IInputMethodInvoker iInputMethodInvoker4 = sessionState.mMethod;
            iInputMethodInvoker4.getClass();
            IInputMethod.StartInputParams startInputParams = new IInputMethod.StartInputParams();
            startInputParams.startInputToken = binder;
            startInputParams.remoteInputConnection = iRemoteInputConnection;
            startInputParams.editorInfo = editorInfo;
            startInputParams.restarting = z2;
            startInputParams.navigationBarFlags = inputMethodNavButtonFlagsLocked;
            startInputParams.imeDispatcher = imeOnBackInvokedDispatcher;
            try {
                iInputMethodInvoker4.mTarget.startInput(startInputParams);
            } catch (RemoteException e4) {
                IInputMethodInvoker.logRemoteException(e4);
            }
        }
        EditorInfo editorInfo2 = this.mCurEditorInfo;
        if (editorInfo2 != null && (bundle = editorInfo2.extras) != null) {
            int i7 = bundle.getInt("displayId", -2);
            DeviceIdleController$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i7, "checkDisplayOfStartInputAndUpdateKeyboard: displayId=", ", mFocusedDisplayId="), this.mFocusedDisplayId, "InputMethodManagerService");
            if (this.mFocusedDisplayId != i7 && i7 != -2) {
                this.mFocusedDisplayId = i7;
                if (isDeskTopMode() && !shouldShowImeKeyboardDefaultDisplayOnly()) {
                    this.mDeXDualViewChangeObserver.onChange(true);
                }
            }
        }
        if (Flags.refactorInsetsController()) {
            if (isShowRequestedForCurrentWindow() && (imeBindingState = this.mImeBindingState) != null && (iBinder = imeBindingState.mFocusedWindow) != null) {
                showSoftInputInternal(iBinder);
            }
        } else if (isShowRequestedForCurrentWindow()) {
            ImeTracker.Token token = this.mCurStatsToken;
            if (token == null) {
                token = createStatsTokenForFocusedClient(2, true);
            }
            ImeTracker.Token token2 = token;
            this.mCurStatsToken = null;
            IBinder iBinder3 = this.mImeBindingState.mFocusedWindow;
            ImeVisibilityStateComputer imeVisibilityStateComputer = this.mVisibilityStateComputer;
            showCurrentInputLocked(iBinder3, token2, imeVisibilityStateComputer.mShowForced ? 2 : !imeVisibilityStateComputer.mRequestedShowExplicitly ? 1 : 0, 0, null, 2);
        }
        String str2 = inputMethodBindingController.mCurId;
        InputMethodInfo inputMethodInfo = InputMethodSettingsRepository.get(i2).mMethodMap.get(str2);
        boolean z4 = inputMethodInfo != null && inputMethodInfo.suppressesSpellChecker();
        SparseArray createAccessibilityInputMethodSessions = createAccessibilityInputMethodSessions(this.mCurClient.mAccessibilitySessions);
        if (inputMethodBindingController.mSupportsStylusHw && (intArray = this.mStylusIds) != null && intArray.size() != 0) {
            this.mHwController.mInkWindowInitRunnable = new InkWindowInitializer();
        }
        IInputMethodSession iInputMethodSession3 = sessionState.mSession;
        InputChannel inputChannel = sessionState.mChannel;
        return new InputBindResult(0, iInputMethodSession3, createAccessibilityInputMethodSessions, inputChannel != null ? inputChannel.dup() : null, str2, inputMethodBindingController.mCurSeq, z4);
    }

    public final void buildKeyboardTypeMouseList(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "sip_keyboard_type_mouse_id_list");
        if (string == null || string.isEmpty()) {
            string = "1133:45077,1133:45072,1133:45091,1133:45083,1133:45082,1133:45076,9390:8195";
            Settings.Secure.putString(context.getContentResolver(), "sip_keyboard_type_mouse_id_list", "1133:45077,1133:45072,1133:45091,1133:45083,1133:45082,1133:45076,9390:8195");
        }
        this.mKeyboardTypeMouseIdList = (List) Arrays.stream(string.split(",")).collect(Collectors.toList());
        if (DEBUG_SEP) {
            Slog.i("InputMethodManagerService", "buildKeyboardTypeMouseList: mKeyboardTypeMouseIdList=" + this.mKeyboardTypeMouseIdList);
        }
    }

    public final boolean calledFromValidUserLocked() {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
        if (callingUid == 1000 || userId == inputMethodSettings.mUserId || this.misSecurefolderIdOrDualAppId || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) {
            return true;
        }
        Slog.w("InputMethodManagerService", "--- IPC called from background users. Ignore. callers=" + Debug.getCallers(10));
        return false;
    }

    public final boolean calledWithValidTokenLocked(IBinder iBinder) {
        if (iBinder == null) {
            throw new InvalidParameterException("token must not be null.");
        }
        if (iBinder == getInputMethodBindingController(this.mCurrentUserId).mCurToken) {
            return true;
        }
        Slog.e("InputMethodManagerService", "Ignoring " + Debug.getCaller() + " due to an invalid token. uid:" + Binder.getCallingUid() + " token:" + iBinder);
        return false;
    }

    public final boolean canCallerAccessInputMethod(String str, int i, int i2, InputMethodSettings inputMethodSettings) {
        String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
        ComponentName unflattenFromString = selectedInputMethod != null ? ComponentName.unflattenFromString(selectedInputMethod) : null;
        if (unflattenFromString == null || !unflattenFromString.getPackageName().equals(str)) {
            return !this.mPackageManagerInternal.filterAppAccess(i, i2, str, true);
        }
        return true;
    }

    public final boolean canInteractWithImeLocked(int i, IInputMethodClient iInputMethodClient, String str, ImeTracker.Token token) {
        ClientState clientState = this.mCurClient;
        if (clientState == null || iInputMethodClient == null || clientState.mClient.mTarget.asBinder() != iInputMethodClient.asBinder()) {
            ClientState client = this.mClientController.getClient(iInputMethodClient.asBinder());
            if (client == null) {
                ImeTracker.forLogging().onFailed(token, 2);
                throw new IllegalArgumentException("unknown client " + iInputMethodClient.asBinder());
            }
            ImeTracker.forLogging().onProgress(token, 2);
            if (this.mWindowManagerInternal.hasInputMethodClientFocus(this.mImeBindingState.mFocusedWindow, client.mUid, client.mPid, client.mSelfReportedDisplayId) != 0) {
                Slog.w("InputMethodManagerService", String.format("Ignoring %s of uid %d : %s", str, Integer.valueOf(i), iInputMethodClient));
                return false;
            }
        }
        ImeTracker.forLogging().onProgress(token, 3);
        return true;
    }

    public final boolean canShowInputMethodPickerLocked(IInputMethodClient iInputMethodClient) {
        Intent intent;
        int callingUid = Binder.getCallingUid();
        ClientState clientState = this.mImeBindingState.mFocusedWindowClient;
        if (clientState != null && iInputMethodClient != null && clientState.mClient.mTarget.asBinder() == iInputMethodClient.asBinder()) {
            return true;
        }
        if (this.mCurrentUserId == UserHandle.getUserId(callingUid) && (intent = getInputMethodBindingController(this.mCurrentUserId).mCurIntent) != null) {
            if (this.mPackageManagerInternal.isSameApp(callingUid, UserHandle.getUserId(callingUid), 0L, intent.getComponent().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x009b A[LOOP:0: B:5:0x001a->B:10:0x009b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x009a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int checkBlocklistUsbKeyboardConnected() {
        /*
            r9 = this;
            android.hardware.input.InputManager r0 = r9.mInputManager
            if (r0 != 0) goto L11
            android.content.Context r0 = r9.mContext
            java.lang.String r1 = "input"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.input.InputManager r0 = (android.hardware.input.InputManager) r0
            r9.mInputManager = r0
        L11:
            android.hardware.input.InputManager r0 = r9.mInputManager
            int[] r0 = r0.getInputDeviceIds()
            int r1 = r0.length
            r2 = 0
            r3 = r2
        L1a:
            if (r3 >= r1) goto L9f
            r4 = r0[r3]
            android.hardware.input.InputManager r5 = r9.mInputManager
            android.view.InputDevice r4 = r5.getInputDevice(r4)
            r5 = 1
            if (r4 != 0) goto L29
        L27:
            r6 = r2
            goto L98
        L29:
            boolean r6 = r4.isVirtual()
            if (r6 != 0) goto L38
            int r6 = r4.getKeyboardType()
            r7 = 2
            if (r6 != r7) goto L38
            r6 = r5
            goto L39
        L38:
            r6 = r2
        L39:
            if (r6 == 0) goto L98
            r7 = 8194(0x2002, float:1.1482E-41)
            boolean r7 = r4.supportsSource(r7)
            if (r7 == 0) goto L98
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            int r8 = r4.getVendorId()
            r7.append(r8)
            java.lang.String r8 = ":"
            r7.append(r8)
            int r8 = r4.getProductId()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.util.List r8 = r9.mKeyboardTypeMouseIdList
            int r7 = r8.indexOf(r7)
            if (r7 < 0) goto L98
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "isQwertyKeyboard: name="
            r6.<init>(r7)
            java.lang.String r7 = r4.getName()
            r6.append(r7)
            java.lang.String r7 = " vendorId="
            r6.append(r7)
            int r7 = r4.getVendorId()
            r6.append(r7)
            java.lang.String r7 = " productId="
            r6.append(r7)
            int r4 = r4.getProductId()
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            java.lang.String r6 = "InputMethodManagerService"
            android.util.Slog.d(r6, r4)
            goto L27
        L98:
            if (r6 == 0) goto L9b
            return r5
        L9b:
            int r3 = r3 + 1
            goto L1a
        L9f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.checkBlocklistUsbKeyboardConnected():int");
    }

    public final boolean chooseNewDefaultIMELocked() {
        InputMethodInfo mostApplicableDefaultIME = InputMethodInfoUtils.getMostApplicableDefaultIME(InputMethodSettingsRepository.get(this.mCurrentUserId).getEnabledInputMethodListWithFilter(null));
        if (mostApplicableDefaultIME == null) {
            return false;
        }
        resetSelectedInputMethodAndSubtypeLocked(mostApplicableDefaultIME.getId());
        return true;
    }

    public final ImeTracker.Token createStatsTokenForFocusedClient(int i, boolean z) {
        ImeBindingState imeBindingState = this.mImeBindingState;
        ClientState clientState = imeBindingState.mFocusedWindowClient;
        int i2 = clientState != null ? clientState.mUid : -1;
        EditorInfo editorInfo = imeBindingState.mFocusedWindowEditorInfo;
        return ImeTracker.forLogging().onStart(editorInfo != null ? editorInfo.packageName : BinaryTransparencyService$$ExternalSyntheticOutline0.m(i2, "uid(", ")"), i2, z ? 1 : 2, 6, i, false);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void dismissAndShowAgainInputMethodPicker() {
        if (calledFromValidUserLocked()) {
            Slog.w("InputMethodManagerService", "showAgainInputMehtodPicker");
            this.mHandler.sendEmptyMessage(1023);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "InputMethodManagerService", printWriter)) {
            PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream) {
        synchronized (ImfLock.class) {
            try {
                InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
                long start = protoOutputStream.start(1146756268035L);
                protoOutputStream.write(1138166333441L, getSelectedMethodIdLocked());
                protoOutputStream.write(1120986464258L, inputMethodBindingController.mCurSeq);
                protoOutputStream.write(1138166333443L, Objects.toString(this.mCurClient));
                ImeBindingState imeBindingState = this.mImeBindingState;
                protoOutputStream.write(1138166333444L, this.mWindowManagerInternal.getWindowName(imeBindingState.mFocusedWindow));
                protoOutputStream.write(1138166333446L, InputMethodDebug.softInputModeToString(imeBindingState.mFocusedWindowSoftInputMode));
                protoOutputStream.write(1138166333445L, this.mWindowManagerInternal.getWindowName(this.mLastImeTargetWindow));
                protoOutputStream.write(1138166333446L, InputMethodDebug.softInputModeToString(this.mImeBindingState.mFocusedWindowSoftInputMode));
                EditorInfo editorInfo = this.mCurEditorInfo;
                if (editorInfo != null) {
                    editorInfo.dumpDebug(protoOutputStream, 1146756268039L);
                }
                protoOutputStream.write(1138166333448L, inputMethodBindingController.mCurId);
                ImeVisibilityStateComputer imeVisibilityStateComputer = this.mVisibilityStateComputer;
                protoOutputStream.write(1133871366154L, imeVisibilityStateComputer.mRequestedShowExplicitly);
                protoOutputStream.write(1133871366155L, imeVisibilityStateComputer.mShowForced);
                protoOutputStream.write(1133871366168L, imeVisibilityStateComputer.mPolicy.mA11yRequestingNoSoftKeyboard);
                protoOutputStream.write(1133871366156L, imeVisibilityStateComputer.mInputShown);
                protoOutputStream.write(1133871366157L, this.mInFullscreenMode);
                protoOutputStream.write(1138166333454L, Objects.toString(getInputMethodBindingController(this.mCurrentUserId).mCurToken));
                protoOutputStream.write(1120986464271L, getCurTokenDisplayIdLocked());
                protoOutputStream.write(1133871366160L, this.mSystemReady);
                protoOutputStream.write(1133871366162L, inputMethodBindingController.mHasMainConnection);
                protoOutputStream.write(1133871366163L, this.mBoundToMethod);
                protoOutputStream.write(1133871366164L, this.mIsInteractive);
                protoOutputStream.write(1120986464277L, this.mBackDisposition);
                protoOutputStream.write(1120986464278L, this.mImeWindowVis);
                protoOutputStream.write(1133871366167L, this.mMenuController.mShowImeWithHardKeyboard);
                protoOutputStream.end(start);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void finishSessionLocked(SessionState sessionState) {
        if (sessionState != null) {
            IInputMethodSession iInputMethodSession = sessionState.mSession;
            if (iInputMethodSession != null) {
                try {
                    iInputMethodSession.finishSession();
                } catch (RemoteException e) {
                    Slog.w("InputMethodManagerService", "Session failed to close due to remote exception", e);
                    updateSystemUiLocked(0, this.mBackDisposition);
                }
                sessionState.mSession = null;
            }
            InputChannel inputChannel = sessionState.mChannel;
            if (inputChannel != null) {
                inputChannel.dispose();
                sessionState.mChannel = null;
            }
        }
    }

    public final IInputMethodInvoker getCurMethodLocked() {
        return getInputMethodBindingController(this.mCurrentUserId).mCurMethod;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getCurTokenDisplayId() {
        Slog.v("InputMethodManagerService", "getCurTokenDisplayId : mCurTokenDisplayId=" + getCurTokenDisplayIdLocked());
        return getCurTokenDisplayIdLocked();
    }

    public final int getCurTokenDisplayIdLocked() {
        return getInputMethodBindingController(this.mCurrentUserId).mCurTokenDisplayId;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getCurrentFocusDisplayID() {
        GmsAlarmManager$$ExternalSyntheticOutline0.m(new StringBuilder("getCurrentFocusDisplayID : mFocusedDisplayId"), this.mFocusedDisplayId, "InputMethodManagerService");
        return this.mFocusedDisplayId;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        InputMethodInfo inputMethodInfo;
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
            inputMethodInfo = inputMethodSettings.mMethodMap.get(inputMethodSettings.getSelectedInputMethod());
        }
        return inputMethodInfo;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                if (this.mCurrentUserId == i) {
                    return getCurrentInputMethodSubtypeLocked();
                }
                return InputMethodSettingsRepository.get(i).getCurrentInputMethodSubtypeForNonCurrentUsers();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final InputMethodSubtype getCurrentInputMethodSubtypeLocked() {
        int i = this.mCurrentUserId;
        String str = getInputMethodBindingController(i).mSelectedMethodId;
        if (str == null) {
            return null;
        }
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(str);
        if (inputMethodInfo == null || inputMethodInfo.getSubtypeCount() == 0) {
            return null;
        }
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i);
        InputMethodSubtype inputMethodSubtype = inputMethodBindingController.mCurrentSubtype;
        if (SecureSettingsWrapper.getInt(-1, inputMethodSettings.mUserId, "selected_input_method_subtype") == -1 || inputMethodSubtype == null || SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, inputMethodSubtype.hashCode()) == -1) {
            int selectedInputMethodSubtypeId = inputMethodSettings.getSelectedInputMethodSubtypeId(inputMethodInfo.getId());
            if (selectedInputMethodSubtypeId != -1) {
                inputMethodSubtype = inputMethodInfo.getSubtypeAt(selectedInputMethodSubtypeId);
            } else {
                List enabledInputMethodSubtypeList = inputMethodSettings.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
                if (!enabledInputMethodSubtypeList.isEmpty()) {
                    if (enabledInputMethodSubtypeList.size() == 1) {
                        inputMethodSubtype = (InputMethodSubtype) enabledInputMethodSubtypeList.get(0);
                    } else {
                        String locale = SystemLocaleWrapper.get().get(0).toString();
                        InputMethodSubtype findLastResortApplicableSubtype = SubtypeUtils.findLastResortApplicableSubtype("keyboard", locale, enabledInputMethodSubtypeList);
                        inputMethodSubtype = findLastResortApplicableSubtype != null ? findLastResortApplicableSubtype : SubtypeUtils.findLastResortApplicableSubtype(null, locale, enabledInputMethodSubtypeList);
                    }
                }
            }
        }
        inputMethodBindingController.mCurrentSubtype = inputMethodSubtype;
        return inputMethodSubtype;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean getDexSettingsValue(String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String dexSettings = getDexSettings(this.mContext.getContentResolver(), str, str2);
            boolean equals = "keyboard_dex".equals(str) ? "1".equals(dexSettings) : Boolean.valueOf(dexSettings).booleanValue();
            Slog.d("InputMethodManagerService", "getDexSettingsValue: isDexDualModeEnable=" + equals);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return equals;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfoSafeList getEnabledInputMethodList(int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                int[] resolveUserId = InputMethodUtils.resolveUserId(i, this.mCurrentUserId, null);
                if (resolveUserId.length != 1) {
                    return InputMethodInfoSafeList.empty();
                }
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return InputMethodInfoSafeList.create(getEnabledInputMethodListLocked(resolveUserId[0], callingUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getEnabledInputMethodListLegacy(int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                int[] resolveUserId = InputMethodUtils.resolveUserId(i, this.mCurrentUserId, null);
                if (resolveUserId.length != 1) {
                    return Collections.emptyList();
                }
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List enabledInputMethodListLocked = getEnabledInputMethodListLocked(resolveUserId[0], callingUid);
                    InputMethodInfoUtils.getAuxilaryRemoveList(enabledInputMethodListLocked);
                    return enabledInputMethodListLocked;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getEnabledInputMethodListLocked(int i, int i2) {
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        ArrayList enabledInputMethodListWithFilter = inputMethodSettings.getEnabledInputMethodListWithFilter(null);
        enabledInputMethodListWithFilter.removeIf(new InputMethodManagerService$$ExternalSyntheticLambda13(this, i2, i, inputMethodSettings, 1));
        InputMethodInfoUtils.getAuxilaryRemoveList(enabledInputMethodListWithFilter);
        return enabledInputMethodListWithFilter;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getEnabledInputMethodSubtypeList(String str, boolean z, int i) {
        List emptyList;
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
                    InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(str);
                    emptyList = inputMethodInfo == null ? Collections.emptyList() : !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), callingUid, i, inputMethodSettings) ? Collections.emptyList() : inputMethodSettings.getEnabledInputMethodSubtypeList(inputMethodInfo, z);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return emptyList;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final ImeTrackerService getImeTrackerService() {
        return this.mImeTrackerService;
    }

    public final InputMethodBindingController getInputMethodBindingController(int i) {
        return this.mUserDataRepository.getOrCreate(i).mBindingController;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfoSafeList getInputMethodList(int i, int i2) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                int[] resolveUserId = InputMethodUtils.resolveUserId(i, this.mCurrentUserId, null);
                if (resolveUserId.length != 1) {
                    return InputMethodInfoSafeList.empty();
                }
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return InputMethodInfoSafeList.create(getInputMethodListLocked(resolveUserId[0], i2, callingUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getInputMethodListLegacy(int i, int i2) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            try {
                int[] resolveUserId = InputMethodUtils.resolveUserId(i, this.mCurrentUserId, null);
                if (resolveUserId.length != 1) {
                    return Collections.emptyList();
                }
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return getInputMethodListLocked(resolveUserId[0], i2, callingUid);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getInputMethodListLocked(int i, int i2, int i3) {
        InputMethodSettings queryInputMethodServicesInternal;
        if (i2 == 0) {
            queryInputMethodServicesInternal = InputMethodSettingsRepository.get(i);
        } else {
            queryInputMethodServicesInternal = queryInputMethodServicesInternal(this.mContext, i, AdditionalSubtypeMapRepository.get(i), i2);
        }
        InputMethodSettings inputMethodSettings = queryInputMethodServicesInternal;
        ArrayList arrayList = new ArrayList(inputMethodSettings.mMethodList);
        arrayList.removeIf(new InputMethodManagerService$$ExternalSyntheticLambda13(this, i3, i, inputMethodSettings, 0));
        InputMethodInfoUtils.getAuxilaryRemoveList(arrayList);
        return arrayList;
    }

    public final int getInputMethodNavButtonFlagsLocked() {
        Future future = this.mImeDrawsImeNavBarResLazyInitFuture;
        if (future != null) {
            ConcurrentUtils.waitForFutureNoInterrupt(future, "Waiting for the lazy init of mImeDrawsImeNavBarRes");
        }
        int curTokenDisplayIdLocked = getCurTokenDisplayIdLocked();
        WindowManagerInternal windowManagerInternal = this.mWindowManagerInternal;
        if (curTokenDisplayIdLocked == -1) {
            curTokenDisplayIdLocked = 0;
        }
        boolean hasNavigationBar = windowManagerInternal.hasNavigationBar(curTokenDisplayIdLocked);
        OverlayableSystemBooleanResourceWrapper overlayableSystemBooleanResourceWrapper = this.mImeDrawsImeNavBarRes;
        return ((overlayableSystemBooleanResourceWrapper != null && overlayableSystemBooleanResourceWrapper.mValueRef.get() && hasNavigationBar) ? 1 : 0) | (shouldShowImeSwitcherLocked(3, this.mCurrentUserId) ? 2 : 0);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getInputMethodWindowVisibleHeight(final IInputMethodClient iInputMethodClient) {
        final int callingUid = Binder.getCallingUid();
        return ((Integer) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda17
            public final Object getOrThrow() {
                InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                int i = callingUid;
                IInputMethodClient iInputMethodClient2 = iInputMethodClient;
                inputMethodManagerService.getClass();
                synchronized (ImfLock.class) {
                    try {
                        if (!inputMethodManagerService.canInteractWithImeLocked(i, iInputMethodClient2, "getInputMethodWindowVisibleHeight", null)) {
                            return 0;
                        }
                        return Integer.valueOf(inputMethodManagerService.mWindowManagerInternal.getInputMethodWindowVisibleHeight(inputMethodManagerService.getCurTokenDisplayIdLocked()));
                    } finally {
                    }
                }
            }
        })).intValue();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodSubtype getLastInputMethodSubtype(int i) {
        InputMethodSubtype lastInputMethodSubtype;
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        if (SemPersonaManager.isSecureFolderId(i) || SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        synchronized (ImfLock.class) {
            lastInputMethodSubtype = InputMethodSettingsRepository.get(i).getLastInputMethodSubtype();
        }
        return lastInputMethodSubtype;
    }

    public final String getSelectedMethodIdLocked() {
        return getInputMethodBindingController(this.mCurrentUserId).mSelectedMethodId;
    }

    public final int getTargetInputMethodSubtypeId() {
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
        InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get("com.samsung.android.honeyboard/.service.HoneyBoardService");
        if (inputMethodInfo != null && !TextUtils.isEmpty("com.samsung.android.honeyboard/.service.HoneyBoardService")) {
            Pair lastSubtypeForInputMethodInternal = inputMethodSettings.getLastSubtypeForInputMethodInternal("com.samsung.android.honeyboard/.service.HoneyBoardService");
            String str = lastSubtypeForInputMethodInternal != null ? (String) lastSubtypeForInputMethodInternal.second : null;
            if (str != null) {
                try {
                    return SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    Slog.w("InputMethodManagerService", "HashCode for subtype looks broken: ".concat(str), e);
                }
            }
        }
        return -1;
    }

    public ImeVisibilityApplier getVisibilityApplier() {
        DefaultImeVisibilityApplier defaultImeVisibilityApplier;
        synchronized (ImfLock.class) {
            defaultImeVisibilityApplier = this.mVisibilityApplier;
        }
        return defaultImeVisibilityApplier;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean getWACOMPen() {
        return this.mSemImmsUtil.mSpenLastUsed;
    }

    public final boolean handleDictation(boolean z) {
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(1103, "keyCode");
        if (z) {
            m.putBoolean("needRestoreIME", true);
            m.putParcelable("editorInfo", this.mCurEditorInfo);
        }
        return this.mContext.getContentResolver().call(DICTATION, "handle_dictation_for_hw_voice_key", (String) null, m).getBoolean("dictation_executed", false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v103 */
    /* JADX WARN: Type inference failed for: r0v104 */
    /* JADX WARN: Type inference failed for: r0v107 */
    /* JADX WARN: Type inference failed for: r0v48 */
    /* JADX WARN: Type inference failed for: r0v49 */
    /* JADX WARN: Type inference failed for: r0v54 */
    /* JADX WARN: Type inference failed for: r0v62 */
    /* JADX WARN: Type inference failed for: r0v63 */
    /* JADX WARN: Type inference failed for: r0v67 */
    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        boolean z;
        boolean z2;
        int i;
        ClientState clientState;
        IInputMethodClientInvoker iInputMethodClientInvoker;
        SessionState sessionState;
        IInputMethodSession iInputMethodSession;
        ClientState clientState2;
        Pair lastSubtypeForInputMethodInternal;
        int i2 = 0;
        boolean z3 = false;
        switch (message.what) {
            case 1:
                int i3 = message.arg2;
                int i4 = message.arg1;
                if (i4 == 0) {
                    synchronized (ImfLock.class) {
                        z = this.mVisibilityStateComputer.mInputShown;
                    }
                } else if (i4 == 1) {
                    z = true;
                } else {
                    if (i4 != 2) {
                        VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown subtype picker mode = "), message.arg1, "InputMethodManagerService");
                        return false;
                    }
                    z = false;
                }
                EditorInfo editorInfo = this.mSemImmsUtil.mService.mCurEditorInfo;
                if (editorInfo != null) {
                    String str = editorInfo.privateImeOptions;
                    z2 = (str != null && (str.contains("nm") || str.contains("noMicrophoneKey"))) || (i = editorInfo.inputType & 4095) == 129 || i == 225 || i == 18;
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m("Check voice input Disable : ", "SemInputMethodManagerServiceUtil", z2);
                } else {
                    z2 = false;
                }
                if (z2) {
                    z = false;
                }
                this.mCurrentShowAuxSubtypes = z;
                this.mCurrentDisplayId = i3;
                synchronized (ImfLock.class) {
                    try {
                        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
                        boolean z4 = this.mWindowManagerInternal.isKeyguardLocked() && this.mWindowManagerInternal.isKeyguardSecure(inputMethodSettings.mUserId);
                        String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
                        int selectedInputMethodSubtypeId = inputMethodSettings.getSelectedInputMethodSubtypeId(selectedInputMethod);
                        List sortedInputMethodAndSubtypeList = InputMethodSubtypeSwitchingController.getSortedInputMethodAndSubtypeList(z, z4, true, this.mContext, inputMethodSettings.mMethodMap, inputMethodSettings.mUserId);
                        if (!((ArrayList) sortedInputMethodAndSubtypeList).isEmpty()) {
                            this.mMenuController.showInputMethodMenuLocked(i3, selectedInputMethod, selectedInputMethodSubtypeId, sortedInputMethodAndSubtypeList);
                            return true;
                        }
                        Slog.w("InputMethodManagerService", "Show switching menu failed, imList is empty, showAuxSubtypes: " + z + " isScreenLocked: " + z4 + " userId: " + inputMethodSettings.mUserId);
                        return false;
                    } finally {
                    }
                }
            case 1023:
                AlertDialog alertDialog = this.mMenuController.mSwitchingDialog;
                if (alertDialog != null && alertDialog.isShowing()) {
                    Slog.w("InputMethodManagerService", "MSG_SHOW_AGAIN_IM_PICKER");
                    AlertDialog alertDialog2 = this.mMenuController.mSwitchingDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                    }
                    synchronized (ImfLock.class) {
                        try {
                            InputMethodSettings inputMethodSettings2 = InputMethodSettingsRepository.get(this.mCurrentUserId);
                            boolean z5 = this.mWindowManagerInternal.isKeyguardLocked() && this.mWindowManagerInternal.isKeyguardSecure(inputMethodSettings2.mUserId);
                            String selectedInputMethod2 = inputMethodSettings2.getSelectedInputMethod();
                            int selectedInputMethodSubtypeId2 = inputMethodSettings2.getSelectedInputMethodSubtypeId(selectedInputMethod2);
                            List sortedInputMethodAndSubtypeList2 = InputMethodSubtypeSwitchingController.getSortedInputMethodAndSubtypeList(this.mCurrentShowAuxSubtypes, z5, true, this.mContext, inputMethodSettings2.mMethodMap, inputMethodSettings2.mUserId);
                            if (((ArrayList) sortedInputMethodAndSubtypeList2).isEmpty()) {
                                Slog.w("InputMethodManagerService", "MSG_SHOW_AGAIN_IM_PICKER, imList is empty, showAuxSubtypes: " + this.mCurrentShowAuxSubtypes + " isScreenLocked: " + z5 + " userId: " + inputMethodSettings2.mUserId);
                                return false;
                            }
                            this.mMenuController.showInputMethodMenuLocked(this.mCurrentDisplayId, selectedInputMethod2, selectedInputMethodSubtypeId2, sortedInputMethodAndSubtypeList2);
                        } finally {
                        }
                    }
                }
                return true;
            case 1026:
                updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
                return true;
            case 1035:
                synchronized (ImfLock.class) {
                    try {
                        if (Flags.refactorInsetsController()) {
                            ImeBindingState imeBindingState = this.mImeBindingState;
                            if (imeBindingState != null && (clientState = imeBindingState.mFocusedWindowClient) != null && (iInputMethodClientInvoker = clientState.mClient) != null) {
                                iInputMethodClientInvoker.setImeVisibility(false);
                            }
                        } else {
                            hideCurrentInputLocked(((Integer) message.obj).intValue(), this.mImeBindingState.mFocusedWindow);
                        }
                    } finally {
                    }
                }
                return true;
            case 1060:
                synchronized (ImfLock.class) {
                    try {
                        SessionState sessionState2 = this.mEnabledSession;
                        if (sessionState2 != null && sessionState2.mSession != null && !isShowRequestedForCurrentWindow()) {
                            this.mEnabledSession.mSession.removeImeSurface();
                        }
                    } catch (RemoteException unused) {
                    }
                }
                return true;
            case 1061:
                IBinder iBinder = (IBinder) message.obj;
                synchronized (ImfLock.class) {
                    try {
                        if (iBinder == this.mImeBindingState.mFocusedWindow && (sessionState = this.mEnabledSession) != null && (iInputMethodSession = sessionState.mSession) != null) {
                            iInputMethodSession.removeImeSurface();
                        }
                    } catch (RemoteException unused2) {
                    }
                }
                return true;
            case 1070:
                ?? r0 = message.arg1 == 1;
                synchronized (ImfLock.class) {
                    try {
                        if (r0 == true) {
                            updateSystemUiLocked(0, this.mBackDisposition);
                            updateImeSwitchStatus("disableImeIcon");
                        } else {
                            updateSystemUiLocked$1();
                            updateImeSwitchStatus("enableImeIcon");
                        }
                    } finally {
                    }
                }
                return true;
            case 1090:
                synchronized (ImfLock.class) {
                    try {
                        if (getInputMethodBindingController(this.mCurrentUserId).mSupportsStylusHw && getCurMethodLocked() != null) {
                            IntArray intArray = this.mStylusIds;
                            if (((intArray == null || intArray.size() == 0) ? false : true) != false) {
                                Slog.d("InputMethodManagerService", "Initializing Handwriting Spy");
                                this.mHwController.initializeHandwritingSpy(getCurTokenDisplayIdLocked());
                            }
                        }
                        this.mHwController.reset(false);
                    } finally {
                    }
                }
                return true;
            case 1100:
                synchronized (ImfLock.class) {
                    try {
                        IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                        if (curMethodLocked != null && this.mImeBindingState.mFocusedWindow != null) {
                            HandwritingModeController.HandwritingSession startHandwritingSession = this.mHwController.startHandwritingSession(message.arg1, message.arg2, getInputMethodBindingController(this.mCurrentUserId).mCurMethodUid, this.mImeBindingState.mFocusedWindow);
                            if (startHandwritingSession == null) {
                                Slog.e("InputMethodManagerService", "Failed to start handwriting session for requestId: " + message.arg1);
                                return true;
                            }
                            try {
                                curMethodLocked.mTarget.startStylusHandwriting(startHandwritingSession.mRequestId, startHandwritingSession.mHandwritingChannel, startHandwritingSession.mRecordedEvents);
                            } catch (RemoteException e) {
                                IInputMethodInvoker.logRemoteException(e);
                                Slog.w("InputMethodManagerService", "Resetting handwriting mode.");
                                this.mHandler.obtainMessage(1090).sendToTarget();
                            }
                            return true;
                        }
                        return true;
                    } finally {
                    }
                }
            case 1110:
                synchronized (ImfLock.class) {
                    try {
                        IInputMethodInvoker curMethodLocked2 = getCurMethodLocked();
                        if (curMethodLocked2 != null && this.mHwController.getCurrentRequestId().isPresent()) {
                            curMethodLocked2.mTarget.finishStylusHandwriting();
                        }
                    } catch (RemoteException e2) {
                        IInputMethodInvoker.logRemoteException(e2);
                    } finally {
                    }
                }
                return true;
            case 1120:
                synchronized (ImfLock.class) {
                    IInputMethodInvoker curMethodLocked3 = getCurMethodLocked();
                    if (curMethodLocked3 != null) {
                        try {
                            curMethodLocked3.mTarget.removeStylusHandwritingWindow();
                        } catch (RemoteException e3) {
                            IInputMethodInvoker.logRemoteException(e3);
                        }
                    }
                }
                return true;
            case 1130:
                synchronized (ImfLock.class) {
                    int i5 = message.arg1;
                    Object obj = message.obj;
                    this.mHwController.prepareStylusHandwritingDelegation(i5, (String) ((Pair) obj).first, (String) ((Pair) obj).second, false);
                }
                return true;
            case 3030:
                boolean z6 = message.arg1 != 0;
                synchronized (ImfLock.class) {
                    try {
                        this.mIsInteractive = z6;
                        updateSystemUiLocked(z6 ? this.mImeWindowVis : 0, this.mBackDisposition);
                        clientState2 = this.mCurClient;
                    } catch (RemoteException e4) {
                        IInputMethodClientInvoker.logRemoteException(e4);
                    } finally {
                    }
                    if (clientState2 != null && clientState2.mClient != null) {
                        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
                        ImePlatformCompatUtils imePlatformCompatUtils = this.mImePlatformCompatUtils;
                        int i6 = inputMethodBindingController.mCurMethodUid;
                        imePlatformCompatUtils.getClass();
                        try {
                            z3 = imePlatformCompatUtils.mPlatformCompat.isChangeEnabledByUid(156215187L, i6);
                        } catch (RemoteException unused3) {
                        }
                        if (z3) {
                            ImeVisibilityStateComputer.ImeVisibilityResult onInteractiveChanged = this.mVisibilityStateComputer.onInteractiveChanged(this.mImeBindingState.mFocusedWindow, z6);
                            if (onInteractiveChanged != null) {
                                this.mVisibilityApplier.applyImeVisibility(this.mImeBindingState.mFocusedWindow, null, onInteractiveChanged.mState, onInteractiveChanged.mReason, this.mCurrentUserId);
                            }
                            IInputMethodClientInvoker iInputMethodClientInvoker2 = this.mCurClient.mClient;
                            boolean z7 = this.mIsInteractive;
                            boolean z8 = this.mInFullscreenMode;
                            if (iInputMethodClientInvoker2.mIsProxy) {
                                iInputMethodClientInvoker2.mTarget.setInteractive(z7, z8);
                            } else {
                                iInputMethodClientInvoker2.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda2(iInputMethodClientInvoker2, z7, z8, 0));
                            }
                        } else {
                            this.mCurClient.mClient.setActive(this.mIsInteractive, this.mInFullscreenMode);
                        }
                    }
                }
                return true;
            case 4000:
                InputMethodMenuController inputMethodMenuController = this.mMenuController;
                ?? r02 = message.arg1 == 1;
                inputMethodMenuController.getClass();
                synchronized (ImfLock.class) {
                    try {
                        AlertDialog alertDialog3 = inputMethodMenuController.mSwitchingDialog;
                        if (alertDialog3 != null && inputMethodMenuController.mSwitchInSelectDialogView != null && alertDialog3.isShowing()) {
                            View findViewById = inputMethodMenuController.mSwitchInSelectDialogView.findViewById(R.id.immersive_cling_back_bg);
                            if (r02 == false) {
                                i2 = 8;
                            }
                            findViewById.setVisibility(i2);
                        }
                    } finally {
                    }
                }
                synchronized (ImfLock.class) {
                    sendOnNavButtonFlagsChangedLocked();
                }
                return true;
            case 5000:
                int i7 = message.arg1;
                synchronized (ImfLock.class) {
                    try {
                        if (this.mSystemReady) {
                            InputMethodSettingsRepository.put(i7, queryInputMethodServicesInternal(this.mContext, i7, AdditionalSubtypeMapRepository.get(i7), 0));
                            int i8 = this.mCurrentUserId;
                            if (i8 == i7) {
                                if (ViewRune.SUPPORT_WRITING_TOOLKIT) {
                                    InputMethodSettings inputMethodSettings3 = InputMethodSettingsRepository.get(i8);
                                    if ("com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(inputMethodSettings3.getSelectedInputMethod()) && (lastSubtypeForInputMethodInternal = inputMethodSettings3.getLastSubtypeForInputMethodInternal(null)) != null) {
                                        Slog.i("InputMethodManagerService", "onUnlockUser: restore last ime before toolkitHbd=" + ((String) lastSubtypeForInputMethodInternal.first));
                                        inputMethodSettings3.putSelectedInputMethod((String) lastSubtypeForInputMethodInternal.first);
                                    }
                                }
                                postInputMethodSettingUpdatedLocked(false);
                                updateInputMethodsFromSettingsLocked(true);
                            } else if (this.mExperimentalConcurrentMultiUserModeEnabled) {
                                experimentalInitializeVisibleBackgroundUserLocked(i7);
                            }
                            Slog.d("InputMethodManagerService", "onUnlockUser : mImeSelectedOnBoot=" + this.mImeSelectedOnBoot + " mInitialUserSwitch=" + this.mInitialUserSwitch);
                            postInputMethodSettingUpdatedLocked(!this.mImeSelectedOnBoot || this.mInitialUserSwitch);
                            updateInputMethodsFromSettingsLocked(true);
                        }
                    } finally {
                    }
                }
                return true;
            case 5010:
                this.mInputMethodListListeners.forEach(new InputMethodManagerService$$ExternalSyntheticLambda8(0));
                return true;
            case 7000:
                if (this.mAudioManagerInternal == null) {
                    this.mAudioManagerInternal = (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
                }
                AudioManagerInternal audioManagerInternal = this.mAudioManagerInternal;
                if (audioManagerInternal != null) {
                    audioManagerInternal.setInputMethodServiceUid(message.arg1);
                }
                return true;
            default:
                return false;
        }
    }

    public final boolean handleShellCommandEnableDisableInputMethodInternalLocked(int i, String str, boolean z, PrintWriter printWriter, PrintWriter printWriter2) {
        boolean buildAndPutEnabledInputMethodsStrRemovingId;
        boolean z2;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        int i2 = this.mCurrentUserId;
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        if (i == i2) {
            if (!z || inputMethodMap.mMap.containsKey(str)) {
                buildAndPutEnabledInputMethodsStrRemovingId = setInputMethodEnabledLocked(str, z);
                z2 = false;
            }
            z2 = true;
            buildAndPutEnabledInputMethodsStrRemovingId = false;
        } else {
            if (z) {
                if (inputMethodMap.mMap.containsKey(str)) {
                    String enabledInputMethodsStr = inputMethodSettings.getEnabledInputMethodsStr();
                    String concatEnabledImeIds = InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
                    buildAndPutEnabledInputMethodsStrRemovingId = TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds);
                    if (!buildAndPutEnabledInputMethodsStrRemovingId) {
                        inputMethodSettings.putEnabledInputMethodsStr(concatEnabledImeIds);
                    }
                }
                z2 = true;
                buildAndPutEnabledInputMethodsStrRemovingId = false;
            } else {
                buildAndPutEnabledInputMethodsStrRemovingId = inputMethodSettings.buildAndPutEnabledInputMethodsStrRemovingId(new StringBuilder(), inputMethodSettings.getEnabledInputMethodsAndSubtypeList(), str);
            }
            z2 = false;
        }
        if (z2) {
            printWriter2.print("Unknown input method ");
            printWriter2.print(str);
            printWriter2.println(" cannot be enabled for user #" + i);
            Slog.e("InputMethodManagerService", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "\"ime enable ", str, "\" for user #", " failed due to its unrecognized IME ID."));
            return false;
        }
        printWriter.print("Input method ");
        printWriter.print(str);
        printWriter.print(": ");
        printWriter.print(z == buildAndPutEnabledInputMethodsStrRemovingId ? "already " : "now ");
        printWriter.print(z ? "enabled" : "disabled");
        printWriter.print(" for user #");
        printWriter.println(i);
        return true;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void handleVoiceHWKey() {
        long clearCallingIdentity;
        synchronized (ImfLock.class) {
            try {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                } catch (Exception e) {
                    Slog.wtf("InputMethodManagerService", "handleVoiceHWKey: exception:", e);
                }
                if (!InputMethodSettingsRepository.get(this.mCurrentUserId).isShowImeWithHardKeyboardEnabled() && this.mWindowManagerInternal.isHardKeyboardAvailable()) {
                    Slog.d("InputMethodManagerService", "handleVoiceHWKey: show ime with hard keyboard disable, skip");
                    return;
                }
                boolean isCurrentInputMethodAsSamsungKeyboard = isCurrentInputMethodAsSamsungKeyboard();
                if (!handleDictation(!isCurrentInputMethodAsSamsungKeyboard)) {
                    Slog.d("InputMethodManagerService", "handleVoiceHWKey: voice input disable or need close voice");
                    return;
                }
                if (!isCurrentInputMethodAsSamsungKeyboard) {
                    setInputMethodLocked(getTargetInputMethodSubtypeId(), 0, "com.samsung.android.honeyboard/.service.HoneyBoardService");
                }
                if (!isInputMethodShown()) {
                    if (isCurrentInputMethodAsSamsungKeyboard) {
                        showCurrentInputLocked(this.mImeBindingState.mFocusedWindow, createStatsTokenForFocusedClient(23, true), 1, 0, null, 23);
                    } else {
                        this.mVisibilityStateComputer.requestImeVisibility(this.mImeBindingState.mFocusedWindow, true);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean hideCurrentInputLocked(int i, IBinder iBinder) {
        return hideCurrentInputLocked(iBinder, createStatsTokenForFocusedClient(i, false), 0, null, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:
    
        if ((r6.mImeWindowVis & 1) == 0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hideCurrentInputLocked(android.os.IBinder r7, android.view.inputmethod.ImeTracker.Token r8, int r9, android.os.ResultReceiver r10, int r11) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.hideCurrentInputLocked(android.os.IBinder, android.view.inputmethod.ImeTracker$Token, int, android.os.ResultReceiver, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0082 A[Catch: all -> 0x003d, TryCatch #1 {all -> 0x003d, blocks: (B:4:0x0023, B:7:0x002b, B:11:0x0077, B:13:0x0082, B:15:0x0088, B:16:0x0097, B:17:0x00ac, B:20:0x0090, B:21:0x00ae, B:37:0x00de, B:38:0x00e4, B:40:0x00e6, B:41:0x00ec, B:44:0x0103, B:45:0x0109, B:48:0x010b, B:49:0x0111, B:52:0x0046, B:55:0x0067, B:56:0x006d, B:60:0x0070, B:61:0x0076, B:23:0x00b2, B:25:0x00bd, B:27:0x00c1, B:29:0x00c5, B:31:0x00c9, B:35:0x00d7, B:43:0x00ee, B:54:0x004a), top: B:3:0x0023, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ae A[Catch: all -> 0x003d, TRY_LEAVE, TryCatch #1 {all -> 0x003d, blocks: (B:4:0x0023, B:7:0x002b, B:11:0x0077, B:13:0x0082, B:15:0x0088, B:16:0x0097, B:17:0x00ac, B:20:0x0090, B:21:0x00ae, B:37:0x00de, B:38:0x00e4, B:40:0x00e6, B:41:0x00ec, B:44:0x0103, B:45:0x0109, B:48:0x010b, B:49:0x0111, B:52:0x0046, B:55:0x0067, B:56:0x006d, B:60:0x0070, B:61:0x0076, B:23:0x00b2, B:25:0x00bd, B:27:0x00c1, B:29:0x00c5, B:31:0x00c9, B:35:0x00d7, B:43:0x00ee, B:54:0x004a), top: B:3:0x0023, inners: #0, #2 }] */
    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient r14, android.os.IBinder r15, android.view.inputmethod.ImeTracker.Token r16, int r17, android.os.ResultReceiver r18, int r19) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.hideSoftInput(com.android.internal.inputmethod.IInputMethodClient, android.os.IBinder, android.view.inputmethod.ImeTracker$Token, int, android.os.ResultReceiver, int):boolean");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void hideSoftInputFromServerForTest() {
        synchronized (ImfLock.class) {
            hideCurrentInputLocked(4, this.mImeBindingState.mFocusedWindow);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int isAccessoryKeyboard() {
        SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = this.mSamsungIMMSHWKeyboard;
        int i = samsungIMMSHWKeyboard.keyboardState;
        if ((i != 0 && (i & (-73)) == 0) && (samsungIMMSHWKeyboard.isPogoBackfolded() || this.mRes.getConfiguration().orientation == 1)) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "isAccessoryKeyboard ", ", PogoKeyboard connectedOnly=");
            int i2 = this.mSamsungIMMSHWKeyboard.keyboardState;
            m.append(i2 != 0 && (i2 & (-73)) == 0);
            m.append(", backfolded=");
            m.append(this.mSamsungIMMSHWKeyboard.isPogoBackfolded());
            m.append(", orientation=");
            SystemServiceManager$$ExternalSyntheticOutline0.m(m, this.mRes.getConfiguration().orientation, "InputMethodManagerService");
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i == 0) {
            try {
                i = checkBlocklistUsbKeyboardConnected();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Slog.i("InputMethodManagerService", "isAccessoryKeyboard " + i);
        return i;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isCurrentInputMethodAsSamsungKeyboard() {
        return "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(getCurrentInputMethodPackageName(this.mContext, this.mContentResolver));
    }

    public final boolean isDEXStandAloneMode() {
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager == null) {
            Slog.d("InputMethodManagerService", "mDesktopModeManager null!");
            return false;
        }
        SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
        if (desktopModeState == null) {
            return false;
        }
        Slog.d("InputMethodManagerService", "DESKTOP TYPE! : " + desktopModeState.getDisplayType());
        if (desktopModeState.getDisplayType() == 101) {
            Slog.d("InputMethodManagerService", "IN KNOX DESKTOP MODE with STAND ALONE!");
            return true;
        }
        Slog.d("InputMethodManagerService", "NOT IN KNOX DESKTOP MODE with STAND ALONE!");
        return false;
    }

    public final boolean isDeskTopMode() {
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager == null) {
            Slog.d("InputMethodManagerService", "mDesktopModeManager null!");
            return false;
        }
        SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("DESKTOP MODE! : "), desktopModeState.enabled, "InputMethodManagerService");
        int i = desktopModeState.enabled;
        if (i == 4 || i == 3) {
            Slog.d("InputMethodManagerService", "IN KNOX DESKTOP MODE!");
            return true;
        }
        Slog.d("InputMethodManagerService", "NOT IN KNOX DESKTOP MODE!");
        return false;
    }

    public final boolean isDexSetting() {
        return (isDeskTopMode() && this.mFocusedDisplayId != 0) || isDEXStandAloneMode();
    }

    public final boolean isHoneyboardInstalled() {
        return FEATURE_CONFIG_SIP.equals(KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME) || isInstalledInputMethod("com.samsung.android.honeyboard/.service.HoneyBoardService");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isImeTraceEnabled() {
        return ImeTracing.getInstance().isEnabled();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isInputMethodPickerShownForTest() {
        boolean isShowing;
        synchronized (ImfLock.class) {
            AlertDialog alertDialog = this.mMenuController.mSwitchingDialog;
            isShowing = alertDialog == null ? false : alertDialog.isShowing();
        }
        return isShowing;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isInputMethodShown() {
        boolean z = (this.mImeWindowVis & 2) != 0;
        Slog.i("InputMethodManagerService", "isInputMethodShown: isShown=" + z);
        return z;
    }

    public final boolean isInstalledInputMethod(String str) {
        ArrayList enabledInputMethodListWithFilter = InputMethodSettingsRepository.get(this.mCurrentUserId).getEnabledInputMethodListWithFilter(null);
        if (enabledInputMethodListWithFilter.size() > 0) {
            int size = enabledInputMethodListWithFilter.size();
            for (int i = 0; i < size; i++) {
                if (str.equals(((InputMethodInfo) enabledInputMethodListWithFilter.get(i)).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSamsungDefaultMethodID() {
        return isHoneyboardInstalled() && "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(getSelectedMethodIdLocked());
    }

    public final boolean isShowRequestedForCurrentWindow() {
        if ("com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(getSelectedMethodIdLocked())) {
            return true;
        }
        ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState = (ImeVisibilityStateComputer.ImeTargetWindowState) this.mVisibilityStateComputer.mRequestWindowStateMap.get(this.mImeBindingState.mFocusedWindow);
        return imeTargetWindowState != null && imeTargetWindowState.mRequestedImeVisible;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (ImfLock.class) {
            try {
                boolean z2 = false;
                if (!isStylusHandwritingEnabled(this.mContext, i)) {
                    return false;
                }
                if (i == this.mCurrentUserId) {
                    InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i);
                    if (inputMethodBindingController.mSupportsStylusHw && (!z || inputMethodBindingController.mSupportsConnectionlessStylusHw)) {
                        z2 = true;
                    }
                    return z2;
                }
                InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
                InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(inputMethodSettings.getSelectedInputMethod());
                if (inputMethodInfo != null && inputMethodInfo.supportsStylusHandwriting() && (!z || inputMethodInfo.supportsConnectionlessStylusHandwriting())) {
                    z2 = true;
                }
                return z2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isStylusHandwritingEnabled(Context context, int i) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), "stylus_handwriting_enabled", 1, this.mUserManagerInternal.getProfileParentId(i)) != 0;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.1.<init>(android.content.Context, java.util.concurrent.atomic.AtomicBoolean, com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda9, com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:290)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:193)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:64)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    public final void maybeInitImeNavbarConfigLocked(int r12) {
        /*
            r11 = this;
            com.android.server.pm.UserManagerInternal r0 = r11.mUserManagerInternal
            int r12 = r0.getProfileParentId(r12)
            com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper r0 = r11.mImeDrawsImeNavBarRes
            if (r0 == 0) goto L14
            int r1 = r0.mUserId
            if (r1 == r12) goto L14
            r0.close()
            r0 = 0
            r11.mImeDrawsImeNavBarRes = r0
        L14:
            com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper r0 = r11.mImeDrawsImeNavBarRes
            if (r0 != 0) goto L7c
            android.content.Context r0 = r11.mContext
            int r0 = r0.getUserId()
            r1 = 0
            if (r0 != r12) goto L24
            android.content.Context r12 = r11.mContext
            goto L2e
        L24:
            android.content.Context r0 = r11.mContext
            android.os.UserHandle r12 = android.os.UserHandle.of(r12)
            android.content.Context r12 = r0.createContextAsUser(r12, r1)
        L2e:
            android.os.Handler r6 = r11.mHandler
            com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda9 r0 = new com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda9
            r2 = 0
            r0.<init>(r2, r11)
            java.util.concurrent.atomic.AtomicBoolean r8 = new java.util.concurrent.atomic.AtomicBoolean
            boolean r2 = com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.evaluate(r12)
            r8.<init>(r2)
            java.util.concurrent.atomic.AtomicReference r9 = new java.util.concurrent.atomic.AtomicReference
            r9.<init>()
            com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper r10 = new com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper
            int r2 = r12.getUserId()
            r10.<init>(r2, r8, r9)
            android.content.IntentFilter r4 = new android.content.IntentFilter
            java.lang.String r2 = "android.intent.action.OVERLAY_CHANGED"
            r4.<init>(r2)
            java.lang.String r2 = "package"
            r4.addDataScheme(r2)
            java.lang.String r2 = "android"
            r4.addDataSchemeSpecificPart(r2, r1)
            com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper$1 r1 = new com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper$1
            r1.<init>()
            r5 = 0
            r7 = 4
            r2 = r12
            r3 = r1
            r2.registerReceiver(r3, r4, r5, r6, r7)
            com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper$$ExternalSyntheticLambda0 r0 = new com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper$$ExternalSyntheticLambda0
            r0.<init>()
            r9.set(r0)
            boolean r12 = com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.evaluate(r12)
            r8.set(r12)
            r11.mImeDrawsImeNavBarRes = r10
        L7c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.maybeInitImeNavbarConfigLocked(int):void");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) {
        IInputMethodInvoker curMethodLocked = getCurMethodLocked();
        if (curMethodLocked == null) {
            return false;
        }
        try {
            curMethodLocked.mTarget.minimizeSoftInput(i);
            return true;
        } catch (RemoteException e) {
            IInputMethodInvoker.logRemoteException(e);
            return true;
        }
    }

    public final void notifyInputMethodSubtypeChangedLocked(int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
        if (DEBUG_SEP) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(10, new StringBuilder("notifyInputMethodSubtypeChangedLocked: callers="), "InputMethodManagerService");
        }
        if (inputMethodSubtype == null || !inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
            inputMethodSubtype = null;
        }
        InputMethodSubtypeHandle of = inputMethodSubtype != null ? InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype) : null;
        KeyboardLayoutManager keyboardLayoutManager = InputManagerService.this.mKeyboardLayoutManager;
        if (of == null) {
            keyboardLayoutManager.getClass();
            Slog.d("KeyboardLayoutManager", "No InputMethod is running, ignoring change");
            return;
        }
        synchronized (keyboardLayoutManager.mImeInfoLock) {
            try {
                KeyboardLayoutManager.ImeInfo imeInfo = keyboardLayoutManager.mCurrentImeInfo;
                if (imeInfo != null) {
                    if (of.equals(imeInfo.mImeSubtypeHandle)) {
                        if (keyboardLayoutManager.mCurrentImeInfo.mUserId != i) {
                        }
                    }
                }
                keyboardLayoutManager.mCurrentImeInfo = new KeyboardLayoutManager.ImeInfo(i, of, inputMethodSubtype);
                keyboardLayoutManager.mHandler.sendEmptyMessage(2);
                Slog.d("KeyboardLayoutManager", "InputMethodSubtype changed: userId=" + i + " subtypeHandle=" + of);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyUserActivity() {
        ClientState clientState = this.mCurClient;
        int i = clientState != null ? clientState.mSelfReportedDisplayId : 0;
        if (i == 0) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "notifyUserActivity: canceled, displayId=", "InputMethodManagerService");
            return;
        }
        if (isDeskTopMode()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (shouldShowImeKeyboardDefaultDisplayOnly()) {
                    Slog.i("InputMethodManagerService", "notifyUserActivity: notified.");
                    long uptimeMillis = SystemClock.uptimeMillis();
                    this.mPowerManager.wakeUp(uptimeMillis);
                    this.mPowerManager.userActivity(uptimeMillis, 0, 4096);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void onSessionCreated(IInputMethodInvoker iInputMethodInvoker, IInputMethodSession iInputMethodSession, InputChannel inputChannel) {
        ClientState clientState;
        Trace.traceBegin(32L, "IMMS.onSessionCreated");
        try {
            synchronized (ImfLock.class) {
                if (this.mUserSwitchHandlerTask != null) {
                    inputChannel.dispose();
                    return;
                }
                IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked == null || iInputMethodInvoker == null || curMethodLocked.mTarget.asBinder() != iInputMethodInvoker.mTarget.asBinder() || (clientState = this.mCurClient) == null) {
                    inputChannel.dispose();
                    return;
                }
                finishSessionLocked(clientState.mCurSession);
                clientState.mCurSession = null;
                clientState.mSessionRequested = false;
                ClientState clientState2 = this.mCurClient;
                clientState2.mCurSession = new SessionState(clientState2, iInputMethodInvoker, iInputMethodSession, inputChannel);
                final InputBindResult attachNewInputLocked = attachNewInputLocked(10, true);
                attachNewAccessibilityLocked(10, true);
                if (attachNewInputLocked.method != null) {
                    final IInputMethodClientInvoker iInputMethodClientInvoker = this.mCurClient.mClient;
                    if (iInputMethodClientInvoker.mIsProxy) {
                        iInputMethodClientInvoker.onBindMethodInternal(attachNewInputLocked);
                    } else {
                        iInputMethodClientInvoker.mHandler.post(new Runnable() { // from class: com.android.server.inputmethod.IInputMethodClientInvoker$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                IInputMethodClientInvoker.this.onBindMethodInternal(attachNewInputLocked);
                            }
                        });
                    }
                }
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver, Binder binder) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 2000) {
            new ShellCommandImpl(this).exec(binder, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            return;
        }
        if (resultReceiver != null) {
            resultReceiver.send(-1, null);
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(callingUid, "InputMethodManagerService does not support shell commands from non-shell users. callingUid=", " args=");
        m.append(Arrays.toString(strArr));
        String sb = m.toString();
        if (!Process.isCoreUid(callingUid)) {
            throw new SecurityException(sb);
        }
        Slog.e("InputMethodManagerService", sb);
    }

    public final void onShowHideSoftInputRequested(boolean z, IBinder iBinder, int i, ImeTracker.Token token) {
        boolean z2 = CoreRune.FW_VRR_DISCRETE;
        if (z2 && z2 && this.mMinRefreshRateToken == null) {
            if (this.mIDisplayManager == null) {
                this.mIDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
            IDisplayManager iDisplayManager = this.mIDisplayManager;
            if (iDisplayManager != null) {
                try {
                    this.mMinRefreshRateToken = iDisplayManager.acquireRefreshRateMinLimitToken(this.mRefreshRateToken, 120, "InputMethodManagerService");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (this.mMinRefreshRateToken != null) {
                    this.mHandler.removeCallbacks(this.mMinRefreshRateTokenRelease);
                    this.mHandler.postDelayed(this.mMinRefreshRateTokenRelease, 1500L);
                } else {
                    Slog.w("InputMethodManagerService", "acquireMinRefreshRateToken() failed");
                }
            }
        }
        WindowManagerInternal.ImeTargetInfo onToggleImeRequested = this.mWindowManagerInternal.onToggleImeRequested(z, this.mImeBindingState.mFocusedWindow, this.mVisibilityStateComputer.getWindowTokenFrom(iBinder), getCurTokenDisplayIdLocked());
        SoftInputShowHideHistory softInputShowHideHistory = this.mSoftInputShowHideHistory;
        ImeBindingState imeBindingState = this.mImeBindingState;
        SoftInputShowHideHistory.Entry entry = new SoftInputShowHideHistory.Entry(imeBindingState.mFocusedWindowClient, imeBindingState.mFocusedWindowEditorInfo, onToggleImeRequested.focusedWindowName, imeBindingState.mFocusedWindowSoftInputMode, i, this.mInFullscreenMode, onToggleImeRequested.requestWindowName, onToggleImeRequested.imeControlTargetName, onToggleImeRequested.imeLayerTargetName, onToggleImeRequested.imeSurfaceParentName);
        int i2 = softInputShowHideHistory.mNextIndex;
        SoftInputShowHideHistory.Entry[] entryArr = softInputShowHideHistory.mEntries;
        entryArr[i2] = entry;
        softInputShowHideHistory.mNextIndex = (i2 + 1) % entryArr.length;
        if (token != null) {
            ImeTrackerService imeTrackerService = this.mImeTrackerService;
            String str = onToggleImeRequested.requestWindowName;
            synchronized (imeTrackerService.mLock) {
                try {
                    ImeTrackerService.History history = imeTrackerService.mHistory;
                    ImeTrackerService.History.Entry entry2 = (ImeTrackerService.History.Entry) history.mLiveEntries.get(token.getBinder());
                    if (entry2 != null) {
                        entry2.mRequestWindowName = str;
                    }
                } finally {
                }
            }
        }
    }

    public final void onUnbindCurrentMethodByReset() {
        ImeVisibilityStateComputer imeVisibilityStateComputer = this.mVisibilityStateComputer;
        ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState = (ImeVisibilityStateComputer.ImeTargetWindowState) imeVisibilityStateComputer.mRequestWindowStateMap.get(this.mImeBindingState.mFocusedWindow);
        if (imeTargetWindowState == null || imeTargetWindowState.mRequestedImeVisible || this.mVisibilityStateComputer.mInputShown) {
            return;
        }
        this.mVisibilityApplier.applyImeVisibility(this.mImeBindingState.mFocusedWindow, createStatsTokenForFocusedClient(50, false), 0, 0, this.mCurrentUserId);
    }

    public final void onUpdateEditorToolType(int i) {
        synchronized (ImfLock.class) {
            IInputMethodInvoker curMethodLocked = getCurMethodLocked();
            if (curMethodLocked != null) {
                try {
                    curMethodLocked.mTarget.updateEditorToolType(i);
                } catch (RemoteException e) {
                    IInputMethodInvoker.logRemoteException(e);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0081 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void postInputMethodSettingUpdatedLocked(boolean r18) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.postInputMethodSettingUpdatedLocked(boolean):void");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) {
        if (!isStylusHandwritingEnabled(this.mContext, i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Can not prepare stylus handwriting delegation. Stylus handwriting pref is disabled for user: ", "InputMethodManagerService");
            return;
        }
        synchronized (ImfLock.class) {
            if (!this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str2)) {
                Slog.w("InputMethodManagerService", "prepareStylusHandwritingDelegation() fail");
                throw new IllegalArgumentException("Delegator doesn't match Uid");
            }
        }
        this.mHandler.obtainMessage(1130, i, 0, new Pair(str, str2)).sendToTarget();
    }

    public final void registerDeviceListenerAndCheckStylusSupport() {
        final InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        IntArray intArray = new IntArray();
        for (int i : inputManager.getInputDeviceIds()) {
            InputDevice inputDevice = inputManager.getInputDevice(i);
            if (inputDevice != null && inputDevice.isEnabled() && isStylusDevice(inputDevice)) {
                intArray.add(i);
            }
        }
        if (intArray.size() > 0) {
            synchronized (ImfLock.class) {
                IntArray intArray2 = new IntArray();
                this.mStylusIds = intArray2;
                intArray2.addAll(intArray);
            }
        }
        inputManager.registerInputDeviceListener(new InputManager.InputDeviceListener() { // from class: com.android.server.inputmethod.InputMethodManagerService.4
            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceAdded(int i2) {
                InputDevice inputDevice2 = inputManager.getInputDevice(i2);
                if (inputDevice2 == null || !InputMethodManagerService.isStylusDevice(inputDevice2)) {
                    return;
                }
                synchronized (ImfLock.class) {
                    InputMethodManagerService.this.addStylusDeviceIdLocked(i2);
                }
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceChanged(int i2) {
                InputDevice inputDevice2 = inputManager.getInputDevice(i2);
                if (inputDevice2 == null) {
                    return;
                }
                if (InputMethodManagerService.isStylusDevice(inputDevice2)) {
                    synchronized (ImfLock.class) {
                        InputMethodManagerService.this.addStylusDeviceIdLocked(i2);
                    }
                } else {
                    synchronized (ImfLock.class) {
                        InputMethodManagerService.this.removeStylusDeviceIdLocked(i2);
                    }
                }
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public final void onInputDeviceRemoved(int i2) {
                synchronized (ImfLock.class) {
                    InputMethodManagerService.this.removeStylusDeviceIdLocked(i2);
                }
            }
        }, this.mHandler);
    }

    public final void registerSamsungAdditionalReceivers() {
        this.mContext.registerReceiver(new DemoResetReceiver(5, this), BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE);
        intentFilter.addAction(UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE);
        this.mContext.registerReceiver(new DemoResetReceiver(4, this), intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.sec.android.inputmethod.Subtype");
        this.mContext.registerReceiver(new DemoResetReceiver(3, this), intentFilter2, "android.permission.WRITE_SECURE_SETTINGS", null, 2);
        this.mContext.registerReceiver(new DemoResetReceiver(0, this), BatteryService$$ExternalSyntheticOutline0.m("com.samsung.sea.rm.DEMO_RESET_STARTED"));
        if ("VZW".equals(SystemProperties.get("ro.csc.sales_code")) || "VPP".equals(SystemProperties.get("ro.csc.sales_code"))) {
            this.mContext.registerReceiver(new DemoResetReceiver(6, this), BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.SETTINGS_SOFT_RESET"), "com.sec.android.settings.permission.SOFT_RESET", null);
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("universal_switch_enabled"), false, this.mUniversalSwitchChangeObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("dexonpc_connection_state"), false, this.mDexOnPCStateChangeObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("access_control_enabled"), false, this.mAccessControlEnableChangeObserver);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("access_control_keyboard_block"), false, this.mAccessControlKeyboardEnableChangeObserver);
        InputMethodManagerService$$ExternalSyntheticLambda2 inputMethodManagerService$$ExternalSyntheticLambda2 = new InputMethodManagerService$$ExternalSyntheticLambda2(this);
        SamsungIMMSHWKeyboard samsungIMMSHWKeyboard = new SamsungIMMSHWKeyboard();
        samsungIMMSHWKeyboard.mBTKeyboardCount = 0;
        samsungIMMSHWKeyboard.mUSBKeyboardCount = 0;
        samsungIMMSHWKeyboard.mUSBKeyboardCountOld = 0;
        this.mSamsungIMMSHWKeyboard = samsungIMMSHWKeyboard;
        Context context = this.mContext;
        context.registerReceiver(new SamsungIMMSHWKeyboard.KMSKeyboardReceiver(samsungIMMSHWKeyboard, 1), BatteryService$$ExternalSyntheticOutline0.m("com.sec.android.sidesync.action.PSS_KEYBOARD"), "com.sec.android.permission.SIDESYNC_RECEIVER_PERMISSION", null, 2);
        context.registerReceiver(new SamsungIMMSHWKeyboard.KMSKeyboardReceiver(samsungIMMSHWKeyboard, 0), BatteryService$$ExternalSyntheticOutline0.m("com.sec.android.sidesync.action.KMS_KEYBOARD"), "com.sec.android.permission.SIDESYNC_RECEIVER_PERMISSION", null, 2);
        context.registerReceiver(new SamsungIMMSHWKeyboard.KMSKeyboardReceiver(samsungIMMSHWKeyboard, 2), DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.hardware.usb.action.USB_DEVICE_ATTACHED", "android.hardware.usb.action.USB_DEVICE_DETACHED"), "android.permission.MANAGE_USB", null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter3.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        context.registerReceiver(samsungIMMSHWKeyboard.new BTKeyboardReceiver(), intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
        SamsungIMMSHWKeyboard.POGOKeyboardReceiver pOGOKeyboardReceiver = samsungIMMSHWKeyboard.new POGOKeyboardReceiver();
        context.registerReceiver(pOGOKeyboardReceiver, intentFilter4, 2);
        InputManagerService inputManagerService = pOGOKeyboardReceiver.mInputManagerService;
        if (inputManagerService != null) {
            inputManagerService.mInputMethodManagerCallbacks = pOGOKeyboardReceiver.mCallbacks;
        }
        this.mSamsungIMMSHWKeyboard.mHardKeyboardStatusChangeListener = inputMethodManagerService$$ExternalSyntheticLambda2;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void removeImeSurface() {
        this.mHandler.obtainMessage(1060).sendToTarget();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void removeImeSurfaceFromWindowAsync(IBinder iBinder) {
        this.mHandler.obtainMessage(1061, iBinder).sendToTarget();
    }

    public final void removeStylusDeviceIdLocked(int i) {
        IntArray intArray = this.mStylusIds;
        if (intArray == null || intArray.size() == 0) {
            return;
        }
        int indexOf = this.mStylusIds.indexOf(i);
        if (indexOf != -1) {
            this.mStylusIds.remove(indexOf);
            Slog.d("InputMethodManagerService", "Stylus deviceId: " + i + " removed.");
        }
        if (this.mStylusIds.size() == 0) {
            this.mHwController.reset(false);
            this.mHandler.obtainMessage(1120).sendToTarget();
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void reportPerceptibleAsync(final IBinder iBinder, final boolean z) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda18
            public final void runOrThrow() {
                InputMethodManagerService inputMethodManagerService = InputMethodManagerService.this;
                IBinder iBinder2 = iBinder;
                boolean z2 = z;
                inputMethodManagerService.getClass();
                Objects.requireNonNull(iBinder2, "windowToken must not be null");
                synchronized (ImfLock.class) {
                    try {
                        Boolean bool = (Boolean) inputMethodManagerService.mFocusedWindowPerceptible.get(iBinder2);
                        if (inputMethodManagerService.mImeBindingState.mFocusedWindow == iBinder2 && (bool == null || bool.booleanValue() != z2)) {
                            inputMethodManagerService.mFocusedWindowPerceptible.put(iBinder2, bool);
                            inputMethodManagerService.updateSystemUiLocked$1();
                        }
                    } finally {
                    }
                }
            }
        });
    }

    public final void requestClientSessionLocked(ClientState clientState) {
        if (clientState.mSessionRequested) {
            return;
        }
        Slog.v("InputMethodManagerService", "Creating new session for client " + clientState);
        InputChannel[] openInputChannelPair = InputChannel.openInputChannelPair(clientState.toString());
        final InputChannel inputChannel = openInputChannelPair[0];
        InputChannel inputChannel2 = openInputChannelPair[1];
        clientState.mSessionRequested = true;
        final IInputMethodInvoker curMethodLocked = getCurMethodLocked();
        IInputMethodSessionCallback.Stub stub = new IInputMethodSessionCallback.Stub() { // from class: com.android.server.inputmethod.InputMethodManagerService.2
            public final void sessionCreated(IInputMethodSession iInputMethodSession) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    InputMethodManagerService.this.onSessionCreated(curMethodLocked, iInputMethodSession, inputChannel);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        try {
            curMethodLocked.getClass();
            try {
                curMethodLocked.mTarget.createSession(inputChannel2, stub);
            } catch (RemoteException e) {
                IInputMethodInvoker.logRemoteException(e);
            }
        } finally {
            if (inputChannel2 != null) {
                inputChannel2.dispose();
            }
        }
    }

    public final void resetCurrentMethodAndClientLocked(int i) {
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
        inputMethodBindingController.mSelectedMethodId = null;
        onUnbindCurrentMethodByReset();
        inputMethodBindingController.unbindCurrentMethod();
        unbindCurrentClientLocked(i);
    }

    public final void resetDefaultImeLocked(Context context) {
        String selectedMethodIdLocked = getSelectedMethodIdLocked();
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        InputMethodInfo inputMethodInfo = selectedMethodIdLocked != null ? inputMethodMap.get(selectedMethodIdLocked) : null;
        if (inputMethodInfo != null && ("com.samsung.android.honeyboard/.service.HoneyBoardService".equals(inputMethodInfo.getId()) || "com.sohu.inputmethod.sogou.samsung/.SogouIME".equals(inputMethodInfo.getId()) || "com.touchtype.swiftkey/com.touchtype.KeyboardService".equals(inputMethodInfo.getId()))) {
            Slog.w("InputMethodManagerService", "resetDefaultImeLocked: Do not reset the default (current) IME that preloaded.");
            return;
        }
        String selectedMethodIdLocked2 = getSelectedMethodIdLocked();
        if (selectedMethodIdLocked2 == null || inputMethodMap.get(selectedMethodIdLocked2).isSystem()) {
            ArrayList defaultEnabledImes = InputMethodInfoUtils.getDefaultEnabledImes(context, inputMethodSettings.getEnabledInputMethodListWithFilter(null), false);
            if (defaultEnabledImes.isEmpty()) {
                Slog.i("InputMethodManagerService", "No default found");
            } else {
                setSelectedInputMethodAndSubtypeLocked((InputMethodInfo) defaultEnabledImes.get(0), -1, false);
            }
        }
    }

    public final void resetSelectedInputMethodAndSubtypeLocked(String str) {
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
        int i = -1;
        inputMethodBindingController.mDisplayIdToShowIme = -1;
        inputMethodBindingController.mDeviceIdToShowIme = 0;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
        inputMethodSettings.putSelectedDefaultDeviceInputMethod(null);
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        InputMethodInfo inputMethodInfo = inputMethodMap.get(str);
        Slog.d("InputMethodManagerService", "resetSelectedInputMethodAndSubtypeLocked settings.getMethodMap() size : " + inputMethodMap.mMap.size());
        if (inputMethodInfo != null) {
            Slog.d("InputMethodManagerService", "resetSelectedInputMethodAndSubtypeLocked imi : " + inputMethodInfo);
        }
        if (inputMethodInfo != null && !TextUtils.isEmpty(str)) {
            Pair lastSubtypeForInputMethodInternal = inputMethodSettings.getLastSubtypeForInputMethodInternal(str);
            String str2 = lastSubtypeForInputMethodInternal != null ? (String) lastSubtypeForInputMethodInternal.second : null;
            if (str2 != null) {
                try {
                    i = SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, Integer.parseInt(str2));
                } catch (NumberFormatException e) {
                    Slog.w("InputMethodManagerService", "HashCode for subtype looks broken: ".concat(str2), e);
                }
            }
        }
        setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, false);
    }

    public final void scheduleSwitchUserTaskLocked(int i, IInputMethodClientInvoker iInputMethodClientInvoker) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "scheduleSwitchUserTaskLocked: userId=", "InputMethodManagerService");
        UserSwitchHandlerTask userSwitchHandlerTask = this.mUserSwitchHandlerTask;
        if (userSwitchHandlerTask != null) {
            if (userSwitchHandlerTask.mToUserId == i) {
                userSwitchHandlerTask.mClientToBeReset = iInputMethodClientInvoker;
                return;
            }
            this.mHandler.removeCallbacks(userSwitchHandlerTask);
        }
        hideCurrentInputLocked(10, this.mImeBindingState.mFocusedWindow);
        UserSwitchHandlerTask userSwitchHandlerTask2 = new UserSwitchHandlerTask(this, i, iInputMethodClientInvoker);
        this.mUserSwitchHandlerTask = userSwitchHandlerTask2;
        this.mHandler.post(userSwitchHandlerTask2);
    }

    public final int semComputeImeDisplayIdForTarget(int i) {
        this.mWMS.getClass();
        if (isDeskTopMode() && shouldShowImeKeyboardDefaultDisplayOnly()) {
            i = 0;
        }
        ProxyManager$$ExternalSyntheticOutline0.m(i, "semComputeImeDisplayIdForTarget: displayId=", "InputMethodManagerService");
        return i;
    }

    public final void sendOnNavButtonFlagsChangedLocked() {
        IInputMethodInvoker iInputMethodInvoker = getInputMethodBindingController(this.mCurrentUserId).mCurMethod;
        if (iInputMethodInvoker == null) {
            return;
        }
        try {
            iInputMethodInvoker.mTarget.onNavButtonFlagsChanged(getInputMethodNavButtonFlagsLocked());
        } catch (RemoteException e) {
            IInputMethodInvoker.logRemoteException(e);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        int callingUid = Binder.getCallingUid();
        if (TextUtils.isEmpty(str) || inputMethodSubtypeArr == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (InputMethodSubtype inputMethodSubtype : inputMethodSubtypeArr) {
            if (arrayList.contains(inputMethodSubtype)) {
                Slog.w("InputMethodManagerService", "Duplicated subtype definition found: " + inputMethodSubtype.getLocale() + ", " + inputMethodSubtype.getMode());
            } else {
                arrayList.add(inputMethodSubtype);
            }
        }
        synchronized (ImfLock.class) {
            try {
                if (this.mSystemReady) {
                    AdditionalSubtypeMap additionalSubtypeMap = AdditionalSubtypeMapRepository.get(i);
                    boolean z = this.mCurrentUserId == i;
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
                    AdditionalSubtypeMap newAdditionalSubtypeMap = inputMethodSettings.getNewAdditionalSubtypeMap(str, arrayList, additionalSubtypeMap, this.mPackageManagerInternal, callingUid);
                    if (additionalSubtypeMap != newAdditionalSubtypeMap) {
                        AdditionalSubtypeMapRepository.putAndSave(i, newAdditionalSubtypeMap, inputMethodSettings.mMethodMap);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            InputMethodSettingsRepository.put(i, queryInputMethodServicesInternal(this.mContext, i, AdditionalSubtypeMapRepository.get(i), 0));
                            if (z) {
                                postInputMethodSettingUpdatedLocked(false);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void setAttachedClientForTesting(ClientState clientState) {
        synchronized (ImfLock.class) {
            this.mCurClient = clientState;
        }
    }

    public final void setCurrentInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
        int subtypeIdFromHashCode;
        synchronized (ImfLock.class) {
            try {
                if (calledFromValidUserLocked()) {
                    String selectedMethodIdLocked = getSelectedMethodIdLocked();
                    if (inputMethodSubtype == null || selectedMethodIdLocked == null || (subtypeIdFromHashCode = SubtypeUtils.getSubtypeIdFromHashCode(InputMethodSettingsRepository.get(this.mCurrentUserId).mMethodMap.get(selectedMethodIdLocked), inputMethodSubtype.hashCode())) == -1) {
                        return;
                    }
                    setInputMethodLocked(subtypeIdFromHashCode, 0, selectedMethodIdLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDisplayImePolicyDexDeskTopMode(int i) {
        if (i != 0 && isDeskTopMode()) {
            boolean shouldShowImeKeyboardDefaultDisplayOnly = shouldShowImeKeyboardDefaultDisplayOnly();
            Slog.i("InputMethodManagerService", "setDisplayImePolicyDexDeskTopMode: setDisplayImePolicy displayId=" + i + " imePolicy=" + (shouldShowImeKeyboardDefaultDisplayOnly ? 1 : 0));
            this.mWMS.setDisplayImePolicy(i, shouldShowImeKeyboardDefaultDisplayOnly ? 1 : 0);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        int callingUid = Binder.getCallingUid();
        ComponentName unflattenFromString = str != null ? ComponentName.unflattenFromString(str) : null;
        if (unflattenFromString != null) {
            if (this.mPackageManagerInternal.isSameApp(callingUid, UserHandle.getUserId(callingUid), 0L, unflattenFromString.getPackageName())) {
                Objects.requireNonNull(iArr, "subtypeHashCodes must not be null");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (ImfLock.class) {
                        boolean z = this.mCurrentUserId == i;
                        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
                        if (inputMethodSettings.setEnabledInputMethodSubtypes(str, iArr)) {
                            if (z) {
                                SettingsObserver settingsObserver = this.mSettingsObserver;
                                if (settingsObserver != null) {
                                    settingsObserver.mLastEnabled = inputMethodSettings.getEnabledInputMethodsStr();
                                }
                                updateInputMethodsFromSettingsLocked(false);
                            }
                            return;
                        }
                        return;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Calling UID=", " does not belong to imeId=", str));
    }

    public final void setInputMethod(IBinder iBinder, String str) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        synchronized (ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
                    InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(str);
                    if (inputMethodInfo == null || !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), callingUid, userId, inputMethodSettings)) {
                        throw getExceptionForUnknownImeId(str);
                    }
                    setInputMethodWithSubtypeIdLocked(-1, iBinder, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setInputMethodEnabledLocked(String str, boolean z) {
        int i = this.mCurrentUserId;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        if (z) {
            String enabledInputMethodsStr = inputMethodSettings.getEnabledInputMethodsStr();
            String concatEnabledImeIds = InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
            if (TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds)) {
                return true;
            }
            inputMethodSettings.putEnabledInputMethodsStr(concatEnabledImeIds);
            return false;
        }
        if (!inputMethodSettings.buildAndPutEnabledInputMethodsStrRemovingId(new StringBuilder(), inputMethodSettings.getEnabledInputMethodsAndSubtypeList(), str)) {
            return false;
        }
        if (getInputMethodBindingController(i).mDeviceIdToShowIme != 0) {
            if (str.equals(SecureSettingsWrapper.getString("default_device_input_method", null, inputMethodSettings.mUserId))) {
                InputMethodInfo mostApplicableDefaultIME = InputMethodInfoUtils.getMostApplicableDefaultIME(inputMethodSettings.getEnabledInputMethodListWithFilter(null));
                inputMethodSettings.putSelectedDefaultDeviceInputMethod(mostApplicableDefaultIME != null ? mostApplicableDefaultIME.getId() : null);
            }
        } else if (str.equals(inputMethodSettings.getSelectedInputMethod()) && !chooseNewDefaultIMELocked()) {
            Slog.i("InputMethodManagerService", "Can't find new IME, unsetting the current input method.");
            resetSelectedInputMethodAndSubtypeLocked("");
        }
        return true;
    }

    public final void setInputMethodLocked(int i, int i2, String str) {
        InputMethodSubtype inputMethodSubtype;
        int i3 = this.mCurrentUserId;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i3);
        InputMethodInfo inputMethodInfo = inputMethodSettings.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            throw getExceptionForUnknownImeId(str);
        }
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i3);
        if (!str.equals(inputMethodBindingController.mSelectedMethodId)) {
            if (inputMethodBindingController.mDeviceIdToShowIme != 0 && i2 == 0) {
                inputMethodSettings.putSelectedDefaultDeviceInputMethod(str);
                return;
            }
            IInputMethodInvoker curMethodLocked = getCurMethodLocked();
            if (curMethodLocked != null) {
                try {
                    curMethodLocked.mTarget.removeStylusHandwritingWindow();
                } catch (RemoteException e) {
                    IInputMethodInvoker.logRemoteException(e);
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, false);
                inputMethodBindingController.mSelectedMethodId = str;
                if (this.mActivityManagerInternal.isSystemReady()) {
                    Intent intent = new Intent("android.intent.action.INPUT_METHOD_CHANGED");
                    intent.addFlags(536870912);
                    intent.putExtra("input_method_id", str);
                    this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
                }
                unbindCurrentClientLocked(2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                getCurrentInputMethodSubtypeLocked();
                return;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        if (subtypeCount <= 0) {
            notifyInputMethodSubtypeChangedLocked(i3, inputMethodInfo, null);
            return;
        }
        InputMethodSubtype inputMethodSubtype2 = inputMethodBindingController.mCurrentSubtype;
        if (i < 0 || i >= subtypeCount) {
            InputMethodSubtype currentInputMethodSubtypeLocked = getCurrentInputMethodSubtypeLocked();
            if (currentInputMethodSubtypeLocked != null) {
                for (int i4 = 0; i4 < subtypeCount; i4++) {
                    if (currentInputMethodSubtypeLocked.equals(inputMethodInfo.getSubtypeAt(i4))) {
                        inputMethodSubtype = currentInputMethodSubtypeLocked;
                        i = i4;
                        break;
                    }
                }
            }
            inputMethodSubtype = currentInputMethodSubtypeLocked;
            i = -1;
        } else {
            inputMethodSubtype = inputMethodInfo.getSubtypeAt(i);
        }
        Slog.i("InputMethodManagerService", "subtype state: oldSubtype = " + inputMethodSubtype2 + " newSubtype = " + inputMethodSubtype + " force = " + this.mIsNeedUpdateSubtypeForLocaleChanged + ", intent received : " + this.mSubTypeIntentReceived);
        if (!Objects.equals(inputMethodSubtype, inputMethodSubtype2) || this.mIsNeedUpdateSubtypeForLocaleChanged || this.mSubTypeIntentReceived) {
            setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, true);
            IInputMethodInvoker curMethodLocked2 = getCurMethodLocked();
            if (curMethodLocked2 != null) {
                updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
                try {
                    curMethodLocked2.mTarget.changeInputMethodSubtype(inputMethodSubtype);
                } catch (RemoteException e2) {
                    IInputMethodInvoker.logRemoteException(e2);
                }
            }
            if (this.mIsNeedUpdateSubtypeForLocaleChanged && inputMethodSubtype == inputMethodSubtype2) {
                this.mIsNeedUpdateSubtypeForLocaleChanged = false;
            }
            this.mSubTypeIntentReceived = false;
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) {
        int callingUid = Binder.getCallingUid();
        try {
            if (mInputMethodSwitchDisable != z) {
                Slog.d("InputMethodManagerService", "setInputMethodSwitchDisable change");
                ClientState client = this.mClientController.getClient(iInputMethodClient.asBinder());
                if (client == null) {
                    throw new IllegalArgumentException("unknown client " + iInputMethodClient.asBinder());
                }
                if (this.mWindowManagerInternal.hasInputMethodClientFocus(this.mImeBindingState.mFocusedWindow, client.mUid, client.mPid, client.mSelfReportedDisplayId) == 0) {
                    return;
                }
                Slog.w("InputMethodManagerService", "setInputMethodSwitchDisable : Ignoring, uid " + callingUid + ": " + iInputMethodClient);
                mInputMethodSwitchDisable = z;
            }
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(e, "setInputMethodSwitchDisable : exception ", "InputMethodManagerService");
        }
    }

    public final void setInputMethodWithSubtypeIdLocked(int i, IBinder iBinder, final String str) {
        if (iBinder == null) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new SecurityException("Using null token requires permission android.permission.WRITE_SECURE_SETTINGS");
            }
        } else {
            if (getInputMethodBindingController(this.mCurrentUserId).mCurToken != iBinder) {
                Slog.w("InputMethodManagerService", "Ignoring setInputMethod of uid " + Binder.getCallingUid() + " token: " + iBinder);
                return;
            }
            InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
            if (inputMethodSettings.mMethodMap.get(str) != null && inputMethodSettings.getEnabledInputMethodListWithFilter(new Predicate() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda22
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((InputMethodInfo) obj).getId().equals(str);
                }
            }).isEmpty()) {
                throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Requested IME is not enabled: ", str));
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            setInputMethodLocked(i, 0, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSelectedInputMethodAndSubtypeLocked(InputMethodInfo inputMethodInfo, int i, boolean z) {
        ArrayList arrayList;
        boolean z2;
        int i2 = this.mCurrentUserId;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i2);
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i2);
        String selectedMethodIdLocked = getSelectedMethodIdLocked();
        InputMethodSubtype inputMethodSubtype = inputMethodBindingController.mCurrentSubtype;
        InputMethodSubtype inputMethodSubtype2 = null;
        if (!"com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(selectedMethodIdLocked)) {
            String str = InputMethodSettings.INVALID_SUBTYPE_HASHCODE_STR;
            String valueOf = inputMethodSubtype != null ? String.valueOf(inputMethodSubtype.hashCode()) : str;
            if (inputMethodSubtype == null ? true : !inputMethodSubtype.isAuxiliary()) {
                List loadInputMethodAndSubtypeHistory = inputMethodSettings.loadInputMethodAndSubtypeHistory();
                int i3 = 0;
                while (true) {
                    arrayList = (ArrayList) loadInputMethodAndSubtypeHistory;
                    if (i3 >= arrayList.size()) {
                        break;
                    }
                    Pair pair = (Pair) arrayList.get(i3);
                    if (((String) pair.first).equals(selectedMethodIdLocked)) {
                        arrayList.remove(pair);
                        break;
                    }
                    i3++;
                }
                StringBuilder sb = new StringBuilder();
                if (TextUtils.isEmpty(selectedMethodIdLocked) || TextUtils.isEmpty(valueOf)) {
                    z2 = false;
                } else {
                    sb.append(selectedMethodIdLocked);
                    sb.append(';');
                    sb.append(valueOf);
                    z2 = true;
                }
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    Pair pair2 = (Pair) arrayList.get(i4);
                    String str2 = (String) pair2.first;
                    String str3 = (String) pair2.second;
                    if (TextUtils.isEmpty(str3)) {
                        str3 = str;
                    }
                    if (z2) {
                        sb.append(':');
                    } else {
                        z2 = true;
                    }
                    sb.append(str2);
                    sb.append(';');
                    sb.append(str3);
                }
                String sb2 = sb.toString();
                if (TextUtils.isEmpty(sb2)) {
                    inputMethodSettings.putString("input_methods_subtype_history", null);
                } else {
                    inputMethodSettings.putString("input_methods_subtype_history", sb2);
                }
            }
        }
        int i5 = -1;
        if (inputMethodInfo != null && i >= 0) {
            if (i < inputMethodInfo.getSubtypeCount()) {
                inputMethodSubtype2 = inputMethodInfo.getSubtypeAt(i);
                i5 = inputMethodSubtype2.hashCode();
            } else {
                inputMethodSubtype2 = getCurrentInputMethodSubtypeLocked();
            }
        }
        int i6 = inputMethodSettings.mUserId;
        SecureSettingsWrapper.get(i6).putInt(i5, "selected_input_method_subtype");
        inputMethodBindingController.mCurrentSubtype = inputMethodSubtype2;
        notifyInputMethodSubtypeChangedLocked(i6, inputMethodInfo, inputMethodSubtype2);
        if (z) {
            return;
        }
        inputMethodSettings.putSelectedInputMethod(inputMethodInfo != null ? inputMethodInfo.getId() : "");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) {
        int callingUid = Binder.getCallingUid();
        synchronized (ImfLock.class) {
            try {
                if (canInteractWithImeLocked(callingUid, iInputMethodClient, "setStylusWindowIdleTimeoutForTest", null)) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                        curMethodLocked.getClass();
                        try {
                            curMethodLocked.mTarget.setStylusWindowIdleTimeoutForTest(j);
                        } catch (RemoteException e) {
                            IInputMethodInvoker.logRemoteException(e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean shouldShowImeKeyboardDefaultDisplayOnly() {
        boolean booleanValue = Boolean.valueOf(getDexSettings(this.mContext.getContentResolver(), "touch_keyboard", "false")).booleanValue();
        DeviceIdleController$$ExternalSyntheticOutline0.m("shouldShowImeKeyboardDefaultDisplayOnly(): ", "InputMethodManagerService", booleanValue);
        return booleanValue;
    }

    public final boolean shouldShowImeSwitcherLocked(int i, int i2) {
        if (!this.mShowOngoingImeSwitcherForPhones || this.mMenuController.mSwitchingDialog != null) {
            return false;
        }
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(i2);
        if (!Objects.equals(inputMethodBindingController.mCurId, inputMethodBindingController.mSelectedMethodId)) {
            return false;
        }
        if (this.mWindowManagerInternal.isKeyguardShowingAndNotOccluded() && this.mWindowManagerInternal.isKeyguardSecure(i2)) {
            return false;
        }
        EditorInfo editorInfo = this.mCurEditorInfo;
        if (editorInfo == null) {
            Slog.d("InputMethodManagerService", "mCurEditorInfo is null");
        } else if (TextUtils.equals(editorInfo.packageName, KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME)) {
            Slog.d("InputMethodManagerService", "isImeSwitcherDisabledPackage : true");
            return false;
        }
        Slog.d("InputMethodManagerService", "isImeSwitcherDisabledPackage : false");
        if (mInputMethodSwitchDisable || !this.mIsInteractive || this.mSemImmsUtil.isKeyguardLocked()) {
            return false;
        }
        SemInputMethodManagerServiceUtil semInputMethodManagerServiceUtil = this.mSemImmsUtil;
        if (semInputMethodManagerServiceUtil.mKeyguardManager == null) {
            semInputMethodManagerServiceUtil.mKeyguardManager = (KeyguardManager) semInputMethodManagerServiceUtil.mContext.getSystemService(KeyguardManager.class);
        }
        KeyguardManager keyguardManager = semInputMethodManagerServiceUtil.mKeyguardManager;
        if ((keyguardManager != null && keyguardManager.isKeyguardLocked() && semInputMethodManagerServiceUtil.mKeyguardManager.isKeyguardSecure()) || (i & 1) == 0) {
            return false;
        }
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(this.mCurrentUserId);
        if (((i & 3) != 3 && inputMethodSettings.isShowImeWithHardKeyboardEnabled() && this.mWindowManagerInternal.isHardKeyboardAvailable()) || !inputMethodSettings.isShowKeyboardButton()) {
            return false;
        }
        if ((ImmsRune.SUPPORT_SKBD_MULTI_MODAL_CONCEPT && "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(getSelectedMethodIdLocked()) && inputMethodSettings.isShowKeyboardButton()) || "com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(getSelectedMethodIdLocked())) {
            return false;
        }
        if (this.mWindowManagerInternal.isHardKeyboardAvailable()) {
            Slog.d("InputMethodManagerService", "shouldShowImeSwitcherLocked isHardKeyboardAvailable return true");
            return true;
        }
        if ((i & 2) == 0) {
            return false;
        }
        Slog.d("InputMethodManagerService", "shouldShowImeSwitcherLocked : checking vis : " + i);
        InputMethodSettings inputMethodSettings2 = InputMethodSettingsRepository.get(i2);
        ArrayList enabledInputMethodListWithFilter = inputMethodSettings2.getEnabledInputMethodListWithFilter(new InputMethodManagerService$$ExternalSyntheticLambda11());
        int size = enabledInputMethodListWithFilter.size();
        if (size > 2) {
            return true;
        }
        if (size < 1) {
            return false;
        }
        InputMethodSubtype inputMethodSubtype = null;
        int i3 = 0;
        int i4 = 0;
        InputMethodSubtype inputMethodSubtype2 = null;
        for (int i5 = 0; i5 < size; i5++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) enabledInputMethodListWithFilter.get(i5);
            List enabledInputMethodSubtypeList = inputMethodSettings2.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
            int size2 = enabledInputMethodSubtypeList.size();
            if (!"com.samsung.android.honeyboard/.service.HoneyBoardService".equals(inputMethodInfo.getId()) || size2 <= 1) {
                if (!"com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco".equals(inputMethodInfo.getId())) {
                    if ("com.samsung.android.bixby.service/.dictation.DictationInputMethodService".equals(inputMethodInfo.getId())) {
                    }
                }
            } else {
                size2 = 1;
            }
            if (size2 == 0) {
                i3++;
            } else {
                for (int i6 = 0; i6 < size2; i6++) {
                    InputMethodSubtype inputMethodSubtype3 = (InputMethodSubtype) enabledInputMethodSubtypeList.get(i6);
                    if (inputMethodSubtype3.isAuxiliary()) {
                        i4++;
                        inputMethodSubtype2 = inputMethodSubtype3;
                    } else {
                        i3++;
                        inputMethodSubtype = inputMethodSubtype3;
                    }
                }
            }
        }
        if (i3 > 1 || i4 > 1) {
            return true;
        }
        return (i3 == 1 && i4 == 1) ? inputMethodSubtype == null || inputMethodSubtype2 == null || !((inputMethodSubtype.getLocale().equals(inputMethodSubtype2.getLocale()) || inputMethodSubtype2.overridesImplicitlyEnabledSubtype() || inputMethodSubtype.overridesImplicitlyEnabledSubtype()) && inputMethodSubtype.containsExtraValueKey("TrySuppressingImeSwitcher")) : size == 1 && "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(((InputMethodInfo) enabledInputMethodListWithFilter.get(0)).getId());
    }

    public final boolean showCurrentInputLocked(IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3) {
        InputDevice inputDevice;
        ClientState clientState;
        StringBuilder sb = new StringBuilder("ACCESS_CONTROL_ENABLED = ");
        sb.append(this.mAccessControlEnable);
        sb.append(", ACCESS_CONTROL_KEYBOARD_BLOCK = ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("InputMethodManagerService", sb, this.mAccessControlKeyboardBlockEnable);
        int i4 = 0;
        if (this.mAccessControlEnable && this.mAccessControlKeyboardBlockEnable) {
            Slog.i("InputMethodManagerService", "Interaction Control Keyboard block is enabled, so not show keyboard");
            return false;
        }
        ImeVisibilityStateComputer imeVisibilityStateComputer = this.mVisibilityStateComputer;
        ImeVisibilityStateComputer.ImeVisibilityPolicy imeVisibilityPolicy = imeVisibilityStateComputer.mPolicy;
        if (imeVisibilityPolicy.mA11yRequestingNoSoftKeyboard || imeVisibilityPolicy.mImeHiddenByDisplayPolicy) {
            Slog.i("InputMethodManagerService", "onImeShowFlags: onFailed, A11yRequestingNoSoftKeyboard=" + imeVisibilityPolicy.mA11yRequestingNoSoftKeyboard + ", mImeHiddenByDisplayPolicy=" + imeVisibilityPolicy.mImeHiddenByDisplayPolicy);
            ImeTracker.forLogging().onFailed(token, 4);
            return false;
        }
        ImeTracker.forLogging().onProgress(token, 4);
        if ((i & 2) != 0) {
            imeVisibilityStateComputer.mRequestedShowExplicitly = true;
            imeVisibilityStateComputer.mShowForced = true;
        } else if ((i & 1) == 0) {
            imeVisibilityStateComputer.mRequestedShowExplicitly = true;
        }
        if (!this.mSystemReady) {
            ImeTracker.forLogging().onFailed(token, 5);
            Slog.i("InputMethodManagerService", "System is not Ready, so not show keyboard");
            return false;
        }
        ImeTracker.forLogging().onProgress(token, 5);
        this.mVisibilityStateComputer.requestImeVisibility(iBinder, true);
        InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
        if (inputMethodBindingController.mCurMethod != null) {
            if (inputMethodBindingController.mHasMainConnection && !inputMethodBindingController.mVisibleBound) {
                inputMethodBindingController.mVisibleBound = inputMethodBindingController.bindCurrentInputMethodService(inputMethodBindingController.mVisibleConnection, 738201601);
            }
        } else if (inputMethodBindingController.mHasMainConnection) {
            long uptimeMillis = SystemClock.uptimeMillis() - inputMethodBindingController.mLastBindTime;
            BatteryService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("setCurrentMethodVisible: bindingDuration=", uptimeMillis, ", mLastBindTime="), inputMethodBindingController.mLastBindTime, "InputMethodBindingController");
            if (uptimeMillis >= 3000) {
                EventLog.writeEvent(32000, inputMethodBindingController.mSelectedMethodId, Long.valueOf(uptimeMillis), 1);
                Slog.w("InputMethodBindingController", "Force disconnect/connect to the IME in setCurrentMethodVisible()");
                Context context = inputMethodBindingController.mContext;
                InputMethodBindingController.AnonymousClass2 anonymousClass2 = inputMethodBindingController.mMainConnection;
                context.unbindService(anonymousClass2);
                inputMethodBindingController.mHasMainConnection = false;
                inputMethodBindingController.mHasMainConnection = inputMethodBindingController.bindCurrentInputMethodService(anonymousClass2, 1082654725);
            }
        } else {
            inputMethodBindingController.bindCurrentMethod();
        }
        IInputMethodInvoker curMethodLocked = getCurMethodLocked();
        ImeTracker.forLogging().onCancelled(this.mCurStatsToken, 8);
        if (!Flags.deferShowSoftInputUntilSessionCreation() ? curMethodLocked != null : !(curMethodLocked == null || (clientState = this.mCurClient) == null || clientState.mCurSession == null)) {
            ImeTracker.forLogging().onProgress(token, 8);
            this.mCurStatsToken = token;
            return false;
        }
        ImeTracker.forLogging().onProgress(token, 9);
        this.mCurStatsToken = null;
        if (Flags.useHandwritingListenerForTooltype()) {
            int lastUsedInputDeviceId = InputManagerService.this.mNative.getLastUsedInputDeviceId();
            InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
            if (inputManager != null && (inputDevice = inputManager.getInputDevice(lastUsedInputDeviceId)) != null) {
                if (isStylusDevice(inputDevice)) {
                    i4 = 2;
                } else if (inputDevice.supportsSource(4098)) {
                    i4 = 1;
                }
                onUpdateEditorToolType(i4);
            }
        } else if (i2 != 0) {
            onUpdateEditorToolType(i2);
        }
        DefaultImeVisibilityApplier defaultImeVisibilityApplier = this.mVisibilityApplier;
        int i5 = this.mVisibilityStateComputer.mShowForced ? 3 : 1;
        InputMethodManagerService inputMethodManagerService = defaultImeVisibilityApplier.mService;
        IInputMethodInvoker curMethodLocked2 = inputMethodManagerService.getCurMethodLocked();
        if (curMethodLocked2 != null) {
            Slog.v("InputMethodManagerService", "Calling " + curMethodLocked2 + ".showSoftInput(" + iBinder + ", " + i5 + ", " + resultReceiver + ") for reason: " + InputMethodDebug.softInputDisplayReasonToString(i3));
            try {
                curMethodLocked2.mTarget.showSoftInput(iBinder, token, i5, resultReceiver);
                if (ImeTracker.DEBUG_IME_VISIBILITY) {
                    EventLog.writeEvent(32001, token != null ? token.getTag() : "TOKEN_NONE", Objects.toString(inputMethodManagerService.mImeBindingState.mFocusedWindow), InputMethodDebug.softInputDisplayReasonToString(i3), InputMethodDebug.softInputModeToString(inputMethodManagerService.mImeBindingState.mFocusedWindowSoftInputMode));
                }
                inputMethodManagerService.onShowHideSoftInputRequested(true, iBinder, i3, token);
            } catch (RemoteException e) {
                IInputMethodInvoker.logRemoteException(e);
            }
        }
        this.mVisibilityStateComputer.mInputShown = true;
        return true;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager != null && proKioskManager.getProKioskState() && proKioskManager.getInputMethodRestrictionState()) {
            Slog.w("InputMethodManagerService", "Input method restricted by Knox Customization");
            return;
        }
        synchronized (ImfLock.class) {
            try {
                if (canShowInputMethodPickerLocked(iInputMethodClient)) {
                    ClientState clientState = this.mCurClient;
                    this.mHandler.obtainMessage(1, i, clientState != null ? clientState.mSelfReportedDisplayId : 0).sendToTarget();
                    return;
                }
                Slog.w("InputMethodManagerService", "Ignoring showInputMethodPickerFromClient of uid " + Binder.getCallingUid() + ": " + iInputMethodClient);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void showInputMethodPickerFromSystem(int i, int i2) {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager != null && proKioskManager.getProKioskState() && proKioskManager.getInputMethodRestrictionState()) {
            Slog.w("InputMethodManagerService", "Input method restricted by Knox Customization");
        } else {
            this.mHandler.obtainMessage(1, i, i2).sendToTarget();
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3) {
        ClientState clientState;
        IInputMethodClientInvoker iInputMethodClientInvoker;
        Trace.traceBegin(32L, "IMMS.showSoftInput");
        int callingUid = Binder.getCallingUid();
        ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#showSoftInput", this.mDumper);
        synchronized (ImfLock.class) {
            try {
                int i4 = 0;
                if (!canInteractWithImeLocked(callingUid, iInputMethodClient, "showSoftInput", token)) {
                    ImeTracker.forLogging().onFailed(token, 3);
                    Trace.traceEnd(32L);
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!Flags.refactorInsetsController()) {
                        Slog.v("InputMethodManagerService", "Client requesting input be shown, flags : " + i);
                        return showCurrentInputLocked(iBinder, token, i, i2, resultReceiver, i3);
                    }
                    boolean z = this.mVisibilityStateComputer.mInputShown;
                    ImeBindingState imeBindingState = this.mImeBindingState;
                    if (imeBindingState == null || (clientState = imeBindingState.mFocusedWindowClient) == null || (iInputMethodClientInvoker = clientState.mClient) == null) {
                        return false;
                    }
                    iInputMethodClientInvoker.setImeVisibility(true);
                    if (resultReceiver != null) {
                        if (!z) {
                            i4 = 2;
                        }
                        resultReceiver.send(i4, null);
                    }
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Trace.traceEnd(32L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void showSoftInputInternal(IBinder iBinder) {
        Trace.traceBegin(32L, "IMMS.showSoftInputInternal");
        ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#showSoftInput", this.mDumper);
        synchronized (ImfLock.class) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    showCurrentInputLocked(iBinder, null, 0, 0, null, 1);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Trace.traceEnd(32L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, final int i, CursorAnchorInfo cursorAnchorInfo, final String str, final String str2, final IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) {
        IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback2;
        synchronized (ImfLock.class) {
            if (!getInputMethodBindingController(i).mSupportsConnectionlessStylusHw) {
                Slog.w("InputMethodManagerService", "Connectionless stylus handwriting mode unsupported by IME.");
                try {
                    iConnectionlessHandwritingCallback.onError(1);
                } catch (RemoteException e) {
                    Slog.e("InputMethodManagerService", "Failed to report CONNECTIONLESS_HANDWRITING_ERROR_UNSUPPORTED", e);
                    e.rethrowAsRuntimeException();
                }
                return;
            }
            boolean z = (str == null || str2 == null) ? false : true;
            if (z) {
                synchronized (ImfLock.class) {
                    if (!this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str2)) {
                        Slog.w("InputMethodManagerService", "startConnectionlessStylusHandwriting() fail");
                        try {
                            iConnectionlessHandwritingCallback.onError(2);
                        } catch (RemoteException e2) {
                            Slog.e("InputMethodManagerService", "Failed to report CONNECTIONLESS_HANDWRITING_ERROR_OTHER", e2);
                            e2.rethrowAsRuntimeException();
                        }
                        throw new IllegalArgumentException("Delegator doesn't match UID");
                    }
                }
                iConnectionlessHandwritingCallback2 = new IConnectionlessHandwritingCallback.Stub() { // from class: com.android.server.inputmethod.InputMethodManagerService.3
                    public final void onError(int i2) {
                        iConnectionlessHandwritingCallback.onError(i2);
                    }

                    public final void onResult(CharSequence charSequence) {
                        synchronized (ImfLock.class) {
                            InputMethodManagerService.this.mHwController.prepareStylusHandwritingDelegation(i, str, str2, true);
                        }
                        iConnectionlessHandwritingCallback.onResult(charSequence);
                    }
                };
            } else {
                iConnectionlessHandwritingCallback2 = iConnectionlessHandwritingCallback;
            }
            if (startStylusHandwriting(iInputMethodClient, false, iConnectionlessHandwritingCallback2, cursorAnchorInfo, z)) {
                return;
            }
            try {
                iConnectionlessHandwritingCallback.onError(2);
            } catch (RemoteException e3) {
                Slog.e("InputMethodManagerService", "Failed to report CONNECTIONLESS_HANDWRITING_ERROR_OTHER", e3);
                e3.rethrowAsRuntimeException();
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startImeTrace() {
        ImeTracing.getInstance().startTrace((PrintWriter) null);
        synchronized (ImfLock.class) {
            this.mClientController.forAllClients(new InputMethodManagerService$$ExternalSyntheticLambda8(2));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x008c A[Catch: all -> 0x007c, TryCatch #7 {all -> 0x007c, blocks: (B:26:0x006d, B:28:0x0075, B:31:0x0084, B:33:0x008c, B:34:0x00ad, B:37:0x00b2, B:39:0x00be, B:48:0x00d5, B:49:0x00d8, B:59:0x00f8, B:60:0x00fb, B:65:0x0105, B:66:0x0108, B:166:0x0081), top: B:25:0x006d }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b2 A[Catch: all -> 0x007c, TRY_ENTER, TryCatch #7 {all -> 0x007c, blocks: (B:26:0x006d, B:28:0x0075, B:31:0x0084, B:33:0x008c, B:34:0x00ad, B:37:0x00b2, B:39:0x00be, B:48:0x00d5, B:49:0x00d8, B:59:0x00f8, B:60:0x00fb, B:65:0x0105, B:66:0x0108, B:166:0x0081), top: B:25:0x006d }] */
    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int r22, com.android.internal.inputmethod.IInputMethodClient r23, android.os.IBinder r24, int r25, int r26, int r27, android.view.inputmethod.EditorInfo r28, com.android.internal.inputmethod.IRemoteInputConnection r29, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection r30, int r31, int r32, android.window.ImeOnBackInvokedDispatcher r33) {
        /*
            Method dump skipped, instructions count: 799
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.startInputOrWindowGainedFocus(int, com.android.internal.inputmethod.IInputMethodClient, android.os.IBinder, int, int, int, android.view.inputmethod.EditorInfo, com.android.internal.inputmethod.IRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection, int, int, android.window.ImeOnBackInvokedDispatcher):com.android.internal.inputmethod.InputBindResult");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0176, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x031d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocusInternalLocked(int r24, android.os.IBinder r25, int r26, int r27, int r28, android.view.inputmethod.EditorInfo r29, com.android.internal.inputmethod.IRemoteInputConnection r30, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection r31, int r32, com.android.server.inputmethod.InputMethodBindingController r33, android.window.ImeOnBackInvokedDispatcher r34, com.android.server.inputmethod.ClientState r35) {
        /*
            Method dump skipped, instructions count: 830
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.startInputOrWindowGainedFocusInternalLocked(int, android.os.IBinder, int, int, int, android.view.inputmethod.EditorInfo, com.android.internal.inputmethod.IRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection, int, com.android.server.inputmethod.InputMethodBindingController, android.window.ImeOnBackInvokedDispatcher, com.android.server.inputmethod.ClientState):com.android.internal.inputmethod.InputBindResult");
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x02dd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.internal.inputmethod.InputBindResult startInputUncheckedLocked(com.android.server.inputmethod.ClientState r18, com.android.internal.inputmethod.IRemoteInputConnection r19, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection r20, android.view.inputmethod.EditorInfo r21, int r22, int r23, int r24, android.window.ImeOnBackInvokedDispatcher r25, com.android.server.inputmethod.InputMethodBindingController r26) {
        /*
            Method dump skipped, instructions count: 745
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.startInputUncheckedLocked(com.android.server.inputmethod.ClientState, com.android.internal.inputmethod.IRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo, int, int, int, android.window.ImeOnBackInvokedDispatcher, com.android.server.inputmethod.InputMethodBindingController):com.android.internal.inputmethod.InputBindResult");
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startProtoDump(byte[] bArr, int i, String str) {
        if (bArr != null || i == 2) {
            ImeTracing imeTracing = ImeTracing.getInstance();
            if (imeTracing.isAvailable() && imeTracing.isEnabled()) {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                if (i == 0) {
                    long start = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1125281431553L, SystemClock.elapsedRealtimeNanos());
                    protoOutputStream.write(1138166333442L, str);
                    protoOutputStream.write(1146756268035L, bArr);
                    protoOutputStream.end(start);
                } else if (i == 1) {
                    long start2 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1125281431553L, SystemClock.elapsedRealtimeNanos());
                    protoOutputStream.write(1138166333442L, str);
                    protoOutputStream.write(1146756268035L, bArr);
                    protoOutputStream.end(start2);
                } else {
                    if (i != 2) {
                        return;
                    }
                    long start3 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1125281431553L, SystemClock.elapsedRealtimeNanos());
                    protoOutputStream.write(1138166333442L, str);
                    dumpDebug(protoOutputStream);
                    protoOutputStream.end(start3);
                }
                imeTracing.addToBuffer(protoOutputStream, i);
            }
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startStylusHandwriting(IInputMethodClient iInputMethodClient) {
        startStylusHandwriting(iInputMethodClient, false, null, null, false);
    }

    public final boolean startStylusHandwriting(IInputMethodClient iInputMethodClient, boolean z, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z2) {
        Trace.traceBegin(32L, "IMMS.startStylusHandwriting");
        try {
            ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#startStylusHandwriting", this.mDumper);
            int callingUid = Binder.getCallingUid();
            synchronized (ImfLock.class) {
                if (!z) {
                    try {
                        this.mHwController.clearPendingHandwritingDelegation();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!canInteractWithImeLocked(callingUid, iInputMethodClient, "startStylusHandwriting", null)) {
                    return false;
                }
                IntArray intArray = this.mStylusIds;
                if (intArray == null || intArray.size() == 0) {
                    Slog.w("InputMethodManagerService", "No supported Stylus hardware found on device. Ignoring startStylusHandwriting()");
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!getInputMethodBindingController(this.mCurrentUserId).mSupportsStylusHw) {
                        Slog.w("InputMethodManagerService", "Stylus HW unsupported by IME. Ignoring startStylusHandwriting()");
                        return false;
                    }
                    OptionalInt currentRequestId = this.mHwController.getCurrentRequestId();
                    if (!currentRequestId.isPresent()) {
                        Slog.e("InputMethodManagerService", "Stylus handwriting was not initialized.");
                        return false;
                    }
                    if (!this.mHwController.isStylusGestureOngoing()) {
                        Slog.e("InputMethodManagerService", "There is no ongoing stylus gesture to start stylus handwriting.");
                        return false;
                    }
                    HandwritingEventReceiverSurface handwritingEventReceiverSurface = this.mHwController.mHandwritingSurface;
                    if (handwritingEventReceiverSurface != null && handwritingEventReceiverSurface.mIsIntercepting) {
                        Slog.e("InputMethodManagerService", "Stylus handwriting session is already ongoing. Ignoring startStylusHandwriting().");
                        return false;
                    }
                    IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                    if (curMethodLocked == null) {
                        return false;
                    }
                    try {
                        curMethodLocked.mTarget.canStartStylusHandwriting(currentRequestId.getAsInt(), iConnectionlessHandwritingCallback, cursorAnchorInfo, z2);
                    } catch (RemoteException e) {
                        IInputMethodInvoker.logRemoteException(e);
                    }
                    Trace.traceEnd(32L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void stopImeTrace() {
        ImeTracing.getInstance().stopTrace((PrintWriter) null);
        synchronized (ImfLock.class) {
            this.mClientController.forAllClients(new InputMethodManagerService$$ExternalSyntheticLambda8(1));
        }
    }

    public final boolean switchToInputMethodLocked(int i, String str) {
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        int i2 = this.mCurrentUserId;
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        if (i == i2) {
            if (!inputMethodMap.mMap.containsKey(str) || !inputMethodSettings.getEnabledInputMethodListWithFilter(null).contains(inputMethodMap.get(str))) {
                return false;
            }
            setInputMethodLocked(-1, 0, str);
            return true;
        }
        if (!inputMethodMap.mMap.containsKey(str) || !inputMethodSettings.getEnabledInputMethodListWithFilter(null).contains(inputMethodMap.get(str))) {
            return false;
        }
        inputMethodSettings.putSelectedInputMethod(str);
        SecureSettingsWrapper.get(inputMethodSettings.mUserId).putInt(-1, "selected_input_method_subtype");
        return true;
    }

    public final void unbindCurrentClientLocked(int i) {
        if (this.mCurClient != null) {
            if (this.mBoundToMethod) {
                this.mBoundToMethod = false;
                IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    try {
                        curMethodLocked.mTarget.unbindInput();
                    } catch (RemoteException e) {
                        IInputMethodInvoker.logRemoteException(e);
                    }
                }
            }
            this.mBoundToAccessibility = false;
            this.mCurClient.mClient.setActive(false, false);
            InputMethodBindingController inputMethodBindingController = getInputMethodBindingController(this.mCurrentUserId);
            IInputMethodClientInvoker iInputMethodClientInvoker = this.mCurClient.mClient;
            int i2 = inputMethodBindingController.mCurSeq;
            if (iInputMethodClientInvoker.mIsProxy) {
                try {
                    iInputMethodClientInvoker.mTarget.onUnbindMethod(i2, i);
                } catch (RemoteException e2) {
                    IInputMethodClientInvoker.logRemoteException(e2);
                }
            } else {
                iInputMethodClientInvoker.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda0(iInputMethodClientInvoker, i2, i, 0));
            }
            ClientState clientState = this.mCurClient;
            clientState.mSessionRequested = false;
            clientState.mSessionRequestedForAccessibility = false;
            this.mCurClient = null;
            ImeTracker.forLogging().onFailed(this.mCurStatsToken, 8);
            this.mCurStatsToken = null;
            this.mMenuController.hideInputMethodMenuLocked();
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void undoMinimizeSoftInput() {
        IInputMethodInvoker curMethodLocked = getCurMethodLocked();
        if (curMethodLocked == null) {
            return;
        }
        try {
            curMethodLocked.mTarget.undoMinimizeSoftInput();
        } catch (RemoteException e) {
            IInputMethodInvoker.logRemoteException(e);
        }
    }

    public final void updateFromSettingsLocked(boolean z) {
        Slog.d("InputMethodManagerService", "updateFromSettingsLocked");
        updateInputMethodsFromSettingsLocked(z);
        this.mMenuController.updateKeyboardFromSettingsLocked();
    }

    public final void updateImeSwitchStatus(String str) {
        IInputMethodSession iInputMethodSession;
        try {
            SessionState sessionState = this.mEnabledSession;
            if (sessionState == null || (iInputMethodSession = sessionState.mSession) == null) {
                return;
            }
            iInputMethodSession.appPrivateCommand(str, (Bundle) null);
        } catch (RemoteException unused) {
        }
    }

    public final void updateInputMethodsFromSettingsLocked(boolean z) {
        ApplicationInfo applicationInfo;
        int i = this.mCurrentUserId;
        InputMethodSettings inputMethodSettings = InputMethodSettingsRepository.get(i);
        if (z) {
            PackageManager packageManagerForUser = getPackageManagerForUser(this.mContext, i);
            ArrayList enabledInputMethodListWithFilter = inputMethodSettings.getEnabledInputMethodListWithFilter(null);
            for (int i2 = 0; i2 < enabledInputMethodListWithFilter.size(); i2++) {
                InputMethodInfo inputMethodInfo = (InputMethodInfo) enabledInputMethodListWithFilter.get(i2);
                try {
                    applicationInfo = packageManagerForUser.getApplicationInfo(inputMethodInfo.getPackageName(), PackageManager.ApplicationInfoFlags.of(32768L));
                } catch (PackageManager.NameNotFoundException unused) {
                    applicationInfo = null;
                }
                if (applicationInfo != null && applicationInfo.enabledSetting == 4) {
                    packageManagerForUser.setApplicationEnabledSetting(inputMethodInfo.getPackageName(), 0, 1);
                }
            }
        }
        if (getInputMethodBindingController(this.mCurrentUserId).mDeviceIdToShowIme == 0) {
            String string = SecureSettingsWrapper.getString("default_input_method", null, i);
            String string2 = SecureSettingsWrapper.getString("default_device_input_method", null, i);
            if (string2 != null && !Objects.equals(string, string2)) {
                SecureSettingsWrapper.get(i).putString("default_input_method", string2);
                SecureSettingsWrapper.get(i).putString("default_device_input_method", null);
            }
        }
        String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
        if (TextUtils.isEmpty(selectedInputMethod) && chooseNewDefaultIMELocked()) {
            selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
        }
        if (TextUtils.isEmpty(selectedInputMethod)) {
            resetCurrentMethodAndClientLocked(4);
        } else {
            try {
                setInputMethodLocked(inputMethodSettings.getSelectedInputMethodSubtypeId(selectedInputMethod), 0, selectedInputMethod);
            } catch (IllegalArgumentException e) {
                Slog.w("InputMethodManagerService", "Unknown input method from prefs: " + selectedInputMethod, e);
                resetCurrentMethodAndClientLocked(5);
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m("updateInputMethodsFromSettingsLocked: id=", selectedInputMethod, "InputMethodManagerService");
        InputMethodSubtypeSwitchingController inputMethodSubtypeSwitchingController = this.mSwitchingController;
        int i3 = inputMethodSubtypeSwitchingController.mUserId;
        InputMethodMap inputMethodMap = inputMethodSettings.mMethodMap;
        if (i == i3) {
            inputMethodSubtypeSwitchingController.mController = InputMethodSubtypeSwitchingController.ControllerImpl.createFrom(inputMethodSubtypeSwitchingController.mController, InputMethodSubtypeSwitchingController.getSortedInputMethodAndSubtypeList(false, false, false, inputMethodSubtypeSwitchingController.mContext, inputMethodMap, i3));
        } else {
            this.mSwitchingController = new InputMethodSubtypeSwitchingController(this.mContext, inputMethodMap, i);
        }
        HardwareKeyboardShortcutController hardwareKeyboardShortcutController = this.mHardwareKeyboardShortcutController;
        if (i == hardwareKeyboardShortcutController.mUserId) {
            hardwareKeyboardShortcutController.reset(inputMethodMap);
        } else {
            this.mHardwareKeyboardShortcutController = new HardwareKeyboardShortcutController(inputMethodMap, i);
        }
        sendOnNavButtonFlagsChangedLocked();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0072 A[Catch: all -> 0x0035, TRY_LEAVE, TryCatch #0 {all -> 0x0035, blocks: (B:37:0x0028, B:39:0x0030, B:42:0x0037, B:11:0x0042, B:13:0x0048, B:15:0x004c, B:17:0x0055, B:19:0x005d, B:23:0x006a, B:25:0x0072, B:35:0x0052), top: B:36:0x0028 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSystemUiLocked(int r11, int r12) {
        /*
            r10 = this;
            int r0 = r10.mCurrentUserId
            com.android.server.inputmethod.InputMethodBindingController r1 = r10.getInputMethodBindingController(r0)
            android.os.IBinder r4 = r1.mCurToken
            if (r4 != 0) goto Lc
            goto L7c
        Lc:
            int r3 = r1.mCurTokenDisplayId
            com.android.server.inputmethod.ImeBindingState r2 = r10.mImeBindingState
            r5 = 0
            if (r2 == 0) goto L16
            android.os.IBinder r2 = r2.mFocusedWindow
            goto L17
        L16:
            r2 = r5
        L17:
            if (r2 == 0) goto L22
            java.util.WeakHashMap r5 = r10.mFocusedWindowPerceptible
            java.lang.Object r2 = r5.get(r2)
            r5 = r2
            java.lang.Boolean r5 = (java.lang.Boolean) r5
        L22:
            long r8 = android.os.Binder.clearCallingIdentity()
            if (r11 == 0) goto L40
            com.android.server.inputmethod.SemInputMethodManagerServiceUtil r2 = r10.mSemImmsUtil     // Catch: java.lang.Throwable -> L35
            boolean r2 = r2.isKeyguardLocked()     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto L37
            boolean r2 = r10.mIsInteractive     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto L40
            goto L37
        L35:
            r10 = move-exception
            goto L7d
        L37:
            java.lang.String r11 = "InputMethodManagerService"
            java.lang.String r2 = "updateSystemUiLocked(), Current client is not Keyguard, changing visibility to Vis : 0"
            android.util.Slog.d(r11, r2)     // Catch: java.lang.Throwable -> L35
            r11 = 0
        L40:
            if (r5 == 0) goto L52
            boolean r2 = r5.booleanValue()     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto L52
            r2 = r11 & 2
            if (r2 == 0) goto L50
            r11 = r11 & (-3)
            r11 = r11 | 8
        L50:
            r5 = r11
            goto L55
        L52:
            r11 = r11 & (-9)
            goto L50
        L55:
            java.lang.String r11 = r1.mCurId     // Catch: java.lang.Throwable -> L35
            com.android.server.inputmethod.InputMethodMenuController r2 = r10.mMenuController     // Catch: java.lang.Throwable -> L35
            android.app.AlertDialog r2 = r2.mSwitchingDialog     // Catch: java.lang.Throwable -> L35
            if (r2 != 0) goto L68
            java.lang.String r1 = r1.mSelectedMethodId     // Catch: java.lang.Throwable -> L35
            boolean r11 = java.util.Objects.equals(r11, r1)     // Catch: java.lang.Throwable -> L35
            if (r11 != 0) goto L66
            goto L68
        L66:
            r6 = r12
            goto L6a
        L68:
            r12 = 3
            goto L66
        L6a:
            boolean r7 = r10.shouldShowImeSwitcherLocked(r5, r0)     // Catch: java.lang.Throwable -> L35
            com.android.server.statusbar.StatusBarManagerInternal r10 = r10.mStatusBarManagerInternal     // Catch: java.lang.Throwable -> L35
            if (r10 == 0) goto L79
            com.android.server.statusbar.StatusBarManagerService$2 r10 = (com.android.server.statusbar.StatusBarManagerService.AnonymousClass2) r10     // Catch: java.lang.Throwable -> L35
            com.android.server.statusbar.StatusBarManagerService r2 = com.android.server.statusbar.StatusBarManagerService.this     // Catch: java.lang.Throwable -> L35
            r2.setImeWindowStatus(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L35
        L79:
            android.os.Binder.restoreCallingIdentity(r8)
        L7c:
            return
        L7d:
            android.os.Binder.restoreCallingIdentity(r8)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.inputmethod.InputMethodManagerService.updateSystemUiLocked(int, int):void");
    }

    public final void updateSystemUiLocked$1() {
        updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
    }

    public final boolean userHasDebugPriv(int i, ShellCommand shellCommand) {
        if (!this.mUserManagerInternal.hasUserRestriction("no_debugging_features", i)) {
            return true;
        }
        ActiveServices$$ExternalSyntheticOutline0.m(i, shellCommand.getErrPrintWriter(), "User #", " is restricted with DISALLOW_DEBUGGING_FEATURES.");
        return false;
    }
}
