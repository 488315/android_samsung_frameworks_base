package com.android.systemui.p016qs.tiles.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R;
import com.android.systemui.p016qs.tiles.dialog.InternetAdapter;
import com.android.systemui.p016qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InternetAdapter extends RecyclerView.Adapter {
    public View mHolderView;
    public final InternetDialogController mInternetDialogController;
    protected int mMaxEntriesCount = 3;
    public List mWifiEntries;
    protected int mWifiEntriesCount;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InternetViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final InternetDialogController mInternetDialogController;
        public final ImageView mWifiEndIcon;
        public final ImageView mWifiIcon;
        protected WifiUtils.InternetIconInjector mWifiIconInjector;
        public final LinearLayout mWifiListLayout;
        public final TextView mWifiSummaryText;
        public final TextView mWifiTitleText;

        public InternetViewHolder(View view, InternetDialogController internetDialogController) {
            super(view);
            this.mContext = view.getContext();
            this.mInternetDialogController = internetDialogController;
            this.mWifiListLayout = (LinearLayout) view.requireViewById(R.id.wifi_list);
            this.mWifiIcon = (ImageView) view.requireViewById(R.id.wifi_icon);
            this.mWifiTitleText = (TextView) view.requireViewById(R.id.wifi_title);
            this.mWifiSummaryText = (TextView) view.requireViewById(R.id.wifi_summary);
            this.mWifiEndIcon = (ImageView) view.requireViewById(R.id.wifi_end_icon);
            this.mWifiIconInjector = internetDialogController.mWifiIconInjector;
        }
    }

    public InternetAdapter(InternetDialogController internetDialogController) {
        this.mInternetDialogController = internetDialogController;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mWifiEntriesCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Drawable drawable;
        Drawable icon;
        final InternetViewHolder internetViewHolder = (InternetViewHolder) viewHolder;
        List list = this.mWifiEntries;
        if (list == null || i >= this.mWifiEntriesCount) {
            return;
        }
        final WifiEntry wifiEntry = (WifiEntry) list.get(i);
        int i2 = wifiEntry.mLevel;
        boolean shouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
        Context context = internetViewHolder.mContext;
        Drawable drawable2 = null;
        final int i3 = 0;
        if (i2 == -1 || (icon = internetViewHolder.mWifiIconInjector.getIcon(i2, shouldShowXLevelIcon)) == null) {
            drawable = null;
        } else {
            icon.setTint(Utils.getColorAttrDefaultColor(android.R.attr.textColorTertiary, context, 0));
            AtomicReference atomicReference = new AtomicReference();
            atomicReference.set(icon);
            drawable = (Drawable) atomicReference.get();
        }
        internetViewHolder.mWifiIcon.setImageDrawable(drawable);
        String title = wifiEntry.getTitle();
        Spanned fromHtml = Html.fromHtml(wifiEntry.getSummary(false), 0);
        internetViewHolder.mWifiTitleText.setText(title);
        boolean isEmpty = TextUtils.isEmpty(fromHtml);
        TextView textView = internetViewHolder.mWifiSummaryText;
        if (isEmpty) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(fromHtml);
        }
        int connectedState = wifiEntry.getConnectedState();
        int security = wifiEntry.getSecurity();
        if (connectedState != 0) {
            drawable2 = context.getDrawable(R.drawable.ic_settings_24dp);
        } else if (security != 0 && security != 4) {
            drawable2 = context.getDrawable(R.drawable.ic_friction_lock_closed);
        }
        ImageView imageView = internetViewHolder.mWifiEndIcon;
        if (drawable2 == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageDrawable(drawable2);
        }
        final int i4 = 1;
        boolean z = wifiEntry.canConnect() || wifiEntry.canDisconnect() || wifiEntry.isSaved();
        LinearLayout linearLayout = internetViewHolder.mWifiListLayout;
        linearLayout.setEnabled(z);
        if (connectedState != 0) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i3) {
                        case 0:
                            internetViewHolder.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                            break;
                        default:
                            InternetAdapter.InternetViewHolder internetViewHolder2 = internetViewHolder;
                            WifiEntry wifiEntry2 = wifiEntry;
                            internetViewHolder2.getClass();
                            if (!wifiEntry2.shouldEditBeforeConnect()) {
                                boolean canConnect = wifiEntry2.canConnect();
                                InternetDialogController internetDialogController = internetViewHolder2.mInternetDialogController;
                                if (!canConnect) {
                                    if (wifiEntry2.isSaved()) {
                                        Log.w("InternetAdapter", "The saved Wi-Fi network does not allow to connect. SSID:" + wifiEntry2.getSsid());
                                        internetDialogController.launchWifiDetailsSetting(view, wifiEntry2.getKey());
                                        break;
                                    }
                                } else {
                                    internetDialogController.getClass();
                                    boolean z2 = InternetDialogController.DEBUG;
                                    if (wifiEntry2.getWifiConfiguration() != null) {
                                        if (z2) {
                                            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("connect networkId="), wifiEntry2.getWifiConfiguration().networkId, "InternetDialogController");
                                        }
                                    } else if (z2) {
                                        Log.d("InternetDialogController", "connect to unsaved network " + wifiEntry2.getTitle());
                                    }
                                    wifiEntry2.connect(new InternetDialogController.WifiEntryConnectCallback(internetDialogController.mActivityStarter, wifiEntry2, internetDialogController));
                                    break;
                                }
                            } else {
                                String key = wifiEntry2.getKey();
                                Intent intent = new Intent("com.android.settings.WIFI_DIALOG");
                                intent.putExtra("key_chosen_wifientry_key", key);
                                intent.putExtra("connect_for_caller", true);
                                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                intent.addFlags(131072);
                                internetViewHolder2.mContext.startActivity(intent);
                                break;
                            }
                            break;
                    }
                }
            });
        } else {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i4) {
                        case 0:
                            internetViewHolder.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                            break;
                        default:
                            InternetAdapter.InternetViewHolder internetViewHolder2 = internetViewHolder;
                            WifiEntry wifiEntry2 = wifiEntry;
                            internetViewHolder2.getClass();
                            if (!wifiEntry2.shouldEditBeforeConnect()) {
                                boolean canConnect = wifiEntry2.canConnect();
                                InternetDialogController internetDialogController = internetViewHolder2.mInternetDialogController;
                                if (!canConnect) {
                                    if (wifiEntry2.isSaved()) {
                                        Log.w("InternetAdapter", "The saved Wi-Fi network does not allow to connect. SSID:" + wifiEntry2.getSsid());
                                        internetDialogController.launchWifiDetailsSetting(view, wifiEntry2.getKey());
                                        break;
                                    }
                                } else {
                                    internetDialogController.getClass();
                                    boolean z2 = InternetDialogController.DEBUG;
                                    if (wifiEntry2.getWifiConfiguration() != null) {
                                        if (z2) {
                                            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("connect networkId="), wifiEntry2.getWifiConfiguration().networkId, "InternetDialogController");
                                        }
                                    } else if (z2) {
                                        Log.d("InternetDialogController", "connect to unsaved network " + wifiEntry2.getTitle());
                                    }
                                    wifiEntry2.connect(new InternetDialogController.WifiEntryConnectCallback(internetDialogController.mActivityStarter, wifiEntry2, internetDialogController));
                                    break;
                                }
                            } else {
                                String key = wifiEntry2.getKey();
                                Intent intent = new Intent("com.android.settings.WIFI_DIALOG");
                                intent.putExtra("key_chosen_wifientry_key", key);
                                intent.putExtra("connect_for_caller", true);
                                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                intent.addFlags(131072);
                                internetViewHolder2.mContext.startActivity(intent);
                                break;
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        this.mHolderView = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.internet_list_item, (ViewGroup) recyclerView, false);
        return new InternetViewHolder(this.mHolderView, this.mInternetDialogController);
    }
}
