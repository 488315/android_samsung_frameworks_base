package android.service.autofill;

import android.annotation.SystemApi;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.Presentations;
import android.util.ArrayMap;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class Dataset implements Parcelable {
    public static final Parcelable.Creator<Dataset> CREATOR = new Parcelable.Creator<Dataset>() { // from class: android.service.autofill.Dataset.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Dataset createFromParcel(Parcel parcel) {
            Builder builder;
            RemoteViews remoteViews = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            RemoteViews remoteViews2 = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            InlinePresentation inlinePresentation = (InlinePresentation) parcel.readParcelable(null, InlinePresentation.class);
            InlinePresentation inlinePresentation2 = (InlinePresentation) parcel.readParcelable(null, InlinePresentation.class);
            ArrayList createTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
            ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AutofillValue.CREATOR);
            ArrayList createTypedArrayList3 = parcel.createTypedArrayList(RemoteViews.CREATOR);
            ArrayList createTypedArrayList4 = parcel.createTypedArrayList(RemoteViews.CREATOR);
            ArrayList createTypedArrayList5 = parcel.createTypedArrayList(InlinePresentation.CREATOR);
            ArrayList createTypedArrayList6 = parcel.createTypedArrayList(InlinePresentation.CREATOR);
            ArrayList createTypedArrayList7 = parcel.createTypedArrayList(DatasetFieldFilter.CREATOR);
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            ClipData clipData = (ClipData) parcel.readParcelable(null, ClipData.class);
            IntentSender intentSender = (IntentSender) parcel.readParcelable(null, IntentSender.class);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            if (remoteViews != null || inlinePresentation != null || remoteViews2 != null) {
                Presentations.Builder builder2 = new Presentations.Builder();
                if (remoteViews != null) {
                    builder2.setMenuPresentation(remoteViews);
                }
                if (inlinePresentation != null) {
                    builder2.setInlinePresentation(inlinePresentation);
                }
                if (inlinePresentation2 != null) {
                    builder2.setInlineTooltipPresentation(inlinePresentation2);
                }
                if (remoteViews2 != null) {
                    builder2.setDialogPresentation(remoteViews2);
                }
                builder = new Builder(builder2.build());
            } else {
                builder = new Builder();
            }
            int i = 0;
            if (clipData != null) {
                builder.setContent((AutofillId) createTypedArrayList.get(0), clipData);
            }
            int size = createTypedArrayList5.size();
            while (i < createTypedArrayList.size()) {
                builder.createFromParcel((AutofillId) createTypedArrayList.get(i), createStringArrayList.get(i), (AutofillValue) createTypedArrayList2.get(i), (RemoteViews) createTypedArrayList3.get(i), i < size ? (InlinePresentation) createTypedArrayList5.get(i) : null, i < size ? (InlinePresentation) createTypedArrayList6.get(i) : null, (DatasetFieldFilter) createTypedArrayList7.get(i), (RemoteViews) createTypedArrayList4.get(i));
                i++;
            }
            builder.setAuthentication(intentSender);
            builder.setCredentialFillInIntent(intent);
            builder.setId(readString);
            Dataset build = builder.build();
            build.mEligibleReason = readInt;
            return build;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Dataset[] newArray(int i) {
            return new Dataset[i];
        }
    };
    public static final int PICK_REASON_NO_PCC = 1;
    public static final int PICK_REASON_PCC_DETECTION_ONLY = 4;
    public static final int PICK_REASON_PCC_DETECTION_PREFERRED_WITH_PROVIDER = 5;
    public static final int PICK_REASON_PROVIDER_DETECTION_ONLY = 2;
    public static final int PICK_REASON_PROVIDER_DETECTION_PREFERRED_WITH_PCC = 3;
    public static final int PICK_REASON_UNKNOWN = 0;
    private final IntentSender mAuthentication;
    private final ArrayList<String> mAutofillDatatypes;
    private Intent mCredentialFillInIntent;
    private final RemoteViews mDialogPresentation;
    private int mEligibleReason;
    private final ClipData mFieldContent;
    private final ArrayList<RemoteViews> mFieldDialogPresentations;
    private final ArrayList<DatasetFieldFilter> mFieldFilters;
    private final ArrayList<AutofillId> mFieldIds;
    private final ArrayList<InlinePresentation> mFieldInlinePresentations;
    private final ArrayList<InlinePresentation> mFieldInlineTooltipPresentations;
    private final ArrayList<RemoteViews> mFieldPresentations;
    private final ArrayList<AutofillValue> mFieldValues;
    String mId;
    private final InlinePresentation mInlinePresentation;
    private final InlinePresentation mInlineTooltipPresentation;
    private final RemoteViews mPresentation;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatasetEligibleReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Dataset(ArrayList<AutofillId> arrayList, ArrayList<AutofillValue> arrayList2, ArrayList<RemoteViews> arrayList3, ArrayList<RemoteViews> arrayList4, ArrayList<InlinePresentation> arrayList5, ArrayList<InlinePresentation> arrayList6, ArrayList<DatasetFieldFilter> arrayList7, ArrayList<String> arrayList8, ClipData clipData, RemoteViews remoteViews, RemoteViews remoteViews2, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, String str, IntentSender intentSender) {
        this.mFieldIds = arrayList;
        this.mFieldValues = arrayList2;
        this.mFieldPresentations = arrayList3;
        this.mFieldDialogPresentations = arrayList4;
        this.mFieldInlinePresentations = arrayList5;
        this.mFieldInlineTooltipPresentations = arrayList6;
        this.mAutofillDatatypes = arrayList8;
        this.mFieldFilters = arrayList7;
        this.mFieldContent = clipData;
        this.mPresentation = remoteViews;
        this.mDialogPresentation = remoteViews2;
        this.mInlinePresentation = inlinePresentation;
        this.mInlineTooltipPresentation = inlinePresentation2;
        this.mAuthentication = intentSender;
        this.mCredentialFillInIntent = null;
        this.mId = str;
    }

    public Dataset(Dataset dataset, ArrayList<AutofillId> arrayList) {
        this.mFieldIds = arrayList;
        this.mFieldValues = dataset.mFieldValues;
        this.mFieldPresentations = dataset.mFieldPresentations;
        this.mFieldDialogPresentations = dataset.mFieldDialogPresentations;
        this.mFieldInlinePresentations = dataset.mFieldInlinePresentations;
        this.mFieldInlineTooltipPresentations = dataset.mFieldInlineTooltipPresentations;
        this.mFieldFilters = dataset.mFieldFilters;
        this.mFieldContent = dataset.mFieldContent;
        this.mPresentation = dataset.mPresentation;
        this.mDialogPresentation = dataset.mDialogPresentation;
        this.mInlinePresentation = dataset.mInlinePresentation;
        this.mInlineTooltipPresentation = dataset.mInlineTooltipPresentation;
        this.mAuthentication = dataset.mAuthentication;
        this.mCredentialFillInIntent = dataset.mCredentialFillInIntent;
        this.mId = dataset.mId;
        this.mAutofillDatatypes = dataset.mAutofillDatatypes;
    }

    private Dataset(Builder builder) {
        this.mFieldIds = builder.mFieldIds;
        this.mFieldValues = builder.mFieldValues;
        this.mFieldPresentations = builder.mFieldPresentations;
        this.mFieldDialogPresentations = builder.mFieldDialogPresentations;
        this.mFieldInlinePresentations = builder.mFieldInlinePresentations;
        this.mFieldInlineTooltipPresentations = builder.mFieldInlineTooltipPresentations;
        this.mFieldFilters = builder.mFieldFilters;
        this.mFieldContent = builder.mFieldContent;
        this.mPresentation = builder.mPresentation;
        this.mDialogPresentation = builder.mDialogPresentation;
        this.mInlinePresentation = builder.mInlinePresentation;
        this.mInlineTooltipPresentation = builder.mInlineTooltipPresentation;
        this.mAuthentication = builder.mAuthentication;
        this.mCredentialFillInIntent = builder.mCredentialFillInIntent;
        this.mId = builder.mId;
        this.mAutofillDatatypes = builder.mAutofillDatatypes;
    }

    public ArrayList<String> getAutofillDatatypes() {
        return this.mAutofillDatatypes;
    }

    public ArrayList<AutofillId> getFieldIds() {
        return this.mFieldIds;
    }

    public ArrayList<AutofillValue> getFieldValues() {
        return this.mFieldValues;
    }

    public RemoteViews getFieldPresentation(int i) {
        RemoteViews remoteViews = this.mFieldPresentations.get(i);
        return remoteViews != null ? remoteViews : this.mPresentation;
    }

    public RemoteViews getFieldDialogPresentation(int i) {
        RemoteViews remoteViews = this.mFieldDialogPresentations.get(i);
        return remoteViews != null ? remoteViews : this.mDialogPresentation;
    }

    public InlinePresentation getFieldInlinePresentation(int i) {
        InlinePresentation inlinePresentation = this.mFieldInlinePresentations.get(i);
        return inlinePresentation != null ? inlinePresentation : this.mInlinePresentation;
    }

    public InlinePresentation getFieldInlineTooltipPresentation(int i) {
        InlinePresentation inlinePresentation = this.mFieldInlineTooltipPresentations.get(i);
        return inlinePresentation != null ? inlinePresentation : this.mInlineTooltipPresentation;
    }

    public DatasetFieldFilter getFilter(int i) {
        return this.mFieldFilters.get(i);
    }

    public ClipData getFieldContent() {
        return this.mFieldContent;
    }

    public IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public Intent getCredentialFillInIntent() {
        return this.mCredentialFillInIntent;
    }

    public void setCredentialFillInIntent(Intent intent) {
        this.mCredentialFillInIntent = intent;
    }

    public boolean isEmpty() {
        ArrayList<AutofillId> arrayList = this.mFieldIds;
        return arrayList == null || arrayList.isEmpty();
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder("Dataset[");
        if (this.mId == null) {
            sb.append("noId");
        } else {
            sb.append("id=");
            sb.append(this.mId.length());
            sb.append("_chars");
        }
        if (this.mFieldIds != null) {
            sb.append(", fieldIds=");
            sb.append(this.mFieldIds);
        }
        if (this.mFieldValues != null) {
            sb.append(", fieldValues=");
            sb.append(this.mFieldValues);
        }
        if (this.mFieldContent != null) {
            sb.append(", fieldContent=");
            sb.append(this.mFieldContent);
        }
        if (this.mFieldPresentations != null) {
            sb.append(", fieldPresentations=");
            sb.append(this.mFieldPresentations.size());
        }
        if (this.mFieldDialogPresentations != null) {
            sb.append(", fieldDialogPresentations=");
            sb.append(this.mFieldDialogPresentations.size());
        }
        if (this.mFieldInlinePresentations != null) {
            sb.append(", fieldInlinePresentations=");
            sb.append(this.mFieldInlinePresentations.size());
        }
        if (this.mFieldInlineTooltipPresentations != null) {
            sb.append(", fieldInlineTooltipInlinePresentations=");
            sb.append(this.mFieldInlineTooltipPresentations.size());
        }
        if (this.mFieldFilters != null) {
            sb.append(", fieldFilters=");
            sb.append(this.mFieldFilters.size());
        }
        if (this.mPresentation != null) {
            sb.append(", hasPresentation");
        }
        if (this.mDialogPresentation != null) {
            sb.append(", hasDialogPresentation");
        }
        if (this.mInlinePresentation != null) {
            sb.append(", hasInlinePresentation");
        }
        if (this.mInlineTooltipPresentation != null) {
            sb.append(", hasInlineTooltipPresentation");
        }
        if (this.mAuthentication != null) {
            sb.append(", hasAuthentication");
        }
        if (this.mCredentialFillInIntent != null) {
            sb.append(", hasAuthenticationExtras");
        }
        if (this.mAutofillDatatypes != null) {
            sb.append(", autofillDatatypes=");
            sb.append(this.mAutofillDatatypes);
        }
        sb.append(']');
        return sb.toString();
    }

    public String getId() {
        return this.mId;
    }

    public void setEligibleReasonReason(int i) {
        this.mEligibleReason = i;
    }

    public int getEligibleReason() {
        return this.mEligibleReason;
    }

    public static final class Builder {
        private IntentSender mAuthentication;
        private Intent mCredentialFillInIntent;
        private boolean mDestroyed;
        private RemoteViews mDialogPresentation;
        private ClipData mFieldContent;
        private String mId;
        private InlinePresentation mInlinePresentation;
        private InlinePresentation mInlineTooltipPresentation;
        private RemoteViews mPresentation;
        private ArrayList<AutofillId> mFieldIds = new ArrayList<>();
        private ArrayList<AutofillValue> mFieldValues = new ArrayList<>();
        private ArrayList<RemoteViews> mFieldPresentations = new ArrayList<>();
        private ArrayList<RemoteViews> mFieldDialogPresentations = new ArrayList<>();
        private ArrayList<InlinePresentation> mFieldInlinePresentations = new ArrayList<>();
        private ArrayList<InlinePresentation> mFieldInlineTooltipPresentations = new ArrayList<>();
        private ArrayList<DatasetFieldFilter> mFieldFilters = new ArrayList<>();
        private ArrayList<String> mAutofillDatatypes = new ArrayList<>();
        private ArrayMap<Field, Integer> mFieldToIndexdMap = new ArrayMap<>();

        @Deprecated
        public Builder(RemoteViews remoteViews) {
            Objects.requireNonNull(remoteViews, "presentation must be non-null");
            this.mPresentation = remoteViews;
        }

        @SystemApi
        @Deprecated
        public Builder(InlinePresentation inlinePresentation) {
            Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
        }

        public Builder(Presentations presentations) {
            Objects.requireNonNull(presentations, "presentations must be non-null");
            this.mPresentation = presentations.getMenuPresentation();
            this.mInlinePresentation = presentations.getInlinePresentation();
            this.mInlineTooltipPresentation = presentations.getInlineTooltipPresentation();
            this.mDialogPresentation = presentations.getDialogPresentation();
        }

        public Builder() {
        }

        @Deprecated
        public Builder setInlinePresentation(InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
            return this;
        }

        @Deprecated
        public Builder setInlinePresentation(InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
            this.mInlineTooltipPresentation = inlinePresentation2;
            return this;
        }

        public Builder setAuthentication(IntentSender intentSender) {
            throwIfDestroyed();
            this.mAuthentication = intentSender;
            return this;
        }

        public Builder setCredentialFillInIntent(Intent intent) {
            throwIfDestroyed();
            this.mCredentialFillInIntent = intent;
            return this;
        }

        public Builder setId(String str) {
            throwIfDestroyed();
            this.mId = str;
            return this;
        }

        @SystemApi
        public Builder setContent(AutofillId autofillId, ClipData clipData) {
            throwIfDestroyed();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Preconditions.checkArgument(clipData.getItemAt(i).getIntent() == null, "Content items cannot contain an Intent: content=" + clipData);
                }
            }
            setLifeTheUniverseAndEverything(autofillId, (AutofillValue) null, (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, (DatasetFieldFilter) null, (RemoteViews) null);
            this.mFieldContent = clipData;
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue) {
            throwIfDestroyed();
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, (DatasetFieldFilter) null, (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, (InlinePresentation) null, (InlinePresentation) null, (DatasetFieldFilter) null, (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern) {
            throwIfDestroyed();
            Preconditions.checkState(this.mPresentation != null, "Dataset presentation not set on constructor");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, new DatasetFieldFilter(pattern), (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern, RemoteViews remoteViews) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, (InlinePresentation) null, (InlinePresentation) null, new DatasetFieldFilter(pattern), (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews, InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, (InlinePresentation) null, (DatasetFieldFilter) null, (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, inlinePresentation2, (DatasetFieldFilter) null, (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern, RemoteViews remoteViews, InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, (InlinePresentation) null, new DatasetFieldFilter(pattern), (RemoteViews) null);
            return this;
        }

        @Deprecated
        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews, "presentation cannot be null");
            Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, inlinePresentation2, new DatasetFieldFilter(pattern), (RemoteViews) null);
            return this;
        }

        public Builder setField(AutofillId autofillId, Field field) {
            Builder builder;
            int lifeTheUniverseAndEverything;
            throwIfDestroyed();
            if (this.mFieldToIndexdMap.containsKey(field)) {
                int intValue = this.mFieldToIndexdMap.get(field).intValue();
                if (this.mFieldIds.get(intValue) == null) {
                    this.mFieldIds.set(intValue, autofillId);
                    return this;
                }
            }
            if (field == null) {
                lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(autofillId, (AutofillValue) null, (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, (DatasetFieldFilter) null, (RemoteViews) null);
                builder = this;
            } else {
                builder = this;
                DatasetFieldFilter datasetFieldFilter = field.getDatasetFieldFilter();
                Presentations presentations = field.getPresentations();
                if (presentations == null) {
                    lifeTheUniverseAndEverything = builder.setLifeTheUniverseAndEverything(autofillId, field.getValue(), (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, datasetFieldFilter, (RemoteViews) null);
                } else {
                    lifeTheUniverseAndEverything = builder.setLifeTheUniverseAndEverything(autofillId, field.getValue(), presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), datasetFieldFilter, presentations.getDialogPresentation());
                }
            }
            builder.mFieldToIndexdMap.put(field, Integer.valueOf(lifeTheUniverseAndEverything));
            return builder;
        }

        public Builder setField(String str, Field field) {
            Builder builder;
            int lifeTheUniverseAndEverything;
            throwIfDestroyed();
            if (this.mFieldToIndexdMap.containsKey(field)) {
                int intValue = this.mFieldToIndexdMap.get(field).intValue();
                if (this.mAutofillDatatypes.get(intValue) == null) {
                    this.mAutofillDatatypes.set(intValue, str);
                    return this;
                }
            }
            DatasetFieldFilter datasetFieldFilter = field.getDatasetFieldFilter();
            Presentations presentations = field.getPresentations();
            if (presentations == null) {
                builder = this;
                lifeTheUniverseAndEverything = builder.setLifeTheUniverseAndEverything(str, field.getValue(), (RemoteViews) null, (InlinePresentation) null, (InlinePresentation) null, datasetFieldFilter, (RemoteViews) null);
            } else {
                builder = this;
                lifeTheUniverseAndEverything = builder.setLifeTheUniverseAndEverything(str, field.getValue(), presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), datasetFieldFilter, presentations.getDialogPresentation());
            }
            builder.mFieldToIndexdMap.put(field, Integer.valueOf(lifeTheUniverseAndEverything));
            return builder;
        }

        public Builder setFieldForAllHints(Field field) {
            return setField(AutofillManager.ANY_HINT, field);
        }

        @SystemApi
        @Deprecated
        public Builder setFieldInlinePresentation(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern, InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (RemoteViews) null, inlinePresentation, (InlinePresentation) null, new DatasetFieldFilter(pattern), (RemoteViews) null);
            return this;
        }

        private int setLifeTheUniverseAndEverything(String str, AutofillValue autofillValue, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, DatasetFieldFilter datasetFieldFilter, RemoteViews remoteViews2) {
            Objects.requireNonNull(str, "datatype cannot be null");
            int indexOf = this.mAutofillDatatypes.indexOf(str);
            if (indexOf >= 0) {
                this.mAutofillDatatypes.add(str);
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return indexOf;
            }
            this.mFieldIds.add(null);
            this.mAutofillDatatypes.add(str);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
            return this.mFieldIds.size() - 1;
        }

        private int setLifeTheUniverseAndEverything(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, DatasetFieldFilter datasetFieldFilter, RemoteViews remoteViews2) {
            Objects.requireNonNull(autofillId, "id cannot be null");
            int indexOf = this.mFieldIds.indexOf(autofillId);
            if (indexOf >= 0) {
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return indexOf;
            }
            this.mFieldIds.add(autofillId);
            this.mAutofillDatatypes.add(null);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
            return this.mFieldIds.size() - 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createFromParcel(AutofillId autofillId, String str, AutofillValue autofillValue, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, DatasetFieldFilter datasetFieldFilter, RemoteViews remoteViews2) {
            int indexOf;
            if (autofillId != null && (indexOf = this.mFieldIds.indexOf(autofillId)) >= 0) {
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return;
            }
            this.mFieldIds.add(autofillId);
            this.mAutofillDatatypes.add(str);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
        }

        public Dataset build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            ArrayList<AutofillId> arrayList = this.mFieldIds;
            if (arrayList == null && this.mAutofillDatatypes == null) {
                throw new IllegalStateException("at least one of field or datatype must be set");
            }
            if (arrayList != null && this.mAutofillDatatypes != null && arrayList.size() == 0 && this.mAutofillDatatypes.size() == 0) {
                throw new IllegalStateException("at least one of field or datatype must be set");
            }
            if (this.mFieldContent != null) {
                if (this.mFieldIds.size() > 1) {
                    throw new IllegalStateException("when filling content, only one field can be filled");
                }
                if (this.mFieldValues.get(0) != null) {
                    throw new IllegalStateException("cannot fill both content and values");
                }
            }
            return new Dataset(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPresentation, i);
        parcel.writeParcelable(this.mDialogPresentation, i);
        parcel.writeParcelable(this.mInlinePresentation, i);
        parcel.writeParcelable(this.mInlineTooltipPresentation, i);
        parcel.writeTypedList(this.mFieldIds, i);
        parcel.writeTypedList(this.mFieldValues, i);
        parcel.writeTypedList(this.mFieldPresentations, i);
        parcel.writeTypedList(this.mFieldDialogPresentations, i);
        parcel.writeTypedList(this.mFieldInlinePresentations, i);
        parcel.writeTypedList(this.mFieldInlineTooltipPresentations, i);
        parcel.writeTypedList(this.mFieldFilters, i);
        parcel.writeStringList(this.mAutofillDatatypes);
        parcel.writeParcelable(this.mFieldContent, i);
        parcel.writeParcelable(this.mAuthentication, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mEligibleReason);
        parcel.writeTypedObject(this.mCredentialFillInIntent, i);
    }

    public static final class DatasetFieldFilter implements Parcelable {
        public static final Parcelable.Creator<DatasetFieldFilter> CREATOR = new Parcelable.Creator<DatasetFieldFilter>() { // from class: android.service.autofill.Dataset.DatasetFieldFilter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DatasetFieldFilter createFromParcel(Parcel parcel) {
                return new DatasetFieldFilter((Pattern) parcel.readSerializable(Pattern.class.getClassLoader(), Pattern.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DatasetFieldFilter[] newArray(int i) {
                return new DatasetFieldFilter[i];
            }
        };
        public final Pattern pattern;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        DatasetFieldFilter(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return this.pattern;
        }

        public String toString() {
            if (!Helper.sDebug) {
                return super.toString();
            }
            if (this.pattern == null) {
                return PerfettoProtoLogImpl.NULL_STRING;
            }
            return this.pattern.pattern().length() + "_chars";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable(this.pattern);
        }
    }
}
