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
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Pair;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceItem extends CustomVersionedParcelable {
    public String mFormat;
    public String[] mHints;
    public SliceItemHolder mHolder;
    public Object mObj;
    public CharSequence mSanitizedText;
    public String mSubType;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            Object obj2 = (obj instanceof AlignmentSpan) || (obj instanceof ForegroundColorSpan) || (obj instanceof RelativeSizeSpan) || (obj instanceof StyleSpan) ? obj : null;
            if (obj2 != obj) {
                if (obj2 != null) {
                    spannable.setSpan(obj2, spannable.getSpanStart(obj), spannable.getSpanEnd(obj), spannable.getSpanFlags(obj));
                }
                spannable.removeSpan(obj);
            }
        }
    }

    public final void addHint() {
        Object[] objArr;
        String[] strArr = this.mHints;
        int i = 0;
        if (strArr != null) {
            int length = strArr.length;
            objArr = (Object[]) Array.newInstance((Class<?>) String.class, length + 1);
            System.arraycopy(strArr, 0, objArr, 0, length);
            i = length;
        } else {
            objArr = (Object[]) Array.newInstance((Class<?>) String.class, 1);
        }
        objArr[i] = "partial";
        this.mHints = (String[]) objArr;
    }

    public final void fireActionInternal(Context context, Intent intent) {
        Object obj = ((Pair) this.mObj).first;
        if (obj instanceof PendingIntent) {
            ((PendingIntent) obj).send(context, 0, intent, null, null);
        } else {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
            throw null;
        }
    }

    public final PendingIntent getAction() {
        Object obj = ((Pair) this.mObj).first;
        if (obj instanceof PendingIntent) {
            return (PendingIntent) obj;
        }
        return null;
    }

    public final List getHints() {
        return Arrays.asList(this.mHints);
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
                boolean z = false;
                Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                int length = spans.length;
                int i = 0;
                while (true) {
                    boolean z2 = true;
                    if (i >= length) {
                        z = true;
                        break;
                    }
                    Object obj = spans[i];
                    if (!(obj instanceof AlignmentSpan) && !(obj instanceof ForegroundColorSpan) && !(obj instanceof RelativeSizeSpan) && !(obj instanceof StyleSpan)) {
                        z2 = false;
                    }
                    if (!z2) {
                        break;
                    }
                    i++;
                }
                if (!z) {
                    SpannableString spannableString = new SpannableString(charSequence);
                    fixSpannableText(spannableString);
                    charSequence = spannableString;
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

    public final boolean hasHint(String str) {
        return ArrayUtils.contains(this.mHints, str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01dc, code lost:
    
        if (r14.equals("long") == false) goto L97;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String toString(String str) {
        char c;
        String str2;
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(this.mFormat);
        if (this.mSubType != null) {
            m18m.append('<');
            m18m.append(this.mSubType);
            m18m.append('>');
        }
        m18m.append(' ');
        String[] strArr = this.mHints;
        if (strArr.length > 0) {
            Slice.appendHints(m18m, strArr);
            m18m.append(' ');
        }
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        String str3 = this.mFormat;
        str3.getClass();
        char c2 = 2;
        switch (str3.hashCode()) {
            case -1422950858:
                if (str3.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 104431:
                if (str3.equals("int")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3327612:
                if (str3.equals("long")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3556653:
                if (str3.equals("text")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 100313435:
                if (str3.equals("image")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 109526418:
                if (str3.equals("slice")) {
                    c = 5;
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
                Object obj = ((Pair) this.mObj).first;
                m18m.append('[');
                m18m.append(obj);
                m18m.append("] ");
                m18m.append("{\n");
                m18m.append(getSlice().toString(m14m));
                m18m.append('\n');
                m18m.append(str);
                m18m.append('}');
                break;
            case 1:
                if (!"color".equals(this.mSubType)) {
                    if (!"layout_direction".equals(this.mSubType)) {
                        m18m.append(getInt());
                        break;
                    } else {
                        int i = getInt();
                        m18m.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? Integer.toString(i) : "LOCALE" : "INHERIT" : "RTL" : "LTR");
                        break;
                    }
                } else {
                    int i2 = getInt();
                    m18m.append(String.format("a=0x%02x r=0x%02x g=0x%02x b=0x%02x", Integer.valueOf(Color.alpha(i2)), Integer.valueOf(Color.red(i2)), Integer.valueOf(Color.green(i2)), Integer.valueOf(Color.blue(i2))));
                    break;
                }
            case 2:
                if (!"millis".equals(this.mSubType)) {
                    m18m.append(getLong());
                    m18m.append('L');
                    break;
                } else if (getLong() != -1) {
                    m18m.append(DateUtils.getRelativeTimeSpanString(getLong(), Calendar.getInstance().getTimeInMillis(), 1000L, 262144));
                    break;
                } else {
                    m18m.append("INFINITY");
                    break;
                }
            case 3:
                m18m.append('\"');
                m18m.append((CharSequence) this.mObj);
                m18m.append('\"');
                break;
            case 4:
                m18m.append((IconCompat) this.mObj);
                break;
            case 5:
                m18m.append("{\n");
                m18m.append(getSlice().toString(m14m));
                m18m.append('\n');
                m18m.append(str);
                m18m.append('}');
                break;
            default:
                String str4 = this.mFormat;
                str4.getClass();
                switch (str4.hashCode()) {
                    case -1422950858:
                        if (str4.equals("action")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 104431:
                        if (str4.equals("int")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 3327612:
                        break;
                    case 3556653:
                        if (str4.equals("text")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 100313435:
                        if (str4.equals("image")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 100358090:
                        if (str4.equals("input")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 109526418:
                        if (str4.equals("slice")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        str2 = "Action";
                        break;
                    case 1:
                        str2 = "Int";
                        break;
                    case 2:
                        str2 = "Long";
                        break;
                    case 3:
                        str2 = "Text";
                        break;
                    case 4:
                        str2 = "Image";
                        break;
                    case 5:
                        str2 = "RemoteInput";
                        break;
                    case 6:
                        str2 = "Slice";
                        break;
                    default:
                        str2 = "Unrecognized format: ".concat(str4);
                        break;
                }
                m18m.append(str2);
                break;
        }
        m18m.append("\n");
        return m18m.toString();
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public SliceItem(Bundle bundle) {
        char c;
        Object pair;
        this.mHints = Slice.NO_HINTS;
        this.mFormat = "text";
        this.mSubType = null;
        this.mHints = bundle.getStringArray("hints");
        this.mFormat = bundle.getString("format");
        this.mSubType = bundle.getString("subtype");
        String str = this.mFormat;
        str.getClass();
        switch (str.hashCode()) {
            case -1422950858:
                if (str.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1377881982:
                if (str.equals("bundle")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 104431:
                if (str.equals("int")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3327612:
                if (str.equals("long")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3556653:
                if (str.equals("text")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 100313435:
                if (str.equals("image")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 100358090:
                if (str.equals("input")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 109526418:
                if (str.equals("slice")) {
                    c = 7;
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
                pair = new Pair(bundle.getParcelable("obj"), new Slice(bundle.getBundle("obj_2")));
                break;
            case 1:
                pair = bundle.getBundle("obj");
                break;
            case 2:
                pair = Integer.valueOf(bundle.getInt("obj"));
                break;
            case 3:
                pair = Long.valueOf(bundle.getLong("obj"));
                break;
            case 4:
                pair = bundle.getCharSequence("obj");
                break;
            case 5:
                pair = IconCompat.createFromBundle(bundle.getBundle("obj"));
                break;
            case 6:
                pair = bundle.getParcelable("obj");
                break;
            case 7:
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
