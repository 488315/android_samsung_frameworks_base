package com.android.systemui.keyevent.domain.interactor;

import android.content.Context;
import android.media.session.MediaSessionLegacyHelper;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.Dependency;
import com.android.systemui.back.domain.interactor.BackActionInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SysUIKeyEventHandler {
    public static final Companion Companion = new Companion(null);
    public final BackActionInteractor backActionInteractor;
    public final KeyguardKeyEventInteractor keyguardKeyEventInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SysUIKeyEventHandler(BackActionInteractor backActionInteractor, KeyguardKeyEventInteractor keyguardKeyEventInteractor) {
        this.backActionInteractor = backActionInteractor;
        this.keyguardKeyEventInteractor = keyguardKeyEventInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean zDispatchMenuKeyEvent;
        int keyCode;
        KeyguardKeyEventInteractor keyguardKeyEventInteractor = this.keyguardKeyEventInteractor;
        StatusBarStateController statusBarStateController = keyguardKeyEventInteractor.statusBarStateController;
        boolean zIsDozing = statusBarStateController.isDozing();
        Companion companion = Companion;
        if (!zIsDozing || ((keyCode = keyEvent.getKeyCode()) != 24 && keyCode != 25)) {
            companion.getClass();
            if (keyEvent.getAction() == 0) {
                zDispatchMenuKeyEvent = false;
            } else {
                if (KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && ((WakefulnessModel) keyguardKeyEventInteractor.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).isAwake()) {
                    int state = statusBarStateController.getState();
                    if (state == 1) {
                        keyguardKeyEventInteractor.statusBarKeyguardViewManager.showPrimaryBouncer("KeyguardKeyEventInteractor#collapseShadeLockedOrShowPrimaryBouncer", true);
                    } else if (state == 2) {
                        keyguardKeyEventInteractor.shadeController.animateCollapseShade(1.0f, 0, true, false);
                    }
                } else if (keyEvent.getKeyCode() == 82) {
                    zDispatchMenuKeyEvent = keyguardKeyEventInteractor.dispatchMenuKeyEvent();
                }
                zDispatchMenuKeyEvent = false;
            }
            if (!zDispatchMenuKeyEvent) {
                if (keyEvent.getKeyCode() != 4) {
                    return false;
                }
                BackActionInteractor backActionInteractor = this.backActionInteractor;
                if (!backActionInteractor.isCallbackRegistered) {
                    companion.getClass();
                    if (keyEvent.getAction() != 0 && (!backActionInteractor.statusBarKeyguardViewManager.isBouncerShowing() || !keyEvent.isCanceled())) {
                        backActionInteractor.onBackRequested();
                    }
                }
            }
            return true;
        }
        Context context = keyguardKeyEventInteractor.context;
        keyguardKeyEventInteractor.mediaSessionLegacyHelperWrapper.getClass();
        MediaSessionLegacyHelper.getHelper(context).sendVolumeKeyEvent(keyEvent, Integer.MIN_VALUE, true);
        zDispatchMenuKeyEvent = true;
        if (!zDispatchMenuKeyEvent) {
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0196  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean interceptMediaKey(final KeyEvent keyEvent) {
        boolean z;
        int i;
        boolean zInterceptMediaKey;
        boolean z2;
        boolean z3;
        boolean zIsSideSyncEnabled;
        boolean zInterceptRestKey;
        final KeyguardKeyEventInteractor keyguardKeyEventInteractor = this.keyguardKeyEventInteractor;
        StatusBarStateController statusBarStateController = keyguardKeyEventInteractor.statusBarStateController;
        int state = statusBarStateController.getState();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardKeyEventInteractor.statusBarKeyguardViewManager;
        if (state == 1 || statusBarStateController.getState() == 2 || statusBarKeyguardViewManager.isBouncerShowing()) {
            KeyguardSysDumpTrigger keyguardSysDumpTrigger = keyguardKeyEventInteractor.sysDumpTrigger;
            if (keyguardSysDumpTrigger.isEnabled()) {
                int keyCode = keyEvent.getKeyCode();
                int action = keyEvent.getAction();
                long eventTime = keyEvent.getEventTime();
                int[] iArr = KeyguardSysDumpTrigger.KEY;
                if (action != 0) {
                    z = (action == 1 && (i = keyguardSysDumpTrigger.keyIndex) > 0 && keyCode == iArr[i - 1]) ? false : true;
                } else {
                    if (keyguardSysDumpTrigger.wakefulnessLifecycle.mWakefulness == 2) {
                        int i2 = keyguardSysDumpTrigger.keyIndex;
                        if (keyCode == iArr[i2] && (i2 == 0 || eventTime - keyguardSysDumpTrigger.prevEventTime <= 800)) {
                            keyguardSysDumpTrigger.prevEventTime = eventTime;
                            keyguardSysDumpTrigger.keyIndex = i2 + 1;
                        }
                    }
                }
                if (keyguardSysDumpTrigger.isDebug) {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(action, keyguardSysDumpTrigger.keyIndex, "interceptKey action=", " index=", " reset="), z, "KeyguardSysDumpTrigger");
                }
                if (action == 0 && keyguardSysDumpTrigger.keyIndex % (iArr.length / 2) == 1) {
                    keyguardSysDumpTrigger.powerManager.userActivity(SystemClock.uptimeMillis(), false);
                }
                if (action == 0 && keyguardSysDumpTrigger.keyIndex == iArr.length) {
                    Log.d("KeyguardSysDumpTrigger", "matched keys");
                    keyguardSysDumpTrigger.start(1, 0L, System.currentTimeMillis());
                    z = true;
                }
                if (z) {
                    keyguardSysDumpTrigger.keyIndex = 0;
                    keyguardSysDumpTrigger.prevEventTime = 0L;
                }
            }
        }
        if (statusBarStateController.getState() == 1) {
            ComposeBouncerFlags.INSTANCE.getClass();
            zInterceptMediaKey = statusBarKeyguardViewManager.interceptMediaKey(keyEvent);
        } else {
            zInterceptMediaKey = false;
        }
        if (!zInterceptMediaKey) {
            boolean z4 = ArrayUtils.indexOf(keyguardKeyEventInteractor.IGNORED_EXT_KEYCODE, Integer.valueOf(keyEvent.getKeyCode())) == -1;
            Boolean boolValueOf = Boolean.valueOf(z4);
            StatusBarStateController statusBarStateController2 = keyguardKeyEventInteractor.statusBarStateController;
            LogUtil.d("CentralSurfaces", "interceptRestKey isRestKey=%s event=%s, state=%d, isDozing=%s", boolValueOf, keyEvent, Integer.valueOf(statusBarStateController2.getState()), Boolean.valueOf(statusBarStateController2.isDozing()));
            if (statusBarStateController2.getState() == 1 && !statusBarKeyguardViewManager.isBouncerShowing() && z4) {
                final boolean[] zArr = {false};
                if (keyEvent.getAction() == 0) {
                    if (keyguardKeyEventInteractor.mIsKeyDownInDozing == null) {
                        keyguardKeyEventInteractor.mIsKeyDownInDozing = Boolean.valueOf(statusBarStateController2.isDozing());
                    }
                    if (statusBarStateController2.isDozing()) {
                        keyguardKeyEventInteractor.mKeyUpCountInDozing = 0;
                    }
                    if (keyEvent.getRepeatCount() > 0 && keyguardKeyEventInteractor.mIsKeyDownInDozing.booleanValue() && !statusBarStateController2.isDozing() && keyguardKeyEventInteractor.mKeyUpCountInDozing > 0) {
                        keyguardKeyEventInteractor.mKeyUpCountInDozing = 0;
                    }
                }
                if (keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                    int i3 = keyguardKeyEventInteractor.displayLifecycle.mPreviousState;
                    Boolean bool = keyguardKeyEventInteractor.mIsKeyDownInDozing;
                    if ((bool != null || i3 == 1) && (bool == null || bool.equals(Boolean.valueOf(statusBarStateController2.isDozing())))) {
                        if (statusBarStateController2.isDozing() && keyguardKeyEventInteractor.mKeyUpCountInDozing == 0) {
                            Log.d("CentralSurfaces", "interceptRestKey : ignore screen on ACTION_UP");
                            keyguardKeyEventInteractor.mKeyUpCountInDozing++;
                            keyguardKeyEventInteractor.mIsKeyDownInDozing = null;
                        }
                        InputDevice device = keyEvent.getDevice();
                        if (device == null) {
                            zIsSideSyncEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isSideSyncEnabled();
                            if (keyEvent.getAction() == 1) {
                                keyguardKeyEventInteractor.mIsKeyDownInDozing = null;
                                if (!z3) {
                                    LogUtil.d("CentralSurfaces", "interceptRestKey isExt=%s, sideSync=%s", Boolean.valueOf(z3), Boolean.valueOf(zIsSideSyncEnabled));
                                    Optional optionalOfNullable = Optional.ofNullable((InputMethodManager) keyguardKeyEventInteractor.context.getSystemService("input_method"));
                                    final Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            KeyEvent keyEvent2 = keyEvent;
                                            if (((InputMethodManager) obj).isAccessoryKeyboardState() == 0) {
                                                return Unit.INSTANCE;
                                            }
                                            zArr[0] = true;
                                            if (keyEvent2.isCanceled()) {
                                                return Unit.INSTANCE;
                                            }
                                            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_KEYBOARD);
                                            Log.d("CentralSurfaces", "interceptRestKey call dismiss");
                                            keyguardKeyEventInteractor.dispatchMenuKeyEvent();
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    optionalOfNullable.ifPresent(new Consumer() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor$sam$java_util_function_Consumer$0
                                        @Override // java.util.function.Consumer
                                        public final /* synthetic */ void accept(Object obj) {
                                            function1.mo781invoke(obj);
                                        }
                                    });
                                }
                            }
                            if (!z3) {
                                zInterceptRestKey = statusBarKeyguardViewManager.interceptRestKey(keyEvent);
                                zArr[0] = zInterceptRestKey;
                                if (zInterceptRestKey) {
                                    PowerInteractor.onUserTouch$default(keyguardKeyEventInteractor.powerInteractor);
                                }
                            }
                            z2 = zArr[0];
                        }
                    } else {
                        Log.d("CentralSurfaces", "interceptRestKey : reset state");
                        keyguardKeyEventInteractor.mKeyUpCountInDozing = 0;
                        keyguardKeyEventInteractor.mIsKeyDownInDozing = null;
                    }
                    z2 = true;
                } else {
                    InputDevice device2 = keyEvent.getDevice();
                    z3 = device2 == null && device2.isExternal();
                    zIsSideSyncEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isSideSyncEnabled();
                    if (keyEvent.getAction() == 1 && (!statusBarStateController2.isDozing() || keyguardKeyEventInteractor.mKeyUpCountInDozing > 0)) {
                        keyguardKeyEventInteractor.mIsKeyDownInDozing = null;
                        if (!z3 || zIsSideSyncEnabled) {
                            LogUtil.d("CentralSurfaces", "interceptRestKey isExt=%s, sideSync=%s", Boolean.valueOf(z3), Boolean.valueOf(zIsSideSyncEnabled));
                            Optional optionalOfNullable2 = Optional.ofNullable((InputMethodManager) keyguardKeyEventInteractor.context.getSystemService("input_method"));
                            final Function1 function12 = new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    KeyEvent keyEvent2 = keyEvent;
                                    if (((InputMethodManager) obj).isAccessoryKeyboardState() == 0) {
                                        return Unit.INSTANCE;
                                    }
                                    zArr[0] = true;
                                    if (keyEvent2.isCanceled()) {
                                        return Unit.INSTANCE;
                                    }
                                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_KEYBOARD);
                                    Log.d("CentralSurfaces", "interceptRestKey call dismiss");
                                    keyguardKeyEventInteractor.dispatchMenuKeyEvent();
                                    return Unit.INSTANCE;
                                }
                            };
                            optionalOfNullable2.ifPresent(new Consumer() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor$sam$java_util_function_Consumer$0
                                @Override // java.util.function.Consumer
                                public final /* synthetic */ void accept(Object obj) {
                                    function12.mo781invoke(obj);
                                }
                            });
                        }
                    }
                    if (!z3 && !zIsSideSyncEnabled && statusBarStateController2.getState() == 1) {
                        zInterceptRestKey = statusBarKeyguardViewManager.interceptRestKey(keyEvent);
                        zArr[0] = zInterceptRestKey;
                        if (zInterceptRestKey && keyEvent.getAction() == 0) {
                            PowerInteractor.onUserTouch$default(keyguardKeyEventInteractor.powerInteractor);
                        }
                    }
                    z2 = zArr[0];
                }
            } else {
                z2 = false;
            }
            if (!z2) {
                return false;
            }
        }
        return true;
    }
}
