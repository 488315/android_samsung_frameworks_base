package com.android.systemui.statusbar;

import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.statusbar.model.KshData;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KshViewAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public List mData;
    public final LayoutInflater mInflater;
    public KshData mKshData;
    public int mMaxColumn;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final RecyclerView recyclerView;
        public final TextView subHeader;

        public ViewHolder(KshViewAdapter kshViewAdapter, View view) {
            super(view);
            this.subHeader = (TextView) view.findViewById(R.id.ksh_subheader_text);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.ksh_info_recycler_view);
        }
    }

    public KshViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mData.size();
    }

    public KshData getKshData() {
        return this.mKshData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        KeyboardShortcutGroup keyboardShortcutGroup = (KeyboardShortcutGroup) this.mData.get(i);
        viewHolder2.subHeader.setText(keyboardShortcutGroup.getLabel());
        KshChildViewAdapter kshChildViewAdapter = new KshChildViewAdapter(this.mContext, this.mInflater);
        List<KeyboardShortcutInfo> items = keyboardShortcutGroup.getItems();
        KshData kshData = this.mKshData;
        kshChildViewAdapter.mData = items;
        kshChildViewAdapter.mKshData = kshData;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        RecyclerView recyclerView = viewHolder2.recyclerView;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(kshChildViewAdapter);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        LinearLayout linearLayout = (LinearLayout) this.mInflater.inflate(R.layout.samsung_keyboard_shortcut_group_view, viewGroup, false);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = viewGroup.getMeasuredWidth() / this.mMaxColumn;
        linearLayout.setLayoutParams(layoutParams);
        return new ViewHolder(this, linearLayout);
    }
}
