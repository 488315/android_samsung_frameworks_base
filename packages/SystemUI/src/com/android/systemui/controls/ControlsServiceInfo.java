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
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

public final class ControlsServiceInfo extends DefaultAppInfo {
    public final ComponentName _panelActivity;
    public final Context context;
    public ComponentName panelActivity;
    public boolean resolved;
    public final ServiceInfo serviceInfo;

    public ControlsServiceInfo(Context context, ServiceInfo serviceInfo) {
        super(context, context.getPackageManager(), context.getUserId(), serviceInfo.getComponentName());
        String string;
        this.context = context;
        this.serviceInfo = serviceInfo;
        Bundle bundle = serviceInfo.metaData;
        ComponentName unflattenFromString = ComponentName.unflattenFromString((bundle == null || (string = bundle.getString("android.service.controls.META_DATA_PANEL_ACTIVITY")) == null) ? "" : string);
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
        ComponentName componentName = this.panelActivity;
        CharSequence charSequence = null;
        int i = this.userId;
        if (componentName != null) {
            ComponentName componentName2 = this.componentName;
            if (componentName2 == null || (loadLabel = this.mPm.getApplicationInfoAsUser(componentName2.getPackageName(), 0, i).loadLabel(this.mPm)) == null) {
                PackageItemInfo packageItemInfo = this.packageItemInfo;
                if (packageItemInfo != null) {
                    charSequence = packageItemInfo.loadLabel(this.mPm);
                }
            } else {
                charSequence = loadLabel;
            }
            if (charSequence != null) {
                return charSequence;
            }
            throw new IllegalArgumentException("Package info is missing");
        }
        if (this.componentName == null) {
            PackageItemInfo packageItemInfo2 = this.packageItemInfo;
            if (packageItemInfo2 != null) {
                return packageItemInfo2.loadLabel(this.mPm);
            }
            return null;
        }
        try {
            try {
                componentInfo = AppGlobals.getPackageManager().getActivityInfo(this.componentName, 0L, i);
                if (componentInfo == null) {
                    componentInfo = AppGlobals.getPackageManager().getServiceInfo(this.componentName, 0L, i);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        } catch (RemoteException unused2) {
            componentInfo = null;
        }
        return componentInfo != null ? componentInfo.loadLabel(this.mPm) : this.mPm.getApplicationInfoAsUser(this.componentName.getPackageName(), 0, i).loadLabel(this.mPm);
    }

    public final String toString() {
        return StringsKt__IndentKt.trimIndent("\n            ControlsServiceInfo(serviceInfo=" + this.serviceInfo + ", panelActivity=" + this.panelActivity + ", resolved=" + this.resolved + ")\n        ");
    }
}
