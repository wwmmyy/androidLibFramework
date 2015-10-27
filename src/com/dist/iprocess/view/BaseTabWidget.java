package com.dist.iprocess.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dist.iprocess.R;
 

/**
 * 底部导航
 * 
 * @author dewyze
 * 
 */
@SuppressLint("NewApi")
public class BaseTabWidget extends LinearLayout {

	private static final String TAG = "MyTabWidget";
	       private int[] mDrawableIds = new int[] { R.drawable.base_tab0,
                       R.drawable.base_tab1, R.drawable.base_tab0,
                       R.drawable.base_tab0 };
	
	// 存放底部菜单的各个文字CheckedTextView
	private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
	// 存放底部菜单每项View
	private List<View> mViewList = new ArrayList<View>();
	// 存放指示点
	private List<ImageView> mIndicateImgs = new ArrayList<ImageView>();

	// 底部菜单的文字数组
	private CharSequence[] mLabels={"议 程","资 料","笔 记","讨 论"};

	public BaseTabWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

//		TypedArray a = context.obtainStyledAttributes(attrs,
//				R.styleable.TabWidget, defStyle, 0);
//
//		// 读取xml中，各个tab使用的文字
////		mLabels = a.getTextArray(R.styleable.TabWidget_bottom_labels);
////		mLabels={"首页","首页1","首页2","首页3"};
//		        
//		        
//
//		if (null == mLabels || mLabels.length <= 0) {
////			try {
////				throw new CustomException("底部菜单的文字数组未添加...");
////			} catch (CustomException e) {
////				e.printStackTrace();
////			} finally {
////				LogUtils.i(TAG, MyTabWidget.class.getSimpleName() + " 出错");
////			}
//			a.recycle();
//			return;
//		}
//
//		a.recycle();

		// 初始化控件
		init(context);
	}

	public BaseTabWidget(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseTabWidget(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 初始化控件
	 */
	@SuppressLint("ResourceAsColor")
    private void init(final Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(R.drawable.base_tab_bottom_bg);

		LayoutInflater inflater = LayoutInflater.from(context);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.CENTER;

		int size = mLabels.length;
		for (int i = 0; i < size; i++) {

			final int index = i;

			// 每个tab对应的layout
			final View view = inflater.inflate(R.layout.base_tab_item, null);

			final CheckedTextView itemName = (CheckedTextView) view
					.findViewById(R.id.item_name);
			itemName.setCompoundDrawablesWithIntrinsicBounds(null, context
					.getResources().getDrawable(mDrawableIds[i]), null, null);
			itemName.setText(mLabels[i]);

			// 指示点ImageView，如有版本更新需要显示
			final ImageView news_indicate_img = (ImageView) view
					.findViewById(R.id.news_indicate_img);

			this.addView(view, params);

			//设置消息更新红点可见
			if(index==1){
			    news_indicate_img.setVisibility(View.VISIBLE);  
			}
			
			// CheckedTextView设置索引作为tag，以便后续更改颜色、图片等
			itemName.setTag(index);

			// 将CheckedTextView添加到list中，便于操作
			mCheckedList.add(itemName);
			// 将指示图片加到list，便于控制显示隐藏
			mIndicateImgs.add(news_indicate_img);
			// 将各个tab的View添加到list
			mViewList.add(view);

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 设置底部图片和文字的显示
					setTabsDisplay(context, index);

					if (null != mTabListener) {
						// tab项被选中的回调事件
						mTabListener.onTabSelected(index);
					}
				}
			});

			// 初始化 底部菜单选中状态,默认第一个选中
			if (i == 0) {
				itemName.setChecked(true);
				itemName.setTextColor(context.getResources().getColor(R.color.name_blue));
//				itemName.setTextColor(Color.rgb(247, 88, 123));
//				view.setBackgroundColor(Color.rgb(240, 241, 242));
				view.setBackgroundColor(Color.rgb(220, 220, 220));
				
//				    // 字体加粗
//                                TextPaint tp = itemName.getPaint(); 
//                                tp.setFakeBoldText(true); 
				
			} else {
				itemName.setChecked(false);
				itemName.setTextColor(Color.rgb(19, 12, 14));
				view.setBackgroundColor(Color.rgb(250, 250, 250));
			}

		}
	}

	/**
	 * 设置指示点的显示
	 * 
	 * @param context
	 * @param position
	 *            显示位置
	 * @param visible
	 *            是否显示，如果false，则都不显示
	 */
	public void setIndicateDisplay(Context context, int position,
			boolean visible) {
		int size = mIndicateImgs.size();
		if (size <= position) {
			return;
		}
		ImageView indicateImg = mIndicateImgs.get(position);
		indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置底部导航中图片显示状态和字体颜色
	 */
	public void setTabsDisplay(Context context, int index) {
		int size = mCheckedList.size();
		for (int i = 0; i < size; i++) {
			CheckedTextView checkedTextView = mCheckedList.get(i);
			if ((Integer) (checkedTextView.getTag()) == index) {
//				LogUtils.i(TAG, mLabels[index] + " is selected...");
				checkedTextView.setChecked(true); 
				checkedTextView.setTextColor(context.getResources().getColor(R.color.name_blue)); 
				mViewList.get(i).setBackgroundColor(Color.rgb(220, 220, 220)); 
//				// 字体加粗
//				TextPaint tp = checkedTextView.getPaint(); 
//				tp.setFakeBoldText(true);  
//				mViewList.get(i).setAlpha(0.5f);
				//设置消息更新红点可见
	                        if(index==1){
	                            mIndicateImgs.get(i).setVisibility(View.GONE);  
	                        }	
				
			} else {
				checkedTextView.setChecked(false);  
				checkedTextView.setTextColor(Color.rgb(19, 12, 14));
				mViewList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
//				mViewList.get(i).setAlpha(0.3f);
				
////				字体不加粗
//				TextPaint tp = checkedTextView.getPaint(); 
//                                tp.setFakeBoldText(false); 
			}
			
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthSpecMode != MeasureSpec.EXACTLY) {
			widthSpecSize = 0;
		}

		if (heightSpecMode != MeasureSpec.EXACTLY) {
			heightSpecSize = 0;
		}

		if (widthSpecMode == MeasureSpec.UNSPECIFIED
				|| heightSpecMode == MeasureSpec.UNSPECIFIED) {
		}

		// 控件的最大高度，就是下边tab的背景最大高度
		int width;
		int height;
		width = Math.max(getMeasuredWidth(), widthSpecSize);
		height = Math.max(this.getBackground().getIntrinsicHeight(),
				heightSpecSize);
//		height=80;
		setMeasuredDimension(width, height);
	}

	// 回调接口，用于获取tab的选中状态
	private OnTabSelectedListener mTabListener;

	public interface OnTabSelectedListener {
		void onTabSelected(int index);
	}

	public void setOnTabSelectedListener(OnTabSelectedListener listener) {
		this.mTabListener = listener;
	}

}
