package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.ControlWrapper;
import com.android.systemui.controls.management.model.LoadingWrapper;
import com.android.systemui.controls.management.model.PaddingWrapper;
import com.android.systemui.controls.management.model.ReorderStructureModel;
import com.android.systemui.controls.management.model.ReorderWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;
import com.android.systemui.controls.management.model.StructureModel;
import com.android.systemui.controls.management.model.SubtitleWrapper;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecStructureAdapter extends RecyclerView.Adapter {
    public final AUIFacade auiFacade;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public Consumer layoutCompletedCallback;
    public final LayoutUtil layoutUtil;
    public StructureModel model;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ SecStructureAdapter(LayoutUtil layoutUtil, ControlsUtil controlsUtil, AUIFacade aUIFacade, int i, Consumer consumer, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(layoutUtil, controlsUtil, aUIFacade, i, (i2 & 16) != 0 ? null : consumer);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List elements;
        StructureModel structureModel = this.model;
        if (structureModel == null || (elements = structureModel.getElements()) == null) {
            return 0;
        }
        return ((ArrayList) elements).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        StructureModel structureModel = this.model;
        if (structureModel == null) {
            throw new IllegalStateException("Getting item type for null model");
        }
        StructureElementWrapper structureElementWrapper = (StructureElementWrapper) ((ArrayList) structureModel.getElements()).get(i);
        if (structureElementWrapper instanceof ControlWrapper) {
            return 1;
        }
        if (structureElementWrapper instanceof ReorderWrapper) {
            return 2;
        }
        if (structureElementWrapper instanceof PaddingWrapper) {
            return 3;
        }
        if (structureElementWrapper instanceof SubtitleWrapper) {
            return 4;
        }
        if (structureElementWrapper instanceof LoadingWrapper) {
            return 100;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context) { // from class: com.android.systemui.controls.management.adapter.SecStructureAdapter$onAttachedToRecyclerView$1$1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public final void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                SecStructureAdapter secStructureAdapter = SecStructureAdapter.this;
                Consumer consumer = secStructureAdapter.layoutCompletedCallback;
                if (consumer != null) {
                    consumer.accept(this);
                }
                secStructureAdapter.layoutCompletedCallback = null;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SecStructureViewHolder secStructureViewHolder = (SecStructureViewHolder) viewHolder;
        StructureModel structureModel = this.model;
        if (structureModel != null) {
            secStructureViewHolder.bindData((StructureElementWrapper) ((ArrayList) structureModel.getElements()).get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        RecyclerView.ViewHolder structureControlHolder;
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 1) {
            structureControlHolder = new StructureControlHolder(from.inflate(R.layout.controls_structure_view, viewGroup, false), this.currentUserId, this.layoutUtil, this.controlsUtil);
        } else {
            if (i != 2) {
                if (i == 3) {
                    return new StructureControlPaddingHolder(from.inflate(R.layout.controls_empty_padding, viewGroup, false));
                }
                if (i == 4) {
                    return new StructureControlSubtitleHolder(from.inflate(R.layout.controls_sub_title_view, viewGroup, false));
                }
                if (i == 100) {
                    return new StructureControlLoadingHolder(from.inflate(R.layout.controls_loading_view, viewGroup, false));
                }
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong viewType: "));
            }
            structureControlHolder = new StructureControlReorderHolder(from.inflate(R.layout.controls_structure_reorder_header, viewGroup, false), new Consumer() { // from class: com.android.systemui.controls.management.adapter.SecStructureAdapter$onCreateViewHolder$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StructureControlReorderHolder structureControlReorderHolder = (StructureControlReorderHolder) obj;
                    ItemTouchHelper itemTouchHelper = ((ReorderStructureModel) SecStructureAdapter.this.model).itemTouchHelper;
                    ItemTouchHelper.Callback callback = itemTouchHelper.mCallback;
                    RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                    int movementFlags = callback.getMovementFlags(structureControlReorderHolder);
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (!((ItemTouchHelper.Callback.convertToAbsoluteDirection(movementFlags, recyclerView.getLayoutDirection()) & 16711680) != 0)) {
                        Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
                        structureControlReorderHolder.itemView.announceForAccessibility(itemTouchHelper.mRecyclerView.getContext().getString(R.string.dragndroplist_item_cannot_be_dragged, Integer.valueOf(structureControlReorderHolder.getLayoutPosition() + 1)));
                    } else if (structureControlReorderHolder.itemView.getParent() != itemTouchHelper.mRecyclerView) {
                        Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
                    } else {
                        VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.recycle();
                        }
                        itemTouchHelper.mVelocityTracker = VelocityTracker.obtain();
                        itemTouchHelper.mDy = 0.0f;
                        itemTouchHelper.mDx = 0.0f;
                        itemTouchHelper.select(structureControlReorderHolder, 2);
                    }
                    AUIFacade aUIFacade = SecStructureAdapter.this.auiFacade;
                    View view = structureControlReorderHolder.itemView;
                    ((AUIFacadeImpl) aUIFacade).audioManager.playSoundEffect(106);
                    view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                }
            });
        }
        return structureControlHolder;
    }

    public SecStructureAdapter(LayoutUtil layoutUtil, ControlsUtil controlsUtil, AUIFacade aUIFacade, int i, Consumer<RecyclerView.LayoutManager> consumer) {
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.auiFacade = aUIFacade;
        this.currentUserId = i;
        this.layoutCompletedCallback = consumer;
    }
}
