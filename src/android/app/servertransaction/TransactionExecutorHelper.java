package android.app.servertransaction;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.admin.DevicePolicyResources;
import android.os.IBinder;
import android.util.IntArray;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/* loaded from: classes.dex */
public class TransactionExecutorHelper {
    private static final int DESTRUCTION_PENALTY = 10;
    private static final int[] ON_RESUME_PRE_EXCUTION_STATES = {2, 4};
    private static final String TAG = "TransactionExecutorHelper";
    private final IntArray mLifecycleSequence = new IntArray(6);

    public IntArray getLifecyclePath(int i, int i2, boolean z) {
        if (i == -1 || i2 == -1) {
            throw new IllegalArgumentException("Can't resolve lifecycle path for undefined state");
        }
        if (i == 7 || i2 == 7) {
            throw new IllegalArgumentException("Can't start or finish in intermittent RESTART state");
        }
        if (i2 == 0 && i != i2) {
            throw new IllegalArgumentException("Can only start in pre-onCreate state");
        }
        this.mLifecycleSequence.clear();
        if (i2 >= i) {
            if (i == 2 && i2 == 5) {
                this.mLifecycleSequence.add(5);
            } else {
                for (int i3 = i + 1; i3 <= i2; i3++) {
                    this.mLifecycleSequence.add(i3);
                }
            }
        } else if (i == 4 && i2 == 3) {
            this.mLifecycleSequence.add(3);
        } else if (i > 5 || i2 < 2) {
            for (int i4 = i + 1; i4 <= 6; i4++) {
                this.mLifecycleSequence.add(i4);
            }
            for (int i5 = 1; i5 <= i2; i5++) {
                this.mLifecycleSequence.add(i5);
            }
        } else {
            for (int i6 = i + 1; i6 <= 5; i6++) {
                this.mLifecycleSequence.add(i6);
            }
            this.mLifecycleSequence.add(7);
            for (int i7 = 2; i7 <= i2; i7++) {
                this.mLifecycleSequence.add(i7);
            }
        }
        if (z && this.mLifecycleSequence.size() != 0) {
            IntArray intArray = this.mLifecycleSequence;
            intArray.remove(intArray.size() - 1);
        }
        return this.mLifecycleSequence;
    }

    public int getClosestPreExecutionState(ActivityThread.ActivityClientRecord activityClientRecord, int i) {
        if (i == -1) {
            return -1;
        }
        if (i == 3) {
            return getClosestOfStates(activityClientRecord, ON_RESUME_PRE_EXCUTION_STATES);
        }
        throw new UnsupportedOperationException("Pre-execution states for state: " + i + " is not supported.");
    }

    public int getClosestOfStates(ActivityThread.ActivityClientRecord activityClientRecord, int[] iArr) {
        int i = -1;
        if (iArr != null && iArr.length != 0) {
            if (activityClientRecord == null) {
                Log.w(TAG, "ActivityClientRecord was null");
                return -1;
            }
            int lifecycleState = activityClientRecord.getLifecycleState();
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                getLifecyclePath(lifecycleState, iArr[i3], false);
                int size = this.mLifecycleSequence.size();
                if (pathInvolvesDestruction(this.mLifecycleSequence)) {
                    size += 10;
                }
                if (i2 > size) {
                    i = iArr[i3];
                    i2 = size;
                }
            }
        }
        return i;
    }

    public static ActivityLifecycleItem getLifecycleRequestForCurrentState(ActivityThread.ActivityClientRecord activityClientRecord) {
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState == 2 || lifecycleState == 4) {
            return new PauseActivityItem(activityClientRecord.token);
        }
        if (lifecycleState == 5) {
            return new StopActivityItem(activityClientRecord.token);
        }
        return new ResumeActivityItem(activityClientRecord.token, false, false);
    }

    private static boolean pathInvolvesDestruction(IntArray intArray) {
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            if (intArray.get(i) == 6) {
                return true;
            }
        }
        return false;
    }

    private static int lastCallbackRequestingStateIndex(List<ClientTransactionItem> list, int i, int i2, IBinder iBinder) {
        int i3 = -1;
        int i4 = -1;
        while (i2 >= i) {
            ClientTransactionItem clientTransactionItem = list.get(i2);
            int postExecutionState = clientTransactionItem.getPostExecutionState();
            if (postExecutionState != -1 && iBinder.equals(clientTransactionItem.getActivityToken())) {
                if (i3 != -1 && i3 != postExecutionState) {
                    break;
                }
                i4 = i2;
                i3 = postExecutionState;
            }
            i2--;
        }
        return i4;
    }

    static boolean shouldExcludeLastLifecycleState(List<ClientTransactionItem> list, int i) {
        int findNextLifecycleItemIndex;
        ClientTransactionItem clientTransactionItem = list.get(i);
        IBinder activityToken = clientTransactionItem.getActivityToken();
        int postExecutionState = clientTransactionItem.getPostExecutionState();
        return (activityToken == null || postExecutionState == -1 || (findNextLifecycleItemIndex = findNextLifecycleItemIndex(list, i + 1, activityToken)) == -1 || postExecutionState != ((ActivityLifecycleItem) list.get(findNextLifecycleItemIndex)).getTargetState() || i != lastCallbackRequestingStateIndex(list, i, findNextLifecycleItemIndex - 1, activityToken)) ? false : true;
    }

    private static int findNextLifecycleItemIndex(List<ClientTransactionItem> list, int i, IBinder iBinder) {
        int size = list.size();
        while (i < size) {
            ClientTransactionItem clientTransactionItem = list.get(i);
            if (clientTransactionItem.isActivityLifecycleItem() && clientTransactionItem.getActivityToken().equals(iBinder)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    static String transactionToString(ClientTransaction clientTransaction, ClientTransactionHandler clientTransactionHandler) {
        StringWriter stringWriter = new StringWriter();
        clientTransaction.dump(tId(clientTransaction), new PrintWriter(stringWriter), clientTransactionHandler);
        return stringWriter.toString();
    }

    static String tId(ClientTransaction clientTransaction) {
        return "tId:" + clientTransaction.hashCode() + " ";
    }

    static String getActivityName(IBinder iBinder, ClientTransactionHandler clientTransactionHandler) {
        Activity activityForToken = getActivityForToken(iBinder, clientTransactionHandler);
        if (activityForToken != null) {
            return activityForToken.getComponentName().getClassName();
        }
        return "Not found for token: " + iBinder;
    }

    static String getShortActivityName(IBinder iBinder, ClientTransactionHandler clientTransactionHandler) {
        Activity activityForToken = getActivityForToken(iBinder, clientTransactionHandler);
        if (activityForToken != null) {
            return activityForToken.getComponentName().getShortClassName();
        }
        return "Not found for token: " + iBinder;
    }

    private static Activity getActivityForToken(IBinder iBinder, ClientTransactionHandler clientTransactionHandler) {
        if (iBinder == null) {
            return null;
        }
        return clientTransactionHandler.getActivity(iBinder);
    }

    static String getStateName(int i) {
        switch (i) {
            case -1:
                return DevicePolicyResources.UNDEFINED;
            case 0:
                return "PRE_ON_CREATE";
            case 1:
                return "ON_CREATE";
            case 2:
                return "ON_START";
            case 3:
                return "ON_RESUME";
            case 4:
                return "ON_PAUSE";
            case 5:
                return "ON_STOP";
            case 6:
                return "ON_DESTROY";
            case 7:
                return "ON_RESTART";
            default:
                throw new IllegalArgumentException("Unexpected lifecycle state: " + i);
        }
    }
}
