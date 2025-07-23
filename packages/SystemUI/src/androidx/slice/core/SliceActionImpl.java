package androidx.slice.core;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public SliceActionImpl(SliceItem sliceItem) {
        char c;
        this.mImageMode = 5;
        ActionType actionType = ActionType.DEFAULT;
        this.mActionType = actionType;
        this.mPriority = -1;
        this.mDateTimeMillis = -1L;
        this.mSliceItem = sliceItem;
        SliceItem find = SliceQuery.find(sliceItem, "action", (String[]) null, (String[]) null);
        if (find == null) {
            return;
        }
        this.mActionItem = find;
        this.mAction = find.getAction();
        SliceItem find2 = SliceQuery.find(find.getSlice(), "image", (String[]) null, (String[]) null);
        if (find2 != null) {
            this.mIcon = (IconCompat) find2.mObj;
            this.mImageMode = parseImageMode(find2);
        }
        SliceItem find3 = SliceQuery.find(find.getSlice(), "text", UniversalCredentialUtil.AGENT_TITLE);
        if (find3 != null) {
            this.mTitle = find3.getSanitizedText();
        }
        SliceItem findSubtype = SliceQuery.findSubtype(find.getSlice(), "text", "content_description");
        if (findSubtype != null) {
            this.mContentDescription = (CharSequence) findSubtype.mObj;
        }
        String str = find.mSubType;
        if (str == null) {
            this.mActionType = actionType;
        } else {
            switch (str.hashCode()) {
                case -868304044:
                    if (str.equals("toggle")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 759128640:
                    if (str.equals("time_picker")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1250407999:
                    if (str.equals("date_picker")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mActionType = ActionType.TOGGLE;
                    this.mIsChecked = ArrayUtils.contains(find.mHints, "selected");
                    break;
                case 1:
                    this.mActionType = ActionType.TIME_PICKER;
                    SliceItem findSubtype2 = SliceQuery.findSubtype(find, "long", "millis");
                    if (findSubtype2 != null) {
                        this.mDateTimeMillis = findSubtype2.getLong();
                        break;
                    }
                    break;
                case 2:
                    this.mActionType = ActionType.DATE_PICKER;
                    SliceItem findSubtype3 = SliceQuery.findSubtype(find, "long", "millis");
                    if (findSubtype3 != null) {
                        this.mDateTimeMillis = findSubtype3.getLong();
                        break;
                    }
                    break;
                default:
                    this.mActionType = actionType;
                    break;
            }
        }
        this.mIsActivity = ArrayUtils.contains(sliceItem.mHints, "activity");
        SliceItem findSubtype4 = SliceQuery.findSubtype(find.getSlice(), "int", SystemUIAnalytics.QPNE_VID_PRIORITY);
        this.mPriority = findSubtype4 != null ? findSubtype4.getInt() : -1;
        SliceItem findSubtype5 = SliceQuery.findSubtype(find.getSlice(), "text", "action_key");
        if (findSubtype5 != null) {
            this.mActionKey = ((CharSequence) findSubtype5.mObj).toString();
        }
    }
}
