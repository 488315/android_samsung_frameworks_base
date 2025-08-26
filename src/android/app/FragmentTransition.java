package android.app;

import android.app.BackStackRecord;
import android.content.res.Resources;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    public static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;
    }

    FragmentTransition() {
    }

    static void startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (fragmentManagerImpl.mCurState < 1) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = arrayList.get(i3);
            if (arrayList2.get(i3).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z);
            } else {
                calculateFragments(backStackRecord, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(fragmentManagerImpl.mHost.getContext());
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = sparseArray.keyAt(i4);
                ArrayMap<String, String> arrayMapCalculateNameOverrides = calculateNameOverrides(iKeyAt, arrayList, arrayList2, i, i2);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                if (z) {
                    configureTransitionsReordered(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides);
                } else {
                    configureTransitionsOrdered(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides);
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int i, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            if (backStackRecord.interactsWith(i)) {
                boolean zBooleanValue = arrayList2.get(i4).booleanValue();
                if (backStackRecord.mSharedElementSourceNames != null) {
                    int size = backStackRecord.mSharedElementSourceNames.size();
                    if (zBooleanValue) {
                        arrayList3 = backStackRecord.mSharedElementSourceNames;
                        arrayList4 = backStackRecord.mSharedElementTargetNames;
                    } else {
                        ArrayList<String> arrayList5 = backStackRecord.mSharedElementSourceNames;
                        arrayList3 = backStackRecord.mSharedElementTargetNames;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = arrayList4.get(i5);
                        String str2 = arrayList3.get(i5);
                        String strRemove = arrayMap.remove(str2);
                        if (strRemove != null) {
                            arrayMap.put(str, strRemove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    private static void configureTransitionsReordered(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        ViewGroup viewGroup = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i) : null;
        if (viewGroup == null) {
            return;
        }
        Fragment fragment = fragmentContainerTransition.lastIn;
        Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Transition enterTransition = getEnterTransition(fragment, z);
        Transition exitTransition = getExitTransition(fragment2, z2);
        TransitionSet transitionSetConfigureSharedElementsReordered = configureSharedElementsReordered(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition);
        if (enterTransition == null && transitionSetConfigureSharedElementsReordered == null && exitTransition == null) {
            return;
        }
        ArrayList<View> arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(exitTransition, fragment2, arrayList2, view);
        ArrayList<View> arrayListConfigureEnteringExitingViews2 = configureEnteringExitingViews(enterTransition, fragment, arrayList, view);
        setViewVisibility(arrayListConfigureEnteringExitingViews2, 4);
        Transition transitionMergeTransitions = mergeTransitions(enterTransition, exitTransition, transitionSetConfigureSharedElementsReordered, fragment, z);
        if (transitionMergeTransitions != null) {
            replaceHide(exitTransition, fragment2, arrayListConfigureEnteringExitingViews);
            transitionMergeTransitions.setNameOverrides(arrayMap);
            scheduleRemoveTargets(transitionMergeTransitions, enterTransition, arrayListConfigureEnteringExitingViews2, exitTransition, arrayListConfigureEnteringExitingViews, transitionSetConfigureSharedElementsReordered, arrayList);
            TransitionManager.beginDelayedTransition(viewGroup, transitionMergeTransitions);
            setViewVisibility(arrayListConfigureEnteringExitingViews2, 0);
            if (transitionSetConfigureSharedElementsReordered != null) {
                transitionSetConfigureSharedElementsReordered.getTargets().clear();
                transitionSetConfigureSharedElementsReordered.getTargets().addAll(arrayList);
                replaceTargets(transitionSetConfigureSharedElementsReordered, arrayList2, arrayList);
            }
        }
    }

    private static void configureTransitionsOrdered(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        Transition transition = null;
        ViewGroup viewGroup = fragmentManagerImpl.mContainer.onHasView() ? (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i) : null;
        if (viewGroup == null) {
            return;
        }
        Fragment fragment = fragmentContainerTransition.lastIn;
        Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        Transition enterTransition = getEnterTransition(fragment, z);
        Transition exitTransition = getExitTransition(fragment2, z2);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        TransitionSet transitionSetConfigureSharedElementsOrdered = configureSharedElementsOrdered(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && transitionSetConfigureSharedElementsOrdered == null && exitTransition == null) {
            return;
        }
        ArrayList<View> arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(exitTransition, fragment2, arrayList, view);
        if (arrayListConfigureEnteringExitingViews != null && !arrayListConfigureEnteringExitingViews.isEmpty()) {
            transition = exitTransition;
        }
        if (enterTransition != null) {
            enterTransition.addTarget(view);
        }
        Transition transitionMergeTransitions = mergeTransitions(enterTransition, transition, transitionSetConfigureSharedElementsOrdered, fragment, fragmentContainerTransition.lastInIsPop);
        if (transitionMergeTransitions != null) {
            transitionMergeTransitions.setNameOverrides(arrayMap);
            ArrayList arrayList3 = new ArrayList();
            Transition transition2 = transition;
            scheduleRemoveTargets(transitionMergeTransitions, enterTransition, arrayList3, transition2, arrayListConfigureEnteringExitingViews, transitionSetConfigureSharedElementsOrdered, arrayList2);
            scheduleTargetChange(viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, transition2, arrayListConfigureEnteringExitingViews);
            TransitionManager.beginDelayedTransition(viewGroup, transitionMergeTransitions);
        }
    }

    private static void replaceHide(Transition transition, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && transition != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            final View view = fragment.getView();
            OneShotPreDrawListener.add(fragment.mContainer, new Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FragmentTransition.setViewVisibility(arrayList, 4);
                }
            });
            transition.addListener(new TransitionListenerAdapter() { // from class: android.app.FragmentTransition.1
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(Transition transition2) {
                    transition2.removeListener(this);
                    view.setVisibility(8);
                    FragmentTransition.setViewVisibility(arrayList, 0);
                }
            });
        }
    }

    private static void scheduleTargetChange(ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Transition transition, final ArrayList<View> arrayList2, final Transition transition2, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FragmentTransition.lambda$scheduleTargetChange$1(transition, view, fragment, arrayList, arrayList2, arrayList3, transition2);
            }
        });
    }

    static /* synthetic */ void lambda$scheduleTargetChange$1(Transition transition, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Transition transition2) {
        if (transition != null) {
            transition.removeTarget(view);
            arrayList2.addAll(configureEnteringExitingViews(transition, fragment, arrayList, view));
        }
        if (arrayList3 != null) {
            if (transition2 != null) {
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(view);
                replaceTargets(transition2, arrayList3, arrayList4);
            }
            arrayList3.clear();
            arrayList3.add(view);
        }
    }

    private static TransitionSet getSharedElementTransition(Fragment fragment, Fragment fragment2, boolean z) {
        Transition sharedElementEnterTransition;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            sharedElementEnterTransition = fragment2.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = fragment.getSharedElementEnterTransition();
        }
        Transition transitionCloneTransition = cloneTransition(sharedElementEnterTransition);
        if (transitionCloneTransition == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transitionCloneTransition);
        return transitionSet;
    }

    private static Transition getEnterTransition(Fragment fragment, boolean z) {
        Transition enterTransition;
        if (fragment == null) {
            return null;
        }
        if (z) {
            enterTransition = fragment.getReenterTransition();
        } else {
            enterTransition = fragment.getEnterTransition();
        }
        return cloneTransition(enterTransition);
    }

    private static Transition getExitTransition(Fragment fragment, boolean z) {
        Transition exitTransition;
        if (fragment == null) {
            return null;
        }
        if (z) {
            exitTransition = fragment.getReturnTransition();
        } else {
            exitTransition = fragment.getExitTransition();
        }
        return cloneTransition(exitTransition);
    }

    private static Transition cloneTransition(Transition transition) {
        return transition != null ? transition.mo5502clone() : transition;
    }

    private static TransitionSet configureSharedElementsReordered(ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Transition transition, Transition transition2) {
        final View view2;
        final Rect rect;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        TransitionSet sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragment, fragment2, z);
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        final ArrayMap<String, View> arrayMapCaptureInSharedElements = captureInSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            if (arrayMapCaptureOutSharedElements != null) {
                arrayMapCaptureOutSharedElements.clear();
            }
            if (arrayMapCaptureInSharedElements != null) {
                arrayMapCaptureInSharedElements.clear();
            }
            sharedElementTransition = null;
        } else {
            addSharedElementsWithMatchingNames(arrayList, arrayMapCaptureOutSharedElements, arrayMap.keySet());
            addSharedElementsWithMatchingNames(arrayList2, arrayMapCaptureInSharedElements, arrayMap.values());
        }
        if (transition == null && transition2 == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (sharedElementTransition != null) {
            arrayList2.add(view);
            setSharedElementTargets(sharedElementTransition, view, arrayList);
            setOutEpicenter(sharedElementTransition, transition2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            final Rect rect2 = new Rect();
            View inEpicenterView = getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, transition, z);
            if (inEpicenterView != null) {
                transition.setEpicenterCallback(new Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.2
                    @Override // android.transition.Transition.EpicenterCallback
                    public Rect onGetEpicenter(Transition transition3) {
                        return rect2;
                    }
                });
            }
            view2 = inEpicenterView;
            rect = rect2;
        } else {
            view2 = null;
            rect = null;
        }
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                FragmentTransition.lambda$configureSharedElementsReordered$2(fragment, fragment2, z, arrayMapCaptureInSharedElements, view2, rect);
            }
        });
        return sharedElementTransition;
    }

    static /* synthetic */ void lambda$configureSharedElementsReordered$2(Fragment fragment, Fragment fragment2, boolean z, ArrayMap arrayMap, View view, Rect rect) {
        callSharedElementStartEnd(fragment, fragment2, z, arrayMap, false);
        if (view != null) {
            view.getBoundsOnScreen(rect);
        }
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View viewValueAt = arrayMap.valueAt(size);
            if (viewValueAt != null && collection.contains(viewValueAt.getTransitionName())) {
                arrayList.add(viewValueAt);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static TransitionSet configureSharedElementsOrdered(ViewGroup viewGroup, final View view, final ArrayMap<String, String> arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Transition transition, Transition transition2) {
        final TransitionSet sharedElementTransition;
        TransitionSet transitionSet;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        if (arrayMap.isEmpty()) {
            sharedElementTransition = null;
            transitionSet = null;
        } else {
            sharedElementTransition = getSharedElementTransition(fragment, fragment2, z);
            transitionSet = null;
        }
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            sharedElementTransition = transitionSet;
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values());
        }
        if (transition == null && transition2 == null && sharedElementTransition == null) {
            return transitionSet;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        TransitionSet transitionSet2 = transitionSet;
        if (sharedElementTransition != null) {
            final Rect rect = new Rect();
            setSharedElementTargets(sharedElementTransition, view, arrayList);
            setOutEpicenter(sharedElementTransition, transition2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            transitionSet2 = rect;
            if (transition != null) {
                transition.setEpicenterCallback(new Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.3
                    @Override // android.transition.Transition.EpicenterCallback
                    public Rect onGetEpicenter(Transition transition3) {
                        if (rect.isEmpty()) {
                            return null;
                        }
                        return rect;
                    }
                });
                transitionSet2 = rect;
            }
        }
        final Rect rect2 = transitionSet2;
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: android.app.FragmentTransition$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FragmentTransition.lambda$configureSharedElementsOrdered$3(arrayMap, sharedElementTransition, fragmentContainerTransition, arrayList2, view, fragment, fragment2, z, arrayList, transition, rect2);
            }
        });
        return sharedElementTransition;
    }

    static /* synthetic */ void lambda$configureSharedElementsOrdered$3(ArrayMap arrayMap, TransitionSet transitionSet, FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean z, ArrayList arrayList2, Transition transition, Rect rect) {
        ArrayMap<String, View> arrayMapCaptureInSharedElements = captureInSharedElements(arrayMap, transitionSet, fragmentContainerTransition);
        if (arrayMapCaptureInSharedElements != null) {
            arrayList.addAll(arrayMapCaptureInSharedElements.values());
            arrayList.add(view);
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false);
        if (transitionSet != null) {
            transitionSet.getTargets().clear();
            transitionSet.getTargets().addAll(arrayList);
            replaceTargets(transitionSet, arrayList2, arrayList);
            View inEpicenterView = getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, transition, z);
            if (inEpicenterView != null) {
                inEpicenterView.getBoundsOnScreen(rect);
            }
        }
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> arrayMap, TransitionSet transitionSet, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback exitTransitionCallback;
        ArrayList<String> arrayList;
        if (arrayMap.isEmpty() || transitionSet == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.firstOut;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragment.getView().findNamedViews(arrayMap2);
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        }
        arrayMap2.retainAll(arrayList);
        if (exitTransitionCallback != null) {
            exitTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(view.getTransitionName())) {
                    arrayMap.put(view.getTransitionName(), arrayMap.remove(str));
                }
            }
            return arrayMap2;
        }
        arrayMap.retainAll(arrayMap2.keySet());
        return arrayMap2;
    }

    private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> arrayMap, TransitionSet transitionSet, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback enterTransitionCallback;
        ArrayList<String> arrayList;
        String strFindKeyForValue;
        Fragment fragment = fragmentContainerTransition.lastIn;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || transitionSet == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        view.findNamedViews(arrayMap2);
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (arrayList != null && enterTransitionCallback != null) {
            enterTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    String strFindKeyForValue2 = findKeyForValue(arrayMap, str);
                    if (strFindKeyForValue2 != null) {
                        arrayMap.remove(strFindKeyForValue2);
                    }
                } else if (!str.equals(view2.getTransitionName()) && (strFindKeyForValue = findKeyForValue(arrayMap, str)) != null) {
                    arrayMap.put(strFindKeyForValue, view2.getTransitionName());
                }
            }
            return arrayMap2;
        }
        retainValues(arrayMap, arrayMap2);
        return arrayMap2;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    private static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragmentContainerTransition, Transition transition, boolean z) {
        String str;
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (transition == null || arrayMap == null || backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (z) {
            str = backStackRecord.mSharedElementSourceNames.get(0);
        } else {
            str = backStackRecord.mSharedElementTargetNames.get(0);
        }
        return arrayMap.get(str);
    }

    private static void setOutEpicenter(TransitionSet transitionSet, Transition transition, ArrayMap<String, View> arrayMap, boolean z, BackStackRecord backStackRecord) {
        String str;
        if (backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return;
        }
        if (z) {
            str = backStackRecord.mSharedElementTargetNames.get(0);
        } else {
            str = backStackRecord.mSharedElementSourceNames.get(0);
        }
        View view = arrayMap.get(str);
        setEpicenter(transitionSet, view);
        if (transition != null) {
            setEpicenter(transition, view);
        }
    }

    private static void setEpicenter(Transition transition, View view) {
        if (view != null) {
            final Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            transition.setEpicenterCallback(new Transition.EpicenterCallback() { // from class: android.app.FragmentTransition.4
                @Override // android.transition.Transition.EpicenterCallback
                public Rect onGetEpicenter(Transition transition2) {
                    return rect;
                }
            });
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    private static void callSharedElementStartEnd(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback enterTransitionCallback;
        if (z) {
            enterTransitionCallback = fragment2.getEnterTransitionCallback();
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
        }
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    private static void setSharedElementTargets(TransitionSet transitionSet, View view, ArrayList<View> arrayList) {
        List<View> targets = transitionSet.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            bfsAddViewChildren(targets, arrayList.get(i));
        }
        targets.add(view);
        arrayList.add(view);
        addTargets(transitionSet, arrayList);
    }

    private static void bfsAddViewChildren(List<View> list, View view) {
        int size = list.size();
        if (containedBeforeIndex(list, view, size)) {
            return;
        }
        list.add(view);
        for (int i = size; i < list.size(); i++) {
            View view2 = list.get(i);
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = viewGroup.getChildAt(i2);
                    if (!containedBeforeIndex(list, childAt, size)) {
                        list.add(childAt);
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(List<View> list, View view, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (list.get(i2) == view) {
                return true;
            }
        }
        return false;
    }

    private static void scheduleRemoveTargets(Transition transition, final Transition transition2, final ArrayList<View> arrayList, final Transition transition3, final ArrayList<View> arrayList2, final TransitionSet transitionSet, final ArrayList<View> arrayList3) {
        transition.addListener(new TransitionListenerAdapter() { // from class: android.app.FragmentTransition.5
            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition4) {
                Transition transition5 = transition2;
                if (transition5 != null) {
                    FragmentTransition.replaceTargets(transition5, arrayList, null);
                }
                Transition transition6 = transition3;
                if (transition6 != null) {
                    FragmentTransition.replaceTargets(transition6, arrayList2, null);
                }
                TransitionSet transitionSet2 = transitionSet;
                if (transitionSet2 != null) {
                    FragmentTransition.replaceTargets(transitionSet2, arrayList3, null);
                }
            }

            @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition4) {
                transition4.removeListener(this);
            }
        });
    }

    public static void replaceTargets(Transition transition, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        List<View> targets;
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                replaceTargets(transitionSet.getTransitionAt(i), arrayList, arrayList2);
                i++;
            }
            return;
        }
        if (hasSimpleTarget(transition) || (targets = transition.getTargets()) == null || targets.size() != arrayList.size() || !targets.containsAll(arrayList)) {
            return;
        }
        int size = arrayList2 == null ? 0 : arrayList2.size();
        while (i < size) {
            transition.addTarget(arrayList2.get(i));
            i++;
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            transition.removeTarget(arrayList.get(size2));
        }
    }

    public static void addTargets(Transition transition, ArrayList<View> arrayList) {
        if (transition == null) {
            return;
        }
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                addTargets(transitionSet.getTransitionAt(i), arrayList);
                i++;
            }
            return;
        }
        if (hasSimpleTarget(transition) || !isNullOrEmpty(transition.getTargets())) {
            return;
        }
        int size = arrayList.size();
        while (i < size) {
            transition.addTarget(arrayList.get(i));
            i++;
        }
    }

    private static boolean hasSimpleTarget(Transition transition) {
        return (isNullOrEmpty(transition.getTargetIds()) && isNullOrEmpty(transition.getTargetNames()) && isNullOrEmpty(transition.getTargetTypes())) ? false : true;
    }

    private static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    private static ArrayList<View> configureEnteringExitingViews(Transition transition, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (transition == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View view2 = fragment.getView();
        if (view2 != null) {
            view2.captureTransitioningViews(arrayList2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            arrayList2.add(view);
            addTargets(transition, arrayList2);
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(i);
        }
    }

    private static Transition mergeTransitions(Transition transition, Transition transition2, Transition transition3, Fragment fragment, boolean z) {
        boolean allowEnterTransitionOverlap;
        if (transition == null || transition2 == null || fragment == null) {
            allowEnterTransitionOverlap = true;
        } else if (z) {
            allowEnterTransitionOverlap = fragment.getAllowReturnTransitionOverlap();
        } else {
            allowEnterTransitionOverlap = fragment.getAllowEnterTransitionOverlap();
        }
        if (allowEnterTransitionOverlap) {
            TransitionSet transitionSet = new TransitionSet();
            if (transition != null) {
                transitionSet.addTransition(transition);
            }
            if (transition2 != null) {
                transitionSet.addTransition(transition2);
            }
            if (transition3 != null) {
                transitionSet.addTransition(transition3);
            }
            return transitionSet;
        }
        if (transition2 != null && transition != null) {
            transition = new TransitionSet().addTransition(transition2).addTransition(transition).setOrdering(1);
        } else if (transition2 != null) {
            transition = transition2;
        } else if (transition == null) {
            transition = null;
        }
        if (transition3 == null) {
            return transition;
        }
        TransitionSet transitionSet2 = new TransitionSet();
        if (transition != null) {
            transitionSet2.addTransition(transition);
        }
        transitionSet2.addTransition(transition3);
        return transitionSet2;
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) throws Resources.NotFoundException {
        int size = backStackRecord.mOps.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, false, z);
        }
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) throws Resources.NotFoundException {
        if (backStackRecord.mManager.mContainer.onHasView()) {
            for (int size = backStackRecord.mOps.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(size), sparseArray, true, z);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void addToFirstInLastOut(BackStackRecord backStackRecord, BackStackRecord.Op op, SparseArray<FragmentContainerTransition> sparseArray, boolean z, boolean z2) throws Resources.NotFoundException {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        Fragment fragment = op.fragment;
        if (fragment == null || (i = fragment.mContainerId) == 0) {
            return;
        }
        int i2 = z ? INVERSE_OPS[op.cmd] : op.cmd;
        boolean z7 = false;
        if (i2 == 1) {
            if (!z2) {
                z3 = fragment.mIsNewlyAdded;
                z4 = false;
                z5 = false;
                z7 = z3;
                z6 = true;
            } else {
                z3 = (fragment.mAdded || fragment.mHidden) ? false : true;
                z4 = false;
                z5 = false;
                z7 = z3;
                z6 = true;
            }
        } else if (i2 == 3) {
            boolean z8 = z2 ? !(!fragment.mAdded || fragment.mHidden) : !(fragment.mAdded || fragment.mView == null || fragment.mView.getVisibility() != 0 || fragment.mView.getTransitionAlpha() <= 0.0f);
            z5 = z8;
            z6 = false;
            z4 = true;
        } else if (i2 == 4) {
            if (!z2 ? !fragment.mAdded || fragment.mHidden : !fragment.mHiddenChanged || !fragment.mAdded || !fragment.mHidden) {
            }
            z5 = z8;
            z6 = false;
            z4 = true;
        } else if (i2 != 5) {
            if (i2 != 6) {
                if (i2 != 7) {
                    z6 = false;
                    z4 = false;
                    z5 = false;
                }
                if (!z2) {
                }
            }
            if (z2) {
            }
            z5 = z8;
            z6 = false;
            z4 = true;
        } else if (z2) {
            if (!fragment.mHiddenChanged || fragment.mHidden || !fragment.mAdded) {
            }
            z4 = false;
            z5 = false;
            z7 = z3;
            z6 = true;
        } else {
            z3 = fragment.mHidden;
            z4 = false;
            z5 = false;
            z7 = z3;
            z6 = true;
        }
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer = sparseArray.get(i);
        if (z7) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i);
            fragmentContainerTransitionEnsureContainer.lastIn = fragment;
            fragmentContainerTransitionEnsureContainer.lastInIsPop = z;
            fragmentContainerTransitionEnsureContainer.lastInTransaction = backStackRecord;
        }
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer2 = fragmentContainerTransitionEnsureContainer;
        if (!z2 && z6) {
            if (fragmentContainerTransitionEnsureContainer2 != null && fragmentContainerTransitionEnsureContainer2.firstOut == fragment) {
                fragmentContainerTransitionEnsureContainer2.firstOut = null;
            }
            FragmentManagerImpl fragmentManagerImpl = backStackRecord.mManager;
            if (fragment.mState < 1 && fragmentManagerImpl.mCurState >= 1 && fragmentManagerImpl.mHost.getContext().getApplicationInfo().targetSdkVersion >= 24 && !backStackRecord.mReorderingAllowed) {
                fragmentManagerImpl.makeActive(fragment);
                fragmentManagerImpl.moveToState(fragment, 1, 0, 0, false);
            }
        }
        if (z5 && (fragmentContainerTransitionEnsureContainer2 == null || fragmentContainerTransitionEnsureContainer2.firstOut == null)) {
            fragmentContainerTransitionEnsureContainer2 = ensureContainer(fragmentContainerTransitionEnsureContainer2, sparseArray, i);
            fragmentContainerTransitionEnsureContainer2.firstOut = fragment;
            fragmentContainerTransitionEnsureContainer2.firstOutIsPop = z;
            fragmentContainerTransitionEnsureContainer2.firstOutTransaction = backStackRecord;
        }
        if (z2 || !z4 || fragmentContainerTransitionEnsureContainer2 == null || fragmentContainerTransitionEnsureContainer2.lastIn != fragment) {
            return;
        }
        fragmentContainerTransitionEnsureContainer2.lastIn = null;
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition;
        }
        FragmentContainerTransition fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(i, fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }
}
