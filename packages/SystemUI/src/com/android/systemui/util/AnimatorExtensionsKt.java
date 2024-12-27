package com.android.systemui.util;

import androidx.core.animation.Animator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AnimatorExtensionsKt {
    public static final Animator.AnimatorListener addListener(Animator animator, Function1 function1, Function1 function12, Function1 function13, Function1 function14) {
        AnimatorExtensionsKt$addListener$listener$1 animatorExtensionsKt$addListener$listener$1 = new AnimatorExtensionsKt$addListener$listener$1(function14, function1, function13, function12);
        animator.addListener(animatorExtensionsKt$addListener$listener$1);
        return animatorExtensionsKt$addListener$listener$1;
    }

    public static /* synthetic */ Animator.AnimatorListener addListener$default(Animator animator, Function1 function1, Function1 function12, Function1 function13, Function1 function14, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: com.android.systemui.util.AnimatorExtensionsKt$addListener$1
                public final void invoke(Animator animator2) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((Animator) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 2) != 0) {
            function12 = new Function1() { // from class: com.android.systemui.util.AnimatorExtensionsKt$addListener$2
                public final void invoke(Animator animator2) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((Animator) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 4) != 0) {
            function13 = new Function1() { // from class: com.android.systemui.util.AnimatorExtensionsKt$addListener$3
                public final void invoke(Animator animator2) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((Animator) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 8) != 0) {
            function14 = new Function1() { // from class: com.android.systemui.util.AnimatorExtensionsKt$addListener$4
                public final void invoke(Animator animator2) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((Animator) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        AnimatorExtensionsKt$addListener$listener$1 animatorExtensionsKt$addListener$listener$1 = new AnimatorExtensionsKt$addListener$listener$1(function14, function1, function13, function12);
        animator.addListener(animatorExtensionsKt$addListener$listener$1);
        return animatorExtensionsKt$addListener$listener$1;
    }

    public static final Animator.AnimatorListener doOnCancel(Animator animator, final Function1 function1) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: com.android.systemui.util.AnimatorExtensionsKt$doOnCancel$$inlined$addListener$default$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
                Function1.this.invoke(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2, boolean z) {
                onAnimationEnd(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2, boolean z) {
                onAnimationStart(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorListener doOnEnd(Animator animator, final Function1 function1) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: com.android.systemui.util.AnimatorExtensionsKt$doOnEnd$$inlined$addListener$default$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                Function1.this.invoke(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2, boolean z) {
                onAnimationEnd(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2, boolean z) {
                onAnimationStart(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorListener doOnRepeat(Animator animator, final Function1 function1) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: com.android.systemui.util.AnimatorExtensionsKt$doOnRepeat$$inlined$addListener$default$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
                Function1.this.invoke(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2, boolean z) {
                onAnimationEnd(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2, boolean z) {
                onAnimationStart(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorListener doOnStart(Animator animator, final Function1 function1) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: com.android.systemui.util.AnimatorExtensionsKt$doOnStart$$inlined$addListener$default$1
            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                Function1.this.invoke(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2, boolean z) {
                onAnimationEnd(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2, boolean z) {
                onAnimationStart(animator2);
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }

            @Override // androidx.core.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }
}
