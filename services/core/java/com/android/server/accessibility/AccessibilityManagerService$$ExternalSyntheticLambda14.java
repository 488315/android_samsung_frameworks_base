package com.android.server.accessibility;

import android.content.ComponentName;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.view.inputmethod.EditorInfo;

import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.QuadConsumer;
import com.android.server.LocalServices;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda14
        implements QuadConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda14(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        switch (this.$r8$classId) {
            case 0:
                AccessibilityManagerService.m115$r8$lambda$qVXwVz5kwbGQqyOhKn52nbOVWI(
                        accessibilityManagerService,
                        ((Integer) obj2).intValue(),
                        ((Integer) obj3).intValue(),
                        (String) obj4);
                return;
            case 1:
                Set set = (Set) obj2;
                Set set2 = (Set) obj3;
                int intValue = ((Integer) obj4).intValue();
                accessibilityManagerService.getClass();
                final StatusBarManagerInternal statusBarManagerInternal =
                        (StatusBarManagerInternal)
                                LocalServices.getService(StatusBarManagerInternal.class);
                if (statusBarManagerInternal == null) {
                    return;
                }
                final Map a11yFeatureToTileMapInternal =
                        accessibilityManagerService.getA11yFeatureToTileMapInternal(intValue);
                final ArraySet arraySet = new ArraySet();
                final int i = 0;
                set.stream()
                        .filter(new AccessibilityManagerService$$ExternalSyntheticLambda50(1, set2))
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda56
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj5) {
                                        switch (i) {
                                            case 0:
                                                Map map = a11yFeatureToTileMapInternal;
                                                Set set3 = arraySet;
                                                StatusBarManagerInternal statusBarManagerInternal2 =
                                                        statusBarManagerInternal;
                                                String str = (String) obj5;
                                                ComponentName unflattenFromString =
                                                        ComponentName.unflattenFromString(str);
                                                if (unflattenFromString == null
                                                        || !map.containsKey(unflattenFromString)) {
                                                    set3.add(str);
                                                    break;
                                                } else if (!ShortcutConstants
                                                        .A11Y_FEATURE_TO_FRAMEWORK_TILE
                                                        .containsKey(unflattenFromString)) {
                                                    ComponentName componentName =
                                                            (ComponentName)
                                                                    map.get(unflattenFromString);
                                                    StatusBarManagerService.AnonymousClass2
                                                            anonymousClass2 =
                                                                    (StatusBarManagerService
                                                                                    .AnonymousClass2)
                                                                            statusBarManagerInternal2;
                                                    anonymousClass2.getClass();
                                                    if (android.view.accessibility.Flags
                                                            .a11yQsShortcut()) {
                                                        StatusBarManagerService
                                                                statusBarManagerService =
                                                                        StatusBarManagerService
                                                                                .this;
                                                        statusBarManagerService
                                                                .enforceStatusBarOrShell();
                                                        if (statusBarManagerService.mBar != null) {
                                                            try {
                                                                statusBarManagerService.mBar
                                                                        .addQsTileToFrontOrEnd(
                                                                                componentName,
                                                                                true);
                                                                break;
                                                            } catch (RemoteException unused) {
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            default:
                                                Map map2 = a11yFeatureToTileMapInternal;
                                                Set set4 = arraySet;
                                                StatusBarManagerInternal statusBarManagerInternal3 =
                                                        statusBarManagerInternal;
                                                String str2 = (String) obj5;
                                                ComponentName unflattenFromString2 =
                                                        ComponentName.unflattenFromString(str2);
                                                if (unflattenFromString2 != null
                                                        && map2.containsKey(unflattenFromString2)) {
                                                    if (!ShortcutConstants
                                                            .A11Y_FEATURE_TO_FRAMEWORK_TILE
                                                            .containsKey(unflattenFromString2)) {
                                                        ComponentName componentName2 =
                                                                (ComponentName)
                                                                        map2.get(
                                                                                unflattenFromString2);
                                                        StatusBarManagerService.AnonymousClass2
                                                                anonymousClass22 =
                                                                        (StatusBarManagerService
                                                                                        .AnonymousClass2)
                                                                                statusBarManagerInternal3;
                                                        anonymousClass22.getClass();
                                                        if (android.view.accessibility.Flags
                                                                .a11yQsShortcut()) {
                                                            StatusBarManagerService.this.remTile(
                                                                    componentName2);
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    set4.add(str2);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                final int i2 = 1;
                set2.stream()
                        .filter(new AccessibilityManagerService$$ExternalSyntheticLambda50(2, set))
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda56
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj5) {
                                        switch (i2) {
                                            case 0:
                                                Map map = a11yFeatureToTileMapInternal;
                                                Set set3 = arraySet;
                                                StatusBarManagerInternal statusBarManagerInternal2 =
                                                        statusBarManagerInternal;
                                                String str = (String) obj5;
                                                ComponentName unflattenFromString =
                                                        ComponentName.unflattenFromString(str);
                                                if (unflattenFromString == null
                                                        || !map.containsKey(unflattenFromString)) {
                                                    set3.add(str);
                                                    break;
                                                } else if (!ShortcutConstants
                                                        .A11Y_FEATURE_TO_FRAMEWORK_TILE
                                                        .containsKey(unflattenFromString)) {
                                                    ComponentName componentName =
                                                            (ComponentName)
                                                                    map.get(unflattenFromString);
                                                    StatusBarManagerService.AnonymousClass2
                                                            anonymousClass2 =
                                                                    (StatusBarManagerService
                                                                                    .AnonymousClass2)
                                                                            statusBarManagerInternal2;
                                                    anonymousClass2.getClass();
                                                    if (android.view.accessibility.Flags
                                                            .a11yQsShortcut()) {
                                                        StatusBarManagerService
                                                                statusBarManagerService =
                                                                        StatusBarManagerService
                                                                                .this;
                                                        statusBarManagerService
                                                                .enforceStatusBarOrShell();
                                                        if (statusBarManagerService.mBar != null) {
                                                            try {
                                                                statusBarManagerService.mBar
                                                                        .addQsTileToFrontOrEnd(
                                                                                componentName,
                                                                                true);
                                                                break;
                                                            } catch (RemoteException unused) {
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            default:
                                                Map map2 = a11yFeatureToTileMapInternal;
                                                Set set4 = arraySet;
                                                StatusBarManagerInternal statusBarManagerInternal3 =
                                                        statusBarManagerInternal;
                                                String str2 = (String) obj5;
                                                ComponentName unflattenFromString2 =
                                                        ComponentName.unflattenFromString(str2);
                                                if (unflattenFromString2 != null
                                                        && map2.containsKey(unflattenFromString2)) {
                                                    if (!ShortcutConstants
                                                            .A11Y_FEATURE_TO_FRAMEWORK_TILE
                                                            .containsKey(unflattenFromString2)) {
                                                        ComponentName componentName2 =
                                                                (ComponentName)
                                                                        map2.get(
                                                                                unflattenFromString2);
                                                        StatusBarManagerService.AnonymousClass2
                                                                anonymousClass22 =
                                                                        (StatusBarManagerService
                                                                                        .AnonymousClass2)
                                                                                statusBarManagerInternal3;
                                                        anonymousClass22.getClass();
                                                        if (android.view.accessibility.Flags
                                                                .a11yQsShortcut()) {
                                                            StatusBarManagerService.this.remTile(
                                                                    componentName2);
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    set4.add(str2);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                if (arraySet.isEmpty()) {
                    return;
                }
                Slog.e(
                        "AccessibilityManagerService",
                        "Unable to add/remove Tiles for a11y features: "
                                + arraySet
                                + "as the Tiles aren't provided");
                return;
            default:
                IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection =
                        (IRemoteAccessibilityInputConnection) obj2;
                EditorInfo editorInfo = (EditorInfo) obj3;
                boolean booleanValue = ((Boolean) obj4).booleanValue();
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        accessibilityManagerService.mRemoteInputConnection =
                                iRemoteAccessibilityInputConnection;
                        accessibilityManagerService.mEditorInfo = editorInfo;
                        accessibilityManagerService.mRestarting = booleanValue;
                        AccessibilityUserState userStateLocked =
                                accessibilityManagerService.getUserStateLocked(
                                        accessibilityManagerService.mCurrentUserId);
                        for (int size = userStateLocked.mBoundServices.size() - 1;
                                size >= 0;
                                size--) {
                            AccessibilityServiceConnection accessibilityServiceConnection =
                                    (AccessibilityServiceConnection)
                                            userStateLocked.mBoundServices.get(size);
                            if (accessibilityServiceConnection.mRequestImeApis) {
                                AbstractAccessibilityServiceConnection.InvocationHandler
                                        invocationHandler =
                                                accessibilityServiceConnection.mInvocationHandler;
                                invocationHandler.getClass();
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = iRemoteAccessibilityInputConnection;
                                obtain.arg2 = editorInfo;
                                invocationHandler
                                        .obtainMessage(14, booleanValue ? 1 : 0, 0, obtain)
                                        .sendToTarget();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
