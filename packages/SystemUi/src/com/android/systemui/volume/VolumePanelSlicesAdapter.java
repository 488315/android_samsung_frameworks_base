package com.android.systemui.volume;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.ShortcutView;
import androidx.slice.widget.SliceChildView;
import androidx.slice.widget.SliceView;
import androidx.slice.widget.SliceViewPolicy;
import androidx.slice.widget.TemplateView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumePanelSlicesAdapter extends RecyclerView.Adapter {
    public final LifecycleOwner mLifecycleOwner;
    public VolumePanelDialog$$ExternalSyntheticLambda4 mOnSliceActionListener;
    public final List mSliceLiveData;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SliceRowViewHolder extends RecyclerView.ViewHolder {
        public final SliceView mSliceView;

        /* JADX WARN: Removed duplicated region for block: B:15:0x007c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public SliceRowViewHolder(View view) {
            super(view);
            RowContent rowContent;
            boolean z;
            TemplateView templateView;
            ListContent listContent;
            SliceView sliceView = (SliceView) view.findViewById(R.id.slice_view);
            this.mSliceView = sliceView;
            SliceViewPolicy sliceViewPolicy = sliceView.mViewPolicy;
            int i = sliceViewPolicy.mMode;
            if (i != 2) {
                if (i != 2) {
                    sliceViewPolicy.mMode = 2;
                    SliceViewPolicy.PolicyChangeListener policyChangeListener = sliceViewPolicy.mListener;
                    if (policyChangeListener != null && (listContent = (templateView = (TemplateView) policyChangeListener).mListContent) != null) {
                        templateView.updateDisplayedItems(listContent.getHeight(templateView.mSliceStyle, templateView.mViewPolicy));
                    }
                }
                int i2 = sliceView.mViewPolicy.mMode;
                SliceChildView sliceChildView = sliceView.mCurrentView;
                boolean z2 = sliceChildView instanceof ShortcutView;
                Set loadingActions = sliceChildView.getLoadingActions();
                if (i2 == 3 && !z2) {
                    sliceView.removeView(sliceView.mCurrentView);
                    ShortcutView shortcutView = new ShortcutView(sliceView.getContext());
                    sliceView.mCurrentView = shortcutView;
                    sliceView.addView(shortcutView, sliceView.getChildLp(shortcutView));
                } else if (i2 == 3 || !z2) {
                    z = false;
                    if (z) {
                        sliceView.mCurrentView.setPolicy(sliceView.mViewPolicy);
                        sliceView.applyConfigurations();
                        ListContent listContent2 = sliceView.mListContent;
                        if (listContent2 != null && listContent2.isValid()) {
                            sliceView.mCurrentView.setSliceContent(sliceView.mListContent);
                        }
                        sliceView.mCurrentView.setLoadingActions(loadingActions);
                    }
                    sliceView.updateActions();
                } else {
                    sliceView.removeView(sliceView.mCurrentView);
                    TemplateView templateView2 = new TemplateView(sliceView.getContext());
                    sliceView.mCurrentView = templateView2;
                    sliceView.addView(templateView2, sliceView.getChildLp(templateView2));
                }
                z = true;
                if (z) {
                }
                sliceView.updateActions();
            }
            sliceView.mShowTitleItems = true;
            ListContent listContent3 = sliceView.mListContent;
            if (listContent3 != null && (rowContent = listContent3.mHeaderContent) != null) {
                rowContent.mShowTitleItems = true;
            }
            sliceView.setImportantForAccessibility(2);
            VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4 = VolumePanelSlicesAdapter.this.mOnSliceActionListener;
            sliceView.mSliceObserver = volumePanelDialog$$ExternalSyntheticLambda4;
            sliceView.mCurrentView.setSliceActionListener(volumePanelDialog$$ExternalSyntheticLambda4);
        }
    }

    public VolumePanelSlicesAdapter(LifecycleOwner lifecycleOwner, Map<Uri, LiveData> map) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mSliceLiveData = new ArrayList(map.values());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((ArrayList) this.mSliceLiveData).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        SliceRowViewHolder sliceRowViewHolder = (SliceRowViewHolder) viewHolder;
        LiveData liveData = (LiveData) ((ArrayList) this.mSliceLiveData).get(i);
        LifecycleOwner lifecycleOwner = VolumePanelSlicesAdapter.this.mLifecycleOwner;
        SliceView sliceView = sliceRowViewHolder.mSliceView;
        liveData.observe(lifecycleOwner, sliceView);
        Slice slice = (Slice) liveData.getValue();
        if (slice != null) {
            if (!Arrays.asList(slice.mHints).contains("error")) {
                Iterator it = slice.getItems().iterator();
                while (it.hasNext()) {
                    if (((SliceItem) it.next()).mFormat.equals("slice")) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (z) {
                sliceView.setVisibility(0);
                return;
            }
        }
        sliceView.setVisibility(8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new SliceRowViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.volume_panel_slice_slider_row, (ViewGroup) recyclerView, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return i;
    }
}
