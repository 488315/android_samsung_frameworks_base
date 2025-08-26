package com.android.systemui.statusbar.data.repository;

import android.graphics.Rect;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.layout.BoundsPair;
import com.android.systemui.statusbar.layout.LetterboxAppearance;
import com.android.systemui.statusbar.layout.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.layout.LetterboxBackgroundProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
final class StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ StatusBarModePerDisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1(StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = statusBarModePerDisplayRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1 statusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1 = new StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1(this.this$0, (Continuation) obj3);
        statusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1.L$0 = (StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes) obj;
        statusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1.L$1 = (BoundsPair) obj2;
        return statusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Pair pair;
        LetterboxAppearance letterboxAppearance;
        Object next;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes statusBarAttributes = (StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes) this.L$0;
        BoundsPair boundsPair = (BoundsPair) this.L$1;
        if (statusBarAttributes == null) {
            return null;
        }
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = this.this$0;
        List list = statusBarAttributes.appearanceRegions;
        List list2 = statusBarAttributes.letterboxDetails;
        int i = StatusBarModePerDisplayRepositoryImpl.$r8$clinit;
        statusBarModePerDisplayRepositoryImpl.getClass();
        boolean zIsEmpty = list2.isEmpty();
        int i2 = statusBarAttributes.appearance;
        if (zIsEmpty) {
            pair = new Pair(Integer.valueOf(i2), list);
        } else {
            Integer numValueOf = Integer.valueOf(i2);
            LetterboxAppearanceCalculator letterboxAppearanceCalculator = statusBarModePerDisplayRepositoryImpl.letterboxAppearanceCalculator;
            letterboxAppearanceCalculator.lastAppearance = numValueOf;
            letterboxAppearanceCalculator.lastAppearanceRegions = list;
            letterboxAppearanceCalculator.lastLetterboxes = list2;
            LetterboxBackgroundProvider letterboxBackgroundProvider = letterboxAppearanceCalculator.letterboxBackgroundProvider;
            if (letterboxBackgroundProvider.isLetterboxBackgroundMultiColored) {
                letterboxAppearance = new LetterboxAppearance(i2 | 32, list);
                letterboxAppearanceCalculator.lastLetterboxAppearance = letterboxAppearance;
                pair = new Pair(Integer.valueOf(letterboxAppearance.appearance), letterboxAppearance.appearanceRegions);
            } else {
                List<LetterboxDetails> list3 = list2;
                if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                    for (LetterboxDetails letterboxDetails : list3) {
                        Rect letterboxInnerBounds = letterboxDetails.getLetterboxInnerBounds();
                        Rect rect = boundsPair.start;
                        if (!((letterboxInnerBounds.contains(rect) || rect.contains(letterboxInnerBounds)) ? false : letterboxInnerBounds.intersects(rect.left, rect.top, rect.right, rect.bottom))) {
                            Rect letterboxInnerBounds2 = letterboxDetails.getLetterboxInnerBounds();
                            Rect rect2 = boundsPair.end;
                            if ((letterboxInnerBounds2.contains(rect2) || rect2.contains(letterboxInnerBounds2)) ? false : letterboxInnerBounds2.intersects(rect2.left, rect2.top, rect2.right, rect2.bottom)) {
                            }
                        }
                        letterboxAppearance = new LetterboxAppearance(i2 | 32, list);
                    }
                }
                int i3 = i2 & (-33);
                List<AppearanceRegion> list4 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                for (AppearanceRegion appearanceRegion : list4) {
                    Iterator it = list3.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (Intrinsics.areEqual(((LetterboxDetails) next).getLetterboxFullBounds(), appearanceRegion.getBounds())) {
                            break;
                        }
                    }
                    LetterboxDetails letterboxDetails2 = (LetterboxDetails) next;
                    if (letterboxDetails2 != null) {
                        appearanceRegion = new AppearanceRegion(appearanceRegion.getAppearance(), letterboxDetails2.getLetterboxInnerBounds());
                    }
                    arrayList.add(appearanceRegion);
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                Iterator it2 = list3.iterator();
                while (it2.hasNext()) {
                    LetterboxDetails letterboxDetails3 = (LetterboxDetails) it2.next();
                    int i4 = letterboxBackgroundProvider.letterboxBackgroundColor;
                    int i5 = ContrastColorUtil.calculateContrast(letterboxAppearanceCalculator.lightAppearanceIconColor, i4) > ContrastColorUtil.calculateContrast(letterboxAppearanceCalculator.darkAppearanceIconColor, i4) ? 8 : 0;
                    Rect letterboxInnerBounds3 = letterboxDetails3.getLetterboxInnerBounds();
                    Rect letterboxFullBounds = letterboxDetails3.getLetterboxFullBounds();
                    Iterator it3 = it2;
                    LetterboxBackgroundProvider letterboxBackgroundProvider2 = letterboxBackgroundProvider;
                    List listAsList = Arrays.asList(new Rect(letterboxFullBounds.left, letterboxFullBounds.top, letterboxInnerBounds3.left, letterboxFullBounds.bottom), new Rect(letterboxFullBounds.left, letterboxFullBounds.top, letterboxFullBounds.right, letterboxInnerBounds3.top), new Rect(letterboxInnerBounds3.right, letterboxFullBounds.top, letterboxFullBounds.right, letterboxFullBounds.bottom), new Rect(letterboxFullBounds.left, letterboxInnerBounds3.bottom, letterboxFullBounds.right, letterboxFullBounds.bottom));
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj2 : listAsList) {
                        if (!((Rect) obj2).isEmpty()) {
                            arrayList3.add(obj2);
                        }
                    }
                    ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                    int size = arrayList3.size();
                    int i6 = 0;
                    while (i6 < size) {
                        Object obj3 = arrayList3.get(i6);
                        i6++;
                        arrayList4.add(new AppearanceRegion(i5, (Rect) obj3));
                    }
                    arrayList2.add(arrayList4);
                    it2 = it3;
                    letterboxBackgroundProvider = letterboxBackgroundProvider2;
                }
                letterboxAppearance = new LetterboxAppearance(i3, CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__IterablesKt.flatten(arrayList2), (Collection) arrayList));
                letterboxAppearanceCalculator.lastLetterboxAppearance = letterboxAppearance;
                pair = new Pair(Integer.valueOf(letterboxAppearance.appearance), letterboxAppearance.appearanceRegions);
            }
        }
        return new StatusBarModePerDisplayRepositoryImpl.ModifiedStatusBarAttributes(((Number) pair.component1()).intValue(), (List) pair.component2(), statusBarAttributes.navbarColorManagedByIme, boundsPair);
    }
}
