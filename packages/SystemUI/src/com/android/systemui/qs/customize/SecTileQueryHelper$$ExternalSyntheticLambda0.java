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
        QSTile.State stateCopy;
        Drawable drawableLoadIcon;
        Bundle bundle;
        SecTileQueryHelper secTileQueryHelper = this.f$0;
        QSHost qSHost = this.f$1;
        secTileQueryHelper.getClass();
        Collection tiles = qSHost.getTiles();
        PackageManager packageManager = secTileQueryHelper.mContext.getPackageManager();
        Intent intent = new Intent("android.service.quicksettings.action.QS_TILE");
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) secTileQueryHelper.mUserTracker;
        List<ResolveInfo> listQueryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, userTrackerImpl.getUserId());
        secTileQueryHelper.mContext.getString(R.string.quick_settings_tiles_stock);
        for (ResolveInfo resolveInfo : listQueryIntentServicesAsUser) {
            ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            Bundle bundle2 = serviceInfo.metaData;
            if (bundle2 != null) {
                if (!"OWNER".equals(bundle2.getString("android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY", "")) || userTrackerImpl.getUserId() == 0) {
                    if (serviceInfo.metaData.getBoolean("android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY", false)) {
                    }
                }
            }
            CharSequence charSequenceLoadLabel = resolveInfo.serviceInfo.applicationInfo.loadLabel(packageManager);
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
                            stateCopy = null;
                            break;
                        }
                        Object obj = arrayList.get(i);
                        i++;
                        QSTile qSTile = (QSTile) obj;
                        if (spec.equals(qSTile.getTileSpec())) {
                            stateCopy = qSTile.getState().copy();
                            break;
                        }
                    }
                    if (stateCopy != null) {
                        secTileQueryHelper.addTile(spec, charSequenceLoadLabel, stateCopy, false);
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
                                Icon iconCreateWithResource = i2 != 0 ? Icon.createWithResource(serviceInfo3.packageName, i2) : null;
                                drawableLoadIcon = iconCreateWithResource != null ? iconCreateWithResource.loadDrawableAsUser(secTileQueryHelper.mContext, userId) : resolveInfo.serviceInfo.loadIcon(packageManager);
                            } catch (Exception unused) {
                                drawableLoadIcon = resolveInfo.serviceInfo.loadIcon(packageManager);
                            }
                            if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(resolveInfo.serviceInfo.permission) && drawableLoadIcon != null) {
                                drawableLoadIcon.mutate();
                                drawableLoadIcon.setTint(secTileQueryHelper.mContext.getColor(android.R.color.white));
                                CharSequence charSequenceLoadLabel2 = resolveInfo.serviceInfo.loadLabel(packageManager);
                                String string = charSequenceLoadLabel2 != null ? charSequenceLoadLabel2.toString() : "null";
                                QSTile.State state = new QSTile.State();
                                state.state = 1;
                                state.label = string;
                                state.contentDescription = string;
                                try {
                                    bundle = secTileQueryHelper.mContext.getPackageManager().getServiceInfo(CustomTile.getComponentFromSpec(spec), 786560).metaData;
                                } catch (PackageManager.NameNotFoundException unused2) {
                                }
                                if (bundle == null || "".equals(bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", ""))) {
                                    ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(drawableLoadIcon, SecurityUtils$$ExternalSyntheticOutline0.m(secTileQueryHelper.mContext, R.dimen.qs_non_sec_customtile_icon_resize_ratio, secTileQueryHelper.mResourcePicker.getTileIconSize(secTileQueryHelper.mContext) / drawableLoadIcon.getIntrinsicWidth()));
                                    scalingDrawableWrapper.mCloneDrawable = drawableLoadIcon.getConstantState().newDrawable();
                                    state.icon = new QSTileImpl.DrawableIcon(scalingDrawableWrapper, secTileQueryHelper.mContext);
                                } else {
                                    state.icon = new QSTileImpl.DrawableIcon(drawableLoadIcon);
                                }
                                secTileQueryHelper.addTile(spec, charSequenceLoadLabel, state, false);
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
