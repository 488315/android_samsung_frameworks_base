package com.android.systemui.controls;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import com.android.settingslib.applications.DefaultAppInfo;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ControlsServiceInfo extends DefaultAppInfo {
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002f, code lost:
    
        if (r0 != null) goto L24;
     */
    @Override // com.android.settingslib.applications.DefaultAppInfo
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.CharSequence loadLabel() {
        /*
            r5 = this;
            android.content.ComponentName r0 = r5.panelActivity
            java.lang.String r1 = ""
            if (r0 != 0) goto Le
            java.lang.CharSequence r5 = super.loadLabel()
            if (r5 != 0) goto Ld
            return r1
        Ld:
            return r5
        Le:
            android.content.ComponentName r0 = r5.componentName
            if (r0 == 0) goto L31
            android.content.pm.PackageManager r2 = r5.mPm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            java.lang.String r0 = r0.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            int r3 = r5.userId     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            r4 = 0
            android.content.pm.ApplicationInfo r0 = r2.getApplicationInfoAsUser(r0, r4, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            android.content.pm.PackageManager r2 = r5.mPm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            java.lang.CharSequence r0 = r0.loadLabel(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L26
            goto L2f
        L26:
            java.lang.CharSequence r0 = super.loadLabel()
            if (r0 != 0) goto L2d
            goto L2e
        L2d:
            r1 = r0
        L2e:
            r0 = r1
        L2f:
            if (r0 != 0) goto L41
        L31:
            android.content.pm.PackageItemInfo r0 = r5.packageItemInfo
            if (r0 == 0) goto L3d
            android.content.pm.PackageManager r5 = r5.mPm
            java.lang.CharSequence r5 = r0.loadLabel(r5)
        L3b:
            r0 = r5
            goto L3f
        L3d:
            r5 = 0
            goto L3b
        L3f:
            if (r0 == 0) goto L42
        L41:
            return r0
        L42:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Package info is missing"
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ControlsServiceInfo.loadLabel():java.lang.CharSequence");
    }

    public final String toString() {
        return StringsKt__IndentKt.trimIndent("\n            ControlsServiceInfo(serviceInfo=" + this.serviceInfo + ", panelActivity=" + this.panelActivity + ", resolved=" + this.resolved + ")\n        ");
    }
}
