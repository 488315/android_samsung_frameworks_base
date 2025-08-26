package com.android.systemui.qs.tiles.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.DNDDetailItems;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.settings.GlobalSettings;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class DndDetailAdapter implements DetailAdapter, View.OnAttachStateChangeListener, DNDDetailItems.Callback {
    public final Context mContext;
    public DNDDetailItems mDNDActivationItems;
    public final DndTile mDndTile;
    public final GlobalSettings mGlobalSettings;
    public final SQSTileImpl.SHandler mHandler;
    public ViewGroup mMenuOptions;
    public SecQSDetailController mSecQSDetailController;
    public final ContentObserver mSettingsObserver;
    public TextView mSummary;
    public final ArrayList mItemsList = new ArrayList();
    public final String[] mDndMenuOptions = new String[6];

    public DndDetailAdapter(DndTile dndTile, Context context, QSTile.BooleanState booleanState, ZenModeController zenModeController, GlobalSettings globalSettings, ContentObserver contentObserver, SQSTileImpl.SHandler sHandler) {
        this.mDndTile = dndTile;
        this.mContext = context;
        this.mGlobalSettings = globalSettings;
        this.mSettingsObserver = contentObserver;
        this.mHandler = sHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:155:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0199 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x019e  */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) throws Resources.NotFoundException, NumberFormatException {
        boolean z;
        String string;
        int i;
        int iIndexOf;
        int i2;
        int i3;
        String strSubstring;
        int i4;
        int i5;
        TextView textView;
        int i6;
        String string2;
        ArrayMap arrayMap;
        Uri uri;
        this.mSummary = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false).findViewById(R.id.message);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_qs_detail_dnd, viewGroup, false);
        this.mSummary = (TextView) viewInflate.findViewById(R.id.dnd_summary);
        this.mGlobalSettings.unregisterContentObserverSync(this.mSettingsObserver);
        DndTile dndTile = this.mDndTile;
        ZenModeConfig zenModeConfig = ((ZenModeControllerImpl) dndTile.mController).mConfig;
        boolean z2 = (zenModeConfig == null || zenModeConfig.manualRule == null) ? false : true;
        boolean z3 = z2 && zenModeConfig.manualRule.conditionId == null;
        boolean z4 = z2 && (uri = zenModeConfig.manualRule.conditionId) != null && ZenModeConfig.isValidCountdownConditionId(uri);
        if (zenModeConfig == null || zenModeConfig.manualRule != null || (arrayMap = zenModeConfig.automaticRules) == null || arrayMap.isEmpty()) {
            z = false;
        } else {
            Iterator it = zenModeConfig.automaticRules.values().iterator();
            while (it.hasNext()) {
                if (((ZenModeConfig.ZenRule) it.next()).isAutomaticActive()) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateZenModeConfigState,isTurnOnAsManualRule: ", ",isDurationForever: ", ",isDurationTime: ", z2, z3);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z4, ",isAutomaticRule:", z, ",mIsSettingsUpdated:");
        sbM.append(dndTile.mIsSettingsUpdated);
        Log.d(dndTile.TAG, sbM.toString());
        StringBuilder sb = new StringBuilder();
        string = "";
        if (z3) {
            String str = zenModeConfig.manualRule.enabler;
            if (str != null) {
                sb.append(dndTile.getApplicationNameFromPackage$1(str));
                string = dndTile.mContext.getString(R.string.sec_zen_mode_footer_by_app_name, sb);
                i = 0;
            } else {
                i = dndTile.mIsSettingsUpdated ? 5 : 4;
            }
        } else if (z4) {
            long jTryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
            boolean zIsToday = ZenModeConfig.isToday(jTryParseCountdownConditionId);
            Context context2 = dndTile.mContext;
            CharSequence formattedTime = ZenModeConfig.getFormattedTime(context2, jTryParseCountdownConditionId, zIsToday, context2.getUserId());
            String string3 = zIsToday ? dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_today, formattedTime) : dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_tomorrow, formattedTime);
            if (dndTile.mIsSettingsUpdated) {
                string = string3;
            } else {
                int i7 = dndTile.mLastDndDurationSelected;
                if (i7 == -1) {
                    String string4 = (String) formattedTime;
                    try {
                        Context context3 = dndTile.mContext;
                        boolean zIs24HourFormat = DateFormat.is24HourFormat(context3, context3.getUserId());
                        boolean zContains = string4.contains("pm");
                        Context context4 = dndTile.mContext;
                        boolean zIs24HourFormat2 = DateFormat.is24HourFormat(context4, context4.getUserId());
                        int[] iArr = null;
                        if (!TextUtils.isEmpty(string4) && (iIndexOf = string4.indexOf(58)) >= 1) {
                            i2 = 1;
                            if (iIndexOf < string4.length() - 1) {
                                try {
                                    String strSubstring2 = string4.substring((zIs24HourFormat2 || iIndexOf + (-2) >= 0) ? iIndexOf - 2 : iIndexOf - 1, iIndexOf);
                                    if (TextUtils.isEmpty(strSubstring2)) {
                                        i3 = -1;
                                        strSubstring = string4.substring(iIndexOf + 1, iIndexOf + 3);
                                        if (TextUtils.isEmpty(strSubstring)) {
                                            try {
                                                i4 = Integer.parseInt(strSubstring);
                                            } catch (NumberFormatException unused) {
                                            }
                                            if (i3 >= 0) {
                                                iArr = new int[]{i3, i4};
                                            }
                                        } else {
                                            i4 = -1;
                                            if (i3 >= 0 && i3 < 24 && i4 >= 0 && i4 < 60) {
                                                iArr = new int[]{i3, i4};
                                            }
                                        }
                                    } else {
                                        try {
                                            i3 = Integer.parseInt(strSubstring2);
                                        } catch (NumberFormatException unused2) {
                                        }
                                        strSubstring = string4.substring(iIndexOf + 1, iIndexOf + 3);
                                        if (TextUtils.isEmpty(strSubstring)) {
                                        }
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    e.printStackTrace();
                                    string = dndTile.mDndMenuSummary;
                                    dndTile.mDndMenuSummary = string;
                                    dndTile.mDndMenuSelectedItem = i;
                                    textView = this.mSummary;
                                    if (textView != null) {
                                    }
                                    ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(R.id.dnd_menu_layout);
                                    this.mMenuOptions = viewGroup2;
                                    int i8 = DNDDetailItems.$r8$clinit;
                                    DNDDetailItems dNDDetailItems = (DNDDetailItems) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_dnd_items, viewGroup2, false);
                                    this.mDNDActivationItems = dNDDetailItems;
                                    this.mMenuOptions.addView(dNDDetailItems);
                                    i6 = 0;
                                    while (i6 < 6) {
                                    }
                                    updateDndActivationItems(false);
                                    DNDDetailItems dNDDetailItems2 = this.mDNDActivationItems;
                                    dNDDetailItems2.getClass();
                                    dNDDetailItems2.mTag = "DNDDetailItems.Do not disturb";
                                    DNDDetailItems dNDDetailItems3 = this.mDNDActivationItems;
                                    dNDDetailItems3.mHandler.removeMessages(2);
                                    dNDDetailItems3.mHandler.obtainMessage(2, this).sendToTarget();
                                    return viewInflate;
                                }
                            }
                        } else {
                            i2 = 1;
                        }
                        Calendar calendar = Calendar.getInstance();
                        int i9 = (calendar.get(11) * 60) + calendar.get(12);
                        if (iArr != null) {
                            int i10 = iArr[0];
                            int i11 = (i10 * 60) + iArr[i2];
                            if (!zIsToday) {
                                i11 += 1440;
                            }
                            if (i11 < i9 && !zIs24HourFormat) {
                                i11 += DeviceState.CAPTURED_BLUR_THRESHOLD_WIDTH;
                            }
                            int i12 = (i11 - i9) / 60;
                            String str2 = ((i10 >= 10 || !zIs24HourFormat) ? "" : "0") + iArr[0] + ":";
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            int i13 = iArr[i2];
                            sb2.append(i13 < 10 ? "0" + iArr[i2] : Integer.valueOf(i13));
                            String string5 = sb2.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(string5);
                            sb3.append(zIs24HourFormat ? "" : zContains ? " pm" : " am");
                            string4 = sb3.toString();
                            i5 = i12;
                        } else {
                            i5 = 4;
                        }
                        if (zIsToday) {
                            dndTile.mDndMenuSummary = dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_today, string4);
                        } else {
                            dndTile.mDndMenuSummary = dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_tomorrow, string4);
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                    i = i5 < 2 ? i2 : (i5 < 2 || i5 >= 4) ? i5 == 4 ? 3 : 5 : 2;
                    string = dndTile.mDndMenuSummary;
                } else {
                    string = string3;
                    i = i7;
                }
            }
        } else {
            if (z) {
                dndTile.mLastDndDurationSelected = -1;
                String description = ZenModeConfig.getDescription(dndTile.mContext, true, zenModeConfig, false);
                for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                    if (zenRule.isAutomaticActive() && description != null && !description.isEmpty() && description.equals(zenRule.name)) {
                        if (ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                            long nextChangeTime = ZenModeConfig.toScheduleCalendar(zenRule.conditionId).getNextChangeTime(System.currentTimeMillis());
                            Context context5 = dndTile.mContext;
                            CharSequence formattedTime2 = ZenModeConfig.getFormattedTime(context5, nextChangeTime, true, context5.getUserId());
                            if (ZenModeConfig.isToday(nextChangeTime)) {
                                sb.append(dndTile.mContext.getResources().getString(R.string.sec_dnd_detail_on_until_time_today, formattedTime2));
                            } else {
                                sb.append(dndTile.mContext.getResources().getString(R.string.sec_dnd_detail_on_until_time_tomorrow, formattedTime2));
                            }
                            string = sb.toString() + "\n" + dndTile.mContext.getResources().getString(R.string.sec_dnd_detail_turned_by_app_name, description);
                        } else {
                            string = dndTile.mContext.getResources().getString(R.string.sec_dnd_detail_turned_by_app_name, dndTile.getApplicationNameFromPackage$1(zenRule.pkg)) + "\n" + ("(" + description + ')');
                        }
                    }
                }
            }
            i = 0;
        }
        dndTile.mDndMenuSummary = string;
        dndTile.mDndMenuSelectedItem = i;
        textView = this.mSummary;
        if (textView != null) {
            textView.setText(this.mDndTile.mDndMenuSummary);
        }
        ViewGroup viewGroup22 = (ViewGroup) viewInflate.findViewById(R.id.dnd_menu_layout);
        this.mMenuOptions = viewGroup22;
        int i82 = DNDDetailItems.$r8$clinit;
        DNDDetailItems dNDDetailItems4 = (DNDDetailItems) LayoutInflater.from(context).inflate(R.layout.sec_qs_detail_dnd_items, viewGroup22, false);
        this.mDNDActivationItems = dNDDetailItems4;
        this.mMenuOptions.addView(dNDDetailItems4);
        i6 = 0;
        while (i6 < 6) {
            if (i6 == 0) {
                string2 = this.mContext.getResources().getString(R.string.sec_qs_dnd_detail_off_option);
            } else if (i6 < 4) {
                int iPow = (int) Math.pow(2.0d, i6 - 1);
                string2 = this.mContext.getResources().getQuantityString(R.plurals.sec_qs_dnd_detail_fhl_hours_option, iPow, Integer.valueOf(iPow));
            } else {
                string2 = i6 == 4 ? this.mContext.getResources().getString(R.string.sec_qs_dnd_detail_until_i_turn_off_option) : this.mContext.getResources().getString(R.string.sec_qs_dnd_detail_on_option);
            }
            this.mDndMenuOptions[i6] = string2;
            i6++;
        }
        updateDndActivationItems(false);
        DNDDetailItems dNDDetailItems22 = this.mDNDActivationItems;
        dNDDetailItems22.getClass();
        dNDDetailItems22.mTag = "DNDDetailItems.Do not disturb";
        DNDDetailItems dNDDetailItems32 = this.mDNDActivationItems;
        dNDDetailItems32.mHandler.removeMessages(2);
        dNDDetailItems32.mHandler.obtainMessage(2, this).sendToTarget();
        return viewInflate;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final String getDetailAdapterSummary() {
        return this.mDndTile.mDndMenuSummary;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 149;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return DndTile.DND_SETTINGS;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.quick_settings_dnd_detail_title);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return null;
    }

    public final void updateDetailItem(DNDDetailItems.Item item, boolean z) throws Resources.NotFoundException {
        item.getClass();
        Resources resources = this.mContext.getResources();
        Typeface typefaceCreate = Typeface.create(Typeface.create("sec", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        Typeface typefaceCreate2 = Typeface.create(Typeface.create("sec", 0), 400, false);
        int color = resources.getColor(R.color.dnd_detail_selected_text_color);
        int color2 = resources.getColor(R.color.dnd_detail_unselected_text_color);
        int color3 = resources.getColor(R.color.dnd_detail_unselected_text_summary_color);
        CheckedTextView checkedTextView = item.ctv;
        FontSizeUtils.updateFontSize(checkedTextView, R.dimen.qs_detail_dnd_check_text_size, 0.8f, 1.3f);
        TextView textView = item.stv;
        if (textView.getText().toString().isEmpty()) {
            textView.setVisibility(8);
        }
        if (checkedTextView != null) {
            boolean z2 = resources.getConfiguration().getLayoutDirection() == 1;
            checkedTextView.setChecked(z);
            if (z) {
                color3 = color;
            }
            textView.setTextColor(color3);
            if (!z) {
                color = color2;
            }
            checkedTextView.setTextColor(color);
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.dnd_detail_option_ic_check);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable drawable2 = (z && z2) ? drawable : null;
            if (!z || z2) {
                drawable = null;
            }
            checkedTextView.setCompoundDrawables(drawable2, null, drawable, null);
            if (!z) {
                typefaceCreate = typefaceCreate2;
            }
            checkedTextView.setTypeface(typefaceCreate);
        }
        DNDDetailItems dNDDetailItems = this.mDNDActivationItems;
        int i = dNDDetailItems.mAdapter.getCount() <= 0 ? 0 : 1;
        dNDDetailItems.mHandler.removeMessages(3);
        dNDDetailItems.mHandler.obtainMessage(3, i, 0).sendToTarget();
    }

    public final void updateDndActivationItems(boolean z) {
        DNDDetailItems dNDDetailItems = this.mDNDActivationItems;
        int i = this.mDndTile.mDndMenuSelectedItem;
        String str = this.mDndMenuOptions[i];
        dNDDetailItems.getClass();
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
            dNDDetailItems.updateQSPanelOptions(1);
        } else if (i == 5) {
            dNDDetailItems.updateQSPanelOptions(0);
        }
        dNDDetailItems.mSelectedMenu = str;
        DNDDetailItems dNDDetailItems2 = this.mDNDActivationItems;
        int i2 = dNDDetailItems2.mAdapter.getCount() > 0 ? 1 : 0;
        dNDDetailItems2.mHandler.removeMessages(3);
        dNDDetailItems2.mHandler.obtainMessage(3, i2, 0).sendToTarget();
        if (!z) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.DndDetailAdapter.1
                @Override // java.lang.Runnable
                public final void run() {
                    final DndDetailAdapter dndDetailAdapter = DndDetailAdapter.this;
                    if (dndDetailAdapter.mDNDActivationItems == null) {
                        return;
                    }
                    Log.d("DndDetailAdapter", "setItems");
                    ArrayList arrayList = new ArrayList();
                    for (String str2 : dndDetailAdapter.mDndMenuOptions) {
                        DNDDetailItems.Item item = new DNDDetailItems.Item();
                        item.line1 = str2;
                        arrayList.add(item);
                    }
                    DNDDetailItems dNDDetailItems3 = dndDetailAdapter.mDNDActivationItems;
                    DNDDetailItems.Item[] itemArr = (DNDDetailItems.Item[]) arrayList.toArray(new DNDDetailItems.Item[arrayList.size()]);
                    dNDDetailItems3.mHandler.removeMessages(1);
                    dNDDetailItems3.mHandler.obtainMessage(1, itemArr).sendToTarget();
                    dndDetailAdapter.mDNDActivationItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.DndDetailAdapter.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DndDetailAdapter dndDetailAdapter2 = DndDetailAdapter.this;
                            dndDetailAdapter2.mMenuOptions.setVisibility(dndDetailAdapter2.mDNDActivationItems.mAdapter.getCount() > 0 ? 0 : 8);
                        }
                    });
                    dndDetailAdapter.mItemsList.clear();
                    dndDetailAdapter.mItemsList.addAll(arrayList);
                }
            });
            return;
        }
        DNDDetailItems dNDDetailItems3 = this.mDNDActivationItems;
        ArrayList arrayList = this.mItemsList;
        DNDDetailItems.Item[] itemArr = (DNDDetailItems.Item[]) arrayList.toArray(new DNDDetailItems.Item[arrayList.size()]);
        dNDDetailItems3.mHandler.removeMessages(1);
        dNDDetailItems3.mHandler.obtainMessage(1, itemArr).sendToTarget();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
    }
}
