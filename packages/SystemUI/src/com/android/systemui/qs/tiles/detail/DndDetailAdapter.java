package com.android.systemui.qs.tiles.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.DNDDetailItems;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.settings.GlobalSettings;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;

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

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View createDetailView(android.content.Context r20, android.view.View r21, android.view.ViewGroup r22) {
        /*
            Method dump skipped, instructions count: 1021
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.detail.DndDetailAdapter.createDetailView(android.content.Context, android.view.View, android.view.ViewGroup):android.view.View");
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

    public final void updateDetailItem(DNDDetailItems.Item item, boolean z) {
        item.getClass();
        Resources resources = this.mContext.getResources();
        Typeface create = Typeface.create(Typeface.create("sec", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        Typeface create2 = Typeface.create(Typeface.create("sec", 0), 400, false);
        int color = resources.getColor(R.color.dnd_detail_selected_text_color);
        int color2 = resources.getColor(R.color.dnd_detail_unselected_text_color);
        int color3 = resources.getColor(R.color.dnd_detail_unselected_text_summary_color);
        CheckedTextView checkedTextView = item.ctv;
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
                create = create2;
            }
            checkedTextView.setTypeface(create);
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
        if (i == 0) {
            dNDDetailItems.updateQSPanelOptions(1);
        } else if (i == 1 || i == 2 || i == 3) {
            dNDDetailItems.updateQSPanelOptions(1);
        } else if (i == 4) {
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
