package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FillEventHistory implements Parcelable {
    public static final Parcelable.Creator<FillEventHistory> CREATOR =
            new Parcelable.Creator<
                    FillEventHistory>() { // from class: android.service.autofill.FillEventHistory.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FillEventHistory createFromParcel(Parcel parcel) {
                    ArrayList<ArrayList<String>> manuallyFilledDatasetIds;
                    Parcel parcel2 = parcel;
                    FillEventHistory selection = new FillEventHistory(0, parcel.readBundle());
                    int numEvents = parcel.readInt();
                    int i = 0;
                    while (i < numEvents) {
                        int eventType = parcel.readInt();
                        String datasetId = parcel.readString();
                        Bundle clientState = parcel.readBundle();
                        ArrayList<String> selectedDatasetIds = parcel.createStringArrayList();
                        FieldClassification[] fieldClassificationArr = null;
                        ArraySet<? extends Object> readArraySet = parcel2.readArraySet(null);
                        ArrayList<AutofillId> changedFieldIds =
                                parcel2.createTypedArrayList(AutofillId.CREATOR);
                        ArrayList<String> changedDatasetIds = parcel.createStringArrayList();
                        ArrayList<AutofillId> manuallyFilledFieldIds =
                                parcel2.createTypedArrayList(AutofillId.CREATOR);
                        if (manuallyFilledFieldIds != null) {
                            int size = manuallyFilledFieldIds.size();
                            ArrayList<ArrayList<String>> manuallyFilledDatasetIds2 =
                                    new ArrayList<>(size);
                            for (int j = 0; j < size; j++) {
                                manuallyFilledDatasetIds2.add(parcel.createStringArrayList());
                            }
                            manuallyFilledDatasetIds = manuallyFilledDatasetIds2;
                        } else {
                            manuallyFilledDatasetIds = null;
                        }
                        AutofillId[] detectedFieldIds =
                                (AutofillId[]) parcel2.readParcelableArray(null, AutofillId.class);
                        if (detectedFieldIds != null) {
                            fieldClassificationArr =
                                    FieldClassification.readArrayFromParcel(parcel);
                        }
                        FieldClassification[] detectedFieldClassifications = fieldClassificationArr;
                        int saveDialogNotShowReason = parcel.readInt();
                        int uiType = parcel.readInt();
                        selection.addEvent(
                                new Event(
                                        eventType,
                                        datasetId,
                                        clientState,
                                        selectedDatasetIds,
                                        readArraySet,
                                        changedFieldIds,
                                        changedDatasetIds,
                                        manuallyFilledFieldIds,
                                        manuallyFilledDatasetIds,
                                        detectedFieldIds,
                                        detectedFieldClassifications,
                                        saveDialogNotShowReason,
                                        uiType));
                        i++;
                        parcel2 = parcel;
                    }
                    return selection;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FillEventHistory[] newArray(int size) {
                    return new FillEventHistory[size];
                }
            };
    private static final String TAG = "FillEventHistory";
    private final Bundle mClientState;
    List<Event> mEvents;
    private final int mSessionId;

    public int getSessionId() {
        return this.mSessionId;
    }

    @Deprecated
    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<Event> getEvents() {
        return this.mEvents;
    }

    public void addEvent(Event event) {
        if (this.mEvents == null) {
            this.mEvents = new ArrayList(1);
        }
        this.mEvents.add(event);
    }

    public FillEventHistory(int sessionId, Bundle clientState) {
        this.mClientState = clientState;
        this.mSessionId = sessionId;
    }

    public String toString() {
        return this.mEvents == null ? "no events" : this.mEvents.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBundle(this.mClientState);
        if (this.mEvents == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(this.mEvents.size());
        int numEvents = this.mEvents.size();
        for (int i = 0; i < numEvents; i++) {
            Event event = this.mEvents.get(i);
            parcel.writeInt(event.mEventType);
            parcel.writeString(event.mDatasetId);
            parcel.writeBundle(event.mClientState);
            parcel.writeStringList(event.mSelectedDatasetIds);
            parcel.writeArraySet(event.mIgnoredDatasetIds);
            parcel.writeTypedList(event.mChangedFieldIds);
            parcel.writeStringList(event.mChangedDatasetIds);
            parcel.writeTypedList(event.mManuallyFilledFieldIds);
            if (event.mManuallyFilledFieldIds != null) {
                int size = event.mManuallyFilledFieldIds.size();
                for (int j = 0; j < size; j++) {
                    parcel.writeStringList((List) event.mManuallyFilledDatasetIds.get(j));
                }
            }
            AutofillId[] detectedFields = event.mDetectedFieldIds;
            parcel.writeParcelableArray(detectedFields, flags);
            if (detectedFields != null) {
                FieldClassification.writeArrayToParcel(parcel, event.mDetectedFieldClassifications);
            }
            parcel.writeInt(event.mSaveDialogNotShowReason);
            parcel.writeInt(event.mUiType);
        }
    }

    public static final class Event {
        public static final int NO_SAVE_UI_REASON_DATASET_MATCH = 6;
        public static final int NO_SAVE_UI_REASON_FIELD_VALIDATION_FAILED = 5;
        public static final int NO_SAVE_UI_REASON_HAS_EMPTY_REQUIRED = 3;
        public static final int NO_SAVE_UI_REASON_NONE = 0;
        public static final int NO_SAVE_UI_REASON_NO_SAVE_INFO = 1;
        public static final int NO_SAVE_UI_REASON_NO_VALUE_CHANGED = 4;
        public static final int NO_SAVE_UI_REASON_WITH_DELAY_SAVE_FLAG = 2;
        public static final int TYPE_AUTHENTICATION_SELECTED = 2;
        public static final int TYPE_CONTEXT_COMMITTED = 4;
        public static final int TYPE_DATASETS_SHOWN = 5;
        public static final int TYPE_DATASET_AUTHENTICATION_SELECTED = 1;
        public static final int TYPE_DATASET_SELECTED = 0;
        public static final int TYPE_SAVE_SHOWN = 3;
        public static final int TYPE_VIEW_REQUESTED_AUTOFILL = 6;
        public static final int UI_TYPE_CREDMAN_BOTTOM_SHEET = 4;
        public static final int UI_TYPE_DIALOG = 3;
        public static final int UI_TYPE_INLINE = 2;
        public static final int UI_TYPE_MENU = 1;
        public static final int UI_TYPE_UNKNOWN = 0;
        private final ArrayList<String> mChangedDatasetIds;
        private final ArrayList<AutofillId> mChangedFieldIds;
        private final Bundle mClientState;
        private final String mDatasetId;
        private final FieldClassification[] mDetectedFieldClassifications;
        private final AutofillId[] mDetectedFieldIds;
        private final int mEventType;
        private final ArraySet<String> mIgnoredDatasetIds;
        private final ArrayList<ArrayList<String>> mManuallyFilledDatasetIds;
        private final ArrayList<AutofillId> mManuallyFilledFieldIds;
        private final int mSaveDialogNotShowReason;
        private final List<String> mSelectedDatasetIds;
        private final int mUiType;

        @Retention(RetentionPolicy.SOURCE)
        @interface EventIds {}

        @Retention(RetentionPolicy.SOURCE)
        public @interface NoSaveReason {}

        @Retention(RetentionPolicy.SOURCE)
        public @interface UiType {}

        public int getType() {
            return this.mEventType;
        }

        public String getDatasetId() {
            return this.mDatasetId;
        }

        public Bundle getClientState() {
            return this.mClientState;
        }

        public Set<String> getSelectedDatasetIds() {
            return this.mSelectedDatasetIds == null
                    ? Collections.emptySet()
                    : new ArraySet(this.mSelectedDatasetIds);
        }

        public Set<String> getIgnoredDatasetIds() {
            return this.mIgnoredDatasetIds == null
                    ? Collections.emptySet()
                    : this.mIgnoredDatasetIds;
        }

        public Map<AutofillId, String> getChangedFields() {
            if (this.mChangedFieldIds == null || this.mChangedDatasetIds == null) {
                return Collections.emptyMap();
            }
            int size = this.mChangedFieldIds.size();
            ArrayMap<AutofillId, String> changedFields = new ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                changedFields.put(this.mChangedFieldIds.get(i), this.mChangedDatasetIds.get(i));
            }
            return changedFields;
        }

        public Map<AutofillId, FieldClassification> getFieldsClassification() {
            if (this.mDetectedFieldIds == null) {
                return Collections.emptyMap();
            }
            int size = this.mDetectedFieldIds.length;
            ArrayMap<AutofillId, FieldClassification> map = new ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                AutofillId id = this.mDetectedFieldIds[i];
                FieldClassification fc = this.mDetectedFieldClassifications[i];
                if (Helper.sVerbose) {
                    Log.v(
                            FillEventHistory.TAG,
                            "getFieldsClassification[" + i + "]: id=" + id + ", fc=" + fc);
                }
                map.put(id, fc);
            }
            return map;
        }

        public Map<AutofillId, Set<String>> getManuallyEnteredField() {
            if (this.mManuallyFilledFieldIds == null || this.mManuallyFilledDatasetIds == null) {
                return Collections.emptyMap();
            }
            int size = this.mManuallyFilledFieldIds.size();
            Map<AutofillId, Set<String>> manuallyFilledFields = new ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                AutofillId fieldId = this.mManuallyFilledFieldIds.get(i);
                ArrayList<String> datasetIds = this.mManuallyFilledDatasetIds.get(i);
                manuallyFilledFields.put(fieldId, new ArraySet<>(datasetIds));
            }
            return manuallyFilledFields;
        }

        public int getNoSaveUiReason() {
            return this.mSaveDialogNotShowReason;
        }

        public int getUiType() {
            return this.mUiType;
        }

        public Event(
                int eventType,
                String datasetId,
                Bundle clientState,
                List<String> selectedDatasetIds,
                ArraySet<String> ignoredDatasetIds,
                ArrayList<AutofillId> changedFieldIds,
                ArrayList<String> changedDatasetIds,
                ArrayList<AutofillId> manuallyFilledFieldIds,
                ArrayList<ArrayList<String>> manuallyFilledDatasetIds,
                AutofillId[] detectedFieldIds,
                FieldClassification[] detectedFieldClassifications) {
            this(
                    eventType,
                    datasetId,
                    clientState,
                    selectedDatasetIds,
                    ignoredDatasetIds,
                    changedFieldIds,
                    changedDatasetIds,
                    manuallyFilledFieldIds,
                    manuallyFilledDatasetIds,
                    detectedFieldIds,
                    detectedFieldClassifications,
                    0);
        }

        public Event(
                int eventType,
                String datasetId,
                Bundle clientState,
                List<String> selectedDatasetIds,
                ArraySet<String> ignoredDatasetIds,
                ArrayList<AutofillId> changedFieldIds,
                ArrayList<String> changedDatasetIds,
                ArrayList<AutofillId> manuallyFilledFieldIds,
                ArrayList<ArrayList<String>> manuallyFilledDatasetIds,
                AutofillId[] detectedFieldIds,
                FieldClassification[] detectedFieldClassifications,
                int saveDialogNotShowReason) {
            this(
                    eventType,
                    datasetId,
                    clientState,
                    selectedDatasetIds,
                    ignoredDatasetIds,
                    changedFieldIds,
                    changedDatasetIds,
                    manuallyFilledFieldIds,
                    manuallyFilledDatasetIds,
                    detectedFieldIds,
                    detectedFieldClassifications,
                    saveDialogNotShowReason,
                    0);
        }

        public Event(
                int eventType,
                String datasetId,
                Bundle clientState,
                List<String> selectedDatasetIds,
                ArraySet<String> ignoredDatasetIds,
                ArrayList<AutofillId> changedFieldIds,
                ArrayList<String> changedDatasetIds,
                ArrayList<AutofillId> manuallyFilledFieldIds,
                ArrayList<ArrayList<String>> manuallyFilledDatasetIds,
                AutofillId[] detectedFieldIds,
                FieldClassification[] detectedFieldClassifications,
                int saveDialogNotShowReason,
                int uiType) {
            this.mEventType = Preconditions.checkArgumentInRange(eventType, 0, 6, "eventType");
            this.mDatasetId = datasetId;
            this.mClientState = clientState;
            this.mSelectedDatasetIds = selectedDatasetIds;
            this.mIgnoredDatasetIds = ignoredDatasetIds;
            if (changedFieldIds != null) {
                Preconditions.checkArgument(
                        (ArrayUtils.isEmpty(changedFieldIds)
                                        || changedDatasetIds == null
                                        || changedFieldIds.size() != changedDatasetIds.size())
                                ? false
                                : true,
                        "changed ids must have same length and not be empty");
            }
            this.mChangedFieldIds = changedFieldIds;
            this.mChangedDatasetIds = changedDatasetIds;
            if (manuallyFilledFieldIds != null) {
                Preconditions.checkArgument(
                        (ArrayUtils.isEmpty(manuallyFilledFieldIds)
                                        || manuallyFilledDatasetIds == null
                                        || manuallyFilledFieldIds.size()
                                                != manuallyFilledDatasetIds.size())
                                ? false
                                : true,
                        "manually filled ids must have same length and not be empty");
            }
            this.mManuallyFilledFieldIds = manuallyFilledFieldIds;
            this.mManuallyFilledDatasetIds = manuallyFilledDatasetIds;
            this.mDetectedFieldIds = detectedFieldIds;
            this.mDetectedFieldClassifications = detectedFieldClassifications;
            this.mSaveDialogNotShowReason =
                    Preconditions.checkArgumentInRange(
                            saveDialogNotShowReason, 0, 6, "saveDialogNotShowReason");
            this.mUiType = uiType;
        }

        public String toString() {
            return "FillEvent [datasetId="
                    + this.mDatasetId
                    + ", type="
                    + eventToString(this.mEventType)
                    + ", uiType="
                    + uiTypeToString(this.mUiType)
                    + ", selectedDatasets="
                    + this.mSelectedDatasetIds
                    + ", ignoredDatasetIds="
                    + this.mIgnoredDatasetIds
                    + ", changedFieldIds="
                    + this.mChangedFieldIds
                    + ", changedDatasetsIds="
                    + this.mChangedDatasetIds
                    + ", manuallyFilledFieldIds="
                    + this.mManuallyFilledFieldIds
                    + ", manuallyFilledDatasetIds="
                    + this.mManuallyFilledDatasetIds
                    + ", detectedFieldIds="
                    + Arrays.toString(this.mDetectedFieldIds)
                    + ", detectedFieldClassifications ="
                    + Arrays.toString(this.mDetectedFieldClassifications)
                    + ", saveDialogNotShowReason="
                    + this.mSaveDialogNotShowReason
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }

        private static String eventToString(int eventType) {
            switch (eventType) {
                case 0:
                    return "TYPE_DATASET_SELECTED";
                case 1:
                    return "TYPE_DATASET_AUTHENTICATION_SELECTED";
                case 2:
                    return "TYPE_AUTHENTICATION_SELECTED";
                case 3:
                    return "TYPE_SAVE_SHOWN";
                case 4:
                    return "TYPE_CONTEXT_COMMITTED";
                case 5:
                    return "TYPE_DATASETS_SHOWN";
                case 6:
                    return "TYPE_VIEW_REQUESTED_AUTOFILL";
                default:
                    return "TYPE_UNKNOWN";
            }
        }

        private static String uiTypeToString(int uiType) {
            switch (uiType) {
                case 1:
                    return "UI_TYPE_MENU";
                case 2:
                    return "UI_TYPE_INLINE";
                case 3:
                    return "UI_TYPE_FILL_DIALOG";
                case 4:
                    return "UI_TYPE_CREDMAN_BOTTOM_SHEET";
                default:
                    return "UI_TYPE_UNKNOWN";
            }
        }
    }
}
