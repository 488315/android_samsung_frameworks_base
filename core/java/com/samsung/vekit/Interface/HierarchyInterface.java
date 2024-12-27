package com.samsung.vekit.Interface;

import com.samsung.vekit.Animation.Animation;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface HierarchyInterface<T> {
    void attach(T t);

    void attach(T t, int i);

    void attach(ArrayList<T> arrayList);

    void attachAnimation(Animation<?> animation);

    void clear();

    void clearAnimations();

    boolean contains(T t);

    void detach(int i);

    void detach(T t);

    void detachAnimation(Animation<?> animation);

    Animation<?> getAnimation(int i);

    int getAnimationIndex(Animation<?> animation);

    List<Animation<?>> getAnimationList();

    T getChild(int i);

    int getChildSize();

    List<T> getChildren();

    int getIndex(T t);

    boolean isEmpty();

    void swap(T t, T t2);
}
