package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public abstract class ComposeStackTraceBuilder {
    public final List trace = new ArrayList();

    public final void appendTraceFrame(GroupSourceInformation groupSourceInformation, Object obj) {
        ParsedSourceInformation parsedSourceInformation;
        String str;
        int i;
        String str2;
        int i2;
        boolean z;
        String str3;
        int[] intArray;
        String str4;
        String str5;
        int i3 = 0;
        String str6 = groupSourceInformation.sourceInformation;
        ComposeStackTraceFrame composeStackTraceFrame = null;
        if (str6 == null || str6.length() == 0) {
            parsedSourceInformation = null;
        } else {
            if (str6.charAt(0) == 'C') {
                i2 = str6.charAt(1) == 'C' ? 2 : 1;
                if (str6.charAt(i2) == '(') {
                    int i4 = i2 + 1;
                    int i5 = i4;
                    while (str6.charAt(i5) != ')') {
                        i5++;
                    }
                    String strSubstring = str6.substring(i4, i5);
                    int i6 = i5 + 1;
                    str5 = strSubstring;
                    i2 = i6;
                } else {
                    str5 = "<lambda>";
                }
                if (str6.charAt(i2) == 'P') {
                    do {
                        i2++;
                    } while (str6.charAt(i2) != ')');
                    i2++;
                }
                str3 = str5;
                z = true;
            } else {
                i2 = 0;
                z = false;
                str3 = null;
            }
            int i7 = i2;
            while (i7 < str6.length() && str6.charAt(i7) != ':') {
                i7++;
            }
            if (i2 < i7) {
                List<String> listSplit$default = StringsKt__StringsKt.split$default(str6.substring(i2, i7), new char[]{','}, 6);
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
                for (String strSubstring2 : listSplit$default) {
                    int iIndexOf$default = StringsKt__StringsKt.indexOf$default(strSubstring2, '@', 0, 6);
                    if (iIndexOf$default != -1) {
                        strSubstring2 = strSubstring2.substring(0, iIndexOf$default);
                    }
                    arrayList.add(Integer.valueOf(Integer.parseInt(StringsKt__StringsKt.substringAfter$default(strSubstring2, '*')) + 1));
                }
                intArray = CollectionsKt___CollectionsKt.toIntArray(arrayList);
            } else {
                intArray = ComposeStackTraceBuilderKt.EmptyIntArray;
            }
            int[] iArr = intArray;
            if (i7 < str6.length()) {
                int i8 = i7 + 1;
                int i9 = i8;
                while (str6.charAt(i9) != '#') {
                    i9++;
                }
                String strSubstring3 = str6.substring(i8, i9);
                i7 = i9;
                str4 = strSubstring3;
            } else {
                str4 = null;
            }
            parsedSourceInformation = new ParsedSourceInformation(z, str3, str4, i7 < str6.length() ? str6.substring(i7 + 1) : null, iArr, str6);
        }
        if (parsedSourceInformation != null) {
            if (obj == null) {
                composeStackTraceFrame = new ComposeStackTraceFrame(parsedSourceInformation, null);
            } else {
                ArrayList arrayList2 = groupSourceInformation.groups;
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    int i10 = 0;
                    for (int i11 = 0; i11 < size; i11++) {
                        Object obj2 = arrayList2.get(i11);
                        if (Intrinsics.areEqual(obj2, obj)) {
                            break;
                        }
                        GroupSourceInformation groupSourceInformationSourceInformationOf = sourceInformationOf(obj2);
                        if (groupSourceInformationSourceInformationOf != null && (((i = groupSourceInformationSourceInformationOf.key) == -127 || (i == 0 && (obj2 instanceof Anchor) && groupKeyOf((Anchor) obj2) == -127)) && groupSourceInformationSourceInformationOf.sourceInformation == null)) {
                            ArrayList arrayList3 = groupSourceInformationSourceInformationOf.groups;
                            if (arrayList3 != null) {
                                int size2 = arrayList3.size();
                                for (int i12 = 0; i12 < size2; i12++) {
                                    GroupSourceInformation groupSourceInformationSourceInformationOf2 = sourceInformationOf(arrayList3.get(i12));
                                    if (groupSourceInformationSourceInformationOf2 != null && (str2 = groupSourceInformationSourceInformationOf2.sourceInformation) != null && str2.startsWith(ImsProfile.TIMER_NAME_C)) {
                                        i10++;
                                    }
                                }
                            }
                        } else if (groupSourceInformationSourceInformationOf != null && (str = groupSourceInformationSourceInformationOf.sourceInformation) != null && str.startsWith(ImsProfile.TIMER_NAME_C)) {
                            i10++;
                        }
                    }
                    i3 = i10;
                }
                composeStackTraceFrame = new ComposeStackTraceFrame(parsedSourceInformation, Integer.valueOf(i3));
            }
        }
        if (composeStackTraceFrame != null) {
            ((ArrayList) this.trace).add(composeStackTraceFrame);
        }
    }

    public final boolean findInGroupSourceInformation(GroupSourceInformation groupSourceInformation, Object obj) {
        ArrayList arrayList = groupSourceInformation.groups;
        if (arrayList == null) {
            appendTraceFrame(groupSourceInformation, null);
            return true;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Object obj2 = arrayList.get(i);
            if (obj2 instanceof Anchor) {
                if (Intrinsics.areEqual(obj2, obj)) {
                    appendTraceFrame(groupSourceInformation, obj2);
                    return true;
                }
            } else {
                if (!(obj2 instanceof GroupSourceInformation)) {
                    throw new IllegalStateException(("Unexpected child source info " + obj2).toString());
                }
                if (findInGroupSourceInformation((GroupSourceInformation) obj2, obj)) {
                    appendTraceFrame(groupSourceInformation, obj2);
                    return true;
                }
            }
        }
        return false;
    }

    public abstract int groupKeyOf(Anchor anchor);

    public final void processEdge(GroupSourceInformation groupSourceInformation, Object obj) {
        if (groupSourceInformation != null) {
            if (obj == null) {
                appendTraceFrame(groupSourceInformation, null);
            } else {
                if (findInGroupSourceInformation(groupSourceInformation, obj)) {
                    return;
                }
                appendTraceFrame(groupSourceInformation, obj);
            }
        }
    }

    public abstract GroupSourceInformation sourceInformationOf(Anchor anchor);

    public final GroupSourceInformation sourceInformationOf(Object obj) {
        if (obj instanceof Anchor) {
            return sourceInformationOf((Anchor) obj);
        }
        if (obj instanceof GroupSourceInformation) {
            return (GroupSourceInformation) obj;
        }
        throw new IllegalStateException(("Unexpected child source info " + obj).toString());
    }
}
