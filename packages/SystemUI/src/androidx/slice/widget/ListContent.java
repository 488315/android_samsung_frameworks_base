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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int i = sliceViewPolicy.mMaxHeight;
        boolean z = sliceViewPolicy.mScrollable;
        int listItemsHeight = sliceStyle.getListItemsHeight(this.mRowItems, sliceViewPolicy);
        if (i > 0) {
            i = Math.max(this.mHeaderContent.getHeight(sliceStyle, sliceViewPolicy), i);
        }
        int i2 = i > 0 ? i : sliceStyle.mListLargeHeight;
        if (listItemsHeight - i2 >= sliceStyle.mListMinScrollHeight && !sliceStyle.mExpandToAvailableHeight) {
            listItemsHeight = i2;
        } else if (i > 0) {
            listItemsHeight = Math.min(i2, listItemsHeight);
        }
        return !z ? sliceStyle.getListItemsHeight(sliceStyle.getListItemsForNonScrollingList(this, listItemsHeight, sliceViewPolicy).mDisplayedItems, sliceViewPolicy) : listItemsHeight;
    }

    public final SliceAction getShortcut(Context context) {
        SliceItem sliceItem;
        SliceItem sliceItem2;
        Intent launchIntentForPackage;
        IconCompat createWithBitmap;
        SliceActionImpl sliceActionImpl = this.mPrimaryAction;
        if (sliceActionImpl != null) {
            return sliceActionImpl;
        }
        SliceItem sliceItem3 = this.mSliceItem;
        if (sliceItem3 != null) {
            SliceItem find = SliceQuery.find(sliceItem3, "action", new String[]{UniversalCredentialUtil.AGENT_TITLE, "shortcut"}, (String[]) null);
            if (find != null) {
                sliceItem = SliceQuery.find(find, "image", UniversalCredentialUtil.AGENT_TITLE);
                sliceItem2 = SliceQuery.find(find, "text", (String) null);
            } else {
                sliceItem = null;
                sliceItem2 = null;
            }
            if (find == null) {
                find = SliceQuery.find(this.mSliceItem, "action", (String) null);
            }
            if (sliceItem == null) {
                sliceItem = SliceQuery.find(this.mSliceItem, "image", UniversalCredentialUtil.AGENT_TITLE);
            }
            if (sliceItem2 == null) {
                sliceItem2 = SliceQuery.find(this.mSliceItem, "text", UniversalCredentialUtil.AGENT_TITLE);
            }
            if (sliceItem == null) {
                sliceItem = SliceQuery.find(this.mSliceItem, "image", (String) null);
            }
            if (sliceItem2 == null) {
                sliceItem2 = SliceQuery.find(this.mSliceItem, "text", (String) null);
            }
            int parseImageMode = sliceItem != null ? SliceActionImpl.parseImageMode(sliceItem) : 5;
            if (context != null) {
                SliceItem find2 = SliceQuery.find(this.mSliceItem, "slice", (String) null);
                if (find2 != null) {
                    Uri parse = Uri.parse(find2.getSlice().mUri);
                    IconCompat iconCompat = sliceItem != null ? (IconCompat) sliceItem.mObj : null;
                    CharSequence charSequence = sliceItem2 != null ? (CharSequence) sliceItem2.mObj : null;
                    PackageManager packageManager = context.getPackageManager();
                    ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(parse.getAuthority(), 0);
                    ApplicationInfo applicationInfo = resolveContentProvider != null ? resolveContentProvider.applicationInfo : null;
                    if (applicationInfo != null) {
                        if (iconCompat == null) {
                            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
                            if (applicationIcon instanceof BitmapDrawable) {
                                createWithBitmap = IconCompat.createWithBitmap(((BitmapDrawable) applicationIcon).getBitmap());
                            } else {
                                Bitmap createBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(createBitmap);
                                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                applicationIcon.draw(canvas);
                                createWithBitmap = IconCompat.createWithBitmap(createBitmap);
                            }
                            iconCompat = createWithBitmap;
                            parseImageMode = 2;
                        }
                        if (charSequence == null) {
                            charSequence = packageManager.getApplicationLabel(applicationInfo);
                        }
                        if (find == null && (launchIntentForPackage = packageManager.getLaunchIntentForPackage(applicationInfo.packageName)) != null) {
                            find = new SliceItem(PendingIntent.getActivity(context, 0, launchIntentForPackage, 67108864), new Slice.Builder(parse).build(), "action", (String) null, new String[0]);
                        }
                    }
                    if (find == null) {
                        find = new SliceItem(PendingIntent.getActivity(context, 0, new Intent(), 67108864), (Slice) null, "action", (String) null, (String[]) null);
                    }
                    if (charSequence != null && iconCompat != null) {
                        return new SliceActionImpl(find.getAction(), iconCompat, parseImageMode, charSequence);
                    }
                }
            } else if (sliceItem != null && find != null && sliceItem2 != null) {
                return new SliceActionImpl(find.getAction(), (IconCompat) sliceItem.mObj, parseImageMode, (CharSequence) sliceItem2.mObj);
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
        SliceItem find = SliceQuery.find(slice, "slice", SystemUIAnalytics.QPNE_VID_ACTIONS);
        List findAll = find != null ? SliceQuery.findAll(find, "slice", new String[]{SystemUIAnalytics.QPNE_VID_ACTIONS, "shortcut"}, null) : null;
        if (findAll != null) {
            arrayList = new ArrayList(findAll.size());
            for (int i = 0; i < findAll.size(); i++) {
                arrayList.add(new SliceActionImpl((SliceItem) findAll.get(i)));
            }
        } else {
            arrayList = null;
        }
        this.mSliceActions = arrayList;
        SliceItem find2 = SliceQuery.find(slice, "slice", (String[]) null, new String[]{"list_item", "shortcut", SystemUIAnalytics.QPNE_VID_ACTIONS, "keywords", "ttl", "last_updated", "horizontal", "selection_option"});
        if (find2 == null || !"slice".equals(find2.mFormat) || find2.hasAnyHints(SystemUIAnalytics.QPNE_VID_ACTIONS, "keywords", "see_more") || SliceQuery.find(find2, "text", (String) null) == null) {
            find2 = null;
        }
        if (find2 != null) {
            RowContent rowContent = new RowContent(find2, 0);
            this.mHeaderContent = rowContent;
            this.mRowItems.add(rowContent);
        }
        SliceItem findTopLevelItem = SliceQuery.findTopLevelItem(slice, null, null, new String[]{"see_more"});
        if (findTopLevelItem == null || !"slice".equals(findTopLevelItem.mFormat)) {
            findTopLevelItem = null;
        } else {
            List asList = Arrays.asList(findTopLevelItem.getSlice().mItems);
            if (asList.size() == 1 && "action".equals(((SliceItem) asList.get(0)).mFormat)) {
                findTopLevelItem = (SliceItem) asList.get(0);
            }
        }
        if (findTopLevelItem != null) {
            this.mSeeMoreContent = new RowContent(findTopLevelItem, -1);
        }
        List asList2 = Arrays.asList(slice.mItems);
        for (int i2 = 0; i2 < asList2.size(); i2++) {
            SliceItem sliceItem = (SliceItem) asList2.get(i2);
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
        if (this.mRowItems.size() > 0 && (AlertController$$ExternalSyntheticOutline0.m(this.mRowItems, 1) instanceof GridContent)) {
            ((GridContent) AlertController$$ExternalSyntheticOutline0.m(this.mRowItems, 1)).mIsLastIndex = true;
        }
        RowContent rowContent4 = this.mHeaderContent;
        SliceItem sliceItem2 = rowContent4 != null ? rowContent4.mPrimaryAction : null;
        if (sliceItem2 == null) {
            sliceItem2 = SliceQuery.find(this.mSliceItem, "action", new String[]{"shortcut", UniversalCredentialUtil.AGENT_TITLE}, (String[]) null);
        }
        if (sliceItem2 == null) {
            sliceItem2 = SliceQuery.find(this.mSliceItem, "action", (String) null);
        }
        this.mPrimaryAction = sliceItem2 != null ? new SliceActionImpl(sliceItem2) : null;
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
