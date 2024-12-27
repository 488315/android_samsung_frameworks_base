package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R;
import com.android.systemui.qs.tiles.dialog.InternetAdapter;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.wifi.flags.Flags;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InternetAdapter extends RecyclerView.Adapter {
    public final CoroutineScope mCoroutineScope;
    public View mHolderView;
    public final InternetDialogController mInternetDialogController;
    protected int mMaxEntriesCount = 3;
    public List mWifiEntries;
    protected int mWifiEntriesCount;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class InternetViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final CoroutineScope mCoroutineScope;
        public final InternetDialogController mInternetDialogController;
        public Job mJob;
        public final ImageView mWifiEndIcon;
        public final ImageView mWifiIcon;
        public final LinearLayout mWifiListLayout;
        public final TextView mWifiSummaryText;
        public final TextView mWifiTitleText;

        public InternetViewHolder(View view, InternetDialogController internetDialogController, CoroutineScope coroutineScope) {
            super(view);
            this.mContext = view.getContext();
            this.mInternetDialogController = internetDialogController;
            this.mCoroutineScope = coroutineScope;
            this.mWifiListLayout = (LinearLayout) view.requireViewById(R.id.wifi_list);
            this.mWifiIcon = (ImageView) view.requireViewById(R.id.wifi_icon);
            this.mWifiTitleText = (TextView) view.requireViewById(R.id.wifi_title);
            this.mWifiSummaryText = (TextView) view.requireViewById(R.id.wifi_summary);
            this.mWifiEndIcon = (ImageView) view.requireViewById(R.id.wifi_end_icon);
        }

        public final void wifiConnect(WifiEntry wifiEntry, View view) {
            if (wifiEntry.shouldEditBeforeConnect()) {
                String key = wifiEntry.getKey();
                WifiUtils.Companion.getClass();
                Intent intent = new Intent(WifiUtils.ACTION_WIFI_DIALOG);
                intent.putExtra(WifiUtils.EXTRA_CHOSEN_WIFI_ENTRY_KEY, key);
                intent.putExtra(WifiUtils.EXTRA_CONNECT_FOR_CALLER, true);
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.addFlags(131072);
                this.mContext.startActivity(intent);
                return;
            }
            boolean canConnect = wifiEntry.canConnect();
            InternetDialogController internetDialogController = this.mInternetDialogController;
            if (!canConnect) {
                if (wifiEntry.isSaved()) {
                    Log.w("InternetAdapter", "The saved Wi-Fi network does not allow to connect. SSID:" + wifiEntry.getSsid());
                    internetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                    return;
                }
                return;
            }
            internetDialogController.getClass();
            boolean z = InternetDialogController.DEBUG;
            if (wifiEntry.getWifiConfiguration() != null) {
                if (z) {
                    RecyclerView$$ExternalSyntheticOutline0.m(wifiEntry.getWifiConfiguration().networkId, "InternetDialogController", new StringBuilder("connect networkId="));
                }
            } else if (z) {
                Log.d("InternetDialogController", "connect to unsaved network " + wifiEntry.getTitle());
            }
            wifiEntry.connect(new InternetDialogController.WifiEntryConnectCallback(internetDialogController.mActivityStarter, wifiEntry, internetDialogController));
        }
    }

    public InternetAdapter(InternetDialogController internetDialogController, CoroutineScope coroutineScope) {
        this.mInternetDialogController = internetDialogController;
        this.mCoroutineScope = coroutineScope;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mWifiEntriesCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Drawable drawable;
        final InternetViewHolder internetViewHolder = (InternetViewHolder) viewHolder;
        List list = this.mWifiEntries;
        if (list == null || i >= this.mWifiEntriesCount) {
            return;
        }
        final WifiEntry wifiEntry = (WifiEntry) list.get(i);
        ImageView imageView = internetViewHolder.mWifiIcon;
        Drawable wifiDrawable = internetViewHolder.mInternetDialogController.getWifiDrawable(wifiEntry);
        Drawable drawable2 = null;
        if (wifiDrawable == null) {
            drawable = null;
        } else {
            wifiDrawable.setTint(Utils.getColorAttrDefaultColor(internetViewHolder.mContext, android.R.attr.textColorTertiary, 0));
            AtomicReference atomicReference = new AtomicReference();
            atomicReference.set(wifiDrawable);
            drawable = (Drawable) atomicReference.get();
        }
        imageView.setImageDrawable(drawable);
        String title = wifiEntry.getTitle();
        Spanned fromHtml = Html.fromHtml(wifiEntry.getSummary(false), 0);
        internetViewHolder.mWifiTitleText.setText(title);
        if (TextUtils.isEmpty(fromHtml)) {
            internetViewHolder.mWifiSummaryText.setVisibility(8);
        } else {
            internetViewHolder.mWifiSummaryText.setVisibility(0);
            internetViewHolder.mWifiSummaryText.setText(fromHtml);
        }
        int connectedState = wifiEntry.getConnectedState();
        int security = wifiEntry.getSecurity();
        if (connectedState != 0) {
            drawable2 = internetViewHolder.mContext.getDrawable(R.drawable.ic_settings_24dp);
        } else if (security != 0 && security != 4) {
            drawable2 = internetViewHolder.mContext.getDrawable(R.drawable.ic_friction_lock_closed);
        }
        if (drawable2 == null) {
            internetViewHolder.mWifiEndIcon.setVisibility(8);
        } else {
            internetViewHolder.mWifiEndIcon.setVisibility(0);
            internetViewHolder.mWifiEndIcon.setImageDrawable(drawable2);
        }
        internetViewHolder.mWifiListLayout.setEnabled(wifiEntry.canConnect() || wifiEntry.canDisconnect() || wifiEntry.isSaved());
        if (connectedState != 0) {
            final int i2 = 0;
            internetViewHolder.mWifiListLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0
                /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2] */
                /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3] */
                @Override // android.view.View.OnClickListener
                public final void onClick(final View view) {
                    switch (i2) {
                        case 0:
                            internetViewHolder.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                            break;
                        default:
                            final InternetAdapter.InternetViewHolder internetViewHolder2 = internetViewHolder;
                            final WifiEntry wifiEntry2 = wifiEntry;
                            internetViewHolder2.getClass();
                            Flags.FEATURE_FLAGS.getClass();
                            if (!wifiEntry2.getSecurityTypes().contains(1)) {
                                internetViewHolder2.wifiConnect(wifiEntry2, view);
                                break;
                            } else if (internetViewHolder2.mJob == null) {
                                internetViewHolder2.mJob = WifiUtils.checkWepAllowed(internetViewHolder2.mContext, internetViewHolder2.mCoroutineScope, wifiEntry2.getSsid(), new Function1() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        View view2 = view;
                                        InternetAdapter.InternetViewHolder.this.mInternetDialogController.startActivity((Intent) obj, view2);
                                        return null;
                                    }
                                }, new Function0() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        InternetAdapter.InternetViewHolder.this.wifiConnect(wifiEntry2, view);
                                        return null;
                                    }
                                });
                                break;
                            }
                            break;
                    }
                }
            });
        } else {
            final int i3 = 1;
            internetViewHolder.mWifiListLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0
                /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2] */
                /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3] */
                @Override // android.view.View.OnClickListener
                public final void onClick(final View view) {
                    switch (i3) {
                        case 0:
                            internetViewHolder.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                            break;
                        default:
                            final InternetAdapter.InternetViewHolder internetViewHolder2 = internetViewHolder;
                            final WifiEntry wifiEntry2 = wifiEntry;
                            internetViewHolder2.getClass();
                            Flags.FEATURE_FLAGS.getClass();
                            if (!wifiEntry2.getSecurityTypes().contains(1)) {
                                internetViewHolder2.wifiConnect(wifiEntry2, view);
                                break;
                            } else if (internetViewHolder2.mJob == null) {
                                internetViewHolder2.mJob = WifiUtils.checkWepAllowed(internetViewHolder2.mContext, internetViewHolder2.mCoroutineScope, wifiEntry2.getSsid(), new Function1() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        View view2 = view;
                                        InternetAdapter.InternetViewHolder.this.mInternetDialogController.startActivity((Intent) obj, view2);
                                        return null;
                                    }
                                }, new Function0() { // from class: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        InternetAdapter.InternetViewHolder.this.wifiConnect(wifiEntry2, view);
                                        return null;
                                    }
                                });
                                break;
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        this.mHolderView = MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.internet_list_item, viewGroup, false);
        return new InternetViewHolder(this.mHolderView, this.mInternetDialogController, this.mCoroutineScope);
    }
}
