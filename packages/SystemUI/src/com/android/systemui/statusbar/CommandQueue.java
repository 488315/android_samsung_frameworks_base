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
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.RemoteViews;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.DisableStates;
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
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.SafeUIState;
import com.android.wm.shell.shortcut.ShortcutController;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
public class CommandQueue extends IStatusBar.Stub implements CallbackController {
    public final ArrayList mCallbacks;
    public final SparseArray mDisplayDisabled;
    public final DisplayTracker mDisplayTracker;
    public final DisplayTracker.Callback mDisplayTrackerCallback;
    public final DumpHandler mDumpHandler;
    public final H mHandler;
    public int mLastUpdatedImeDisplayId;
    public final Object mLock;
    public SecPanelTouchBlockHelper mPanelTouchBlockHelper;
    public final Lazy mPowerInteractor;
    public final CommandRegistry mRegistry;

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
            int i3 = 0;
            int i4 = 0;
            CommandQueue commandQueue = CommandQueue.this;
            switch (i) {
                case 65536:
                    int i5 = message.arg1;
                    if (i5 == 1) {
                        Pair pair = (Pair) message.obj;
                        for (int i6 = 0; i6 < commandQueue.mCallbacks.size(); i6++) {
                            ((Callbacks) commandQueue.mCallbacks.get(i6)).setIcon((String) pair.first, (StatusBarIcon) pair.second);
                        }
                        break;
                    } else if (i5 == 2) {
                        for (int i7 = 0; i7 < commandQueue.mCallbacks.size(); i7++) {
                            ((Callbacks) commandQueue.mCallbacks.get(i7)).removeIcon((String) message.obj);
                        }
                        break;
                    }
                    break;
                case 131072:
                case 8257536:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    for (int i8 = 0; i8 < commandQueue.mCallbacks.size(); i8++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i8)).disable(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4 != 0);
                    }
                    break;
                case 196608:
                    for (int i9 = 0; i9 < commandQueue.mCallbacks.size(); i9++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i9)).animateExpandNotificationsPanel();
                    }
                    break;
                case 262144:
                    for (int i10 = 0; i10 < commandQueue.mCallbacks.size(); i10++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i10)).animateCollapsePanels(message.arg1, message.arg2 != 0);
                    }
                    break;
                case EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC /* 327680 */:
                    for (int i11 = 0; i11 < commandQueue.mCallbacks.size(); i11++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i11)).animateExpandSettingsPanel((String) message.obj);
                    }
                    break;
                case 393216:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    for (int i12 = 0; i12 < commandQueue.mCallbacks.size(); i12++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i12)).onSystemBarAttributesChanged(someArgs2.argi1, someArgs2.argi2, (AppearanceRegion[]) someArgs2.arg1, someArgs2.argi3 == 1, someArgs2.argi4, someArgs2.argi5, (String) someArgs2.arg3, (LetterboxDetails[]) someArgs2.arg4);
                    }
                    someArgs2.recycle();
                    break;
                case 458752:
                    for (int i13 = 0; i13 < commandQueue.mCallbacks.size(); i13++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i13)).onDisplayAddSystemDecorations(message.arg1);
                    }
                    break;
                case NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME /* 524288 */:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    CommandQueue.m2959$$Nest$mhandleShowImeButton(commandQueue, someArgs3.argi1, someArgs3.argi2, someArgs3.argi3, someArgs3.argi4 != 0);
                    break;
                case 589824:
                    for (int i14 = 0; i14 < commandQueue.mCallbacks.size(); i14++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i14)).toggleRecentApps();
                    }
                    break;
                case 655360:
                    for (int i15 = 0; i15 < commandQueue.mCallbacks.size(); i15++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i15)).preloadRecentApps();
                    }
                    break;
                case 720896:
                    for (int i16 = 0; i16 < commandQueue.mCallbacks.size(); i16++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i16)).cancelPreloadRecentApps();
                    }
                    break;
                case 786432:
                    for (int i17 = 0; i17 < commandQueue.mCallbacks.size(); i17++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i17)).setWindowState(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                    }
                    break;
                case 851968:
                    for (int i18 = 0; i18 < commandQueue.mCallbacks.size(); i18++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i18)).showRecentApps(message.arg1 != 0);
                    }
                    break;
                case 917504:
                    for (int i19 = 0; i19 < commandQueue.mCallbacks.size(); i19++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i19)).hideRecentApps(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 1179648:
                    for (int i20 = 0; i20 < commandQueue.mCallbacks.size(); i20++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i20)).showScreenPinningRequest(message.arg1);
                    }
                    break;
                case 1245184:
                    for (int i21 = 0; i21 < commandQueue.mCallbacks.size(); i21++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i21)).appTransitionPending(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 1310720:
                    for (int i22 = 0; i22 < commandQueue.mCallbacks.size(); i22++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i22)).appTransitionCancelled(message.arg1);
                    }
                    break;
                case 1376256:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    for (int i23 = 0; i23 < commandQueue.mCallbacks.size(); i23++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i23)).appTransitionStarting(someArgs4.argi1, ((Long) someArgs4.arg1).longValue(), ((Long) someArgs4.arg2).longValue(), someArgs4.argi2 != 0);
                    }
                    break;
                case 1441792:
                    for (int i24 = 0; i24 < commandQueue.mCallbacks.size(); i24++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i24)).showAssistDisclosure();
                    }
                    break;
                case 1507328:
                    for (int i25 = 0; i25 < commandQueue.mCallbacks.size(); i25++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i25)).startAssist((Bundle) message.obj);
                    }
                    break;
                case 1572864:
                    for (int i26 = 0; i26 < commandQueue.mCallbacks.size(); i26++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i26)).onCameraLaunchGestureDetected(message.arg1);
                    }
                    break;
                case 1638400:
                    for (int i27 = 0; i27 < commandQueue.mCallbacks.size(); i27++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i27)).toggleKeyboardShortcutsMenu(message.arg1);
                    }
                    break;
                case 1703936:
                    for (int i28 = 0; i28 < commandQueue.mCallbacks.size(); i28++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i28)).showPictureInPictureMenu();
                    }
                    break;
                case 1769472:
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    for (int i29 = 0; i29 < commandQueue.mCallbacks.size(); i29++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i29)).addQsTileToFrontOrEnd((ComponentName) someArgs5.arg1, ((Boolean) someArgs5.arg2).booleanValue());
                    }
                    someArgs5.recycle();
                    break;
                case 1835008:
                    for (int i30 = 0; i30 < commandQueue.mCallbacks.size(); i30++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i30)).remQsTile((ComponentName) message.obj);
                    }
                    break;
                case 1900544:
                    for (int i31 = 0; i31 < commandQueue.mCallbacks.size(); i31++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i31)).clickTile((ComponentName) message.obj);
                    }
                    break;
                case 1966080:
                    for (int i32 = 0; i32 < commandQueue.mCallbacks.size(); i32++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i32)).toggleSplitScreen();
                    }
                    break;
                case 2031616:
                    for (int i33 = 0; i33 < commandQueue.mCallbacks.size(); i33++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i33)).appTransitionFinished(message.arg1);
                    }
                    break;
                case 2097152:
                    for (int i34 = 0; i34 < commandQueue.mCallbacks.size(); i34++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i34)).dismissKeyboardShortcutsMenu();
                    }
                    break;
                case 2162688:
                    for (int i35 = 0; i35 < commandQueue.mCallbacks.size(); i35++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i35)).handleSystemKey((KeyEvent) message.obj);
                    }
                    break;
                case 2228224:
                    for (int i36 = 0; i36 < commandQueue.mCallbacks.size(); i36++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i36)).handleShowGlobalActionsMenu(message.arg1);
                    }
                    break;
                case 2293760:
                    for (int i37 = 0; i37 < commandQueue.mCallbacks.size(); i37++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i37)).toggleNotificationsPanel();
                    }
                    break;
                case 2359296:
                    for (int i38 = 0; i38 < commandQueue.mCallbacks.size(); i38++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i38)).handleShowShutdownUi((String) message.obj, message.arg1 != 0);
                    }
                    break;
                case 2424832:
                    for (int i39 = 0; i39 < commandQueue.mCallbacks.size(); i39++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i39)).setTopAppHidesStatusBar(message.arg1 != 0);
                    }
                    break;
                case 2490368:
                    for (int i40 = 0; i40 < commandQueue.mCallbacks.size(); i40++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i40)).onRotationProposal(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 2555904:
                    commandQueue.mHandler.removeMessages(2752512);
                    commandQueue.mHandler.removeMessages(2686976);
                    commandQueue.mHandler.removeMessages(2621440);
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    for (int i41 = 0; i41 < commandQueue.mCallbacks.size(); i41++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i41)).showAuthenticationDialog((PromptInfo) someArgs6.arg1, (IBiometricSysuiReceiver) someArgs6.arg2, (int[]) someArgs6.arg3, ((Boolean) someArgs6.arg4).booleanValue(), ((Boolean) someArgs6.arg5).booleanValue(), someArgs6.argi1, someArgs6.argl1, (String) someArgs6.arg6, someArgs6.argl2);
                    }
                    someArgs6.recycle();
                    break;
                case 2621440:
                    SomeArgs someArgs7 = (SomeArgs) message.obj;
                    for (int i42 = 0; i42 < commandQueue.mCallbacks.size(); i42++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i42)).onBiometricAuthenticated(someArgs7.argi1);
                    }
                    someArgs7.recycle();
                    break;
                case 2686976:
                    SomeArgs someArgs8 = (SomeArgs) message.obj;
                    for (int i43 = 0; i43 < commandQueue.mCallbacks.size(); i43++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i43)).onBiometricHelp(someArgs8.argi1, (String) someArgs8.arg1);
                    }
                    someArgs8.recycle();
                    break;
                case 2752512:
                    SomeArgs someArgs9 = (SomeArgs) message.obj;
                    for (int i44 = 0; i44 < commandQueue.mCallbacks.size(); i44++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i44)).onBiometricError(someArgs9.argi1, someArgs9.argi2, someArgs9.argi3);
                    }
                    someArgs9.recycle();
                    break;
                case 2818048:
                    SomeArgs someArgs10 = (SomeArgs) message.obj;
                    for (int i45 = 0; i45 < commandQueue.mCallbacks.size(); i45++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i45)).hideAuthenticationDialog(someArgs10.argl1);
                    }
                    someArgs10.recycle();
                    break;
                case 2883584:
                    for (int i46 = 0; i46 < commandQueue.mCallbacks.size(); i46++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i46)).showWirelessChargingAnimation(message.arg1);
                    }
                    break;
                case 2949120:
                    for (int i47 = 0; i47 < commandQueue.mCallbacks.size(); i47++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i47)).showPinningEnterExitToast(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3014656:
                    for (int i48 = 0; i48 < commandQueue.mCallbacks.size(); i48++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i48)).showPinningEscapeToast();
                    }
                    break;
                case 3080192:
                    for (int i49 = 0; i49 < commandQueue.mCallbacks.size(); i49++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i49)).onRecentsAnimationStateChanged(message.arg1 > 0);
                    }
                    break;
                case 3145728:
                    SomeArgs someArgs11 = (SomeArgs) message.obj;
                    int i50 = someArgs11.argi1;
                    int i51 = someArgs11.argi2;
                    z = someArgs11.argi3 != 0;
                    someArgs11.recycle();
                    for (int i52 = 0; i52 < commandQueue.mCallbacks.size(); i52++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i52)).showTransient(i50, i51, z);
                    }
                    break;
                case 3211264:
                    SomeArgs someArgs12 = (SomeArgs) message.obj;
                    int i53 = someArgs12.argi1;
                    int i54 = someArgs12.argi2;
                    someArgs12.recycle();
                    for (int i55 = 0; i55 < commandQueue.mCallbacks.size(); i55++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i55)).abortTransient(i53, i54);
                    }
                    break;
                case 3276800:
                    for (int i56 = 0; i56 < commandQueue.mCallbacks.size(); i56++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i56)).showInattentiveSleepWarning();
                    }
                    break;
                case 3342336:
                    for (int i57 = 0; i57 < commandQueue.mCallbacks.size(); i57++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i57)).dismissInattentiveSleepWarning(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3407872:
                    SomeArgs someArgs13 = (SomeArgs) message.obj;
                    String str = (String) someArgs13.arg1;
                    IBinder iBinder = (IBinder) someArgs13.arg2;
                    CharSequence charSequence = (CharSequence) someArgs13.arg3;
                    IBinder iBinder2 = (IBinder) someArgs13.arg4;
                    ITransientNotificationCallback iTransientNotificationCallback = (ITransientNotificationCallback) someArgs13.arg5;
                    int i58 = someArgs13.argi1;
                    int i59 = someArgs13.argi2;
                    int i60 = someArgs13.argi3;
                    ArrayList arrayList = commandQueue.mCallbacks;
                    int size = arrayList.size();
                    while (i4 < size) {
                        Object obj = arrayList.get(i4);
                        i4++;
                        ((Callbacks) obj).showToast(i58, str, iBinder, charSequence, iBinder2, i59, iTransientNotificationCallback, i60);
                    }
                    break;
                case 3473408:
                    SomeArgs someArgs14 = (SomeArgs) message.obj;
                    String str2 = (String) someArgs14.arg1;
                    IBinder iBinder3 = (IBinder) someArgs14.arg2;
                    ArrayList arrayList2 = commandQueue.mCallbacks;
                    int size2 = arrayList2.size();
                    while (i3 < size2) {
                        Object obj2 = arrayList2.get(i3);
                        i3++;
                        ((Callbacks) obj2).hideToast(str2, iBinder3);
                    }
                    break;
                case 3538944:
                    for (int i61 = 0; i61 < commandQueue.mCallbacks.size(); i61++) {
                        Callbacks callbacks = (Callbacks) commandQueue.mCallbacks.get(i61);
                        ((Boolean) message.obj).getClass();
                        callbacks.getClass();
                    }
                    break;
                case 3604480:
                    ArrayList arrayList3 = commandQueue.mCallbacks;
                    int size3 = arrayList3.size();
                    while (i2 < size3) {
                        Object obj3 = arrayList3.get(i2);
                        i2++;
                        ((Callbacks) obj3).suppressAmbientDisplay(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3670016:
                    for (int i62 = 0; i62 < commandQueue.mCallbacks.size(); i62++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i62)).requestMagnificationConnection(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 3801088:
                    for (int i63 = 0; i63 < commandQueue.mCallbacks.size(); i63++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i63)).onEmergencyActionLaunchGestureDetected();
                    }
                    break;
                case 3866624:
                    for (int i64 = 0; i64 < commandQueue.mCallbacks.size(); i64++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i64)).setNavigationBarLumaSamplingEnabled(message.arg1, message.arg2 != 0);
                    }
                    break;
                case 3932160:
                    for (int i65 = 0; i65 < commandQueue.mCallbacks.size(); i65++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i65)).setUdfpsRefreshRateCallback((IUdfpsRefreshRateRequestCallback) message.obj);
                    }
                    break;
                case 3997696:
                    SomeArgs someArgs15 = (SomeArgs) message.obj;
                    ComponentName componentName = (ComponentName) someArgs15.arg1;
                    CharSequence charSequence2 = (CharSequence) someArgs15.arg2;
                    CharSequence charSequence3 = (CharSequence) someArgs15.arg3;
                    Icon icon = (Icon) someArgs15.arg4;
                    IAddTileResultCallback iAddTileResultCallback = (IAddTileResultCallback) someArgs15.arg5;
                    int iIntValue = ((Integer) someArgs15.arg6).intValue();
                    for (int i66 = 0; i66 < commandQueue.mCallbacks.size(); i66++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i66)).requestAddTile(iIntValue, componentName, charSequence2, charSequence3, icon, iAddTileResultCallback);
                    }
                    someArgs15.recycle();
                    break;
                case 4063232:
                    String str3 = (String) message.obj;
                    for (int i67 = 0; i67 < commandQueue.mCallbacks.size(); i67++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i67)).cancelRequestAddTile(str3);
                    }
                    break;
                case 4128768:
                    for (int i68 = 0; i68 < commandQueue.mCallbacks.size(); i68++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i68)).setBiometricContextListener((IBiometricContextListener) message.obj);
                    }
                    break;
                case 4194304:
                    SomeArgs someArgs16 = (SomeArgs) message.obj;
                    int iIntValue2 = ((Integer) someArgs16.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) someArgs16.arg2;
                    IUndoMediaTransferCallback iUndoMediaTransferCallback = (IUndoMediaTransferCallback) someArgs16.arg3;
                    for (int i69 = 0; i69 < commandQueue.mCallbacks.size(); i69++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i69)).updateMediaTapToTransferSenderDisplay(iIntValue2, mediaRoute2Info, iUndoMediaTransferCallback);
                    }
                    someArgs16.recycle();
                    break;
                case 4259840:
                    SomeArgs someArgs17 = (SomeArgs) message.obj;
                    int iIntValue3 = ((Integer) someArgs17.arg1).intValue();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) someArgs17.arg2;
                    Icon icon2 = (Icon) someArgs17.arg3;
                    CharSequence charSequence4 = (CharSequence) someArgs17.arg4;
                    for (int i70 = 0; i70 < commandQueue.mCallbacks.size(); i70++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i70)).updateMediaTapToTransferReceiverDisplay(iIntValue3, mediaRoute2Info2, icon2, charSequence4);
                    }
                    someArgs17.recycle();
                    break;
                case 4325376:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider = (INearbyMediaDevicesProvider) message.obj;
                    for (int i71 = 0; i71 < commandQueue.mCallbacks.size(); i71++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i71)).registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
                    }
                    break;
                case 4390912:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProvider2 = (INearbyMediaDevicesProvider) message.obj;
                    for (int i72 = 0; i72 < commandQueue.mCallbacks.size(); i72++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i72)).unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider2);
                    }
                    break;
                case 4456448:
                    ComponentName componentName2 = (ComponentName) message.obj;
                    for (int i73 = 0; i73 < commandQueue.mCallbacks.size(); i73++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i73)).requestTileServiceListeningState(componentName2);
                    }
                    break;
                case 4521984:
                    for (int i74 = 0; i74 < commandQueue.mCallbacks.size(); i74++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i74)).showRearDisplayDialog(((Integer) message.obj).intValue());
                    }
                    break;
                case 4587520:
                    int i75 = ((SomeArgs) message.obj).argi1;
                    for (int i76 = 0; i76 < commandQueue.mCallbacks.size(); i76++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i76)).moveFocusedTaskToFullscreen(i75);
                    }
                    break;
                case 4653056:
                    SomeArgs someArgs18 = (SomeArgs) message.obj;
                    ShortcutController.getInstance().moveFocusedTaskToStageSplit(someArgs18.argi1, someArgs18.argi2 != 0);
                    break;
                case 4718592:
                    SomeArgs someArgs19 = (SomeArgs) message.obj;
                    String str4 = (String) someArgs19.arg1;
                    UserHandle userHandle = (UserHandle) someArgs19.arg2;
                    for (int i77 = 0; i77 < commandQueue.mCallbacks.size(); i77++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i77)).showMediaOutputSwitcher(str4, userHandle);
                    }
                    break;
                case 4784128:
                    for (int i78 = 0; i78 < commandQueue.mCallbacks.size(); i78++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i78)).toggleTaskbar();
                    }
                    break;
                case 5046272:
                    for (int i79 = 0; i79 < commandQueue.mCallbacks.size(); i79++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i79)).confirmImmersivePrompt();
                    }
                    break;
                case 5111808:
                    SomeArgs someArgs20 = (SomeArgs) message.obj;
                    int i80 = someArgs20.argi1;
                    z = someArgs20.argi2 != 0;
                    int i81 = someArgs20.argi3;
                    for (int i82 = 0; i82 < commandQueue.mCallbacks.size(); i82++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i82)).immersiveModeChanged(i80, z, i81);
                    }
                    break;
                case 5177344:
                    for (int i83 = 0; i83 < commandQueue.mCallbacks.size(); i83++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i83)).setQsTiles((String[]) message.obj);
                    }
                    break;
                case 5242880:
                    int i84 = ((SomeArgs) message.obj).argi1;
                    for (int i85 = 0; i85 < commandQueue.mCallbacks.size(); i85++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i85)).moveFocusedTaskToDesktop(i84);
                    }
                    break;
                case 5308416:
                    for (int i86 = 0; i86 < commandQueue.mCallbacks.size(); i86++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i86)).setSplitscreenFocus(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 5373952:
                    for (int i87 = 0; i87 < commandQueue.mCallbacks.size(); i87++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i87)).toggleQuickSettingsPanel();
                    }
                    break;
                case 5439488:
                    for (int i88 = 0; i88 < commandQueue.mCallbacks.size(); i88++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i88)).onWalletLaunchGestureDetected();
                    }
                    break;
                case 5570560:
                    for (int i89 = 0; i89 < commandQueue.mCallbacks.size(); i89++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i89)).onDisplayRemoveSystemDecorations(message.arg1);
                    }
                    break;
                case 5636096:
                    DisableStates disableStates = (DisableStates) message.obj;
                    boolean z2 = disableStates.animate;
                    for (Map.Entry entry : disableStates.displaysWithStates.entrySet()) {
                        int iIntValue4 = ((Integer) entry.getKey()).intValue();
                        Pair pair2 = (Pair) entry.getValue();
                        for (int i90 = 0; i90 < commandQueue.mCallbacks.size(); i90++) {
                            ((Callbacks) commandQueue.mCallbacks.get(i90)).disable(iIntValue4, ((Integer) pair2.first).intValue(), ((Integer) pair2.second).intValue(), z2);
                        }
                    }
                    break;
                case 6619136:
                    for (int i91 = 0; i91 < commandQueue.mCallbacks.size(); i91++) {
                        Callbacks callbacks2 = (Callbacks) commandQueue.mCallbacks.get(i91);
                        callbacks2.getClass();
                    }
                    break;
                case 6750208:
                    for (int i92 = 0; i92 < commandQueue.mCallbacks.size(); i92++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i92)).getClass();
                    }
                    break;
                case 6815744:
                    for (int i93 = 0; i93 < commandQueue.mCallbacks.size(); i93++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i93)).onFlashlightKeyPressed(message.arg1);
                    }
                    break;
                case 7929856:
                    SomeArgs someArgs21 = (SomeArgs) message.obj;
                    for (int i94 = 0; i94 < commandQueue.mCallbacks.size(); i94++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i94)).setNavigationBarShortcut((String) someArgs21.arg1, (RemoteViews) someArgs21.arg2, someArgs21.argi1, someArgs21.argi2);
                    }
                    someArgs21.recycle();
                    break;
                case 7995392:
                    for (int i95 = 0; i95 < commandQueue.mCallbacks.size(); i95++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i95)).notifyRequestedGameToolsWin(((Boolean) message.obj).booleanValue());
                    }
                    break;
                case 8060928:
                    for (int i96 = 0; i96 < commandQueue.mCallbacks.size(); i96++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i96)).resetScheduleAutoHide();
                    }
                    break;
                case 8126464:
                    for (int i97 = 0; i97 < commandQueue.mCallbacks.size(); i97++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i97)).notifyRequestedSystemKey(message.arg1 != 0, message.arg2 != 0);
                    }
                    break;
                case 8192000:
                    SomeArgs someArgs22 = (SomeArgs) message.obj;
                    for (int i98 = 0; i98 < commandQueue.mCallbacks.size(); i98++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i98)).notifySamsungPayInfo(someArgs22.argi1, ((Boolean) someArgs22.arg1).booleanValue(), (Rect) someArgs22.arg2);
                    }
                    someArgs22.recycle();
                    break;
                case 8716288:
                    for (int i99 = 0; i99 < commandQueue.mCallbacks.size(); i99++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i99)).sendThreeFingerGestureKeyEvent((KeyEvent) message.obj);
                    }
                    break;
                case 9175040:
                    SomeArgs someArgs23 = (SomeArgs) message.obj;
                    for (int i100 = 0; i100 < commandQueue.mCallbacks.size(); i100++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i100)).startSearcleByHomeKey(Boolean.valueOf(someArgs23.argi1 != 0), Boolean.valueOf(someArgs23.argi2 != 0));
                    }
                    break;
                case 9830400:
                    for (int i101 = 0; i101 < commandQueue.mCallbacks.size(); i101++) {
                        ((Callbacks) commandQueue.mCallbacks.get(i101)).notifyPenState(message.arg1);
                    }
                    break;
            }
        }

        private H(Looper looper) {
            super(looper);
        }
    }

    /* renamed from: -$$Nest$mhandleShowImeButton, reason: not valid java name */
    public static void m2959$$Nest$mhandleShowImeButton(CommandQueue commandQueue, int i, int i2, int i3, boolean z) {
        if (i == -1) {
            commandQueue.getClass();
            return;
        }
        int i4 = commandQueue.mLastUpdatedImeDisplayId;
        if (i4 != i && i4 != -1) {
            for (int i5 = 0; i5 < commandQueue.mCallbacks.size(); i5++) {
                ((Callbacks) commandQueue.mCallbacks.get(i5)).setImeWindowStatus(commandQueue.mLastUpdatedImeDisplayId, 0, 0, false);
            }
        }
        for (int i6 = 0; i6 < commandQueue.mCallbacks.size(); i6++) {
            ((Callbacks) commandQueue.mCallbacks.get(i6)).setImeWindowStatus(i, i2, i3, z);
        }
        commandQueue.mLastUpdatedImeDisplayId = i;
    }

    public CommandQueue(Context context, DisplayTracker displayTracker) {
        this(context, displayTracker, null, null, null);
    }

    public final void abortTransient(int i, int i2) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            this.mHandler.obtainMessage(3211264, someArgsObtain).sendToTarget();
        }
    }

    public final void addQsTile(ComponentName componentName) {
        addQsTileToFrontOrEnd(componentName, false);
    }

    public final void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = componentName;
            someArgsObtain.arg2 = Boolean.valueOf(z);
            this.mHandler.obtainMessage(1769472, someArgsObtain).sendToTarget();
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

    public final void confirmImmersivePrompt() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(5046272).sendToTarget();
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            try {
                this.mDisplayDisabled.put(i, new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
                int i4 = (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && i == 1) ? 8257536 : 131072;
                this.mHandler.removeMessages(i4);
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.argi1 = i;
                someArgsObtain.argi2 = i2;
                someArgsObtain.argi3 = i3;
                someArgsObtain.argi4 = z ? 1 : 0;
                Message messageObtainMessage = this.mHandler.obtainMessage(i4, someArgsObtain);
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    this.mHandler.handleMessage(messageObtainMessage);
                    messageObtainMessage.recycle();
                } else {
                    messageObtainMessage.sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void disableForAllDisplays(DisableStates disableStates) {
        synchronized (this.mLock) {
            try {
                for (Map.Entry entry : disableStates.displaysWithStates.entrySet()) {
                    int iIntValue = ((Integer) entry.getKey()).intValue();
                    Pair pair = (Pair) entry.getValue();
                    Integer num = (Integer) pair.first;
                    num.getClass();
                    Integer num2 = (Integer) pair.second;
                    num2.getClass();
                    this.mDisplayDisabled.put(iIntValue, new Pair(num, num2));
                }
                this.mHandler.removeMessages(5636096);
                Message messageObtainMessage = this.mHandler.obtainMessage(5636096, disableStates);
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    this.mHandler.handleMessage(messageObtainMessage);
                    messageObtainMessage.recycle();
                } else {
                    messageObtainMessage.sendToTarget();
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
            this.mHandler.removeMessages(2097152);
            this.mHandler.obtainMessage(2097152).sendToTarget();
        }
    }

    public final void dumpProto(final String[] strArr, final ParcelFileDescriptor parcelFileDescriptor) {
        final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        new Thread("Sysui.dumpProto") { // from class: com.android.systemui.statusbar.CommandQueue.3
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() throws IOException {
                try {
                    if (CommandQueue.this.mDumpHandler != null) {
                        CommandQueue.this.mDumpHandler.dump(fileDescriptor, new PrintWriter(new OutputStream(this) { // from class: com.android.systemui.statusbar.CommandQueue.3.1
                            @Override // java.io.OutputStream
                            public final void write(int i) {
                            }
                        }), strArr);
                    }
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused) {
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argl1 = j;
            this.mHandler.obtainMessage(2818048, someArgsObtain).sendToTarget();
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = iBinder;
            this.mHandler.obtainMessage(3473408, someArgsObtain).sendToTarget();
        }
    }

    public final void immersiveModeChanged(int i, boolean z, int i2) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = z ? 1 : 0;
            someArgsObtain.argi3 = i2;
            this.mHandler.obtainMessage(5111808, someArgsObtain).sendToTarget();
        }
    }

    public final void moveFocusedTaskToDesktop(int i) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i;
        this.mHandler.obtainMessage(5242880, someArgsObtain).sendToTarget();
    }

    public final void moveFocusedTaskToFullscreen(int i) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i;
        this.mHandler.obtainMessage(4587520, someArgsObtain).sendToTarget();
    }

    public final void moveFocusedTaskToStageSplit(int i, boolean z) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = z ? 1 : 0;
            this.mHandler.obtainMessage(4653056, someArgsObtain).sendToTarget();
        }
    }

    public final void notifyPenState(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(9830400);
            this.mHandler.obtainMessage(9830400, i, 0, null).sendToTarget();
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.arg1 = Boolean.valueOf(z);
            someArgsObtain.arg2 = rect;
            this.mHandler.obtainMessage(8192000, someArgsObtain).sendToTarget();
        }
    }

    public final void onBiometricAuthenticated(int i) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            this.mHandler.obtainMessage(2621440, someArgsObtain).sendToTarget();
        }
    }

    public final void onBiometricError(int i, int i2, int i3) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            someArgsObtain.argi3 = i3;
            this.mHandler.obtainMessage(2752512, someArgsObtain).sendToTarget();
        }
    }

    public final void onBiometricHelp(int i, String str) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.arg1 = str;
            this.mHandler.obtainMessage(2686976, someArgsObtain).sendToTarget();
        }
    }

    public final void onCameraLaunchGestureDetected(int i) {
        synchronized (this.mLock) {
            try {
                Lazy lazy = this.mPowerInteractor;
                if (lazy != null) {
                    PowerInteractor powerInteractor = (PowerInteractor) lazy.get();
                    if (!powerInteractor.isPowerButtonGestureSuppressed()) {
                        PowerRepository.updateWakefulness$default(powerInteractor.repository, null, null, null, true, 7);
                    }
                }
                this.mHandler.removeMessages(1572864);
                this.mHandler.obtainMessage(1572864, i, 0).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDisplayAddSystemDecorations(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(458752, i, 0).sendToTarget();
        }
    }

    public final void onDisplayRemoveSystemDecorations(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(5570560, i, 0).sendToTarget();
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

    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            someArgsObtain.argi3 = z ? 1 : 0;
            someArgsObtain.arg1 = appearanceRegionArr;
            someArgsObtain.argi4 = i3;
            someArgsObtain.argi5 = i4;
            someArgsObtain.arg3 = str;
            someArgsObtain.arg4 = letterboxDetailsArr;
            this.mHandler.obtainMessage(393216, someArgsObtain).sendToTarget();
        }
    }

    public final void onWalletLaunchGestureDetected() {
        synchronized (this.mLock) {
            try {
                Lazy lazy = this.mPowerInteractor;
                if (lazy != null) {
                    PowerRepository.updateWakefulness$default(((PowerInteractor) lazy.get()).repository, null, null, null, true, 7);
                }
                this.mHandler.removeMessages(5439488);
                this.mHandler.obtainMessage(5439488).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean panelsEnabled() {
        boolean z;
        if (this.mPanelTouchBlockHelper == null) {
            this.mPanelTouchBlockHelper = (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
        }
        SecPanelTouchBlockHelper secPanelTouchBlockHelper = this.mPanelTouchBlockHelper;
        if (secPanelTouchBlockHelper == null) {
            this.mDisplayTracker.getClass();
            Pair pair = (Pair) this.mDisplayDisabled.get(0);
            if (pair == null) {
                pair = new Pair(0, 0);
                this.mDisplayDisabled.put(0, pair);
            }
            int iIntValue = ((Integer) pair.first).intValue();
            this.mDisplayTracker.getClass();
            Pair pair2 = (Pair) this.mDisplayDisabled.get(0);
            if (pair2 == null) {
                pair2 = new Pair(0, 0);
                this.mDisplayDisabled.put(0, pair2);
            }
            int iIntValue2 = ((Integer) pair2.second).intValue();
            if ((iIntValue & 65536) == 0 && (iIntValue2 & 4) == 0) {
                return true;
            }
        } else {
            StatusBarStateController statusBarStateController = (StatusBarStateController) secPanelTouchBlockHelper.statusBarStateController$delegate.getValue();
            if (statusBarStateController == null || statusBarStateController.getState() != 0) {
                z = false;
                if (z) {
                    Log.d("SecPanelTouchBlockHelper", "isShadePanelDisabled");
                }
                if (!z) {
                }
            } else {
                if (!secPanelTouchBlockHelper.isBlockedByKeyguardAnimating() && !secPanelTouchBlockHelper.isBlockedByKnoxPanelExpandDisabled()) {
                    KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) secPanelTouchBlockHelper.knoxStateMonitor$delegate.getValue();
                    boolean z2 = knoxStateMonitor != null && ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden();
                    if (z2) {
                        Log.d("SecPanelTouchBlockHelper", "isBlockedByKnoxStatusBarHidden");
                    }
                    if (!z2) {
                        boolean z3 = secPanelTouchBlockHelper.userChangeInProgress.get();
                        if (z3) {
                            Log.d("SecPanelTouchBlockHelper", "isBlockedByUserChangeInProgress");
                        }
                        if (!z3) {
                            boolean zIsSysUiSafeModeEnabled = SafeUIState.isSysUiSafeModeEnabled();
                            if (zIsSysUiSafeModeEnabled) {
                                Log.d("SecPanelTouchBlockHelper", "isBlockedBySafeMode");
                            }
                            if (zIsSysUiSafeModeEnabled) {
                            }
                            if (z) {
                            }
                            if (!z) {
                            }
                        }
                    }
                }
                z = true;
                if (z) {
                }
                if (!z) {
                }
            }
        }
        return false;
    }

    public final void passThroughShellCommand(final String[] strArr, final ParcelFileDescriptor parcelFileDescriptor) {
        final PrintWriter printWriter = new PrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        new Thread("Sysui.passThroughShellCommand") { // from class: com.android.systemui.statusbar.CommandQueue.2
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() throws IOException {
                try {
                    CommandRegistry commandRegistry = CommandQueue.this.mRegistry;
                    if (commandRegistry != null) {
                        commandRegistry.onShellCommand(printWriter, strArr);
                    }
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused) {
                    }
                } finally {
                    printWriter.flush();
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused2) {
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
            int iIntValue = ((Integer) pair.first).intValue();
            Pair pair2 = (Pair) this.mDisplayDisabled.get(i);
            if (pair2 == null) {
                pair2 = new Pair(0, 0);
                this.mDisplayDisabled.put(i, pair2);
            }
            disable(i, iIntValue, ((Integer) pair2.second).intValue(), z);
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
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = componentName;
        someArgsObtain.arg2 = charSequence;
        someArgsObtain.arg3 = charSequence2;
        someArgsObtain.arg4 = icon;
        someArgsObtain.arg5 = iAddTileResultCallback;
        someArgsObtain.arg6 = Integer.valueOf(i);
        this.mHandler.obtainMessage(3997696, someArgsObtain).sendToTarget();
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

    public final void setImeWindowStatus(int i, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            someArgsObtain.argi3 = i3;
            someArgsObtain.argi4 = z ? 1 : 0;
            this.mHandler.obtainMessage(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME, someArgsObtain).sendToTarget();
        }
    }

    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3866624, i, z ? 1 : 0).sendToTarget();
        }
    }

    public final void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = remoteViews;
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            this.mHandler.obtainMessage(7929856, someArgsObtain).sendToTarget();
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = promptInfo;
            someArgsObtain.arg2 = iBiometricSysuiReceiver;
            someArgsObtain.arg3 = iArr;
            someArgsObtain.arg4 = Boolean.valueOf(z);
            someArgsObtain.arg5 = Boolean.valueOf(z2);
            someArgsObtain.argi1 = i;
            someArgsObtain.arg6 = str;
            someArgsObtain.argl1 = j;
            someArgsObtain.argl2 = j2;
            this.mHandler.obtainMessage(2555904, someArgsObtain).sendToTarget();
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = userHandle;
            this.mHandler.obtainMessage(4718592, someArgsObtain).sendToTarget();
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
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = iBinder;
            someArgsObtain.arg3 = charSequence;
            someArgsObtain.arg4 = iBinder2;
            someArgsObtain.arg5 = iTransientNotificationCallback;
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            someArgsObtain.argi3 = i3;
            this.mHandler.obtainMessage(3407872, someArgsObtain).sendToTarget();
        }
    }

    public final void showTransient(int i, int i2, boolean z) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = i2;
            someArgsObtain.argi3 = z ? 1 : 0;
            this.mHandler.obtainMessage(3145728, someArgsObtain).sendToTarget();
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
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.argi1 = z ? 1 : 0;
                someArgsObtain.argi2 = z2 ? 1 : 0;
                this.mHandler.obtainMessage(9175040, someArgsObtain).sendToTarget();
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
            Message messageObtainMessage = this.mHandler.obtainMessage(589824, 0, 0, null);
            messageObtainMessage.setAsynchronous(true);
            messageObtainMessage.sendToTarget();
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
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = Integer.valueOf(i);
        someArgsObtain.arg2 = mediaRoute2Info;
        someArgsObtain.arg3 = icon;
        someArgsObtain.arg4 = charSequence;
        this.mHandler.obtainMessage(4259840, someArgsObtain).sendToTarget();
    }

    public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = Integer.valueOf(i);
        someArgsObtain.arg2 = mediaRoute2Info;
        someArgsObtain.arg3 = iUndoMediaTransferCallback;
        this.mHandler.obtainMessage(4194304, someArgsObtain).sendToTarget();
    }

    public CommandQueue(Context context, DisplayTracker displayTracker, CommandRegistry commandRegistry, DumpHandler dumpHandler, Lazy lazy) {
        this.mLock = new Object();
        this.mCallbacks = new ArrayList();
        H h = new H(this, Looper.getMainLooper(), 0);
        this.mHandler = h;
        SparseArray sparseArray = new SparseArray();
        this.mDisplayDisabled = sparseArray;
        this.mLastUpdatedImeDisplayId = -1;
        DisplayTracker.Callback callback = new DisplayTracker.Callback() { // from class: com.android.systemui.statusbar.CommandQueue.1
            @Override // com.android.systemui.settings.DisplayTracker.Callback
            public final void onDisplayRemoved(int i) {
                synchronized (CommandQueue.this.mLock) {
                    CommandQueue.this.mDisplayDisabled.remove(i);
                }
                for (int size = CommandQueue.this.mCallbacks.size() - 1; size >= 0; size--) {
                    ((Callbacks) CommandQueue.this.mCallbacks.get(size)).onDisplayRemoved(i);
                }
            }
        };
        this.mDisplayTrackerCallback = callback;
        this.mDisplayTracker = displayTracker;
        this.mRegistry = commandRegistry;
        this.mDumpHandler = dumpHandler;
        ((DisplayTrackerImpl) displayTracker).addDisplayChangeCallback(callback, new HandlerExecutor(h));
        displayTracker.getClass();
        sparseArray.put(0, new Pair(0, 0));
        this.mPowerInteractor = lazy;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
        for (int i = 0; i < this.mDisplayDisabled.size(); i++) {
            int iKeyAt = this.mDisplayDisabled.keyAt(i);
            Pair pair = (Pair) this.mDisplayDisabled.get(iKeyAt);
            if (pair == null) {
                pair = new Pair(0, 0);
                this.mDisplayDisabled.put(iKeyAt, pair);
            }
            int iIntValue = ((Integer) pair.first).intValue();
            Pair pair2 = (Pair) this.mDisplayDisabled.get(iKeyAt);
            if (pair2 == null) {
                pair2 = new Pair(0, 0);
                this.mDisplayDisabled.put(iKeyAt, pair2);
            }
            callbacks.disable(iKeyAt, iIntValue, ((Integer) pair2.second).intValue(), false);
        }
    }

    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            someArgsObtain.argi2 = z ? 1 : 0;
            someArgsObtain.arg1 = Long.valueOf(j);
            someArgsObtain.arg2 = Long.valueOf(j2);
            this.mHandler.obtainMessage(1376256, someArgsObtain).sendToTarget();
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

    public interface Callbacks {
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

        default void notifyPenState(int i) {
        }

        default void notifyRequestedGameToolsWin(boolean z) {
        }

        default void onBiometricAuthenticated(int i) {
        }

        default void onCameraLaunchGestureDetected(int i) {
        }

        default void onDisplayAddSystemDecorations(int i) {
        }

        default void onDisplayRemoveSystemDecorations(int i) {
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

        default void onWalletLaunchGestureDetected() {
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

        default void immersiveModeChanged(int i, boolean z, int i2) {
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

        default void setImeWindowStatus(int i, int i2, int i3, boolean z) {
        }

        default void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        }

        default void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        }

        default void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        }

        default void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) {
        }

        default void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        }

        default void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) {
        }
    }

    public final void onFocusedDisplayChanged(int i) {
    }
}
