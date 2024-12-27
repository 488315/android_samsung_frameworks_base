package com.android.systemui.volume;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelSlicesAdapter extends RecyclerView.Adapter {
    public final LifecycleOwner mLifecycleOwner;
    public VolumePanelDialog$$ExternalSyntheticLambda5 mOnSliceActionListener;
    public final List mSliceLiveData;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SliceRowViewHolder extends RecyclerView.ViewHolder {
        public final SliceView mSliceView;

        public SliceRowViewHolder(View view) {
            super(view);
            RowContent rowContent;
            SliceView sliceView = (SliceView) view.findViewById(R.id.slice_view);
            this.mSliceView = sliceView;
            sliceView.setMode(2);
            sliceView.mShowTitleItems = true;
            ListContent listContent = sliceView.mListContent;
            if (listContent != null && (rowContent = listContent.mHeaderContent) != null) {
                rowContent.mShowTitleItems = true;
            }
            sliceView.setImportantForAccessibility(2);
            VolumePanelDialog$$ExternalSyntheticLambda5 volumePanelDialog$$ExternalSyntheticLambda5 = VolumePanelSlicesAdapter.this.mOnSliceActionListener;
            sliceView.mSliceObserver = volumePanelDialog$$ExternalSyntheticLambda5;
            sliceView.mCurrentView.setSliceActionListener(volumePanelDialog$$ExternalSyntheticLambda5);
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
        SliceRowViewHolder sliceRowViewHolder = (SliceRowViewHolder) viewHolder;
        LiveData liveData = (LiveData) ((ArrayList) this.mSliceLiveData).get(i);
        LifecycleOwner lifecycleOwner = VolumePanelSlicesAdapter.this.mLifecycleOwner;
        SliceView sliceView = sliceRowViewHolder.mSliceView;
        liveData.observe(lifecycleOwner, sliceView);
        Slice slice = (Slice) liveData.getValue();
        if (slice != null && !Arrays.asList(slice.mHints).contains("error")) {
            Iterator it = Arrays.asList(slice.mItems).iterator();
            while (it.hasNext()) {
                if (((SliceItem) it.next()).mFormat.equals("slice")) {
                    sliceView.setVisibility(0);
                    return;
                }
            }
        }
        sliceView.setVisibility(8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        return new SliceRowViewHolder(MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.volume_panel_slice_slider_row, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return i;
    }
}
