package com.android.systemui.p016qs.customize;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.p016qs.customize.CustomizerTileViewPager;
import com.android.systemui.p016qs.customize.SecQSCustomizerBase;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomActionMoveItem implements View.OnClickListener {
    public final Consumer actionCancelConsumer;
    public final Context context;
    public final CustomizerTileViewPager destinationTileLayout;
    public final boolean isAvailableSource;
    public final BiConsumer moveToSourceConsumer;
    public final BiConsumer moveToTargetConsumer;
    public final SecQSCustomizerBase.CustomTileInfo source;
    public final CustomizerTileViewPager sourceTileLayout;

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

    public CustomActionMoveItem(Context context, SecQSCustomizerBase.CustomTileInfo customTileInfo, CustomizerTileViewPager customizerTileViewPager, CustomizerTileViewPager customizerTileViewPager2, boolean z, BiConsumer<SecQSCustomizerBase.CustomTileInfo, Integer> biConsumer, Consumer<SecQSCustomizerBase.CustomTileInfo> consumer, BiConsumer<SecQSCustomizerBase.CustomTileInfo, Integer> biConsumer2, BiConsumer<SecQSCustomizerBase.CustomTileInfo, Integer> biConsumer3, boolean z2) {
        this.context = context;
        this.source = customTileInfo;
        this.sourceTileLayout = customizerTileViewPager;
        this.destinationTileLayout = customizerTileViewPager2;
        this.isAvailableSource = z;
        this.actionCancelConsumer = consumer;
        this.moveToSourceConsumer = biConsumer2;
        this.moveToTargetConsumer = biConsumer3;
        if (z2) {
            Log.d("CSTMPagedTileLayout", "addDummyTile");
            if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(customizerTileViewPager2.mPages.size() - 1)).isFull()) {
                customizerTileViewPager2.addPage();
            }
            SecQSCustomizerBase.CustomTileInfo customTileInfo2 = new SecQSCustomizerBase.CustomTileInfo();
            customizerTileViewPager2.mDummyTile = customTileInfo2;
            customTileInfo2.state = new QSTile.State();
            SecQSCustomizerBase.CustomTileInfo customTileInfo3 = customizerTileViewPager2.mDummyTile;
            customTileInfo3.spec = "dummy";
            customTileInfo3.isActive = false;
            ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(r1.size() - 1)).addTile(customizerTileViewPager2.mDummyTile);
        }
        for (SecQSCustomizerBase.CustomTileInfo customTileInfo4 : getSources()) {
            SecCustomizeTileView secCustomizeTileView = customTileInfo4.customTileView;
            String contentDescription = getContentDescription(customTileInfo4);
            secCustomizeTileView.setOnClickListener(this);
            secCustomizeTileView.setContentDescription(contentDescription);
        }
        for (SecQSCustomizerBase.CustomTileInfo customTileInfo5 : getDestinations()) {
            SecCustomizeTileView secCustomizeTileView2 = customTileInfo5.customTileView;
            String contentDescription2 = getContentDescription(customTileInfo5);
            secCustomizeTileView2.setOnClickListener(this);
            secCustomizeTileView2.setContentDescription(contentDescription2);
        }
        CustomizerTileViewPager customizerTileViewPager3 = this.destinationTileLayout;
        ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager3.mPages.get(customizerTileViewPager3.getCurrentItem())).mCustomTilesInfo.stream().findFirst().ifPresent(new CustomizerTileViewPager$$ExternalSyntheticLambda1());
        biConsumer.accept(this.source, Integer.valueOf(getSources().indexOf(this.source)));
    }

    public static final String getContentDescription$getContentDescription(CustomActionMoveItem customActionMoveItem, CustomizerTileViewPager customizerTileViewPager, int i, int i2) {
        if (customizerTileViewPager.mColumns * customizerTileViewPager.mRows == 0 || customizerTileViewPager.getColumnCount() == 0) {
            return customActionMoveItem.context.getResources().getString(i2, -1, -1);
        }
        int i3 = i % (customizerTileViewPager.mColumns * customizerTileViewPager.mRows);
        int columnCount = i3 % customizerTileViewPager.getColumnCount();
        return customActionMoveItem.context.getResources().getString(i2, Integer.valueOf(i3 / customizerTileViewPager.getColumnCount()), Integer.valueOf(columnCount));
    }

    public final void actionFinish() {
        for (SecQSCustomizerBase.CustomTileInfo customTileInfo : getSources()) {
            SecCustomizeTileView secCustomizeTileView = customTileInfo.customTileView;
            String str = customTileInfo.customizeTileContentDes;
            secCustomizeTileView.setOnClickListener(null);
            secCustomizeTileView.setContentDescription(str);
        }
        for (SecQSCustomizerBase.CustomTileInfo customTileInfo2 : getDestinations()) {
            SecCustomizeTileView secCustomizeTileView2 = customTileInfo2.customTileView;
            String str2 = customTileInfo2.customizeTileContentDes;
            secCustomizeTileView2.setOnClickListener(null);
            secCustomizeTileView2.setContentDescription(str2);
        }
        CustomizerTileViewPager customizerTileViewPager = this.destinationTileLayout;
        customizerTileViewPager.getClass();
        Log.d("CSTMPagedTileLayout", "removeDummyTile");
        if (customizerTileViewPager.mDummyTile != null) {
            int size = customizerTileViewPager.mPages.size() - 1;
            if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size)).indexOf(customizerTileViewPager.mDummyTile) >= 0) {
                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size)).removeTile(customizerTileViewPager.mDummyTile, false);
                if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size)).mCustomTilesInfo.size() <= 0) {
                    customizerTileViewPager.removePage();
                }
                customizerTileViewPager.mDummyTile = null;
            }
        }
        this.actionCancelConsumer.accept(this.source);
    }

    public final String getContentDescription(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        int indexOf = getSources().indexOf(customTileInfo);
        int indexOf2 = getDestinations().indexOf(customTileInfo);
        int i = R.string.qs_custom_action_move_from_available_to_available;
        if (indexOf >= 0) {
            if (!this.isAvailableSource) {
                i = R.string.qs_custom_action_move_from_available_to_active;
            }
            return getContentDescription$getContentDescription(this, this.sourceTileLayout, indexOf, i);
        }
        if (indexOf2 < 0) {
            return "";
        }
        if (this.isAvailableSource) {
            i = R.string.qs_custom_action_move_from_available_to_active;
        }
        return getContentDescription$getContentDescription(this, this.destinationTileLayout, indexOf2, i);
    }

    public final ArrayList getDestinations() {
        ArrayList tilesInfo = this.destinationTileLayout.getTilesInfo();
        ArrayList arrayList = new ArrayList();
        CollectionsKt___CollectionsKt.toCollection(tilesInfo, arrayList);
        return arrayList;
    }

    public final ArrayList getSources() {
        ArrayList tilesInfo = this.sourceTileLayout.getTilesInfo();
        ArrayList arrayList = new ArrayList();
        CollectionsKt___CollectionsKt.toCollection(tilesInfo, arrayList);
        return arrayList;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == null) {
            return;
        }
        Object tag = view.getTag();
        SecQSCustomizerBase.CustomTileInfo customTileInfo = tag instanceof SecQSCustomizerBase.CustomTileInfo ? (SecQSCustomizerBase.CustomTileInfo) tag : null;
        if (customTileInfo == null) {
            return;
        }
        int indexOf = getSources().indexOf(customTileInfo);
        int indexOf2 = getDestinations().indexOf(customTileInfo);
        if (indexOf >= 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("move to source=", indexOf, "CustomActionMoveItem");
            this.moveToSourceConsumer.accept(this.source, Integer.valueOf(indexOf));
        } else if (indexOf2 >= 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("move to target=", indexOf2, "CustomActionMoveItem");
            this.moveToTargetConsumer.accept(this.source, Integer.valueOf(indexOf2));
        }
        actionFinish();
    }
}
