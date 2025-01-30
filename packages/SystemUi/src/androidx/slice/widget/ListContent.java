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
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ListContent extends SliceContent {
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
        if (sliceContent == null) {
            return 0;
        }
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
        if (!z || list == null) {
            return rowContent.mToggleItems.size() > 0 ? 3 : 0;
        }
        for (int i = 0; i < list.size(); i++) {
            if (((SliceAction) list.get(i)).isToggle()) {
                return 3;
            }
        }
        return 0;
    }

    @Override // androidx.slice.widget.SliceContent
    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        sliceStyle.getClass();
        if (sliceViewPolicy.mMode == 1) {
            return this.mHeaderContent.getHeight(sliceStyle, sliceViewPolicy);
        }
        int i = sliceViewPolicy.mMaxHeight;
        int listItemsHeight = sliceStyle.getListItemsHeight(this.mRowItems, sliceViewPolicy);
        if (i > 0) {
            i = Math.max(this.mHeaderContent.getHeight(sliceStyle, sliceViewPolicy), i);
        }
        int i2 = i > 0 ? i : sliceStyle.mListLargeHeight;
        if ((listItemsHeight - i2 >= sliceStyle.mListMinScrollHeight) && !sliceStyle.mExpandToAvailableHeight) {
            listItemsHeight = i2;
        } else if (i > 0) {
            listItemsHeight = Math.min(i2, listItemsHeight);
        }
        return !sliceViewPolicy.mScrollable ? sliceStyle.getListItemsHeight(sliceStyle.getListItemsForNonScrollingList(this, listItemsHeight, sliceViewPolicy).mDisplayedItems, sliceViewPolicy) : listItemsHeight;
    }

    public final SliceAction getShortcut(Context context) {
        SliceItem sliceItem;
        SliceItem sliceItem2;
        SliceActionImpl sliceActionImpl;
        Intent launchIntentForPackage;
        IconCompat createWithBitmap;
        SliceActionImpl sliceActionImpl2 = this.mPrimaryAction;
        if (sliceActionImpl2 != null) {
            return sliceActionImpl2;
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
                    Uri uri = find2.getSlice().getUri();
                    IconCompat iconCompat = sliceItem != null ? (IconCompat) sliceItem.mObj : null;
                    CharSequence charSequence = sliceItem2 != null ? (CharSequence) sliceItem2.mObj : null;
                    PackageManager packageManager = context.getPackageManager();
                    ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
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
                            find = new SliceItem(PendingIntent.getActivity(context, 0, launchIntentForPackage, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY), new Slice.Builder(uri).build(), "action", (String) null, new String[0]);
                        }
                    }
                    if (find == null) {
                        find = new SliceItem(PendingIntent.getActivity(context, 0, new Intent(), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY), (Slice) null, "action", (String) null, (String[]) null);
                    }
                    if (charSequence != null && iconCompat != null) {
                        sliceActionImpl = new SliceActionImpl(find.getAction(), iconCompat, parseImageMode, charSequence);
                        return sliceActionImpl;
                    }
                }
            } else if (sliceItem != null && find != null && sliceItem2 != null) {
                sliceActionImpl = new SliceActionImpl(find.getAction(), (IconCompat) sliceItem.mObj, parseImageMode, (CharSequence) sliceItem2.mObj);
                return sliceActionImpl;
            }
        }
        return null;
    }

    public final boolean isValid() {
        return (this.mSliceItem != null) && this.mRowItems.size() > 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0088, code lost:
    
        if (((!"slice".equals(r4.mFormat) || r4.hasAnyHints("actions", "keywords", "see_more") || androidx.slice.core.SliceQuery.find(r4, "text", (java.lang.String) null) == null) ? false : true) != false) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v4, types: [androidx.slice.core.SliceActionImpl] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void populate(Slice slice) {
        ArrayList arrayList;
        SliceActionImpl sliceActionImpl;
        if (slice == null) {
            return;
        }
        SliceItem find = SliceQuery.find(slice, "slice", "actions");
        SliceItem sliceItem = null;
        List findAll = find != null ? SliceQuery.findAll(find, "slice", new String[]{"actions", "shortcut"}, null) : null;
        if (findAll != null) {
            arrayList = new ArrayList(findAll.size());
            for (int i = 0; i < findAll.size(); i++) {
                arrayList.add(new SliceActionImpl((SliceItem) findAll.get(i)));
            }
        } else {
            arrayList = null;
        }
        this.mSliceActions = arrayList;
        SliceItem find2 = SliceQuery.find(slice, "slice", (String[]) null, new String[]{"list_item", "shortcut", "actions", "keywords", "ttl", "last_updated", "horizontal", "selection_option"});
        if (find2 != null) {
        }
        find2 = null;
        ArrayList arrayList2 = this.mRowItems;
        if (find2 != null) {
            RowContent rowContent = new RowContent(find2, 0);
            this.mHeaderContent = rowContent;
            arrayList2.add(rowContent);
        }
        SliceItem findTopLevelItem = SliceQuery.findTopLevelItem(slice, null, null, new String[]{"see_more"});
        if (findTopLevelItem != null && "slice".equals(findTopLevelItem.mFormat)) {
            List items = findTopLevelItem.getSlice().getItems();
            sliceItem = (items.size() == 1 && "action".equals(((SliceItem) items.get(0)).mFormat)) ? (SliceItem) items.get(0) : findTopLevelItem;
        }
        if (sliceItem != null) {
            this.mSeeMoreContent = new RowContent(sliceItem, -1);
        }
        List items2 = slice.getItems();
        for (int i2 = 0; i2 < items2.size(); i2++) {
            SliceItem sliceItem2 = (SliceItem) items2.get(i2);
            String str = sliceItem2.mFormat;
            if (!sliceItem2.hasAnyHints("actions", "see_more", "keywords", "ttl", "last_updated") && ("action".equals(str) || "slice".equals(str))) {
                if (this.mHeaderContent == null && !sliceItem2.hasHint("list_item")) {
                    RowContent rowContent2 = new RowContent(sliceItem2, 0);
                    this.mHeaderContent = rowContent2;
                    arrayList2.add(0, rowContent2);
                } else if (sliceItem2.hasHint("list_item")) {
                    if (sliceItem2.hasHint("horizontal")) {
                        arrayList2.add(new GridContent(sliceItem2, i2));
                    } else {
                        arrayList2.add(new RowContent(sliceItem2, i2));
                    }
                }
            }
        }
        if (this.mHeaderContent == null && arrayList2.size() >= 1) {
            RowContent rowContent3 = (RowContent) arrayList2.get(0);
            this.mHeaderContent = rowContent3;
            rowContent3.mIsHeader = true;
        }
        if (arrayList2.size() > 0 && (arrayList2.get(arrayList2.size() - 1) instanceof GridContent)) {
            ((GridContent) arrayList2.get(arrayList2.size() - 1)).mIsLastIndex = true;
        }
        RowContent rowContent4 = this.mHeaderContent;
        SliceItem sliceItem3 = rowContent4 != null ? rowContent4.mPrimaryAction : null;
        if (sliceItem3 == null) {
            sliceActionImpl = 0;
            sliceItem3 = SliceQuery.find(this.mSliceItem, "action", new String[]{"shortcut", UniversalCredentialUtil.AGENT_TITLE}, (String[]) null);
        } else {
            sliceActionImpl = 0;
        }
        if (sliceItem3 == null) {
            sliceItem3 = SliceQuery.find(this.mSliceItem, "action", (String) sliceActionImpl);
        }
        if (sliceItem3 != null) {
            sliceActionImpl = new SliceActionImpl(sliceItem3);
        }
        this.mPrimaryAction = sliceActionImpl;
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
