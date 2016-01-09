package com.inthecheesefactory.thecheeselibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

/**
 * Created by nuuneoi on 1/9/2016.
 */
public class BaseCustomViewGroup extends FrameLayout {

    public BaseCustomViewGroup(Context context) {
        super(context);
    }

    public BaseCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        // Save Children's state as a Bundle
        Bundle bundle = new Bundle();
        SparseArray childrenStates = new SparseArray();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(childrenStates);
        }
        bundle.putSparseParcelableArray("childrenStates", childrenStates);

        // Save it to Parcelable
        BundleSavedState ss = new BundleSavedState(superState);
        ss.setBundle(bundle);
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        // Restore SparseArray
        SparseArray childrenStates = ss.getBundle().getSparseParcelableArray("childrenStates");

        // Restore Children's state
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(childrenStates);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

}
