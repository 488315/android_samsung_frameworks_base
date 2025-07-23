package com.android.systemui.qs.customize;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecTileQueryHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SecTileQueryHelper f$0;
    public final /* synthetic */ QSHost f$1;

    public /* synthetic */ SecTileQueryHelper$$ExternalSyntheticLambda0(SecTileQueryHelper secTileQueryHelper, QSHost qSHost) {
        this.f$0 = secTileQueryHelper;
        this.f$1 = qSHost;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        QSTile.State state;
        Drawable loadIcon;
        Bundle bundle;
        SecTileQueryHelper secTileQueryHelper = this.f$0;
        QSHost qSHost = this.f$1;
        secTileQueryHelper.getClass();
        Collection tiles = qSHost.getTiles();
        PackageManager packageManager = secTileQueryHelper.mContext.getPackageManager();
        Intent intent = new Intent("android.service.quicksettings.action.QS_TILE");
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) secTileQueryHelper.mUserTracker;
        List<ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, userTrackerImpl.getUserId());
        secTileQueryHelper.mContext.getString(R.string.quick_settings_tiles_stock);
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            Bundle bundle2 = serviceInfo.metaData;
            if (bundle2 != null) {
                if (!"OWNER".equals(bundle2.getString("android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY", "")) || userTrackerImpl.getUserId() == 0) {
                    if (serviceInfo.metaData.getBoolean("android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY", false)) {
                    }
                }
            }
            CharSequence loadLabel = resolveInfo.serviceInfo.applicationInfo.loadLabel(packageManager);
            String spec = CustomTile.toSpec(componentName);
            if (qSHost.isUnsupportedTile(spec)) {
                str = "addPackageTiles : isUnsupportedTile : ";
            } else if (!qSHost.isAvailableCustomTile(spec) || ("com.sec.unifiedwfc.ux.quicksettings.WFCQSTileService".equalsIgnoreCase(componentName.getClassName()) && DeviceState.getVoWifiEnableState(secTileQueryHelper.mContext) == 0)) {
                str = "addPackageTiles : isAvailableCustomTile = false : ";
            } else if (qSHost.shouldBeHiddenByKnox(spec)) {
                str = "addPackageTiles : shouldBeHiddenByKnox : ";
            } else if (qSHost.isBarTile(spec)) {
                str = "addPackageTiles : isBarTile : ";
            } else {
                Log.d("TileQueryHelper", "addPackageTiles : spec = " + spec);
                if ((Operator.isChinaQsTileBranding() && componentName.getClassName() != null && componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService")) || (Operator.isChinaQsTileBranding() && componentName.getClassName() != null && componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.scribe.service.ScribeTileService"))) {
                    Log.d("TileQueryHelper", "addPackageTiles : remove componentName : " + componentName.getClassName());
                } else {
                    ArrayList arrayList = (ArrayList) tiles;
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            state = null;
                            break;
                        }
                        Object obj = arrayList.get(i);
                        i++;
                        QSTile qSTile = (QSTile) obj;
                        if (spec.equals(qSTile.getTileSpec())) {
                            state = qSTile.getState().copy();
                            break;
                        }
                    }
                    if (state != null) {
                        secTileQueryHelper.addTile(spec, loadLabel, state, false);
                    } else {
                        ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
                        if (serviceInfo2.icon != 0 || serviceInfo2.applicationInfo.icon != 0) {
                            int userId = userTrackerImpl.getUserId();
                            try {
                                ServiceInfo serviceInfo3 = resolveInfo.serviceInfo;
                                int i2 = serviceInfo3.icon;
                                if (i2 == 0) {
                                    i2 = serviceInfo3.applicationInfo.icon;
                                }
                                Icon createWithResource = i2 != 0 ? Icon.createWithResource(serviceInfo3.packageName, i2) : null;
                                loadIcon = createWithResource != null ? createWithResource.loadDrawableAsUser(secTileQueryHelper.mContext, userId) : resolveInfo.serviceInfo.loadIcon(packageManager);
                            } catch (Exception unused) {
                                loadIcon = resolveInfo.serviceInfo.loadIcon(packageManager);
                            }
                            if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(resolveInfo.serviceInfo.permission) && loadIcon != null) {
                                loadIcon.mutate();
                                loadIcon.setTint(secTileQueryHelper.mContext.getColor(android.R.color.white));
                                CharSequence loadLabel2 = resolveInfo.serviceInfo.loadLabel(packageManager);
                                String charSequence = loadLabel2 != null ? loadLabel2.toString() : "null";
                                QSTile.State state2 = new QSTile.State();
                                state2.state = 1;
                                state2.label = charSequence;
                                state2.contentDescription = charSequence;
                                try {
                                    bundle = secTileQueryHelper.mContext.getPackageManager().getServiceInfo(CustomTile.getComponentFromSpec(spec), 786560).metaData;
                                } catch (PackageManager.NameNotFoundException unused2) {
                                }
                                if (bundle != null && !"".equals(bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", ""))) {
                                    state2.icon = new QSTileImpl.DrawableIcon(loadIcon);
                                    secTileQueryHelper.addTile(spec, loadLabel, state2, false);
                                }
                                ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(loadIcon, SecurityUtils$$ExternalSyntheticOutline0.m(secTileQueryHelper.mContext, R.dimen.qs_non_sec_customtile_icon_resize_ratio, secTileQueryHelper.mResourcePicker.getTileIconSize(secTileQueryHelper.mContext) / loadIcon.getIntrinsicWidth()));
                                scalingDrawableWrapper.mCloneDrawable = loadIcon.getConstantState().newDrawable();
                                state2.icon = new QSTileImpl.DrawableIcon(scalingDrawableWrapper, secTileQueryHelper.mContext);
                                secTileQueryHelper.addTile(spec, loadLabel, state2, false);
                            }
                        }
                    }
                }
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, spec, "TileQueryHelper");
        }
        secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda1(secTileQueryHelper, true, new ArrayList(secTileQueryHelper.mTiles)));
    }
}
