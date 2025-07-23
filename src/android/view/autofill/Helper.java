package android.view.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/* loaded from: classes4.dex */
public final class Helper {
    public static boolean sDebug = false;
    public static boolean sVerbose = false;

    public static void appendRedacted(StringBuilder sb, CharSequence charSequence) {
        sb.append(getRedacted(charSequence));
    }

    public static String getRedacted(CharSequence charSequence) {
        if (charSequence == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return charSequence.length() + "_chars";
    }

    public static void appendRedacted(StringBuilder sb, String[] strArr) {
        if (strArr == null) {
            sb.append("N/A");
            return;
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        for (String str : strArr) {
            sb.append(" '");
            appendRedacted(sb, str);
            sb.append("'");
        }
        sb.append(" ]");
    }

    public static AutofillId[] toArray(Collection<AutofillId> collection) {
        if (collection == null) {
            return new AutofillId[0];
        }
        AutofillId[] autofillIdArr = new AutofillId[collection.size()];
        collection.toArray(autofillIdArr);
        return autofillIdArr;
    }

    public static <T> ArrayList<T> toList(Set<T> set) {
        if (set == null) {
            return null;
        }
        return new ArrayList<>(set);
    }

    private Helper() {
        throw new UnsupportedOperationException("contains static members only");
    }
}
