package com.android.systemui.qs.bar.micmode;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.AutoSizingList;
import com.android.systemui.qs.bar.MicModeDetailAdapter;
import com.android.systemui.qs.bar.micmode.MicModeDetailItems;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MicModeDetailItems extends FrameLayout {
    public final Adapter adapter;
    public final AudioManager audioManager;
    public Callback callback;
    public final Context context;
    public final H handler;
    public AutoSizingList itemList;
    public final List items;
    public int selectedMode;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Adapter extends BaseAdapter {
        public Adapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return ((ArrayList) MicModeDetailItems.this.items).size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (Item) ((ArrayList) MicModeDetailItems.this.items).get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            final Item item = (Item) ((ArrayList) MicModeDetailItems.this.items).get(i);
            if (view == null) {
                view = LayoutInflater.from(MicModeDetailItems.this.context).inflate(R.layout.sec_qs_detail_mic_mode_item, viewGroup, false);
            }
            CheckedTextView checkedTextView = (CheckedTextView) view.requireViewById(R.id.check_text);
            Item item2 = item.ctv == null ? item : null;
            if (item2 != null) {
                MicModeDetailItems micModeDetailItems = MicModeDetailItems.this;
                checkedTextView.setText(item2.getText());
                item2.ctv = checkedTextView;
                Callback callback = micModeDetailItems.callback;
                if (callback != null) {
                    ((MicModeDetailAdapter) callback).updateDetailItem(item, item2.getMicMode() == micModeDetailItems.selectedMode);
                }
            }
            final MicModeDetailItems micModeDetailItems2 = MicModeDetailItems.this;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.micmode.MicModeDetailItems$Adapter$getView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MicModeDetailItems.Callback callback2 = MicModeDetailItems.this.callback;
                    if (callback2 != null) {
                        MicModeDetailItems.Item item3 = item;
                        MicModeDetailAdapter micModeDetailAdapter = (MicModeDetailAdapter) callback2;
                        Iterator it = micModeDetailAdapter.mItemsList.iterator();
                        while (it.hasNext()) {
                            micModeDetailAdapter.updateDetailItem((MicModeDetailItems.Item) it.next(), false);
                        }
                        micModeDetailAdapter.updateDetailItem(item3, true);
                    }
                    if (MicModeDetailItems.this.selectedMode != item.getMicMode()) {
                        MicModeDetailItems.this.selectedMode = item.getMicMode();
                        MicModeDetailItems micModeDetailItems3 = MicModeDetailItems.this;
                        int micMode = item.getMicMode();
                        micModeDetailItems3.getClass();
                        Log.d("MicModeDetailItems", "set mic mode to " + micMode);
                        micModeDetailItems3.audioManager.setMicInputControlMode(micMode);
                        MicModeDetailItems micModeDetailItems4 = MicModeDetailItems.this;
                        String loggingId = item.getLoggingId();
                        String loggingValue = item.getLoggingValue();
                        micModeDetailItems4.context.getSharedPreferences(SystemUIAnalytics.MIC_MODE_PREF_NAME, 0).edit().putString(loggingId, loggingValue).apply();
                        String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
                        if (SystemUIAnalytics.STID_MIC_MODE_EFFECT_CP_CALL.equals(loggingId)) {
                            loggingValue = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("VOICE_", loggingValue);
                        }
                        SystemUIAnalytics.sendEventLog(currentScreenID, SystemUIAnalytics.EID_MIC_MODE_EFFECT, loggingValue);
                    }
                }
            });
            return view;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            MicModeDetailItems micModeDetailItems = MicModeDetailItems.this;
            if (i != 1) {
                if (i == 2) {
                    micModeDetailItems.callback = (Callback) message.obj;
                    return;
                }
                return;
            }
            ArrayList arrayList = (ArrayList) message.obj;
            ((ArrayList) micModeDetailItems.items).clear();
            int size = arrayList.size();
            AutoSizingList autoSizingList = micModeDetailItems.itemList;
            if (autoSizingList == null) {
                autoSizingList = null;
            }
            autoSizingList.setVisibility(size == 0 ? 8 : 0);
            ((ArrayList) micModeDetailItems.items).addAll(arrayList);
            micModeDetailItems.adapter.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Item {
        public CheckedTextView ctv;

        public abstract String getLoggingId();

        public abstract String getLoggingValue();

        public abstract int getMicMode();

        public abstract String getText();
    }

    static {
        new Companion(null);
    }

    public MicModeDetailItems(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.handler = new H();
        this.adapter = new Adapter();
        this.items = new ArrayList();
        this.audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("MicModeDetailItems", "onAttachedToWindow");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("MicModeDetailItems", "onDetachedFromWindow");
        this.callback = null;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        AutoSizingList autoSizingList = (AutoSizingList) requireViewById(android.R.id.list);
        this.itemList = autoSizingList;
        autoSizingList.setAdapter(this.adapter);
    }
}
