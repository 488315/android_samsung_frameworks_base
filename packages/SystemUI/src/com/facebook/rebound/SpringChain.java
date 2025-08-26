package com.facebook.rebound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class SpringChain implements SpringListener {
    public final SpringConfig mAttachmentSpringConfig;
    public int mControlSpringIndex;
    public final CopyOnWriteArrayList mListeners;
    public final SpringConfig mMainSpringConfig;
    public final SpringSystem mSpringSystem;
    public final CopyOnWriteArrayList mSprings;
    public static final SpringConfigRegistry registry = SpringConfigRegistry.INSTANCE;
    public static int id = 0;

    private SpringChain() {
        this(40, 6, 70, 10);
    }

    public static SpringChain create() {
        return new SpringChain(150, 20, 200, 12);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringActivate(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringActivate(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringAtRest(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringAtRest(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringEndStateChange(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringEndStateChange(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringUpdate(Spring spring) {
        int i;
        int i2;
        int iIndexOf = this.mSprings.indexOf(spring);
        SpringListener springListener = (SpringListener) this.mListeners.get(iIndexOf);
        int i3 = this.mControlSpringIndex;
        if (iIndexOf == i3) {
            i2 = iIndexOf - 1;
            i = iIndexOf + 1;
        } else if (iIndexOf < i3) {
            i2 = iIndexOf - 1;
            i = -1;
        } else if (iIndexOf > i3) {
            i = iIndexOf + 1;
            i2 = -1;
        } else {
            i = -1;
            i2 = -1;
        }
        if (i > -1 && i < this.mSprings.size()) {
            ((Spring) this.mSprings.get(i)).setEndValue(spring.mCurrentState.position);
        }
        if (i2 > -1 && i2 < this.mSprings.size()) {
            ((Spring) this.mSprings.get(i2)).setEndValue(spring.mCurrentState.position);
        }
        springListener.onSpringUpdate(spring);
    }

    public final void setControlSpringIndex() {
        this.mControlSpringIndex = 0;
        if (((Spring) this.mSprings.get(0)) == null) {
            return;
        }
        Collection collectionValues = ((HashMap) this.mSpringSystem.mSpringRegistry).values();
        for (Spring spring : Collections.unmodifiableList(collectionValues instanceof List ? (List) collectionValues : new ArrayList(collectionValues))) {
            SpringConfig springConfig = this.mAttachmentSpringConfig;
            if (springConfig == null) {
                spring.getClass();
                throw new IllegalArgumentException("springConfig is required");
            }
            spring.mSpringConfig = springConfig;
        }
        Spring spring2 = (Spring) this.mSprings.get(this.mControlSpringIndex);
        SpringConfig springConfig2 = this.mMainSpringConfig;
        if (springConfig2 == null) {
            spring2.getClass();
            throw new IllegalArgumentException("springConfig is required");
        }
        spring2.mSpringConfig = springConfig2;
    }

    private SpringChain(int i, int i2, int i3, int i4) {
        this.mSpringSystem = SpringSystem.create();
        this.mListeners = new CopyOnWriteArrayList();
        this.mSprings = new CopyOnWriteArrayList();
        this.mControlSpringIndex = -1;
        SpringConfig springConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(i), OrigamiValueConverter.frictionFromOrigamiValue(i2));
        this.mMainSpringConfig = springConfig;
        SpringConfig springConfig2 = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(i3), OrigamiValueConverter.frictionFromOrigamiValue(i4));
        this.mAttachmentSpringConfig = springConfig2;
        SpringConfigRegistry springConfigRegistry = registry;
        StringBuilder sb = new StringBuilder("main spring ");
        int i5 = id;
        id = i5 + 1;
        sb.append(i5);
        springConfigRegistry.addSpringConfig(springConfig, sb.toString());
        StringBuilder sb2 = new StringBuilder("attachment spring ");
        int i6 = id;
        id = i6 + 1;
        sb2.append(i6);
        springConfigRegistry.addSpringConfig(springConfig2, sb2.toString());
    }
}
