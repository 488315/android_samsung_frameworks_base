package com.android.systemui.shared.recents;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.SecFgsManagerController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda5;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda8;
import com.android.systemui.recents.OverviewProxyService$5$$ExternalSyntheticLambda0;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ISystemUiProxy extends IInterface {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Stub extends Binder implements ISystemUiProxy {
        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.ISystemUiProxy");
        }

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
                final int readInt = parcel.readInt();
                final boolean readBoolean = parcel.readBoolean();
                final String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                final OverviewProxyService.AnonymousClass1 anonymousClass1 = (OverviewProxyService.AnonymousClass1) this;
                anonymousClass1.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda25
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverviewProxyService.AnonymousClass1 anonymousClass12 = OverviewProxyService.AnonymousClass1.this;
                        OverviewProxyService.this.mSamsungScreenPinningRequest.showPrompt(readInt, readBoolean, readString);
                    }
                }, "startScreenPinning");
            } else if (i == 7) {
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                OverviewProxyService.AnonymousClass1 anonymousClass12 = (OverviewProxyService.AnonymousClass1) this;
                anonymousClass12.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$5$$ExternalSyntheticLambda0(anonymousClass12, readBoolean2, 3), "onOverviewShown");
            } else if (i == 10) {
                MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                OverviewProxyService.AnonymousClass1 anonymousClass13 = (OverviewProxyService.AnonymousClass1) this;
                Log.d("OverviewProxyService", "onStatusBarTouchEvent: " + motionEvent.getAction());
                final OverviewProxyService$1$$ExternalSyntheticLambda8 overviewProxyService$1$$ExternalSyntheticLambda8 = new OverviewProxyService$1$$ExternalSyntheticLambda8(anonymousClass13, motionEvent, 1);
                anonymousClass13.verifyCallerAndClearCallingIdentity("onStatusBarTouchEvent", new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda32
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        overviewProxyService$1$$ExternalSyntheticLambda8.run();
                        return null;
                    }
                });
            } else if (i == 26) {
                final int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                final OverviewProxyService.AnonymousClass1 anonymousClass14 = (OverviewProxyService.AnonymousClass1) this;
                final int i3 = 0;
                anonymousClass14.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                OverviewProxyService.AnonymousClass1 anonymousClass15 = anonymousClass14;
                                int i4 = readInt2;
                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).onPrioritizedRotation(i4);
                                }
                                break;
                            default:
                                AccessibilityManager.getInstance(OverviewProxyService.this.mContext).notifyAccessibilityButtonClicked(readInt2);
                                break;
                        }
                    }
                }, "notifyPrioritizedRotation");
            } else if (i == 30) {
                final OverviewProxyService.AnonymousClass1 anonymousClass15 = (OverviewProxyService.AnonymousClass1) this;
                final int i4 = 2;
                anonymousClass15.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i5 = i4;
                        OverviewProxyService.AnonymousClass1 anonymousClass16 = anonymousClass15;
                        switch (i5) {
                            case 0:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                return;
                            case 1:
                                anonymousClass16.getClass();
                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                intent.addFlags(268468224);
                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                return;
                            case 2:
                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                return;
                            case 3:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                return;
                            case 4:
                                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                }
                                return;
                            case 5:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                return;
                            case 6:
                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                return;
                            case 7:
                                OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                return;
                            case 8:
                                anonymousClass16.sendEvent(0);
                                anonymousClass16.sendEvent(1);
                                return;
                            case 9:
                                OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                for (int size2 = ((ArrayList) overviewProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService3.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                }
                                return;
                            default:
                                CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                synchronized (commandQueue.mLock) {
                                    commandQueue.mHandler.removeMessages(5373952);
                                    commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                }
                                return;
                        }
                    }
                }, "expandNotificationPanel");
            } else if (i == 13) {
                final float readFloat = parcel.readFloat();
                parcel.enforceNoDataAvail();
                final OverviewProxyService.AnonymousClass1 anonymousClass16 = (OverviewProxyService.AnonymousClass1) this;
                final int i5 = 1;
                anonymousClass16.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                OverviewProxyService.AnonymousClass1 anonymousClass17 = anonymousClass16;
                                float f = readFloat;
                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).onAssistantGestureCompletion(f);
                                }
                                break;
                            default:
                                OverviewProxyService.AnonymousClass1 anonymousClass18 = anonymousClass16;
                                float f2 = readFloat;
                                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                for (int size2 = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size2)).onAssistantProgress(f2);
                                }
                                break;
                        }
                    }
                }, "onAssistantProgress");
            } else if (i == 14) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                OverviewProxyService.AnonymousClass1 anonymousClass17 = (OverviewProxyService.AnonymousClass1) this;
                anonymousClass17.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda5(anonymousClass17, bundle, 2), "startAssistant");
            } else if (i == 45) {
                final OverviewProxyService.AnonymousClass1 anonymousClass18 = (OverviewProxyService.AnonymousClass1) this;
                final int i6 = 8;
                anonymousClass18.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i52 = i6;
                        OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass18;
                        switch (i52) {
                            case 0:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                return;
                            case 1:
                                anonymousClass162.getClass();
                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                intent.addFlags(268468224);
                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                return;
                            case 2:
                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                return;
                            case 3:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                return;
                            case 4:
                                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                }
                                return;
                            case 5:
                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                return;
                            case 6:
                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                return;
                            case 7:
                                OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                return;
                            case 8:
                                anonymousClass162.sendEvent(0);
                                anonymousClass162.sendEvent(1);
                                return;
                            case 9:
                                OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                for (int size2 = ((ArrayList) overviewProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService3.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                }
                                return;
                            default:
                                CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                synchronized (commandQueue.mLock) {
                                    commandQueue.mHandler.removeMessages(5373952);
                                    commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                }
                                return;
                        }
                    }
                }, "onBackPressed");
            } else if (i != 46) {
                switch (i) {
                    case 16:
                        final int readInt3 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        final OverviewProxyService.AnonymousClass1 anonymousClass19 = (OverviewProxyService.AnonymousClass1) this;
                        final int i7 = 1;
                        final Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda21
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i7) {
                                    case 0:
                                        OverviewProxyService.AnonymousClass1 anonymousClass152 = anonymousClass19;
                                        int i42 = readInt3;
                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).onPrioritizedRotation(i42);
                                        }
                                        break;
                                    default:
                                        AccessibilityManager.getInstance(OverviewProxyService.this.mContext).notifyAccessibilityButtonClicked(readInt3);
                                        break;
                                }
                            }
                        };
                        anonymousClass19.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonClicked", new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda32
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                runnable.run();
                                return null;
                            }
                        });
                        break;
                    case 17:
                        final OverviewProxyService.AnonymousClass1 anonymousClass110 = (OverviewProxyService.AnonymousClass1) this;
                        final int i8 = 1;
                        final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i52 = i8;
                                OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass110;
                                switch (i52) {
                                    case 0:
                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                        return;
                                    case 1:
                                        anonymousClass162.getClass();
                                        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                        intent.addFlags(268468224);
                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                        overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                        return;
                                    case 2:
                                        OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                        return;
                                    case 3:
                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                        return;
                                    case 4:
                                        OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                        }
                                        return;
                                    case 5:
                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                        return;
                                    case 6:
                                        ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                        return;
                                    case 7:
                                        OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                        return;
                                    case 8:
                                        anonymousClass162.sendEvent(0);
                                        anonymousClass162.sendEvent(1);
                                        return;
                                    case 9:
                                        OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                        for (int size2 = ((ArrayList) overviewProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService3.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                        }
                                        return;
                                    default:
                                        CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                        synchronized (commandQueue.mLock) {
                                            commandQueue.mHandler.removeMessages(5373952);
                                            commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                        }
                                        return;
                                }
                            }
                        };
                        anonymousClass110.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonLongClicked", new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda32
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                runnable2.run();
                                return null;
                            }
                        });
                        break;
                    case 18:
                        final int i9 = 1;
                        ((OverviewProxyService.AnonymousClass1) this).verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i9) {
                                    case 0:
                                        ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                                        PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                                        if (pluginMultiStar != null) {
                                            MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                                            break;
                                        }
                                        break;
                                    default:
                                        try {
                                            ActivityTaskManager.getService().stopSystemLockTaskMode();
                                            break;
                                        } catch (RemoteException unused) {
                                            Log.e("OverviewProxyService", "Failed to stop screen pinning");
                                            return;
                                        }
                                }
                            }
                        }, "stopScreenPinning");
                        break;
                    case 19:
                        final float readFloat2 = parcel.readFloat();
                        parcel.enforceNoDataAvail();
                        final OverviewProxyService.AnonymousClass1 anonymousClass111 = (OverviewProxyService.AnonymousClass1) this;
                        final int i10 = 0;
                        anonymousClass111.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i10) {
                                    case 0:
                                        OverviewProxyService.AnonymousClass1 anonymousClass172 = anonymousClass111;
                                        float f = readFloat2;
                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).onAssistantGestureCompletion(f);
                                        }
                                        break;
                                    default:
                                        OverviewProxyService.AnonymousClass1 anonymousClass182 = anonymousClass111;
                                        float f2 = readFloat2;
                                        OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                        for (int size2 = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size2)).onAssistantProgress(f2);
                                        }
                                        break;
                                }
                            }
                        }, "onAssistantGestureCompletion");
                        break;
                    default:
                        switch (i) {
                            case 48:
                                final boolean readBoolean3 = parcel.readBoolean();
                                final boolean readBoolean4 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                final OverviewProxyService.AnonymousClass1 anonymousClass112 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass112.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        OverviewProxyService.AnonymousClass1 anonymousClass113 = OverviewProxyService.AnonymousClass1.this;
                                        boolean z = readBoolean3;
                                        boolean z2 = readBoolean4;
                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).onTaskbarStatusUpdated$1(z, z2);
                                        }
                                    }
                                }, "notifyTaskbarStatus");
                                break;
                            case 49:
                                boolean readBoolean5 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                OverviewProxyService.AnonymousClass1 anonymousClass113 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass113.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$5$$ExternalSyntheticLambda0(anonymousClass113, readBoolean5, 1), "notifyTaskbarAutohideSuspend");
                                break;
                            case 50:
                                OverviewProxyService.AnonymousClass1 anonymousClass114 = (OverviewProxyService.AnonymousClass1) this;
                                InputMethodManager inputMethodManager = (InputMethodManager) OverviewProxyService.this.mContext.getSystemService(InputMethodManager.class);
                                OverviewProxyService.this.mDisplayTracker.getClass();
                                inputMethodManager.showInputMethodPickerFromSystem(true, 0);
                                OverviewProxyService.this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                                break;
                            case 51:
                                final OverviewProxyService.AnonymousClass1 anonymousClass115 = (OverviewProxyService.AnonymousClass1) this;
                                final int i11 = 7;
                                anonymousClass115.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i52 = i11;
                                        OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass115;
                                        switch (i52) {
                                            case 0:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                return;
                                            case 1:
                                                anonymousClass162.getClass();
                                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                intent.addFlags(268468224);
                                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                                overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                                return;
                                            case 2:
                                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                return;
                                            case 3:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                return;
                                            case 4:
                                                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                                for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                }
                                                return;
                                            case 5:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                return;
                                            case 6:
                                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                return;
                                            case 7:
                                                OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                return;
                                            case 8:
                                                anonymousClass162.sendEvent(0);
                                                anonymousClass162.sendEvent(1);
                                                return;
                                            case 9:
                                                OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                                for (int size2 = ((ArrayList) overviewProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService3.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                }
                                                return;
                                            default:
                                                CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                synchronized (commandQueue.mLock) {
                                                    commandQueue.mHandler.removeMessages(5373952);
                                                    commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                }
                                                return;
                                        }
                                    }
                                }, "toggleNotificationPanel");
                                break;
                            case 52:
                                ScreenshotRequest screenshotRequest = (ScreenshotRequest) parcel.readTypedObject(ScreenshotRequest.CREATOR);
                                parcel.enforceNoDataAvail();
                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                overviewProxyService.mScreenshotHelper.takeScreenshot(screenshotRequest, overviewProxyService.mHandler, (Consumer) null);
                                break;
                            case 53:
                                MotionEvent motionEvent2 = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                                parcel.enforceNoDataAvail();
                                OverviewProxyService.AnonymousClass1 anonymousClass116 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass116.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda8(anonymousClass116, motionEvent2, 0), "onStatusBarTrackpadEvent");
                                break;
                            case 54:
                                int[] createIntArray = parcel.createIntArray();
                                parcel.enforceNoDataAvail();
                                OverviewProxyService.AnonymousClass1 anonymousClass117 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass117.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda5(anonymousClass117, createIntArray), "setAssistantOverridesRequested");
                                break;
                            case 55:
                                final boolean readBoolean6 = parcel.readBoolean();
                                final boolean readBoolean7 = parcel.readBoolean();
                                final long readLong = parcel.readLong();
                                parcel.enforceNoDataAvail();
                                final OverviewProxyService.AnonymousClass1 anonymousClass118 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass118.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        OverviewProxyService.AnonymousClass1 anonymousClass119 = OverviewProxyService.AnonymousClass1.this;
                                        boolean z = readBoolean6;
                                        boolean z2 = readBoolean7;
                                        long j = readLong;
                                        OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).animateNavBarLongPress(z, z2, j);
                                        }
                                    }
                                }, "animateNavBarLongPress");
                                break;
                            case 56:
                                final long readLong2 = parcel.readLong();
                                final float readFloat3 = parcel.readFloat();
                                final boolean readBoolean8 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                final OverviewProxyService.AnonymousClass1 anonymousClass119 = (OverviewProxyService.AnonymousClass1) this;
                                anonymousClass119.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda13
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        OverviewProxyService.AnonymousClass1 anonymousClass120 = OverviewProxyService.AnonymousClass1.this;
                                        long j = readLong2;
                                        float f = readFloat3;
                                        boolean z = readBoolean8;
                                        OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                        for (int size = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size)).setOverrideHomeButtonLongPress(f, z, j);
                                        }
                                    }
                                }, "setOverrideHomeButtonLongPress");
                                break;
                            case 57:
                                final OverviewProxyService.AnonymousClass1 anonymousClass120 = (OverviewProxyService.AnonymousClass1) this;
                                final int i12 = 10;
                                anonymousClass120.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i52 = i12;
                                        OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass120;
                                        switch (i52) {
                                            case 0:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                return;
                                            case 1:
                                                anonymousClass162.getClass();
                                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                intent.addFlags(268468224);
                                                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                                overviewProxyService2.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService2.mUserTracker).getUserHandle());
                                                return;
                                            case 2:
                                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                return;
                                            case 3:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                return;
                                            case 4:
                                                OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                for (int size = ((ArrayList) overviewProxyService22.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService22.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                }
                                                return;
                                            case 5:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                return;
                                            case 6:
                                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                return;
                                            case 7:
                                                OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                return;
                                            case 8:
                                                anonymousClass162.sendEvent(0);
                                                anonymousClass162.sendEvent(1);
                                                return;
                                            case 9:
                                                OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                                for (int size2 = ((ArrayList) overviewProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService3.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                }
                                                return;
                                            default:
                                                CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                synchronized (commandQueue.mLock) {
                                                    commandQueue.mHandler.removeMessages(5373952);
                                                    commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                }
                                                return;
                                        }
                                    }
                                }, "toggleQuickSettingsPanel");
                                break;
                            default:
                                switch (i) {
                                    case 101:
                                        OverviewProxyService.AnonymousClass1 anonymousClass121 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.NAVBAR_GESTURE) {
                                            OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                            ((NavBarStoreImpl) overviewProxyService2.mNavBarStore).handleEvent(overviewProxyService2, new EventTypeFactory.EventType.ResetBottomGestureHintVI());
                                        }
                                        parcel2.writeNoException();
                                        break;
                                    case 102:
                                        int readInt4 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        OverviewProxyService.AnonymousClass1 anonymousClass122 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.NAVBAR_GESTURE) {
                                            OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                            ((NavBarStoreImpl) overviewProxyService3.mNavBarStore).handleEvent(overviewProxyService3, new EventTypeFactory.EventType.StartBottomGestureHintVI(readInt4));
                                        }
                                        parcel2.writeNoException();
                                        break;
                                    case 103:
                                        int readInt5 = parcel.readInt();
                                        int readInt6 = parcel.readInt();
                                        int readInt7 = parcel.readInt();
                                        long readLong3 = parcel.readLong();
                                        parcel.enforceNoDataAvail();
                                        OverviewProxyService.AnonymousClass1 anonymousClass123 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.NAVBAR_GESTURE) {
                                            OverviewProxyService overviewProxyService4 = OverviewProxyService.this;
                                            ((NavBarStoreImpl) overviewProxyService4.mNavBarStore).handleEvent(overviewProxyService4, new EventTypeFactory.EventType.MoveBottomGestureHintDistance(readInt5, readInt6, readInt7, readLong3));
                                        }
                                        parcel2.writeNoException();
                                        break;
                                    case 104:
                                        final int i13 = 0;
                                        ((OverviewProxyService.AnonymousClass1) this).verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i13) {
                                                    case 0:
                                                        ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                                                        PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                                                        if (pluginMultiStar != null) {
                                                            MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                                                            break;
                                                        }
                                                        break;
                                                    default:
                                                        try {
                                                            ActivityTaskManager.getService().stopSystemLockTaskMode();
                                                            break;
                                                        } catch (RemoteException unused) {
                                                            Log.e("OverviewProxyService", "Failed to stop screen pinning");
                                                            return;
                                                        }
                                                }
                                            }
                                        }, "notifyOnLongPressRecentsWithMultiStar");
                                        parcel2.writeNoException();
                                        break;
                                    case 105:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass124 = (OverviewProxyService.AnonymousClass1) this;
                                        final int i14 = 4;
                                        anonymousClass124.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i52 = i14;
                                                OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass124;
                                                switch (i52) {
                                                    case 0:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                        return;
                                                    case 1:
                                                        anonymousClass162.getClass();
                                                        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                        intent.addFlags(268468224);
                                                        OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                        overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                        return;
                                                    case 2:
                                                        OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                        return;
                                                    case 3:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                        return;
                                                    case 4:
                                                        OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                        for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                        }
                                                        return;
                                                    case 5:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                        return;
                                                    case 6:
                                                        ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                        return;
                                                    case 7:
                                                        OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                        return;
                                                    case 8:
                                                        anonymousClass162.sendEvent(0);
                                                        anonymousClass162.sendEvent(1);
                                                        return;
                                                    case 9:
                                                        OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                        for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                        }
                                                        return;
                                                    default:
                                                        CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                        synchronized (commandQueue.mLock) {
                                                            commandQueue.mHandler.removeMessages(5373952);
                                                            commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                        }
                                                        return;
                                                }
                                            }
                                        }, "notifyTaskbarNavigationBarInitialized");
                                        parcel2.writeNoException();
                                        break;
                                    case 106:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass125 = (OverviewProxyService.AnonymousClass1) this;
                                        final int i15 = 9;
                                        anonymousClass125.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i52 = i15;
                                                OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass125;
                                                switch (i52) {
                                                    case 0:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                        return;
                                                    case 1:
                                                        anonymousClass162.getClass();
                                                        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                        intent.addFlags(268468224);
                                                        OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                        overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                        return;
                                                    case 2:
                                                        OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                        return;
                                                    case 3:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                        return;
                                                    case 4:
                                                        OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                        for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                        }
                                                        return;
                                                    case 5:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                        return;
                                                    case 6:
                                                        ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                        return;
                                                    case 7:
                                                        OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                        return;
                                                    case 8:
                                                        anonymousClass162.sendEvent(0);
                                                        anonymousClass162.sendEvent(1);
                                                        return;
                                                    case 9:
                                                        OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                        for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                            ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                        }
                                                        return;
                                                    default:
                                                        CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                        synchronized (commandQueue.mLock) {
                                                            commandQueue.mHandler.removeMessages(5373952);
                                                            commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                        }
                                                        return;
                                                }
                                            }
                                        }, "notifyTaskbarSPluginButtonClicked");
                                        parcel2.writeNoException();
                                        break;
                                    case 107:
                                        boolean readBoolean9 = parcel.readBoolean();
                                        parcel.enforceNoDataAvail();
                                        OverviewProxyService.AnonymousClass1 anonymousClass126 = (OverviewProxyService.AnonymousClass1) this;
                                        FgsManagerController fgsManagerController = OverviewProxyService.this.mFgsManagerController;
                                        if (fgsManagerController != null) {
                                            if (readBoolean9) {
                                                OverviewProxyService$1$$ExternalSyntheticLambda2 overviewProxyService$1$$ExternalSyntheticLambda2 = anonymousClass126.mOnNumberOfPackagesChangedListener;
                                                FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) fgsManagerController;
                                                synchronized (fgsManagerControllerImpl.lock) {
                                                    fgsManagerControllerImpl.onNumberOfPackagesChangedListeners.add(overviewProxyService$1$$ExternalSyntheticLambda2);
                                                }
                                                if (fgsManagerControllerImpl.secFgsManagerController != null) {
                                                    SecFgsManagerController.log("addOnNumberOfPackagesChangedListener");
                                                }
                                                OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                                                int numRunningPackages = ((FgsManagerControllerImpl) overviewProxyService5.mFgsManagerController).getNumRunningPackages();
                                                try {
                                                    IOverviewProxy iOverviewProxy = overviewProxyService5.mOverviewProxy;
                                                    if (iOverviewProxy != null) {
                                                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onNumberOfVisibleFgsChanged(numRunningPackages);
                                                    }
                                                } catch (RemoteException e) {
                                                    Log.e("OverviewProxyService", "Failed to call onNumberOfVisibleFgsChanged().", e);
                                                }
                                            } else {
                                                OverviewProxyService$1$$ExternalSyntheticLambda2 overviewProxyService$1$$ExternalSyntheticLambda22 = anonymousClass126.mOnNumberOfPackagesChangedListener;
                                                FgsManagerControllerImpl fgsManagerControllerImpl2 = (FgsManagerControllerImpl) fgsManagerController;
                                                if (fgsManagerControllerImpl2.secFgsManagerController != null) {
                                                    SecFgsManagerController.log("removeOnNumberOfPackagesChangedListener");
                                                }
                                                synchronized (fgsManagerControllerImpl2.lock) {
                                                    fgsManagerControllerImpl2.onNumberOfPackagesChangedListeners.remove(overviewProxyService$1$$ExternalSyntheticLambda22);
                                                }
                                            }
                                        }
                                        parcel2.writeNoException();
                                        break;
                                    case 108:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass127 = (OverviewProxyService.AnonymousClass1) this;
                                        OverviewProxyService overviewProxyService6 = OverviewProxyService.this;
                                        if (overviewProxyService6.mFgsManagerController != null) {
                                            final int i16 = 6;
                                            overviewProxyService6.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i52 = i16;
                                                    OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass127;
                                                    switch (i52) {
                                                        case 0:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                            return;
                                                        case 1:
                                                            anonymousClass162.getClass();
                                                            Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                            intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                            intent.addFlags(268468224);
                                                            OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                            overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                            return;
                                                        case 2:
                                                            OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                            return;
                                                        case 3:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                            return;
                                                        case 4:
                                                            OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                            for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                            }
                                                            return;
                                                        case 5:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                            return;
                                                        case 6:
                                                            ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                            return;
                                                        case 7:
                                                            OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                            return;
                                                        case 8:
                                                            anonymousClass162.sendEvent(0);
                                                            anonymousClass162.sendEvent(1);
                                                            return;
                                                        case 9:
                                                            OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                            for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                            }
                                                            return;
                                                        default:
                                                            CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                            synchronized (commandQueue.mLock) {
                                                                commandQueue.mHandler.removeMessages(5373952);
                                                                commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                            }
                                                            return;
                                                    }
                                                }
                                            });
                                        }
                                        parcel2.writeNoException();
                                        break;
                                    case 109:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass128 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.SEARCLE && OverviewProxyService.this.mSearcleManager != null) {
                                            Log.d("OverviewProxyService", "startSearcle");
                                            final int i17 = 3;
                                            OverviewProxyService.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i52 = i17;
                                                    OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass128;
                                                    switch (i52) {
                                                        case 0:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                            return;
                                                        case 1:
                                                            anonymousClass162.getClass();
                                                            Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                            intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                            intent.addFlags(268468224);
                                                            OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                            overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                            return;
                                                        case 2:
                                                            OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                            return;
                                                        case 3:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                            return;
                                                        case 4:
                                                            OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                            for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                            }
                                                            return;
                                                        case 5:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                            return;
                                                        case 6:
                                                            ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                            return;
                                                        case 7:
                                                            OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                            return;
                                                        case 8:
                                                            anonymousClass162.sendEvent(0);
                                                            anonymousClass162.sendEvent(1);
                                                            return;
                                                        case 9:
                                                            OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                            for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                            }
                                                            return;
                                                        default:
                                                            CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                            synchronized (commandQueue.mLock) {
                                                                commandQueue.mHandler.removeMessages(5373952);
                                                                commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                            }
                                                            return;
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                        break;
                                    case 110:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass129 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.SEARCLE && OverviewProxyService.this.mSearcleManager != null) {
                                            Log.d("OverviewProxyService", "invokeSearcle");
                                            final int i18 = 5;
                                            OverviewProxyService.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i52 = i18;
                                                    OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass129;
                                                    switch (i52) {
                                                        case 0:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                            return;
                                                        case 1:
                                                            anonymousClass162.getClass();
                                                            Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                            intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                            intent.addFlags(268468224);
                                                            OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                            overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                            return;
                                                        case 2:
                                                            OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                            return;
                                                        case 3:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                            return;
                                                        case 4:
                                                            OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                            for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                            }
                                                            return;
                                                        case 5:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                            return;
                                                        case 6:
                                                            ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                            return;
                                                        case 7:
                                                            OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                            return;
                                                        case 8:
                                                            anonymousClass162.sendEvent(0);
                                                            anonymousClass162.sendEvent(1);
                                                            return;
                                                        case 9:
                                                            OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                            for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                            }
                                                            return;
                                                        default:
                                                            CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                            synchronized (commandQueue.mLock) {
                                                                commandQueue.mHandler.removeMessages(5373952);
                                                                commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                            }
                                                            return;
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                        break;
                                    case 111:
                                        final OverviewProxyService.AnonymousClass1 anonymousClass130 = (OverviewProxyService.AnonymousClass1) this;
                                        if (BasicRune.SEARCLE && OverviewProxyService.this.mSearcleManager != null) {
                                            Log.d("OverviewProxyService", "cancelSearcle");
                                            final int i19 = 0;
                                            OverviewProxyService.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i52 = i19;
                                                    OverviewProxyService.AnonymousClass1 anonymousClass162 = anonymousClass130;
                                                    switch (i52) {
                                                        case 0:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                            return;
                                                        case 1:
                                                            anonymousClass162.getClass();
                                                            Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                            intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                            intent.addFlags(268468224);
                                                            OverviewProxyService overviewProxyService22 = OverviewProxyService.this;
                                                            overviewProxyService22.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService22.mUserTracker).getUserHandle());
                                                            return;
                                                        case 2:
                                                            OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                            return;
                                                        case 3:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                            return;
                                                        case 4:
                                                            OverviewProxyService overviewProxyService222 = OverviewProxyService.this;
                                                            for (int size = ((ArrayList) overviewProxyService222.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService222.mConnectionCallbacks).get(size)).onInitializedTaskbarNavigationBar();
                                                            }
                                                            return;
                                                        case 5:
                                                            OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                            return;
                                                        case 6:
                                                            ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog$1();
                                                            return;
                                                        case 7:
                                                            OverviewProxyService.this.mCommandQueue.toggleNotificationsPanel();
                                                            return;
                                                        case 8:
                                                            anonymousClass162.sendEvent(0);
                                                            anonymousClass162.sendEvent(1);
                                                            return;
                                                        case 9:
                                                            OverviewProxyService overviewProxyService32 = OverviewProxyService.this;
                                                            for (int size2 = ((ArrayList) overviewProxyService32.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                                                                ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService32.mConnectionCallbacks).get(size2)).onTaskbarSPluginButtonClicked();
                                                            }
                                                            return;
                                                        default:
                                                            CommandQueue commandQueue = OverviewProxyService.this.mCommandQueue;
                                                            synchronized (commandQueue.mLock) {
                                                                commandQueue.mHandler.removeMessages(5373952);
                                                                commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                                                            }
                                                            return;
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                        break;
                                    case 112:
                                        String readString2 = parcel.readString();
                                        parcel.enforceNoDataAvail();
                                        OverviewProxyService.AnonymousClass1 anonymousClass131 = (OverviewProxyService.AnonymousClass1) this;
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("invokeSearcleWithPackageName packageName = ", readString2, "OverviewProxyService");
                                        if (BasicRune.SEARCLE) {
                                            OverviewProxyService.this.mMainHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda5(anonymousClass131, readString2, 0));
                                            break;
                                        }
                                        break;
                                    default:
                                        return super.onTransact(i, parcel, parcel2, i2);
                                }
                        }
                }
            } else {
                boolean readBoolean10 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                OverviewProxyService.AnonymousClass1 anonymousClass132 = (OverviewProxyService.AnonymousClass1) this;
                anonymousClass132.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$5$$ExternalSyntheticLambda0(anonymousClass132, readBoolean10, 2), "setHomeRotationEnabled");
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
