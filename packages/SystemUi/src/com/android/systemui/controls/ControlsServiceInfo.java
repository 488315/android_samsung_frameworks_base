package com.android.systemui.controls;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.IconDrawableFactory;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.systemui.BasicRune;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsServiceInfo extends DefaultAppInfo {
    public final ComponentName _panelActivity;
    public final Context context;
    public ComponentName panelActivity;
    public boolean resolved;
    public final ServiceInfo serviceInfo;

    public ControlsServiceInfo(Context context, ServiceInfo serviceInfo) {
        super(context, context.getPackageManager(), context.getUserId(), serviceInfo.getComponentName());
        this.context = context;
        this.serviceInfo = serviceInfo;
        Bundle bundle = serviceInfo.metaData;
        String string = bundle != null ? bundle.getString("android.service.controls.META_DATA_PANEL_ACTIVITY") : null;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string == null ? "" : string);
        if (unflattenFromString == null || !Intrinsics.areEqual(unflattenFromString.getPackageName(), this.componentName.getPackageName())) {
            this._panelActivity = null;
        } else {
            this._panelActivity = unflattenFromString;
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ControlsServiceInfo) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
            if (this.userId == controlsServiceInfo.userId && Intrinsics.areEqual(this.componentName, controlsServiceInfo.componentName) && Intrinsics.areEqual(this.panelActivity, controlsServiceInfo.panelActivity)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.userId), this.componentName, this.panelActivity);
    }

    public final Drawable loadIcon() {
        String str;
        ComponentName componentName = this.componentName;
        if (componentName == null || (str = componentName.getPackageName()) == null) {
            PackageItemInfo packageItemInfo = this.packageItemInfo;
            str = packageItemInfo != null ? packageItemInfo.packageName : null;
            if (str == null) {
                throw new IllegalArgumentException("Package info is missing");
            }
        }
        return IconDrawableFactory.newInstance(this.context).getBadgedIcon(this.mPm.getApplicationInfoAsUser(str, 0, this.userId));
    }

    public final CharSequence loadLabel() {
        CharSequence loadLabel;
        ComponentInfo componentInfo;
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        int i = this.userId;
        PackageItemInfo packageItemInfo = this.packageItemInfo;
        ComponentName componentName = this.componentName;
        PackageManager packageManager = this.mPm;
        if (!z || this.panelActivity != null) {
            if (componentName != null && (loadLabel = packageManager.getApplicationInfoAsUser(componentName.getPackageName(), 0, i).loadLabel(packageManager)) != null) {
                return loadLabel;
            }
            CharSequence loadLabel2 = packageItemInfo != null ? packageItemInfo.loadLabel(packageManager) : null;
            if (loadLabel2 != null) {
                return loadLabel2;
            }
            throw new IllegalArgumentException("Package info is missing");
        }
        if (componentName == null) {
            if (packageItemInfo != null) {
                return packageItemInfo.loadLabel(packageManager);
            }
            return null;
        }
        try {
            try {
                componentInfo = AppGlobals.getPackageManager().getActivityInfo(componentName, 0L, i);
                if (componentInfo == null) {
                    componentInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, i);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        } catch (RemoteException unused2) {
            componentInfo = null;
        }
        return componentInfo != null ? componentInfo.loadLabel(packageManager) : packageManager.getApplicationInfoAsUser(componentName.getPackageName(), 0, i).loadLabel(packageManager);
    }

    public final String toString() {
        return StringsKt__IndentKt.trimIndent("\n            ControlsServiceInfo(serviceInfo=" + this.serviceInfo + ", panelActivity=" + this.panelActivity + ", resolved=" + this.resolved + ")\n        ");
    }
}
