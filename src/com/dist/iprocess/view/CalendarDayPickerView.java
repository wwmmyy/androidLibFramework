/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2014 Robin Chutaux

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.dist.iprocess.view;

import com.dist.iprocess.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
/**
 * https://github.com/traex/CalendarListview
* @类名: CalendarDayPickerView 
* @描述: TODO 
* @作者: 王明远 
* @日期: 2015年10月22日 下午1:19:05 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public class CalendarDayPickerView extends RecyclerView
{
    protected Context mContext;
	protected CalendarMonthAdapter mAdapter;
	private CalendarDatePickerController mController;
    protected int mCurrentScrollState = 0;
	protected long mPreviousScrollPosition;
	protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;

    public CalendarDayPickerView(Context context)
    {
        this(context, null);
    }

    public CalendarDayPickerView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CalendarDayPickerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        if (!isInEditMode())
        {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            init(context);
        }
    }

    public void setController(CalendarDatePickerController mController)
    {
        this.mController = mController;
        setUpAdapter();
        setAdapter(mAdapter);
    }


	public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
		mContext = paramContext;
		setUpListView();

        onScrollListener = new OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                final CalendarMonthView child = (CalendarMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };
	}


	protected void setUpAdapter() {
		if (mAdapter == null) {
			mAdapter = new CalendarMonthAdapter(getContext(), mController, typedArray);
        }
		mAdapter.notifyDataSetChanged();
	}

	protected void setUpListView() {
		setVerticalScrollBarEnabled(false);
		setOnScrollListener(onScrollListener);
		setFadingEdgeLength(0);
	}

    public CalendarMonthAdapter.SelectedDays<CalendarMonthAdapter.CalendarDay> getSelectedDays()
    {
        return mAdapter.getSelectedDays();
    }

    protected CalendarDatePickerController getController()
    {
        return mController;
    }

    protected TypedArray getTypedArray()
    {
        return typedArray;
    }
}