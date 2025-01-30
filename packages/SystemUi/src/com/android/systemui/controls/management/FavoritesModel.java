package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.widget.TextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FavoritesModel implements ControlsModel {
    public RecyclerView.Adapter adapter;
    public final ComponentName componentName;
    public final CustomIconCache customIconCache;
    public int dividerPosition;
    public final List elements;
    public final FavoritesModelCallback favoritesModelCallback;
    public final FavoritesModel$itemTouchHelperCallback$1 itemTouchHelperCallback;
    public boolean modified;
    public final FavoritesModel$moveHelper$1 moveHelper = new FavoritesModel$moveHelper$1(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FavoritesModelCallback extends ControlsModel.ControlsModelCallback {
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.systemui.controls.management.FavoritesModel$itemTouchHelperCallback$1] */
    public FavoritesModel(CustomIconCache customIconCache, ComponentName componentName, List<ControlInfo> list, FavoritesModelCallback favoritesModelCallback) {
        this.customIconCache = customIconCache;
        this.componentName = componentName;
        this.favoritesModelCallback = favoritesModelCallback;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlInfoWrapper(this.componentName, (ControlInfo) it.next(), true, new FavoritesModel$elements$1$1(this.customIconCache)));
        }
        List plus = CollectionsKt___CollectionsKt.plus(arrayList, new DividerWrapper(false, false, 3, null));
        this.elements = plus;
        this.dividerPosition = ((ArrayList) plus).size() - 1;
        this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.FavoritesModel$itemTouchHelperCallback$1
            public final int MOVEMENT;

            {
                super(0, 0);
                this.MOVEMENT = 15;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                return viewHolder2.getBindingAdapterPosition() < FavoritesModel.this.dividerPosition;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                return viewHolder.getBindingAdapterPosition() < FavoritesModel.this.dividerPosition ? ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0) : ItemTouchHelper.Callback.makeMovementFlags(0, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                FavoritesModel.this.onMoveItemInternal(viewHolder.getBindingAdapterPosition(), viewHolder2.getBindingAdapterPosition());
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            }
        };
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final void changeFavoriteStatus(String str, boolean z) {
        List list = this.elements;
        Iterator it = ((ArrayList) list).iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            Object obj = (ElementWrapper) it.next();
            if ((obj instanceof ControlInterface) && Intrinsics.areEqual(((ControlInterface) obj).getControlId(), str)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        int i2 = this.dividerPosition;
        if (i >= i2 || !z) {
            if (i <= i2 || z) {
                if (z) {
                    onMoveItemInternal(i, i2);
                } else {
                    onMoveItemInternal(i, ((ArrayList) list).size() - 1);
                }
            }
        }
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final List getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final List getFavorites() {
        List take = CollectionsKt___CollectionsKt.take(this.elements, this.dividerPosition);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(take, 10));
        Iterator it = take.iterator();
        while (it.hasNext()) {
            arrayList.add(((ControlInfoWrapper) ((ElementWrapper) it.next())).controlInfo);
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final ControlsModel.MoveHelper getMoveHelper() {
        return this.moveHelper;
    }

    public final void onMoveItemInternal(int i, int i2) {
        RecyclerView.Adapter adapter;
        boolean z;
        RecyclerView.Adapter adapter2;
        int i3 = this.dividerPosition;
        if (i == i3) {
            return;
        }
        List list = this.elements;
        boolean z2 = false;
        if ((i < i3 && i2 >= i3) || (i > i3 && i2 <= i3)) {
            if (i < i3 && i2 >= i3) {
                ((ControlInfoWrapper) ((ArrayList) list).get(i)).favorite = false;
            } else if (i > i3 && i2 <= i3) {
                ((ControlInfoWrapper) ((ArrayList) list).get(i)).favorite = true;
            }
            int i4 = this.dividerPosition;
            if (i >= i4 || i2 < i4) {
                if (i > i4 && i2 <= i4) {
                    int i5 = i4 + 1;
                    this.dividerPosition = i5;
                    if (i5 == 1) {
                        updateDividerNone(i4, false);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.dividerPosition == ((ArrayList) list).size() - 1) {
                        ((DividerWrapper) ((ArrayList) list).get(i4)).showDivider = false;
                        z = true;
                    }
                }
                z = z2;
            } else {
                int i6 = i4 - 1;
                this.dividerPosition = i6;
                if (i6 == 0) {
                    updateDividerNone(i4, true);
                    z2 = true;
                }
                if (this.dividerPosition == ((ArrayList) list).size() - 2) {
                    ((DividerWrapper) ((ArrayList) list).get(i4)).showDivider = true;
                    z = true;
                }
                z = z2;
            }
            if (z && (adapter2 = this.adapter) != null) {
                adapter2.notifyItemChanged(i4);
            }
            z2 = true;
        }
        if (i < i2) {
            int i7 = i;
            while (i7 < i2) {
                int i8 = i7 + 1;
                Collections.swap(list, i7, i8);
                i7 = i8;
            }
        } else {
            int i9 = i2 + 1;
            if (i9 <= i) {
                int i10 = i;
                while (true) {
                    int i11 = i10 - 1;
                    Collections.swap(list, i10, i11);
                    if (i10 == i9) {
                        break;
                    } else {
                        i10 = i11;
                    }
                }
            }
        }
        RecyclerView.Adapter adapter3 = this.adapter;
        if (adapter3 != null) {
            adapter3.notifyItemMoved(i, i2);
        }
        if (z2 && (adapter = this.adapter) != null) {
            adapter.notifyItemChanged(i2, new Object());
        }
        if (this.modified) {
            return;
        }
        this.modified = true;
        ((ControlsEditingActivity$favoritesModelCallback$1) this.favoritesModelCallback).onFirstChange();
    }

    public final void updateDividerNone(int i, boolean z) {
        ((DividerWrapper) ((ArrayList) this.elements).get(i)).showNone = z;
        ControlsEditingActivity controlsEditingActivity = ((ControlsEditingActivity$favoritesModelCallback$1) this.favoritesModelCallback).this$0;
        if (z) {
            TextView textView = controlsEditingActivity.subtitle;
            (textView != null ? textView : null).setText(ControlsEditingActivity.EMPTY_TEXT_ID);
        } else {
            TextView textView2 = controlsEditingActivity.subtitle;
            (textView2 != null ? textView2 : null).setText(ControlsEditingActivity.SUBTITLE_ID);
        }
    }
}
