package androidx.fragment.app.strictmode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FragmentStrictMode.kt */
/* loaded from: classes.dex */
public final class FragmentStrictMode {
    private static Policy defaultPolicy = Policy.LAX;

    /* compiled from: FragmentStrictMode.kt */
    public enum Flag {
        PENALTY_LOG,
        PENALTY_DEATH,
        DETECT_FRAGMENT_REUSE,
        DETECT_FRAGMENT_TAG_USAGE,
        DETECT_WRONG_NESTED_HIERARCHY,
        /* JADX INFO: Fake field, exist only in values array */
        EF5,
        /* JADX INFO: Fake field, exist only in values array */
        EF6,
        /* JADX INFO: Fake field, exist only in values array */
        EF7,
        DETECT_WRONG_FRAGMENT_CONTAINER;

        Flag() {
        }
    }

    /* compiled from: FragmentStrictMode.kt */
    public static final class Policy {
        public static final Policy LAX = new Policy(EmptySet.INSTANCE, MapsKt.emptyMap());
        private final Set<Flag> flags;
        private final Map<String, Set<Class<? extends Violation>>> mAllowedViolations;

        public Policy(EmptySet flags, Map map) {
            Intrinsics.checkNotNullParameter(flags, "flags");
            this.flags = flags;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            ((EmptySet) map.entrySet()).getClass();
            EmptyIterator emptyIterator = EmptyIterator.INSTANCE;
            this.mAllowedViolations = linkedHashMap;
        }

        public final Set<Flag> getFlags$fragment_release() {
            return this.flags;
        }

        public final Map<String, Set<Class<? extends Violation>>> getMAllowedViolations$fragment_release() {
            return this.mAllowedViolations;
        }
    }

    private static Policy getNearestPolicy(Fragment fragment) {
        while (fragment != null) {
            if (fragment.isAdded()) {
                fragment.getParentFragmentManager();
            }
            fragment = fragment.getParentFragment();
        }
        return defaultPolicy;
    }

    private static void handlePolicyViolation(Policy policy, final Violation violation) {
        Fragment fragment = violation.getFragment();
        final String name = fragment.getClass().getName();
        if (policy.getFlags$fragment_release().contains(Flag.PENALTY_LOG)) {
            Log.d("FragmentStrictMode", "Policy violation in ".concat(name), violation);
        }
        if (policy.getFlags$fragment_release().contains(Flag.PENALTY_DEATH)) {
            Runnable runnable = new Runnable() { // from class: androidx.fragment.app.strictmode.FragmentStrictMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    String str = (String) name;
                    Violation violation2 = violation;
                    Intrinsics.checkNotNullParameter(violation2, "$violation");
                    Log.e("FragmentStrictMode", "Policy violation with PENALTY_DEATH in " + str, violation2);
                    throw violation2;
                }
            };
            if (!fragment.isAdded()) {
                runnable.run();
                throw null;
            }
            Handler handler = fragment.getParentFragmentManager().getHost().getHandler();
            Intrinsics.checkNotNullExpressionValue(handler, "fragment.parentFragmentManager.host.handler");
            if (Intrinsics.areEqual(handler.getLooper(), Looper.myLooper())) {
                runnable.run();
                throw null;
            }
            handler.post(runnable);
        }
    }

    private static void logIfDebuggingEnabled(Violation violation) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "StrictMode violation in ".concat(violation.getFragment().getClass().getName()), violation);
        }
    }

    public static final void onFragmentReuse(Fragment fragment, String previousFragmentId) {
        Intrinsics.checkNotNullParameter(previousFragmentId, "previousFragmentId");
        FragmentReuseViolation fragmentReuseViolation = new FragmentReuseViolation(fragment, previousFragmentId);
        logIfDebuggingEnabled(fragmentReuseViolation);
        Policy nearestPolicy = getNearestPolicy(fragment);
        if (nearestPolicy.getFlags$fragment_release().contains(Flag.DETECT_FRAGMENT_REUSE) && shouldHandlePolicyViolation(nearestPolicy, Fragment.class, FragmentReuseViolation.class)) {
            handlePolicyViolation(nearestPolicy, fragmentReuseViolation);
        }
    }

    public static final void onFragmentTagUsage(Fragment fragment, ViewGroup viewGroup) {
        FragmentTagUsageViolation fragmentTagUsageViolation = new FragmentTagUsageViolation(fragment, viewGroup);
        logIfDebuggingEnabled(fragmentTagUsageViolation);
        Policy nearestPolicy = getNearestPolicy(fragment);
        if (nearestPolicy.getFlags$fragment_release().contains(Flag.DETECT_FRAGMENT_TAG_USAGE) && shouldHandlePolicyViolation(nearestPolicy, Fragment.class, FragmentTagUsageViolation.class)) {
            handlePolicyViolation(nearestPolicy, fragmentTagUsageViolation);
        }
    }

    public static final void onWrongFragmentContainer(Fragment fragment, ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        WrongFragmentContainerViolation wrongFragmentContainerViolation = new WrongFragmentContainerViolation(fragment, viewGroup);
        logIfDebuggingEnabled(wrongFragmentContainerViolation);
        Policy nearestPolicy = getNearestPolicy(fragment);
        if (nearestPolicy.getFlags$fragment_release().contains(Flag.DETECT_WRONG_FRAGMENT_CONTAINER) && shouldHandlePolicyViolation(nearestPolicy, Fragment.class, WrongFragmentContainerViolation.class)) {
            handlePolicyViolation(nearestPolicy, wrongFragmentContainerViolation);
        }
    }

    private static boolean shouldHandlePolicyViolation(Policy policy, Class cls, Class cls2) {
        Set set = (Set) ((LinkedHashMap) policy.getMAllowedViolations$fragment_release()).get(cls.getName());
        if (set == null) {
            return true;
        }
        if (Intrinsics.areEqual(cls2.getSuperclass(), Violation.class) || !CollectionsKt.contains(set, cls2.getSuperclass())) {
            return !set.contains(cls2);
        }
        return false;
    }
}
