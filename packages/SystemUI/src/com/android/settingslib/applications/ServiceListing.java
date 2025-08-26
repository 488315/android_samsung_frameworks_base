package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes.dex */
public class ServiceListing {
    public final boolean mAddDeviceLockedFlags;
    public final List mCallbacks;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final HashSet mEnabledServices;
    public final String mIntentAction;
    public boolean mListening;
    public final String mNoun;
    public final AnonymousClass2 mPackageReceiver;
    public final String mPermission;
    public final List mServices;
    public final String mSetting;
    public final AnonymousClass1 mSettingsObserver;
    public final String mTag;
    public final Predicate mValidator;

    public class Builder {
        public final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }
    }

    public /* synthetic */ ServiceListing(Context context) {
        this(context, "controls_providers", "controls_providers", "android.service.controls.ControlsProviderService", "android.permission.BIND_CONTROLS", "Controls Provider", true, null);
    }

    public final void reload() {
        final ControlsListingControllerImpl controlsListingControllerImpl;
        this.mEnabledServices.clear();
        String string = Settings.Secure.getString(this.mContentResolver, this.mSetting);
        int i = 0;
        if (string != null && !"".equals(string)) {
            for (String str : string.split(":")) {
                ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
                if (componentNameUnflattenFromString != null) {
                    this.mEnabledServices.add(componentNameUnflattenFromString);
                }
            }
        }
        ((ArrayList) this.mServices).clear();
        int currentUser = ActivityManager.getCurrentUser();
        int i2 = this.mAddDeviceLockedFlags ? 786564 : 132;
        PackageManager packageManager = this.mContext.getPackageManager();
        Iterator it = packageManager.queryIntentServicesAsUser(new Intent(this.mIntentAction), i2, currentUser).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if (!this.mEnabledServices.contains(serviceInfo.getComponentName())) {
                String str2 = serviceInfo.permission;
                String str3 = this.mPermission;
                if (str3.equals(str2)) {
                    Predicate predicate = this.mValidator;
                    if (predicate == null || predicate.test(serviceInfo)) {
                        ((ArrayList) this.mServices).add(serviceInfo);
                    }
                } else {
                    Slog.w(this.mTag, "Skipping " + this.mNoun + " service " + serviceInfo.packageName + "/" + serviceInfo.name + ": it does not require the permission " + str3);
                }
            }
        }
        Iterator it2 = this.mEnabledServices.iterator();
        while (it2.hasNext()) {
            Iterator it3 = packageManager.queryIntentServicesAsUser(new Intent().setComponent((ComponentName) it2.next()), i2, currentUser).iterator();
            while (it3.hasNext()) {
                ((ArrayList) this.mServices).add(((ResolveInfo) it3.next()).serviceInfo);
            }
        }
        ArrayList arrayList = (ArrayList) this.mCallbacks;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = (ControlsListingControllerImpl$serviceListingCallback$1) obj;
            List list = this.mServices;
            controlsListingControllerImpl$serviceListingCallback$1.getClass();
            ArrayList arrayList2 = (ArrayList) list;
            Log.d("ControlsListingControllerImpl", "ServiceConfig reloaded, count: " + arrayList2.size());
            final ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it4 = arrayList2.iterator();
            while (true) {
                boolean zHasNext = it4.hasNext();
                controlsListingControllerImpl = controlsListingControllerImpl$serviceListingCallback$1.this$0;
                if (zHasNext) {
                    ServiceInfo serviceInfo2 = (ServiceInfo) it4.next();
                    Context userContext = ((UserTrackerImpl) controlsListingControllerImpl.userTracker).getUserContext();
                    serviceInfo2.getClass();
                    arrayList3.add(new ControlsServiceInfo(userContext, serviceInfo2));
                }
            }
            controlsListingControllerImpl.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1
                public final /* synthetic */ List $newServices;

                public AnonymousClass1(final List<? extends ControlsServiceInfo> arrayList32) {
                    list = arrayList32;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (controlsListingControllerImpl.userChangeInProgress.get() > 0) {
                        return;
                    }
                    controlsListingControllerImpl.updateServices(list);
                }
            });
        }
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSettingsObserver;
        AnonymousClass2 anonymousClass2 = this.mPackageReceiver;
        if (!z) {
            this.mContext.unregisterReceiver(anonymousClass2);
            this.mContentResolver.unregisterContentObserver(anonymousClass1);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(anonymousClass2, intentFilter);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(this.mSetting), false, anonymousClass1);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.applications.ServiceListing$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settingslib.applications.ServiceListing$2] */
    private ServiceListing(Context context, String str, String str2, String str3, String str4, String str5, boolean z, Predicate predicate) {
        this.mEnabledServices = new HashSet();
        this.mServices = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mSettingsObserver = new ContentObserver(new Handler()) { // from class: com.android.settingslib.applications.ServiceListing.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2, Uri uri) {
                ServiceListing.this.reload();
            }
        };
        this.mPackageReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.applications.ServiceListing.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                ServiceListing.this.reload();
            }
        };
        this.mContentResolver = context.getContentResolver();
        this.mContext = context;
        this.mTag = str;
        this.mSetting = str2;
        this.mIntentAction = str3;
        this.mPermission = str4;
        this.mNoun = str5;
        this.mAddDeviceLockedFlags = z;
        this.mValidator = predicate;
    }
}
