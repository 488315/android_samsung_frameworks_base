package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt__GroupingJVMKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class FaceHelpMessageDebouncer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String TAG;
    public final List helpFaceAuthStatuses;
    public Integer lastMessageIdShown;
    public final int shownFaceMessageFrequencyBoost;
    public long startTime;
    public final long startWindow;
    public final float threshold;
    public final long window;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FaceHelpMessageDebouncer() {
        this(0L, 0L, 0, 0.0f, 15, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final HelpFaceAuthenticationStatus getMessageToShow(long j) {
        Object next;
        int iIntValue;
        Object objPrevious;
        HelpFaceAuthenticationStatus helpFaceAuthenticationStatus;
        if (((ArrayList) this.helpFaceAuthStatuses).isEmpty() || j - this.startTime < this.startWindow) {
            return null;
        }
        int size = ((ArrayList) this.helpFaceAuthStatuses).size();
        int i = 0;
        for (int i2 = 0; i2 < size && ((HelpFaceAuthenticationStatus) ((ArrayList) this.helpFaceAuthStatuses).get(i2)).createdAt + this.window < j; i2++) {
            i++;
        }
        for (int i3 = 0; i3 < i; i3++) {
            ((ArrayList) this.helpFaceAuthStatuses).removeFirst();
        }
        final List list = this.helpFaceAuthStatuses;
        LinkedHashMap linkedHashMap = new LinkedHashMap(GroupingKt__GroupingJVMKt.eachCount(new Grouping() { // from class: com.android.systemui.biometrics.FaceHelpMessageDebouncer$getMostFrequentHelpMessageSurpassingThreshold$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public final Object keyOf(Object obj) {
                return Integer.valueOf(((HelpFaceAuthenticationStatus) obj).msgId);
            }

            @Override // kotlin.collections.Grouping
            public final Iterator sourceIterator() {
                return list.iterator();
            }
        }));
        Integer num = this.lastMessageIdShown;
        if (num != null) {
            final int i4 = 0;
            final Function2 function2 = new Function2(this) { // from class: com.android.systemui.biometrics.FaceHelpMessageDebouncer$$ExternalSyntheticLambda0
                public final /* synthetic */ FaceHelpMessageDebouncer f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int iCompare;
                    Object obj3;
                    Object objPrevious2;
                    FaceHelpMessageDebouncer faceHelpMessageDebouncer = this.f$0;
                    switch (i4) {
                        case 0:
                            int i5 = FaceHelpMessageDebouncer.$r8$clinit;
                            return Integer.valueOf(((Integer) obj2).intValue() + faceHelpMessageDebouncer.shownFaceMessageFrequencyBoost);
                        default:
                            Map.Entry entry = (Map.Entry) obj;
                            Map.Entry entry2 = (Map.Entry) obj2;
                            int i6 = FaceHelpMessageDebouncer.$r8$clinit;
                            entry.getClass();
                            int iIntValue2 = ((Number) entry.getKey()).intValue();
                            int iIntValue3 = ((Number) entry.getValue()).intValue();
                            entry2.getClass();
                            int iIntValue4 = ((Number) entry2.getKey()).intValue();
                            int iIntValue5 = ((Number) entry2.getValue()).intValue();
                            if (iIntValue3 == iIntValue5) {
                                ArrayList arrayList = (ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses;
                                ListIterator listIterator = arrayList.listIterator(arrayList.size());
                                while (true) {
                                    obj3 = null;
                                    if (listIterator.hasPrevious()) {
                                        objPrevious2 = listIterator.previous();
                                        if (((HelpFaceAuthenticationStatus) objPrevious2).msgId == iIntValue2) {
                                        }
                                    } else {
                                        objPrevious2 = null;
                                    }
                                }
                                objPrevious2.getClass();
                                HelpFaceAuthenticationStatus helpFaceAuthenticationStatus2 = (HelpFaceAuthenticationStatus) objPrevious2;
                                ArrayList arrayList2 = (ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses;
                                ListIterator listIterator2 = arrayList2.listIterator(arrayList2.size());
                                while (true) {
                                    if (listIterator2.hasPrevious()) {
                                        Object objPrevious3 = listIterator2.previous();
                                        if (((HelpFaceAuthenticationStatus) objPrevious3).msgId == iIntValue4) {
                                            obj3 = objPrevious3;
                                        }
                                    }
                                }
                                obj3.getClass();
                                long j2 = helpFaceAuthenticationStatus2.createdAt;
                                long j3 = ((HelpFaceAuthenticationStatus) obj3).createdAt;
                                iCompare = j2 < j3 ? -1 : j2 == j3 ? 0 : 1;
                            } else {
                                iCompare = Intrinsics.compare(iIntValue3, iIntValue5);
                            }
                            return Integer.valueOf(iCompare);
                    }
                }
            };
            linkedHashMap.computeIfPresent(num, new BiFunction() { // from class: com.android.systemui.biometrics.FaceHelpMessageDebouncer$sam$java_util_function_BiFunction$0
                @Override // java.util.function.BiFunction
                public final /* synthetic */ Object apply(Object obj, Object obj2) {
                    return function2.invoke(obj, obj2);
                }
            });
        }
        Set setEntrySet = linkedHashMap.entrySet();
        final int i5 = 1;
        final Function2 function22 = new Function2(this) { // from class: com.android.systemui.biometrics.FaceHelpMessageDebouncer$$ExternalSyntheticLambda0
            public final /* synthetic */ FaceHelpMessageDebouncer f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int iCompare;
                Object obj3;
                Object objPrevious2;
                FaceHelpMessageDebouncer faceHelpMessageDebouncer = this.f$0;
                switch (i5) {
                    case 0:
                        int i52 = FaceHelpMessageDebouncer.$r8$clinit;
                        return Integer.valueOf(((Integer) obj2).intValue() + faceHelpMessageDebouncer.shownFaceMessageFrequencyBoost);
                    default:
                        Map.Entry entry = (Map.Entry) obj;
                        Map.Entry entry2 = (Map.Entry) obj2;
                        int i6 = FaceHelpMessageDebouncer.$r8$clinit;
                        entry.getClass();
                        int iIntValue2 = ((Number) entry.getKey()).intValue();
                        int iIntValue3 = ((Number) entry.getValue()).intValue();
                        entry2.getClass();
                        int iIntValue4 = ((Number) entry2.getKey()).intValue();
                        int iIntValue5 = ((Number) entry2.getValue()).intValue();
                        if (iIntValue3 == iIntValue5) {
                            ArrayList arrayList = (ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses;
                            ListIterator listIterator = arrayList.listIterator(arrayList.size());
                            while (true) {
                                obj3 = null;
                                if (listIterator.hasPrevious()) {
                                    objPrevious2 = listIterator.previous();
                                    if (((HelpFaceAuthenticationStatus) objPrevious2).msgId == iIntValue2) {
                                    }
                                } else {
                                    objPrevious2 = null;
                                }
                            }
                            objPrevious2.getClass();
                            HelpFaceAuthenticationStatus helpFaceAuthenticationStatus2 = (HelpFaceAuthenticationStatus) objPrevious2;
                            ArrayList arrayList2 = (ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses;
                            ListIterator listIterator2 = arrayList2.listIterator(arrayList2.size());
                            while (true) {
                                if (listIterator2.hasPrevious()) {
                                    Object objPrevious3 = listIterator2.previous();
                                    if (((HelpFaceAuthenticationStatus) objPrevious3).msgId == iIntValue4) {
                                        obj3 = objPrevious3;
                                    }
                                }
                            }
                            obj3.getClass();
                            long j2 = helpFaceAuthenticationStatus2.createdAt;
                            long j3 = ((HelpFaceAuthenticationStatus) obj3).createdAt;
                            iCompare = j2 < j3 ? -1 : j2 == j3 ? 0 : 1;
                        } else {
                            iCompare = Intrinsics.compare(iIntValue3, iIntValue5);
                        }
                        return Integer.valueOf(iCompare);
                }
            }
        };
        Comparator comparator = new Comparator() { // from class: com.android.systemui.biometrics.FaceHelpMessageDebouncer$sam$java_util_Comparator$0
            @Override // java.util.Comparator
            public final /* synthetic */ int compare(Object obj, Object obj2) {
                return ((Number) function22.invoke(obj, obj2)).intValue();
            }
        };
        Iterator it = setEntrySet.iterator();
        if (it.hasNext()) {
            next = it.next();
            while (it.hasNext()) {
                Object next2 = it.next();
                if (comparator.compare(next, next2) < 0) {
                    next = next2;
                }
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        Integer num2 = entry != null ? (Integer) entry.getKey() : null;
        if (num2 != null) {
            if (num2.equals(this.lastMessageIdShown)) {
                Object obj = linkedHashMap.get(num2);
                obj.getClass();
                iIntValue = ((Number) obj).intValue() - this.shownFaceMessageFrequencyBoost;
            } else {
                Object obj2 = linkedHashMap.get(num2);
                obj2.getClass();
                iIntValue = ((Number) obj2).intValue();
            }
            if (iIntValue / ((ArrayList) this.helpFaceAuthStatuses).size() >= this.threshold) {
                ArrayList arrayList = (ArrayList) this.helpFaceAuthStatuses;
                ListIterator listIterator = arrayList.listIterator(arrayList.size());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        objPrevious = null;
                        break;
                    }
                    objPrevious = listIterator.previous();
                    if (((HelpFaceAuthenticationStatus) objPrevious).msgId == num2.intValue()) {
                        break;
                    }
                }
                helpFaceAuthenticationStatus = (HelpFaceAuthenticationStatus) objPrevious;
            } else {
                helpFaceAuthenticationStatus = null;
            }
        }
        if (!Intrinsics.areEqual(this.lastMessageIdShown, helpFaceAuthenticationStatus != null ? Integer.valueOf(helpFaceAuthenticationStatus.msgId) : null)) {
            ((ArrayList) this.helpFaceAuthStatuses).size();
            List list2 = this.helpFaceAuthStatuses;
            Objects.toString(helpFaceAuthenticationStatus);
            Objects.toString(list2);
            this.lastMessageIdShown = helpFaceAuthenticationStatus != null ? Integer.valueOf(helpFaceAuthenticationStatus.msgId) : null;
        }
        return helpFaceAuthenticationStatus;
    }

    public final void startNewFaceAuthSession(long j) {
        Log.d(this.TAG, "startNewFaceAuthSession at startTime=" + this.startTime);
        this.startTime = j;
        ((ArrayList) this.helpFaceAuthStatuses).clear();
        this.lastMessageIdShown = null;
    }

    public FaceHelpMessageDebouncer(long j, long j2, int i, float f) {
        this.window = j;
        this.startWindow = j2;
        this.shownFaceMessageFrequencyBoost = i;
        this.threshold = f;
        this.TAG = "FaceHelpMessageDebouncer";
        this.helpFaceAuthStatuses = new ArrayList();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ FaceHelpMessageDebouncer(long j, long j2, int i, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        long j3 = (i2 & 1) != 0 ? 200L : j;
        this(j3, (i2 & 2) != 0 ? j3 : j2, (i2 & 4) != 0 ? 4 : i, (i2 & 8) != 0 ? 0.0f : f);
    }
}
