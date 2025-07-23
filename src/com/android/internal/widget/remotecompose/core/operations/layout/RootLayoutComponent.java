package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.SerializableToString;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Measurable;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentModifiers;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class RootLayoutComponent extends Component {
    private int mCurrentId;
    private boolean mHasTouchListeners;

    public static int id() {
        return 200;
    }

    public RootLayoutComponent(int i, float f, float f2, float f3, float f4, Component component, int i2) {
        super(component, i, i2, f, f2, f3, f4);
        this.mCurrentId = -1;
        this.mHasTouchListeners = false;
    }

    public RootLayoutComponent(int i, float f, float f2, float f3, float f4, Component component) {
        super(component, i, -1, f, f2, f3, f4);
        this.mCurrentId = -1;
        this.mHasTouchListeners = false;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "ROOT " + this.mComponentId + " (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + Component.Visibility.toString(this.mVisibility);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "ROOT [" + this.mComponentId + ":" + this.mAnimationId + "] = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] " + Component.Visibility.toString(this.mVisibility));
    }

    public void setHasTouchListeners(boolean z) {
        this.mHasTouchListeners = z;
    }

    public void assignIds(int i) {
        this.mCurrentId = i;
        assignId(this);
    }

    private void assignId(Component component) {
        if (component.mComponentId == -1) {
            int i = this.mCurrentId - 1;
            this.mCurrentId = i;
            component.mComponentId = i;
        }
        Iterator<Operation> it = component.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                assignId((Component) next);
            }
        }
    }

    public void layout(RemoteContext remoteContext) {
        if (this.mNeedsMeasure) {
            remoteContext.mLastComponent = this;
            setWidth(remoteContext.mWidth);
            setHeight(remoteContext.mHeight);
            MeasurePass measurePass = new MeasurePass();
            Iterator<Operation> it = this.mList.iterator();
            while (it.hasNext()) {
                Object obj = (Operation) it.next();
                if (obj instanceof Measurable) {
                    Measurable measurable = (Measurable) obj;
                    measurable.measure(remoteContext.getPaintContext(), 0.0f, this.mWidth, 0.0f, this.mHeight, measurePass);
                    measurable.layout(remoteContext, measurePass);
                }
            }
            this.mNeedsMeasure = false;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        this.mNeedsRepaint = false;
        RemoteContext context = paintContext.getContext();
        context.mLastComponent = this;
        paintContext.save();
        if (this.mParent == null) {
            paintContext.clipRect(0.0f, 0.0f, this.mWidth, this.mHeight);
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof PaintOperation) {
                ((PaintOperation) next).paint(paintContext);
                context.incrementOpCount();
            }
        }
        paintContext.restore();
    }

    public String displayHierarchy() {
        StringSerializer stringSerializer = new StringSerializer();
        displayHierarchy(this, 0, stringSerializer);
        return stringSerializer.toString();
    }

    public void displayHierarchy(Component component, int i, StringSerializer stringSerializer) {
        component.serializeToString(i, stringSerializer);
        Iterator<Operation> it = component.mList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof ComponentModifiers) {
                ((ComponentModifiers) obj).serializeToString(i + 1, stringSerializer);
            } else if (obj instanceof Component) {
                displayHierarchy((Component) obj, i + 1, stringSerializer);
            } else if (obj instanceof SerializableToString) {
                ((SerializableToString) obj).serializeToString(i + 1, stringSerializer);
            }
        }
    }

    public static String name() {
        return "RootLayout";
    }

    public static void apply(WireBuffer wireBuffer, int i) {
        wireBuffer.start(200);
        wireBuffer.writeInt(i);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new RootLayoutComponent(wireBuffer.readInt(), 0.0f, 0.0f, 0.0f, 0.0f, null, -1));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).field(0, "COMPONENT_ID", "unique id for this component").description("Root element for a document. Other components / layout managers are children in the component tree starting fromthis Root component.");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId);
    }

    public boolean hasTouchListeners() {
        return this.mHasTouchListeners;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.addTags(SerializeTags.COMPONENT);
        mapSerializer.addType("RootLayoutComponent");
    }
}
