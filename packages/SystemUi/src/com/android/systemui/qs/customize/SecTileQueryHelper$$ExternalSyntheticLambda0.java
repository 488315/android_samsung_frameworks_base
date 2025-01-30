package com.android.systemui.qs.customize;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecQSCustomizerBase;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.DeviceState;
import com.samsung.android.feature.SemCscFeature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecTileQueryHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ SecTileQueryHelper f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ SecTileQueryHelper$$ExternalSyntheticLambda0(SecTileQueryHelper secTileQueryHelper, QSHost qSHost, boolean z) {
        this.f$0 = secTileQueryHelper;
        this.f$1 = qSHost;
        this.f$2 = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x028c  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        String str;
        ArrayList arrayList;
        QSTile.State state;
        Drawable loadIcon;
        String str2;
        boolean z;
        Bundle bundle;
        boolean z2;
        ArrayList arrayList2;
        String str3 = "";
        boolean z3 = false;
        switch (this.$r8$classId) {
            case 0:
                SecTileQueryHelper secTileQueryHelper = this.f$0;
                QSHost qSHost = (QSHost) this.f$1;
                boolean z4 = this.f$2;
                secTileQueryHelper.getClass();
                Collection tiles = qSHost.getTiles();
                Context context = secTileQueryHelper.mContext;
                PackageManager packageManager = context.getPackageManager();
                Intent intent = new Intent("android.service.quicksettings.action.QS_TILE");
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) secTileQueryHelper.mUserTracker;
                List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, userTrackerImpl.getUserId());
                context.getString(R.string.quick_settings_tiles_stock);
                String string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigRemoveQuickSettingItem", "");
                ArrayList arrayList3 = new ArrayList();
                for (String str4 : string.split(",")) {
                    String trim = str4.trim();
                    if (!trim.isEmpty()) {
                        arrayList3.add(trim);
                    }
                }
                Iterator it = queryIntentServicesAsUser.iterator();
                while (it.hasNext()) {
                    ResolveInfo resolveInfo = (ResolveInfo) it.next();
                    ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                    ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                    Bundle bundle2 = serviceInfo.metaData;
                    if (bundle2 != null) {
                        if (!"OWNER".equals(bundle2.getString("android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY", str3)) || userTrackerImpl.getUserId() == 0) {
                            if (serviceInfo.metaData.getBoolean("android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY", z3)) {
                            }
                        }
                    }
                    CharSequence loadLabel = resolveInfo.serviceInfo.applicationInfo.loadLabel(packageManager);
                    String spec = CustomTile.toSpec(componentName);
                    String customTileNameFromSpec = qSHost.getCustomTileNameFromSpec(spec);
                    Iterator it2 = it;
                    AbstractC0000x2c234b15.m3m("addPackageTiles : tileName = ", customTileNameFromSpec, "TileQueryHelper");
                    if (customTileNameFromSpec != null) {
                        if (arrayList3.contains(customTileNameFromSpec)) {
                            Log.d("TileQueryHelper", "addPackageTiles : shouldBlockTileArray = " + arrayList3);
                            str = str3;
                            arrayList = arrayList3;
                        } else {
                            if (qSHost.isAvailableCustomTile(customTileNameFromSpec)) {
                                if ("WifiCalling".equals(customTileNameFromSpec)) {
                                    Point point = DeviceState.sDisplaySize;
                                    arrayList = arrayList3;
                                    str = str3;
                                    if (Settings.System.getInt(context.getContentResolver(), "vowifi_menu_enable", 0) == 0) {
                                        z2 = false;
                                        if (z2) {
                                            if (qSHost.shouldBeHiddenByKnox(spec)) {
                                                Log.d("TileQueryHelper", "addPackageTiles : shouldBeHiddenByKnox : ".concat(customTileNameFromSpec));
                                            } else if (!z4 && qSHost.isBarTile(customTileNameFromSpec)) {
                                                Log.d("TileQueryHelper", "addPackageTiles : isBarTile : ".concat(customTileNameFromSpec));
                                            }
                                        }
                                    }
                                } else {
                                    str = str3;
                                    arrayList = arrayList3;
                                }
                                z2 = true;
                                if (z2) {
                                }
                            } else {
                                str = str3;
                                arrayList = arrayList3;
                            }
                            Log.d("TileQueryHelper", "addPackageTiles : isAvailableCustomTile = false : ".concat(customTileNameFromSpec));
                        }
                        str2 = str;
                        z3 = false;
                        it = it2;
                        str3 = str2;
                        arrayList3 = arrayList;
                    } else {
                        str = str3;
                        arrayList = arrayList3;
                    }
                    if (((!Operator.isChinaQsTileBranding()) || componentName.getClassName() == null || !componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService")) && ((!Operator.isChinaQsTileBranding()) || componentName.getClassName() == null || !componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.scribe.service.ScribeTileService"))) {
                        Iterator it3 = tiles.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                QSTile qSTile = (QSTile) it3.next();
                                if (spec.equals(qSTile.getTileSpec())) {
                                    state = qSTile.getState().copy();
                                }
                            } else {
                                state = null;
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
                                    int i = serviceInfo3.icon;
                                    if (i == 0) {
                                        i = serviceInfo3.applicationInfo.icon;
                                    }
                                    Icon createWithResource = i != 0 ? Icon.createWithResource(serviceInfo3.packageName, i) : null;
                                    loadIcon = createWithResource != null ? createWithResource.loadDrawableAsUser(context, userId) : resolveInfo.serviceInfo.loadIcon(packageManager);
                                } catch (Exception unused) {
                                    loadIcon = resolveInfo.serviceInfo.loadIcon(packageManager);
                                }
                                if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(resolveInfo.serviceInfo.permission) && loadIcon != null) {
                                    loadIcon.mutate();
                                    loadIcon.setTint(context.getColor(android.R.color.white));
                                    CharSequence loadLabel2 = resolveInfo.serviceInfo.loadLabel(packageManager);
                                    String charSequence = loadLabel2 != null ? loadLabel2.toString() : "null";
                                    QSTile.State state2 = new QSTile.State();
                                    state2.state = 1;
                                    state2.label = charSequence;
                                    state2.contentDescription = charSequence;
                                    try {
                                        bundle = context.getPackageManager().getServiceInfo(CustomTile.getComponentFromSpec(spec), 786560).metaData;
                                    } catch (PackageManager.NameNotFoundException unused2) {
                                    }
                                    if (bundle != null) {
                                        str2 = str;
                                        if (!str2.equals(bundle.getString("android.service.quicksettings.SEM_DEFAULT_TILE_NAME", str2))) {
                                            z = true;
                                            if (z) {
                                                state2.icon = new QSTileImpl.DrawableIcon(loadIcon);
                                            } else {
                                                secTileQueryHelper.mResourcePicker.getClass();
                                                ScalingDrawableWrapper scalingDrawableWrapper = new ScalingDrawableWrapper(loadIcon, context.getResources().getFloat(R.dimen.qs_non_sec_customtile_icon_resize_ratio) * (SecQSPanelResourcePicker.getTileIconSize(context) / loadIcon.getIntrinsicWidth()));
                                                scalingDrawableWrapper.mCloneDrawable = loadIcon.getConstantState().newDrawable();
                                                state2.icon = new QSTileImpl.DrawableIcon(scalingDrawableWrapper, context);
                                            }
                                            secTileQueryHelper.addTile(spec, loadLabel, state2, false);
                                            z3 = false;
                                            it = it2;
                                            str3 = str2;
                                            arrayList3 = arrayList;
                                        }
                                        z = false;
                                        if (z) {
                                        }
                                        secTileQueryHelper.addTile(spec, loadLabel, state2, false);
                                        z3 = false;
                                        it = it2;
                                        str3 = str2;
                                        arrayList3 = arrayList;
                                    }
                                    str2 = str;
                                    z = false;
                                    if (z) {
                                    }
                                    secTileQueryHelper.addTile(spec, loadLabel, state2, false);
                                    z3 = false;
                                    it = it2;
                                    str3 = str2;
                                    arrayList3 = arrayList;
                                }
                            }
                        }
                    } else {
                        Log.d("TileQueryHelper", "addPackageTiles : remove componentName : " + componentName.getClassName());
                    }
                    str2 = str;
                    z3 = false;
                    it = it2;
                    str3 = str2;
                    arrayList3 = arrayList;
                }
                secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, true, new ArrayList(secTileQueryHelper.mTiles)));
                break;
            default:
                SecTileQueryHelper secTileQueryHelper2 = this.f$0;
                boolean z5 = this.f$2;
                ArrayList arrayList4 = (ArrayList) this.f$1;
                secTileQueryHelper2.mTileQueryFinished = z5;
                SecTileQueryHelper.TileStateListener tileStateListener = secTileQueryHelper2.mListener;
                if (tileStateListener != null) {
                    SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = (SecQSCustomizerTileAdapter) tileStateListener;
                    secQSCustomizerTileAdapter.mAllTiles = arrayList4;
                    if (secQSCustomizerTileAdapter.mCurrentSpecs != null && arrayList4 != null && secQSCustomizerTileAdapter.mTileQueryHelper.mTileQueryFinished) {
                        Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs = " + secQSCustomizerTileAdapter.mCurrentSpecs + "mAllTiles size = " + secQSCustomizerTileAdapter.mAllTiles.size());
                        ArrayList arrayList5 = new ArrayList(secQSCustomizerTileAdapter.mAllTiles);
                        for (int i2 = 0; i2 < ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).size(); i2++) {
                            String str5 = (String) ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).get(i2);
                            int i3 = 0;
                            while (true) {
                                if (i3 >= arrayList5.size()) {
                                    break;
                                } else if (((SecTileQueryHelper.TileInfo) arrayList5.get(i3)).spec.equals(str5)) {
                                } else {
                                    i3++;
                                }
                            }
                        }
                        Iterator it4 = arrayList5.iterator();
                        while (it4.hasNext()) {
                            ((SecTileQueryHelper.TileInfo) it4.next()).isActive = false;
                        }
                        ArrayList arrayList6 = new ArrayList();
                        StringBuilder sb = new StringBuilder();
                        Context context2 = secQSCustomizerTileAdapter.mContext;
                        sb.append(context2.getString(R.string.qs_edit_setting_available_area_tapped));
                        sb.append(" ");
                        String sb2 = sb.toString();
                        String m73m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context2, R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder, new StringBuilder(", "));
                        for (int i4 = 0; i4 < arrayList5.size(); i4++) {
                            SecQSCustomizerBase.CustomTileInfo customTileInfo = new SecQSCustomizerBase.CustomTileInfo();
                            SecTileQueryHelper.TileInfo tileInfo = (SecTileQueryHelper.TileInfo) arrayList5.get(i4);
                            customTileInfo.spec = tileInfo.spec;
                            customTileInfo.isActive = tileInfo.isActive;
                            customTileInfo.state = tileInfo.state;
                            String str6 = tileInfo.spec;
                            if (str6 != null && str6.startsWith("custom(")) {
                                CustomTile.getComponentFromSpec(str6);
                            }
                            customTileInfo.isNewCustomTile = false;
                            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(sb2);
                            m18m.append((Object) customTileInfo.state.label);
                            customTileInfo.customizeTileContentDes = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(m18m, customTileInfo.isNewCustomTile ? KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context2, R.string.controls_badge_description, new StringBuilder(", ")) : "", m73m);
                            arrayList6.add(customTileInfo);
                        }
                        secQSCustomizerTileAdapter.mDefaultAvailableTiles = arrayList6;
                        secQSCustomizerTileAdapter.mAvailableTiles = arrayList6;
                    }
                    SecQSCustomizerController$$ExternalSyntheticLambda6 secQSCustomizerController$$ExternalSyntheticLambda6 = secQSCustomizerTileAdapter.mOnTileChangedCallback;
                    if (secQSCustomizerController$$ExternalSyntheticLambda6 != null && (arrayList2 = secQSCustomizerTileAdapter.mAvailableTiles) != null) {
                        SecQSCustomizerController secQSCustomizerController = secQSCustomizerController$$ExternalSyntheticLambda6.f$0;
                        secQSCustomizerController.mAvailableTileLayout.addTiles(arrayList2);
                        Iterator it5 = arrayList2.iterator();
                        while (it5.hasNext()) {
                            ((SecQSCustomizerBase.CustomTileInfo) it5.next()).longClickListener = secQSCustomizerController.mLongClickListener;
                        }
                    }
                    if (secQSCustomizerTileAdapter.mAvailableTiles != null) {
                        secQSCustomizerTileAdapter.mIsLoadedAllTiles = true;
                    }
                }
                secTileQueryHelper2.mFinished = z5;
                break;
        }
    }

    public /* synthetic */ SecTileQueryHelper$$ExternalSyntheticLambda0(SecTileQueryHelper secTileQueryHelper, boolean z, ArrayList arrayList) {
        this.f$0 = secTileQueryHelper;
        this.f$2 = z;
        this.f$1 = arrayList;
    }
}
