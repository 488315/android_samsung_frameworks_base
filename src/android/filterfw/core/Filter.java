package android.filterfw.core;

import android.filterfw.format.ObjectFormat;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.TextGraphReader;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class Filter {
    static final int STATUS_ERROR = 6;
    static final int STATUS_FINISHED = 5;
    static final int STATUS_PREINIT = 0;
    static final int STATUS_PREPARED = 2;
    static final int STATUS_PROCESSING = 3;
    static final int STATUS_RELEASED = 7;
    static final int STATUS_SLEEPING = 4;
    static final int STATUS_UNPREPARED = 1;
    private static final String TAG = "Filter";
    private long mCurrentTimestamp;
    private HashMap<String, InputPort> mInputPorts;
    private String mName;
    private HashMap<String, OutputPort> mOutputPorts;
    private int mSleepDelay;
    private int mStatus;
    private int mInputCount = -1;
    private int mOutputCount = -1;
    private boolean mIsOpen = false;
    private HashSet<Frame> mFramesToRelease = new HashSet<>();
    private HashMap<String, Frame> mFramesToSet = new HashMap<>();
    private boolean mLogVerbose = Log.isLoggable(TAG, 2);

    public void close(FilterContext filterContext) {
    }

    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
    }

    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return null;
    }

    public final int getSleepDelay() {
        return 250;
    }

    public void open(FilterContext filterContext) {
    }

    protected void parametersUpdated(Set<String> set) {
    }

    protected void prepare(FilterContext filterContext) {
    }

    public abstract void process(FilterContext filterContext);

    public abstract void setupPorts();

    public void tearDown(FilterContext filterContext) {
    }

    public Filter(String str) {
        this.mStatus = 0;
        this.mName = str;
        this.mStatus = 0;
    }

    public static final boolean isAvailable(String str) {
        try {
            return Filter.class.isAssignableFrom(Thread.currentThread().getContextClassLoader().loadClass(str));
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public final void initWithValueMap(KeyValueMap keyValueMap) {
        initFinalPorts(keyValueMap);
        initRemainingPorts(keyValueMap);
        this.mStatus = 1;
    }

    public final void initWithAssignmentString(String str) {
        try {
            initWithValueMap(new TextGraphReader().readKeyValueAssignments(str));
        } catch (GraphIOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public final void initWithAssignmentList(Object... objArr) {
        KeyValueMap keyValueMap = new KeyValueMap();
        keyValueMap.setKeyValues(objArr);
        initWithValueMap(keyValueMap);
    }

    public final void init() throws ProtocolException {
        initWithValueMap(new KeyValueMap());
    }

    public String getFilterClassName() {
        return getClass().getSimpleName();
    }

    public final String getName() {
        return this.mName;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public void setInputFrame(String str, Frame frame) {
        InputPort inputPort = getInputPort(str);
        if (!inputPort.isOpen()) {
            inputPort.open();
        }
        inputPort.setFrame(frame);
    }

    public final void setInputValue(String str, Object obj) {
        setInputFrame(str, wrapInputValue(str, obj));
    }

    protected void delayNextProcess(int i) {
        this.mSleepDelay = i;
        this.mStatus = 4;
    }

    public final FrameFormat getInputFormat(String str) {
        return getInputPort(str).getSourceFormat();
    }

    public final int getNumberOfConnectedInputs() {
        Iterator<InputPort> it = this.mInputPorts.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isConnected()) {
                i++;
            }
        }
        return i;
    }

    public final int getNumberOfConnectedOutputs() {
        Iterator<OutputPort> it = this.mOutputPorts.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isConnected()) {
                i++;
            }
        }
        return i;
    }

    public final int getNumberOfInputs() {
        if (this.mOutputPorts == null) {
            return 0;
        }
        return this.mInputPorts.size();
    }

    public final int getNumberOfOutputs() {
        if (this.mInputPorts == null) {
            return 0;
        }
        return this.mOutputPorts.size();
    }

    public final InputPort getInputPort(String str) {
        HashMap<String, InputPort> hashMap = this.mInputPorts;
        if (hashMap == null) {
            throw new NullPointerException("Attempting to access input port '" + str + "' of " + this + " before Filter has been initialized!");
        }
        InputPort inputPort = hashMap.get(str);
        if (inputPort != null) {
            return inputPort;
        }
        throw new IllegalArgumentException("Unknown input port '" + str + "' on filter " + this + "!");
    }

    public final OutputPort getOutputPort(String str) {
        if (this.mInputPorts == null) {
            throw new NullPointerException("Attempting to access output port '" + str + "' of " + this + " before Filter has been initialized!");
        }
        OutputPort outputPort = this.mOutputPorts.get(str);
        if (outputPort != null) {
            return outputPort;
        }
        throw new IllegalArgumentException("Unknown output port '" + str + "' on filter " + this + "!");
    }

    protected final void pushOutput(String str, Frame frame) {
        if (frame.getTimestamp() == -2) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Default-setting output Frame timestamp on port " + str + " to " + this.mCurrentTimestamp);
            }
            frame.setTimestamp(this.mCurrentTimestamp);
        }
        getOutputPort(str).pushFrame(frame);
    }

    protected final Frame pullInput(String str) {
        Frame pullFrame = getInputPort(str).pullFrame();
        if (this.mCurrentTimestamp == -1) {
            this.mCurrentTimestamp = pullFrame.getTimestamp();
            if (this.mLogVerbose) {
                Log.v(TAG, "Default-setting current timestamp from input port " + str + " to " + this.mCurrentTimestamp);
            }
        }
        this.mFramesToRelease.add(pullFrame);
        return pullFrame;
    }

    protected void transferInputPortFrame(String str, FilterContext filterContext) {
        getInputPort(str).transfer(filterContext);
    }

    protected void initProgramInputs(Program program, FilterContext filterContext) {
        if (program != null) {
            for (InputPort inputPort : this.mInputPorts.values()) {
                if (inputPort.getTarget() == program) {
                    inputPort.transfer(filterContext);
                }
            }
        }
    }

    protected void addInputPort(String str) {
        addMaskedInputPort(str, null);
    }

    protected void addMaskedInputPort(String str, FrameFormat frameFormat) {
        StreamPort streamPort = new StreamPort(this, str);
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter " + this + " adding " + streamPort);
        }
        this.mInputPorts.put(str, streamPort);
        streamPort.setPortFormat(frameFormat);
    }

    protected void addOutputPort(String str, FrameFormat frameFormat) {
        OutputPort outputPort = new OutputPort(this, str);
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter " + this + " adding " + outputPort);
        }
        outputPort.setPortFormat(frameFormat);
        this.mOutputPorts.put(str, outputPort);
    }

    protected void addOutputBasedOnInput(String str, String str2) {
        OutputPort outputPort = new OutputPort(this, str);
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter " + this + " adding " + outputPort);
        }
        outputPort.setBasePort(getInputPort(str2));
        this.mOutputPorts.put(str, outputPort);
    }

    protected void addFieldPort(String str, Field field, boolean z, boolean z2) {
        InputPort fieldPort;
        field.setAccessible(true);
        if (z2) {
            fieldPort = new FinalPort(this, str, field, z);
        } else {
            fieldPort = new FieldPort(this, str, field, z);
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter " + this + " adding " + fieldPort);
        }
        fieldPort.setPortFormat(ObjectFormat.fromClass(field.getType(), 1));
        this.mInputPorts.put(str, fieldPort);
    }

    protected void addProgramPort(String str, String str2, Field field, Class cls, boolean z) {
        field.setAccessible(true);
        ProgramPort programPort = new ProgramPort(this, str, str2, field, z);
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter " + this + " adding " + programPort);
        }
        programPort.setPortFormat(ObjectFormat.fromClass(cls, 1));
        this.mInputPorts.put(str, programPort);
    }

    protected void closeOutputPort(String str) {
        getOutputPort(str).close();
    }

    protected void setWaitsOnInputPort(String str, boolean z) {
        getInputPort(str).setBlocking(z);
    }

    protected void setWaitsOnOutputPort(String str, boolean z) {
        getOutputPort(str).setBlocking(z);
    }

    public String toString() {
        return "'" + getName() + "' (" + getFilterClassName() + NavigationBarInflaterView.KEY_CODE_END;
    }

    final Collection<InputPort> getInputPorts() {
        return this.mInputPorts.values();
    }

    final Collection<OutputPort> getOutputPorts() {
        return this.mOutputPorts.values();
    }

    final synchronized int getStatus() {
        return this.mStatus;
    }

    final synchronized void unsetStatus(int i) {
        this.mStatus = (~i) & this.mStatus;
    }

    final synchronized void performOpen(FilterContext filterContext) {
        if (!this.mIsOpen) {
            if (this.mStatus == 1) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Preparing " + this);
                }
                prepare(filterContext);
                this.mStatus = 2;
            }
            if (this.mStatus == 2) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Opening " + this);
                }
                open(filterContext);
                this.mStatus = 3;
            }
            if (this.mStatus != 3) {
                throw new RuntimeException("Filter " + this + " was brought into invalid state during opening (state: " + this.mStatus + ")!");
            }
            this.mIsOpen = true;
        }
    }

    final synchronized void performProcess(FilterContext filterContext) {
        if (this.mStatus == 7) {
            throw new RuntimeException("Filter " + this + " is already torn down!");
        }
        transferInputFrames(filterContext);
        if (this.mStatus < 3) {
            performOpen(filterContext);
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Processing " + this);
        }
        this.mCurrentTimestamp = -1L;
        process(filterContext);
        releasePulledFrames(filterContext);
        if (filterMustClose()) {
            performClose(filterContext);
        }
    }

    final synchronized void performClose(FilterContext filterContext) {
        if (this.mIsOpen) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Closing " + this);
            }
            this.mIsOpen = false;
            this.mStatus = 2;
            close(filterContext);
            closePorts();
        }
    }

    final synchronized void performTearDown(FilterContext filterContext) {
        performClose(filterContext);
        if (this.mStatus != 7) {
            tearDown(filterContext);
            this.mStatus = 7;
        }
    }

    final synchronized boolean canProcess() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Checking if can process: " + this + " (" + this.mStatus + ").");
        }
        boolean z = false;
        if (this.mStatus > 3) {
            return false;
        }
        if (inputConditionsMet()) {
            if (outputConditionsMet()) {
                z = true;
            }
        }
        return z;
    }

    final void openOutputs() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Opening all output ports on " + this + "!");
        }
        for (OutputPort outputPort : this.mOutputPorts.values()) {
            if (!outputPort.isOpen()) {
                outputPort.open();
            }
        }
    }

    final void clearInputs() {
        Iterator<InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    final void clearOutputs() {
        Iterator<OutputPort> it = this.mOutputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    final void notifyFieldPortValueUpdated(String str, FilterContext filterContext) {
        int i = this.mStatus;
        if (i == 3 || i == 2) {
            fieldPortValueUpdated(str, filterContext);
        }
    }

    final synchronized void pushInputFrame(String str, Frame frame) {
        InputPort inputPort = getInputPort(str);
        if (!inputPort.isOpen()) {
            inputPort.open();
        }
        inputPort.pushFrame(frame);
    }

    final synchronized void pushInputValue(String str, Object obj) {
        pushInputFrame(str, wrapInputValue(str, obj));
    }

    private final void initFinalPorts(KeyValueMap keyValueMap) {
        this.mInputPorts = new HashMap<>();
        this.mOutputPorts = new HashMap<>();
        addAndSetFinalPorts(keyValueMap);
    }

    private final void initRemainingPorts(KeyValueMap keyValueMap) {
        addAnnotatedPorts();
        setupPorts();
        setInitialInputValues(keyValueMap);
    }

    private final void addAndSetFinalPorts(KeyValueMap keyValueMap) {
        for (Field field : getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(GenerateFinalPort.class);
            if (annotation != null) {
                GenerateFinalPort generateFinalPort = (GenerateFinalPort) annotation;
                String name = generateFinalPort.name().isEmpty() ? field.getName() : generateFinalPort.name();
                addFieldPort(name, field, generateFinalPort.hasDefault(), true);
                if (keyValueMap.containsKey(name)) {
                    setImmediateInputValue(name, keyValueMap.get(name));
                    keyValueMap.remove(name);
                } else if (!generateFinalPort.hasDefault()) {
                    throw new RuntimeException("No value specified for final input port '" + name + "' of filter " + this + "!");
                }
            }
        }
    }

    private final void addAnnotatedPorts() {
        for (Field field : getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(GenerateFieldPort.class);
            if (annotation != null) {
                addFieldGenerator((GenerateFieldPort) annotation, field);
            } else {
                Annotation annotation2 = field.getAnnotation(GenerateProgramPort.class);
                if (annotation2 != null) {
                    addProgramGenerator((GenerateProgramPort) annotation2, field);
                } else {
                    Annotation annotation3 = field.getAnnotation(GenerateProgramPorts.class);
                    if (annotation3 != null) {
                        for (GenerateProgramPort generateProgramPort : ((GenerateProgramPorts) annotation3).value()) {
                            addProgramGenerator(generateProgramPort, field);
                        }
                    }
                }
            }
        }
    }

    private final void addFieldGenerator(GenerateFieldPort generateFieldPort, Field field) {
        addFieldPort(generateFieldPort.name().isEmpty() ? field.getName() : generateFieldPort.name(), field, generateFieldPort.hasDefault(), false);
    }

    private final void addProgramGenerator(GenerateProgramPort generateProgramPort, Field field) {
        String name = generateProgramPort.name();
        addProgramPort(name, generateProgramPort.variableName().isEmpty() ? name : generateProgramPort.variableName(), field, generateProgramPort.type(), generateProgramPort.hasDefault());
    }

    private final void setInitialInputValues(KeyValueMap keyValueMap) {
        for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
            setInputValue(entry.getKey(), entry.getValue());
        }
    }

    private final void setImmediateInputValue(String str, Object obj) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Setting immediate value " + obj + " for port " + str + "!");
        }
        InputPort inputPort = getInputPort(str);
        inputPort.open();
        inputPort.setFrame(SimpleFrame.wrapObject(obj, null));
    }

    private final void transferInputFrames(FilterContext filterContext) {
        Iterator<InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().transfer(filterContext);
        }
    }

    private final Frame wrapInputValue(String str, Object obj) {
        Frame simpleFrame;
        MutableFrameFormat fromObject = ObjectFormat.fromObject(obj, 1);
        if (obj == null) {
            FrameFormat portFormat = getInputPort(str).getPortFormat();
            fromObject.setObjectClass(portFormat == null ? null : portFormat.getObjectClass());
        }
        if (!(obj instanceof Number) && !(obj instanceof Boolean) && !(obj instanceof String) && (obj instanceof Serializable)) {
            simpleFrame = new SerializedFrame(fromObject, null);
        } else {
            simpleFrame = new SimpleFrame(fromObject, null);
        }
        simpleFrame.setObjectValue(obj);
        return simpleFrame;
    }

    private final void releasePulledFrames(FilterContext filterContext) {
        Iterator<Frame> it = this.mFramesToRelease.iterator();
        while (it.hasNext()) {
            filterContext.getFrameManager().releaseFrame(it.next());
        }
        this.mFramesToRelease.clear();
    }

    private final boolean inputConditionsMet() {
        for (InputPort inputPort : this.mInputPorts.values()) {
            if (!inputPort.isReady()) {
                if (!this.mLogVerbose) {
                    return false;
                }
                Log.v(TAG, "Input condition not met: " + inputPort + "!");
                return false;
            }
        }
        return true;
    }

    private final boolean outputConditionsMet() {
        for (OutputPort outputPort : this.mOutputPorts.values()) {
            if (!outputPort.isReady()) {
                if (!this.mLogVerbose) {
                    return false;
                }
                Log.v(TAG, "Output condition not met: " + outputPort + "!");
                return false;
            }
        }
        return true;
    }

    private final void closePorts() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing all ports on " + this + "!");
        }
        Iterator<InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        Iterator<OutputPort> it2 = this.mOutputPorts.values().iterator();
        while (it2.hasNext()) {
            it2.next().close();
        }
    }

    private final boolean filterMustClose() {
        for (InputPort inputPort : this.mInputPorts.values()) {
            if (inputPort.filterMustClose()) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Filter " + this + " must close due to port " + inputPort);
                }
                return true;
            }
        }
        for (OutputPort outputPort : this.mOutputPorts.values()) {
            if (outputPort.filterMustClose()) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Filter " + this + " must close due to port " + outputPort);
                }
                return true;
            }
        }
        return false;
    }
}
