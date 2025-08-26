package androidx.slice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Pair;
import androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticOutline0;
import androidx.versionedparcelable.CustomVersionedParcelable;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes.dex */
public final class SliceItem extends CustomVersionedParcelable {
    public String mFormat;
    public String[] mHints;
    public SliceItemHolder mHolder;
    public Object mObj;
    public CharSequence mSanitizedText;
    public String mSubType;

    public interface ActionHandler {
    }

    public SliceItem(Object obj, String str, String str2, String[] strArr) {
        this.mHints = strArr;
        this.mFormat = str;
        this.mSubType = str2;
        this.mObj = obj;
    }

    public static void fixSpannableText(Spannable spannable) {
        for (Object obj : spannable.getSpans(0, spannable.length(), Object.class)) {
            Object obj2 = ((obj instanceof AlignmentSpan) || (obj instanceof ForegroundColorSpan) || (obj instanceof RelativeSizeSpan) || (obj instanceof StyleSpan)) ? obj : null;
            if (obj2 != obj) {
                if (obj2 != null) {
                    spannable.setSpan(obj2, spannable.getSpanStart(obj), spannable.getSpanEnd(obj), spannable.getSpanFlags(obj));
                }
                spannable.removeSpan(obj);
            }
        }
    }

    public final void fireActionInternal(Context context, Intent intent) {
        Object obj = ((Pair) this.mObj).first;
        if (!(obj instanceof PendingIntent)) {
            throw MediaControllerImplBase$$ExternalSyntheticOutline0.m(obj);
        }
        ((PendingIntent) obj).send(context, 0, intent, null, null);
    }

    public final PendingIntent getAction() {
        Object obj = ((Pair) this.mObj).first;
        if (obj instanceof PendingIntent) {
            return (PendingIntent) obj;
        }
        return null;
    }

    public final int getInt() {
        return ((Integer) this.mObj).intValue();
    }

    public final long getLong() {
        return ((Long) this.mObj).longValue();
    }

    public final CharSequence getSanitizedText() {
        if (this.mSanitizedText == null) {
            CharSequence charSequence = (CharSequence) this.mObj;
            if (charSequence instanceof Spannable) {
                fixSpannableText((Spannable) charSequence);
            } else if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned) charSequence;
                int i = 0;
                Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                int length = spans.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Object obj = spans[i];
                    if (!(obj instanceof AlignmentSpan) && !(obj instanceof ForegroundColorSpan) && !(obj instanceof RelativeSizeSpan) && !(obj instanceof StyleSpan)) {
                        SpannableString spannableString = new SpannableString(charSequence);
                        fixSpannableText(spannableString);
                        charSequence = spannableString;
                        break;
                    }
                    i++;
                }
            }
            this.mSanitizedText = charSequence;
        }
        return this.mSanitizedText;
    }

    public final Slice getSlice() {
        return "action".equals(this.mFormat) ? (Slice) ((Pair) this.mObj).second : (Slice) this.mObj;
    }

    public final boolean hasAnyHints(String... strArr) {
        for (String str : strArr) {
            if (ArrayUtils.contains(this.mHints, str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String toString(String str) {
        StringBuilder sbM;
        String strM;
        String str2;
        String strConcat;
        sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str);
        sbM.append(this.mFormat);
        if (this.mSubType != null) {
            sbM.append('<');
            sbM.append(this.mSubType);
            sbM.append('>');
        }
        sbM.append(' ');
        String[] strArr = this.mHints;
        if (strArr.length > 0) {
            Slice.appendHints(sbM, strArr);
            sbM.append(' ');
        }
        strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        String str3 = this.mFormat;
        str3.getClass();
        switch (str3) {
            case "action":
                Object obj = ((Pair) this.mObj).first;
                sbM.append('[');
                sbM.append(obj);
                sbM.append("] ");
                sbM.append("{\n");
                sbM.append(getSlice().toString(strM));
                sbM.append('\n');
                sbM.append(str);
                sbM.append('}');
                break;
            case "int":
                if (!"color".equals(this.mSubType)) {
                    if (!"layout_direction".equals(this.mSubType)) {
                        sbM.append(getInt());
                        break;
                    } else {
                        int i = getInt();
                        sbM.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? Integer.toString(i) : "LOCALE" : "INHERIT" : "RTL" : "LTR");
                        break;
                    }
                } else {
                    int i2 = getInt();
                    sbM.append(String.format("a=0x%02x r=0x%02x g=0x%02x b=0x%02x", Integer.valueOf(Color.alpha(i2)), Integer.valueOf(Color.red(i2)), Integer.valueOf(Color.green(i2)), Integer.valueOf(Color.blue(i2))));
                    break;
                }
            case "long":
                if (!"millis".equals(this.mSubType)) {
                    sbM.append(getLong());
                    sbM.append('L');
                    break;
                } else if (getLong() != -1) {
                    sbM.append(DateUtils.getRelativeTimeSpanString(getLong(), Calendar.getInstance().getTimeInMillis(), 1000L, 262144));
                    break;
                } else {
                    sbM.append("INFINITY");
                    break;
                }
            case "text":
                sbM.append('\"');
                sbM.append((CharSequence) this.mObj);
                sbM.append('\"');
                break;
            case "image":
                sbM.append((IconCompat) this.mObj);
                break;
            case "slice":
                sbM.append("{\n");
                sbM.append(getSlice().toString(strM));
                sbM.append('\n');
                sbM.append(str);
                sbM.append('}');
                break;
            default:
                str2 = this.mFormat;
                str2.getClass();
                switch (str2) {
                    case "action":
                        strConcat = "Action";
                        break;
                    case "int":
                        strConcat = "Int";
                        break;
                    case "long":
                        strConcat = "Long";
                        break;
                    case "text":
                        strConcat = "Text";
                        break;
                    case "image":
                        strConcat = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_IMAGE;
                        break;
                    case "input":
                        strConcat = "RemoteInput";
                        break;
                    case "slice":
                        strConcat = "Slice";
                        break;
                    default:
                        strConcat = "Unrecognized format: ".concat(str2);
                        break;
                }
                sbM.append(strConcat);
                break;
        }
        sbM.append("\n");
        return sbM.toString();
    }

    public SliceItem(Object obj, String str, String str2, List<String> list) {
        this(obj, str, str2, (String[]) list.toArray(new String[list.size()]));
    }

    public SliceItem() {
        this.mHints = Slice.NO_HINTS;
        this.mFormat = "text";
        this.mSubType = null;
    }

    public SliceItem(PendingIntent pendingIntent, Slice slice, String str, String str2, String[] strArr) {
        this(new Pair(pendingIntent, slice), str, str2, strArr);
    }

    public SliceItem(ActionHandler actionHandler, Slice slice, String str, String str2, String[] strArr) {
        this(new Pair(actionHandler, slice), str, str2, strArr);
    }

    public SliceItem(Bundle bundle) {
        String str;
        Object pair;
        this.mHints = Slice.NO_HINTS;
        this.mFormat = "text";
        this.mSubType = null;
        this.mHints = bundle.getStringArray("hints");
        this.mFormat = bundle.getString("format");
        this.mSubType = bundle.getString("subtype");
        str = this.mFormat;
        str.getClass();
        switch (str) {
            case "action":
                pair = new Pair(bundle.getParcelable("obj"), new Slice(bundle.getBundle("obj_2")));
                break;
            case "bundle":
                pair = bundle.getBundle("obj");
                break;
            case "int":
                pair = Integer.valueOf(bundle.getInt("obj"));
                break;
            case "long":
                pair = Long.valueOf(bundle.getLong("obj"));
                break;
            case "text":
                pair = bundle.getCharSequence("obj");
                break;
            case "image":
                pair = IconCompat.createFromBundle(bundle.getBundle("obj"));
                break;
            case "input":
                pair = bundle.getParcelable("obj");
                break;
            case "slice":
                pair = new Slice(bundle.getBundle("obj"));
                break;
            default:
                throw new RuntimeException("Unsupported type ".concat(str));
        }
        this.mObj = pair;
    }

    public final String toString() {
        return toString("");
    }
}
