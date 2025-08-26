package com.android.systemui.qs.bar;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.PanelAgent;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class BarBackUpRestoreHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BarOrderInteractor barOrderInteractor;
    public final Context context;
    public final Executor mainExecutor;
    public final Lazy panelViewControllerLazy;
    public final kotlin.Lazy qsBackupRestoreManager$delegate;
    private final SettingsHelper settingsHelper;
    public final kotlin.Lazy shadeRepository$delegate;
    public final kotlin.Lazy tunerService$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BarBackUpRestoreHelper(Context context, SettingsHelper settingsHelper, BarOrderInteractor barOrderInteractor, Lazy lazy, Executor executor) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.barOrderInteractor = barOrderInteractor;
        this.panelViewControllerLazy = lazy;
        this.mainExecutor = executor;
        final int i = 0;
        this.qsBackupRestoreManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = BarBackUpRestoreHelper.$r8$clinit;
                        return (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                    case 1:
                        int i3 = BarBackUpRestoreHelper.$r8$clinit;
                        return (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
                    default:
                        int i4 = BarBackUpRestoreHelper.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i2 = 1;
        this.tunerService$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = BarBackUpRestoreHelper.$r8$clinit;
                        return (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                    case 1:
                        int i3 = BarBackUpRestoreHelper.$r8$clinit;
                        return (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
                    default:
                        int i4 = BarBackUpRestoreHelper.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i3 = 2;
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = BarBackUpRestoreHelper.$r8$clinit;
                        return (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                    case 1:
                        int i32 = BarBackUpRestoreHelper.$r8$clinit;
                        return (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
                    default:
                        int i4 = BarBackUpRestoreHelper.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
    }

    public static final String access$getBackupData(BarBackUpRestoreHelper barBackUpRestoreHelper, boolean z) {
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        String strValueOf4;
        String value = null;
        if (z) {
            strValueOf = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "brightness_on_top") != 0);
        } else {
            strValueOf = null;
        }
        if (z) {
            strValueOf2 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "qspanel_media_quickcontrol_bar_available") != 0);
        } else {
            strValueOf2 = null;
        }
        if (z) {
            strValueOf3 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "multi_sim_bar_show_on_qspanel") != 0);
        } else {
            strValueOf3 = null;
        }
        if (z) {
            strValueOf4 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(0, "hide_smart_view_large_tile_on_panel") == 0);
        } else {
            strValueOf4 = null;
        }
        String strValueOf5 = z ? String.valueOf(barBackUpRestoreHelper.settingsHelper.isPanelSplit()) : null;
        String strValueOf6 = z ? String.valueOf(barBackUpRestoreHelper.settingsHelper.isPanelSplitReversed()) : null;
        String value2 = z ? barBackUpRestoreHelper.getTunerService().getValue("sysui_quick_bar_order", "") : null;
        if (z) {
            value = barBackUpRestoreHelper.getTunerService().getValue("sysui_quick_bar_collapsed_row", "2");
        } else {
            barBackUpRestoreHelper.getClass();
        }
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TAG::qplayout_brightnessbar::", strValueOf, "::TAG::qplayout_mediadevices::", strValueOf2, "::TAG::qplayout_multisim::");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strValueOf3, "::TAG::hide_smart_view_large_tile_on_panel::", strValueOf4, "::TAG::split_quick_panel::");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strValueOf5, "::TAG::split_quick_panel_reversed::", strValueOf6, "::TAG::sysui_quick_bar_collapsed_row::");
        String strM = MutablePreferences$$ExternalSyntheticOutline0.m(sbM, value, "::TAG::sysui_quick_bar_order::", value2);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" getBackupData: ", strM, "BarBackUpRestoreManager");
        return strM;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public static final void access$setRestoreData(BarBackUpRestoreHelper barBackUpRestoreHelper, String str) {
        barBackUpRestoreHelper.getClass();
        List listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, 0, 6);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" setRestoreData: ", str, "BarBackUpRestoreManager");
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setRestoreData: string: ", (String) it.next(), "BarBackUpRestoreManager");
        }
        if (listSplit$default.size() <= 1) {
            return;
        }
        final PanelAgent panelAgent = ((NotificationPanelViewController) barBackUpRestoreHelper.panelViewControllerLazy.get()).mPanelAgent;
        if (panelAgent != null) {
            final boolean asBoolean = panelAgent.keyguardShowing.getAsBoolean();
            final boolean asBoolean2 = panelAgent.panelExpandedSupplier.getAsBoolean();
            final boolean asBoolean3 = panelAgent.trackingSupplier.getAsBoolean();
            final boolean zBooleanValue = ((Boolean) ((ShadeRepositoryImpl) ((ShadeRepository) barBackUpRestoreHelper.shadeRepository$delegate.getValue())).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue();
            CarrierTextManager$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("setRestoreData: isKeyguardShowing:", ", qsExpanded:", ", isPanelExpanded:", asBoolean, zBooleanValue), asBoolean2, ", isTracking:", asBoolean3, "BarBackUpRestoreManager");
            barBackUpRestoreHelper.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$setRestoreData$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (asBoolean) {
                        if (zBooleanValue) {
                            panelAgent.closeQsRunnable.run();
                            Log.d("BarBackUpRestoreManager", "setRestoreData: closeQs()");
                            return;
                        }
                        return;
                    }
                    if (asBoolean2) {
                        if (asBoolean3) {
                            panelAgent.trackingStoppedConsumer.accept(Boolean.FALSE);
                        }
                        panelAgent.instantCollapseRunnable.run();
                        Log.d("BarBackUpRestoreManager", "setRestoreData: instantCollapse()");
                    }
                }
            });
        }
        String str2 = (String) listSplit$default.get(0);
        switch (str2.hashCode()) {
            case -2041819251:
                if (str2.equals(SettingsHelper.INDEX_SPLIT_QUICK_PANEL)) {
                    String str3 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str3, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored split_quick_panel is null");
                        return;
                    } else {
                        barBackUpRestoreHelper.settingsHelper.setPanelSplit(Intrinsics.areEqual(str3, "true"));
                        return;
                    }
                }
                break;
            case -1719643190:
                if (str2.equals("hide_smart_view_large_tile_on_panel")) {
                    String str4 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str4, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored hide_smart_view_large_tile_on_panel is null");
                        return;
                    } else if (QpRune.QUICK_TILE_HIDE_FROM_BAR) {
                        barBackUpRestoreHelper.getTunerService().setValue(!Intrinsics.areEqual(str4, "true") ? 1 : 0, "hide_smart_view_large_tile_on_panel");
                        return;
                    } else {
                        MotionLayout$$ExternalSyntheticOutline0.m("restored hide_smart_view_large_tile_on_panel, device has QpRune.QUICK_HIDE_TILE_FROM_BAR is false. value:", str4, "BarBackUpRestoreManager");
                        return;
                    }
                }
                break;
            case -997857676:
                if (str2.equals("qplayout_multisim")) {
                    String str5 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str5, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored qplayout_multisim is null");
                        return;
                    } else {
                        barBackUpRestoreHelper.getTunerService().setValue(Intrinsics.areEqual(str5, "true") ? 1 : 0, "multi_sim_bar_show_on_qspanel");
                        return;
                    }
                }
                break;
            case -552835476:
                if (str2.equals("qplayout_brightnessbar")) {
                    String str6 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str6, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored qplayout_brightnessbar is null");
                        return;
                    } else {
                        barBackUpRestoreHelper.getTunerService().setValue(Intrinsics.areEqual(str6, "true") ? 1 : 0, "brightness_on_top");
                        return;
                    }
                }
                break;
            case -80668108:
                if (str2.equals(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED)) {
                    String str7 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str7, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored split_quick_panel_reversed is null");
                        return;
                    } else {
                        barBackUpRestoreHelper.settingsHelper.setPanelSplitReversed(Intrinsics.areEqual(str7, "true"));
                        return;
                    }
                }
                break;
            case -78389521:
                if (str2.equals("qplayout_mediadevices")) {
                    String str8 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str8, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored qplayout_mediadevices is null");
                        return;
                    }
                    String str9 = Intrinsics.areEqual(str8, "false") ? "0" : Intrinsics.areEqual(str8, "true") ? "1" : str8;
                    switch (str9.hashCode()) {
                        case 48:
                            if (str9.equals("0")) {
                                barBackUpRestoreHelper.getTunerService().setValue(0, "qspanel_media_quickcontrol_bar_available");
                                return;
                            }
                            break;
                        case 49:
                            if (str9.equals("1")) {
                                barBackUpRestoreHelper.getTunerService().setValue(1, "qspanel_media_quickcontrol_bar_available");
                                return;
                            }
                            break;
                        case 50:
                            if (str9.equals("2")) {
                                barBackUpRestoreHelper.getTunerService().setValue(1, "qspanel_media_quickcontrol_bar_available");
                                return;
                            }
                            break;
                    }
                    Log.w("BarBackUpRestoreManager", "updateMediaDevices: " + str8 + " is unknown");
                    return;
                }
                break;
            case 321940018:
                if (str2.equals("sysui_quick_bar_order")) {
                    String str10 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str10, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored sysui_quick_bar_order is null");
                        return;
                    }
                    barBackUpRestoreHelper.getTunerService().setValue("sysui_quick_bar_order", str10);
                    Context context = barBackUpRestoreHelper.context;
                    int i = BarOrderInteractor.$r8$clinit;
                    barBackUpRestoreHelper.barOrderInteractor.initValuesAndApply(context, true);
                    return;
                }
                break;
            case 1306457814:
                if (str2.equals("sysui_quick_bar_collapsed_row")) {
                    String str11 = (String) listSplit$default.get(1);
                    if (Intrinsics.areEqual(str11, "null")) {
                        Log.w("BarBackUpRestoreManager", "restored sysui_quick_bar_collapsed_row is null");
                        return;
                    } else {
                        barBackUpRestoreHelper.getTunerService().setValue("sysui_quick_bar_collapsed_row", str11);
                        return;
                    }
                }
                break;
        }
        Log.w("BarBackUpRestoreManager", "setRestoreData: " + listSplit$default.get(0) + " is unknown");
    }

    public final TunerService getTunerService() {
        return (TunerService) this.tunerService$delegate.getValue();
    }
}
