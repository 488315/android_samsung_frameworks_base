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
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda4;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // java.lang.Runnable
    public final void run() {
        QSTile.State state;
        Drawable loadIcon;
        Bundle bundle;
        ArrayList arrayList;
        switch (this.$r8$classId) {
            case 0:
                SecTileQueryHelper secTileQueryHelper = this.f$0;
                QSHost qSHost = (QSHost) this.f$1;
                boolean z = this.f$2;
                secTileQueryHelper.getClass();
                Collection tiles = qSHost.getTiles();
                PackageManager packageManager = secTileQueryHelper.mContext.getPackageManager();
                Intent intent = new Intent("android.service.quicksettings.action.QS_TILE");
                UserTrackerImpl userTrackerImpl = (UserTrackerImpl) secTileQueryHelper.mUserTracker;
                List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, userTrackerImpl.getUserId());
                secTileQueryHelper.mContext.getString(R.string.quick_settings_tiles_stock);
                Iterator it = queryIntentServicesAsUser.iterator();
                while (true) {
                    boolean z2 = true;
                    if (!it.hasNext()) {
                        secTileQueryHelper.mMainExecutor.execute(new SecTileQueryHelper$$ExternalSyntheticLambda0(secTileQueryHelper, z2, new ArrayList(secTileQueryHelper.mTiles)));
                        break;
                    } else {
                        ResolveInfo resolveInfo = (ResolveInfo) it.next();
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
                        String customTileNameFromSpec = qSHost.getCustomTileNameFromSpec(spec);
                        Log.d("TileQueryHelper", "addPackageTiles : tileName = " + customTileNameFromSpec);
                        if (qSHost.isUnsupportedTile(spec)) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("addPackageTiles : isUnsupportedTile : ", spec, "TileQueryHelper");
                        } else {
                            if (customTileNameFromSpec != null) {
                                if (!qSHost.isAvailableCustomTile(customTileNameFromSpec) || ("WifiCalling".equals(customTileNameFromSpec) && DeviceState.getVoWifiEnableState(secTileQueryHelper.mContext) == 0)) {
                                    Log.d("TileQueryHelper", "addPackageTiles : isAvailableCustomTile = false : ".concat(customTileNameFromSpec));
                                } else if (qSHost.shouldBeHiddenByKnox(spec)) {
                                    Log.d("TileQueryHelper", "addPackageTiles : shouldBeHiddenByKnox : ".concat(customTileNameFromSpec));
                                } else if (!z && qSHost.isBarTile(customTileNameFromSpec)) {
                                    Log.d("TileQueryHelper", "addPackageTiles : isBarTile : ".concat(customTileNameFromSpec));
                                }
                            }
                            if (((!Operator.isChinaQsTileBranding()) || componentName.getClassName() == null || !componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.dolphin.service.DolphinTileService")) && ((!Operator.isChinaQsTileBranding()) || componentName.getClassName() == null || !componentName.getClassName().equals("com.google.audio.hearing.visualization.accessibility.scribe.service.ScribeTileService"))) {
                                Iterator it2 = tiles.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        QSTile qSTile = (QSTile) it2.next();
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
                            } else {
                                Log.d("TileQueryHelper", "addPackageTiles : remove componentName : " + componentName.getClassName());
                            }
                        }
                    }
                }
                break;
            default:
                SecTileQueryHelper secTileQueryHelper2 = this.f$0;
                boolean z3 = this.f$2;
                ArrayList arrayList2 = (ArrayList) this.f$1;
                secTileQueryHelper2.mTileQueryFinished = z3;
                SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secTileQueryHelper2.mListener;
                if (secQSCustomizerTileAdapter != null) {
                    secQSCustomizerTileAdapter.mAllTiles = arrayList2;
                    if (secQSCustomizerTileAdapter.mCurrentSpecs != null && arrayList2 != null && secQSCustomizerTileAdapter.mTileQueryHelper.mTileQueryFinished) {
                        Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs = " + secQSCustomizerTileAdapter.mCurrentSpecs + "mAllTiles size = " + secQSCustomizerTileAdapter.mAllTiles.size());
                        ArrayList arrayList3 = new ArrayList(secQSCustomizerTileAdapter.mAllTiles);
                        for (int i2 = 0; i2 < ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).size(); i2++) {
                            String str = (String) ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).get(i2);
                            int i3 = 0;
                            while (true) {
                                if (i3 >= arrayList3.size()) {
                                    break;
                                } else if (((SecTileQueryHelper.TileInfo) arrayList3.get(i3)).spec.equals(str)) {
                                } else {
                                    i3++;
                                }
                            }
                        }
                        Iterator it3 = arrayList3.iterator();
                        while (it3.hasNext()) {
                            ((SecTileQueryHelper.TileInfo) it3.next()).isActive = false;
                        }
                        ArrayList arrayList4 = new ArrayList();
                        String str2 = secQSCustomizerTileAdapter.mContext.getString(R.string.qs_edit_setting_available_area_tapped) + " ";
                        String m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(secQSCustomizerTileAdapter.mContext, R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder, new StringBuilder(", "));
                        for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                            CustomTileInfo customTileInfo = new CustomTileInfo();
                            SecTileQueryHelper.TileInfo tileInfo = (SecTileQueryHelper.TileInfo) arrayList3.get(i4);
                            customTileInfo.spec = tileInfo.spec;
                            customTileInfo.isActive = tileInfo.isActive;
                            customTileInfo.state = tileInfo.state;
                            Log.d("SecQSCustomizerTileAdapter", "addTile state = " + customTileInfo.state);
                            customTileInfo.isNewCustomTile = false;
                            StringBuilder m2 = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str2);
                            m2.append((Object) customTileInfo.state.label);
                            customTileInfo.customizeTileContentDes = ComponentActivity$1$$ExternalSyntheticOutline0.m(m2, customTileInfo.isNewCustomTile ? ", " : "", m);
                            arrayList4.add(customTileInfo);
                        }
                        secQSCustomizerTileAdapter.mAvailableTiles = arrayList4;
                    }
                    QSTileCustomizerController$$ExternalSyntheticLambda4 qSTileCustomizerController$$ExternalSyntheticLambda4 = secQSCustomizerTileAdapter.mOnTileChangedCallback;
                    if (qSTileCustomizerController$$ExternalSyntheticLambda4 != null && (arrayList = secQSCustomizerTileAdapter.mAvailableTiles) != null) {
                        QSTileCustomizerController qSTileCustomizerController = qSTileCustomizerController$$ExternalSyntheticLambda4.f$0;
                        qSTileCustomizerController.mAvailableTileLayout.addTiles(arrayList);
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            ((CustomTileInfo) it4.next()).longClickListener = qSTileCustomizerController.mLongClickListener;
                        }
                    }
                    if (secQSCustomizerTileAdapter.mAvailableTiles != null) {
                        secQSCustomizerTileAdapter.mIsLoadedAllTiles = true;
                    }
                }
                secTileQueryHelper2.mFinished = z3;
                break;
        }
    }

    public /* synthetic */ SecTileQueryHelper$$ExternalSyntheticLambda0(SecTileQueryHelper secTileQueryHelper, boolean z, ArrayList arrayList) {
        this.f$0 = secTileQueryHelper;
        this.f$2 = z;
        this.f$1 = arrayList;
    }
}
