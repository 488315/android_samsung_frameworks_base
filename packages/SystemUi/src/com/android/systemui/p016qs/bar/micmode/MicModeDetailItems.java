package com.android.systemui.p016qs.bar.micmode;

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
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.p016qs.AutoSizingList;
import com.android.systemui.p016qs.bar.MicModeDetailAdapter;
import com.android.systemui.p016qs.bar.micmode.MicModeDetailItems;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MicModeDetailItems extends FrameLayout {
    public final Adapter adapter;
    public final AudioManager audioManager;
    public Callback callback;
    public final Context context;
    public final HandlerC2094H handler;
    public AutoSizingList itemList;
    public final List items;
    public int selectedMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.check_text);
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
                        micModeDetailItems4.context.getSharedPreferences("micmode_pref", 0).edit().putString(loggingId, loggingValue).apply();
                        String str = SystemUIAnalytics.sCurrentScreenID;
                        if (Intrinsics.areEqual("ASMM1031", loggingId)) {
                            loggingValue = KeyAttributes$$ExternalSyntheticOutline0.m21m("VOICE_", loggingValue);
                        }
                        SystemUIAnalytics.sendEventLog(str, "ASMM1029", loggingValue);
                    }
                }
            });
            return view;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.bar.micmode.MicModeDetailItems$H */
    public final class HandlerC2094H extends Handler {
        public HandlerC2094H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    MicModeDetailItems.this.callback = (Callback) message.obj;
                    return;
                }
                return;
            }
            MicModeDetailItems micModeDetailItems = MicModeDetailItems.this;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.handler = new HandlerC2094H();
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
        AutoSizingList autoSizingList = (AutoSizingList) findViewById(android.R.id.list);
        this.itemList = autoSizingList;
        autoSizingList.setAdapter(this.adapter);
    }
}
