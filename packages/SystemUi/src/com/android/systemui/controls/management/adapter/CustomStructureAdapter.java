package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.systemui.BasicRune;
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
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomStructureAdapter extends RecyclerView.Adapter {
    public final AUIFacade auiFacade;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public Consumer layoutCompletedCallback;
    public final LayoutUtil layoutUtil;
    public StructureModel model;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public /* synthetic */ CustomStructureAdapter(LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, AUIFacade aUIFacade, int i, Consumer consumer, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(layoutUtil, controlsUtil, controlsRuneWrapper, aUIFacade, i, (i2 & 32) != 0 ? null : consumer);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List elements;
        StructureModel structureModel = this.model;
        if (structureModel == null || (elements = structureModel.getElements()) == null) {
            return 0;
        }
        return elements.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        StructureModel structureModel = this.model;
        if (structureModel == null) {
            throw new IllegalStateException("Getting item type for null model");
        }
        StructureElementWrapper structureElementWrapper = (StructureElementWrapper) structureModel.getElements().get(i);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(context) { // from class: com.android.systemui.controls.management.adapter.CustomStructureAdapter$onAttachedToRecyclerView$1$1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public final void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                CustomStructureAdapter customStructureAdapter = CustomStructureAdapter.this;
                Consumer consumer = customStructureAdapter.layoutCompletedCallback;
                if (consumer != null) {
                    consumer.accept(this);
                }
                customStructureAdapter.layoutCompletedCallback = null;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CustomStructureHolder customStructureHolder = (CustomStructureHolder) viewHolder;
        StructureModel structureModel = this.model;
        if (structureModel != null) {
            customStructureHolder.bindData((StructureElementWrapper) structureModel.getElements().get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        RecyclerView.ViewHolder structureControlHolder;
        LayoutInflater from = LayoutInflater.from(recyclerView.getContext());
        if (i == 1) {
            structureControlHolder = new StructureControlHolder(from.inflate(R.layout.controls_structure_view, (ViewGroup) recyclerView, false), this.currentUserId, this.layoutUtil, this.controlsUtil, this.controlsRuneWrapper);
        } else {
            if (i != 2) {
                if (i == 3) {
                    return new StructureControlPaddingHolder(from.inflate(R.layout.controls_empty_padding, (ViewGroup) recyclerView, false));
                }
                if (i == 4) {
                    return new StructureControlSubtitleHolder(from.inflate(R.layout.controls_subtitle_view, (ViewGroup) recyclerView, false));
                }
                if (i == 100) {
                    return new StructureControlLoadingHolder(from.inflate(R.layout.controls_loading_view, (ViewGroup) recyclerView, false));
                }
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Wrong viewType: ", i));
            }
            structureControlHolder = new StructureControlReorderHolder(from.inflate(R.layout.controls_structure_category_reorder_header, (ViewGroup) recyclerView, false), new Consumer() { // from class: com.android.systemui.controls.management.adapter.CustomStructureAdapter$onCreateViewHolder$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2;
                    StructureControlReorderHolder structureControlReorderHolder = (StructureControlReorderHolder) obj;
                    ItemTouchHelper itemTouchHelper = ((ReorderStructureModel) CustomStructureAdapter.this.model).itemTouchHelper;
                    ItemTouchHelper.Callback callback = itemTouchHelper.mCallback;
                    RecyclerView recyclerView2 = itemTouchHelper.mRecyclerView;
                    int movementFlags = callback.getMovementFlags(structureControlReorderHolder);
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView2);
                    int i3 = movementFlags & 3158064;
                    if (i3 != 0) {
                        int i4 = movementFlags & (~i3);
                        if (layoutDirection == 0) {
                            i2 = i3 >> 2;
                        } else {
                            int i5 = i3 >> 1;
                            i4 |= (-3158065) & i5;
                            i2 = (i5 & 3158064) >> 2;
                        }
                        movementFlags = i4 | i2;
                    }
                    boolean z = (movementFlags & 16711680) != 0;
                    View view = structureControlReorderHolder.itemView;
                    if (!z) {
                        Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
                        view.announceForAccessibility(itemTouchHelper.mRecyclerView.getContext().getString(R.string.dragndroplist_item_cannot_be_dragged, Integer.valueOf(structureControlReorderHolder.getLayoutPosition() + 1)));
                    } else if (view.getParent() != itemTouchHelper.mRecyclerView) {
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
                    if (BasicRune.CONTROLS_AUI) {
                        ((AUIFacadeImpl) CustomStructureAdapter.this.auiFacade).audioManager.playSoundEffect(106);
                        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                    }
                }
            });
        }
        return structureControlHolder;
    }

    public CustomStructureAdapter(LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, AUIFacade aUIFacade, int i, Consumer<RecyclerView.LayoutManager> consumer) {
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.auiFacade = aUIFacade;
        this.currentUserId = i;
        this.layoutCompletedCallback = consumer;
    }
}
