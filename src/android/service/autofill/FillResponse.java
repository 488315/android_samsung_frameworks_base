package android.service.autofill;

import android.app.PendingIntent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Downloads;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public final class FillResponse implements Parcelable {
    public static final Parcelable.Creator<FillResponse> CREATOR = new Parcelable.Creator<FillResponse>() { // from class: android.service.autofill.FillResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillResponse createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readParcelable(null, ParceledListSlice.class);
            List list = parceledListSlice != null ? parceledListSlice.getList() : null;
            int size = list != null ? list.size() : 0;
            for (int i = 0; i < size; i++) {
                builder.addDataset((Dataset) list.get(i));
            }
            builder.setSaveInfo((SaveInfo) parcel.readParcelable(null, SaveInfo.class));
            builder.setClientState((Bundle) parcel.readParcelable(null, Bundle.class));
            AutofillId[] autofillIdArr = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            IntentSender intentSender = (IntentSender) parcel.readParcelable(null, IntentSender.class);
            RemoteViews remoteViews = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            InlinePresentation inlinePresentation = (InlinePresentation) parcel.readParcelable(null, InlinePresentation.class);
            InlinePresentation inlinePresentation2 = (InlinePresentation) parcel.readParcelable(null, InlinePresentation.class);
            RemoteViews remoteViews2 = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (autofillIdArr != null) {
                builder.setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, inlinePresentation2, remoteViews2);
            }
            RemoteViews remoteViews3 = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (remoteViews3 != null) {
                builder.setDialogHeader(remoteViews3);
            }
            PendingIntent pendingIntent = (PendingIntent) parcel.readParcelable(null, PendingIntent.class);
            if (pendingIntent != null) {
                builder.setDialogPendingIntent(pendingIntent);
            }
            AutofillId[] autofillIdArr2 = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            if (autofillIdArr2 != null) {
                builder.setFillDialogTriggerIds(autofillIdArr2);
            }
            RemoteViews remoteViews4 = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (remoteViews4 != null) {
                builder.setHeader(remoteViews4);
            }
            RemoteViews remoteViews5 = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (remoteViews5 != null) {
                builder.setFooter(remoteViews5);
            }
            UserData userData = (UserData) parcel.readParcelable(null, UserData.class);
            if (userData != null) {
                builder.setUserData(userData);
            }
            builder.setIgnoredIds((AutofillId[]) parcel.readParcelableArray(null, AutofillId.class));
            long readLong = parcel.readLong();
            if (readLong > 0) {
                builder.disableAutofill(readLong);
            }
            AutofillId[] autofillIdArr3 = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            if (autofillIdArr3 != null) {
                builder.setFieldClassificationIds(autofillIdArr3);
            }
            android.service.assist.classification.FieldClassification[] fieldClassificationArr = (android.service.assist.classification.FieldClassification[]) parcel.readParcelableArray(null, android.service.assist.classification.FieldClassification.class);
            if (fieldClassificationArr != null) {
                builder.setDetectedFieldClassifications(Set.of((Object[]) fieldClassificationArr));
            }
            builder.setIconResourceId(parcel.readInt());
            builder.setServiceDisplayNameResourceId(parcel.readInt());
            builder.setShowFillDialogIcon(parcel.readBoolean());
            builder.setShowSaveDialogIcon(parcel.readBoolean());
            builder.setFlags(parcel.readInt());
            builder.setPresentationCancelIds(parcel.createIntArray());
            FillResponse build = builder.build();
            build.setRequestId(parcel.readInt());
            return build;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillResponse[] newArray(int i) {
            return new FillResponse[i];
        }
    };
    public static final int FLAG_CREDENTIAL_MANAGER_RESPONSE = 8;
    public static final int FLAG_DELAY_FILL = 4;
    public static final int FLAG_DISABLE_ACTIVITY_ONLY = 2;
    public static final int FLAG_TRACK_CONTEXT_COMMITED = 1;
    private final IntentSender mAuthentication;
    private final AutofillId[] mAuthenticationIds;
    private final int[] mCancelIds;
    private final Bundle mClientState;
    private final ParceledListSlice<Dataset> mDatasets;
    private final android.service.assist.classification.FieldClassification[] mDetectedFieldTypes;
    private final RemoteViews mDialogHeader;
    private final PendingIntent mDialogPendingIntent;
    private final RemoteViews mDialogPresentation;
    private final long mDisableDuration;
    private final AutofillId[] mFieldClassificationIds;
    private final AutofillId[] mFillDialogTriggerIds;
    private final int mFlags;
    private final RemoteViews mFooter;
    private final RemoteViews mHeader;
    private final int mIconResourceId;
    private final AutofillId[] mIgnoredIds;
    private final InlinePresentation mInlinePresentation;
    private final InlinePresentation mInlineTooltipPresentation;
    private final RemoteViews mPresentation;
    private int mRequestId;
    private final SaveInfo mSaveInfo;
    private final int mServiceDisplayNameResourceId;
    private final boolean mShowFillDialogIcon;
    private final boolean mShowSaveDialogIcon;
    private final boolean mSupportsInlineSuggestions;
    private final UserData mUserData;

    @Retention(RetentionPolicy.SOURCE)
    @interface FillResponseFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static FillResponse shallowCopy(FillResponse fillResponse, List<Dataset> list, SaveInfo saveInfo) {
        return new FillResponse(list != null ? new ParceledListSlice(list) : null, saveInfo, fillResponse.mClientState, fillResponse.mPresentation, fillResponse.mInlinePresentation, fillResponse.mInlineTooltipPresentation, fillResponse.mDialogPresentation, fillResponse.mDialogHeader, fillResponse.mHeader, fillResponse.mFooter, fillResponse.mAuthentication, fillResponse.mAuthenticationIds, fillResponse.mIgnoredIds, fillResponse.mFillDialogTriggerIds, fillResponse.mDisableDuration, fillResponse.mFieldClassificationIds, fillResponse.mFlags, fillResponse.mRequestId, fillResponse.mUserData, fillResponse.mCancelIds, fillResponse.mSupportsInlineSuggestions, fillResponse.mIconResourceId, fillResponse.mServiceDisplayNameResourceId, fillResponse.mShowFillDialogIcon, fillResponse.mShowSaveDialogIcon, fillResponse.mDetectedFieldTypes, fillResponse.mDialogPendingIntent);
    }

    private FillResponse(ParceledListSlice<Dataset> parceledListSlice, SaveInfo saveInfo, Bundle bundle, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, RemoteViews remoteViews5, IntentSender intentSender, AutofillId[] autofillIdArr, AutofillId[] autofillIdArr2, AutofillId[] autofillIdArr3, long j, AutofillId[] autofillIdArr4, int i, int i2, UserData userData, int[] iArr, boolean z, int i3, int i4, boolean z2, boolean z3, android.service.assist.classification.FieldClassification[] fieldClassificationArr, PendingIntent pendingIntent) {
        this.mDatasets = parceledListSlice;
        this.mSaveInfo = saveInfo;
        this.mClientState = bundle;
        this.mPresentation = remoteViews;
        this.mInlinePresentation = inlinePresentation;
        this.mInlineTooltipPresentation = inlinePresentation2;
        this.mDialogPresentation = remoteViews2;
        this.mDialogHeader = remoteViews3;
        this.mHeader = remoteViews4;
        this.mFooter = remoteViews5;
        this.mAuthentication = intentSender;
        this.mAuthenticationIds = autofillIdArr;
        this.mIgnoredIds = autofillIdArr2;
        this.mFillDialogTriggerIds = autofillIdArr3;
        this.mDisableDuration = j;
        this.mFieldClassificationIds = autofillIdArr4;
        this.mFlags = i;
        this.mRequestId = i2;
        this.mUserData = userData;
        this.mCancelIds = iArr;
        this.mSupportsInlineSuggestions = z;
        this.mIconResourceId = i3;
        this.mServiceDisplayNameResourceId = i4;
        this.mShowFillDialogIcon = z2;
        this.mShowSaveDialogIcon = z3;
        this.mDetectedFieldTypes = fieldClassificationArr;
        this.mDialogPendingIntent = pendingIntent;
    }

    private FillResponse(Builder builder) {
        this.mDatasets = builder.mDatasets != null ? new ParceledListSlice<>(builder.mDatasets) : null;
        this.mSaveInfo = builder.mSaveInfo;
        this.mClientState = builder.mClientState;
        this.mPresentation = builder.mPresentation;
        this.mInlinePresentation = builder.mInlinePresentation;
        this.mInlineTooltipPresentation = builder.mInlineTooltipPresentation;
        this.mDialogPresentation = builder.mDialogPresentation;
        this.mDialogHeader = builder.mDialogHeader;
        this.mHeader = builder.mHeader;
        this.mFooter = builder.mFooter;
        this.mAuthentication = builder.mAuthentication;
        this.mAuthenticationIds = builder.mAuthenticationIds;
        this.mFillDialogTriggerIds = builder.mFillDialogTriggerIds;
        this.mIgnoredIds = builder.mIgnoredIds;
        this.mDisableDuration = builder.mDisableDuration;
        this.mFieldClassificationIds = builder.mFieldClassificationIds;
        this.mFlags = builder.mFlags;
        this.mRequestId = Integer.MIN_VALUE;
        this.mUserData = builder.mUserData;
        this.mCancelIds = builder.mCancelIds;
        this.mSupportsInlineSuggestions = builder.mSupportsInlineSuggestions;
        this.mIconResourceId = builder.mIconResourceId;
        this.mServiceDisplayNameResourceId = builder.mServiceDisplayNameResourceId;
        this.mShowFillDialogIcon = builder.mShowFillDialogIcon;
        this.mShowSaveDialogIcon = builder.mShowSaveDialogIcon;
        this.mDetectedFieldTypes = builder.mDetectedFieldTypes;
        this.mDialogPendingIntent = builder.mDialogPendingIntent;
    }

    public Set<android.service.assist.classification.FieldClassification> getDetectedFieldClassifications() {
        return Set.of((Object[]) this.mDetectedFieldTypes);
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<Dataset> getDatasets() {
        ParceledListSlice<Dataset> parceledListSlice = this.mDatasets;
        if (parceledListSlice != null) {
            return parceledListSlice.getList();
        }
        return null;
    }

    public SaveInfo getSaveInfo() {
        return this.mSaveInfo;
    }

    public RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public InlinePresentation getInlinePresentation() {
        return this.mInlinePresentation;
    }

    public InlinePresentation getInlineTooltipPresentation() {
        return this.mInlineTooltipPresentation;
    }

    public RemoteViews getDialogPresentation() {
        return this.mDialogPresentation;
    }

    public RemoteViews getDialogHeader() {
        return this.mDialogHeader;
    }

    public RemoteViews getHeader() {
        return this.mHeader;
    }

    public RemoteViews getFooter() {
        return this.mFooter;
    }

    public IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public AutofillId[] getAuthenticationIds() {
        return this.mAuthenticationIds;
    }

    public AutofillId[] getFillDialogTriggerIds() {
        return this.mFillDialogTriggerIds;
    }

    public AutofillId[] getIgnoredIds() {
        return this.mIgnoredIds;
    }

    public long getDisableDuration() {
        return this.mDisableDuration;
    }

    public AutofillId[] getFieldClassificationIds() {
        return this.mFieldClassificationIds;
    }

    public UserData getUserData() {
        return this.mUserData;
    }

    public int getIconResourceId() {
        return this.mIconResourceId;
    }

    public int getServiceDisplayNameResourceId() {
        return this.mServiceDisplayNameResourceId;
    }

    public boolean getShowFillDialogIcon() {
        return this.mShowFillDialogIcon;
    }

    public boolean getShowSaveDialogIcon() {
        return this.mShowSaveDialogIcon;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int[] getCancelIds() {
        return this.mCancelIds;
    }

    public boolean supportsInlineSuggestions() {
        return this.mSupportsInlineSuggestions;
    }

    public static final class Builder {
        private IntentSender mAuthentication;
        private AutofillId[] mAuthenticationIds;
        private int[] mCancelIds;
        private Bundle mClientState;
        private ArrayList<Dataset> mDatasets;
        private boolean mDestroyed;
        private android.service.assist.classification.FieldClassification[] mDetectedFieldTypes;
        private RemoteViews mDialogHeader;
        private PendingIntent mDialogPendingIntent;
        private RemoteViews mDialogPresentation;
        private long mDisableDuration;
        private AutofillId[] mFieldClassificationIds;
        private AutofillId[] mFillDialogTriggerIds;
        private int mFlags;
        private RemoteViews mFooter;
        private RemoteViews mHeader;
        private int mIconResourceId;
        private AutofillId[] mIgnoredIds;
        private InlinePresentation mInlinePresentation;
        private InlinePresentation mInlineTooltipPresentation;
        private RemoteViews mPresentation;
        private SaveInfo mSaveInfo;
        private int mServiceDisplayNameResourceId;
        private boolean mShowFillDialogIcon = true;
        private boolean mShowSaveDialogIcon = true;
        private boolean mSupportsInlineSuggestions;
        private UserData mUserData;

        public Builder setDetectedFieldClassifications(Set<android.service.assist.classification.FieldClassification> set) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mDetectedFieldTypes = (android.service.assist.classification.FieldClassification[]) set.toArray(new android.service.assist.classification.FieldClassification[0]);
            return this;
        }

        @Deprecated
        public Builder setAuthentication(AutofillId[] autofillIdArr, IntentSender intentSender, RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (this.mHeader != null || this.mFooter != null) {
                throw new IllegalStateException("Already called #setHeader() or #setFooter()");
            }
            if ((remoteViews == null) ^ (intentSender == null)) {
                throw new IllegalArgumentException("authentication and presentation must be both non-null or null");
            }
            this.mAuthentication = intentSender;
            this.mPresentation = remoteViews;
            this.mAuthenticationIds = AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        @Deprecated
        public Builder setAuthentication(AutofillId[] autofillIdArr, IntentSender intentSender, RemoteViews remoteViews, InlinePresentation inlinePresentation) {
            return setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, null);
        }

        @Deprecated
        public Builder setAuthentication(AutofillId[] autofillIdArr, IntentSender intentSender, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            return setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, inlinePresentation2, null);
        }

        public Builder setAuthentication(AutofillId[] autofillIdArr, IntentSender intentSender, Presentations presentations) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (presentations == null) {
                return setAuthentication(autofillIdArr, intentSender, null, null, null, null);
            }
            return setAuthentication(autofillIdArr, intentSender, presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), presentations.getDialogPresentation());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Builder setAuthentication(AutofillId[] autofillIdArr, IntentSender intentSender, RemoteViews remoteViews, InlinePresentation inlinePresentation, InlinePresentation inlinePresentation2, RemoteViews remoteViews2) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (this.mHeader != null || this.mFooter != null) {
                throw new IllegalStateException("Already called #setHeader() or #setFooter()");
            }
            if ((remoteViews == null && inlinePresentation == null) ^ (intentSender == null)) {
                throw new IllegalArgumentException("authentication and presentation (dropdown or inline), must be both non-null or null");
            }
            this.mAuthentication = intentSender;
            this.mPresentation = remoteViews;
            this.mInlinePresentation = inlinePresentation;
            this.mInlineTooltipPresentation = inlinePresentation2;
            this.mDialogPresentation = remoteViews2;
            this.mAuthenticationIds = AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        public Builder setIgnoredIds(AutofillId... autofillIdArr) {
            throwIfDestroyed();
            this.mIgnoredIds = autofillIdArr;
            return this;
        }

        public Builder addDataset(Dataset dataset) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (dataset == null) {
                return this;
            }
            if (this.mDatasets == null) {
                this.mDatasets = new ArrayList<>();
            }
            this.mDatasets.add(dataset);
            return this;
        }

        public Builder setDatasets(ArrayList<Dataset> arrayList) {
            this.mDatasets = arrayList;
            return this;
        }

        public Builder setSaveInfo(SaveInfo saveInfo) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mSaveInfo = saveInfo;
            return this;
        }

        public Builder setClientState(Bundle bundle) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mClientState = bundle;
            return this;
        }

        public Builder setFieldClassificationIds(AutofillId... autofillIdArr) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            Preconditions.checkArrayElementsNotNull(autofillIdArr, Downloads.EXTRA_IDS);
            Preconditions.checkArgumentInRange(autofillIdArr.length, 1, UserData.getMaxFieldClassificationIdsSize(), "ids length");
            this.mFieldClassificationIds = autofillIdArr;
            this.mFlags |= 1;
            return this;
        }

        public Builder setFlags(int i) {
            throwIfDestroyed();
            this.mFlags = Preconditions.checkFlagsArgument(i, 15);
            return this;
        }

        public Builder disableAutofill(long j) {
            throwIfDestroyed();
            if (j <= 0) {
                throw new IllegalArgumentException("duration must be greater than 0");
            }
            if (this.mAuthentication != null || this.mDatasets != null || this.mSaveInfo != null || this.mFieldClassificationIds != null || this.mClientState != null) {
                throw new IllegalStateException("disableAutofill() must be the only method called");
            }
            this.mDisableDuration = j;
            return this;
        }

        public Builder setIconResourceId(int i) {
            throwIfDestroyed();
            this.mIconResourceId = i;
            return this;
        }

        public Builder setServiceDisplayNameResourceId(int i) {
            throwIfDestroyed();
            this.mServiceDisplayNameResourceId = i;
            return this;
        }

        public Builder setShowFillDialogIcon(boolean z) {
            throwIfDestroyed();
            this.mShowFillDialogIcon = z;
            return this;
        }

        public Builder setShowSaveDialogIcon(boolean z) {
            throwIfDestroyed();
            this.mShowSaveDialogIcon = z;
            return this;
        }

        public Builder setHeader(RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mHeader = (RemoteViews) Objects.requireNonNull(remoteViews);
            return this;
        }

        public Builder setFooter(RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mFooter = (RemoteViews) Objects.requireNonNull(remoteViews);
            return this;
        }

        public Builder setUserData(UserData userData) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mUserData = (UserData) Objects.requireNonNull(userData);
            return this;
        }

        public Builder setPresentationCancelIds(int[] iArr) {
            throwIfDestroyed();
            this.mCancelIds = iArr;
            return this;
        }

        public Builder setDialogHeader(RemoteViews remoteViews) {
            throwIfDestroyed();
            Objects.requireNonNull(remoteViews);
            this.mDialogHeader = remoteViews;
            return this;
        }

        public Builder setFillDialogTriggerIds(AutofillId... autofillIdArr) {
            throwIfDestroyed();
            Preconditions.checkArrayElementsNotNull(autofillIdArr, Downloads.EXTRA_IDS);
            this.mFillDialogTriggerIds = autofillIdArr;
            return this;
        }

        public Builder setDialogPendingIntent(PendingIntent pendingIntent) {
            throwIfDestroyed();
            Preconditions.checkNotNull(pendingIntent, "can't pass a null object to setDialogPendingIntent");
            this.mDialogPendingIntent = pendingIntent;
            return this;
        }

        public FillResponse build() {
            throwIfDestroyed();
            if (this.mAuthentication == null && this.mDatasets == null && this.mSaveInfo == null && this.mDisableDuration == 0 && this.mFieldClassificationIds == null && this.mClientState == null) {
                throw new IllegalStateException("need to provide: at least one DataSet, or a SaveInfo, or an authentication with a presentation, or a FieldsDetection, or a client state, or disable autofill");
            }
            ArrayList<Dataset> arrayList = this.mDatasets;
            if (arrayList == null && (this.mHeader != null || this.mFooter != null)) {
                throw new IllegalStateException("must add at least 1 dataset when using header or footer");
            }
            if (arrayList != null) {
                Iterator<Dataset> it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (it.next().getFieldInlinePresentation(0) != null) {
                        this.mSupportsInlineSuggestions = true;
                        break;
                    }
                }
            } else if (this.mInlinePresentation != null) {
                this.mSupportsInlineSuggestions = true;
            }
            this.mDestroyed = true;
            return new FillResponse(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }

        private void throwIfDisableAutofillCalled() {
            if (this.mDisableDuration > 0) {
                throw new IllegalStateException("Already called #disableAutofill()");
            }
        }

        private void throwIfAuthenticationCalled() {
            if (this.mAuthentication != null) {
                throw new IllegalStateException("Already called #setAuthentication()");
            }
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder("FillResponse : [mRequestId=" + this.mRequestId);
        if (this.mDatasets != null) {
            sb.append(", datasets=");
            sb.append(this.mDatasets.getList());
        }
        if (this.mSaveInfo != null) {
            sb.append(", saveInfo=");
            sb.append(this.mSaveInfo);
        }
        if (this.mClientState != null) {
            sb.append(", hasClientState");
        }
        if (this.mPresentation != null) {
            sb.append(", hasPresentation");
        }
        if (this.mInlinePresentation != null) {
            sb.append(", hasInlinePresentation");
        }
        if (this.mInlineTooltipPresentation != null) {
            sb.append(", hasInlineTooltipPresentation");
        }
        if (this.mDialogPresentation != null) {
            sb.append(", hasDialogPresentation");
        }
        if (this.mDialogHeader != null) {
            sb.append(", hasDialogHeader");
        }
        if (this.mHeader != null) {
            sb.append(", hasHeader");
        }
        if (this.mFooter != null) {
            sb.append(", hasFooter");
        }
        if (this.mAuthentication != null) {
            sb.append(", hasAuthentication");
        }
        if (this.mDialogPendingIntent != null) {
            sb.append(", hasDialogPendingIntent");
        }
        if (this.mAuthenticationIds != null) {
            sb.append(", authenticationIds=");
            sb.append(Arrays.toString(this.mAuthenticationIds));
        }
        if (this.mFillDialogTriggerIds != null) {
            sb.append(", fillDialogTriggerIds=");
            sb.append(Arrays.toString(this.mFillDialogTriggerIds));
        }
        sb.append(", disableDuration=");
        sb.append(this.mDisableDuration);
        if (this.mFlags != 0) {
            sb.append(", flags=");
            sb.append(this.mFlags);
        }
        AutofillId[] autofillIdArr = this.mFieldClassificationIds;
        if (autofillIdArr != null) {
            sb.append(Arrays.toString(autofillIdArr));
        }
        if (this.mUserData != null) {
            sb.append(", userData=");
            sb.append(this.mUserData);
        }
        if (this.mCancelIds != null) {
            sb.append(", mCancelIds=");
            sb.append(this.mCancelIds.length);
        }
        sb.append(", mSupportInlinePresentations=");
        sb.append(this.mSupportsInlineSuggestions);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mDatasets, i);
        parcel.writeParcelable(this.mSaveInfo, i);
        parcel.writeParcelable(this.mClientState, i);
        parcel.writeParcelableArray(this.mAuthenticationIds, i);
        parcel.writeParcelable(this.mAuthentication, i);
        parcel.writeParcelable(this.mPresentation, i);
        parcel.writeParcelable(this.mInlinePresentation, i);
        parcel.writeParcelable(this.mInlineTooltipPresentation, i);
        parcel.writeParcelable(this.mDialogPresentation, i);
        parcel.writeParcelable(this.mDialogHeader, i);
        parcel.writeParcelable(this.mDialogPendingIntent, i);
        parcel.writeParcelableArray(this.mFillDialogTriggerIds, i);
        parcel.writeParcelable(this.mHeader, i);
        parcel.writeParcelable(this.mFooter, i);
        parcel.writeParcelable(this.mUserData, i);
        parcel.writeParcelableArray(this.mIgnoredIds, i);
        parcel.writeLong(this.mDisableDuration);
        parcel.writeParcelableArray(this.mFieldClassificationIds, i);
        parcel.writeParcelableArray(this.mDetectedFieldTypes, i);
        parcel.writeInt(this.mIconResourceId);
        parcel.writeInt(this.mServiceDisplayNameResourceId);
        parcel.writeBoolean(this.mShowFillDialogIcon);
        parcel.writeBoolean(this.mShowSaveDialogIcon);
        parcel.writeInt(this.mFlags);
        parcel.writeIntArray(this.mCancelIds);
        parcel.writeInt(this.mRequestId);
    }
}
