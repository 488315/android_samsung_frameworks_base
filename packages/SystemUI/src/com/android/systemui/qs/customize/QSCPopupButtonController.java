package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.qs.customize.QSBlurPopUpMenu;
import com.android.systemui.qs.customize.QSCPopupButtonController;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import kotlin.collections.AbstractCollection;
import kotlin.collections.builders.ListBuilder;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSCPopupButtonController {
    public boolean barChanged;
    public final ArrayList children = new ArrayList(((AbstractCollection) POPUPTYPE.$ENTRIES).size());
    public LinearLayout container;
    public final Context context;
    public final SecQSSettingEditResources editResources;
    public QSBlurPopUpMenu qsBlurPopUpMenu;
    public final TunerService tunerService;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class POPUPTYPE {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ POPUPTYPE[] $VALUES;
        public static final POPUPTYPE HIDE_SMART_VIEW_LARGE_TILE;
        public static final POPUPTYPE MULTISIM;
        private final int first;
        private final int second;
        private final int title;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class BRIGHTNESS extends POPUPTYPE {
            public BRIGHTNESS(String str, int i) {
                super(str, i, R.string.sec_brightness_control, R.string.qs_panel_detail_popup_menu_always_text, R.string.qs_panel_detail_popup_menu_when_expanded_text, null);
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final int getSelectedIdx(TunerService tunerService) {
                return tunerService.getValue(1, "brightness_on_top") != 0 ? 0 : 1;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final boolean isAvailable(TunerService tunerService) {
                return true;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final void updateValue(boolean z, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources) {
                tunerService.setValue(z ? 1 : 0, "brightness_on_top");
                secQSSettingEditResources.updateSALog(SystemUIAnalytics.STATUS_SHOW_BRIGHTNESS_ON_TOP, z);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class DEVICEMEDIA extends POPUPTYPE {
            public DEVICEMEDIA(String str, int i) {
                super(str, i, R.string.sec_devices_and_media_control, R.string.qs_panel_detail_popup_menu_when_collapsed_text, R.string.qs_panel_detail_popup_menu_dont_show_text, null);
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final int getSelectedIdx(TunerService tunerService) {
                return (tunerService.getValue(0, "qspanel_media_quickcontrol_bar_available") != 0 ? 1 : 0) ^ 1;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final boolean isAvailable(TunerService tunerService) {
                return true;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final void updateValue(boolean z, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources) {
                tunerService.setValue(z ? 1 : 0, "qspanel_media_quickcontrol_bar_available");
                secQSSettingEditResources.updateSALog(SystemUIAnalytics.STATUS_SHOW_DEVICES_AND_MEDIA, z);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class HIDE_SMART_VIEW_LARGE_TILE extends POPUPTYPE {
            public HIDE_SMART_VIEW_LARGE_TILE(String str, int i) {
                super(str, i, R.string.sec_smart_view_large_tile_info, R.string.qs_panel_detail_popup_menu_when_expanded_text, R.string.qs_panel_detail_popup_menu_dont_show_text, null);
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final int getSelectedIdx(TunerService tunerService) {
                return tunerService.getValue(0, "hide_smart_view_large_tile_on_panel");
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final boolean isAvailable(TunerService tunerService) {
                return QpRune.QUICK_TILE_HIDE_FROM_BAR;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final void updateValue(boolean z, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources) {
                tunerService.setValue(!z ? 1 : 0, "hide_smart_view_large_tile_on_panel");
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class MULTISIM extends POPUPTYPE {
            public MULTISIM(String str, int i) {
                super(str, i, R.string.sec_multi_sim_info_control, R.string.qs_panel_detail_popup_menu_when_expanded_text, R.string.qs_panel_detail_popup_menu_dont_show_text, null);
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final int getSelectedIdx(TunerService tunerService) {
                return tunerService.getValue(1, "multi_sim_bar_show_on_qspanel") != 0 ? 0 : 1;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final boolean isAvailable(TunerService tunerService) {
                return QpRune.QUICK_BAR_MULTISIM && Rune.SYSUI_MULTI_SIM && ((MultiSIMController) Dependency.sDependency.getDependencyInner(MultiSIMController.class)).isMultiSimAvailable() && tunerService != null && tunerService.getValue(0, "multi_sim_bar_hide_by_knox_restrictions") == 0;
            }

            @Override // com.android.systemui.qs.customize.QSCPopupButtonController.POPUPTYPE
            public final void updateValue(boolean z, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources) {
                tunerService.setValue(z ? 1 : 0, "multi_sim_bar_show_on_qspanel");
                secQSSettingEditResources.updateSALog(SystemUIAnalytics.STATUS_SHOW_MULTISIM_INFO, z);
            }
        }

        static {
            BRIGHTNESS brightness = new BRIGHTNESS("BRIGHTNESS", 0);
            DEVICEMEDIA devicemedia = new DEVICEMEDIA("DEVICEMEDIA", 1);
            MULTISIM multisim = new MULTISIM("MULTISIM", 2);
            MULTISIM = multisim;
            HIDE_SMART_VIEW_LARGE_TILE hide_smart_view_large_tile = new HIDE_SMART_VIEW_LARGE_TILE("HIDE_SMART_VIEW_LARGE_TILE", 3);
            HIDE_SMART_VIEW_LARGE_TILE = hide_smart_view_large_tile;
            POPUPTYPE[] popuptypeArr = {brightness, devicemedia, multisim, hide_smart_view_large_tile};
            $VALUES = popuptypeArr;
            $ENTRIES = EnumEntriesKt.enumEntries(popuptypeArr);
        }

        public /* synthetic */ POPUPTYPE(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, i2, i3, i4);
        }

        public static POPUPTYPE valueOf(String str) {
            return (POPUPTYPE) Enum.valueOf(POPUPTYPE.class, str);
        }

        public static POPUPTYPE[] values() {
            return (POPUPTYPE[]) $VALUES.clone();
        }

        public final int getFirst() {
            return this.first;
        }

        public final int getSecond() {
            return this.second;
        }

        public abstract int getSelectedIdx(TunerService tunerService);

        public final int getTitle() {
            return this.title;
        }

        public abstract boolean isAvailable(TunerService tunerService);

        public abstract void updateValue(boolean z, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources);

        private POPUPTYPE(String str, int i, int i2, int i3, int i4) {
            this.title = i2;
            this.first = i3;
            this.second = i4;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[POPUPTYPE.values().length];
            try {
                iArr[POPUPTYPE.MULTISIM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[POPUPTYPE.HIDE_SMART_VIEW_LARGE_TILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public QSCPopupButtonController(Context context, TunerService tunerService, SecQSSettingEditResources secQSSettingEditResources) {
        this.context = context;
        this.tunerService = tunerService;
        this.editResources = secQSSettingEditResources;
    }

    public final boolean isAvailableMenu(int i) {
        POPUPTYPE popuptype = (POPUPTYPE) POPUPTYPE.$ENTRIES.get(i);
        return popuptype == POPUPTYPE.MULTISIM ? popuptype.isAvailable(this.tunerService) : popuptype.isAvailable(null);
    }

    public final void setPopupText(final View view, final POPUPTYPE popuptype) {
        TextView textView = (TextView) view.requireViewById(R.id.button_title);
        if (textView != null) {
            textView.setText(view.getContext().getString(popuptype.getTitle()));
        }
        TextView textView2 = (TextView) view.requireViewById(R.id.button_summary);
        TunerService tunerService = this.tunerService;
        if (textView2 != null) {
            Context context = view.getContext();
            int ordinal = popuptype.ordinal();
            EnumEntries enumEntries = POPUPTYPE.$ENTRIES;
            textView2.setText(context.getString(((POPUPTYPE) enumEntries.get(ordinal)).getSelectedIdx(tunerService) == 0 ? ((POPUPTYPE) enumEntries.get(popuptype.ordinal())).getFirst() : ((POPUPTYPE) enumEntries.get(popuptype.ordinal())).getSecond()));
        }
        ListBuilder listBuilder = new ListBuilder();
        EnumEntries enumEntries2 = POPUPTYPE.$ENTRIES;
        POPUPTYPE popuptype2 = (POPUPTYPE) enumEntries2.get(popuptype.ordinal());
        boolean z = ((POPUPTYPE) enumEntries2.get(popuptype.ordinal())).getSelectedIdx(tunerService) == 0;
        listBuilder.add(new QSBlurPopUpMenu.PopUpContent(this.context.getString(popuptype2.getFirst()), z));
        listBuilder.add(new QSBlurPopUpMenu.PopUpContent(this.context.getString(popuptype2.getSecond()), !z));
        final QSBlurPopUpMenu.PopupListAdapter popupListAdapter = new QSBlurPopUpMenu.PopupListAdapter(this.context, listBuilder.build());
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.QSCPopupButtonController$makePopUpMenu$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                final QSBlurPopUpMenu qSBlurPopUpMenu = new QSBlurPopUpMenu(QSCPopupButtonController.this.context);
                final View view3 = view;
                final QSCPopupButtonController qSCPopupButtonController = QSCPopupButtonController.this;
                final QSBlurPopUpMenu.PopupListAdapter popupListAdapter2 = popupListAdapter;
                final QSCPopupButtonController.POPUPTYPE popuptype3 = popuptype;
                qSBlurPopUpMenu.setWidth(-2);
                qSBlurPopUpMenu.setAnchorView(view3);
                qSCPopupButtonController.qsBlurPopUpMenu = qSBlurPopUpMenu;
                qSBlurPopUpMenu.setDropDownGravity(8388611);
                qSBlurPopUpMenu.setAdapter(popupListAdapter2);
                qSBlurPopUpMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.qs.customize.QSCPopupButtonController$makePopUpMenu$1$1$1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(AdapterView adapterView, View view4, int i, long j) {
                        int count = QSBlurPopUpMenu.PopupListAdapter.this.getCount();
                        for (int i2 = 0; i2 < count; i2++) {
                            Object item = QSBlurPopUpMenu.PopupListAdapter.this.getItem(i2);
                            Intrinsics.checkNotNull(item);
                            ((QSBlurPopUpMenu.PopUpContent) item).checked = false;
                        }
                        Object item2 = QSBlurPopUpMenu.PopupListAdapter.this.getItem(i);
                        Intrinsics.checkNotNull(item2);
                        ((QSBlurPopUpMenu.PopUpContent) item2).checked = true;
                        View view5 = view3;
                        QSCPopupButtonController.POPUPTYPE popuptype4 = popuptype3;
                        TextView textView3 = (TextView) view5.requireViewById(R.id.button_summary);
                        if (textView3 != null) {
                            textView3.setText(view5.getContext().getString(i == 0 ? QSCPopupButtonController.POPUPTYPE.values()[popuptype4.ordinal()].getFirst() : QSCPopupButtonController.POPUPTYPE.values()[popuptype4.ordinal()].getSecond()));
                        }
                        QSCPopupButtonController qSCPopupButtonController2 = qSCPopupButtonController;
                        int ordinal2 = popuptype3.ordinal();
                        boolean z2 = i == 0;
                        qSCPopupButtonController2.getClass();
                        QSCPopupButtonController.POPUPTYPE popuptype5 = (QSCPopupButtonController.POPUPTYPE) QSCPopupButtonController.POPUPTYPE.$ENTRIES.get(ordinal2);
                        if (popuptype5 == QSCPopupButtonController.POPUPTYPE.MULTISIM || popuptype5 == QSCPopupButtonController.POPUPTYPE.HIDE_SMART_VIEW_LARGE_TILE) {
                            qSCPopupButtonController2.barChanged = true;
                        }
                        popuptype5.updateValue(z2, qSCPopupButtonController2.tunerService, qSCPopupButtonController2.editResources);
                        qSBlurPopUpMenu.dismiss();
                    }
                });
                qSBlurPopUpMenu.show();
            }
        });
    }
}
