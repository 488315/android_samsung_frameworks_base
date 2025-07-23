package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.ComponentValue;
import com.android.internal.widget.remotecompose.core.operations.DataListFloat;
import com.android.internal.widget.remotecompose.core.operations.DrawContent;
import com.android.internal.widget.remotecompose.core.operations.FloatConstant;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.Header;
import com.android.internal.widget.remotecompose.core.operations.IntegerExpression;
import com.android.internal.widget.remotecompose.core.operations.NamedVariable;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.Theme;
import com.android.internal.widget.remotecompose.core.operations.layout.CanvasOperations;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.Container;
import com.android.internal.widget.remotecompose.core.operations.layout.ContainerEnd;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.LoopOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentModifiers;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.types.IntegerConstant;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes6.dex */
public class CoreDocument implements Serializable {
    static final float BUILD = 0.0f;
    private static final boolean DEBUG = false;
    public static final int DOCUMENT_API_LEVEL = 6;
    public static final int MAJOR_VERSION = 1;
    public static final int MINOR_VERSION = 0;
    public static final int PATCH_VERSION = 0;
    private static final boolean UPDATE_VARIABLES_BEFORE_LAYOUT = false;
    String mContentDescription;
    private IntMap<Object> mDocProperties;
    HapticEngine mHapticEngine;
    private int mLastOpCount;
    ArrayList<Operation> mOperations = new ArrayList<>();
    RootLayoutComponent mRootLayoutComponent = null;
    RemoteComposeState mRemoteComposeState = new RemoteComposeState();
    public TimeVariables mTimeVariables = new TimeVariables();
    Version mVersion = new Version(1, 0, 0);
    long mRequiredCapabilities = 0;
    int mWidth = 0;
    int mHeight = 0;
    int mContentScroll = 0;
    int mContentSizing = 0;
    int mContentMode = 0;
    int mContentAlignment = 34;
    RemoteComposeBuffer mBuffer = new RemoteComposeBuffer(this.mRemoteComposeState);
    private final HashMap<Long, IntegerExpression> mIntegerExpressions = new HashMap<>();
    private final HashMap<Integer, FloatExpression> mFloatExpressions = new HashMap<>();
    private HashSet<Component> mAppliedTouchOperations = new HashSet<>();
    private int mLastId = 1;
    boolean mFirstPaint = true;
    private boolean mIsUpdateDoc = false;
    HashSet<ActionCallback> mActionListeners = new HashSet<>();
    HashSet<IdActionCallback> mIdActionListeners = new HashSet<>();
    HashSet<TouchListener> mTouchListeners = new HashSet<>();
    HashSet<ClickAreaRepresentation> mClickAreas = new HashSet<>();
    private HashMap<Integer, Component> mComponentMap = new HashMap<>();
    private final float[] mScaleOutput = new float[2];
    private final float[] mTranslateOutput = new float[2];
    private int mRepaintNext = -1;

    public interface ActionCallback {
        void onAction(String str, Object obj);
    }

    public interface HapticEngine {
        void haptic(int i);
    }

    public interface IdActionCallback {
        void onAction(int i, String str);
    }

    public interface ShaderControl {
        boolean isShaderValid(String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface Visitor {
        void visit(Operation operation);
    }

    public static int getDocumentApiLevel() {
        return 6;
    }

    public String getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(String str) {
        this.mContentDescription = str;
    }

    public long getRequiredCapabilities() {
        return this.mRequiredCapabilities;
    }

    public void setRequiredCapabilities(long j) {
        this.mRequiredCapabilities = j;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
        this.mRemoteComposeState.setWindowWidth(i);
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
        this.mRemoteComposeState.setWindowHeight(i);
    }

    public RemoteComposeBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(RemoteComposeBuffer remoteComposeBuffer) {
        this.mBuffer = remoteComposeBuffer;
    }

    public RemoteComposeState getRemoteComposeState() {
        return this.mRemoteComposeState;
    }

    public void setRemoteComposeState(RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public int getContentScroll() {
        return this.mContentScroll;
    }

    public int getContentSizing() {
        return this.mContentSizing;
    }

    public int getContentMode() {
        return this.mContentMode;
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        this.mContentScroll = i;
        this.mContentAlignment = i2;
        this.mContentSizing = i3;
        this.mContentMode = i4;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void computeScale(float f, float f2, float[] fArr) {
        float f3;
        float f4 = 1.0f;
        if (this.mContentSizing == 2) {
            switch (this.mContentMode) {
                case 1:
                    f4 = Math.min(1.0f, Math.min(f / this.mWidth, f2 / this.mHeight));
                    break;
                case 2:
                    f4 = f / this.mWidth;
                    break;
                case 3:
                    f4 = f2 / this.mHeight;
                    break;
                case 4:
                    f4 = Math.min(f / this.mWidth, f2 / this.mHeight);
                    break;
                case 5:
                    f4 = Math.max(f / this.mWidth, f2 / this.mHeight);
                    break;
                case 6:
                    f4 = f / this.mWidth;
                    f3 = f2 / this.mHeight;
                    break;
            }
            fArr[0] = f4;
            fArr[1] = f3;
        }
        f3 = f4;
        fArr[0] = f4;
        fArr[1] = f3;
    }

    private void computeTranslate(float f, float f2, float f3, float f4, float[] fArr) {
        int i = this.mContentAlignment;
        int i2 = i & 240;
        int i3 = i & 15;
        float f5 = this.mWidth * f3;
        float f6 = this.mHeight * f4;
        float f7 = 0.0f;
        float f8 = i2 != 32 ? i2 != 64 ? 0.0f : f - f5 : (f - f5) / 2.0f;
        if (i3 == 2) {
            f7 = (f2 - f6) / 2.0f;
        } else if (i3 == 4) {
            f7 = f2 - f6;
        }
        fArr[0] = f8;
        fArr[1] = f7;
    }

    public Set<ClickAreaRepresentation> getClickAreas() {
        return this.mClickAreas;
    }

    public RootLayoutComponent getRootLayoutComponent() {
        return this.mRootLayoutComponent;
    }

    public void invalidateMeasure() {
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        if (rootLayoutComponent != null) {
            rootLayoutComponent.invalidateMeasure();
        }
    }

    public Component getComponent(int i) {
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        if (rootLayoutComponent != null) {
            return rootLayoutComponent.getComponent(i);
        }
        return null;
    }

    public String displayHierarchy() {
        StringSerializer stringSerializer = new StringSerializer();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof RootLayoutComponent) {
                ((RootLayoutComponent) obj).displayHierarchy((Component) obj, 0, stringSerializer);
            } else if (obj instanceof SerializableToString) {
                ((SerializableToString) obj).serializeToString(0, stringSerializer);
            }
        }
        return stringSerializer.toString();
    }

    public void evaluateIntExpression(long j, int i, RemoteContext remoteContext) {
        IntegerExpression integerExpression = this.mIntegerExpressions.get(Long.valueOf(j));
        if (integerExpression != null) {
            remoteContext.overrideInteger(i, integerExpression.evaluate(remoteContext));
        }
    }

    public void evaluateFloatExpression(int i, int i2, RemoteContext remoteContext) {
        FloatExpression floatExpression = this.mFloatExpressions.get(Integer.valueOf(i));
        if (floatExpression != null) {
            remoteContext.overrideFloat(i2, floatExpression.evaluate(remoteContext));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType("CoreDocument").add("width", Integer.valueOf(this.mWidth)).add("height", Integer.valueOf(this.mHeight)).add("operations", this.mOperations);
    }

    public void setProperties(IntMap<Object> intMap) {
        this.mDocProperties = intMap;
    }

    public Object getProperty(short s) {
        IntMap<Object> intMap = this.mDocProperties;
        if (intMap == null) {
            return null;
        }
        return intMap.get(s);
    }

    public void applyUpdate(CoreDocument coreDocument) {
        final HashMap hashMap = new HashMap();
        final HashMap hashMap2 = new HashMap();
        final HashMap hashMap3 = new HashMap();
        final HashMap hashMap4 = new HashMap();
        final HashMap hashMap5 = new HashMap();
        final HashMap hashMap6 = new HashMap();
        recursiveTraverse(this.mOperations, new Visitor() { // from class: com.android.internal.widget.remotecompose.core.CoreDocument$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.Visitor
            public final void visit(Operation operation) {
                CoreDocument.lambda$applyUpdate$0(hashMap, hashMap2, hashMap3, hashMap4, hashMap5, hashMap6, operation);
            }
        });
        recursiveTraverse(coreDocument.mOperations, new Visitor() { // from class: com.android.internal.widget.remotecompose.core.CoreDocument$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.Visitor
            public final void visit(Operation operation) {
                CoreDocument.lambda$applyUpdate$1(hashMap, hashMap2, hashMap3, hashMap4, hashMap5, hashMap6, operation);
            }
        });
    }

    static /* synthetic */ void lambda$applyUpdate$0(HashMap hashMap, HashMap hashMap2, HashMap hashMap3, HashMap hashMap4, HashMap hashMap5, HashMap hashMap6, Operation operation) {
        if (operation instanceof TextData) {
            TextData textData = (TextData) operation;
            hashMap.put(Integer.valueOf(textData.mTextId), textData);
            return;
        }
        if (operation instanceof BitmapData) {
            BitmapData bitmapData = (BitmapData) operation;
            hashMap2.put(Integer.valueOf(bitmapData.mImageId), bitmapData);
            return;
        }
        if (operation instanceof FloatConstant) {
            FloatConstant floatConstant = (FloatConstant) operation;
            hashMap3.put(Integer.valueOf(floatConstant.mId), floatConstant);
            return;
        }
        if (operation instanceof IntegerConstant) {
            IntegerConstant integerConstant = (IntegerConstant) operation;
            hashMap4.put(Integer.valueOf(integerConstant.mId), integerConstant);
        } else if (operation instanceof LongConstant) {
            LongConstant longConstant = (LongConstant) operation;
            hashMap5.put(Integer.valueOf(longConstant.mId), longConstant);
        } else if (operation instanceof DataListFloat) {
            DataListFloat dataListFloat = (DataListFloat) operation;
            hashMap6.put(Integer.valueOf(dataListFloat.mId), dataListFloat);
        }
    }

    static /* synthetic */ void lambda$applyUpdate$1(HashMap hashMap, HashMap hashMap2, HashMap hashMap3, HashMap hashMap4, HashMap hashMap5, HashMap hashMap6, Operation operation) {
        if (operation instanceof TextData) {
            TextData textData = (TextData) operation;
            TextData textData2 = (TextData) hashMap.get(Integer.valueOf(textData.mTextId));
            if (textData2 != null) {
                textData2.update(textData);
                textData2.markDirty();
                return;
            }
            return;
        }
        if (operation instanceof BitmapData) {
            BitmapData bitmapData = (BitmapData) operation;
            BitmapData bitmapData2 = (BitmapData) hashMap2.get(Integer.valueOf(bitmapData.mImageId));
            if (bitmapData2 != null) {
                bitmapData2.update(bitmapData);
                bitmapData2.markDirty();
                return;
            }
            return;
        }
        if (operation instanceof FloatConstant) {
            FloatConstant floatConstant = (FloatConstant) operation;
            FloatConstant floatConstant2 = (FloatConstant) hashMap3.get(Integer.valueOf(floatConstant.mId));
            if (floatConstant2 != null) {
                floatConstant2.update(floatConstant);
                floatConstant2.markDirty();
                return;
            }
            return;
        }
        if (operation instanceof IntegerConstant) {
            IntegerConstant integerConstant = (IntegerConstant) operation;
            IntegerConstant integerConstant2 = (IntegerConstant) hashMap4.get(Integer.valueOf(integerConstant.mId));
            if (integerConstant2 != null) {
                integerConstant2.update(integerConstant);
                integerConstant2.markDirty();
                return;
            }
            return;
        }
        if (operation instanceof LongConstant) {
            LongConstant longConstant = (LongConstant) operation;
            LongConstant longConstant2 = (LongConstant) hashMap5.get(Integer.valueOf(longConstant.mId));
            if (longConstant2 != null) {
                longConstant2.update(longConstant);
                longConstant2.markDirty();
                return;
            }
            return;
        }
        if (operation instanceof DataListFloat) {
            DataListFloat dataListFloat = (DataListFloat) operation;
            DataListFloat dataListFloat2 = (DataListFloat) hashMap6.get(Integer.valueOf(dataListFloat.mId));
            if (dataListFloat2 != null) {
                dataListFloat2.update(dataListFloat);
                dataListFloat2.markDirty();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void recursiveTraverse(ArrayList<Operation> arrayList, Visitor visitor) {
        Iterator<Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Container) {
                recursiveTraverse(((Container) next).getList(), visitor);
            }
            visitor.visit(next);
        }
    }

    public void setHapticEngine(HapticEngine hapticEngine) {
        this.mHapticEngine = hapticEngine;
    }

    public void haptic(int i) {
        HapticEngine hapticEngine = this.mHapticEngine;
        if (hapticEngine != null) {
            hapticEngine.haptic(i);
        }
    }

    public void appliedTouchOperation(Component component) {
        this.mAppliedTouchOperations.add(component);
    }

    public void runNamedAction(String str, Object obj) {
        Iterator<ActionCallback> it = this.mActionListeners.iterator();
        while (it.hasNext()) {
            it.next().onAction(str, obj);
        }
    }

    public void addActionCallback(ActionCallback actionCallback) {
        this.mActionListeners.add(actionCallback);
    }

    public void clearActionCallbacks() {
        this.mActionListeners.clear();
    }

    static class Version {
        public final int major;
        public final int minor;
        public final int patchLevel;

        Version(int i, int i2, int i3) {
            this.major = i;
            this.minor = i2;
            this.patchLevel = i3;
        }

        public boolean supportsVersion(int i, int i2, int i3) {
            int i4 = this.major;
            if (i > i4) {
                return false;
            }
            if (i < i4) {
                return true;
            }
            int i5 = this.minor;
            if (i2 > i5) {
                return false;
            }
            return i2 < i5 || i3 <= this.patchLevel;
        }
    }

    public static class ClickAreaRepresentation {
        float mBottom;
        final String mContentDescription;
        int mId;
        float mLeft;
        final String mMetadata;
        float mRight;
        float mTop;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClickAreaRepresentation)) {
                return false;
            }
            ClickAreaRepresentation clickAreaRepresentation = (ClickAreaRepresentation) obj;
            return this.mId == clickAreaRepresentation.mId && Objects.equals(this.mContentDescription, clickAreaRepresentation.mContentDescription) && Objects.equals(this.mMetadata, clickAreaRepresentation.mMetadata);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mId), this.mContentDescription, this.mMetadata);
        }

        public ClickAreaRepresentation(int i, String str, float f, float f2, float f3, float f4, String str2) {
            this.mId = i;
            this.mContentDescription = str;
            this.mLeft = f;
            this.mTop = f2;
            this.mRight = f3;
            this.mBottom = f4;
            this.mMetadata = str2;
        }

        public boolean contains(float f, float f2) {
            return f >= this.mLeft && f < this.mRight && f2 >= this.mTop && f2 < this.mBottom;
        }

        public float getLeft() {
            return this.mLeft;
        }

        public float getTop() {
            return this.mTop;
        }

        public float width() {
            return Math.max(0.0f, this.mRight - this.mLeft);
        }

        public float height() {
            return Math.max(0.0f, this.mBottom - this.mTop);
        }

        public int getId() {
            return this.mId;
        }

        public String getContentDescription() {
            return this.mContentDescription;
        }

        public String getMetadata() {
            return this.mMetadata;
        }
    }

    public void initFromBuffer(RemoteComposeBuffer remoteComposeBuffer) {
        ArrayList<Operation> arrayList = new ArrayList<>();
        this.mOperations = arrayList;
        remoteComposeBuffer.inflateFromBuffer(arrayList);
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Header) {
                ((Header) next).setVersion(this);
            }
            if (next instanceof IntegerExpression) {
                this.mIntegerExpressions.put(Long.valueOf(r2.mId), (IntegerExpression) next);
            }
            if (next instanceof FloatExpression) {
                FloatExpression floatExpression = (FloatExpression) next;
                this.mFloatExpressions.put(Integer.valueOf(floatExpression.mId), floatExpression);
            }
        }
        ArrayList<Operation> inflateComponents = inflateComponents(this.mOperations);
        this.mOperations = inflateComponents;
        this.mBuffer = remoteComposeBuffer;
        Iterator<Operation> it2 = inflateComponents.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Operation next2 = it2.next();
            if (next2 instanceof RootLayoutComponent) {
                this.mRootLayoutComponent = (RootLayoutComponent) next2;
                break;
            }
        }
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        if (rootLayoutComponent != null) {
            rootLayoutComponent.assignIds(this.mLastId);
        }
    }

    private ArrayList<Operation> inflateComponents(ArrayList<Operation> arrayList) {
        ArrayList<Operation> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        this.mLastId = -1;
        Iterator<Operation> it = arrayList.iterator();
        ArrayList arrayList4 = arrayList2;
        LayoutComponent layoutComponent = null;
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof Container) {
                Container container = (Container) obj;
                if (container instanceof Component) {
                    Component component = (Component) container;
                    if (!arrayList3.isEmpty()) {
                        Container container2 = (Container) arrayList3.get(arrayList3.size() - 1);
                        if (container2 instanceof Component) {
                            component.setParent((Component) container2);
                        }
                    }
                    if (component.getComponentId() < this.mLastId) {
                        this.mLastId = component.getComponentId();
                    }
                    if (component instanceof LayoutComponent) {
                        layoutComponent = (LayoutComponent) component;
                    }
                }
                arrayList3.add(container);
                arrayList4 = container.getList();
            } else if (obj instanceof ContainerEnd) {
                Container container3 = !arrayList3.isEmpty() ? (Container) arrayList3.remove(arrayList3.size() - 1) : null;
                Container container4 = !arrayList3.isEmpty() ? (Container) arrayList3.get(arrayList3.size() - 1) : null;
                ArrayList<Operation> list = container4 != null ? container4.getList() : arrayList2;
                if (container3 != null) {
                    if (container3 instanceof Component) {
                        ((Component) container3).inflate();
                    }
                    list.add((Operation) container3);
                }
                if (container3 instanceof CanvasOperations) {
                    ((CanvasOperations) container3).setComponent(layoutComponent);
                }
                arrayList4 = list;
            } else {
                if (obj instanceof DrawContent) {
                    ((DrawContent) obj).setComponent(layoutComponent);
                }
                arrayList4.add(obj);
            }
        }
        return arrayList4;
    }

    private void registerVariables(RemoteContext remoteContext, ArrayList<Operation> arrayList) {
        Iterator<Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof VariableSupport) {
                ((VariableSupport) obj).registerListening(remoteContext);
            }
            if (obj instanceof Component) {
                Component component = (Component) obj;
                this.mComponentMap.put(Integer.valueOf(component.getComponentId()), component);
                component.registerVariables(remoteContext);
            }
            if (obj instanceof Container) {
                registerVariables(remoteContext, ((Container) obj).getList());
            }
            if (obj instanceof ComponentValue) {
                ComponentValue componentValue = (ComponentValue) obj;
                Component component2 = this.mComponentMap.get(Integer.valueOf(componentValue.getComponentId()));
                if (component2 != null) {
                    component2.addComponentValue(componentValue);
                } else {
                    System.out.println("=> Component not found for id " + componentValue.getComponentId());
                }
            }
            if (obj instanceof ComponentModifiers) {
                Iterator<ModifierOperation> it2 = ((ComponentModifiers) obj).getList().iterator();
                while (it2.hasNext()) {
                    ModifierOperation next = it2.next();
                    if (next instanceof VariableSupport) {
                        ((VariableSupport) next).registerListening(remoteContext);
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void applyOperations(RemoteContext remoteContext, ArrayList<Operation> arrayList) {
        Iterator<Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof VariableSupport) {
                ((VariableSupport) next).updateVariables(remoteContext);
            }
            if (next instanceof Component) {
                ((Component) next).updateVariables(remoteContext);
            }
            next.markNotDirty();
            next.apply(remoteContext);
            remoteContext.incrementOpCount();
            if (next instanceof Container) {
                applyOperations(remoteContext, ((Container) next).getList());
            }
        }
    }

    public void initializeContext(RemoteContext remoteContext) {
        this.mRemoteComposeState.reset();
        this.mRemoteComposeState.setContext(remoteContext);
        this.mClickAreas.clear();
        this.mRemoteComposeState.setNextId(42);
        remoteContext.mDocument = this;
        remoteContext.mRemoteComposeState = this.mRemoteComposeState;
        remoteContext.mMode = RemoteContext.ContextMode.DATA;
        this.mTimeVariables.updateTime(remoteContext);
        registerVariables(remoteContext, this.mOperations);
        applyOperations(remoteContext, this.mOperations);
        remoteContext.mMode = RemoteContext.ContextMode.UNSET;
    }

    public boolean canBeDisplayed(int i, int i2, long j) {
        if (this.mVersion.major < i) {
            return true;
        }
        return this.mVersion.major <= i && this.mVersion.minor <= i2;
    }

    public void setVersion(int i, int i2, int i3) {
        this.mVersion = new Version(i, i2, i3);
    }

    public void addClickArea(int i, String str, float f, float f2, float f3, float f4, String str2) {
        ClickAreaRepresentation clickAreaRepresentation = new ClickAreaRepresentation(i, str, f, f2, f3, f4, str2);
        this.mClickAreas.remove(clickAreaRepresentation);
        this.mClickAreas.add(clickAreaRepresentation);
    }

    public void addTouchListener(TouchListener touchListener) {
        this.mTouchListeners.add(touchListener);
    }

    public void addIdActionListener(IdActionCallback idActionCallback) {
        this.mIdActionListeners.add(idActionCallback);
    }

    public HashSet<IdActionCallback> getIdActionListeners() {
        return this.mIdActionListeners;
    }

    public void onClick(RemoteContext remoteContext, float f, float f2) {
        Iterator<ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            ClickAreaRepresentation next = it.next();
            if (next.contains(f, f2)) {
                warnClickListeners(next);
            }
        }
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        if (rootLayoutComponent != null) {
            rootLayoutComponent.onClick(remoteContext, this, f, f2);
        }
    }

    public void performClick(RemoteContext remoteContext, int i, String str) {
        Iterator<ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            ClickAreaRepresentation next = it.next();
            if (next.mId == i) {
                warnClickListeners(next);
                return;
            }
        }
        Iterator<IdActionCallback> it2 = this.mIdActionListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onAction(i, str);
        }
        Component component = getComponent(i);
        if (component != null) {
            component.onClick(remoteContext, this, -1.0f, -1.0f);
        }
    }

    private void warnClickListeners(ClickAreaRepresentation clickAreaRepresentation) {
        Iterator<IdActionCallback> it = this.mIdActionListeners.iterator();
        while (it.hasNext()) {
            it.next().onAction(clickAreaRepresentation.mId, clickAreaRepresentation.mMetadata);
        }
    }

    public boolean hasTouchListener() {
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        return (rootLayoutComponent != null && rootLayoutComponent.hasTouchListeners()) || !this.mTouchListeners.isEmpty();
    }

    public boolean touchDrag(RemoteContext remoteContext, float f, float f2) {
        CoreDocument coreDocument;
        remoteContext.loadFloat(13, f);
        remoteContext.loadFloat(14, f2);
        Iterator<TouchListener> it = this.mTouchListeners.iterator();
        while (it.hasNext()) {
            it.next().touchDrag(remoteContext, f, f2);
        }
        if (this.mRootLayoutComponent != null) {
            Iterator<Component> it2 = this.mAppliedTouchOperations.iterator();
            while (it2.hasNext()) {
                it2.next().onTouchDrag(remoteContext, this, f, f2, true);
            }
            coreDocument = this;
            if (!coreDocument.mAppliedTouchOperations.isEmpty()) {
                return true;
            }
        } else {
            coreDocument = this;
        }
        return !coreDocument.mTouchListeners.isEmpty();
    }

    public void touchDown(RemoteContext remoteContext, float f, float f2) {
        remoteContext.loadFloat(13, f);
        remoteContext.loadFloat(14, f2);
        Iterator<TouchListener> it = this.mTouchListeners.iterator();
        while (it.hasNext()) {
            it.next().touchDown(remoteContext, f, f2);
        }
        RootLayoutComponent rootLayoutComponent = this.mRootLayoutComponent;
        if (rootLayoutComponent != null) {
            rootLayoutComponent.onTouchDown(remoteContext, this, f, f2);
        }
        this.mRepaintNext = 1;
    }

    public void touchUp(RemoteContext remoteContext, float f, float f2, float f3, float f4) {
        remoteContext.loadFloat(13, f);
        remoteContext.loadFloat(14, f2);
        Iterator<TouchListener> it = this.mTouchListeners.iterator();
        while (it.hasNext()) {
            it.next().touchUp(remoteContext, f, f2, f3, f4);
        }
        if (this.mRootLayoutComponent != null) {
            Iterator<Component> it2 = this.mAppliedTouchOperations.iterator();
            while (it2.hasNext()) {
                it2.next().onTouchUp(remoteContext, this, f, f2, f3, f4, true);
            }
            this.mAppliedTouchOperations.clear();
        }
        this.mRepaintNext = 1;
    }

    public void touchCancel(RemoteContext remoteContext, float f, float f2, float f3, float f4) {
        CoreDocument coreDocument;
        if (this.mRootLayoutComponent != null) {
            Iterator<Component> it = this.mAppliedTouchOperations.iterator();
            while (it.hasNext()) {
                it.next().onTouchCancel(remoteContext, this, f, f2, true);
            }
            coreDocument = this;
            coreDocument.mAppliedTouchOperations.clear();
        } else {
            coreDocument = this;
        }
        coreDocument.mRepaintNext = 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    public String[] getNamedColors() {
        return getNamedVariables(2);
    }

    public String[] getNamedVariables(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        getNamedVars(i, this.mOperations, arrayList);
        return (String[]) arrayList.toArray(new String[0]);
    }

    private void getNamedVars(int i, ArrayList<Operation> arrayList, ArrayList<String> arrayList2) {
        Iterator<Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            if (obj instanceof NamedVariable) {
                NamedVariable namedVariable = (NamedVariable) obj;
                if (namedVariable.mVarType == i) {
                    arrayList2.add(namedVariable.mVarName);
                }
            }
            if (obj instanceof Container) {
                getNamedVars(i, ((Container) obj).getList(), arrayList2);
            }
        }
    }

    public int getOpsPerFrame() {
        return this.mLastOpCount;
    }

    public int needsRepaint() {
        return this.mRepaintNext;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateVariables(RemoteContext remoteContext, int i, List<Operation> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Operation operation = list.get(i2);
            if (operation.isDirty() && (operation instanceof VariableSupport)) {
                ((VariableSupport) operation).updateVariables(remoteContext);
                operation.apply(remoteContext);
                operation.markNotDirty();
            }
            if (operation instanceof Container) {
                updateVariables(remoteContext, i, ((Container) operation).getList());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void paint(RemoteContext remoteContext, int i) {
        CoreDocument coreDocument;
        RootLayoutComponent rootLayoutComponent;
        boolean isDirty;
        int theme;
        remoteContext.clearLastOpCount();
        remoteContext.getPaintContext().clearNeedsRepaint();
        remoteContext.loadFloat(27, remoteContext.getDensity());
        remoteContext.mMode = RemoteContext.ContextMode.UNSET;
        remoteContext.setTheme(-1);
        remoteContext.mRemoteComposeState = this.mRemoteComposeState;
        remoteContext.mRemoteComposeState.setContext(remoteContext);
        if (this.mContentSizing == 2) {
            computeScale(remoteContext.mWidth, remoteContext.mHeight, this.mScaleOutput);
            float[] fArr = this.mScaleOutput;
            float f = fArr[0];
            float f2 = fArr[1];
            coreDocument = this;
            coreDocument.computeTranslate(remoteContext.mWidth, remoteContext.mHeight, f, f2, this.mTranslateOutput);
            PaintContext paintContext = remoteContext.mPaintContext;
            float[] fArr2 = coreDocument.mTranslateOutput;
            paintContext.translate(fArr2[0], fArr2[1]);
            remoteContext.mPaintContext.scale(f, f2);
        } else {
            coreDocument = this;
            coreDocument.setWidth((int) remoteContext.mWidth);
            coreDocument.setHeight((int) remoteContext.mHeight);
        }
        coreDocument.mTimeVariables.updateTime(remoteContext);
        coreDocument.mRepaintNext = remoteContext.updateOps();
        if (coreDocument.mRootLayoutComponent != null) {
            if (remoteContext.mWidth != coreDocument.mRootLayoutComponent.getWidth() || remoteContext.mHeight != coreDocument.mRootLayoutComponent.getHeight()) {
                coreDocument.mRootLayoutComponent.invalidateMeasure();
            }
            if (coreDocument.mRootLayoutComponent.needsMeasure()) {
                coreDocument.mRootLayoutComponent.layout(remoteContext);
            }
            if (coreDocument.mRootLayoutComponent.needsBoundsAnimation()) {
                coreDocument.mRepaintNext = 1;
                coreDocument.mRootLayoutComponent.clearNeedsBoundsAnimation();
                coreDocument.mRootLayoutComponent.animatingBounds(remoteContext);
            }
            if (coreDocument.mRootLayoutComponent.doesNeedsRepaint()) {
                coreDocument.mRepaintNext = 1;
            }
        }
        remoteContext.mMode = RemoteContext.ContextMode.PAINT;
        for (int i2 = 0; i2 < coreDocument.mOperations.size(); i2++) {
            Operation operation = coreDocument.mOperations.get(i2);
            if ((i == -1 || (theme = remoteContext.getTheme()) == i || theme == -1 || (operation instanceof Theme)) && ((isDirty = operation.isDirty()) || (operation instanceof PaintOperation))) {
                if (isDirty && (operation instanceof VariableSupport)) {
                    operation.markNotDirty();
                    ((VariableSupport) operation).updateVariables(remoteContext);
                }
                remoteContext.incrementOpCount();
                operation.apply(remoteContext);
            }
        }
        if (remoteContext.getPaintContext().doesNeedsRepaint() || ((rootLayoutComponent = coreDocument.mRootLayoutComponent) != null && rootLayoutComponent.doesNeedsRepaint())) {
            coreDocument.mRepaintNext = 1;
        }
        remoteContext.mMode = RemoteContext.ContextMode.UNSET;
        coreDocument.mLastOpCount = remoteContext.getLastOpCount();
    }

    public int getNumberOfOps() {
        int size = this.mOperations.size();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                size += getChildOps((Component) next);
            }
        }
        return size;
    }

    private int getChildOps(Component component) {
        int size = component.mList.size();
        Iterator<Operation> it = component.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if (next instanceof Component) {
                size += (next instanceof LoopOperation ? ((LoopOperation) next).estimateIterations() : 1) * getChildOps((Component) next);
            }
        }
        return size;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String[] getStats() {
        int[] iArr;
        int addChildren;
        ArrayList arrayList = new ArrayList();
        WireBuffer wireBuffer = new WireBuffer();
        int size = this.mOperations.size();
        HashMap<String, int[]> hashMap = new HashMap<>();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            Class<?> cls = next.getClass();
            if (hashMap.containsKey(cls.getSimpleName())) {
                iArr = hashMap.get(cls.getSimpleName());
            } else {
                int[] iArr2 = new int[2];
                hashMap.put(cls.getSimpleName(), iArr2);
                iArr = iArr2;
            }
            iArr[0] = iArr[0] + 1;
            iArr[1] = iArr[1] + sizeOfComponent(next, wireBuffer);
            if (next instanceof Container) {
                addChildren = addChildren((Container) next, hashMap, wireBuffer);
            } else if (next instanceof LoopOperation) {
                addChildren = addChildren((LoopOperation) next, hashMap, wireBuffer);
            }
            size += addChildren;
        }
        arrayList.add(0, "number of operations : " + size);
        for (String str : hashMap.keySet()) {
            int[] iArr3 = hashMap.get(str);
            arrayList.add(str + " : " + iArr3[0] + ":" + iArr3[1]);
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private int sizeOfComponent(Operation operation, WireBuffer wireBuffer) {
        wireBuffer.reset(100);
        operation.write(wireBuffer);
        int size = wireBuffer.getSize();
        wireBuffer.reset(100);
        return size;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int addChildren(Container container, HashMap<String, int[]> hashMap, WireBuffer wireBuffer) {
        int[] iArr;
        int size = container.getList().size();
        Iterator<Operation> it = container.getList().iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            Class<?> cls = next.getClass();
            if (hashMap.containsKey(cls.getSimpleName())) {
                iArr = hashMap.get(cls.getSimpleName());
            } else {
                int[] iArr2 = new int[2];
                hashMap.put(cls.getSimpleName(), iArr2);
                iArr = iArr2;
            }
            iArr[0] = iArr[0] + 1;
            iArr[1] = iArr[1] + sizeOfComponent(next, wireBuffer);
            if (next instanceof Container) {
                size += addChildren((Container) next, hashMap, wireBuffer);
            }
        }
        return size;
    }

    public String toNestedString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            sb.append(obj.toString());
            sb.append(ShaderAssembler.NEWLINE);
            if (obj instanceof Container) {
                toNestedString((Container) obj, sb, "  ");
            }
        }
        return sb.toString();
    }

    private void toNestedString(Container container, StringBuilder sb, String str) {
        Iterator<Operation> it = container.getList().iterator();
        while (it.hasNext()) {
            Object obj = (Operation) it.next();
            for (String str2 : obj.toString().split(ShaderAssembler.NEWLINE)) {
                sb.append(str);
                sb.append(str2);
                sb.append(ShaderAssembler.NEWLINE);
            }
            if (obj instanceof Container) {
                toNestedString((Container) obj, sb, str + "  ");
            }
        }
    }

    public List<Operation> getOperations() {
        return this.mOperations;
    }

    public void checkShaders(RemoteContext remoteContext, ShaderControl shaderControl) {
        checkShaders(remoteContext, shaderControl, this.mOperations);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void checkShaders(RemoteContext remoteContext, ShaderControl shaderControl, List<Operation> list) {
        for (Operation operation : list) {
            if (operation instanceof TextData) {
                operation.apply(remoteContext);
            }
            if (operation instanceof Container) {
                checkShaders(remoteContext, shaderControl, ((Container) operation).getList());
            }
            if (operation instanceof ShaderData) {
                ShaderData shaderData = (ShaderData) operation;
                shaderData.enable(shaderControl.isShaderValid(remoteContext.getText(shaderData.getShaderTextId())));
            }
        }
    }

    public void setUpdateDoc(boolean z) {
        this.mIsUpdateDoc = z;
    }

    public boolean isUpdateDoc() {
        return this.mIsUpdateDoc;
    }
}
