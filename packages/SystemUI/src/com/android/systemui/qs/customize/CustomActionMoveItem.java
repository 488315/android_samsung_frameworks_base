package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CustomActionMoveItem implements View.OnClickListener {
    public final Consumer actionCancelConsumer;
    public final Context context;
    public final CustomizerTileViewPager destinationTileLayout;
    public final boolean isAvailableSource;
    public final BiConsumer moveToSourceConsumer;
    public final BiConsumer moveToTargetConsumer;
    public final CustomTileInfo source;
    public final CustomizerTileViewPager sourceTileLayout;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomActionMoveItem(Context context, CustomTileInfo customTileInfo, CustomizerTileViewPager customizerTileViewPager, CustomizerTileViewPager customizerTileViewPager2, boolean z, BiConsumer<CustomTileInfo, Integer> biConsumer, Consumer<CustomTileInfo> consumer, BiConsumer<CustomTileInfo, Integer> biConsumer2, BiConsumer<CustomTileInfo, Integer> biConsumer3, boolean z2) {
        this.context = context;
        this.source = customTileInfo;
        this.sourceTileLayout = customizerTileViewPager;
        this.destinationTileLayout = customizerTileViewPager2;
        this.isAvailableSource = z;
        this.actionCancelConsumer = consumer;
        this.moveToSourceConsumer = biConsumer2;
        this.moveToTargetConsumer = biConsumer3;
        int i = 0;
        if (z2 && customizerTileViewPager2.mPages.size() != 0) {
            Log.d("CSTMPagedTileLayout", "addDummyTile");
            if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager2.mPages.get(customizerTileViewPager2.mPages.size() - 1)).isFull()) {
                customizerTileViewPager2.addPage();
            }
            CustomTileInfo customTileInfo2 = new CustomTileInfo();
            customizerTileViewPager2.mDummyTile = customTileInfo2;
            customTileInfo2.state = new QSTile.State();
            CustomTileInfo customTileInfo3 = customizerTileViewPager2.mDummyTile;
            customTileInfo3.spec = "dummy";
            customTileInfo3.isActive = false;
            ((CustomizerTileViewPager.CustomizerTilePage) AlertController$$ExternalSyntheticOutline0.m(1, customizerTileViewPager2.mPages)).addTile(customizerTileViewPager2.mDummyTile);
        }
        ArrayList sources = getSources();
        int size = sources.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = sources.get(i2);
            i2++;
            CustomTileInfo customTileInfo4 = (CustomTileInfo) obj;
            SecCustomizeTileView secCustomizeTileView = customTileInfo4.customTileView;
            String contentDescription = getContentDescription(customTileInfo4);
            secCustomizeTileView.setOnClickListener(this);
            secCustomizeTileView.setContentDescription(contentDescription);
        }
        ArrayList destinations = getDestinations();
        int size2 = destinations.size();
        while (i < size2) {
            Object obj2 = destinations.get(i);
            i++;
            CustomTileInfo customTileInfo5 = (CustomTileInfo) obj2;
            SecCustomizeTileView secCustomizeTileView2 = customTileInfo5.customTileView;
            String contentDescription2 = getContentDescription(customTileInfo5);
            secCustomizeTileView2.setOnClickListener(this);
            secCustomizeTileView2.setContentDescription(contentDescription2);
        }
        CustomizerTileViewPager customizerTileViewPager3 = this.destinationTileLayout;
        if (customizerTileViewPager3.mPages.size() != 0) {
            ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager3.mPages.get(customizerTileViewPager3.getCurrentItem())).mCustomTilesInfo.stream().findFirst().ifPresent(new CustomizerTileViewPager$$ExternalSyntheticLambda0());
        }
        biConsumer.accept(this.source, Integer.valueOf(getSources().indexOf(this.source)));
    }

    public static final String getContentDescription$getContentDescription(CustomActionMoveItem customActionMoveItem, CustomizerTileViewPager customizerTileViewPager, int i, int i2) {
        int columnMaxCountInPage = customizerTileViewPager.getColumnMaxCountInPage();
        int columnCount = customizerTileViewPager.getColumnCount();
        if (columnMaxCountInPage == 0 || columnCount == 0) {
            return customActionMoveItem.context.getResources().getString(i2, -1, -1);
        }
        int i3 = i % columnMaxCountInPage;
        return customActionMoveItem.context.getResources().getString(i2, Integer.valueOf(i3 / columnCount), Integer.valueOf(i3 % columnCount));
    }

    public final void actionFinish() {
        ArrayList sources = getSources();
        int size = sources.size();
        int i = 0;
        while (i < size) {
            Object obj = sources.get(i);
            i++;
            CustomTileInfo customTileInfo = (CustomTileInfo) obj;
            SecCustomizeTileView secCustomizeTileView = customTileInfo.customTileView;
            String str = customTileInfo.customizeTileContentDes;
            secCustomizeTileView.setOnClickListener(null);
            secCustomizeTileView.setContentDescription(str);
        }
        ArrayList destinations = getDestinations();
        int size2 = destinations.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = destinations.get(i2);
            i2++;
            CustomTileInfo customTileInfo2 = (CustomTileInfo) obj2;
            SecCustomizeTileView secCustomizeTileView2 = customTileInfo2.customTileView;
            String str2 = customTileInfo2.customizeTileContentDes;
            secCustomizeTileView2.setOnClickListener(null);
            secCustomizeTileView2.setContentDescription(str2);
        }
        CustomizerTileViewPager customizerTileViewPager = this.destinationTileLayout;
        customizerTileViewPager.getClass();
        Log.d("CSTMPagedTileLayout", "removeDummyTile");
        if (customizerTileViewPager.mDummyTile != null) {
            int size3 = customizerTileViewPager.mPages.size() - 1;
            if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size3)).indexOf(customizerTileViewPager.mDummyTile) >= 0) {
                ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size3)).removeTile(customizerTileViewPager.mDummyTile, false);
                if (((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(size3)).mCustomTilesInfo.size() <= 0) {
                    customizerTileViewPager.removePage();
                }
                customizerTileViewPager.mDummyTile = null;
            }
        }
        this.actionCancelConsumer.accept(this.source);
    }

    public final String getContentDescription(CustomTileInfo customTileInfo) {
        int iIndexOf = getSources().indexOf(customTileInfo);
        int iIndexOf2 = getDestinations().indexOf(customTileInfo);
        int i = R.string.qs_custom_action_move_from_available_to_active;
        if (iIndexOf >= 0) {
            if (this.isAvailableSource) {
                i = R.string.qs_custom_action_move_from_available_to_available;
            }
            return getContentDescription$getContentDescription(this, this.sourceTileLayout, iIndexOf, i);
        }
        if (iIndexOf2 < 0) {
            return "";
        }
        if (!this.isAvailableSource) {
            i = R.string.qs_custom_action_move_from_available_to_available;
        }
        return getContentDescription$getContentDescription(this, this.destinationTileLayout, iIndexOf2, i);
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
        CustomTileInfo customTileInfo = tag instanceof CustomTileInfo ? (CustomTileInfo) tag : null;
        if (customTileInfo == null) {
            return;
        }
        int iIndexOf = getSources().indexOf(customTileInfo);
        int iIndexOf2 = getDestinations().indexOf(customTileInfo);
        if (iIndexOf >= 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(iIndexOf, "move to source=", "CustomActionMoveItem");
            this.moveToSourceConsumer.accept(this.source, Integer.valueOf(iIndexOf));
        } else if (iIndexOf2 >= 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(iIndexOf2, "move to target=", "CustomActionMoveItem");
            this.moveToTargetConsumer.accept(this.source, Integer.valueOf(iIndexOf2));
        }
        actionFinish();
    }
}
