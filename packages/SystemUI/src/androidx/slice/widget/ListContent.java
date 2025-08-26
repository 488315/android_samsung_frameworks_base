package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class ListContent extends SliceContent {
    public RowContent mHeaderContent;
    public SliceActionImpl mPrimaryAction;
    public final ArrayList mRowItems;
    public RowContent mSeeMoreContent;
    public List mSliceActions;

    public ListContent(Slice slice) {
        super(slice);
        this.mRowItems = new ArrayList();
        if (this.mSliceItem == null) {
            return;
        }
        populate(slice);
    }

    public static int getRowType(SliceContent sliceContent, boolean z, List list) {
        if (sliceContent != null) {
            if (sliceContent instanceof GridContent) {
                return 1;
            }
            RowContent rowContent = (RowContent) sliceContent;
            SliceItem sliceItem = rowContent.mPrimaryAction;
            SliceActionImpl sliceActionImpl = sliceItem != null ? new SliceActionImpl(sliceItem) : null;
            SliceItem sliceItem2 = rowContent.mRange;
            if (sliceItem2 != null) {
                return "action".equals(sliceItem2.mFormat) ? 4 : 5;
            }
            if (rowContent.mSelection != null) {
                return 6;
            }
            if (sliceActionImpl != null && sliceActionImpl.isToggle()) {
                return 3;
            }
            if (z && list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (((SliceAction) list.get(i)).isToggle()) {
                        return 3;
                    }
                }
            } else if (rowContent.mToggleItems.size() > 0) {
                return 3;
            }
        }
        return 0;
    }

    @Override // androidx.slice.widget.SliceContent
    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        sliceStyle.getClass();
        sliceViewPolicy.getClass();
        int iMax = sliceViewPolicy.mMaxHeight;
        boolean z = sliceViewPolicy.mScrollable;
        int listItemsHeight = sliceStyle.getListItemsHeight(this.mRowItems, sliceViewPolicy);
        if (iMax > 0) {
            iMax = Math.max(this.mHeaderContent.getHeight(sliceStyle, sliceViewPolicy), iMax);
        }
        int i = iMax > 0 ? iMax : sliceStyle.mListLargeHeight;
        if (listItemsHeight - i >= sliceStyle.mListMinScrollHeight && !sliceStyle.mExpandToAvailableHeight) {
            listItemsHeight = i;
        } else if (iMax > 0) {
            listItemsHeight = Math.min(i, listItemsHeight);
        }
        return !z ? sliceStyle.getListItemsHeight(sliceStyle.getListItemsForNonScrollingList(this, listItemsHeight, sliceViewPolicy).mDisplayedItems, sliceViewPolicy) : listItemsHeight;
    }

    public final SliceAction getShortcut(Context context) {
        SliceItem sliceItemFind;
        SliceItem sliceItemFind2;
        Intent launchIntentForPackage;
        IconCompat iconCompatCreateWithBitmap;
        SliceActionImpl sliceActionImpl = this.mPrimaryAction;
        if (sliceActionImpl != null) {
            return sliceActionImpl;
        }
        SliceItem sliceItem = this.mSliceItem;
        if (sliceItem != null) {
            SliceItem sliceItemFind3 = SliceQuery.find(sliceItem, "action", new String[]{UniversalCredentialUtil.AGENT_TITLE, "shortcut"}, (String[]) null);
            if (sliceItemFind3 != null) {
                sliceItemFind = SliceQuery.find(sliceItemFind3, "image", UniversalCredentialUtil.AGENT_TITLE);
                sliceItemFind2 = SliceQuery.find(sliceItemFind3, "text", (String) null);
            } else {
                sliceItemFind = null;
                sliceItemFind2 = null;
            }
            if (sliceItemFind3 == null) {
                sliceItemFind3 = SliceQuery.find(this.mSliceItem, "action", (String) null);
            }
            if (sliceItemFind == null) {
                sliceItemFind = SliceQuery.find(this.mSliceItem, "image", UniversalCredentialUtil.AGENT_TITLE);
            }
            if (sliceItemFind2 == null) {
                sliceItemFind2 = SliceQuery.find(this.mSliceItem, "text", UniversalCredentialUtil.AGENT_TITLE);
            }
            if (sliceItemFind == null) {
                sliceItemFind = SliceQuery.find(this.mSliceItem, "image", (String) null);
            }
            if (sliceItemFind2 == null) {
                sliceItemFind2 = SliceQuery.find(this.mSliceItem, "text", (String) null);
            }
            int imageMode = sliceItemFind != null ? SliceActionImpl.parseImageMode(sliceItemFind) : 5;
            if (context != null) {
                SliceItem sliceItemFind4 = SliceQuery.find(this.mSliceItem, "slice", (String) null);
                if (sliceItemFind4 != null) {
                    Uri uri = Uri.parse(sliceItemFind4.getSlice().mUri);
                    IconCompat iconCompat = sliceItemFind != null ? (IconCompat) sliceItemFind.mObj : null;
                    CharSequence applicationLabel = sliceItemFind2 != null ? (CharSequence) sliceItemFind2.mObj : null;
                    PackageManager packageManager = context.getPackageManager();
                    ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
                    ApplicationInfo applicationInfo = providerInfoResolveContentProvider != null ? providerInfoResolveContentProvider.applicationInfo : null;
                    if (applicationInfo != null) {
                        if (iconCompat == null) {
                            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
                            if (applicationIcon instanceof BitmapDrawable) {
                                iconCompatCreateWithBitmap = IconCompat.createWithBitmap(((BitmapDrawable) applicationIcon).getBitmap());
                            } else {
                                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(bitmapCreateBitmap);
                                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                applicationIcon.draw(canvas);
                                iconCompatCreateWithBitmap = IconCompat.createWithBitmap(bitmapCreateBitmap);
                            }
                            iconCompat = iconCompatCreateWithBitmap;
                            imageMode = 2;
                        }
                        if (applicationLabel == null) {
                            applicationLabel = packageManager.getApplicationLabel(applicationInfo);
                        }
                        if (sliceItemFind3 == null && (launchIntentForPackage = packageManager.getLaunchIntentForPackage(applicationInfo.packageName)) != null) {
                            sliceItemFind3 = new SliceItem(PendingIntent.getActivity(context, 0, launchIntentForPackage, 67108864), new Slice.Builder(uri).build(), "action", (String) null, new String[0]);
                        }
                    }
                    if (sliceItemFind3 == null) {
                        sliceItemFind3 = new SliceItem(PendingIntent.getActivity(context, 0, new Intent(), 67108864), (Slice) null, "action", (String) null, (String[]) null);
                    }
                    if (applicationLabel != null && iconCompat != null) {
                        return new SliceActionImpl(sliceItemFind3.getAction(), iconCompat, imageMode, applicationLabel);
                    }
                }
            } else if (sliceItemFind != null && sliceItemFind3 != null && sliceItemFind2 != null) {
                return new SliceActionImpl(sliceItemFind3.getAction(), (IconCompat) sliceItemFind.mObj, imageMode, (CharSequence) sliceItemFind2.mObj);
            }
        }
        return null;
    }

    public final boolean isValid() {
        return this.mSliceItem != null && this.mRowItems.size() > 0;
    }

    public final void populate(Slice slice) {
        ArrayList arrayList;
        if (slice == null) {
            return;
        }
        SliceItem sliceItemFind = SliceQuery.find(slice, "slice", SystemUIAnalytics.QPNE_VID_ACTIONS);
        List listFindAll = sliceItemFind != null ? SliceQuery.findAll(sliceItemFind, "slice", new String[]{SystemUIAnalytics.QPNE_VID_ACTIONS, "shortcut"}, null) : null;
        if (listFindAll != null) {
            arrayList = new ArrayList(listFindAll.size());
            for (int i = 0; i < listFindAll.size(); i++) {
                arrayList.add(new SliceActionImpl((SliceItem) listFindAll.get(i)));
            }
        } else {
            arrayList = null;
        }
        this.mSliceActions = arrayList;
        SliceItem sliceItemFind2 = SliceQuery.find(slice, "slice", (String[]) null, new String[]{"list_item", "shortcut", SystemUIAnalytics.QPNE_VID_ACTIONS, "keywords", "ttl", "last_updated", "horizontal", "selection_option"});
        if (sliceItemFind2 == null || !"slice".equals(sliceItemFind2.mFormat) || sliceItemFind2.hasAnyHints(SystemUIAnalytics.QPNE_VID_ACTIONS, "keywords", "see_more") || SliceQuery.find(sliceItemFind2, "text", (String) null) == null) {
            sliceItemFind2 = null;
        }
        if (sliceItemFind2 != null) {
            RowContent rowContent = new RowContent(sliceItemFind2, 0);
            this.mHeaderContent = rowContent;
            this.mRowItems.add(rowContent);
        }
        SliceItem sliceItemFindTopLevelItem = SliceQuery.findTopLevelItem(slice, null, null, new String[]{"see_more"});
        if (sliceItemFindTopLevelItem == null || !"slice".equals(sliceItemFindTopLevelItem.mFormat)) {
            sliceItemFindTopLevelItem = null;
        } else {
            List listAsList = Arrays.asList(sliceItemFindTopLevelItem.getSlice().mItems);
            if (listAsList.size() == 1 && "action".equals(((SliceItem) listAsList.get(0)).mFormat)) {
                sliceItemFindTopLevelItem = (SliceItem) listAsList.get(0);
            }
        }
        if (sliceItemFindTopLevelItem != null) {
            this.mSeeMoreContent = new RowContent(sliceItemFindTopLevelItem, -1);
        }
        List listAsList2 = Arrays.asList(slice.mItems);
        for (int i2 = 0; i2 < listAsList2.size(); i2++) {
            SliceItem sliceItem = (SliceItem) listAsList2.get(i2);
            String str = sliceItem.mFormat;
            if (!sliceItem.hasAnyHints(SystemUIAnalytics.QPNE_VID_ACTIONS, "see_more", "keywords", "ttl", "last_updated") && ("action".equals(str) || "slice".equals(str))) {
                if (this.mHeaderContent == null && !ArrayUtils.contains(sliceItem.mHints, "list_item")) {
                    RowContent rowContent2 = new RowContent(sliceItem, 0);
                    this.mHeaderContent = rowContent2;
                    this.mRowItems.add(0, rowContent2);
                } else if (ArrayUtils.contains(sliceItem.mHints, "list_item")) {
                    if (ArrayUtils.contains(sliceItem.mHints, "horizontal")) {
                        this.mRowItems.add(new GridContent(sliceItem, i2));
                    } else {
                        this.mRowItems.add(new RowContent(sliceItem, i2));
                    }
                }
            }
        }
        if (this.mHeaderContent == null && this.mRowItems.size() >= 1) {
            RowContent rowContent3 = (RowContent) this.mRowItems.get(0);
            this.mHeaderContent = rowContent3;
            rowContent3.mIsHeader = true;
        }
        if (this.mRowItems.size() > 0 && (AlertController$$ExternalSyntheticOutline0.m(1, this.mRowItems) instanceof GridContent)) {
            ((GridContent) AlertController$$ExternalSyntheticOutline0.m(1, this.mRowItems)).mIsLastIndex = true;
        }
        RowContent rowContent4 = this.mHeaderContent;
        SliceItem sliceItemFind3 = rowContent4 != null ? rowContent4.mPrimaryAction : null;
        if (sliceItemFind3 == null) {
            sliceItemFind3 = SliceQuery.find(this.mSliceItem, "action", new String[]{"shortcut", UniversalCredentialUtil.AGENT_TITLE}, (String[]) null);
        }
        if (sliceItemFind3 == null) {
            sliceItemFind3 = SliceQuery.find(this.mSliceItem, "action", (String) null);
        }
        this.mPrimaryAction = sliceItemFind3 != null ? new SliceActionImpl(sliceItemFind3) : null;
    }

    @Deprecated
    public ListContent(Context context, Slice slice) {
        super(slice);
        this.mRowItems = new ArrayList();
        if (this.mSliceItem == null) {
            return;
        }
        populate(slice);
    }
}
