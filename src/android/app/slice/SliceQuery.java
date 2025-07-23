package android.app.slice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: classes.dex */
public class SliceQuery {
    private static final String TAG = "SliceQuery";

    static /* synthetic */ boolean lambda$contains$0(SliceItem sliceItem, SliceItem sliceItem2) {
        return sliceItem2 == sliceItem;
    }

    public static SliceItem getPrimaryIcon(Slice slice) {
        SliceItem find;
        for (SliceItem sliceItem : slice.getItems()) {
            if (Objects.equals(sliceItem.getFormat(), "image")) {
                return sliceItem;
            }
            if (!compareTypes(sliceItem, "slice") || !sliceItem.hasHint(Slice.HINT_LIST)) {
                if (!sliceItem.hasHint(Slice.HINT_ACTIONS) && !sliceItem.hasHint(Slice.HINT_LIST_ITEM) && !compareTypes(sliceItem, "action") && (find = find(sliceItem, "image")) != null) {
                    return find;
                }
            }
        }
        return null;
    }

    public static SliceItem findNotContaining(SliceItem sliceItem, List<SliceItem> list) {
        SliceItem sliceItem2 = null;
        while (sliceItem2 == null && list.size() != 0) {
            SliceItem remove = list.remove(0);
            if (!contains(sliceItem, remove)) {
                sliceItem2 = remove;
            }
        }
        return sliceItem2;
    }

    private static boolean contains(SliceItem sliceItem, final SliceItem sliceItem2) {
        if (sliceItem == null || sliceItem2 == null) {
            return false;
        }
        return stream(sliceItem).filter(new Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SliceQuery.lambda$contains$0(SliceItem.this, (SliceItem) obj);
            }
        }).findAny().isPresent();
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, String str) {
        return findAll(sliceItem, str, (String[]) null, (String[]) null);
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, String str, String str2, String str3) {
        return findAll(sliceItem, str, new String[]{str2}, new String[]{str3});
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, final String str, final String[] strArr, final String[] strArr2) {
        return (List) stream(sliceItem).filter(new Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SliceQuery.lambda$findAll$1(str, strArr, strArr2, (SliceItem) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$findAll$1(String str, String[] strArr, String[] strArr2, SliceItem sliceItem) {
        return compareTypes(sliceItem, str) && sliceItem.hasHints(strArr) && !sliceItem.hasAnyHints(strArr2);
    }

    public static SliceItem find(Slice slice, String str, String str2, String str3) {
        return find(slice, str, new String[]{str2}, new String[]{str3});
    }

    public static SliceItem find(Slice slice, String str) {
        return find(slice, str, (String[]) null, (String[]) null);
    }

    public static SliceItem find(SliceItem sliceItem, String str) {
        return find(sliceItem, str, (String[]) null, (String[]) null);
    }

    public static SliceItem find(SliceItem sliceItem, String str, String str2, String str3) {
        return find(sliceItem, str, new String[]{str2}, new String[]{str3});
    }

    public static SliceItem find(Slice slice, String str, String[] strArr, String[] strArr2) {
        List<String> hints = slice.getHints();
        return find(new SliceItem(slice, "slice", (String) null, (String[]) hints.toArray(new String[hints.size()])), str, strArr, strArr2);
    }

    public static SliceItem find(SliceItem sliceItem, final String str, final String[] strArr, final String[] strArr2) {
        return stream(sliceItem).filter(new Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SliceQuery.lambda$find$2(str, strArr, strArr2, (SliceItem) obj);
            }
        }).findFirst().orElse(null);
    }

    static /* synthetic */ boolean lambda$find$2(String str, String[] strArr, String[] strArr2, SliceItem sliceItem) {
        return compareTypes(sliceItem, str) && sliceItem.hasHints(strArr) && !sliceItem.hasAnyHints(strArr2);
    }

    public static Stream<SliceItem> stream(SliceItem sliceItem) {
        final LinkedList linkedList = new LinkedList();
        linkedList.add(sliceItem);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<SliceItem>() { // from class: android.app.slice.SliceQuery.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return linkedList.size() != 0;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public SliceItem next() {
                SliceItem sliceItem2 = (SliceItem) linkedList.poll();
                if (!SliceQuery.compareTypes(sliceItem2, "slice") && !SliceQuery.compareTypes(sliceItem2, "action")) {
                    return sliceItem2;
                }
                linkedList.addAll(sliceItem2.getSlice().getItems());
                return sliceItem2;
            }
        }, 0), false);
    }

    public static boolean compareTypes(SliceItem sliceItem, String str) {
        if (str.length() == 3 && str.equals("*/*")) {
            return true;
        }
        if (sliceItem.getSubType() == null && str.indexOf(47) < 0) {
            return sliceItem.getFormat().equals(str);
        }
        return (sliceItem.getFormat() + "/" + sliceItem.getSubType()).matches(str.replaceAll("\\*", ".*"));
    }
}
