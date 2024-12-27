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
import android.os.UserHandle;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.accessibility.Flags;
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
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.CallbackController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.EnterpriseDeviceManager;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommandQueue extends IStatusBar.Stub implements CallbackController {
    public final ArrayList mCallbacks;
    public final SparseArray mDisplayDisabled;
    public final DisplayTracker mDisplayTracker;
    public final DumpHandler mDumpHandler;
    public final H mHandler;
    public int mLastUpdatedImeDisplayId;
    public final Object mLock;
    public SecPanelTouchBlockHelper mPanelTouchBlockHelper;
    public final Lazy mPowerInteractor;
    public final CommandRegistry mRegistry;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class H extends Handler {
        public /* synthetic */ H(CommandQueue commandQueue, Looper looper, int i) {
            this(looper);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = message.what & (-65536);
            int i2 = 0;
            CommandQueue commandQueue = CommandQueue.this;
            switch (i) {
                case 65536:
                    int i3 = message.arg1;
                    if (i3 == 1) {
                        Pair pair = (Pair) message.obj;
                        while (i2 < commandQueue.mCallbacks.size()) {
                            ((Callbacks) commandQueue.mCallbacks.get(i2)).setIcon((String) pair.first, (StatusBarIcon) pair.second);
                            i2++;
                        }
                        break;
                    } else if (i3 == 2) {
                        while (i2 < commandQueue.mCallbacks.size()) {
                            ((Callbacks) commandQueue.mCallbacks.get(i2)).removeIcon((String) message.obj);
                            i2++;
                        }
                        break;
                    }
                    break;
                case 131072:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    for (int i4 = 0; i4 < commandQueue.mCallbacks.size(); i4++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i4)).disable(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4 != 0);
                    }
                    break;
                case 196608:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).animateExpandNotificationsPanel();
                        i2++;
                    }
                    break;
                case 262144:
                    for (int i5 = 0; i5 < commandQueue.mCallbacks.size(); i5++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i5)).animateCollapsePanels(message.arg1, message.arg2 != 0);
                    }
                    break;
                case EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC /* 327680 */:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).animateExpandSettingsPanel((String) message.obj);
                        i2++;
                    }
                    break;
                case 393216:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    for (int i6 = 0; i6 < commandQueue.mCallbacks.size(); i6++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i6)).onSystemBarAttributesChanged(someArgs2.argi1, someArgs2.argi2, (AppearanceRegion[]) someArgs2.arg1, someArgs2.argi3 == 1, someArgs2.argi4, someArgs2.argi5, (String) someArgs2.arg3, (LetterboxDetails[]) someArgs2.arg4);
                    }
                    someArgs2.recycle();
                    break;
                case 458752:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onDisplayReady(message.arg1);
                        i2++;
                    }
                    break;
                case 524288:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    CommandQueue.m2114$$Nest$mhandleShowImeButton(commandQueue, someArgs3.argi1, (IBinder) someArgs3.arg1, someArgs3.argi2, someArgs3.argi3, someArgs3.argi4 != 0);
                    break;
                case 589824:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleRecentApps();
                        i2++;
                    }
                    break;
                case 655360:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).preloadRecentApps();
                        i2++;
                    }
                    break;
                case 720896:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).cancelPreloadRecentApps();
                        i2++;
                    }
                    break;
                case 786432:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setWindowState(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        i2++;
                    }
                    break;
                case 851968:
                    for (int i7 = 0; i7 < commandQueue.mCallbacks.size(); i7++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i7)).showRecentApps(message.arg1 != 0);
                    }
                    break;
                case 917504:
                    for (int i8 = 0; i8 < commandQueue.mCallbacks.size(); i8++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i8)).hideRecentApps(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 1179648:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showScreenPinningRequest(message.arg1);
                        i2++;
                    }
                    break;
                case 1245184:
                    for (int i9 = 0; i9 < commandQueue.mCallbacks.size(); i9++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i9)).appTransitionPending(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 1310720:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).appTransitionCancelled(message.arg1);
                        i2++;
                    }
                    break;
                case 1376256:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    for (int i10 = 0; i10 < commandQueue.mCallbacks.size(); i10++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i10)).appTransitionStarting(((Long) someArgs4.arg1).longValue(), ((Long) someArgs4.arg2).longValue(), someArgs4.argi2 != 0, someArgs4.argi1);
                    }
                    break;
                case 1441792:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showAssistDisclosure();
                        i2++;
                    }
                    break;
                case 1507328:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).startAssist((Bundle) message.obj);
                        i2++;
                    }
                    break;
                case 1572864:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onCameraLaunchGestureDetected(message.arg1);
                        i2++;
                    }
                    break;
                case 1638400:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleKeyboardShortcutsMenu(message.arg1);
                        i2++;
                    }
                    break;
                case 1703936:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showPictureInPictureMenu();
                        i2++;
                    }
                    break;
                case 1769472:
                    if (Flags.a11yQsShortcut()) {
                        SomeArgs someArgs5 = (SomeArgs) message.obj;
                        while (i2 < commandQueue.mCallbacks.size()) {
                            ((Callbacks) commandQueue.mCallbacks.get(i2)).addQsTileToFrontOrEnd((ComponentName) someArgs5.arg1, ((Boolean) someArgs5.arg2).booleanValue());
                            i2++;
                        }
                        someArgs5.recycle();
                        break;
                    } else {
                        while (i2 < commandQueue.mCallbacks.size()) {
                            ((Callbacks) commandQueue.mCallbacks.get(i2)).addQsTile((ComponentName) message.obj);
                            i2++;
                        }
                        break;
                    }
                case 1835008:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).remQsTile((ComponentName) message.obj);
                        i2++;
                    }
                    break;
                case 1900544:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).clickTile((ComponentName) message.obj);
                        i2++;
                    }
                    break;
                case 1966080:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleSplitScreen();
                        i2++;
                    }
                    break;
                case 2031616:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).appTransitionFinished(message.arg1);
                        i2++;
                    }
                    break;
                case QuickStepContract.SYSUI_STATE_DEVICE_DOZING /* 2097152 */:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).dismissKeyboardShortcutsMenu();
                        i2++;
                    }
                    break;
                case 2162688:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).handleSystemKey((KeyEvent) message.obj);
                        i2++;
                    }
                    break;
                case 2228224:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).handleShowGlobalActionsMenu(message.arg1);
                        i2++;
                    }
                    break;
                case 2293760:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleNotificationsPanel();
                        i2++;
                    }
                    break;
                case 2359296:
                    for (int i11 = 0; i11 < commandQueue.mCallbacks.size(); i11++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i11)).handleShowShutdownUi((String) message.obj, message.arg1 != 0);
                    }
                    break;
                case 2424832:
                    for (int i12 = 0; i12 < commandQueue.mCallbacks.size(); i12++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i12)).setTopAppHidesStatusBar(message.arg1 != 0);
                    }
                    break;
                case 2490368:
                    for (int i13 = 0; i13 < commandQueue.mCallbacks.size(); i13++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i13)).onRotationProposal(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 2555904:
                    commandQueue.mHandler.removeMessages(2752512);
                    commandQueue.mHandler.removeMessages(2686976);
                    commandQueue.mHandler.removeMessages(2621440);
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showAuthenticationDialog((PromptInfo) someArgs6.arg1, (IBiometricSysuiReceiver) someArgs6.arg2, (int[]) someArgs6.arg3, ((Boolean) someArgs6.arg4).booleanValue(), ((Boolean) someArgs6.arg5).booleanValue(), someArgs6.argi1, someArgs6.argl1, (String) someArgs6.arg6, someArgs6.argl2);
                        i2++;
                        commandQueue = commandQueue;
                    }
                    someArgs6.recycle();
                    break;
                case 2621440:
                    SomeArgs someArgs7 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onBiometricAuthenticated(someArgs7.argi1);
                        i2++;
                    }
                    someArgs7.recycle();
                    break;
                case 2686976:
                    SomeArgs someArgs8 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onBiometricHelp(someArgs8.argi1, (String) someArgs8.arg1);
                        i2++;
                    }
                    someArgs8.recycle();
                    break;
                case 2752512:
                    SomeArgs someArgs9 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onBiometricError(someArgs9.argi1, someArgs9.argi2, someArgs9.argi3);
                        i2++;
                    }
                    someArgs9.recycle();
                    break;
                case 2818048:
                    SomeArgs someArgs10 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).hideAuthenticationDialog(someArgs10.argl1);
                        i2++;
                    }
                    someArgs10.recycle();
                    break;
                case 2883584:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showWirelessChargingAnimation(message.arg1);
                        i2++;
                    }
                    break;
                case 2949120:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showPinningEnterExitToast(((Boolean) message.obj).booleanValue());
                        i2++;
                    }
                    break;
                case 3014656:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showPinningEscapeToast();
                        i2++;
                    }
                    break;
                case 3080192:
                    for (int i14 = 0; i14 < commandQueue.mCallbacks.size(); i14++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i14)).onRecentsAnimationStateChanged(message.arg1 > 0);
                    }
                    break;
                case 3145728:
                    SomeArgs someArgs11 = (SomeArgs) message.obj;
                    int i15 = someArgs11.argi1;
                    int i16 = someArgs11.argi2;
                    z = someArgs11.argi3 != 0;
                    someArgs11.recycle();
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showTransient(i15, i16, z);
                        i2++;
                    }
                    break;
                case 3211264:
                    SomeArgs someArgs12 = (SomeArgs) message.obj;
                    int i17 = someArgs12.argi1;
                    int i18 = someArgs12.argi2;
                    someArgs12.recycle();
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).abortTransient(i17, i18);
                        i2++;
                    }
                    break;
                case 3276800:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showInattentiveSleepWarning();
                        i2++;
                    }
                    break;
                case 3342336:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).dismissInattentiveSleepWarning(((Boolean) message.obj).booleanValue());
                        i2++;
                    }
                    break;
                case 3407872:
                    SomeArgs someArgs13 = (SomeArgs) message.obj;
                    String str = (String) someArgs13.arg1;
                    IBinder iBinder = (IBinder) someArgs13.arg2;
                    CharSequence charSequence = (CharSequence) someArgs13.arg3;
                    IBinder iBinder2 = (IBinder) someArgs13.arg4;
                    ITransientNotificationCallback iTransientNotificationCallback = (ITransientNotificationCallback) someArgs13.arg5;
                    int i19 = someArgs13.argi1;
                    int i20 = someArgs13.argi2;
                    int i21 = someArgs13.argi3;
                    Iterator it = commandQueue.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callbacks) it.next()).showToast(i19, str, iBinder, charSequence, iBinder2, i20, iTransientNotificationCallback, i21);
                        i20 = i20;
                        i19 = i19;
                    }
                    break;
                case 3473408:
                    SomeArgs someArgs14 = (SomeArgs) message.obj;
                    String str2 = (String) someArgs14.arg1;
                    IBinder iBinder3 = (IBinder) someArgs14.arg2;
                    Iterator it2 = commandQueue.mCallbacks.iterator();
                    while (it2.hasNext()) {
                        ((Callbacks) it2.next()).hideToast(str2, iBinder3);
                    }
                    break;
                case 3538944:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        Callbacks callbacks = (Callbacks) commandQueue.mCallbacks.get(i2);
                        ((Boolean) message.obj).getClass();
                        callbacks.getClass();
                        i2++;
                    }
                    break;
                case 3604480:
                    Iterator it3 = commandQueue.mCallbacks.iterator();
                    while (it3.hasNext()) {
                        ((Callbacks) it3.next()).suppressAmbientDisplay(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3670016:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).requestMagnificationConnection(((Boolean) message.obj).booleanValue());
                        i2++;
                    }
                    break;
                case 3801088:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onEmergencyActionLaunchGestureDetected();
                        i2++;
                    }
                    break;
                case 3866624:
                    for (int i22 = 0; i22 < commandQueue.mCallbacks.size(); i22++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i22)).setNavigationBarLumaSamplingEnabled(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 3932160:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setUdfpsRefreshRateCallback((IUdfpsRefreshRateRequestCallback) message.obj);
                        i2++;
                    }
                    break;
                case 3997696:
                    SomeArgs someArgs15 = (SomeArgs) message.obj;
                    ComponentName componentName = (ComponentName) someArgs15.arg1;
                    CharSequence charSequence2 = (CharSequence) someArgs15.arg2;
                    CharSequence charSequence3 = (CharSequence) someArgs15.arg3;
                    Icon icon = (Icon) someArgs15.arg4;
                    IAddTileResultCallback iAddTileResultCallback = (IAddTileResultCallback) someArgs15.arg5;
                    int intValue = ((Integer) someArgs15.arg6).intValue();
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).requestAddTile(intValue, componentName, charSequence2, charSequence3, icon, iAddTileResultCallback);
                        i2++;
                    }
                    someArgs15.recycle();
                    break;
                case 4063232:
                    String str3 = (String) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).cancelRequestAddTile(str3);
                        i2++;
                    }
                    break;
                case 4128768:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setBiometricContextListener((IBiometricContextListener) message.obj);
                        i2++;
                    }
                    break;
                case QuickStepContract.SYSUI_STATE_BACK_DISABLED /* 4194304 */:
                    SomeArgs someArgs16 = (SomeArgs) message.obj;
                    int intValue2 = ((Integer) someArgs16.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) someArgs16.arg2;
                    IUndoMediaTransferCallback iUndoMediaTransferCallback = (IUndoMediaTransferCallback) someArgs16.arg3;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).updateMediaTapToTransferSenderDisplay(intValue2, mediaRoute2Info, iUndoMediaTransferCallback);
                        i2++;
                    }
                    someArgs16.recycle();
                    break;
                case 4259840:
                    SomeArgs someArgs17 = (SomeArgs) message.obj;
                    int intValue3 = ((Integer) someArgs17.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) someArgs17.arg2;
                    Icon icon2 = (Icon) someArgs17.arg3;
                    CharSequence charSequence4 = (CharSequence) someArgs17.arg4;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).updateMediaTapToTransferReceiverDisplay(intValue3, mediaRoute2Info2, icon2, charSequence4);
                        i2++;
                    }
                    someArgs17.recycle();
                    break;
                case 4325376:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider = (INearbyMediaDevicesProvider) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
                        i2++;
                    }
                    break;
                case 4390912:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider2 = (INearbyMediaDevicesProvider) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider2);
                        i2++;
                    }
                    break;
                case 4456448:
                    ComponentName componentName2 = (ComponentName) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).requestTileServiceListeningState(componentName2);
                        i2++;
                    }
                    break;
                case 4521984:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showRearDisplayDialog(((Integer) message.obj).intValue());
                        i2++;
                    }
                    break;
                case 4587520:
                    int i23 = ((SomeArgs) message.obj).argi1;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).moveFocusedTaskToFullscreen(i23);
                        i2++;
                    }
                    break;
                case 4653056:
                    SomeArgs someArgs18 = (SomeArgs) message.obj;
                    int i24 = someArgs18.argi1;
                    z = someArgs18.argi2 != 0;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).moveFocusedTaskToStageSplit(i24, z);
                        i2++;
                    }
                    break;
                case 4718592:
                    SomeArgs someArgs19 = (SomeArgs) message.obj;
                    String str4 = (String) someArgs19.arg1;
                    UserHandle userHandle = (UserHandle) someArgs19.arg2;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).showMediaOutputSwitcher(str4, userHandle);
                        i2++;
                    }
                    break;
                case 4784128:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleTaskbar();
                        i2++;
                    }
                    break;
                case 5046272:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).confirmImmersivePrompt();
                        i2++;
                    }
                    break;
                case 5111808:
                    SomeArgs someArgs20 = (SomeArgs) message.obj;
                    int i25 = someArgs20.argi1;
                    z = someArgs20.argi2 != 0;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).immersiveModeChanged(i25, z);
                        i2++;
                    }
                    break;
                case 5177344:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setQsTiles((String[]) message.obj);
                        i2++;
                    }
                    break;
                case 5242880:
                    int i26 = ((SomeArgs) message.obj).argi1;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).moveFocusedTaskToDesktop(i26);
                        i2++;
                    }
                    break;
                case 5308416:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setSplitscreenFocus(((Boolean) message.obj).booleanValue());
                        i2++;
                    }
                    break;
                case 5373952:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).toggleQuickSettingsPanel();
                        i2++;
                    }
                    break;
                case 6619136:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        Callbacks callbacks2 = (Callbacks) commandQueue.mCallbacks.get(i2);
                        callbacks2.getClass();
                        i2++;
                    }
                    break;
                case 6750208:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).getClass();
                        i2++;
                    }
                    break;
                case 6815744:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).onFlashlightKeyPressed(message.arg1);
                        i2++;
                    }
                    break;
                case 7929856:
                    SomeArgs someArgs21 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).setNavigationBarShortcut((String) someArgs21.arg1, (RemoteViews) someArgs21.arg2, someArgs21.argi1, someArgs21.argi2);
                        i2++;
                    }
                    someArgs21.recycle();
                    break;
                case 7995392:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).notifyRequestedGameToolsWin(((Boolean) message.obj).booleanValue());
                        i2++;
                    }
                    break;
                case 8060928:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).resetScheduleAutoHide();
                        i2++;
                    }
                    break;
                case 8126464:
                    for (int i27 = 0; i27 < commandQueue.mCallbacks.size(); i27++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i27)).notifyRequestedSystemKey(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 8192000:
                    SomeArgs someArgs22 = (SomeArgs) message.obj;
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).notifySamsungPayInfo(someArgs22.argi1, ((Boolean) someArgs22.arg1).booleanValue(), (Rect) someArgs22.arg2);
                        i2++;
                    }
                    someArgs22.recycle();
                    break;
                case 8716288:
                    while (i2 < commandQueue.mCallbacks.size()) {
                        ((Callbacks) commandQueue.mCallbacks.get(i2)).sendThreeFingerGestureKeyEvent((KeyEvent) message.obj);
                        i2++;
                    }
                    break;
                case 9175040:
                    SomeArgs someArgs23 = (SomeArgs) message.obj;
                    for (int i28 = 0; i28 < commandQueue.mCallbacks.size(); i28++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i28)).startSearcleByHomeKey(Boolean.valueOf(someArgs23.argi1 != 0), Boolean.valueOf(someArgs23.argi2 != 0));
                    }
                    break;
            }
        }

        private H(Looper looper) {
            super(looper);
        }
    }

    /* renamed from: -$$Nest$mhandleShowImeButton, reason: not valid java name */
    public static void m2114$$Nest$mhandleShowImeButton(CommandQueue commandQueue, int i, IBinder iBinder, int i2, int i3, boolean z) {
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
        if (Flags.a11yQsShortcut()) {
            addQsTileToFrontOrEnd(componentName, false);
            return;
        }
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1769472, componentName).sendToTarget();
        }
    }

    public final void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) {
        if (Flags.a11yQsShortcut()) {
            synchronized (this.mLock) {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = componentName;
                obtain.arg2 = Boolean.valueOf(z);
                this.mHandler.obtainMessage(1769472, obtain).sendToTarget();
            }
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
        appTransitionStarting(j, j2, false, i);
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

    public final void confirmImmersivePrompt() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(5046272).sendToTarget();
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            try {
                this.mDisplayDisabled.put(i, new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
                this.mHandler.removeMessages(131072);
                SomeArgs obtain = SomeArgs.obtain();
                obtain.argi1 = i;
                obtain.argi2 = i2;
                obtain.argi3 = i3;
                obtain.argi4 = z ? 1 : 0;
                Message obtainMessage = this.mHandler.obtainMessage(131072, obtain);
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    this.mHandler.handleMessage(obtainMessage);
                    obtainMessage.recycle();
                } else {
                    obtainMessage.sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
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
                    if (CommandQueue.this.mDumpHandler == null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception unused) {
                        }
                    } else {
                        CommandQueue.this.mDumpHandler.dump(fileDescriptor, new PrintWriter(new OutputStream(this) { // from class: com.android.systemui.statusbar.CommandQueue.3.1
                            @Override // java.io.OutputStream
                            public final void write(int i) {
                            }
                        }), strArr);
                    }
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused2) {
                    }
                }
            }
        }.start();
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

    public final void immersiveModeChanged(int i, boolean z) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = z ? 1 : 0;
            this.mHandler.obtainMessage(5111808, obtain).sendToTarget();
        }
    }

    public final void moveFocusedTaskToDesktop(int i) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        this.mHandler.obtainMessage(5242880, obtain).sendToTarget();
    }

    public final void moveFocusedTaskToFullscreen(int i) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        this.mHandler.obtainMessage(4587520, obtain).sendToTarget();
    }

    public final void moveFocusedTaskToStageSplit(int i, boolean z) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = z ? 1 : 0;
            this.mHandler.obtainMessage(4653056, obtain).sendToTarget();
        }
    }

    public final void notifyRequestedGameToolsWin(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(7995392, Boolean.valueOf(z)).sendToTarget();
        }
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
            try {
                Lazy lazy = this.mPowerInteractor;
                if (lazy != null) {
                    PowerRepository.updateWakefulness$default(((PowerInteractor) lazy.get()).repository, null, null, null, true, 7);
                }
                this.mHandler.removeMessages(1572864);
                this.mHandler.obtainMessage(1572864, i, 0).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
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
            this.mHandler.removeMessages(6815744);
            this.mHandler.obtainMessage(6815744, i, 0, null).sendToTarget();
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
    
        if (r0 != false) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0062 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean panelsEnabled() {
        /*
            r6 = this;
            com.android.systemui.shade.SecPanelTouchBlockHelper r0 = r6.mPanelTouchBlockHelper
            if (r0 != 0) goto L10
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.shade.SecPanelTouchBlockHelper> r1 = com.android.systemui.shade.SecPanelTouchBlockHelper.class
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.shade.SecPanelTouchBlockHelper r0 = (com.android.systemui.shade.SecPanelTouchBlockHelper) r0
            r6.mPanelTouchBlockHelper = r0
        L10:
            com.android.systemui.shade.SecPanelTouchBlockHelper r0 = r6.mPanelTouchBlockHelper
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L63
            kotlin.Lazy r3 = r0.statusBarStateController$delegate
            java.lang.Object r3 = r3.getValue()
            com.android.systemui.plugins.statusbar.StatusBarStateController r3 = (com.android.systemui.plugins.statusbar.StatusBarStateController) r3
            java.lang.String r4 = "SecPanelTouchBlockHelper"
            if (r3 == 0) goto L56
            int r3 = r3.getState()
            if (r3 != 0) goto L56
            boolean r3 = r0.isBlockedByKeyguardAnimating()
            if (r3 != 0) goto L58
            boolean r3 = r0.isBlockedByKnoxPanelExpandDisabled()
            if (r3 != 0) goto L58
            java.util.concurrent.atomic.AtomicInteger r0 = r0.userChangeInProgress
            int r0 = r0.get()
            if (r0 <= 0) goto L3e
            r0 = r1
            goto L3f
        L3e:
            r0 = r2
        L3f:
            if (r0 == 0) goto L46
            java.lang.String r3 = "isBlockedByUserChangeInProgress"
            android.util.Log.d(r4, r3)
        L46:
            if (r0 != 0) goto L58
            boolean r0 = com.android.systemui.util.SafeUIState.isSysUiSafeModeEnabled()
            if (r0 == 0) goto L53
            java.lang.String r3 = "isBlockedBySafeMode"
            android.util.Log.d(r4, r3)
        L53:
            if (r0 == 0) goto L56
            goto L58
        L56:
            r0 = r2
            goto L59
        L58:
            r0 = r1
        L59:
            if (r0 == 0) goto L60
            java.lang.String r3 = "isShadePanelDisabled"
            android.util.Log.d(r4, r3)
        L60:
            if (r0 == 0) goto L63
            return r2
        L63:
            com.android.systemui.settings.DisplayTracker r0 = r6.mDisplayTracker
            r0.getClass()
            android.util.SparseArray r0 = r6.mDisplayDisabled
            java.lang.Object r0 = r0.get(r2)
            android.util.Pair r0 = (android.util.Pair) r0
            if (r0 != 0) goto L84
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)
            r0.<init>(r3, r4)
            android.util.SparseArray r3 = r6.mDisplayDisabled
            r3.put(r2, r0)
        L84:
            java.lang.Object r0 = r0.first
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            com.android.systemui.settings.DisplayTracker r3 = r6.mDisplayTracker
            r3.getClass()
            android.util.SparseArray r3 = r6.mDisplayDisabled
            java.lang.Object r3 = r3.get(r2)
            android.util.Pair r3 = (android.util.Pair) r3
            if (r3 != 0) goto Lad
            android.util.Pair r3 = new android.util.Pair
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            r3.<init>(r4, r5)
            android.util.SparseArray r6 = r6.mDisplayDisabled
            r6.put(r2, r3)
        Lad:
            java.lang.Object r6 = r3.second
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r3 = 65536(0x10000, float:9.18355E-41)
            r0 = r0 & r3
            if (r0 != 0) goto Lbf
            r6 = r6 & 4
            if (r6 != 0) goto Lbf
            goto Lc0
        Lbf:
            r1 = r2
        Lc0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.CommandQueue.panelsEnabled():boolean");
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
            Pair pair = (Pair) this.mDisplayDisabled.get(i);
            if (pair == null) {
                pair = new Pair(0, 0);
                this.mDisplayDisabled.put(i, pair);
            }
            int intValue = ((Integer) pair.first).intValue();
            Pair pair2 = (Pair) this.mDisplayDisabled.get(i);
            if (pair2 == null) {
                pair2 = new Pair(0, 0);
                this.mDisplayDisabled.put(i, pair2);
            }
            disable(i, intValue, ((Integer) pair2.second).intValue(), z);
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

    public final void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = componentName;
        obtain.arg2 = charSequence;
        obtain.arg3 = charSequence2;
        obtain.arg4 = icon;
        obtain.arg5 = iAddTileResultCallback;
        obtain.arg6 = Integer.valueOf(i);
        this.mHandler.obtainMessage(3997696, obtain).sendToTarget();
    }

    public final void requestMagnificationConnection(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3670016, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void requestTileServiceListeningState(ComponentName componentName) {
        this.mHandler.obtainMessage(4456448, componentName).sendToTarget();
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

    public final void setQsTiles(String[] strArr) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(5177344, strArr).sendToTarget();
        }
    }

    public final void setSplitscreenFocus(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(5308416, Boolean.valueOf(z)).sendToTarget();
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

    public final void showMediaOutputSwitcher(String str, UserHandle userHandle) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            throw new SecurityException("Call only allowed from system server.");
        }
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = userHandle;
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
        if (BasicRune.SEARCLE) {
            synchronized (this.mLock) {
                this.mHandler.removeMessages(9175040);
                SomeArgs obtain = SomeArgs.obtain();
                obtain.argi1 = z ? 1 : 0;
                obtain.argi2 = z2 ? 1 : 0;
                this.mHandler.obtainMessage(9175040, obtain).sendToTarget();
            }
        }
    }

    public final void startTracing() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3538944, Boolean.TRUE).sendToTarget();
        }
    }

    public final void stopTracing() {
        synchronized (this.mLock) {
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

    public final void toggleNotificationsPanel() {
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

    public CommandQueue(Context context, DisplayTracker displayTracker, CommandRegistry commandRegistry, DumpHandler dumpHandler, Lazy lazy) {
        this.mLock = new Object();
        this.mCallbacks = new ArrayList();
        H h = new H(this, Looper.getMainLooper(), 0);
        this.mHandler = h;
        this.mDisplayDisabled = new SparseArray();
        this.mLastUpdatedImeDisplayId = -1;
        this.mDisplayTracker = displayTracker;
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
        }, new HandlerExecutor(h));
        displayTracker.getClass();
        this.mDisplayDisabled.put(0, new Pair(0, 0));
        this.mPowerInteractor = lazy;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
        for (int i = 0; i < this.mDisplayDisabled.size(); i++) {
            int keyAt = this.mDisplayDisabled.keyAt(i);
            Pair pair = (Pair) this.mDisplayDisabled.get(keyAt);
            if (pair == null) {
                pair = new Pair(0, 0);
                this.mDisplayDisabled.put(keyAt, pair);
            }
            int intValue = ((Integer) pair.first).intValue();
            Pair pair2 = (Pair) this.mDisplayDisabled.get(keyAt);
            if (pair2 == null) {
                pair2 = new Pair(0, 0);
                this.mDisplayDisabled.put(keyAt, pair2);
            }
            callbacks.disable(keyAt, intValue, ((Integer) pair2.second).intValue(), false);
        }
    }

    public final void appTransitionStarting(long j, long j2, boolean z, int i) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callbacks {
        default void animateExpandNotificationsPanel() {
        }

        default void cancelPreloadRecentApps() {
        }

        default void confirmImmersivePrompt() {
        }

        default void dismissKeyboardShortcutsMenu() {
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

        default void toggleNotificationsPanel() {
        }

        default void toggleQuickSettingsPanel() {
        }

        default void toggleRecentApps() {
        }

        default void toggleSplitScreen() {
        }

        default void toggleTaskbar() {
        }

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

        default void handleShowGlobalActionsMenu(int i) {
        }

        default void handleSystemKey(KeyEvent keyEvent) {
        }

        default void hideAuthenticationDialog(long j) {
        }

        default void moveFocusedTaskToDesktop(int i) {
        }

        default void moveFocusedTaskToFullscreen(int i) {
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

        default void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        }

        default void remQsTile(ComponentName componentName) {
        }

        default void removeIcon(String str) {
        }

        default void requestMagnificationConnection(boolean z) {
        }

        default void requestTileServiceListeningState(ComponentName componentName) {
        }

        default void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
        }

        default void setBiometricContextListener(IBiometricContextListener iBiometricContextListener) {
        }

        default void setQsTiles(String[] strArr) {
        }

        default void setSplitscreenFocus(boolean z) {
        }

        default void setTopAppHidesStatusBar(boolean z) {
        }

        default void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
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

        default void abortTransient(int i, int i2) {
        }

        default void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) {
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

        default void immersiveModeChanged(int i, boolean z) {
        }

        default void moveFocusedTaskToStageSplit(int i, boolean z) {
        }

        default void notifyRequestedSystemKey(boolean z, boolean z2) {
        }

        default void onBiometricHelp(int i, String str) {
        }

        default void onRotationProposal(int i, boolean z) {
        }

        default void setIcon(String str, StatusBarIcon statusBarIcon) {
        }

        default void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        }

        default void showMediaOutputSwitcher(String str, UserHandle userHandle) {
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

        default void appTransitionStarting(long j, long j2, boolean z, int i) {
        }

        default void disable(int i, int i2, int i3, boolean z) {
        }

        default void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        }

        default void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        }

        default void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        }

        default void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        }

        default void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        }

        default void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) {
        }

        default void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        }
    }

    public final void onFocusedDisplayChanged(int i) {
    }
}
