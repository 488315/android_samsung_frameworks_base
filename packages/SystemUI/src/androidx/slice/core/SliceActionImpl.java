package androidx.slice.core;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* loaded from: classes.dex */
public class SliceActionImpl implements SliceAction {
    public final PendingIntent mAction;
    public final SliceItem mActionItem;
    public final String mActionKey;
    public final ActionType mActionType;
    public final CharSequence mContentDescription;
    public final long mDateTimeMillis;
    public final IconCompat mIcon;
    public final int mImageMode;
    public boolean mIsActivity;
    public final boolean mIsChecked;
    public final int mPriority;
    public final SliceItem mSliceItem;
    public final CharSequence mTitle;

    /* renamed from: androidx.slice.core.SliceActionImpl$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$slice$core$SliceActionImpl$ActionType;

        static {
            int[] iArr = new int[ActionType.values().length];
            $SwitchMap$androidx$slice$core$SliceActionImpl$ActionType = iArr;
            try {
                iArr[ActionType.TOGGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$slice$core$SliceActionImpl$ActionType[ActionType.DATE_PICKER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$slice$core$SliceActionImpl$ActionType[ActionType.TIME_PICKER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    enum ActionType {
        DEFAULT,
        TOGGLE,
        DATE_PICKER,
        TIME_PICKER
    }

    public SliceActionImpl(PendingIntent pendingIntent, IconCompat iconCompat, CharSequence charSequence) {
        this(pendingIntent, iconCompat, 0, charSequence);
    }

    public static int parseImageMode(SliceItem sliceItem) {
        if (ArrayUtils.contains(sliceItem.mHints, "show_label")) {
            return 6;
        }
        if (ArrayUtils.contains(sliceItem.mHints, "no_tint")) {
            return ArrayUtils.contains(sliceItem.mHints, "raw") ? ArrayUtils.contains(sliceItem.mHints, "large") ? 4 : 3 : ArrayUtils.contains(sliceItem.mHints, "large") ? 2 : 1;
        }
        return 0;
    }

    public final Slice.Builder buildSliceContent(Slice.Builder builder) {
        Slice.Builder builder2 = new Slice.Builder(builder);
        IconCompat iconCompat = this.mIcon;
        if (iconCompat != null) {
            int i = this.mImageMode;
            builder2.addIcon(iconCompat, null, i == 6 ? new String[]{"show_label"} : i == 0 ? new String[0] : new String[]{"no_tint"});
        }
        CharSequence charSequence = this.mTitle;
        if (charSequence != null) {
            builder2.addText(charSequence, null, UniversalCredentialUtil.AGENT_TITLE);
        }
        CharSequence charSequence2 = this.mContentDescription;
        if (charSequence2 != null) {
            builder2.addText(charSequence2, "content_description", new String[0]);
        }
        long j = this.mDateTimeMillis;
        if (j != -1) {
            builder2.mItems.add(new SliceItem(Long.valueOf(j), "long", "millis", new String[0]));
        }
        if (this.mActionType == ActionType.TOGGLE && this.mIsChecked) {
            builder2.addHints("selected");
        }
        int i2 = this.mPriority;
        if (i2 != -1) {
            builder2.addInt(i2, SystemUIAnalytics.QPNE_VID_PRIORITY, new String[0]);
        }
        String str = this.mActionKey;
        if (str != null) {
            builder2.addText(str, "action_key", new String[0]);
        }
        if (this.mIsActivity) {
            builder.addHints("activity");
        }
        return builder2;
    }

    @Override // androidx.slice.core.SliceAction
    public final int getPriority() {
        return this.mPriority;
    }

    public final String getSubtype() {
        int i = AnonymousClass1.$SwitchMap$androidx$slice$core$SliceActionImpl$ActionType[this.mActionType.ordinal()];
        if (i == 1) {
            return "toggle";
        }
        if (i == 2) {
            return "date_picker";
        }
        if (i != 3) {
            return null;
        }
        return "time_picker";
    }

    public final boolean isDefaultToggle() {
        return this.mActionType == ActionType.TOGGLE && this.mIcon == null;
    }

    @Override // androidx.slice.core.SliceAction
    public final boolean isToggle() {
        return this.mActionType == ActionType.TOGGLE;
    }

    public SliceActionImpl(PendingIntent pendingIntent, CharSequence charSequence, long j, boolean z) {
        this.mImageMode = 5;
        this.mActionType = ActionType.DEFAULT;
        this.mPriority = -1;
        this.mDateTimeMillis = -1L;
        this.mAction = pendingIntent;
        this.mTitle = charSequence;
        this.mActionType = z ? ActionType.DATE_PICKER : ActionType.TIME_PICKER;
        this.mDateTimeMillis = j;
    }

    public SliceActionImpl(PendingIntent pendingIntent, IconCompat iconCompat, int i, CharSequence charSequence) {
        this.mImageMode = 5;
        this.mActionType = ActionType.DEFAULT;
        this.mPriority = -1;
        this.mDateTimeMillis = -1L;
        this.mAction = pendingIntent;
        this.mIcon = iconCompat;
        this.mTitle = charSequence;
        this.mImageMode = i;
    }

    public SliceActionImpl(PendingIntent pendingIntent, IconCompat iconCompat, CharSequence charSequence, boolean z) {
        this(pendingIntent, iconCompat, 0, charSequence);
        this.mIsChecked = z;
        this.mActionType = ActionType.TOGGLE;
    }

    public SliceActionImpl(PendingIntent pendingIntent, CharSequence charSequence, boolean z) {
        this.mImageMode = 5;
        this.mActionType = ActionType.DEFAULT;
        this.mPriority = -1;
        this.mDateTimeMillis = -1L;
        this.mAction = pendingIntent;
        this.mTitle = charSequence;
        this.mActionType = ActionType.TOGGLE;
        this.mIsChecked = z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SliceActionImpl(SliceItem sliceItem) {
        this.mImageMode = 5;
        ActionType actionType = ActionType.DEFAULT;
        this.mActionType = actionType;
        this.mPriority = -1;
        this.mDateTimeMillis = -1L;
        this.mSliceItem = sliceItem;
        SliceItem sliceItemFind = SliceQuery.find(sliceItem, "action", (String[]) null, (String[]) null);
        if (sliceItemFind == null) {
            return;
        }
        this.mActionItem = sliceItemFind;
        this.mAction = sliceItemFind.getAction();
        SliceItem sliceItemFind2 = SliceQuery.find(sliceItemFind.getSlice(), "image", (String[]) null, (String[]) null);
        if (sliceItemFind2 != null) {
            this.mIcon = (IconCompat) sliceItemFind2.mObj;
            this.mImageMode = parseImageMode(sliceItemFind2);
        }
        SliceItem sliceItemFind3 = SliceQuery.find(sliceItemFind.getSlice(), "text", UniversalCredentialUtil.AGENT_TITLE);
        if (sliceItemFind3 != null) {
            this.mTitle = sliceItemFind3.getSanitizedText();
        }
        SliceItem sliceItemFindSubtype = SliceQuery.findSubtype(sliceItemFind.getSlice(), "text", "content_description");
        if (sliceItemFindSubtype != null) {
            this.mContentDescription = (CharSequence) sliceItemFindSubtype.mObj;
        }
        String str = sliceItemFind.mSubType;
        if (str == null) {
            this.mActionType = actionType;
        } else {
            switch (str) {
                case "toggle":
                    this.mActionType = ActionType.TOGGLE;
                    this.mIsChecked = ArrayUtils.contains(sliceItemFind.mHints, "selected");
                    break;
                case "time_picker":
                    this.mActionType = ActionType.TIME_PICKER;
                    SliceItem sliceItemFindSubtype2 = SliceQuery.findSubtype(sliceItemFind, "long", "millis");
                    if (sliceItemFindSubtype2 != null) {
                        this.mDateTimeMillis = sliceItemFindSubtype2.getLong();
                        break;
                    }
                    break;
                case "date_picker":
                    this.mActionType = ActionType.DATE_PICKER;
                    SliceItem sliceItemFindSubtype3 = SliceQuery.findSubtype(sliceItemFind, "long", "millis");
                    if (sliceItemFindSubtype3 != null) {
                        this.mDateTimeMillis = sliceItemFindSubtype3.getLong();
                        break;
                    }
                    break;
                default:
                    this.mActionType = actionType;
                    break;
            }
        }
        this.mIsActivity = ArrayUtils.contains(sliceItem.mHints, "activity");
        SliceItem sliceItemFindSubtype4 = SliceQuery.findSubtype(sliceItemFind.getSlice(), "int", SystemUIAnalytics.QPNE_VID_PRIORITY);
        this.mPriority = sliceItemFindSubtype4 != null ? sliceItemFindSubtype4.getInt() : -1;
        SliceItem sliceItemFindSubtype5 = SliceQuery.findSubtype(sliceItemFind.getSlice(), "text", "action_key");
        if (sliceItemFindSubtype5 != null) {
            this.mActionKey = ((CharSequence) sliceItemFindSubtype5.mObj).toString();
        }
    }
}
