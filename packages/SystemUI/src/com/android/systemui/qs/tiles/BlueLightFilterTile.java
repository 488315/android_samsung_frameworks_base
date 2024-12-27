package com.android.systemui.qs.tiles;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BlueLightFilterTile extends SQSTileImpl {
    public static final Intent EYECOMFORT_SETTINGS;
    public final AnonymousClass3 COLOR_ADJUSTMENT_FEATURE;
    public final AnonymousClass4 COLOR_LENS_FEATURE;
    public final AnonymousClass1 GRAYSCALE_FEATURE;
    public final AnonymousClass2 NEGATIVE_COLORS_FEATURE;
    public final ActivityStarter mActivityStarter;
    public final LinkedHashMap mFeatureEnabled;
    private SettingsHelper.OnChangedCallback mFeatureSettingsCallback;
    public final Uri[] mFeatureSettingsValueList;
    public final LinkedHashMap mFeatures;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public SystemUIDialog mLocationOnDialog;
    public final PanelInteractor mPanelInteractor;
    private SettingsHelper.OnChangedCallback mSettingsCallback;
    private final SettingsHelper mSettingsHelper;
    public String mToasMsg;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.tiles.BlueLightFilterTile$1, reason: invalid class name */
    public final class AnonymousClass1 implements Feature {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final String getName() {
            Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
            return BlueLightFilterTile.this.mContext.getString(R.string.greyscale_mode_title);
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final boolean isEnabled() {
            return BlueLightFilterTile.this.mSettingsHelper.isGrayScale();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.tiles.BlueLightFilterTile$2, reason: invalid class name */
    public final class AnonymousClass2 implements Feature {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final String getName() {
            Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
            return BlueLightFilterTile.this.mContext.getString(R.string.negative_color_title);
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final boolean isEnabled() {
            return BlueLightFilterTile.this.mSettingsHelper.isNegativeColor();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.tiles.BlueLightFilterTile$3, reason: invalid class name */
    public final class AnonymousClass3 implements Feature {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final String getName() {
            Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
            return BlueLightFilterTile.this.mContext.getString(R.string.colour_adjustment_title);
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final boolean isEnabled() {
            return BlueLightFilterTile.this.mSettingsHelper.isColorAdjustment();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.tiles.BlueLightFilterTile$4, reason: invalid class name */
    public final class AnonymousClass4 implements Feature {
        public AnonymousClass4() {
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final String getName() {
            Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
            return BlueLightFilterTile.this.mContext.getString(R.string.colour_lens_title);
        }

        @Override // com.android.systemui.qs.tiles.BlueLightFilterTile.Feature
        public final boolean isEnabled() {
            return BlueLightFilterTile.this.mSettingsHelper.isColorLens();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Feature {
        String getName();

        boolean isEnabled();
    }

    static {
        new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$BlueLightFilterSettingsActivity")).setAction("android.intent.action.MAIN");
        EYECOMFORT_SETTINGS = new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$EyeComfortSettingsActivity")).setAction("android.intent.action.MAIN");
    }

    public BlueLightFilterTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, SettingsHelper settingsHelper, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLocationOnDialog = null;
        this.mFeatureEnabled = new LinkedHashMap();
        this.mFeatures = new LinkedHashMap();
        this.mFeatureSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_GRAYSCALE), Settings.System.getUriFor(SettingsHelper.INDEX_NEGATIVE_COLORS), Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_ADJUSTMENT), Settings.Secure.getUriFor(SettingsHelper.INDEX_COLOR_LENS)};
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_BLUE_LIGHT_FILTER), Settings.System.getUriFor(SettingsHelper.INDEX_BLUELIGHT_FLITER_ADAPTIVE), Settings.System.getUriFor(SettingsHelper.INDEX_NIGHT_DIM)};
        this.GRAYSCALE_FEATURE = new AnonymousClass1();
        this.NEGATIVE_COLORS_FEATURE = new AnonymousClass2();
        this.COLOR_ADJUSTMENT_FEATURE = new AnonymousClass3();
        this.COLOR_LENS_FEATURE = new AnonymousClass4();
        this.mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.5
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                boolean equals = uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_BLUE_LIGHT_FILTER));
                BlueLightFilterTile blueLightFilterTile = BlueLightFilterTile.this;
                if (equals) {
                    int blueLightFilterMode = blueLightFilterTile.mSettingsHelper.getBlueLightFilterMode(SettingsHelper.INDEX_BLUE_LIGHT_FILTER);
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(blueLightFilterMode, "handleValueChanged( value:", " )", blueLightFilterTile.TAG);
                    blueLightFilterTile.refreshState(Boolean.valueOf(blueLightFilterMode == 1));
                } else if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_BLUELIGHT_FLITER_ADAPTIVE))) {
                    blueLightFilterTile.refreshState(null);
                } else if (QpRune.QUICK_TILE_BLUELIGHT_FILTER_NIGHT_DIM && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_NIGHT_DIM))) {
                    blueLightFilterTile.mSettingsHelper.isNightDim();
                    blueLightFilterTile.getClass();
                }
            }
        };
        this.mFeatureSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.6
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
                BlueLightFilterTile blueLightFilterTile = BlueLightFilterTile.this;
                if (blueLightFilterTile.DEBUG) {
                    Log.d(blueLightFilterTile.TAG, "Feature onChange( Uri:" + uri.toString() + ")");
                }
                blueLightFilterTile.mFeatureEnabled.put(uri.toString(), Boolean.valueOf(((Feature) blueLightFilterTile.mFeatures.get(uri.toString())).isEnabled()));
                blueLightFilterTile.refreshState(null);
            }
        };
        this.mActivityStarter = activityStarter;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        settingsHelper.registerCallback(this.mSettingsCallback, uriArr);
        addFeature();
        this.mPanelInteractor = panelInteractor;
    }

    public final void addFeature() {
        this.mFeatureEnabled.clear();
        this.mFeatures.clear();
        this.mSettingsHelper.registerCallback(this.mFeatureSettingsCallback, this.mFeatureSettingsValueList);
        Uri uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_GRAYSCALE);
        LinkedHashMap linkedHashMap = this.mFeatureEnabled;
        String uri = uriFor.toString();
        AnonymousClass1 anonymousClass1 = this.GRAYSCALE_FEATURE;
        linkedHashMap.put(uri, Boolean.valueOf(anonymousClass1.isEnabled()));
        this.mFeatures.put(uriFor.toString(), anonymousClass1);
        Uri uriFor2 = Settings.System.getUriFor(SettingsHelper.INDEX_NEGATIVE_COLORS);
        LinkedHashMap linkedHashMap2 = this.mFeatureEnabled;
        String uri2 = uriFor2.toString();
        AnonymousClass2 anonymousClass2 = this.NEGATIVE_COLORS_FEATURE;
        linkedHashMap2.put(uri2, Boolean.valueOf(anonymousClass2.isEnabled()));
        this.mFeatures.put(uriFor2.toString(), anonymousClass2);
        Uri uriFor3 = Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_ADJUSTMENT);
        LinkedHashMap linkedHashMap3 = this.mFeatureEnabled;
        String uri3 = uriFor3.toString();
        AnonymousClass3 anonymousClass3 = this.COLOR_ADJUSTMENT_FEATURE;
        linkedHashMap3.put(uri3, Boolean.valueOf(anonymousClass3.isEnabled()));
        this.mFeatures.put(uriFor3.toString(), anonymousClass3);
        Uri uriFor4 = Settings.Secure.getUriFor(SettingsHelper.INDEX_COLOR_LENS);
        LinkedHashMap linkedHashMap4 = this.mFeatureEnabled;
        String uri4 = uriFor4.toString();
        AnonymousClass4 anonymousClass4 = this.COLOR_LENS_FEATURE;
        linkedHashMap4.put(uri4, Boolean.valueOf(anonymousClass4.isEnabled()));
        this.mFeatures.put(uriFor4.toString(), anonymousClass4);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        super.destroy();
        this.mSettingsHelper.unregisterCallback(this.mSettingsCallback);
        this.mSettingsHelper.unregisterCallback(this.mFeatureSettingsCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return EYECOMFORT_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5005;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_eyecomfortshield_detail_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        if (expandable == null) {
            return;
        }
        final int i = 0;
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "location_mode", 0, -2) != 0;
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_BLUELIGHT_FLITER_TYPE, 0, -2);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled() && !z && intForUser == 2 && !((QSTile.BooleanState) this.mState).value) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BlueLightFilterTile.this.handleClick(expandable);
                    }
                });
                return;
            }
        }
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        boolean z2 = booleanState.value;
        boolean z3 = !z2;
        if (booleanState.state == 0) {
            if (TextUtils.isEmpty(this.mToasMsg)) {
                return;
            }
            Toast.makeText(this.mContext, this.mToasMsg, 0).show();
            return;
        }
        MetricsLogger.action(this.mContext, 5005, z2);
        Log.d(this.TAG, "handleClick " + z3);
        if (z || ((QpRune.QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE && this.mSettingsHelper.isAdaptiveBluelight()) || intForUser != 2)) {
            setMode(z3);
            return;
        }
        if (!z3) {
            setMode(z3);
            return;
        }
        Resources resources = this.mContext.getResources();
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
        this.mLocationOnDialog = systemUIDialog;
        systemUIDialog.setCancelable(true);
        this.mLocationOnDialog.setTitle(resources.getString(R.string.sec_blue_light_filter_dlg_turn_on_location_title));
        this.mLocationOnDialog.setMessage(resources.getString(R.string.sec_blue_light_filter_dlg_turn_on_location));
        this.mLocationOnDialog.setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                if (i3 == 0) {
                    BlueLightFilterTile blueLightFilterTile = BlueLightFilterTile.this;
                    Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
                    blueLightFilterTile.setMode(false);
                } else if (i3 == 1) {
                    BlueLightFilterTile.this.mSettingsHelper.setScheduledBluelight(0);
                    BlueLightFilterTile.this.mSettingsHelper.setAdaptiveBluelight(1);
                }
            }
        });
        this.mLocationOnDialog.setPositiveButton(R.string.sec_dlg_turn_on, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                if (i3 == 0) {
                    BlueLightFilterTile blueLightFilterTile = BlueLightFilterTile.this;
                    Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
                    blueLightFilterTile.setMode(true);
                } else if (i3 == 1) {
                    BlueLightFilterTile.this.mSettingsHelper.setScheduledBluelight(1);
                    BlueLightFilterTile.this.mSettingsHelper.setAdaptiveBluelight(0);
                }
            }
        });
        this.mLocationOnDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.9
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                int i2 = i;
                if (i2 == 0) {
                    BlueLightFilterTile blueLightFilterTile = BlueLightFilterTile.this;
                    Intent intent = BlueLightFilterTile.EYECOMFORT_SETTINGS;
                    blueLightFilterTile.setMode(false);
                } else if (i2 == 1) {
                    BlueLightFilterTile.this.mSettingsHelper.setScheduledBluelight(0);
                    BlueLightFilterTile.this.mSettingsHelper.setAdaptiveBluelight(1);
                }
            }
        });
        this.mLocationOnDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.BlueLightFilterTile.10
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BlueLightFilterTile.this.mLocationOnDialog = null;
            }
        });
        this.mLocationOnDialog.getWindow().setType(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        this.mLocationOnDialog.getWindow().getAttributes().privateFlags |= 16;
        this.mPanelInteractor.collapsePanels();
        this.mLocationOnDialog.show();
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleLongClick(Expandable expandable) {
        if (((QSTile.BooleanState) this.mState).state != 0) {
            super.handleLongClick(expandable);
        } else {
            if (TextUtils.isEmpty(this.mToasMsg)) {
                return;
            }
            Toast.makeText(this.mContext, this.mToasMsg, 0).show();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        if (((QSTile.BooleanState) this.mState).state != 0 || TextUtils.isEmpty(this.mToasMsg)) {
            return;
        }
        Toast.makeText(this.mContext, this.mToasMsg, 0).show();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean booleanValue = obj instanceof Boolean ? ((Boolean) obj).booleanValue() : this.mSettingsHelper.getBlueLightFilterMode(SettingsHelper.INDEX_BLUE_LIGHT_FILTER) == 1;
        booleanState.value = booleanValue;
        Iterator it = this.mFeatureEnabled.keySet().iterator();
        while (true) {
            if (it.hasNext()) {
                String str = (String) it.next();
                if (((Boolean) this.mFeatureEnabled.get(str)).booleanValue()) {
                    this.mToasMsg = this.mContext.getString(R.string.blue_light_disable_reason, ((Feature) this.mFeatures.get(str)).getName(), this.mContext.getString(R.string.quick_settings_eyecomfortshield_detail_title));
                    booleanState.state = 0;
                    break;
                }
            } else {
                this.mToasMsg = "";
                booleanState.state = booleanValue ? 2 : 1;
            }
        }
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_eyecomfortshield_detail_title);
        if (QpRune.QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE && this.mSettingsHelper.isAdaptiveBluelight()) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_eye_comfort_shield_adaptive);
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_eye_comfort_shield);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        this.mSettingsHelper.unregisterCallback(this.mFeatureSettingsCallback);
        addFeature();
        sendIntent(41);
        refreshState(null);
        handleRefreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void sendIntent(int i) {
        if (this.DEBUG) {
            Log.d(this.TAG, LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "sendIntent( key:", " ) BLUE_LIGHT_SETTING"));
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.samsung.android.bluelightfilter", "com.samsung.android.bluelightfilter.BlueLightFilterService"));
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", i);
        intent.setPackage("com.samsung.android.bluelightfilter.BlueLightFilterService");
        this.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
    }

    public final void setMode(boolean z) {
        Log.d(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setMode : ", z));
        this.mSettingsHelper.setBlueLightFilterMode(SettingsHelper.INDEX_BLUE_LIGHT_FILTER, z ? 1 : 0);
        sendIntent(z ? 21 : 22);
    }
}
