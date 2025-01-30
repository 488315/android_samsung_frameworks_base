package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardShortcutManager extends KeyguardUpdateMonitorCallback implements SettingsHelper.OnChangedCallback, Dumpable {
    public final BroadcastDispatcher broadcastDispatcher;
    public int dndShortcutIndex;
    public boolean isDndCallbackAdded;
    public boolean isLockTaskMode;
    public final Context mContext;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final boolean mHasSPenFeature;
    public int mIconSize;
    public final boolean mIsFlashlightSupported;
    public boolean mIsPermDisabled;
    public final boolean mIsTablet;
    public final KeyguardStateController mKeyguardStateController;
    public final PackageManager mPm;
    public final SettingsHelper mSettingsHelper;
    public boolean mShortcutVisibleForMDM;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final Set taskConfigs;
    public final UserSwitcherController userSwitcherController;
    public static final Companion Companion = new Companion(null);
    public static final String DEF_SHORTCUT = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigDefAppShortcutForLockScreen");
    public static final String[] SAMSUNG_LIVE_ICON_PACKAGES = {"com.samsung.android.calendar", "com.android.calendar", "com.sec.android.app.clockpackage"};
    public static final Intent SECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent INSECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent SAMSUNG_EXPERT_RAW_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.samsung.android.app.galaxyraw", "com.samsung.android.app.galaxyraw.GalaxyRaw");
    public static final Intent PHONE_INTENT = new Intent("android.intent.action.DIAL").setClassName("com.samsung.android.dialer", "com.samsung.android.dialer.DialtactsActivity");
    public static final KeyguardShortcutManager$Companion$EMPTY_CONFIG$1 EMPTY_CONFIG = new KeyguardQuickAffordanceConfig() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$Companion$EMPTY_CONFIG$1
        public final String key = "";
        public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 lockScreenState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final String getKey() {
            return this.key;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final Flow getLockScreenState() {
            return this.lockScreenState;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final int getPickerIconResourceId() {
            return 0;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final Object getPickerScreenState(Continuation continuation) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final String pickerName() {
            return "";
        }
    };
    public final KeyguardQuickAffordanceConfig[] mKeyguardBottomAreaShortcutTask = new KeyguardQuickAffordanceConfig[2];
    public final ShortcutData[] mShortcuts = new ShortcutData[2];
    public final StringBuilder mSb = new StringBuilder();
    public final ArrayList mCallbacks = new ArrayList();
    public final KeyguardShortcutManager$mUpdateShortcutsRunnable$1 mUpdateShortcutsRunnable = new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$mUpdateShortcutsRunnable$1
        /* JADX WARN: Removed duplicated region for block: B:35:0x0194  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            Collection collection;
            final KeyguardShortcutManager keyguardShortcutManager = KeyguardShortcutManager.this;
            String stringValue = keyguardShortcutManager.mSettingsHelper.mItemLists.get("lock_application_shortcut").getStringValue();
            boolean isEmpty = TextUtils.isEmpty(stringValue);
            KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager.mShortcuts;
            if (isEmpty) {
                List listOf = CollectionsKt__CollectionsKt.listOf(0, 1);
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
                Iterator it = listOf.iterator();
                while (it.hasNext()) {
                    KeyguardShortcutManager.ShortcutData shortcutData = shortcutDataArr[((Number) it.next()).intValue()];
                    Intrinsics.checkNotNull(shortcutData);
                    shortcutData.taskName = null;
                    shortcutData.mComponentName = null;
                    shortcutData.enabled = false;
                    arrayList.add(Unit.INSTANCE);
                }
            } else {
                StringBuilder sb = keyguardShortcutManager.mSb;
                sb.setLength(0);
                int length = stringValue.length();
                for (int i = 0; i < length; i++) {
                    if (i % 5 == 0) {
                        sb.append((char) (stringValue.codePointAt(i) + 1));
                    } else {
                        sb.append(stringValue.charAt(i));
                    }
                }
                List split = new Regex(";").split(stringValue);
                if (!split.isEmpty()) {
                    ListIterator listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (!(((String) listIterator.previous()).length() == 0)) {
                            collection = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                collection = EmptyList.INSTANCE;
                String[] strArr = (String[]) collection.toArray(new String[0]);
                if (strArr.length < 2) {
                    List listOf2 = CollectionsKt__CollectionsKt.listOf(0, 1);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf2, 10));
                    Iterator it2 = listOf2.iterator();
                    while (it2.hasNext()) {
                        KeyguardShortcutManager.ShortcutData shortcutData2 = shortcutDataArr[((Number) it2.next()).intValue()];
                        Intrinsics.checkNotNull(shortcutData2);
                        shortcutData2.taskName = null;
                        shortcutData2.mComponentName = null;
                        shortcutData2.enabled = false;
                        arrayList2.add(Unit.INSTANCE);
                    }
                    Unit unit = Unit.INSTANCE;
                    for (final int i2 = 0; i2 < 2; i2++) {
                        boolean isTaskType = keyguardShortcutManager.isTaskType(i2);
                        Executor executor = keyguardShortcutManager.mExecutor;
                        if (isTaskType) {
                            KeyguardShortcutManager.ShortcutData shortcutData3 = shortcutDataArr[i2];
                            Intrinsics.checkNotNull(shortcutData3);
                            final String str = shortcutData3.taskName;
                            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    try {
                                        final int i3 = i2;
                                        final KeyguardShortcutManager keyguardShortcutManager2 = keyguardShortcutManager;
                                        final String str2 = str;
                                        if (new Predicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                if (((String) obj) == null) {
                                                    AbstractC0147x487e7be7.m26m("updateTaskShortcut : ", i3, " is disabled from settings", "KeyguardShortcutManager");
                                                    return false;
                                                }
                                                KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager2;
                                                KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = keyguardShortcutManager3.mKeyguardBottomAreaShortcutTask;
                                                int i4 = i3;
                                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = keyguardQuickAffordanceConfigArr[i4];
                                                KeyguardShortcutManager.ShortcutData shortcutData4 = keyguardShortcutManager3.mShortcuts[i4];
                                                Intrinsics.checkNotNull(shortcutData4);
                                                keyguardQuickAffordanceConfigArr[i4] = keyguardShortcutManager3.getKeyguardBottomAreaShortcutTask(shortcutData4.taskName);
                                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig2 = keyguardShortcutManager2.mKeyguardBottomAreaShortcutTask[i3];
                                                AbstractC0000x2c234b15.m3m("updateTaskShortcut: key =  ", keyguardQuickAffordanceConfig2 != null ? keyguardQuickAffordanceConfig2.getKey() : null, "KeyguardShortcutManager");
                                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig3 = keyguardShortcutManager2.mKeyguardBottomAreaShortcutTask[i3];
                                                if (keyguardQuickAffordanceConfig3 == null || Intrinsics.areEqual(keyguardQuickAffordanceConfig3, KeyguardShortcutManager.EMPTY_CONFIG)) {
                                                    AbstractC0147x487e7be7.m26m("updateTaskShortcut : ", i3, " is invalid task name", "KeyguardShortcutManager");
                                                    return false;
                                                }
                                                keyguardShortcutManager2.isDndCallbackAdded = false;
                                                if (Intrinsics.areEqual("Dnd", str2)) {
                                                    DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = (DoNotDisturbQuickAffordanceConfig) keyguardShortcutManager2.mKeyguardBottomAreaShortcutTask[i3];
                                                    ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig.controller).addCallback(doNotDisturbQuickAffordanceConfig.callback);
                                                    KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager2;
                                                    keyguardShortcutManager4.dndShortcutIndex = i3;
                                                    keyguardShortcutManager4.isDndCallbackAdded = true;
                                                }
                                                if (Intrinsics.areEqual("Flashlight", str2) && !keyguardShortcutManager2.mIsFlashlightSupported) {
                                                    AbstractC0147x487e7be7.m26m("updateTaskShortcut : ", i3, " Shortcut set to Flashlight but Flashlight is not supported for the device", "KeyguardShortcutManager");
                                                    return false;
                                                }
                                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig4 = keyguardShortcutManager2.mKeyguardBottomAreaShortcutTask[i3];
                                                Intrinsics.checkNotNull(keyguardQuickAffordanceConfig4);
                                                int pickerIconResourceId = keyguardQuickAffordanceConfig4.getPickerIconResourceId();
                                                if (pickerIconResourceId == 0) {
                                                    if (Intrinsics.areEqual("Dnd", str2)) {
                                                        pickerIconResourceId = R.drawable.fg_do_not_disturb_off;
                                                    } else if (Intrinsics.areEqual("Flashlight", str2)) {
                                                        pickerIconResourceId = R.drawable.ic_flashlight_off;
                                                    }
                                                }
                                                KeyguardShortcutManager.ShortcutData shortcutData5 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData5);
                                                shortcutData5.mNoUnlockNeeded = true;
                                                KeyguardShortcutManager.ShortcutData shortcutData6 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData6);
                                                shortcutData6.enabled = true;
                                                boolean isTaskTypeEnabled = keyguardShortcutManager2.isTaskTypeEnabled(i3);
                                                KeyguardShortcutManager.ShortcutData shortcutData7 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData7);
                                                KeyguardShortcutManager keyguardShortcutManager5 = keyguardShortcutManager2;
                                                Drawable blendingFgIcon = keyguardShortcutManager5.getBlendingFgIcon(null, keyguardShortcutManager5.mContext.getResources().getDrawable(pickerIconResourceId), true, isTaskTypeEnabled);
                                                int i5 = keyguardShortcutManager2.mIconSize;
                                                shortcutData7.mDrawable = keyguardShortcutManager5.drawableToScaledBitmapDrawable(blendingFgIcon, i5, i5);
                                                KeyguardShortcutManager.ShortcutData shortcutData8 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData8);
                                                KeyguardShortcutManager keyguardShortcutManager6 = keyguardShortcutManager2;
                                                shortcutData8.mPanelDrawable = KeyguardShortcutManager.access$getFgPanelIcon(keyguardShortcutManager6, keyguardShortcutManager6.mContext.getResources().getDrawable(pickerIconResourceId));
                                                if (keyguardShortcutManager2.isPanelIconTransitionNeeded(i3)) {
                                                    KeyguardShortcutManager.ShortcutData shortcutData9 = keyguardShortcutManager2.mShortcuts[i3];
                                                    Intrinsics.checkNotNull(shortcutData9);
                                                    KeyguardShortcutManager keyguardShortcutManager7 = keyguardShortcutManager2;
                                                    shortcutData9.mPanelTransitDrawable = KeyguardShortcutManager.access$getFgPanelIcon(keyguardShortcutManager7, keyguardShortcutManager7.mContext.getResources().getDrawable(isTaskTypeEnabled ? R.drawable.fg_flash_off : R.drawable.fg_flash_on));
                                                }
                                                KeyguardShortcutManager.ShortcutData shortcutData10 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData10);
                                                shortcutData10.mComponentName = null;
                                                Intrinsics.checkNotNull(keyguardShortcutManager2.mShortcuts[i3]);
                                                keyguardShortcutManager2.isDefaultShortcutIcon(str2);
                                                KeyguardShortcutManager.ShortcutData shortcutData11 = keyguardShortcutManager2.mShortcuts[i3];
                                                Intrinsics.checkNotNull(shortcutData11);
                                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig5 = keyguardShortcutManager2.mKeyguardBottomAreaShortcutTask[i3];
                                                Intrinsics.checkNotNull(keyguardQuickAffordanceConfig5);
                                                shortcutData11.mAppLabel = keyguardQuickAffordanceConfig5.pickerName();
                                                int i6 = i3;
                                                KeyguardShortcutManager.ShortcutData shortcutData12 = keyguardShortcutManager2.mShortcuts[i6];
                                                Intrinsics.checkNotNull(shortcutData12);
                                                Log.d("KeyguardShortcutManager", "updateTaskShortcut th : " + i6 + " class : " + shortcutData12.taskName);
                                                keyguardShortcutManager2.getQuickAffordanceConfigList();
                                                return true;
                                            }
                                        }.test(str)) {
                                            final KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager;
                                            Handler handler = keyguardShortcutManager3.mHandler;
                                            final int i4 = i2;
                                            handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.2
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    KeyguardShortcutManager keyguardShortcutManager4 = KeyguardShortcutManager.this;
                                                    int i5 = i4;
                                                    KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
                                                    keyguardShortcutManager4.sendUpdateShortcutViewToCallback(i5);
                                                }
                                            });
                                        } else {
                                            final KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager;
                                            Handler handler2 = keyguardShortcutManager4.mHandler;
                                            final int i5 = i2;
                                            handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.3
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    KeyguardShortcutManager.access$resetShortcut(KeyguardShortcutManager.this, i5);
                                                }
                                            });
                                        }
                                    } catch (Exception e) {
                                        keyguardShortcutManager.mSettingsHelper.resetShortcutValue();
                                        Log.e("KeyguardShortcutManager", "getPositionCorrectionRatio exception = " + e);
                                    }
                                }
                            });
                        } else {
                            KeyguardShortcutManager.ShortcutData shortcutData4 = shortcutDataArr[i2];
                            Intrinsics.checkNotNull(shortcutData4);
                            executor.execute(new KeyguardShortcutManager$updateShortcut$1(shortcutData4.mComponentName, keyguardShortcutManager, i2));
                        }
                    }
                }
                int length2 = strArr.length / 2;
                for (int i3 = 0; i3 < length2 && i3 < 2; i3++) {
                    int i4 = i3 * 2;
                    if (Intrinsics.areEqual("1", strArr[i4])) {
                        int i5 = i4 + 1;
                        String str2 = strArr[i5];
                        if (str2 == null || !StringsKt__StringsKt.contains(str2, "NoUnlockNeeded", false)) {
                            KeyguardShortcutManager.ShortcutData shortcutData5 = shortcutDataArr[i3];
                            Intrinsics.checkNotNull(shortcutData5);
                            shortcutData5.shortcutProperty = 0;
                            String str3 = strArr[i5];
                            Intrinsics.checkNotNull(str3);
                            ComponentName unflattenFromString = ComponentName.unflattenFromString(str3);
                            if (unflattenFromString == null) {
                                PackageManager packageManager = keyguardShortcutManager.mPm;
                                Intrinsics.checkNotNull(packageManager);
                                String str4 = strArr[i5];
                                Intrinsics.checkNotNull(str4);
                                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str4);
                                if (launchIntentForPackage != null) {
                                    unflattenFromString = launchIntentForPackage.getComponent();
                                }
                            }
                            KeyguardShortcutManager.ShortcutData shortcutData6 = shortcutDataArr[i3];
                            Intrinsics.checkNotNull(shortcutData6);
                            shortcutData6.mComponentName = unflattenFromString;
                        } else {
                            KeyguardShortcutManager.ShortcutData shortcutData7 = shortcutDataArr[i3];
                            Intrinsics.checkNotNull(shortcutData7);
                            shortcutData7.shortcutProperty = 1;
                            KeyguardShortcutManager.ShortcutData shortcutData8 = shortcutDataArr[i3];
                            Intrinsics.checkNotNull(shortcutData8);
                            String str5 = strArr[i5];
                            Intrinsics.checkNotNull(str5);
                            String str6 = strArr[i5];
                            Intrinsics.checkNotNull(str6);
                            shortcutData8.taskName = str5.substring(StringsKt__StringsKt.lastIndexOf$default(str6, "/", 6) + 1);
                        }
                    } else {
                        String str7 = strArr[i4];
                        KeyguardShortcutManager.ShortcutData shortcutData9 = shortcutDataArr[i3];
                        Intrinsics.checkNotNull(shortcutData9);
                        shortcutData9.taskName = null;
                        KeyguardShortcutManager.ShortcutData shortcutData10 = shortcutDataArr[i3];
                        Intrinsics.checkNotNull(shortcutData10);
                        shortcutData10.mComponentName = null;
                        KeyguardShortcutManager.ShortcutData shortcutData11 = shortcutDataArr[i3];
                        Intrinsics.checkNotNull(shortcutData11);
                        shortcutData11.enabled = false;
                    }
                }
            }
            Unit unit2 = Unit.INSTANCE;
            while (i2 < 2) {
            }
        }
    };
    public final ColorMatrix WHITE_BG_INVERT = new ColorMatrix(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    public final ColorMatrix WHITE_BG_CONTRAST_60 = new ColorMatrix(new float[]{3.1f, 0.0f, 0.0f, 0.0f, -213.0f, 0.0f, 3.1f, 0.0f, 0.0f, -213.0f, 0.0f, 0.0f, 3.1f, 0.0f, -213.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    public final ColorMatrix BLACK_BG_CONTRAST_60 = new ColorMatrix(new float[]{2.3f, 0.0f, 0.0f, 0.0f, -213.0f, 0.0f, 2.3f, 0.0f, 0.0f, -213.0f, 0.0f, 0.0f, 2.3f, 0.0f, -213.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    public final HashMap themeShortcutHashMap = new HashMap();
    public final KeyguardShortcutManager$mIntentReceiver$1 mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$mIntentReceiver$1
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0140  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0193  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -1001645458:
                        if (!action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                        }
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        for (int i = 0; i < 2; i++) {
                            KeyguardShortcutManager.ShortcutData shortcutData = KeyguardShortcutManager.this.mShortcuts[i];
                            Intrinsics.checkNotNull(shortcutData);
                            if (shortcutData.mComponentName != null && stringArrayExtra != null) {
                                List asList = Arrays.asList(Arrays.copyOf(stringArrayExtra, stringArrayExtra.length));
                                KeyguardShortcutManager.ShortcutData shortcutData2 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData2);
                                ComponentName componentName = shortcutData2.mComponentName;
                                Intrinsics.checkNotNull(componentName);
                                if (asList.contains(componentName.getPackageName())) {
                                    KeyguardShortcutManager.ShortcutData shortcutData3 = KeyguardShortcutManager.this.mShortcuts[i];
                                    Intrinsics.checkNotNull(shortcutData3);
                                    ComponentName componentName2 = shortcutData3.mComponentName;
                                    Intrinsics.checkNotNull(componentName2);
                                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("onReceive : ", action, ", suspended shortcut ", componentName2.getPackageName(), "KeyguardShortcutManager");
                                    KeyguardShortcutManager keyguardShortcutManager = KeyguardShortcutManager.this;
                                    KeyguardShortcutManager.ShortcutData shortcutData4 = keyguardShortcutManager.mShortcuts[i];
                                    Intrinsics.checkNotNull(shortcutData4);
                                    keyguardShortcutManager.mExecutor.execute(new KeyguardShortcutManager$updateShortcut$1(shortcutData4.mComponentName, keyguardShortcutManager, i));
                                }
                            }
                        }
                        break;
                    case -810471698:
                        if (!action.equals("android.intent.action.PACKAGE_REPLACED")) {
                        }
                        Uri data = intent.getData();
                        Intrinsics.checkNotNull(data);
                        String schemeSpecificPart = data.getSchemeSpecificPart();
                        for (int i2 = 0; i2 < 2; i2++) {
                            KeyguardShortcutManager.ShortcutData shortcutData5 = KeyguardShortcutManager.this.mShortcuts[i2];
                            Intrinsics.checkNotNull(shortcutData5);
                            if (shortcutData5.mComponentName != null) {
                                KeyguardShortcutManager.ShortcutData shortcutData6 = KeyguardShortcutManager.this.mShortcuts[i2];
                                Intrinsics.checkNotNull(shortcutData6);
                                ComponentName componentName3 = shortcutData6.mComponentName;
                                Intrinsics.checkNotNull(componentName3);
                                if (Intrinsics.areEqual(schemeSpecificPart, componentName3.getPackageName())) {
                                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("onReceive : ", action, ", starting update of shortcut ", schemeSpecificPart, "KeyguardShortcutManager");
                                    KeyguardShortcutManager keyguardShortcutManager2 = KeyguardShortcutManager.this;
                                    KeyguardShortcutManager.ShortcutData shortcutData7 = keyguardShortcutManager2.mShortcuts[i2];
                                    Intrinsics.checkNotNull(shortcutData7);
                                    keyguardShortcutManager2.mExecutor.execute(new KeyguardShortcutManager$updateShortcut$1(shortcutData7.mComponentName, keyguardShortcutManager2, i2));
                                }
                            }
                        }
                        break;
                    case -224747295:
                        if (action.equals("com.samsung.android.action.LOCK_TASK_MODE")) {
                            KeyguardShortcutManager keyguardShortcutManager3 = KeyguardShortcutManager.this;
                            keyguardShortcutManager3.isLockTaskMode = ((ActivityManager) keyguardShortcutManager3.mContext.getSystemService("activity")).getLockTaskModeState() == 1;
                            Log.d("KeyguardShortcutManager", "onReceive : " + action + ", mIsLocksTaskModeLocked : " + KeyguardShortcutManager.this.isLockTaskMode);
                            break;
                        }
                        break;
                    case -147579983:
                        if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                            int intExtra = intent.getIntExtra("reason", 0);
                            Log.d("KeyguardShortcutManager", "onReceive : " + action + ", with state " + intExtra + ", updating shortcuts");
                            if (intExtra == 3 || intExtra == 5) {
                                KeyguardShortcutManager.this.updateShortcuts();
                                break;
                            }
                        }
                        break;
                    case -19011148:
                        if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                        }
                        KeyguardShortcutManager.this.updateShortcuts();
                        break;
                    case 172491798:
                        if (!action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        }
                        Uri data2 = intent.getData();
                        Intrinsics.checkNotNull(data2);
                        String schemeSpecificPart2 = data2.getSchemeSpecificPart();
                        while (i2 < 2) {
                        }
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            Bundle extras = intent.getExtras();
                            Intrinsics.checkNotNull(extras);
                            if (!extras.getBoolean("android.intent.extra.REPLACING")) {
                                Uri data3 = intent.getData();
                                Intrinsics.checkNotNull(data3);
                                String schemeSpecificPart3 = data3.getSchemeSpecificPart();
                                AbstractC0000x2c234b15.m3m("onReceive : Intent.EXTRA_REPLACING false, ", schemeSpecificPart3, "KeyguardShortcutManager");
                                for (int i3 = 0; i3 < 2; i3++) {
                                    KeyguardShortcutManager.ShortcutData shortcutData8 = KeyguardShortcutManager.this.mShortcuts[i3];
                                    Intrinsics.checkNotNull(shortcutData8);
                                    if (shortcutData8.mComponentName != null) {
                                        KeyguardShortcutManager.ShortcutData shortcutData9 = KeyguardShortcutManager.this.mShortcuts[i3];
                                        Intrinsics.checkNotNull(shortcutData9);
                                        ComponentName componentName4 = shortcutData9.mComponentName;
                                        Intrinsics.checkNotNull(componentName4);
                                        if (Intrinsics.areEqual(schemeSpecificPart3, componentName4.getPackageName())) {
                                            KeyguardShortcutManager.access$resetShortcut(KeyguardShortcutManager.this, i3);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    case 1129769556:
                        if (!action.equals("com.samsung.applock.intent.action.APPLOCK_ENABLE_CHANGED")) {
                        }
                        KeyguardShortcutManager.this.updateShortcuts();
                        break;
                    case 1290767157:
                        if (!action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                        }
                        String[] stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        while (i < 2) {
                        }
                        break;
                    case 1544582882:
                        if (!action.equals("android.intent.action.PACKAGE_ADDED")) {
                        }
                        Uri data22 = intent.getData();
                        Intrinsics.checkNotNull(data22);
                        String schemeSpecificPart22 = data22.getSchemeSpecificPart();
                        while (i2 < 2) {
                        }
                        break;
                    case 2039271079:
                        if (!action.equals("com.samsung.applock.intent.action.SSECURE_UPDATE")) {
                        }
                        KeyguardShortcutManager.this.updateShortcuts();
                        break;
                }
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ShortcutCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShortcutData {
        public boolean enabled;
        public boolean launchInsecureMain;
        public String mAppLabel;
        public ComponentName mComponentName;
        public Drawable mDrawable;
        public boolean mNoUnlockNeeded;
        public Drawable mPanelDrawable;
        public Drawable mPanelTransitDrawable;
        public int shortcutProperty;
        public String taskName;
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.statusbar.KeyguardShortcutManager$mUpdateShortcutsRunnable$1] */
    /* JADX WARN: Type inference failed for: r3v9, types: [com.android.systemui.statusbar.KeyguardShortcutManager$mIntentReceiver$1] */
    public KeyguardShortcutManager(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PackageManager packageManager, KeyguardStateController keyguardStateController, Set<KeyguardQuickAffordanceConfig> set, UserSwitcherController userSwitcherController) {
        String[] stringArray;
        String[] stringArray2;
        this.mContext = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSettingsHelper = settingsHelper;
        this.mPm = packageManager;
        this.mKeyguardStateController = keyguardStateController;
        this.taskConfigs = set;
        this.userSwitcherController = userSwitcherController;
        try {
            stringArray = context.getResources().getStringArray(R.array.theme_app_icon_package);
            stringArray2 = context.getResources().getStringArray(R.array.theme_app_icon_drawable);
        } catch (IllegalArgumentException e) {
            Log.d("KeyguardShortcutManager", "Making theme hash error : " + e);
        }
        if (stringArray.length != stringArray2.length) {
            Log.d("KeyguardShortcutManager", "themeAppIconPackageArray error :" + stringArray.length);
            Log.d("KeyguardShortcutManager", "themeAppIconDrawableArray error :" + stringArray2.length);
            throw new IllegalArgumentException("Arrays must have the same size");
        }
        int length = stringArray2.length;
        for (int i = 0; i < length; i++) {
            this.themeShortcutHashMap.put(stringArray[i], stringArray2[i]);
        }
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
        SettingsHelper.ItemMap itemMap = this.mSettingsHelper.mItemLists;
        this.mShortcutVisibleForMDM = (itemMap.get("set_shortcuts_mode") == null || itemMap.get("set_shortcuts_mode").getIntValue() == 0) ? false : true;
        this.mIsTablet = DeviceType.isTablet();
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.spen_usp");
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isSupportActionMemoOnLockScreen FEATURE_SPEN : ", hasSystemFeature, "DeviceType");
        this.mHasSPenFeature = hasSystemFeature;
        this.mIsFlashlightSupported = ((FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class))).mHasFlashlight;
        for (int i2 = 0; i2 < 2; i2++) {
            this.mShortcuts[i2] = new ShortcutData();
        }
        this.mSettingsHelper.registerCallback(this, Settings.System.getUriFor("lock_application_shortcut"), Settings.System.getUriFor("set_shortcuts_mode"), Settings.System.getUriFor("current_sec_appicon_theme_package"), Settings.System.getUriFor("accessibility_reduce_transparency"));
        this.mUpdateMonitor.registerCallback(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.mIntentReceiver, intentFilter, null, UserHandle.CURRENT, 0, null, 48);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_SWITCHED");
        intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.mIntentReceiver, intentFilter2, null, UserHandle.CURRENT, 0, null, 48);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter3.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.mIntentReceiver, intentFilter3, null, UserHandle.CURRENT, 0, null, 48);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.samsung.applock.intent.action.APPLOCK_ENABLE_CHANGED");
        intentFilter4.addAction("com.samsung.applock.intent.action.SSECURE_UPDATE");
        intentFilter4.addAction("com.samsung.android.action.LOCK_TASK_MODE");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.mIntentReceiver, intentFilter4, null, UserHandle.CURRENT, 0, null, 48);
    }

    public static final Drawable access$getFgPanelIcon(KeyguardShortcutManager keyguardShortcutManager, Drawable drawable) {
        keyguardShortcutManager.getClass();
        return new BitmapDrawable(keyguardShortcutManager.getCropFg(((BitmapDrawable) drawable).getBitmap()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00d7, code lost:
    
        if (r13 != null) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:47:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Drawable access$getShortcutIcon(KeyguardShortcutManager keyguardShortcutManager, ActivityInfo activityInfo, boolean z) {
        Drawable drawable;
        int identifier;
        Drawable foreground;
        keyguardShortcutManager.getClass();
        String str = activityInfo.packageName;
        SettingsHelper settingsHelper = keyguardShortcutManager.mSettingsHelper;
        String stringValue = settingsHelper.mItemLists.get("current_sec_appicon_theme_package").getStringValue();
        boolean z2 = true;
        PackageManager packageManager = keyguardShortcutManager.mPm;
        if (stringValue == null) {
            drawable = keyguardShortcutManager.getSamsungAppIconDrawable(str);
        } else {
            String str2 = (String) keyguardShortcutManager.themeShortcutHashMap.get(str);
            if (str2 == null) {
                str2 = null;
            }
            Context context = keyguardShortcutManager.mContext;
            if (str2 != null && (identifier = context.getResources().getIdentifier(str2, "drawable", context.getPackageName())) != 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), identifier, options);
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), identifier, options2);
                if (options2.outWidth != 1) {
                    drawable = context.getDrawable(identifier);
                    if (drawable == null) {
                        BitmapFactory.Options options3 = new BitmapFactory.Options();
                        options3.inJustDecodeBounds = true;
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_shortcut_theme_bg, options3);
                        if (options3.outWidth != 1) {
                            drawable = activityInfo.loadIcon(packageManager, true, 256);
                        }
                    }
                }
            }
            drawable = null;
            if (drawable == null) {
            }
        }
        if (drawable == null) {
            drawable = activityInfo.loadIcon(packageManager, true, 1);
        }
        if (drawable == null) {
            drawable = activityInfo.loadDefaultIcon(packageManager);
        }
        if (keyguardShortcutManager.isblendNeeded(activityInfo, drawable)) {
            try {
                if (!Intrinsics.areEqual("com.sec.android.app.camera", str) || settingsHelper.mItemLists.get("current_sec_appicon_theme_package").getStringValue() != null) {
                    z2 = false;
                }
                if (z2) {
                    foreground = keyguardShortcutManager.getSamsungAppIconDrawable(str);
                } else {
                    DrawableWrapper drawableWrapper = (DrawableWrapper) drawable;
                    AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) (drawableWrapper != null ? drawableWrapper.getDrawable() : null);
                    Intrinsics.checkNotNull(adaptiveIconDrawable);
                    foreground = adaptiveIconDrawable.getForeground();
                }
                if (foreground != null) {
                    if (!z) {
                        foreground = keyguardShortcutManager.getBlendingFgIcon(str, foreground, false, false);
                    }
                    drawable = foreground;
                }
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Making samsung Icon error : ", e, "KeyguardShortcutManager");
            }
        }
        int i = keyguardShortcutManager.mIconSize;
        return keyguardShortcutManager.drawableToScaledBitmapDrawable(drawable, i, i);
    }

    public static final void access$resetShortcut(KeyguardShortcutManager keyguardShortcutManager, int i) {
        ShortcutData[] shortcutDataArr = keyguardShortcutManager.mShortcuts;
        ShortcutData shortcutData = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData);
        shortcutData.enabled = false;
        ShortcutData shortcutData2 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData2);
        shortcutData2.mDrawable = null;
        ShortcutData shortcutData3 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData3);
        shortcutData3.mPanelDrawable = null;
        ShortcutData shortcutData4 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData4);
        shortcutData4.mPanelTransitDrawable = null;
        ShortcutData shortcutData5 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData5);
        shortcutData5.mAppLabel = null;
        keyguardShortcutManager.sendUpdateShortcutViewToCallback(i);
    }

    public static final void access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager keyguardShortcutManager, final int i) {
        Iterator it = keyguardShortcutManager.mCallbacks.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (((ShortcutCallback) weakReference.get()) != null) {
                Object obj = weakReference.get();
                Intrinsics.checkNotNull(obj);
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) ((ShortcutCallback) obj)).this$0;
                ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1$updateShortcutIconOnly$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (i == 0) {
                            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = keyguardSecBottomAreaViewController;
                            String str2 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                            if (keyguardSecBottomAreaViewController2.getLeftView() != null) {
                                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = keyguardSecBottomAreaViewController;
                                keyguardSecBottomAreaViewController3.updateCustomShortcutIcon(keyguardSecBottomAreaViewController3.getLeftView(), 0, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0));
                                return;
                            }
                            return;
                        }
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController4 = keyguardSecBottomAreaViewController;
                        String str3 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                        if (keyguardSecBottomAreaViewController4.getRightView() != null) {
                            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController5 = keyguardSecBottomAreaViewController;
                            keyguardSecBottomAreaViewController5.updateCustomShortcutIcon(keyguardSecBottomAreaViewController5.getRightView(), 1, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1));
                        }
                    }
                });
            }
        }
    }

    public static Bitmap imgShadow(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), new RectF(0.0f, 0.0f, width, height), Matrix.ScaleToFit.CENTER);
        Matrix matrix2 = new Matrix(matrix);
        matrix2.postTranslate(0.0f, 0.0f);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(bitmap, matrix2, paint);
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(4.0f, BlurMaskFilter.Blur.SOLID);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setMaskFilter(blurMaskFilter);
        paint.setFilterBitmap(true);
        Paint paint2 = new Paint(1);
        paint2.setFilterBitmap(true);
        Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        canvas2.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        canvas2.drawBitmap(bitmap, matrix, paint2);
        createBitmap.recycle();
        return createBitmap2;
    }

    public static boolean isAllowNonPlatformKeyApp(Context context, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        Unit unit = Unit.INSTANCE;
        ArrayList arrayList2 = new ArrayList();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            SigningInfo signingInfo = context.getPackageManager().getPackageInfo(str, 134217728).signingInfo;
            for (Signature signature : signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory()) {
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest.digest(signature.toCharsString().getBytes(Charset.defaultCharset()))) {
                    CharsKt__CharJVMKt.checkRadix(16);
                    sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
                }
                arrayList2.add(sb.toString());
            }
        } catch (Exception e) {
            Log.e("AppSignature", "isAllowNonPlatformKeyApp : " + e.getMessage());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (arrayList2.contains((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSamsungCameraPackage(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return Intrinsics.areEqual("com.sec.android.app.camera", componentName.getPackageName());
    }

    public final BitmapDrawable drawableToScaledBitmapDrawable(Drawable drawable, int i, int i2) {
        if (drawable == null) {
            Log.d("KeyguardShortcutManager", "drawableToScaledBitmapDrawable : drawable is null");
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Context context = this.mContext;
        createBitmap.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        return new BitmapDrawable(context.getResources(), createBitmap);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ShortcutManager state:");
        printWriter.println("  CurrentUserId = " + ActivityManager.getCurrentUser());
        printWriter.println("  Shortcut count = 2");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  Master switch = ", this.mSettingsHelper.mItemLists.get("lockscreen_show_shortcut").getIntValue() == 1, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  disabled shortcut by MDM = ", !this.mShortcutVisibleForMDM, printWriter);
        ShortcutData[] shortcutDataArr = this.mShortcuts;
        int length = shortcutDataArr.length;
        for (int i = 0; i < length; i++) {
            SideFpsController$$ExternalSyntheticOutline0.m105m("  Shortcut ", i, printWriter);
            ShortcutData shortcutData = shortcutDataArr[i];
            if (shortcutData == null) {
                printWriter.println("    null");
            } else {
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("    enabled = ", shortcutData.enabled, printWriter);
                printWriter.println("    component = " + shortcutData.mComponentName);
                FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("    label = ", shortcutData.mAppLabel, printWriter);
                ComponentName componentName = shortcutData.mComponentName;
                if (componentName != null) {
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("    isSuspended = ", getSuspended(componentName.getPackageName()), printWriter);
                    ComponentName componentName2 = shortcutData.mComponentName;
                    Intrinsics.checkNotNull(componentName2);
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("    isLockTaskPermitted = ", isLockTaskPermitted(componentName2.getPackageName()), printWriter);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006c A[Catch: Exception -> 0x00b5, TryCatch #0 {Exception -> 0x00b5, blocks: (B:3:0x0008, B:8:0x002f, B:12:0x003c, B:14:0x0056, B:16:0x005e, B:21:0x006c, B:23:0x0074, B:27:0x0086, B:36:0x0014), top: B:2:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getBlendingFgIcon(String str, Drawable drawable, boolean z, boolean z2) {
        int i;
        boolean z3;
        Context context = this.mContext;
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("bottom");
        try {
            Bitmap bitmap$default = DrawableKt.toBitmap$default(drawable);
            Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), ((z && z2) || this.mSettingsHelper.isReduceTransparencyEnabled()) ? isWhiteKeyguardWallpaper ? R.drawable.bg_bk_on : R.drawable.bg_wh_on : isWhiteKeyguardWallpaper ? R.drawable.bg_bk : R.drawable.bg_wh);
            Bitmap cropFg = getCropFg(grayInvertDrawable(bitmap$default, isWhiteKeyguardWallpaper, str, z && z2, false));
            int width = decodeResource.getWidth();
            int height = decodeResource.getHeight();
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(cropFg, width, height, true);
            if (isWhiteKeyguardWallpaper) {
                if (!Intrinsics.areEqual("com.samsung.android.oneconnect", str) && !Intrinsics.areEqual("com.samsung.android.tvplus", str)) {
                    z3 = false;
                    if (!z3) {
                        if (!(Intrinsics.areEqual("com.samsung.android.aremoji", str) || Intrinsics.areEqual("com.sec.android.mimage.avatarstickers", str))) {
                            i = R.color.shortcut_whitebg_tint;
                            Bitmap imgShadow = imgShadow(createScaledBitmap, context.getColor(i));
                            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap);
                            Paint paint = new Paint();
                            canvas.drawBitmap(decodeResource, 0.0f, 0.0f, paint);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                            canvas.drawBitmap(imgShadow, 0.0f, 0.0f, paint);
                            return new BitmapDrawable(createBitmap);
                        }
                    }
                }
                z3 = true;
                if (!z3) {
                }
            }
            i = R.color.shortcut_blackbg_tint;
            Bitmap imgShadow2 = imgShadow(createScaledBitmap, context.getColor(i));
            Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            Paint paint2 = new Paint();
            canvas2.drawBitmap(decodeResource, 0.0f, 0.0f, paint2);
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas2.drawBitmap(imgShadow2, 0.0f, 0.0f, paint2);
            return new BitmapDrawable(createBitmap2);
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Making samsung Icon error : ", e, "KeyguardShortcutManager");
            return null;
        }
    }

    public final Bitmap getCropFg(Bitmap bitmap) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_fg_margin);
        int i = dimensionPixelSize * 2;
        return Bitmap.createBitmap(bitmap, dimensionPixelSize, dimensionPixelSize, bitmap.getWidth() - i, bitmap.getHeight() - i);
    }

    public final Intent getIntent(int i) {
        if (i < 0 || i >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("getIntent wrong param : ", i, "KeyguardShortcutManager");
            return null;
        }
        ShortcutData[] shortcutDataArr = this.mShortcuts;
        ShortcutData shortcutData = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData);
        if (isSamsungCameraPackage(shortcutData.mComponentName)) {
            Log.d("KeyguardShortcutManager", "th = " + i + " is camera package");
            return isSecure() ? SECURE_CAMERA_INTENT : INSECURE_CAMERA_INTENT;
        }
        ShortcutData shortcutData2 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData2);
        ComponentName componentName = shortcutData2.mComponentName;
        if (componentName == null ? false : Intrinsics.areEqual("com.samsung.android.app.galaxyraw", componentName.getPackageName())) {
            AbstractC0147x487e7be7.m26m("th = ", i, " is expert raw camera package", "KeyguardShortcutManager");
            return SAMSUNG_EXPERT_RAW_CAMERA_INTENT;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        boolean isSecure = isSecure();
        if (!isSecure) {
            ShortcutData shortcutData3 = shortcutDataArr[i];
            Intrinsics.checkNotNull(shortcutData3);
            if (shortcutData3.launchInsecureMain) {
                intent.addCategory("android.intent.category.LAUNCHER");
                ShortcutData shortcutData4 = shortcutDataArr[i];
                Intrinsics.checkNotNull(shortcutData4);
                ComponentName componentName2 = shortcutData4.mComponentName;
                intent.setPackage(componentName2 != null ? componentName2.getPackageName() : null);
                ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, 1, KeyguardUpdateMonitor.getCurrentUser());
                if ((resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null) != null) {
                    ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
                    intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                } else {
                    ShortcutData shortcutData5 = shortcutDataArr[i];
                    Intrinsics.checkNotNull(shortcutData5);
                    intent.setComponent(shortcutData5.mComponentName);
                }
                intent.putExtra("isSecure", isSecure);
                return intent.addFlags(268500992);
            }
        }
        ShortcutData shortcutData6 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData6);
        intent.setComponent(shortcutData6.mComponentName);
        intent.putExtra("isSecure", isSecure);
        return intent.addFlags(268500992);
    }

    public final KeyguardQuickAffordanceConfig getKeyguardBottomAreaShortcutTask(String str) {
        Object obj;
        Iterator it = this.taskConfigs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                break;
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        return keyguardQuickAffordanceConfig == null ? EMPTY_CONFIG : keyguardQuickAffordanceConfig;
    }

    public final List getQuickAffordanceConfigList() {
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig;
        ComponentName componentName;
        KeyguardQuickAffordancePosition[] values = KeyguardQuickAffordancePosition.values();
        ArrayList arrayList = new ArrayList(values.length);
        for (KeyguardQuickAffordancePosition keyguardQuickAffordancePosition : values) {
            final int ordinal = keyguardQuickAffordancePosition.ordinal();
            ShortcutData[] shortcutDataArr = this.mShortcuts;
            final ShortcutData shortcutData = shortcutDataArr[ordinal];
            if (shortcutData == null || !shortcutData.enabled || (!isTaskType(ordinal) && ((componentName = shortcutData.mComponentName) == null || componentName.getPackageName() == null))) {
                keyguardQuickAffordanceConfig = EMPTY_CONFIG;
            } else if (isTaskType(ordinal)) {
                ShortcutData shortcutData2 = shortcutDataArr[ordinal];
                keyguardQuickAffordanceConfig = getKeyguardBottomAreaShortcutTask(shortcutData2 != null ? shortcutData2.taskName : null);
            } else {
                keyguardQuickAffordanceConfig = new KeyguardQuickAffordanceConfig() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$generateQuickAffordanceConfig$1$1
                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final String getKey() {
                        ComponentName componentName2 = KeyguardShortcutManager.ShortcutData.this.mComponentName;
                        String flattenToString = componentName2 != null ? componentName2.flattenToString() : null;
                        Intrinsics.checkNotNull(flattenToString);
                        return flattenToString;
                    }

                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final Flow getLockScreenState() {
                        KeyguardShortcutManager keyguardShortcutManager = this;
                        int i = ordinal;
                        Drawable shortcutDrawable = keyguardShortcutManager.getShortcutDrawable(i);
                        Intrinsics.checkNotNull(shortcutDrawable);
                        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(shortcutDrawable, new ContentDescription.Loaded((String) this.getShortcutContentDescription(i))), null, 2, null));
                    }

                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final int getPickerIconResourceId() {
                        return 0;
                    }

                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final Object getPickerScreenState(Continuation continuation) {
                        return new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
                    }

                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
                        KeyguardShortcutManager keyguardShortcutManager = this;
                        int i = ordinal;
                        Intent intent = keyguardShortcutManager.getIntent(i);
                        Intrinsics.checkNotNull(intent);
                        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(intent, this.isNoUnlockNeeded(i));
                    }

                    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                    public final String pickerName() {
                        String str = KeyguardShortcutManager.ShortcutData.this.mAppLabel;
                        Intrinsics.checkNotNull(str);
                        return str;
                    }
                };
            }
            arrayList.add(keyguardQuickAffordanceConfig);
        }
        return arrayList;
    }

    public final Drawable getSamsungAppIconDrawable(String str) {
        if (str == null || !isDefaultShortcutIcon(str)) {
            return null;
        }
        int i = Intrinsics.areEqual(str, "com.sec.android.app.camera") ? R.drawable.fg_camera : 0;
        if (i != 0) {
            return this.mContext.getResources().getDrawable(i);
        }
        return null;
    }

    public final CharSequence getShortcutContentDescription(int i) {
        if (i < 0 || i >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("IllegalArgument : ", i, "KeyguardShortcutManager");
            return null;
        }
        ShortcutData[] shortcutDataArr = this.mShortcuts;
        ShortcutData shortcutData = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData);
        if (TextUtils.isEmpty(shortcutData.mAppLabel)) {
            return "Shortcut";
        }
        ShortcutData shortcutData2 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData2);
        return shortcutData2.mAppLabel;
    }

    public final Drawable getShortcutDrawable(int i) {
        if (i < 0 || i >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("IllegalArgument : ", i, "KeyguardShortcutManager");
            return null;
        }
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        return shortcutData.mDrawable;
    }

    public final boolean getSuspended(String str) {
        PackageManager packageManager = this.mPm;
        if (packageManager == null) {
            return false;
        }
        try {
            return packageManager.isPackageSuspended(str);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("KeyguardShortcutManager", "getSuspended() - Not found package name = " + str);
            return false;
        }
    }

    public final Bitmap grayInvertDrawable(Bitmap bitmap, boolean z, String str, boolean z2, boolean z3) {
        boolean z4 = false;
        Bitmap copy = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()).copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        if (!(Intrinsics.areEqual("com.samsung.android.aremoji", str) || Intrinsics.areEqual("com.sec.android.mimage.avatarstickers", str))) {
            int i = R.color.shortcut_smartthing_tint_whitebg;
            Context context = this.mContext;
            if (str == null && z2) {
                if (z) {
                    i = R.color.shortcut_smartthing_tint;
                }
                paint.setColorFilter(new PorterDuffColorFilter(context.getColor(i), PorterDuff.Mode.SRC_IN));
            } else {
                if (Intrinsics.areEqual("com.samsung.android.oneconnect", str) || Intrinsics.areEqual("com.samsung.android.tvplus", str)) {
                    if (!z) {
                        i = R.color.shortcut_smartthing_tint;
                    }
                    paint.setColorFilter(new PorterDuffColorFilter(context.getColor(i), PorterDuff.Mode.SRC_IN));
                } else {
                    ColorMatrix colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.0f);
                    if (this.mSettingsHelper.isReduceTransparencyEnabled() && !z3) {
                        z4 = true;
                    }
                    if (z ^ z4) {
                        colorMatrix.postConcat(this.WHITE_BG_INVERT);
                        colorMatrix.postConcat(this.WHITE_BG_CONTRAST_60);
                    } else {
                        colorMatrix.postConcat(this.BLACK_BG_CONTRAST_60);
                    }
                    paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                }
            }
        }
        new Canvas(copy).drawBitmap(copy, 0.0f, 0.0f, paint);
        return copy;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0048, code lost:
    
        if (r2.taskName == null) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean hasShortcut(int i) {
        boolean z;
        if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
            return false;
        }
        ShortcutData[] shortcutDataArr = this.mShortcuts;
        if (2 > i) {
            if (this.mSettingsHelper.mItemLists.get("lockscreen_show_shortcut").getIntValue() == 1) {
                ShortcutData shortcutData = shortcutDataArr[i];
                Intrinsics.checkNotNull(shortcutData);
                if (shortcutData.enabled) {
                    z = true;
                    if (z) {
                        return false;
                    }
                    if (isTaskType(i)) {
                        ShortcutData shortcutData2 = shortcutDataArr[i];
                        Intrinsics.checkNotNull(shortcutData2);
                    }
                    ShortcutData shortcutData3 = shortcutDataArr[i];
                    Intrinsics.checkNotNull(shortcutData3);
                    if (shortcutData3.mComponentName == null) {
                        return false;
                    }
                    ShortcutData shortcutData4 = shortcutDataArr[i];
                    Intrinsics.checkNotNull(shortcutData4);
                    ComponentName componentName = shortcutData4.mComponentName;
                    Intrinsics.checkNotNull(componentName);
                    if (!isLockTaskPermitted(componentName.getPackageName())) {
                        return false;
                    }
                    return true;
                }
            }
        }
        z = false;
        if (z) {
        }
    }

    public final boolean isDefaultShortcutIcon(String str) {
        String str2 = DEF_SHORTCUT;
        if (!TextUtils.isEmpty(str2)) {
            Intrinsics.checkNotNull(str);
            if (!StringsKt__StringsKt.contains(str2, str, false)) {
                return false;
            }
        }
        if (str == null) {
            return false;
        }
        int hashCode = str.hashCode();
        boolean z = this.mHasSPenFeature;
        boolean z2 = this.mIsTablet;
        switch (hashCode) {
            case 68858:
                if (!str.equals("Dnd")) {
                    return false;
                }
                break;
            case 640747243:
                if (!str.equals("com.sec.android.app.sbrowser") || !z2 || z) {
                    return false;
                }
                break;
            case 708520957:
                if (str.equals("com.samsung.android.dialer")) {
                    return !z2;
                }
                return false;
            case 810391366:
                if (!str.equals("Flashlight")) {
                    return false;
                }
                break;
            case 1181686996:
                if (!str.equals("com.samsung.android.app.notes") || !z2 || !z) {
                    return false;
                }
                break;
            case 1923638331:
                if (!str.equals("com.sec.android.app.camera")) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public final boolean isLockTaskPermitted(String str) {
        if (this.isLockTaskMode) {
            return ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).isLockTaskPermitted(str);
        }
        return true;
    }

    public final boolean isNoUnlockNeeded(int i) {
        if (i < 0 || i >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("isNoUnlockNeeded wrong param: ", i, "KeyguardShortcutManager");
            return false;
        }
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        return shortcutData.mNoUnlockNeeded;
    }

    public final boolean isPanelIconTransitionNeeded(int i) {
        KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr;
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig;
        if (i < 0 || i >= 2 || (keyguardQuickAffordanceConfig = (keyguardQuickAffordanceConfigArr = this.mKeyguardBottomAreaShortcutTask)[i]) == null || Intrinsics.areEqual(keyguardQuickAffordanceConfig, EMPTY_CONFIG)) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("IllegalArgument : ", i, "KeyguardShortcutManager");
            return false;
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig2 = keyguardQuickAffordanceConfigArr[i];
        Intrinsics.checkNotNull(keyguardQuickAffordanceConfig2);
        return Intrinsics.areEqual(keyguardQuickAffordanceConfig2.getKey(), "Flashlight");
    }

    public final boolean isSecure() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mSecure && !((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
    }

    public final boolean isShortcutForCamera(int i) {
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        return isSamsungCameraPackage(shortcutData.mComponentName);
    }

    public final boolean isShortcutForLiveIcon(int i) {
        ShortcutData[] shortcutDataArr = this.mShortcuts;
        ShortcutData shortcutData = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData);
        if (shortcutData.mComponentName == null) {
            return false;
        }
        ShortcutData shortcutData2 = shortcutDataArr[i];
        Intrinsics.checkNotNull(shortcutData2);
        ComponentName componentName = shortcutData2.mComponentName;
        Intrinsics.checkNotNull(componentName);
        String packageName = componentName.getPackageName();
        for (String str : SAMSUNG_LIVE_ICON_PACKAGES) {
            if (Intrinsics.areEqual(str, packageName)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isShortcutForPhone(int i) {
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        ComponentName componentName = shortcutData.mComponentName;
        return componentName != null && Intrinsics.areEqual("com.samsung.android.dialer", componentName.getPackageName()) && Intrinsics.areEqual("com.samsung.android.dialer.DialtactsActivity", componentName.getClassName());
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003b, code lost:
    
        if (r6.equals("com.sec.android.app.popupcalculator") == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0061, code lost:
    
        if (isAllowNonPlatformKeyApp(r5, r6, "9e92121f90ad13d9f1085b06ea9e7c72ca6d5b603cdfd6adaff7b3071792d71f") == false) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isShortcutPermission(String str) {
        boolean z;
        PackageManager packageManager = this.mPm;
        Intrinsics.checkNotNull(packageManager);
        if (packageManager.checkPermission("com.samsung.keyguard.SHORTCUT_PERMISSION", str) == 0) {
            return true;
        }
        int hashCode = str.hashCode();
        Context context = this.mContext;
        if (hashCode != -662003450) {
            if (hashCode != 988032088) {
                if (hashCode == 2094270320 && str.equals("com.snapchat.android")) {
                    z = isAllowNonPlatformKeyApp(context, str, "9c1c8918e17cc686d3274f41cd04154b4cbe6a5272700de3f4f30c2c62ae2ad4");
                }
            }
            if (str.startsWith("com.snapchat.android")) {
                z = isAllowNonPlatformKeyApp(context, str, "2f4eaa0c67e2a670935ca79164f3ba4b426988b6997a97bb31152cc317dc648a");
            }
            z = false;
        } else {
            if (str.equals("com.instagram.android")) {
                if (!isAllowNonPlatformKeyApp(context, str, "a044dbdb712ab81e76949f5d76ada4dd7035643b462cb7ea2b75ecae637c2da3")) {
                }
                z = true;
            }
            if (str.startsWith("com.snapchat.android")) {
            }
            z = false;
        }
        return z;
    }

    public final boolean isTaskType(int i) {
        if (i < 0 || i >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("isTaskType wrong param: ", i, "KeyguardShortcutManager");
            return false;
        }
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        return shortcutData.shortcutProperty == 1;
    }

    public final boolean isTaskTypeEnabled(int i) {
        KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr;
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig;
        if (i < 0 || i >= 2 || (keyguardQuickAffordanceConfig = (keyguardQuickAffordanceConfigArr = this.mKeyguardBottomAreaShortcutTask)[i]) == null || Intrinsics.areEqual(keyguardQuickAffordanceConfig, EMPTY_CONFIG)) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("IllegalArgument : ", i, "KeyguardShortcutManager");
            return false;
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig2 = keyguardQuickAffordanceConfigArr[i];
        Intrinsics.checkNotNull(keyguardQuickAffordanceConfig2);
        String key = keyguardQuickAffordanceConfig2.getKey();
        return Intrinsics.areEqual(key, "Flashlight") ? ((FlashlightControllerImpl) ((FlashlightQuickAffordanceConfig) keyguardQuickAffordanceConfigArr[i]).flashlightController).isEnabled() : Intrinsics.areEqual(key, "Dnd") && ((ZenModeControllerImpl) ((DoNotDisturbQuickAffordanceConfig) keyguardQuickAffordanceConfigArr[i]).controller).mZenMode != 0;
    }

    public final boolean isblendNeeded(ActivityInfo activityInfo, Drawable drawable) {
        if (Intrinsics.areEqual("com.sec.android.app.camera", activityInfo.packageName) && this.mSettingsHelper.mItemLists.get("current_sec_appicon_theme_package").getStringValue() == null) {
            return true;
        }
        if (drawable instanceof DrawableWrapper) {
            Bundle bundle = activityInfo.metaData;
            if ((bundle != null ? bundle.getString("com.sec.android.app.launcher.icon_theme", null) : null) != null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (Intrinsics.areEqual(uri, Settings.System.getUriFor("lock_application_shortcut")) || Intrinsics.areEqual(uri, Settings.System.getUriFor("current_sec_appicon_theme_package"))) {
            updateShortcuts();
            return;
        }
        if (!Intrinsics.areEqual(uri, Settings.System.getUriFor("set_shortcuts_mode"))) {
            if (Intrinsics.areEqual(uri, Settings.System.getUriFor("accessibility_reduce_transparency"))) {
                updateShortcutIcons();
                return;
            }
            return;
        }
        boolean z = this.mShortcutVisibleForMDM;
        SettingsHelper.ItemMap itemMap = this.mSettingsHelper.mItemLists;
        boolean z2 = (itemMap.get("set_shortcuts_mode") == null || itemMap.get("set_shortcuts_mode").getIntValue() == 0) ? false : true;
        this.mShortcutVisibleForMDM = z2;
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onSystemSettingsChanged oldShortcutVisibleForMDM = ", z, ", mShortcutVisibleForMDM = ", z2, "KeyguardShortcutManager");
        if (z != this.mShortcutVisibleForMDM) {
            updateShortcuts();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onSimStateChanged(int i, int i2, int i3) {
        boolean z = this.mIsPermDisabled;
        boolean z2 = LsRune.SECURITY_SIM_PERM_DISABLED && this.mUpdateMonitor.isIccBlockedPermanently();
        this.mIsPermDisabled = z2;
        if (z != z2) {
            updateShortcuts();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onStartedWakingUp() {
        if (isShortcutForLiveIcon(0)) {
            updateShortcutIcon(0);
        }
        if (isShortcutForLiveIcon(1)) {
            updateShortcutIcon(1);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onUserSwitchComplete(int i) {
        updateShortcuts();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onUserUnlocked() {
        updateShortcuts();
    }

    public final void sendUpdateShortcutViewToCallback(int i) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (((ShortcutCallback) weakReference.get()) != null) {
                Object obj = weakReference.get();
                Intrinsics.checkNotNull(obj);
                ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) ((ShortcutCallback) obj)).updateShortcutView(i);
            }
        }
    }

    public final void updateShortcutIcon(final int i) {
        boolean isTaskType = isTaskType(i);
        Executor executor = this.mExecutor;
        if (isTaskType) {
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    final int i2 = i;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i3) {
                            KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = KeyguardShortcutManager.this.mKeyguardBottomAreaShortcutTask[i3];
                            if (keyguardQuickAffordanceConfig == null || Intrinsics.areEqual(keyguardQuickAffordanceConfig, KeyguardShortcutManager.EMPTY_CONFIG)) {
                                AbstractC0147x487e7be7.m26m("updateShortcutsIcon : ", i3, " is invalid task name", "KeyguardShortcutManager");
                                return false;
                            }
                            boolean isTaskTypeEnabled = KeyguardShortcutManager.this.isTaskTypeEnabled(i2);
                            KeyguardShortcutManager.ShortcutData shortcutData = KeyguardShortcutManager.this.mShortcuts[i3];
                            Intrinsics.checkNotNull(shortcutData);
                            KeyguardShortcutManager keyguardShortcutManager2 = KeyguardShortcutManager.this;
                            Resources resources = keyguardShortcutManager2.mContext.getResources();
                            KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig2 = KeyguardShortcutManager.this.mKeyguardBottomAreaShortcutTask[i2];
                            Intrinsics.checkNotNull(keyguardQuickAffordanceConfig2);
                            Drawable blendingFgIcon = keyguardShortcutManager2.getBlendingFgIcon(null, resources.getDrawable(keyguardQuickAffordanceConfig2.getPickerIconResourceId()), true, isTaskTypeEnabled);
                            int i4 = KeyguardShortcutManager.this.mIconSize;
                            shortcutData.mDrawable = keyguardShortcutManager2.drawableToScaledBitmapDrawable(blendingFgIcon, i4, i4);
                            KeyguardShortcutManager.ShortcutData shortcutData2 = KeyguardShortcutManager.this.mShortcuts[i2];
                            Intrinsics.checkNotNull(shortcutData2);
                            KeyguardShortcutManager keyguardShortcutManager3 = KeyguardShortcutManager.this;
                            Resources resources2 = keyguardShortcutManager3.mContext.getResources();
                            KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig3 = KeyguardShortcutManager.this.mKeyguardBottomAreaShortcutTask[i2];
                            Intrinsics.checkNotNull(keyguardQuickAffordanceConfig3);
                            shortcutData2.mPanelDrawable = KeyguardShortcutManager.access$getFgPanelIcon(keyguardShortcutManager3, resources2.getDrawable(keyguardQuickAffordanceConfig3.getPickerIconResourceId()));
                            if (KeyguardShortcutManager.this.isPanelIconTransitionNeeded(i2)) {
                                KeyguardShortcutManager.ShortcutData shortcutData3 = KeyguardShortcutManager.this.mShortcuts[i2];
                                Intrinsics.checkNotNull(shortcutData3);
                                KeyguardShortcutManager keyguardShortcutManager4 = KeyguardShortcutManager.this;
                                shortcutData3.mPanelTransitDrawable = KeyguardShortcutManager.access$getFgPanelIcon(keyguardShortcutManager4, keyguardShortcutManager4.mContext.getResources().getDrawable(isTaskTypeEnabled ? R.drawable.fg_flash_off : R.drawable.fg_flash_on));
                            }
                            return true;
                        }
                    }.test(i)) {
                        final KeyguardShortcutManager keyguardShortcutManager2 = this;
                        Handler handler = keyguardShortcutManager2.mHandler;
                        final int i3 = i;
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager.this, i3);
                            }
                        });
                    }
                }
            });
            return;
        }
        ShortcutData shortcutData = this.mShortcuts[i];
        Intrinsics.checkNotNull(shortcutData);
        if (shortcutData.mComponentName != null) {
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i2) {
                            try {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                KeyguardShortcutManager.ShortcutData shortcutData2 = KeyguardShortcutManager.this.mShortcuts[i2];
                                Intrinsics.checkNotNull(shortcutData2);
                                intent.setComponent(shortcutData2.mComponentName);
                                ActivityInfo activityInfo = KeyguardShortcutManager.this.mPm.resolveActivityAsUser(intent, 129, KeyguardUpdateMonitor.getCurrentUser()).activityInfo;
                                if (activityInfo != null) {
                                    KeyguardShortcutManager.ShortcutData shortcutData3 = KeyguardShortcutManager.this.mShortcuts[i2];
                                    Intrinsics.checkNotNull(shortcutData3);
                                    shortcutData3.mDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, false);
                                    KeyguardShortcutManager.ShortcutData shortcutData4 = KeyguardShortcutManager.this.mShortcuts[i2];
                                    Intrinsics.checkNotNull(shortcutData4);
                                    shortcutData4.mPanelDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, true);
                                }
                                return true;
                            } catch (Exception e) {
                                Log.e("KeyguardShortcutManager", "NameNotFoundException while updating icon : " + e.getMessage());
                                return false;
                            }
                        }
                    }.test(i)) {
                        final KeyguardShortcutManager keyguardShortcutManager2 = this;
                        Handler handler = keyguardShortcutManager2.mHandler;
                        final int i2 = i;
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager.this, i2);
                            }
                        });
                    }
                }
            });
        }
    }

    public final void updateShortcutIcons() {
        for (int i = 0; i < 2; i++) {
            updateShortcutIcon(i);
        }
    }

    public final void updateShortcuts() {
        Handler handler = this.mHandler;
        KeyguardShortcutManager$mUpdateShortcutsRunnable$1 keyguardShortcutManager$mUpdateShortcutsRunnable$1 = this.mUpdateShortcutsRunnable;
        handler.removeCallbacks(keyguardShortcutManager$mUpdateShortcutsRunnable$1);
        handler.post(keyguardShortcutManager$mUpdateShortcutsRunnable$1);
        if (this.isDndCallbackAdded) {
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = (DoNotDisturbQuickAffordanceConfig) this.mKeyguardBottomAreaShortcutTask[this.dndShortcutIndex];
            ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig.controller).removeCallback(doNotDisturbQuickAffordanceConfig.callback);
            this.isDndCallbackAdded = false;
        }
    }

    public static /* synthetic */ void getMIntentReceiver$annotations() {
    }
}
