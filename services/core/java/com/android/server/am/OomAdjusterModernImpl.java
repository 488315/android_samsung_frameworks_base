package com.android.server.am;

import android.os.SystemClock;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OomAdjusterModernImpl extends OomAdjuster {
    public static final int[] ADJ_SLOT_VALUES = {-1000, -900, -800, -700, 0, 50, 100, 200, 225, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE, 300, 400, 500, 600, 700, 800, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, 1001};
    public final ComputeConnectionIgnoringReachableClientsConsumer mComputeConnectionIgnoringReachableClientsConsumer;
    public final ComputeConnectionsConsumer mComputeConnectionsConsumer;
    public final ComputeHostConsumer mComputeHostConsumer;
    public final ProcessRecordNodes mProcessRecordAdjNodes;
    public final ProcessRecordNodes mProcessRecordProcStateNodes;
    public final ReachableCollectingConsumer mReachableCollectingConsumer;
    public final OomAdjusterArgs mTmpOomAdjusterArgs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComputeConnectionIgnoringReachableClientsConsumer implements BiConsumer {
        public boolean hasReachableClient;
        public OomAdjusterArgs mArgs = null;

        public ComputeConnectionIgnoringReachableClientsConsumer() {
        }

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            Connection connection = (Connection) obj;
            ProcessRecord processRecord = (ProcessRecord) obj2;
            OomAdjusterArgs oomAdjusterArgs = this.mArgs;
            ProcessRecord processRecord2 = oomAdjusterArgs.mApp;
            ProcessRecord processRecord3 = oomAdjusterArgs.mTopApp;
            long j = oomAdjusterArgs.mNow;
            int i = oomAdjusterArgs.mOomAdjReason;
            if (processRecord.mState.mReachable) {
                return;
            }
            Flags.skipUnimportantConnections();
            connection.computeHostOomAdjLSP(OomAdjusterModernImpl.this, processRecord2, processRecord, j, processRecord3, false, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComputeConnectionsConsumer implements Consumer {
        public ComputeConnectionsConsumer() {
        }

        @Override // java.util.function.Consumer
        public final void accept(OomAdjusterArgs oomAdjusterArgs) {
            UidRecord uidRecord;
            ProcessRecord processRecord = oomAdjusterArgs.mApp;
            ActiveUids activeUids = oomAdjusterArgs.mUids;
            processRecord.mState.mCompletedAdjSeq = OomAdjusterModernImpl.this.mAdjSeq;
            if (activeUids != null && (uidRecord = processRecord.mUidRecord) != null) {
                activeUids.put(uidRecord.mUid, uidRecord);
            }
            ComputeHostConsumer computeHostConsumer = OomAdjusterModernImpl.this.mComputeHostConsumer;
            computeHostConsumer.args = oomAdjusterArgs;
            OomAdjusterModernImpl.forEachConnectionLSP(processRecord, computeHostConsumer);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComputeHostConsumer implements BiConsumer {
        public OomAdjusterArgs args = null;

        public ComputeHostConsumer() {
        }

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            Connection connection = (Connection) obj;
            ProcessRecord processRecord = (ProcessRecord) obj2;
            OomAdjusterArgs oomAdjusterArgs = this.args;
            ProcessRecord processRecord2 = oomAdjusterArgs.mApp;
            ProcessRecord processRecord3 = oomAdjusterArgs.mTopApp;
            long j = oomAdjusterArgs.mNow;
            int i = oomAdjusterArgs.mOomAdjReason;
            boolean z = oomAdjusterArgs.mFullUpdate;
            ProcessStateRecord processStateRecord = processRecord.mState;
            int i2 = processStateRecord.mCurProcState;
            int i3 = processStateRecord.mCurRawAdj;
            Flags.skipUnimportantConnections();
            connection.computeHostOomAdjLSP(OomAdjusterModernImpl.this, processRecord, processRecord2, j, processRecord3, z, i);
            OomAdjusterModernImpl oomAdjusterModernImpl = OomAdjusterModernImpl.this;
            oomAdjusterModernImpl.getClass();
            if (processRecord.mState.mCurProcState != i2) {
                Flags.simplifyProcessTraversal();
                oomAdjusterModernImpl.mProcessRecordProcStateNodes.offer(processRecord);
            }
            OomAdjusterModernImpl oomAdjusterModernImpl2 = OomAdjusterModernImpl.this;
            oomAdjusterModernImpl2.getClass();
            if (processRecord.mState.mCurRawAdj != i3) {
                Flags.simplifyProcessTraversal();
                oomAdjusterModernImpl2.mProcessRecordAdjNodes.offer(processRecord);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Connection {
        void computeHostOomAdjLSP(OomAdjuster oomAdjuster, ProcessRecord processRecord, ProcessRecord processRecord2, long j, ProcessRecord processRecord3, boolean z, int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OomAdjusterArgs {
        public ProcessRecord mApp;
        public int mCachedAdj;
        public boolean mFullUpdate;
        public long mNow;
        public int mOomAdjReason;
        public ProcessRecord mTopApp;
        public ActiveUids mUids;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessRecordNode {
        public final ProcessRecord mApp;
        public ProcessRecordNode mNext;
        public ProcessRecordNode mPrev;

        public ProcessRecordNode(ProcessRecord processRecord) {
            this.mApp = processRecord;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ProcessRecordNode{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            ProcessRecord processRecord = this.mApp;
            sb.append(processRecord);
            sb.append(' ');
            sb.append(processRecord != null ? processRecord.mState.mCurProcState : -1);
            sb.append(' ');
            sb.append(processRecord != null ? processRecord.mState.mCurAdj : 1001);
            sb.append(' ');
            sb.append(Integer.toHexString(System.identityHashCode(this.mPrev)));
            sb.append(' ');
            sb.append(Integer.toHexString(System.identityHashCode(this.mNext)));
            sb.append('}');
            return sb.toString();
        }

        public final void unlink() {
            ProcessRecordNode processRecordNode = this.mPrev;
            if (processRecordNode != null) {
                processRecordNode.mNext = this.mNext;
            }
            ProcessRecordNode processRecordNode2 = this.mNext;
            if (processRecordNode2 != null) {
                processRecordNode2.mPrev = processRecordNode;
            }
            this.mNext = null;
            this.mPrev = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessRecordNodes {
        public int mFirstPopulatedSlot = 0;
        public final ProcessRecordNode[] mLastNode;
        public final LinkedProcessRecordList[] mProcessRecordNodes;
        public final ToIntFunction mSlotFunction;
        public final int mType;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class LinkedProcessRecordList {
            public final ProcessRecordNode HEAD;
            public final ProcessRecordNode TAIL;
            public final ToIntFunction mValueFunction;

            public LinkedProcessRecordList(ToIntFunction toIntFunction) {
                ProcessRecordNode processRecordNode = new ProcessRecordNode(null);
                this.HEAD = processRecordNode;
                ProcessRecordNode processRecordNode2 = new ProcessRecordNode(null);
                this.TAIL = processRecordNode2;
                processRecordNode.mNext = processRecordNode2;
                processRecordNode2.mPrev = processRecordNode;
                this.mValueFunction = toIntFunction;
            }

            public void reset() {
                ProcessRecordNode processRecordNode = this.HEAD;
                ProcessRecordNode processRecordNode2 = processRecordNode.mNext;
                ProcessRecordNode processRecordNode3 = this.TAIL;
                if (processRecordNode2 != processRecordNode3) {
                    processRecordNode3.mPrev.mNext = null;
                    processRecordNode2.mPrev = null;
                }
                processRecordNode.mNext = processRecordNode3;
                processRecordNode3.mPrev = processRecordNode;
            }
        }

        public ProcessRecordNodes(OomAdjusterModernImpl oomAdjusterModernImpl, int i, int i2) {
            ToIntFunction toIntFunction;
            this.mType = i;
            if (i == 0) {
                final int i3 = 0;
                toIntFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i3) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i4 = processRecord.mState.mCurProcState;
                                if (i4 < 0 || i4 > 19) {
                                    return 20;
                                }
                                return i4;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i5 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i5 < iArr[0] || i5 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i5);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
                final int i4 = 1;
                this.mSlotFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i4) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i42 = processRecord.mState.mCurProcState;
                                if (i42 < 0 || i42 > 19) {
                                    return 20;
                                }
                                return i42;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i5 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i5 < iArr[0] || i5 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i5);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
            } else if (i != 1) {
                final int i5 = 4;
                toIntFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i5) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i42 = processRecord.mState.mCurProcState;
                                if (i42 < 0 || i42 > 19) {
                                    return 20;
                                }
                                return i42;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i52 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i52 < iArr[0] || i52 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i52);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
                final int i6 = 4;
                this.mSlotFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i6) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i42 = processRecord.mState.mCurProcState;
                                if (i42 < 0 || i42 > 19) {
                                    return 20;
                                }
                                return i42;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i52 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i52 < iArr[0] || i52 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i52);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
            } else {
                final int i7 = 2;
                toIntFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i7) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i42 = processRecord.mState.mCurProcState;
                                if (i42 < 0 || i42 > 19) {
                                    return 20;
                                }
                                return i42;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i52 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i52 < iArr[0] || i52 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i52);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
                final int i8 = 3;
                this.mSlotFunction = new ToIntFunction() { // from class: com.android.server.am.OomAdjusterModernImpl$ProcessRecordNodes$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        ProcessRecord processRecord = (ProcessRecord) obj;
                        switch (i8) {
                            case 0:
                                return processRecord.mState.mCurProcState;
                            case 1:
                                int i42 = processRecord.mState.mCurProcState;
                                if (i42 < 0 || i42 > 19) {
                                    return 20;
                                }
                                return i42;
                            case 2:
                                return processRecord.mState.mCurRawAdj;
                            case 3:
                                int i52 = processRecord.mState.mCurRawAdj;
                                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                                if (i52 < iArr[0] || i52 > iArr[17]) {
                                    return 17;
                                }
                                int binarySearch = Arrays.binarySearch(iArr, i52);
                                return binarySearch >= 0 ? binarySearch : (-(binarySearch + 1)) - 1;
                            default:
                                return 0;
                        }
                    }
                };
            }
            this.mProcessRecordNodes = new LinkedProcessRecordList[i2];
            for (int i9 = 0; i9 < i2; i9++) {
                this.mProcessRecordNodes[i9] = new LinkedProcessRecordList(toIntFunction);
            }
            this.mLastNode = new ProcessRecordNode[i2];
            Flags.simplifyProcessTraversal();
            reset();
        }

        public final void offer(ProcessRecord processRecord) {
            ProcessRecordNode processRecordNode = processRecord.mLinkedNodes[this.mType];
            int applyAsInt = this.mSlotFunction.applyAsInt(processRecord);
            if (applyAsInt < this.mFirstPopulatedSlot) {
                this.mFirstPopulatedSlot = applyAsInt;
            }
            processRecordNode.unlink();
            LinkedProcessRecordList linkedProcessRecordList = this.mProcessRecordNodes[applyAsInt];
            int applyAsInt2 = linkedProcessRecordList.mValueFunction.applyAsInt(processRecordNode.mApp);
            ProcessRecordNode processRecordNode2 = linkedProcessRecordList.TAIL.mPrev;
            while (processRecordNode2 != linkedProcessRecordList.HEAD && linkedProcessRecordList.mValueFunction.applyAsInt(processRecordNode2.mApp) > applyAsInt2) {
                processRecordNode2 = processRecordNode2.mPrev;
            }
            processRecordNode.mPrev = processRecordNode2;
            processRecordNode.mNext = processRecordNode2.mNext;
            processRecordNode2.mNext.mPrev = processRecordNode;
            processRecordNode2.mNext = processRecordNode;
        }

        public final ProcessRecord poll() {
            LinkedProcessRecordList[] linkedProcessRecordListArr = this.mProcessRecordNodes;
            int length = linkedProcessRecordListArr.length;
            ProcessRecordNode processRecordNode = null;
            while (processRecordNode == null) {
                int i = this.mFirstPopulatedSlot;
                if (i >= length) {
                    break;
                }
                LinkedProcessRecordList linkedProcessRecordList = linkedProcessRecordListArr[i];
                ProcessRecordNode processRecordNode2 = linkedProcessRecordList.HEAD.mNext;
                if (processRecordNode2 == linkedProcessRecordList.TAIL) {
                    processRecordNode = null;
                } else {
                    processRecordNode2.unlink();
                    processRecordNode = processRecordNode2;
                }
                if (processRecordNode == null) {
                    this.mFirstPopulatedSlot++;
                }
            }
            if (processRecordNode == null) {
                return null;
            }
            return processRecordNode.mApp;
        }

        public void reset() {
            int i = 0;
            while (true) {
                LinkedProcessRecordList[] linkedProcessRecordListArr = this.mProcessRecordNodes;
                if (i >= linkedProcessRecordListArr.length) {
                    return;
                }
                linkedProcessRecordListArr[i].reset();
                this.mLastNode[i] = linkedProcessRecordListArr[i].HEAD;
                i++;
            }
        }

        public final void unlink(ProcessRecord processRecord) {
            int i;
            ProcessRecordNode[] processRecordNodeArr = processRecord.mLinkedNodes;
            int i2 = this.mType;
            ProcessRecordNode processRecordNode = processRecordNodeArr[i2];
            ProcessStateRecord processStateRecord = processRecord.mState;
            if (i2 == 0) {
                int i3 = processStateRecord.mCurProcState;
                if (i3 < 0 || i3 > 19) {
                    i3 = 20;
                }
                i = i3;
            } else if (i2 != 1) {
                i = -1;
            } else {
                int i4 = processStateRecord.mCurRawAdj;
                int[] iArr = OomAdjusterModernImpl.ADJ_SLOT_VALUES;
                i = 17;
                if (i4 >= iArr[0] && i4 <= iArr[17] && (i = Arrays.binarySearch(iArr, i4)) < 0) {
                    i = (-(i + 1)) - 1;
                }
            }
            if (i != -1) {
                ProcessRecordNode[] processRecordNodeArr2 = this.mLastNode;
                if (processRecordNodeArr2[i] == processRecordNode) {
                    processRecordNodeArr2[i] = processRecordNode.mPrev;
                }
            }
            processRecordNode.unlink();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReachableCollectingConsumer implements BiConsumer {
        public ArrayList mReachables;

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ProcessRecord processRecord = (ProcessRecord) obj2;
            ProcessStateRecord processStateRecord = processRecord.mState;
            if (processStateRecord.mReachable) {
                return;
            }
            processStateRecord.mReachable = true;
            this.mReachables.add(processRecord);
        }
    }

    public OomAdjusterModernImpl(ActivityManagerService activityManagerService, ProcessList processList, ActiveUids activeUids, ServiceThread serviceThread) {
        super(activityManagerService, processList, activeUids, serviceThread);
        ReachableCollectingConsumer reachableCollectingConsumer = new ReachableCollectingConsumer();
        reachableCollectingConsumer.mReachables = null;
        this.mReachableCollectingConsumer = reachableCollectingConsumer;
        this.mComputeConnectionIgnoringReachableClientsConsumer = new ComputeConnectionIgnoringReachableClientsConsumer();
        this.mComputeHostConsumer = new ComputeHostConsumer();
        this.mComputeConnectionsConsumer = new ComputeConnectionsConsumer();
        this.mProcessRecordProcStateNodes = new ProcessRecordNodes(this, 0, 21);
        this.mProcessRecordAdjNodes = new ProcessRecordNodes(this, 1, 18);
        this.mTmpOomAdjusterArgs = new OomAdjusterArgs();
    }

    public static void forEachConnectionLSP(ProcessRecord processRecord, BiConsumer biConsumer) {
        ProcessStateRecord processStateRecord;
        int i;
        ProcessStateRecord processStateRecord2;
        int i2;
        ProcessStateRecord processStateRecord3;
        int i3;
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        int size = processServiceRecord.mConnections.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(size);
            boolean hasFlag = connectionAt.hasFlag(2);
            AppBindRecord appBindRecord = connectionAt.binding;
            ProcessRecord processRecord2 = hasFlag ? appBindRecord.service.isolationHostProc : appBindRecord.service.app;
            if (processRecord2 != null && processRecord2 != processRecord && (((i3 = (processStateRecord3 = processRecord2.mState).mMaxAdj) < -900 || i3 >= 0) && ((processStateRecord3.mCurAdj > 0 || processStateRecord3.mCurSchedGroup <= 0 || processStateRecord3.mCurProcState > 2) && (!processRecord2.isSdkSandbox || appBindRecord.attributedClient == null)))) {
                biConsumer.accept(connectionAt, processRecord2);
            }
        }
        ArraySet arraySet = processServiceRecord.mSdkSandboxConnections;
        for (int size2 = (arraySet != null ? arraySet.size() : 0) - 1; size2 >= 0; size2--) {
            ArraySet arraySet2 = processServiceRecord.mSdkSandboxConnections;
            ConnectionRecord connectionRecord = arraySet2 != null ? (ConnectionRecord) arraySet2.valueAt(size2) : null;
            ProcessRecord processRecord3 = connectionRecord.binding.service.app;
            if (processRecord3 != null && processRecord3 != processRecord && (((i2 = (processStateRecord2 = processRecord3.mState).mMaxAdj) < -900 || i2 >= 0) && (processStateRecord2.mCurAdj > 0 || processStateRecord2.mCurSchedGroup <= 0 || processStateRecord2.mCurProcState > 2))) {
                biConsumer.accept(connectionRecord, processRecord3);
            }
        }
        ProcessProviderRecord processProviderRecord = processRecord.mProviders;
        for (int size3 = processProviderRecord.mConProviders.size() - 1; size3 >= 0; size3--) {
            ContentProviderConnection contentProviderConnection = (ContentProviderConnection) processProviderRecord.mConProviders.get(size3);
            ProcessRecord processRecord4 = contentProviderConnection.provider.proc;
            if (processRecord4 != null && processRecord4 != processRecord && (((i = (processStateRecord = processRecord4.mState).mMaxAdj) < -900 || i >= 0) && (processStateRecord.mCurAdj > 0 || processStateRecord.mCurSchedGroup <= 0 || processStateRecord.mCurProcState > 2))) {
                biConsumer.accept(contentProviderConnection, processRecord4);
            }
        }
    }

    public final void computeConnectionsLSP() {
        ComputeConnectionsConsumer computeConnectionsConsumer;
        OomAdjusterArgs oomAdjusterArgs;
        Flags.simplifyProcessTraversal();
        ProcessRecordNodes processRecordNodes = this.mProcessRecordProcStateNodes;
        ProcessRecord poll = processRecordNodes.poll();
        while (true) {
            computeConnectionsConsumer = this.mComputeConnectionsConsumer;
            oomAdjusterArgs = this.mTmpOomAdjusterArgs;
            if (poll == null) {
                break;
            }
            oomAdjusterArgs.mApp = poll;
            computeConnectionsConsumer.accept(oomAdjusterArgs);
            poll = processRecordNodes.poll();
        }
        ProcessRecordNodes processRecordNodes2 = this.mProcessRecordAdjNodes;
        for (ProcessRecord poll2 = processRecordNodes2.poll(); poll2 != null; poll2 = processRecordNodes2.poll()) {
            oomAdjusterArgs.mApp = poll2;
            computeConnectionsConsumer.accept(oomAdjusterArgs);
        }
    }

    @Override // com.android.server.am.OomAdjuster
    public final int getInitialAdj(ProcessRecord processRecord) {
        return 1001;
    }

    @Override // com.android.server.am.OomAdjuster
    public final int getInitialCapability(ProcessRecord processRecord) {
        return 0;
    }

    @Override // com.android.server.am.OomAdjuster
    public final boolean getInitialIsCurBoundByNonBgRestrictedApp(ProcessRecord processRecord) {
        return false;
    }

    @Override // com.android.server.am.OomAdjuster
    public final int getInitialProcState(ProcessRecord processRecord) {
        return -1;
    }

    @Override // com.android.server.am.OomAdjuster
    public final void onProcessEndLocked(ProcessRecord processRecord) {
        ProcessRecordNode processRecordNode = processRecord.mLinkedNodes[0];
        if (processRecordNode == null || processRecordNode.mPrev == null || processRecordNode.mNext == null) {
            return;
        }
        this.mProcessRecordProcStateNodes.unlink(processRecord);
        this.mProcessRecordAdjNodes.unlink(processRecord);
    }

    @Override // com.android.server.am.OomAdjuster
    public final void onProcessOomAdjChanged(int i, ProcessRecord processRecord) {
        if (processRecord.mState.mCurRawAdj != i) {
            Flags.simplifyProcessTraversal();
            this.mProcessRecordAdjNodes.offer(processRecord);
        }
    }

    @Override // com.android.server.am.OomAdjuster
    public final void onProcessStateChanged(int i, ProcessRecord processRecord) {
        if (processRecord.mState.mCurProcState != i) {
            Flags.simplifyProcessTraversal();
            this.mProcessRecordProcStateNodes.offer(processRecord);
        }
    }

    public final void partialUpdateLSP(int i, ArraySet arraySet) {
        Object obj;
        ProcessRecord topApp = this.mService.getTopApp();
        this.mInjector.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = uptimeMillis - this.mConstants.mMaxEmptyTimeMillis;
        ActiveUids activeUids = this.mTmpUidRecords;
        activeUids.mActiveUids.clear();
        OomAdjusterArgs oomAdjusterArgs = this.mTmpOomAdjusterArgs;
        oomAdjusterArgs.mTopApp = topApp;
        oomAdjusterArgs.mNow = uptimeMillis;
        oomAdjusterArgs.mCachedAdj = 1001;
        oomAdjusterArgs.mOomAdjReason = i;
        oomAdjusterArgs.mUids = activeUids;
        boolean z = false;
        oomAdjusterArgs.mFullUpdate = false;
        int i2 = 1;
        this.mAdjSeq++;
        ArrayList arrayList = this.mTmpProcessList;
        arrayList.clear();
        int size = arraySet.size();
        for (int i3 = 0; i3 < size; i3++) {
            ProcessRecord processRecord = (ProcessRecord) arraySet.valueAtUnchecked(i3);
            processRecord.mState.resetCachedInfo();
            processRecord.mState.mReachable = true;
            arrayList.add(processRecord);
        }
        ReachableCollectingConsumer reachableCollectingConsumer = this.mReachableCollectingConsumer;
        reachableCollectingConsumer.mReachables = arrayList;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            forEachConnectionLSP((ProcessRecord) arrayList.get(i4), reachableCollectingConsumer);
        }
        ProcessRecordNodes processRecordNodes = this.mProcessRecordProcStateNodes;
        processRecordNodes.getClass();
        Flags.simplifyProcessTraversal();
        processRecordNodes.reset();
        arraySet.size();
        OomAdjusterArgs oomAdjusterArgs2 = this.mTmpOomAdjusterArgs;
        Flags.skipUnimportantConnections();
        int size2 = arrayList.size();
        int i5 = 0;
        while (i5 < size2) {
            ProcessRecord processRecord2 = (ProcessRecord) arrayList.get(i5);
            int i6 = processRecord2.mState.mCurProcState;
            oomAdjusterArgs2.mApp = processRecord2;
            ArrayList arrayList2 = arrayList;
            int i7 = i5;
            int i8 = size2;
            OomAdjusterArgs oomAdjusterArgs3 = oomAdjusterArgs2;
            long j2 = elapsedRealtime;
            ActiveUids activeUids2 = activeUids;
            computeOomAdjLSP(processRecord2, 1001, oomAdjusterArgs2.mTopApp, false, oomAdjusterArgs2.mNow, false, false, oomAdjusterArgs2.mOomAdjReason, false);
            ComputeConnectionIgnoringReachableClientsConsumer computeConnectionIgnoringReachableClientsConsumer = this.mComputeConnectionIgnoringReachableClientsConsumer;
            computeConnectionIgnoringReachableClientsConsumer.mArgs = oomAdjusterArgs3;
            computeConnectionIgnoringReachableClientsConsumer.hasReachableClient = false;
            ProcessServiceRecord processServiceRecord = processRecord2.mServices;
            for (int size3 = processServiceRecord.mServices.size() - 1; size3 >= 0; size3--) {
                ArrayMap arrayMap = processServiceRecord.getRunningServiceAt(size3).connections;
                for (int size4 = arrayMap.size() - 1; size4 >= 0; size4--) {
                    ArrayList arrayList3 = (ArrayList) arrayMap.valueAt(size4);
                    for (int size5 = arrayList3.size() - 1; size5 >= 0; size5--) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList3.get(size5);
                        if (!processRecord2.isSdkSandbox || (obj = connectionRecord.binding.attributedClient) == null) {
                            obj = connectionRecord.binding.client;
                        }
                        if (obj != null && obj != processRecord2) {
                            computeConnectionIgnoringReachableClientsConsumer.accept(connectionRecord, obj);
                        }
                    }
                }
            }
            ProcessProviderRecord processProviderRecord = processRecord2.mProviders;
            for (int size6 = processProviderRecord.mPubProviders.size() - 1; size6 >= 0; size6--) {
                ContentProviderRecord contentProviderRecord = (ContentProviderRecord) processProviderRecord.mPubProviders.valueAt(size6);
                for (int size7 = contentProviderRecord.connections.size() - 1; size7 >= 0; size7--) {
                    ContentProviderConnection contentProviderConnection = (ContentProviderConnection) contentProviderRecord.connections.get(size7);
                    computeConnectionIgnoringReachableClientsConsumer.accept(contentProviderConnection, contentProviderConnection.client);
                }
            }
            Flags.simplifyProcessTraversal();
            this.mProcessRecordProcStateNodes.offer(processRecord2);
            oomAdjusterArgs2 = oomAdjusterArgs3;
            z = false;
            activeUids = activeUids2;
            i2 = 1;
            arrayList = arrayList2;
            elapsedRealtime = j2;
            size2 = i8;
            i5 = i7 + 1;
        }
        int i9 = i2;
        ArrayList arrayList4 = arrayList;
        boolean z2 = z;
        long j3 = elapsedRealtime;
        ActiveUids activeUids3 = activeUids;
        ProcessRecordNodes processRecordNodes2 = this.mProcessRecordAdjNodes;
        processRecordNodes2.getClass();
        Flags.simplifyProcessTraversal();
        processRecordNodes2.reset();
        computeConnectionsLSP();
        int size8 = arrayList4.size();
        int i10 = z2 ? 1 : 0;
        int i11 = i10;
        while (i11 < size8) {
            ArrayList arrayList5 = arrayList4;
            ProcessStateRecord processStateRecord = ((ProcessRecord) arrayList5.get(i11)).mState;
            processStateRecord.mReachable = z2;
            processStateRecord.mCompletedAdjSeq = this.mAdjSeq;
            if (processStateRecord.mCurAdj >= 1001) {
                i10 = i9;
            }
            i11++;
            arrayList4 = arrayList5;
        }
        if (i10 != 0) {
            assignCachedAdjIfNecessary(this.mProcessList.mLruProcesses);
        }
        int size9 = activeUids3.mActiveUids.size();
        for (int i12 = z2 ? 1 : 0; i12 < size9; i12++) {
            UidRecord valueAt = activeUids3.valueAt(i12);
            valueAt.mCurProcState = 19;
            valueAt.mForegroundServices = z2;
            valueAt.mCurCapability = z2 ? 1 : 0;
            for (int size10 = valueAt.mProcRecords.size() - 1; size10 >= 0; size10--) {
                ProcessRecord processRecord3 = (ProcessRecord) valueAt.mProcRecords.valueAt(size10);
                if (!processRecord3.mKilledByAm && processRecord3.mThread != null && (!processRecord3.isolated || processRecord3.mServices.mServices.size() > 0 || processRecord3.mIsolatedEntryPoint != null)) {
                    OomAdjuster.updateAppUidRecLSP(processRecord3);
                }
            }
        }
        postUpdateOomAdjInnerLSP(i, activeUids3, uptimeMillis, j3, j, false);
    }

    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    @Override // com.android.server.am.OomAdjuster
    public final void performUpdateOomAdjLSP(int i) {
        this.mService.getTopApp();
        this.mProcessStateCurTop = this.mService.mAtmInternal.getTopProcessState();
        this.mPendingProcessSet.clear();
        this.mService.mAppProfiler.getClass();
        this.mLastReason = i;
        Trace.traceBegin(64L, OomAdjuster.oomAdjReasonToString(i));
        ProcessRecord topApp = this.mService.getTopApp();
        this.mInjector.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = uptimeMillis - this.mConstants.mMaxEmptyTimeMillis;
        this.mAdjSeq++;
        boolean z = 0;
        this.mNewNumServiceProcs = 0;
        this.mNewNumAServiceProcs = 0;
        this.mProcessRecordProcStateNodes.reset();
        this.mProcessRecordAdjNodes.reset();
        ArrayList arrayList = this.mProcessList.mLruProcesses;
        int size = arrayList.size() - 1;
        while (size >= 0) {
            ProcessRecord processRecord = (ProcessRecord) arrayList.get(size);
            ProcessStateRecord processStateRecord = processRecord.mState;
            int i2 = processStateRecord.mCurProcState;
            processStateRecord.resetCachedInfo();
            UidRecord uidRecord = processRecord.mUidRecord;
            if (uidRecord != null) {
                uidRecord.mCurProcState = 19;
                uidRecord.mForegroundServices = z;
                uidRecord.mCurCapability = z;
            }
            computeOomAdjLSP(processRecord, 1001, topApp, true, uptimeMillis, false, false, i, false);
            Flags.simplifyProcessTraversal();
            this.mProcessRecordProcStateNodes.offer(processRecord);
            size--;
            arrayList = arrayList;
            z = z;
            uptimeMillis = uptimeMillis;
        }
        long j2 = uptimeMillis;
        ProcessRecordNodes processRecordNodes = this.mProcessRecordAdjNodes;
        processRecordNodes.getClass();
        Flags.simplifyProcessTraversal();
        processRecordNodes.reset();
        OomAdjusterArgs oomAdjusterArgs = this.mTmpOomAdjusterArgs;
        oomAdjusterArgs.mTopApp = topApp;
        oomAdjusterArgs.mNow = j2;
        oomAdjusterArgs.mCachedAdj = 1001;
        oomAdjusterArgs.mOomAdjReason = i;
        oomAdjusterArgs.mUids = null;
        oomAdjusterArgs.mFullUpdate = true;
        computeConnectionsLSP();
        assignCachedAdjIfNecessary(this.mProcessList.mLruProcesses);
        postUpdateOomAdjInnerLSP(i, this.mActiveUids, j2, elapsedRealtime, j, true);
        Trace.traceEnd(64L);
    }

    @Override // com.android.server.am.OomAdjuster
    public final void performUpdateOomAdjLSP(int i, ProcessRecord processRecord) {
        this.mPendingProcessSet.add(processRecord);
        performUpdateOomAdjPendingTargetsLocked(i);
    }

    @Override // com.android.server.am.OomAdjuster
    public final void performUpdateOomAdjPendingTargetsLocked(int i) {
        this.mLastReason = i;
        this.mProcessStateCurTop = enqueuePendingTopAppIfNecessaryLSP();
        Trace.traceBegin(64L, OomAdjuster.oomAdjReasonToString(i));
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                partialUpdateLSP(i, this.mPendingProcessSet);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        this.mPendingProcessSet.clear();
        Trace.traceEnd(64L);
    }

    @Override // com.android.server.am.OomAdjuster
    public void resetInternal() {
        this.mProcessRecordProcStateNodes.reset();
        this.mProcessRecordAdjNodes.reset();
    }
}
