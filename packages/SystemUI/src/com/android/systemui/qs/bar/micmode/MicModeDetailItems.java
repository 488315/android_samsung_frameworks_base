package com.android.systemui.qs.bar.micmode;

import android.content.Context;
import android.content.res.Resources;
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
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MicModeDetailItems extends FrameLayout {
    public final Adapter adapter;
    public final AudioManager audioManager;
    public Callback callback;
    public final Context context;
    public final H handler;
    public AutoSizingList itemList;
    public final List items;
    public int selectedMode;

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
        public final View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
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
                public final void onClick(View view2) throws Resources.NotFoundException {
                    MicModeDetailItems.Callback callback2 = micModeDetailItems2.callback;
                    if (callback2 != null) {
                        MicModeDetailItems.Item item3 = item;
                        MicModeDetailAdapter micModeDetailAdapter = (MicModeDetailAdapter) callback2;
                        ArrayList arrayList = micModeDetailAdapter.mItemsList;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            micModeDetailAdapter.updateDetailItem((MicModeDetailItems.Item) obj, false);
                        }
                        micModeDetailAdapter.updateDetailItem(item3, true);
                    }
                    if (micModeDetailItems2.selectedMode != item.getMicMode()) {
                        micModeDetailItems2.selectedMode = item.getMicMode();
                        MicModeDetailItems micModeDetailItems3 = micModeDetailItems2;
                        int micMode = item.getMicMode();
                        micModeDetailItems3.getClass();
                        Log.d("MicModeDetailItems", "set mic mode to " + micMode);
                        micModeDetailItems3.audioManager.setMicInputControlMode(micMode);
                        MicModeDetailItems micModeDetailItems4 = micModeDetailItems2;
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

    public interface Callback {
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
        if (autoSizingList == null) {
            autoSizingList = null;
        }
        autoSizingList.setAdapter(this.adapter);
    }
}
