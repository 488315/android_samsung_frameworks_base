package androidx.compose.runtime;

import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GroupSourceInformation {
    public ArrayList groups;
    public final int key;
    public final String sourceInformation;

    public GroupSourceInformation(int i, String str, int i2) {
        this.key = i;
        this.sourceInformation = str;
    }

    public final boolean hasAnchor(Anchor anchor) {
        ArrayList arrayList = this.groups;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Object obj = arrayList.get(i);
                if (Intrinsics.areEqual(obj, anchor)) {
                    return true;
                }
                if ((obj instanceof GroupSourceInformation) && ((GroupSourceInformation) obj).hasAnchor(anchor)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final GroupSourceInformation openInformation() {
        Object obj;
        ArrayList arrayList = this.groups;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Object obj2 = arrayList.get(size);
                if (obj2 instanceof GroupSourceInformation) {
                    ((GroupSourceInformation) obj2).getClass();
                    obj = obj2;
                    break;
                }
            }
        }
        obj = null;
        GroupSourceInformation groupSourceInformation = obj instanceof GroupSourceInformation ? (GroupSourceInformation) obj : null;
        return groupSourceInformation != null ? groupSourceInformation.openInformation() : this;
    }

    public final boolean removeAnchor(Anchor anchor) {
        ArrayList arrayList = this.groups;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Object obj = arrayList.get(size);
                if (obj instanceof Anchor) {
                    if (Intrinsics.areEqual(obj, anchor)) {
                        arrayList.remove(size);
                    }
                } else if ((obj instanceof GroupSourceInformation) && !((GroupSourceInformation) obj).removeAnchor(anchor)) {
                    arrayList.remove(size);
                }
            }
            if (arrayList.isEmpty()) {
                this.groups = null;
                return false;
            }
        }
        return true;
    }
}
