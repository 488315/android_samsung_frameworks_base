package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.bar.micmode.MicModeDetailItems;
import com.android.systemui.qs.bar.micmode.MicModeItemFactory;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.presence.ServiceTuple;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MicModeDetailAdapter implements DetailAdapter, MicModeDetailItems.Callback {
    public final AudioManager mAudioManager;
    public final Context mContext;
    public final ArrayList mItemsList = new ArrayList();
    public MicModeDetailItems mMicModeActivationItems;

    public MicModeDetailAdapter(Context context) {
        this.mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mAudioManager = audioManager;
        context.getSharedPreferences(SystemUIAnalytics.MIC_MODE_PREF_NAME, 0).edit().putBoolean(SystemUIAnalytics.STID_MIC_MODE_IS_SUPPORTED, audioManager.getMicModeType() != 0).apply();
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        View inflate = from.inflate(R.layout.sec_qs_detail_mic_mode, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.mic_mode_menu_layout);
        this.mMicModeActivationItems = (MicModeDetailItems) from.inflate(R.layout.sec_qs_detail_mic_mode_items, viewGroup2, false);
        int micModeEffect = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getMicModeEffect();
        boolean z = this.mAudioManager.getModeInternal() == 3 && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isMicModeWifiCalling();
        if (this.mMicModeActivationItems != null) {
            Log.d("MicModeDetailAdapter", "setItems");
            ArrayList arrayList = new ArrayList();
            int i = z ? 0 : 3;
            int i2 = (z ? 3 : 2) + i;
            while (i < i2) {
                arrayList.add(MicModeItemFactory.create(i, this.mContext));
                i++;
            }
            MicModeDetailItems micModeDetailItems = this.mMicModeActivationItems;
            micModeDetailItems.handler.removeMessages(1);
            micModeDetailItems.handler.obtainMessage(1, arrayList).sendToTarget();
            this.mItemsList.clear();
            this.mItemsList.addAll(arrayList);
        }
        if (!z) {
            micModeEffect += 3;
        }
        MicModeDetailItems micModeDetailItems2 = this.mMicModeActivationItems;
        micModeDetailItems2.selectedMode = micModeEffect;
        micModeDetailItems2.handler.removeMessages(2);
        micModeDetailItems2.handler.obtainMessage(2, this).sendToTarget();
        viewGroup2.addView(this.mMicModeActivationItems);
        return inflate;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 5011;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.sec_qs_mic_mode);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final boolean getToggleEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return null;
    }

    public final void updateDetailItem(MicModeDetailItems.Item item, boolean z) {
        Resources resources = this.mContext.getResources();
        Typeface create = Typeface.create(Typeface.create("sec-600", 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        Typeface create2 = Typeface.create(Typeface.create("sec-400", 0), 400, false);
        int color = resources.getColor(R.color.mic_mode_detail_selected_text_color, null);
        int color2 = resources.getColor(R.color.mic_mode_detail_unselected_text_color, null);
        CheckedTextView checkedTextView = item.ctv;
        if (checkedTextView != null) {
            boolean z2 = resources.getConfiguration().getLayoutDirection() == 1;
            checkedTextView.setChecked(z);
            if (!z) {
                color = color2;
            }
            checkedTextView.setTextColor(color);
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.mic_mode_detail_option_ic_check);
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
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
    }
}
