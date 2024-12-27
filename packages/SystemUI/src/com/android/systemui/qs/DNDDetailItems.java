package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.detail.DndDetailAdapter;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import java.util.Calendar;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

public class DNDDetailItems extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Adapter mAdapter;
    public Callback mCallback;
    public final Context mContext;
    public final H mHandler;
    public AutoSizingList mItemList;
    public final int[] mItemVisible;
    public Item[] mItems;
    public boolean mItemsVisible;
    public String mSelectedMenu;
    public String mTag;

    public final class Adapter extends BaseAdapter {
        public /* synthetic */ Adapter(DNDDetailItems dNDDetailItems, int i) {
            this();
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            Item[] itemArr = DNDDetailItems.this.mItems;
            if (itemArr != null) {
                return itemArr.length;
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return DNDDetailItems.this.mItems[i];
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            DNDDetailItems dNDDetailItems = DNDDetailItems.this;
            final Item item = dNDDetailItems.mItems[i];
            if (view == null) {
                view = LayoutInflater.from(dNDDetailItems.mContext).inflate(R.layout.sec_qs_detail_dnd_item, viewGroup, false);
            }
            view.setVisibility(DNDDetailItems.this.mItemVisible[i] == 1 ? 0 : 8);
            CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.check_text);
            TextView textView = (TextView) view.findViewById(R.id.check_summary);
            if (item.ctv == null) {
                if (item.stv == null) {
                    textView.setText((CharSequence) null);
                    item.stv = textView;
                }
                checkedTextView.setText(item.line1);
                item.ctv = checkedTextView;
                DNDDetailItems dNDDetailItems2 = DNDDetailItems.this;
                if (dNDDetailItems2.mCallback != null) {
                    if (item.line1.equalsIgnoreCase(dNDDetailItems2.mSelectedMenu)) {
                        ((DndDetailAdapter) DNDDetailItems.this.mCallback).updateDetailItem(item, true);
                    } else {
                        ((DndDetailAdapter) DNDDetailItems.this.mCallback).updateDetailItem(item, false);
                    }
                }
            }
            checkedTextView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.DNDDetailItems.Adapter.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    TextView textView2;
                    Callback callback = DNDDetailItems.this.mCallback;
                    if (callback != null) {
                        Item item2 = item;
                        DndDetailAdapter dndDetailAdapter = (DndDetailAdapter) callback;
                        Iterator it = dndDetailAdapter.mItemsList.iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            Item item3 = (Item) it.next();
                            dndDetailAdapter.updateDetailItem(item3, false);
                            if (item3.line1.equals(item2.line1)) {
                                dndDetailAdapter.mDndTile.mDndMenuSelectedItem = i2;
                            }
                            i2++;
                        }
                        dndDetailAdapter.updateDetailItem(item2, true);
                        dndDetailAdapter.updateDndActivationItems(true);
                        DndTile dndTile = dndDetailAdapter.mDndTile;
                        int i3 = dndTile.mDndMenuSelectedItem;
                        String str = dndTile.TAG;
                        ZenModeController zenModeController = dndTile.mController;
                        if (i3 == 0) {
                            ((ZenModeControllerImpl) zenModeController).setZen(0, null, str);
                            dndTile.mDndMenuSummary = "";
                        } else if (i3 == 1 || i3 == 2 || i3 == 3) {
                            int pow = DndTile.mZenOneHourSession * ((int) Math.pow(2.0d, i3 - 1));
                            Calendar calendar = Calendar.getInstance();
                            long j = (calendar.get(11) * 60) + calendar.get(12) + pow;
                            if (j >= 1440) {
                                dndTile.mDndMenuSummary = dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_tomorrow, DndTile.getStringFromMillis(dndTile.mContext, j - 1440));
                            } else {
                                dndTile.mDndMenuSummary = dndTile.mContext.getString(R.string.sec_dnd_detail_on_until_time_today, DndTile.getStringFromMillis(dndTile.mContext, j));
                            }
                            ((ZenModeControllerImpl) zenModeController).setZen(1, ZenModeConfig.toTimeCondition(dndTile.mContext, pow, ActivityManager.getCurrentUser(), true).id, str);
                            Settings.Secure.putInt(dndTile.mContext.getContentResolver(), "zen_duration", pow);
                            dndTile.mLastDndDurationSelected = dndTile.mDndMenuSelectedItem;
                        } else if (i3 == 4 || i3 == 5) {
                            ((ZenModeControllerImpl) zenModeController).setZen(1, null, str);
                            Settings.Secure.putInt(dndTile.mContext.getContentResolver(), "zen_duration", 0);
                            dndTile.mDndMenuSummary = "";
                        }
                        if (dndTile.mDndMenuSelectedItem != 0) {
                            dndTile.mIsSettingsUpdated = false;
                        }
                        SecQSDetailController secQSDetailController = dndDetailAdapter.mSecQSDetailController;
                        if (secQSDetailController != null) {
                            SecQSDetailController$dndSummaryCallback$1 secQSDetailController$dndSummaryCallback$1 = secQSDetailController.dndSummaryCallback;
                            secQSDetailController$dndSummaryCallback$1.getClass();
                            int i4 = SecQSDetailController.$r8$clinit;
                            SecQSDetailController secQSDetailController2 = secQSDetailController$dndSummaryCallback$1.this$0;
                            if (Intrinsics.areEqual(secQSDetailController2.detailTileSpec, PluginLockShortcutTask.DO_NOT_DISTURB_TASK)) {
                                ViewGroup viewGroup2 = secQSDetailController2.detailExtendedSummarContainer;
                                if ((viewGroup2 != null ? Integer.valueOf(viewGroup2.getVisibility()) : null) != 0 || (textView2 = secQSDetailController2.detailExtendedSummary) == null) {
                                    return;
                                }
                                DetailAdapter detailAdapter = secQSDetailController2.detailAdapter;
                                textView2.setText(detailAdapter != null ? detailAdapter.getDetailAdapterSummary() : null);
                            }
                        }
                    }
                }
            });
            return view;
        }

        private Adapter() {
        }
    }

    public interface Callback {
    }

    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            int i2 = message.what;
            DNDDetailItems dNDDetailItems = DNDDetailItems.this;
            if (i2 == 1) {
                Item[] itemArr = (Item[]) message.obj;
                if (itemArr != null) {
                    int i3 = DNDDetailItems.$r8$clinit;
                    dNDDetailItems.getClass();
                    i = itemArr.length;
                } else {
                    i = 0;
                }
                dNDDetailItems.mItemList.setVisibility(i != 0 ? 0 : 8);
                dNDDetailItems.mItems = itemArr;
                dNDDetailItems.mAdapter.notifyDataSetChanged();
                return;
            }
            if (i2 == 2) {
                dNDDetailItems.mCallback = (Callback) message.obj;
                return;
            }
            if (i2 == 3) {
                boolean z = message.arg1 != 0;
                if (dNDDetailItems.mItemsVisible == z) {
                    return;
                }
                dNDDetailItems.mItemsVisible = z;
                for (int i4 = 0; i4 < dNDDetailItems.mItemList.getChildCount(); i4++) {
                    dNDDetailItems.mItemList.getChildAt(i4).setVisibility(dNDDetailItems.mItemVisible[i4] == 1 ? 0 : 8);
                }
            }
        }
    }

    public final class Item {
        public String line1;
        public CheckedTextView ctv = null;
        public TextView stv = null;
    }

    public DNDDetailItems(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new H();
        this.mAdapter = new Adapter(this, 0);
        this.mItemsVisible = true;
        this.mItemVisible = new int[]{1, 1, 1, 1, 1, 0};
        this.mContext = context;
        this.mTag = "DNDDetailItems";
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(this.mTag, "onAttachedToWindow");
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(this.mTag, "onDetachedFromWindow");
        this.mCallback = null;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        super.onFinishInflate();
        AutoSizingList autoSizingList = (AutoSizingList) findViewById(android.R.id.list);
        this.mItemList = autoSizingList;
        autoSizingList.setAdapter(this.mAdapter);
    }

    public final void updateQSPanelOptions(int i) {
        int[] iArr = this.mItemVisible;
        iArr[2] = i;
        iArr[1] = i;
        iArr[4] = i;
        iArr[3] = i;
        iArr[5] = 1 - i;
    }
}
