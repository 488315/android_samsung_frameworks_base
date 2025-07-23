package com.android.internal.app.chooser;

import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.widget.RecyclerView;
import java.util.List;

/* loaded from: classes5.dex */
public class SemSelectTaskListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ActivityCallback mActivityCallback;
    protected AbstractMultiProfilePagerAdapter mMultiProfilePagerAdapter;
    public int mSelectedItem = -1;
    public List<DisplayResolveInfo> secondDepthList;
    public ViewHolder viewHolder;

    public interface ActivityCallback {
        void onStartSelected(int i, boolean z, boolean z2);
    }

    public SemSelectTaskListAdapter(List<DisplayResolveInfo> list, AbstractMultiProfilePagerAdapter abstractMultiProfilePagerAdapter) {
        this.secondDepthList = list;
        this.mMultiProfilePagerAdapter = abstractMultiProfilePagerAdapter;
    }

    public SemSelectTaskListAdapter(List<DisplayResolveInfo> list, AbstractMultiProfilePagerAdapter abstractMultiProfilePagerAdapter, ActivityCallback activityCallback) {
        this.secondDepthList = list;
        this.mMultiProfilePagerAdapter = abstractMultiProfilePagerAdapter;
        this.mActivityCallback = activityCallback;
    }

    public ResolveInfo resolveInfoForPosition(int i, boolean z) {
        TargetInfo targetInfoForPosition = targetInfoForPosition(i, z);
        if (targetInfoForPosition != null) {
            return targetInfoForPosition.getResolveInfo();
        }
        return null;
    }

    public TargetInfo targetInfoForPosition(int i, boolean z) {
        List<DisplayResolveInfo> list = this.secondDepthList;
        if (list == null || list.isEmpty() || i < 0 || this.secondDepthList.size() <= i) {
            return null;
        }
        return this.secondDepthList.get(i);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sem_resolver_second_depth_list_item, viewGroup, false));
        this.viewHolder = viewHolder;
        return viewHolder;
    }

    @Override // com.android.internal.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int max = Math.max(this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenActivityIndex(), 0);
        String lastChosenPackage = this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenPackage();
        boolean equals = !TextUtils.isEmpty(lastChosenPackage) ? lastChosenPackage.equals(this.secondDepthList.get(i).getResolvedComponentName().getPackageName()) : false;
        if (this.mSelectedItem < 0) {
            if (!equals) {
                max = 0;
            }
            this.mSelectedItem = max;
        }
        viewHolder.radioButton.setChecked(this.mSelectedItem == i);
        CharSequence extendedInfo = this.secondDepthList.get(i).getExtendedInfo();
        if (extendedInfo == null) {
            extendedInfo = this.secondDepthList.get(i).getDisplayLabel();
        }
        viewHolder.textView.lambda$setTextAsync$0(extendedInfo);
    }

    @Override // com.android.internal.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.secondDepthList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioButton;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.radioButton = (RadioButton) view.findViewById(R.id.sem_resolver_second_depth_item_button);
            this.textView = (TextView) view.findViewById(R.id.sem_resolver_second_depth_item_text);
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.internal.app.chooser.SemSelectTaskListAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    SemSelectTaskListAdapter.this.mSelectedItem = ViewHolder.this.getAdapterPosition();
                    SemSelectTaskListAdapter.this.notifyDataSetChanged();
                    if (!ViewHolder.this.radioButton.isChecked() || SemSelectTaskListAdapter.this.mActivityCallback == null) {
                        return;
                    }
                    SemSelectTaskListAdapter.this.mActivityCallback.onStartSelected(SemSelectTaskListAdapter.this.mSelectedItem, false, true);
                }
            };
            this.radioButton.setOnClickListener(onClickListener);
            view.setOnClickListener(onClickListener);
        }
    }
}
