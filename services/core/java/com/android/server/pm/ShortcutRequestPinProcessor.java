package com.android.server.pm;

import android.app.ActivityOptions;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.IPinItemRequest;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;

public final class ShortcutRequestPinProcessor {
    public final Object mLock;
    public final ShortcutService mService;

    public final class PinAppWidgetRequestInner extends PinItemRequestInner {
        public final AppWidgetProviderInfo mAppWidgetProviderInfo;
        public final Bundle mExtras;

        public PinAppWidgetRequestInner(ShortcutRequestPinProcessor shortcutRequestPinProcessor, IntentSender intentSender, int i, AppWidgetProviderInfo appWidgetProviderInfo, Bundle bundle) {
            super(shortcutRequestPinProcessor, intentSender, i);
            this.mAppWidgetProviderInfo = appWidgetProviderInfo;
            this.mExtras = bundle;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public final AppWidgetProviderInfo getAppWidgetProviderInfo() {
            return this.mAppWidgetProviderInfo;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public final Bundle getExtras() {
            return this.mExtras;
        }
    }

    public abstract class PinItemRequestInner extends IPinItemRequest.Stub {
        public boolean mAccepted;
        public final int mLauncherUid;
        public final ShortcutRequestPinProcessor mProcessor;
        public final IntentSender mResultIntent;

        public PinItemRequestInner(ShortcutRequestPinProcessor shortcutRequestPinProcessor, IntentSender intentSender, int i) {
            this.mProcessor = shortcutRequestPinProcessor;
            this.mResultIntent = intentSender;
            this.mLauncherUid = i;
        }

        public final boolean accept(Bundle bundle) {
            Intent putExtras;
            if (this.mLauncherUid != this.mProcessor.mService.injectBinderCallingUid()) {
                throw new SecurityException("Calling uid mismatch");
            }
            if (bundle != null) {
                try {
                    bundle.size();
                    putExtras = new Intent().putExtras(bundle);
                } catch (RuntimeException e) {
                    throw new IllegalArgumentException("options cannot be unparceled", e);
                }
            } else {
                putExtras = null;
            }
            synchronized (this) {
                if (this.mAccepted) {
                    throw new IllegalStateException("accept() called already");
                }
                this.mAccepted = true;
            }
            if (!tryAccept()) {
                return false;
            }
            this.mProcessor.sendResultIntent(this.mResultIntent, putExtras);
            return true;
        }

        public AppWidgetProviderInfo getAppWidgetProviderInfo() {
            return null;
        }

        public Bundle getExtras() {
            return null;
        }

        public ShortcutInfo getShortcutInfo() {
            return null;
        }

        public final boolean isValid() {
            boolean z;
            if (!(this.mLauncherUid == this.mProcessor.mService.injectBinderCallingUid())) {
                return false;
            }
            synchronized (this) {
                z = !this.mAccepted;
            }
            return z;
        }

        public boolean tryAccept() {
            return true;
        }
    }

    public final class PinShortcutRequestInner extends PinItemRequestInner {
        public final String launcherPackage;
        public final int launcherUserId;
        public final ShortcutInfo shortcutForLauncher;
        public final ShortcutInfo shortcutOriginal;

        public PinShortcutRequestInner(ShortcutRequestPinProcessor shortcutRequestPinProcessor, ShortcutInfo shortcutInfo, ShortcutInfo shortcutInfo2, IntentSender intentSender, String str, int i, int i2) {
            super(shortcutRequestPinProcessor, intentSender, i2);
            this.shortcutOriginal = shortcutInfo;
            this.shortcutForLauncher = shortcutInfo2;
            this.launcherPackage = str;
            this.launcherUserId = i;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public final ShortcutInfo getShortcutInfo() {
            return this.shortcutForLauncher;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.pm.ShortcutService] */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.pm.ShortcutLauncher, com.android.server.pm.ShortcutPackageItem] */
        /* JADX WARN: Type inference failed for: r4v0, types: [int] */
        /* JADX WARN: Type inference failed for: r4v1 */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r6v2, types: [com.android.server.pm.ShortcutService] */
        /* JADX WARN: Type inference failed for: r8v0, types: [com.android.server.pm.ShortcutService] */
        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public final boolean tryAccept() {
            boolean z;
            Object obj;
            ShortcutRequestPinProcessor shortcutRequestPinProcessor = this.mProcessor;
            shortcutRequestPinProcessor.getClass();
            ShortcutInfo shortcutInfo = this.shortcutOriginal;
            ?? userId = shortcutInfo.getUserId();
            String str = shortcutInfo.getPackage();
            int i = this.launcherUserId;
            String str2 = this.launcherPackage;
            String id = shortcutInfo.getId();
            Object obj2 = shortcutRequestPinProcessor.mLock;
            synchronized (obj2) {
                try {
                    try {
                        if (shortcutRequestPinProcessor.mService.isUserUnlockedL(userId) && shortcutRequestPinProcessor.mService.isUserUnlockedL(this.launcherUserId)) {
                            ?? launcherShortcutsLocked = shortcutRequestPinProcessor.mService.getLauncherShortcutsLocked(userId, i, str2);
                            launcherShortcutsLocked.attemptToRestoreIfNeededAndSave();
                            if (!launcherShortcutsLocked.hasPinned(shortcutInfo)) {
                                ShortcutPackage packageShortcutsForPublisherLocked = shortcutRequestPinProcessor.mService.getPackageShortcutsForPublisherLocked(userId, str);
                                ShortcutInfo findShortcutById = packageShortcutsForPublisherLocked.findShortcutById(id);
                                try {
                                    if (findShortcutById == null) {
                                        shortcutRequestPinProcessor.mService.fixUpIncomingShortcutInfo(shortcutInfo, false, true);
                                    } else {
                                        Preconditions.checkArgument(findShortcutById.isEnabled(), "Shortcut ID=" + findShortcutById + " already exists but disabled.");
                                    }
                                    if (findShortcutById == null) {
                                        if (shortcutInfo.getActivity() == null) {
                                            shortcutRequestPinProcessor.mService.getClass();
                                            shortcutInfo.setActivity(new ComponentName(str, "android.__dummy__"));
                                        }
                                        packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(shortcutInfo);
                                    }
                                    launcherShortcutsLocked.addPinnedShortcut(userId, str, id);
                                    if (findShortcutById == null) {
                                        obj = obj2;
                                        packageShortcutsForPublisherLocked.deleteOrDisableWithId(id, false, false, false, 0, false);
                                    } else {
                                        obj = obj2;
                                    }
                                    packageShortcutsForPublisherLocked.adjustRanks();
                                    List singletonList = Collections.singletonList(packageShortcutsForPublisherLocked.findShortcutById(id));
                                    shortcutRequestPinProcessor.mService.verifyStates();
                                    shortcutRequestPinProcessor.mService.packageShortcutsChanged(packageShortcutsForPublisherLocked, singletonList, null);
                                } catch (RuntimeException e) {
                                    z = false;
                                    Log.w("ShortcutService", "Unable to pin shortcut: " + e.getMessage());
                                }
                            }
                            return true;
                        }
                        z = false;
                        Log.w("ShortcutService", "User is locked now.");
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        userId = obj2;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    public ShortcutRequestPinProcessor(ShortcutService shortcutService, Object obj) {
        this.mService = shortcutService;
        this.mLock = obj;
    }

    public Pair getRequestPinConfirmationActivity(int i, int i2) {
        ShortcutService shortcutService = this.mService;
        if (!shortcutService.areShortcutsSupportedOnHomeScreen(i)) {
            return null;
        }
        int profileParentId = shortcutService.mUserManagerInternal.getProfileParentId(i);
        String defaultLauncher = shortcutService.getDefaultLauncher(profileParentId);
        if (defaultLauncher == null) {
            Log.e("ShortcutService", "Default launcher not found.");
            return null;
        }
        ComponentName injectGetPinConfirmationActivity = shortcutService.injectGetPinConfirmationActivity(profileParentId, i2, defaultLauncher);
        if (injectGetPinConfirmationActivity == null) {
            return null;
        }
        return Pair.create(injectGetPinConfirmationActivity, Integer.valueOf(profileParentId));
    }

    public final boolean requestPinItemLocked(ShortcutInfo shortcutInfo, AppWidgetProviderInfo appWidgetProviderInfo, Bundle bundle, int i, IntentSender intentSender, int i2) {
        int i3 = shortcutInfo != null ? 1 : 2;
        Pair requestPinConfirmationActivity = getRequestPinConfirmationActivity(i, i3);
        ShortcutService shortcutService = this.mService;
        if (i2 == 2) {
            int profileParentId = shortcutService.mUserManagerInternal.getProfileParentId(i);
            ComponentName injectGetPinConfirmationActivity = shortcutService.injectGetPinConfirmationActivity(profileParentId, i3, "com.sec.android.app.desktoplauncher");
            requestPinConfirmationActivity = injectGetPinConfirmationActivity == null ? null : Pair.create(injectGetPinConfirmationActivity, Integer.valueOf(profileParentId));
        }
        Pair pair = requestPinConfirmationActivity;
        if (pair == null) {
            Log.w("ShortcutService", "Launcher doesn't support requestPinnedShortcut(). Shortcut not created.");
            return false;
        }
        int intValue = ((Integer) pair.second).intValue();
        shortcutService.throwIfUserLockedL(intValue);
        LauncherApps.PinItemRequest requestPinShortcutLocked = shortcutInfo != null ? requestPinShortcutLocked(shortcutInfo, intentSender, ((ComponentName) pair.first).getPackageName(), ((Integer) pair.second).intValue()) : new LauncherApps.PinItemRequest(new PinAppWidgetRequestInner(this, intentSender, shortcutService.injectGetPackageUid(intValue, ((ComponentName) pair.first).getPackageName()), appWidgetProviderInfo, bundle), 2);
        ComponentName componentName = (ComponentName) pair.first;
        Intent intent = new Intent(i3 == 1 ? "android.content.pm.action.CONFIRM_PIN_SHORTCUT" : "android.content.pm.action.CONFIRM_PIN_APPWIDGET");
        intent.setComponent(componentName);
        intent.putExtra("android.content.pm.extra.PIN_ITEM_REQUEST", requestPinShortcutLocked);
        intent.addFlags(268468224);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i2 == 2) {
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(i2);
            try {
                shortcutService.mContext.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.of(intValue));
                return true;
            } catch (RuntimeException e) {
                Log.e("ShortcutService", "Unable to start activity " + componentName, e);
            } finally {
            }
        } else {
            try {
                shortcutService.mContext.startActivityAsUser(intent, UserHandle.of(intValue));
                return true;
            } catch (RuntimeException e2) {
                Log.e("ShortcutService", "Unable to start activity " + componentName, e2);
            } finally {
            }
        }
        return false;
    }

    public final LauncherApps.PinItemRequest requestPinShortcutLocked(ShortcutInfo shortcutInfo, IntentSender intentSender, String str, int i) {
        ShortcutInfo clone;
        IntentSender intentSender2;
        String str2 = shortcutInfo.getPackage();
        int userId = shortcutInfo.getUserId();
        ShortcutService shortcutService = this.mService;
        ShortcutInfo findShortcutById = shortcutService.getPackageShortcutsForPublisherLocked(userId, str2).findShortcutById(shortcutInfo.getId());
        boolean z = findShortcutById != null;
        if (z) {
            findShortcutById.isVisibleToPublisher();
        }
        if (z) {
            Preconditions.checkArgument(findShortcutById.isEnabled(), "Shortcut ID=" + findShortcutById + " already exists but disabled.");
            boolean hasPinned = shortcutService.getLauncherShortcutsLocked(findShortcutById.getUserId(), i, str).hasPinned(findShortcutById);
            if (hasPinned) {
                intentSender2 = null;
                sendResultIntent(intentSender, null);
            } else {
                intentSender2 = intentSender;
            }
            clone = findShortcutById.clone(27);
            if (!hasPinned) {
                clone.clearFlags(2);
            }
        } else {
            if (shortcutInfo.getActivity() == null) {
                shortcutInfo.setActivity(shortcutService.injectGetDefaultMainActivity(shortcutInfo.getUserId(), shortcutInfo.getPackage()));
            }
            shortcutService.fixUpIncomingShortcutInfo(shortcutInfo, false, true);
            shortcutInfo.resolveResourceStrings(shortcutService.injectGetResourcesForApplicationAsUser(shortcutInfo.getUserId(), shortcutInfo.getPackage()));
            clone = shortcutInfo.clone(26);
            intentSender2 = intentSender;
        }
        return new LauncherApps.PinItemRequest(new PinShortcutRequestInner(this, shortcutInfo, clone, intentSender2, str, i, shortcutService.injectGetPackageUid(i, str)), 1);
    }

    public final void sendResultIntent(IntentSender intentSender, Intent intent) {
        ShortcutService shortcutService = this.mService;
        shortcutService.getClass();
        if (intentSender == null) {
            return;
        }
        try {
            intentSender.sendIntent(shortcutService.mContext, 0, intent, null, null, null, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(2).toBundle());
        } catch (IntentSender.SendIntentException e) {
            Slog.w("ShortcutService", "sendIntent failed().", e);
        }
    }
}
