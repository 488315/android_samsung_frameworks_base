package com.android.systemui.statusbar;

import android.app.ITransientNotificationCallback;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.RemoteViews;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.GcUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shade.SecPanelBlockExpandingHelper;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.util.SafeUIState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CommandQueue extends IStatusBar.Stub implements CallbackController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mCallbacks;
    public final SparseArray mDisplayDisabled;
    public final DisplayTracker mDisplayTracker;
    public final DumpHandler mDumpHandler;
    public final HandlerC2505H mHandler;
    public int mLastUpdatedImeDisplayId;
    public final Object mLock;
    public final ProtoTracer mProtoTracer;
    public final CommandRegistry mRegistry;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.CommandQueue$H */
    public final class HandlerC2505H extends Handler {
        public /* synthetic */ HandlerC2505H(CommandQueue commandQueue, Looper looper, int i) {
            this(looper);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = 0;
            switch (message.what & (-65536)) {
                case 65536:
                    int i2 = message.arg1;
                    if (i2 == 1) {
                        Pair pair = (Pair) message.obj;
                        while (i < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setIcon((String) pair.first, (StatusBarIcon) pair.second);
                            i++;
                        }
                        break;
                    } else if (i2 == 2) {
                        while (i < CommandQueue.this.mCallbacks.size()) {
                            ((Callbacks) CommandQueue.this.mCallbacks.get(i)).removeIcon((String) message.obj);
                            i++;
                        }
                        break;
                    }
                    break;
                case 131072:
                case 8257536:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    for (int i3 = 0; i3 < CommandQueue.this.mCallbacks.size(); i3++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i3)).disable(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4 != 0);
                    }
                    break;
                case 196608:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).animateExpandNotificationsPanel();
                        i++;
                    }
                    break;
                case 262144:
                    for (int i4 = 0; i4 < CommandQueue.this.mCallbacks.size(); i4++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i4)).animateCollapsePanels(message.arg1, message.arg2 != 0);
                    }
                    break;
                case EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC /* 327680 */:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).animateExpandSettingsPanel((String) message.obj);
                        i++;
                    }
                    break;
                case 393216:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    for (int i5 = 0; i5 < CommandQueue.this.mCallbacks.size(); i5++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i5)).onSystemBarAttributesChanged(someArgs2.argi1, someArgs2.argi2, (AppearanceRegion[]) someArgs2.arg1, someArgs2.argi3 == 1, someArgs2.argi4, someArgs2.argi5, (String) someArgs2.arg3, (LetterboxDetails[]) someArgs2.arg4);
                    }
                    someArgs2.recycle();
                    break;
                case 458752:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onDisplayReady(message.arg1);
                        i++;
                    }
                    break;
                case 524288:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    CommandQueue.m1700$$Nest$mhandleShowImeButton(CommandQueue.this, someArgs3.argi1, (IBinder) someArgs3.arg1, someArgs3.argi2, someArgs3.argi3, someArgs3.argi4 != 0);
                    break;
                case 589824:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).toggleRecentApps();
                        i++;
                    }
                    break;
                case 655360:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).preloadRecentApps();
                        i++;
                    }
                    break;
                case 720896:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).cancelPreloadRecentApps();
                        i++;
                    }
                    break;
                case 786432:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setWindowState(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        i++;
                    }
                    break;
                case 851968:
                    for (int i6 = 0; i6 < CommandQueue.this.mCallbacks.size(); i6++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i6)).showRecentApps(message.arg1 != 0);
                    }
                    break;
                case 917504:
                    for (int i7 = 0; i7 < CommandQueue.this.mCallbacks.size(); i7++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i7)).hideRecentApps(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 1179648:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showScreenPinningRequest(message.arg1);
                        i++;
                    }
                    break;
                case 1245184:
                    for (int i8 = 0; i8 < CommandQueue.this.mCallbacks.size(); i8++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i8)).appTransitionPending(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 1310720:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).appTransitionCancelled(message.arg1);
                        i++;
                    }
                    break;
                case 1376256:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    for (int i9 = 0; i9 < CommandQueue.this.mCallbacks.size(); i9++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i9)).appTransitionStarting(someArgs4.argi1, ((Long) someArgs4.arg1).longValue(), ((Long) someArgs4.arg2).longValue(), someArgs4.argi2 != 0);
                    }
                    break;
                case 1441792:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showAssistDisclosure();
                        i++;
                    }
                    break;
                case 1507328:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).startAssist((Bundle) message.obj);
                        i++;
                    }
                    break;
                case 1572864:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onCameraLaunchGestureDetected(message.arg1);
                        i++;
                    }
                    break;
                case 1638400:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).toggleKeyboardShortcutsMenu(message.arg1);
                        i++;
                    }
                    break;
                case 1703936:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showPictureInPictureMenu();
                        i++;
                    }
                    break;
                case 1769472:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).addQsTile((ComponentName) message.obj);
                        i++;
                    }
                    break;
                case 1835008:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).remQsTile((ComponentName) message.obj);
                        i++;
                    }
                    break;
                case 1900544:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).clickTile((ComponentName) message.obj);
                        i++;
                    }
                    break;
                case 1966080:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).toggleSplitScreen();
                        i++;
                    }
                    break;
                case 2031616:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).appTransitionFinished(message.arg1);
                        i++;
                    }
                    break;
                case QuickStepContract.SYSUI_STATE_DEVICE_DOZING /* 2097152 */:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).dismissKeyboardShortcutsMenu();
                        i++;
                    }
                    break;
                case 2162688:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).handleSystemKey((KeyEvent) message.obj);
                        i++;
                    }
                    break;
                case 2228224:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).handleShowGlobalActionsMenu(message.arg1);
                        i++;
                    }
                    break;
                case 2293760:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).togglePanel();
                        i++;
                    }
                    break;
                case 2359296:
                    for (int i10 = 0; i10 < CommandQueue.this.mCallbacks.size(); i10++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i10)).handleShowShutdownUi((String) message.obj, message.arg1 != 0);
                    }
                    break;
                case 2424832:
                    for (int i11 = 0; i11 < CommandQueue.this.mCallbacks.size(); i11++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i11)).setTopAppHidesStatusBar(message.arg1 != 0);
                    }
                    break;
                case 2490368:
                    for (int i12 = 0; i12 < CommandQueue.this.mCallbacks.size(); i12++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i12)).onRotationProposal(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 2555904:
                    CommandQueue.this.mHandler.removeMessages(2752512);
                    CommandQueue.this.mHandler.removeMessages(2686976);
                    CommandQueue.this.mHandler.removeMessages(2621440);
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showAuthenticationDialog((PromptInfo) someArgs5.arg1, (IBiometricSysuiReceiver) someArgs5.arg2, (int[]) someArgs5.arg3, ((Boolean) someArgs5.arg4).booleanValue(), ((Boolean) someArgs5.arg5).booleanValue(), someArgs5.argi1, someArgs5.argl1, (String) someArgs5.arg6, someArgs5.argl2);
                        i++;
                    }
                    someArgs5.recycle();
                    break;
                case 2621440:
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onBiometricAuthenticated(someArgs6.argi1);
                        i++;
                    }
                    someArgs6.recycle();
                    break;
                case 2686976:
                    SomeArgs someArgs7 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onBiometricHelp(someArgs7.argi1, (String) someArgs7.arg1);
                        i++;
                    }
                    someArgs7.recycle();
                    break;
                case 2752512:
                    SomeArgs someArgs8 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onBiometricError(someArgs8.argi1, someArgs8.argi2, someArgs8.argi3);
                        i++;
                    }
                    someArgs8.recycle();
                    break;
                case 2818048:
                    SomeArgs someArgs9 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).hideAuthenticationDialog(someArgs9.argl1);
                        i++;
                    }
                    someArgs9.recycle();
                    break;
                case 2883584:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showWirelessChargingAnimation(message.arg1);
                        i++;
                    }
                    break;
                case 2949120:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showPinningEnterExitToast(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 3014656:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showPinningEscapeToast();
                        i++;
                    }
                    break;
                case 3080192:
                    for (int i13 = 0; i13 < CommandQueue.this.mCallbacks.size(); i13++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i13)).onRecentsAnimationStateChanged(message.arg1 > 0);
                    }
                    break;
                case 3145728:
                    SomeArgs someArgs10 = (SomeArgs) message.obj;
                    int i14 = someArgs10.argi1;
                    int i15 = someArgs10.argi2;
                    boolean z = someArgs10.argi3 != 0;
                    someArgs10.recycle();
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showTransient(i14, i15, z);
                        i++;
                    }
                    break;
                case 3211264:
                    SomeArgs someArgs11 = (SomeArgs) message.obj;
                    int i16 = someArgs11.argi1;
                    int i17 = someArgs11.argi2;
                    someArgs11.recycle();
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).abortTransient(i16, i17);
                        i++;
                    }
                    break;
                case 3276800:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showInattentiveSleepWarning();
                        i++;
                    }
                    break;
                case 3342336:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).dismissInattentiveSleepWarning(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 3407872:
                    SomeArgs someArgs12 = (SomeArgs) message.obj;
                    String str = (String) someArgs12.arg1;
                    IBinder iBinder = (IBinder) someArgs12.arg2;
                    CharSequence charSequence = (CharSequence) someArgs12.arg3;
                    IBinder iBinder2 = (IBinder) someArgs12.arg4;
                    ITransientNotificationCallback iTransientNotificationCallback = (ITransientNotificationCallback) someArgs12.arg5;
                    int i18 = someArgs12.argi1;
                    int i19 = someArgs12.argi2;
                    int i20 = someArgs12.argi3;
                    Iterator it = CommandQueue.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callbacks) it.next()).showToast(i18, str, iBinder, charSequence, iBinder2, i19, iTransientNotificationCallback, i20);
                        i19 = i19;
                        i18 = i18;
                    }
                    break;
                case 3473408:
                    SomeArgs someArgs13 = (SomeArgs) message.obj;
                    String str2 = (String) someArgs13.arg1;
                    IBinder iBinder3 = (IBinder) someArgs13.arg2;
                    Iterator it2 = CommandQueue.this.mCallbacks.iterator();
                    while (it2.hasNext()) {
                        ((Callbacks) it2.next()).hideToast(str2, iBinder3);
                    }
                    break;
                case 3538944:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onTracingStateChanged(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 3604480:
                    Iterator it3 = CommandQueue.this.mCallbacks.iterator();
                    while (it3.hasNext()) {
                        ((Callbacks) it3.next()).suppressAmbientDisplay(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3670016:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).requestWindowMagnificationConnection(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 3801088:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onEmergencyActionLaunchGestureDetected();
                        i++;
                    }
                    break;
                case 3866624:
                    for (int i21 = 0; i21 < CommandQueue.this.mCallbacks.size(); i21++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i21)).setNavigationBarLumaSamplingEnabled(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 3932160:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setUdfpsRefreshRateCallback((IUdfpsRefreshRateRequestCallback) message.obj);
                        i++;
                    }
                    break;
                case 3997696:
                    SomeArgs someArgs14 = (SomeArgs) message.obj;
                    ComponentName componentName = (ComponentName) someArgs14.arg1;
                    CharSequence charSequence2 = (CharSequence) someArgs14.arg2;
                    CharSequence charSequence3 = (CharSequence) someArgs14.arg3;
                    Icon icon = (Icon) someArgs14.arg4;
                    IAddTileResultCallback iAddTileResultCallback = (IAddTileResultCallback) someArgs14.arg5;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).requestAddTile(componentName, charSequence2, charSequence3, icon, iAddTileResultCallback);
                        i++;
                    }
                    someArgs14.recycle();
                    break;
                case 4063232:
                    String str3 = (String) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).cancelRequestAddTile(str3);
                        i++;
                    }
                    break;
                case 4128768:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setBiometricContextListener((IBiometricContextListener) message.obj);
                        i++;
                    }
                    break;
                case QuickStepContract.SYSUI_STATE_BACK_DISABLED /* 4194304 */:
                    SomeArgs someArgs15 = (SomeArgs) message.obj;
                    int intValue = ((Integer) someArgs15.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) someArgs15.arg2;
                    IUndoMediaTransferCallback iUndoMediaTransferCallback = (IUndoMediaTransferCallback) someArgs15.arg3;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).updateMediaTapToTransferSenderDisplay(intValue, mediaRoute2Info, iUndoMediaTransferCallback);
                        i++;
                    }
                    someArgs15.recycle();
                    break;
                case 4259840:
                    SomeArgs someArgs16 = (SomeArgs) message.obj;
                    int intValue2 = ((Integer) someArgs16.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) someArgs16.arg2;
                    Icon icon2 = (Icon) someArgs16.arg3;
                    CharSequence charSequence4 = (CharSequence) someArgs16.arg4;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).updateMediaTapToTransferReceiverDisplay(intValue2, mediaRoute2Info2, icon2, charSequence4);
                        i++;
                    }
                    someArgs16.recycle();
                    break;
                case 4325376:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider = (INearbyMediaDevicesProvider) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
                        i++;
                    }
                    break;
                case 4390912:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider2 = (INearbyMediaDevicesProvider) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider2);
                        i++;
                    }
                    break;
                case 4456448:
                    ComponentName componentName2 = (ComponentName) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).requestTileServiceListeningState(componentName2);
                        i++;
                    }
                    break;
                case 4521984:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showRearDisplayDialog(((Integer) message.obj).intValue());
                        i++;
                    }
                    break;
                case 4587520:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).goToFullscreenFromSplit();
                        i++;
                    }
                    break;
                case 4653056:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).enterStageSplitFromRunningApp(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 4718592:
                    String str4 = (String) ((SomeArgs) message.obj).arg1;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).showMediaOutputSwitcher(str4);
                        i++;
                    }
                    break;
                case 4784128:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).toggleTaskbar();
                        i++;
                    }
                    break;
                case 4849664:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).onFlashlightKeyPressed(message.arg1);
                        i++;
                    }
                    break;
                case 6619136:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        Callbacks callbacks = (Callbacks) CommandQueue.this.mCallbacks.get(i);
                        callbacks.getClass();
                        i++;
                    }
                    break;
                case 6684672:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).getClass();
                        i++;
                    }
                    break;
                case 6750208:
                    for (int i22 = 0; i22 < CommandQueue.this.mCallbacks.size(); i22++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i22)).setBlueLightFilter(message.arg1 != 0, message.arg2);
                    }
                    break;
                case 7405568:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).getClass();
                        i++;
                    }
                    break;
                case 7929856:
                    SomeArgs someArgs17 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).setNavigationBarShortcut((String) someArgs17.arg1, (RemoteViews) someArgs17.arg2, someArgs17.argi1, someArgs17.argi2);
                        i++;
                    }
                    someArgs17.recycle();
                    break;
                case 7995392:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).notifyRequestedGameToolsWin(((Boolean) message.obj).booleanValue());
                        i++;
                    }
                    break;
                case 8060928:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).resetScheduleAutoHide();
                        i++;
                    }
                    break;
                case 8126464:
                    for (int i23 = 0; i23 < CommandQueue.this.mCallbacks.size(); i23++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i23)).notifyRequestedSystemKey(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 8192000:
                    SomeArgs someArgs18 = (SomeArgs) message.obj;
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).notifySamsungPayInfo(someArgs18.argi1, ((Boolean) someArgs18.arg1).booleanValue(), (Rect) someArgs18.arg2);
                        i++;
                    }
                    someArgs18.recycle();
                    break;
                case 8716288:
                    while (i < CommandQueue.this.mCallbacks.size()) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i)).sendThreeFingerGestureKeyEvent((KeyEvent) message.obj);
                        i++;
                    }
                    break;
                case 9175040:
                    SomeArgs someArgs19 = (SomeArgs) message.obj;
                    for (int i24 = 0; i24 < CommandQueue.this.mCallbacks.size(); i24++) {
                        ((Callbacks) CommandQueue.this.mCallbacks.get(i24)).startSearcleByHomeKey(Boolean.valueOf(someArgs19.argi1 != 0), Boolean.valueOf(someArgs19.argi2 != 0));
                    }
                    break;
            }
        }

        private HandlerC2505H(Looper looper) {
            super(looper);
        }
    }

    /* renamed from: -$$Nest$mhandleShowImeButton, reason: not valid java name */
    public static void m1700$$Nest$mhandleShowImeButton(CommandQueue commandQueue, int i, IBinder iBinder, int i2, int i3, boolean z) {
        if (i == -1) {
            commandQueue.getClass();
            return;
        }
        int i4 = commandQueue.mLastUpdatedImeDisplayId;
        if (i4 != i && i4 != -1) {
            for (int i5 = 0; i5 < commandQueue.mCallbacks.size(); i5++) {
                ((Callbacks) commandQueue.mCallbacks.get(i5)).setImeWindowStatus(commandQueue.mLastUpdatedImeDisplayId, null, 4, 0, false);
            }
        }
        for (int i6 = 0; i6 < commandQueue.mCallbacks.size(); i6++) {
            ((Callbacks) commandQueue.mCallbacks.get(i6)).setImeWindowStatus(i, iBinder, i2, i3, z);
        }
        commandQueue.mLastUpdatedImeDisplayId = i;
    }

    public CommandQueue(Context context, DisplayTracker displayTracker) {
        this(context, displayTracker, null, null, null);
    }

    public final void abortTransient(int i, int i2) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            this.mHandler.obtainMessage(3211264, obtain).sendToTarget();
        }
    }

    public final void addQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1769472, componentName).sendToTarget();
        }
    }

    public final void animateCollapsePanels() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, 0, 0).sendToTarget();
        }
    }

    public final void animateExpandNotificationsPanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(196608);
            this.mHandler.sendEmptyMessage(196608);
        }
    }

    public final void animateExpandSettingsPanel(String str) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC);
            this.mHandler.obtainMessage(EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC, str).sendToTarget();
        }
    }

    public final void appTransitionCancelled(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1310720, i, 0).sendToTarget();
        }
    }

    public final void appTransitionFinished(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2031616, i, 0).sendToTarget();
        }
    }

    public final void appTransitionPending(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1245184, i, 0).sendToTarget();
        }
    }

    public final void appTransitionStarting(int i, long j, long j2) {
        appTransitionStarting(i, j, j2, false);
    }

    public final void cancelPreloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(720896);
            this.mHandler.obtainMessage(720896, 0, 0, null).sendToTarget();
        }
    }

    public final void cancelRequestAddTile(String str) {
        this.mHandler.obtainMessage(4063232, str).sendToTarget();
    }

    public final void clickQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1900544, componentName).sendToTarget();
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            this.mDisplayDisabled.put(i, new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
            int i4 = 1;
            int i5 = (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && i == 1) ? 8257536 : 131072;
            this.mHandler.removeMessages(i5);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            if (!z) {
                i4 = 0;
            }
            obtain.argi4 = i4;
            Message obtainMessage = this.mHandler.obtainMessage(i5, obtain);
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                this.mHandler.handleMessage(obtainMessage);
                obtainMessage.recycle();
            } else {
                obtainMessage.sendToTarget();
            }
        }
    }

    public final void dismissInattentiveSleepWarning(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3342336, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void dismissKeyboardShortcutsMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(QuickStepContract.SYSUI_STATE_DEVICE_DOZING);
            this.mHandler.obtainMessage(QuickStepContract.SYSUI_STATE_DEVICE_DOZING).sendToTarget();
        }
    }

    public final void dumpProto(final String[] strArr, final ParcelFileDescriptor parcelFileDescriptor) {
        final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        new Thread("Sysui.dumpProto") { // from class: com.android.systemui.statusbar.CommandQueue.3
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    try {
                    } catch (Exception unused) {
                        int i = CommandQueue.$r8$clinit;
                        Log.d("CommandQueue", "Process interrupted by Exception");
                    }
                    if (CommandQueue.this.mDumpHandler == null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception unused2) {
                        }
                    } else {
                        CommandQueue.this.mDumpHandler.dump(fileDescriptor, new PrintWriter(new OutputStream(this) { // from class: com.android.systemui.statusbar.CommandQueue.3.1
                            @Override // java.io.OutputStream
                            public final void write(int i2) {
                            }
                        }), strArr);
                    }
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused3) {
                    }
                }
            }
        }.start();
    }

    public final void enterStageSplitFromRunningApp(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(4653056, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final Pair getDisabled(int i) {
        Pair pair = (Pair) this.mDisplayDisabled.get(i);
        if (pair != null) {
            return pair;
        }
        Pair pair2 = new Pair(0, 0);
        this.mDisplayDisabled.put(i, pair2);
        return pair2;
    }

    public final void goToFullscreenFromSplit() {
        this.mHandler.obtainMessage(4587520).sendToTarget();
    }

    public final void handleSystemKey(KeyEvent keyEvent) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2162688, keyEvent).sendToTarget();
        }
    }

    public final void hideAuthenticationDialog(long j) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argl1 = j;
            this.mHandler.obtainMessage(2818048, obtain).sendToTarget();
        }
    }

    public final void hideRecentApps(boolean z, boolean z2) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(917504);
            this.mHandler.obtainMessage(917504, z ? 1 : 0, z2 ? 1 : 0, null).sendToTarget();
        }
    }

    public final void hideToast(String str, IBinder iBinder) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = iBinder;
            this.mHandler.obtainMessage(3473408, obtain).sendToTarget();
        }
    }

    public final void notifyRequestedGameToolsWin(boolean z) {
        this.mHandler.obtainMessage(7995392, Boolean.valueOf(z)).sendToTarget();
    }

    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(8126464, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
        }
    }

    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg1 = Boolean.valueOf(z);
            obtain.arg2 = rect;
            this.mHandler.obtainMessage(8192000, obtain).sendToTarget();
        }
    }

    public final void onBiometricAuthenticated(int i) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            this.mHandler.obtainMessage(2621440, obtain).sendToTarget();
        }
    }

    public final void onBiometricError(int i, int i2, int i3) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            this.mHandler.obtainMessage(2752512, obtain).sendToTarget();
        }
    }

    public final void onBiometricHelp(int i, String str) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg1 = str;
            this.mHandler.obtainMessage(2686976, obtain).sendToTarget();
        }
    }

    public final void onCameraLaunchGestureDetected(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1572864);
            this.mHandler.obtainMessage(1572864, i, 0).sendToTarget();
        }
    }

    public final void onDisplayReady(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(458752, i, 0).sendToTarget();
        }
    }

    public final void onEmergencyActionLaunchGestureDetected() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(3801088);
            this.mHandler.obtainMessage(3801088).sendToTarget();
        }
    }

    public final void onFlashlightKeyPressed(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(4849664);
            this.mHandler.obtainMessage(4849664, i, 0, null).sendToTarget();
        }
    }

    public final void onFocusedDisplayChanged(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(6684672);
            this.mHandler.obtainMessage(6684672, i, 0, null).sendToTarget();
        }
    }

    public final void onProposedRotationChanged(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2490368);
            this.mHandler.obtainMessage(2490368, i, z ? 1 : 0, null).sendToTarget();
        }
    }

    public final void onRecentsAnimationStateChanged(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3080192, z ? 1 : 0, 0).sendToTarget();
        }
    }

    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = z ? 1 : 0;
            obtain.arg1 = appearanceRegionArr;
            obtain.argi4 = i3;
            obtain.argi5 = i4;
            obtain.arg3 = str;
            obtain.arg4 = letterboxDetailsArr;
            this.mHandler.obtainMessage(393216, obtain).sendToTarget();
        }
    }

    public final boolean panelsEnabled() {
        boolean z;
        SecPanelBlockExpandingHelper secPanelBlockExpandingHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);
        boolean isBlockedByKnoxPanel = secPanelBlockExpandingHelper.isBlockedByKnoxPanel();
        boolean isSysUiSafeModeEnabled = SafeUIState.isSysUiSafeModeEnabled();
        if (isSysUiSafeModeEnabled) {
            Log.d("SecPanelBlockExpandingHelper", "SafeUIState.isSysUiSafeModeEnabled() is true");
        }
        boolean z2 = ((KeyguardStateControllerImpl) secPanelBlockExpandingHelper.mKeyguardStateController).mKeyguardGoingAway;
        if (z2) {
            Log.d("SecPanelBlockExpandingHelper", "SecPanelLog PANEL_TOUCH_BLOCK_EXPAND_WHEN_KEYGUARD_GOING_AWAY isKeyguardGoingAway is true");
        }
        if (isBlockedByKnoxPanel || isSysUiSafeModeEnabled || z2) {
            SecPanelLogger secPanelLogger = (SecPanelLogger) Dependency.get(SecPanelLogger.class);
            StringBuilder sb = new StringBuilder("QpRune.PANEL_BLOCK_EXPANDING panelsEnabled == false");
            sb.append(", Knox:");
            sb.append(isBlockedByKnoxPanel);
            sb.append(", SafeMode:");
            sb.append(isSysUiSafeModeEnabled);
            sb.append(", Unlock:");
            sb.append(z2);
            SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) secPanelLogger;
            secPanelLoggerImpl.appendStatusBarState(sb, " | ");
            String sb2 = sb.toString();
            Log.d("SecPanelLogger", sb2);
            secPanelLoggerImpl.writer.logPanel("TOUCH", sb2);
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        this.mDisplayTracker.getClass();
        int intValue = ((Integer) getDisabled(0).first).intValue();
        this.mDisplayTracker.getClass();
        return (intValue & 65536) == 0 && (((Integer) getDisabled(0).second).intValue() & 4) == 0;
    }

    public final void passThroughShellCommand(final String[] strArr, final ParcelFileDescriptor parcelFileDescriptor) {
        final PrintWriter printWriter = new PrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        new Thread("Sysui.passThroughShellCommand") { // from class: com.android.systemui.statusbar.CommandQueue.2
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    CommandRegistry commandRegistry = CommandQueue.this.mRegistry;
                    if (commandRegistry == null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception unused) {
                        }
                    } else {
                        commandRegistry.onShellCommand(printWriter, strArr);
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception unused2) {
                        }
                    }
                } finally {
                    printWriter.flush();
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused3) {
                    }
                }
            }
        }.start();
    }

    public final void preloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(655360);
            this.mHandler.obtainMessage(655360, 0, 0, null).sendToTarget();
        }
    }

    public final void recomputeDisableFlags(int i, boolean z) {
        synchronized (this.mLock) {
            disable(i, ((Integer) getDisabled(i).first).intValue(), ((Integer) getDisabled(i).second).intValue(), z);
        }
    }

    public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mHandler.obtainMessage(4325376, iNearbyMediaDevicesProvider).sendToTarget();
    }

    public final void remQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1835008, componentName).sendToTarget();
        }
    }

    public final void removeIcon(String str) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 2, 0, str).sendToTarget();
        }
    }

    public final void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = componentName;
        obtain.arg2 = charSequence;
        obtain.arg3 = charSequence2;
        obtain.arg4 = icon;
        obtain.arg5 = iAddTileResultCallback;
        this.mHandler.obtainMessage(3997696, obtain).sendToTarget();
    }

    public final void requestTileServiceListeningState(ComponentName componentName) {
        this.mHandler.obtainMessage(4456448, componentName).sendToTarget();
    }

    public final void requestWindowMagnificationConnection(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3670016, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void resetScheduleAutoHide() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(8060928).sendToTarget();
        }
    }

    public final void runGcForTest() {
        GcUtils.runGcAndFinalizersSync();
    }

    public final void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(6619136, 0, 0, keyEvent).sendToTarget();
        }
    }

    public final void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(8716288, 0, 0, keyEvent).sendToTarget();
        }
    }

    public final void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(4128768, iBiometricContextListener).sendToTarget();
        }
    }

    public final void setBlueLightFilter(boolean z, int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(6750208);
            this.mHandler.obtainMessage(6750208, z ? 1 : 0, i, null).sendToTarget();
        }
    }

    public final void setIcon(String str, StatusBarIcon statusBarIcon) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 1, 0, new Pair(str, statusBarIcon)).sendToTarget();
        }
    }

    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(524288);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            obtain.argi4 = z ? 1 : 0;
            obtain.arg1 = iBinder;
            this.mHandler.obtainMessage(524288, obtain).sendToTarget();
        }
    }

    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3866624, i, z ? 1 : 0).sendToTarget();
        }
    }

    public final void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = remoteViews;
            obtain.argi1 = i;
            obtain.argi2 = i2;
            this.mHandler.obtainMessage(7929856, obtain).sendToTarget();
        }
    }

    public final void setTopAppHidesStatusBar(boolean z) {
        this.mHandler.removeMessages(2424832);
        this.mHandler.obtainMessage(2424832, z ? 1 : 0, 0).sendToTarget();
    }

    public final void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3932160, iUdfpsRefreshRateRequestCallback).sendToTarget();
        }
    }

    public final void setWindowState(int i, int i2, int i3) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(786432, i, i2, Integer.valueOf(i3)).sendToTarget();
        }
    }

    public final void showAssistDisclosure() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1441792);
            this.mHandler.obtainMessage(1441792).sendToTarget();
        }
    }

    public final void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = promptInfo;
            obtain.arg2 = iBiometricSysuiReceiver;
            obtain.arg3 = iArr;
            obtain.arg4 = Boolean.valueOf(z);
            obtain.arg5 = Boolean.valueOf(z2);
            obtain.argi1 = i;
            obtain.arg6 = str;
            obtain.argl1 = j;
            obtain.argl2 = j2;
            this.mHandler.obtainMessage(2555904, obtain).sendToTarget();
        }
    }

    public final void showGlobalActionsMenu(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2228224);
            this.mHandler.obtainMessage(2228224, i, 0).sendToTarget();
        }
    }

    public final void showInattentiveSleepWarning() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3276800).sendToTarget();
        }
    }

    public final void showMediaOutputSwitcher(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            throw new SecurityException("Call only allowed from system server.");
        }
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            this.mHandler.obtainMessage(4718592, obtain).sendToTarget();
        }
    }

    public final void showPictureInPictureMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1703936);
            this.mHandler.obtainMessage(1703936).sendToTarget();
        }
    }

    public final void showPinningEnterExitToast(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2949120, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void showPinningEscapeToast() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3014656).sendToTarget();
        }
    }

    public final void showRearDisplayDialog(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(4521984, Integer.valueOf(i)).sendToTarget();
        }
    }

    public final void showRecentApps(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(851968);
            this.mHandler.obtainMessage(851968, z ? 1 : 0, 0, null).sendToTarget();
        }
    }

    public final void showScreenPinningRequest(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1179648, i, 0, null).sendToTarget();
        }
    }

    public final void showShutdownUi(boolean z, String str) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2359296);
            this.mHandler.obtainMessage(2359296, z ? 1 : 0, 0, str).sendToTarget();
        }
    }

    public final void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = iBinder;
            obtain.arg3 = charSequence;
            obtain.arg4 = iBinder2;
            obtain.arg5 = iTransientNotificationCallback;
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            this.mHandler.obtainMessage(3407872, obtain).sendToTarget();
        }
    }

    public final void showTransient(int i, int i2, boolean z) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = z ? 1 : 0;
            this.mHandler.obtainMessage(3145728, obtain).sendToTarget();
        }
    }

    public final void showWirelessChargingAnimation(int i) {
        this.mHandler.removeMessages(2883584);
        this.mHandler.obtainMessage(2883584, i, 0).sendToTarget();
    }

    public final void startAssist(Bundle bundle) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1507328);
            this.mHandler.obtainMessage(1507328, bundle).sendToTarget();
        }
    }

    public final void startSearcleByHomeKey(boolean z, boolean z2) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(9175040);
            SomeArgs obtain = SomeArgs.obtain();
            int i = 1;
            obtain.argi1 = z ? 1 : 0;
            if (!z2) {
                i = 0;
            }
            obtain.argi2 = i;
            this.mHandler.obtainMessage(9175040, obtain).sendToTarget();
        }
    }

    public final void startTracing() {
        synchronized (this.mLock) {
            ProtoTracer protoTracer = this.mProtoTracer;
            if (protoTracer != null) {
                FrameProtoTracer frameProtoTracer = protoTracer.mProtoTracer;
                synchronized (frameProtoTracer.mLock) {
                    if (!frameProtoTracer.mEnabled) {
                        frameProtoTracer.mBuffer.resetBuffer();
                        frameProtoTracer.mEnabled = true;
                        frameProtoTracer.logState();
                    }
                }
            }
            this.mHandler.obtainMessage(3538944, Boolean.TRUE).sendToTarget();
        }
    }

    public final void stopTracing() {
        synchronized (this.mLock) {
            ProtoTracer protoTracer = this.mProtoTracer;
            if (protoTracer != null) {
                protoTracer.stop();
            }
            this.mHandler.obtainMessage(3538944, Boolean.FALSE).sendToTarget();
        }
    }

    public final void suppressAmbientDisplay(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3604480, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void toggleKeyboardShortcutsMenu(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1638400);
            this.mHandler.obtainMessage(1638400, i, 0).sendToTarget();
        }
    }

    public final void togglePanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2293760);
            this.mHandler.obtainMessage(2293760, 0, 0).sendToTarget();
        }
    }

    public final void toggleRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(589824);
            Message obtainMessage = this.mHandler.obtainMessage(589824, 0, 0, null);
            obtainMessage.setAsynchronous(true);
            obtainMessage.sendToTarget();
        }
    }

    public final void toggleSplitScreen() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1966080);
            this.mHandler.obtainMessage(1966080, 0, 0, null).sendToTarget();
        }
    }

    public final void toggleTaskbar() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(4784128);
            this.mHandler.obtainMessage(4784128, 0, 0, null).sendToTarget();
        }
    }

    public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mHandler.obtainMessage(4390912, iNearbyMediaDevicesProvider).sendToTarget();
    }

    public final void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = mediaRoute2Info;
        obtain.arg3 = icon;
        obtain.arg4 = charSequence;
        this.mHandler.obtainMessage(4259840, obtain).sendToTarget();
    }

    public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = mediaRoute2Info;
        obtain.arg3 = iUndoMediaTransferCallback;
        this.mHandler.obtainMessage(QuickStepContract.SYSUI_STATE_BACK_DISABLED, obtain).sendToTarget();
    }

    public CommandQueue(Context context, DisplayTracker displayTracker, ProtoTracer protoTracer, CommandRegistry commandRegistry, DumpHandler dumpHandler) {
        this.mLock = new Object();
        this.mCallbacks = new ArrayList();
        HandlerC2505H handlerC2505H = new HandlerC2505H(this, Looper.getMainLooper(), 0);
        this.mHandler = handlerC2505H;
        SparseArray sparseArray = new SparseArray();
        this.mDisplayDisabled = sparseArray;
        this.mLastUpdatedImeDisplayId = -1;
        this.mDisplayTracker = displayTracker;
        this.mProtoTracer = protoTracer;
        this.mRegistry = commandRegistry;
        this.mDumpHandler = dumpHandler;
        ((DisplayTrackerImpl) displayTracker).addDisplayChangeCallback(new DisplayTracker.Callback() { // from class: com.android.systemui.statusbar.CommandQueue.1
            @Override // com.android.systemui.settings.DisplayTracker.Callback
            public final void onDisplayRemoved(int i) {
                synchronized (CommandQueue.this.mLock) {
                    CommandQueue.this.mDisplayDisabled.remove(i);
                }
                for (int size = CommandQueue.this.mCallbacks.size() - 1; size >= 0; size--) {
                    ((Callbacks) CommandQueue.this.mCallbacks.get(size)).onDisplayRemoved(i);
                }
            }
        }, new HandlerExecutor(handlerC2505H));
        displayTracker.getClass();
        sparseArray.put(0, new Pair(0, 0));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
        for (int i = 0; i < this.mDisplayDisabled.size(); i++) {
            int keyAt = this.mDisplayDisabled.keyAt(i);
            callbacks.disable(keyAt, ((Integer) getDisabled(keyAt).first).intValue(), ((Integer) getDisabled(keyAt).second).intValue(), false);
        }
    }

    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = z ? 1 : 0;
            obtain.arg1 = Long.valueOf(j);
            obtain.arg2 = Long.valueOf(j2);
            this.mHandler.obtainMessage(1376256, obtain).sendToTarget();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Callbacks callbacks) {
        this.mCallbacks.remove(callbacks);
    }

    public final void animateCollapsePanels(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, i, z ? 1 : 0).sendToTarget();
        }
    }

    public final void disable(int i, int i2, int i3) {
        disable(i, i2, i3, true);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callbacks {
        default void addQsTile(ComponentName componentName) {
        }

        default void animateExpandSettingsPanel(String str) {
        }

        default void appTransitionCancelled(int i) {
        }

        default void appTransitionFinished(int i) {
        }

        default void cancelRequestAddTile(String str) {
        }

        default void clickTile(ComponentName componentName) {
        }

        default void dismissInattentiveSleepWarning(boolean z) {
        }

        default void enterStageSplitFromRunningApp(boolean z) {
        }

        default void handleShowGlobalActionsMenu(int i) {
        }

        default void handleSystemKey(KeyEvent keyEvent) {
        }

        default void hideAuthenticationDialog(long j) {
        }

        default void notifyRequestedGameToolsWin(boolean z) {
        }

        default void onBiometricAuthenticated(int i) {
        }

        default void onCameraLaunchGestureDetected(int i) {
        }

        default void onDisplayReady(int i) {
        }

        default void onDisplayRemoved(int i) {
        }

        default void onFlashlightKeyPressed(int i) {
        }

        default void onRecentsAnimationStateChanged(boolean z) {
        }

        default void onTracingStateChanged(boolean z) {
        }

        default void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        }

        default void remQsTile(ComponentName componentName) {
        }

        default void removeIcon(String str) {
        }

        default void requestTileServiceListeningState(ComponentName componentName) {
        }

        default void requestWindowMagnificationConnection(boolean z) {
        }

        default void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
        }

        default void setBiometricContextListener(IBiometricContextListener iBiometricContextListener) {
        }

        default void setTopAppHidesStatusBar(boolean z) {
        }

        default void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
        }

        default void showMediaOutputSwitcher(String str) {
        }

        default void showPinningEnterExitToast(boolean z) {
        }

        default void showRearDisplayDialog(int i) {
        }

        default void showRecentApps(boolean z) {
        }

        default void showScreenPinningRequest(int i) {
        }

        default void showWirelessChargingAnimation(int i) {
        }

        default void startAssist(Bundle bundle) {
        }

        default void suppressAmbientDisplay(boolean z) {
        }

        default void toggleKeyboardShortcutsMenu(int i) {
        }

        default void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        }

        default void animateExpandNotificationsPanel() {
        }

        default void cancelPreloadRecentApps() {
        }

        default void dismissKeyboardShortcutsMenu() {
        }

        default void goToFullscreenFromSplit() {
        }

        default void onEmergencyActionLaunchGestureDetected() {
        }

        default void preloadRecentApps() {
        }

        default void resetScheduleAutoHide() {
        }

        default void showAssistDisclosure() {
        }

        default void showInattentiveSleepWarning() {
        }

        default void showPictureInPictureMenu() {
        }

        default void showPinningEscapeToast() {
        }

        default void togglePanel() {
        }

        default void toggleRecentApps() {
        }

        default void toggleSplitScreen() {
        }

        default void toggleTaskbar() {
        }

        default void abortTransient(int i, int i2) {
        }

        default void animateCollapsePanels(int i, boolean z) {
        }

        default void appTransitionPending(int i, boolean z) {
        }

        default void handleShowShutdownUi(String str, boolean z) {
        }

        default void hideRecentApps(boolean z, boolean z2) {
        }

        default void hideToast(String str, IBinder iBinder) {
        }

        default void notifyRequestedSystemKey(boolean z, boolean z2) {
        }

        default void onBiometricHelp(int i, String str) {
        }

        default void onRotationProposal(int i, boolean z) {
        }

        default void setBlueLightFilter(boolean z, int i) {
        }

        default void setIcon(String str, StatusBarIcon statusBarIcon) {
        }

        default void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        }

        default void startSearcleByHomeKey(Boolean bool, Boolean bool2) {
        }

        default void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        }

        default void onBiometricError(int i, int i2, int i3) {
        }

        default void setWindowState(int i, int i2, int i3) {
        }

        default void showTransient(int i, int i2, boolean z) {
        }

        default void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        }

        default void appTransitionStarting(int i, long j, long j2, boolean z) {
        }

        default void disable(int i, int i2, int i3, boolean z) {
        }

        default void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        }

        default void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        }

        default void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        }

        default void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        }

        default void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        }

        default void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        }

        default void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) {
        }
    }
}
