package com.example.presenter.view.mystreamer.databinding;
import com.example.presenter.view.mystreamer.R;
import com.example.presenter.view.mystreamer.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding implements com.example.presenter.view.mystreamer.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.pW1, 8);
        sViewsWithIds.put(R.id.txt, 9);
        sViewsWithIds.put(R.id.prg_bar, 10);
        sViewsWithIds.put(R.id.rv, 11);
        sViewsWithIds.put(R.id.control, 12);
        sViewsWithIds.put(R.id.live_btn, 13);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[6]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[5]
            , (com.google.android.exoplayer2.ui.PlayerView) bindings[8]
            , (android.widget.Button) bindings[3]
            , (android.widget.ProgressBar) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[11]
            , (android.widget.Button) bindings[4]
            , (android.widget.Button) bindings[2]
            , (android.widget.TextView) bindings[9]
            );
        this.edtDate.setTag(null);
        this.edtTime.setTag(null);
        this.layout.setTag(null);
        this.oneMinBackShiftBtn.setTag(null);
        this.oneMinNextShiftBtn.setTag(null);
        this.playBtn.setTag(null);
        this.tenSecNextShiftBtn.setTag(null);
        this.tenSesBackShiftBtn.setTag(null);
        setRootTag(root);
        // listeners
        mCallback4 = new com.example.presenter.view.mystreamer.generated.callback.OnClickListener(this, 4);
        mCallback2 = new com.example.presenter.view.mystreamer.generated.callback.OnClickListener(this, 2);
        mCallback5 = new com.example.presenter.view.mystreamer.generated.callback.OnClickListener(this, 5);
        mCallback3 = new com.example.presenter.view.mystreamer.generated.callback.OnClickListener(this, 3);
        mCallback1 = new com.example.presenter.view.mystreamer.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x200L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.timeVm == variableId) {
            setTimeVm((com.example.presenter.view.mystreamer.dataBinding.TimeViewModel) variable);
        }
        else if (BR.dateVm == variableId) {
            setDateVm((com.example.presenter.view.mystreamer.dataBinding.DateViewModel) variable);
        }
        else if (BR.activity == variableId) {
            setActivity((com.example.presenter.view.mystreamer.MainActivity) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTimeVm(@Nullable com.example.presenter.view.mystreamer.dataBinding.TimeViewModel TimeVm) {
        updateRegistration(0, TimeVm);
        this.mTimeVm = TimeVm;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.timeVm);
        super.requestRebind();
    }
    public void setDateVm(@Nullable com.example.presenter.view.mystreamer.dataBinding.DateViewModel DateVm) {
        updateRegistration(1, DateVm);
        this.mDateVm = DateVm;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.dateVm);
        super.requestRebind();
    }
    public void setActivity(@Nullable com.example.presenter.view.mystreamer.MainActivity Activity) {
        this.mActivity = Activity;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.activity);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeTimeVm((com.example.presenter.view.mystreamer.dataBinding.TimeViewModel) object, fieldId);
            case 1 :
                return onChangeDateVm((com.example.presenter.view.mystreamer.dataBinding.DateViewModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeTimeVm(com.example.presenter.view.mystreamer.dataBinding.TimeViewModel TimeVm, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.hour) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.min) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.sec) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeDateVm(com.example.presenter.view.mystreamer.dataBinding.DateViewModel DateVm, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.year) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.month) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.day) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.presenter.view.mystreamer.dataBinding.TimeViewModel timeVm = mTimeVm;
        java.lang.String dateVmDay = null;
        java.lang.String timeVmHourJavaLangString = null;
        java.lang.String dateVmYear = null;
        java.lang.String dateVmYearJavaLangStringDateVmMonth = null;
        java.lang.String timeVmMin = null;
        java.lang.String dateVmYearJavaLangStringDateVmMonthJavaLangString = null;
        java.lang.String dateVmYearJavaLangStringDateVmMonthJavaLangStringDateVmDay = null;
        java.lang.String timeVmHourJavaLangStringTimeVmMinJavaLangString = null;
        java.lang.String timeVmSec = null;
        com.example.presenter.view.mystreamer.dataBinding.DateViewModel dateVm = mDateVm;
        java.lang.String timeVmHour = null;
        java.lang.String dateVmMonth = null;
        java.lang.String timeVmHourJavaLangStringTimeVmMin = null;
        java.lang.String dateVmYearJavaLangString = null;
        com.example.presenter.view.mystreamer.MainActivity activity = mActivity;
        java.lang.String timeVmHourJavaLangStringTimeVmMinJavaLangStringTimeVmSec = null;

        if ((dirtyFlags & 0x239L) != 0) {



                if (timeVm != null) {
                    // read timeVm.min
                    timeVmMin = timeVm.getMin();
                    // read timeVm.sec
                    timeVmSec = timeVm.getSec();
                    // read timeVm.hour
                    timeVmHour = timeVm.getHour();
                }


                // read (timeVm.hour) + (":")
                timeVmHourJavaLangString = (timeVmHour) + (":");


                // read ((timeVm.hour) + (":")) + (timeVm.min)
                timeVmHourJavaLangStringTimeVmMin = (timeVmHourJavaLangString) + (timeVmMin);


                // read (((timeVm.hour) + (":")) + (timeVm.min)) + (":")
                timeVmHourJavaLangStringTimeVmMinJavaLangString = (timeVmHourJavaLangStringTimeVmMin) + (":");


                // read ((((timeVm.hour) + (":")) + (timeVm.min)) + (":")) + (timeVm.sec)
                timeVmHourJavaLangStringTimeVmMinJavaLangStringTimeVmSec = (timeVmHourJavaLangStringTimeVmMinJavaLangString) + (timeVmSec);
        }
        if ((dirtyFlags & 0x3c2L) != 0) {



                if (dateVm != null) {
                    // read dateVm.day
                    dateVmDay = dateVm.getDay();
                    // read dateVm.year
                    dateVmYear = dateVm.getYear();
                    // read dateVm.month
                    dateVmMonth = dateVm.getMonth();
                }


                // read (dateVm.year) + (":")
                dateVmYearJavaLangString = (dateVmYear) + (":");


                // read ((dateVm.year) + (":")) + (dateVm.month)
                dateVmYearJavaLangStringDateVmMonth = (dateVmYearJavaLangString) + (dateVmMonth);


                // read (((dateVm.year) + (":")) + (dateVm.month)) + (":")
                dateVmYearJavaLangStringDateVmMonthJavaLangString = (dateVmYearJavaLangStringDateVmMonth) + (":");


                // read ((((dateVm.year) + (":")) + (dateVm.month)) + (":")) + (dateVm.day)
                dateVmYearJavaLangStringDateVmMonthJavaLangStringDateVmDay = (dateVmYearJavaLangStringDateVmMonthJavaLangString) + (dateVmDay);
        }
        // batch finished
        if ((dirtyFlags & 0x3c2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.edtDate, dateVmYearJavaLangStringDateVmMonthJavaLangStringDateVmDay);
        }
        if ((dirtyFlags & 0x239L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.edtTime, timeVmHourJavaLangStringTimeVmMinJavaLangStringTimeVmSec);
        }
        if ((dirtyFlags & 0x200L) != 0) {
            // api target 1

            this.oneMinBackShiftBtn.setOnClickListener(mCallback1);
            this.oneMinNextShiftBtn.setOnClickListener(mCallback5);
            this.playBtn.setOnClickListener(mCallback3);
            this.tenSecNextShiftBtn.setOnClickListener(mCallback4);
            this.tenSesBackShiftBtn.setOnClickListener(mCallback2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 4: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.presenter.view.mystreamer.MainActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {



                    activity.timeShiftForWardSec(callbackArg_0);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.presenter.view.mystreamer.MainActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {



                    activity.timeShiftBackWardSec(callbackArg_0);
                }
                break;
            }
            case 5: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.presenter.view.mystreamer.MainActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {



                    activity.timeShiftForWardMin(callbackArg_0);
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.presenter.view.mystreamer.MainActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {



                    activity.playFile(callbackArg_0);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.presenter.view.mystreamer.MainActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {



                    activity.timeShiftBackWardMin(callbackArg_0);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): timeVm
        flag 1 (0x2L): dateVm
        flag 2 (0x3L): activity
        flag 3 (0x4L): timeVm.hour
        flag 4 (0x5L): timeVm.min
        flag 5 (0x6L): timeVm.sec
        flag 6 (0x7L): dateVm.year
        flag 7 (0x8L): dateVm.month
        flag 8 (0x9L): dateVm.day
        flag 9 (0xaL): null
    flag mapping end*/
    //end
}