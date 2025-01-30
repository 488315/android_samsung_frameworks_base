package com.android.server.audio;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class MultiFocusStack extends SparseArray {
  public MultiFocusStack() {
    append(0, new Stack());
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

  public Stack getFocusStack(int i) {
    return getStackForDevice(i);
  }

  public void pushFocusRequester(int i, FocusRequester focusRequester) {
    getStackForDevice(i).push(focusRequester);
  }

  public ArrayList getFocusList(int i) {
    return new ArrayList(getStackForDevice(i));
  }

  public ArrayList getFocusRequester(int i, boolean z) {
    ArrayList arrayList = new ArrayList();
    for (int i2 = 0; i2 < size(); i2++) {
      Stack stack = (Stack) valueAt(i2);
      ArrayList findFocusRequester = findFocusRequester(stack, i);
      arrayList.addAll(findFocusRequester);
      if (z) {
        stack.removeAll(findFocusRequester);
      }
    }
    return arrayList;
  }

  public final ArrayList findFocusRequester(Stack stack, final int i) {
    return (ArrayList)
        stack.stream()
            .filter(
                new Predicate() { // from class:
                  // com.android.server.audio.MultiFocusStack$$ExternalSyntheticLambda1
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$findFocusRequester$0;
                    lambda$findFocusRequester$0 =
                        MultiFocusStack.lambda$findFocusRequester$0(i, (FocusRequester) obj);
                    return lambda$findFocusRequester$0;
                  }
                })
            .collect(
                Collectors.toCollection(
                    new Supplier() { // from class:
                      // com.android.server.audio.MultiFocusStack$$ExternalSyntheticLambda2
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        return new ArrayList();
                      }
                    }));
  }

  public static /* synthetic */ boolean lambda$findFocusRequester$0(
      int i, FocusRequester focusRequester) {
    return focusRequester.getCallingUid() == i;
  }

  public FocusRequester getFocusRequester(int i, String str, boolean z) {
    for (int i2 = 0; i2 < size(); i2++) {
      Stack stack = (Stack) valueAt(i2);
      FocusRequester findFocusRequester = findFocusRequester(stack, i, str);
      if (findFocusRequester != null) {
        if (z) {
          stack.remove(findFocusRequester);
        }
        return findFocusRequester;
      }
    }
    return null;
  }

  public final FocusRequester findFocusRequester(Stack stack, final int i, final String str) {
    return (FocusRequester)
        stack.stream()
            .filter(
                new Predicate() { // from class:
                  // com.android.server.audio.MultiFocusStack$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$findFocusRequester$1;
                    lambda$findFocusRequester$1 =
                        MultiFocusStack.lambda$findFocusRequester$1(i, str, (FocusRequester) obj);
                    return lambda$findFocusRequester$1;
                  }
                })
            .findFirst()
            .orElse(null);
  }

  public static /* synthetic */ boolean lambda$findFocusRequester$1(
      int i, String str, FocusRequester focusRequester) {
    return focusRequester.getCallingUid() == i && focusRequester.getClientId().equals(str);
  }
}
