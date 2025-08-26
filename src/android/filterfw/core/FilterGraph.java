package android.filterfw.core;

import android.filterpacks.base.FrameBranch;
import android.filterpacks.base.NullFilter;
import android.media.MediaMetrics;
import android.telecom.Logging.Session;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/* loaded from: classes.dex */
public class FilterGraph {
    public static final int AUTOBRANCH_OFF = 0;
    public static final int AUTOBRANCH_SYNCED = 1;
    public static final int AUTOBRANCH_UNSYNCED = 2;
    public static final int TYPECHECK_DYNAMIC = 1;
    public static final int TYPECHECK_OFF = 0;
    public static final int TYPECHECK_STRICT = 2;
    private HashSet<Filter> mFilters = new HashSet<>();
    private HashMap<String, Filter> mNameMap = new HashMap<>();
    private HashMap<OutputPort, LinkedList<InputPort>> mPreconnections = new HashMap<>();
    private boolean mIsReady = false;
    private int mAutoBranchMode = 0;
    private int mTypeCheckMode = 2;
    private boolean mDiscardUnconnectedOutputs = false;
    private String TAG = "FilterGraph";
    private boolean mLogVerbose = Log.isLoggable("FilterGraph", 2);

    private void checkConnections() {
    }

    public boolean addFilter(Filter filter) {
        if (containsFilter(filter)) {
            return false;
        }
        this.mFilters.add(filter);
        this.mNameMap.put(filter.getName(), filter);
        return true;
    }

    public boolean containsFilter(Filter filter) {
        return this.mFilters.contains(filter);
    }

    public Filter getFilter(String str) {
        return this.mNameMap.get(str);
    }

    public void connect(Filter filter, String str, Filter filter2, String str2) {
        if (filter == null || filter2 == null) {
            throw new IllegalArgumentException("Passing null Filter in connect()!");
        }
        if (!containsFilter(filter) || !containsFilter(filter2)) {
            throw new RuntimeException("Attempting to connect filter not in graph!");
        }
        OutputPort outputPort = filter.getOutputPort(str);
        InputPort inputPort = filter2.getInputPort(str2);
        if (outputPort == null) {
            throw new RuntimeException("Unknown output port '" + str + "' on Filter " + filter + "!");
        }
        if (inputPort == null) {
            throw new RuntimeException("Unknown input port '" + str2 + "' on Filter " + filter2 + "!");
        }
        preconnect(outputPort, inputPort);
    }

    public void connect(String str, String str2, String str3, String str4) {
        Filter filter = getFilter(str);
        Filter filter2 = getFilter(str3);
        if (filter == null) {
            throw new RuntimeException("Attempting to connect unknown source filter '" + str + "'!");
        }
        if (filter2 == null) {
            throw new RuntimeException("Attempting to connect unknown target filter '" + str3 + "'!");
        }
        connect(filter, str2, filter2, str4);
    }

    public Set<Filter> getFilters() {
        return this.mFilters;
    }

    public void beginProcessing() {
        if (this.mLogVerbose) {
            Log.v(this.TAG, "Opening all filter connections...");
        }
        Iterator<Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().openOutputs();
        }
        this.mIsReady = true;
    }

    public void flushFrames() {
        Iterator<Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().clearOutputs();
        }
    }

    public void closeFilters(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(this.TAG, "Closing all filters...");
        }
        Iterator<Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().performClose(filterContext);
        }
        this.mIsReady = false;
    }

    public boolean isReady() {
        return this.mIsReady;
    }

    public void setAutoBranchMode(int i) {
        this.mAutoBranchMode = i;
    }

    public void setDiscardUnconnectedOutputs(boolean z) {
        this.mDiscardUnconnectedOutputs = z;
    }

    public void setTypeCheckMode(int i) {
        this.mTypeCheckMode = i;
    }

    public void tearDown(FilterContext filterContext) {
        if (this.mFilters.isEmpty()) {
            return;
        }
        flushFrames();
        Iterator<Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().performTearDown(filterContext);
        }
        this.mFilters.clear();
        this.mNameMap.clear();
        this.mIsReady = false;
    }

    private boolean readyForProcessing(Filter filter, Set<Filter> set) {
        if (set.contains(filter)) {
            return false;
        }
        Iterator<InputPort> it = filter.getInputPorts().iterator();
        while (it.hasNext()) {
            Filter sourceFilter = it.next().getSourceFilter();
            if (sourceFilter != null && !set.contains(sourceFilter)) {
                return false;
            }
        }
        return true;
    }

    private void runTypeCheck() {
        Stack stack = new Stack();
        HashSet hashSet = new HashSet();
        stack.addAll(getSourceFilters());
        while (!stack.empty()) {
            Filter filter = (Filter) stack.pop();
            hashSet.add(filter);
            updateOutputs(filter);
            if (this.mLogVerbose) {
                Log.v(this.TAG, "Running type check on " + filter + Session.TRUNCATE_STRING);
            }
            runTypeCheckOn(filter);
            Iterator<OutputPort> it = filter.getOutputPorts().iterator();
            while (it.hasNext()) {
                Filter targetFilter = it.next().getTargetFilter();
                if (targetFilter != null && readyForProcessing(targetFilter, hashSet)) {
                    stack.push(targetFilter);
                }
            }
        }
        if (hashSet.size() != getFilters().size()) {
            throw new RuntimeException("Could not schedule all filters! Is your graph malformed?");
        }
    }

    private void updateOutputs(Filter filter) {
        for (OutputPort outputPort : filter.getOutputPorts()) {
            InputPort basePort = outputPort.getBasePort();
            if (basePort != null) {
                FrameFormat outputFormat = filter.getOutputFormat(outputPort.getName(), basePort.getSourceFormat());
                if (outputFormat == null) {
                    throw new RuntimeException("Filter did not return an output format for " + outputPort + "!");
                }
                outputPort.setPortFormat(outputFormat);
            }
        }
    }

    private void runTypeCheckOn(Filter filter) {
        for (InputPort inputPort : filter.getInputPorts()) {
            if (this.mLogVerbose) {
                Log.v(this.TAG, "Type checking port " + inputPort);
            }
            FrameFormat sourceFormat = inputPort.getSourceFormat();
            FrameFormat portFormat = inputPort.getPortFormat();
            if (sourceFormat != null && portFormat != null) {
                if (this.mLogVerbose) {
                    Log.v(this.TAG, "Checking " + sourceFormat + " against " + portFormat + MediaMetrics.SEPARATOR);
                }
                int i = this.mTypeCheckMode;
                boolean zIsCompatibleWith = true;
                if (i == 0) {
                    inputPort.setChecksType(false);
                } else if (i == 1) {
                    boolean zMayBeCompatibleWith = sourceFormat.mayBeCompatibleWith(portFormat);
                    inputPort.setChecksType(true);
                    zIsCompatibleWith = zMayBeCompatibleWith;
                } else if (i == 2) {
                    zIsCompatibleWith = sourceFormat.isCompatibleWith(portFormat);
                    inputPort.setChecksType(false);
                }
                if (!zIsCompatibleWith) {
                    throw new RuntimeException("Type mismatch: Filter " + filter + " expects a format of type " + portFormat + " but got a format of type " + sourceFormat + "!");
                }
            }
        }
    }

    private void discardUnconnectedOutputs() {
        LinkedList linkedList = new LinkedList();
        Iterator<Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            Filter next = it.next();
            int i = 0;
            for (OutputPort outputPort : next.getOutputPorts()) {
                if (!outputPort.isConnected()) {
                    if (this.mLogVerbose) {
                        Log.v(this.TAG, "Autoconnecting unconnected " + outputPort + " to Null filter.");
                    }
                    NullFilter nullFilter = new NullFilter(next.getName() + "ToNull" + i);
                    nullFilter.init();
                    linkedList.add(nullFilter);
                    outputPort.connectTo(nullFilter.getInputPort("frame"));
                    i++;
                }
            }
        }
        Iterator it2 = linkedList.iterator();
        while (it2.hasNext()) {
            addFilter((Filter) it2.next());
        }
    }

    private void removeFilter(Filter filter) {
        this.mFilters.remove(filter);
        this.mNameMap.remove(filter.getName());
    }

    private void preconnect(OutputPort outputPort, InputPort inputPort) {
        LinkedList<InputPort> linkedList = this.mPreconnections.get(outputPort);
        if (linkedList == null) {
            linkedList = new LinkedList<>();
            this.mPreconnections.put(outputPort, linkedList);
        }
        linkedList.add(inputPort);
    }

    private void connectPorts() {
        int i = 1;
        for (Map.Entry<OutputPort, LinkedList<InputPort>> entry : this.mPreconnections.entrySet()) {
            OutputPort key = entry.getKey();
            LinkedList<InputPort> value = entry.getValue();
            if (value.size() == 1) {
                key.connectTo(value.get(0));
            } else {
                if (this.mAutoBranchMode == 0) {
                    throw new RuntimeException("Attempting to connect " + key + " to multiple filter ports! Enable auto-branching to allow this.");
                }
                if (this.mLogVerbose) {
                    Log.v(this.TAG, "Creating branch for " + key + "!");
                }
                if (this.mAutoBranchMode == 1) {
                    StringBuilder sb = new StringBuilder("branch");
                    int i2 = i + 1;
                    sb.append(i);
                    FrameBranch frameBranch = new FrameBranch(sb.toString());
                    new KeyValueMap();
                    frameBranch.initWithAssignmentList("outputs", Integer.valueOf(value.size()));
                    addFilter(frameBranch);
                    key.connectTo(frameBranch.getInputPort("in"));
                    Iterator<InputPort> it = value.iterator();
                    Iterator<OutputPort> it2 = frameBranch.getOutputPorts().iterator();
                    while (it2.hasNext()) {
                        it2.next().connectTo(it.next());
                    }
                    i = i2;
                } else {
                    throw new RuntimeException("TODO: Unsynced branches not implemented yet!");
                }
            }
        }
        this.mPreconnections.clear();
    }

    private HashSet<Filter> getSourceFilters() {
        HashSet<Filter> hashSet = new HashSet<>();
        for (Filter filter : getFilters()) {
            if (filter.getNumberOfConnectedInputs() == 0) {
                if (this.mLogVerbose) {
                    Log.v(this.TAG, "Found source filter: " + filter);
                }
                hashSet.add(filter);
            }
        }
        return hashSet;
    }

    void setupFilters() {
        if (this.mDiscardUnconnectedOutputs) {
            discardUnconnectedOutputs();
        }
        connectPorts();
        checkConnections();
        runTypeCheck();
    }
}
