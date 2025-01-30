package androidx.slice.core;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceActionImpl implements SliceAction {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.slice.core.SliceActionImpl$1 */
    public abstract /* synthetic */ class AbstractC04951 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        if (sliceItem.hasHint("show_label")) {
            return 6;
        }
        if (sliceItem.hasHint("no_tint")) {
            return sliceItem.hasHint("raw") ? sliceItem.hasHint("large") ? 4 : 3 : sliceItem.hasHint("large") ? 2 : 1;
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
            builder2.addLong(j, "millis", new String[0]);
        }
        if (this.mActionType == ActionType.TOGGLE && this.mIsChecked) {
            builder2.addHints("selected");
        }
        int i2 = this.mPriority;
        if (i2 != -1) {
            builder2.addInt(i2, "priority", new String[0]);
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
        int i = AbstractC04951.$SwitchMap$androidx$slice$core$SliceActionImpl$ActionType[this.mActionType.ordinal()];
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            int hashCode = str.hashCode();
            if (hashCode == -868304044) {
                if (str.equals("toggle")) {
                    c = 0;
                    if (c != 0) {
                    }
                }
                c = 65535;
                if (c != 0) {
                }
            } else if (hashCode != 759128640) {
                if (hashCode == 1250407999 && str.equals("date_picker")) {
                    c = 2;
                    if (c != 0) {
                        this.mActionType = ActionType.TOGGLE;
                        this.mIsChecked = find.hasHint("selected");
                    } else if (c == 1) {
                        this.mActionType = ActionType.TIME_PICKER;
                        SliceItem findSubtype2 = SliceQuery.findSubtype(find, "long", "millis");
                        if (findSubtype2 != null) {
                            this.mDateTimeMillis = findSubtype2.getLong();
                        }
                    } else if (c != 2) {
                        this.mActionType = actionType;
                    } else {
                        this.mActionType = ActionType.DATE_PICKER;
                        SliceItem findSubtype3 = SliceQuery.findSubtype(find, "long", "millis");
                        if (findSubtype3 != null) {
                            this.mDateTimeMillis = findSubtype3.getLong();
                        }
                    }
                }
                c = 65535;
                if (c != 0) {
                }
            } else {
                if (str.equals("time_picker")) {
                    c = 1;
                    if (c != 0) {
                    }
                }
                c = 65535;
                if (c != 0) {
                }
            }
        }
        this.mIsActivity = sliceItem.hasHint("activity");
        SliceItem findSubtype4 = SliceQuery.findSubtype(find.getSlice(), "int", "priority");
        this.mPriority = findSubtype4 != null ? findSubtype4.getInt() : -1;
        SliceItem findSubtype5 = SliceQuery.findSubtype(find.getSlice(), "text", "action_key");
        if (findSubtype5 != null) {
            this.mActionKey = ((CharSequence) findSubtype5.mObj).toString();
        }
    }
}
