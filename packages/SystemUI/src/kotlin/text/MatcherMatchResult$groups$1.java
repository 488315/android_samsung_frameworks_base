package kotlin.text;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes4.dex */
public final class MatcherMatchResult$groups$1 extends AbstractCollection implements Collection {
    public final /* synthetic */ MatcherMatchResult this$0;

    public MatcherMatchResult$groups$1(MatcherMatchResult matcherMatchResult) {
        this.this$0 = matcherMatchResult;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj == null ? true : obj instanceof MatchGroup) {
            return super.contains((MatchGroup) obj);
        }
        return false;
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.this$0.matcher.groupCount() + 1;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return false;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(new IntRange(0, getSize() - 1)), new Function1() { // from class: kotlin.text.MatcherMatchResult$groups$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int iIntValue = ((Integer) obj).intValue();
                MatcherMatchResult$groups$1 matcherMatchResult$groups$1 = this.f$0;
                Matcher matcher = matcherMatchResult$groups$1.this$0.matcher;
                IntRange intRangeUntil = RangesKt___RangesKt.until(matcher.start(iIntValue), matcher.end(iIntValue));
                if (intRangeUntil.first >= 0) {
                    return new MatchGroup(matcherMatchResult$groups$1.this$0.matcher.group(iIntValue), intRangeUntil);
                }
                return null;
            }
        }).new AnonymousClass1();
    }
}
