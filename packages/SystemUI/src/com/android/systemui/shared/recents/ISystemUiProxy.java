package com.android.systemui.shared.recents;

import android.app.ActivityTaskManager;
import android.hardware.input.InputManagerGlobal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.buttons.KeyButtonView;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.SecFgsManagerController;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda10;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda16;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda27;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda3;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda4;
import com.android.systemui.recents.LauncherProxyService$5$$ExternalSyntheticLambda0;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.wm.shell.back.BackAnimationController;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public interface ISystemUiProxy extends IInterface {

    public abstract class Stub extends Binder implements ISystemUiProxy {
        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.ISystemUiProxy");
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.recents.ISystemUiProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.shared.recents.ISystemUiProxy");
                return true;
            }
            if (i == 2) {
                final int i3 = parcel.readInt();
                final boolean z = parcel.readBoolean();
                final String string = parcel.readString();
                parcel.enforceNoDataAvail();
                final LauncherProxyService.AnonymousClass1 anonymousClass1 = (LauncherProxyService.AnonymousClass1) this;
                anonymousClass1.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        LauncherProxyService.AnonymousClass1 anonymousClass12 = anonymousClass1;
                        LauncherProxyService.this.mSamsungScreenPinningRequest.showPrompt(i3, z, string);
                    }
                }, "startScreenPinning");
                return true;
            }
            if (i == 7) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                LauncherProxyService.AnonymousClass1 anonymousClass12 = (LauncherProxyService.AnonymousClass1) this;
                anonymousClass12.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass12, z2), "onOverviewShown");
                return true;
            }
            if (i == 10) {
                MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                LauncherProxyService.AnonymousClass1 anonymousClass13 = (LauncherProxyService.AnonymousClass1) this;
                Log.d("LauncherProxyService", "onStatusBarTouchEvent: " + motionEvent.getAction());
                final LauncherProxyService$1$$ExternalSyntheticLambda3 launcherProxyService$1$$ExternalSyntheticLambda3 = new LauncherProxyService$1$$ExternalSyntheticLambda3(anonymousClass13, motionEvent, 0);
                anonymousClass13.verifyCallerAndClearCallingIdentity("onStatusBarTouchEvent", new Supplier() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda38
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Runnable runnable = launcherProxyService$1$$ExternalSyntheticLambda3;
                        int i4 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                        runnable.run();
                        return null;
                    }
                });
                return true;
            }
            if (i == 26) {
                final int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                final LauncherProxyService.AnonymousClass1 anonymousClass14 = (LauncherProxyService.AnonymousClass1) this;
                final int i5 = 0;
                anonymousClass14.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                LauncherProxyService.AnonymousClass1 anonymousClass15 = anonymousClass14;
                                int i6 = i4;
                                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onPrioritizedRotation(i6);
                                }
                                break;
                            case 1:
                                AccessibilityManager.getInstance(LauncherProxyService.this.mContext).notifyAccessibilityButtonClicked(i4);
                                break;
                            default:
                                int i7 = i4;
                                int i8 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                long jUptimeMillis = SystemClock.uptimeMillis();
                                KeyEvent keyEvent = new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 4, 0, 0, -1, 0, 72, 257);
                                keyEvent.setDisplayId(i7);
                                InputManagerGlobal.getInstance().injectInputEvent(keyEvent, 0);
                                long jUptimeMillis2 = SystemClock.uptimeMillis();
                                KeyEvent keyEvent2 = new KeyEvent(jUptimeMillis2, jUptimeMillis2, 1, 4, 0, 0, -1, 0, 72, 257);
                                keyEvent2.setDisplayId(i7);
                                InputManagerGlobal.getInstance().injectInputEvent(keyEvent2, 0);
                                break;
                        }
                    }
                }, "notifyPrioritizedRotation");
                return true;
            }
            if (i == 30) {
                LauncherProxyService.AnonymousClass1 anonymousClass15 = (LauncherProxyService.AnonymousClass1) this;
                anonymousClass15.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass15, 2), "expandNotificationPanel");
                return true;
            }
            if (i == 13) {
                final float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                final LauncherProxyService.AnonymousClass1 anonymousClass16 = (LauncherProxyService.AnonymousClass1) this;
                final int i6 = 1;
                anonymousClass16.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i6) {
                            case 0:
                                LauncherProxyService.AnonymousClass1 anonymousClass17 = anonymousClass16;
                                float f2 = f;
                                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onAssistantGestureCompletion(f2);
                                }
                                break;
                            default:
                                LauncherProxyService.AnonymousClass1 anonymousClass18 = anonymousClass16;
                                float f3 = f;
                                LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                for (int size2 = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size2)).onAssistantProgress(f3);
                                }
                                break;
                        }
                    }
                }, "onAssistantProgress");
                return true;
            }
            if (i == 14) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                LauncherProxyService.AnonymousClass1 anonymousClass17 = (LauncherProxyService.AnonymousClass1) this;
                anonymousClass17.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda16(anonymousClass17, bundle, 0), "startAssistant");
                return true;
            }
            if (i == 45) {
                KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                parcel.enforceNoDataAvail();
                final LauncherProxyService.AnonymousClass1 anonymousClass18 = (LauncherProxyService.AnonymousClass1) this;
                final int displayId = keyEvent == null ? -1 : keyEvent.getDisplayId();
                ListPopupWindow$$ExternalSyntheticOutline0.m(displayId, "onBackEvent for displayId=", "LauncherProxyService");
                BackAnimationController.BackAnimationImpl backAnimationImpl = LauncherProxyService.this.mBackAnimation;
                if (backAnimationImpl == null || keyEvent == null) {
                    final int i7 = 2;
                    anonymousClass18.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i7) {
                                case 0:
                                    LauncherProxyService.AnonymousClass1 anonymousClass152 = anonymousClass18;
                                    int i62 = displayId;
                                    LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onPrioritizedRotation(i62);
                                    }
                                    break;
                                case 1:
                                    AccessibilityManager.getInstance(LauncherProxyService.this.mContext).notifyAccessibilityButtonClicked(displayId);
                                    break;
                                default:
                                    int i72 = displayId;
                                    int i8 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                    long jUptimeMillis = SystemClock.uptimeMillis();
                                    KeyEvent keyEvent2 = new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 4, 0, 0, -1, 0, 72, 257);
                                    keyEvent2.setDisplayId(i72);
                                    InputManagerGlobal.getInstance().injectInputEvent(keyEvent2, 0);
                                    long jUptimeMillis2 = SystemClock.uptimeMillis();
                                    KeyEvent keyEvent22 = new KeyEvent(jUptimeMillis2, jUptimeMillis2, 1, 4, 0, 0, -1, 0, 72, 257);
                                    keyEvent22.setDisplayId(i72);
                                    InputManagerGlobal.getInstance().injectInputEvent(keyEvent22, 0);
                                    break;
                            }
                        }
                    }, "onBackPressed");
                    return true;
                }
                backAnimationImpl.setTriggerBack(!keyEvent.isCanceled());
                LauncherProxyService.this.mBackAnimation.onBackMotion(keyEvent.getAction(), 2, displayId, 0.0f, 0.0f);
                return true;
            }
            if (i == 46) {
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                LauncherProxyService.AnonymousClass1 anonymousClass19 = (LauncherProxyService.AnonymousClass1) this;
                anonymousClass19.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$5$$ExternalSyntheticLambda0(anonymousClass19, z3, 1), "setHomeRotationEnabled");
                return true;
            }
            switch (i) {
                case 16:
                    final int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    final LauncherProxyService.AnonymousClass1 anonymousClass110 = (LauncherProxyService.AnonymousClass1) this;
                    final int i9 = 1;
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i9) {
                                case 0:
                                    LauncherProxyService.AnonymousClass1 anonymousClass152 = anonymousClass110;
                                    int i62 = i8;
                                    LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onPrioritizedRotation(i62);
                                    }
                                    break;
                                case 1:
                                    AccessibilityManager.getInstance(LauncherProxyService.this.mContext).notifyAccessibilityButtonClicked(i8);
                                    break;
                                default:
                                    int i72 = i8;
                                    int i82 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                    long jUptimeMillis = SystemClock.uptimeMillis();
                                    KeyEvent keyEvent2 = new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 4, 0, 0, -1, 0, 72, 257);
                                    keyEvent2.setDisplayId(i72);
                                    InputManagerGlobal.getInstance().injectInputEvent(keyEvent2, 0);
                                    long jUptimeMillis2 = SystemClock.uptimeMillis();
                                    KeyEvent keyEvent22 = new KeyEvent(jUptimeMillis2, jUptimeMillis2, 1, 4, 0, 0, -1, 0, 72, 257);
                                    keyEvent22.setDisplayId(i72);
                                    InputManagerGlobal.getInstance().injectInputEvent(keyEvent22, 0);
                                    break;
                            }
                        }
                    };
                    anonymousClass110.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonClicked", new Supplier() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda38
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            Runnable runnable2 = runnable;
                            int i42 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                            runnable2.run();
                            return null;
                        }
                    });
                    return true;
                case 17:
                    LauncherProxyService.AnonymousClass1 anonymousClass111 = (LauncherProxyService.AnonymousClass1) this;
                    final LauncherProxyService$1$$ExternalSyntheticLambda4 launcherProxyService$1$$ExternalSyntheticLambda4 = new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass111, 0);
                    anonymousClass111.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonLongClicked", new Supplier() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda38
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            Runnable runnable2 = launcherProxyService$1$$ExternalSyntheticLambda4;
                            int i42 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                            runnable2.run();
                            return null;
                        }
                    });
                    return true;
                case 18:
                    final int i10 = 1;
                    ((LauncherProxyService.AnonymousClass1) this).verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i10) {
                                case 0:
                                    int i11 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                    ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                                    PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                                    if (pluginMultiStar != null) {
                                        MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                                        break;
                                    }
                                    break;
                                default:
                                    int i12 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                    try {
                                        ActivityTaskManager.getService().stopSystemLockTaskMode();
                                        break;
                                    } catch (RemoteException unused) {
                                        Log.e("LauncherProxyService", "Failed to stop screen pinning");
                                    }
                            }
                        }
                    }, "stopScreenPinning");
                    return true;
                case 19:
                    final float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    final LauncherProxyService.AnonymousClass1 anonymousClass112 = (LauncherProxyService.AnonymousClass1) this;
                    final int i11 = 0;
                    anonymousClass112.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i11) {
                                case 0:
                                    LauncherProxyService.AnonymousClass1 anonymousClass172 = anonymousClass112;
                                    float f22 = f2;
                                    LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onAssistantGestureCompletion(f22);
                                    }
                                    break;
                                default:
                                    LauncherProxyService.AnonymousClass1 anonymousClass182 = anonymousClass112;
                                    float f3 = f2;
                                    LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                    for (int size2 = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size2)).onAssistantProgress(f3);
                                    }
                                    break;
                            }
                        }
                    }, "onAssistantGestureCompletion");
                    return true;
                default:
                    switch (i) {
                        case 48:
                            final boolean z4 = parcel.readBoolean();
                            final boolean z5 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            final LauncherProxyService.AnonymousClass1 anonymousClass113 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass113.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda8
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LauncherProxyService.AnonymousClass1 anonymousClass114 = anonymousClass113;
                                    boolean z6 = z4;
                                    boolean z7 = z5;
                                    LauncherProxyService launcherProxyService = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onTaskbarStatusUpdated$1(z6, z7);
                                    }
                                }
                            }, "notifyTaskbarStatus");
                            return true;
                        case 49:
                            boolean z6 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            LauncherProxyService.AnonymousClass1 anonymousClass114 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass114.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$5$$ExternalSyntheticLambda0(anonymousClass114, z6, 2), "notifyTaskbarAutohideSuspend");
                            return true;
                        case 50:
                            LauncherProxyService.AnonymousClass1 anonymousClass115 = (LauncherProxyService.AnonymousClass1) this;
                            InputMethodManager inputMethodManager = (InputMethodManager) LauncherProxyService.this.mContext.getSystemService(InputMethodManager.class);
                            LauncherProxyService.this.mDisplayTracker.getClass();
                            inputMethodManager.showInputMethodPickerFromSystem(true, 0);
                            LauncherProxyService.this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                            return true;
                        case 51:
                            LauncherProxyService.AnonymousClass1 anonymousClass116 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass116.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass116, 8), "toggleNotificationPanel");
                            return true;
                        case 52:
                            ScreenshotRequest screenshotRequest = (ScreenshotRequest) parcel.readTypedObject(ScreenshotRequest.CREATOR);
                            parcel.enforceNoDataAvail();
                            LauncherProxyService launcherProxyService = LauncherProxyService.this;
                            launcherProxyService.mScreenshotHelper.takeScreenshot(screenshotRequest, launcherProxyService.mHandler, (Consumer) null);
                            return true;
                        case 53:
                            MotionEvent motionEvent2 = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                            parcel.enforceNoDataAvail();
                            LauncherProxyService.AnonymousClass1 anonymousClass117 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass117.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda3(anonymousClass117, motionEvent2, 1), "onStatusBarTrackpadEvent");
                            return true;
                        case 54:
                            int[] iArrCreateIntArray = parcel.createIntArray();
                            parcel.enforceNoDataAvail();
                            LauncherProxyService.AnonymousClass1 anonymousClass118 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass118.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda16(anonymousClass118, iArrCreateIntArray), "setAssistantOverridesRequested");
                            return true;
                        case 55:
                            final boolean z7 = parcel.readBoolean();
                            final boolean z8 = parcel.readBoolean();
                            final long j = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            final LauncherProxyService.AnonymousClass1 anonymousClass119 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass119.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda22
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LauncherProxyService.AnonymousClass1 anonymousClass120 = anonymousClass119;
                                    boolean z9 = z7;
                                    boolean z10 = z8;
                                    long j2 = j;
                                    LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size)).animateNavBarLongPress(z9, z10, j2);
                                    }
                                }
                            }, "animateNavBarLongPress");
                            return true;
                        case 56:
                            final long j2 = parcel.readLong();
                            final float f3 = parcel.readFloat();
                            final boolean z9 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            final LauncherProxyService.AnonymousClass1 anonymousClass120 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass120.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda32
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LauncherProxyService.AnonymousClass1 anonymousClass121 = anonymousClass120;
                                    long j3 = j2;
                                    float f4 = f3;
                                    boolean z10 = z9;
                                    LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                    for (int size = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                        ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size)).setOverrideHomeButtonLongPress(f4, z10, j3);
                                    }
                                }
                            }, "setOverrideHomeButtonLongPress");
                            return true;
                        case 57:
                            LauncherProxyService.AnonymousClass1 anonymousClass121 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass121.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass121, 9), "toggleQuickSettingsPanel");
                            return true;
                        case 58:
                            return true;
                        case 59:
                            boolean z10 = parcel.readBoolean();
                            String string2 = parcel.readString();
                            parcel.enforceNoDataAvail();
                            LauncherProxyService.AnonymousClass1 anonymousClass122 = (LauncherProxyService.AnonymousClass1) this;
                            anonymousClass122.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda10(0, anonymousClass122, string2, z10), "updateContextualEduStats");
                            return true;
                        default:
                            switch (i) {
                                case 101:
                                    LauncherProxyService.AnonymousClass1 anonymousClass123 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.NAVBAR_GESTURE) {
                                        LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                                        ((NavBarStoreImpl) launcherProxyService2.mNavBarStore).handleEvent(launcherProxyService2, new EventTypeFactory.EventType.ResetBottomGestureHintVI());
                                    }
                                    LauncherProxyService launcherProxyService3 = LauncherProxyService.this;
                                    if (launcherProxyService3.mQsCustomizerContoller != null) {
                                        launcherProxyService3.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass123, 4));
                                    }
                                    parcel2.writeNoException();
                                    return true;
                                case 102:
                                    int i12 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    LauncherProxyService.AnonymousClass1 anonymousClass124 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.NAVBAR_GESTURE) {
                                        LauncherProxyService launcherProxyService4 = LauncherProxyService.this;
                                        ((NavBarStoreImpl) launcherProxyService4.mNavBarStore).handleEvent(launcherProxyService4, new EventTypeFactory.EventType.StartBottomGestureHintVI(i12));
                                    }
                                    parcel2.writeNoException();
                                    return true;
                                case 103:
                                    int i13 = parcel.readInt();
                                    int i14 = parcel.readInt();
                                    int i15 = parcel.readInt();
                                    long j3 = parcel.readLong();
                                    parcel.enforceNoDataAvail();
                                    LauncherProxyService.AnonymousClass1 anonymousClass125 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.NAVBAR_GESTURE) {
                                        LauncherProxyService launcherProxyService5 = LauncherProxyService.this;
                                        ((NavBarStoreImpl) launcherProxyService5.mNavBarStore).handleEvent(launcherProxyService5, new EventTypeFactory.EventType.MoveBottomGestureHintDistance(i13, i14, i15, j3));
                                    }
                                    parcel2.writeNoException();
                                    return true;
                                case 104:
                                    final int i16 = 0;
                                    ((LauncherProxyService.AnonymousClass1) this).verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i16) {
                                                case 0:
                                                    int i112 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                                    ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                                                    PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                                                    if (pluginMultiStar != null) {
                                                        MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    int i122 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                                                    try {
                                                        ActivityTaskManager.getService().stopSystemLockTaskMode();
                                                        break;
                                                    } catch (RemoteException unused) {
                                                        Log.e("LauncherProxyService", "Failed to stop screen pinning");
                                                    }
                                            }
                                        }
                                    }, "notifyOnLongPressRecentsWithMultiStar");
                                    parcel2.writeNoException();
                                    return true;
                                case 105:
                                    LauncherProxyService.AnonymousClass1 anonymousClass126 = (LauncherProxyService.AnonymousClass1) this;
                                    anonymousClass126.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass126, 10), "notifyTaskbarNavigationBarInitialized");
                                    parcel2.writeNoException();
                                    return true;
                                case 106:
                                    LauncherProxyService.AnonymousClass1 anonymousClass127 = (LauncherProxyService.AnonymousClass1) this;
                                    anonymousClass127.verifyCallerAndClearCallingIdentityPostMain(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass127, 5), "notifyTaskbarSPluginButtonClicked");
                                    parcel2.writeNoException();
                                    return true;
                                case 107:
                                    boolean z11 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    LauncherProxyService.AnonymousClass1 anonymousClass128 = (LauncherProxyService.AnonymousClass1) this;
                                    FgsManagerController fgsManagerController = LauncherProxyService.this.mFgsManagerController;
                                    if (fgsManagerController != null) {
                                        if (z11) {
                                            LauncherProxyService$1$$ExternalSyntheticLambda27 launcherProxyService$1$$ExternalSyntheticLambda27 = anonymousClass128.mOnNumberOfPackagesChangedListener;
                                            FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) fgsManagerController;
                                            synchronized (fgsManagerControllerImpl.lock) {
                                                fgsManagerControllerImpl.onNumberOfPackagesChangedListeners.add(launcherProxyService$1$$ExternalSyntheticLambda27);
                                            }
                                            if (fgsManagerControllerImpl.secFgsManagerController != null) {
                                                SecFgsManagerController.log("addOnNumberOfPackagesChangedListener");
                                            }
                                            LauncherProxyService launcherProxyService6 = LauncherProxyService.this;
                                            int numRunningPackages = ((FgsManagerControllerImpl) launcherProxyService6.mFgsManagerController).getNumRunningPackages();
                                            try {
                                                ILauncherProxy iLauncherProxy = launcherProxyService6.mLauncherProxy;
                                                if (iLauncherProxy != null) {
                                                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onNumberOfVisibleFgsChanged(numRunningPackages);
                                                }
                                            } catch (RemoteException e) {
                                                Log.e("LauncherProxyService", "Failed to call onNumberOfVisibleFgsChanged().", e);
                                            }
                                        } else {
                                            LauncherProxyService$1$$ExternalSyntheticLambda27 launcherProxyService$1$$ExternalSyntheticLambda272 = anonymousClass128.mOnNumberOfPackagesChangedListener;
                                            FgsManagerControllerImpl fgsManagerControllerImpl2 = (FgsManagerControllerImpl) fgsManagerController;
                                            if (fgsManagerControllerImpl2.secFgsManagerController != null) {
                                                SecFgsManagerController.log("removeOnNumberOfPackagesChangedListener");
                                            }
                                            synchronized (fgsManagerControllerImpl2.lock) {
                                                fgsManagerControllerImpl2.onNumberOfPackagesChangedListeners.remove(launcherProxyService$1$$ExternalSyntheticLambda272);
                                            }
                                        }
                                    }
                                    parcel2.writeNoException();
                                    return true;
                                case 108:
                                    LauncherProxyService.AnonymousClass1 anonymousClass129 = (LauncherProxyService.AnonymousClass1) this;
                                    LauncherProxyService launcherProxyService7 = LauncherProxyService.this;
                                    if (launcherProxyService7.mFgsManagerController != null) {
                                        launcherProxyService7.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass129, 1));
                                    }
                                    parcel2.writeNoException();
                                    return true;
                                case 109:
                                    LauncherProxyService.AnonymousClass1 anonymousClass130 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.SEARCLE && LauncherProxyService.this.mSearcleManager != null) {
                                        Log.d("LauncherProxyService", "startSearcle");
                                        LauncherProxyService.this.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass130, 7));
                                        return true;
                                    }
                                    return true;
                                case 110:
                                    LauncherProxyService.AnonymousClass1 anonymousClass131 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.SEARCLE && LauncherProxyService.this.mSearcleManager != null) {
                                        Log.d("LauncherProxyService", "invokeSearcle");
                                        LauncherProxyService.this.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass131, 3));
                                        return true;
                                    }
                                    return true;
                                case 111:
                                    LauncherProxyService.AnonymousClass1 anonymousClass132 = (LauncherProxyService.AnonymousClass1) this;
                                    if (BasicRune.SEARCLE && LauncherProxyService.this.mSearcleManager != null) {
                                        Log.d("LauncherProxyService", "cancelSearcle");
                                        LauncherProxyService.this.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda4(anonymousClass132, 6));
                                        return true;
                                    }
                                    return true;
                                case 112:
                                    String string3 = parcel.readString();
                                    parcel.enforceNoDataAvail();
                                    LauncherProxyService.AnonymousClass1 anonymousClass133 = (LauncherProxyService.AnonymousClass1) this;
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("invokeSearcleWithPackageName packageName = ", string3, "LauncherProxyService");
                                    if (BasicRune.SEARCLE) {
                                        LauncherProxyService.this.mMainHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda16(anonymousClass133, string3, 2));
                                        return true;
                                    }
                                    return true;
                                case 113:
                                    final boolean z12 = parcel.readBoolean();
                                    final int i17 = parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    final LauncherProxyService.AnonymousClass1 anonymousClass134 = (LauncherProxyService.AnonymousClass1) this;
                                    anonymousClass134.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticLambda33
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            LauncherProxyService.AnonymousClass1 anonymousClass135 = anonymousClass134;
                                            boolean z13 = z12;
                                            int i18 = i17;
                                            LauncherProxyService launcherProxyService8 = LauncherProxyService.this;
                                            for (int size = ((ArrayList) launcherProxyService8.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService8.mConnectionCallbacks).get(size)).onTaskbarAutohideSuspendForDisplay(i18, z13);
                                            }
                                        }
                                    }, "notifyTaskbarAutohideSuspendForDisplay");
                                    return true;
                                default:
                                    return super.onTransact(i, parcel, parcel2, i2);
                            }
                    }
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
