package androidx.fragment.app.strictmode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentStrictMode {
    public static final FragmentStrictMode INSTANCE = new FragmentStrictMode();
    public static final Policy defaultPolicy = Policy.LAX;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Flag {
        PENALTY_LOG,
        PENALTY_DEATH,
        DETECT_FRAGMENT_REUSE,
        DETECT_FRAGMENT_TAG_USAGE,
        /* JADX INFO: Fake field, exist only in values array */
        DETECT_RETAIN_INSTANCE_USAGE,
        /* JADX INFO: Fake field, exist only in values array */
        DETECT_SET_USER_VISIBLE_HINT,
        DETECT_TARGET_FRAGMENT_USAGE,
        DETECT_WRONG_FRAGMENT_CONTAINER
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnViolationListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Policy {
        public static final Policy LAX;
        public final Set flags;
        public final Map mAllowedViolations;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            new Companion(null);
            LAX = new Policy(EmptySet.INSTANCE, null, MapsKt__MapsKt.emptyMap());
        }

        public Policy(Set<? extends Flag> set, OnViolationListener onViolationListener, Map<String, ? extends Set<Class<? extends Violation>>> map) {
            this.flags = set;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<String, ? extends Set<Class<? extends Violation>>> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
            this.mAllowedViolations = linkedHashMap;
        }
    }

    private FragmentStrictMode() {
    }

    public static Policy getNearestPolicy(Fragment fragment) {
        while (fragment != null) {
            if (fragment.isAdded()) {
                fragment.getParentFragmentManager();
            }
            fragment = fragment.mParentFragment;
        }
        return defaultPolicy;
    }

    public static void handlePolicyViolation(Policy policy, final Violation violation) {
        Fragment fragment = violation.getFragment();
        final String name = fragment.getClass().getName();
        Flag flag = Flag.PENALTY_LOG;
        Set set = policy.flags;
        if (set.contains(flag)) {
            Log.d("FragmentStrictMode", "Policy violation in ".concat(name), violation);
        }
        if (set.contains(Flag.PENALTY_DEATH)) {
            Runnable runnable = new Runnable() { // from class: androidx.fragment.app.strictmode.FragmentStrictMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    String str = (String) name;
                    Violation violation2 = violation;
                    Log.e("FragmentStrictMode", "Policy violation with PENALTY_DEATH in " + str, violation2);
                    throw violation2;
                }
            };
            if (!fragment.isAdded()) {
                runnable.run();
                throw null;
            }
            Handler handler = fragment.getParentFragmentManager().mHost.mHandler;
            if (Intrinsics.areEqual(handler.getLooper(), Looper.myLooper())) {
                runnable.run();
                throw null;
            }
            handler.post(runnable);
        }
    }

    public static void logIfDebuggingEnabled(Violation violation) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "StrictMode violation in ".concat(violation.getFragment().getClass().getName()), violation);
        }
    }

    public static final void onFragmentReuse(Fragment fragment, String str) {
        FragmentReuseViolation fragmentReuseViolation = new FragmentReuseViolation(fragment, str);
        INSTANCE.getClass();
        logIfDebuggingEnabled(fragmentReuseViolation);
        Policy nearestPolicy = getNearestPolicy(fragment);
        if (nearestPolicy.flags.contains(Flag.DETECT_FRAGMENT_REUSE) && shouldHandlePolicyViolation(nearestPolicy, fragment.getClass(), FragmentReuseViolation.class)) {
            handlePolicyViolation(nearestPolicy, fragmentReuseViolation);
        }
    }

    public static boolean shouldHandlePolicyViolation(Policy policy, Class cls, Class cls2) {
        Set set = (Set) ((LinkedHashMap) policy.mAllowedViolations).get(cls.getName());
        if (set == null) {
            return true;
        }
        if (Intrinsics.areEqual(cls2.getSuperclass(), Violation.class) || !CollectionsKt___CollectionsKt.contains(set, cls2.getSuperclass())) {
            return !set.contains(cls2);
        }
        return false;
    }
}
