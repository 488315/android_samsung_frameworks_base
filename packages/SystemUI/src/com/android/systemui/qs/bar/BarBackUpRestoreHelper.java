package com.android.systemui.qs.bar;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BarBackUpRestoreHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BarOrderInteractor barOrderInteractor;
    public final Context context;
    public final Lazy qsBackupRestoreManager$delegate;
    private final SettingsHelper settingsHelper;
    public final Lazy tunerService$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public BarBackUpRestoreHelper(Context context, SettingsHelper settingsHelper, BarOrderInteractor barOrderInteractor) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.barOrderInteractor = barOrderInteractor;
        final int i = 0;
        this.qsBackupRestoreManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.BarBackUpRestoreHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = BarBackUpRestoreHelper.$r8$clinit;
                        return (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
                    default:
                        int i3 = BarBackUpRestoreHelper.$r8$clinit;
                        return (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
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
                    default:
                        int i3 = BarBackUpRestoreHelper.$r8$clinit;
                        return (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
                }
            }
        });
    }

    public static final String access$getBackupData(BarBackUpRestoreHelper barBackUpRestoreHelper, boolean z) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5 = null;
        if (z) {
            str = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "brightness_on_top") != 0);
        } else {
            str = null;
        }
        if (z) {
            str2 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "qspanel_media_quickcontrol_bar_available") != 0);
        } else {
            str2 = null;
        }
        if (z) {
            str3 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(1, "multi_sim_bar_show_on_qspanel") != 0);
        } else {
            str3 = null;
        }
        if (z) {
            str4 = String.valueOf(barBackUpRestoreHelper.getTunerService().getValue(0, "hide_smart_view_large_tile_on_panel") == 0);
        } else {
            str4 = null;
        }
        String valueOf = z ? String.valueOf(barBackUpRestoreHelper.settingsHelper.isPanelSplit()) : null;
        String valueOf2 = z ? String.valueOf(barBackUpRestoreHelper.settingsHelper.isPanelSplitReversed()) : null;
        String value = z ? barBackUpRestoreHelper.getTunerService().getValue("sysui_quick_bar_order", "") : null;
        if (z) {
            str5 = barBackUpRestoreHelper.getTunerService().getValue("sysui_quick_bar_collapsed_row", "2");
        } else {
            barBackUpRestoreHelper.getClass();
        }
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TAG::qplayout_brightnessbar::", str, "::TAG::qplayout_mediadevices::", str2, "::TAG::qplayout_multisim::");
        MoveResult$$ExternalSyntheticOutline0.m(m, str3, "::TAG::hide_smart_view_large_tile_on_panel::", str4, "::TAG::split_quick_panel::");
        MoveResult$$ExternalSyntheticOutline0.m(m, valueOf, "::TAG::split_quick_panel_reversed::", valueOf2, "::TAG::sysui_quick_bar_collapsed_row::");
        String m2 = MutablePreferences$$ExternalSyntheticOutline0.m(m, str5, "::TAG::sysui_quick_bar_order::", value);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" getBackupData: ", m2, "BarBackUpRestoreManager");
        return m2;
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
        List split$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, 0, 6);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" setRestoreData: ", str, "BarBackUpRestoreManager");
        Iterator it = split$default.iterator();
        while (it.hasNext()) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setRestoreData: string: ", (String) it.next(), "BarBackUpRestoreManager");
        }
        if (split$default.size() <= 1) {
            return;
        }
        String str2 = (String) split$default.get(0);
        switch (str2.hashCode()) {
            case -2041819251:
                if (str2.equals(SettingsHelper.INDEX_SPLIT_QUICK_PANEL)) {
                    String str3 = (String) split$default.get(1);
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
                    String str4 = (String) split$default.get(1);
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
                    String str5 = (String) split$default.get(1);
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
                    String str6 = (String) split$default.get(1);
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
                    String str7 = (String) split$default.get(1);
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
                    String str8 = (String) split$default.get(1);
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
                    String str10 = (String) split$default.get(1);
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
                    String str11 = (String) split$default.get(1);
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
        Log.w("BarBackUpRestoreManager", "setRestoreData: " + split$default.get(0) + " is unknown");
    }

    public final TunerService getTunerService() {
        return (TunerService) this.tunerService$delegate.getValue();
    }
}
