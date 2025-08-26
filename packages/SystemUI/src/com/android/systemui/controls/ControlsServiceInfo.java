package com.android.systemui.controls;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import com.android.settingslib.applications.DefaultAppInfo;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: classes2.dex */
public class ControlsServiceInfo extends DefaultAppInfo {
    public final ComponentName _panelActivity;
    public final Context context;
    public ComponentName panelActivity;
    public boolean resolved;
    public final ServiceInfo serviceInfo;

    public ControlsServiceInfo(Context context, ServiceInfo serviceInfo) {
        String string;
        super(context, context.getPackageManager(), context.getUserId(), serviceInfo.getComponentName());
        this.context = context;
        this.serviceInfo = serviceInfo;
        Bundle bundle = serviceInfo.metaData;
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString((bundle == null || (string = bundle.getString("android.service.controls.META_DATA_PANEL_ACTIVITY")) == null) ? "" : string);
        if (componentNameUnflattenFromString == null || !Intrinsics.areEqual(componentNameUnflattenFromString.getPackageName(), this.componentName.getPackageName())) {
            this._panelActivity = null;
        } else {
            this._panelActivity = componentNameUnflattenFromString;
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ControlsServiceInfo)) {
            return false;
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        return this.userId == controlsServiceInfo.userId && Intrinsics.areEqual(this.componentName, controlsServiceInfo.componentName) && Intrinsics.areEqual(this.panelActivity, controlsServiceInfo.panelActivity);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.userId), this.componentName, this.panelActivity);
    }

    public final Drawable loadIcon() {
        String packageName;
        ComponentName componentName = this.componentName;
        if (componentName == null || (packageName = componentName.getPackageName()) == null) {
            PackageItemInfo packageItemInfo = this.packageItemInfo;
            packageName = packageItemInfo != null ? packageItemInfo.packageName : null;
            if (packageName == null) {
                throw new IllegalArgumentException("Package info is missing");
            }
        }
        return IconDrawableFactory.newInstance(this.context).getBadgedIcon(this.mPm.getApplicationInfoAsUser(packageName, 0, this.userId));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    @Override // com.android.settingslib.applications.DefaultAppInfo
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CharSequence loadLabel() {
        CharSequence charSequenceLoadLabel;
        if (this.panelActivity == null) {
            CharSequence charSequenceLoadLabel2 = super.loadLabel();
            return charSequenceLoadLabel2 == null ? "" : charSequenceLoadLabel2;
        }
        ComponentName componentName = this.componentName;
        if (componentName != null) {
            try {
                charSequenceLoadLabel = this.mPm.getApplicationInfoAsUser(componentName.getPackageName(), 0, this.userId).loadLabel(this.mPm);
            } catch (PackageManager.NameNotFoundException unused) {
                CharSequence charSequenceLoadLabel3 = super.loadLabel();
                charSequenceLoadLabel = charSequenceLoadLabel3 != null ? charSequenceLoadLabel3 : "";
            }
            if (charSequenceLoadLabel == null) {
                PackageItemInfo packageItemInfo = this.packageItemInfo;
                charSequenceLoadLabel = packageItemInfo != null ? packageItemInfo.loadLabel(this.mPm) : null;
                if (charSequenceLoadLabel == null) {
                    throw new IllegalArgumentException("Package info is missing");
                }
            }
        }
        return charSequenceLoadLabel;
    }

    public final String toString() {
        return StringsKt__IndentKt.trimIndent("\n            ControlsServiceInfo(serviceInfo=" + this.serviceInfo + ", panelActivity=" + this.panelActivity + ", resolved=" + this.resolved + ")\n        ");
    }
}
