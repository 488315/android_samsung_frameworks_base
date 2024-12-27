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

    public SemSelectTaskListAdapter(
            List<DisplayResolveInfo> list,
            AbstractMultiProfilePagerAdapter multiProfilePagerAdapter) {
        this.secondDepthList = list;
        this.mMultiProfilePagerAdapter = multiProfilePagerAdapter;
    }

    public SemSelectTaskListAdapter(
            List<DisplayResolveInfo> list,
            AbstractMultiProfilePagerAdapter multiProfilePagerAdapter,
            ActivityCallback activityCallback) {
        this.secondDepthList = list;
        this.mMultiProfilePagerAdapter = multiProfilePagerAdapter;
        this.mActivityCallback = activityCallback;
    }

    public ResolveInfo resolveInfoForPosition(int position, boolean filtered) {
        TargetInfo target = targetInfoForPosition(position, filtered);
        if (target != null) {
            return target.getResolveInfo();
        }
        return null;
    }

    public TargetInfo targetInfoForPosition(int position, boolean filtered) {
        if (position < 0) {
            position = 0;
        }
        if (filtered) {
            return getItem(position);
        }
        if (this.secondDepthList.size() > position) {
            return this.secondDepthList.get(position);
        }
        return null;
    }

    public TargetInfo getItem(int position) {
        return this.secondDepthList.get(position);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem =
                layoutInflater.inflate(R.layout.sem_resolver_second_depth_list_item, parent, false);
        this.viewHolder = new ViewHolder(listItem);
        return this.viewHolder;
    }

    @Override // com.android.internal.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean isSamePackage;
        int index =
                Math.max(
                        this.mMultiProfilePagerAdapter
                                .getActiveListAdapter()
                                .getLastChosenActivityIndex(),
                        0);
        String lastChosenPackage =
                this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenPackage();
        if (!TextUtils.isEmpty(lastChosenPackage)) {
            isSamePackage =
                    lastChosenPackage.equals(
                            this.secondDepthList
                                    .get(position)
                                    .getResolvedComponentName()
                                    .getPackageName());
        } else {
            isSamePackage = false;
        }
        if (this.mSelectedItem < 0) {
            this.mSelectedItem = isSamePackage ? index : 0;
        }
        holder.radioButton.setChecked(this.mSelectedItem == position);
        CharSequence text = this.secondDepthList.get(position).getExtendedInfo();
        if (text == null) {
            text = this.secondDepthList.get(position).getDisplayLabel();
        }
        holder.textView.lambda$setTextAsync$0(text);
    }

    @Override // com.android.internal.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.secondDepthList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioButton;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.radioButton =
                    (RadioButton) itemView.findViewById(R.id.sem_resolver_second_depth_item_button);
            this.textView =
                    (TextView) itemView.findViewById(R.id.sem_resolver_second_depth_item_text);
            View.OnClickListener clickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.internal.app.chooser.SemSelectTaskListAdapter.ViewHolder.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            SemSelectTaskListAdapter.this.mSelectedItem =
                                    ViewHolder.this.getAdapterPosition();
                            SemSelectTaskListAdapter.this.notifyDataSetChanged();
                            if (ViewHolder.this.radioButton.isChecked()
                                    && SemSelectTaskListAdapter.this.mActivityCallback != null) {
                                SemSelectTaskListAdapter.this.mActivityCallback.onStartSelected(
                                        SemSelectTaskListAdapter.this.mSelectedItem, false, true);
                            }
                        }
                    };
            this.radioButton.setOnClickListener(clickListener);
            itemView.setOnClickListener(clickListener);
        }
    }
}
