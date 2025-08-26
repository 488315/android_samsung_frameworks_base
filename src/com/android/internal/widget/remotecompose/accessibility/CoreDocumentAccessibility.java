package com.android.internal.widget.remotecompose.accessibility;

import android.graphics.PointF;
import android.os.Bundle;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentModifiers;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class CoreDocumentAccessibility implements RemoteComposeDocumentAccessibility {
    private final CoreDocument mDocument;
    private final RemoteContext mRemoteContext;

    private static boolean isClickAction(int i) {
        return i == 16;
    }

    private static boolean isScrollBackwardAction(int i) {
        return i == 8192 || i == 16908344 || i == 16908345;
    }

    private static boolean isScrollForwardAction(int i) {
        return i == 4096 || i == 16908346 || i == 16908347;
    }

    private static boolean isShowOnScreenAction(int i) {
        return i == 16908342;
    }

    public CoreDocumentAccessibility(CoreDocument coreDocument, RemoteContext remoteContext) {
        this.mDocument = coreDocument;
        this.mRemoteContext = remoteContext;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public Integer getComponentIdAt(PointF pointF) {
        return RootId;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public Component findComponentById(final int i) {
        RootLayoutComponent rootLayoutComponent = this.mDocument.getRootLayoutComponent();
        return (rootLayoutComponent == null || i == -1) ? rootLayoutComponent : componentStream(rootLayoutComponent).filter(new Predicate() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CoreDocumentAccessibility.lambda$findComponentById$0(i, (Component) obj);
            }
        }).findFirst().orElse(null);
    }

    static /* synthetic */ boolean lambda$findComponentById$0(int i, Component component) {
        return component.getComponentId() == i;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public AccessibleComponent.Mode mergeMode(Component component) {
        if (!(component instanceof LayoutComponent)) {
            return AccessibleComponent.Mode.SET;
        }
        AccessibleComponent.Mode mode = AccessibleComponent.Mode.SET;
        Iterator<ModifierOperation> it = ((LayoutComponent) component).getComponentModifiers().getList().iterator();
        while (it.hasNext()) {
            ModifierOperation next = it.next();
            if (next instanceof AccessibleComponent) {
                AccessibleComponent accessibleComponent = (AccessibleComponent) next;
                if (accessibleComponent.getMode().ordinal() > mode.ordinal()) {
                    mode = accessibleComponent.getMode();
                }
            }
        }
        return mode;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public boolean performAction(Component component, int i, Bundle bundle) {
        boolean zShowOnScreen;
        try {
            if (isClickAction(i)) {
                zShowOnScreen = performClick(component);
            } else if (isScrollForwardAction(i)) {
                zShowOnScreen = scrollDirection(this.mRemoteContext, component, ScrollableComponent.ScrollDirection.FORWARD);
            } else if (isScrollBackwardAction(i)) {
                zShowOnScreen = scrollDirection(this.mRemoteContext, component, ScrollableComponent.ScrollDirection.BACKWARD);
            } else {
                if (!isShowOnScreenAction(i)) {
                    return false;
                }
                zShowOnScreen = showOnScreen(this.mRemoteContext, component);
            }
            return zShowOnScreen;
        } finally {
            this.mDocument.needsRepaint();
        }
    }

    private boolean showOnScreen(RemoteContext remoteContext, Component component) {
        ScrollableComponent scrollableComponentFindScrollable = findScrollable(component);
        if (scrollableComponentFindScrollable != null) {
            return scrollableComponentFindScrollable.showOnScreen(remoteContext, component);
        }
        return false;
    }

    private static ScrollableComponent findScrollable(Component component) {
        for (Component parent = component.getParent(); parent != null; parent = parent.getParent()) {
            ScrollableComponent scrollableComponent = (ScrollableComponent) parent.selfOrModifier(ScrollableComponent.class);
            if (scrollableComponent != null) {
                return scrollableComponent;
            }
        }
        return null;
    }

    public int scrollByOffset(RemoteContext remoteContext, Component component, int i) {
        ScrollableComponent scrollableComponent = (ScrollableComponent) component.selfOrModifier(ScrollableComponent.class);
        if (scrollableComponent != null) {
            return scrollableComponent.scrollByOffset(remoteContext, i);
        }
        return 0;
    }

    public boolean scrollDirection(RemoteContext remoteContext, Component component, ScrollableComponent.ScrollDirection scrollDirection) {
        ScrollableComponent scrollableComponent = (ScrollableComponent) component.selfOrModifier(ScrollableComponent.class);
        if (scrollableComponent != null) {
            return scrollableComponent.scrollDirection(remoteContext, scrollDirection);
        }
        return false;
    }

    public boolean performClick(Component component) {
        this.mDocument.performClick(this.mRemoteContext, component.getComponentId(), "");
        return true;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public String stringValue(int i) {
        Object fromId = this.mDocument.getRemoteComposeState().getFromId(i);
        if (fromId != null) {
            return String.valueOf(fromId);
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public List<AccessibilitySemantics> semanticModifiersForComponent(Component component) {
        if (!(component instanceof LayoutComponent)) {
            return Collections.EMPTY_LIST;
        }
        return (List) ((LayoutComponent) component).getComponentModifiers().getList().stream().filter(new Predicate() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CoreDocumentAccessibility.lambda$semanticModifiersForComponent$1((ModifierOperation) obj);
            }
        }).map(new Function() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CoreDocumentAccessibility.lambda$semanticModifiersForComponent$2((ModifierOperation) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$semanticModifiersForComponent$1(ModifierOperation modifierOperation) {
        return (modifierOperation instanceof AccessibilitySemantics) && ((AccessibilitySemantics) modifierOperation).isInterestingForSemantics();
    }

    static /* synthetic */ AccessibilitySemantics lambda$semanticModifiersForComponent$2(ModifierOperation modifierOperation) {
        return (AccessibilitySemantics) modifierOperation;
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeDocumentAccessibility
    public List<Integer> semanticallyRelevantChildComponents(Component component, boolean z) {
        if (!component.isVisible()) {
            return Collections.EMPTY_LIST;
        }
        AccessibleComponent.Mode modeMergeMode = mergeMode(component);
        if (modeMergeMode == AccessibleComponent.Mode.CLEAR_AND_SET || (!z && modeMergeMode == AccessibleComponent.Mode.MERGE)) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Operation> it = component.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                Component component2 = (Component) next;
                if (isInteresting(component2)) {
                    arrayList.add(Integer.valueOf(component2.getComponentId()));
                } else {
                    arrayList.addAll(semanticallyRelevantChildComponents(component2, z));
                }
            }
        }
        return arrayList;
    }

    static Stream<Component> componentStream(Component component) {
        return Stream.concat(Stream.of(component), component.mList.stream().flatMap(new Function() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CoreDocumentAccessibility.lambda$componentStream$3((Operation) obj);
            }
        }));
    }

    static /* synthetic */ Stream lambda$componentStream$3(Operation operation) {
        if (operation instanceof Component) {
            return componentStream((Component) operation);
        }
        return Stream.empty();
    }

    static Stream<ModifierOperation> modifiersStream(Component component) {
        return component.mList.stream().filter(new Predicate() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CoreDocumentAccessibility.lambda$modifiersStream$4((Operation) obj);
            }
        }).flatMap(new Function() { // from class: com.android.internal.widget.remotecompose.accessibility.CoreDocumentAccessibility$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ComponentModifiers) ((Operation) obj)).getList().stream();
            }
        });
    }

    static /* synthetic */ boolean lambda$modifiersStream$4(Operation operation) {
        return operation instanceof ComponentModifiers;
    }

    static boolean isInteresting(Component component) {
        if (component.isVisible()) {
            return isContainerWithSemantics(component) || modifiersStream(component).anyMatch(new CoreDocumentAccessibility$$ExternalSyntheticLambda0());
        }
        return false;
    }

    static boolean isModifierWithSemantics(ModifierOperation modifierOperation) {
        return (modifierOperation instanceof AccessibilitySemantics) && ((AccessibilitySemantics) modifierOperation).isInterestingForSemantics();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static boolean isContainerWithSemantics(Component component) {
        if (component instanceof AccessibilitySemantics) {
            return ((AccessibilitySemantics) component).isInterestingForSemantics();
        }
        if (component instanceof LayoutComponent) {
            return ((LayoutComponent) component).getComponentModifiers().getList().stream().anyMatch(new CoreDocumentAccessibility$$ExternalSyntheticLambda0());
        }
        return false;
    }
}
