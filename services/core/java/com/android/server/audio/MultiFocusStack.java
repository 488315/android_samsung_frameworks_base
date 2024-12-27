package com.android.server.audio;

import android.util.SparseArray;

import com.android.server.appwidget.AppWidgetXmlUtil$$ExternalSyntheticLambda1;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MultiFocusStack extends SparseArray {
    public final FocusRequester getFocusRequester(final int i, final String str, boolean z) {
        FocusRequester focusRequester;
        int i2 = 0;
        while (true) {
            focusRequester = null;
            if (i2 >= size()) {
                break;
            }
            Stack stack = (Stack) valueAt(i2);
            focusRequester =
                    (FocusRequester)
                            stack.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.server.audio.MultiFocusStack$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    FocusRequester focusRequester2 =
                                                            (FocusRequester) obj;
                                                    return focusRequester2.mCallingUid == i
                                                            && focusRequester2.mClientId.equals(
                                                                    str);
                                                }
                                            })
                                    .findFirst()
                                    .orElse(null);
            if (focusRequester == null) {
                i2++;
            } else if (z) {
                stack.remove(focusRequester);
            }
        }
        return focusRequester;
    }

    public final ArrayList getFocusRequester(final int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size(); i2++) {
            Stack stack = (Stack) valueAt(i2);
            ArrayList arrayList2 =
                    (ArrayList)
                            stack.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.android.server.audio.MultiFocusStack$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    return ((FocusRequester) obj).mCallingUid == i;
                                                }
                                            })
                                    .collect(
                                            Collectors.toCollection(
                                                    new AppWidgetXmlUtil$$ExternalSyntheticLambda1()));
            arrayList.addAll(arrayList2);
            if (z) {
                stack.removeAll(arrayList2);
            }
        }
        return arrayList;
    }

    public final Stack getStackForDevice(int i) {
        Stack stack = (Stack) get(i);
        if (stack != null) {
            return stack;
        }
        Stack stack2 = new Stack();
        append(i, stack2);
        return stack2;
    }
}
