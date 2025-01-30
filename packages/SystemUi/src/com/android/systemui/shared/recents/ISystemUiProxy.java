package com.android.systemui.shared.recents;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.BasicRune;
import com.android.systemui.QpRune;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda5;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ISystemUiProxy extends IInterface {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISystemUiProxy {
        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.ISystemUiProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            long clearCallingIdentity;
            final int i3 = 1;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.recents.ISystemUiProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.shared.recents.ISystemUiProxy");
                return true;
            }
            final int i4 = 2;
            if (i != 2) {
                final int i5 = 7;
                if (i == 7) {
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.BinderC23081 binderC23081 = (OverviewProxyService.BinderC23081) this;
                    binderC23081.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda5(binderC23081, readBoolean, 2), "onOverviewShown");
                } else if (i == 10) {
                    final MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    final OverviewProxyService.BinderC23081 binderC230812 = (OverviewProxyService.BinderC23081) this;
                    Log.d("OverviewProxyService", "onStatusBarMotionEvent " + motionEvent.getAction());
                    Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    OverviewProxyService.BinderC23081 binderC230813 = binderC230812;
                                    String str = (String) motionEvent;
                                    SearcleManager searcleManager = OverviewProxyService.this.mSearcleManager;
                                    searcleManager.invokedPackageName = str;
                                    searcleManager.startSearcleByHomeKey(false, true);
                                    break;
                                case 1:
                                    final OverviewProxyService.BinderC23081 binderC230814 = binderC230812;
                                    final MotionEvent motionEvent2 = (MotionEvent) motionEvent;
                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ShadeViewController shadeViewController;
                                            final OverviewProxyService.BinderC23081 binderC230815 = OverviewProxyService.BinderC23081.this;
                                            final MotionEvent motionEvent3 = motionEvent2;
                                            final CentralSurfaces centralSurfaces = (CentralSurfaces) obj;
                                            binderC230815.getClass();
                                            if (motionEvent3.getActionMasked() == 0 && (shadeViewController = ((CentralSurfacesImpl) centralSurfaces).getShadeViewController()) != null) {
                                                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
                                                LatencyTracker latencyTracker = notificationPanelViewController.mLatencyTracker;
                                                if (latencyTracker.isEnabled()) {
                                                    latencyTracker.onActionStart(0);
                                                    notificationPanelViewController.mExpandLatencyTracking = true;
                                                }
                                            }
                                            OverviewProxyService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    OverviewProxyService.BinderC23081 binderC230816 = OverviewProxyService.BinderC23081.this;
                                                    MotionEvent motionEvent4 = motionEvent3;
                                                    CentralSurfaces centralSurfaces2 = centralSurfaces;
                                                    binderC230816.getClass();
                                                    int actionMasked = motionEvent4.getActionMasked();
                                                    if (actionMasked == 0) {
                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_DOWN");
                                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                                        overviewProxyService.mInputFocusTransferStarted = true;
                                                        overviewProxyService.mInputFocusTransferStartY = motionEvent4.getY();
                                                        OverviewProxyService.this.mInputFocusTransferStartMillis = motionEvent4.getEventTime();
                                                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                                                        centralSurfacesImpl.onInputFocusTransfer(0.0f, OverviewProxyService.this.mInputFocusTransferStarted, false);
                                                        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                                                            centralSurfacesImpl.setNextUpdateHorizontalPosition(motionEvent4.getX());
                                                        }
                                                    }
                                                    if (actionMasked == 1 || actionMasked == 3) {
                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_UP / ACTION_CANCEL");
                                                        OverviewProxyService.this.mInputFocusTransferStarted = false;
                                                        float y = motionEvent4.getY() - OverviewProxyService.this.mInputFocusTransferStartY;
                                                        long eventTime = motionEvent4.getEventTime();
                                                        ((CentralSurfacesImpl) centralSurfaces2).onInputFocusTransfer(y / (eventTime - r0.mInputFocusTransferStartMillis), OverviewProxyService.this.mInputFocusTransferStarted, actionMasked == 3);
                                                    }
                                                    motionEvent4.recycle();
                                                }
                                            });
                                        }
                                    });
                                    break;
                                default:
                                    OverviewProxyService.BinderC23081 binderC230815 = binderC230812;
                                    Bundle bundle = (Bundle) motionEvent;
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).startAssistant(bundle);
                                        }
                                    }
                            }
                        }
                    };
                    if (binderC230812.verifyCaller("onStatusBarMotionEvent")) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            runnable.run();
                        } finally {
                        }
                    }
                } else if (i == 26) {
                    final int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    final OverviewProxyService.BinderC23081 binderC230813 = (OverviewProxyService.BinderC23081) this;
                    binderC230813.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    AccessibilityManager.getInstance(OverviewProxyService.this.mContext).notifyAccessibilityButtonClicked(readInt);
                                    break;
                                default:
                                    OverviewProxyService.BinderC23081 binderC230814 = binderC230813;
                                    int i6 = readInt;
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onPrioritizedRotation(i6);
                                        }
                                    }
                            }
                        }
                    }, "notifyPrioritizedRotation");
                } else if (i == 30) {
                    final OverviewProxyService.BinderC23081 binderC230814 = (OverviewProxyService.BinderC23081) this;
                    final int i6 = 8;
                    Runnable runnable2 = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i6) {
                                case 0:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                    break;
                                case 1:
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                        }
                                    }
                                case 2:
                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                    break;
                                case 3:
                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size2 = arrayList2.size();
                                    while (true) {
                                        size2--;
                                        if (size2 < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                        }
                                    }
                                case 4:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                    break;
                                case 5:
                                    OverviewProxyService.BinderC23081 binderC230815 = binderC230814;
                                    binderC230815.sendEvent(0);
                                    binderC230815.sendEvent(1);
                                    break;
                                case 6:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                    break;
                                case 7:
                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                    break;
                                case 8:
                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                    break;
                                default:
                                    OverviewProxyService.BinderC23081 binderC230816 = binderC230814;
                                    binderC230816.getClass();
                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                    intent.addFlags(268468224);
                                    OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                    overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                    break;
                            }
                        }
                    };
                    if (binderC230814.verifyCaller("expandNotificationPanel")) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            runnable2.run();
                        } finally {
                        }
                    }
                } else if (i == 13) {
                    final float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    final OverviewProxyService.BinderC23081 binderC230815 = (OverviewProxyService.BinderC23081) this;
                    binderC230815.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    OverviewProxyService.BinderC23081 binderC230816 = binderC230815;
                                    float f = readFloat;
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onAssistantGestureCompletion(f);
                                        }
                                    }
                                default:
                                    OverviewProxyService.BinderC23081 binderC230817 = binderC230815;
                                    float f2 = readFloat;
                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size2 = arrayList2.size();
                                    while (true) {
                                        size2--;
                                        if (size2 < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onAssistantProgress(f2);
                                        }
                                    }
                            }
                        }
                    }, "onAssistantProgress");
                } else if (i == 14) {
                    final Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    final OverviewProxyService.BinderC23081 binderC230816 = (OverviewProxyService.BinderC23081) this;
                    binderC230816.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    OverviewProxyService.BinderC23081 binderC2308132 = binderC230816;
                                    String str = (String) bundle;
                                    SearcleManager searcleManager = OverviewProxyService.this.mSearcleManager;
                                    searcleManager.invokedPackageName = str;
                                    searcleManager.startSearcleByHomeKey(false, true);
                                    break;
                                case 1:
                                    final OverviewProxyService.BinderC23081 binderC2308142 = binderC230816;
                                    final MotionEvent motionEvent2 = (MotionEvent) bundle;
                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ShadeViewController shadeViewController;
                                            final OverviewProxyService.BinderC23081 binderC2308152 = OverviewProxyService.BinderC23081.this;
                                            final MotionEvent motionEvent3 = motionEvent2;
                                            final CentralSurfaces centralSurfaces = (CentralSurfaces) obj;
                                            binderC2308152.getClass();
                                            if (motionEvent3.getActionMasked() == 0 && (shadeViewController = ((CentralSurfacesImpl) centralSurfaces).getShadeViewController()) != null) {
                                                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
                                                LatencyTracker latencyTracker = notificationPanelViewController.mLatencyTracker;
                                                if (latencyTracker.isEnabled()) {
                                                    latencyTracker.onActionStart(0);
                                                    notificationPanelViewController.mExpandLatencyTracking = true;
                                                }
                                            }
                                            OverviewProxyService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    OverviewProxyService.BinderC23081 binderC2308162 = OverviewProxyService.BinderC23081.this;
                                                    MotionEvent motionEvent4 = motionEvent3;
                                                    CentralSurfaces centralSurfaces2 = centralSurfaces;
                                                    binderC2308162.getClass();
                                                    int actionMasked = motionEvent4.getActionMasked();
                                                    if (actionMasked == 0) {
                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_DOWN");
                                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                                        overviewProxyService.mInputFocusTransferStarted = true;
                                                        overviewProxyService.mInputFocusTransferStartY = motionEvent4.getY();
                                                        OverviewProxyService.this.mInputFocusTransferStartMillis = motionEvent4.getEventTime();
                                                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                                                        centralSurfacesImpl.onInputFocusTransfer(0.0f, OverviewProxyService.this.mInputFocusTransferStarted, false);
                                                        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                                                            centralSurfacesImpl.setNextUpdateHorizontalPosition(motionEvent4.getX());
                                                        }
                                                    }
                                                    if (actionMasked == 1 || actionMasked == 3) {
                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_UP / ACTION_CANCEL");
                                                        OverviewProxyService.this.mInputFocusTransferStarted = false;
                                                        float y = motionEvent4.getY() - OverviewProxyService.this.mInputFocusTransferStartY;
                                                        long eventTime = motionEvent4.getEventTime();
                                                        ((CentralSurfacesImpl) centralSurfaces2).onInputFocusTransfer(y / (eventTime - r0.mInputFocusTransferStartMillis), OverviewProxyService.this.mInputFocusTransferStarted, actionMasked == 3);
                                                    }
                                                    motionEvent4.recycle();
                                                }
                                            });
                                        }
                                    });
                                    break;
                                default:
                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC230816;
                                    Bundle bundle2 = (Bundle) bundle;
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).startAssistant(bundle2);
                                        }
                                    }
                            }
                        }
                    }, "startAssistant");
                } else if (i != 45) {
                    final int i7 = 0;
                    if (i != 46) {
                        switch (i) {
                            case 16:
                                int readInt2 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                OverviewProxyService.BinderC23081 binderC230817 = (OverviewProxyService.BinderC23081) this;
                                if (binderC230817.verifyCaller("notifyAccessibilityButtonClicked")) {
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AccessibilityManager.getInstance(OverviewProxyService.this.mContext).notifyAccessibilityButtonClicked(readInt2);
                                        break;
                                    } finally {
                                    }
                                }
                                break;
                            case 17:
                                final OverviewProxyService.BinderC23081 binderC230818 = (OverviewProxyService.BinderC23081) this;
                                final int i8 = 9;
                                Runnable runnable3 = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i8) {
                                            case 0:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                break;
                                            case 1:
                                                ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                int size = arrayList.size();
                                                while (true) {
                                                    size--;
                                                    if (size < 0) {
                                                        break;
                                                    } else {
                                                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                    }
                                                }
                                            case 2:
                                                ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                break;
                                            case 3:
                                                ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                int size2 = arrayList2.size();
                                                while (true) {
                                                    size2--;
                                                    if (size2 < 0) {
                                                        break;
                                                    } else {
                                                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                    }
                                                }
                                            case 4:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                break;
                                            case 5:
                                                OverviewProxyService.BinderC23081 binderC2308152 = binderC230818;
                                                binderC2308152.sendEvent(0);
                                                binderC2308152.sendEvent(1);
                                                break;
                                            case 6:
                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                break;
                                            case 7:
                                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                break;
                                            case 8:
                                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                break;
                                            default:
                                                OverviewProxyService.BinderC23081 binderC2308162 = binderC230818;
                                                binderC2308162.getClass();
                                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                intent.addFlags(268468224);
                                                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                                overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                                break;
                                        }
                                    }
                                };
                                if (binderC230818.verifyCaller("notifyAccessibilityButtonLongClicked")) {
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        runnable3.run();
                                        break;
                                    } finally {
                                    }
                                }
                                break;
                            case 18:
                                ((OverviewProxyService.BinderC23081) this).verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda7(), "stopScreenPinning");
                                break;
                            case 19:
                                final float readFloat2 = parcel.readFloat();
                                parcel.enforceNoDataAvail();
                                final OverviewProxyService.BinderC23081 binderC230819 = (OverviewProxyService.BinderC23081) this;
                                binderC230819.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda6
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i7) {
                                            case 0:
                                                OverviewProxyService.BinderC23081 binderC2308162 = binderC230819;
                                                float f = readFloat2;
                                                ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                int size = arrayList.size();
                                                while (true) {
                                                    size--;
                                                    if (size < 0) {
                                                        break;
                                                    } else {
                                                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onAssistantGestureCompletion(f);
                                                    }
                                                }
                                            default:
                                                OverviewProxyService.BinderC23081 binderC2308172 = binderC230819;
                                                float f2 = readFloat2;
                                                ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                int size2 = arrayList2.size();
                                                while (true) {
                                                    size2--;
                                                    if (size2 < 0) {
                                                        break;
                                                    } else {
                                                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onAssistantProgress(f2);
                                                    }
                                                }
                                        }
                                    }
                                }, "onAssistantGestureCompletion");
                                break;
                            default:
                                switch (i) {
                                    case 48:
                                        final boolean readBoolean2 = parcel.readBoolean();
                                        final boolean readBoolean3 = parcel.readBoolean();
                                        parcel.enforceNoDataAvail();
                                        final OverviewProxyService.BinderC23081 binderC2308110 = (OverviewProxyService.BinderC23081) this;
                                        binderC2308110.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                OverviewProxyService.BinderC23081 binderC2308111 = OverviewProxyService.BinderC23081.this;
                                                boolean z = readBoolean2;
                                                boolean z2 = readBoolean3;
                                                ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                int size = arrayList.size();
                                                while (true) {
                                                    size--;
                                                    if (size < 0) {
                                                        return;
                                                    } else {
                                                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onTaskbarStatusUpdated(z, z2);
                                                    }
                                                }
                                            }
                                        }, "notifyTaskbarStatus");
                                        break;
                                    case 49:
                                        boolean readBoolean4 = parcel.readBoolean();
                                        parcel.enforceNoDataAvail();
                                        OverviewProxyService.BinderC23081 binderC2308111 = (OverviewProxyService.BinderC23081) this;
                                        binderC2308111.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda5(binderC2308111, readBoolean4, 1), "notifyTaskbarAutohideSuspend");
                                        break;
                                    case 50:
                                        OverviewProxyService.BinderC23081 binderC2308112 = (OverviewProxyService.BinderC23081) this;
                                        InputMethodManager inputMethodManager = (InputMethodManager) OverviewProxyService.this.mContext.getSystemService(InputMethodManager.class);
                                        OverviewProxyService.this.mDisplayTracker.getClass();
                                        inputMethodManager.showInputMethodPickerFromSystem(true, 0);
                                        OverviewProxyService.this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                                        break;
                                    case 51:
                                        final OverviewProxyService.BinderC23081 binderC2308113 = (OverviewProxyService.BinderC23081) this;
                                        binderC2308113.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i4) {
                                                    case 0:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                        break;
                                                    case 1:
                                                        ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                        int size = arrayList.size();
                                                        while (true) {
                                                            size--;
                                                            if (size < 0) {
                                                                break;
                                                            } else {
                                                                ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                            }
                                                        }
                                                    case 2:
                                                        ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                        break;
                                                    case 3:
                                                        ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                        int size2 = arrayList2.size();
                                                        while (true) {
                                                            size2--;
                                                            if (size2 < 0) {
                                                                break;
                                                            } else {
                                                                ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                            }
                                                        }
                                                    case 4:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                        break;
                                                    case 5:
                                                        OverviewProxyService.BinderC23081 binderC2308152 = binderC2308113;
                                                        binderC2308152.sendEvent(0);
                                                        binderC2308152.sendEvent(1);
                                                        break;
                                                    case 6:
                                                        OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                        break;
                                                    case 7:
                                                        ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                        break;
                                                    case 8:
                                                        OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                        break;
                                                    default:
                                                        OverviewProxyService.BinderC23081 binderC2308162 = binderC2308113;
                                                        binderC2308162.getClass();
                                                        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                        intent.addFlags(268468224);
                                                        OverviewProxyService overviewProxyService = OverviewProxyService.this;
                                                        overviewProxyService.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService.mUserTracker).getUserHandle());
                                                        break;
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
                                    default:
                                        switch (i) {
                                            case 101:
                                                OverviewProxyService.BinderC23081 binderC2308114 = (OverviewProxyService.BinderC23081) this;
                                                if (BasicRune.NAVBAR_GESTURE) {
                                                    OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                                                    ((NavBarStoreImpl) overviewProxyService2.mNavBarStore).handleEvent(overviewProxyService2, new EventTypeFactory.EventType.ResetBottomGestureHintVI());
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 102:
                                                int readInt3 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                OverviewProxyService.BinderC23081 binderC2308115 = (OverviewProxyService.BinderC23081) this;
                                                if (BasicRune.NAVBAR_GESTURE) {
                                                    OverviewProxyService overviewProxyService3 = OverviewProxyService.this;
                                                    ((NavBarStoreImpl) overviewProxyService3.mNavBarStore).handleEvent(overviewProxyService3, new EventTypeFactory.EventType.StartBottomGestureHintVI(readInt3));
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 103:
                                                int readInt4 = parcel.readInt();
                                                int readInt5 = parcel.readInt();
                                                int readInt6 = parcel.readInt();
                                                long readLong = parcel.readLong();
                                                parcel.enforceNoDataAvail();
                                                OverviewProxyService.BinderC23081 binderC2308116 = (OverviewProxyService.BinderC23081) this;
                                                if (BasicRune.NAVBAR_GESTURE) {
                                                    OverviewProxyService overviewProxyService4 = OverviewProxyService.this;
                                                    ((NavBarStoreImpl) overviewProxyService4.mNavBarStore).handleEvent(overviewProxyService4, new EventTypeFactory.EventType.MoveBottomGestureHintDistance(readInt4, readInt5, readInt6, readLong));
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 104:
                                                if (((OverviewProxyService.BinderC23081) this).verifyCaller("notifyOnLongPressRecentsWithMultiStar")) {
                                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                                    try {
                                                        ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                                                        PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                                                        if (pluginMultiStar != null) {
                                                            MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 105:
                                                final OverviewProxyService.BinderC23081 binderC2308117 = (OverviewProxyService.BinderC23081) this;
                                                binderC2308117.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        switch (i3) {
                                                            case 0:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                break;
                                                            case 1:
                                                                ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                int size = arrayList.size();
                                                                while (true) {
                                                                    size--;
                                                                    if (size < 0) {
                                                                        break;
                                                                    } else {
                                                                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                    }
                                                                }
                                                            case 2:
                                                                ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                break;
                                                            case 3:
                                                                ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                int size2 = arrayList2.size();
                                                                while (true) {
                                                                    size2--;
                                                                    if (size2 < 0) {
                                                                        break;
                                                                    } else {
                                                                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                    }
                                                                }
                                                            case 4:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                break;
                                                            case 5:
                                                                OverviewProxyService.BinderC23081 binderC2308152 = binderC2308117;
                                                                binderC2308152.sendEvent(0);
                                                                binderC2308152.sendEvent(1);
                                                                break;
                                                            case 6:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                break;
                                                            case 7:
                                                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                break;
                                                            case 8:
                                                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                break;
                                                            default:
                                                                OverviewProxyService.BinderC23081 binderC2308162 = binderC2308117;
                                                                binderC2308162.getClass();
                                                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                intent.addFlags(268468224);
                                                                OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                                                                overviewProxyService5.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService5.mUserTracker).getUserHandle());
                                                                break;
                                                        }
                                                    }
                                                }, "notifyTaskbarNavigationBarInitialized");
                                                parcel2.writeNoException();
                                                break;
                                            case 106:
                                                final OverviewProxyService.BinderC23081 binderC2308118 = (OverviewProxyService.BinderC23081) this;
                                                final int i9 = 3;
                                                binderC2308118.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        switch (i9) {
                                                            case 0:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                break;
                                                            case 1:
                                                                ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                int size = arrayList.size();
                                                                while (true) {
                                                                    size--;
                                                                    if (size < 0) {
                                                                        break;
                                                                    } else {
                                                                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                    }
                                                                }
                                                            case 2:
                                                                ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                break;
                                                            case 3:
                                                                ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                int size2 = arrayList2.size();
                                                                while (true) {
                                                                    size2--;
                                                                    if (size2 < 0) {
                                                                        break;
                                                                    } else {
                                                                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                    }
                                                                }
                                                            case 4:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                break;
                                                            case 5:
                                                                OverviewProxyService.BinderC23081 binderC2308152 = binderC2308118;
                                                                binderC2308152.sendEvent(0);
                                                                binderC2308152.sendEvent(1);
                                                                break;
                                                            case 6:
                                                                OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                break;
                                                            case 7:
                                                                ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                break;
                                                            case 8:
                                                                OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                break;
                                                            default:
                                                                OverviewProxyService.BinderC23081 binderC2308162 = binderC2308118;
                                                                binderC2308162.getClass();
                                                                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                intent.addFlags(268468224);
                                                                OverviewProxyService overviewProxyService5 = OverviewProxyService.this;
                                                                overviewProxyService5.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService5.mUserTracker).getUserHandle());
                                                                break;
                                                        }
                                                    }
                                                }, "notifyTaskbarSPluginButtonClicked");
                                                parcel2.writeNoException();
                                                break;
                                            case 107:
                                                boolean readBoolean5 = parcel.readBoolean();
                                                parcel.enforceNoDataAvail();
                                                OverviewProxyService.BinderC23081 binderC2308119 = (OverviewProxyService.BinderC23081) this;
                                                FgsManagerController fgsManagerController = OverviewProxyService.this.mFgsManagerController;
                                                if (fgsManagerController != null) {
                                                    if (readBoolean5) {
                                                        OverviewProxyService$1$$ExternalSyntheticLambda4 overviewProxyService$1$$ExternalSyntheticLambda4 = binderC2308119.mOnNumberOfPackagesChangedListener;
                                                        FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) fgsManagerController;
                                                        synchronized (fgsManagerControllerImpl.lock) {
                                                            fgsManagerControllerImpl.onNumberOfPackagesChangedListeners.add(overviewProxyService$1$$ExternalSyntheticLambda4);
                                                        }
                                                        fgsManagerControllerImpl.secFgsManagerController.log("addOnNumberOfPackagesChangedListener");
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
                                                        OverviewProxyService$1$$ExternalSyntheticLambda4 overviewProxyService$1$$ExternalSyntheticLambda42 = binderC2308119.mOnNumberOfPackagesChangedListener;
                                                        FgsManagerControllerImpl fgsManagerControllerImpl2 = (FgsManagerControllerImpl) fgsManagerController;
                                                        fgsManagerControllerImpl2.secFgsManagerController.log("removeOnNumberOfPackagesChangedListener");
                                                        synchronized (fgsManagerControllerImpl2.lock) {
                                                            fgsManagerControllerImpl2.onNumberOfPackagesChangedListeners.remove(overviewProxyService$1$$ExternalSyntheticLambda42);
                                                        }
                                                    }
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 108:
                                                final OverviewProxyService.BinderC23081 binderC2308120 = (OverviewProxyService.BinderC23081) this;
                                                if (OverviewProxyService.this.mFgsManagerController != null) {
                                                    binderC2308120.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i5) {
                                                                case 0:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                    break;
                                                                case 1:
                                                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size = arrayList.size();
                                                                    while (true) {
                                                                        size--;
                                                                        if (size < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                        }
                                                                    }
                                                                case 2:
                                                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                    break;
                                                                case 3:
                                                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size2 = arrayList2.size();
                                                                    while (true) {
                                                                        size2--;
                                                                        if (size2 < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                        }
                                                                    }
                                                                case 4:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                    break;
                                                                case 5:
                                                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308120;
                                                                    binderC2308152.sendEvent(0);
                                                                    binderC2308152.sendEvent(1);
                                                                    break;
                                                                case 6:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                    break;
                                                                case 7:
                                                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                    break;
                                                                case 8:
                                                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                    break;
                                                                default:
                                                                    OverviewProxyService.BinderC23081 binderC2308162 = binderC2308120;
                                                                    binderC2308162.getClass();
                                                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                    intent.addFlags(268468224);
                                                                    OverviewProxyService overviewProxyService52 = OverviewProxyService.this;
                                                                    overviewProxyService52.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService52.mUserTracker).getUserHandle());
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 109:
                                                final OverviewProxyService.BinderC23081 binderC2308121 = (OverviewProxyService.BinderC23081) this;
                                                Log.d("OverviewProxyService", "startSearcle");
                                                if (BasicRune.SEARCLE) {
                                                    final int i10 = 4;
                                                    binderC2308121.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i10) {
                                                                case 0:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                    break;
                                                                case 1:
                                                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size = arrayList.size();
                                                                    while (true) {
                                                                        size--;
                                                                        if (size < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                        }
                                                                    }
                                                                case 2:
                                                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                    break;
                                                                case 3:
                                                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size2 = arrayList2.size();
                                                                    while (true) {
                                                                        size2--;
                                                                        if (size2 < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                        }
                                                                    }
                                                                case 4:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                    break;
                                                                case 5:
                                                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308121;
                                                                    binderC2308152.sendEvent(0);
                                                                    binderC2308152.sendEvent(1);
                                                                    break;
                                                                case 6:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                    break;
                                                                case 7:
                                                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                    break;
                                                                case 8:
                                                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                    break;
                                                                default:
                                                                    OverviewProxyService.BinderC23081 binderC2308162 = binderC2308121;
                                                                    binderC2308162.getClass();
                                                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                    intent.addFlags(268468224);
                                                                    OverviewProxyService overviewProxyService52 = OverviewProxyService.this;
                                                                    overviewProxyService52.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService52.mUserTracker).getUserHandle());
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 110:
                                                final OverviewProxyService.BinderC23081 binderC2308122 = (OverviewProxyService.BinderC23081) this;
                                                Log.d("OverviewProxyService", "invokeSearcle");
                                                if (BasicRune.SEARCLE) {
                                                    binderC2308122.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i7) {
                                                                case 0:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                    break;
                                                                case 1:
                                                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size = arrayList.size();
                                                                    while (true) {
                                                                        size--;
                                                                        if (size < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                        }
                                                                    }
                                                                case 2:
                                                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                    break;
                                                                case 3:
                                                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size2 = arrayList2.size();
                                                                    while (true) {
                                                                        size2--;
                                                                        if (size2 < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                        }
                                                                    }
                                                                case 4:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                    break;
                                                                case 5:
                                                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308122;
                                                                    binderC2308152.sendEvent(0);
                                                                    binderC2308152.sendEvent(1);
                                                                    break;
                                                                case 6:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                    break;
                                                                case 7:
                                                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                    break;
                                                                case 8:
                                                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                    break;
                                                                default:
                                                                    OverviewProxyService.BinderC23081 binderC2308162 = binderC2308122;
                                                                    binderC2308162.getClass();
                                                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                    intent.addFlags(268468224);
                                                                    OverviewProxyService overviewProxyService52 = OverviewProxyService.this;
                                                                    overviewProxyService52.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService52.mUserTracker).getUserHandle());
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 111:
                                                final OverviewProxyService.BinderC23081 binderC2308123 = (OverviewProxyService.BinderC23081) this;
                                                Log.d("OverviewProxyService", "cancelSearcle");
                                                if (BasicRune.SEARCLE) {
                                                    final int i11 = 6;
                                                    binderC2308123.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i11) {
                                                                case 0:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                                                    break;
                                                                case 1:
                                                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size = arrayList.size();
                                                                    while (true) {
                                                                        size--;
                                                                        if (size < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                                                        }
                                                                    }
                                                                case 2:
                                                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                                                    break;
                                                                case 3:
                                                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size2 = arrayList2.size();
                                                                    while (true) {
                                                                        size2--;
                                                                        if (size2 < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                                                        }
                                                                    }
                                                                case 4:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                                                    break;
                                                                case 5:
                                                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308123;
                                                                    binderC2308152.sendEvent(0);
                                                                    binderC2308152.sendEvent(1);
                                                                    break;
                                                                case 6:
                                                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                                                    break;
                                                                case 7:
                                                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                                                    break;
                                                                case 8:
                                                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                                                    break;
                                                                default:
                                                                    OverviewProxyService.BinderC23081 binderC2308162 = binderC2308123;
                                                                    binderC2308162.getClass();
                                                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                                                    intent.addFlags(268468224);
                                                                    OverviewProxyService overviewProxyService52 = OverviewProxyService.this;
                                                                    overviewProxyService52.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService52.mUserTracker).getUserHandle());
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            case 112:
                                                final String readString = parcel.readString();
                                                parcel.enforceNoDataAvail();
                                                final OverviewProxyService.BinderC23081 binderC2308124 = (OverviewProxyService.BinderC23081) this;
                                                AbstractC0000x2c234b15.m3m("invokeSearcleWithPackageName packageName = ", readString, "OverviewProxyService");
                                                if (BasicRune.SEARCLE) {
                                                    binderC2308124.mMainHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            switch (i7) {
                                                                case 0:
                                                                    OverviewProxyService.BinderC23081 binderC2308132 = binderC2308124;
                                                                    String str = (String) readString;
                                                                    SearcleManager searcleManager = OverviewProxyService.this.mSearcleManager;
                                                                    searcleManager.invokedPackageName = str;
                                                                    searcleManager.startSearcleByHomeKey(false, true);
                                                                    break;
                                                                case 1:
                                                                    final OverviewProxyService.BinderC23081 binderC2308142 = binderC2308124;
                                                                    final MotionEvent motionEvent2 = (MotionEvent) readString;
                                                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10
                                                                        @Override // java.util.function.Consumer
                                                                        public final void accept(Object obj) {
                                                                            ShadeViewController shadeViewController;
                                                                            final OverviewProxyService.BinderC23081 binderC2308152 = OverviewProxyService.BinderC23081.this;
                                                                            final MotionEvent motionEvent3 = motionEvent2;
                                                                            final CentralSurfaces centralSurfaces = (CentralSurfaces) obj;
                                                                            binderC2308152.getClass();
                                                                            if (motionEvent3.getActionMasked() == 0 && (shadeViewController = ((CentralSurfacesImpl) centralSurfaces).getShadeViewController()) != null) {
                                                                                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
                                                                                LatencyTracker latencyTracker = notificationPanelViewController.mLatencyTracker;
                                                                                if (latencyTracker.isEnabled()) {
                                                                                    latencyTracker.onActionStart(0);
                                                                                    notificationPanelViewController.mExpandLatencyTracking = true;
                                                                                }
                                                                            }
                                                                            OverviewProxyService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12
                                                                                @Override // java.lang.Runnable
                                                                                public final void run() {
                                                                                    OverviewProxyService.BinderC23081 binderC2308162 = OverviewProxyService.BinderC23081.this;
                                                                                    MotionEvent motionEvent4 = motionEvent3;
                                                                                    CentralSurfaces centralSurfaces2 = centralSurfaces;
                                                                                    binderC2308162.getClass();
                                                                                    int actionMasked = motionEvent4.getActionMasked();
                                                                                    if (actionMasked == 0) {
                                                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_DOWN");
                                                                                        OverviewProxyService overviewProxyService6 = OverviewProxyService.this;
                                                                                        overviewProxyService6.mInputFocusTransferStarted = true;
                                                                                        overviewProxyService6.mInputFocusTransferStartY = motionEvent4.getY();
                                                                                        OverviewProxyService.this.mInputFocusTransferStartMillis = motionEvent4.getEventTime();
                                                                                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces2;
                                                                                        centralSurfacesImpl.onInputFocusTransfer(0.0f, OverviewProxyService.this.mInputFocusTransferStarted, false);
                                                                                        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
                                                                                            centralSurfacesImpl.setNextUpdateHorizontalPosition(motionEvent4.getX());
                                                                                        }
                                                                                    }
                                                                                    if (actionMasked == 1 || actionMasked == 3) {
                                                                                        Log.d("OverviewProxyService", "onStatusBarMotionEvent ACTION_UP / ACTION_CANCEL");
                                                                                        OverviewProxyService.this.mInputFocusTransferStarted = false;
                                                                                        float y = motionEvent4.getY() - OverviewProxyService.this.mInputFocusTransferStartY;
                                                                                        long eventTime = motionEvent4.getEventTime();
                                                                                        ((CentralSurfacesImpl) centralSurfaces2).onInputFocusTransfer(y / (eventTime - r0.mInputFocusTransferStartMillis), OverviewProxyService.this.mInputFocusTransferStarted, actionMasked == 3);
                                                                                    }
                                                                                    motionEvent4.recycle();
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                    break;
                                                                default:
                                                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308124;
                                                                    Bundle bundle2 = (Bundle) readString;
                                                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                                                    int size = arrayList.size();
                                                                    while (true) {
                                                                        size--;
                                                                        if (size < 0) {
                                                                            break;
                                                                        } else {
                                                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).startAssistant(bundle2);
                                                                        }
                                                                    }
                                                            }
                                                        }
                                                    });
                                                }
                                                parcel2.writeNoException();
                                                break;
                                            default:
                                                return super.onTransact(i, parcel, parcel2, i2);
                                        }
                                }
                        }
                    } else {
                        boolean readBoolean6 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        OverviewProxyService.BinderC23081 binderC2308125 = (OverviewProxyService.BinderC23081) this;
                        binderC2308125.verifyCallerAndClearCallingIdentityPostMain(new OverviewProxyService$1$$ExternalSyntheticLambda5(binderC2308125, readBoolean6, 0), "setHomeRotationEnabled");
                    }
                } else {
                    final OverviewProxyService.BinderC23081 binderC2308126 = (OverviewProxyService.BinderC23081) this;
                    final int i12 = 5;
                    binderC2308126.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i12) {
                                case 0:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                                    break;
                                case 1:
                                    ArrayList arrayList = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size = arrayList.size();
                                    while (true) {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onInitializedTaskbarNavigationBar();
                                        }
                                    }
                                case 2:
                                    ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda11());
                                    break;
                                case 3:
                                    ArrayList arrayList2 = (ArrayList) OverviewProxyService.this.mConnectionCallbacks;
                                    int size2 = arrayList2.size();
                                    while (true) {
                                        size2--;
                                        if (size2 < 0) {
                                            break;
                                        } else {
                                            ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size2)).onTaskbarSPluginButtonClicked();
                                        }
                                    }
                                case 4:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                                    break;
                                case 5:
                                    OverviewProxyService.BinderC23081 binderC2308152 = binderC2308126;
                                    binderC2308152.sendEvent(0);
                                    binderC2308152.sendEvent(1);
                                    break;
                                case 6:
                                    OverviewProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                                    break;
                                case 7:
                                    ((FgsManagerControllerImpl) OverviewProxyService.this.mFgsManagerController).showDialog();
                                    break;
                                case 8:
                                    OverviewProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                                    break;
                                default:
                                    OverviewProxyService.BinderC23081 binderC2308162 = binderC2308126;
                                    binderC2308162.getClass();
                                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                                    intent.addFlags(268468224);
                                    OverviewProxyService overviewProxyService52 = OverviewProxyService.this;
                                    overviewProxyService52.mContext.startActivityAsUser(intent, ((UserTrackerImpl) overviewProxyService52.mUserTracker).getUserHandle());
                                    break;
                            }
                        }
                    }, "onBackPressed");
                }
            } else {
                final int readInt7 = parcel.readInt();
                final boolean readBoolean7 = parcel.readBoolean();
                final String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                final OverviewProxyService.BinderC23081 binderC2308127 = (OverviewProxyService.BinderC23081) this;
                binderC2308127.verifyCallerAndClearCallingIdentityPostMain(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverviewProxyService.BinderC23081 binderC2308128 = OverviewProxyService.BinderC23081.this;
                        final int i13 = readInt7;
                        final boolean z = readBoolean7;
                        final String str = readString2;
                        ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda9
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) ((CentralSurfaces) obj);
                                centralSurfacesImpl.showScreenPinningRequest(i13, str, z);
                            }
                        });
                    }
                }, "startScreenPinning");
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
